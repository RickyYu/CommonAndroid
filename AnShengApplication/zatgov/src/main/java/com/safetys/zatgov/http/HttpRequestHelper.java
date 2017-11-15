package com.safetys.zatgov.http;

import android.content.Context;

import com.safetys.widget.common.SPUtils;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.PrefKeys;
import com.safetys.zatgov.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:网络通讯辅助类
 */
public class HttpRequestHelper {
    private static HttpRequestHelper mHelper = new HttpRequestHelper();

    private HttpRequestHelper() {
    }

    public static HttpRequestHelper getInstance() {
        if (mHelper == null) {
            mHelper = new HttpRequestHelper();
        }
        return mHelper;
    }

    /**
     * 登录接口
     *
     * @param accountId
     *            登录账号ID
     * @param password
     *            登录密码
     * @param mCallback
     *            回调接口
     */
    public void login(String accountId, String password, onNetCallback mCallback) {
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("username", accountId);
        mDatas.put("password", password);
        mDatas.put("type", 2);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                + AppConfig.URI_LOGIN, null, false, mDatas, 0, mCallback);
    }

    /**
     * 获取信息咨询列表
     *
     * @param mContext
     * @param newPass
     *            新密码
     * @param oldPass
     *            老密码
     * @param requestCode
     * @param mCallback
     */
    public void changePassword(Context mContext, String newPass,
                               String oldPass, int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("newPasswordUnEncrypted", newPass);
        mDatas.put("password", oldPass);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_DA
                        + AppConfig.URI_CHANGE_PASSWORD, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取版本信息
     *
     * @param mContext
     * @param requestCode
     * @param mCallback
     */
    public void getVersionInfo(Context mContext, int requestCode,
                               onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("edition.type", 2);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_VERSION_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取信息咨询列表
     *
     * @param mContext
     * @param code
     *            企业政策（QYZC）、法律法规(FLFG)、技术标准(JSBZ)、通知公告(TZGG)、安全知识(AQZZ)、安监要闻(
     *            AJYW)
     * @param pageIndex
     *            第几页数据
     * @param requestCode
     * @param mCallback
     */
    public void getReadList(Context mContext, String code, int pageIndex,
                            int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("code", code);
        mDatas.put("pagination.pageSize", 10);
        mDatas.put("pagination.itemCount", pageIndex);
        mDatas.put("pagination.totalCount", 0);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_DA
                        + AppConfig.URI_GET_COMPANY_MESS_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     *  获取信息咨询详细信息
     企业政策（QYZC）、法律法规(FLFG)、技术标准(JSBZ)、通知公告(TZGG)、安全知识(AQZZ)、安监要闻(
     AJYW)
     * @param mContext
     * @param id
     * @param mCallback
     */
    public void getReadDetail(Context mContext, String id,
                              onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("article.id", id);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_DA
                        + AppConfig.URI_GET_COMPANY_DEETAIL_INFO, key, false, mDatas,
                0, mCallback);
    }

    /**
     * MSDS库
     *
     * @param mContext
     * @param pageIndex
     *            第几页数据
     * @param requestCode
     * @param mCallback
     */
    public void getMsds(Context mContext, int pageIndex, int totalCount,
                        String selectWord, int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("pagination.pageSize", 10);
        mDatas.put("pagination.itemCount", pageIndex);
        mDatas.put("pagination.totalCount", totalCount);
        mDatas.put("chemicals.chineseName", selectWord);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_DA
                        + AppConfig.URI_GET_COMPANY_MSDS_INFO, key, false, mDatas,
                requestCode, mCallback);
    }
}
