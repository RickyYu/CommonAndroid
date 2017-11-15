package com.safetys.zatgov.config;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class Const {
    /**
     * 页大小
     */
    public static final int PAGE_SIZE = 10;// 页大小
    /**
     * 页大小
     */
    public static final int MAX_PICTURE_SIZE = 9;// 最多拍照张数
    /**
     * 加载更多
     */
    public static final int LOADING_MORE = 0;
    /**
     * 刷新检查记录
     */
    public static final String UPDATE_CHECK_LIST = "cn.saftys.ZfCheckListActivity.update";
    /**
     * 获取资讯信息列表
     */
    public static final int NET_GET_COMPANY_READ_LIST_CODE = 9813;

    /**
     * MSDS库
     */
    public static final int NET_GET_COMPANY_MSDS_CODE = 9818;

    /**
     * 政府首页统计查询
     */
    public static final int NET_GET_GOV_COUNT_CODE = 9816;
    /**
     * 政府首页统计查询政府
     */
    public static final int NET_GET_GOV_CODE = 3378;
    /**
     * 政府首页统计查询企业
     */
    public static final int NET_GET_COM_CODE = 3379;
    /**
     * 检查记录列表
     */
    public static final int NET_GET_GOV_CHECK_LIST_CODE = 9817;

    /**
     * 未处罚code
     */
    public static final int NET_GET_GOV_UNPUN_LIST_CODE = 1001;

    /**
     * 已处罚code
     */
    public static final int NET_GET_GOV_PUN_LIST_CODE = 1002;
    /**
     * 隐患列表code
     */
    public static final int NET_GET_GOV_HIDDEN_LIST_CODE = 1003;
    /**
     * 历史复查记录code
     */
    public static final int NET_GET_GOV_REVIEW_HISTORY_LIST_CODE = 1004;
    /**
     * 初查记录code
     */
    public static final int NET_GET_GOV_PRE_IVS_RECORD_CODE = 1005;




    /**
     * 删除检查记录
     */
    public static final int NET_DELETE_GOV_CHECK_LIST_CODE = 9888;
    /**
     * 复查信息删除
     */
    public static final int NET_DELETE_REVIEW_ITEM = 1890;
    /**
     * 复查信息修改
     */
    public static final int NET_CHANGE_REVIEW_ITEM = 1891;
    /**
     * 复查信息增加
     */
    public static final int NET_ADD_REVIEW_ITEM = 1892;
    /**
     * 新增检查记录
     */
    public static final int NET_ADD_CHECK_ITEM = 9879;
    /**
     * 新增处罚
     */
    public static final int NET_ADD_PU_ITEM = 9819;
    /**
     * 修改处罚
     */
    public static final int NET_CHANGE_PU_ITEM = 9820;
    /**
     * 修改检查记录
     */
    public static final int NET_CHANGE_CHECK_ITEM = 9879;
    /**
     * 提交重大隐患
     */
    public static final int SUB_MAJOR_CHECK = 2222;
    /**
     * 提交一般隐患
     */
    public static final int SUB_NOMARL_CHECK = 4444;
    /**
     *修改企业信息
     */
    public static final int NET_CHANGE_COMPANY_INFO_CODE = 4020;

    /**
     * 获取一般隐患整改详细信息--请求码
     */
    public static final int NET_GET_COMPANY_GENERAL_ITEM_INFO_CODE = 4210;

    /**
     *隐患类型
     */
    public static final String[] TYPE_YH_ARRAY = {"人","物","管理"};
    /**
     * 获取重大隐患详细信息
     */
    public static final int NET_GET_COMPANY_MAJOR_DETAIL_INFO_CODE = 4310;
    /**
     * 重大隐患整改识别码
     */
    public static final int NET_GET_MAJOR_CHANGE = 378729;
    /**
     * 重大隐患整改
     */
    public static final int NET_GET_MAJOR = 147852;
    /**
     * 是否存在未整改
     */
    public static final int HAVE_PROBLEM = 3458;
    /**
     * 接管未整改
     */
    public static final int SAVE_PROBLEM = 45296;
    /**
     * 网格员检查表
     */
    public static final int WGY_CHECKLIST = 41234;
    /**
     * 新增检查表
     */
    public static final int NET_ADD_SAFETY_CHECK_INFO_CODE = 42123;
    /**
     * 查询检查表
     */
    public static final int NET_GET_SAFETY_CHECK_INFO_CODE = 40123;
    /**
     * 查询检查表
     */
    public static final int NET_GET_COMPANY_ITEM_INFO_CODE = 40124;
    /**
     * 修改检查表
     */
    public static final int NET_CHANGE_SAFETY_CHECK_INFO_CODE = 41123;
    /**
     * 新增检查表查询以及列表
     */
    public static final int NET_CHECK_LIST_ONE = 43123;
    /**
     * 新增检查表查询二级检查项
     */
    public static final int NET_CHECK_LIST_TWO = 44123;
    /**
     * 修改新增检查表
     */
    public static final int SUBMIT_CHECK_LIST_TWO = 445123;
    /**
     * 删除检查表
     */
    public static final int DELETE_CHECK_LIST = 455123;

    /**
     * 获取消防用户Code
     */
    public static final int NET_GET_FIRE_USER_CODE = 100001;
}
