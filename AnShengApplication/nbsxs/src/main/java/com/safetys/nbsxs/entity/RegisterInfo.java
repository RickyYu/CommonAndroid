package com.safetys.nbsxs.entity;

import java.io.Serializable;

/**
 * 登记信息类
 */
public class RegisterInfo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 证件类型
	 */
	private String credentials;
	
	/**
	 * 证件编号
	 */
	private String credentialsCode;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 住址
	 */
	private String address;

	/**
	 * 所在单位
	 */
	private String companyName;

	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 品名
	 */
	private String productName;
	
	/**
	 * 松香水数量
	 */
	private String productNumber;
	
	/**
	 * 用途
	 */
	private String content;
	
	/**
	 * 经办人
	 */
	private String companyPerson;
	
	/**
	 * 购买时间
	 */
	private String payTime;

	
	/**
	 * 是否可以修改删除操作,1可以  0不可以
	 */
	private String isOperate;
	
	public String getCompanyPerson() {
		return companyPerson;
	}

	public void setCompanyPerson(String companyPerson) {
		this.companyPerson = companyPerson;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public String getCredentialsCode() {
		return credentialsCode;
	}

	public void setCredentialsCode(String credentialsCode) {
		this.credentialsCode = credentialsCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	public String getIsOperate() {
		return isOperate;
	}
	
	public void setIsOperate(String isOperate) {
		this.isOperate = isOperate;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
