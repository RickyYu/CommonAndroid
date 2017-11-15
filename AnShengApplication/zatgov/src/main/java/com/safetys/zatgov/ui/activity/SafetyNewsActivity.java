package com.safetys.zatgov.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.LawRegulationListAdapter;
import com.safetys.zatgov.bean.ReadInfo;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
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
public class SafetyNewsActivity extends BaseActivity implements onNetCallback {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.list_news)
    PullToRefresh listNews;

    private LawRegulationListAdapter mAdapter;
    private ArrayList<ReadInfo> mdatas;
    private int mCurrentPage = 0;//当前页
    private int totalCount;//总数

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_new);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setHeadTitle("安监要闻");
        mLoading = new LoadingDialogUtil(this, true);
        mLoading.show();
        loadingDatas();
        listNews.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(SafetyNewsActivity.this, "没有更多的数据了。");
                    listNews.finishLoading(false);
                } else {
                    loadingDatas();
                }
            }
        });
        mdatas = new ArrayList<ReadInfo>();
        mAdapter = new LawRegulationListAdapter(this, mdatas);
        listNews.setAdapter(mAdapter);
        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent mIntent = new Intent(SafetyNewsActivity.this, EnterprisePolicyDetailActivity.class);
                String id2 = mdatas.get((int) id).getId();
                Bundle bundle = new Bundle();                           //创建Bundle对象
                bundle.putString("id", id2);     //装入数据
                bundle.putString("title", "安监要闻详情");
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

    }

    private void loadingDatas() {
        HttpRequestHelper.getInstance().getReadList(this, "AJYW", mCurrentPage, Const.NET_GET_COMPANY_READ_LIST_CODE, this);
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
            mdatas.addAll(JSON.parseArray((String)mJsonResult.getJson(), ReadInfo.class));
            mAdapter.notifyDataSetChanged();
        }
        listNews.finishLoading(false);
    }
}
