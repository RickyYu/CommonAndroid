package com.safetys.zatgov;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.safetys.widget.common.SPUtils;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.PrefKeys;
import com.safetys.zatgov.ui.fragment.InfomationFragment;
import com.safetys.zatgov.ui.fragment.LawEnforcementFragment;
import com.safetys.zatgov.ui.fragment.SettingFragment;
import com.safetys.zatgov.utils.LoadingDialogUtil;
import com.safetys.zatgov.utils.XmlParseUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends FragmentActivity {
    LawEnforcementFragment fragmentScf;
    InfomationFragment fragmentInfo;
    SettingFragment fragmentSetting;
    @BindView(R.id.button_law)
    RelativeLayout buttonLaw;
    @BindView(R.id.button_infomation)
    RelativeLayout buttonInfomation;
    @BindView(R.id.button_setting)
    RelativeLayout buttonSetting;
    private View mCurrentButton;
    private LoadingDialogUtil mLoading;// 正在加载框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        // 更新数据
        updateData();
    }

    private void initView() {
        fragmentScf = (LawEnforcementFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_scf);// 行政执法
        fragmentInfo = (InfomationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_info);// 信息查询
        fragmentSetting = (SettingFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_setting);// 设置
        getSupportFragmentManager().beginTransaction().hide(fragmentInfo)
                .hide(fragmentSetting).show(fragmentScf).commit();
        setButton(buttonLaw);
        mLoading = new LoadingDialogUtil(this);
    }

    @OnClick({R.id.button_law, R.id.button_infomation, R.id.button_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_law:
                getSupportFragmentManager().beginTransaction().hide(fragmentInfo)
                        .hide(fragmentSetting).show(fragmentScf).commit();
                setButton(view);
                break;
            case R.id.button_infomation:
                getSupportFragmentManager().beginTransaction().hide(fragmentScf)
                        .hide(fragmentSetting).show(fragmentInfo).commit();
                setButton(view);
                break;
            case R.id.button_setting:
                getSupportFragmentManager().beginTransaction().hide(fragmentScf)
                        .hide(fragmentInfo).show(fragmentSetting).commit();
                setButton(view);
                break;
        }
    }

    private void setButton(View v) {
        if (mCurrentButton != null && mCurrentButton.getId() != v.getId()) {
            (((ViewGroup) mCurrentButton).getChildAt(0)).setEnabled(true);
            (((ViewGroup) mCurrentButton).getChildAt(1)).setEnabled(true);
        }
        (((ViewGroup) v).getChildAt(0)).setEnabled(false);
        (((ViewGroup) v).getChildAt(1)).setEnabled(false);
        mCurrentButton = v;
    }

    /**
     * 更新数据 如行业类型
     */
    private void updateData() {
        mLoading.show();
        new Thread(new Runnable() {

            @Override
            public void run() {

                // 当前app中存储的
                int versionCode = (int) SPUtils.getData(PrefKeys.PREF_ENUM_VERSION_CODE, 0);


                if (versionCode < AppConfig.ENUM_VERSION_CODE) {
                    // 更新
                    XmlParseUtil.starParse(MainActivity.this);
                    SPUtils.saveData(PrefKeys.PREF_ENUM_VERSION_CODE,
                            AppConfig.ENUM_VERSION_CODE);
                }
                // 关闭加载框
                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Process.killProcess(Process.myPid());
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            mLoading.dismiss();
            // 具体的操作代码
            new AlertDialog.Builder(this)

                    .setTitle("确定退出程序?")

                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }

                    })

                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            finish();//
                        }

                    }).show();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

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
}
