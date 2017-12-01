package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.ZFReviewCompanyListAdapter;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.ReviewInfoGov;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.entity.MessageEvent;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.GreenDaoUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class ZfReviewCompanyListActivity extends BaseActivity implements
        onNetCallback {
   public static final String ACTION_UPDATE_LIST_REVIEW = "ZfReviewCompanyListActivity";
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.search_bar)
    SearchBar searchBar;
    @BindView(R.id.list_troubles_a)
    PullToRefresh listTroublesA;
    private ZFReviewCompanyListAdapter mAdapter;
    private ArrayList<ReviewInfoGov> mdatas;
    private int mCurrentPage = 0;// 当前显示页数量
    private int totalCount = 0;// 总数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zf_trouble_review_list);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initView();
        loadingDatas();
    }

    private void initView() {
        // 注册通知刷新界面的广播

        setHeadTitle("政府待复查列表");
        searchBar.setOnSearchListener(new SearchBar.onSearchListener() {
            @Override
            public void onSearchButtonClick(String searchStr) {
                reLoadListDatas();
            }
        });
        EditText ed = (EditText) findViewById(R.id.et_search_text);
        ed.setHint("请输入企业名称");
        listTroublesA.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(ZfReviewCompanyListActivity.this,
                            "没有更多的数据了。");
                    listTroublesA.finishLoading(false);
                } else {
                    loadingDatas();
                }
            }
        });
        mdatas = new ArrayList<ReviewInfoGov>();
        mAdapter = new ZFReviewCompanyListAdapter(this, mdatas);
        listTroublesA.setAdapter(mAdapter);
        mLoading = new LoadingDialogUtil(this);

    }

    @OnItemClick(R.id.list_troubles_a)
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        Intent mIntent = new Intent(ZfReviewCompanyListActivity.this,
                ZfReviewCompanyHiddenListActivity.class);
        mIntent.putExtra("companyId", mdatas.get(position).getCompanyId());
        mIntent.putExtra("source", "review");
        startActivity(mIntent);
    }

    private void loadingDatas() {
        mLoading.show();
        getLocalData();
        HttpRequestHelper.getInstance().getReviewList(this, mCurrentPage,
                totalCount, searchBar.getSearchData(), 0, this);
    }

    private void getLocalData() {
        //先加载本地数据
        if(mCurrentPage == 0) {//1、第一次加载,第一页，后续页暂无需获取缓存
            List<ReviewInfoGov> reviewInfoGovs = new ArrayList<ReviewInfoGov>();
            try {
                reviewInfoGovs = GreenDaoUtil.getDaoSession().getReviewInfoGovDao().loadAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (reviewInfoGovs.size() > 0) {//2、如果本地有数据，则加载，显示，无数据则去网络中获取
                mLoading.dismiss();
                mdatas.addAll(reviewInfoGovs);
            }
            //再更新网络数据
            mAdapter.notifyDataSetChanged();
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
                if (mJsonResult.getJson() == null) {
                    if (mdatas.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(ZfReviewCompanyListActivity.this,
                                "没有更多的数据。", false, null);
                    }
                } else {

                    mCurrentPage = mCurrentPage + Const.PAGE_SIZE;
                    totalCount = mJsonResult.getTotalCount();
                    List<ReviewInfoGov> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), ReviewInfoGov.class);
                    if (list == null || list.size() == 0) {
                    } else {
                        if(mCurrentPage == Const.PAGE_SIZE){//第一页加载则先清除
                            mdatas.clear();
                            GreenDaoUtil.getDaoSession().getReviewInfoGovDao().deleteAll();
                        }
                        GreenDaoUtil.getDaoSession().getReviewInfoGovDao().insertOrReplaceInTx(list);
                        mdatas.addAll(list);
                    }
                }

                mAdapter.notifyDataSetChanged();
                listTroublesA.finishLoading(false);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.btn_back, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.title_right:
                Intent mIntent = new Intent(ZfReviewCompanyListActivity.this,
                        EnterpriseListActivity.class);
                mIntent.putExtra(EnterpriseListActivity.SKIP_TYPR,
                        EnterpriseListActivity.SKIP_CHECK_RECORD_LIST_TO_REVIEW);
                startActivity(mIntent);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals(ACTION_UPDATE_LIST_REVIEW)){
            searchBar.clearData();
            reLoadListDatas();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    private void reLoadListDatas() {
        // 刷新列表
        mCurrentPage = 0;
        totalCount = 0;
        mdatas.clear();
        GreenDaoUtil.getDaoSession().getReviewInfoGovDao().deleteAll();
        mAdapter.notifyDataSetChanged();
        mLoading.show();
        loadingDatas();

    }


}
