package com.safetys.zatgov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.entity.InformationDataVo;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class InfomationListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<InformationDataVo> mDatas;

    public InfomationListAdapter(Context context,ArrayList<InformationDataVo> mdatas) {
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
            convertView = mInflater.inflate(R.layout.list_view_img_and_string_item, null);
            ViewHodler mHodler = new ViewHodler();
            mHodler.mTextView1 = (TextView) convertView.findViewById(R.id.text1);
            mHodler.mImageView = (ImageView) convertView.findViewById(R.id.left_img);
            convertView.setTag(mHodler);
        }
        ViewHodler mVH = (ViewHodler) convertView.getTag();
        mVH.mTextView1.setText(mDatas.get(position).getName());
        mVH.mImageView.setBackgroundResource(mDatas.get(position).getImgId());
        return convertView;
    }

    private class ViewHodler{
        ImageView mImageView;
        TextView mTextView1;
    }
}
