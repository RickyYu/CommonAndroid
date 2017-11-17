package com.safetys.zatgov.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.ZatApplication;
import com.safetys.zatgov.bean.CompanyInfo;
import com.safetys.zatgov.config.BornTypeEnum;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.EconomyTypeEnum;
import com.safetys.zatgov.config.IsEnterTypeEnum;
import com.safetys.zatgov.config.SecondTypeEnum;
import com.safetys.zatgov.config.ThirdTypeEnum;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 企业信息详情
 */
public class EnterpriseItemFragment extends Fragment implements
		OnClickListener, onNetCallback {
	private EditText ed_person;
	private EditText ed_phone;
	private TextView ed_typejj;
	private TextView ed_big;
	private View btn1;
	private View btn2;
	private String businessRegNumber;
	private String companyName;
	private String adress;
	private String tradeBig;
	private String tradeMid;
	private String tradeType;
	private String tradeTypes;
	private String type;
	private String fd;
	private String phone;
	private String big;
	private String typejj;
	private String one;
	private String two;
	private String three;
	private LoadingDialogUtil mLoading;
	private CompanyInfo mCompanyInfo;
	private EditText tv_name;
	private EditText tv_address;
	private EditText tv_buss;
	private TextView tv_types;
	private TextView tv_tradeType;
	private TextView tv_tradeBig;
	private TextView tv_tradeMid;
	private String oneCode;
	private String twoCode;
	private String threeCode;
	private View btny;
	private TextView tv_qy;
	private String bigCode;
	private String typejjCode;
	private View btn_submit;
	private View btn_yh_qy;
	private String id2;
	private View back;
	private TextView ed_born;
	private String bornCode;
	private String jd=null;
	private String y=null;
	
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.activity_enterprise_detail, null);
		mLoading = new LoadingDialogUtil(getActivity(),"请稍后...");
		initView(mView);
		
		return mView;
		
	}

	

	private void initView(View mView) {
		
		Intent intent = getActivity().getIntent();
		id2 = intent.getExtras().getString("id");
		
		
		mLoading.show();
		HttpRequestHelper.getInstance().getCompanyInfo(getActivity(), id2, 0,this);
		
		View title_baryh=mView.findViewById(R.id.title_baryh);
		title_baryh.setVisibility(View.GONE);
		
		tv_name = (EditText) mView.findViewById(R.id.tv_name);
		tv_address = (EditText) mView.findViewById(R.id.tv_address);
		
		btn_yh_qy = mView.findViewById(R.id.btn_yh_qy);
		btn_yh_qy.setOnClickListener(this);
		tv_qy = (TextView) mView.findViewById(R.id.tv_qy);
		tv_buss = (EditText) mView.findViewById(R.id.tv_buss);
		tv_types = (TextView) mView.findViewById(R.id.tv_types);
		tv_tradeType = (TextView) mView.findViewById(R.id.tv_tradeType);
		tv_tradeBig = (TextView) mView.findViewById(R.id.tv_tradeBig);
		tv_tradeMid = (TextView) mView.findViewById(R.id.tv_tradeMid);
		
		
		ed_born = (TextView) mView.findViewById(R.id.ed_born);
		ed_person = (EditText) mView.findViewById(R.id.ed_person);
		ed_phone = (EditText) mView.findViewById(R.id.ed_phone);
		ed_typejj = (TextView) mView.findViewById(R.id.ed_typejj);
		ed_big = (TextView) mView.findViewById(R.id.ed_big);
		btn1 = mView.findViewById(R.id.btn_yh_type);
		btn1.setOnClickListener(this);
		btn2 = mView.findViewById(R.id.btn_yh_type2);
		
		btn2.setOnClickListener(this);
		
		btn_submit = mView.findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		
		
		case R.id.btn_yh_type:
			ArrayList<String> datas = new ArrayList<String>();
			Collections.addAll(datas, EconomyTypeEnum.typedata);
			//// FIXME: 2017/11/16 
	/*		SigleWheelDialog mChangeAddressDialog = new SigleWheelDialog(
					getActivity(), datas);
			mChangeAddressDialog.setText(ed_typejj.getText().toString());
			mChangeAddressDialog.show();
			mChangeAddressDialog.setAddresskListener(new OnTextCListener() {

				
				@Override
				public void onClick(String mText) {
					ed_typejj.setText(mText);
					typejjCode = EconomyTypeEnum.getValue(mText);
				}
			});*/
			
			break;
			
		case R.id.btn_yh_type2:
			ArrayList<String> datas2 = new ArrayList<String>();
			Collections.addAll(datas2, IsEnterTypeEnum.typedata);
			//// FIXME: 2017/11/16 
	/*		SigleWheelDialog mChangeAddressDialog2 = new SigleWheelDialog(
					getActivity(), datas2);
			mChangeAddressDialog2.setText(ed_big.getText().toString());
			mChangeAddressDialog2.show();
			mChangeAddressDialog2.setAddresskListener(new OnTextCListener() {
				@Override
				public void onClick(String mText) {
					ed_big.setText(mText);
					bigCode = IsEnterTypeEnum.getValue(mText);
				}
			});*/
			
			break;
			
//		case R.id.btn_yh_type3:
//			ArrayList<String> datas3 = new ArrayList<String>();
//			Collections.addAll(datas3, BornTypeEnum.typedata);
//			SigleWheelDialog mChangeAddressDialog3 = new SigleWheelDialog(
//					getActivity(), datas3);
//			mChangeAddressDialog3.setText(ed_big.getText().toString());
//			mChangeAddressDialog3.show();
//			mChangeAddressDialog3.setAddresskListener(new OnTextCListener() {
//
//				
//
//				@Override
//				public void onClick(String mText) {
//					ed_born.setText(mText);
//					bornCode = BornTypeEnum.getValue(mText);
//				}
//			});
//			
//			break;
			
		case R.id.btn_yh_qy:
			//// FIXME: 2017/11/16
/*			new StreetSelectDialog(getActivity(),new cn.safetys.ywngovernment.view.StreetSelectDialog.OnTextCListener() {
				
				@Override
				public void onClick(String mText,String pcode,String ccode, String dcode) {

					tv_qy.setText(mText);
					oneCode= pcode;
					twoCode= ccode;
					threeCode= dcode;
				}
			}).show();*/
			break;
			
		case R.id.btn_submit:
			submit();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 检查文本是否都已经输入
	 * @return
	 */
	private void submit() {
		String personName = ed_person.getText().toString();//联系人
		String phone = ed_phone.getText().toString();//联系电话
		companyName=tv_name.getText().toString();
		adress=tv_address.getText().toString();
		businessRegNumber=tv_buss.getText().toString();
		
		if(TextUtils.isEmpty(companyName)){
			ToastUtils.showMessage(getActivity(), "单位名称不能为空");
			return;
		}
		if(TextUtils.isEmpty(adress)){
			ToastUtils.showMessage(getActivity(), "单位地址不能为空");
			return;
		}
		if(TextUtils.isEmpty(businessRegNumber)){
			ToastUtils.showMessage(getActivity(), "工商注册号不能为空");
			return;
		}
		if(TextUtils.isEmpty(personName)){
			ToastUtils.showMessage(getActivity(), "联系人不能为空");
			return;
		}
		if(TextUtils.isEmpty(phone)){
			ToastUtils.showMessage(getActivity(), "联系电话不能为空");
			return;
		}
		mLoading.show();
		HttpRequestHelper.getInstance().changeCompany(getActivity(),id2, companyName, adress,
				businessRegNumber, 
				oneCode, twoCode, threeCode, 
				personName, phone,  
				typejjCode,bigCode,bornCode,jd,y,
				Const.NET_CHANGE_COMPANY_INFO_CODE, this);
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
		case 0:
			// 获取企业信息返回
			if (mJsonResult.getEntity() != null&&!mJsonResult.getEntity().equals("{}")) {
				mCompanyInfo = JSON.parseObject(mJsonResult.getEntity(),
						CompanyInfo.class);
				companyName = mCompanyInfo.getCompanyName();
				adress = mCompanyInfo.getAddress();
				tradeBig = mCompanyInfo.getTradeBig();
				tradeMid = mCompanyInfo.getTradeMid();
				tradeType = mCompanyInfo.getTradeType();
				tradeTypes = mCompanyInfo.getTradeTypes();
				fd = mCompanyInfo.getFdDelegate();
				phone = mCompanyInfo.getPhone();
				businessRegNumber = mCompanyInfo.getBusinessRegNumber();
				bigCode = mCompanyInfo.getIsEnterprise();
				typejjCode = mCompanyInfo.getEconomyKind();
				big=IsEnterTypeEnum.getValue(bigCode);
				typejj=EconomyTypeEnum.getValue(typejjCode);
				
				
				oneCode = mCompanyInfo.getFirstArea();
				twoCode = mCompanyInfo.getSecondArea();
				threeCode = mCompanyInfo.getThirdArea();
				
				one="湖州市";
				two=twoCode==null ? " ": SecondTypeEnum.getValue(twoCode);
//				two=SecondTypeEnum.getValue(twoCode);
				three=threeCode==null?" ": ThirdTypeEnum.getValue(threeCode);
				tv_qy.setText(one+two+three);
				
				bornCode = mCompanyInfo.getIsProduction();
				
				ed_born.setText(BornTypeEnum.getValue(bornCode));
				
				ed_person.setText(fd);
				ed_phone.setText(phone);
				ed_big.setText(big);
				ed_typejj.setText(typejj);
				
				tv_name.setText(companyName);
				tv_address.setText(adress);
				tv_types.setText( tradeTypes);
				tv_tradeType.setText(mCompanyInfo.getTradeString(
						((ZatApplication) x.app()), tradeType));
				tv_tradeBig.setText( mCompanyInfo.getTradeString(
						((ZatApplication) x.app()), tradeBig));
				tv_tradeMid.setText(mCompanyInfo.getTradeString(
						((ZatApplication) x.app()), tradeMid));
				tv_buss.setText(businessRegNumber);
			}

			break;

		case Const.NET_CHANGE_COMPANY_INFO_CODE:
			DialogUtil.showMsgDialog(getActivity(), "修改成功", true, null);
			
			break;
		default:
			break;
	}

	}
	
	
}



