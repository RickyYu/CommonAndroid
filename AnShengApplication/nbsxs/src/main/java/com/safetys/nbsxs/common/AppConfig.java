package com.safetys.nbsxs.common;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.safetys.nbsxs.R;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.webkit.WebView;

/**
 * 应用配置
 * 
 */
public class AppConfig {
	
	/**
	 * 应用主包
	 */
	public static final String BASE_PACKAGE = "cn.safetys.nbsxs";
	/**
	 * 需要和移动应用维护中心的应用标识名称一致
	 */
	public static final String APP_SHORT_NAME = "nbsxs";
	/** 
	 * 数据库文件名称 ,本地操作全部保存在这个数据库
	 */
	public static final String DB_FILE_NAME = "sxs_db";
	
	/** 
	 * 数据库版本号
	 */
	public static final int DB_VERSION_CODE = 1;
	
	/**
	 * 枚举数据插入数据库
	 */
	public static final int ENUM_VERSION_CODE = 1;
	
	/**
	 * SDCard 初始根目录
	 */
	public static final String SDCARD_APP_ROOT = Environment
			.getExternalStorageDirectory().getPath()
			+ File.separator + APP_SHORT_NAME;
	public static final String HOME_DB = "db";
	public static final String HOME_XML = "xml";
	public static final String HOME_DOWNLOAD = "download";
	public static final String HOME_CACHE = "cache";
	public static final String AREA_XML = "area.xml";
	
	public static final int NORMAL_LIST_PAGE_SIZE = 10;//一般分頁listview数量
	
	/**
	 * 主机地址
	 */
	//松香水开发地址
//	public static String HOST_ADDRESS_YH ="http://192.168.2.132:8081/nbsxs";
	//松香水测试地址
//	public static String HOST_ADDRESS_YH ="http://192.168.2.224:8005/nbsxs";
	//松香水正式地址
	public static String HOST_ADDRESS_YH ="http://60.190.2.247:7003/nbsxs";
//	public static String HOST_ADDRESS_YH ="http://192.168.2.154:8081/nbsxs";//yufeng ip
	/********************************接口地址begin********************************************/
	
	/*登录地址*/
	public static String URI_LOGIN = "/app/user/login.sj";
	/*销售记录列表信息获取*/
	public static String URI_GET_SELL_LIST_INFO = "/app/saleRecord/list.sj";
	/*企业信息获取*/
	public static String URI_GET_COMPANY_INFO = "/app/company/info.sj";
	/*企业信息修改*/
	public static String URI_CHANGE_COMPANY_INFO = "/app/company/update.sj";
	/*销售登记*/
	public static String URI_SUMBIT_SELL_INFO = "/app/saleRecord/save.sj";
	/*删除销售记录*/
	public static String URI_DELETE_SELL_INFO = "/app/saleRecord/deleteSaleRecord.sj";
	/*修改密码*/
	public static String URI_CHANGE_PASSWORD = "/app/user/updatePassword.sj";
	/*获取版本号*/
	public static String URI_GET_VERSION_INFO = "/app/client/loadLatest.sj";
	/*获取统计信息*/
	public static String URI_GET_SELL_COUNT = "/app/saleRecord/saleRecordCount.sj";
	/********************************接口地址end********************************************/
	
	/**
	 * 获取本机版本序号
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(BASE_PACKAGE,
					0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
	}
	/**
	 * 获取本机版本名称
	 */
	public static String getVerName(Context context) {
		String verName = "1.0.0";
		try {
			verName = context.getPackageManager().getPackageInfo(BASE_PACKAGE,
					0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * 获取本机应用名称
	 */
	public static String getAppName(Context context) {
		String appName = context.getResources().getText(R.string.app_name)
				.toString();
		return appName;
	}

	// 初始化SDCard应用数据根目录
	public static File buildAppHome() {
		File base = new File(SDCARD_APP_ROOT);
		if (!base.exists()) {
			base.mkdir();
		}
		if (base.exists() && base.isFile()) {
			base.mkdir();
		}
		return base;
	}

	/**
	 * 初始化SDCard应用数据子目录
	 * 
	 * @author safetys
	 * @create_time 2013-1-4
	 * @param homeName
	 *            {@link #HOME_CACHE} or {@link #HOME_DB} or
	 *            {@link #HOME_DOWNLOAD}
	 */
	public static File buildPath(String homeName) {
		File base = buildAppHome();
		String path = base.getPath() + File.separator + homeName;
		File build = new File(path);
		if (!build.exists()) {
			build.mkdirs();
		}
		if (build.exists() && build.isFile()) {
			build.mkdirs();
		}

		return build;

	}
	
	public static void buildSdPath(String path){
		File build = new File(path);
		if (!build.exists()) {
			build.mkdirs();
		}
		if (build.exists() && build.isFile()) {
			build.mkdirs();
		}
	}
	
	public static String getAreaXmlPath(){
		return AppConfig.SDCARD_APP_ROOT+
				File.separator+AppConfig.HOME_XML+File.separator+AppConfig.AREA_XML;
	}

	public static String stringFilter(String str){
		String regRx = "[`@#$^&*+=|{}''\\[\\]<>/~￥……——+|{}【】]";
		Pattern p = Pattern.compile(regRx);
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}
}
