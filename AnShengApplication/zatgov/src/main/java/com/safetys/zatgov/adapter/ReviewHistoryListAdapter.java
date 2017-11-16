package com.safetys.zatgov.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.ReviewHistoryInfo;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class ReviewHistoryListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ReviewHistoryInfo> mDatas;

    public ReviewHistoryListAdapter(Context context,
                                    ArrayList<ReviewHistoryInfo> mdatas) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDatas = mdatas;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_string_three_and_arrow_and_date_item,
                    null);
            ViewHodler mHodler = new ViewHodler();
            mHodler.mTextView1 = (TextView) convertView
                    .findViewById(R.id.text1);
            mHodler.mTextView2 = (TextView) convertView
                    .findViewById(R.id.text2);
            mHodler.mTextView3 = (TextView) convertView
                    .findViewById(R.id.text3);
            mHodler.mTextView4 = (TextView) convertView
                    .findViewById(R.id.text4);
            convertView.setTag(mHodler);
        }
        ViewHodler mVH = (ViewHodler) convertView.getTag();
        mVH.mTextView1.setText("复查情况：" + mDatas.get(position).getHiddenState());
        mVH.mTextView1.setTextColor(Color.rgb(0, 0, 0));
        mVH.mTextView2.setText("复查日期：" + mDatas.get(position).getCallbackTime());
        // mVH.mTextView3.setText("责令整改日期：" +
        // mDatas.get(position).getCallbackTime());
        mVH.mTextView4.setText("复查人：" + mDatas.get(position).getExecuter());

        return convertView;
    }

    private class ViewHodler {
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        TextView mTextView4;
    }
}
