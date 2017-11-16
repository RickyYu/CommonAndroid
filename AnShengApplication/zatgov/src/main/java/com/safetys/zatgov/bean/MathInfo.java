package com.safetys.zatgov.bean;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class MathInfo {
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

    /**
     *
     */
    private List<MonthCounts> monthCounts;

    public List<MonthCounts> getMonthCounts() {
        return monthCounts;
    }

    public void setMonthCounts(List<MonthCounts> monthCounts) {
        this.monthCounts = monthCounts;
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
}
