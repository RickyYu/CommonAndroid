package com.safetys.nbsxs.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.safetys.nbsxs.R;


public class SearchBar extends LinearLayout implements View.OnClickListener {

    private Button mBtn_Search;
    private ImageView iv_delete;
    private EditText mEt_text;
    private onSearchListener mListener;

    public interface onSearchListener {

        public void onSearchButtonClick(String searchStr);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View mView = LayoutInflater.from(context).inflate(R.layout.common_search_bar, this);
        mBtn_Search = (Button) mView.findViewById(R.id.btn_search);
        mEt_text = (EditText) mView.findViewById(R.id.et_search_text);
        mBtn_Search.setOnClickListener(this);

        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
    }


    public void setOnSearchListener(onSearchListener mOnSearchListener) {
        this.mListener = mOnSearchListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                mEt_text.clearFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEt_text.getWindowToken(), 0);
                String searchStr = mEt_text.getText().toString();
                if (mListener != null) {
                    mListener.onSearchButtonClick(searchStr);
                }
                break;
            case R.id.iv_delete:
                mEt_text.setText("");
                break;
            default:
                break;
        }
    }

    /**
     * 清除搜索数据
     */
    public void clearData() {
        mEt_text.setText("");
    }

    /**
     * @return 返回搜索的数据
     */
    public String getSearchData() {
        return mEt_text.getText().toString();
    }
}
