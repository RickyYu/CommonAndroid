package com.safetys.widget.common;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ricky
 * @time 2017-10-24
 */
public class DateParseUtils {

	/**
	 * 时间转换成时间戳
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static long dateToStamp(String time) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateToStamp(time, simpleDateFormat);
	}

	/**
	 * 时间转换成时间戳
	 * 
	 * @param time
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 */
	public static long dateToStamp(String time, SimpleDateFormat dateFormat)
			throws ParseException {
		Date date = dateFormat.parse(time);
		return date.getTime();
	}

	/**
	 * 将时间戳转换为时间
	 * 
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String stampToDate(long time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return stampToDate(time,simpleDateFormat);
	}

	/**
	 * 将时间戳转换为时间
	 * 
	 * @param time
	 * @param dateFormat
	 * @return
	 */
	public static String stampToDate(long time, SimpleDateFormat dateFormat) {
		Date date = new Date(time);
		return dateFormat.format(date);
	}
}
