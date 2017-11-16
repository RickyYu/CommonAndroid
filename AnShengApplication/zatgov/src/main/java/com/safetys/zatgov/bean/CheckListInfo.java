package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class CheckListInfo {
    /**
     * 检查记录id
     */
    private String id;

    /**
     * executeUnit检查单位
     */
    private String executeUnit;
    /**
     * 检查时间
     */
    private String checkTimeBegin;
    /**
     * 企业id
     */
    private String hzCompanyId;
    /**
     * 现场检查记录
     */
    private String content;
    /**
     * 检查场所
     */
    private String checkGround;
    /**
     * 未整改隐患数
     */
    private String wzgNum;
    /**
     * 是否可删除
     */
    private Boolean isReview;
    /**
     * 是否已处罚
     */
    private Boolean punishState;
    /**
     * 是否選中本地用
     */
    private Boolean check;

    private String description;//检查记录下隐患记录


    public Boolean getCheck() {
        return check;
    }
    public void setCheck(Boolean check) {
        this.check = check;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getExecuteUnit() {
        return executeUnit;
    }
    public void setExecuteUnit(String executeUnit) {
        this.executeUnit = executeUnit;
    }
    public String getCheckTimeBegin() {
        return checkTimeBegin;
    }
    public void setCheckTimeBegin(String checkTimeBegin) {
        this.checkTimeBegin = checkTimeBegin;
    }
    public String getHzCompanyId() {
        return hzCompanyId;
    }
    public void setHzCompanyId(String hzCompanyId) {
        this.hzCompanyId = hzCompanyId;
    }
    public String getCheckGround() {
        return checkGround;
    }
    public void setCheckGround(String checkGround) {
        this.checkGround = checkGround;
    }
    public String getWzgNum() {
        return wzgNum;
    }
    public void setWzgNum(String wzgNum) {
        this.wzgNum = wzgNum;
    }
    public Boolean getIsReview() {
        return isReview;
    }
    public void setIsReview(Boolean isReview) {
        this.isReview = isReview;
    }
    public Boolean getPunishState() {
        return punishState;
    }
    public void setPunishState(Boolean punishState) {
        this.punishState = punishState;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
