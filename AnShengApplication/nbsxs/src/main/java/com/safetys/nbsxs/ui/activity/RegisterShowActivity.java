package com.safetys.nbsxs.ui.activity;


import java.util.ArrayList;
import java.util.List;

import org.xutils.common.util.LogUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.safetys.nbsxs.R;
import cn.safetys.nbsxs.base.AppApplication;
import cn.safetys.nbsxs.base.BaseActivity;
import cn.safetys.nbsxs.bean.CompanyVo;
import cn.safetys.nbsxs.bean.JsonResult;
import cn.safetys.nbsxs.bean.RegisterInfo;
import cn.safetys.nbsxs.config.IdTypeEnum;
import cn.safetys.nbsxs.http.HttpRequestHelper;
import cn.safetys.nbsxs.http.onNetCallback;
import cn.safetys.nbsxs.util.CheckPhoneUtil;
import cn.safetys.nbsxs.util.DialogUtil;
import cn.safetys.nbsxs.util.IdCardUtil;
import cn.safetys.nbsxs.util.LoadingDialogUtil;
import cn.safetys.nbsxs.util.StringUtil;
import cn.safetys.nbsxs.util.ToastUtils;
import cn.safetys.nbsxs.wheel.widget.views.SigleWheelDialog;
import cn.safetys.nbsxs.wheel.widget.views.SigleWheelDialog.OnTextCListener;
import cn.safetys.nbsxs.wheel.widget.views.StreetSelectDialog;

/**
 * 销售实名登记展示  修改 删除
 */
public class RegisterShowActivity extends BaseActivity implements OnClickListener,onNetCallback{

	private View btn_back;
	private EditText mEt_name;//姓名
	private EditText mEt_idcard;//证件号码
	private EditText mEt_address;//住址
	private EditText mEt_unit;//所在单位
	private EditText mEt_operator;//经办人
	private EditText mEt_tel;//联系电话
	private EditText mEt_pm;//品名
	private EditText mEt_num;//数量
	private EditText mEt_yt;//用途
	private TextView mTv_time;//购买时间
	
	private TextView mTv_idType;//证件类型
	
	private Button mBtn_save;//
	
