package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:检查表子项
 */
public class SafetyMatter {
    /**
     * 事项id
     */
    private int id = -1;
    /**
     * 事项Infoid
     */
    private int infoId = -1;
    /**
     * 事项名称
     */
    private String content;
    /**
     * 事项备注
     */
    private String remark;
    /**
     * 状态
     */
    private boolean matterState;
    /**
     * 是否被删除
     */
    private boolean deleted;

    /**
     * 主表id
     */
    private SafetyCheck safetyCheck;

    private boolean checked;

    private boolean checkTag;//用于判断在页面操作中是否选中

    public boolean isCheckTag() {
        return checkTag;
    }

    public void setCheckTag(boolean checkTag) {
        this.checkTag = checkTag;
    }

    public SafetyMatter(){

    }

    public SafetyMatter(String content,String remark){
        this.content = content;
        this.remark = remark;
    }

    public boolean isMatterState() {
        return matterState;
    }

    public void setMatterState(boolean matterState) {
        this.matterState = matterState;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public SafetyCheck getSafetyCheck() {
        return safetyCheck;
    }

    public void setSafetyCheck(SafetyCheck safetyCheck) {
        this.safetyCheck = safetyCheck;
    }

}
