package com.safetys.zatgov.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.DateParseUtils;
import com.safetys.widget.common.SPUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CheckItemInfo;
import com.safetys.zatgov.bean.GovGelCheckInfoVo;
import com.safetys.zatgov.bean.HiddenDesInfoVo;
import com.safetys.zatgov.bean.ImageInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.PrefKeys;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.FileUtil;
import com.safetys.zatgov.utils.ImageUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:对隐患进行复查
 */
public class ZfReviewCompanyHiddenActivity extends BaseActivity implements onNetCallback {

    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.photos2)
    LinearLayout photos2;
    @BindView(R.id.scrollview2)
    HorizontalScrollView scrollview2;
    @BindView(R.id.ed_zgqk)
    EditText edZgqk;
    @BindView(R.id.text_fcsj)
    TextView textFcsj;
    @BindView(R.id.ed_fcry)
    EditText edFcry;
    @BindView(R.id.iv_now)
    ImageView ivNow;
    @BindView(R.id.photos1)
    LinearLayout photos1;
    @BindView(R.id.scrollview1)
    HorizontalScrollView scrollview1;
    @BindView(R.id.ed_fcqk)
    EditText edFcqk;
    @BindView(R.id.cb_pun_yes)
    CheckBox cbPunYes;
    @BindView(R.id.cb_pun_no)
    CheckBox cbPunNo;
    @BindView(R.id.ll_ispun)
    LinearLayout llIspun;
    @BindView(R.id.cb_reform_yes)
    CheckBox cbReformYes;
    @BindView(R.id.cb_reform_no)
    CheckBox cbReformNo;
    @BindView(R.id.ll_reform_state)
    LinearLayout llReformState;
    @BindView(R.id.rll_hidden)
    RelativeLayout rllHidden;
    @BindView(R.id.rll_recod)
    RelativeLayout rllRecod;
    String zgState = "1";// 0未查看,1查看未通过。2已通过
    private long reviewId;
    private HiddenDesInfoVo desInfo;
    String hiddenId;
    String hiddenLevel;
    private TextView tvZgPictureDes;
    private List<String> listid;
    private String fcry;
    private String fcbh;
    private String yhzg;
    private File tempFile;// 拍摄缓存照片文件
    protected static int mTakePictureRequestCode = 1;
    private String oldFileCId = "";// 老照片信息
    private boolean isPunTag = false;

    private List<File> pics = new ArrayList<File>(0);// 所有照片
    private Bitmap smallBit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zf_activity_review_company_hidden);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        setHeadTitle("我要复查");
        // 复查时间
        textFcsj.setText(DateParseUtils.stampToDate(System
                .currentTimeMillis()));
        cbPunNo.setChecked(true);
        cbReformNo.setChecked(true);
        mLoading = new LoadingDialogUtil(this);
        cbReformNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbReformYes.setChecked(!isChecked);
                if (isChecked) {
                    zgState = "1";
                }
            }
        });
        cbReformYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                cbReformNo.setChecked(!isChecked);
                if (isChecked) {
                    zgState = "2";
                }

            }
        });

        cbPunNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                cbPunYes.setChecked(!isChecked);
            }
        });

        cbPunYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                cbPunNo.setChecked(!isChecked);
            }
        });
    }

    private void initData() {
        boolean isGridPerson = (boolean) SPUtils.getData(PrefKeys.PREF_USER_TYPE_KEY, false);
        if (isGridPerson) {
            llIspun.setVisibility(View.GONE);
        }
        desInfo = (HiddenDesInfoVo) getIntent().getSerializableExtra("hidden");
        hiddenId = desInfo.getHiddenId();
        hiddenLevel = desInfo.getIsBig();
        if (desInfo.getIsBig().equals("1")) {
            loadingMajorHiddenData();
        } else {
            loadingGeneralHiddenData();
        }
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
            case Const.NET_ADD_REVIEW_ITEM:
                ToastUtils.showMessage(getApplicationContext(), "复查成功！");
                sendBroadcast(new Intent(
                        ZfReviewCompanyHiddenListActivity.ACTION_UPDATE_REVIEW_HIDDEN_LIST));
                this.finish();
                break;

            case Const.NET_GET_COMPANY_GENERAL_ITEM_INFO_CODE:// 一般隐患
                GovGelCheckInfoVo mGeneralcheckInfo = JSON.parseObject(
                        mJsonResult.getEntity(), GovGelCheckInfoVo.class);

                reviewId = mGeneralcheckInfo.getHzCallBackId();
                edZgqk.setText(mGeneralcheckInfo.getRemarks());
                showZgImage(mGeneralcheckInfo.getImagesInfoZf());
                break;

            case Const.NET_GET_COMPANY_MAJOR_DETAIL_INFO_CODE:// 重大隐患
                if (mJsonResult.getJson() == null
                        || mJsonResult.getEntity().toString().equals("[]")) {

                    DialogUtil.showMsgDialog(this, "没有找到录入信息", true, null);
                } else {

                    CheckItemInfo checkItemInfo = JSON.parseObject(
                            mJsonResult.getEntity(), CheckItemInfo.class);
                    edZgqk.setText(checkItemInfo.getRemarks());
                    reviewId = checkItemInfo.getHzCallBackId();
                    showZgImage(checkItemInfo.getImagesInfoZg());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取一般隐患详情
     */
    private void loadingGeneralHiddenData() {

        mLoading.show();
        HttpRequestHelper.getInstance().getGenealItemInfo(this, hiddenId,
                Const.NET_GET_COMPANY_GENERAL_ITEM_INFO_CODE, this);
    }

    /**
     * 获取重大隐患详情
     */
    private void loadingMajorHiddenData() {
        mLoading.show();
        HttpRequestHelper.getInstance().getMajorDetailInfo(this, hiddenId,
                Const.NET_GET_COMPANY_MAJOR_DETAIL_INFO_CODE, this);
    }

    @OnClick({R.id.btn_back, R.id.btn_submit, R.id.iv_now, R.id.cb_pun_yes, R.id.cb_pun_no, R.id.ll_ispun, R.id.cb_reform_yes, R.id.cb_reform_no, R.id.ll_reform_state, R.id.rll_hidden, R.id.rll_recod})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_submit:
                fcry = edFcry.getText().toString();

                yhzg = edFcqk.getText().toString();
                if (TextUtils.isEmpty(fcry)) {
                    ToastUtils.showMessage(this, "请填写复查人员。");
                    return;
                }
                if (TextUtils.isEmpty(yhzg)) {
                    ToastUtils.showMessage(this, "请填写隐患整改情况。");
                    return;
                }

                mLoading.show();
                submit();
                break;
            case R.id.iv_now:
                if (pics.size() < Const.MAX_PICTURE_SIZE) {
                    // 调用手机系统相机
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    tempFile = new File(AppConfig.buildPath(AppConfig.HOME_CACHE),
                            UUID.randomUUID().toString().replace("-", "") + ".jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, mTakePictureRequestCode);
                    }
                } else {
                    DialogUtil
                            .showMsgDialog(this, "最大上传图片数量为"
                                            + Const.MAX_PICTURE_SIZE + "张，无法再继续拍照，谢谢。",
                                    false, null);
                }
                break;
            case R.id.cb_pun_yes:
                isPunTag = true;
                break;
            case R.id.cb_pun_no:
                isPunTag = false;
                break;
            case R.id.ll_ispun:
                break;
            case R.id.cb_reform_yes:
                break;
            case R.id.cb_reform_no:
                break;
            case R.id.ll_reform_state:
                break;
            case R.id.rll_hidden:
                Intent intent1 = null;
                if (desInfo.getIsBig().equals("1")) {
                    intent1 = new Intent(this, NoMajorChangeActivity.class);// 重大隐患
                } else {
                    intent1 = new Intent(this, HistoryTroubleChangeActivity.class);// 一般隐患
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", hiddenId);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.rll_recod:
                if (reviewId != 0){
                    Intent intent2 = new Intent(this, ReviewShowActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putLong("reviewId", reviewId);
                    intent2.putExtras(bundle2);
                    startActivity(intent2);
                }else{
                    ToastUtils.showMessage(getApplicationContext(), "该隐患暂未复查");
                }
                break;
        }
    }
    /**
     * 提交
     */
    private void submit() {
        mLoading.show();
        HttpRequestHelper.getInstance().addReviewInfo(getApplicationContext(),
                Integer.parseInt(hiddenId), true,
                textFcsj.getText().toString(), fcbh, fcry, yhzg, pics,
                hiddenLevel, zgState, Const.NET_ADD_REVIEW_ITEM, this);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {

            case Activity.RESULT_OK:
                // 拍照，照片删除
                if (requestCode == mTakePictureRequestCode
                        && resultCode == Activity.RESULT_OK && tempFile != null
                        && tempFile.length() > 0) {
                    try {
                        if (pics.size() < Const.MAX_PICTURE_SIZE) {
                            // 只有一张照片
                            oldFileCId = "";
                            tempFile = FileUtil.getCompressFile(tempFile);
                            pics.add(tempFile);
                            smallBit = ImageUtil.getThumbnails(tempFile
                                    .getAbsolutePath());
                            final LayoutInflater inflater = LayoutInflater
                                    .from(this);
                            handlePicture(inflater);
                        } else {
                            DialogUtil.showMsgDialog(this,
                                    "最大上传图片数量为9张，无法再继续拍照，谢谢。", false, null);
                        }
                    } catch (Exception e) {
                        LogUtil.i("error:" + e.getMessage());
                    }
                }
                break;
            default:
                break;
        }
    }
    /**
     * 处理图片
     *
     * @param inflater
     */
    private void handlePicture(LayoutInflater inflater) {

        final RelativeLayout picture = (RelativeLayout) inflater.inflate(
                R.layout.photo, null);
        ImageView photo = (ImageView) picture.findViewById(R.id.photo);
        ImageButton delete = (ImageButton) picture.findViewById(R.id.delete);

        delete.setTag(tempFile);

        // 缩略图使用压缩后的

        x.image().bind(photo, tempFile.getAbsolutePath(), getImageOptions());
        photos1.addView(picture);
        scrollview1.setVisibility(View.VISIBLE);
        // 删除监听
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogUtil.showDefaultAlertDialog(
                        ZfReviewCompanyHiddenActivity.this, "提示", "确定删除吗？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                photos1.removeView(picture);
                                pics.remove(v.getTag());
                                //
                                pics.remove(tempFile);
                                tempFile = null;
                            }
                        });
            }
        });

        List<String> paths = new ArrayList<String>();
        for (int i = 0; i < pics.size(); i++) {
            paths.add(pics.get(i).getAbsolutePath());
        }
        picture.setTag(paths);
        // 弹出查看
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil
                        .showDefaultProgressDialog(ZfReviewCompanyHiddenActivity.this);
                // 弹出传递照片地址
                showPictures((List<String>) v.getTag());
            }
        });
    }

    /**
     * 显示整改图片
     *
     * @param mImages
     */
    private void showZgImage(final List<ImageInfo> mImages) {
        LayoutInflater inflater = null;
        photos2.removeAllViews();
        if (mImages != null) {
            scrollview2.setVisibility(View.VISIBLE);

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
				/*
				 * String photoPath = AppConfig.HOST_ADDRESS_YH +
				 * mImages.get(i).getPath();
				 */
                delete.setTag(tempFile);

                x.image().bind(photo, paths.get(i), getImageOptions());
                photos2.addView(picture);
                delete.setVisibility(View.GONE);
                // 弹出查看

                picture.setTag(paths);
                picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil
                                .showDefaultProgressDialog(ZfReviewCompanyHiddenActivity.this);
                        // 弹出传递照片地址

                        showPictures(paths);
                    }
                });

            }
        } else {
            tvZgPictureDes.setText("整改照片:无");
        }
    }
}
