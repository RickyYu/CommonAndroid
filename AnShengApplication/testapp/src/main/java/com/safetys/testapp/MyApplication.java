package com.safetys.testapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.safetys.widget.common.SPUtils;

/**
 * Author:Created by Ricky on 2017/10/16.
 * Description:
 */
public class MyApplication extends Application {
    public static final  String SP_DATA_NAME = "test.sp";
    @Override
    public void onCreate() {
        super.onCreate();
        //SharedPreferences 初始化
        SPUtils.getInstance(getApplicationContext(),SP_DATA_NAME);
    }
}
