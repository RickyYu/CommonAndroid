package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class MonthCounts {
    /**
     * dateMonth(月份)
     */
    private String dateMonth ;
    /**
     * 自查数
     */
    private Float  byCom
            ;
    /**复查数
     *
     */
    private Float  byGov;
    /**
     * v整改数
     */
    private Float  repairedNum;
    public String getDateMonth() {
        return dateMonth;
    }
    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }
    public Float getByCom() {
        return byCom;
    }
    public void setByCom(Float byCom) {
        this.byCom = byCom;
    }
    public Float getByGov() {
        return byGov;
    }
    public void setByGov(Float byGov) {
        this.byGov = byGov;
    }
    public Float getRepairedNum() {
        return repairedNum;
    }
    public void setRepairedNum(Float repairedNum) {
        this.repairedNum = repairedNum;
    }
}
