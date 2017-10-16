package com.safetys.nbsxs.common;

/**
 * 全局常用变量   
 * @author Steven
 * @created 2015-11-10
 */
public class Const {
	
	/**
	 * 获取企业信息--请求码
	 */
	public static final int NET_GET_COMPANY_INFO_CODE = 9801;
	/**
	 * 修改企业信息--请求码
	 */
	public static final int NET_CHANGE_COMPANY_INFO_CODE = 3002;
	/**
	 * 获取一般隐患整改信息列表--请求码
	 */
	public static final int NET_GET_COMPANY_GENERALCHECK_INFO_CODE = 9802;
	
	/**
	 * 获取重大隐患整改信息列表--请求码
	 */
	public static final int NET_GET_COMPANY_MAJORCHECK_INFO_CODE = 9807;
	
	/**
	 * 获取安全生产信息--请求码
	 */
	public static final int NET_GET_COMPANY_SAFETY_INFO_CODE = 9808;
	
	/**
	 * 获取重大隐患整改详细信息--请求码
	 */
	public static final int NET_GET_COMPANY_MAJOR_ITEM_INFO_CODE = 9809;
	
	/**
	 * 获取一般隐患整改详细信息--请求码
	 */
	public static final int NET_GET_COMPANY_GENERAL_ITEM_INFO_CODE = 9811;
	/**
	 * 获取检查表详细信息
	 */
	public static final int NET_GET_SAFETY_CHECK_INFO_CODE = 9834;
	/**
	 * 获取重大隐患详细信息
	 */
	public static final int NET_GET_COMPANY_MAJOR_DETAIL_INFO_CODE = 9812;
	/**
	 * 获取资讯信息列表
	 */
	public static final int NET_GET_COMPANY_READ_LIST_CODE = 9813;
	/**
	 * 修改检查表详细信息
	 */
	public static final int NET_CHANGE_SAFETY_CHECK_INFO_CODE = 9835;
	/**
	 * 新增检查表
	 */
	public static final int NET_ADD_SAFETY_CHECK_INFO_CODE = 9836;
	/**
	 * 删除检查表
	 */
	public static final int NET_DELETE_SAFETY_CHECK_CODE = 9839;
	/**
	 * 加载检查事项列表
	 */
	public static final int NET_GET_MATTARS_LIST_CODE = 9837;
	/**
	 * 提交检查事项是否检查
	 */
	public static final int NET_SUBMIT_MATTARS_LIST_CODE = 9838;
	
	/**
	 * 用人单位首页统计查询
	 */
	public static final int NET_GET_COMPANY_COUNT_CODE = 9816;
	/**
	 * 专家库
	 */
	public static final int NET_GET_COMPANY_EXPERTS_CODE = 9817;
	/**
	 * MSDS库
	 */
	public static final int NET_GET_COMPANY_MSDS_CODE = 9818;
	
	/**
	 *隐患区域
	 */
	public static final String[] TYPE_TROUBLE__QY_ARRAY = {"消防通道","公共区域"};
	
	/**
	 *整改类型 
	 */
	public static final String[] TYPE_TROUBLE_C_ARRAY = {"立即整改","限期整改"};
	/**
	 * 加載更多
	 */
	public static final int LOADING_MORE=0;
	
	/**
	 *隐患类型 
	 */
	public static final String[] TYPE_YH_ARRAY = {"人","物","管理"};
	
	/**
	 * 新建
	 */
	public static final int TYPE_ADD = 1;
	/**
	 * 修改
	 */
	public static final int TYPE_MODIFY = 2;
	/**
	 *刷新
	 */
	public static final String ACTION_UPDATE_LIST = "cn.saftys.CompanyInfo.update";
	/**
	 *刷新安全
	 */
	public static final String ACTION_UPDATE_SAFE = "cn.saftys.SafeInfo.update";
	/**
	 *关闭加载弹窗
	 */
	public static final String ACTION_CLOSE_DIALOG = "cn.saftys.close.dialog";
}