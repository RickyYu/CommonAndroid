package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import com.github.paolorotolo.appintro.AppIntro;
import com.safetys.zatgov.R;
import com.safetys.zatgov.ui.fragment.SlideFragment;
import org.xutils.common.util.LogUtil;

/**
 * Author:Created by Ricky on 2017/11/28.
 * Description:
 */
public class AppIntroActivity extends AppIntro {
    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(SlideFragment.newInstance(R.layout.activity_intro));
        addSlide(SlideFragment.newInstance(R.layout.activity_intro));
        addSlide(SlideFragment.newInstance(R.layout.activity_intro));
        addSlide(SlideFragment.newInstance(R.layout.activity_intro));
       // setBarColor(getResources().getColor(R.color.white));
      //  setSeparatorColor(getResources().getColor(R.color.blue));
        setVibrateIntensity(30);
        setSkipText("跳转");
        setDoneText("进入");
    }

    private void startMain(){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
    @Override
    public void onSkipPressed() {
        LogUtil.e("onSkipPressed");
        startMain();
    }

    @Override
    public void onDonePressed() {
        LogUtil.e("onDonePressed");
        startMain();
    }

    @Override
    public void onSlideChanged() {
        LogUtil.e("onSlideChanged");
    }

    @Override
    public void onNextPressed() {
        LogUtil.e("onNextPressed");

    }

}
