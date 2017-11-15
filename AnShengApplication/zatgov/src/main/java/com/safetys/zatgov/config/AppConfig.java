package com.safetys.zatgov.config;

import android.os.Environment;

import java.io.File;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
 */
public class AppConfig {

    /******************************** 接口地址begin ********************************************/
    /* 登录地址 */
    public static String URI_LOGIN = "/appUser/nosecuritycheck/login.xhtml";
    /* 修改密码 */
    public static String URI_CHANGE_PASSWORD = "/appUser/nosecuritycheck/changePassword.xhtml";
    /* 版本更新 */
    public static String URI_GET_VERSION_INFO = "/appEdition/nosecuritycheck/loadLastEdition.xhtml";
    /* 获取企业资讯列表 */
    public static String URI_GET_COMPANY_MESS_INFO = "/appArticle/nosecuritycheck/loadArticles.xhtml";
    /* 获取企业资讯 */
    public static String URI_GET_COMPANY_DEETAIL_INFO = "/appArticle/nosecuritycheck/loadArticle.xhtml";
    /* MSDS库 */
    public static String URI_GET_COMPANY_MSDS_INFO = "/appDangerousChemicals/nosecuritycheck/loadDangerousChemicals.xhtml";













    // http://121.41.101.87:7090 演示地址
    // http://192.168.2.154:8080 余峰地址
    //public static String DEFAULT_IP = "121.41.101.87:7090";
    public static String HEAD_IP = "http://";
    public static String DEFAULT_IP = "121.41.101.87:7090";
    public static String BASE_IP = HEAD_IP + AppConfig.DEFAULT_IP;


    /**
     * 主机地址--隐患
     */
    public static String HOST_ADDRESS_YH = BASE_IP + "/huzhou";
    /**
     *
     * 主机地址--档案
     */
    public static String HOST_ADDRESS_DA = BASE_IP + "/huzhouArchives";

    /**
     * 应用主包
     */
    public static final String BASE_PACKAGE = "com.safetys.zatgov";

    /**
     * 需要和移动应用维护中心的应用标识名称一致
     */
    public static final String APP_SHORT_NAME = "ywngovernment";
    /**
     * 数据库文件名称 ,本地操作全部保存在这个数据库
     */
    public static final String DB_FILE_NAME = "government_db";

    /**
     * 数据库版本号
     */
    public static final int DB_VERSION_CODE = 1;

    /**
     * 枚举数据插入数据库
     */
    public static final int ENUM_VERSION_CODE = 1;

    /**
     * SDCard 初始根目录
     */
    public static final String SDCARD_APP_ROOT = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + APP_SHORT_NAME;
    public static final String HOME_DB = "db";
    public static final String HOME_DOWNLOAD = "download";
    public static final String HOME_CACHE = "cache";
    /**
     * 初始化SDCard应用数据子目录
     *
     * @author safetys
     * @param homeName
     *            {@link #HOME_CACHE} or {@link #HOME_DB} or
     *            {@link #HOME_DOWNLOAD}
     */
    public static File buildPath(String homeName) {
        File base = buildAppHome();
        String path = base.getPath() + File.separator + homeName;
        File build = new File(path);
        if (!build.exists()) {
            build.mkdirs();
        }
        if (build.exists() && build.isFile()) {
            build.mkdirs();
        }
        return build;
    }
    // 初始化SDCard应用数据根目录
    public static File buildAppHome() {
        File base = new File(SDCARD_APP_ROOT);
        if (!base.exists()) {
            base.mkdir();
        }
        if (base.exists() && base.isFile()) {
            base.mkdir();
        }
        return base;
    }

}
