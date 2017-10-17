package com.safetys.nbsxs.utils;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.safetys.nbsxs.R;
import com.safetys.nbsxs.entity.JsonResult;
import com.safetys.nbsxs.http.onNetCallback;

import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Iterator;
import java.util.Map;



/**
 * @author sjw
 * 网络请求工具
 */
public class HttpUtil {
	
	
	/**
	 * 发送网络请求
	 * @param uri 请求地址
	 * @param key 登陆后返回的identify,不在cookie中添加时填null
	 * @param hasFile 是否会上传文件
	 * @param mDatas 上传的数据
	 * @param requestCode 请求的code，用户返回时区分
	 * @param mListener 监听
	 */
	public static Cancelable sendRequestWithCookie(String uri, String key, boolean hasFile, Map<String , Object> mDatas, final int requestCode, final onNetCallback mListener, int timeout){
		RequestParams mParams = new RequestParams(uri);
		/*在Cookie中加入登录返回的key*/
		if(key!=null){
//			LogUtil.i("abc:"+URLEncoder.encode("SXS_FIREWORK_CLIENT_AUTH_KEY_2017="+key));
//			mParams.addHeader("Cookie", URLEncoder.encode("SXS_FIREWORK_CLIENT_AUTH_KEY_2017="+key));
//			LogUtil.i("SXS_FIREWORK_CLIENT_AUTH_KEY_2017="+key);
			mParams.addHeader("Cookie", "SXS_FIREWORK_CLIENT_AUTH_KEY_2017="+key);
		}
		mParams.addHeader("accept", "*/*");
		mParams.addHeader("connection", "Keep-Alive");
		mParams.addHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		mParams.addHeader("Content-Type", "application/json;charset=UTF-8");
		mParams.setUseCookie(true);
		mParams.setConnectTimeout(timeout);
		/*设置是否会有文件上传*/
		mParams.setMultipart(hasFile);
		/*添加提交的参数*/
		if(mDatas!=null&&mDatas.size()>0){
			Iterator<String> keys = mDatas.keySet().iterator(); 
			   while(keys.hasNext()) { 
			   String keyStr = (String) keys.next(); 
			   Object value=mDatas.get(keyStr); 
			   mParams.addParameter(keyStr, value);
			} 
		}
		/*提交*/
		Cancelable mCancelable = x.http().post(mParams, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				//返回通用错误提示
				mListener.onError(x.app().getString(R.string.network_error));
			}

			@Override
			public void onFinished() {
				
			}

			@Override
			public void onSuccess(String jsonStr) {
				Logger.i("jsonStr："+jsonStr);
				//解析一次，看返回的消息是否为正确信息
				JsonResult mJsonResult = JSON.parseObject(jsonStr, JsonResult.class);
				if(mJsonResult.isSuccess()){
					mListener.onSuccess(requestCode,mJsonResult);
				}else{
					mListener.onError(mJsonResult.getMsg());
				}
				
			}
		});
		return mCancelable;
	}
	/**
	 * 发送网络请求
	 * @param uri 请求地址
	 * @param key 登陆后返回的identify,不在cookie中添加时填null
	 * @param hasFile 是否会上传文件
	 * @param mDatas 上传的数据
	 * @param requestCode 请求的code，用户返回时区分
	 * @param mListener 监听
	 */
	public static Cancelable sendRequestWithCookie(String uri,String key,boolean hasFile,Map<String , Object> mDatas,final int requestCode,final onNetCallback mListener){
		return sendRequestWithCookie(uri, key, hasFile, mDatas, requestCode, mListener,30*1000);
	}
	
	/**
	 * 取消请求
	 * @param mCancelable
	 */
	public static void cancel(Cancelable mCancelable){
		if(mCancelable!=null&&(!mCancelable.isCancelled())){
			mCancelable.cancel();
		}
	}
}
