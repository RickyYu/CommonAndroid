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
    /* 网格员检查表查询 */
    public static String URI_GET_WGY_CHECKLIST = "/apphztemplatechecktable/nosecuritycheck/list.xhtml";
    /* 删除检查表 */
    public static String URI_DELETE_WGY_CHECKLIST = "/apphztemplatechecktable/nosecuritycheck/delete.xhtml";
    /* 网格员检查表详情 */
    public static String URI_GET_WGY_CHECKLIST_ITEM = "/apphztemplatechecktable/nosecuritycheck/input.xhtml";
    /* 新增修改检查表 */
    public static String URI_SUBMIT_WGY_CHECKLIST = "/apphztemplatechecktable/nosecuritycheck/save.xhtml";
    /* 网格员检查表新增以及列表 */
    public static String URI_GET_WGY_CHECKLIST_ONE = "/apphztemplatecheckcitytable/nosecuritycheck/list.xhtml";
    /* 网格员检查表新增二级检查项 */
    public static String URI_GET_WGY_CHECKLIST_TWO = "/apphztemplatecheckcitytable/nosecuritycheck/secondaryMenu.xhtml";
    /* 企业列表 */
    public static String URI_GET_COMPANY_LIST_INFO = "/appCompany/nosecuritycheck/loadCompanys.xhtml";
    /* 获取行业类型表 */
    public static String URI_GET_TRADE_TYPES = "/appCompany/nosecuritycheck/getTradeTypes.xhtml";
    /* 获取复查列表 */
    public static String URI_GET_REVIEW_LIST = "/appProduceLocaleNote/nosecuritycheck/loadProduceCallBacks.xhtml";
    /* 获取隐患描述列表 */
    public static String URI_GET_HIDDEN_DES_LIST = "/appProduceLocaleNote/nosecuritycheck/loadHzHiddenTroubles.xhtml";
    /* 新增复查信息 */
    public static String URI_ADD_REVIEW_INFO = "/appProduceLocaleNote/nosecuritycheck/createProduceCallBack.xhtml";
    /* 重大隐患详细信息查询 */
    public static String URI_GET_COMPANY_MAJOR_DETAIL_INFO = "/appDanger/nosecuritycheck/loadDanger.xhtml";
    /* 政府一般隐患信息获取 */
    public static String URI_GET_MAINCHEAK_INFO_NOW = "/appCheckRecord/nosecuritycheck/loadNomalDanger.xhtml";
    /* 获取复查详细信息 */
    public static String URI_GET_REVIEW_INFO = "/appProduceLocaleNote/nosecuritycheck/loadProduceCallBack.xhtml";
    /* 一般隐患提交22222 */
    public static String URI_SUBMIT_GENERALCHEAK_INFO_NOW = "/appCheckRecord/nosecuritycheck/createHiddenTrouble.xhtml";
    /* 重大隐患历史纪录修改 */
    public static String URI_SUBMIT_MAJOR_HIS_INFO = "/appDanger/nosecuritycheck/updateDanger.xhtml";
    /* 获取未处罚列表 */
    public static String URI_GET_UNPUNISHMENT_LIST = "/appPunishment/nosecuritycheck/loadNotPunishmentList.xhtml";
    /* 获取处罚列表 */
    public static String URI_GET_PUNISHMENT_LIST = "/appPunishment/nosecuritycheck/loadPunishmentList.xhtml";
    /* 获取企业基本信息 */
    public static String URI_GET_COMPANY_INFO = "/appCompany/nosecuritycheck/loadCompanyInfo.xhtml";
    /* 新增处罚 */
    public static String URI_SUBMIT_PUNISHMENT_INFO = "/appPunishment/nosecuritycheck/createPunishment.xhtml";
    /* 获取历史复查记录 */
    public static String URI_GET_REVIEW_HISTORY_LIST = "/appProduceLocaleNote/nosecuritycheck/loadHistoryProduceCallBacks.xhtml";
    /* 初查记录 */
    public static String URI_GET_PRE_IVS_RECORD = "/appCheckRecord/nosecuritycheck/loadCheckRecord.xhtml";
    /* 获取处罚信息 */
    public static String URI_GET_PUNISHMENT_INFO = "/appPunishment/nosecuritycheck/loadPunishment.xhtml";
    /* 企业信息 */
    public static String URI_GET_COMPANY_DETAIL_INFO = "/appCheckRecord/nosecuritycheck/loadCompanyGrid.xhtml";
    /* 获取首页资讯政府 */
    public static String URI_GET_GOV_INFO = "/appDanger/nosecuritycheck/loadCountGov.xhtml";
    /* 获取首页资讯企业 */
    public static String URI_GET_COM_INFO = "/appDanger/nosecuritycheck/loadCountCom.xhtml";
    /* 修改企业基本信息 */
    public static String URI_COMPANY_CHANGE_INFO = "/appCompany/nosecuritycheck/updateCompany.xhtml";
    /* 获取企业坐标 */
    public static String URI_COMPANY_LOCATION_INFO = "/appCompany/nosecuritycheck/getPoint.xhtml";
    /* 发送企业坐标 */
    public static String URI_SET_COMPANY_LOCATION_INFO = "/appCompany/nosecuritycheck/savePoint.xhtml";
    /* 企业未整改隐患列表 */
    public static String URI_SUBMIT_COMPANY_YH_LIST_INFO = "/appDanger/nosecuritycheck/loadVDangers.xhtml";
    /* 一般隐患信息查询 */
    public static String URI_GET_COMPANY_GENERAL_ITEM_INFO = "/appNomalDanger/nosecuritycheck/loadNomalDanger.xhtml";
    /* 一般隐患修改 */
    public static String URI_SUBMIT_GENERALCHECK_INFO = "/appNomalDanger/nosecuritycheck/updateNomalDanger.xhtml";
    /* 新增检查记录 */
    public static String URI_SUBMIT_CHECK_INFO = "/appCheckRecord/nosecuritycheck/createCheckRecord.xhtml";
    /* 删除一般隐患 */
    public static String DELETE_GENERAL_ID = "/appCheckRecord/nosecuritycheck/deleteHiddenTrouble.xhtml";
    /* 删除重大隐患 */
    public static String DELETE_MAJOR_ID = "/appDanger/nosecuritycheck/deleteDanger.xhtml";
    /* 检查记录隐患列表 */
    public static String URI_SUBMIT_CHECK_YH_LIST_INFO = "/appDanger/nosecuritycheck/loadProDangers.xhtml";
    /* 检查记录新接口 */
    public static String URI_GET_GOV_COMPANY_CHECK_LIST_INFO = "/appCheckRecord/nosecuritycheck/loadGovCheckRecordList.xhtml";
    /* 删除检查记录 */
    public static String URI_DELETE_CHECK_INFO = "/appCheckRecord/nosecuritycheck/deleteCheckRecord.xhtml";
    /* 检查记录详情 */
    public static String URI_GET_COMPANY_CHECK_DETAIL_INFO = "/appCheckRecord/nosecuritycheck/loadCheckRecord.xhtml";
    /* 重大隐患提交 */
    public static String URI_SUBMIT_MAJOR_INFO = "/appDanger/nosecuritycheck/createDanger.xhtml";





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
