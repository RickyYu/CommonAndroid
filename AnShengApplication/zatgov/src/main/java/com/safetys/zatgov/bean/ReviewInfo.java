package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description: 复查情况
 */
public class ReviewInfo {


    private int id;
    private String executer;//复查人员
    private String companyName;//公司名
    private String callbackTime;//复查时间
    private String hiddenState;//隐患整改情况
    private Boolean isPigeonhole;//状态
    private String callBackNum;//复查编号
    private Boolean isCallBack;//是否生成复查表
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getExecuter() {
        return executer;
    }
    public void setExecuter(String executer) {
        this.executer = executer;
    }
    public String getCallbackTime() {
        return callbackTime;
    }
    public void setCallbackTime(String callbackTime) {
        this.callbackTime = callbackTime;
    }
    public String getHiddenState() {
        return hiddenState;
    }
    public void setHiddenState(String hiddenState) {
        this.hiddenState = hiddenState;
    }
    public Boolean getIsPigeonhole() {
        return isPigeonhole;
    }
    public void setIsPigeonhole(Boolean isPigeonhole) {
        this.isPigeonhole = isPigeonhole;
    }
    public String getCallBackNum() {
        return callBackNum;
    }
    public void setCallBackNum(String callBackNum) {
        this.callBackNum = callBackNum;
    }
    public Boolean getIsCallBack() {
        return isCallBack;
    }
    public void setIsCallBack(Boolean isCallBack) {
        this.isCallBack = isCallBack;
    }
    @Override
    public String toString() {
        return "ReviewInfo [id=" + id + ", executer=" + executer
                + ", companyName=" + companyName + ", callbackTime="
                + callbackTime + ", hiddenState=" + hiddenState
                + ", isPigeonhole=" + isPigeonhole + ", callBackNum="
                + callBackNum + ", isCallBack=" + isCallBack + "]";
    }
}
