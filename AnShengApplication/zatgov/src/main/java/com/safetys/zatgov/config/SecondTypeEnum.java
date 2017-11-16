package com.safetys.zatgov.config;

import java.util.HashMap;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class SecondTypeEnum {
    private static final HashMap<String, String> data = new HashMap();
    public static final String[] typedata2={"德清县","长兴县","安吉县",
            "吴兴区","南浔区","湖州开发区","太湖度假区"};
    static{
        data.put("请选择",null);
        data.put("德清县","330521");
        data.put("长兴县","330522");
        data.put("安吉县","330523");
        data.put("吴兴区","330502");
        data.put("南浔区","330503");
        data.put("湖州开发区","330504");
        data.put("太湖度假区","330505");
        data.put(null,"");
        data.put("0","");
        data.put("330521","德清县");
        data.put("330522","长兴县");
        data.put("330523","安吉县");
        data.put("330502","吴兴区");
        data.put("330503","南浔区");
        data.put("330504","湖州开发区");
        data.put("330505","太湖度假区");
    }
    public static String getValue(String name){
        return data.get(name);
    }
}
