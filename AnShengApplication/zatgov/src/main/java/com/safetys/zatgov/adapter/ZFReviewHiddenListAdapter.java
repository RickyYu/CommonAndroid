package com.safetys.zatgov.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safetys.widget.common.DateDistanceUtils;
import com.safetys.widget.common.DateParseUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.HiddenDesInfoVo;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:企业列表Adapter
 */
public class ZFReviewHiddenListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<HiddenDesInfoVo> mDatas;
    private String source;

    public ZFReviewHiddenListAdapter(Context context,
                                     ArrayList<HiddenDesInfoVo> mdatas,String source) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDatas = mdatas;
        this.source = source;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_string_and_arrow_and_date_item, null);
            ViewHodler mHodler = new ViewHodler();
            mHodler.mTextView1 = (TextView) convertView
                    .findViewById(R.id.text1);
            mHodler.mTextView2 = (TextView) convertView
                    .findViewById(R.id.text2);
            mHodler.mTextView3 = (TextView) convertView
                    .findViewById(R.id.text3);
            mHodler.mTextView4 = (TextView) convertView
                    .findViewById(R.id.tv_review_times);
            mHodler.llReadView = (LinearLayout) convertView
                    .findViewById(R.id.ll_read_view);
            mHodler.mBgView = (RelativeLayout) convertView
                    .findViewById(R.id.rll_bg_view);
            convertView.setTag(mHodler);
        }
        ViewHodler mVH = (ViewHodler) convertView.getTag();
        HiddenDesInfoVo desInfo = mDatas.get(position);
        mVH.mTextView1.setText(desInfo.getContent());

        if (desInfo.getIsBig().equals("1")) {
            mVH.mTextView2.setText("隐患类型：重大隐患");
        } else {
            mVH.mTextView2.setText("隐患类型：一般隐患");
        }

        if (desInfo.getIsRead() == 1) {
            mVH.llReadView.setVisibility(View.VISIBLE);
        } else {
            mVH.llReadView.setVisibility(View.GONE);
        }

        String hiddenState = desInfo.getGovCheck();
        // -1：政府端新增   0：企业已整改    1：政府端未通过   2：政府端已通过
        if (hiddenState.equals("0")) {
            mVH.mTextView4.setText("企业已整改");
            mVH.mTextView4.setTextColor(Color.parseColor("#66CD00"));
        } else if(hiddenState.equals("-1")) {
            mVH.mTextView4.setText("企业未整改");
            mVH.mTextView4.setTextColor(Color.parseColor("#FF0000"));
        } else if(hiddenState.equals("1")) {
            mVH.mTextView4.setText("政府未通过");
            mVH.mTextView4.setTextColor(Color.parseColor("#FF7F00"));
        }else if(hiddenState.equals("2")) {
            mVH.mTextView4.setText("政府已通过");
            mVH.mTextView4.setTextColor(Color.parseColor("#66CD00"));
        }

        try {
            if (!DateDistanceUtils.isEndDay(DateParseUtils.dateToStamp(desInfo
                    .getCreateTime())) && desInfo.getIsRead() != 1 && source.equals("check")) {
                //如果入口是从监督检查进入则可以 修改该隐患
                desInfo.setReview(true);
                mVH.mBgView.setBackgroundColor(Color.parseColor("#F5F5DC"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mVH.mTextView3.setText("隐患日期：" + desInfo.getCreateTime());

        return convertView;
    }

    private class ViewHodler {
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        TextView mTextView4;
        RelativeLayout mBgView;
        LinearLayout llReadView;
    }
}
