package com.safetys.zatgov.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.safetys.zatgov.ZatApplication;
import com.safetys.zatgov.entity.TradeType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xutils.ex.DbException;

import java.io.InputStream;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class XmlParseUtil {
    private interface onParseListener{

        void onDataDeal(XmlPullParser parser);
    }

    private static void parse(InputStream inStream,String tagName,onParseListener mListener)throws Throwable{
        //========创建XmlPullParser,有两种方式=======
        //方式一:使用工厂类XmlPullParserFactory
        XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = pullFactory.newPullParser();
        //方式二:使用Android提供的实用工具类android.util.Xml
        //XmlPullParser parser = Xml.newPullParser();
        //解析文件输入流
        parser.setInput(inStream, "UTF-8");
        //产生第一个事件
        int eventType = parser.getEventType();  //只要不是文档结束事件，就一直循环
        boolean flag = true;
        boolean canGet = false;
        while(eventType!=XmlPullParser.END_DOCUMENT&&flag)
        {
            switch (eventType)
            {
                //触发开始文档事件
                case XmlPullParser.START_DOCUMENT:

                    break;
                //触发开始元素事件
                case XmlPullParser.START_TAG:
                    //获取解析器当前指向的元素的名称
                    String name = parser.getName();
                    if(canGet){
                        mListener.onDataDeal(parser);
                    }
                    if(tagName.equals(name)){
                        canGet = true;
                    }
                    break;
                //触发结束元素事件
                case XmlPullParser.END_TAG:
                    //
                    if(tagName.equals(parser.getName()))
                    {
                        flag = false;
                    }
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }
    }

    /**
     * 解析行业类别
     * @param mContext
     */
    private static void parseTradeType(final Context mContext){
        AssetManager asset = mContext.getAssets();
        try {
            InputStream input = asset.open("huzhou_enum.xml");
            parse(input,"tradeType",new onParseListener() {

                @Override
                public void onDataDeal(XmlPullParser parser) {
                    String code = parser.getName();
                    String name = parser.getAttributeValue(0);
                    TradeType mTradeType = new TradeType();
                    mTradeType.setTypeCode(code);
                    mTradeType.setTypeName(name);
                    try {
                        ((ZatApplication)mContext.getApplicationContext()).getDbManager().save(mTradeType);
                    } catch (DbException e) {

                        e.printStackTrace();
                    }
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void starParse(final Context mContext){
        //解析行业类型
        parseTradeType(mContext);
    }
}
