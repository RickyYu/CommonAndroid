package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:处罚信息
 */
public class Punishment {

    private int id;
    /**
     * 处罚类型
     */
    private String punishmentType;
    /**
     * 企业名字
     */
    private String companyName;
    /**
     * 处罚时间
     */
    private String punishmentTime;
    /**
     * 法定代表人
     */
    private String fdDelegate;
    /**
     * 企业地址
     */
    private String address;
    /**
     * 备注
     */
    private String Remark;
    /**
     * 处罚内容
     */
    private String Content;
    /**
     * 处罚原因
     */
    private String punishmentCause;
    /**
     * 处罚单位
     */
    private String punishmentUnit;

    /**
     * check id
     */
    private String punishmentId;



    public String getPunishmentId() {
        return punishmentId;
    }
    public void setPunishmentId(String punishmentId) {
        this.punishmentId = punishmentId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPunishmentType() {
        return punishmentType;
    }
    public void setPunishmentType(String punishmentType) {
        this.punishmentType = punishmentType;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getPunishmentTime() {
        return punishmentTime;
    }
    public void setPunishmentTime(String punishmentTime) {
        this.punishmentTime = punishmentTime;
    }
    public String getFdDelegate() {
        return fdDelegate;
    }
    public void setFdDelegate(String fdDelegate) {
        this.fdDelegate = fdDelegate;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRemark() {
        return Remark;
    }
    public void setRemark(String remark) {
        Remark = remark;
    }
    public String getContent() {
        return Content;
    }
    public void setContent(String content) {
        Content = content;
    }
    public String getPunishmentCause() {
        return punishmentCause;
    }
    public void setPunishmentCause(String punishmentCause) {
        this.punishmentCause = punishmentCause;
    }
    public String getPunishmentUnit() {
        return punishmentUnit;
    }
    public void setPunishmentUnit(String punishmentUnit) {
        this.punishmentUnit = punishmentUnit;
    }
}
