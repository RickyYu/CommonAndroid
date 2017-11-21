package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */

public class MonthCounts {

    private Long id;

    private Long countId;
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


    public MonthCounts(Long id, Long countId, String dateMonth, Float byCom,
            Float byGov, Float repairedNum) {
        this.id = id;
        this.countId = countId;
        this.dateMonth = dateMonth;
        this.byCom = byCom;
        this.byGov = byGov;
        this.repairedNum = repairedNum;
    }


    public MonthCounts() {
    }

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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCountId() {
        return this.countId;
    }

    public void setCountId(Long countId) {
        this.countId = countId;
    }
}
