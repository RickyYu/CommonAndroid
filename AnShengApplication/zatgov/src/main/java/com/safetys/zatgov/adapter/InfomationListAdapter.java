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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class InfomationListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<InformationDataVo> mDatas;

    public InfomationListAdapter(Context context, ArrayList<InformationDataVo> mdatas) {
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
        ViewHolder mHodler;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_img_and_string_item, null);
            mHodler = new ViewHolder(convertView);
            convertView.setTag(mHodler);
        } else {
            mHodler = (ViewHolder) convertView.getTag();
        }

        mHodler.text1.setText(mDatas.get(position).getName());
        mHodler.leftImg.setBackgroundResource(mDatas.get(position).getImgId());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.left_img)
        ImageView leftImg;
        @BindView(R.id.text1)
        TextView text1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
