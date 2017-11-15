package com.safetys.zatgov.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.safetys.zatgov.R;
import com.safetys.zatgov.ui.activity.LoginActivity;

/**
 * 基本 Dialog 工具
 * 
 * @author hehc@safetys.cn
 * 
 */
public class DialogUtil {

	private static ProgressDialog dialog;

	// AlertDialog 上次弹出时间
	private static long lastClickTime;

	private static Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (dialog != null)
				dialog.cancel();
			dialog = null;
		}
	};

	private static com.safetys.zatgov.ui.view.Dialog  mDialog;

	public static void showProgressDialog(Context context, int title,
			int message) {
		dialog = new ProgressDialog(context);
		dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		dialog.setTitle(title);
		dialog.setIndeterminate(false);// 进度条是否明确进度
		dialog.setMessage(context.getString(message));
		dialog.show();
	}

	public static void showProgressDialog(Context context, String title,
			String message) {
		dialog = new ProgressDialog(context);
		dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
		dialog.setTitle(title);
		dialog.setIndeterminate(false);// 进度条是否明确进度
		dialog.setMessage(message);
		dialog.show();
	}

	public static void showDefaultProgressDialog(Context context) {
		showProgressDialog(context, "提示", "加载中...");
	}

	public static void cancelProgressDialog() {
		mHandler.sendEmptyMessage(0);
	}

	public static boolean isProgressCanceled() {
		if (dialog == null || !dialog.isShowing()) {
			return true;
		}
		return false;
	}

	public static void showDefaultAlertDialog(Context context, int title,
			int message, DialogInterface.OnClickListener positiveListener) {
		if (!isFastDoubleClick()) {
			getDefaultAlertDialog(context, title, message, positiveListener)
					.show();
		}
	}

	public static void showDefaultAlertDialog(Context context, String title,
			String message, DialogInterface.OnClickListener positiveListener) {
		if (!isFastDoubleClick()) {
			getDefaultAlertDialog(context, title, message, positiveListener)
					.show();
		}
	}

	public static void showDefaultAlertDialog_oneb(Context context,
			String title, String message,
			DialogInterface.OnClickListener positiveListener) {
		if (!isFastDoubleClick()) {
			getDefaultAlertDialog_oneb(context, title, message,
					positiveListener).show();
		}
	}

	public static Dialog getDefaultAlertDialog(Context context, String title,
			String message, DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setPositiveButton("是", positiveListener);
		builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		return builder.create();
	}

	public static Dialog getDefaultAlertDialog_oneb(Context context,
			String title, String message,
			DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setPositiveButton("确定", positiveListener);
		return builder.create();
	}

	public static Dialog getDefaultAlertDialog(Context context, int title,
			int message, DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setPositiveButton("是", positiveListener);
		builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		return builder.create();
	}

	public static void showSystemAlertDialog(Context context, int title,
			int message, DialogInterface.OnClickListener positiveListener) {
		if (!isFastDoubleClick()) {
			Dialog dialog = getDefaultAlertDialog(context, title, message,
					positiveListener);
			// 此参数设置需要权限android.permission.SYSTEM_ALERT_WINDOW，弹出框建立在系统层面
			dialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialog.show();
		}
	}

	public static void showSystemAlertDialog(Context context, String title,
			String message, DialogInterface.OnClickListener positiveListener) {
		if (!isFastDoubleClick()) {
			Dialog dialog = getDefaultAlertDialog(context, title, message,
					positiveListener);
			dialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialog.show();
		}
	}
	public static void showlistDialog(Context context, String title,
			View inflate, DialogInterface.OnClickListener positiveListener) {
		if (!isFastDoubleClick()) {
			Dialog dialog = getlistDialog(context, title, inflate,
					positiveListener);
			dialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialog.show();
		}
	}
	
	public static Dialog getlistDialog(Context context,
			String title, View inflate,
			DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setView(inflate);
		//builder.setIcon(android.R.drawable.ic_dialog_alert);
		return builder.create();
	}
	public static void showlistDialog_button(Context context, String title,
			View inflate, DialogInterface.OnClickListener positiveListener) {
		if (!isFastDoubleClick()) {
			mHandler.sendEmptyMessage(0);
			Dialog dialog = getlistDialog_button(context, title, inflate,
					positiveListener);
			dialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialog.show();
		}
	}
	
	public static Dialog getlistDialog_button(Context context,
			String title, View inflate,
			DialogInterface.OnClickListener positiveListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setView(inflate);
		//builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setPositiveButton("确定", positiveListener);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		return builder.create();
	}

	/**
	 * 连续两次操作间隔太小，不执行操作
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 1000) {// 控制 800 ms
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 和服务器通信后onFile后提示框
	 * 
	 * @param mActivity
	 */
	public static void showMsgDialog(final Activity mActivity,
			final String hintString, boolean onFinishClick,
			OnClickListener mOnClickListener) {

		try {
			if (mDialog != null) {
				mDialog.dismiss();
			}

		} catch (Exception e) {

		}

		try {

			mDialog = new com.safetys.zatgov.ui.view.Dialog(mActivity,
					"提示", hintString);
			if (hintString.equals(mActivity
					.getString(R.string.account_security_tip))) {
				mDialog.setOnAcceptButtonClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mActivity,
								LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK);
						mActivity.startActivity(intent);
					}
				});

			} else {

				if (onFinishClick) {
					mDialog.setOnAcceptButtonClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							mActivity.finish();
						}
					});
				} else {
					mDialog.setOnAcceptButtonClickListener(new OnClickListener() {

						@TargetApi(23)
						@Override
						public void onClick(View v) {
							if (hintString.equals(mActivity
									.getString(R.string.account_security_tip))) {
								Intent intent = new Intent(mActivity,
										LoginActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
										| Intent.FLAG_ACTIVITY_NEW_TASK);
								mActivity.startActivity(intent);
							}
						}
					});
				}
			}
			if (mOnClickListener != null) {
				mDialog.setOnAcceptButtonClickListener(mOnClickListener);
			}
			mDialog.setCancelable(false);
			mDialog.show();
		} catch (Exception e) {

		}
	}

	/**
	 * 带取消按钮的提示框
	 * 
	 * @param mActivity
	 */
	public static void showMsgDialog2(final Activity mActivity,
			String hintString, String cancelStr, boolean onFinishClick,
			OnClickListener mOnClickListener) {
		try {
			com.safetys.zatgov.ui.view.Dialog mDialog = new com.safetys.zatgov.ui.view.Dialog(
					mActivity, "提示", hintString);
			if (onFinishClick) {
				mDialog.setOnAcceptButtonClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						mActivity.finish();
					}
				});
			}
			if (mOnClickListener != null) {
				mDialog.setOnAcceptButtonClickListener(mOnClickListener);
			}
			mDialog.addCancelButton(cancelStr);
			mDialog.setCancelable(false);
			mDialog.show();
		} catch (Exception e) {

		}
	}

	/**
	 * 带取消按钮的提示框
	 * 
	 * @param mContext
	 */
	public static void showMsgDialog2(final Context mContext,
			String hintString, String cancelStr,
			OnClickListener mOnClickListener) {
		try {
			com.safetys.zatgov.ui.view.Dialog mDialog = new com.safetys.zatgov.ui.view.Dialog(
					mContext, "提示", hintString);
			if (mOnClickListener != null) {
				mDialog.setOnAcceptButtonClickListener(mOnClickListener);
			}
			mDialog.addCancelButton(cancelStr);
			mDialog.setCancelable(false);
			mDialog.show();
		} catch (Exception e) {

		}
	}
	
	/**
	 * 带加入取消按钮的click事件
	 * 
	 * @param mContext
	 */
	public static void showMsgDialogWithCancel(final Context mContext,
			String hintString, String cancelStr,
			OnClickListener mOnClickListener,OnClickListener mCancelClickListener) {
		try {
			com.safetys.zatgov.ui.view.Dialog mDialog = new com.safetys.zatgov.ui.view.Dialog(
					mContext, "提示", hintString);
			if (mOnClickListener != null) {
				mDialog.setOnAcceptButtonClickListener(mOnClickListener);
				mDialog.setOnCancelButtonClickListener(mCancelClickListener);
			}
			mDialog.addCancelButton(cancelStr);
			mDialog.setCancelable(false);
			mDialog.show();
		} catch (Exception e) {

		}
	}

	/**
	 * 带取消按钮的提示框
	 * 
	 * @param mContext
	 */
	@TargetApi(23)
	public static void showSystemMsgDialog(final Context mContext,
			String hintString, String cancelStr,
			OnClickListener mOnClickListener) {
		if (Build.VERSION.SDK_INT >= 23) {
			if (!Settings.canDrawOverlays(mContext)) {
				Intent intent = new Intent(
						Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
				return;
			} else {
		try {
			com.safetys.zatgov.ui.view.Dialog mDialog = new com.safetys.zatgov.ui.view.Dialog(
					mContext, "提示", hintString);
			if (mOnClickListener != null) {
				mDialog.setOnAcceptButtonClickListener(mOnClickListener);
			}
			mDialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			mDialog.addCancelButton(cancelStr);
			mDialog.setCancelable(false);
			mDialog.show();
		} catch (Exception e) {

		}
			}
		} else {
			try {
				com.safetys.zatgov.ui.view.Dialog mDialog = new com.safetys.zatgov.ui.view.Dialog(
						mContext, "提示", hintString);
				if (mOnClickListener != null) {
					mDialog.setOnAcceptButtonClickListener(mOnClickListener);
				}
				mDialog.getWindow().setType(
						WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				mDialog.addCancelButton(cancelStr);
				mDialog.setCancelable(false);
				mDialog.show();
			} catch (Exception e) {

			}
		}

	}
}
