package com.safetys.nbsxs.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.safetys.nbsxs.R;
import com.safetys.nbsxs.SxsApplication;
import com.safetys.nbsxs.base.BaseActivity;
import com.safetys.nbsxs.common.PrefKeys;
import com.safetys.nbsxs.entity.JsonResult;
import com.safetys.nbsxs.http.HttpRequestHelper;
import com.safetys.nbsxs.http.onNetCallback;
import com.safetys.nbsxs.utils.DialogUtil;
import com.safetys.nbsxs.utils.LoadingDialogUtil;
import com.safetys.widget.common.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sjw
 * 修改密码界面
 */
public class ChangePasswordActivity extends BaseActivity implements OnClickListener ,onNetCallback {

	private View mBtn_back;
	private EditText mEd_new_pass;//新密码
	private EditText mEd_new_pass2;//确认新密码
	private EditText mEd_old_pass;//老密码
	private View btn_save;//提交
	private LoadingDialogUtil mLoading;
	private TextView username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		initView();
	}

	private void initView() {
		setHeadTitle("修改密码");
		mBtn_back = findViewById(R.id.btn_back);
		mBtn_back.setOnClickListener(this);
		String userName=getIntent().getStringExtra("username");
		username = (TextView) findViewById(R.id.tv_username2);
		username.setText(userName);
		mEd_new_pass = (EditText) findViewById(R.id.ed_new_pass);
		mEd_new_pass2 = (EditText) findViewById(R.id.ed_new_pass2);
		mEd_old_pass = (EditText) findViewById(R.id.ed_old_pass);
		
		btn_save =  findViewById(R.id.title_right);
		btn_save.setVisibility(View.VISIBLE);
		btn_save.setOnClickListener(this);
		
		mLoading = new LoadingDialogUtil(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.title_right:
			submit();
			break;
		default:
			break;
		}
	}

	/**
	 * 保存
	 */
	private void submit() {
		final String newPass = mEd_new_pass.getText().toString();
		String newPass2 = mEd_new_pass2.getText().toString();
		final String oldPass = mEd_old_pass.getText().toString();
		if(TextUtils.isEmpty(newPass)||TextUtils.isEmpty(newPass2)||TextUtils.isEmpty(oldPass)){
			ToastUtils.showMessage(this, "请填写完整信息。");
			return;
		}
		if(!newPass.equals(newPass2)){
			ToastUtils.showMessage(this, "两次填写的密码不同。");
			return;
		}
		if(newPass.length()<8||newPass.length()>16){
			ToastUtils.showMessage(this, "密码长度必须为8到16位。");
			return;
		}
		if(!checkPassword(newPass)){
			ToastUtils.showMessage(this, "密码由数字字母组成，必须包含大小写字母以及数字");
			return;
		}
		DialogUtil.showMsgDialog2(this, "确定修改密码？", "取消", false, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mLoading.show();
				//TODO
				HttpRequestHelper.getInstance().changePassword(ChangePasswordActivity.this, newPass, oldPass, 0, ChangePasswordActivity.this);
			}
		});
	}

	@Override
	public void onError(String errorMsg) {
		mLoading.dismiss();
		DialogUtil.showMsgDialog(this, errorMsg, false, null);
	}

	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		mLoading.dismiss();
		DialogUtil.showMsgDialog(this, "修改密码成功。", true,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						final SharedPreferences mPreferences = ((SxsApplication) getApplicationContext())
								.getAppMainPreferences();
						Editor mEditor = mPreferences.edit();
						// mEditor.remove(PrefKeys.PREF_USER_ACCOUNT);
						mEditor.remove(PrefKeys.PREF_USER_ENCRYPT_PWD_KEY);

						mEditor.putBoolean(PrefKeys.PREF_REMMBER_PWD_KEY,
								false);
						mEditor.commit();
						Intent intent = new Intent(ChangePasswordActivity.this,
								LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);

					}
				});
	}
	
	private boolean checkPassword(String password){
		String regex_a="[a-z]+?";
		String regex_A="[A-Z]+?";
		String regex_N="[0-9]+?";
		Pattern p_a=Pattern.compile(regex_a);
		Matcher m=p_a.matcher(password);
		if(m.find()!=true){
			return false;
		}
		Pattern p_A=Pattern.compile(regex_A);
		Matcher m_A=p_A.matcher(password);
		if(m_A.find()!=true){
			return false;
		}
		
		Pattern p_N=Pattern.compile(regex_N);
		Matcher m_N=p_N.matcher(password);
		if(m_N.find()!=true){
			return false;
		}
		
		String regex_all="[a-zA-Z0-9]+?";
		Pattern pattern_All= Pattern.compile(regex_all);
		Matcher matcher = pattern_All.matcher(password);
		if(!matcher.matches()){
			return false;
		}
		return true;
	}
}
