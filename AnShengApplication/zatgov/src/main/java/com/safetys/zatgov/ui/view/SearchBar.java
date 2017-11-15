package com.safetys.zatgov.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.safetys.zatgov.R;


public class SearchBar extends LinearLayout implements View.OnClickListener{
	
	private Button mBtn_Search;
	private EditText mEt_text;
	private onSearchListener mListener;
	
	public interface onSearchListener{
		
		public void onSearchButtonClick(String searchStr);
	}
	
	public SearchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		View mView = LayoutInflater.from(context).inflate(R.layout.common_search_bar, this);
		mBtn_Search = (Button) mView.findViewById(R.id.btn_search);
		mEt_text = (EditText) mView.findViewById(R.id.et_search_text);
		mBtn_Search.setOnClickListener(this);
	}
	
	
	public void setOnSearchListener(onSearchListener mOnSearchListener){
		this.mListener = mOnSearchListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			String searchStr = mEt_text.getText().toString();
			if(mListener!=null){
				mListener.onSearchButtonClick(searchStr);
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 清除搜索数据
	 */
	public void clearData(){
		mEt_text.setText("");
	}
	
	public void setHintSearch(String msg){
		mEt_text.setHint(msg);
	}
	
	public void setHintCorlor(int color){
		mEt_text.setHintTextColor(color);
		
	}
	/**
	 * @return 返回搜索的数据
	 */
	public String getSearchData(){
		return mEt_text.getText().toString();
	}
	
	public void setSearchData(String msg){
		mEt_text.setText(msg);
	}
}
