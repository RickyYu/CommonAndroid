package com.safetys.zatgov.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class HiddenDesInfoVo implements Serializable {
    String hiddenId;//隐患ID
    String typeCN;
    String createTime;
    Boolean repaired;
    String content;
    int checkInfoId = -1;
    private String isBig;
    int isRead;
    String govCheck;//企业是否已整改
    boolean needPunish;//是否需要处罚
    boolean isReview = false;//false是复查  true是修改隐患
    private List<ImageInfo> hzUploadFile;//获取到的隐患图片

    public HiddenDesInfoVo() {
        super();
    }

    public HiddenDesInfoVo(String hiddenId, String typeCN, String createTime,
                           Boolean repaired, String content, int checkInfoId, String isBig) {
        super();
        this.hiddenId = hiddenId;
        this.typeCN = typeCN;
        this.createTime = createTime;
        this.repaired = repaired;
        this.content = content;
        this.checkInfoId = checkInfoId;
        this.isBig = isBig;
    }

    public HiddenDesInfoVo(Boolean repaired, String content,String createTime) {
        super();
        this.repaired = repaired;
        this.content = content;
        this.createTime = createTime;
    }

    public int getCheckInfoId() {
        return checkInfoId;
    }
    public void setCheckInfoId(int checkInfoId) {
        this.checkInfoId = checkInfoId;
    }

    public String getIsBig() {
        return isBig;
    }
    public void setIsBig(String isBig) {
        this.isBig = isBig;
    }
    public String getHiddenId() {
        return hiddenId;
    }
    public void setHiddenId(String hiddenId) {
        this.hiddenId = hiddenId;
    }
    public String getTypeCN() {
        return typeCN;
    }
    public void setTypeCN(String typeCN) {
        this.typeCN = typeCN;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Boolean getRepaired() {
        return repaired;
    }
    public void setRepaired(Boolean repaired) {
        this.repaired = repaired;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getGovCheck() {
        return govCheck;
    }

    public void setGovCheck(String govCheck) {
        this.govCheck = govCheck;
    }

    public boolean isNeedPunish() {
        return needPunish;
    }

    public void setNeedPunish(boolean needPunish) {
        this.needPunish = needPunish;
    }

    public boolean isReview() {
        return isReview;
    }

    public void setReview(boolean isReview) {
        this.isReview = isReview;
    }

    public List<ImageInfo> getHzUploadFile() {
        return hzUploadFile;
    }

    public void setHzUploadFile(List<ImageInfo> hzUploadFile) {
        this.hzUploadFile = hzUploadFile;
    }


}

