package com.safetys.nbsxs.ui.activity;

import java.io.File;

import org.xutils.common.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.safetys.nbsxs.LoginActivity;
import cn.safetys.nbsxs.R;
import cn.safetys.nbsxs.base.AppApplication;
import cn.safetys.nbsxs.base.BaseActivity;
import cn.safetys.nbsxs.bean.JsonResult;
import cn.safetys.nbsxs.config.AppConfig;
import cn.safetys.nbsxs.config.Const;
import cn.safetys.nbsxs.config.PrefKeys;
import cn.safetys.nbsxs.http.HttpRequestHelper;
import cn.safetys.nbsxs.http.onNetCallback;
import cn.safetys.nbsxs.service.UpgradeDetector;
import cn.safetys.nbsxs.util.DialogUtil;
import cn.safetys.nbsxs.util.LoadingDialogUtil;

/**
 * 个人中心20161008
 */
public class SettingActivity extends BaseActivity implements OnClickListener {

	private TextView tv_username;// 用户名
	private View btn_deletePic;// 清除缓存
	private View btn_checkupdate;// 检查更新
	private View btn_changepassword;// 修改密码
	private View btn_stop;// 注销
	private Intent intent;
	private LoadingDialogUtil mLoading;
	private LoadingDialogUtil deletLoading;
	private LoadingDialogUtil checkLoading;
	private TextView mTv_version;
	private TextView tv_updata;
	private String userName;
	private SharedPreferences mPreferences;
	private MyBroadcastReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
	}

	private void initView() {
		mLoading = new LoadingDialogUtil(this, "正在加载，请稍后。");
		deletLoading = new LoadingDialogUtil(this, "清除缓存中，请稍后。");
		checkLoading = new LoadingDialogUtil(this, "检查更新中，请稍后。");
		mReceiver = new MyBroadcastReceiver();
		IntentFilter mFilter = new IntentFilter(Const.ACTION_CLOSE_DIALOG);
		this.registerReceiver(mReceiver, mFilter);

		mLoading.show();
		setHeadTitle("设置");
		setBackBtnClick(this);
		tv_username = (TextView) findViewById(R.id.tv_username);
		btn_deletePic = findViewById(R.id.btn_deletePic);
		btn_deletePic.setOnClickListener(this);
		btn_checkupdate = findViewById(R.id.btn_checkupdate);
		btn_checkupdate.setOnClickListener(this);
		btn_changepassword = findViewById(R.id.btn_changepassword);
		btn_changepassword.setOnClickListener(this);
		btn_stop = findViewById(R.id.btn_stop);
		btn_stop.setOnClickListener(this);
		mTv_version = (TextView) findViewById(R.id.text_version);
		tv_updata = (TextView) findViewById(R.id.tv_updata);

		mPreferences = ((AppApplication) this.getApplicationContext())
				.getAppMainPreferences();

		boolean hasNew = mPreferences.getBoolean(
				PrefKeys.PREF_HAVE_NEW_VERSION, false);
		try {
			String versionName = getPackageManager().getPackageInfo(
					this.getPackageName(), 0).versionName;
			String result = versionName.substring(0, versionName.indexOf("_"));
			mTv_version.setText(result);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (hasNew) {
			tv_updata.setText("有新版本");
		} else {
			tv_updata.setText("已是最新版");
		}
		userName = mPreferences.getString(PrefKeys.PREF_USER_ACCOUNT, "");
		tv_username.setText(userName);

	}

	@Override
	protected void onResume() {
		super.onResume();

		mLoading.dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_checkupdate:
			checkLoading.show();
			new UpgradeDetector().processFrontstageOneTime(this);
			break;

		case R.id.btn_changepassword:
			intent = new Intent(SettingActivity.this,
					ChangePasswordActivity.class);
			intent.putExtra("username", userName);
			startActivity(intent);

			break;

		case R.id.btn_deletePic:

			DialogUtil.showMsgDialog2(SettingActivity.this, "确定清除缓存？", "取消",
					new OnClickListener() {

						@Override
						public void onClick(View v) {

							deletLoading.show();
							File file = AppConfig
									.buildPath(AppConfig.HOME_CACHE);
							// 遍历文件查看
							printLev(file);
							// 清空文件夹
							deleteFile(file);
							// 关闭弹窗
							deletLoading.dismiss();
							DialogUtil.showMsgDialog(SettingActivity.this, "清除缓存完成。",
									false, null);

						}
					});
			break;
		case R.id.btn_stop:

			DialogUtil.showMsgDialog2(SettingActivity.this, "确定注销么?", "取消",
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							final SharedPreferences mPreferences = ((AppApplication) getApplicationContext())
									.getAppMainPreferences();
							Editor mEditor = mPreferences.edit();
							// mEditor.remove(PrefKeys.PREF_USER_ACCOUNT);
							mEditor.remove(PrefKeys.PREF_USER_ENCRYPT_PWD_KEY);

							mEditor.putBoolean(PrefKeys.PREF_REMMBER_PWD_KEY,
									false);
							mEditor.commit();
							intent = new Intent(SettingActivity.this,
									LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
									| Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);

						}
					});

			break;

		default:
			break;
		}
	}

	public static void deleteFile(File dir) {
		// 1,获取该文件夹下的所有的文件和文件夹
		File[] subFiles = dir.listFiles();
		// 2,遍历数组
		for (File subFile : subFiles) {
			// 3,判断是文件直接删除
			if (subFile.isFile()) {
				subFile.delete();
				// 4,如果是文件夹,递归调用
			} else {
				deleteFile(subFile);
			}
		}
		// 5,循环结束后,把空文件夹删掉
		Log.e("asd", "删除完成");
		dir.delete();
	}

	public static void printLev(File dir) {
		// 1,把文件夹中的所有文件以及文件夹的名字按层级打印
		File[] subFiles = dir.listFiles();
		// 2,遍历数组
		for (File subFile : subFiles) {
			// 3,无论是文件还是文件夹,都需要直接打印
			System.out.println(subFile);
			Log.e("asd", subFile + "");
			// 4,如果是文件夹,递归调用
			if (subFile.isDirectory()) {
				printLev(subFile);
			}
		}
	}

	/**
	 * 
	 * 关闭加载广播
	 */
	private class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(Const.ACTION_CLOSE_DIALOG)) {
				checkLoading.dismiss();
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mReceiver!=null){
			unregisterReceiver(mReceiver);
		}
	}
}
