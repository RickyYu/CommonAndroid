package com.safetys.zatgov.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.utils.LoadingDialogUtil;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
 */
public class BaseActivity extends Activity {
    protected LoadingDialogUtil mLoading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 默认不弹出软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setHeadTitle(String title) {
        TextView mTextView = (TextView) findViewById(R.id.title);
        if (mTextView != null) {
            mTextView.setText(title);
        }
    }

    /**
     * 设置标题右侧
     *
     * @param title
     */
    public void setRightTitle(String title, View.OnClickListener mListener) {
        TextView mTextView = (TextView) findViewById(R.id.title_right);
        if (mTextView != null) {
            mTextView.setText(title);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setOnClickListener(mListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /** 弹出查看照片
     * @param paths  照片地址
     */
    protected void showPictures(List<String> paths) {
        Intent intent = new Intent(this, ViewPhotosActivity.class);
        ArrayList<String> picPaths = new ArrayList<String>();
        for (int i = 0; i < paths.size(); i++) {
            picPaths.add(paths.get(i));
        }
        intent.putStringArrayListExtra("picPaths", picPaths);
        startActivity(intent);
    }

    protected ImageOptions getImageOptions() {
        ImageOptions mImageOptions = null;
        if (mImageOptions == null) {
            mImageOptions = new ImageOptions.Builder()
                    .setSize(DensityUtil.dip2px(300), DensityUtil.dip2px(397))
                    .setRadius(DensityUtil.dip2px(5))
                    // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setCrop(false) // 很多时候设置了合适的scaleType也不需要它.
                    // 加载中或错误图片的ScaleType
                    // .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP).build();
        }
        return mImageOptions;
    }

}
