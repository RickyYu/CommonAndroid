package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CheckList;
import com.safetys.zatgov.ui.fragment.GeneralHazardFragment;
import com.safetys.zatgov.ui.fragment.MajorHazardFragment;

import java.io.Serializable;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:隐患录入
 */
public class TroubleRegisterActivity extends FragmentActivity implements
        View.OnClickListener {

    private View mBtn_back;
    private View btn_yb;
    private View btn_zd;
    private View currentButton;

    private GeneralHazardFragment genealFragment;// 一般隐患
    private MajorHazardFragment majorFragment;// 重大隐患
    private String companyid;
    private String noteId;
    private String have;
    private String isform;
    private String source = "";// 判断是从检查表检查跳入
    private CheckList checkList;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 默认不弹出软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_trouble_register);
        initView();
    }

    private void initView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("隐患录入");
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);
        btn_yb = findViewById(R.id.btn_yb);
        btn_zd = findViewById(R.id.btn_zd);
        btn_yb.setOnClickListener(this);
        btn_zd.setOnClickListener(this);
        setButton(btn_yb);
        source = getIntent().getExtras().getString("source");
        genealFragment = (GeneralHazardFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_yb);// 一般隐患
        majorFragment = (MajorHazardFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_zd);// 重大隐患
        if (source == null) {
            have = getIntent().getExtras().getString("ishave");
            isform = getIntent().getExtras().getString("isform");
            checkList = (CheckList) getIntent().getExtras().getSerializable(
                    "checklist");
            companyid = checkList.getCompanyId();
            noteId = checkList.getCheckId();
        } else {
            btn_zd.setVisibility(View.GONE);
        }

        getSupportFragmentManager().beginTransaction().hide(majorFragment)
                .show(genealFragment).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                if (source == null) {//未与坚持表关联时做的检查操作
                    mIntent = new Intent(TroubleRegisterActivity.this,
                            NewZfYhLrActivity.class);
                    mIntent.putExtra("checklist", (Serializable) checkList);
                    mIntent.putExtra("ishave", "no");
                    mIntent.putExtra("isform", isform);


                    startActivity(mIntent);
                    finish();
                } else {
                    finish();
                }
                break;
            // 一般隐患
            case R.id.btn_yb:
                getSupportFragmentManager().beginTransaction().hide(majorFragment)
                        .show(genealFragment).commit();
                setButton(v);

                break;
            // 重大隐患
            case R.id.btn_zd:
                getSupportFragmentManager().beginTransaction().hide(genealFragment)
                        .show(majorFragment).commit();
                setButton(v);

                break;
            default:
                break;
        }
    }

    private void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
            ((TextView) ((ViewGroup) currentButton).getChildAt(0))
                    .setEnabled(true);
        }
        v.setEnabled(false);
        ((TextView) ((ViewGroup) v).getChildAt(0)).setEnabled(false);
        currentButton = v;
    }
}
