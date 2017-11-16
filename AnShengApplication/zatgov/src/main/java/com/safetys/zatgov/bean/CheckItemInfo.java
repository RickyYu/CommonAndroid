package com.safetys.zatgov.bean;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class CheckItemInfo {
    private String produceLocaleNoteId;//检查表id
    private String companyId;//企业id
    private String companyName;//企业名字
    private String checkTimeBegin;//创建时间

    /**
     * 单位地址
     */
    private String  address;
    /**
     * 检查场所
     */
    private String checkGround ;
    /**
     * 联系人
     */
    private String fdDelegate ;
    /**
     * 联系方式
     */
    private String  fdDelegateLink;
    /**
     * 检查人/记录人
     */
    private String  noter;
    /**
     * 执法单位
     */
    private String  executeUnit;
    /**
     * 现场检查记录
     */
    private String  content;
    /**
     * 发送整改通知书
     */
    private boolean  sendCleanUp;

    /**
     * 附件名称
     */
    private String  fileName;
    /**
     * 附件地址
     */
    private String  fileUrl;
    /**
     * 责令整改日期
     */
    private String  cleanUpTimeLimit;

    /**
     * fileIdfileId(图片删除id)
     */
    private String fileId;

    private List<ImageInfo> imagesInfo;

    /**
     * 行业检查表
     */
    private String  checkTable;


    private List<ImageInfo> imagesInfoZg;// 整改图片
    private long hzCallBackId;// 复查ID
    private String remarks;//整改描述


    public String getCheckTable() {
        return checkTable;
    }
    public void setCheckTable(String checkTable) {
        this.checkTable = checkTable;
    }
    public String getCleanUpTimeLimit() {
        return cleanUpTimeLimit;
    }
    public void setCleanUpTimeLimit(String cleanUpTimeLimit) {
        this.cleanUpTimeLimit = cleanUpTimeLimit;
    }
    public List<ImageInfo> getImagesInfo() {
        return imagesInfo;
    }
    public void setImagesInfo(List<ImageInfo> imagesInfo) {
        this.imagesInfo = imagesInfo;
    }
    public String getFileId() {
        return fileId;
    }
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileUrl() {
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public String getProduceLocaleNoteId() {
        return produceLocaleNoteId;
    }
    public void setProduceLocaleNoteId(String produceLocaleNoteId) {
        this.produceLocaleNoteId = produceLocaleNoteId;
    }
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCheckGround() {
        return checkGround;
    }
    public void setCheckGround(String checkGround) {
        this.checkGround = checkGround;
    }
    public String getFdDelegate() {
        return fdDelegate;
    }
    public void setFdDelegate(String fdDelegate) {
        this.fdDelegate = fdDelegate;
    }
    public String getFdDelegateLink() {
        return fdDelegateLink;
    }
    public void setFdDelegateLink(String fdDelegateLink) {
        this.fdDelegateLink = fdDelegateLink;
    }
    public String getNoter() {
        return noter;
    }
    public void setNoter(String noter) {
        this.noter = noter;
    }
    public String getExecuteUnit() {
        return executeUnit;
    }
    public void setExecuteUnit(String executeUnit) {
        this.executeUnit = executeUnit;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public boolean getSendCleanUp() {
        return sendCleanUp;
    }
    public void setSendCleanUp(boolean sendCleanUp) {
        this.sendCleanUp = sendCleanUp;
    }
    public String getCheckTimeBegin() {
        return checkTimeBegin;
    }
    public void setCheckTimeBegin(String checkTimeBegin) {
        this.checkTimeBegin = checkTimeBegin;
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
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
