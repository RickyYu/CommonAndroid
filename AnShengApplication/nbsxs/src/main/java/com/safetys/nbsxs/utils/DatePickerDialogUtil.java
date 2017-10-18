package com.safetys.nbsxs.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Build;

import java.util.Calendar;

/**
 * 时间选择器帮助类
 */
public class DatePickerDialogUtil {

	public static void showDialog(Activity mActivity,OnDateSetListener mListener){
		Calendar c = Calendar.getInstance();
		showDialog(mActivity,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),mListener);
	}
	
	public static void showDialog(Activity mActivity,int year,int month,int day,OnDateSetListener mListener){
		int theme = AlertDialog.THEME_HOLO_LIGHT;
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
			theme = android.R.style.Theme_Material_Light_Dialog_Alert;
		}
		DatePickerDialog dialog = new DatePickerDialog(mActivity,theme,mListener, year, // 传入年份
				month, // 传入月份
				day // 传入天数
		);
		dialog.show();
	}
}
