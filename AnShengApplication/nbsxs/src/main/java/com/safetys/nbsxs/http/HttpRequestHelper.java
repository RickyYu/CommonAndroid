package com.safetys.nbsxs.http;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.safetys.nbsxs.SxsApplication;
import com.safetys.nbsxs.common.AppConfig;
import com.safetys.nbsxs.common.PrefKeys;
import com.safetys.nbsxs.entity.CompanyVo;
import com.safetys.nbsxs.entity.RegisterInfo;
import com.safetys.nbsxs.utils.HttpUtil;
import com.safetys.nbsxs.utils.Utils;

import org.xutils.common.Callback.Cancelable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sjw
 * 网络通讯帮助类，通讯方法都放在这里实现
 */
public class HttpRequestHelper {
	
	private static final int TYPE = 1;//

	private static HttpRequestHelper mHelper;
	
	private HttpRequestHelper() {}
	
	public static HttpRequestHelper getInstance(){
		if(mHelper == null){
			mHelper = new HttpRequestHelper();
		}
		return mHelper;
	}
	
	
	/**
	 * 登录接口
	 * @param accountId 登录账号ID
	 * @param password 登录密码
	 * @param mCallback 回调接口
	 */
	public void login(String accountId,String password,onNetCallback mCallback){
		Map<String , Object> mDatas = new HashMap<String, Object>();
		mDatas.put("username", accountId);
		mDatas.put("password", password);
		mDatas.put("type", TYPE);
		HttpUtil.sendRequestWithCookie(
				AppConfig.HOST_ADDRESS_YH+AppConfig.URI_LOGIN,
				null, 
				false, 
				mDatas, 
				0, 
				mCallback);
	}
	
