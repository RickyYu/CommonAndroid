package com.safetys.nbsxs.ui.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.safetys.nbsxs.base.BaseActivity;
import com.safetys.nbsxs.common.PrefKeys;
import com.safetys.nbsxs.entity.JsonResult;
import com.safetys.nbsxs.http.HttpRequestHelper;
import com.safetys.nbsxs.http.onNetCallback;
import com.safetys.nbsxs.utils.DialogUtil;

import static com.umeng.analytics.pro.x.R;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements onNetCallback {

	private EditText mEd_account;// 登陆账号
	private EditText mEd_password;// 登陆密码

	private Button mBtn_login;// 登陆按钮
	private CheckBox mCb_remember;// 记住密码
//	private LoadingDialogUtil mLoading;
	private TransitionView mTransitionView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	@SuppressLint("CommitPrefEdits")
	private void initView() {
		mEd_account = (EditText) findViewById(R.id.edit_account);
		mEd_password = (EditText) findViewById(R.id.edit_password);
		mCb_remember = (CheckBox) findViewById(R.id.cb_save_password);
		// 开发时使用 AJZQ 123
		// 330200400042819
		// 123456Aa
		final SharedPreferences mPreferences = ((AppApplication) getApplicationContext())
				.getAppMainPreferences();
		mEd_account.setText(mPreferences.getString(PrefKeys.PREF_USER_ACCOUNT,
				""));
		boolean remb = mPreferences.getBoolean(PrefKeys.PREF_REMMBER_PWD_KEY,
				false);
		if (remb) {
			mCb_remember.setChecked(true);
			mEd_password.setText(mPreferences.getString(
					PrefKeys.PREF_USER_ENCRYPT_PWD_KEY, ""));
		} else {
			mCb_remember.setChecked(false);
		}
		// 记住密码功能勾选框
		mCb_remember.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Editor mEditor = mPreferences.edit();
				mEditor.putBoolean(PrefKeys.PREF_REMMBER_PWD_KEY, isChecked);
				mEditor.commit();
			}
		});

//		mLoading = new LoadingDialogUtil(this, "正在登录，请稍后。");

		mBtn_login = (Button) findViewById(R.id.btn_login);
		mBtn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String accountId = mEd_account.getText().toString().trim().toUpperCase();
				String password = mEd_password.getText().toString().trim();

				// 假如账号或者密码为空，进行提示
				if (TextUtils.isEmpty(accountId) || TextUtils.isEmpty(password)) {
					Toast.makeText(LoginActivity.this, "账号或密码不能为空。",
							Toast.LENGTH_LONG).show();
				} else {
//					mLoading.show();
					mTransitionView.startAnimation();
					HttpRequestHelper.getInstance().login(accountId, password,
							LoginActivity.this);
				}

			}
		});
		
		mTransitionView = (TransitionView) findViewById(R.id.ani_view);
		
		mEd_account.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				mEd_password.setText("");
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onError(final String errorMsg) {
//		mLoading.dismiss();
		mTransitionView.setOnAnimationEndListener(new TransitionView.OnAnimationEndListener() {
			
			@Override
			public void onEnd() {
				// TODO Auto-generated method stub
				DialogUtil.showMsgDialog(LoginActivity.this, errorMsg, false, null);
			}
		});
		mTransitionView.endAnimation(false);
		
	}

	@Override
	public void onSuccess(int requestCode, final JsonResult mJsonResult) {
//		mLoading.dismiss();
		findViewById(R.id.view_login).setVisibility(View.INVISIBLE);
		mTransitionView.setOnAnimationEndListener(new TransitionView.OnAnimationEndListener() {
			
			@Override
			public void onEnd() {
				// 跳转至主界面
				Intent mIntent = new Intent(LoginActivity.this, MenuActivity.class);
				startActivity(mIntent);
				JSONObject mJsonObject = JSON.parseObject(mJsonResult.getEntity());
				String userComanyName = mJsonObject.getString("name");
				String userPhone = mJsonObject.getString("phone");
				// 保存账号和密码等信息
				Editor mEditor = ((AppApplication) getApplicationContext())
						.getAppMainPreferences().edit();
				mEditor.putString(PrefKeys.PREF_USER_ACCOUNT, mEd_account.getText()
						.toString());
				//假如账号变化，第一次登录标记值重置
				String oldName = ((AppApplication) getApplicationContext()).getAppMainPreferences().getString(PrefKeys.PREF_USER_ACCOUNT, "");
				if(!oldName.equals(mEd_account.getText().toString())){
					mEditor.putBoolean(PrefKeys.PREF_NOT_IS_FIRST_LOGIN, false);
				}
				
				mEditor.putString(PrefKeys.PREF_USER_ENCRYPT_PWD_KEY, mEd_password
						.getText().toString());
				mEditor.putString(PrefKeys.PREF_LOGIN_IDENTITY_KEY,
						mJsonResult.getIdentify());
				mEditor.putString(PrefKeys.PREF_USER_ORG_NAME_KEY, userComanyName);
				mEditor.putString(PrefKeys.PREF_USER_PHONE_KEY, userPhone);
				mEditor.commit();
				finish();
			}
		});
		mTransitionView.endAnimation(true);
		
	}

}
