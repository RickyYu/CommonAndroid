package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class ReadTextInfo {
    /**
     * 标题
     */
    private String caption;
    /**
     * 发布机构
     */
    private String publisher;
    /**
     * 创建时间
     */
    private String createtime;
    /**
     * 修改时间
     */
    private String modifytime;
    /**
     * 内容
     */
    private String detail;
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getCreatetime() {
        return createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getModifytime() {
        return modifytime;
    }
    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

}
