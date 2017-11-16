package com.safetys.zatgov.bean;

import java.io.Serializable;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class HyCheckItemInfo implements Serializable {

    private long id;
    /**
     * 标题
     */
    private String content;
    /**
     * 内容
     */
    private String description;
    /**
     * 1是重大，0是一般
     */

    private String isBig;

    /**
     * 1是重大，2是一般
     */

    private String type;

    private String noteId;

    /**
     * 录入时间
     */
    private String createTime;

    private boolean repaired;// 是否整改

    private int checkInfoId;

    /**
     * true是接管
     */
    private boolean take;
    /**
     * BENDI接管
     */
    private boolean takeLocal;
    /**
     * 是否政府
     */
    private String sourceType;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HyCheckItemInfo() {
        super();
    }

    public HyCheckItemInfo(long id, String content, String description,
                           int checkInfoId) {
        super();
        this.id = id;
        this.content = content;
        this.description = description;
        this.checkInfoId = checkInfoId;
    }

    public HyCheckItemInfo(long id, String content, String description,
                           int checkInfoId, String remark) {
        super();
        this.id = id;
        this.content = content;
        this.description = description;
        this.checkInfoId = checkInfoId;
        this.remark = remark;
    }

    public HyCheckItemInfo(String content, String description) {
        super();
        this.content = content;
        this.description = description;
    }

    public int getCheckInfoId() {
        return checkInfoId;
    }

    public void setCheckInfoId(int checkInfoId) {
        this.checkInfoId = checkInfoId;
    }

    public boolean isTakeLocal() {
        return takeLocal;
    }

    public void setTakeLocal(boolean takeLocal) {
        this.takeLocal = takeLocal;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public boolean isTake() {
        return take;
    }

    public void setTake(boolean take) {
        this.take = take;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isRepaired() {
        return repaired;
    }

    public void setRepaired(boolean repaired) {
        this.repaired = repaired;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getIsBig() {
        return isBig;
    }

    public void setIsBig(String isBig) {
        this.isBig = isBig;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}