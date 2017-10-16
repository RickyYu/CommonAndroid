package com.safetys.nbsxs.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


import cn.safetys.nbsxs.R;
import cn.safetys.nbsxs.bean.RegisterInfo;
import cn.safetys.nbsxs.util.StringUtil;

/**
 * 销售历史信息列表适配器
 * Created by sunjw on 2016/10/30 .
 */
public class SellListAdapter extends BaseListAdapter<RegisterInfo> {

	private Context mContext;
	
    public SellListAdapter(Context context, List<RegisterInfo> objects) {
        super(context, objects);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(position==0)convertView=null;
        if (convertView == null) {
            holder = new ViewHolder();
            if (getItemViewType(position) == 0) {
                convertView = ((Activity) (mContext)).getLayoutInflater().inflate(R.layout.layout_has_no_data, parent, false);
                holder.noDataRootLayout = (RelativeLayout) convertView.findViewById(R.id.root_layout);
            } else {
                convertView = ((Activity) (mContext)).getLayoutInflater().inflate(R.layout.list_view_string_and_arrow_and_date_item, parent, false);
                holder.mTextView1 = (TextView) convertView
						.findViewById(R.id.text1);
                holder.mTextView2 = (TextView) convertView
						.findViewById(R.id.text2);
                holder.mTextView3 = (TextView) convertView
						.findViewById(R.id.text3);
                holder.mTextView4 = (TextView) convertView
						.findViewById(R.id.text4);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (hasNoData) {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(getScreenWidth(), getScreenHeight()*2/3);
            holder.noDataRootLayout.setLayoutParams(lp);
            if(isLoading){
            	holder.noDataRootLayout.findViewById(R.id.loading_view).setVisibility(View.VISIBLE);
            	holder.noDataRootLayout.findViewById(R.id.tv_result).setVisibility(View.GONE);
            }else{
            	holder.noDataRootLayout.findViewById(R.id.loading_view).setVisibility(View.GONE);
            	TextView mText = (TextView) holder.noDataRootLayout.findViewById(R.id.tv_result);
            	mText.setVisibility(View.VISIBLE);
            	if(networkSuccess){
            		mText.setText(mContext.getString(R.string.has_no_data));
            	}else{
            		mText.setText(mContext.getString(R.string.get_data_error));
            	}
            }
        } else {
//        	holder.mTextView1.setText(mDataList.get(position).getProductName());
//        	holder.mTextView2.setText("联系人:"+StringUtil.nvl(mDataList.get(position).getName())
//        			+" "+"电话:"+StringUtil.nvl(mDataList.get(position).getPhone())
//        					+" "+"数量:"+mDataList.get(position).getProductNumber());
        	
        	holder.mTextView1.setText(mDataList.get(position).getName()+"购买（"+mDataList.get(position).getProductName()+"）"+mDataList.get(position).getProductNumber()+"升");
        	holder.mTextView2.setText("联系电话:"+StringUtil.nvl(mDataList.get(position).getPhone()));
//        	holder.mTextView3.setText();
        	holder.mTextView4.setText(mDataList.get(position).getPayTime());
        }

        return convertView;
    }

    private static final class ViewHolder {
    	TextView mTextView1;
		TextView mTextView2;
		TextView mTextView3;
		TextView mTextView4;

        RelativeLayout noDataRootLayout;
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetric = Resources.getSystem().getDisplayMetrics();
        return displayMetric.widthPixels;
    }

    private int getScreenHeight() {
        DisplayMetrics displayMetric = Resources.getSystem().getDisplayMetrics();
        return displayMetric.heightPixels;
    }

}
