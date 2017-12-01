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
import com.safetys.zatgov.base.BaseActivity;
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
public class LawRegulationsActivity extends BaseActivity implements onNetCallback {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.list_laws)
    PullToRefresh listLaws;
    private int mCurrentPage = 0;//当前页
    private int totalCount;//总数
    private ArrayList<ReadInfo> mdatas;
    private LawRegulationListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_regulations);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        loadingDatas();
        mLoading=new LoadingDialogUtil(this,true);
        mLoading.show();
        setHeadTitle("法律法规");
        listLaws.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if(mCurrentPage>totalCount){
                    ToastUtils.showMessage(LawRegulationsActivity.this, "没有更多的数据了。");
                    listLaws.finishLoading(false);
                }else{
                    loadingDatas();
                }
            }
        });

        listLaws.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent mIntent = new Intent(LawRegulationsActivity.this, EnterprisePolicyDetailActivity.class);
                String id2=mdatas.get((int) id).getId();
                Bundle bundle = new Bundle();                           //创建Bundle对象
                bundle.putString("id", id2);     //装入数据
                bundle.putString("title", "法律法规详情");
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

        mdatas = new ArrayList<ReadInfo>();
        mAdapter = new LawRegulationListAdapter(this, mdatas);
        listLaws.setAdapter(mAdapter);

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

    private void loadingDatas() {
        HttpRequestHelper.getInstance().getReadList(this,"FLFG",mCurrentPage, Const.NET_GET_COMPANY_READ_LIST_CODE, this);
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
        listLaws.finishLoading(false);
    }
}
