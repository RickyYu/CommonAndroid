package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:安全检查表  主表
 */
public class SafetyCheck {
    private int id;
    private String title;
    private String note;
    private String remark;
    private String createTime;
    private boolean checkState;
    private boolean deleted;
    private boolean check;

    public SafetyCheck() {
        super();
    }

    public SafetyCheck(int id, String createTime,String title){
        super();
        this.id = id;
        this.title = title;
        this.createTime = createTime;
    }

    public SafetyCheck(int id, String title, String note, String remark,
                       String createTime, boolean checkState, boolean deleted,
                       boolean check) {
        super();
        this.id = id;
        this.title = title;
        this.note = note;
        this.remark = remark;
        this.createTime = createTime;
        this.checkState = checkState;
        this.deleted = deleted;
        this.check = check;
    }

    public boolean getCheck() {
        return check;
    }
    public void setCheck(boolean check) {
        this.check = check;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public boolean isCheckState() {
        return checkState;
    }
    public void setCheckState(boolean checkState) {
        this.checkState = checkState;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
