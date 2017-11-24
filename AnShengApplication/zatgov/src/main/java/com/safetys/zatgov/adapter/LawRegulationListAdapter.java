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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class LawRegulationListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ReadInfo> mDatas;

    public LawRegulationListAdapter(Context context, ArrayList<ReadInfo> mdatas) {
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
        ViewHolder mHodler;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_string_and_arrow_and_date_item, null);
            mHodler = new ViewHolder(convertView);
            convertView.setTag(mHodler);
        } else {
            mHodler = (ViewHolder) convertView.getTag();
        }

        mHodler.text1.setText(mDatas.get(position).getCaption());
        mHodler.text2.setText(mDatas.get(position).getPublisher());
        mHodler.text3.setText(mDatas.get(position).getCreatetime());
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
