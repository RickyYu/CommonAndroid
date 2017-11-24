package com.safetys.zatgov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.entity.GridBtnData;
import com.safetys.zatgov.ui.activity.MainWanggyActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        ViewHolder mHodler;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.grid_viwe_law_enforcement_item, null);
            mHodler = new ViewHolder(convertView);
            convertView.setTag(mHodler);
        } else {
            mHodler = (ViewHolder) convertView.getTag();
        }

        mHodler.imageview.setImageResource(mDatas.get(position).resourceId);
        mHodler.textDescribe.setText(mDatas.get(position).btnString);

        if (mContext.getClass().equals(new MainWanggyActivity().getClass())) {
            mHodler.textDescribe.setTextColor(mContext.getResources().getColor(R.color.white));

        }

        if (mDatas.get(position).unReadNum > 0) {
            mHodler.textUnread.setText("" + mDatas.get(position).unReadNum);
            mHodler.imgUnread.setVisibility(View.VISIBLE);
        } else {
            mHodler.textUnread.setText("");
            mHodler.imgUnread.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageview)
        ImageView imageview;
        @BindView(R.id.text_describe)
        TextView textDescribe;
        @BindView(R.id.text_unread)
        TextView textUnread;
        @BindView(R.id.img_unread)
        LinearLayout imgUnread;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
