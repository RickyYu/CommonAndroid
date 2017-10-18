package com.safetys.nbsxs.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.safetys.nbsxs.R;
import com.safetys.nbsxs.base.BaseActivity;
import com.safetys.nbsxs.common.PrefKeys;
import com.safetys.nbsxs.entity.JsonResult;
import com.safetys.nbsxs.http.HttpRequestHelper;
import com.safetys.nbsxs.http.onNetCallback;
import com.safetys.nbsxs.utils.DialogUtil;
import com.safetys.nbsxs.utils.LoadingDialogUtil;
import com.safetys.widget.common.SPUtils;

import org.xutils.common.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements onNetCallback {

    @BindView(R.id.logo_view)
    LinearLayout logoView;
    @BindView(R.id.edit_account)
    EditText editAccount;// 登陆账号
    @BindView(R.id.edit_password)
    EditText editPassword;// 登陆密码
    @BindView(R.id.cb_save_password)
    CheckBox cbSavePassword;// 记住密码
    @BindView(R.id.btn_login)
    Button btnLogin;// 登陆按钮
    @BindView(R.id.view_login)
    RelativeLayout viewLogin;
    private LoadingDialogUtil mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String accountId = editAccount.getText().toString().trim()
                        .toUpperCase();
                String password = editPassword.getText().toString().trim();

                // 假如账号或者密码为空，进行提示
                if (TextUtils.isEmpty(accountId) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空。",
                            Toast.LENGTH_LONG).show();
                } else {
                    mLoading.show();
                    HttpRequestHelper.getInstance().login(accountId, password,
                            LoginActivity.this);
                }
                break;
        }
    }

    @OnTextChanged(value=R.id.edit_account,callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void textChange(CharSequence s, int start, int before,
                           int count){
        editPassword.setText("");
    }

    @OnCheckedChanged(value = R.id.cb_save_password)
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        Logger.i("isChecked =  "+isChecked);
        SPUtils.saveData(PrefKeys.PREF_REMMBER_PWD_KEY, isChecked);
    }

    @SuppressLint("CommitPrefEdits")
    private void initView() {
        // 开发时使用 AJZQ 123
        // 330200400042819
        // 123456Aa
        //330203600058664 Abc123456
        editAccount.setText((String) SPUtils.getData(PrefKeys.PREF_USER_ACCOUNT, ""));
        boolean remb = (boolean) SPUtils.getData(PrefKeys.PREF_REMMBER_PWD_KEY, false);
        if (remb) {
            cbSavePassword.setChecked(true);
            editPassword.setText((String) SPUtils.getData(PrefKeys.PREF_USER_ENCRYPT_PWD_KEY, ""));
        } else {
            cbSavePassword.setChecked(false);
        }
        mLoading = new LoadingDialogUtil(this, "正在登录，请稍后。");
    }

    @Override
    public void onError(final String errorMsg) {
        mLoading.dismiss();
        DialogUtil.showMsgDialog(LoginActivity.this, errorMsg, false, null);
    }

    @Override
    public void onSuccess(int requestCode, final JsonResult mJsonResult) {
        mLoading.dismiss();
        // 跳转至主界面
        JSONObject mJsonObject = JSON.parseObject(mJsonResult
                .getEntity());
        String userComanyName = mJsonObject.getString("name") + "";
        String userPhone = mJsonObject.getString("phone") + "";
        // 保存账号和密码等信息
        SPUtils.saveData(PrefKeys.PREF_USER_ACCOUNT,
                editAccount.getText().toString());
        // 假如账号变化，第一次登录标记值重置

        String oldName = (String) SPUtils.getData(PrefKeys.PREF_USER_ACCOUNT, "");

        if (!oldName.equals(editAccount.getText().toString())) {
            SPUtils.saveData(PrefKeys.PREF_NOT_IS_FIRST_LOGIN, false);
        }
        SPUtils.saveData(PrefKeys.PREF_USER_ENCRYPT_PWD_KEY,
                editPassword.getText().toString());
        SPUtils.saveData(PrefKeys.PREF_LOGIN_IDENTITY_KEY,
                mJsonResult.getIdentify());
        SPUtils.saveData(PrefKeys.PREF_USER_ORG_NAME_KEY,
                userComanyName);
        SPUtils.saveData(PrefKeys.PREF_USER_PHONE_KEY,
                userPhone);
        LogUtil.i("userPhone:" + userPhone);
        Logger.i("userPhone:" + userPhone);
        Intent mIntent = new Intent(LoginActivity.this,
                MenuActivity.class);
        startActivity(mIntent);
        finish();
    }
}
