package com.safetys.zatgov.config;

import java.util.HashMap;

public class BornTypeEnum {
	private static final HashMap<String, String> data = new HashMap();
	public static final String[] typedata={"生产中","停产"};
	static{
		data.put("生产中","true");
		data.put("停产","false");
		data.put("true","生产中");
		data.put("false","停产");
	}
	public static String getValue(String name){
		return data.get(name);
	}
}
