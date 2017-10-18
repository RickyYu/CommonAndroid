package com.safetys.nbsxs.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSON;
import com.safetys.nbsxs.common.AppConfig;
import com.safetys.nbsxs.common.PrefKeys;
import com.safetys.nbsxs.entity.JsonResult;
import com.safetys.nbsxs.entity.VerInfo;
import com.safetys.nbsxs.http.HttpRequestHelper;
import com.safetys.nbsxs.http.onNetCallback;
import com.safetys.nbsxs.utils.DialogUtil;
import com.safetys.nbsxs.utils.DownloadFileUtil;
import com.safetys.widget.common.SPUtils;

public class UpgradeService extends Service implements onNetCallback {
	private int count = 0;
	public static final String ACTION_UPGRADE = "com.safetys.nbsxs.service.UpgradeService";
	private static VerInfo newVerInfo;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		SPUtils.saveData(PrefKeys.PREF_HAVE_NEW_VERSION, false);
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
			SPUtils.saveData(PrefKeys.PREF_HAVE_NEW_VERSION, true);
			// 提示下载
			DialogUtil.showSystemMsgDialog(this, "检查到最新版本，是否立即下载？", "取消", new OnClickListener(){

				@Override
				public void onClick(View v) {

					DownloadFileUtil.startDownloadFile(getApplicationContext(),newVerInfo.getRealPath() , true);
				}
				
			});
		}else{
			//无新版本
			SPUtils.saveData(PrefKeys.PREF_HAVE_NEW_VERSION, false);
		}
		stopSelf();
	}
}
