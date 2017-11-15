package com.safetys.zatgov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.MSDSinfo;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class MsdsListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<MSDSinfo> mDatas;

    public MsdsListAdapter(Context context,ArrayList<MSDSinfo> mdatas) {
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

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.list_view_string_and_arrow_item, null);
            ViewHodler mHodler = new ViewHodler();
            mHodler.mTextView1 = (TextView) convertView.findViewById(R.id.text1);
            mHodler.mTextView2 = (TextView) convertView.findViewById(R.id.text2);
            convertView.setTag(mHodler);
        }
        ViewHodler mVH = (ViewHodler) convertView.getTag();
        mVH.mTextView1.setText(mDatas.get(position).getChineseName());
        mVH.mTextView2.setText("英文名："+mDatas.get(position).getEnglishName()+"    CAS号："+mDatas.get(position).getCas());
        return convertView;
    }

    private class ViewHodler{
        TextView mTextView1;
        TextView mTextView2;
    }
}
