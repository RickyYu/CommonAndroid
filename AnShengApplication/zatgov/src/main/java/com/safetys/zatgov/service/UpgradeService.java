package com.safetys.zatgov.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.AppUtils;
import com.safetys.widget.common.SPUtils;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.PrefKeys;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.entity.VerInfo;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.DownloadFileUtil;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class UpgradeService extends Service implements onNetCallback {
    private int count = 0;
    public static final String ACTION_UPGRADE = "com.safetys.zatgov.service.UpgradeService";
    private static VerInfo newVerInfo;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        LogUtil.i("UpgradeService onCreate");
        SPUtils.saveData(PrefKeys.PREF_HAVE_NEW_VERSION, false);
        HttpRequestHelper.getInstance().getVersionInfo(this, 0, this);
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
        int verNum = AppUtils.getVerCode(x.app(),AppConfig.BASE_PACKAGE);
        int newVerNum = verNum;
        if(newVerInfo != null){
            newVerNum = Integer.parseInt(newVerInfo.getVersionCode());
        }
        if ((newVerNum-verNum) > 0) {
            //有新版本
            SPUtils.saveData(PrefKeys.PREF_HAVE_NEW_VERSION, true);
            // 提示下载
            DialogUtil.showSystemMsgDialog(this, "检查到最新版本，是否立即下载？", "取消", new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    DownloadFileUtil.startDownloadFile(getApplicationContext(), AppConfig.HOST_ADDRESS_YH+newVerInfo.getFileRealPath() , true);
                }

            });
        }
        stopSelf();
    }
}
