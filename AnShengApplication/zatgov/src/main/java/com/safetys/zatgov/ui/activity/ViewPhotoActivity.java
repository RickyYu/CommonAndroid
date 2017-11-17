package com.safetys.zatgov.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.utils.DialogUtil;

import org.xutils.x;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:图片查看
 */
public class ViewPhotoActivity extends Activity {
    private ImageView mPhotoShow;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_view_photo);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                android.R.drawable.ic_dialog_info);
        init();
    }

    private void init() {
        mPhotoShow = (ImageView) findViewById(R.id.photo);
        String picPath = getIntent().getStringExtra("picPath");
        x.image().bind(mPhotoShow, picPath);
        DialogUtil.cancelProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtil.cancelProgressDialog();
    }

}
