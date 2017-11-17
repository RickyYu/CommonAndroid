package com.safetys.zatgov.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CompanyInfo;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import java.util.List;

public class LocationActivity extends BaseActivity implements
		BDLocationListener, OnGetGeoCoderResultListener,
		BaiduMap.OnMapStatusChangeListener, TextWatcher, OnClickListener,
		onNetCallback {

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	/**
	 * 定位模式
	 */
	private MyLocationConfiguration.LocationMode mCurrentMode;
	/**
	 * 定位端
	 */
	private LocationClient mLocClient;
	/**
	 * 是否是第一次定位
	 */
	private boolean isFirstLoc = true;
	private boolean isPoisLL = false;
	/**
	 * 定位坐标
	 */
	private LatLng locationLatLng;
	/**
	 * 定位城市
	 */
	private String city;
	/**
	 * 反地理编码
	 */
	private GeoCoder geoCoder;
	/**
	 * 界面上方布局
	 */
	private RelativeLayout topRL;
	/**
	 * 搜索地址输入框
	 */
	private EditText searchAddress;
	/**
	 * 搜索输入框对应的ListView
	 */
	private ListView searchPois;
	private View loca;
	private View save;
	private String id;
	private LoadingDialogUtil mLoading;
	private BDLocation bdLocation2;
	private Float x = 0f;
	private Float y = 0f;
	private LatLng ll;
	private View change;
	private ImageView iv_change;
	private Double loactionY;
	private Double loactionX;
	private Boolean isChange = false;
	private Boolean isShow = false;
	BitmapDescriptor mCurrentMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.location_main);
		initView();
	}

	private void initView() {
		setHeadTitle("企业定位");
		View mBtn_back = findViewById(R.id.btn_back);
		mBtn_back.setOnClickListener(this);
		mLoading = new LoadingDialogUtil(this, "请稍后...");
		id = getIntent().getExtras().getString("id");

		isFirstLoc = true;
		mLoading.show();
		HttpRequestHelper.getInstance().getLocationInfo(this, id, 0, this);

		mMapView = (MapView) findViewById(R.id.main_bdmap);
		mBaiduMap = mMapView.getMap();


		topRL = (RelativeLayout) findViewById(R.id.main_top_RL);

		searchAddress = (EditText) findViewById(R.id.main_search_address);

		searchPois = (ListView) findViewById(R.id.main_search_pois);
		change = findViewById(R.id.change);
		change.setOnClickListener(this);
		loca = findViewById(R.id.loca);
		loca.setOnClickListener(this);
		save = findViewById(R.id.save);
		save.setOnClickListener(this);
		iv_change = (ImageView) findViewById(R.id.ivLoc);
		// 定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder().zoom(18).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);

		// 地图状态改变相关监听
		mBaiduMap.setOnMapStatusChangeListener(this);

		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位图层显示方式
		mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

		/**
		 * 设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效 customMarker用户自定义定位图标
		 * enableDirection是否允许显示方向信息 locationMode定位图层显示方式
		 */
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				mCurrentMode, true, null));

		loaction();

	}

	private void loaction() {
		// 定位选项
		// 初始化定位
		mLocClient = new LocationClient(this);
		// 注册定位监听
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		/**
		 * coorType - 取值有3个： 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系
		 * ：bd09ll
		 */
		option.setCoorType("bd09ll");
		// 设置是否需要地址信息，默认为无地址
		option.setIsNeedAddress(true);
		// 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
		// 可以用作地址信息的补充
		option.setIsNeedLocationDescribe(true);
		// 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
		option.setIsNeedLocationPoiList(true);
		/**
		 * 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps)模式 Hight_Accuracy
		 * 高精度模式
		 */
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		// 设置是否打开gps进行定位
		option.setOpenGps(true);
		// 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
		// option.setScanSpan(1000);

		// 设置 LocationClientOption
		mLocClient.setLocOption(option);
		// 开始定位
		mLocClient.start();
	}

	private static final int accuracyCircleFillColor = 0xAAFFFF88;
	private static final int accuracyCircleStrokeColor = 0xAA00FF00;
	private MyLocationData locData;
	private Boolean isNow=false;

	/**
	 * 定位监听
	 *
	 * @param bdLocation
	 */
	@Override
	public void onReceiveLocation(BDLocation bdLocation) {

		if (isNow) {
			ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//			Log.e("asd", "当前位置=y=" + bdLocation.getLatitude() + ","
//					+ bdLocation.getLongitude());
			locData = new MyLocationData.Builder()
					.latitude(bdLocation.getLatitude())
					.longitude(bdLocation.getLongitude()).build();
			MapStatus.Builder builder = new MapStatus.Builder();
			builder.target(ll).zoom(18.0f);
			mBaiduMap.animateMapStatus(MapStatusUpdateFactory
					.newMapStatus(builder.build()));
			mBaiduMap.setMyLocationData(locData);
			mCurrentMarker = BitmapDescriptorFactory
					.fromResource(R.mipmap.baidumap_ico_poi_on);
			mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
					mCurrentMode, true, mCurrentMarker,
					accuracyCircleFillColor, accuracyCircleStrokeColor));
		}

		if (isFirstLoc) {
			isFirstLoc = false;
			if (x == 0.0) {
				ll = new LatLng(bdLocation.getLatitude(),
						bdLocation.getLongitude());
				locData = new MyLocationData.Builder()
						.latitude(bdLocation.getLatitude())
						.longitude(bdLocation.getLongitude()).build();
			} else {
				ll = new LatLng(y, x);
				locData = new MyLocationData.Builder().latitude(y).longitude(x)
						.build();
			}
			MapStatus.Builder builder = new MapStatus.Builder();
			builder.target(ll).zoom(18.0f);
			mBaiduMap.animateMapStatus(MapStatusUpdateFactory
					.newMapStatus(builder.build()));
			mBaiduMap.setMyLocationData(locData);
			mCurrentMarker = BitmapDescriptorFactory
					.fromResource(R.mipmap.baidumap_ico_poi_on);
			mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
					mCurrentMode, true, mCurrentMarker,
					accuracyCircleFillColor, accuracyCircleStrokeColor));
		}
		
		if (isChange&&!isNow) {
			mBaiduMap.clear();
			ll = new LatLng(loactionY, loactionX);
			MapStatus.Builder builder = new MapStatus.Builder();
			builder.target(ll).zoom(18.0f);
			mBaiduMap.animateMapStatus(MapStatusUpdateFactory
					.newMapStatus(builder.build()));
			MyLocationData locData = new MyLocationData.Builder()
					.latitude(loactionY).longitude(loactionX).build();
			mBaiduMap.setMyLocationData(locData);
			mCurrentMarker = BitmapDescriptorFactory
					.fromResource(R.mipmap.baidumap_ico_poi_on);
			mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
					mCurrentMode, true, mCurrentMarker,
					accuracyCircleFillColor, accuracyCircleStrokeColor));
		}


		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);
		mBaiduMap.animateMapStatus(msu);

		// 获取坐标，待会用于POI信息点与定位的距离
		locationLatLng = new LatLng(bdLocation.getLatitude(),
				bdLocation.getLongitude());
		// 获取城市，待会用于POISearch
		city = bdLocation.getCity();

		// 文本输入框改变监听，必须在定位完成之后
		// searchAddress.addTextChangedListener(this);

		// 创建GeoCoder实例对象
		geoCoder = GeoCoder.newInstance();
		// 发起反地理编码请求(经纬度->地址信息)
		ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
		if (x == 0.0) {
			// 设置反地理编码位置坐标
			reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(),
					bdLocation.getLongitude()));
		} else {
			// 设置反地理编码位置坐标
			reverseGeoCodeOption.location(new LatLng(y, x));

		}

		geoCoder.reverseGeoCode(reverseGeoCodeOption);

		// 设置查询结果监听者
		geoCoder.setOnGetGeoCodeResultListener(this);

		searchAddress.setText(bdLocation.getLatitude() + "long="
				+ bdLocation.getLongitude());
	}

	// 地理编码查询结果回调函数
	@Override
	public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
	}

	// 反地理编码查询结果回调函数
	@Override
	public void onGetReverseGeoCodeResult(
			ReverseGeoCodeResult reverseGeoCodeResult) {
		List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
	//	PoiAdapter poiAdapter = new PoiAdapter(LocationActivity.this, poiInfos);
		setadress(poiInfos);

	}

	private void setadress(final List<PoiInfo> poiInfos) {
	}

	/**
	 * 手势操作地图，设置地图状态等操作导致地图状态开始改变
	 *
	 * @param mapStatus
	 *            地图状态改变开始时的地图状态
	 */
	@Override
	public void onMapStatusChangeStart(MapStatus mapStatus) {
	}

	@Override
	public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

	}

	/**
	 * 地图状态变化中
	 *
	 * @param mapStatus
	 *            当前地图状态
	 */
	@Override
	public void onMapStatusChange(MapStatus mapStatus) {
	}

	/**
	 * 地图状态改变结束
	 *
	 * @param mapStatus
	 *            地图状态改变结束后的地图状态
	 */
	@Override
	public void onMapStatusChangeFinish(MapStatus mapStatus) {
		// 地图操作的中心点
		LatLng cenpt = mapStatus.target;

		geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
		loactionY = cenpt.latitude;
		loactionX = cenpt.longitude;
		isChange = true;
	}

	/**
	 * 输入框监听---输入之前
	 *
	 * @param s
	 *            字符序列
	 * @param start
	 *            开始
	 * @param count
	 *            总计
	 * @param after
	 *            之后
	 */
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	/**
	 * 输入框监听---正在输入
	 *
	 * @param s
	 *            字符序列
	 * @param start
	 *            开始
	 * @param before
	 *            之前
	 * @param count
	 *            总计
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	/**
	 * 输入框监听---输入完毕
	 *
	 * @param s
	 */
	@Override
	public void afterTextChanged(Editable s) {
		/*
		 * if ("".equals(s.toString())) { searchPois.setVisibility(View.GONE); }
		 * else { // 创建PoiSearch实例 PoiSearch poiSearch =
		 * PoiSearch.newInstance(); // 城市内检索 PoiCitySearchOption
		 * poiCitySearchOption = new PoiCitySearchOption(); // 关键字
		 * poiCitySearchOption.keyword(s.toString()); // 城市
		 * poiCitySearchOption.city(city); // 设置每页容量，默认为每页10条
		 * poiCitySearchOption.pageCapacity(10); // 分页编号
		 * poiCitySearchOption.pageNum(1);
		 * poiSearch.searchInCity(poiCitySearchOption); // 设置poi检索监听者 poiSearch
		 * .setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
		 * // poi 查询结果回调
		 * 
		 * @Override public void onGetPoiResult(PoiResult poiResult) {
		 * List<PoiInfo> poiInfos = poiResult.getAllPoi(); PoiSearchAdapter
		 * poiSearchAdapter = new PoiSearchAdapter( LocationActivity.this,
		 * poiInfos, locationLatLng);
		 * 
		 * try { if (poiInfos.size() > 0) {
		 * searchPois.setVisibility(View.VISIBLE); }
		 * searchPois.setAdapter(poiSearchAdapter); } catch (Exception e) { //
		 *  handle exception }
		 * 
		 * set(poiResult);
		 * 
		 * }
		 * 
		 * // poi 详情查询结果回调
		 * 
		 * @Override public void onGetPoiIndoorResult( PoiIndoorResult
		 * poiDetailResult) { // searchAddress.setText("1111");
		 * 
		 * }
		 * 
		 * @Override public void onGetPoiDetailResult( PoiDetailResult
		 * poiDetailResult) { //
		 * searchAddress.setText(poiDetailResult.getName());
		 * 
		 * } });
		 */
		// }
	}

	protected void set(final PoiResult poiResult) {
		searchPois.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				searchAddress
						.setText(poiResult.getAllPoi().get(position).address);
				searchPois.setVisibility(View.GONE);

			}
		});

	}

	// 回退键
	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 退出时停止定位
		mLocClient.stop();
		// 退出时关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);

		// activity 销毁时同时销毁地图控件
		mBaiduMap.clear();
		mMapView.onDestroy();

		// 释放资源
		if (geoCoder != null) {
			geoCoder.destroy();
		}

		mMapView = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.save:

			if (!isShow) {
				Toast.makeText(this, "请先修改位置", Toast.LENGTH_SHORT).show();
			} else {
				mLoading.show();
				String x = loactionX + "";
				String y = loactionY + "";
				HttpRequestHelper.getInstance().setLocationInfo(this, id, 1, x,
						y, this);

			}

			break;

		case R.id.loca:
			isPoisLL = false;
			iv_change.setVisibility(View.GONE);
			isShow = false;
			isNow = true;
			loaction();

			break;

		case R.id.change:
			isPoisLL = true;
			iv_change.setVisibility(View.VISIBLE);
			isShow = true;

			break;

		default:
			break;
		}

	}

	@Override
	public void onError(String errorMsg) {
		mLoading.dismiss();
		DialogUtil.showMsgDialog(this, errorMsg, true, null);

	}

	@Override
	public void onSuccess(int requestCode, JsonResult mJsonResult) {
		mLoading.dismiss();
		switch (requestCode) {
		case 0:
			if (mJsonResult.getEntity() != null
					&& !mJsonResult.getEntity().equals("{}")) {
				CompanyInfo mCompanyInfo = JSON.parseObject(
						mJsonResult.getEntity(), CompanyInfo.class);
				x = mCompanyInfo.getX();
				y = mCompanyInfo.getY();
//				Log.e("asd", "x=" + x);
//				Log.e("asd", "y=" + y);
			}
			break;

		case 1:
			isPoisLL = false;
			iv_change.setVisibility(View.GONE);
			isShow = false;
			mBaiduMap.clear();
			loaction();
			DialogUtil.showMsgDialog(this, "保存成功", true, null);
			break;

		}
	}

}
