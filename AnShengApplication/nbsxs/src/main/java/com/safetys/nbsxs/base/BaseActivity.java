package com.safetys.nbsxs.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.safetys.nbsxs.R;
import com.safetys.nbsxs.ui.activity.ViewPhotoActivity;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

/**
 * Author:Created by Ricky on 2017/10/13.
 * Email:584182977@qq.com
 * Description:
 */
public class BaseActivity extends Activity {
    private ImageOptions mImageOptions;//加载图片的格式设置
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //默认不弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * 设置标题
     * @param title
     */
    public void setHeadTitle(String title){
        TextView mTextView = (TextView) findViewById(R.id.title);
        if(mTextView!=null){
            mTextView.setText(title);
        }
    }

    /**
     * 返回按钮监听
     */
    public void setBackBtnClick(View.OnClickListener mListener){
        findViewById(R.id.btn_back).setOnClickListener(mListener);
    }

    /**
     * 右侧按钮文字设置
     */
    public void setRightTitle(String right){
        TextView rightTitle=(TextView) findViewById(R.id.title_right);
        if (rightTitle!=null) {
            rightTitle.setVisibility(View.VISIBLE);
            rightTitle.setText(right);
        }else{
            rightTitle.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 右侧文字按钮监听
     */
    public void setRightBtnClick(View.OnClickListener mListener){
        findViewById(R.id.title_right).setOnClickListener(mListener);
    }

    /**
     * 设置右侧按钮背景
     * @param backgroundId
     */
    public void setRightBtnImage(int backgroundId){
        ImageView mImageView = (ImageView) findViewById(R.id.iv_right);
        mImageView.setBackgroundResource(backgroundId);
        mImageView.setVisibility(View.VISIBLE);
    }

    /**
     * 右侧背景按钮监听
     */
    public void setRightImgBtnClick(View.OnClickListener mListener){
        findViewById(R.id.iv_right).setOnClickListener(mListener);
    }

    public ImageOptions getImageOptions(){
        if(mImageOptions==null){
            mImageOptions = new ImageOptions.Builder()
                    .setSize(DensityUtil.dip2px(150), DensityUtil.dip2px(198))
                    .setRadius(DensityUtil.dip2px(5))
                    // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setCrop(false) // 很多时候设置了合适的scaleType也不需要它.
                    // 加载中或错误图片的ScaleType
                    //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .build();
        }
        return mImageOptions;
    }

    // 弹出查看照片TODO
    public void showPicture(String picPath) {
        Intent intent = new Intent(this, ViewPhotoActivity.class);
        intent.putExtra("picPath", picPath);
        startActivity(intent);
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
}
