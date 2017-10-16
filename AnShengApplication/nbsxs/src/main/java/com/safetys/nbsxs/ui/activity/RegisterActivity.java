package com.safetys.nbsxs.ui.activity;


import java.util.ArrayList;
import java.util.List;

import org.xutils.common.util.LogUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.safetys.nbsxs.R;
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
 * 销售实名登记
 */
public class RegisterActivity extends BaseActivity implements OnClickListener,onNetCallback{

	private View btn_back;
	private EditText mEt_name;//姓名
	private EditText mEt_idcard;//证件号码
	private EditText mEt_address;//住址
	private EditText mEt_unit;//所在单位
	private EditText mEt_tel;//联系电话
	private EditText mEt_pm;//品名
	private EditText mEt_num;//数量
	private EditText mEt_yt;//用途
	private EditText mEt_operator;//经办人
	private TextView mTv_idType;//证件类型
	
	private Button mBtn_save;
	
	private LoadingDialogUtil mLoading;//正在加载
	private RegisterInfo mRegisterInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		setHeadTitle("销售实名登记");
		btn_back = findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		View title=findViewById(R.id.title_bar);
		title.setBackgroundResource(R.drawable.qytitle);
		
		//保存按钮
		
		
		mBtn_save = (Button) findViewById(R.id.btn_save);
		mBtn_save.setOnClickListener(this);
		
		mEt_name = (EditText) findViewById(R.id.tv_name);
		mEt_idcard = (EditText) findViewById(R.id.tv_idcard);
		mEt_address = (EditText) findViewById(R.id.tv_address);
		mEt_unit = (EditText) findViewById(R.id.tv_unit);
		mEt_tel = (EditText) findViewById(R.id.tv_tel);
		mEt_pm = (EditText) findViewById(R.id.tv_pm);
		mEt_num = (EditText) findViewById(R.id.tv_num);
		mEt_yt = (EditText) findViewById(R.id.tv_yt);
		mEt_operator= (EditText) findViewById(R.id.tv_operator);
		mEt_pm.setText("松香水");
		
		mTv_idType = (TextView) findViewById(R.id.tv_idtype);
		mTv_idType.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<String> mDatas = new ArrayList<String>();
				mDatas.add("身份证");
				mDatas.add("护照");
				mDatas.add("驾驶证");
				SigleWheelDialog mDialog = new SigleWheelDialog(RegisterActivity.this, mDatas);
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
		
		mEt_num.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String temp = s.toString();  
                int posDot = temp.indexOf(".");  
                if (posDot <= 0) return;  
                if (temp.length() - posDot - 1 > 2)  
                {  
                    s.delete(posDot + 3, posDot + 4);  
                }  
				
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
		}else if(v == mBtn_save){
			//保存
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
			
			if(!checkValue(tel,"联系电话不能为空")){
				return;
			}
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
			if(!checkValue(pm,"品名不能为空")){
				return;
			}
			if(!checkValue(num,"数量不能为空")){
				return;
			}
			if(!tel.equals("")&&!CheckPhoneUtil.isPhoneOrTel(tel)){
				ToastUtils.showMessage(RegisterActivity.this, "联系电话格式不正确！");
				return;
			}
			mRegisterInfo = new RegisterInfo();
			mRegisterInfo.setAddress(address);
			mRegisterInfo.setCompanyName(unit);
			mRegisterInfo.setContent(yt);
			mRegisterInfo.setCredentials(IdTypeEnum.getValue(idType));
			mRegisterInfo.setCredentialsCode(idcard);
			mRegisterInfo.setName(name);
			mRegisterInfo.setCompanyPerson(operator);
			mRegisterInfo.setPhone(tel);
			mRegisterInfo.setProductName(pm);
			mRegisterInfo.setProductNumber(num);
			//提交
			mLoading.show();
			HttpRequestHelper.getInstance().submitSellInfo(this, mRegisterInfo, this);
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
		//提交更新返回数据
		DialogUtil.showMsgDialog(RegisterActivity.this, "保存成功", true, null);
	}
	
}
