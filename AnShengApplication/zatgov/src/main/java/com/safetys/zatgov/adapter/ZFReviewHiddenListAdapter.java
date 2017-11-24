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

import butterknife.BindView;
import butterknife.ButterKnife;

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
                                     ArrayList<HiddenDesInfoVo> mdatas, String source) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDatas = mdatas;
        this.source = source;
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

        HiddenDesInfoVo desInfo = mDatas.get(position);
        mHodler.text1.setText(desInfo.getContent());

        if (desInfo.getIsBig().equals("1")) {
            mHodler.text2.setText("隐患类型：重大隐患");
        } else {
            mHodler.text2.setText("隐患类型：一般隐患");
        }

        if (desInfo.getIsRead() == 1) {
            mHodler.llReadView.setVisibility(View.VISIBLE);
        } else {
            mHodler.llReadView.setVisibility(View.GONE);
        }

        String hiddenState = desInfo.getGovCheck();
        // -1：政府端新增   0：企业已整改    1：政府端未通过   2：政府端已通过
        if (hiddenState.equals("0")) {
            mHodler.tvReviewTimes.setText("企业已整改");
            mHodler.tvReviewTimes.setTextColor(Color.parseColor("#66CD00"));
        } else if (hiddenState.equals("-1")) {
            mHodler.tvReviewTimes.setText("企业未整改");
            mHodler.tvReviewTimes.setTextColor(Color.parseColor("#FF0000"));
        } else if (hiddenState.equals("1")) {
            mHodler.tvReviewTimes.setText("政府未通过");
            mHodler.tvReviewTimes.setTextColor(Color.parseColor("#FF7F00"));
        } else if (hiddenState.equals("2")) {
            mHodler.tvReviewTimes.setText("政府已通过");
            mHodler.tvReviewTimes.setTextColor(Color.parseColor("#66CD00"));
        }

        try {
            if (!DateDistanceUtils.isEndDay(DateParseUtils.dateToStamp(desInfo
                    .getCreateTime())) && desInfo.getIsRead() != 1 && source.equals("check")) {
                //如果入口是从监督检查进入则可以 修改该隐患
                desInfo.setReview(true);
                mHodler.rllBgView.setBackgroundColor(Color.parseColor("#F5F5DC"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mHodler.text3.setText("隐患日期：" + desInfo.getCreateTime());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ll_read_view)
        LinearLayout llReadView;
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.text3)
        TextView text3;
        @BindView(R.id.text2)
        TextView text2;
        @BindView(R.id.tv_review_times)
        TextView tvReviewTimes;
        @BindView(R.id.rll_bg_view)
        RelativeLayout rllBgView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
