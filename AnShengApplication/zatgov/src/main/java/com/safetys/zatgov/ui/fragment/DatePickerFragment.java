package com.safetys.zatgov.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.safetys.widget.common.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	TheListener listener;
	String type = null;
	Context context;
	String error = "";

	public interface TheListener {
		public void returnDate(String date);
	}

	public DatePickerFragment(TheListener listener) {
		this.listener = listener;
	}

	@SuppressLint("ValidFragment")
	public DatePickerFragment(TheListener listener, String type,
			Context context, String error) {
		this.listener = listener;
		this.type = type;
		this.context = context;
		this.error = error;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdf.format(c.getTime());
		if (listener != null) {
			if (type == null) {
				listener.returnDate(formattedDate);
			} else if (type.equals("luru")) {
				if (checktime(formattedDate)) {
					ToastUtils.showMessage(context, error);
					listener.returnDate("");
				} else {
					listener.returnDate(formattedDate);
				}
			}

		}
	}

	private boolean checktime(String time) {
		try {
			Date curDate = new Date(System.currentTimeMillis());
			Date beginDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(time.trim() + " 00:00:00");
			// 判断当前是否为同一天
			Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(time.trim() + " 23:59:59");
			if (endDate.after(curDate)) {
				return false;
			} else if (curDate.after(beginDate) && curDate.before(endDate)) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;

	}

}
