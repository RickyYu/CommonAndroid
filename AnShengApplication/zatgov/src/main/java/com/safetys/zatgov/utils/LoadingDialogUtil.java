package com.safetys.zatgov.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safetys.zatgov.R;


public class LoadingDialogUtil {

	private Dialog dialog;
	private String textInfo = null;
	private Context mContext;
	private boolean canCance=false;
	private ImageView spaceshipImage;
	private Animation hyperspaceJumpAnimation;
	
	public LoadingDialogUtil(Context context,String textInfo){
		this.mContext = context;
		this.textInfo = textInfo;
		intiView();
	}
	
	public LoadingDialogUtil(Context context){
		this.mContext = context;
		intiView();
	}
	public LoadingDialogUtil(Context context,boolean canCance){
		this.mContext = context;
		this.canCance=canCance;
		intiView();
	}
	
	private void intiView(){
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.view_waiting_dialog, null);

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		
		if(textInfo!=null){
			TextView mTextView = (TextView)v.findViewById(R.id.textview_loading);
			mTextView.setText(textInfo);
		}
		spaceshipImage = (ImageView) v.findViewById(R.id.img);
		hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mContext,
				R.anim.waiting_dialog_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);

		dialog = new Dialog(mContext,R.style.FullHeightDialog);
		
		dialog.setCancelable(canCance);
		
		dialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}
	
	public void show(){
		if(dialog != null){
			// 使用ImageView显示动画
			spaceshipImage.startAnimation(hyperspaceJumpAnimation);
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
