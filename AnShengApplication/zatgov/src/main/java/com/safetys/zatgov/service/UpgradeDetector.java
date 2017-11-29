package com.safetys.zatgov.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.AppUtils;
import com.safetys.widget.common.SPUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.ZatApplication;
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
public class UpgradeDetector {
    // 3小时检测周期
    private static final long DETECTOR_PERIOD = 3 * 60 * 60 * 1000;
    // 检查更新结果处理者
    private static Handler handler = null;

    // 更新线程
    private static Thread cycleThread;
    private static Thread singleThread;

    private static boolean loopTag = false;

    private static VerInfo newVerInfo;

    private static onNetCallback mCallback = new onNetCallback() {

        @Override
        public void onSuccess(int requestCode, JsonResult mJsonResult) {
            switch (requestCode) {
                case 211:
                    handler = getHandler(x.app());
                    newVerInfo = null;
                    if(mJsonResult.getEntity()!=null){
                        newVerInfo = JSON.parseObject(mJsonResult.getEntity(), VerInfo.class);
                    }
                    int verNum = AppUtils.getVerCode(x.app(),AppConfig.BASE_PACKAGE);
                    int newVerNum = verNum;
                    if(newVerInfo != null){
                        newVerNum = Integer.parseInt(newVerInfo.getVersionCode());
                    }
                    handler.sendEmptyMessage(newVerNum-verNum);
                    break;
                case 222:
                    handler = getHandler(x.app());
                    newVerInfo = null;
                    if(mJsonResult.getEntity()!=null){
                        newVerInfo = JSON.parseObject(mJsonResult.getEntity(), VerInfo.class);
                    }
                    int verNum2 = AppUtils.getVerCode(x.app(),AppConfig.BASE_PACKAGE);
                    int newVerNum2 = verNum2;
                    if(newVerInfo != null)
                        newVerNum = Integer.parseInt(newVerInfo.getVersionCode());
                    Message msg = new Message();
                    msg.what = newVerNum2 - verNum2;
                    // 以此判别是否需要提示已经是最新版，true 表示不提示
                    msg.obj = true;
                    handler.sendMessage(msg);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onError(String errorMsg) {

            LogUtil.i("errorMsg:"+errorMsg);
        }
    };

    /**
     * 获取更新检测结果处理器
     */
    private static Handler getHandler(final Context context) {

        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //停止服务
                ((ZatApplication)x.app()).stoptUploadService();
                int what = msg.what;
                Object isBackstage = msg.obj;
                if (what > 0) {
                    //有新版本
                    SPUtils.saveData(PrefKeys.PREF_HAVE_NEW_VERSION, true);
                    // 提示下载
                    DialogUtil.showSystemMsgDialog(context, "检查到最新版本，是否立即下载？", "取消", new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            //"http://dldir1.qq.com/qqfile/QQIntl/QQi_wireless/Android/qqi_4.6.13.6034_office.apk"
                            DownloadFileUtil.startDownloadFile(context,AppConfig.HOST_ADDRESS_YH+newVerInfo.getFileRealPath() , true);
                        }
                    });
                } else {//已经是最新版

                    if (isBackstage != null && (Boolean) isBackstage) {
                        // 自动检测升级时检测到为最新版不需提示
                        Toast.makeText(
                                context,
                                context.getString(R.string.upgrade_is_the_latest_version),
                                Toast.LENGTH_LONG).show();
                    } else {
                        // 手动检测时需提示
                        Toast.makeText(
                                context,
                                context.getString(R.string.upgrade_is_the_latest_version),
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
    public static void processFrontstageOneTime(final Context context){
        Toast.makeText(context, R.string.upgrade_now, Toast.LENGTH_LONG).show();
        HttpRequestHelper.getInstance().getVersionInfo(context, 211, mCallback);
    }



    /**
     * 后台一次检测
     */
    public static void processBackstageOneTime(final Context context) {
        HttpRequestHelper.getInstance().getVersionInfo(context,222, mCallback);
    }

    /**
     * 后台循环检测
     */
    public static void processBackstageLoop(final Context context) {

        cycleThread = new Thread() {
            @Override
            public void run() {
                while (loopTag) {
                    try {
                        HttpRequestHelper.getInstance().getVersionInfo(context, 222, mCallback);
                        Thread.sleep(DETECTOR_PERIOD);
                    } catch ( Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        cycleThread.start();
    }

    //FIXME 更恰当的终止更新线程
    public static void stopProcess() {
        try {
            if (cycleThread != null) {
                cycleThread.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 终止 while 循环
            loopTag = false;
        }
    }

}
