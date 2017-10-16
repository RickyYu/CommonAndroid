package com.safetys.nbsxs.service;


import org.xutils.x;
import org.xutils.common.util.LogUtil;

import cn.safetys.nbsxs.R;
import cn.safetys.nbsxs.base.*;
import cn.safetys.nbsxs.bean.JsonResult;
import cn.safetys.nbsxs.bean.VerInfo;
import cn.safetys.nbsxs.config.AppConfig;
import cn.safetys.nbsxs.config.Const;
import cn.safetys.nbsxs.config.PrefKeys;
import cn.safetys.nbsxs.http.HttpRequestHelper;
import cn.safetys.nbsxs.http.onNetCallback;
import cn.safetys.nbsxs.util.DialogUtil;
import cn.safetys.nbsxs.util.DownloadFileUtil;

import com.alibaba.fastjson.JSON;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 升级装饰器
 */
public class UpgradeDetector {
	private  Activity mActivity;
	private  String errorMsg;
	// 检查更新结果处理者
	private Handler handler = null;
	
	private VerInfo newVerInfo;
	
	private onNetCallback mCallback = new onNetCallback() {
		
		@Override
		public void onSuccess(int requestCode, JsonResult mJsonResult) {
			switch (requestCode) {
			case 211:
				handler = getHandler(mActivity);
				newVerInfo = null;
				if(mJsonResult.getEntity()!=null){
					newVerInfo = JSON.parseObject(mJsonResult.getEntity(), VerInfo.class);
				}
				int verNum = AppConfig.getVerCode(x.app());
				int newVerNum = verNum;
				if(newVerInfo != null){
					newVerNum = Integer.parseInt(newVerInfo.getVersionNum());
					Editor mEditor = ((AppApplication)x.app()).getAppMainPreferences().edit();
					mEditor.putBoolean(PrefKeys.PREF_HAVE_NEW_VERSION, true);
					mEditor.commit();
				}
				handler.sendEmptyMessage(newVerNum-verNum);
				break;
			default:
				break;
			}
		}
		
		@Override
		public void onError(String errorMsg) {
			handler=getHandler(UpgradeDetector.this.mActivity);
			UpgradeDetector.this.errorMsg = errorMsg;
			handler.sendEmptyMessage(-99);
		}
	};

	/**
	 * 获取更新检测结果处理器
	 */
	private Handler getHandler(final Activity mActivity) {

		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				//停止服务
				((AppApplication)x.app()).stopUploadService();
				int what = msg.what;
				Object isBackstage = msg.obj;
				//发送关闭广播
				mActivity.sendBroadcast(new Intent(Const.ACTION_CLOSE_DIALOG));
				if (what==-99) {
					Toast.makeText(mActivity,errorMsg,Toast.LENGTH_SHORT).show();
					return;
				}
				
				if (what > 0) {
					//有新版本
					Editor mEditor = ((AppApplication)x.app()).getAppMainPreferences().edit();
					mEditor.putBoolean(PrefKeys.PREF_HAVE_NEW_VERSION, true);
					mEditor.commit();
					// 提示下载
					DialogUtil.showMsgDialog2(mActivity, "检查到最新版本，是否立即下载？", "取消", new OnClickListener(){

						@Override
						public void onClick(View v) {
							//"http://dldir1.qq.com/qqfile/QQIntl/QQi_wireless/Android/qqi_4.6.13.6034_office.apk"
							DownloadFileUtil.startDownloadFile(mActivity,newVerInfo.getRealPath() , true);
						}
						
					});
				} else {//已经是最新版
					if (isBackstage != null && (Boolean) isBackstage) {
						// 自动检测升级时检测到为最新版不需提示
						Toast.makeText(mActivity, "您已经是最新版，不需要更新", Toast.LENGTH_SHORT).show();
					} else {
						// 手动检测时需提示
						Toast.makeText(
								mActivity,
								mActivity.getString(R.string.upgrade_is_the_latest_version),
								Toast.LENGTH_LONG).show();
					}
				} 
			}

		};
	}
	
	/**
	 * 前台一次检测
	 * @param context
	 */
	public void processFrontstageOneTime(final Activity mActivity){
		this.mActivity = mActivity;
		HttpRequestHelper.getInstance().getVersionInfo(mActivity, 211, mCallback);
	}
	
}
