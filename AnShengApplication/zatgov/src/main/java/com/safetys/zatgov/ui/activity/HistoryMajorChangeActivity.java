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
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
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
import com.safetys.zatgov.utils.FileUtil;
import com.safetys.zatgov.utils.ImageUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class HistoryMajorChangeActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback {
    // 单位名称
//	private TextView ed_dwmc;
//	private TextView ed_dwdz;// 单位地址
//	private EditText ed_qyfr;// 单位地址
    private EditText ed_lrsj2;// 录入时间
    private EditText ed_yhdz;// 隐患地址
    private EditText ed_lxr2;// 联系人
    private EditText ed_phonenumber2;// 手机
    private EditText ed_phone2;// 电话
    private EditText ed_lszljf;// 落实治理经费
    private EditText ed_dwfzr;// 单位负责人
    private EditText ed_tbr;// 填报人
    private TextView tv_jhtime;// 计划完成治理时间
    private ImageView iv_jttime;
    private ImageView iv_now;
    private View mBtn_back;
    private View btn_yh_qy;// 隐患区域
    private TextView text_yh_qy;
    private String yh_qy_c_code = "";// 城市code
    private String yh_qy_z_code = "";// 镇code
    private String yh_qy_j_code = "";// 街道code

    private Bitmap smallBit;
    private File tempFile;// 拍摄缓存照片文件
    protected static int mTakePictureRequestCode = 1;
    private LoadingDialogUtil mloading;
    private Calendar c = null;
    private final static int DATE_DIALOG = 1;
    private final static int DATE_DIALOG2 = 2;
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
    private ImageView iv_lrsj;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_item);
        initView();
    }

    private void initView() {
        setHeadTitle("重大隐患修改");
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);

        mloading = new LoadingDialogUtil(this);
        mloading.show();
        Intent intent = this.getIntent();
        hiddenId = intent.getExtras().getString("id");
        noteid = intent.getExtras().getString("noteid");
        HttpRequestHelper.getInstance().getMajorDetailInfo(this, hiddenId,
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
        iv_jttime = (ImageView) findViewById(R.id.iv_jhtime);
        ed_lrsj = (TextView) findViewById(R.id.tv_lrtime);
        iv_lrsj = (ImageView) findViewById(R.id.iv_lrtime);

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

        iv_now = (ImageView) findViewById(R.id.iv_now);
        iv_now.setOnClickListener(this);
        iv_jttime.setOnClickListener(this);
        mPicScrollView = (HorizontalScrollView) findViewById(R.id.scrollview1);
        mPicsLayout = (LinearLayout) findViewById(R.id.photos1);
        cb_major = (CheckBox) findViewById(R.id.cb_major);
        btn_yh_qy = findViewById(R.id.btn_yh_qy);
        text_yh_qy = (TextView) findViewById(R.id.text_yh_qy);
        btn_yh_qy.setOnClickListener(this);

        btn_sub = (Button) findViewById(R.id.btn_submit);
        btn_sub.setOnClickListener(this);

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

            case R.id.major:
                Boolean isCheck = cb_major.isChecked();
                cb_major.setChecked(!isCheck);
                break;
            case R.id.zf:
                Boolean isCheck2 = govCoordinationBox.isChecked();
                govCoordinationBox.setChecked(!isCheck2);
                break;
            case R.id.partStopProduct:
                Boolean isCheck3 = partStopProduct.isChecked();
                partStopProduct.setChecked(!isCheck3);
                break;
            case R.id.fullStopProduct:
                Boolean isCheck4 = fullStopProduct.isChecked();
                fullStopProduct.setChecked(!isCheck4);
                break;
            case R.id.target:
                Boolean isCheck5 = target.isChecked();
                target.setChecked(!isCheck5);
                break;
            case R.id.resource:
                Boolean isCheck6 = resource.isChecked();
                resource.setChecked(!isCheck6);
                break;
            case R.id.safetyMethod:
                Boolean isCheck7 = safetyMethod.isChecked();
                safetyMethod.setChecked(!isCheck7);
                break;
            case R.id.goods:
                Boolean isCheck8 = goods.isChecked();
                goods.setChecked(!isCheck8);
                break;

            case R.id.btn_back:
                finish();
                break;

            case R.id.iv_now:
                if (pics.size() < 10) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    tempFile = new File(AppConfig.buildPath(AppConfig.HOME_CACHE),
                            UUID.randomUUID().toString().replace("-", "") + ".jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, mTakePictureRequestCode);

                    }
                } else {
                    DialogUtil.showMsgDialog(this, "最大上传图片数量为10张，无法再继续拍照，谢谢。",
                            false, null);
                }

                break;

            case R.id.iv_jhtime:
                showDialog(DATE_DIALOG);
                break;
            case R.id.iv_lrtime:
                showDialog(DATE_DIALOG2);
                break;

            case R.id.delete2:
                DialogUtil.showDefaultAlertDialog(HistoryMajorChangeActivity.this,
                        "提示", "确定删除吗？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                pic2.setVisibility(View.GONE);
                                delete2.setVisibility(View.GONE);
                                iv0.setVisibility(View.GONE);
                                deletephoto = "1";
                            }
                        });

                break;

            case R.id.btn_yh_qy:
                //// FIXME: 2017/11/15
      /*          new cn.safetys.ywngovernment.view.StreetSelectDialog(
                        this,
                        new cn.safetys.ywngovernment.view.StreetSelectDialog.OnTextCListener() {

                            @Override
                            public void onClick(String mText, String pcode,
                                                String ccode, String dcode) {

                                text_yh_qy.setText(mText);
                                yh_qy_c_code = pcode;
                                yh_qy_z_code = ccode;
                                yh_qy_j_code = dcode;
                            }
                        }).show();

                break;*/
            case R.id.btn_submit:
                submit();
                break;
            default:
                break;
        }
    }

    /**
     * 提交重大隐患
     */
    private void submit() {
        String yhAddress = ed_yhdz.getText().toString();
        String linkName = ed_lxr2.getText().toString();
        String linktelete = ed_phone2.getText().toString();// 联系电话
        String linkphone = ed_phonenumber2.getText().toString();// 手机
        String yhqk = ed_yhjbqk.getText().toString();// 隐患基本情况\
        String jhTime = tv_jhtime.getText().toString();// 计划整改时间
        String frz = ed_dwfzr.getText().toString();// 单位负责人
        String tbr = ed_tbr.getText().toString();// 填报人
        String lsjf = ed_lszljf.getText().toString();
        if (TextUtils.isEmpty(yhAddress)) {
            ToastUtils.showMessage(this, "请输入隐患地址。");
            return;
        }

        if (TextUtils.isEmpty(yh_qy_j_code)) {
            ToastUtils.showMessage(this, "请选择隐患地址。");
            return;
        }
        if (TextUtils.isEmpty(linkName)) {
            ToastUtils.showMessage(this, "请输入联系人。");
            return;
        }
        if (TextUtils.isEmpty(linktelete)) {
            ToastUtils.showMessage(this, "请输入联系电话。");
            return;
        }
        if (TextUtils.isEmpty(linkphone)) {
            ToastUtils.showMessage(this, "请输入手机号。");
            return;
        }
        if (linkphone.length() != 11) {
            ToastUtils.showMessage(this, "手机号位数异常，请重新填写");
            return;
        }
        if (TextUtils.isEmpty(yhqk)) {
            ToastUtils.showMessage(this, "请输入隐患基本情况。");
            return;
        }
        if (TextUtils.isEmpty(jhTime)) {
            ToastUtils.showMessage(this, "请选择计划完成治理时间。");
            return;
        }
        if (TextUtils.isEmpty(frz)) {
            ToastUtils.showMessage(this, "请填写单位负责人。");
            return;
        }
        if (TextUtils.isEmpty(tbr)) {
            ToastUtils.showMessage(this, "请填写填报人。");
            return;
        }
        if (TextUtils.isEmpty(lsjf)) {
            ToastUtils.showMessage(this, "请填写落实治理经费。");
            return;
        }
        mloading.show();
        HttpRequestHelper.getInstance().submitMajorChange(this, deletephoto,
                hiddenId, noteid, cb_major.isChecked(), yhAddress, yh_qy_c_code,
                yh_qy_z_code, yh_qy_j_code, linkName, linktelete, linkphone,
                yhqk, govCoordinationBox.isChecked(),
                partStopProduct.isChecked(), fullStopProduct.isChecked(),
                target.isChecked(), resource.isChecked(),
                safetyMethod.isChecked(), goods.isChecked(), jhTime, lsjf, frz,
                ed_lrsj.getText().toString(), tbr, pics,list_detele,checkinfoid+"", 0, this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拍照，照片删除
        if (requestCode == mTakePictureRequestCode && resultCode == RESULT_OK
                && tempFile != null && tempFile.length() > 0) {
            try {
                // 只有10张照片
                if (pics.size() < 10) {
                    tempFile= FileUtil.getCompressFile(tempFile);
                    pics.add(FileUtil.getCompressFile(tempFile));
                    smallBit = ImageUtil.getThumbnails(tempFile
                            .getAbsolutePath());
                    final LayoutInflater inflater = LayoutInflater.from(this);
                    final RelativeLayout picture = (RelativeLayout) inflater
                            .inflate(R.layout.photo, null);
                    ImageView photo = (ImageView) picture
                            .findViewById(R.id.photo);
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
                                    HistoryMajorChangeActivity.this, "提示",
                                    "确定删除吗？",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
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
                                    .showDefaultProgressDialog(HistoryMajorChangeActivity.this);
                            // 弹出传递照片地址
                            showPicture(v.getTag().toString());
                        }
                    });
                }
            } catch (Exception e) {

            }
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
                    // Log.e("info","1111+" +yh_qy_z_code );
                    // Log.e("info","1111+"+ yh_qy_j_code);
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

                    mPicsLayout.removeAllViews();
                    final List<ImageInfo> mImages = checkItemInfo.getImagesInfo();
                    if (mImages != null) {
                        for (int i = 0; i < mImages.size(); i++) {
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
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    DialogUtil.showDefaultAlertDialog(
                                            HistoryMajorChangeActivity.this, "提示",
                                            "确定删除吗？",
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
                            // tempFile=new File(photoPath);
                            // pics.add(tempFile);
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

            case 0:
                DialogUtil.showMsgDialog(this, "修改成功", true, null);
                sendBroadcast(new Intent(ZfReviewCompanyHiddenListActivity.ACTION_UPDATE_REVIEW_HIDDEN_LIST));
                //// FIXME: 2017/11/15
               /* sendBroadcast(new Intent(NewZfYhLrActivity.ACTION_UPDATE_LIST_YH));
                sendBroadcast(new Intent(NewZfCheckItemActivity.ACTION_UPDATE_LIST_YH_ITEM));*/
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

    private ImageOptions mImageOptions;
    private String hiddenId;

    protected ImageOptions getImageOptions() {
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
                                tv_jhtime.setText(formattedDate);
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
                                ed_lrsj.setText(formattedDate);
                            }
                        }, c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );
                break;
        }

        return dialog;

    }
}
