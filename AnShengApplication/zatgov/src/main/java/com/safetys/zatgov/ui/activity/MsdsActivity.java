package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.MsdsListAdapter;
import com.safetys.zatgov.bean.MSDSinfo;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class MsdsActivity extends BaseActivity implements onNetCallback {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.search_bar)
    SearchBar searchBar;
    @BindView(R.id.list_msds)
    PullToRefresh listMsds;

    private ArrayList<MSDSinfo> mdatas;

    private int mCurrentPage = 0;//当前页
    private int totalCount = 0;//总数
    private MsdsListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msds);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setHeadTitle("MSDS查询");
        searchBar.setOnSearchListener(new SearchBar.onSearchListener() {

            @Override
            public void onSearchButtonClick(String searchStr) {
                update();
            }
        });
        EditText search =(EditText) findViewById(R.id.et_search_text);
        search.setHint("请输入中文名");
        mLoading=new LoadingDialogUtil(this,true);
        mLoading.show();
        loadingDatas();
        listMsds.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            public void onLoadingMore() {
                // 加载更多
                if(mCurrentPage>totalCount){
                    ToastUtils.showMessage(MsdsActivity.this, "没有更多的数据了。");
                    listMsds.finishLoading(false);
                }else{
                    loadingDatas();
                }
            }
        });
        mdatas = new ArrayList<MSDSinfo>();
        mAdapter = new MsdsListAdapter(this, mdatas);
        listMsds.setAdapter(mAdapter);

        listMsds.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent mIntent = new Intent(MsdsActivity.this, MsdsItemActivity.class);
                String id2=mdatas.get(position).getId();
                MSDSinfo msdSinfo=mdatas.get(position);
                Bundle bundle = new Bundle();                           //创建Bundle对象
                bundle.putString("chineseName", msdSinfo.getChineseName());
                bundle.putString("englishName", msdSinfo.getEnglishName());
                bundle.putString("cas", msdSinfo.getCas());
                bundle.putString("dangerousGoodsNumber", msdSinfo.getDangerousGoodsNumber());
                bundle.putString("appearanceAndProperties", msdSinfo.getAppearanceAndProperties());
                bundle.putString("avoidContactConditions", msdSinfo.getAvoidContactConditions());
                bundle.putString("meltingPoint", msdSinfo.getMeltingPoint());
                bundle.putString("boilingPoint", msdSinfo.getBoilingPoint());
                bundle.putString("water", msdSinfo.getWater());
                bundle.putString("atmosphere", msdSinfo.getAtmosphere());
                bundle.putString("saturatedSteamPressure", msdSinfo.getSaturatedSteamPressure());
                bundle.putString("solubility", msdSinfo.getSolubility());
                bundle.putString("burningProperty", msdSinfo.getBurningProperty());
                bundle.putString("autoignitionTemperature", msdSinfo.getAutoignitionTemperature());
                bundle.putString("lowerExplosion", msdSinfo.getLowerExplosion());
                bundle.putString("upperExplosion", msdSinfo.getUpperExplosion());
                bundle.putString("precursorProduct", msdSinfo.getPrecursorProduct());

                bundle.putString("stability", msdSinfo.getStability());
                bundle.putString("polymerizationHazard", msdSinfo.getPolymerizationHazard());
                bundle.putString("riskCategories", msdSinfo.getRiskCategories());
                bundle.putString("protectiveClothing", msdSinfo.getProtectiveClothing());
                bundle.putString("handProtection", msdSinfo.getHandProtection());
                bundle.putString("invasionPathway", msdSinfo.getInvasionPathway());
                bundle.putString("toxicity", msdSinfo.getToxicity());
                bundle.putString("mainUse", msdSinfo.getMainUse());
                bundle.putString("riskCharacteristics", msdSinfo.getRiskCharacteristics());
                bundle.putString("fireMethod", msdSinfo.getFireMethod());
                bundle.putString("handingInformation", msdSinfo.getHandingInformation());
                bundle.putString("healthHarm", msdSinfo.getHealthHarm());
                bundle.putString("eyeprotection", msdSinfo.getEyeprotection());
                bundle.putString("inhalationHarm", msdSinfo.getInhalationHarm());
                bundle.putString("ingestionHarm", msdSinfo.getIngestionHarm());
                bundle.putString("engineeringControl", msdSinfo.getEngineeringControl());
                bundle.putString("respiratorySystemProtection", msdSinfo.getRespiratorySystemProtection());
                bundle.putString("leakageDisposal", msdSinfo.getLeakageDisposal());
                bundle.putString("attentions", msdSinfo.getAttentions());
                Log.e("info", msdSinfo.getChineseName());
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

    }

    private void update() {
        mCurrentPage = 0;
        totalCount = 0;
        mdatas.clear();
        mAdapter.notifyDataSetChanged();
        mLoading.show();
        loadingDatas();

    }

    private void loadingDatas() {
        HttpRequestHelper.getInstance().getMsds(this, mCurrentPage, totalCount, searchBar.getSearchData(), Const.NET_GET_COMPANY_MSDS_CODE, this);

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onError(String errorMsg) {
        mLoading.dismiss();
        DialogUtil.showMsgDialog(this, errorMsg, true, null);
    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();

        if(mJsonResult.getJson() == null||mJsonResult.getJson().toString().equals("[]")){
            if(mdatas.size()==0){
                DialogUtil.showMsgDialog(this, "没有数据", false, null);
            }else{
                DialogUtil.showMsgDialog(this, "没有更多的数据。", false, null);
            }
        }else{
            mCurrentPage=mCurrentPage+Const.PAGE_SIZE;
            totalCount = mJsonResult.getTotalCount();
            mdatas.addAll(JSON.parseArray((String)mJsonResult.getJson(), MSDSinfo.class));
            mAdapter.notifyDataSetChanged();
        }
        listMsds.finishLoading(false);
    }
}
