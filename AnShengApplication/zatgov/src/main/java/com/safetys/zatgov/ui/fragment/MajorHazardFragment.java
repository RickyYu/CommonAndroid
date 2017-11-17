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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.DateParseUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CheckList;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.activity.EnterpriseListActivity;
import com.safetys.zatgov.ui.activity.NewZfCheckItemActivity;
import com.safetys.zatgov.ui.activity.NewZfYhLrActivity;
import com.safetys.zatgov.ui.activity.ViewPhotoActivity;
import com.safetys.zatgov.ui.activity.ZfCheckRecordListActivity;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.EditTextUtils;
import com.safetys.zatgov.utils.FileUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * 新增重大隐患
 * @author ricky
 *
 */
public class MajorHazardFragment extends Fragment implements OnClickListener,
		DatePickerFragment.TheListener, onNetCallback {
	private EditText ed_qyfr;// 单位地址
	private EditText ed_lrsj2;// 录入时间
	private EditText ed_yhdz;// 隐患地址
	private EditText ed_lxr2;// 联系人
	private EditText ed_phonenumber2;// 手机
	private EditText ed_phone2;// 电话
	private EditText ed_yhjbqk;// 隐患基本情况
	private EditText ed_lszljf;// 落实治理经费
	private EditText ed_dwfzr;// 单位负责人
	private EditText ed_tbr;// 填报人
	private TextView tv_jhtime;// 计划完成治理时间
	private ImageView iv_jttime;
	private ImageView iv_now;
	private Button mBtn_submit;// 提交

	private CheckBox mCB_isDzqy;// 重大企业是否
	private CheckBox mCB_isZfxt;// 是否政府协调
	private CheckBox mCB_isJbtc;// 是否局部停产
	private CheckBox mCB_isQbtc;// 是否全部停产
	private CheckBox mCB_isZlmb;// 落实治理目标
	private CheckBox mCB_isJgry;// 落实治理机构人员
	private CheckBox mCB_isYjya;// 落实安全促使及应急预案
	private CheckBox mCB_isJfwz;// 落实治理经费物资

	private LoadingDialogUtil mLoading;
	private View btn_yh_qy;// 隐患区域
	private TextView text_yh_qy;
	private String yh_qy_c_code = "";// 城市code
	private String yh_qy_z_code = "";// 镇code
	private String yh_qy_j_code = "";// 街道code

	private Calendar c = null;

	private File tempFile;// 拍摄缓存照片文件
	protected static int mTakePictureRequestCode = 1;
	private HorizontalScrollView mPicScrollView;
	private LinearLayout mPicsLayout;
	
	private List<File> pics = new ArrayList<File>(0);// 所有照片
	private Bitmap smallBit;
	private String companyid;
	private PullToRefresh list_cb;
	private View btn_cb;
	private boolean isVis=false;
	private ImageView iv_arrow;
//	private ArrayList<Data> mdatas;
//	private MyListAdapter mAdapter;
	private CheckList checkList;
	private Spinner spinner;
	private LinkedHashMap<Long, String> hashMap;
	private LinkedHashMap<String, Long> hashMap2;
	private List<Long> list_id;
	private List<String> list_content;
	private ArrayList<Long> listCheck;
	private ArrayList<Long> listAll;
	private String[] areas;
	private Long checkinfoid;
	private String checkId;//检查记录ID
	LayoutInflater inflater = null;


	private String companyId;
	private String tv_place;
	private String tv_phone;
	private String tv_people;
	private String tv_law;
	private String tv_now;
	private ArrayList<HyCheckItemInfo> listHy;
	private String zgtime;
	private String nowtime;
	private List<File> listfile;
	private ArrayList<Long> list2;

	private boolean ischeck;
	private String yhlb;
	private String yhms;
	private String jhzgsj;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.fragment_zdyh, null);
		initView(mView);
		return mView;
	}

	private void initView(View mView) {
		hashMap2=new LinkedHashMap<String, Long>();
		ed_lrsj2 = (EditText) mView.findViewById(R.id.ed_lrsj2);
		ed_yhdz = (EditText) mView.findViewById(R.id.ed_yhdz);
		ed_lxr2 = (EditText) mView.findViewById(R.id.ed_lxr2);
		ed_phone2 = (EditText) mView.findViewById(R.id.ed_phone2);
		ed_phonenumber2 = (EditText) mView.findViewById(R.id.ed_phonenumber2);
		ed_lszljf = (EditText) mView.findViewById(R.id.ed_lszljf);
		EditTextUtils.editWatcher(ed_lszljf, null);
		ed_yhjbqk = (EditText) mView.findViewById(R.id.ed_yhjbqk);
		ed_dwfzr = (EditText) mView.findViewById(R.id.ed_dwfzr);
		ed_tbr = (EditText) mView.findViewById(R.id.ed_tbr);
		tv_jhtime = (TextView) mView.findViewById(R.id.tv_jhtime);
		iv_jttime = (ImageView) mView.findViewById(R.id.iv_jhtime);
		iv_now = (ImageView) mView.findViewById(R.id.iv_now);
		mBtn_submit = (Button) mView.findViewById(R.id.btn_submit);
		iv_now.setOnClickListener(this);
		iv_jttime.setOnClickListener(this);
		mBtn_submit.setOnClickListener(this);

		mCB_isDzqy = (CheckBox) mView.findViewById(R.id.cb_zdqy);// 重大企业是否
		mCB_isZfxt = (CheckBox) mView.findViewById(R.id.cb_zf);// 是否政府协调
		mCB_isJbtc = (CheckBox) mView.findViewById(R.id.cb_tcty);// 是否局部停产
		mCB_isQbtc = (CheckBox) mView.findViewById(R.id.cb_qbtc);// 是否全部停产
		mCB_isZlmb = (CheckBox) mView.findViewById(R.id.cb_zlmb);// 落实治理目标
		mCB_isJgry = (CheckBox) mView.findViewById(R.id.cb_zljgry);// 落实治理机构人员
		mCB_isYjya = (CheckBox) mView.findViewById(R.id.cb_yjya);// 落实安全促使及应急预案
		mCB_isJfwz = (CheckBox) mView.findViewById(R.id.cb_jfwz);// 落实治理经费物资
		View c1 = mView.findViewById(R.id.c1);// 重大企业是否
		View c2 = mView.findViewById(R.id.c2);// 是否政府协调
		View c3 = mView.findViewById(R.id.c3);// 是否局部停产
		View c4 = mView.findViewById(R.id.c4);// 是否全部停产
		View c5 = mView.findViewById(R.id.c5);// 落实治理目标
		View c6 = mView.findViewById(R.id.c6);// 落实治理机构人员
		View c7 = mView.findViewById(R.id.c7);// 落实安全促使及应急预案
		View c8 = mView.findViewById(R.id.c8);// 落实治理经费物资
		c1.setOnClickListener(this);
		c2.setOnClickListener(this);
		c3.setOnClickListener(this);
		c4.setOnClickListener(this);
		c5.setOnClickListener(this);
		c6.setOnClickListener(this);
		c7.setOnClickListener(this);
		c8.setOnClickListener(this);
		btn_yh_qy = mView.findViewById(R.id.btn_yh_qy);
		text_yh_qy = (TextView) mView.findViewById(R.id.text_yh_qy);
		btn_yh_qy.setOnClickListener(this);
		ed_lrsj2.setText(DateParseUtils.stampToDate(System.currentTimeMillis()));
		mPicScrollView = (HorizontalScrollView) mView
				.findViewById(R.id.scrollview1);
		mPicsLayout = (LinearLayout) mView.findViewById(R.id.photos1);
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
				checkinfoid=(long) -1;
				
			}
		});
		
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();
		String source = bundle.getString("source");
		if (source == null){
			ishave = bundle.getString("ishave");
			
			checkList = (CheckList) bundle.getSerializable(
					"checklist");
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
			if (checkList.getNowcontent()!=null) {
				tv_now=checkList.getNowcontent();
			}
			if (checkList.getListHy()!=null) {
				listHy=checkList.getListHy();
			}else {
				listHy=null;
			}
		}
		mLoading = new LoadingDialogUtil(getActivity(), "请稍后...");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			getActivity().finish();
			break;
		case R.id.btn_cb:
			if (isVis) {
				isVis=false;
				list_cb.setVisibility(View.VISIBLE);
				iv_arrow.setImageResource(R.mipmap.arrow_down);
			}else {
				isVis=true;
				list_cb.setVisibility(View.GONE);
				iv_arrow.setImageResource(R.mipmap.arrow_up);
			}
			
			break;
		case R.id.c1:
			Boolean isCheck = mCB_isDzqy.isChecked();
			mCB_isDzqy.setChecked(!isCheck);

			break;
		case R.id.c2:
			Boolean isCheck2 = mCB_isZfxt.isChecked();
			mCB_isZfxt.setChecked(!isCheck2);

			break;
		case R.id.c3:
			Boolean isCheck3 = mCB_isJbtc.isChecked();
			mCB_isJbtc.setChecked(!isCheck3);

			break;
		case R.id.c4:
			Boolean isCheck4 = mCB_isQbtc.isChecked();
			mCB_isQbtc.setChecked(!isCheck4);

			break;
		case R.id.c5:
			Boolean isCheck5 = mCB_isZlmb.isChecked();
			mCB_isZlmb.setChecked(!isCheck5);

			break;
		case R.id.c6:
			Boolean isCheck6 = mCB_isJgry.isChecked();
			mCB_isJgry.setChecked(!isCheck6);

			break;
		case R.id.c7:
			Boolean isCheck7 = mCB_isYjya.isChecked();
			mCB_isYjya.setChecked(!isCheck7);

			break;
		case R.id.c8:
			Boolean isCheck8 = mCB_isJfwz.isChecked();
			mCB_isJfwz.setChecked(!isCheck8);

			break;

		case R.id.iv_jhtime:
			DatePickerFragment fragment = new DatePickerFragment(this, "luru", getActivity(), getResources().getString(R.string.error_date_tips));
			fragment.show(getFragmentManager(), "datePicker");
			break;

		case R.id.btn_yh_qy:
			//// FIXME: 2017/11/16 
	/*		new StreetSelectDialog(getActivity(), new OnTextCListener() {

				@Override
				public void onClick(String mText, String pcode, String ccode,
						String dcode) {
					text_yh_qy.setText(mText);
					yh_qy_c_code = pcode;
					yh_qy_z_code = ccode;
					yh_qy_j_code = dcode;
				}
			}).show();*/
			break;

		case R.id.iv_now:
			if (pics.size() < Const.MAX_PICTURE_SIZE) {
			// 调用手机系统相机
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			tempFile = new File(AppConfig.buildPath(AppConfig.HOME_CACHE), UUID
					.randomUUID().toString().replace("-", "")
					+ ".jpg");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
			if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
				startActivityForResult(intent, mTakePictureRequestCode);
			}}else{
				DialogUtil.showMsgDialog(getActivity(), "最大上传图片数量为"+Const.MAX_PICTURE_SIZE+"张，无法再继续拍照，谢谢。", false, null);
			}

			break;
		case R.id.btn_submit:
			if (submit()) {
				return;
			}
			if (!checkId.isEmpty()) {
				submitMajorTrouble();
				
			}else {
				
				mLoading.show();
				HttpRequestHelper.getInstance().submitCheck(getActivity(),false, companyId,
						nowtime, tv_place, tv_phone, list2, tv_people, tv_law,
						listfile, ischeck, zgtime, tv_now, "-1", listHy,
						Const.NET_ADD_CHECK_ITEM, MajorHazardFragment.this);
			}
			
			break;
		default:
			break;
		}

	}
	
	private void submitMajorTrouble(){
		mLoading.show();
		HttpRequestHelper.getInstance().submitMajorTrouble(getActivity(), companyId,checkId,
				mCB_isDzqy.isChecked(), yhAddress, yh_qy_c_code, yh_qy_z_code,
				yh_qy_j_code, linkName, linktelete, linkphone, yhqk,
				mCB_isZfxt.isChecked(), mCB_isJbtc.isChecked(),
				mCB_isQbtc.isChecked(), mCB_isZlmb.isChecked(),
				mCB_isJgry.isChecked(), mCB_isYjya.isChecked(),
				mCB_isJfwz.isChecked(), jhTime, lsjf, frz,
				ed_lrsj2.getText().toString(), tbr, pics,checkinfoid+"",
				Const.SUB_MAJOR_CHECK, MajorHazardFragment.this);
	
	}

	/**
	 * 提交重大隐患
	 */
	private boolean submit() {
		yhAddress = ed_yhdz.getText().toString();
		linkName = ed_lxr2.getText().toString();
		linktelete = ed_phone2.getText().toString();
		linkphone = ed_phonenumber2.getText().toString();
		yhqk = ed_yhjbqk.getText().toString();
		jhTime = tv_jhtime.getText().toString();
		frz = ed_dwfzr.getText().toString();
		tbr = ed_tbr.getText().toString();
		lsjf = ed_lszljf.getText().toString();
		if (TextUtils.isEmpty(yhAddress)) {
			ToastUtils.showMessage(getActivity(), "请输入隐患地址。");
			return true;
		}

		if (TextUtils.isEmpty(yh_qy_j_code)) {
			ToastUtils.showMessage(getActivity(), "请选择隐患地址。");
			return true;
		}
		if (TextUtils.isEmpty(linkName)) {
			ToastUtils.showMessage(getActivity(), "请输入联系人。");
			return true;
		}
		if (TextUtils.isEmpty(linktelete)) {
			ToastUtils.showMessage(getActivity(), "请输入联系电话。");
			return true;
		}
		if (!linktelete.contains("-")) {
			if (linktelete.length() == 7 || linktelete.length() == 8) {
			} else {
				ToastShow("联系电话格式有问题，请参照027-1234567或0574-12345678重新填写");
				return true; 
			}
		} else {
			String[] s1 = linktelete.split("-");
			String s2 = s1[0];
			String s3 = s1[1];
			if (s2.length()==3||s2.length()==4) {
				if (s3.length()==7||s3.length()==8) {
					
				}else {
					ToastShow("联系电话格式有问题，请参照027-1234567或0574-12345678重新填写");	
					return true;
				}
			}else {
				ToastShow("联系电话格式有问题，请参照027-1234567或0574-12345678重新填写");
				return true;
			}

		}
		if (TextUtils.isEmpty(linkphone)) {
			ToastUtils.showMessage(getActivity(), "请输入手机号。");
			return true;
		}
		if (linkphone.length()!=11) {
			ToastUtils.showMessage(getActivity(), "手机号位数异常，请重新填写");
			return true;
		}
		if (TextUtils.isEmpty(yhqk)) {
			ToastUtils.showMessage(getActivity(), "请输入隐患基本情况。");
			return true;
		}
		if(pics.size()<1){
			ToastUtils.showMessage(getActivity(), "现场图片不能为空");
			return true;
		}
		if (TextUtils.isEmpty(jhTime)) {
			ToastUtils.showMessage(getActivity(), "请选择计划完成治理时间。");
			return true;
		}
		if (TextUtils.isEmpty(frz)) {
			ToastUtils.showMessage(getActivity(), "请填写单位负责人。");
			return true;
		}
		if (TextUtils.isEmpty(tbr)) {
			ToastUtils.showMessage(getActivity(), "请填写填报人。");
			return true;
		}
		if (TextUtils.isEmpty(lsjf)) {
			ToastUtils.showMessage(getActivity(), "请填写落实治理经费。");
			return true;
		}
		
		return false;
	}

	private void ToastShow(String string) {
		Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		// 拍照，照片删除
		if (requestCode == mTakePictureRequestCode
				&& resultCode == Activity.RESULT_OK && tempFile != null
				&& tempFile.length() > 0) {
			try {
				tempFile= FileUtil.getCompressFile(tempFile);
				if (pics.size() < 10) {
					
				pics.add(tempFile);
//				smallBit = ImageUtil.getThumbnails(tempFile.getAbsolutePath());
				if(inflater == null){
					inflater = LayoutInflater.from(getActivity());
				}
				final RelativeLayout picture = (RelativeLayout) inflater
						.inflate(R.layout.photo, null);
				ImageView photo = (ImageView) picture.findViewById(R.id.photo);
				ImageButton delete = (ImageButton) picture
						.findViewById(R.id.delete);
				delete.setTag(tempFile);
				picture.setTag(tempFile.getAbsolutePath());
				// 缩略图使用压缩后的
//				photo.setImageBitmap(smallBit);
				x.image().bind(photo, tempFile.getAbsolutePath(), getImageOptions());
				mPicsLayout.addView(picture);
				mPicScrollView.setVisibility(View.VISIBLE);
				// 删除监听
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
										tempFile=null;
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
				});}
			} catch (Exception e) {
				LogUtil.i("error:" + e.getMessage());
			}
		}
	}

	// 弹出查看照片
	protected void showPicture(String picPath) {
		Intent intent = new Intent(getActivity(), ViewPhotoActivity.class);
		intent.putExtra("picPath", picPath);
		startActivity(intent);
	}

	@Override
	public void returnDate(String date) {
		tv_jhtime.setText(date);
	}

	@Override
	public void onError(String errorMsg) {
		DialogUtil.showMsgDialog(getActivity(), errorMsg, true, null);
	}

	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		mLoading.dismiss();
		switch (requestCode) {
		case Const.SUB_MAJOR_CHECK:
			ToastUtils.showMessage(getActivity(), "提交隐患成功");
			getActivity().sendBroadcast(new Intent(NewZfYhLrActivity.ACTION_UPDATE_LIST_YH));
			getActivity().sendBroadcast(new Intent(NewZfCheckItemActivity.ACTION_UPDATE_LIST_YH_ITEM));
			getActivity().sendBroadcast(new Intent(ZfCheckRecordListActivity.ACTION_UPDATE_LIST_CHECK_NEW));
			getActivity().sendBroadcast(new Intent(
					EnterpriseListActivity.ACTION_UPDATE_LIST_YH));
			setMajor(true);
			Intent	mIntent = new Intent(getActivity(),
					NewZfYhLrActivity.class);
	
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
			
				submitMajorTrouble();
				
			}
			break;
			
		case 1:
			if (mJsonResult.getJson() != null) {

				List<HyCheckItemInfo> list = JSONArray.parseArray(
						(String) mJsonResult.getJson(), HyCheckItemInfo.class);
				list_id = new ArrayList();
				list_content = new ArrayList();
				hashMap = new LinkedHashMap<Long, String>();
				hashMap2 = new LinkedHashMap<String, Long>();

				 areas = new String[mJsonResult.getTotalCount()+1];
				int i = 0;
				for (HyCheckItemInfo heCheckInfo : list) {
					list_content.add(heCheckInfo.getContent());
					list_id.add(heCheckInfo.getId());
					areas[i] = heCheckInfo.getContent();
					i++;
					hashMap2.put(heCheckInfo.getContent(), heCheckInfo.getId());
					hashMap.put( heCheckInfo.getId(),heCheckInfo.getContent());
				}
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
						getActivity(), R.layout.item_spinner_view, areas);
				arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// 设置显示的数据
				spinner.setAdapter(arrayAdapter);
			}

			break;
		default:
			break;
		}

	}
	
	private String yhAddress;
	private String linkName;
	private String linktelete;
	private String linkphone;
	private String yhqk;
	private String jhTime;
	private String frz;
	private String tbr;
	private String lsjf;
	private ImageOptions mImageOptions;
	private ImageOptions getImageOptions() {
		if (mImageOptions == null) {
			mImageOptions = new ImageOptions.Builder()
					.setSize(DensityUtil.dip2px(300), DensityUtil.dip2px(397))
					.setRadius(DensityUtil.dip2px(5))
					// 如果ImageView的大小不是定义为wrap_content, 不要crop.
					.setCrop(false) // 很多时候设置了合适的scaleType也不需要它.
					// 加载中或错误图片的ScaleType
					// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
					.setImageScaleType(ImageView.ScaleType.CENTER_CROP).build();
		}
		return mImageOptions;
	}
	
	private boolean isMajor=false;
	private String ishave;


	public boolean isMajor() {
		return isMajor;
	}

	public void setMajor(boolean isMajor) {
		this.isMajor = isMajor;
	}
	
	
}
