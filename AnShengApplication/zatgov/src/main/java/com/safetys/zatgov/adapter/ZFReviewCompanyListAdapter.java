package com.safetys.zatgov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.ReviewInfoGov;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:企业列表Adapter
 */
public class ZFReviewCompanyListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ReviewInfoGov> mDatas;

    public ZFReviewCompanyListAdapter(Context context, ArrayList<ReviewInfoGov> mdatas) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDatas = mdatas;
    }

    @Override
    public int getCount() {

        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {

        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater
                    .inflate(
                            R.layout.list_view_string_and_arrow_and_date_item,
                            null);
            ViewHodler mHodler = new ViewHodler();
            mHodler.mTextView1 = (TextView) convertView
                    .findViewById(R.id.text1);
            mHodler.mTextView2 = (TextView) convertView
                    .findViewById(R.id.text2);
            mHodler.mTextView3 = (TextView) convertView
                    .findViewById(R.id.text3);

            convertView.setTag(mHodler);
        }
        ViewHodler mVH = (ViewHodler) convertView.getTag();
        mVH.mTextView1.setText(mDatas.get(position).getCompanyName());
        mVH.mTextView2.setText("未整改隐患数量("+mDatas.get(position).getHiddenNum()+")个");
        mVH.mTextView3.setText("责令整改日期："+mDatas.get(position).getCleanUpTimeLimit());


        return convertView;
    }
    private class ViewHodler {
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
    }
}
