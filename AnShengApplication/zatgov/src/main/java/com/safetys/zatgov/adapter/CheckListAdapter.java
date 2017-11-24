package com.safetys.zatgov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.SafetyCheck;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class CheckListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SafetyCheck> mdatas;

    public CheckListAdapter(Context context, ArrayList<SafetyCheck> mdatas) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mdatas = mdatas;
    }

    @Override
    public int getCount() {

        return mdatas.size();
    }

    @Override
    public Object getItem(int position) {

        return mdatas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        ViewHolder mHodler;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_left_and_right_string_item, null);
            mHodler = new ViewHolder(convertView);
            convertView.setTag(mHodler);
        } else {
            mHodler = (ViewHolder) convertView.getTag();
        }
        mHodler.textLeft.setText(mdatas.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.text_left)
        TextView textLeft;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
