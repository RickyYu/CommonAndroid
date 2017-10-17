package com.safetys.nbsxs.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 通过compress的方法只是减小了文件的大小，但是并不能保证减低bitmap文件解码后在内存的占用量，
 * 而android的图片处理的最关键的问题还是解码后大小不能太大，否则很容易出现OOM，而缩小图片的尺寸则可以降低图片解码后在内存的大小。<br/>
 * 如下：<br/>
 * 
 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); <br/>
 * image.compress(Bitmap.CompressFormat.JPEG, 100, baos);<br/>
 * int options = 100;<br/>
 * while ( baos.toByteArray().length / 1024>100) { <br/>
 * baos.reset();//重置baos即清空baos <br/>
 * image.compress(Bitmap.CompressFormat.JPEG, options, baos);<br/>
 * options -= 10;//每次都减少10 <br/>
 * } <br/>
 * 
 * 问题就是：<br/>
 * 
 * 当options=100,while判断为真时，循环体内，compress方法还是按options=100压缩，然后options才自减。。
 * 等于多执行了一次循环体。。<br/>
 * 建议：<br/>
 * 压缩代码 和 自减代码 调换位置，从而节省一次循环。。<br/>
 * 
 * 另： <br/>
 * 一，根据我的压缩实践，我的手机。。 压缩前后图片大小如下：<br/>
 * 100%-116201<br/>
 * 90%-37754<br/>
 * 80%-25571<br/>
 * 
 * 故， 第一次90%压缩，图片大小大幅度降低。第二次80%压缩（相当于90%*80% = 72%压缩），大小已经下降幅度不大了。 <br/>
 * 结论一：所以，可以第一次直接options = 90压缩，跳过=100 的压缩。<br/>
 * 
 * 二， 图片比例压缩时， 我看到一个算法，说比较快。。<br/>
 * be = (int) ((w / STANDARD_WIDTH + h/ STANDARD_HEIGHT) / 2);<br/>
 * 结论二：图片比例压缩倍数 就是 （宽度压缩倍数+高度压缩倍数）/2..<br/>
 * 
 * 三，再刚生成图片时，options里设置如下：<br/>
 * newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565<br/>
 * 结论三：图片配置系数用RGB_565, 默认是24位的ARGB_888<br/>
 * 
 * @author hehc@safetys.cn
 * 
 */
public class ImageUtil {
	// 质量压缩方法
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	//获取足够小的缩略图
	public static Bitmap getThumbnails(String srcPath) throws Exception{
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts); 
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 100;
		float ww = 50;
		int be = 1; 
		if (w > h && w > ww) { 
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) { 
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be; 
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		int degree = readPictureDegree(srcPath);//读取图片属性：旋转的角度 
		bitmap = rotaingImageView(degree, bitmap);//把图片旋转为正的方向 
		return compressImage(bitmap);
	}

	// 图片按比例大小压缩方法（根据路径获取图片并压缩）
	public static Bitmap getImage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		newOpts.inPreferredConfig = Config.RGB_565;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 300f; 
		float ww = 180f; 
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		int degree = readPictureDegree(srcPath);//读取图片属性：旋转的角度 
		bitmap = rotaingImageView(degree, bitmap);//把图片旋转为正的方向 
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	// 图片按比例大小压缩方法（根据Bitmap图片压缩）
	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 512) {// 判断如果图片大于512K,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inPreferredConfig = Config.RGB_565;
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	// 将byte[]转换成InputStream
	public InputStream Byte2InputStream(byte[] b) {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		return bais;
	}

	// 将InputStream转换成byte[]
	public byte[] InputStream2Bytes(InputStream is) {
		String str = "";
		byte[] readByte = new byte[1024];
		int readCount = -1;
		try {
			while ((readCount = is.read(readByte, 0, 1024)) != -1) {
				str += new String(readByte).trim();
			}
			return str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 将Bitmap转换成InputStream
	public static InputStream Bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	// 将Bitmap转换成InputStream
	public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	// 将InputStream转换成Bitmap
	public static Bitmap InputStream2Bitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	// Drawable转换成InputStream
	public static InputStream Drawable2InputStream(Drawable d) {
		Bitmap bitmap = drawable2Bitmap(d);
		return Bitmap2InputStream(bitmap);
	}

	// InputStream转换成Drawable
	public static Drawable InputStream2Drawable(InputStream is) {
		Bitmap bitmap = InputStream2Bitmap(is);
		return bitmap2Drawable(bitmap);
	}

	// Drawable转换成byte[]
	public static byte[] Drawable2Bytes(Drawable d) {
		Bitmap bitmap = drawable2Bitmap(d);
		return Bitmap2Bytes(bitmap);
	}

	// byte[]转换成Drawable
	public static Drawable Bytes2Drawable(byte[] b) {
		Bitmap bitmap = Bytes2Bitmap(b);
		return bitmap2Drawable(bitmap);
	}

	// Bitmap转换成byte[]
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// byte[]转换成Bitmap
	public static Bitmap Bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	// Drawable转换成Bitmap
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
								: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	// Bitmap转换成Drawable
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}
	/*//顺时针翻转90度
	public static Bitmap rotate90(Bitmap bitmap){
		Matrix matrix = new Matrix();   
		matrix.postRotate(90);        //翻转90度
		int width = bitmap.getWidth();  
		int height = bitmap.getHeight();  
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true); 
		return bitmap;
	}*/
	/** 
	 * 读取图片属性：旋转的角度 
	 * @param path 图片绝对路径 
	 * @return degree旋转的角度 
	 */  
	public static int readPictureDegree(String path) {  
		int degree  = 0;  
	    try {  
            ExifInterface exifInterface = new ExifInterface(path);  
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
            switch (orientation) {  
            case ExifInterface.ORIENTATION_ROTATE_90:  
                    degree = 90;  
                    break;  
            case ExifInterface.ORIENTATION_ROTATE_180:  
                    degree = 180;  
                    break;  
            case ExifInterface.ORIENTATION_ROTATE_270:  
                    degree = 270;  
                    break;  
            }  
	     } catch (IOException e) {  
	         e.printStackTrace();  
	     }  
	     return degree;  
	}
	/**
	* 旋转图片 
	* @param angle 
	* @param bitmap 
	* @return Bitmap 
	*/  
	public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {  
	    //旋转图片 动作   
	    Matrix matrix = new Matrix();
	    matrix.postRotate(angle);  
	    System.out.println("angle2=" + angle);  
	    // 创建新的图片   
	    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
	             bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
	    return resizedBitmap;
	}
	
}
