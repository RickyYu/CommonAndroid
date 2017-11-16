package com.safetys.zatgov.config;

import java.util.HashMap;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class PunishmentTypeEnum {
    //	罚款    punishmentType02
//	警告    punishmentType01
//	责令改正   punishmentType03
//	没收违法所得   punishmentType04
//	责令停产停业整顿   punishmentType05
//	暂扣或者吊销有关许可证   punishmentType06
//	关闭    punishmentType07
//	拘留    punishmentType08
//	其他行政处罚   punishmentType09
    private static final HashMap<String, String> data = new HashMap();
    public static final String[] typedata={"罚款","警告","责令改正","没收违法所得",
            "责令停产停业整顿","暂扣或者吊销有关许可证","关闭","拘留","其他行政处罚"};
    static{
        data.put("罚款","punishmentType02");
        data.put("警告","punishmentType01");
        data.put("责令改正","punishmentType03");
        data.put("没收违法所得","punishmentType04");
        data.put("责令停产停业整顿","punishmentType05");
        data.put("暂扣或者吊销有关许可证","punishmentType06");
        data.put("关闭","punishmentType07");
        data.put("拘留","punishmentType08");
        data.put("其他行政处罚","punishmentType09");
        data.put("punishmentType02","罚款");
        data.put("punishmentType01","警告");
        data.put("punishmentType03","责令改正");
        data.put("punishmentType04","没收违法所得");
        data.put("punishmentType05","责令停产停业整顿");
        data.put("punishmentType06","暂扣或者吊销有关许可证");
        data.put("punishmentType07","关闭");
        data.put("punishmentType08","拘留");
        data.put("punishmentType09","其他行政处罚");
    }
    public static String getValue(String name){
        return data.get(name);
    }
}
