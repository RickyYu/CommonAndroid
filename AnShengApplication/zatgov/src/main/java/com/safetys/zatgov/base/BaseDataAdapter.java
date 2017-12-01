package com.safetys.zatgov.base;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/10.
 * Description:通用ListView、Gridview的adapter，
 */
public abstract class BaseDataAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public BaseDataAdapter(Context context, List<T> datas, int itemLayoutId) {
        mContext = context;
        mDatas = datas;
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    public void updateData(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BaseViewHolder viewHolder = BaseViewHolder.get(mContext, convertView, mItemLayoutId);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    public abstract void convert(BaseViewHolder viewHolder, T item, int position);
}
