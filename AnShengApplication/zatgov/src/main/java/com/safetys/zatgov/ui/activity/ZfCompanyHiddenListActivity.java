package com.safetys.zatgov.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.ui.fragment.NoTroubleListFragment;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class ZfCompanyHiddenListActivity extends FragmentActivity implements
        View.OnClickListener {

    private NoTroubleListFragment listFragment;// 查看企业隐患
    private View mBtn_back;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_company_hidden_list);
        // 默认不弹出软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();

    }

    private void initView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("企业隐患列表");
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);
        listFragment = (NoTroubleListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_list);// 重大隐患

        getSupportFragmentManager().beginTransaction().show(listFragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }

    }

}
