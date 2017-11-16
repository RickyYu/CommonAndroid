package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class HyCheckInfo {
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 标题
     */
    private String createTime;
    /**
     * 内容
     */
    private int totalCount;

    private boolean check;//本地识别选中状态



    public boolean getCheck() {
        return check;
    }
    public void setCheck(boolean check) {
        this.check = check;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    private String content;

    private boolean isToogle;

    /**
     * 检查事项id
     */
    private long infoId;




    public long getInfoId() {
        return infoId;
    }
    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }
    public boolean isToogle() {
        return isToogle;
    }
    public void setToogle(boolean isToogle) {
        this.isToogle = isToogle;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
