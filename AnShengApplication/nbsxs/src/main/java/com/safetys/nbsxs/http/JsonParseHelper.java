package com.safetys.nbsxs.http;

/**
 * @author sjw
 * 
 * 解析json帮助类，HttpRequestHelper获取到的结果在这里解析
 */
public class JsonParseHelper {
	
	private static JsonParseHelper mHelper = new JsonParseHelper();
	
	private JsonParseHelper() {}
	
	public static JsonParseHelper getInstance(){
		if(mHelper == null){
			mHelper = new JsonParseHelper();
		}
		return mHelper;
	}
	
}
