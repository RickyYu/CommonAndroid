package com.safetys.zatgov.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:复查列表
 */
@Entity
public class ReviewInfoGov {
    @Id
    private Long dataId;
    private int id;
    private String fcNum;//复查数
    private String companyName;//公司名
    private String cleanUpTimeLimit;//责令整改时间
    private String jcjlId;//检查记录ID
    private String companyId;//企业ID
    private String hiddenNum;//未整改隐患数
    @Generated(hash = 811261746)
    public ReviewInfoGov(Long dataId, int id, String fcNum, String companyName,
            String cleanUpTimeLimit, String jcjlId, String companyId,
            String hiddenNum) {
        this.dataId = dataId;
        this.id = id;
        this.fcNum = fcNum;
        this.companyName = companyName;
        this.cleanUpTimeLimit = cleanUpTimeLimit;
        this.jcjlId = jcjlId;
        this.companyId = companyId;
        this.hiddenNum = hiddenNum;
    }
    @Generated(hash = 775351559)
    public ReviewInfoGov() {
    }
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
    public Long getDataId() {
        return this.dataId;
    }
    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }
}
