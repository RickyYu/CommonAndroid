package com.safetys.zatgov.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
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
public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.ed_new_pass)
    EditText edNewPass;
    @BindView(R.id.ed_new_pass2)
    EditText edNewPass2;
    @BindView(R.id.ed_old_pass)
    EditText edOldPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setHeadTitle("修改密码");
        mLoading = new LoadingDialogUtil(this);
    }

    @OnClick({R.id.btn_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {
        final String newPass = edNewPass.getText().toString();
        String newPass2 = edNewPass2.getText().toString();
        final String oldPass = edOldPass.getText().toString();
        if(TextUtils.isEmpty(newPass)||TextUtils.isEmpty(newPass2)||TextUtils.isEmpty(oldPass)){
            ToastUtils.showMessage(this, "请填写完整信息。");
            return;
        }
        if(!newPass.equals(newPass2)){
            ToastUtils.showMessage(this, "两次填写的密码不同。");
            return;
        }
        DialogUtil.showMsgDialog2(this, "确定修改密码？", "取消", false, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLoading.show();
                HttpRequestHelper.getInstance().changePassword(ChangePasswordActivity.this, newPass, oldPass, 0, new onNetCallback() {
                    @Override
                    public void onError(String errorMsg) {
                        mLoading.dismiss();
                        DialogUtil.showMsgDialog(ChangePasswordActivity.this, errorMsg, true, null);
                    }
                    @Override
                    public void onSuccess(int requestCode, JsonResult mJsonResult) {
                        mLoading.dismiss();
                        DialogUtil.showMsgDialog(ChangePasswordActivity.this, "修改密码成功。", true, null);
                    }
                });
            }
        });
    }
}