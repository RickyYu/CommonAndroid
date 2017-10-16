package com.safetys.nbsxs.entity;

/**
 * @author sjw
 * 版本信息
 */
public class VerInfo {

	/**
	 * 版本号用于显示
	 */
	private String versionCode;
	/**
	 * 正式路径
	 */
	private String RealPath;
	/**
	 * 版本号做判断
	 */
	private String versionNum;
	
	
	
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getRealPath() {
		return RealPath;
	}
	public void setRealPath(String fileRealPath) {
		this.RealPath = fileRealPath;
	}
	
	
}
