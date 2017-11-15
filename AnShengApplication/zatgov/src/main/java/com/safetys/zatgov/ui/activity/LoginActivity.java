package com.safetys.zatgov.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.safetys.widget.common.SPUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.MainActivity;
import com.safetys.zatgov.R;
import com.safetys.zatgov.SecondActivity;
import com.safetys.zatgov.bean.UserInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.PrefKeys;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;
import com.safetys.zatgov.utils.StringUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:登陆页面
 */
public class LoginActivity extends BaseActivity implements onNetCallback {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.lly_logo_view)
    LinearLayout llyLogoView;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_save_password)
    CheckBox cbSavePassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    // 进入工程模式的判断
    private long exitTime0 = 0;
    private long exitTime1 = 0;
    private long exitTime2 = 0;
    private long exitTime3 = 0;
    private boolean isGridPerson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoading = new LoadingDialogUtil(this, "正在登陆");
        //初始化数据
        initData();
    }

    private void initData() {

        etAccount.setText((String) SPUtils.getData(PrefKeys.PREF_USER_ACCOUNT,
                ""));
        boolean isRemb = (boolean) SPUtils.getData(PrefKeys.PREF_REMMBER_PWD_KEY,
                false);
        if (isRemb) {
            cbSavePassword.setChecked(true);
            etPassword.setText((String) SPUtils.getData(PrefKeys.PREF_USER_ENCRYPT_PWD_KEY, ""));
        } else {
            cbSavePassword.setChecked(false);
        }
    }

    @OnClick({R.id.iv_logo, R.id.cb_save_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                if ((System.currentTimeMillis() - exitTime0) > 800) {
                    exitTime0 = System.currentTimeMillis();
                } else if ((System.currentTimeMillis() - exitTime1) > 800) {
                    exitTime1 = System.currentTimeMillis();
                } else if ((System.currentTimeMillis() - exitTime2) > 800) {
                    exitTime2 = System.currentTimeMillis();
                } else if ((System.currentTimeMillis() - exitTime3) > 800) {
                    exitTime3 = System.currentTimeMillis();
                } else {
                    ToastUtils.showMessage(getApplication(),"进入工程模式！");
                    final EditText et = new EditText(LoginActivity.this);
                    String hintStr = (String) SPUtils.getData(PrefKeys.PREF_IP_KEY, "");
                    et.setHint(!hintStr.equals("") ? hintStr
                            : AppConfig.DEFAULT_IP);
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("输入IP,例：121.41.101.87:7090")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(et)
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            String input = et.getText()
                                                    .toString();
                                            if (input.equals("")) {
                                                ToastUtils.showMessage(getApplicationContext(),"IP地址不能为空！" + input);

                                            } else {
                                                AppConfig.HOST_ADDRESS_YH = AppConfig.HEAD_IP
                                                        + et.getText()
                                                        .toString()
                                                        + "/huzhou";
                                                AppConfig.HOST_ADDRESS_DA = AppConfig.HEAD_IP
                                                        + et.getText()
                                                        .toString()
                                                        + "/huzhouArchives";
                                                String str = AppConfig.HOST_ADDRESS_YH;
                                                SPUtils.saveData(PrefKeys.PREF_IP_KEY,
                                                        et.getText().toString());
                                                ToastUtils.showMessage(
                                                        LoginActivity.this,
                                                        "IP地址更改成功！");
                                            }
                                        }

                                    }).setNegativeButton("取消", null).show();
                }
                break;
            case R.id.cb_save_password:
                break;
            case R.id.btn_login:
                String accountId = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                if(TextUtils.isEmpty(accountId) || TextUtils.isEmpty(password)){
                  ToastUtils.showMessage(getApplicationContext(), "账号或密码不能为空!");
                }else{
                    mLoading.show();
                    MobclickAgent.onProfileSignIn("accountId");
                    HttpRequestHelper.getInstance().login(accountId, password,
                            LoginActivity.this);
                }
                break;
        }
    }

    @OnCheckedChanged(value = R.id.cb_save_password)
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        Logger.i("isChecked =  "+isChecked);
        SPUtils.saveData(PrefKeys.PREF_REMMBER_PWD_KEY, isChecked);
    }

    @OnTextChanged(value=R.id.et_account)
    public void textChange(CharSequence s, int start, int before,
                           int count){
        etPassword.setText("");
        cbSavePassword.setChecked(false);
    }

    @Override
    public void onError(String errorMsg) {
        mLoading.dismiss();
        DialogUtil.showMsgDialog(LoginActivity.this, errorMsg, false, null);
    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();
        SPUtils.saveData(PrefKeys.PREF_USER_ACCOUNT, etAccount.getText()
                .toString());
        SPUtils.saveData(PrefKeys.PREF_USER_ENCRYPT_PWD_KEY, etPassword
                .getText().toString());
        SPUtils.saveData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, mJsonResult.getIdentify());

        if (mJsonResult.getEntity() != null) {
            UserInfo mInfo = JSON.parseObject(mJsonResult.getEntity(),
                    UserInfo.class);
            SPUtils.saveData(PrefKeys.PREF_USER_ORG_NAME_KEY,
                    StringUtil.nvl(mInfo.getUserCompany()));
            SPUtils.saveData(PrefKeys.PREF_USER_NAME_KEY,StringUtil.nvl(mInfo.getFactName()));

            isGridPerson = mInfo.isGridPerson();
            SPUtils.saveData(PrefKeys.PREF_USER_TYPE_KEY, isGridPerson);
        }
        Intent mIntent;
        if (isGridPerson) {
            mIntent = new Intent(LoginActivity.this, SecondActivity.class);

        } else {
            mIntent = new Intent(LoginActivity.this, MainActivity.class);
        }
        startActivity(mIntent);
    }
}
