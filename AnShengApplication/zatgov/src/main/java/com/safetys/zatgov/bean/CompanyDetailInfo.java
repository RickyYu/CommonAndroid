package com.safetys.zatgov.bean;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class CompanyDetailInfo {
    /**
     * 企业名称
     */

    private String companyName ;
    /**
     * 地址
     */

    private String address;
    /**
     * 联系人
     */

    private String fdDelegate ;
    /**
     * 参与检查人员
     */

    private List<UserIds> userNames;
    /**
     * 检查人/记录人
     */

    private String noter ;
    /**
     * 执法单位
     */

    private String  executeUnit;
    /**
     * 发送整改通知书
     */

    private boolean  sendCleanUp;



    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getNoter() {
        return noter;
    }
    public void setNoter(String noter) {
        this.noter = noter;
    }
    public String getExecuteUnit() {
        return executeUnit;
    }
    public void setExecuteUnit(String executeUnit) {
        this.executeUnit = executeUnit;
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


    public boolean isSendCleanUp() {
        return sendCleanUp;
    }
    public void setSendCleanUp(boolean sendCleanUp) {
        this.sendCleanUp = sendCleanUp;
    }
    public List<UserIds> getUserNames() {
        return userNames;
    }
    public void setUserNames(List<UserIds> userNames) {
        this.userNames = userNames;
    }
}
