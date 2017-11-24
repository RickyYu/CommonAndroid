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

import butterknife.BindView;
import butterknife.ButterKnife;

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
        ViewHolder mHodler;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_string_three, null);
            mHodler = new ViewHolder(convertView);
            convertView.setTag(mHodler);
        } else {
            mHodler = (ViewHolder) convertView.getTag();
        }

        mHodler.text1.setText(mDatas.get(position).getContent());
        mHodler.text1.setTextColor(Color.rgb(0, 0, 0));
        mHodler.text2.setText("录入日期："
                + mDatas.get(position).getCreateTime());
        if (mDatas.get(position).getRepaired()) {
            mHodler.text3.setText("已整改");
            mHodler.text3.setTextColor(Color.RED);
        } else {
            mHodler.text3.setText("未整改");
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.text3)
        TextView text3;
        @BindView(R.id.text2)
        TextView text2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

