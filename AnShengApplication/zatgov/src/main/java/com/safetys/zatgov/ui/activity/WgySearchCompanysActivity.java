package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.safetys.zatgov.R.id.iv_right;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class WgySearchCompanysActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(iv_right)
    ImageView ivRight;
    @BindView(R.id.ed_big)
    TextView edBig;
    @BindView(R.id.btn_yh_type2)
    RelativeLayout btnYhType2;
    @BindView(R.id.tv_qy)
    TextView tvQy;
    @BindView(R.id.btn_yh_qy)
    RelativeLayout btnYhQy;
    @BindView(R.id.tv_trade_type)
    TextView tvTradeType;
    @BindView(R.id.btn_trade_type)
    RelativeLayout btnTradeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wgy_activity_search_company);
        ButterKnife.bind(this);
        setHeadTitle("请选择查询条件");
        mLoading = new LoadingDialogUtil(this);
        ivRight.setBackgroundResource(R.mipmap.icon_search_img);
        ivRight.setVisibility(View.VISIBLE);
        loadingDatas();
    }

    private void loadingDatas() {
        mLoading.show();
        HttpRequestHelper.getInstance().getCompanyTradeTypes(this, 1, new onNetCallback() {
            @Override
            public void onError(String errorMsg) {
                mLoading.dismiss();
            }

            @Override
            public void onSuccess(int requestCode, JsonResult mJsonResult) {
                mLoading.dismiss();
            }
        });
    }
    private String tradeTypeCode;
    private String oneCode;
    private String twoCode;
    private String threeCode;
    private String bigCode;//企业规模
    @OnClick({R.id.btn_back, iv_right, R.id.btn_yh_type2, R.id.btn_yh_qy, R.id.btn_trade_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case iv_right:
                Intent intent=new Intent(this,EnterpriseListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tradeTypeCode", tradeTypeCode);
                bundle.putString("bigCode", bigCode);
                bundle.putString("oneCode", oneCode);
                bundle.putString("twoCode", twoCode);
                bundle.putString("threeCode", threeCode);
                intent.putExtras(bundle);
                this.setResult(EnterpriseListActivity.SEARCH_COMPANY_CODE, intent);
                finish();

                break;
            case R.id.btn_yh_type2:
                //FIXME

                break;
            case R.id.btn_yh_qy:
                //FIXME
         /*       new StreetSelectDialog(
                        WgySearchCompanysActivity.this,
                        new cn.safetys.ywngovernment.view.StreetSelectDialog.OnTextCListener() {

                            @Override
                            public void onClick(String mText, String pcode,
                                                String ccode, String dcode) {

                                tv_qy.setText(mText);
                                oneCode = pcode;
                                twoCode = ccode;
                                threeCode = dcode;
                            }
                        }).show();*/
                break;
            case R.id.btn_trade_type:
                //FIXME
                break;
        }
    }
}
