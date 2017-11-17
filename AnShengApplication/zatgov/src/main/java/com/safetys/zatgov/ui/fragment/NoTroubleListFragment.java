package com.safetys.zatgov.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.activity.HistoryTroubleModifyActivity;
import com.safetys.zatgov.ui.activity.NoMajorChangeActivity;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 企业信息——隐患列表
 */
public class NoTroubleListFragment extends Fragment implements
        onNetCallback, SearchBar.onSearchListener {
	public static final String ACTION_UPDATE_LIST_YH = "cn.saftys.NoTroubleListActivity.updat.list";
	private PullToRefresh mListView;
	private MyListAdapter mAdapter;
	private ArrayList<HyCheckItemInfo> mDatas;
	private LoadingDialogUtil mLoading;
	private int mCurrentPage = 0;// 当前显示页数量
	private int totalCount = 0;// 总数
	private boolean isRepaired;
	private View btn_submit;
	private SearchBar mSearchBar;
	private EditText ed;
	private String companyid;
	private String sourceType=null;
	private static final String[] m = {"全部", "未整改", "已整改" };
	private Spinner sp_choice;
	private ArrayAdapter<String> adapter;
	private boolean isAll;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.fragment_list_yh, null);
		mLoading = new LoadingDialogUtil(getActivity(),"请稍后...");
		initView(mView);
		return mView;
		
	}

	/**
	 * 初始化界面
	 */
	private void initView(View mView) {
		companyid = getActivity().getIntent().getExtras().getString("id");
		mSearchBar = (SearchBar) mView.findViewById(R.id.search_bar);
		mSearchBar.setOnSearchListener(NoTroubleListFragment.this);
		ed = (EditText) mView.findViewById(R.id.et_search_text);
		ed.setHint("请输入隐患描述");
		sp_choice = (Spinner) mView.findViewById(R.id.sp_choice);
		sp_choice.setVisibility(View.VISIBLE);
		adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.item_spinner_view, m);
		sp_choice.setAdapter(adapter);
		sp_choice.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentPage = 0;
				totalCount = 0;
				mDatas.clear();
				mSearchBar.clearData();
				mAdapter.notifyDataSetChanged();
				if (position == 0) {
					isAll=false;
					
				} else if (position == 1) {
					isRepaired=false;
					isAll=true;
					
				} else if (position == 2) {
					isRepaired=true;
					isAll=true;
			} 
				loadingDatas();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		mListView = (PullToRefresh) mView.findViewById(R.id.list_yh);
		mListView.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

			@Override
			public void onLoadingMore() {
				// 加载更多
				if (mCurrentPage > totalCount) {
					ToastUtils.showMessage(getActivity(), "没有更多的数据了。");
					mListView.finishLoading(false);
				} else {
					loadingDatas();
				}
			}
		});

		mDatas = new ArrayList<HyCheckItemInfo>();
		mAdapter = new MyListAdapter(getActivity(), mDatas);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			private Intent intent;
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mDatas.get(position).getType().equals("1")) {//一般隐患
					intent = new Intent(getActivity(),
							HistoryTroubleModifyActivity.class);
 
				} else {//重大隐患
					intent = new Intent(getActivity(),
							NoMajorChangeActivity.class);
				}
				Bundle bundle = new Bundle();
				bundle.putString("id", mDatas.get(position).getId() + "");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}
	private void loadingDatas() {
		mLoading.show();
		HttpRequestHelper.getInstance().getNoProblemList(
				getActivity(), companyid, isRepaired, sourceType,isAll,
				mCurrentPage, totalCount, mSearchBar.getSearchData(),
				Const.HAVE_PROBLEM, NoTroubleListFragment.this);
	}

	private class MyListAdapter extends BaseAdapter {
		private Context mContext; 
		private LayoutInflater mInflater;
		private ArrayList<HyCheckItemInfo> mDatas;
		private ViewHodler mHodler;

		public MyListAdapter(Context context, ArrayList<HyCheckItemInfo> mDatas) {
			this.mContext = context;
			this.mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.mDatas = mDatas;
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
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.list_view_string_three, null);
				mHodler = new ViewHodler();
				mHodler.mTextView1 = (TextView) convertView
						.findViewById(R.id.text1);
				mHodler.mTextView2 = (TextView) convertView
						.findViewById(R.id.text2);
				mHodler.mTextView3 = (TextView) convertView
						.findViewById(R.id.text3);
				mHodler.mTextView4 = (TextView) convertView
						.findViewById(R.id.text4);
				convertView.setTag(mHodler);
			}
			ViewHodler mHodler = (ViewHodler) convertView.getTag();
			mHodler.mTextView1.setText(mDatas.get(position).getDescription());
			Log.e("asd", mDatas.get(position).getDescription());
			mHodler.mTextView2.setText("录入日期："
					+ mDatas.get(position).getCreateTime());
			if (mDatas.get(position).getSourceType().equals("3")) {
				mHodler.mTextView3.setText("来源：政府");
			}else {
				mHodler.mTextView3.setText("来源：企业");
			}
			
			if(mDatas.get(position).getType().equals("1")){//一般隐患
				mHodler.mTextView4.setText("一般隐患");
				mHodler.mTextView4.setTextColor(getResources().getColor(R.color.gray));
			}else{
				mHodler.mTextView4.setText("重大隐患");
				mHodler.mTextView4.setTextColor(getResources().getColor(R.color.red));
				
			}
			return convertView;
		}
	}

	private class ViewHodler {
		TextView mTextView1;
		TextView mTextView2;
		TextView mTextView3;
		TextView mTextView4;
	}

	@Override
	public void onError(String errorMsg) {
		mLoading.dismiss();
		DialogUtil.showMsgDialog(getActivity(), errorMsg, true, null);
	}

	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		mLoading.dismiss();
		switch (requestCode) {
		case Const.HAVE_PROBLEM:
			if (mJsonResult.getJson() == null) {
				if (mDatas.size() == 0) {

				} else {
				}
			} else {

				mCurrentPage = mCurrentPage + 10;
				totalCount = mJsonResult.getTotalCount();
				List<HyCheckItemInfo> list = JSONArray.parseArray(
						(String) mJsonResult.getJson(), HyCheckItemInfo.class);
				if (list == null || list.size() == 0) {
					
				} else {
					mDatas.addAll(list);
					
				}
			}
			mAdapter.notifyDataSetChanged();
			mListView.finishLoading(false);
			break;
		default:
			break;
		}
	}
	private void reLoadListDatas() {
		// 刷新列表
		mCurrentPage = 0;
		totalCount = 0;
		mDatas.clear();
		
		mAdapter.notifyDataSetChanged();
		loadingDatas();

	}
	@Override
	public void onSearchButtonClick(String searchStr) {
		reLoadListDatas();
		
	}





}
