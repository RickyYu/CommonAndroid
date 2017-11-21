package com.safetys.zatgov.service;

import com.safetys.zatgov.entity.CityModel;
import com.safetys.zatgov.entity.DistrictModel;
import com.safetys.zatgov.entity.ProvinceModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/20.
 * Description:
 */
public class XmlParserHandler extends DefaultHandler {

        private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();

	    public XmlParserHandler() {

        }

        public List<ProvinceModel> getDataList() {
            return provinceList;
        }

        @Override
        public void startDocument() throws SAXException {
        }

        ProvinceModel provinceModel = new ProvinceModel();
        CityModel cityModel = new CityModel();
        DistrictModel districtModel = new DistrictModel();

        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {
            if (qName.equals("first-area")) {
                provinceModel = new ProvinceModel();
                provinceModel.setName(attributes.getValue(0));
                provinceModel.setZipCode(attributes.getValue(1));
                provinceModel.setCityList(new ArrayList<CityModel>());
            } else if (qName.equals("second-area")) {
                cityModel = new CityModel();
                cityModel.setName(attributes.getValue(0));
                cityModel.setZipCode(attributes.getValue(1));
                cityModel.setDistrictList(new ArrayList<DistrictModel>());
            } else if (qName.equals("third-area")) {
                districtModel = new DistrictModel();
                districtModel.setName(attributes.getValue(0));
                districtModel.setZipcode(attributes.getValue(1));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
			throws SAXException {
            if (qName.equals("third-area")) {
                cityModel.getDistrictList().add(districtModel);
            } else if (qName.equals("second-area")) {
                provinceModel.getCityList().add(cityModel);
            } else if (qName.equals("first-area")) {
                provinceList.add(provinceModel);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
			throws SAXException {
        }

    }
