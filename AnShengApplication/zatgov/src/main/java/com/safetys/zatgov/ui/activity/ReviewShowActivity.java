package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.safetys.zatgov.R;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.ReviewInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class ReviewShowActivity extends BaseActivity implements onNetCallback {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.tv_companyname)
    EditText tvCompanyname;
    @BindView(R.id.lly_cpy_name)
    LinearLayout llyCpyName;
    @BindView(R.id.lly_ischeck)
    LinearLayout llyIscheck;
    @BindView(R.id.text_fcsj)
    TextView textFcsj;
    @BindView(R.id.text_fcry)
    TextView textFcry;
    @BindView(R.id.text_fcbh)
    TextView textFcbh;
    @BindView(R.id.text_xczp)
    TextView textXczp;
    @BindView(R.id.photos1)
    LinearLayout photos1;
    @BindView(R.id.scrollview1)
    HorizontalScrollView scrollview1;
    @BindView(R.id.checkfirst)
    RelativeLayout checkfirst;
    @BindView(R.id.history)
    RelativeLayout history;
    @BindView(R.id.text_zgqk)
    TextView textZgqk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_show);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setHeadTitle("复查详情");
        llyIscheck.setVisibility(View.GONE);
        llyCpyName.setVisibility(View.GONE);
        history.setVisibility(View.GONE);
        checkfirst.setVisibility(View.GONE);
        mLoading = new LoadingDialogUtil(this);
    }

    private void initData() {
        Intent mIntent = getIntent();
        long id = mIntent.getLongExtra("reviewId", 0);
        mLoading.show();
        HttpRequestHelper.getInstance().getReviewInfo(this, id, 0, this);
    }

    @Override
    public void onError(String errorMsg) {
        mLoading.dismiss();
        DialogUtil.showMsgDialog(this, errorMsg, true, null);
    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();
        dealReviewInfoLoad(mJsonResult);
    }

    /**
     * 处理加载到的复查信息
     */
    private void dealReviewInfoLoad(JsonResult mJsonResult) {
        if (mJsonResult.getEntity() != null) {
            ReviewInfo mInfo = JSON.parseObject(mJsonResult.getEntity(), ReviewInfo.class);
            if (mInfo != null) {
                tvCompanyname.setText(mInfo.getCompanyName());
                //	mCb_scfcb.setText(mInfo.getIsCallBack()?"是":"否");
                textFcsj.setText(mInfo.getCallbackTime());
                textFcry.setText(mInfo.getExecuter());
                textFcbh.setText(mInfo.getCallBackNum());
                textZgqk.setText(mInfo.getHiddenState());
            } else {
                DialogUtil.showMsgDialog(this, "数据解析错误。", false, null);
            }

            if (mJsonResult.getJson() != null) {
                JSONArray mJsonA = JSONObject.parseArray((String) mJsonResult.getJson());
                String path = null;
                if (mJsonA != null && mJsonA.size() > 0) {
                    path = ((JSONObject) mJsonA.get(0)).getString("path");
                }
                if (path != null && !TextUtils.isEmpty(path)) {
                    final LayoutInflater inflater = LayoutInflater.from(this);
                    final RelativeLayout picture = (RelativeLayout) inflater
                            .inflate(R.layout.photo, null);
                    ImageView photo = (ImageView) picture.findViewById(R.id.photo);
                    ImageButton delete = (ImageButton) picture
                            .findViewById(R.id.delete);
                    delete.setVisibility(View.GONE);
                    picture.setTag(AppConfig.HOST_ADDRESS_YH + path);

                    x.image().bind(photo, AppConfig.HOST_ADDRESS_YH + path);
                    photos1.addView(picture);
                    scrollview1.setVisibility(View.VISIBLE);
                    // 弹出查看
                    picture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtil.showDefaultProgressDialog(ReviewShowActivity.this);
                            // 弹出传递照片地址
                            showPicture(v.getTag().toString());
                        }
                    });
                } else {
                    textXczp.setText("无");
                }
            } else {
                textXczp.setText("无");
            }
        } else {
            DialogUtil.showMsgDialog(this, "数据解析错误。", false, null);
        }
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

    // 弹出查看照片
    protected void showPicture(String picPath) {
        Intent intent = new Intent(this, ViewPhotoActivity.class);
        intent.putExtra("picPath", picPath);
        startActivity(intent);
    }
}
