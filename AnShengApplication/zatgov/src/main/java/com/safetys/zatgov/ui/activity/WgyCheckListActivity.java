package com.safetys.zatgov.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.WgyCheckListAdapter;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.SafetyCheck;
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
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class WgyCheckListActivity extends BaseActivity implements onNetCallback {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.iv_use)
    ImageView ivUse;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.checklist_search_bar)
    com.safetys.zatgov.ui.view.SearchBar checklistSearchBar;
    @BindView(R.id.list_troubles)
    PullToRefresh listTroubles;
    private String isChecklist = "0";
    private int mCurrentPage = 0;// 当前页
    private int totalCount = 0;// 总数
    private ArrayList<SafetyCheck> mdatas;
    private WgyCheckListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wgy_check_list);
        ButterKnife.bind(this);
        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
    private void initData() {
        mLoading.show();
        loadingSafetyChecks();
    }

    private void initView() {
        setHeadTitle("我的检查表");
        Intent mIntent = getIntent();
        if (mIntent != null) {
            isChecklist = mIntent.getStringExtra("isChecklist");
        }
        mLoading = new LoadingDialogUtil(this);
        listTroubles.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(WgyCheckListActivity.this,
                            "没有更多的数据了。");
                    listTroubles.finishLoading(false);
                } else {
                    loadingSafetyChecks();
                }
            }

        });
        mdatas = new ArrayList<SafetyCheck>();
        ivUse.setVisibility(View.GONE);
        checklistSearchBar.setOnSearchListener(new SearchBar.onSearchListener() {
            @Override
            public void onSearchButtonClick(String searchStr) {
                mCurrentPage = 0;
                totalCount = 0;
                mdatas.clear();
                mAdapter.notifyDataSetChanged();
                mLoading.show();
                loadingSafetyChecks();
            }
        });
        checklistSearchBar.setHintSearch("请输入检查表名");
        checklistSearchBar.setHintCorlor(Color.WHITE);
        mAdapter = new WgyCheckListAdapter(this, mdatas);
        listTroubles.setAdapter(mAdapter);

    }
    /**
     * 获取检查表数据
     */
    private void loadingSafetyChecks() {
        String searchStr = "";
        searchStr =  checklistSearchBar.getSearchData();
        HttpRequestHelper.getInstance().getWgyCheckList(this, totalCount,
                mCurrentPage, searchStr,
                Const.WGY_CHECKLIST, this);
    }

    @OnItemClick(R.id.list_troubles)
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id){
        Intent mIntent = new Intent();
        // 详情
        mIntent.setClass(WgyCheckListActivity.this,
                WgyMakeCheckListActivity.class);
        mIntent.putExtra("checkId", mdatas.get(position).getId());
        startActivity(mIntent);
        finish();
    }


    @OnItemLongClick(R.id.list_troubles)
    public boolean onItemLongClick(AdapterView<?> parent,
                                   View view, int position, long id){
        mdatas.remove(position);
        delete(mdatas.get(position).getId());
        return true;
    }


    @OnClick({R.id.btn_back, R.id.iv_use, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_use:
                int checkId = -1;
                for (int i = 0; i < mdatas.size(); i++) {
                    if (mdatas.get(i).getCheck()) {
                        checkId = mdatas.get(i).getId();
                    }
                }
                if (checkId == -1) {
                    ToastUtils.showMessage(getApplicationContext(), "请选择一张检查表！");
                } else {
                    Intent mIntent = new Intent();

                    // 去企业。
                    mIntent.setClass(WgyCheckListActivity.this,
                            EnterpriseListActivity.class);
                    mIntent.putExtra("checkId", checkId);
                    mIntent.putExtra("skipType", 1);
                    mIntent.putExtra("source", "checkTable");
                    startActivity(mIntent);
                    finish();
                }
                break;
            case R.id.iv_add:
                Intent intent = new Intent(this, WgyMakeCheckListActivity.class);
                intent.putExtra("checkId", -99);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onError(String errorMsg) {
        mLoading.dismiss();
    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();
        switch (requestCode) {
            case Const.WGY_CHECKLIST:

                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {
                    if (mdatas.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(this, "没有更多的数据。", false, null);
                    }
                } else {
                    mCurrentPage = mCurrentPage + Const.PAGE_SIZE;
                    totalCount = mJsonResult.getTotalCount();
                    mdatas.addAll(JSON.parseArray((String) mJsonResult.getJson(),
                            SafetyCheck.class));

                        mAdapter.notifyDataSetChanged();

                }
                listTroubles.finishLoading(false);

                break;
            case Const.DELETE_CHECK_LIST:
                ToastUtils.showMessage(getApplicationContext(), "删除成功！");
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 删除检查表
     */
    protected void delete(final int deleteId) {
        DialogUtil.showDefaultAlertDialog_oneb(WgyCheckListActivity.this, "",
                "确认删除?", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mLoading.show();
                        HttpRequestHelper.getInstance().deleteChecklist(
                                WgyCheckListActivity.this, deleteId,
                                Const.DELETE_CHECK_LIST,
                                WgyCheckListActivity.this);
                    }
                });

    }
}
