package com.safetys.zatgov.entity;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/20.
 * Description:
 */
public class CityModel {
    private String name;
    private String zipCode;
    private List<DistrictModel> districtList;

    public CityModel() {
        super();
    }

    public CityModel(String name, List<DistrictModel> districtList) {
        super();
        this.name = name;
        this.districtList = districtList;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictModel> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictModel> districtList) {
        this.districtList = districtList;
    }

    @Override
    public String toString() {
        return "CityModel [name=" + name + ", districtList=" + districtList
                + "]";
    }
}
