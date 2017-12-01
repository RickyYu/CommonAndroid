package com.safetys.zatgov.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

/**
 * Author:Created by Ricky on 2017/11/10.
 * Description:
 */
public class BaseViewHolder {
    /**
     * 保存item里的所有view
     * 这里使用Android提供的SparseArray，更高效
     */
    private final SparseArray<View> mViews;
    /**
     * 整个item
     */
    private View mConvertView;

    private BaseViewHolder(Context context, int layoutId) {
        mViews = new SparseArray<View>();
        mConvertView = View.inflate(context, layoutId, null);
        mConvertView.setTag(this);
    }

    public static BaseViewHolder get(Context context, View convertView, int layoutId) {
        if (convertView == null) {
            return new BaseViewHolder(context, layoutId);
        }
        return (BaseViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}

