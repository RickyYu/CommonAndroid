package com.safetys.zatgov.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.DateDistanceUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.EnterpriseListAdapter;
import com.safetys.zatgov.bean.CompanyVo;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class EnterpriseListActivity extends BaseActivity implements onNetCallback {
    public static final String ACTION_UPDATE_LIST_YH = "EnterpriseListActivity";
    public static final String SKIP_TYPR = "skipType";
    public static final int SEARCH_COMPANY_CODE = 1001;
    public static final int SKIP_COMPANY_INFO = 0;// 企业信息
    public static final int SKIP_CHECK_RECORD_LIST = 4;
    public static final int SKIP_SUPERVISE_CHECKT = 1;// 监督检查
    public static final int SKIP_CHECK_RECORD_LIST_TO_REVIEW = 2;// 企业列表跳到检查表，最终去新增复查信息
    public static final int SKIP_CHECK_RECORD_LIST_TO_PUNISHMENT = 3;// 企业列表跳到检查表，最终去新增处罚信息
    public static final int SKIP_CHECK_RECORD_LIST_EXPANDABLE = 5;// Expandable显示检查记录
    public static final int SKIP_COMPANY_HIDDEN_INFO = 6;// 企业隐患
    public static final int REQUEST_SEARCHDATA_CODE = 0X1001;
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.sp_choice)
    Spinner spChoice;
    @BindView(R.id.search_bar)
    SearchBar searchBar;
    @BindView(R.id.listview)
    PullToRefresh listview;
    private MyBroadcastReceiver mReceiver;
    private int mCurrentPage = 0;// 当前页
    private int totalCount = 0;// 总数
    private static final String[] m = {"修改时间", "隐患数升序", "隐患数降序"};
    private ArrayAdapter<String> adapter;
    private boolean orderType;// 升序降序
    private String orderProperty;// 排序
    private ImageView iv_right;
    private String seekType = "";// 企业状态类型
    private String tradeTypeCode = "";
    private String source;// 表示从哪跳转过来
    private ArrayList<CompanyVo> mdatas;
    private int skipType = SKIP_COMPANY_INFO;
    private int checkId = -1;
    private String oneCode = "";
    private String twoCode = "";
    private String threeCode = "";
    private String bigCode = "";// 企业规模
    private boolean isHistory = false;
    private EditText etSearch;
    private EnterpriseListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_list);
        ButterKnife.bind(this);
        mReceiver = new MyBroadcastReceiver();
        IntentFilter mFilter = new IntentFilter(ACTION_UPDATE_LIST_YH);
        registerReceiver(mReceiver, mFilter);
        initView();
        loadingDatas();
    }

    private void initView() {
        skipType = getIntent().getIntExtra(SKIP_TYPR, SKIP_COMPANY_INFO);
        checkId = getIntent().getIntExtra("checkId", -1);
        seekType = getIntent().getStringExtra("SeekType");
        source = getIntent().getStringExtra("source");
        if(skipType == SKIP_CHECK_RECORD_LIST_EXPANDABLE){
            isHistory = true;
        }
        setHeadTitle("企业信息列表");
        ivRight.setBackgroundResource(R.drawable.icon_search_img);
        ivRight.setVisibility(View.VISIBLE);
        mLoading = new LoadingDialogUtil(this);
        searchBar.setOnSearchListener(new SearchBar.onSearchListener() {
            @Override
            public void onSearchButtonClick(String searchStr) {
                reLoadListDatas();

            }
        });
        etSearch = (EditText) findViewById(R.id.et_search_text);
        spChoice.setVisibility(View.VISIBLE);
        adapter = new ArrayAdapter<String>(this, R.layout.item_spinner_view, m);
        spChoice.setAdapter(adapter);
        listview.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(EnterpriseListActivity.this,
                            "没有更多的数据了。");
                    listview.finishLoading(false);
                } else {
                    loadingDatas();
                }
            }
        });
        mdatas = new ArrayList<CompanyVo>();
        mAdapter = new EnterpriseListAdapter(this, mdatas,skipType);
        listview.setAdapter(mAdapter);
    }

    @OnItemClick(R.id.listview)
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id){
         Intent intent = null;
        boolean needFinish = false;
        switch (skipType) {
            case SKIP_COMPANY_INFO:
                intent = new Intent(EnterpriseListActivity.this,
                        EnterpriseChooseActivity.class);
                break;
            case SKIP_SUPERVISE_CHECKT:
                CompanyVo companys = mdatas.get(position);
                if (DateDistanceUtils.isEndDay(companys.getCreateTime())) {
                    // 新增隐患检查
                    intent = new Intent(EnterpriseListActivity.this,
                            NewZfCheckAddActivityWgy.class);
                    boolean isReview;
                    if (companys.getGridDangerNum().equals("0")) {
                        isReview = false;
                    } else {
                        isReview = true;
                    }
                    intent.putExtra("num", isReview);
                    intent.putExtra("checkId", checkId);

                } else {
                    // 修改隐患
                    intent = new Intent(EnterpriseListActivity.this,
                            ZfReviewCompanyHiddenListActivity.class);
                    intent.putExtra("companyId", mdatas.get(position)
                            .getId());
                    intent.putExtra("source", "check");
                }
                break;
            case SKIP_CHECK_RECORD_LIST_TO_REVIEW:
                //// FIXME: 2017/11/16
        /*        intent = new Intent(EnterpriseListActivity.this,
                        CheckListActivity.class);
                intent.putExtra(SKIP_TYPR, skipType);
                needFinish = true;*/
                break;
            case SKIP_CHECK_RECORD_LIST:
                intent = new Intent(EnterpriseListActivity.this,
                        ZfCheckRecordListActivity.class);
                intent.putExtra("companyName", mdatas.get(position)
                        .getCompanyName());
                break;
            case SKIP_CHECK_RECORD_LIST_EXPANDABLE:
                intent = new Intent(EnterpriseListActivity.this,
                        ZfCheckRecordExpandListActivity.class);
                intent.putExtra("companyName", mdatas.get(position)
                        .getCompanyName());

                break;
            case SKIP_COMPANY_HIDDEN_INFO:
                intent = new Intent(EnterpriseListActivity.this,
                        ZfCompanyHiddenListActivity.class);
                break;
            default:
                break;
        }

        intent.putExtra("id", mdatas.get(position).getId());// 企业id
        intent.putExtra("searchData", searchBar.getSearchData());
        startActivityForResult(intent, REQUEST_SEARCHDATA_CODE);

    }


    @OnItemSelected(value = R.id.sp_choice,callback = OnItemSelected.Callback.ITEM_SELECTED )
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        if (arg2 == 0) {
            orderProperty = null;
            mAdapter.notifyDataSetChanged();
        } else if (arg2 == 1) {
            if (skipType == 0) {
                orderProperty = "allDangerNum";
            } else if (skipType == 1) {
                orderProperty = "gridDangerNum";
            }
            orderType = true;
            reLoadListDatas();
        } else {
            if (skipType == 0) {
                orderProperty = "allDangerNum";
            } else if (skipType == 1) {
                orderProperty = "gridDangerNum";
            }
            orderType = false;
            reLoadListDatas();
        }
    }

    /**
     * 加载数据
     */
    private void loadingDatas() {
        mLoading.show();
        HttpRequestHelper.getInstance().getCompanyListInfo(this, isHistory, tradeTypeCode,
                seekType, bigCode, oneCode, twoCode, threeCode,
                searchBar.getSearchData(), totalCount, mCurrentPage,
                orderProperty, orderType, SKIP_SUPERVISE_CHECKT, this);
    }

    /**
     * 重新加载数据
     */
    private void reLoadListDatas() {
        // 刷新列表
        mCurrentPage = 0;
        totalCount = 0;
        mdatas.clear();
        mAdapter.notifyDataSetChanged();
        loadingDatas();

    }

    @Override
    public void onError(String errorMsg) {
        mLoading.dismiss();
        DialogUtil.showMsgDialog(EnterpriseListActivity.this, errorMsg, true,
                null);
    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();
        switch (requestCode) {
            case SKIP_SUPERVISE_CHECKT:
                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {
                    if (mdatas.size() == 0) {
                        DialogUtil.showMsgDialog(EnterpriseListActivity.this,
                                "没有找到相关数据。", false, null);
                    } else {
                        DialogUtil.showMsgDialog(EnterpriseListActivity.this,
                                "没有更多的数据。", false, null);
                    }
                } else {

                    mCurrentPage = mCurrentPage + Const.PAGE_SIZE;
                    totalCount = mJsonResult.getTotalCount();
                    List<CompanyVo> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), CompanyVo.class);
                    if (list == null || list.size() == 0) {

                    } else {

                        mdatas.addAll(list);
                    }
                }

                mAdapter.notifyDataSetChanged();
                listview.finishLoading(false);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.btn_back, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_right:
                Intent intent = new Intent(EnterpriseListActivity.this,
                        WgySearchCompanysActivity.class);
                startActivityForResult(intent, SEARCH_COMPANY_CODE);
                break;
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(ACTION_UPDATE_LIST_YH)) {
                reLoadListDatas();
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case SEARCH_COMPANY_CODE:
                if (data != null) {
                    Bundle bundle = new Bundle();
                    bundle = data.getExtras();
                    tradeTypeCode = bundle.getString("tradeTypeCode");
                    bigCode = bundle.getString("bigCode");
                    oneCode = bundle.getString("oneCode");
                    twoCode = bundle.getString("twoCode");
                    threeCode = bundle.getString("threeCode");

                }
                reLoadListDatas();
                break;
            case REQUEST_SEARCHDATA_CODE:
                if (data != null) {
                    Bundle bundle = new Bundle();
                    bundle = data.getExtras();
                    searchBar.setSearchData(bundle.getString("searchData"));
                }
                reLoadListDatas();
                break;

            default:
                break;
        }
    }

}
