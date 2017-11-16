package com.safetys.zatgov.bean;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:政府获取一般隐患字段
 */
public class GovGelCheckInfoVo {
    /**
     * 企业id
     */
    private String id;

    /**
     * 完成时间
     */
    private String completedDate;

    /**
     * 是否有隐患
     */
    private boolean danger;
    /**
     * 隐患描述
     */
    private String content;
    /**
     * 联系人
     */
    private String linkMan;
    /**
     * 联系电话
     */
    private String linkManHandset ;
    /**
     * 手机
     */
    private String linkManPhone ;
    /**
     * 隐患类别
     */
    private String type;

    /**
     * 添加隐患用户id
     */
    private String userId;

    /**
     * 计划整改时间
     */
    private String governDate;

    /**
     * 是否整改
     */
    private boolean cleanUp;
    /**
     * 录入时间
     * @return
     */
    private String findTime;
    /**
     * 图片路径
     */
    private String fileRealPath;

    /**
     * 图片名称
     */
    private String fileFactName;
    /**
     * 图片上传前名称
     */
    private String fileFileName;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 隐患未确认整改false
     */
    private boolean check;
    /**
     * 是否政府添加，true不可修改
     */
    private boolean gov;



    private List<ImageInfo> imagesInfo;

    /**
     * 备注
     */
    private String checkInfoId;





    private List<ImageInfo> imagesInfoZf;//整改图片
    private long hzCallBackId;//复查ID





    public String getCheckInfoId() {
        return checkInfoId;
    }
    public void setCheckInfoId(String checkInfoId) {
        this.checkInfoId = checkInfoId;
    }
    public List<ImageInfo> getImagesInfo() {
        return imagesInfo;
    }
    public void setImagesInfo(List<ImageInfo> imagesInfo) {
        this.imagesInfo = imagesInfo;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCompletedDate() {
        return completedDate;
    }
    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }
    public boolean isDanger() {
        return danger;
    }
    public void setDanger(boolean danger) {
        this.danger = danger;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getLinkMan() {
        return linkMan;
    }
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }
    public String getLinkManHandset() {
        return linkManHandset;
    }
    public void setLinkManHandset(String linkManHandset) {
        this.linkManHandset = linkManHandset;
    }
    public String getLinkManPhone() {
        return linkManPhone;
    }
    public void setLinkManPhone(String linkManPhone) {
        this.linkManPhone = linkManPhone;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getGovernDate() {
        return governDate;
    }
    public void setGovernDate(String governDate) {
        this.governDate = governDate;
    }
    public boolean isCleanUp() {
        return cleanUp;
    }
    public void setCleanUp(boolean cleanUp) {
        this.cleanUp = cleanUp;
    }
    public String getFindTime() {
        return findTime;
    }
    public void setFindTime(String findTime) {
        this.findTime = findTime;
    }
    public String getFileRealPath() {
        return fileRealPath;
    }
    public void setFileRealPath(String fileRealPath) {
        this.fileRealPath = fileRealPath;
    }
    public String getFileFactName() {
        return fileFactName;
    }
    public void setFileFactName(String fileFactName) {
        this.fileFactName = fileFactName;
    }
    public String getFileFileName() {
        return fileFileName;
    }
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public boolean isCheck() {
        return check;
    }
    public void setCheck(boolean check) {
        this.check = check;
    }
    public boolean isGov() {
        return gov;
    }
    public void setGov(boolean gov) {
        this.gov = gov;
    }
    public List<ImageInfo> getImagesInfoZf() {
        return imagesInfoZf;
    }
    public void setImagesInfoZf(List<ImageInfo> imagesInfoZf) {
        this.imagesInfoZf = imagesInfoZf;
    }
    public long getHzCallBackId() {
        return hzCallBackId;
    }
    public void setHzCallBackId(long hzCallBackId) {
        this.hzCallBackId = hzCallBackId;
    }
}
