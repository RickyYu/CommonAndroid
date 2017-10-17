package com.safetys.nbsxs.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.safetys.nbsxs.R;
import com.safetys.nbsxs.adapter.AbstractWheelTextAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 单级
 * 
 */
public class SigleWheelDialog extends Dialog implements View.OnClickListener {

	private WheelView mWheelView;
	private View lyView;
	private View lyViewChild;
	private TextView btnSure;
	private TextView btnCancel;

	private Context context;

	private List<String> mDatas = new ArrayList<String>();
	private AddressTextAdapter mAdapter;

	private String strText = "";
	private OnTextCListener onTextListener;

	private int maxsize = 24;
	private int minsize = 14;

	public SigleWheelDialog(Context context,List<String> mDatas) {
		super(context, R.style.ShareDialog);
		this.context = context;
		this.mDatas = mDatas;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_myinfo_sigle_wheel);

		mWheelView = (WheelView) findViewById(R.id.wv_address_province);
		lyView = findViewById(R.id.ly_myinfo_view);
		lyViewChild = findViewById(R.id.ly_myinfo_sigletext_child);
		btnSure = (TextView) findViewById(R.id.btn_myinfo_sure);
		btnCancel = (TextView) findViewById(R.id.btn_myinfo_cancel);

		lyView.setOnClickListener(this);
		lyViewChild.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		mAdapter = new AddressTextAdapter(context, mDatas, getTextItem(strText), maxsize, minsize);
		mWheelView.setVisibleItems(5);
		mWheelView.setViewAdapter(mAdapter);
		mWheelView.setCurrentItem(getTextItem(strText));


		mWheelView.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String currentText = (String) mAdapter.getItemText(wheel.getCurrentItem());
				strText = currentText;
				setTextviewSize(currentText, mAdapter);
			}
		});

		mWheelView.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				String currentText = (String) mAdapter.getItemText(wheel.getCurrentItem());
				strText = currentText;
				setTextviewSize(currentText, mAdapter);
			}
		});
		
	}

	private class AddressTextAdapter extends AbstractWheelTextAdapter {
		List<String> list;

		protected AddressTextAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
			super(context, R.layout.item_wheel_view, NO_RESOURCE, currentItem, maxsize, minsize);
			this.list = list;
			setItemTextResource(R.id.tempValue);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index) + "";
		}
	}

	/**
	 * 设置字体大小
	 * 
	 * @param curriteItemText
	 * @param adapter
	 */
	public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
		ArrayList<View> arrayList = adapter.getTestViews();
		int size = arrayList.size();
		String currentText;
		for (int i = 0; i < size; i++) {
			TextView textvew = (TextView) arrayList.get(i);
			currentText = textvew.getText().toString();
			if (curriteItemText.equals(currentText)) {
				textvew.setTextSize(24);
			} else {
				textvew.setTextSize(14);
			}
		}
	}

	public void setAddresskListener(OnTextCListener onAddressCListener) {
		this.onTextListener = onAddressCListener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btnSure) {
			if (onTextListener != null) {
				onTextListener.onClick(strText);
			}
		} else if (v == btnCancel) {

		}else {
			dismiss();
		}
		dismiss();
	}

	/**
	 * 回调接口
	 * 
	 * @author Administrator
	 *
	 */
	public interface OnTextCListener {
		public void onClick(String mText);
	}

	/**
	 * 初始化
	 * 
	 */
	public void setText(String mtext) {
		if (mtext != null && mtext.length() > 0) {
			this.strText = mtext;
		}
	}

	/**
	 * 返回索引
	 * 
	 */
	public int getTextItem(String mtext) {
		int size = mDatas.size();
		int provinceIndex = 0;
		boolean noprovince = true;
		for (int i = 0; i < size; i++) {
			if (mtext.equals(mDatas.get(i))) {
				noprovince = false;
				return provinceIndex;
			} else {
				provinceIndex++;
			}
		}
		if (noprovince) {
			strText = "";
			return 22;
		}
		return provinceIndex;
	}


}