package com.safetys.zatgov.entity;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description: 版本信息
 */
public class VerInfo {
    /**
     * 版本号
     */
    private String versionCode;
    /**
     * 正式路径
     */
    private String fileRealPath;
    public String getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
    public String getFileRealPath() {
        return fileRealPath;
    }
    public void setFileRealPath(String fileRealPath) {
        this.fileRealPath = fileRealPath;
    }
}
