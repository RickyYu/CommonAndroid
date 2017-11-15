package com.safetys.zatgov.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import com.safetys.zatgov.config.AppConfig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


public class FileUtil {
	private static final String[][] MIME_MapTable={
			//{后缀名，MIME类型} 
			{".3gp", "video/3gpp"}, 
			{".apk", "application/vnd.android.package-archive"}, 
			{".asf", "video/x-ms-asf"}, 
			{".avi", "video/x-msvideo"}, 
			{".bin", "application/octet-stream"}, 
			{".bmp", "image/bmp"}, 
			{".c", "text/plain"}, 
			{".class", "application/octet-stream"}, 
			{".conf", "text/plain"}, 
			{".cpp", "text/plain"}, 
			{".doc", "application/msword"}, 
			{".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
			{".xls", "application/vnd.ms-excel"}, 
			{".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, 
			{".exe", "application/octet-stream"}, 
			{".gif", "image/gif"}, 
			{".gtar", "application/x-gtar"}, 
			{".gz", "application/x-gzip"}, 
			{".h", "text/plain"}, 
			{".htm", "text/html"}, 
			{".html", "text/html"}, 
			{".jar", "application/java-archive"}, 
			{".java", "text/plain"}, 
			{".jpeg", "image/jpeg"}, 
			{".jpg", "image/jpeg"}, 
			{".js", "application/x-javascript"}, 
			{".log", "text/plain"}, 
			{".m3u", "audio/x-mpegurl"}, 
			{".m4a", "audio/mp4a-latm"}, 
			{".m4b", "audio/mp4a-latm"}, 
			{".m4p", "audio/mp4a-latm"}, 
			{".m4u", "video/vnd.mpegurl"}, 
			{".m4v", "video/x-m4v"}, 
			{".mov", "video/quicktime"}, 
			{".mp2", "audio/x-mpeg"}, 
			{".mp3", "audio/x-mpeg"}, 
			{".mp4", "video/mp4"}, 
			{".mpc", "application/vnd.mpohun.certificate"}, 
			{".mpe", "video/mpeg"}, 
			{".mpeg", "video/mpeg"}, 
			{".mpg", "video/mpeg"}, 
			{".mpg4", "video/mp4"}, 
			{".mpga", "audio/mpeg"}, 
			{".msg", "application/vnd.ms-outlook"}, 
			{".ogg", "audio/ogg"}, 
			{".pdf", "application/pdf"}, 
			{".png", "image/png"}, 
			{".pps", "application/vnd.ms-powerpoint"}, 
			{".ppt", "application/vnd.ms-powerpoint"}, 
			{".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
			{".prop", "text/plain"}, 
			{".rc", "text/plain"}, 
			{".rmvb", "audio/x-pn-realaudio"}, 
			{".rtf", "application/rtf"}, 
			{".sh", "text/plain"}, 
			{".tar", "application/x-tar"},
			{".tif", "image/tiff"},
			{".tiff", "image/tiff"},
			{".tgz", "application/x-compressed"}, 
			{".txt", "text/plain"}, 
			{".wav", "audio/x-wav"}, 
			{".wma", "audio/x-ms-wma"}, 
			{".wmv", "audio/x-ms-wmv"}, 
			{".wps", "application/vnd.ms-works"}, 
			{".xml", "text/plain"}, 
			{".z", "application/x-compress"}, 
			{".zip", "application/x-zip-compressed"}, 
			{"", "*/*"} 
	}; 
	
	public static boolean isFileExists(String abPath){
		File file = new File(abPath);
		return file.exists();
	}
	
	/**
	 * @param parent 父路径
	 * @param fileName 文件名
	 * @param inputStream 
	 * @return
	 */
	public static File write2SDFromInput(String parent,
			String fileName, InputStream inputStream){
		
		if(parent.endsWith(File.separator)){
			parent = parent.substring(0, parent.length()-1);
		}
		if(fileName.startsWith(File.separator)){
			fileName = fileName.substring(1, fileName.length());
		}
		if(fileName.endsWith(File.separator)){
			fileName = fileName.substring(0, fileName.length() - 1);
		}
		File file = new File(parent + File.separator + fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			
			byte[] buffer = new byte[1024];
			int l = 0;//实际读取字节
			while((l = bis.read(buffer)) != -1){
				bos.write(buffer, 0, l);
			}
			bos.flush();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(bos != null)
					bos.close();
				if(fos != null)
					fos.close();
				if(bis != null)
					bis.close();
				if(inputStream != null)
					inputStream.close();
			} catch (IOException e) {
			}
		}
		
		return file;
		
	}
	
	/**
	* 根据文件后缀名获得对应的MIME类型。
	* @param fName
	*/ 
	public static String getMIMEType(String fName) { 
		String type = "*/*";
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) { 
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	} 
	
	public static class FileType{
		public static final String TYPE_APK = ".apk";
		public static final String TYPE_DOC = ".doc";
		public static final String TYPE_DOCX = ".docx";
		public static final String TYPE_IMG = ".jpg,.jpeg,.png,.bmp";
	}
	
	/**
	 * 删除文件，可以是文件或文件夹
	 * 
	 * @param fileName
	 *            要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void openNormalFile(String localPath, Context context) {
		// 打开一般文件
		Intent intent = new Intent("android.intent.action.VIEW");
	    intent.addCategory("android.intent.category.DEFAULT");
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    Uri uri = Uri.fromFile(new File(localPath));
	    String type = FileUtil.getMIMEType(localPath);
	    intent.setDataAndType(uri, type);
	    try {
	    	context.startActivity(intent);
		} catch (Exception e) {
			int dotIndex = localPath.lastIndexOf(".");
			String end = "";
			if (dotIndex < 0) {
				Toast.makeText(context, "请先安装相关软件！", Toast.LENGTH_SHORT).show();
			}else {
				end = localPath.substring(dotIndex+1, localPath.length()).toLowerCase();
				Toast.makeText(context, "请先安装打开\"."+end+"\"后缀的软件！", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * 压缩图片，压缩后返回压缩后的路径
	 * 压缩失败，返回原图路径
	 * @param srcPath 原图路径
	 * @return
	 */
	public static String  compress(String srcPath) {

		float hh = 1600;
		float ww = 1200;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, opts);
		opts.inJustDecodeBounds = false;
		int w = opts.outWidth;
		int h = opts.outHeight;
		int size = 0;
		if (w <= ww && h <= hh) {
			size = 1;
		} else {
			double scale = w >= h ? w / ww : h / hh;
			double log = Math.log(scale) / Math.log(2);
			double logCeil = Math.ceil(log);
			size = (int) Math.pow(2, logCeil);
		}
		opts.inSampleSize = size;
		bitmap = BitmapFactory.decodeFile(srcPath, opts);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int quality = 100;
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
//		System.out.println(baos.toByteArray().length);
		while (baos.toByteArray().length > 200 * 1024) {
			baos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			quality -= 10;
//			System.out.println(baos.toByteArray().length);
		}
		
		String newPath = AppConfig.buildPath(AppConfig.HOME_CACHE).getAbsolutePath()+File.separator+ UUID
				.randomUUID().toString().replace("-", "")+ ".jpg";
		try {
			baos.writeTo(new FileOutputStream(newPath));
		} catch (Exception e) {
			e.printStackTrace();
			newPath = srcPath;//假如导出失败，继续沿用老图
		} finally {
			try {
				baos.flush();
				baos.close();
				if(bitmap!=null&&!bitmap.isRecycled()){
					bitmap.recycle();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newPath;
	}
	
	
	/**
	 * 获取压缩后的图片文件
	 * @param mOldFile 老图片文件
	 * @return
	 */
	public static File getCompressFile(File mOldFile){
		if(mOldFile == null){
			return null;
		}
		String oldStr = mOldFile.getAbsolutePath();
		String newStr = compress(oldStr);
		File newFile = new File(newStr);
		if(newFile.exists()){
			return newFile;
		}else{
			return mOldFile;
		}
	}

}
