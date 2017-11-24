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
import com.safetys.zatgov.bean.SafetyMatter;
import com.safetys.zatgov.ui.activity.WgyMakeCheckListActivity;
import com.safetys.zatgov.utils.DialogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class WgyMakeCheckListAdapter extends BaseAdapter {
    private WgyMakeCheckListActivity mContext;
    private LayoutInflater mInflater;
    private ArrayList<SafetyMatter> mdatas;

    public WgyMakeCheckListAdapter(WgyMakeCheckListActivity context,
                                   ArrayList<SafetyMatter> mdatas) {
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
                    R.layout.list_view_string_and_remark_item, null);
            mHodler = new ViewHolder(convertView);
            convertView.setTag(mHodler);
        } else {
            mHodler = (ViewHolder) convertView.getTag();
        }

        mHodler.text1.setText(mdatas.get(position).getContent());
        mHodler.ivRemark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = mdatas.get(position).getRemark();
                DialogUtil.showMsgDialog(mContext,
                        msg, false, null);

            }
        });

        mHodler.btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mdatas.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.btn_delete)
        LinearLayout btnDelete;
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.iv_remark)
        ImageView ivRemark;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
