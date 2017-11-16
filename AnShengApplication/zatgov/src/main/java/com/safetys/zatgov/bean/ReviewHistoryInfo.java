package com.safetys.zatgov.bean;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class ReviewHistoryInfo {
    String callbackTime; // 复查时间
    String executer;// 复查人员
    String hiddenState;// 复查情况

    public String getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(String callbackTime) {
        this.callbackTime = callbackTime;
    }

    public String getExecuter() {
        return executer;
    }

    public void setExecuter(String executer) {
        this.executer = executer;
    }

    public String getHiddenState() {
        return hiddenState;
    }

    public void setHiddenState(String hiddenState) {
        this.hiddenState = hiddenState;
    }
}
