package com.safetys.zatgov.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
 */
@Entity
public class UserInfo {
    @Id
    private Long id;
    private String userCompany;
    private String factName;
    private boolean isGridPerson;//是否是网格员



    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    @Generated(hash = 1573489136)
    public UserInfo(Long id, String userCompany, String factName,
            boolean isGridPerson) {
        this.id = id;
        this.userCompany = userCompany;
        this.factName = factName;
        this.isGridPerson = isGridPerson;
    }

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

    public Long getId() {
        return this.id;
    }



    public boolean getIsGridPerson() {
        return this.isGridPerson;
    }

    public void setIsGridPerson(boolean isGridPerson) {
        this.isGridPerson = isGridPerson;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
