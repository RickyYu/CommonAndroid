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

import butterknife.BindView;
import butterknife.ButterKnife;

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
                                 ArrayList<SafetyMatter> mdatasTwo, ArrayList<SafetyMatter> mdatas) {
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
       final ViewHolder mHodler;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_string_remark_checkbox_item, null);
            mHodler = new ViewHolder(convertView);
            convertView.setTag(mHodler);
        } else {
            mHodler = (ViewHolder) convertView.getTag();
        }

        mHodler.text1.setText(mdatasTwo.get(position).getContent());
        mHodler.ivRemark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mHodler.text2.setText(mdatasTwo.get(position).getRemark());
            }
        });

        mHodler.checked
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

        mHodler.checked.setChecked(mdatasTwo.get(position).isCheckTag());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.checked)
        CheckBox checked;
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.iv_remark)
        ImageView ivRemark;
        @BindView(R.id.text2)
        TextView text2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
