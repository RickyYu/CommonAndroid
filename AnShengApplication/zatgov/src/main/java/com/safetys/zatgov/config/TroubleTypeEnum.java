package com.safetys.zatgov.config;

import java.util.HashMap;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class TroubleTypeEnum {
    private static final HashMap<String, String> data = new HashMap();
    public static final String[] typedata={"人","物","管理"};
    static{
        data.put("人","1327");
        data.put("物","1332");
        data.put("管理","1344");
        data.put("1327","人");
        data.put("1332","物");
        data.put("1344","管理");
    }
    public static String getValue(String name){
        return data.get(name);
    }
}