	private LoadingDialogUtil mLoading;//正在加载
	private RegisterInfo mRegisterInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		initData();
	}

	private void initData() {
		mRegisterInfo = (RegisterInfo) getIntent().getSerializableExtra("info");
		
		mEt_name.setText(mRegisterInfo.getName());
		mEt_idcard.setText(StringUtil.nvl(mRegisterInfo.getCredentialsCode()));
		mEt_address.setText(StringUtil.nvl(mRegisterInfo.getAddress()));
		mEt_unit.setText(mRegisterInfo.getCompanyName());
	    mEt_operator.setText(mRegisterInfo.getCompanyPerson());
		mEt_tel.setText(mRegisterInfo.getPhone());
		mEt_pm.setText(mRegisterInfo.getProductName());
		mEt_num.setText(mRegisterInfo.getProductNumber());
		mEt_yt.setText(mRegisterInfo.getContent());
		mTv_time.setText(mRegisterInfo.getPayTime());
		mTv_idType.setText(IdTypeEnum.getValue(mRegisterInfo.getCredentials()));
		
		
		//是否可删除可修改
		if(!mRegisterInfo.getIsOperate().equals("1")){
			//不可删除修改
			mBtn_save.setVisibility(View.GONE);
			mEt_name.setEnabled(false);
			mEt_idcard.setEnabled(false);
			mEt_address.setEnabled(false);
			mEt_unit.setEnabled(false);
			mEt_operator.setEnabled(false);
			mEt_tel.setEnabled(false);
			mEt_pm.setEnabled(false);
			mEt_num.setEnabled(false);
			mEt_yt.setEnabled(false);
			
			mEt_tel.setHint("");
			mEt_address.setHint("");
			mEt_unit.setHint("");
			mEt_yt.setHint("");
		}else{
			//可删除可以修改
			//右上角功能按钮
			setRightTitle("删除");
			setRightBtnClick(this);
			
			mTv_idType.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					List<String> mDatas = new ArrayList<String>();
					mDatas.add("身份证");
					mDatas.add("护照");
					mDatas.add("驾驶证");
					SigleWheelDialog mDialog = new SigleWheelDialog(RegisterShowActivity.this, mDatas);
					mDialog.setAddresskListener(new OnTextCListener() {
						
						@Override
						public void onClick(String mText) {
							// TODO Auto-generated method stub
							mTv_idType.setText(mText);
						}
					});
					mDialog.setText(mTv_idType.getText().toString());
					mDialog.show();
				}
			});
		}
	}

	private void initView() {
		setHeadTitle("销售实名登记");
		btn_back = findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		View title=findViewById(R.id.title_bar);
		title.setBackgroundResource(R.drawable.qytitle);
		
//		//右上角功能按钮
//		setRightTitle("删除");
//		setRightBtnClick(this);
		
		mBtn_save = (Button) findViewById(R.id.btn_save);
		mBtn_save.setOnClickListener(this);
		
		mEt_name = (EditText) findViewById(R.id.tv_name);
		mEt_idcard = (EditText) findViewById(R.id.tv_idcard);
		mEt_address = (EditText) findViewById(R.id.tv_address);
		mEt_unit = (EditText) findViewById(R.id.tv_unit);
		mEt_operator= (EditText) findViewById(R.id.tv_operator);
		mEt_tel = (EditText) findViewById(R.id.tv_tel);
		mEt_pm = (EditText) findViewById(R.id.tv_pm);
		mEt_num = (EditText) findViewById(R.id.tv_num);
		mEt_yt = (EditText) findViewById(R.id.tv_yt);
		mTv_time = (TextView) findViewById(R.id.tv_time);
		
		findViewById(R.id.view_time).setVisibility(View.VISIBLE);
		findViewById(R.id.view_line).setVisibility(View.VISIBLE);
		
		mTv_idType = (TextView) findViewById(R.id.tv_idtype);
		
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
		}else if(v==mBtn_save){
			//右上角功能按钮--保存
			//校验字段
			String name = mEt_name.getText().toString();
			String address = mEt_address.getText().toString();
			String unit = mEt_unit.getText().toString();
			String operator = mEt_operator.getText().toString();
			String tel = mEt_tel.getText().toString();
			String idcard = mEt_idcard.getText().toString().toUpperCase();
			String pm = mEt_pm.getText().toString();
			String num = mEt_num.getText().toString();
			String yt = mEt_yt.getText().toString();
			String idType = mTv_idType.getText().toString();
			
			if(!checkValue(name,"姓名不能为空")){
				return;
			}
			if(!checkValue(idcard,"证件号码不能为空")){
				return;
			}
			if(idType.equals("身份证")||idType.equals("驾驶证")){
				IdCardUtil mCardUtil = new IdCardUtil(idcard,idType.equals("身份证")?null:"驾驶证");
				if(mCardUtil.isCorrect()!=0){
					ToastUtils.showMessage(getApplicationContext(),  mCardUtil.getErrMsg());
					return;
				}
			}else{
				if(idcard.length()<7||idcard.length()>9){
					ToastUtils.showMessage(getApplicationContext(),  "护照号码长度不对！");
					return;
				}
			}
//			if(!checkValue(address,"住址不能为空")){
//				return;
//			}
//			if(!checkValue(unit,"所在单位不能为空")){
//				return;
//			}
//			if(!checkValue(tel,"联系电话不能为空")){
//				return;
//			}
	/*		if(!checkValue(operator,"经办人不能为空")){
				return;
			}*/
			if(!checkValue(pm,"品名不能为空")){
				return;
			}
			if(!checkValue(num,"数量不能为空")){
				return;
			}
			if(!tel.equals("")&&!CheckPhoneUtil.isPhoneOrTel(tel)){
				ToastUtils.showMessage(RegisterShowActivity.this, "联系电话格式不正确！");
				return;
			}
			mRegisterInfo.setAddress(address);
			mRegisterInfo.setCompanyName(unit);
			mRegisterInfo.setContent(yt);
			mRegisterInfo.setCredentials(IdTypeEnum.getValue(idType));
			mRegisterInfo.setCredentialsCode(idcard);
			mRegisterInfo.setName(name);
			mRegisterInfo.setPhone(tel);
			mRegisterInfo.setCompanyPerson(operator);
			mRegisterInfo.setProductName(pm);
			mRegisterInfo.setProductNumber(num);
			//提交
			mLoading.show();
			HttpRequestHelper.getInstance().submitSellInfo(this, mRegisterInfo, this);
		}else if(v.getId()==R.id.title_right){
			DialogUtil.showMsgDialog2(RegisterShowActivity.this	, "是否删除？", "取消", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mLoading.show();
					HttpRequestHelper.getInstance().deleteSellInfo(RegisterShowActivity.this,
							mRegisterInfo.getId(), 100, RegisterShowActivity.this);
				}
			});
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
			DialogUtil.showMsgDialog(RegisterShowActivity.this, "删除成功", true, null);
			//刷新列表
			Intent mIntent = new Intent();
			mIntent.putExtra("isDelete", true);
			setResult(Activity.RESULT_OK, mIntent);
		}else{
			//提交更新返回数据
			DialogUtil.showMsgDialog(RegisterShowActivity.this, "保存成功", true, null);
			//刷新列表
			Intent mIntent = new Intent();
			mIntent.putExtra("isUpdate", true);
			mIntent.putExtra("info", mRegisterInfo);
			setResult(Activity.RESULT_OK, mIntent);
		}
	}
	
}
