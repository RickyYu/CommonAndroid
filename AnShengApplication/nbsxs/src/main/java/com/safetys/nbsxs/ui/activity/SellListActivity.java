package com.safetys.nbsxs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSON;
import com.safetys.nbsxs.R;
import com.safetys.nbsxs.adapter.SellListAdapter;
import com.safetys.nbsxs.base.BaseActivity;
import com.safetys.nbsxs.entity.JsonResult;
import com.safetys.nbsxs.entity.RegisterInfo;
import com.safetys.nbsxs.http.HttpRequestHelper;
import com.safetys.nbsxs.http.onNetCallback;
import com.safetys.nbsxs.ui.view.LoadMoreListView;
import com.safetys.nbsxs.ui.view.SearchBar;
import com.safetys.nbsxs.utils.DialogUtil;
import com.safetys.nbsxs.utils.HttpUtil;
import com.safetys.widget.common.ToastUtils;

import org.xutils.common.Callback.Cancelable;

import java.util.ArrayList;
import java.util.List;

/** 
 * 销售历史--列表
 */
public class SellListActivity extends BaseActivity implements
	OnClickListener,
	com.safetys.nbsxs.ui.view.LoadMoreListView.OnRefreshListener,
		onNetCallback {
	
	private static final int FLAG_ORDER_TIME = 1;
	private static final int FLAG_ORDER_NUM = 2;

	private View btn_back;
	private SellListAdapter mAdapter;
	private LoadMoreListView mListView;
	private SearchBar searchBar;
	
	private Cancelable mCancelable;//网络请求取消类

	private List<RegisterInfo> mdatas = new ArrayList<RegisterInfo>();
	
	private int orderFlag = FLAG_ORDER_TIME;//排序方式
	
	private int positionClick = -1;//点击的列表位置
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_list);
		init();
		mCancelable =getSellListInfo(searchBar.getSearchData(), mListView.getItemCount());
	}

	private void init() {
		setHeadTitle("销售记录列表");
		btn_back = findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		//
//		setRightBtnImage(R.drawable.icon_time);
//		
//		setRightImgBtnClick(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(orderFlag == FLAG_ORDER_TIME){
//					//当前按照时间排序，变成按数量排序
//					setRightBtnImage(R.drawable.icon_num);
//					orderFlag = FLAG_ORDER_NUM;
//					reGetTableInfo();
//				}else{
//					//当前按照数量排序，变成按时间排序
//					setRightBtnImage(R.drawable.icon_time);
//					orderFlag = FLAG_ORDER_TIME;
//					reGetTableInfo();
//				}
//			}
//		});
		
		setRightTitle("时间排序");
		setRightBtnClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(orderFlag == FLAG_ORDER_TIME){
					//当前按照时间排序，变成按数量排序
					setRightTitle("数量排序");
					orderFlag = FLAG_ORDER_NUM;
					reGetTableInfo();
				}else{
					//当前按照数量排序，变成按时间排序
					setRightTitle("时间排序");
					orderFlag = FLAG_ORDER_TIME;
					reGetTableInfo();
				}
			}
		});
		
		
		//列表
		mListView = (LoadMoreListView) findViewById(R.id.listview);
		mListView.setOnRefreshListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				positionClick = position;
				if(mdatas==null||mdatas.size()==0){
					return;
				}
				Intent mIntent = new Intent(SellListActivity.this,RegisterShowActivity.class);
				mIntent.putExtra("info", mdatas.get(position));
				startActivityForResult(mIntent, 100);
			}
		});
		mAdapter = new SellListAdapter(this, mdatas);
		mListView.showFooter();
		mListView.setAdapter(mAdapter);
		mListView.updateListView(mdatas,mAdapter,true,true);
		

		searchBar = (SearchBar) findViewById(R.id.search_bar);
		searchBar.setOnSearchListener(new SearchBar.onSearchListener() {
			
			@Override
			public void onSearchButtonClick(String searchStr) {
				reGetTableInfo();
			}
		});
		searchBar.setVisibility(View.GONE);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==100&&resultCode==RESULT_OK&&data!=null){
			boolean isDelete = data.getBooleanExtra("isDelete", false);
			boolean isUpdate = data.getBooleanExtra("isUpdate", false);
			if(isDelete){
				//假如该数据被删除
				mdatas.remove(positionClick);
				mListView.setPageInfo(mListView.getItemCount()-1, mListView.getTotalCount()-1);
				mAdapter.notifyDataSetChanged();
			}else if(isUpdate){
				//假如更新该数据
				RegisterInfo mInfo = (RegisterInfo) data.getSerializableExtra("info");
				mdatas.add(positionClick, mInfo);
				mdatas.remove(positionClick+1);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		if(((AppApplication)getApplication()).isNeedFefresh()){
//			//暂时全部刷新
//			((AppApplication)getApplication()).setNeedFefresh(false);
//			reGetTableInfo();
//		}
	}

	/**
	 * 重新获取列表数据
	 */
	private void reGetTableInfo(){
		mdatas.clear();
		mListView.setPageInfo(0, 0);
		mListView.updateListView(mdatas,mAdapter,true,true);
		mCancelable =getSellListInfo(searchBar.getSearchData(), mListView.getItemCount());
	}
	
	/**
	 * @param searchData 搜索内容
	 * @param start 从第几条开始
	 * 
	 * @return
	 */
	private Cancelable getSellListInfo(String searchData,int start){
		//productNumber 排序用,没数据按默认购买日期 有就按数量排序,默认填写null,按数量填写true
		String productNumber = null;
		if(orderFlag == FLAG_ORDER_NUM){
			productNumber = "true";
		}
		return HttpRequestHelper.getInstance().getSellList(SellListActivity.this,
				searchBar.getSearchData(), mListView.getItemCount(),productNumber, SellListActivity.this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
			break;

		default:
			break;
		}
		
	}
	
	@Override
	public void onBackPressed() {
		HttpUtil.cancel(mCancelable);
		finish();
	}

	private boolean isLoading = false;//正在加载中
	@Override
	public void onLoadingMore() {
		if(!isLoading){
			isLoading = true;
			mCancelable =getSellListInfo(searchBar.getSearchData(), mListView.getItemCount());;
		}else{
			ToastUtils.showMessage(this, "正在加载，请勿重复点击。");
		}
	}

	@Override
	public void onError(String errorMsg) {
		isLoading = false;
		mListView.updateListView(mdatas,mAdapter,false,false);
		if(mdatas!=null&&mdatas.size()>0){
			DialogUtil.showMsgDialog(this, errorMsg, false, null);
		}else{
			if(errorMsg.equals(getString(R.string.account_security_tip))){
				DialogUtil.showMsgDialog(this, errorMsg, false, null);
			}
		}
		
	}

	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		isLoading = false;
		int mTotalCount = mJsonResult.getTotalCount();
		if(mTotalCount>0){
			List<RegisterInfo> mRegisterInfo = JSON.parseArray(mJsonResult.getJson(), RegisterInfo.class);
			if(mRegisterInfo!=null){
				mdatas.addAll(mRegisterInfo);
				//页脚是否显示加载更多
				int mItemCount = mListView.getItemCount()+mRegisterInfo.size();
				mListView.setPageInfo(mItemCount, mTotalCount);
			}
		}
		mListView.updateListView(mdatas,mAdapter,false,true);
	}
	
}
