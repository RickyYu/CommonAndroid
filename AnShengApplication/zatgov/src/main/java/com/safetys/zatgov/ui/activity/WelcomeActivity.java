package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.safetys.widget.common.SPUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.WelcomePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
 */
public class WelcomeActivity extends BaseActivity {
    // 首次使用程序的显示的欢迎图片
    private int[] ids = { R.drawable.welcome_frist_image,
            R.drawable.welcome_two_image, R.drawable.welcome_three_image,
            R.drawable.welcome_four_image };
    @BindView(R.id.vp_show_page)
    ViewPager vpShowPage;
    @BindView(R.id.lly_dot_ws)
    LinearLayout llyDotWs;
    @BindView(R.id.iv_cur_dot)
    ImageView ivCurDot;

    // 位移量
    private int offset;
    // 记录当前的位置
    private int curPos = 0;
    private List<View> guides = new ArrayList<View>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        if (SPUtils.contains("first")) {
            simpleInitView();
            int num = (int) SPUtils.getData("first", 0);
            SPUtils.saveData("first", num++);
            skipActivity(1);
        } else {
            SPUtils.saveData("first", 1);
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
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        guides.add(iv);
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(guides);
        vpShowPage.setAdapter(adapter);
        //隐藏小圆点
        llyDotWs.setVisibility(View.GONE);
        ivCurDot.setVisibility(View.GONE);
    }

    private void initView() {
        for (int i = 0; i < ids.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(ids[i]);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            guides.add(iv);
        }
        ivCurDot.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        offset = ivCurDot.getWidth();
                        return true;
                    }
                });

        WelcomePagerAdapter adapter = new WelcomePagerAdapter(guides);

        vpShowPage.setAdapter(adapter);
        vpShowPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

/*    @OnPageChange(value = R.id.vp_show_page )
   private void  setOnPageChangeListener(){

   }*/

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
        ivCurDot.startAnimation(anim);
    }


    /**
     * 延迟多少秒进入主界面
     *
     * @param time 秒
     */
    private void skipActivity(int time) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 1000 * time);
    }
}
