package com.safetys.zatgov.utils;

import android.text.TextUtils;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:字符串工具包
 */
public class StringUtil {

    /**
     * 是否为空
     * @param data
     * @return
     */
    public static boolean isEmpty(String data){
        return (data==null|| TextUtils.isEmpty(data));
    }

    /**
     * 将字符串为空的转""
     * @param data
     * @return
     */
    public static String nvl(String data){
        if(data==null){
            data = "";
        }
        return data;
    }
}
