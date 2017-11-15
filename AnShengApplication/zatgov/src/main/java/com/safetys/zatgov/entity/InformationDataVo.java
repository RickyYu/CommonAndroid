package com.safetys.zatgov.entity;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class InformationDataVo {
    String name;
    int imgId;

    public InformationDataVo(String name, int imgId) {
        super();
        this.name = name;
        this.imgId = imgId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getImgId() {
        return imgId;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

}
