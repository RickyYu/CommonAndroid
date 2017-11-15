package com.safetys.nbsxs.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;

import java.util.List;

/**
 * 点击更多刷新列表父类适配器
 * Created by sunjw on 2016/10/30.
 */
public class BaseListAdapter<T> extends HeaderViewListAdapter {

    protected Context mContext;
    protected Handler mHandler;
    protected List<T> mDataList;
    protected boolean hasNoData;//是否结果集合中有数据
    protected boolean isLoading;//是否在加载状态
    protected boolean networkSuccess;//网络访问是否成功

    public BaseListAdapter(Context context, List<T> objects) {
        super(null, null, null);
        this.mContext = context;
        this.mDataList = objects;
        if (this.mDataList == null || this.mDataList.isEmpty())
            hasNoData = true;
    }

    public BaseListAdapter(Context context, List<T> objects, Handler handler) {
        super(null, null, null);
        this.mContext = context;
        this.mDataList = objects;
        this.mHandler = handler;
        if (this.mDataList == null || this.mDataList.isEmpty())
            hasNoData = true;
    }
    
    //数据刷新相关--begin
    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }
    
    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }
    //数据刷新相关--end

    public void updateListView(List<T> dataList,boolean isLoading,boolean networkSuccess) {
        this.mDataList = dataList;
        hasNoData = (this.mDataList == null || this.mDataList.isEmpty());
        this.isLoading = isLoading;
        this.networkSuccess = networkSuccess;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (hasNoData)
            return 1;
        return (mDataList == null || mDataList.isEmpty()) ? 0 : mDataList.size();
    }

    @Override
    public T getItem(int position) {
        if (hasNoData)
            return null;
        return mDataList.get(position);
    }
    @Override
    public int getItemViewType(int position) {
        if (hasNoData)
            return 0;
        return 1;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return true;
	}
}
