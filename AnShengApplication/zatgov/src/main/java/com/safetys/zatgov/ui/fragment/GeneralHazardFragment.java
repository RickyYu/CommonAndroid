package com.safetys.zatgov.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.DateParseUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CheckList;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.bean.WgyHiddenItemInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.TroubleTypeEnum;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.entity.MessageEvent;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.activity.NewZfCheckAddActivity;
import com.safetys.zatgov.ui.activity.NewZfCheckAddActivityWgy;
import com.safetys.zatgov.ui.activity.NewZfCheckItemActivity;
import com.safetys.zatgov.ui.activity.NewZfYhLrActivity;
import com.safetys.zatgov.ui.activity.ViewPhotoActivity;
import com.safetys.zatgov.ui.activity.ZfCheckRecordListActivity;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.wheel.SigleWheelDialog;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.FileUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/*
 * 一般隐患
 * 
 * 
 */
public class GeneralHazardFragment extends Fragment implements OnClickListener,
		onNetCallback, DatePickerFragment.TheListener {

	private EditText ed_yhms;// 隐患描述
	private TextView text_jhzgsj;// 计划整改时间
	private EditText ed_lrsj;// 录入时间
	private ImageView iv_jttime;

	private View btn_yh_type;// 隐患类别
	private TextView text_yh_type;
	private ImageView iv_now;

	private Button mBtn_submit;// 提交按钮
	private LoadingDialogUtil mLoading;

	private File tempFile;// 拍摄缓存照片文件
	protected static int TAKE_PICTURE_REQUEST_CODE = 1;
	private HorizontalScrollView mPicScrollView;
	private LinearLayout mPicsLayout;
	private String companyid;
	private List<File> pics = new ArrayList<File>();// 所有照片
	private Bitmap smallBit;
	private boolean isVis = false;
	private PullToRefresh list_cb;
	private View btn_cb;
	private ImageView iv_arrow;
	private CheckList checkList;
	private Spinner spinner;
	private List<Long> list_id;
	private List<String> list_content;
	private LinkedHashMap<Long, String> hashMap;
	private LinkedHashMap<String, Long> hashMap2;
	private ArrayList<Long> listCheck;
	private ArrayList<Long> listAll;
	private String[] areas;
	private Long checkinfoid;
	private String checkId;

	private String companyId;
	private String tv_place;
	private String tv_phone;
	private String tv_people;
	private String tv_law;
	private String tv_now = null;
	private ArrayList<HyCheckItemInfo> listHy;
	private String zgtime;
	private String nowtime;
	private List<File> listfile;
	private ArrayList<Long> list2;

	private boolean ischeck;
	private String yhlb;
	private String yhms;
	private String jhzgsj;
	private String source = "";
	
	//用于传回检查表检查数据
	private int groupPosition ;
	private WgyHiddenItemInfo hiddenItemInfo ;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.fragment_ybyh, null);
		initView(mView);
		return mView;
	}

	private void initView(View mView) {
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();
		source = bundle.getString("source");

		if (source == null) {
			ishave = bundle.getString("ishave");
			checkList = (CheckList) bundle.getSerializable("checklist");
			checkId = checkList.getCheckId();
			companyId = checkList.getCompanyId();

			list2 = checkList.getListCb();
			tv_place = checkList.getPlace();
			tv_phone = checkList.getPhone();
			tv_people = checkList.getPeople();
			tv_law = checkList.getLaw();
			zgtime = checkList.getZgtime();
			nowtime = checkList.getChecktime();
			list2 = checkList.getListCb();
			listfile = checkList.getListfile();
			ischeck = checkList.isCheck();
			if (checkList.getNowcontent() != null) {
				tv_now = checkList.getNowcontent();
			}

			lxrs = checkList.getLxr();
			if (checkList.getListHy() != null) {
				listHy = checkList.getListHy();
			} else {
				listHy = null;
			}
		}else{
			groupPosition = bundle.getInt("position");
			hiddenItemInfo = (WgyHiddenItemInfo) bundle.getSerializable("hiddeninfo");
		}
		mLoading = new LoadingDialogUtil(getActivity(), "请稍后...");
		hashMap2 = new LinkedHashMap<String, Long>();
		ed_yhms = (EditText) mView.findViewById(R.id.ed_yhms);
		text_jhzgsj = (TextView) mView.findViewById(R.id.tv_jhtime);
		ed_lrsj = (EditText) mView.findViewById(R.id.ed_lrsj);
		iv_jttime = (ImageView) mView.findViewById(R.id.iv_jhtime);
		iv_jttime.setOnClickListener(this);
		iv_now = (ImageView) mView.findViewById(R.id.iv_now2);
		iv_now.setOnClickListener(this);
		btn_yh_type = mView.findViewById(R.id.btn_yh_type);
		text_yh_type = (TextView) mView.findViewById(R.id.text_yh_type);
		btn_yh_type.setOnClickListener(this);

		mBtn_submit = (Button) mView.findViewById(R.id.btn_submit);
		mBtn_submit.setOnClickListener(this);
		// 获取当前时间
		ed_lrsj.setText(DateParseUtils.stampToDate(System.currentTimeMillis()));
		mPicScrollView = (HorizontalScrollView) mView
				.findViewById(R.id.scrollview2);
		mPicsLayout = (LinearLayout) mView.findViewById(R.id.photos2);

		btn_cb = mView.findViewById(R.id.btn_cb);

		spinner = (Spinner) mView.findViewById(R.id.spinner);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				checkinfoid = hashMap2.get(areas[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				checkinfoid = (long) -1;
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			getActivity().finish();
			break;
		case R.id.btn_cb:
			if (isVis) {
				isVis = false;
				list_cb.setVisibility(View.VISIBLE);
				iv_arrow.setImageResource(R.mipmap.arrow_down);
			} else {
				isVis = true;
				list_cb.setVisibility(View.GONE);
				iv_arrow.setImageResource(R.mipmap.arrow_up);
			}

			break;

		case R.id.iv_jhtime:
			DatePickerFragment fragment = new DatePickerFragment(this, "luru", getActivity(), getResources().getString(R.string.error_date_tips));
			fragment.show(getFragmentManager(), "datePicker");
			break;
		case R.id.btn_yh_type:
			ArrayList<String> mDatas = new ArrayList<String>();
			Collections.addAll(mDatas, TroubleTypeEnum.typedata);
			SigleWheelDialog mChangeAddressDialog = new SigleWheelDialog(
					getActivity(), mDatas);
			mChangeAddressDialog.setText(text_yh_type.getText().toString());
			mChangeAddressDialog.show();
			mChangeAddressDialog.setAddresskListener(new SigleWheelDialog.OnTextCListener() {

				@Override
				public void onClick(String mText) {
					text_yh_type.setText(mText);
				}
			});
			break;

		case R.id.iv_now2:
			if (pics.size() < Const.MAX_PICTURE_SIZE) {
				// 调用手机系统相机
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				tempFile = new File(AppConfig.buildPath(AppConfig.HOME_CACHE),
						UUID.randomUUID().toString().replace("-", "") + ".jpg");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
				if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
					try {//6.0以上会报权限错误
						startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				DialogUtil
						.showMsgDialog(getActivity(), "最大上传图片数量为"
								+ Const.MAX_PICTURE_SIZE + "张，无法再继续拍照，谢谢。",
								false, null);
			}
			break;
		case R.id.btn_submit:
			if (submit()) {
				return;
			}
			// 检查表新增隐患
			if (source != null) {
				Intent intent = new Intent(getActivity(),
						NewZfCheckAddActivity.class);
				Bundle bundle = new Bundle();
				hiddenItemInfo.setDes(yhms);
				if(pics.size() != 0){
					hiddenItemInfo.setImageFiles(pics);
				}
				hiddenItemInfo.setYhlb(TroubleTypeEnum.getValue(text_yh_type.getText().toString()));
				bundle.putInt("position", groupPosition);
				bundle.putSerializable("hiddeninfo", hiddenItemInfo);
				intent.putExtras(bundle);
				getActivity().setResult(
						NewZfCheckAddActivityWgy.ADD_HIDDEN_WITH_TABLE_CODE,
						intent);
				//pics.clear();
				getActivity().finish();

			} else {
				if (!checkId.isEmpty()) {
					mLoading.show();
					HttpRequestHelper.getInstance().submitGenealTrouble(
							getActivity(), null, null, companyId, checkId, lxrs,
							tv_phone, yhlb, yhms, jhzgsj, pics,
							ed_lrsj.getText().toString(), checkinfoid + "",
							null, false, Const.SUB_NOMARL_CHECK,
							GeneralHazardFragment.this);

				} else {
					mLoading.show();
					HttpRequestHelper.getInstance().submitCheck(getActivity(),false,
							companyId, nowtime, tv_place, tv_phone, list2,
							tv_people, tv_law, listfile, ischeck, zgtime,
							tv_now, "-1", listHy, Const.NET_ADD_CHECK_ITEM,
							GeneralHazardFragment.this);
				}
			}

			break;
		default:
			break;
		}
	}

	/**
	 * 检查文本是否都已经输入
	 * 
	 * @return
	 */
	private boolean submit() {

		yhlb = TroubleTypeEnum.getValue(text_yh_type.getText().toString());
		yhms = ed_yhms.getText().toString();
		jhzgsj = text_jhzgsj.getText().toString();
		if (TextUtils.isEmpty(yhms)) {
			ToastUtils.showMessage(getActivity(), "隐患描述不能为空");
			return true;
		}
		if(pics.size()<1){
			ToastUtils.showMessage(getActivity(), "现场图片不能为空");
			return true;
		}
		if (TextUtils.isEmpty(jhzgsj)) {
			ToastUtils.showMessage(getActivity(), "计划整改不能为空");
			return true;
		}
	
		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		// 拍照，照片删除
		if (requestCode == TAKE_PICTURE_REQUEST_CODE
				&& resultCode == Activity.RESULT_OK && tempFile != null
				&& tempFile.length() > 0) {
			handlePicture();
		}
	}

	/**
	 * 照片处理
	 */
	private void handlePicture() {
		try {
			tempFile = FileUtil.getCompressFile(tempFile);
			if (pics.size() < Const.MAX_PICTURE_SIZE) {
				pics.add(tempFile);
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				final RelativeLayout picture = (RelativeLayout) inflater
						.inflate(R.layout.photo, null);
				ImageView photo = (ImageView) picture.findViewById(R.id.photo);
				ImageButton delete = (ImageButton) picture
						.findViewById(R.id.delete);
				delete.setTag(tempFile);
				picture.setTag(tempFile.getAbsolutePath());
				// 缩略图使用压缩后的
				// photo.setImageBitmap(smallBit);
				x.image().bind(photo, tempFile.getAbsolutePath(),
						getImageOptions());
				mPicsLayout.addView(picture);
				mPicScrollView.setVisibility(View.VISIBLE);
				// 删除
				delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(final View v) {
						DialogUtil.showDefaultAlertDialog(getActivity(), "提示",
								"确定删除吗？",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										mPicsLayout.removeView(picture);
										pics.remove(v.getTag());
										//
										tempFile = null;
									}
								});
					}
				});
				// 弹出查看
				picture.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						DialogUtil.showDefaultProgressDialog(getActivity());
						// 弹出传递照片地址
						showPicture(v.getTag().toString());
					}
				});
			}
		} catch (Exception e) {
			LogUtil.i("error:" + e.getMessage());
		}
	}

	// 弹出查看照片
	protected void showPicture(String picPath) {
		Intent intent = new Intent(getActivity(), ViewPhotoActivity.class);
		intent.putExtra("picPath", picPath);
		startActivity(intent);
	}

	private ImageOptions getImageOptions() {
		return new ImageOptions.Builder()
				.setSize(DensityUtil.dip2px(300), DensityUtil.dip2px(397))
				.setRadius(DensityUtil.dip2px(5)).setCrop(false)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP).build();
	}

	@Override
	public void onError(String errorMsg) {
		mLoading.dismiss();
		DialogUtil.showMsgDialog(getActivity(), errorMsg, true, null);
	}

	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		switch (requestCode) {
		case Const.SUB_NOMARL_CHECK:
			mLoading.dismiss();
			ToastUtils.showMessage(getActivity(), "提交隐患成功");
			EventBus.getDefault().post(new MessageEvent(NewZfYhLrActivity.ACTION_UPDATE));
			EventBus.getDefault().post(new MessageEvent(NewZfCheckItemActivity.ACTION_UPDATE));
			EventBus.getDefault().post(new MessageEvent(ZfCheckRecordListActivity.ACTION_UPDATE));

			setGen(true);
			Intent mIntent = new Intent(getActivity(), NewZfYhLrActivity.class);

			
			mIntent.putExtra("ishave", "yes");
			checkList.setCheckId(checkId);
			mIntent.putExtra("checklist", (Serializable) checkList);

			startActivity(mIntent);
			getActivity().finish();
			break;

		case Const.NET_ADD_CHECK_ITEM:
			if (mJsonResult.getEntity() != null) {

				HyCheckItemInfo mCheckItemInfo = JSON.parseObject(
						mJsonResult.getEntity(), HyCheckItemInfo.class);

				checkId = mCheckItemInfo.getNoteId();

				HttpRequestHelper.getInstance().submitGenealTrouble(
						getActivity(), null, null, companyId, checkId, lxrs,
						tv_phone, yhlb, yhms, jhzgsj, pics,
						ed_lrsj.getText().toString(), checkinfoid + "", null,
						false, Const.SUB_NOMARL_CHECK, this);

			}
			break;

		case 1:
			mLoading.dismiss();
			if (mJsonResult.getJson() != null) {

				List<HyCheckItemInfo> list = JSONArray.parseArray(
						(String) mJsonResult.getJson(), HyCheckItemInfo.class);
				list_id = new ArrayList();

				list_content = new ArrayList();
				hashMap = new LinkedHashMap<Long, String>();
				hashMap2 = new LinkedHashMap<String, Long>();

				areas = new String[mJsonResult.getTotalCount() + 1];

				int i = 0;
				for (HyCheckItemInfo heCheckInfo : list) {
					list_content.add(heCheckInfo.getContent());
					list_id.add(heCheckInfo.getId());

					areas[i] = heCheckInfo.getContent();
					i++;
					hashMap2.put(heCheckInfo.getContent(), heCheckInfo.getId());
					hashMap.put(heCheckInfo.getId(), heCheckInfo.getContent());
				}
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
						getActivity(), R.layout.item_spinner_view, areas);
				arrayAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// 设置显示的数据
				spinner.setAdapter(arrayAdapter);
			}

			break;
		default:
			break;
		}

	}

	@Override
	public void returnDate(String date) {

		text_jhzgsj.setText(date);
	}

	private boolean isGen = false;
	private String ishave;
	private String lxrs;

	public boolean isGen() {
		return isGen;
	}

	public void setGen(boolean isGen) {
		this.isGen = isGen;
	}

}
