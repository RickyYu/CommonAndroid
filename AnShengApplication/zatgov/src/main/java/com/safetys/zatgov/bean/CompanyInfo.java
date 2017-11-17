package com.safetys.zatgov.bean;

import android.app.Application;
import android.text.TextUtils;

import com.safetys.zatgov.ZatApplication;
import com.safetys.zatgov.entity.TradeType;

import org.xutils.ex.DbException;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class CompanyInfo{
        /**
         * 企业id
         */
        private int id;
        /**
         * 所属行业
         */
        private String tradeTypes;
        /**
         * 行业小类
         */
        private String tradeMid;
        /**
         * 行业中类
         */
        private String tradeBig;
        /**
         * 行业大类
         */
        private String tradeType;
        /**
         * 电话
         */
        private String phone;
        /**
         * 负责人名字
         */
        private String fdDelegate;
        /**
         * 市
         */
        private String firstArea;
        /**
         * 县
         */
        private String secondArea;
        /**
         * 街道
         */
        private String thirdArea;
        /**
         * 地址
         */
        private String address;
        /**
         * 企业名称
         */
        private String companyName;

        /**
         * 工商注册号
         */
        private String businessRegNumber;
        /**
         * 经济类型
         */
        private String economyKind;
        /**
         * 企业规模
         */
        private String isEnterprise;
        /**
         * 生产
         */
        private String isProduction;
        /**
         * 组织机构代码
         */
        private String organCode;
        /*
         * 经纬度
         */
        private Float x;
        private Float y;
        /*
         * 未检查企业数量
         */
        private String unCheckNum;
        /*
         * 未复查企业数量
         */
        private String unCallbackNum;
        /*
         * 企业未整改隐患数
         */
        private String unCleanDangerNum;
        /*
         * 到上报隐患企业数
         */
        private String unReportNum;
        /*
         * 隐患最多企业
         */
        private List<CompanyVo> companys;



        public String getUnCheckNum() {
            return unCheckNum;
        }

        public void setUnCheckNum(String unCheckNum) {
            this.unCheckNum = unCheckNum;
        }

        public String getUnCallbackNum() {
            return unCallbackNum;
        }

        public void setUnCallbackNum(String unCallbackNum) {
            this.unCallbackNum = unCallbackNum;
        }

        public String getUnCleanDangerNum() {
            return unCleanDangerNum;
        }

        public void setUnCleanDangerNum(String unCleanDangerNum) {
            this.unCleanDangerNum = unCleanDangerNum;
        }

        public String getUnReportNum() {
            return unReportNum;
        }

        public void setUnReportNum(String unReportNum) {
            this.unReportNum = unReportNum;
        }

        public List<CompanyVo> getCompanys() {
            return companys;
        }

        public void setCompanys(List<CompanyVo> companys) {
            this.companys = companys;
        }

        public Float getX() {
            return x;
        }

        public void setX(Float x) {
            this.x = x;
        }

        public Float getY() {
            return y;
        }

        public void setY(Float y) {
            this.y = y;
        }

        public String getIsProduction() {
            return isProduction;
        }

        public void setIsProduction(String isProduction) {
            this.isProduction = isProduction;
        }

        public String getOrganCode() {
            return organCode;
        }

        public void setOrganCode(String organCode) {
            this.organCode = organCode;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTradeTypes() {
            return tradeTypes;
        }

        public void setTradeTypes(String tradeTypes) {
            this.tradeTypes = tradeTypes;
        }

        public String getTradeMid() {
            return tradeMid;
        }

        public void setTradeMid(String tradeMid) {
            this.tradeMid = tradeMid;
        }

        public String getTradeBig() {
            return tradeBig;
        }

        public void setTradeBig(String tradeBig) {
            this.tradeBig = tradeBig;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFdDelegate() {
            return fdDelegate;
        }

        public void setFdDelegate(String fdDelegate) {
            this.fdDelegate = fdDelegate;
        }

        public String getFirstArea() {
            return firstArea;
        }

        public void setFirstArea(String firstArea) {
            this.firstArea = firstArea;
        }

        public String getSecondArea() {
            return secondArea;
        }

        public void setSecondArea(String secondArea) {
            this.secondArea = secondArea;
        }

        public String getThirdArea() {
            return thirdArea;
        }

        public void setThirdArea(String thirdArea) {
            this.thirdArea = thirdArea;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getBusinessRegNumber() {
            return businessRegNumber;
        }

        public void setBusinessRegNumber(String businessRegNumber) {
            this.businessRegNumber = businessRegNumber;
        }

        public String getEconomyKind() {
            return economyKind;
        }

        public void setEconomyKind(String economyKind) {
            this.economyKind = economyKind;
        }

        public String getIsEnterprise() {
            return isEnterprise;
        }

        public void setIsEnterprise(String isEnterprise) {
            this.isEnterprise = isEnterprise;
        }

        /**
         * 用行业编码查询行业名称
         * @param App
         * @param typeCode 行业编码
         * @return
         */
        public String getTradeString(ZatApplication App, String typeCode){
            if(typeCode == null || TextUtils.isEmpty(typeCode)){
                return "";
            }
            try {
                TradeType mTradeType = App.getDbManager().selector(TradeType.class).where("typeCode", "=", typeCode).findFirst();
                if(mTradeType!=null){
                    return mTradeType.getTypeName();
                }
            } catch (DbException e) {

                e.printStackTrace();
            }
            return "";
        }

        /**
         * 用行业名称查询行业名称编码
         * @param App
         * @param typeCode 行业编码
         * @return
         */
        public String getTradeCode(ZatApplication App,String typeName){
            if(typeName == null || TextUtils.isEmpty(typeName)){
                return "";
            }
            try {
                TradeType mTradeType = App.getDbManager().selector(TradeType.class).where("typeName", "=", typeName).findFirst();
                if(mTradeType!=null){
                    return mTradeType.getTypeCode();
                }
            } catch (DbException e) {

                e.printStackTrace();
            }
            return "";
        }

}

