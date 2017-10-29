package com.safetys.zatgov;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import org.xutils.DbManager;
import org.xutils.DbManager.DaoConfig;
import org.xutils.x;

/**
 * Author:Created by Ricky on 2017/10/19.
 * Description:
 */
public class ZatApplication extends Application {

    private SharedPreferences mAppMainPreferences;
    private DbManager.DaoConfig mDaoConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化xutils
        x.Ext.init(this);
        // xutils 开启debug
        x.Ext.setDebug(true);
        // 数据库初始化
        getDaoConfig();

        String ip = getAppMainPreferences().getString(PrefKeys.PREF_IP_KEY, "");
        if (ip.equals("")) {
            AppConfig.HOST_ADDRESS_YH = "http://" + AppConfig.DEFAULT_IP
                    + "/huzhou";
            AppConfig.HOST_ADDRESS_DA = "http://" + AppConfig.DEFAULT_IP
                    + "/huzhouArchives";
        } else {
            AppConfig.HOST_ADDRESS_YH = "http://" + ip + "/huzhou";
            AppConfig.HOST_ADDRESS_DA = "http://" + ip + "/huzhouArchives";
        }

        //TODO
        // 启动更新服务  发布时解除注释
	/*	Intent mIntent = new Intent(getApplicationContext(),
				UpgradeService.class);
		startService(mIntent);*/

    }

    /**
     * 返回应用主配置
     */
    public SharedPreferences getAppMainPreferences() {
        if (mAppMainPreferences == null) {
            mAppMainPreferences = this.getSharedPreferences(
                    AppConfig.BASE_PACKAGE, Context.MODE_PRIVATE);
        }
        return mAppMainPreferences;
    }

    public DbManager.DaoConfig getDaoConfig() {
        if (mDaoConfig == null) {
            mDaoConfig = new DaoConfig();
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
