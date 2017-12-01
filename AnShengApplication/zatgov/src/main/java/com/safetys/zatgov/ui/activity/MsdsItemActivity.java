package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class MsdsItemActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.t)
    TextView t;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.t4)
    TextView t4;
    @BindView(R.id.t5)
    TextView t5;
    @BindView(R.id.t6)
    TextView t6;
    @BindView(R.id.t7)
    TextView t7;
    @BindView(R.id.t8)
    TextView t8;
    @BindView(R.id.t9)
    TextView t9;
    @BindView(R.id.t10)
    TextView t10;
    @BindView(R.id.t11)
    TextView t11;
    @BindView(R.id.t12)
    TextView t12;
    @BindView(R.id.t13)
    TextView t13;
    @BindView(R.id.t14)
    TextView t14;
    @BindView(R.id.t15)
    TextView t15;
    @BindView(R.id.t16)
    TextView t16;
    @BindView(R.id.t17)
    TextView t17;
    @BindView(R.id.t18)
    TextView t18;
    @BindView(R.id.t19)
    TextView t19;
    @BindView(R.id.t20)
    TextView t20;
    @BindView(R.id.t21)
    TextView t21;
    @BindView(R.id.t22)
    TextView t22;
    @BindView(R.id.t23)
    TextView t23;
    @BindView(R.id.t24)
    TextView t24;
    @BindView(R.id.t25)
    TextView t25;
    @BindView(R.id.t26)
    TextView t26;
    @BindView(R.id.t27)
    TextView t27;
    @BindView(R.id.t28)
    TextView t28;
    @BindView(R.id.t29)
    TextView t29;
    @BindView(R.id.t30)
    TextView t30;
    @BindView(R.id.t31)
    TextView t31;
    @BindView(R.id.t32)
    TextView t32;
    @BindView(R.id.t33)
    TextView t33;
    @BindView(R.id.t34)
    TextView t34;
    @BindView(R.id.t35)
    TextView t35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msds_item);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        t.setText( bundle.getString("chineseName"));
        t1.setText( bundle.getString("englishName"));
        t2.setText( bundle.getString("cas"));
        t3.setText( bundle.getString("dangerousGoodsNumber"));
        t4.setText( bundle.getString("appearanceAndProperties"));
        t5.setText( bundle.getString("avoidContactConditions"));
        t6.setText( bundle.getString("meltingPoint"));
        t7.setText( bundle.getString("boilingPoint"));
        t8.setText( bundle.getString("water"));
        t9.setText( bundle.getString("atmosphere"));
        t10.setText( bundle.getString("saturatedSteamPressure"));
        t11.setText( bundle.getString("solubility"));
        t12.setText( bundle.getString("burningProperty"));
        t13.setText( bundle.getString("autoignitionTemperature"));
        t14.setText( bundle.getString("lowerExplosion"));
        t15.setText( bundle.getString("upperExplosion"));
        t16.setText( bundle.getString("precursorProduct"));

        t17.setText( bundle.getString("stability"));
        t18.setText( bundle.getString("polymerizationHazard"));
        t19.setText( bundle.getString("riskCategories"));
        t20.setText( bundle.getString("protectiveClothing"));
        t21.setText( bundle.getString("handProtection"));
        t22.setText( bundle.getString("invasionPathway"));
        t23.setText( bundle.getString("toxicity"));
        t24.setText( bundle.getString("mainUse"));
        t25.setText( bundle.getString("riskCharacteristics"));
        t26.setText( bundle.getString("fireMethod"));
        t27.setText( bundle.getString("handingInformation"));
        t28.setText( bundle.getString("healthHarm"));
        t29.setText( bundle.getString("eyeprotection"));
        t30.setText( bundle.getString("inhalationHarm"));
        t31.setText( bundle.getString("ingestionHarm"));
        t32.setText( bundle.getString("engineeringControl"));
        t33.setText( bundle.getString("respiratorySystemProtection"));
        t34.setText( bundle.getString("leakageDisposal"));
        t35.setText( bundle.getString("attentions"));
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
