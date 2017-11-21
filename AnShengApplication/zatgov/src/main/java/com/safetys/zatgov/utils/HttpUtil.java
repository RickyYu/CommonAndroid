package com.safetys.zatgov.utils;

import com.alibaba.fastjson.JSON;
import com.safetys.zatgov.R;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.onNetCallback;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Iterator;
import java.util.Map;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
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
    public static void sendRequestWithCookie(final String uri, String key, boolean hasFile, Map<String , Object> mDatas, final int requestCode, final onNetCallback mListener){
        RequestParams mParams = new RequestParams(uri);
        LogUtil.i("URI:"+uri);
		/*在Cookie中加入登录返回的key*/
        if(key!=null){
            mParams.addHeader("Cookie", "SAFETYS_CLIENT_AUTH_KEY_2016="+key);
        }
        mParams.addHeader("Content-Type", "application/json;charset=UTF-8");
        mParams.setUseCookie(true);
        mParams.setConnectTimeout(15*1000);
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
        x.http().post(mParams, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                //返回通用错误提示
                mListener.onError(x.app().getString(R.string.network_error));
                LogUtil.i("onError");
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String jsonStr) {
                //解析一次，看返回的消息是否为正确信息
                JsonResult mJsonResult = JSON.parseObject(jsonStr, JsonResult.class);
                LogUtil.i(uri+"====onSuccess==="+mJsonResult.toString());
                if(mJsonResult.isSuccess()){
                    mListener.onSuccess(requestCode,mJsonResult);
                }else{
                    mListener.onError(mJsonResult.getMsg());
                }

            }
        });

    }
}
