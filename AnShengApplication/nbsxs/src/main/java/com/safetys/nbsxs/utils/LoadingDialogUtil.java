package com.safetys.nbsxs.utils;


import cn.safetys.nbsxs.R;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class LoadingDialogUtil {

	private Dialog dialog;
	private String textInfo = null;
	private boolean canCance = false;
	private Context mContext;
	
	public LoadingDialogUtil(Context context,String textInfo){
		this.mContext = context;
		this.textInfo = textInfo;
		intiView();
	}
	public LoadingDialogUtil(Context context,boolean canCance){
		this.mContext = context;
		this.canCance=canCance;
		intiView();
	}
	public LoadingDialogUtil(Context context){
		this.mContext = context;
		intiView();
	}
	
	private void intiView(){
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.waiting_dialogview, null);

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		
		if(textInfo!=null){
			TextView mTextView = (TextView)v.findViewById(R.id.textview_loading);
			mTextView.setText(textInfo);
		}

		dialog = new Dialog(mContext,R.style.FullHeightDialog);
		dialog.setCancelable(canCance);
		
		dialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}
	
	public void show(){
		if(dialog != null){
			dialog.show();
		}
	}
	
	public void dismiss(){
		if(dialog != null){
			dialog.dismiss();
		}
	}
	
	public void cancel(){
		if(dialog != null){
			dialog.cancel();
		}
	}
	
	public boolean isShow(){
		if(dialog != null){
			return dialog.isShowing();
		}
		return false;
	}
	
}
