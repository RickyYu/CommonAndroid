package com.safetys.zatgov.bean;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class MathInfo {
    private Long id;
    /**
     * 检查企业数量
     */
    private String checkNum;

    /**
     * 复查数量
     */
    private String callbackNum;

    /**
     * 隐患总数
     */
    private String dangerNum;

    /**
     * 隐患整改率
     */
    private String rectifyRateNum;

    private List<MonthCounts> monthCounts;

    public MathInfo() {
    }

    public MathInfo(Long id, String checkNum, String callbackNum, String dangerNum, String rectifyRateNum, List<MonthCounts> monthCounts) {
        this.id = id;
        this.checkNum = checkNum;
        this.callbackNum = callbackNum;
        this.dangerNum = dangerNum;
        this.rectifyRateNum = rectifyRateNum;
        this.monthCounts = monthCounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public String getCallbackNum() {
        return callbackNum;
    }

    public void setCallbackNum(String callbackNum) {
        this.callbackNum = callbackNum;
    }

    public String getDangerNum() {
        return dangerNum;
    }

    public void setDangerNum(String dangerNum) {
        this.dangerNum = dangerNum;
    }

    public String getRectifyRateNum() {
        return rectifyRateNum;
    }

    public void setRectifyRateNum(String rectifyRateNum) {
        this.rectifyRateNum = rectifyRateNum;
    }

    public List<MonthCounts> getMonthCounts() {
        return monthCounts;
    }

    public void setMonthCounts(List<MonthCounts> monthCounts) {
        this.monthCounts = monthCounts;
    }
}
