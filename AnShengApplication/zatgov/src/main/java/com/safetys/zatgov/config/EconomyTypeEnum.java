package com.safetys.zatgov.config;

import java.util.HashMap;

public class EconomyTypeEnum {
	private static final HashMap<String, String> data = new HashMap();
	public static final String[] typedata={"01国有经济","02集体经济","03私营经济",
		"04有限责任公司","05联营经济","06股份合作","07外商投资",
		"08港澳台投资","09其它经济","10股份有限公司"

	};
	static{
		data.put("01国有经济","economyType01");
		data.put("02集体经济","economyType02");
		data.put("03私营经济","economyType03");
		data.put("04有限责任公司","economyType04");
		data.put("05联营经济","economyType05");
		data.put("06股份合作","economyType06");
		data.put("07外商投资","economyType07");
		data.put("08港澳台投资","economyType08");
		data.put("09其它经济","economyType09");
		data.put("10股份有限公司","economyType10");
		
		data.put("economyType01","01国有经济");
		data.put("economyType02","02集体经济");
		data.put("economyType03","03私营经济");
		data.put("economyType04","04有限责任公司");
		data.put("economyType05","05联营经济");
		data.put("economyType06","06股份合作");
		data.put("economyType07","07外商投资");
		data.put("economyType08","08港澳台投资");
		data.put("economyType09","09其它经济");
		data.put("economyType10","10股份有限公司");
	}
	public static String getValue(String name){
		return data.get(name);
	}
}
