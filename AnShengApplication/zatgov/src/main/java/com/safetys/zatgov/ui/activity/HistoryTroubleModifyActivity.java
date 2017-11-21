package com.safetys.zatgov.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CpyGelCheckInfoVo;
import com.safetys.zatgov.bean.CpyGelImageInfoVo;
import com.safetys.zatgov.bean.ImageInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.TroubleTypeEnum;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.wheel.SigleWheelDialog;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.ImageUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 企业一般隐患详情
 */
public class HistoryTroubleModifyActivity extends BaseActivity implements
		OnClickListener, onNetCallback {
	private Calendar calendar = null;
	private LoadingDialogUtil mLoading;
	private View mBtnBack;
	private View mRllHiddenType;// 隐患类别
	private TextView mTvHiddenType;// 隐患类别
	private ImageView iv0;
	private ImageView iv2;
	private final static int DATE_DIALOG = 1;
	private final static int DATE_DIALOG2 = 2;
	private Bitmap smallBit;
	private File tempFile;// 拍摄缓存照片文件

	protected static int mTakePictureRequestCode = 1;
	private List<File> pics = new ArrayList<File>();// 所有照片
	private HorizontalScrollView mPicScrollView;
	private LinearLayout mPicsLayout;
	private HorizontalScrollView mPicScrollView2;
	private LinearLayout mPicsLayout2;
	private EditText linkMan;
	private EditText linkTel;
	private EditText linkMo;
	private TextView rectificationPlanTime;
	private TextView completedDate;
	private EditText des;
	private Button btn_submit;
	private EditText bz;
	private ImageView photo;
	private ImageButton delete;
	private ImageView iv_jhzgtime;
	private ImageView iv_wctime;
	private String hiddenId;
	private RelativeLayout picture;
	private String type;
	private ImageButton delete2;
	private String deletephoto = "0";
	private View pic2;
	private CheckBox cb_check;
	private View rlcheck;
	private boolean isGov;
	private boolean isRepaired;
	LayoutInflater inflater = null;
	private View govPic;
	private RelativeLayout rllRecord;
	private long reviewId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_trouble_modify);
		initView();
	}


	private void initView() {
		mBtnBack = findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(this);
		Intent intent = this.getIntent();
		hiddenId = intent.getExtras().getString("id");
		setHeadTitle("一般隐患查看");
		mLoading = new LoadingDialogUtil(this, "请稍等");
		loadingData();
		rlcheck = findViewById(R.id.rlcheck);
		rllRecord = (RelativeLayout) findViewById(R.id.rll_record);
		rllRecord.setOnClickListener(this);
		first = findViewById(R.id.first);
		cb_check = (CheckBox) findViewById(R.id.cb_check);
		mRllHiddenType = findViewById(R.id.btn_zg_type);
		mTvHiddenType = (TextView) findViewById(R.id.text_zg_type);
		linkMan = (EditText) findViewById(R.id.tv_lxr);
		linkTel = (EditText) findViewById(R.id.tv_lxdh);
		linkMo = (EditText) findViewById(R.id.tv_sj);
		des = (EditText) findViewById(R.id.tv_yhms);
		bz = (EditText) findViewById(R.id.tv_bz);
		rectificationPlanTime = (TextView) findViewById(R.id.tv_jhzgtime);
		completedDate = (TextView) findViewById(R.id.tv_wctime);
		iv_jhzgtime = (ImageView) findViewById(R.id.iv_jhzgtime);
		iv_wctime = (ImageView) findViewById(R.id.iv_wctime);
		iv_jhzgtime.setOnClickListener(this);
		iv_wctime.setOnClickListener(this);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		iv2 = (ImageView) findViewById(R.id.iv2);
		iv0 = (ImageView) findViewById(R.id.iv_now1);
		iv0.setOnClickListener(this);
		delete2 = (ImageButton) findViewById(R.id.delete2);
		delete2.setOnClickListener(this);
		pic2 = findViewById(R.id.pic2);
		mPicScrollView = (HorizontalScrollView) findViewById(R.id.scrollview);
		mPicsLayout = (LinearLayout) findViewById(R.id.photos);

		govPic = findViewById(R.id.govPic);

		mPicScrollView2 = (HorizontalScrollView) findViewById(R.id.scrollview2);
		mPicsLayout2 = (LinearLayout) findViewById(R.id.photos2);

		mTvHiddenType.setTextColor(Color.parseColor("#BDBDBD"));
		completedDate.setTextColor(Color.parseColor("#BDBDBD"));
		rectificationPlanTime.setTextColor(Color.parseColor("#BDBDBD"));
		ll_time = findViewById(R.id.ll_time);
		ll_phone = findViewById(R.id.ll_phone);
		ll_checkcontent = findViewById(R.id.ll_checkcontent);
		tv_checkcontent = (TextView) findViewById(R.id.tv_checkcontent);
	}

	private void loadingData() {
		mLoading.show();
		HttpRequestHelper.getInstance().getCompanyGenealItemInfo(this,
				hiddenId, Const.NET_GET_COMPANY_GENERAL_ITEM_INFO_CODE, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rlcheck:
			Boolean isCheck = cb_check.isChecked();
			cb_check.setChecked(!isCheck);
			break;
		case R.id.rll_record:
			if (reviewId != 0) {
				Intent intent2 = new Intent(this, ReviewShowActivity.class);
				Bundle bundle2 = new Bundle();
				bundle2.putLong("reviewId", reviewId);
				intent2.putExtras(bundle2);
				startActivity(intent2);
			} else {
				ToastUtils.showMessage(getApplicationContext(), "该隐患暂未复查");
			}

			break;
		case R.id.btn_back:
			finish();
			break;

		case R.id.iv_wctime:
			showDialog(DATE_DIALOG2);
			break;
		case R.id.iv_jhzgtime:
			showDialog(DATE_DIALOG);
			break;
		case R.id.delete2:
			DialogUtil.showDefaultAlertDialog(
					HistoryTroubleModifyActivity.this, "提示", "确定删除吗？",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							pic2.setVisibility(View.GONE);
							delete2.setVisibility(View.GONE);
							iv2.setVisibility(View.GONE);
							deletephoto = "1";
						}
					});

			break;
		case R.id.btn_zg_type:
			ArrayList<String> mDatas = new ArrayList<String>();
			Collections.addAll(mDatas, Const.TYPE_YH_ARRAY);
			SigleWheelDialog mChangeAddressDialog = new SigleWheelDialog(
					HistoryTroubleModifyActivity.this, mDatas);
			mChangeAddressDialog.setText(mTvHiddenType.getText().toString());
			mChangeAddressDialog.show();
			mChangeAddressDialog.setAddresskListener(new SigleWheelDialog.OnTextCListener() {

				@Override
				public void onClick(String mText) {
					mTvHiddenType.setText(mText);
				}
			});
			break;

		case R.id.iv_now1:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			tempFile = new File(AppConfig.buildPath(AppConfig.HOME_CACHE), UUID
					.randomUUID().toString().replace("-", "")
					+ ".jpg");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivityForResult(intent, mTakePictureRequestCode);

			}
			break;
		case R.id.btn_submit:
			String contact = linkMan.getText().toString();
			String telephone = linkTel.getText().toString();
			String phoneNum = linkMo.getText().toString();
			String describe = des.getText().toString();
			String jhzgTime = rectificationPlanTime.getText().toString();
			String zgtime = completedDate.getText().toString();
			String bz2 = bz.getText().toString();
			String type2 = TroubleTypeEnum.getValue(mTvHiddenType.getText()
					.toString());// 隐患类别
			if (tempFile != null && !tempFile.exists()) {
				tempFile = null;
			}
			if (isGov) {

			} else {

				isRepaired = true;
				if (TextUtils.isEmpty(contact)) {
					ToastUtils.showMessage(this, "联系人不能为空");
					return;
				}
				if (TextUtils.isEmpty(telephone)) {
					ToastUtils.showMessage(this, "联系电话不能为空");
					return;
				}
				if (TextUtils.isEmpty(phoneNum)) {
					ToastUtils.showMessage(this, "手机不能为空");
					return;
				}
				if (phoneNum.length() != 11) {
					ToastUtils.showMessage(this, "手机号位数异常，请重新填写");
					return;
				}
				if (TextUtils.isEmpty(describe)) {
					ToastUtils.showMessage(this, "隐患描述不能为空");
					return;
				}
				if (TextUtils.isEmpty(jhzgTime)) {
					ToastUtils.showMessage(this, "计划整改时间不能为空");
					return;
				}
				if (TextUtils.isEmpty(zgtime)) {
					ToastUtils.showMessage(this, "整改时间不能为空");
					return;
				}
				if (TextUtils.isEmpty(bz2)) {
					ToastUtils.showMessage(this, "备注不能为空");
					return;
				}
			}
			mLoading.show();
			if (tempFile != null && !tempFile.exists()) {
				tempFile = null;
			}
			HttpRequestHelper.getInstance().submitGenealCheck(this,
					cb_check.isChecked(), isRepaired, deletephoto, hiddenId,
					contact, telephone, phoneNum, type2, describe, jhzgTime,
					tempFile, zgtime, bz2, 0, this);

			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 拍照，照片删除
		if (requestCode == mTakePictureRequestCode && resultCode == RESULT_OK
				&& tempFile != null && tempFile.length() > 0) {
			takephoto();
		}
	}

	private void takephoto() {
		try {
			// 只有一张照片
			mPicsLayout.removeAllViews();
			pics.clear();

			smallBit = ImageUtil.getThumbnails(tempFile.getAbsolutePath());
			final LayoutInflater inflater = LayoutInflater.from(this);
			picture = (RelativeLayout) inflater.inflate(R.layout.photo, null);
			photo = (ImageView) picture.findViewById(R.id.photo);
			delete = (ImageButton) picture.findViewById(R.id.delete);
			delete.setTag(tempFile);
			picture.setTag(tempFile.getAbsolutePath());
			// 缩略图使用压缩后的
			photo.setImageBitmap(smallBit);
			mPicsLayout.addView(picture);
			mPicScrollView.setVisibility(View.VISIBLE);
			// 删除监听
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					DialogUtil.showDefaultAlertDialog(
							HistoryTroubleModifyActivity.this, "提示", "确定删除吗？",
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
					DialogUtil
							.showDefaultProgressDialog(HistoryTroubleModifyActivity.this);
					// 弹出传递照片地址
					showPicture(v.getTag().toString());
				}
			});
		} catch (Exception e) {

		}
	}

	// 弹出查看照片
	protected void showPicture(String picPath) {
		Intent intent = new Intent(this, ViewPhotoActivity.class);
		intent.putExtra("picPath", picPath);
		startActivity(intent);
	}

	@Override
	public void onError(String errorMsg) {
		mLoading.dismiss();
		DialogUtil.showMsgDialog(this, errorMsg, true, null);

	}

	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		mLoading.dismiss();
		switch (requestCode) {
		case Const.NET_GET_COMPANY_GENERAL_ITEM_INFO_CODE:

			CpyGelCheckInfoVo mGeneralcheckInfo = JSON.parseObject(
					mJsonResult.getEntity(), CpyGelCheckInfoVo.class);

			String id = mGeneralcheckInfo.getId();
			String type = mGeneralcheckInfo.getType();
			String description = mGeneralcheckInfo.getDescription();
			String linkMan1 = mGeneralcheckInfo.getLinkMan();
			String completedDate2 = mGeneralcheckInfo.getCompletedDate();
			String phone = mGeneralcheckInfo.getLinkMobile();
			String linkTelString = mGeneralcheckInfo.getLinkTel();
			isRepaired = mGeneralcheckInfo.getRepaired();
			isGov = mGeneralcheckInfo.isGov();
			boolean isCheck = mGeneralcheckInfo.isCheck();
			String rectificationPlanTime2 = mGeneralcheckInfo
					.getRectificationPlanTime();
			String checkOpition = mGeneralcheckInfo.getSafetyMatterName();
			if (!checkOpition.isEmpty()) {
				ll_checkcontent.setVisibility(View.VISIBLE);
				tv_checkcontent.setText(checkOpition);

			}
			cb_check.setChecked(isCheck);
			linkMan.setText(linkMan1);
			linkTel.setText(linkTelString);
			linkMo.setText(phone);

			rectificationPlanTime.setText(rectificationPlanTime2);
			completedDate.setText(completedDate2);
			des.setText(description);
			mTvHiddenType.setText(TroubleTypeEnum.getValue(type));
			bz.setText(mGeneralcheckInfo.getDescription());
			reviewId = mGeneralcheckInfo.getHzCallBackId();

		//	if (isGov) {
				linkMan.setEnabled(false);
				linkTel.setEnabled(false);
				linkMo.setEnabled(false);
				des.setEnabled(false);
				iv0.setVisibility(View.GONE);
				ll_time.setVisibility(View.GONE);
				ll_phone.setVisibility(View.GONE);
				rectificationPlanTime.setTextColor(Color.parseColor("#BDBDBD"));

				bz.setEnabled(false);
				mRllHiddenType.setEnabled(false);
				iv_jhzgtime.setVisibility(View.GONE);
				iv_wctime.setVisibility(View.GONE);
				govPic.setVisibility(View.VISIBLE);

				// 显示整改详情
				EditText etRemark = (EditText) findViewById(R.id.et_zg_remark);
				etRemark.setEnabled(false);
				if (mGeneralcheckInfo.getRemarks() == null) {
					etRemark.setText("无整改描述");
				} else {
					etRemark.setText(mGeneralcheckInfo.getRemarks());
				}
				
				if (!mJsonResult.getJson().toString().equals("[]")) {
					List<CpyGelImageInfoVo> cpyGelImageInfoVo = JSONArray
							.parseArray((String) mJsonResult.getJson(),
									CpyGelImageInfoVo.class);
					// 显示整改图片
					showImage(
							cpyGelImageInfoVo.get(0).getImagesInfozg(),
							(HorizontalScrollView) findViewById(R.id.hs_zg_image),
							(LinearLayout) findViewById(R.id.ll_zg_image),
							(TextView) findViewById(R.id.tv_zg_des));
					// 显示隐患图片
					showImage(cpyGelImageInfoVo.get(0).getImagesInfo(), mPicScrollView2,
							mPicsLayout2,
							(TextView) findViewById(R.id.tv_hidden_image));
				}

			break;
		case 0:
			DialogUtil.showMsgDialog(this, "提交整改成功。", true, null);
			break;
		default:
			break;
		}

	}

	private String id;
	private View ll_time;
	private View ll_phone;
	private View ll_checkcontent;
	private TextView tv_checkcontent;
	private View first;

	/**
	 * 创建日期及时间选择对话框
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DATE_DIALOG:
			calendar = Calendar.getInstance();
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							calendar.set(year, month, dayOfMonth);
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");
							String formattedDate = sdf.format(calendar
									.getTime());
							rectificationPlanTime.setText(formattedDate);
						}
					}, calendar.get(Calendar.YEAR), // 传入年份
					calendar.get(Calendar.MONTH), // 传入月份
					calendar.get(Calendar.DAY_OF_MONTH) // 传入天数
			);
			break;
		case DATE_DIALOG2:
			calendar = Calendar.getInstance();
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							calendar.set(year, month, dayOfMonth);
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");
							String formattedDate = sdf.format(calendar
									.getTime());
							completedDate.setText(formattedDate);
						}
					}, calendar.get(Calendar.YEAR), // 传入年份
					calendar.get(Calendar.MONTH), // 传入月份
					calendar.get(Calendar.DAY_OF_MONTH) // 传入天数
			);
			break;
		}
		return dialog;

	}

	/**
	 * 显示获取到的图片
	 */
	private void showImage(final List<ImageInfo> mImages,
			HorizontalScrollView scrollView, LinearLayout llyView,
			TextView tvDes) {
		LayoutInflater inflater = null;
		llyView.removeAllViews();
		if (!mImages.toString().equals("[]")) {
			scrollView.setVisibility(View.VISIBLE);

			final List<String> paths = new ArrayList<String>();

			for (int i = 0; i < mImages.size(); i++) {
				if (inflater == null) {
					inflater = LayoutInflater.from(this);
				}
				final RelativeLayout picture = (RelativeLayout) inflater
						.inflate(R.layout.photo, null);
				ImageView photo = (ImageView) picture.findViewById(R.id.photo);
				ImageButton delete = (ImageButton) picture
						.findViewById(R.id.delete);
				final int a = i;
				paths.add(AppConfig.HOST_ADDRESS_YH + mImages.get(i).getPath());
				delete.setTag(tempFile);
				x.image().bind(photo, paths.get(i), getImageOptions());
				llyView.addView(picture);
				delete.setVisibility(View.GONE);
				picture.setTag(paths);
				picture.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					/*	DialogUtil
								.showDefaultProgressDialog(HistoryTroubleModifyActivity.this);*/
						// 弹出传递照片地址
						showPictures(paths);
					}
				});

			}
		} else {
			tvDes.setText("暂无图片");
		}
	}
}