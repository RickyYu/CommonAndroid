package com.safetys.zatgov.ui.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.safetys.zatgov.R;
import com.safetys.zatgov.entity.CityModel;
import com.safetys.zatgov.entity.DistrictModel;
import com.safetys.zatgov.entity.ProvinceModel;
import com.safetys.zatgov.service.XmlParserHandler;
import com.safetys.zatgov.ui.view.wheel.AbstractWheelTextAdapter;
import com.safetys.zatgov.ui.view.wheel.ArrayWheelAdapter;
import com.safetys.zatgov.ui.view.wheel.OnWheelChangedListener;
import com.safetys.zatgov.ui.view.wheel.OnWheelScrollListener;
import com.safetys.zatgov.ui.view.wheel.WheelView;

import org.xutils.common.util.LogUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Author:Created by Ricky on 2017/11/20.
 * Description:
 */
public class StreetSelectDialog extends android.app.Dialog implements View.OnClickListener, OnWheelChangedListener,OnWheelScrollListener {


    private WheelView mViewProvince;
    private WheelView mViewCity;
    protected WheelView mViewDistrict;
    private TextView mBtnConfirm;
    private TextView mBtnCancle;

    private AddressTextAdapter mAdapterCity;
    public AddressTextAdapter mAdapterDistrict;

    protected OnTextCListener mListener;
    protected TextView tv_title;

    private int maxsize = 24;
    private int minsize = 14;

    public StreetSelectDialog(Context context, OnTextCListener mListener) {
        super(context, R.style.ShareDialog);
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.street_select);
        setUpViews();
        setUpListener();
        setUpData("huzhounew_area.xml");
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (TextView) findViewById(R.id.btn_myinfo_sure);
        mBtnCancle = (TextView) findViewById(R.id.btn_myinfo_cancel);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        mBtnConfirm.setOnClickListener(this);
        mBtnCancle.setOnClickListener(this);

        mViewCity.addScrollingListener(this);
        mViewDistrict.addScrollingListener(this);

    }

    protected void setUpData(String xmlName) {
        initProvinceDatas(xmlName);
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(getContext(), mProvinceDatas));
        mViewProvince.setVisibleItems(5);
        mViewCity.setVisibleItems(5);
        mViewDistrict.setVisibleItems(5);
        updateCities();
        updateAreas();

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
//		if (wheel == mViewProvince) {
//			updateCities();
//		} else
        if (wheel == mViewCity) {
            updateAreas();

        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentDistrictCode = mDistrictCodeDatasMap.get(mCurrentDistrictName);

        }
        setTextviewSize(mViewCity.getCurrentItem(), mAdapterCity);
        setTextviewSize(mViewDistrict.getCurrentItem(), mAdapterDistrict);
    }

    @Override
    public void onScrollingStarted(WheelView wheel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScrollingFinished(WheelView wheel) {
        // TODO Auto-generated method stub
//		if (wheel == mViewCity) {
//			setTextviewSize(mViewCity.getCurrentItem(), mAdapterCity);
//		} else if (wheel == mViewDistrict) {
//			setTextviewSize(mViewDistrict.getCurrentItem(), mAdapterDistrict);
//		}
        setTextviewSize(mViewCity.getCurrentItem(), mAdapterCity);
        setTextviewSize(mViewDistrict.getCurrentItem(), mAdapterDistrict);
    }

    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        mAdapterDistrict = new AddressTextAdapter(getContext(), Arrays.asList(areas), 0, maxsize, minsize);
        mViewDistrict.setViewAdapter(mAdapterDistrict);
        mViewDistrict.setCurrentItem(0);
        setTextviewSize(mViewDistrict.getCurrentItem(), mAdapterDistrict);
    }

    private void updateCities() {
//		int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[0];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mAdapterCity = new AddressTextAdapter(getContext(), Arrays.asList(cities), 0, maxsize, minsize);
        mViewCity.setViewAdapter(mAdapterCity);
        mViewCity.setCurrentItem(0);
        updateAreas();
        setTextviewSize(mViewCity.getCurrentItem(), mAdapterCity);
    }

    /**
     * 设置字体大小
     *
     * @param adapter
     */
    public void setTextviewSize(int curriteItem, AddressTextAdapter adapter) {
        LogUtil.i(""+curriteItem);
        String currItemString = (String) adapter.getItemText(curriteItem);
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (currItemString.equals(currentText)) {
                textvew.setTextSize(24);
            } else {
                textvew.setTextSize(14);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_myinfo_sure:
                mCurrentCityCode = mCitisCodeDatasMap.get(mCurrentCityName);
                mCurrentDistrictName = mAdapterDistrict.getItemText(mViewDistrict.getCurrentItem()).toString();
                mCurrentDistrictCode = mDistrictCodeDatasMap.get(mCurrentDistrictName);
//			showSelectedResult();
                mListener.onClick(mCurrentProviceName+mCurrentCityName+mCurrentDistrictName,
                        mCurrentProviceCode,mCurrentCityCode,mCurrentDistrictCode);
                dismiss();
                break;
            case R.id.btn_myinfo_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }


    private class AddressTextAdapter extends AbstractWheelTextAdapter {
        List<String> list;

        protected AddressTextAdapter(Context context, List<String> list2, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_wheel_view, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list2;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }


    private void showSelectedResult() {
        Toast.makeText(getContext(), mCurrentProviceName+mCurrentProviceCode+","+mCurrentCityName+mCurrentCityCode+","
                +mCurrentDistrictName+mCurrentDistrictCode, Toast.LENGTH_SHORT).show();
    }


    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String> mDistrictCodeDatasMap = new HashMap<String, String>();
    protected Map<String, String> mCitisCodeDatasMap = new HashMap<String, String>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName ="";

    protected String mCurrentProviceCode ="";
    protected String mCurrentCityCode ="";
    protected String mCurrentDistrictCode ="";

    protected void initProvinceDatas(String xmlName)
    {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getContext().getAssets();
        try {
            InputStream input = asset.open(xmlName);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                mCurrentProviceCode = provinceList.get(0).getZipCode();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k=0; k<districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        mDistrictCodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    mCitisCodeDatasMap.put(cityNames[j], cityList.get(j).getZipCode());
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 回调接口
     *
     * @author Administrator
     *
     */
    public interface OnTextCListener {
        public void onClick(String mText,String pcode,String ccode,String dcode);
    }
}
