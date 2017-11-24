package com.safetys.zatgov.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.safetys.widget.common.DateDistanceUtils;
import com.safetys.widget.common.DateParseUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CompanyVo;
import com.safetys.zatgov.config.SecondTypeEnum;
import com.safetys.zatgov.config.ThirdTypeEnum;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.safetys.zatgov.ui.activity.EnterpriseListActivity.SKIP_CHECK_RECORD_LIST;
import static com.safetys.zatgov.ui.activity.EnterpriseListActivity.SKIP_SUPERVISE_CHECKT;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class EnterpriseListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CompanyVo> mdatas;
    private int skipType;

    public EnterpriseListAdapter(Context context, List<CompanyVo> mdatas, int skipType) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mdatas = mdatas;
        this.skipType = skipType;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHodler;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.list_view_string_three, null);
            mHodler = new ViewHolder(convertView);
            convertView.setTag(mHodler);
        } else {
            mHodler = (ViewHolder) convertView.getTag();
        }

        CompanyVo companyListInfo = mdatas.get(position);
        mHodler.text1.setText(companyListInfo.getCompanyName());
        mHodler.text2.setText("负责人：" + companyListInfo.getFdDelegate()
                + "  所属区域：" + "湖州市"
                + SecondTypeEnum.getValue(companyListInfo.getSecondArea())
                + ThirdTypeEnum.getValue(companyListInfo.getThirdArea()));
        if (skipType == SKIP_SUPERVISE_CHECKT
                || skipType == SKIP_CHECK_RECORD_LIST) {

            Log.i("time", DateParseUtils.stampToDate(companyListInfo
                    .getCreateTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
            try {
                if (!DateDistanceUtils.isEndDay(companyListInfo
                        .getCreateTime())) {
                    // 不可新增隐患，只能查阅隐患
                    mHodler.rllBgView.setBackgroundColor(Color
                            .parseColor("#F5F5DC"));
                } else {
                    mHodler.rllBgView.setBackgroundColor(Color
                            .parseColor("#FFFFFF"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (companyListInfo.getGridDangerNum().equals("0")) {
                mHodler.text3.setTextColor(Color.rgb(170, 170, 170));
                mHodler.text3.setText("未整改隐患数量：0个");

            } else {
                // 检查记录需要的是政府未整改隐患数
                mHodler.text3.setText("未整改隐患数量："
                        + companyListInfo.getGridDangerNum() + "个");
                mHodler.text3.setTextColor(Color.rgb(220, 20, 60));

            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.dot_img)
        ImageView dotImg;
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.text3)
        TextView text3;
        @BindView(R.id.text2)
        TextView text2;
        @BindView(R.id.text4)
        TextView text4;
        @BindView(R.id.rll_bg_view)
        RelativeLayout rllBgView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
