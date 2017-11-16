package com.safetys.zatgov.bean;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:企业获取一般隐患详情
 */
public class CpyGelCheckInfoVo {

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
    private String description;
    /**
     * 联系人
     */
    private String linkMan;
    /**
     * 联系电话
     */
    private String linkMobile;
    /**
     * 手机
     */
    private String linkTel;
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
    private String rectificationPlanTime;

    /**
     * 是否整改
     */
    private boolean repaired;
    /**
     * 录入时间
     * @return
     */
    private String createTime;
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
    /**
     * 新-真-关联检查项
     */
    private String safetyMatterName;


    private List<ImageInfo> imagesInfoZg;//整改图片
    private long hzCallBackId;//复查ID



    public String getSafetyMatterName() {
        return safetyMatterName;
    }

    public void setSafetyMatterName(String safetyMatterName) {
        this.safetyMatterName = safetyMatterName;
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

    private String iString;

    public String getiString() {
        return iString;
    }

    public void setiString(String iString) {
        this.iString = iString;
    }

    public boolean getDanger() {
        return danger;
    }

    public boolean getRepaired() {
        return repaired;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkMobile() {
        return linkMobile;
    }

    public void setLinkMobile(String linkMobile) {
        this.linkMobile = linkMobile;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
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

    public String getRectificationPlanTime() {
        return rectificationPlanTime;
    }

    public void setRectificationPlanTime(String rectificationPlanTime) {
        this.rectificationPlanTime = rectificationPlanTime;
    }

    public boolean isRepaired() {
        return repaired;
    }

    public void setRepaired(boolean repaired) {
        this.repaired = repaired;
    }

    public List<ImageInfo> getImagesInfoZg() {
        return imagesInfoZg;
    }

    public void setImagesInfoZg(List<ImageInfo> imagesInfoZg) {
        this.imagesInfoZg = imagesInfoZg;
    }

    public long getHzCallBackId() {
        return hzCallBackId;
    }

    public void setHzCallBackId(long hzCallBackId) {
        this.hzCallBackId = hzCallBackId;
    }
}
