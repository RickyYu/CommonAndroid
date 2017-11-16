package com.safetys.zatgov.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:监督检查页面添加隐患类
 */
public class WgyHiddenItemInfo implements Serializable {
    private int id = -1;
    private String des;//内容项，同content
    private File imageFile;//单张图片
    private String content;
    private int checkInfoId = -2;
    private String description;//是否通过
    private List<File> imageFiles;// 该隐患下多张照片
    private String yhlb;//隐患类别

    public WgyHiddenItemInfo(int id, String title, File imageFile) {
        super();
        this.id = id;
        this.des = title;
        this.imageFile = imageFile;
    }

    public WgyHiddenItemInfo() {
        super();
    }

    public WgyHiddenItemInfo(String content, String description) {
        super();
        this.content = content;
        this.description = description;
    }

    public WgyHiddenItemInfo( String title) {
        super();
        this.des = title;
    }



    public WgyHiddenItemInfo(String des, int checkInfoId) {
        super();
        this.des = des;
        this.checkInfoId = checkInfoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCheckInfoId() {
        return checkInfoId;
    }

    public void setCheckInfoId(int checkInfoId) {
        this.checkInfoId = checkInfoId;
    }

    public List<File> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(List<File> imageFiles) {
        this.imageFiles = imageFiles;
    }

    public String getYhlb() {
        return yhlb;
    }

    public void setYhlb(String yhlb) {
        this.yhlb = yhlb;
    }

}
