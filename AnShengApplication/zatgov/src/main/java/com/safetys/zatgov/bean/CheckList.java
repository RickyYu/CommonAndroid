package com.safetys.zatgov.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:检查列表信息
 */
public class CheckList implements Serializable {

    /**
     * 单位名称
     */
    private String companyname;
    /**
     * 公司dizhi
     */
    private String companyadress;
    /**
     * 联系人
     */
    private String companylxr;
    /**
     * 选择条目内容
     */
    private String content;

    /**
     * 公司id
     */
    private String companyId;
    /**
     * checkid行业检查表id
     */
    private String checkId = "-1";
    /**
     * 隐患部位
     */
    private String place;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 检查人记录人
     */
    private String people;

    /**
     * 执法单位
     */
    private String law;
    /**
     * 责令整改时间
     */
    private String zgtime;
    /**
     * 检查时间
     */
    private String checktime;
    /**
     * 发送整改通知书
     */
    private boolean check;
    /**
     * 参与检查人
     */
    private ArrayList<Long> listCb;
    /**
     * 检查事项
     */
    private ArrayList<Long> listCheck;
    /**
     * 检查事项全部
     */
    private ArrayList<Long> listAll;

    private ArrayList<HyCheckItemInfo> listHy;

    // 照片
    private List<File> listfile;

    private LinkedHashMap<Long, String> hashAll;

    private String nowcontent;
    private String lxr;// 联系人

    /**
     * 参与检查人类
     */
    private List<UserIds> userIds;

    public CheckList() {
        super();
    }
    public CheckList(String companyname, String companyadress,
                     String companylxr, String content, String companyId,
                     String checkId, String place, String phone, String people,
                     String law, String zgtime, String checktime, boolean check,
                     ArrayList<Long> listCb, ArrayList<Long> listCheck,
                     ArrayList<Long> listAll, ArrayList<HyCheckItemInfo> listHy,
                     List<File> listfile, LinkedHashMap<Long, String> hashAll,
                     String nowcontent, String lxr, List<UserIds> userIds) {
        super();
        this.companyname = companyname;
        this.companyadress = companyadress;
        this.companylxr = companylxr;
        this.content = content;
        this.companyId = companyId;
        this.checkId = checkId;
        this.place = place;
        this.phone = phone;
        this.people = people;
        this.law = law;
        this.zgtime = zgtime;
        this.checktime = checktime;
        this.check = check;
        this.listCb = listCb;
        this.listCheck = listCheck;
        this.listAll = listAll;
        this.listHy = listHy;
        this.listfile = listfile;
        this.hashAll = hashAll;
        this.nowcontent = nowcontent;
        this.lxr = lxr;
        this.userIds = userIds;
    }









    public List<UserIds> getUserIds() {
        return userIds;
    }









    public void setUserIds(List<UserIds> userIds) {
        this.userIds = userIds;
    }









    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyadress() {
        return companyadress;
    }

    public void setCompanyadress(String companyadress) {
        this.companyadress = companyadress;
    }

    public String getCompanylxr() {
        return companylxr;
    }

    public void setCompanylxr(String companylxr) {
        this.companylxr = companylxr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getNowcontent() {
        return nowcontent;
    }

    public void setNowcontent(String nowcontent) {
        this.nowcontent = nowcontent;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getLaw() {
        return law;
    }

    public void setLaw(String law) {
        this.law = law;
    }

    public String getZgtime() {
        return zgtime;
    }

    public void setZgtime(String zgtime) {
        this.zgtime = zgtime;
    }

    public String getChecktime() {
        return checktime;
    }

    public void setChecktime(String checktime) {
        this.checktime = checktime;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public ArrayList<Long> getListCb() {
        return listCb;
    }

    public void setListCb(ArrayList<Long> listCb) {
        this.listCb = listCb;
    }

    public ArrayList<Long> getListCheck() {
        return listCheck;
    }

    public void setListCheck(ArrayList<Long> listCheck) {
        this.listCheck = listCheck;
    }

    public ArrayList<Long> getListAll() {
        return listAll;
    }

    public void setListAll(ArrayList<Long> listAll) {
        this.listAll = listAll;
    }

    public ArrayList<HyCheckItemInfo> getListHy() {
        return listHy;
    }

    public void setListHy(ArrayList<HyCheckItemInfo> listHy) {
        this.listHy = listHy;
    }

    public List<File> getListfile() {
        return listfile;
    }

    public void setListfile(List<File> listfile) {
        this.listfile = listfile;
    }

    public LinkedHashMap<Long, String> getHashAll() {
        return hashAll;
    }

    public void setHashAll(LinkedHashMap<Long, String> hashAll) {
        this.hashAll = hashAll;
    }


}
