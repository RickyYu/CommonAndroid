package com.safetys.zatgov.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CheckList;
import com.safetys.zatgov.bean.GovGelCheckInfoVo;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.bean.ImageInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.TroubleTypeEnum;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class NoTroubleModifyActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback {
    private View mBtn_back;
    private EditText ed_yhms;// 隐患描述
    private TextView text_jhzgsj;// 计划整改时间
    private EditText ed_lrsj;// 录入时间

    private View btn_yh_type;// 隐患类别
    private TextView text_yh_type;

    private LoadingDialogUtil mLoading;

    private File tempFile;// 拍摄缓存照片文件
    private HorizontalScrollView mPicScrollView;
    private LinearLayout mPicsLayout;
    private String id2;

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
    private CheckBox cb_zg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_trouble_modify);
        initView();
    }

    private void initView() {
        setHeadTitle("一般隐患查看");
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);
        Intent intent = this.getIntent();
        id2 = intent.getExtras().getString("id");

        list_detele=new ArrayList<String>();
        mLoading = new LoadingDialogUtil(this, "请稍等");
        mLoading.show();
        HttpRequestHelper.getInstance().getGenealItemInfo(this, id2,
                Const.NET_GET_COMPANY_GENERAL_ITEM_INFO_CODE, this);


        ed_yhms = (EditText) findViewById(R.id.ed_yhms);
        text_jhzgsj = (TextView) findViewById(R.id.tv_jhtime);
        ed_lrsj = (EditText) findViewById(R.id.ed_lrsj);


        btn_yh_type = findViewById(R.id.btn_yh_type);
        text_yh_type = (TextView) findViewById(R.id.text_yh_type);
        btn_yh_type.setOnClickListener(this);


        View c1 = findViewById(R.id.c1);// 是狗整改
        c1.setOnClickListener(this);
        c1.setVisibility(View.GONE);
        cb_zg = (CheckBox) findViewById(R.id.cb_zg);


        ed_lrsj.setText(DateParseUtils.stampToDate(System.currentTimeMillis()));

        mPicScrollView = (HorizontalScrollView) findViewById(R.id.scrollview2);
        mPicsLayout = (LinearLayout) findViewById(R.id.photos2);

        spinner = (Spinner) findViewById(R.id.spinner);



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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.c1:
                Boolean isCheck = cb_zg.isChecked();
                cb_zg.setChecked(!isCheck);

                break;

            case R.id.btn_back:
                finish();
                break;


            default:
                break;
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
                    String linkMan1 = mGeneralcheckInfo.getLinkMan();
                    // String completedDate2 = mGeneralcheckInfo.getCompletedDate();
                    String phoneNum = mGeneralcheckInfo.getLinkManHandset();
                    String phone = mGeneralcheckInfo.getLinkManPhone();
                    text_yh_type.setText(TroubleTypeEnum.getValue(type));
                    // boolean isCheck = mGeneralcheckInfo.isCheck();
                    String rectificationPlanTime2 = mGeneralcheckInfo
                            .getGovernDate();

//				checkinfoid=Long.parseLong(mGeneralcheckInfo.getCheckInfoId());

//				HttpRequestHelper.getInstance().getYhCheckItemInfo(this, noteid, 1,
//						this);


                    ed_yhms.setText(description);
                    text_jhzgsj.setText(rectificationPlanTime2);
                    cb_zg.setChecked(mGeneralcheckInfo.isCleanUp());

                    LayoutInflater inflater = null;
                    mPicsLayout.removeAllViews();
                    final List<ImageInfo> mImages = mGeneralcheckInfo.getImagesInfo();
                    if (mImages != null) {
                        for ( int i = 0; i < mImages.size(); i++) {
                            if (inflater == null) {
                                inflater = LayoutInflater.from(this);
                            }
                            final RelativeLayout picture = (RelativeLayout) inflater
                                    .inflate(R.layout.photo, null);
                            ImageView photo = (ImageView) picture
                                    .findViewById(R.id.photo);
                            ImageButton delete = (ImageButton) picture
                                    .findViewById(R.id.delete);
                            final int a=i;
                            delete.setVisibility(View.GONE);
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    DialogUtil.showDefaultAlertDialog(
                                            NoTroubleModifyActivity.this,
                                            "提示", "确定删除吗？",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    mPicsLayout.removeView(picture);
                                                    list_detele.add(mImages.get(a).getId());
                                                }
                                            });
                                }
                            });

                            // 此处兼容本地图片地址以及网络url地址
                            String photoPath = AppConfig.HOST_ADDRESS_YH
                                    + mImages.get(i).getPath();
                            picture.setTag(photoPath);
                            x.image().bind(photo, photoPath, getImageOptions());


                            picture.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                             
                                    showPicture(v.getTag().toString());
                                }
                            });
                            mPicsLayout.addView(picture);
                        }
                    }

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



            default:
                break;
        }

    }
    private String id;

}
