package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:企业信息
 */
public class CompanyVo {
    private String id;
    private String companyName;
    private String fdDelegate;
    private String firstArea;
    private String secondArea;
    private String thirdArea;
    private String x;
    private String y;
    private String dangerNum;//企业隐患数
    private String gridDangerNum;//政府隐患数
    private String allDangerNum;//所有隐患数
    private boolean isXiaofang;//是否是消防企业
    private boolean isAnjian;//是否是安监企业
    private String industry ;
    private String industryName;
    private String companyType;
    private String companyTypeName;
    private long createTime;//企业最新隐患时间

    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public String getIndustryName() {
        return industryName;
    }
    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
    public String getCompanyType() {
        return companyType;
    }
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
    public String getCompanyTypeName() {
        return companyTypeName;
    }
    public void setCompanyTypeName(String companyTypeName) {
        this.companyTypeName = companyTypeName;
    }
    public boolean isXiaofang() {
        return isXiaofang;
    }
    public void setXiaofang(boolean isXiaofang) {
        this.isXiaofang = isXiaofang;
    }
    public boolean isAnjian() {
        return isAnjian;
    }
    public void setAnjian(boolean isAnjian) {
        this.isAnjian = isAnjian;
    }
    public String getGridDangerNum() {
        return gridDangerNum;
    }
    public void setGridDangerNum(String gridDangerNum) {
        this.gridDangerNum = gridDangerNum;
    }
    public String getAllDangerNum() {
        return allDangerNum;
    }
    public void setAllDangerNum(String allDangerNum) {
        this.allDangerNum = allDangerNum;
    }
    public String getDangerNum() {
        return dangerNum;
    }
    public void setDangerNum(String dangerNum) {
        this.dangerNum = dangerNum;
    }
    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }
    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getFdDelegate() {
        return fdDelegate;
    }
    public void setFdDelegate(String fdDelegate) {
        this.fdDelegate = fdDelegate;
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
    public long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
