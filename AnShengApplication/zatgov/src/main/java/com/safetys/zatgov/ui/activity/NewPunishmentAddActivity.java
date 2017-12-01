package com.safetys.zatgov.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.DateParseUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.HiddenListAdapter;
import com.safetys.zatgov.adapter.ReviewHistoryListAdapter;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.CheckItemInfo;
import com.safetys.zatgov.bean.CompanyInfo;
import com.safetys.zatgov.bean.HiddenDesInfoVo;
import com.safetys.zatgov.bean.ImageInfo;
import com.safetys.zatgov.bean.ReviewHistoryInfo;
import com.safetys.zatgov.bean.RummagerInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.PunishmentTypeEnum;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.entity.MessageEvent;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.wheel.SigleWheelDialog;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;
import com.safetys.zatgov.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:新增处罚
 */
public class NewPunishmentAddActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback {

    private final static int DATE_DIALOG = 1;
    private Calendar c = null;
    private int mCurrentPage = 0;// 当前显示页数量
    private int totalCount = 0;// 总数
    private int mCurrentPage2 = 0;// 当前显示页数量
    private int totalCount2 = 0;// 总数
    private Button mBtn_submit;// 提交
    private TextView ed_name;
    private TextView ed_address;
    private EditText ed_unit;
    private EditText ed_reason;
    private EditText ed_content;
    private EditText ed_bz;
    private TextView tv_type;
    private ImageView iv_type;
    private TextView tv_cftime;
    private ImageView iv_cf;
    private LinearLayout ll_name;
    private LinearLayout ll_address;
    private int id;
    private View line;
    private String title;
    private String pushId;
    private View type;
    private TextView ed_lawperson;
    private String companyId;
    private View history;
    private View checkfirst;
    private View ll_check;
    private boolean isCheck = false;
    private boolean isHis = false;
    private boolean isYh = false;
    private ImageView arrow;
    private ImageView arrow2;
    private ImageView arrow3;
    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_lxr;
    private TextView tv_jctime;
    private TextView ed_checkplace;
    private TextView ed_phone;
    private TextView ed_people;
    private TextView ed_checkpeople;
    private TextView ed_law;// 检查单位
    private TextView ed_now;// 现场检查记录
    private TextView tv_zgtime;
    private LoadingDialogUtil mLoading;
    private View yh;
    private String checkId = "";// 检查表id 新增时使用

