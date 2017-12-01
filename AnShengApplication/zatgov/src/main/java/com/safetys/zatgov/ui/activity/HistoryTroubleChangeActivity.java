package com.safetys.zatgov.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.CheckList;
import com.safetys.zatgov.bean.GovGelCheckInfoVo;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.bean.ImageInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.TroubleTypeEnum;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.entity.MessageEvent;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.wheel.SigleWheelDialog;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.FileUtil;
import com.safetys.zatgov.utils.ImageUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class HistoryTroubleChangeActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback {
    private Calendar c = null;
    private View mBtn_back;
    private final static int DATE_DIALOG = 1;
    private final static int DATE_DIALOG2 = 2;
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
    protected static int mTakePictureRequestCode = 1;
    private HorizontalScrollView mPicScrollView;
    private LinearLayout mPicsLayout;
    private String hiddenId;

    private List<File> pics = new ArrayList<File>(0);// 所有照片
    private Bitmap smallBit;
    private boolean isVis = false;
    private PullToRefresh list_cb;
    private View btn_cb;
    private CheckList checkList;
    private Spinner spinner;
    private LinkedHashMap<Long, String> hashMap;
    private LinkedHashMap<String, String> strHashMap;
    private LinkedHashMap<String, Long> hashMap2;
    private ArrayList<Long> listCheck;
    private ArrayList<Long> listAll;
    private String[] areas;
    private Long checkinfoid;
    private List<Long> list_id;
    private List<String> list_content;
    private List<String> list_detele;
    private String noteid;
    LayoutInflater inflater = null;
    private long reviewId;
    private RelativeLayout rllRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_trouble_change);
        initData();
        initView();
        loadingData();
    }

    private void initData() {
        Intent intent = this.getIntent();
        hiddenId = intent.getExtras().getString("id");
        noteid = intent.getExtras().getString("noteid");
        companyId = intent.getExtras().getString("companyid");
    }


    private void initView() {
        setHeadTitle("一般隐患修改");
        mBtn_back = findViewById(R.id.btn_back);
        list_detele=new ArrayList<String>();
        rllRecord = (RelativeLayout) findViewById(R.id.rll_record);
        rllRecord.setOnClickListener(this);
        mLoading = new LoadingDialogUtil(this, "请稍等");
        ed_yhms = (EditText) findViewById(R.id.ed_yhms);
        text_jhzgsj = (TextView) findViewById(R.id.tv_jhtime);
        ed_lrsj = (EditText) findViewById(R.id.ed_lrsj);
        iv_jttime = (ImageView) findViewById(R.id.iv_jhtime);
        iv_now = (ImageView) findViewById(R.id.iv_now2);
        btn_yh_type = findViewById(R.id.btn_yh_type);
        text_yh_type = (TextView) findViewById(R.id.text_yh_type);
        mBtn_submit = (Button) findViewById(R.id.btn_submit);
        View c1 = findViewById(R.id.c1);// 是狗整改
        c1.setVisibility(View.GONE);
        cb_zg = (CheckBox) findViewById(R.id.cb_zg);
        ed_lrsj.setText(DateParseUtils.stampToDate(System.currentTimeMillis()));
        mPicScrollView = (HorizontalScrollView) findViewById(R.id.scrollview2);
        mPicsLayout = (LinearLayout) findViewById(R.id.photos2);
        spinner = (Spinner) findViewById(R.id.spinner);
        boolean view = true;
        if (view ){ //假如只是查看，则无需initListner
            initOnlyView();
        }else{
            initListner();
        }

    }

    private void initOnlyView(){
        setHeadTitle("一般隐患查看");
        mBtn_back.setOnClickListener(this);
        //控件置灰
        text_yh_type.setTextColor(getResources().getColor(R.color.gray));
        findViewById(R.id.iv_yhlb).setVisibility(View.INVISIBLE);
        ed_yhms.setEnabled(false);
        iv_now.setVisibility(View.GONE);
        iv_jttime.setVisibility(View.GONE);
        text_jhzgsj.setTextColor(getResources().getColor(R.color.gray));
        ed_lrsj.setEnabled(false);
        mBtn_submit.setVisibility(View.GONE);

    }

    private void initListner(){
        mBtn_back.setOnClickListener(this);
        iv_jttime.setOnClickListener(this);
        iv_now.setOnClickListener(this);
        btn_yh_type.setOnClickListener(this);
        mBtn_submit.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

    private void loadingData(){
        mLoading.show();
        HttpRequestHelper.getInstance().getGenealItemInfo(this, hiddenId,
                Const.NET_GET_COMPANY_GENERAL_ITEM_INFO_CODE, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rll_record:
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
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_jhtime:
                showDialog(DATE_DIALOG);
                break;
            case R.id.btn_yh_type:

                ArrayList<String> mDatas = new ArrayList<String>();
                Collections.addAll(mDatas, TroubleTypeEnum.typedata);
                SigleWheelDialog mChangeAddressDialog = new SigleWheelDialog(
                        HistoryTroubleChangeActivity.this, mDatas);
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
                    if (intent.resolveActivity(this.getPackageManager()) != null) {
                        startActivityForResult(intent, mTakePictureRequestCode);
                    }
                } else {
                    DialogUtil.showMsgDialog(this, "最大上传图片数量为"+Const.MAX_PICTURE_SIZE+"张，无法再继续拍照，谢谢。",
                            false, null);
                }
                break;

            case R.id.btn_submit:
                submit();
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
    private void submit() {

        String yhlb = TroubleTypeEnum.getValue(text_yh_type.getText()
                .toString());// 隐患类别
        String yhms = ed_yhms.getText().toString();// 隐患描述
        String jhzgsj = text_jhzgsj.getText().toString();// 计划整改时间

        if (TextUtils.isEmpty(yhms)) {
            ToastUtils.showMessage(this, "隐患描述不能为空");
            return;
        }
        if (TextUtils.isEmpty(jhzgsj)) {
            ToastUtils.showMessage(this, "计划整改不能为空");
            return;
        }
        mLoading.show();

        HttpRequestHelper.getInstance().submitGenealTrouble(this,hiddenId, hiddenId,companyId,phone,
                linkMan1, noteid,  yhlb, yhms,
                jhzgsj, pics, ed_lrsj.getText().toString(), checkinfoid + "",list_detele,cb_zg.isChecked(),
                Const.SUB_NOMARL_CHECK, this);
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
            if (pics.size() < 10) {
                // 只有一张照片
                tempFile= FileUtil.getCompressFile(tempFile);
                pics.add(FileUtil.getCompressFile(tempFile));
                smallBit = ImageUtil.getThumbnails(tempFile.getAbsolutePath());
                final LayoutInflater inflater = LayoutInflater.from(this);
                final RelativeLayout picture = (RelativeLayout) inflater
                        .inflate(R.layout.photo, null);
                ImageView photo = (ImageView) picture.findViewById(R.id.photo);
                ImageButton delete = (ImageButton) picture
                        .findViewById(R.id.delete);

                delete.setTag(tempFile);
                picture.setTag(tempFile.getAbsolutePath());
                // 缩略图使用压缩后的
                photo.setImageBitmap(smallBit);
                mPicsLayout.addView(picture);
                mPicScrollView.setVisibility(View.VISIBLE);
                // 删除监听
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        DialogUtil.showDefaultAlertDialog(
                                HistoryTroubleChangeActivity.this, "提示",
                                "确定删除吗？",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        mPicsLayout.removeView(picture);
                                        pics.remove(v.getTag());
                                        //
                                        pics.remove(tempFile);
                                        tempFile = null;
                                    }
                                });
                    }
                });


                // 弹出查看
                picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil
                                .showDefaultProgressDialog(HistoryTroubleChangeActivity.this);
                        // 弹出传递照片地址
                        showPicture(v.getTag().toString());
                    }
                });
            }
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
                if (mJsonResult.getEntity() != null) {

                    GovGelCheckInfoVo mGeneralcheckInfo = JSON.parseObject(
                            mJsonResult.getEntity(), GovGelCheckInfoVo.class);

                    // String id = mGeneralcheckInfo.getId();
                    String type = mGeneralcheckInfo.getType();
                    String description = mGeneralcheckInfo.getContent();
                    linkMan1 = mGeneralcheckInfo.getLinkMan();
                    // String completedDate2 = mGeneralcheckInfo.getCompletedDate();
                    String phoneNum = mGeneralcheckInfo.getLinkManHandset();
                    phone = mGeneralcheckInfo.getLinkManPhone();

                    // boolean isCheck = mGeneralcheckInfo.isCheck();
                    String rectificationPlanTime2 = mGeneralcheckInfo
                            .getGovernDate();
                    text_yh_type.setText(TroubleTypeEnum.getValue(type));
                    ed_yhms.setText(description);
                    text_jhzgsj.setText(rectificationPlanTime2);
                    cb_zg.setChecked(mGeneralcheckInfo.isCleanUp());

                    LayoutInflater inflater = null;
                    mPicsLayout.removeAllViews();
                    //复查ID
                    reviewId = mGeneralcheckInfo.getHzCallBackId();
                    //显示整改图片
                    showImage(mGeneralcheckInfo.getImagesInfoZf(), (HorizontalScrollView)findViewById(R.id.hs_zg_image), (LinearLayout)findViewById(R.id.ll_zg_image),(TextView)findViewById(R.id.tv_zg_des));
                    //显示整改详情
                    EditText etRemark = (EditText)findViewById(R.id.et_zg_remark);
                    etRemark.setEnabled(false);
                    if(mGeneralcheckInfo.getRemarks() == null){
                        etRemark.setText("无整改描述");
                    }else{
                        etRemark.setText(mGeneralcheckInfo.getRemarks());
                    }

                    final List<ImageInfo> mImages = mGeneralcheckInfo.getImagesInfo();

                    showImage(mImages, mPicScrollView, mPicsLayout, (TextView)findViewById(R.id.tv_zg_des));
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
                    strHashMap=new LinkedHashMap<String, String>();
                    int total = mJsonResult.getTotalCount() + 1;
                    areas = new String[total];
                    int i = 0;
                    int a=0;
                    for (HyCheckItemInfo heCheckInfo : list) {
                        list_content.add(heCheckInfo.getContent());
                        list_id.add(heCheckInfo.getId());
                        if (checkinfoid.equals(heCheckInfo.getId())) {
                            a=i;
                        }
                        areas[i] = heCheckInfo.getContent();
                        i++;
                        hashMap2.put(heCheckInfo.getContent(), heCheckInfo.getId());
                        hashMap.put(heCheckInfo.getId(), heCheckInfo.getContent());
                        strHashMap.put(heCheckInfo.getId()+"", heCheckInfo.getContent());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            this, R.layout.item_spinner_view, areas);
                    arrayAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // 设置显示的数据
                    spinner.setAdapter(arrayAdapter);
                    spinner.setSelection(a, true);

                }

                break;


            case Const.SUB_NOMARL_CHECK:
                DialogUtil.showMsgDialog(this, "修改隐患成功。", true, null);

                EventBus.getDefault().post(new MessageEvent(NewZfYhLrActivity.ACTION_UPDATE));
                EventBus.getDefault().post(new MessageEvent(NewZfCheckItemActivity.ACTION_UPDATE));
                EventBus.getDefault().post(new MessageEvent(ZfCheckRecordListActivity.ACTION_UPDATE));
                EventBus.getDefault().post(new MessageEvent(ZfReviewCompanyHiddenListActivity.ACTION_UPDATE));
                break;

            default:
                break;
        }

    }



    //private ImageOptions mImageOptions;
    private String companyId;
    private CheckBox cb_zg;
    private String linkMan1;
    private String phone;


    /**
     * 创建日期及时间选择对话框
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DATE_DIALOG:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                c.set(year, month, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        "yyyy-MM-dd");
                                String formattedDate = sdf.format(c.getTime());
                                text_jhzgsj.setText(formattedDate);
                            }
                        }, c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );
                break;
            case DATE_DIALOG2:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                c.set(year, month, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        "yyyy-MM-dd");
                                String formattedDate = sdf.format(c.getTime());
                                // completedDate.setText(formattedDate);
                            }
                        }, c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );
                break;
        }
        return dialog;

    }

    /**
     * 显示获取到的图片
     */
    private void showImage(final List<ImageInfo> mImages,HorizontalScrollView scrollView,LinearLayout llyView,TextView tvDes) {
        LayoutInflater inflater = null;
        llyView.removeAllViews();
        if (mImages != null) {
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
                picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil
                                .showDefaultProgressDialog(HistoryTroubleChangeActivity.this);
                        // 弹出传递照片地址
                        showPictures(paths);
                    }
                });

            }
        } else {
            tvDes.setText("暂无图片:");
        }
    }
}
