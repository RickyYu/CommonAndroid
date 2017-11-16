package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.ui.fragment.EnterpriseItemFragment;
import com.safetys.zatgov.ui.fragment.NoTroubleListFragment;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class EnterpriseChooseActivity extends FragmentActivity implements
        View.OnClickListener {

    private View mBtn_back;
    private View btn_item;
    private View btn_list;
    private View currentButton;

    private EnterpriseItemFragment enterFragment;// 企业详情
    private NoTroubleListFragment listFragment;// 查看企业隐患
    private String id2;

    private LinearLayout llMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 默认不弹出软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_choose_enter);
        initView();
    }

    private void initView() {
        TextView title = (TextView) findViewById(R.id.title);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llMenu.setVisibility(View.GONE);
        title.setText("企业信息详情");
        Intent intent = this.getIntent();
        id2 = intent.getExtras().getString("id");
        ImageView iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(this);
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);
        btn_item = findViewById(R.id.btn_item);
        btn_list = findViewById(R.id.btn_list);
        btn_item.setOnClickListener(this);
        btn_list.setOnClickListener(this);

        enterFragment = (EnterpriseItemFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_en);// 企业详情
        listFragment = (NoTroubleListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_list);

        getSupportFragmentManager().beginTransaction().hide(listFragment)
                .show(enterFragment).commit();
        setButton(btn_item);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back:
                finish();
                break;
            // 企业详情
            case R.id.btn_item:
                getSupportFragmentManager().beginTransaction().hide(listFragment)
                        .show(enterFragment).commit();
                setButton(v);

                break;
            // 重大隐患
            case R.id.btn_list:
                getSupportFragmentManager().beginTransaction().hide(enterFragment)
                        .show(listFragment).commit();
                setButton(v);


                break;

            case R.id.iv_right:
                Intent intent =new Intent(EnterpriseChooseActivity.this,LocationActivity.class);
                intent.putExtra("id", id2);
                startActivityForResult(intent, 2222);
                break;
            default:
                break;
        }
    }

    private void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
            ( (ImageView)((ViewGroup) currentButton).getChildAt(0))
                    .setEnabled(true);
        }
        v.setEnabled(false);
        ( (ImageView)((ViewGroup) v).getChildAt(0)).setEnabled(false);
        currentButton = v;
    }
}
