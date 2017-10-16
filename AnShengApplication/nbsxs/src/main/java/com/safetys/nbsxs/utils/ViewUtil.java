package com.safetys.nbsxs.utils;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;


public class ViewUtil {

	/**
	 * 设置View的边框以及底色
	 * 给view设置边框以及底色
	 * */
	public static void setEdging(View view,Context mContext ,int backgroundColorId, int edgingColorId) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE); // 画框
		drawable.setCornerRadii(new float[]{9,9,9,9,9,9,9,9});
		drawable.setStroke(1, mContext.getResources().getColor(edgingColorId)); // 边框粗细及颜色
		drawable.setColor(mContext.getResources().getColor(backgroundColorId)); // 边框内部颜色
		view.setBackgroundDrawable(drawable); // 设置背景（效果就是有边框及底色）
	}
	
}
