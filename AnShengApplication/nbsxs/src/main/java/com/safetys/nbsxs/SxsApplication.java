package com.safetys.nbsxs;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.safetys.nbsxs.common.AppConfig;
import com.safetys.nbsxs.service.UpgradeService;
import com.safetys.widget.common.SPUtils;
import com.umeng.analytics.MobclickAgent;

import org.xutils.DbManager;
import org.xutils.DbManager.DaoConfig;
import org.xutils.x;

/**
 * Author:Created by Ricky on 2017/10/9.
 * Description:
 */
public class SxsApplication extends Application{
    public static final  String SP_DATA_NAME = AppConfig.BASE_PACKAGE;
    private SharedPreferences mAppMainPreferences;
    private DbManager.DaoConfig mDaoConfig;

    private boolean needFefresh = false;//是否需要刷新界面
    @Override
    public void onCreate() {
        super.onCreate();
        //友盟统计工具初始化
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        //SharedPreferences 初始化
        SPUtils.getInstance(getApplicationContext(),SP_DATA_NAME);
        Logger.addLogAdapter(new AndroidLogAdapter());
        x.Ext.init(this);
        //xutils 开启debug
        x.Ext.setDebug(true);
        //数据库初始化
//		getDaoConfig();
        //启动更新服务
        Intent mIntent = new Intent(getApplicationContext(), UpgradeService.class);
        startService(mIntent);
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

    /**
     * @return xutils数据库配置
     */
    public DbManager.DaoConfig getDaoConfig(){
        if(mDaoConfig == null){
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

    /**
     * @return xutils数据库主要管理工具
     */
    public DbManager getDbManager(){
        return x.getDb(getDaoConfig());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopUploadService();
    }
    public void stopUploadService(){
        stopService(new Intent(this, UpgradeService.class));
    }

    public boolean isNeedFefresh() {
        return needFefresh;
    }

    public void setNeedFefresh(boolean needFefresh) {
        this.needFefresh = needFefresh;
    }
}
