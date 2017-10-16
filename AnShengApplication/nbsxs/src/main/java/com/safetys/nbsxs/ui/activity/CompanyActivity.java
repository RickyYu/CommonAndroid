package com.safetys.nbsxs.ui.activity;


import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.safetys.nbsxs.R;
import cn.safetys.nbsxs.base.BaseActivity;
import cn.safetys.nbsxs.bean.CompanyVo;
import cn.safetys.nbsxs.bean.JsonResult;
import cn.safetys.nbsxs.http.HttpRequestHelper;
import cn.safetys.nbsxs.http.onNetCallback;
import cn.safetys.nbsxs.util.CheckPhoneUtil;
import cn.safetys.nbsxs.util.DialogUtil;
import cn.safetys.nbsxs.util.LoadingDialogUtil;
import cn.safetys.nbsxs.util.StringUtil;
import cn.safetys.nbsxs.util.ToastUtils;
import cn.safetys.nbsxs.wheel.widget.views.StreetSelectDialog;

/**
 * 门店基本信息界面
 */
public class CompanyActivity extends BaseActivity implements OnClickListener,onNetCallback{

	private View btn_back;
	private TextView mTv_mdmc;//门店名称
	private TextView mTv_szqy;//所在区域
	private TextView mEt_gszch;//工商注册号
	private EditText mEt_mdfzr;//门店负责人
	private EditText mEt_lxfs;//联系方式
	private EditText mEt_mddz;//门店地址
	private EditText mEt_zjxx;//证件信息
	private EditText mEt_jyfw;//经营范围
	
	private LoadingDialogUtil mLoading;//正在加载
	private CompanyVo mCompanyVo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		initView();
		
		//显示加载框
		mLoading.show();
		//请求数据
		HttpRequestHelper.getInstance().getCompanyInfo(this, this);
	}

	private void initView() {
		setHeadTitle("门店基本信息");
		btn_back = findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		View title=findViewById(R.id.title_bar);
		title.setBackgroundResource(R.drawable.qytitle);
		
		//右上角功能按钮
		setRightTitle("保存");
		setRightBtnClick(this);
		
		mTv_mdmc = (TextView) findViewById(R.id.tv_dw);
		mTv_szqy = (TextView) findViewById(R.id.tv_szqy);
		mEt_gszch = (TextView) findViewById(R.id.tv_gszch);
		mEt_mdfzr = (EditText) findViewById(R.id.tv_mdfzr);
		mEt_lxfs = (EditText) findViewById(R.id.tv_lxfs);
		mEt_mddz = (EditText) findViewById(R.id.tv_dz);
		mEt_zjxx = (EditText) findViewById(R.id.tv_zjxx);
		mEt_jyfw = (EditText) findViewById(R.id.tv_jyfw);
		
		mTv_szqy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StreetSelectDialog mDialog = new StreetSelectDialog(CompanyActivity.this, 
						new StreetSelectDialog.OnTextCListener() {
							@Override
							public void onClick(String msText,String mtText,String mfText, String scode, String tcode, String fcode) {
								//和后台返回的数据一样进行拼接
								String areaName = "宁波市 "+msText+" "+mtText;
								if(mCompanyVo==null){
									mCompanyVo = new CompanyVo();
								}
								mCompanyVo.setAreaName(areaName);
								mTv_szqy.setText(areaName);
								mCompanyVo.setFirstArea("330200000000");
								mCompanyVo.setSecondArea(scode);
								mCompanyVo.setThirdArea(tcode);
								mCompanyVo.setFouthArea(fcode);
							}
				});
				if(mCompanyVo!=null&&mCompanyVo.getAreaName()!=null){
					String[] areas = mCompanyVo.getAreaName().split(" ");
					if(areas!=null){
						mDialog.setText(areas.length>1?areas[1]:"",
								areas.length>2?areas[2]:"", areas.length>3?areas[3]:"");
					}
					
				}
				mDialog.show();
			}
		});
		
		mLoading = new LoadingDialogUtil(this);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	@Override
	public void onClick(View v) {
		if(v==btn_back){
			finish();
		}else if(v.getId()==R.id.title_right){
			//右上角功能按钮--保存
			//校验字段
			String companyName = mTv_mdmc.getText().toString();
			String regNum = mEt_gszch.getText().toString();
			String peopleName = mEt_mdfzr.getText().toString();
			String telNum = mEt_lxfs.getText().toString();
			String areaName = mTv_szqy.getText().toString();
			String address = mEt_mddz.getText().toString();
			String mInfo = mEt_zjxx.getText().toString();
			String scope = mEt_jyfw.getText().toString();
//			if(!checkValue(companyName,"门店名称不能为空")){
//				return;
//			}
//			if(!checkValue(regNum,"工商注册号不能为空")){
//				return;
//			}
			if(!checkValue(peopleName,"门店负责人不能为空")){
				return;
			}
			if(!checkValue(telNum,"联系方式不能为空")){
				return;
			}
			if(!CheckPhoneUtil.isPhoneOrTel(telNum)){
				ToastUtils.showMessage(CompanyActivity.this, "联系电话格式不正确！");
				return;
			}
			if(!checkValue(areaName,"所在区域不能为空")){
				return;
			}
			if(!checkValue(address,"门店地址不能为空")){
				return;
			}
			if(!checkValue(mInfo,"证件信息不能为空")){
				return;
			}
			if(!checkValue(scope,"经营范围不能为空")){
				return;
			}
			
			mCompanyVo.setCompanyName(companyName);
			mCompanyVo.setBusinessRegNum(regNum);
			mCompanyVo.setPrincipalTelephone(telNum);
			mCompanyVo.setPrincipalPerson(peopleName);
			mCompanyVo.setBusinessAddress(address);
			mCompanyVo.setDocumentInfo(mInfo);
			mCompanyVo.setBusinessScope(scope);
			
			//提交
			mLoading.show();
			HttpRequestHelper.getInstance().submitCompanyInfo(this, mCompanyVo,100, this);
		}
		
	}
	
	private boolean checkValue(String value,String hint){
		if(value==null||value.equals("")){
			ToastUtils.showMessage(getApplicationContext(), hint);
			return false;
		}
		return true;
	}
	
	@Override
	public void onError(String errorMsg) {
		mLoading.dismiss();
		DialogUtil.showMsgDialog(this, errorMsg, true, null);
	}

	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		mLoading.dismiss();
		if(requestCode==100){
			//提交更新返回数据
			DialogUtil.showMsgDialog(CompanyActivity.this, "保存成功", true, null);
		}else{
			mCompanyVo = JSON.parseObject(mJsonResult.getEntity(),CompanyVo.class);
			//更新界面
			mTv_mdmc.setText(StringUtil.nvl(mCompanyVo.getCompanyName()));//企业名称
			mTv_szqy.setText(StringUtil.nvl(mCompanyVo.getAreaName()));//所在区域
			mEt_gszch.setText(StringUtil.nvl(mCompanyVo.getBusinessRegNum()));//工商注册号
			mEt_mdfzr.setText(StringUtil.nvl(mCompanyVo.getPrincipalPerson()));//负责人
			mEt_lxfs.setText(StringUtil.nvl(mCompanyVo.getPrincipalTelephone()));//联系电话
			mEt_mddz.setText(StringUtil.nvl(mCompanyVo.getBusinessAddress()));//单位详细地址
			mEt_zjxx.setText(StringUtil.nvl(mCompanyVo.getDocumentInfo()));//证件信息
			mEt_jyfw.setText(StringUtil.nvl(mCompanyVo.getBusinessScope()));//经营范围
		}
		
	}
	
}
