package com.safetys.zatgov.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.WelcomePagerAdapter;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
 */
public class ViewPhotosActivity extends Activity {

    @BindView(R.id.vp_show_page)
    ViewPager vpShowPage;
    @BindView(R.id.tv_image_positon)
    TextView tvImagePositon;
    // 位移量
    private int offset;
    // 记录当前的位置
    private int curPos = 0;
    private int imageNum ;
    private List<View> guides = new ArrayList<View>();
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);
        ButterKnife.bind(this);
      /*  getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                android.R.drawable.ic_dialog_info);*/
        init();
    }

    private void init() {
        arrayList = getIntent().getStringArrayListExtra("picPaths");
        imageNum = arrayList.size();
        tvImagePositon.setText(1+"/"+imageNum);
        for (int i = 0; i < arrayList.size(); i++) {
            ImageView iv = new ImageView(this);
            x.image().bind(iv, arrayList.get(i));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            guides.add(iv);
        }
        // pic = ImageUtil.getImage(picPath);
        // mPhotoShow.setImageBitmap(pic);
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(guides);
        vpShowPage.setAdapter(adapter);
        vpShowPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                tvImagePositon.setText(arg0+1+"/"+imageNum);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {


            }

            @Override
            public void onPageScrollStateChanged(int arg0) {


            }
        });
       // DialogUtils.cancelProgressDialog();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  DialogUtil.cancelProgressDialog();
    }
}
