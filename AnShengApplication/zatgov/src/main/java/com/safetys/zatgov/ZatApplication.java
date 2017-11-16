package com.safetys.zatgov;

import android.app.Application;
import android.content.Intent;

import com.safetys.widget.common.SPUtils;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.service.UpgradeService;
import com.safetys.zatgov.utils.LoadingDialogUtil;
import com.umeng.analytics.MobclickAgent;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Author:Created by Ricky on 2017/10/19.
 * Description:
 */
public class ZatApplication extends Application {
    public static final  String SP_DATA_NAME = AppConfig.BASE_PACKAGE;
    private DbManager.DaoConfig mDaoConfig;
    protected LoadingDialogUtil mLoading;
    @Override
    public void onCreate() {
        super.onCreate();

        //友盟统计工具初始化
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        //SharedPreferences 初始化
        SPUtils.getInstance(getApplicationContext(),SP_DATA_NAME);
        // 初始化xutils
        x.Ext.init(this);
        // xutils 开启debug
        x.Ext.setDebug(true);
        // 数据库初始化
      //  getDaoConfig();

  /*      String ip = getAppMainPreferences().getString(PrefKeys.PREF_IP_KEY, "");
        if (ip.equals("")) {
            AppConfig.HOST_ADDRESS_YH = "http://" + AppConfig.DEFAULT_IP
                    + "/huzhou";
            AppConfig.HOST_ADDRESS_DA = "http://" + AppConfig.DEFAULT_IP
                    + "/huzhouArchives";
        } else {
            AppConfig.HOST_ADDRESS_YH = "http://" + ip + "/huzhou";
            AppConfig.HOST_ADDRESS_DA = "http://" + ip + "/huzhouArchives";
        }*/


        // 启动更新服务  发布时解除注释
	/*	Intent mIntent = new Intent(getApplicationContext(),
				UpgradeService.class);
		startService(mIntent);*/

    }


/*
    *//**
     * 返回应用主配置
     *//*
    public SharedPreferences getAppMainPreferences() {
        if (mAppMainPreferences == null) {
            mAppMainPreferences = this.getSharedPreferences(
                    AppConfig.BASE_PACKAGE, Context.MODE_PRIVATE);
        }
        return mAppMainPreferences;
    }*/

    public DbManager.DaoConfig getDaoConfig() {
        if (mDaoConfig == null) {
            mDaoConfig = new DbManager.DaoConfig();
            mDaoConfig.setDbName(AppConfig.DB_FILE_NAME);
            mDaoConfig.setDbVersion(AppConfig.DB_VERSION_CODE);
            mDaoConfig.setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    // 开启WAL, 对写入加速提升巨大
                    db.getDatabase().enableWriteAheadLogging();
                }
            });
        }
        return mDaoConfig;
    }

    public DbManager getDbManager() {
        return x.getDb(getDaoConfig());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stoptUploadService();
    }

    public void stoptUploadService() {
        stopService(new Intent(this, UpgradeService.class));
    }
}
