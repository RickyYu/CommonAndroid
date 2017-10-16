package com.safetys.nbsxs.service;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.safetys.nbsxs.bean.CityModel;
import cn.safetys.nbsxs.bean.DistrictModel;
import cn.safetys.nbsxs.bean.ProvinceModel;


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
		if (qName.equals("second-area")) {
			provinceModel = new ProvinceModel();
			provinceModel.setName(attributes.getValue(0));
			provinceModel.setZipCode(attributes.getValue(1));
			provinceModel.setCityList(new ArrayList<CityModel>());
		} else if (qName.equals("third-area")) {
			cityModel = new CityModel();
			cityModel.setName(attributes.getValue(0));
			cityModel.setZipCode(attributes.getValue(1));
			cityModel.setDistrictList(new ArrayList<DistrictModel>());
		} else if (qName.equals("fouth-area")) {
			districtModel = new DistrictModel();
			districtModel.setName(attributes.getValue(0));
			districtModel.setZipcode(attributes.getValue(1));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("fouth-area")) {
			cityModel.getDistrictList().add(districtModel);
        } else if (qName.equals("third-area")) {
        	provinceModel.getCityList().add(cityModel);
        } else if (qName.equals("second-area")) {
        	provinceList.add(provinceModel);
        }
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

}
