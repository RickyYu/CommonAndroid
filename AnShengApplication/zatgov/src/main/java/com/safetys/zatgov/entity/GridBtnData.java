package com.safetys.zatgov.entity;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class GridBtnData {
    public int resourceId;
    public String btnString;
    public int unReadNum;

    public GridBtnData(int resourceId, String btnString, int unReadNum) {
        this.resourceId = resourceId;
        this.btnString = btnString;
        this.unReadNum = unReadNum;
    }

}
