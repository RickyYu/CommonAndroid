package com.safetys.zatgov.config;

import java.util.HashMap;

public class IsEnterTypeEnum {
	private static final HashMap<String, String> data = new HashMap();
	public static final String[] typedata={"规上企业","规下企业","小微企业"};
	static{
		data.put("规上企业","isEnterprise1");
		data.put("规下企业","isEnterprise2");
		data.put("小微企业","isEnterprise3");
		data.put("isEnterprise1","规上企业");
		data.put("isEnterprise2","规下企业");
		data.put("isEnterprise3","小微企业");
	}
	public static String getValue(String name){
		return data.get(name);
	}
}
