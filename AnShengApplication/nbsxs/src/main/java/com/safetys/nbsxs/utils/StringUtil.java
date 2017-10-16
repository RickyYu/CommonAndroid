package com.safetys.nbsxs.utils;

import android.text.TextUtils;

/**
 * @author sjw
 * 字符串工具包
 */
public class StringUtil {

	/**
	 * 是否为空
	 * @param data
	 * @return
	 */
	public static boolean isEmpty(String data){
		return (data==null||TextUtils.isEmpty(data));
	}
	
	/**
	 * 将字符串为空的转""
	 * @param data
	 * @return
	 */
	public static String nvl(String data){
		if(data==null||data.equals("null")){
			data = "";
		}
		return data;
	}
}
