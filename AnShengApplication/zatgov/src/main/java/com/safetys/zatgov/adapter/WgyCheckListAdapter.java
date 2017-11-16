package com.safetys.zatgov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.SafetyCheck;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class WgyCheckListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SafetyCheck> mdatas;

    public WgyCheckListAdapter(Context context, ArrayList<SafetyCheck> mdatas) {
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
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_checkbox_and_string_item, null);
            ViewHodler mHodler = new ViewHodler();
            mHodler.mTextView1 = (TextView) convertView
                    .findViewById(R.id.text_left);
            mHodler.mTextView2 = (TextView) convertView
                    .findViewById(R.id.text_right);
            mHodler.checkBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(mHodler);
        }
        ViewHodler mVH = (ViewHodler) convertView.getTag();
        mVH.mTextView1.setText(mdatas.get(position).getTitle());
        mVH.mTextView2.setText(mdatas.get(position).getCreateTime());
        mVH.checkBox.setVisibility(View.GONE);
        mVH.checkBox
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            for (int i = 0; i < mdatas.size(); i++) {
                                if (position != i) {
                                    mdatas.get(i).setCheck(false);
                                }

                            }
                            mdatas.get(position).setCheck(true);

                        } else {
                            mdatas.get(position).setCheck(false);
                        }
                        notifyDataSetChanged();
                    }
                });
        ;

        mVH.checkBox.setChecked(mdatas.get(position).getCheck());

        return convertView;
    }
    private class ViewHodler {
        TextView mTextView1;
        TextView mTextView2;
        CheckBox checkBox;
    }
}
