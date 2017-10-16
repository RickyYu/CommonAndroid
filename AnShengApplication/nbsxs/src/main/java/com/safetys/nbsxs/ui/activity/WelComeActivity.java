package com.safetys.nbsxs.ui.activity;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import cn.safetys.nbsxs.adapter.WelcomePagerAdapter;
import cn.safetys.nbsxs.base.BaseActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.safetys.nbsxs.R;

/**
 * 欢迎界面
 */
public class WelComeActivity extends BaseActivity {

	// 首次使用程序的显示的欢迎图片
	private int[] ids = { R.drawable.welcome_frist_image,
			R.drawable.welcome_two_image, R.drawable.welcome_three_image };

	SharedPreferences share;
	private List<View> guides = new ArrayList<View>();
	private ViewPager pager;
	private ImageView curDot;
	private View mDots;
	// 位移量
	private int offset;
	// 记录当前的位置
	private int curPos = 0;

	Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		share = getSharedPreferences("showWelcomm", Context.MODE_PRIVATE);
		editor = share.edit();
		// 判断是否首次登录程序
		if (share.contains("shownum")) {
			setContentView(R.layout.activity_welcome);
			simpleInitView();
			int num = share.getInt("shownum", 0);
			editor.putInt("shownum", num++);
			editor.commit();
			skipActivity(1);
		} else {
			editor.putInt("shownum", 1);
			editor.commit();
			setContentView(R.layout.activity_welcome);
			initView();
		}

	}

	private void simpleInitView(){
		//加载一张图片
		ImageView iv = new ImageView(this);
		iv.setImageResource(ids[0]);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		iv.setLayoutParams(params);
		iv.setScaleType(ScaleType.FIT_XY);
		guides.add(iv);
		
		WelcomePagerAdapter adapter = new WelcomePagerAdapter(guides);
		pager = (ViewPager) findViewById(R.id.showwelom_page);
		pager.setAdapter(adapter);
		//隐藏小圆点
		curDot = (ImageView) findViewById(R.id.cur_dot);
		curDot.setVisibility(View.GONE);
		mDots =  findViewById(R.id.dot_ws);
		mDots.setVisibility(View.GONE);
	}
	
	private void initView() {
		for (int i = 0; i < ids.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setImageResource(ids[i]);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			iv.setLayoutParams(params);
			iv.setScaleType(ScaleType.FIT_XY);
			guides.add(iv);
		}
		curDot = (ImageView) findViewById(R.id.cur_dot);
		curDot.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						offset = curDot.getWidth();
						return true;
					}
				});

		WelcomePagerAdapter adapter = new WelcomePagerAdapter(guides);
		pager = (ViewPager) findViewById(R.id.showwelom_page);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				moveCursorTo(arg0);
				if (arg0 == ids.length - 1) {// 到最后一张了
					skipActivity(2);
				}
				curPos = arg0;
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}

		});

	}

	/**
	 * 移动指针到相邻的位置
	 * 
	 * @param position
	 *            指针的索引值
	 * */
	private void moveCursorTo(int position) {
		TranslateAnimation anim = new TranslateAnimation(offset * curPos,
				offset * position, 0, 0);
		anim.setDuration(300);
		anim.setFillAfter(true);
		curDot.startAnimation(anim);
	}


	/**
	 * 延迟多少秒进入主界面
	 * @param min 秒
	 */
	private void skipActivity(int min) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(WelComeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				WelComeActivity.this.finish();
			}
		}, 1000*min);
	}

}