    private ArrayList<HiddenDesInfoVo> hiddenDatas;// 隐患描述
    private ArrayList<ReviewHistoryInfo> reviewHistoryDates;// 复查历史记录
    private HiddenListAdapter hiddenAdapter;// 隐患描述列表数据适配
    private ReviewHistoryListAdapter rvHisListAdapter;// 历史复查记录数据适配
    private PullToRefresh pr_review;
    private PullToRefresh pr_hidden;
    LayoutInflater inflater = null;
    private LinearLayout mPicsLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chufa);
        initView();
        initData();
    }

    private void initData() {
        mLoading.show();
        Intent mIntent = getIntent();
        int id = mIntent.getIntExtra("id", -1);
        companyId = mIntent.getStringExtra("companyId");
        checkId = mIntent.getStringExtra("checkId");
        mLoading.show();
        HttpRequestHelper.getInstance()
                .getCompanyInfo(this, companyId, 2, this);
    }

    private void initView() {
        setHeadTitle("行政处罚");
        findViewById(R.id.btn_back).setOnClickListener(this);

        mBtn_submit = (Button) findViewById(R.id.btn_submit);
        mBtn_submit.setOnClickListener(this);
        ed_name = (TextView) findViewById(R.id.tv_name);
        ed_address = (TextView) findViewById(R.id.ed_address);
        ed_lawperson = (TextView) findViewById(R.id.ed_lawperson);
        ed_unit = (EditText) findViewById(R.id.ed_unit);
        ed_reason = (EditText) findViewById(R.id.ed_reason);
        ed_content = (EditText) findViewById(R.id.ed_content);
        ed_bz = (EditText) findViewById(R.id.ed_bz);
        type = findViewById(R.id.btn_yh_type);
        type.setOnClickListener(this);
        tv_type = (TextView) findViewById(R.id.tv_type);
        iv_type = (ImageView) findViewById(R.id.iv_type);
        iv_type.setOnClickListener(this);
        tv_cftime = (TextView) findViewById(R.id.tv_cftime);
        iv_cf = (ImageView) findViewById(R.id.iv_cf);
        iv_cf.setOnClickListener(this);
        ll_name = (LinearLayout) findViewById(R.id.ll_name);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        line = findViewById(R.id.v1);
        tv_cftime.setText(DateParseUtils.stampToDate(System.currentTimeMillis()));
        mLoading = new LoadingDialogUtil(this);
        pr_review = (PullToRefresh) findViewById(R.id.list_fc);
        pr_hidden = (PullToRefresh) findViewById(R.id.list_yh);
        checkfirst = findViewById(R.id.checkfirst);// 初查记录
        // 初查记录里不能修改
        tv_name = (TextView) findViewById(R.id.tv_name2);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_lxr = (TextView) findViewById(R.id.tv_lxr);
        tv_jctime = (TextView) findViewById(R.id.tv_jctime);
        ed_checkplace = (TextView) findViewById(R.id.ed_checkplace);
        ed_phone = (TextView) findViewById(R.id.ed_phone);
        ed_people = (TextView) findViewById(R.id.ed_people);
        ed_checkpeople = (TextView) findViewById(R.id.ed_checkpeople);
        ed_law = (TextView) findViewById(R.id.ed_law);
        ed_now = (TextView) findViewById(R.id.ed_now);

        history = findViewById(R.id.history);// 历史记录
        yh = findViewById(R.id.yh);
        checkfirst.setOnClickListener(this);
        history.setOnClickListener(this);
        yh.setOnClickListener(this);
        ll_check = findViewById(R.id.ll_check);
        arrow = (ImageView) findViewById(R.id.arrow);
        arrow2 = (ImageView) findViewById(R.id.arrow2);
        arrow3 = (ImageView) findViewById(R.id.arrow3);


        hiddenDatas = new ArrayList<HiddenDesInfoVo>();
        reviewHistoryDates = new ArrayList<ReviewHistoryInfo>();// 复查历史记录
        hiddenAdapter = new HiddenListAdapter(getApplicationContext(),
                hiddenDatas);
        rvHisListAdapter = new ReviewHistoryListAdapter(
                getApplicationContext(), reviewHistoryDates);
        pr_review.setAdapter(rvHisListAdapter);
        pr_hidden.setAdapter(hiddenAdapter);
        mPicsLayout2 = (LinearLayout) findViewById(R.id.photos2);
        pr_review.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(NewPunishmentAddActivity.this,
                            "没有更多的数据了。");
                    pr_review.finishLoading(false);
                } else {
                    loadingReviewHsitoryDatas();
                }

            }
        });
        pr_hidden.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage2 > totalCount2) {
                    ToastUtils.showMessage(NewPunishmentAddActivity.this,
                            "没有更多的数据了。");
                    pr_hidden.finishLoading(false);
                } else {
                    loadingReviewHsitoryDatas();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
            case R.id.iv_cf:
                showDialog(DATE_DIALOG);
                break;
            case R.id.btn_yh_type:
                ArrayList<String> mDatas = new ArrayList<String>();
                Collections.addAll(mDatas, PunishmentTypeEnum.typedata);
                SigleWheelDialog mChangeAddressDialog = new SigleWheelDialog(
                        NewPunishmentAddActivity.this, mDatas);
                mChangeAddressDialog.setText(tv_type.getText().toString());
                mChangeAddressDialog.show();
                mChangeAddressDialog.setAddresskListener(new SigleWheelDialog.OnTextCListener() {

                    @Override
                    public void onClick(String mText) {
                        tv_type.setText(mText);
                    }
                });

                break;

            case R.id.btn_back:
                finish();
                break;

            case R.id.yh:
                if (!isYh) {
                    isYh = true;
                    pr_hidden.setVisibility(View.VISIBLE);
                    arrow3.setImageResource(R.mipmap.arrow_down);
                    loadingHiddenDesListDatas();
                } else {
                    isYh = false;
                    pr_hidden.setVisibility(View.GONE);
                    arrow3.setImageResource(R.mipmap.arrow_up);
                    hiddenDatas.clear();
                    hiddenAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.checkfirst:
                if (!isCheck) {
                    isCheck = true;
                    ll_check.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.mipmap.arrow_down);
                    loadingPreIvsRecordData();
                } else {
                    isCheck = false;
                    ll_check.setVisibility(View.GONE);
                    arrow.setImageResource(R.mipmap.arrow_up);
                }

                break;
            case R.id.history:
                if (!isHis) {
                    isHis = true;
                    pr_review.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.mipmap.arrow_down);
                    loadingReviewHsitoryDatas();
                } else {
                    isHis = false;
                    pr_review.setVisibility(View.GONE);
                    arrow2.setImageResource(R.mipmap.arrow_up);
                    reviewHistoryDates.clear();
                    rvHisListAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    /*
     * 提交
     */
    private void submit() {
        String name = ed_name.getText().toString();
        String address = ed_address.getText().toString();
        String unit = ed_unit.getText().toString();
        String reason = ed_reason.getText().toString();
        String content = ed_content.getText().toString();
        String bz = ed_bz.getText().toString();
        String type = tv_type.getText().toString();
        String time = tv_cftime.getText().toString();
        String type1 = PunishmentTypeEnum.getValue(type);

        if (TextUtils.isEmpty(bz)) {
            ToastUtils.showMessage(this, "备注不能为空");
            return;
        }
        if (TextUtils.isEmpty(unit)) {
            ToastUtils.showMessage(this, "单位不能为空");
            return;
        }
        if (TextUtils.isEmpty(reason)) {
            ToastUtils.showMessage(this, "处罚原因不能为空");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showMessage(this, "处罚内容不能为空");
            return;
        }
        if (TextUtils.isEmpty(time)) {
            ToastUtils.showMessage(this, "处罚时间不能为空");
            return;
        }

        mLoading.show();
        HttpRequestHelper.getInstance().submitPunishmentInfo(this, checkId,
                time, unit, reason, content, bz, type1, Const.NET_ADD_PU_ITEM,
                this);

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
            case 2:
                // 获取企业信息返回
                if (mJsonResult.getEntity() != null
                        && !mJsonResult.getEntity().equals("{}")) {
                    CompanyInfo mCompanyInfo = JSON.parseObject(
                            mJsonResult.getEntity(), CompanyInfo.class);
                    ed_name.setText(StringUtil.nvl(mCompanyInfo.getCompanyName()));
                    ed_address.setText(StringUtil.nvl(mCompanyInfo.getAddress()));
                    ed_lawperson.setText(StringUtil.nvl(mCompanyInfo
                            .getFdDelegate()));
                }
                break;
            case Const.NET_ADD_PU_ITEM:
                DialogUtil.showMsgDialog(NewPunishmentAddActivity.this, "新增成功。",
                        true, null);
                EventBus.getDefault().post(new MessageEvent(NewZfPunListActivity.ACTION_UPDATE_DATA));
                EventBus.getDefault().post(new MessageEvent(ZfCheckRecordListActivity.ACTION_UPDATE));
                break;

            case Const.NET_GET_GOV_HIDDEN_LIST_CODE:// 获取隐患描述列表
                if (mJsonResult.getJson() == null) {
                    if (hiddenDatas.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(NewPunishmentAddActivity.this,
                                "没有更多的数据。", false, null);
                    }
                } else {
                    mCurrentPage2 = mCurrentPage + 10;
                    totalCount2 = mJsonResult.getTotalCount();
                    List<HiddenDesInfoVo> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), HiddenDesInfoVo.class);
                    if (list == null || list.size() == 0) {

                    } else {
                        hiddenDatas.addAll(list);
                    }
                }
                hiddenAdapter.notifyDataSetChanged();
                pr_hidden.finishLoading(false);
                break;
            case Const.NET_GET_GOV_REVIEW_HISTORY_LIST_CODE:// 历史复查记录
                if (mJsonResult.getJson() == null) {
                    if (reviewHistoryDates.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(NewPunishmentAddActivity.this,
                                "没有更多的数据。", false, null);
                    }
                } else {
                    mCurrentPage = mCurrentPage + 10;
                    totalCount = mJsonResult.getTotalCount();
                    List<ReviewHistoryInfo> list = JSONArray
                            .parseArray((String) mJsonResult.getJson(),
                                    ReviewHistoryInfo.class);
                    if (list == null || list.size() == 0) {

                    } else {

                        reviewHistoryDates.addAll(list);
                    }
                }
                rvHisListAdapter.notifyDataSetChanged();
                pr_review.finishLoading(false);
                break;
            case Const.NET_GET_GOV_PRE_IVS_RECORD_CODE:// 获取初查记录
                ArrayList<RummagerInfo> rmgInfos = new ArrayList<RummagerInfo>();// 隐患描述
                if (mJsonResult.getJson() != null) {
                    List<RummagerInfo> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), RummagerInfo.class);
                    if (list == null || list.size() == 0) {

                    } else {
                        rmgInfos.addAll(list);
                    }
                }
                String gridName = "";
                for (int i = 0; i < rmgInfos.size(); i++) {
                    if (rmgInfos.get(i).getChecked()) {
                        gridName = gridName + " " + rmgInfos.get(i).getGridName();
                    }

                }

                if (mJsonResult.getEntity() != null
                        && !mJsonResult.getEntity().equals("{}")) {
                    CheckItemInfo checkItemInfo = JSON.parseObject(
                            mJsonResult.getEntity(), CheckItemInfo.class);

                    tv_name.setText(checkItemInfo.getCompanyName());
                    tv_address.setText(checkItemInfo.getAddress());
                    tv_lxr.setText(checkItemInfo.getFdDelegate());// 联系人
                    tv_jctime.setText(checkItemInfo.getCheckTimeBegin());
                    ed_checkplace.setText(checkItemInfo.getCheckGround());
                    ed_phone.setText(checkItemInfo.getFdDelegateLink());

                    ed_people.setText(gridName);

                    ed_checkpeople.setText(checkItemInfo.getNoter());
                    ed_law.setText(checkItemInfo.getExecuteUnit());
                    ed_now.setText(checkItemInfo.getContent());

                    mPicsLayout2.removeAllViews();
                    List<ImageInfo> mImages = checkItemInfo.getImagesInfo();
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
                            delete.setVisibility(View.GONE);
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
                            mPicsLayout2.addView(picture);
                        }
                    }

                }

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
                                tv_cftime.setText(formattedDate);
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
     * 获取历史复查记录
     */
    private void loadingReviewHsitoryDatas() {
        HttpRequestHelper.getInstance().getReviewHistoryList(this, checkId,
                Const.NET_GET_GOV_REVIEW_HISTORY_LIST_CODE, this);
    }

    /**
     * 获取隐患描述列表
     */
    private void loadingHiddenDesListDatas() {
        HttpRequestHelper.getInstance().getHiddenDesList(this, checkId,false,
                Const.NET_GET_GOV_HIDDEN_LIST_CODE, this);
    }

    /**
     * 获取初查记录
     */
    private void loadingPreIvsRecordData() {
        HttpRequestHelper.getInstance().getPreIvsRecord(this, checkId,
                Const.NET_GET_GOV_PRE_IVS_RECORD_CODE, this);
    }

/*	private ImageOptions mImageOptions;

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
	}*/
}

