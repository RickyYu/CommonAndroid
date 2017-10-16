package com.safetys.nbsxs.service;

import org.xutils.x;
import org.xutils.common.util.LogUtil;

import cn.safetys.nbsxs.base.AppApplication;
import cn.safetys.nbsxs.bean.JsonResult;
import cn.safetys.nbsxs.bean.VerInfo;
import cn.safetys.nbsxs.config.AppConfig;
import cn.safetys.nbsxs.config.PrefKeys;
import cn.safetys.nbsxs.http.HttpRequestHelper;
import cn.safetys.nbsxs.http.onNetCallback;
import cn.safetys.nbsxs.util.DialogUtil;
import cn.safetys.nbsxs.util.DownloadFileUtil;

import com.alibaba.fastjson.JSON;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;

public class UpgradeService extends Service implements onNetCallback{
	private int count = 0;
	public static final String ACTION_UPGRADE = "cn.safetys.nbsxs.service.UpgradeService";
	private static VerInfo newVerInfo;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		Editor mEditor = ((AppApplication)getApplicationContext()).getAppMainPreferences().edit();
		mEditor.putBoolean(PrefKeys.PREF_HAVE_NEW_VERSION, false);
		mEditor.commit();
//		Toast.makeText(this, R.string.upgrade_now, Toast.LENGTH_LONG).show();
		HttpRequestHelper.getInstance().getVersionInfo(this, 0, this);
	}

	@Override
	public void onDestroy() {
		stopSelf();
		super.onDestroy();
		
	}
	@Override
	public void onError(String errorMsg) {
		
	}
	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		newVerInfo = null;
		if(mJsonResult.getEntity()!=null){
			newVerInfo = JSON.parseObject(mJsonResult.getEntity(), VerInfo.class);
		}
		int verNum = AppConfig.getVerCode(getApplicationContext());
		int newVerNum = verNum;
		if(newVerInfo != null){
			newVerNum = Integer.parseInt(newVerInfo.getVersionNum());
		}
		if ((newVerNum-verNum) > 0) {
			//有新版本
			Editor mEditor = ((AppApplication)x.app()).getAppMainPreferences().edit();
			mEditor.putBoolean(PrefKeys.PREF_HAVE_NEW_VERSION, true);
			mEditor.commit();
			// 提示下载
			DialogUtil.showSystemMsgDialog(this, "检查到最新版本，是否立即下载？", "取消", new OnClickListener(){

				@Override
				public void onClick(View v) {
					//
					//"http://dldir1.qq.com/qqfile/QQIntl/QQi_wireless/Android/qqi_4.6.13.6034_office.apk"
					DownloadFileUtil.startDownloadFile(getApplicationContext(),newVerInfo.getRealPath() , true);
				}
				
			});
		}else{
			//无新版本
			Editor mEditor = ((AppApplication)x.app()).getAppMainPreferences().edit();
			mEditor.putBoolean(PrefKeys.PREF_HAVE_NEW_VERSION, false);
			mEditor.commit();
		}
		stopSelf();
	}
}
