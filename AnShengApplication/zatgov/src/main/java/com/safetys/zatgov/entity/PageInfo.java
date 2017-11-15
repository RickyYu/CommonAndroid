package com.safetys.zatgov.entity;

import java.io.Serializable;

/**
 * Author:Created by Ricky on 2017/11/13.
 * Description:
 */
public class PageInfo implements Serializable {

    private int totalcount;//总共多少条
    private int pagesize;//页大小
    private int itemcount;//第几条开始

    public int getTotalcount() {
        return totalcount;
    }
    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }
    public int getPagesize() {
        return pagesize;
    }
    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
    public int getItemcount() {
        return itemcount;
    }
    public void setItemcount(int itemcount) {
        this.itemcount = itemcount;
    }

}
