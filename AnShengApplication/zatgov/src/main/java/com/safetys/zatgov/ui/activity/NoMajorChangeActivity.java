package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.CheckItemInfo;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.bean.ImageInfo;
import com.safetys.zatgov.bean.MajorItemInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.SecondTypeEnum;
import com.safetys.zatgov.config.ThirdTypeEnum;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.EditTextUtils;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class NoMajorChangeActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback {
    private EditText ed_lrsj2;// 录入时间
    private EditText ed_yhdz;// 隐患地址
    private EditText ed_lxr2;// 联系人
    private EditText ed_phonenumber2;// 手机
    private EditText ed_phone2;// 电话
    private EditText ed_lszljf;// 落实治理经费
    private EditText ed_dwfzr;// 单位负责人
    private EditText ed_tbr;// 填报人
    private TextView tv_jhtime;// 计划完成治理时间
    private View mBtn_back;
    private View btn_yh_qy;// 隐患区域
    private TextView text_yh_qy;
    private String yh_qy_c_code = "";// 城市code
    private String yh_qy_z_code = "";// 镇code
    private String yh_qy_j_code = "";// 街道code

    private Bitmap smallBit;
    private File tempFile;// 拍摄缓存照片文件
    private LoadingDialogUtil mloading;
    private String deletephoto = "0";
    private List<File> pics = new ArrayList<File>(0);// 所有照片
    private HorizontalScrollView mPicScrollView;
    private LinearLayout mPicsLayout;
    private CheckBox govCoordinationBox;
    private CheckBox partStopProduct;
    private CheckBox fullStopProduct;
    private CheckBox target;
    private CheckBox resource;
    private CheckBox safetyMethod;
    private TextView tv_bh;
    private CheckBox cb_major;
    private TextView ed_lrsj;
    private Button btn_sub;
    private EditText ed_yhjbqk;
    private CheckBox goods;
    private ImageButton delete2;
    private ImageView iv0;
    private View pic2;
    private Spinner spinner;
    private String[] areas;
    private Long checkinfoid = null;
    private LinkedHashMap<Long, String> hashMap;
    private LinkedHashMap<String, String> strHashMap;
    private LinkedHashMap<String, Long> hashMap2;
    private List<Long> list_id;
    private List<String> list_content;
    private String noteid;
    LayoutInflater inflater = null;
    private List<String> list_detele;
    private RelativeLayout rllRecord;
    private long reviewId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_major_item);
        initView();
    }

    private void initView() {
        setHeadTitle("重大隐患查看");
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);

        mloading = new LoadingDialogUtil(this);
        mloading.show();
        Intent intent = this.getIntent();
        id = intent.getExtras().getString("id");
        rllRecord = (RelativeLayout) findViewById(R.id.rll_record);
        rllRecord.setOnClickListener(this);
        HttpRequestHelper.getInstance().getMajorDetailInfo(this, id,
                Const.NET_GET_COMPANY_MAJOR_DETAIL_INFO_CODE, this);

        list_detele=new ArrayList<String>();

        ed_yhdz = (EditText) findViewById(R.id.ed_yhdz);
        ed_lxr2 = (EditText) findViewById(R.id.ed_lxr2);
        ed_phone2 = (EditText) findViewById(R.id.ed_phone2);
        ed_phonenumber2 = (EditText) findViewById(R.id.ed_phonenumber2);
        ed_lszljf = (EditText) findViewById(R.id.ed_lszljf);
        EditTextUtils.editWatcher(ed_lszljf, null);

        ed_dwfzr = (EditText) findViewById(R.id.ed_dwfzr);
        ed_tbr = (EditText) findViewById(R.id.ed_tbr);
        ed_yhjbqk = (EditText) findViewById(R.id.ed_yhjbqk);
        tv_jhtime = (TextView) findViewById(R.id.tv_jhtime);
        ed_lrsj = (TextView) findViewById(R.id.tv_lrtime);

        text_yh_qy = (TextView) findViewById(R.id.text_yh_qy);
        cb_major = (CheckBox) findViewById(R.id.cb_major);
        govCoordinationBox = (CheckBox) findViewById(R.id.cb_zf);
        partStopProduct = (CheckBox) findViewById(R.id.cb_tcty);
        fullStopProduct = (CheckBox) findViewById(R.id.cb_qbtc);
        target = (CheckBox) findViewById(R.id.cb_zlmb);
        resource = (CheckBox) findViewById(R.id.cb_zljgry);
        safetyMethod = (CheckBox) findViewById(R.id.cb_yjya);
        goods = (CheckBox) findViewById(R.id.cb_jfwz);

        View c1 = findViewById(R.id.major);// 重大企业是否
        View c2 = findViewById(R.id.zf);// 是否政府协调
        View c3 = findViewById(R.id.partStopProduct);// 是否局部停产
        View c4 = findViewById(R.id.fullStopProduct);// 是否全部停产
        View c5 = findViewById(R.id.target);// 落实治理目标
        View c6 = findViewById(R.id.resource);// 落实治理机构人员
        View c7 = findViewById(R.id.safetyMethod);// 落实安全促使及应急预案
        View c8 = findViewById(R.id.goods);// 落实治理经费物资
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
        c7.setOnClickListener(this);
        c8.setOnClickListener(this);

        mPicScrollView = (HorizontalScrollView) findViewById(R.id.scrollview1);
        mPicsLayout = (LinearLayout) findViewById(R.id.photos1);

        cb_major = (CheckBox) findViewById(R.id.cb_major);
        btn_yh_qy = findViewById(R.id.btn_yh_qy);
        text_yh_qy = (TextView) findViewById(R.id.text_yh_qy);
        btn_yh_qy.setOnClickListener(this);


        spinner = (Spinner) findViewById(R.id.spinner);

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
        mloading.dismiss();
        DialogUtil.showMsgDialog(this, errorMsg, true, null);

    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mloading.dismiss();
        switch (requestCode) {
            case Const.NET_GET_COMPANY_MAJOR_DETAIL_INFO_CODE:
                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {

                    DialogUtil.showMsgDialog(this, "没有找到录入信息", true, null);
                } else {

                    MajorItemInfo majorItemInfo = JSONArray.parseObject(
                            (String) mJsonResult.getJson(), MajorItemInfo.class);
                    cb_major.setChecked(majorItemInfo.isEmphasisProject());
                    ed_yhdz.setText(majorItemInfo.getDangerAdd());
                    ed_lxr2.setText(majorItemInfo.getLinkMan());
                    ed_phone2.setText(majorItemInfo.getLinkTel());
                    ed_phonenumber2.setText(majorItemInfo.getLinkMobile());

                    ed_yhjbqk.setText(majorItemInfo.getDescription());

                    ed_lszljf.setText(majorItemInfo.getGovernMoney());
                    ed_dwfzr.setText(majorItemInfo.getChargePerson());
                    ed_tbr.setText(majorItemInfo.getFillMan());

                    tv_jhtime.setText(majorItemInfo.getFinishDate());
                    ed_lrsj.setText(majorItemInfo.getFillDate());

                    yh_qy_c_code = majorItemInfo.getFirstArea();
                    yh_qy_z_code = majorItemInfo.getSecondArea();
                    yh_qy_j_code = majorItemInfo.getThirdArea();
                    String dz = "湖州市" + SecondTypeEnum.getValue(yh_qy_z_code)
                            + ThirdTypeEnum.getValue(yh_qy_j_code);

                    text_yh_qy.setText(dz);

                    govCoordinationBox.setChecked(majorItemInfo
                            .getGovCoordination());
                    partStopProduct.setChecked(majorItemInfo.getPartStopProduct());
                    fullStopProduct.setChecked(majorItemInfo.getFullStopProduct());
                    target.setChecked(majorItemInfo.getTarget());
                    resource.setChecked(majorItemInfo.getResource());
                    goods.setChecked(majorItemInfo.getGoods());
                    safetyMethod.setChecked(majorItemInfo.getSafetyMethod());

                    checkinfoid = majorItemInfo.getCheckInfoId();


                    CheckItemInfo checkItemInfo = JSON.parseObject(
                            mJsonResult.getEntity(), CheckItemInfo.class);
                    final List<ImageInfo> mImages = checkItemInfo.getImagesInfo();
                    showImage(mImages, mPicScrollView, mPicsLayout, (TextView)findViewById(R.id.tv_hidden_image));
                    reviewId = checkItemInfo.getHzCallBackId();
                    //显示整改图片
                    showImage(checkItemInfo.getImagesInfoZg(), (HorizontalScrollView)findViewById(R.id.hs_zg_image), (LinearLayout)findViewById(R.id.ll_zg_image),(TextView)findViewById(R.id.tv_zg_des));
                    //显示整改详情
                    EditText etRemark = (EditText)findViewById(R.id.et_zg_remark);
                    etRemark.setEnabled(false);
                    if(checkItemInfo.getRemarks() == null){
                        etRemark.setText("无整改描述");
                    }else{
                        etRemark.setText(checkItemInfo.getRemarks());
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

    //private ImageOptions mImageOptions;
    private String id;
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
                                .showDefaultProgressDialog(NoMajorChangeActivity.this);
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
