package com.safetys.nbsxs.ui.activity;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.safetys.nbsxs.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.safetys.nbsxs.R;
import cn.safetys.nbsxs.activity.CompanyActivity;
import cn.safetys.nbsxs.activity.SellListActivity;
import cn.safetys.nbsxs.activity.RegisterActivity;
import cn.safetys.nbsxs.activity.SettingActivity;
import cn.safetys.nbsxs.base.AppApplication;
import cn.safetys.nbsxs.base.BaseActivity;
import cn.safetys.nbsxs.bean.CountInfo;
import cn.safetys.nbsxs.bean.JsonResult;
import cn.safetys.nbsxs.config.PrefKeys;
import cn.safetys.nbsxs.http.HttpRequestHelper;
import cn.safetys.nbsxs.http.onNetCallback;

public class MenuActivity extends BaseActivity implements OnClickListener,onNetCallback{

	private View btn_qy;
	private View btn_ls;
	private View btn_sz;
	private View btn_yb;
	private Intent intent;
	
	private TextView mTv_year_num;
	private TextView mTv_season1;
	private TextView mTv_season2;
	private TextView mTv_season3;
	private TextView mTv_season4;
	
	private TextView mTv_month_num;
	private TextView mTv_day1;
	private TextView mTv_day2;
	private TextView mTv_day3;
	private TextView mTv_day4;
	
//	private ListView mListView;
//	private MyListAdapter myListAdapter;
//	private List<RegisterInfo> mInfos = new ArrayList<RegisterInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		init();
	}


	private void init() {
		btn_qy = findViewById(R.id.btn_qy);
		btn_ls = findViewById(R.id.btn_ls);
		btn_sz = findViewById(R.id.btn_sz);
		btn_yb = findViewById(R.id.btn_yb);
		
		btn_qy.setOnClickListener(this);
		btn_ls.setOnClickListener(this);
		btn_sz.setOnClickListener(this);
		btn_yb.setOnClickListener(this);
		
		mTv_year_num = (TextView) findViewById(R.id.tv_year_num);
		mTv_season1 = (TextView) findViewById(R.id.tv_season1);
		mTv_season2 = (TextView) findViewById(R.id.tv_season2);
		mTv_season3 = (TextView) findViewById(R.id.tv_season3);
		mTv_season4 = (TextView) findViewById(R.id.tv_season4);
		
		mTv_month_num = (TextView) findViewById(R.id.tv_month_num);
		mTv_day1 = (TextView) findViewById(R.id.tv_day1);
		mTv_day2 = (TextView) findViewById(R.id.tv_day2);
		mTv_day3 = (TextView) findViewById(R.id.tv_day3);
		mTv_day4 = (TextView) findViewById(R.id.tv_day4);
		
//		myListAdapter = new MyListAdapter(this, mInfos);
//		mListView = (ListView) findViewById(R.id.listview);
//		mListView.setAdapter(myListAdapter);
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Intent mIntent = new Intent(MenuActivity.this,RegisterShowActivity.class);
//				mIntent.putExtra("info", mInfos.get(position));
//				startActivity(mIntent);
//			}
//		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//第一次登录的时候，默认先跳转到企业信息界面
		SharedPreferences mPreferences = ((AppApplication)getApplicationContext()).getAppMainPreferences();
		if(!mPreferences.getBoolean(PrefKeys.PREF_NOT_IS_FIRST_LOGIN, false)){
			Editor mEditor = mPreferences.edit();
			mEditor.putBoolean(PrefKeys.PREF_NOT_IS_FIRST_LOGIN, true);
			mEditor.commit();
			Intent mIntent = new Intent(this, CompanyActivity.class);
			startActivity(mIntent);
		}		
		
//		获取销售统计
		HttpRequestHelper.getInstance().getSellCount(getApplicationContext(),this);
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_qy:
			intent = new Intent(MenuActivity.this,CompanyActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_ls:
			intent = new Intent(MenuActivity.this,SellListActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_yb:
			intent = new Intent(MenuActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_sz:
			intent = new Intent(MenuActivity.this,SettingActivity.class);
			startActivity(intent);
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
//	private class MyListAdapter extends BaseAdapter{
//		
//		private Context mContext;
//		
//		private LayoutInflater mInflater;
//		
//		private List<RegisterInfo> mDatas;
//
//		public MyListAdapter(Context mContext,List<RegisterInfo> mDatas){
//			this.mContext = mContext;
//			this.mDatas = mDatas;
//			this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		}
//		
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return mDatas.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHodler mHodler;
//			if(convertView==null){
//				convertView = mInflater.inflate(R.layout.list_view_selllist_last_item, null);
//				mHodler = new ViewHodler();
//				mHodler.date = (TextView) convertView.findViewById(R.id.text4);
//				mHodler.title = (TextView) convertView.findViewById(R.id.text1);
//				mHodler.content = (TextView) convertView.findViewById(R.id.text2);
//				convertView.setTag(mHodler);
//			}
//			mHodler = (ViewHodler) convertView.getTag();
//			mHodler.date.setText(mDatas.get(position).getPayTime());
//			mHodler.title.setText(mDatas.get(position).getName()+"购买（"+mDatas.get(position).getProductName()+"）"+mDatas.get(position).getProductNumber()+"升");
//			mHodler.content.setText("联系电话:"+StringUtil.nvl(mDatas.get(position).getPhone()));
//			
//			return convertView;
//		}
//		
//		
//		private class ViewHodler{
//			public TextView date;
//			public TextView title;
//			public TextView content;
//		}
//	}


	@Override
	public void onError(String errorMsg) {
		// TODO Auto-generated method stub
		//不做处理
	}


	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		// TODO Auto-generated method stub
		//销售
		int season1Num = 0,season2Num = 0,season3Num = 0,season4Num = 0,month1Num = 0,month2Num = 0,month3Num = 0,month4Num = 0;
		List<CountInfo> mCountInfos = JSON.parseArray(mJsonResult.getJson(), CountInfo.class);
		for(int i=0;i<mCountInfos.size();i++){
			if(mCountInfos.get(i).getDATATYPE().equals("season1")){
				season1Num = mCountInfos.get(i).getNUM();
				mTv_season1.setText("一季度:"+season1Num);
				
			}else if(mCountInfos.get(i).getDATATYPE().equals("season2")){
				season2Num = mCountInfos.get(i).getNUM();
				mTv_season2.setText("二季度:"+season2Num);
			}else if(mCountInfos.get(i).getDATATYPE().equals("season3")){
				season3Num = mCountInfos.get(i).getNUM();
				mTv_season3.setText("三季度:"+season3Num);
			}else if(mCountInfos.get(i).getDATATYPE().equals("season4")){
				season4Num = mCountInfos.get(i).getNUM();
				mTv_season4.setText("四季度:"+season4Num);
			}else if(mCountInfos.get(i).getDATATYPE().equals("month1")){
				month1Num = mCountInfos.get(i).getNUM();
				mTv_day1.setText("第一周:"+month1Num);
			}else if(mCountInfos.get(i).getDATATYPE().equals("month2")){
				month2Num = mCountInfos.get(i).getNUM();
				mTv_day2.setText("第二周:"+month2Num);
			}else if(mCountInfos.get(i).getDATATYPE().equals("month3")){
				month3Num = mCountInfos.get(i).getNUM();
				mTv_day3.setText("第三周:"+month3Num);
			}else if(mCountInfos.get(i).getDATATYPE().equals("month4")){
				month4Num = mCountInfos.get(i).getNUM();
				mTv_day4.setText("第四周:"+month4Num);
			}
		}
		
		mTv_month_num.setText(""+(month1Num+month2Num+month3Num+month4Num));
		mTv_year_num.setText(""+(season1Num+season2Num+season3Num+season4Num));

	}
}
