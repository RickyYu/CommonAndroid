package com.safetys.nbsxs.common;

import java.util.HashMap;

/**
 * 身份类型枚举
 */
public class IdTypeEnum {
	
	private static final HashMap<String, String> data = new HashMap();
	public static final String[] typedata={"身份证","驾驶证","护照"};
	static{
		data.put("身份证","ID_CARD");
		data.put("护照","PASSPORT");
		data.put("驾驶证","DRIVING_LICENSE");
		data.put("ID_CARD","身份证");
		data.put("PASSPORT","护照");
		data.put("DRIVING_LICENSE","驾驶证");
	}
	public static String getValue(String name){
		return data.get(name);
	}
}
