package com.safetys.nbsxs.ui.view;

import java.util.List;

import cn.safetys.nbsxs.R;
import cn.safetys.nbsxs.adapter.BaseListAdapter;
import cn.safetys.nbsxs.util.ViewUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 *	分页使用时，底部加载更多按钮
 */
public class LoadMoreListView extends ListView{
	
	private View mFooter;//底部布局
	private TextView mBtn_load;//底部加载更多按钮
	private View mLoading_view;//不包含数据时，加载界面以及显示结果界面
	private OnRefreshListener mButtonClick;
	
	private int mtotalCount = 0;//数据总数
	private int mItemCount = 0;//当前处于总数什么位置
	
	
	public interface OnRefreshListener{
		public void onLoadingMore();
	}

	public LoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mFooter = View.inflate(getContext(), R.layout.footer_button_load_more_progress, null);
		mBtn_load = (TextView) mFooter.findViewById(R.id.btn_load);
		mLoading_view = mFooter.findViewById(R.id.loading_view);
		ViewUtil.setEdging(mBtn_load, context, R.color.white, R.color.gray);
		mBtn_load.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mButtonClick!=null){
					mBtn_load.setVisibility(View.GONE);
					mLoading_view.setVisibility(View.VISIBLE);
					mButtonClick.onLoadingMore();
				}
			}
		});
		
	}
	
	public void showFooter(){
		hideFooter();
		mBtn_load.setVisibility(View.VISIBLE);
		mLoading_view.setVisibility(View.GONE);
		addFooterView(mFooter);
	}
	
	public void hideFooter(){
		removeFooterView(mFooter);
	}

	public void setOnRefreshListener(OnRefreshListener mButtonClick){
		this.mButtonClick = mButtonClick;
	}
	
	/**
	 * 设置总数
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount){
		this.mtotalCount = totalCount;
	}
	
	/**
	 * 设置当前数据所处位置
	 * @param itemCount
	 */
	public void setItemCount(int itemCount){
		this.mItemCount = itemCount;
	}
	
	public void setPageInfo(int itemCount,int totalCount){
		this.mItemCount = itemCount;
		this.mtotalCount = totalCount;
	}
	
	public int getItemCount() {
		return mItemCount;
	}
	
	public int getTotalCount() {
		return mtotalCount;
	}
	

	/**
	 * 更新界面
	 * @param <T>
	 * @param dataList 数据
	 * @param isloading 是否在加载中
	 * @param networkSuccess 网络是否访问成功
	 */
	public <T> void updateListView(List<T> dataList,BaseListAdapter<T> mAdapter,boolean isLoading,boolean networkSuccess){
		mAdapter.updateListView(dataList, isLoading,networkSuccess);
		if(dataList==null||dataList.size()==0){
			setDividerHeight(0);
			hideFooter();
		}else{
			setDividerHeight(1);
			//当前数据总数大于或等于总条数的时候，隐藏底部加载更多
			if(dataList.size()>=mtotalCount){
				hideFooter();
			}else{
				showFooter();
			}
		}
	}
}
