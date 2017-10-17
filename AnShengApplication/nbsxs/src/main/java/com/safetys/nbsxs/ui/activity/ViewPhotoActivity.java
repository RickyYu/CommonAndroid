package com.safetys.nbsxs.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.safetys.nbsxs.R;
import com.safetys.nbsxs.base.BaseActivity;

import org.xutils.x;

/**
 * 图片查看
 *
 */
public class ViewPhotoActivity extends BaseActivity {

	private ImageView mPhotoShow;
	
//	private Bitmap pic;

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
//		pic = ImageUtil.getImage(picPath);
//		mPhotoShow.setImageBitmap(pic);
		x.image().bind(mPhotoShow, picPath);
		mPhotoShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		pic.recycle();
//		pic=null;
	}

}
