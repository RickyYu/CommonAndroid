package com.safetys.zatgov.bean;

import java.io.Serializable;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:隐患图片类
 */
public class ImageInfo implements Serializable {

    private Long userId;
    private String fileFileName;
    private String path;
    private String id;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getFileFileName() {
        return fileFileName;
    }
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}

