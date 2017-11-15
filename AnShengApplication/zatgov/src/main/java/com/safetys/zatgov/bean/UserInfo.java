package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
 */
public class UserInfo {
    private String userCompany;
    private String factName;
    private boolean isGridPerson;//是否是网格员

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getFactName() {
        return factName;
    }

    public void setFactName(String factName) {
        this.factName = factName;
    }

    public boolean isGridPerson() {
        return isGridPerson;
    }

    public void setGridPerson(boolean gridPerson) {
        isGridPerson = gridPerson;
    }
}
