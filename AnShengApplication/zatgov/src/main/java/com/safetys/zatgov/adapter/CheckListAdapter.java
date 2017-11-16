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
        // TODO Auto-generated method stub
        return mdatas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_left_and_right_string_item, null);
            ViewHodler mHodler = new ViewHodler();
            mHodler.mTextView1 = (TextView) convertView
                    .findViewById(R.id.text_left);
            convertView.setTag(mHodler);
        }
        ViewHodler mVH = (ViewHodler) convertView.getTag();
        mVH.mTextView1.setText(mdatas.get(position).getTitle());
        return convertView;
    }
    public class ViewHodler {
        TextView mTextView1;
    }
}
