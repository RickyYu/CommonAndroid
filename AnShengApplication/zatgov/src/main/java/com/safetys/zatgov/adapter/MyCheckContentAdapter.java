package com.safetys.zatgov.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.SafetyMatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class MyCheckContentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SafetyMatter> mdatasTwo;
    private SafetyMatter chooseSafetyMatter;
    private ArrayList<SafetyMatter> mdatas;

    public MyCheckContentAdapter(Context context,
                                 ArrayList<SafetyMatter> mdatasTwo,ArrayList<SafetyMatter> mdatas) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mdatasTwo = mdatasTwo;
        this.mdatas = mdatas;
    }

    @Override
    public int getCount() {
        return mdatasTwo.size();
    }

    @Override
    public Object getItem(int position) {
        return mdatasTwo.get(position);
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
                    R.layout.list_view_string_remark_checkbox_item, null);
            CheckContentViewHodler mHodler = new CheckContentViewHodler();
            mHodler.mTextView1 = (TextView) convertView
                    .findViewById(R.id.text1);
            mHodler.mTextView2 = (TextView) convertView
                    .findViewById(R.id.text2);
            mHodler.iv_remark = (ImageView) convertView
                    .findViewById(R.id.iv_remark);
            mHodler.checkBox = (CheckBox) convertView
                    .findViewById(R.id.checked);
            convertView.setTag(mHodler);
        }
        final CheckContentViewHodler mVH = (CheckContentViewHodler) convertView
                .getTag();
        mVH.mTextView1.setText(mdatasTwo.get(position).getContent());
        mVH.iv_remark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mVH.mTextView2.setText(mdatasTwo.get(position).getRemark());
            }
        });

        mVH.checkBox
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        chooseSafetyMatter = mdatasTwo.get(position);
                        List<Integer> listInfoId = new ArrayList<Integer>();
                        for (int i = 0; i < mdatas.size(); i++) {
                            listInfoId.add(new Integer(mdatas.get(i)
                                    .getInfoId()));
                        }
                        if (isChecked) {

                            if (!listInfoId.contains(new Integer(chooseSafetyMatter.getInfoId()))) {
                                mdatas.add(chooseSafetyMatter);
                                chooseSafetyMatter.setCheckTag(true);
                            }
                        } else {
                            if (listInfoId.contains(new Integer(chooseSafetyMatter.getInfoId()))) {
                                mdatas.remove(chooseSafetyMatter);
                                chooseSafetyMatter.setCheckTag(false);
                            }
                        }


                    }
                });

        mVH.checkBox.setChecked(mdatasTwo.get(position).isCheckTag());
        return convertView;
    }
    private class CheckContentViewHodler {
        TextView mTextView1;
        TextView mTextView2;
        ImageView iv_remark;
        CheckBox checkBox;
    }
}
