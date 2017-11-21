package com.safetys.zatgov.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.ui.fragment.SettingFragment;
import com.safetys.zatgov.utils.LoadingDialogUtil;

/**
 * Author:Created by Ricky on 2017/11/17.
 * Description:
 */
public class SettingActivity extends FragmentActivity implements
        View.OnClickListener {
    private View currentButton;
    private LoadingDialogUtil mLoading;// 正在加载框
    private SettingFragment mSettingFragment;// 设置
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mLoading.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("设置");
        mSettingFragment = (SettingFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_setting);// 设置
        View btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
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

    private void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            (((ViewGroup) currentButton).getChildAt(0)).setEnabled(true);
            (((ViewGroup) currentButton).getChildAt(1)).setEnabled(true);
        }
        (((ViewGroup) v).getChildAt(0)).setEnabled(false);
        (((ViewGroup) v).getChildAt(1)).setEnabled(false);
        currentButton = v;
    }

}
