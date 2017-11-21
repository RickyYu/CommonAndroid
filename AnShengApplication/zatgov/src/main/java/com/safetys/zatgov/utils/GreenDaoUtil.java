package com.safetys.zatgov.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.safetys.greenrobot.greendao.gen.DaoMaster;
import com.safetys.greenrobot.greendao.gen.DaoSession;

/**
 * Author:Created by Ricky on 2017/11/21.
 * Description:GreenDao辅助类
 */
public class GreenDaoUtil {
    private static GreenDaoUtil util;
    private static DaoMaster daoMaster;
    private static DaoMaster.DevOpenHelper helper;
    private static SQLiteDatabase db;

    private GreenDaoUtil(Context context, String name) {
        helper = new DaoMaster.DevOpenHelper(context, name, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
    }

    public static void getInstance(Context context, String name) {
        if (util == null) {
            util = new GreenDaoUtil(context, name);
        }
    }
    public static DaoSession getDaoSession() {
        return daoMaster.newSession();
    }
    public static SQLiteDatabase getDB() {
        return db;
    }
}
