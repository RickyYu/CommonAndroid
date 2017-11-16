package com.safetys.zatgov.bean;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class CpyGelImageInfoVo {

    private List<ImageInfo> imagesInfo;//隐患照片
    private List<ImageInfo> imagesInfozg;//整改照片

    public List<ImageInfo> getImagesInfo() {
        return imagesInfo;
    }
    public void setImagesInfo(List<ImageInfo> imagesInfo) {
        this.imagesInfo = imagesInfo;
    }
    public List<ImageInfo> getImagesInfozg() {
        return imagesInfozg;
    }
    public void setImagesInfozg(List<ImageInfo> imagesInfozg) {
        this.imagesInfozg = imagesInfozg;
    }
}
