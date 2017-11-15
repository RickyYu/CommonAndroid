package com.safetys.zatgov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.ReadInfo;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class LawRegulationListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ReadInfo> mDatas;

    public LawRegulationListAdapter(Context context,ArrayList<ReadInfo> mdatas) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.list_view_string_and_arrow_and_date_item, null);
            ViewHodler mHodler = new ViewHodler();
            mHodler.mTextView1 = (TextView) convertView.findViewById(R.id.text1);
            mHodler.mTextView2 = (TextView) convertView.findViewById(R.id.text2);
            mHodler.mTextView3 = (TextView) convertView.findViewById(R.id.text3);
            convertView.setTag(mHodler);
        }
        ViewHodler mVH = (ViewHodler) convertView.getTag();
        mVH.mTextView1.setText(mDatas.get(position).getCaption());
        mVH.mTextView2.setText(mDatas.get(position).getPublisher());
        mVH.mTextView3.setText(mDatas.get(position).getCreatetime());
        return convertView;
    }

    private class ViewHodler{
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
    }

}
