package com.safetys.nbsxs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.safetys.nbsxs.R;
import com.safetys.nbsxs.base.BaseActivity;
import com.safetys.nbsxs.common.PrefKeys;
import com.safetys.nbsxs.entity.CountInfo;
import com.safetys.nbsxs.entity.JsonResult;
import com.safetys.nbsxs.http.HttpRequestHelper;
import com.safetys.nbsxs.http.onNetCallback;
import com.safetys.widget.common.SPUtils;

import java.util.List;


public class MenuActivity extends BaseActivity implements OnClickListener,onNetCallback {

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
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//第一次登录的时候，默认先跳转到企业信息界面
		boolean isFirst = (boolean) SPUtils.getData(PrefKeys.PREF_NOT_IS_FIRST_LOGIN, false);
		if(!isFirst){
			SPUtils.saveData(PrefKeys.PREF_NOT_IS_FIRST_LOGIN, true);
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


	@Override
	public void onError(String errorMsg) {
		// TODO Auto-generated method stub
		//不做处理
	}


	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
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
