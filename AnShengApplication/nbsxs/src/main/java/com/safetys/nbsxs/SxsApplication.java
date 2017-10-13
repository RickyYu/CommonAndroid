package com.safetys.nbsxs;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

/**
 * Author:Created by Ricky on 2017/10/9.
 * Email:584182977@qq.com
 * Description:
 */
public class SxsApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);

    }
}
