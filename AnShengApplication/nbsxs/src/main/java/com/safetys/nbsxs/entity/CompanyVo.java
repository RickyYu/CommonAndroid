package com.safetys.nbsxs.entity;

import java.io.Serializable;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 企业基本信息类
 */
public class CompanyVo implements Serializable{

	/**
	 * 企业名称
	 */
	private String companyName;
	
	/**
	 * 公司负责人
	 */
	private String businessRegNum;
	
	/**
	 * 主要负责人
	 */
	private String principalPerson;
	
	/**
	 * 负责人联系电话
	 */
	private String principalTelephone;
	
	/**
	 * 一级区域
	 */
	private String firstArea;
	
	/**
	 * 二级区域
	 */
	private String secondArea;
	
	/**
	 * 三级区域
	 */
	private String thirdArea;
	
	/**
	 * 四级区域
	 */
	private String fouthArea;
	
	/**
	 * 区域名字
	 */
	private String areaName;
	
	/**
	 * 经营地址
	 */
	private String businessAddress;
	
	/**
	 * 证件信息
	 */
	private String documentInfo;
	
	/**
	 * 经营范围
	 */
	private String businessScope;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBusinessRegNum() {
		return businessRegNum;
	}

	public void setBusinessRegNum(String businessRegNum) {
		this.businessRegNum = businessRegNum;
	}

	public String getPrincipalPerson() {
		return principalPerson;
	}

	public void setPrincipalPerson(String principalPerson) {
		this.principalPerson = principalPerson;
	}

	public String getPrincipalTelephone() {
		return principalTelephone;
	}

	public void setPrincipalTelephone(String principalTelephone) {
		this.principalTelephone = principalTelephone;
	}

	public String getFirstArea() {
		return firstArea;
	}

	public void setFirstArea(String firstArea) {
		this.firstArea = firstArea;
	}

	public String getSecondArea() {
		return secondArea;
	}

	public void setSecondArea(String secondArea) {
		this.secondArea = secondArea;
	}

	public String getThirdArea() {
		return thirdArea;
	}

	public void setThirdArea(String thirdArea) {
		this.thirdArea = thirdArea;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getDocumentInfo() {
		return documentInfo;
	}

	public void setDocumentInfo(String documentInfo) {
		this.documentInfo = documentInfo;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	
	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getFouthArea() {
		return fouthArea;
	}
	
	public void setFouthArea(String fouthArea) {
		this.fouthArea = fouthArea;
	}
}
