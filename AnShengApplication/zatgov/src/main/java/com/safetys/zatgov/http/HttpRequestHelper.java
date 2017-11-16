package com.safetys.zatgov.http;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.SPUtils;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.PrefKeys;
import com.safetys.zatgov.utils.FileUtil;
import com.safetys.zatgov.utils.HttpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 获取检查表列表
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getWgyCheckList(Context mContext, int totalCount,
                                int pageIndex, String selectWord, int requestCode,
                                onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("pagination.pageSize", 10);
        mDatas.put("pagination.itemCount", pageIndex);
        mDatas.put("pagination.totalCount", totalCount);
        if (selectWord != null) {
            mDatas.put("entity.title", selectWord);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_WGY_CHECKLIST, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 删除检查表
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void deleteChecklist(Context mContext, int id,
                                int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("entity.ids", id);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_DELETE_WGY_CHECKLIST, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取检查表详情
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getSafetyCheckInfo(Context mContext, int id, int requestCode,
                                   onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("entity.id", id);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_WGY_CHECKLIST_ITEM, key, false, mDatas,
                requestCode, mCallback);
    }
    /**
     * 添加修改检查表
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void addChecklistchange(Context mContext, int id, String title,
                                   String remark, String ids, int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        if (id != -99) {
            mDatas.put("entity.id", id);
        }
        mDatas.put("entity.title", title);
        mDatas.put("entity.remark", remark);
        mDatas.put("entity.idlist", ids);

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_SUBMIT_WGY_CHECKLIST, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 新增检查表获取一级列表
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getChecklistone(Context mContext, String selectWord,
                                int totalCount, int pageIndex, int pagesize, int requestCode,
                                onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("pagination.pageSize", pagesize);
        mDatas.put("pagination.itemCount", pageIndex);
        mDatas.put("pagination.totalCount", totalCount);
        if (selectWord != null) {
            mDatas.put("templateCheckCityTable.title", selectWord);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_WGY_CHECKLIST_ONE, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 新增检查表获取er级列表检查项
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getChecklisttwo(Context mContext, int id, String selectWord,
                                int totalCount, int pageIndex, int pagesize, int requestCode,
                                onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("templateCheckCityTable.id", id);
        // mDatas.put("pagination.pageSize", pagesize);
        // mDatas.put("pagination.itemCount", pageIndex);
        // mDatas.put("pagination.totalCount", totalCount);
        if (selectWord != null) {
            mDatas.put("hzTemplateCheckCityInfo.content", selectWord);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_WGY_CHECKLIST_TWO, key, false, mDatas,
                requestCode, mCallback);
    }
    /**
     * 获取企业列表
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getCompanyListInfo(Context mContext, boolean isHistory,
                                   String tradeType, String seekType, String bigCode, String oneCode,
                                   String twoCode, String threeCode, String companyName,
                                   int totalCount, int pageIndex, String orderProperty,
                                   boolean orderType, int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("pagination.pageSize", 10);
        mDatas.put("pagination.itemCount", pageIndex);
        mDatas.put("pagination.totalCount", totalCount);

        if (isHistory) {
            mDatas.put("company.isHistory", isHistory);
        }
        if (companyName != null) {
            mDatas.put("company.companyName", companyName);
        }
        if (orderProperty != null) {
            mDatas.put("company.orderProperty", orderProperty);
            mDatas.put("company.orderType", orderType);
        }
        if (bigCode != "") {
            mDatas.put("company.isEnterprise", bigCode);
        }
        if (oneCode != "") {
            mDatas.put("company.firstArea", oneCode);
        }
        if (twoCode != "") {
            mDatas.put("company.secondArea", twoCode);
        }
        if (threeCode != "") {
            mDatas.put("company.thirdArea", threeCode);
        }
        if (seekType != "") {
            mDatas.put("company.seekType", seekType);
        }

        if (tradeType != "") {
            mDatas.put("company.tradeTypes", tradeType);
        }

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_COMPANY_LIST_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取行业类型表
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getCompanyTradeTypes(Context mContext, int requestCode,
                                     onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_TRADE_TYPES, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取复查列表
     *
     * @param mContext
     * @param pageIndex
     *            第几条数据
     * @param searchData
     *            搜索数据
     * @param requestCode
     * @param mCallback
     */
    public void getReviewList(Context mContext, int pageIndex, int totalCount,
                              String searchData, int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("pagination.pageSize", 10);// 页大小
        mDatas.put("pagination.itemCount", pageIndex);
        mDatas.put("pagination.totalCount", totalCount);
        if (searchData != null) {
            // mDatas.put("produceCallback.hiddenState", searchData);
            mDatas.put("companyName", searchData);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_REVIEW_LIST, key, false, mDatas,
                requestCode, mCallback);
    }
    /**
     * 获取隐患描述列表
     *
     * @param mContext
     * @param companyId
     * @param requestCode
     * @param mCallback
     */
    public void getHiddenDesList(Context mContext, String companyId,
                                 boolean isCurrent, int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("company.id", companyId);
        mDatas.put("company.current", isCurrent);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_HIDDEN_DES_LIST, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 新增复查信息
     *
     * @param mContext
     * @param hiddenId
     *            检查表id
     * @param isCallBack
     *            是否生成复查表
     * @param callbackTime
     *            复查时间
     * @param callBackNum
     *            复查编号
     * @param executer
     *            复查人
     * @param yhzgDes
     *            隐患整改情况
     * @param uploadFiles
     *            新图片
     *            老图片集合字符串 格式(1021,1022,1023,)
     * @param requestCode
     * @param mCallback
     */
    public void addReviewInfo(Context mContext, int hiddenId,
                              boolean isCallBack, String callbackTime, String callBackNum,
                              String executer, String yhzgDes, List<File> uploadFiles,
                              String hiddenLevel, String zgState, int requestCode,
                              onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("produceCallback.isCallBack", isCallBack);
        mDatas.put("produceCallback.callbackTime", callbackTime);
        mDatas.put("produceCallback.callBackNum", callBackNum);
        mDatas.put("produceCallback.executer", executer);
        mDatas.put("produceCallback.hiddenState", yhzgDes);
        // mDatas.put("result.list", JSON.toJSONString(hiddenDesList));

        if (hiddenLevel.equals("1")) {// 重大隐患
            mDatas.put("produceCallback.danger.id", hiddenId);
            mDatas.put("produceCallback.danger.govCheck", zgState);// 0未查看,1查看未通过。2已通过
            mDatas.put("produceCallback.danger.govCheckContent", yhzgDes);
        } else {// 一般隐患
            mDatas.put("produceCallback.hiddenTrouble.id", hiddenId);
            mDatas.put("produceCallback.hiddenTrouble.govCheck", zgState);// 0未查看,1查看未通过。2已通过
            mDatas.put("produceCallback.hiddenTrouble.govCheckContent", yhzgDes);
        }

        if (uploadFiles != null && uploadFiles.size() != 0) {
            mDatas.put("file", uploadFiles);
        }

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_ADD_REVIEW_INFO, key, uploadFiles != null,
                mDatas, requestCode, mCallback);
    }

    /**
     * 获取重大隐患详细信息
     */
    public void getMajorDetailInfo(Context mContext, String id,
                                   int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();

        long id2 = (long) SPUtils.getData(PrefKeys.PREF_COMPANY_ID_KEY,
                0l);

        mDatas.put("danger.id", id);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_COMPANY_MAJOR_DETAIL_INFO, key, false,
                mDatas, requestCode, mCallback);
    }

    /**
     * 政府获取一般隐患详细信息
     */
    public void getGenealItemInfo(Context mContext, String id2,
                                  int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("nomalDanger.id", id2);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_MAINCHEAK_INFO_NOW,
                // AppConfig.URI_GET_COMPANY_GENERAL_ITEM_INFO,
                key, false, mDatas, requestCode, mCallback);
    }
    /**
     * 获取复查信息
     *
     * @param mContext
     * @param reviewId
     *            复查id
     * @param requestCode
     * @param mCallback
     */
    public void getReviewInfo(Context mContext, long reviewId, int requestCode,
                              onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("produceCallback.id", reviewId);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_REVIEW_INFO, key, false, mDatas,
                requestCode, mCallback);
    }
    /**
     * 一般隐患提交
     *
     * @param mContext
     * @param contact
     *            联系人
     * @param telephone
     *            联系电话
     *            手机号
     * @param type
     *            隐患类型
     * @param describe
     *            隐患描述
     * @param zgTime
     *            整改时间
     *            图片
     * @param requestCode
     * @param mCallback
     */
    public void submitGenealTrouble(Context mContext, String yhId,
                                    String checkInfoId, String compayId, String noteId, String contact,
                                    String telephone, String type, String describe, String zgTime,
                                    List<File> listpic, String lrtime, String checkid,
                                    List<String> list_detele, boolean isclean, int requestCode,
                                    onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        long id = (long)SPUtils.getData(PrefKeys.PREF_COMPANY_ID_KEY,
                0l);
        Map<String, Object> mDatas = new HashMap<String, Object>();
        if (yhId != null) {
            mDatas.put("nomalDanger.id", yhId);
        }
        mDatas.put("nomalDanger.hzCompany.id", compayId);
        mDatas.put("produceLocaleNote.id", noteId);
        mDatas.put("nomalDanger.linkMan", contact);
        mDatas.put("nomalDanger.linkManPhone", telephone);
        mDatas.put("nomalDanger.danger", true);
        mDatas.put("nomalDanger.type", type);
        mDatas.put("nomalDanger.content", describe);
        mDatas.put("nomalDanger.governDate", zgTime);
        mDatas.put("nomalDanger.repaired", false);
        if (checkInfoId != null) {
            mDatas.put("nomalDanger.checkInfoId", checkInfoId);
        }
        if (checkid != null) {

        }

        if (list_detele != null) {
            mDatas.put("deletedIds", list_detele);
        }
        if (listpic != null && listpic.size() > 0) {
            mDatas.put("file", listpic);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_SUBMIT_GENERALCHEAK_INFO_NOW, key,
                listpic != null && listpic.size() > 0, mDatas, requestCode,
                mCallback);
    }

    /**
     * 重大隐患历史纪录修改
     *
     * @param mContext
     * @param hasisProject
     *            市级以上重点工程
     * @param fristArea
     *            市
     * @param secondArea
     *            县
     * @param threeArea
     *            街道
     * @param linkMan
     *            联系人
     * @param linkTel
     *            联系电话
     * @param linkMobile
     *            手机号
     * @param description
     *            隐患基本情况
     * @param govCoordination
     *            是否需要政府协调
     * @param partStopProduct
     *            是否需局部停产停业
     * @param fullStopProduct
     *            是否需要全部停产停业
     * @param target
     *            落实治理目标
     * @param resource
     *            落实治理机构人员
     * @param safetyMethod
     *            落实安全促使及应急预案
     * @param goods
     *            落实治理经费物资
     * @param finishDate
     *            计划完成治理时间
     * @param governMoney
     *            落实治理经费
     * @param chargePerson
     *            单位责任人
     * @param fillDate
     *            录入时间
     * @param fillMan
     *            填报人
     *            现场照片
     * @param requestCode
     * @param mCallback
     */
    public void submitMajorChange(Context mContext, String delete, String id2,
                                  String noteid, boolean hasisProject, String address,
                                  String fristArea, String secondArea, String threeArea,
                                  String linkMan, String linkTel, String linkMobile,
                                  String description, boolean govCoordination,
                                  boolean partStopProduct, boolean fullStopProduct, boolean target,
                                  boolean resource, boolean safetyMethod, boolean goods,
                                  String finishDate, String governMoney, String chargePerson,
                                  String fillDate, String fillMan, List<File> listpic,
                                  List<String> list_delete, String checkid, int requestCode,
                                  onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        long id = (long)SPUtils.getData(PrefKeys.PREF_COMPANY_ID_KEY,
                0l);
        Map<String, Object> mDatas = new HashMap<String, Object>();
        // mDatas.put("deletePhoto", delete);
        mDatas.put("danger.hzCompany.id", id);
        mDatas.put("produceLocaleNote.id", noteid);
        mDatas.put("danger.id", id2);
        mDatas.put("danger.emphasisProject", hasisProject);
        mDatas.put("danger.dangerAdd", address);
        mDatas.put("danger.firstArea", fristArea);
        mDatas.put("danger.secondArea", secondArea);
        mDatas.put("danger.thirdArea", threeArea);
        mDatas.put("danger.linkMan", linkMan);
        mDatas.put("danger.linkTel", linkTel);
        mDatas.put("danger.linkMobile", linkMobile);
        mDatas.put("danger.description", description);
        mDatas.put("danger.govCoordination", govCoordination);
        mDatas.put("danger.partStopProduct", partStopProduct);
        mDatas.put("danger.fullStopProduct", fullStopProduct);
        mDatas.put("danger.target", target);
        mDatas.put("danger.resource", resource);
        mDatas.put("danger.safetyMethod", safetyMethod);
        mDatas.put("danger.goods", goods);
        mDatas.put("danger.finishDate", finishDate);
        mDatas.put("danger.governMoney", governMoney);
        mDatas.put("danger.chargePerson", chargePerson);
        mDatas.put("danger.fillDate", fillDate);
        mDatas.put("danger.fillMan", fillMan);
        if (checkid != null) {
            // mDatas.put("danger.checkInfoId", checkid);
        }

        if (list_delete != null) {
            mDatas.put("deletedIds", list_delete);
        }
        if (listpic != null && listpic.size() != 0) {
            mDatas.put("file", listpic);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                + AppConfig.URI_SUBMIT_MAJOR_HIS_INFO, key, listpic != null
                && listpic.size() != 0, mDatas, requestCode, mCallback);

    }
    /**
     * 获取未处罚信息列表
     *
     * @param mContext
     * @param pageindex
     * @param requestCode
     * @param mCallback
     */
    public void getUnPunishmentList(Context mContext, int pageindex,
                                    int totalCount, String selectWord, int requestCode,
                                    onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("pagination.pageSize", 10);
        mDatas.put("pagination.itemCount", pageindex);
        mDatas.put("pagination.totalCount", totalCount);
        if (selectWord != null) {
            mDatas.put("companyName", selectWord);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_UNPUNISHMENT_LIST, key, false, mDatas,
                requestCode, mCallback);

    }
    /**
     * 获取处罚信息列表
     *
     * @param mContext
     * @param pageindex
     * @param requestCode
     * @param mCallback
     */
    public void getPunishmentList(Context mContext, int pageindex,
                                  int totalCount, String selectWord, int requestCode,
                                  onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("pagination.pageSize", 10);
        mDatas.put("pagination.itemCount", pageindex);
        mDatas.put("pagination.totalCount", totalCount);
        if (selectWord != null) {
            mDatas.put("companyName", selectWord);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_PUNISHMENT_LIST, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取企业信息
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getCompanyInfo(Context mContext, String id, int requestCode,
                               onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();

        mDatas.put("company.id", id);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_COMPANY_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 新增处罚信息
     *
     * @param mContext
     * @param id
     * @param requestCode
     * @param mCallback
     */
    public void submitPunishmentInfo(Context mContext, String id, String time,
                                     String unit, String cause, String content, String remark,
                                     String type, int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("punishment.hzProduceLocaleNote.id", id);
        mDatas.put("punishment.punishmentUnit", unit);
        mDatas.put("punishment.punishmentCause", cause);
        mDatas.put("punishment.punishmentType", type);
        mDatas.put("punishment.punishmentTime", time);
        mDatas.put("punishment.content", content);
        mDatas.put("punishment.remark", remark);
        mDatas.put("punishState", true);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_SUBMIT_PUNISHMENT_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取历史复查记录
     *
     * @param mContext
     *            检查记录ID
     * @param mCallback
     */
    public void getReviewHistoryList(Context mContext, String checkId,
                                     int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("produceLocaleNote.id", checkId);// 页大小
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_REVIEW_HISTORY_LIST, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取检查记录详情
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getPreIvsRecord(Context mContext, String id, int requestCode,
                                onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("produceLocaleNote.id", id);
        // TODO
        // mDatas.put("typeId", 0);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_PRE_IVS_RECORD, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取处罚信息
     *
     * @param mContext
     * @param id
     * @param requestCode
     * @param mCallback
     */
    public void getPunishmentInfo(Context mContext, int id, int requestCode,
                                  onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("punishment.id", id);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_PUNISHMENT_INFO, key, false, mDatas,
                requestCode, mCallback);
    }
    /**
     * 获取企业详细信息
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getCompanyItemInfo(Context mContext, String id,
                                   int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("produceLocaleNote.hzCompany.id", id);

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_COMPANY_DETAIL_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取企业列表坐标
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getCompanyLocationListInfo(Context mContext,
                                           String companyName, int totalCount, int pageIndex, int requestCode,
                                           onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("pagination.pageSize", 10);
        mDatas.put("pagination.itemCount", pageIndex);
        mDatas.put("pagination.totalCount", totalCount);
        if (companyName != null) {
            mDatas.put("company.companyName", companyName);
        }

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_DA
                        + AppConfig.URI_GET_COMPANY_LIST_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 政府首页统计查询company
     *
     * @param mContext
     *            第几页数据
     * @param requestCode
     * @param mCallback
     */
    public void getComCount(Context mContext, int requestCode,
                            onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_COM_INFO, key, false, null, requestCode,
                mCallback);
    }

    /**
     * 政府首页统计查询政府
     *
     * @param mContext
     *            第几页数据
     * @param requestCode
     * @param mCallback
     */
    public void getGovCount(Context mContext, int requestCode,
                            onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_GOV_INFO, key, false, null, requestCode,
                mCallback);
    }
    /**
     * 获取企业坐标
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getLocationInfo(Context mContext, String id, int requestCode,
                                onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();

        mDatas.put("company.id", id);

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_DA
                        + AppConfig.URI_COMPANY_LOCATION_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 保存企业坐标
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void setLocationInfo(Context mContext, String id, int requestCode,
                                String x, String y, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();

        mDatas.put("company.id", id);
        mDatas.put("point.x", x);
        mDatas.put("point.y", y);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_DA
                        + AppConfig.URI_SET_COMPANY_LOCATION_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取未整改隐患列表
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getNoProblemList(Context mContext, String id,
                                 boolean isRepaired, String sourceType, boolean isAll,
                                 int pageIndex, int totalCount, String searchData, int requestCode,
                                 onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("vDanger.companyId", id);
        if (isAll) {
            mDatas.put("vDanger.repaired", isRepaired);
        }

        if (sourceType != null) {
            mDatas.put("vDanger.sourceType", sourceType);
        }

        mDatas.put("pagination.pageSize", 10);// 页大小
        mDatas.put("pagination.itemCount", pageIndex);
        mDatas.put("pagination.totalCount", totalCount);
        if (searchData != null) {
            mDatas.put("vDanger.description", searchData);
        }

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_SUBMIT_COMPANY_YH_LIST_INFO, key, false,
                mDatas, requestCode, mCallback);
    }

    /**
     * 获取一般隐患详细信息
     */
    public void getCompanyGenealItemInfo(Context mContext, String hiddenId,
                                         int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("nomalDanger.id", hiddenId);
        mDatas.put("nomalDanger.gov", true);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_COMPANY_GENERAL_ITEM_INFO, key, false,
                mDatas, requestCode, mCallback);
    }

    /**
     * 一般隐患整改
     *
     * @param mContext
     * @param contact
     *            联系人
     * @param telephone
     *            联系电话
     * @param phoneNum
     *            手机号
     * @param type
     *            隐患类型
     * @param describe
     *            隐患描述
     *            整改时间
     * @param jhzgTime
     *            计划整改时间
     * @param photo
     *            图片
     * @param requestCode
     * @param mCallback
     */
    public void submitGenealCheck(Context mContext, boolean isChecked,
                                  Boolean isRepaired, String delete, String id2, String contact,
                                  String telephone, String phoneNum, String type, String describe,
                                  String jhzgTime, File photo, String zgtime, String bz,
                                  int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        SPUtils.getData(PrefKeys.PREF_COMPANY_ID_KEY,
                0);
        long id = (long)SPUtils.getData(PrefKeys.PREF_COMPANY_ID_KEY,
                0l);

        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("deletePhoto", delete);
        mDatas.put("nomalDanger.companyPassId", id);
        mDatas.put("nomalDanger.id", id2);
        mDatas.put("nomalDanger.linkMan", contact);
        mDatas.put("nomalDanger.linkTel", telephone);
        mDatas.put("nomalDanger.linkMobile", phoneNum);
        mDatas.put("nomalDanger.danger", true);
        mDatas.put("nomalDanger.type", type);
        mDatas.put("nomalDanger.description", describe);
        mDatas.put("nomalDanger.repaired", isRepaired);
        mDatas.put("nomalDanger.completedDate", jhzgTime);
        mDatas.put("nomalDanger.rectificationPlanTime", zgtime);
        mDatas.put("nomalDanger.remarks", bz);
        mDatas.put("nomalDanger.check", isChecked);
        if (photo != null) {
            mDatas.put("nomalDanger.file", FileUtil.getCompressFile(photo));
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_SUBMIT_GENERALCHECK_INFO, key, photo != null,
                mDatas, requestCode, mCallback);

    }

    /**
     * 新增检查记录
     *
     * @param mContext
     * @param time
     *            检查时间
     * @param cs
     *            检查场所
     * @param phone
     *            联系方式
     *            参与检查人
     * @param user
     *            检查人/记录人
     * @param unit
     *            执法单位
     *            现场检查记录
     *            附件
     * @param mCallback
     */
    public void submitCheck(Context mContext, boolean needPunish, String id,
                            String time, String cs, String phone, List list, String user,
                            String unit, List<File> listpic, Boolean cb, String zgtime,
                            String now, String checkid, List<HyCheckItemInfo> listHy,
                            int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("produceLocaleNote.hzCompany.id", id);
        mDatas.put("produceLocaleNote.checkTimeBegin", time);
        mDatas.put("produceLocaleNote.checkGround", cs);
        mDatas.put("produceLocaleNote.fdDelegateLink", phone);
        mDatas.put("produceLocaleNote.needPunish", needPunish);
        if (list != null && !list.isEmpty()) {
            mDatas.put("gridIds", JSON.toJSONString(list));
        }
        mDatas.put("produceLocaleNote.noter", user);
        mDatas.put("produceLocaleNote.executeUnit", unit);
        mDatas.put("hzProduceCleanUp.cleanUpTimeLimit", zgtime);
        if (now != null) {
            mDatas.put("produceLocaleNote.content", now);
        }

        if (!checkid.equals("-1")) {
            mDatas.put("hzTemplateCheckTable.id", checkid);
        }
        if (listHy != null) {
            mDatas.put("hzCompanyCheckTableInfosJson",
                    JSON.toJSONString(listHy));
        }

        if (listpic != null && listpic.size() > 0) {
            mDatas.put("file", listpic);
        }

        mDatas.put("produceLocaleNote.sendCleanUp", cb);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                + AppConfig.URI_SUBMIT_CHECK_INFO, key, listpic != null
                && listpic.size() > 0, mDatas, requestCode, mCallback);
    }
    /**
     * 删除一般隐患
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void deleteGeneralInfo(Context mContext, String id, int requestCode,
                                  onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("nomalDanger.id", id);

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.DELETE_GENERAL_ID, key, false, mDatas, requestCode,
                mCallback);
    }

    /**
     * 删除重大隐患
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void deleteMajorInfo(Context mContext, String id, int requestCode,
                                onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("dangerIds", id);

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.DELETE_MAJOR_ID, key, false, mDatas, requestCode,
                mCallback);
    }

    /**
     * 获取检查记录隐患列表
     *
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getCheckYhListInfo(Context mContext, String id,
                                   int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("produceLocaleNote.id", id);

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_SUBMIT_CHECK_YH_LIST_INFO, key, false, mDatas,
                requestCode, mCallback);
    }


    /**
     * 获取检查记录新接口
     *
     * @param mContext
     * @param totalCount
     * @param id
     * @param code
     *            是否整改
     * @param checkGround
     *            检查场所
     * @param pageIndex
     * @param selectWord
     * @param requestCode
     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getCheckRecordList(Context mContext, int totalCount, String id,
                                   String code, String checkGround, int pageIndex, String selectWord,
                                   int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("pagination.pageSize", 10);
        mDatas.put("pagination.itemCount", pageIndex);
        mDatas.put("pagination.totalCount", totalCount);
        mDatas.put("produceLocaleNote.hzCompany.id", id);
        // TODO
        mDatas.put("typeId", 0);
        if (selectWord != null) {
            mDatas.put("produceLocaleNote.content", selectWord);
        }
        if (code != null) {
            mDatas.put("produceLocaleNote.appCode", code);
        }
        if (checkGround != null) {
            mDatas.put("produceLocaleNote.checkGround", checkGround);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_GOV_COMPANY_CHECK_LIST_INFO, key, false,
                mDatas, requestCode, mCallback);
    }

    /**
     * 删除检查记录
     *
     * @param mContext
     * @param id
     *            检查记录id
     * @param requestCode
     * @param mCallback
     */
    public void deleteCheckInfo(Context mContext, String id, int requestCode,
                                onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("produceLocaleNote.id", id);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_DELETE_CHECK_INFO, key, false, mDatas,
                requestCode, mCallback);
    }

    /**
     * 获取检查记录详情
     *

     *            用于区分返回时，谁处理
     * @param mCallback
     *            回调接口
     */
    public void getCheckDetail(Context mContext, String id,
                               onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("produceLocaleNote.id", id);
        // TODO
        // mDatas.put("typeId", 0);
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                        + AppConfig.URI_GET_COMPANY_CHECK_DETAIL_INFO, key, false,
                mDatas, 0, mCallback);
    }
    /**
     * 企业基本信息修改
     *
     * @param mContext
     * @param name
     *            企业名称
     * @param address
     *            企业地址
     * @param bus
     *            工商注册号
     * @param oneCode
     *            市
     * @param twoCode
     *            区
     * @param threeCode
     *            街道
     * @param personName
     *            法定代表人
     * @param phone
     *            电话
     * @param typejjCode
     *            经济类型
     * @param bigCode
     *            企业规模
     * @param requestCode
     * @param mCallback
     */
    public void changeCompany(Context mContext, String id, String name,
                              String address, String bus, String oneCode, String twoCode,
                              String threeCode, String personName, String phone,
                              String typejjCode, String bigCode, String bornCode, String x,
                              String y, int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");

        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("company.id", id);
        mDatas.put("company.companyName", name);
        mDatas.put("company.address", address);
        mDatas.put("company.businessRegNumber", bus);
        mDatas.put("company.firstArea", oneCode);
        mDatas.put("company.secondArea", twoCode);
        mDatas.put("company.thirdArea", threeCode);
        mDatas.put("company.fdDelegate", personName);
        mDatas.put("company.phone", phone);

        mDatas.put("company.economyKind", typejjCode);
        mDatas.put("company.isEnterprise", bigCode);
        mDatas.put("company.isProduction", bornCode);
        if (x != null) {

            mDatas.put("point.x", x);
            mDatas.put("point.y", y);
        }

        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_DA
                        + AppConfig.URI_COMPANY_CHANGE_INFO, key, false, mDatas,
                requestCode, mCallback);

    }

    /**
     * 重大隐患提交
     *
     * @param mContext
     * @param hasisProject
     *            市级以上重点工程
     * @param fristArea
     *            市
     * @param secondArea
     *            县
     * @param threeArea
     *            街道
     * @param linkMan
     *            联系人
     * @param linkTel
     *            联系电话
     * @param linkMobile
     *            手机号
     * @param description
     *            隐患基本情况
     * @param govCoordination
     *            是否需要政府协调
     * @param partStopProduct
     *            是否需局部停产停业
     * @param fullStopProduct
     *            是否需要全部停产停业
     * @param target
     *            落实治理目标
     * @param resource
     *            落实治理机构人员
     * @param safetyMethod
     *            落实安全促使及应急预案
     * @param goods
     *            落实治理经费物资
     * @param finishDate
     *            计划完成治理时间
     * @param governMoney
     *            落实治理经费
     * @param chargePerson
     *            单位责任人
     * @param fillDate
     *            录入时间
     * @param fillMan
     *            填报人
     *            现场照片
     * @param requestCode
     * @param mCallback
     */
    public void submitMajorTrouble(Context mContext, String companyid,
                                   String noteid, boolean hasisProject, String address,
                                   String fristArea, String secondArea, String threeArea,
                                   String linkMan, String linkTel, String linkMobile,
                                   String description, boolean govCoordination,
                                   boolean partStopProduct, boolean fullStopProduct, boolean target,
                                   boolean resource, boolean safetyMethod, boolean goods,
                                   String finishDate, String governMoney, String chargePerson,
                                   String fillDate, String fillMan, List<File> listpic,
                                   String checkid, int requestCode, onNetCallback mCallback) {
        String key = (String)SPUtils.getData(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");

        long id = (long)SPUtils.getData(PrefKeys.PREF_COMPANY_ID_KEY,
                0l);
        Map<String, Object> mDatas = new HashMap<String, Object>();
        mDatas.put("danger.hzCompany.id", companyid);
        mDatas.put("danger.noteId", noteid);
        mDatas.put("danger.emphasisProject", hasisProject);
        mDatas.put("danger.dangerAdd", address);
        mDatas.put("danger.firstArea", fristArea);
        mDatas.put("danger.secondArea", secondArea);
        mDatas.put("danger.thirdArea", threeArea);
        mDatas.put("danger.linkMan", linkMan);
        mDatas.put("danger.linkTel", linkTel);
        mDatas.put("danger.linkMobile", linkMobile);
        mDatas.put("danger.description", description);
        mDatas.put("danger.govCoordination", govCoordination);
        mDatas.put("danger.partStopProduct", partStopProduct);
        mDatas.put("danger.fullStopProduct", fullStopProduct);
        mDatas.put("danger.target", target);
        mDatas.put("danger.resource", resource);
        mDatas.put("danger.safetyMethod", safetyMethod);
        mDatas.put("danger.goods", goods);
        mDatas.put("danger.finishDate", finishDate);
        mDatas.put("danger.governMoney", governMoney);
        mDatas.put("danger.chargePerson", chargePerson);
        mDatas.put("danger.fillDate", fillDate);
        mDatas.put("danger.fillMan", fillMan);
        if (listpic != null && listpic.size() > 0) {
            mDatas.put("file", listpic);
        }
        if (checkid != null) {
            // mDatas.put("danger.checkInfoId", checkid);
        }
        HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
                + AppConfig.URI_SUBMIT_MAJOR_INFO, key, listpic != null
                && listpic.size() > 0, mDatas, requestCode, mCallback);

    }
}
