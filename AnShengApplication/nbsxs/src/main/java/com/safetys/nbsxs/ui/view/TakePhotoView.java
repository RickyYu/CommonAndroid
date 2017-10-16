package com.safetys.nbsxs.ui.view;

import java.io.File;
import java.util.UUID;

import cn.safetys.nbsxs.R;
import cn.safetys.nbsxs.config.AppConfig;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


/**
 * 隐患拍照界面类
 */
public class TakePhotoView extends LinearLayout implements View.OnClickListener{

	protected static int mTakePictureRequestCode = 1;
	
	private Context mContext;
	private LinearLayout mPicsLayout;
	private View iv_photo;
	private File tempFile;// 拍摄缓存照片文件
	
	public TakePhotoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		View mView = LayoutInflater.from(context).inflate(R.layout.take_photo_view, this);
		mPicsLayout =  (LinearLayout) mView.findViewById(R.id.photos);
		iv_photo = mView.findViewById(R.id.iv_photo);
		iv_photo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_photo:// 调用手机系统相机
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			tempFile = new File(AppConfig.buildPath(AppConfig.HOME_CACHE), UUID
					.randomUUID().toString().replace("-", "")
					+ ".jpg");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
			if (intent.resolveActivity(mContext.getPackageManager()) != null) {
				((Activity)mContext).startActivityForResult(intent, mTakePictureRequestCode);
			}
			break;

		default:
			break;
		}
	}
	

}
