package com.safetys.nbsxs.ui.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safetys.nbsxs.R;

/**
 * @author sjw
 * 图片选择选择方式选择控件
 * 拍照or图库选择
 */
public class PhotoChooseView extends RelativeLayout{
	
	public static final int CHOOSE_TYPE_CAMERA = 0x1;//拍照方式
	public static final int CHOOSE_TYPE_GET_PHOTO = 0x2;//选图方式
	
	
	private View view_all;
	private View view_choose;
	private TextView mTv_takePhoto;
	private TextView mTv_choosePhoto;
	private TextView mTv_cancle;
	
	private onPhotoChooseListener mListener;
	/**
	 * @author sjw
	 * 图片获取方式选择
	 */
	public interface onPhotoChooseListener{
		void onChoose(int typeCode);
	}

	public PhotoChooseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View mView = LayoutInflater.from(context).inflate(R.layout.photo_choose_view, this);
		mTv_takePhoto = (TextView) mView.findViewById(R.id.tv_takephoto);
		mTv_choosePhoto = (TextView) mView.findViewById(R.id.tv_choose_photo);
		mTv_cancle = (TextView) mView.findViewById(R.id.tv_cancle);
//		view_all = mView.findViewById(R.id.view_photo_choose);
		view_choose = mView.findViewById(R.id.view_choose);
		mTv_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//隐藏本身
				hide();
			}
		});
		view_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//隐藏本身
				hide();
			}
		});
		mTv_choosePhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//选择照片
				if(mListener!=null){
					mListener.onChoose(CHOOSE_TYPE_GET_PHOTO);
				}
				hide();
			}
		});
		mTv_takePhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//拍照
				if(mListener!=null){
					mListener.onChoose(CHOOSE_TYPE_CAMERA);
				}
				hide();
			}
		});
	}
	
	/**
	 * 显示界面
	 */
	public void show(){
		view_all.setVisibility(View.VISIBLE);
		ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view_choose, "ScaleY", 0,0.6f,1.0f);
        scaleYAnimator.setInterpolator(new AccelerateInterpolator());
        
        ObjectAnimator alphaAni = ObjectAnimator.ofFloat(view_all, "Alpha", 0.5f, 1f);
        view_choose.setPivotY(view_choose.getMeasuredHeight());
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(scaleYAnimator).with(alphaAni);
        animSet.setDuration(100);
        animSet.start();
	}
	
	/**
	 * 隐藏界面
	 */
	public void hide(){
		
		ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view_choose, "ScaleY", 1f,0.6f,0f);
        scaleYAnimator.setInterpolator(new AccelerateInterpolator());
        
        ObjectAnimator alphaAni = ObjectAnimator.ofFloat(view_all, "Alpha", 1f, 0.5f);
        
        view_choose.setPivotY(view_choose.getMeasuredHeight());
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(scaleYAnimator).with(alphaAni);
        animSet.setDuration(100);
        animSet.start();
        animSet.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				setVisibility(View.INVISIBLE);
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				view_all.setVisibility(View.INVISIBLE);
			}
		});
	}
	
	/**
	 * @return 是否显示
	 */
	public boolean isShow(){
		return view_all.getVisibility()== View.VISIBLE;
	}
	
	public void setListener(onPhotoChooseListener mListener) {
		this.mListener = mListener;
	}

}
