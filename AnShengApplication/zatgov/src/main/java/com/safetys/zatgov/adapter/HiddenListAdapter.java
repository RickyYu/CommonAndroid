package com.safetys.zatgov.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.HiddenDesInfoVo;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:隐患描述列表数据提供
 */
public class HiddenListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<HiddenDesInfoVo> mDatas;

    public HiddenListAdapter(Context context, ArrayList<HiddenDesInfoVo> mdatas) {
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
                    R.layout.list_view_string_three, null);
            ViewHodler2 mHodler = new ViewHodler2();
            mHodler.mTextView1 = (TextView) convertView
                    .findViewById(R.id.text1);
            mHodler.mTextView2 = (TextView) convertView
                    .findViewById(R.id.text2);
            mHodler.mTextView3 = (TextView) convertView
                    .findViewById(R.id.text3);
            convertView.setTag(mHodler);
        }
        ViewHodler2 mVH = (ViewHodler2) convertView.getTag();
        mVH.mTextView1.setText(mDatas.get(position).getContent());
        mVH.mTextView1.setTextColor(Color.rgb(0, 0, 0));
        mVH.mTextView2.setText("录入日期："
                + mDatas.get(position).getCreateTime());
        if (mDatas.get(position).getRepaired()) {
            mVH.mTextView3.setText("已整改");
            mVH.mTextView3.setTextColor(Color.RED);
        } else {
            mVH.mTextView3.setText("未整改");
        }
        return convertView;
    }
    private class ViewHodler2 {
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
    }
}

