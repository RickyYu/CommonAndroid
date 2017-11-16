package com.safetys.zatgov.bean;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:重大隐患详细信息
 */
public class MajorItemInfo {
    /**
     * 企业id
     */
    private String id;
    /**
     * 隐患编号
     */
    private String dangerNo;
    /**
     * 市级以上重点工程
     */
    private Boolean emphasisProject;
    /**
     * 隐患地址
     */
    private String dangerAdd;

    /**
     * 隐患基本情况
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
     * 单位负责人
     */
    private String chargePerson;
    /**
     * 录入时间
     */
    private String fillDate;
    /**
     * 计划完成治理时间
     */
    private String finishDate;

    /**
     * 填报人
     */
    private String fillMan;

    /**
     * 市
     */
    private String firstArea;
    /**
     * 县
     */
    private String secondArea;
    /**
     * 街道
     */
    private String thirdArea;
    /**
     * 是否需要政府协调
     */
    private Boolean govCoordination;
    /**
     * 是否需局部停产停业
     *
     */
    private Boolean partStopProduct;
    /**
     * 是否需要全部停产停业
     */
    private Boolean fullStopProduct;
    /**
     * 落实治理目标
     */
    private Boolean target;
    /**
     * 落实治理机构人员
     */
    private Boolean resource;
    /**
     * 落实治理经费物资
     */
    private Boolean safetyMethod;
    /**
     * 落实安全促使及应急预案
     */
    private Boolean goods;
    /**
     * 落实治理经费
     */
    private String governMoney;

    /**
     * 照片地址
     */
    private String fileRealPath;

    private Long checkInfoId;

    private List<ImageInfo> imagesInfo;

    /*	private List<ImageInfo> imagesInfoZg;// 整改图片
        private long hzCallBackId;// 复查ID
        private String remarks;//整改描述
    */
    public Long getCheckInfoId() {
        return checkInfoId;
    }

    public void setCheckInfoId(Long checkInfoId) {
        this.checkInfoId = checkInfoId;
    }

    public List<ImageInfo> getImagesInfo() {
        return imagesInfo;
    }

    public void setImagesInfo(List<ImageInfo> imagesInfo) {
        this.imagesInfo = imagesInfo;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Boolean getGovCoordination() {
        return govCoordination;
    }

    public void setGovCoordination(Boolean govCoordination) {
        this.govCoordination = govCoordination;
    }

    public Boolean getPartStopProduct() {
        return partStopProduct;
    }

    public void setPartStopProduct(Boolean partStopProduct) {
        this.partStopProduct = partStopProduct;
    }

    public Boolean getFullStopProduct() {
        return fullStopProduct;
    }

    public void setFullStopProduct(Boolean fullStopProduct) {
        this.fullStopProduct = fullStopProduct;
    }

    public Boolean getTarget() {
        return target;
    }

    public void setTarget(Boolean target) {
        this.target = target;
    }

    public Boolean getResource() {
        return resource;
    }

    public void setResource(Boolean resource) {
        this.resource = resource;
    }

    public Boolean getSafetyMethod() {
        return safetyMethod;
    }

    public void setSafetyMethod(Boolean safetyMethod) {
        this.safetyMethod = safetyMethod;
    }

    public Boolean getGoods() {
        return goods;
    }

    public void setGoods(Boolean goods) {
        this.goods = goods;
    }

    public String getGovernMoney() {
        return governMoney;
    }

    public void setGovernMoney(String governMoney) {
        this.governMoney = governMoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDangerNo() {
        return dangerNo;
    }

    public void setDangerNo(String dangerNo) {
        this.dangerNo = dangerNo;
    }

    public boolean isEmphasisProject() {
        return emphasisProject;
    }

    public void setEmphasisProject(Boolean emphasisProject) {
        this.emphasisProject = emphasisProject;
    }

    public String getDangerAdd() {
        return dangerAdd;
    }

    public void setDangerAdd(String dangerAdd) {
        this.dangerAdd = dangerAdd;
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

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getFillDate() {
        return fillDate;
    }

    public void setFillDate(String fillDate) {
        this.fillDate = fillDate;
    }

    public String getFillMan() {
        return fillMan;
    }

    public void setFillMan(String fillMan) {
        this.fillMan = fillMan;
    }

    public String getFirstArea() {
        return firstArea;
    }

    public void setFirstArea(String firstArea) {
        this.firstArea = firstArea;
    }

    public String getSecondArea() {
        return secondArea;
    }

    public void setSecondArea(String secondArea) {
        this.secondArea = secondArea;
    }

    public String getThirdArea() {
        return thirdArea;
    }

    public void setThirdArea(String thirdArea) {
        this.thirdArea = thirdArea;
    }

    public String getFileRealPath() {
        return fileRealPath;
    }

    public void setFileRealPath(String fileRealPath) {
        this.fileRealPath = fileRealPath;
    }



    public Boolean getEmphasisProject() {
        return emphasisProject;
    }

}
