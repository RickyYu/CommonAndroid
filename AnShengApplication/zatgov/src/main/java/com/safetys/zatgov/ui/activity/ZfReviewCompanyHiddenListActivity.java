package com.safetys.zatgov.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.ZFReviewHiddenListAdapter;
import com.safetys.zatgov.bean.HiddenDesInfoVo;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class ZfReviewCompanyHiddenListActivity extends BaseActivity implements
        onNetCallback {
    public static final String ACTION_UPDATE_REVIEW_HIDDEN_LIST = "ZfTroubleReviewHiddenListActivity";
    // 检查记录ID
    String companyId;
    String source;// 判断是从哪里过来
    boolean isCurrent = false;// 如果是从监督检查过来的，就只查今天的数据
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.search_bar)
    SearchBar searchBar;
    @BindView(R.id.list_troubles_a)
    PullToRefresh listTroublesA;
    private int mCurrentPage = 0;// 当前显示页数量
    private int totalCount = 0;// 总数
    private MyBroadcastReceiver mReceiver;
    private ZFReviewHiddenListAdapter mAdapter;
    private ArrayList<HiddenDesInfoVo> mdatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zf_trouble_review_list);
        ButterKnife.bind(this);
        initView();
        loadingDatas();
    }

    private void initView() {
        setHeadTitle("企业隐患列表");
        // 注册通知刷新界面的广播
        mReceiver = new MyBroadcastReceiver();
        IntentFilter mFilter = new IntentFilter(
                ACTION_UPDATE_REVIEW_HIDDEN_LIST);
        registerReceiver(mReceiver, mFilter);
        companyId = getIntent().getStringExtra("companyId");
        source = getIntent().getStringExtra("source");
        if (source.equals("check")) {// 从检查过来
            isCurrent = true;
        }
        searchBar.setOnSearchListener(new SearchBar.onSearchListener() {
            @Override
            public void onSearchButtonClick(String searchStr) {
                reLoadListDatas();
            }
        });
        EditText   ed = (EditText) findViewById(R.id.et_search_text);
        ed.setHint("请输入隐患名称");
        listTroublesA.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils
                            .showMessage(
                                    ZfReviewCompanyHiddenListActivity.this,
                                    "没有更多的数据了。");
                    listTroublesA.finishLoading(false);
                } else {
                    loadingDatas();
                }
            }
        });

        mdatas = new ArrayList<HiddenDesInfoVo>();
        mAdapter = new ZFReviewHiddenListAdapter(this, mdatas, source);
        listTroublesA.setAdapter(mAdapter);
        mLoading = new LoadingDialogUtil(this);
    }

    @OnItemClick(R.id.list_troubles_a)
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id){
        HiddenDesInfoVo desInfo = mdatas.get(position);
        if (!desInfo.isReview()) {
            // 复查隐患
            // 再传只要传入隐患id
            Intent mIntent = new Intent(
                    ZfReviewCompanyHiddenListActivity.this,
                    ZfReviewCompanyHiddenActivity.class);
            mIntent.putExtra("hidden", desInfo);
            startActivity(mIntent);
        } else {
            // 修改隐患
            Intent mIntent = null;
            if (desInfo.getIsBig().equals("1")) {
                mIntent = new Intent(
                        ZfReviewCompanyHiddenListActivity.this,
                        HistoryMajorChangeActivity.class);
            } else {
                mIntent = new Intent(
                        ZfReviewCompanyHiddenListActivity.this,
                        HistoryTroubleChangeActivity.class);
            }

            mIntent.putExtra("id", desInfo.getHiddenId());
            mIntent.putExtra("companyid", companyId);
            startActivity(mIntent);

        }
    }

    /**
     * 获取未整改隐患列表
     */
    private void loadingDatas() {
        mLoading.show();
        HttpRequestHelper.getInstance().getHiddenDesList(this, companyId,
                isCurrent, Const.NET_GET_GOV_HIDDEN_LIST_CODE, this);

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
            case Const.NET_GET_GOV_HIDDEN_LIST_CODE:
                if (mJsonResult.getJson().equals("[]")) {
                    if (mdatas.size() == 0) {
                        if (source.equals("check")){
                            ToastUtils.showMessage(getApplicationContext(), "该企业今天已检查，无法再添加！");
                        }else{
                            Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        DialogUtil.showMsgDialog(
                                ZfReviewCompanyHiddenListActivity.this, "没有更多的数据。",
                                false, null);
                    }
                } else {
                    mCurrentPage = mCurrentPage + 10;
                    totalCount = mJsonResult.getTotalCount();

                    List<HiddenDesInfoVo> listChildenData = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), HiddenDesInfoVo.class);

                    if (listChildenData == null || listChildenData.size() == 0) {

                    } else {
                        mdatas.addAll(listChildenData);
                    }
                }
                mAdapter.notifyDataSetChanged();
                listTroublesA.finishLoading(false);
                break;

            default:
                break;
        }
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
     
            if (intent.getAction().equals(ACTION_UPDATE_REVIEW_HIDDEN_LIST)) {
                LogUtil.i("now is star reloading list datas for ZfTroubleReviewListActivity");
                searchBar.clearData();
                reLoadListDatas();
            }
        }

    }
    private void reLoadListDatas() {
        // 刷新列表
        mCurrentPage = 0;
        totalCount = 0;
        mdatas.clear();
        mAdapter.notifyDataSetChanged();
        mLoading.show();
        loadingDatas();

    }
}