	/**
	 * 获取门店信息
	 * @param accountId 登录账号ID
	 * @param password 登录密码
	 * @param mCallback 回调接口
	 */
	public void getCompanyInfo(Context mContext,onNetCallback mCallback){
		Map<String , Object> mDatas = new HashMap<String, Object>();
		SharedPreferences mPreferences = ((SxsApplication)mContext.getApplicationContext()).getAppMainPreferences();
		String key = mPreferences.getString(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
		HttpUtil.sendRequestWithCookie(
				AppConfig.HOST_ADDRESS_YH+AppConfig.URI_GET_COMPANY_INFO, 
				key, 
				false, 
				mDatas, 
				0, 
				mCallback);
	}
	
	/**
	 * 提交门店信息
	 * @param mContext
	 * @param mCompanyVo
	 * @param requestCode
	 * @param mCallback
	 */
	public void submitCompanyInfo(Context mContext, CompanyVo mCompanyVo, int requestCode, onNetCallback mCallback){
		Map<String , Object> mDatas = new HashMap<String, Object>();
		SharedPreferences mPreferences = ((SxsApplication)mContext.getApplicationContext()).getAppMainPreferences();
		String key = mPreferences.getString(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
		mDatas.put("companyName",mCompanyVo.getCompanyName());
		mDatas.put("businessRegNum", mCompanyVo.getBusinessRegNum());
		mDatas.put("principalPerson", mCompanyVo.getPrincipalPerson());
		mDatas.put("principalTelephone", mCompanyVo.getPrincipalTelephone());
		mDatas.put("firstArea", mCompanyVo.getFirstArea());
		mDatas.put("secondArea", mCompanyVo.getSecondArea());
		mDatas.put("thirdArea", mCompanyVo.getThirdArea());
		mDatas.put("businessAddress", mCompanyVo.getBusinessAddress());
		mDatas.put("documentInfo", mCompanyVo.getDocumentInfo());
		mDatas.put("businessScope", mCompanyVo.getBusinessScope());
		
		mDatas.put("deviceId", Utils.getIMEI(mContext));//smei
		mDatas.put("deviceName", Build.MODEL);
		mDatas.put("devicePhone", Utils.getPhone(mContext));
		HttpUtil.sendRequestWithCookie(
				AppConfig.HOST_ADDRESS_YH+AppConfig.URI_CHANGE_COMPANY_INFO, 
				key, 
				false, 
				mDatas, 
				requestCode, 
				mCallback);
	}
	
	/**
	 * 提交销售信息
	 */
	public void submitSellInfo(Context mContext, RegisterInfo mInfo, onNetCallback mCallback){
		Map<String , Object> mDatas = new HashMap<String, Object>();
		SharedPreferences mPreferences = ((SxsApplication)mContext.getApplicationContext()).getAppMainPreferences();
		String key = mPreferences.getString(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
		mDatas.put("type", TYPE);
		if(mInfo.getId()!=null){
			mDatas.put("sxsRecord.id", mInfo.getId());
		}
		mDatas.put("sxsRecord.credentials", mInfo.getCredentials());
		mDatas.put("sxsRecord.credentialsCode", mInfo.getCredentialsCode());
		mDatas.put("sxsRecord.name", mInfo.getName());
		mDatas.put("sxsRecord.address", mInfo.getAddress());
		mDatas.put("sxsRecord.companyName", mInfo.getCompanyName());
		mDatas.put("sxsRecord.phone", mInfo.getPhone());
		mDatas.put("sxsRecord.productName", mInfo.getProductName());
		mDatas.put("sxsRecord.productNumber", mInfo.getProductNumber());
		mDatas.put("sxsRecord.content", mInfo.getContent());
		mDatas.put("sxsRecord.payTime", mInfo.getPayTime());
		mDatas.put("sxsRecord.companyPerson", mInfo.getCompanyPerson());
		HttpUtil.sendRequestWithCookie(
				AppConfig.HOST_ADDRESS_YH+AppConfig.URI_SUMBIT_SELL_INFO, 
				key, 
				false, 
				mDatas, 
				0, 
				mCallback);
	}
	
	
	/**
	 * 获取销售记录列表
	 * @param mContext
	 * @param itemCount 从哪一项开始
	 * @param productNumber 排序用没数据按默认购买日期 有就按数量排序
	 * @param mCallback
	 */
	public Cancelable getSellList(Context mContext,String selectWord,int itemCount
			,String productNumber, onNetCallback mCallback){
		
		return getSellList(mContext, selectWord, itemCount, AppConfig.NORMAL_LIST_PAGE_SIZE, productNumber, mCallback);
	}
	
	/**
	 * 获取销售记录列表
	 * @param mContext
	 * @param itemCount 从哪一项开始
	 * @param productNumber 排序用没数据按默认购买日期 有就按数量排序
	 * @param mCallback
	 */
	public Cancelable getSellList(Context mContext,String selectWord,int itemCount,int limit
			,String productNumber, onNetCallback mCallback){
		Map<String , Object> mDatas = new HashMap<String, Object>();
		SharedPreferences mPreferences = ((SxsApplication)mContext.getApplicationContext()).getAppMainPreferences();
		String key = mPreferences.getString(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
		mDatas.put("type", TYPE);
		mDatas.put("limit", limit);
		mDatas.put("start", itemCount);
		if(productNumber!=null){
			mDatas.put("sxsRecord.productNumber", productNumber);
		}
		Cancelable mCancelable = HttpUtil.sendRequestWithCookie(
				AppConfig.HOST_ADDRESS_YH+AppConfig.URI_GET_SELL_LIST_INFO, 
				key, 
				false, 
				mDatas, 
				0, 
				mCallback);
		return mCancelable;
	}
	
	/**
	 * 删除销售记录
	 * @param mContext
	 * @param mCallback
	 */
	public Cancelable deleteSellInfo(Context mContext,String id,int requestCode, onNetCallback mCallback){
		Map<String , Object> mDatas = new HashMap<String, Object>();
		SharedPreferences mPreferences = ((SxsApplication)mContext.getApplicationContext()).getAppMainPreferences();
		String key = mPreferences.getString(PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
		mDatas.put("sxsRecord.id", id);
		mDatas.put("type", TYPE);
		Cancelable mCancelable = HttpUtil.sendRequestWithCookie(
				AppConfig.HOST_ADDRESS_YH+AppConfig.URI_DELETE_SELL_INFO, 
				key, 
				false, 
				mDatas, 
				requestCode, 
				mCallback);
		return mCancelable;
	}
	/************************/
	
	/**
	 * 获取版本信息
	 * @param mContext
	 * @param requestCode
	 * @param mCallback
	 */
	public void getVersionInfo(Context mContext,int requestCode,onNetCallback mCallback){
		
		Map<String , Object> mDatas = new HashMap<String, Object>();
		mDatas.put("osType", "ANDROID");
		mDatas.put("type", TYPE);
		HttpUtil.sendRequestWithCookie(
				AppConfig.HOST_ADDRESS_YH+AppConfig.URI_GET_VERSION_INFO, 
				null, 
				false, 
				mDatas, 
				requestCode, 
				mCallback);
	}
	
	

	/**
	 * 修改密码
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
		String key = ((SxsApplication) mContext.getApplicationContext())
				.getAppMainPreferences().getString(
						PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
		Map<String, Object> mDatas = new HashMap<String, Object>();
		mDatas.put("oldPassword", oldPass);
		mDatas.put("password", newPass);
		
		HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
				+ AppConfig.URI_CHANGE_PASSWORD, key, false, mDatas,
				requestCode, mCallback);
	}
	
	/**
	 * 获取销售统计信息
	 * 
	 * @param mContext
	 * @param mCallback
	 */
	public void getSellCount(Context mContext, onNetCallback mCallback) {
		String key = ((SxsApplication) mContext.getApplicationContext())
				.getAppMainPreferences().getString(
						PrefKeys.PREF_LOGIN_IDENTITY_KEY, "");
		Map<String, Object> mDatas = new HashMap<String, Object>();
		mDatas.put("type",TYPE);
		
		HttpUtil.sendRequestWithCookie(AppConfig.HOST_ADDRESS_YH
				+ AppConfig.URI_GET_SELL_COUNT, key, false, mDatas,
				0, mCallback);
	}
	

}
