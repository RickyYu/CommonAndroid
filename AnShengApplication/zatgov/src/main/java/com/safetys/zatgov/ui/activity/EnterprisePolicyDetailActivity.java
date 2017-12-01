package com.safetys.zatgov.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.safetys.zatgov.R;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.ReadTextInfo;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class EnterprisePolicyDetailActivity extends BaseActivity implements onNetCallback {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.w_title)
    TextView wTitle;
    @BindView(R.id.w_mx)
    TextView wMx;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_policy_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Bundle bundle=this.getIntent().getExtras();
        String id2=bundle.getString("id");
        String title2=bundle.getString("title");
        setHeadTitle(title2);
        mLoading = new LoadingDialogUtil(this,true);
        mLoading.show();
        HttpRequestHelper.getInstance().getReadDetail(this,id2,
                this);
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
    @Override
    public void onError(String errorMsg) {
        mLoading.dismiss();
        DialogUtil.showMsgDialog(this, errorMsg, true, null);
    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();
        if (mJsonResult.getEntity() == null||mJsonResult.getEntity().equals("[]")) {
            DialogUtil.showMsgDialog(this,
                    "没有数据。", false, null);
        }else {
            ReadTextInfo readTextInfo = JSON.parseObject(
                    mJsonResult.getEntity(), ReadTextInfo.class);
            wTitle.setText(readTextInfo.getCaption());
            wMx.setText("发布机构：" + readTextInfo.getPublisher());
            time.setText("发布时间：" + readTextInfo.getCreatetime());
            tvDetail.setText(Html.fromHtml(readTextInfo.getDetail()));
        }

    }
}
