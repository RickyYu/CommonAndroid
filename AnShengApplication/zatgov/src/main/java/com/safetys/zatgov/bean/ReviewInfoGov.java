package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:复查列表
 */
public class ReviewInfoGov {
    private int id;
    private String fcNum;//复查数
    private String companyName;//公司名
    private String cleanUpTimeLimit;//责令整改时间
    private String jcjlId;//检查记录ID
    private String companyId;//企业ID
    private String hiddenNum;//未整改隐患数
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFcNum() {
        return fcNum;
    }
    public void setFcNum(String fcNum) {
        this.fcNum = fcNum;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCleanUpTimeLimit() {
        return cleanUpTimeLimit;
    }
    public void setCleanUpTimeLimit(String cleanUpTimeLimit) {
        this.cleanUpTimeLimit = cleanUpTimeLimit;
    }
    public String getJcjlId() {
        return jcjlId;
    }
    public void setJcjlId(String jcjlId) {
        this.jcjlId = jcjlId;
    }
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getHiddenNum() {
        return hiddenNum;
    }
    public void setHiddenNum(String hiddenNum) {
        this.hiddenNum = hiddenNum;
    }
}
