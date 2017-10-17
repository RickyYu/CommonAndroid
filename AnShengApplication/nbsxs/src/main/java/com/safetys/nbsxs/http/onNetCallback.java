package com.safetys.nbsxs.http;


import com.safetys.nbsxs.entity.JsonResult;

/**
 * @author sjw
 * 网络应答监听
 */
public interface onNetCallback{
	
	public void onError(String errorMsg);


	public void onSuccess(int requestCode, JsonResult mJsonResult);
}
