package com.safetys.zatgov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.entity.GridBtnData;
import com.safetys.zatgov.ui.activity.MainWanggyActivity;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class MyGridAdapter extends BaseAdapter {
    public Context mContext;
    public LayoutInflater mInflater;
    public ArrayList<GridBtnData> mDatas;

    public MyGridAdapter(Context context, ArrayList<GridBtnData> mDatas) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDatas = mDatas;
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
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.grid_viwe_law_enforcement_item, null);
            ViewHodler mHodler = new ViewHodler();
            mHodler.mImageView = (ImageView) convertView
                    .findViewById(R.id.imageview);
            mHodler.mImageUnread = convertView
                    .findViewById(R.id.img_unread);
            mHodler.mTextView = (TextView) convertView
                    .findViewById(R.id.text_describe);
            mHodler.mUnread = (TextView) convertView
                    .findViewById(R.id.text_unread);
            convertView.setTag(mHodler);
        }
        ViewHodler mVH = (ViewHodler) convertView.getTag();
        mVH.mImageView.setImageResource(mDatas.get(position).resourceId);
        mVH.mTextView.setText(mDatas.get(position).btnString);

        if(mContext.getClass().equals(new MainWanggyActivity().getClass())){
            mVH.mTextView.setTextColor(mContext.getResources().getColor(R.color.white));

        }

        if (mDatas.get(position).unReadNum > 0) {
            mVH.mUnread.setText("" + mDatas.get(position).unReadNum);
            mVH.mImageUnread.setVisibility(View.VISIBLE);
        } else {
            mVH.mUnread.setText("");
            mVH.mImageUnread.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHodler {
        ImageView mImageView;
        View mImageUnread;
        TextView mTextView;
        TextView mUnread;
    }
}
