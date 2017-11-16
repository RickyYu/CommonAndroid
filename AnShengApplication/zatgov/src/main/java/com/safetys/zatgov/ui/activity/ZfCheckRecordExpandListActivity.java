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
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.HistoryCheckRecordExpandAdapter;
import com.safetys.zatgov.bean.CheckListInfo;
import com.safetys.zatgov.bean.WgyHiddenItemInfo;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class ZfCheckRecordExpandListActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback, SearchBar.onSearchListener {
    public static final String ACTION_UPDATE_LIST_CHECK_NEW = "com.saftys.ZfCheckRecordExpandListActivity.updateCheckList.new";
    private String companyId;
    private LoadingDialogUtil mLoading;
    private View mBtn_back;
    private ExpandableListView mExpandableListView;
    private int mCurrentPage = 0;// 当前页
    private int totalCount = 0;// 总数
    private static final int PAGE_SIZE = 10;// 页大小
    private Intent intent;
    private SearchBar mSearchBar;
    private MyBroadcastReceiver mReceiver;
    private View mBtn_Trouble_dfc;// 未处罚
    private View mBtn_Trouble_yfc;// 已处罚
    private TextView tv_company_name;
    private String strPunState; // 是否整改
    private String checkGround = "";// 检查场所
    private Spinner sp_choice;
    private String companyName;
    private String searchData;
    private static final String[] m = { "全部", "未处罚", "已处罚" };
    private ArrayAdapter<String> adapter;
    private int mDeteleItem = -1;
    private EditText etSearch;
    private boolean isReview;
    private ArrayList<CheckListInfo> groupDatas;
    private List<List<WgyHiddenItemInfo>> childDatas;// Child数据
    private HistoryCheckRecordExpandAdapter checkRecordExpandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zf_check_expandable_list);
         initView();
        registRecevier();
    }

    private void initView() {
        setHeadTitle("历史检查记录");
        etSearch = (EditText) findViewById(R.id.et_search_text);
        etSearch.setHint("请输入检查场所");
        Intent intent = this.getIntent();
        companyName = intent.getStringExtra("companyName");
        searchData = intent.getStringExtra("searchData");
        isReview = intent.getBooleanExtra("num", false);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_company_name.setVisibility(View.VISIBLE);
        tv_company_name.setText(companyName);
        mBtn_Trouble_dfc = findViewById(R.id.btn_trouble_review);
        mBtn_Trouble_yfc = findViewById(R.id.btn_trouble_reviewed);
        mBtn_Trouble_dfc.setVisibility(View.GONE);
        mBtn_Trouble_yfc.setVisibility(View.GONE);
        sp_choice = (Spinner) findViewById(R.id.sp_choice);
        sp_choice.setVisibility(View.VISIBLE);
        adapter = new ArrayAdapter<String>(this, R.layout.item_spinner_view, m);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_choice.setAdapter(adapter);
        sp_choice.setOnItemSelectedListener(new SpinnerSelectedListener());
        sp_choice.setVisibility(View.VISIBLE);
        companyId = intent.getExtras().getString("id");
        mLoading = new LoadingDialogUtil(this);
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);
        mSearchBar = (SearchBar) findViewById(R.id.search_bar);
        mSearchBar.setOnSearchListener(this);
        // init Expandable
        groupDatas = new ArrayList<CheckListInfo>();
        childDatas = new ArrayList<List<WgyHiddenItemInfo>>();
        mExpandableListView = (ExpandableListView) findViewById(R.id.el_record);

        checkRecordExpandAdapter = new HistoryCheckRecordExpandAdapter(this,
                groupDatas, childDatas);
        mExpandableListView.setAdapter(checkRecordExpandAdapter);
        checkRecordExpandAdapter.notifyDataSetChanged();

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //ToastUtils.showMessage(getApplicationContext(), "开发中");
                return false;
            }
        });
        mLoading.show();
    }

    /**
     *
     * @author ricky
     *
     */
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            mCurrentPage = 0;
            totalCount = 0;
            groupDatas.clear();
            mSearchBar.clearData();
            etSearch.setText("");
            checkRecordExpandAdapter.notifyDataSetChanged();
            if (arg2 == 0) {
                strPunState = "all";
            } else if (arg2 == 1) {
                strPunState = "unPun";
            } else {
                strPunState = "pun";
            }
            loadingCheckListInfos(strPunState, mSearchBar.getSearchData());
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void start(String id, String companyid, int i) {
        intent = new Intent(ZfCheckRecordExpandListActivity.this,
                NewZfCheckItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("right", i + "");
        bundle.putString("id", id);
        bundle.putString("id2", companyid);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 获取检查记录
     * @param checkGround
     */
    private void loadingCheckListInfos(String strPunState, String checkGround) {
        HttpRequestHelper.getInstance().getCheckRecordList(this, totalCount,
                companyId, strPunState, mSearchBar.getSearchData(),
                mCurrentPage, "", Const.NET_GET_GOV_CHECK_LIST_CODE, this);

    }

    @Override
    public void onSearchButtonClick(String searchStr) {
        mCurrentPage = 0;
        totalCount = 0;
        groupDatas.clear();
        checkRecordExpandAdapter.notifyDataSetChanged();
        loadingCheckListInfos(strPunState, mSearchBar.getSearchData());

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
            case Const.NET_GET_GOV_CHECK_LIST_CODE:
                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {
                    if (groupDatas.size() == 0) {
                        ToastUtils.showMessage(getApplicationContext(), "没有数据");
                    }
                } else {
                    mCurrentPage = mCurrentPage + PAGE_SIZE;
                    totalCount = mJsonResult.getTotalCount();
				/*
				 * recordDates.addAll(JSON.parseArray( (String)
				 * mJsonResult.getJson(), CheckListInfo.class));
				 */
                    groupDatas.addAll(JSON.parseArray(
                            (String) mJsonResult.getJson(), CheckListInfo.class));

                    for (int i = 0; i < groupDatas.size(); i++) {
                        String str = groupDatas.get(i).getDescription();
                        List<WgyHiddenItemInfo> listData = new ArrayList<WgyHiddenItemInfo>();
                        if (!str.equals("")) {
                            String[] result = str.split("&");
                            for (int j = 0; j < result.length; j++) {
                                listData.add(new WgyHiddenItemInfo("", result[j]));
                            }
                            childDatas.add(i, listData);
                        }else{
                            childDatas.add(i, listData);
                        }
                    }
                    checkRecordExpandAdapter.notifyDataSetChanged();

			/*	// 构造Child数据
				List<WgyHiddenItemInfo> listData = new ArrayList<WgyHiddenItemInfo>();
				listData.add(new WgyHiddenItemInfo("隐患1", "厂房安全通道堵塞（检查项一）"));
				listData.add(new WgyHiddenItemInfo("隐患2", "无尘间粉尘超标（检查项二）"));
				for (int i = 0; i < groupDatas.size(); i++) {
					childDatas.add(i, listData);
				}
				checkRecordExpandAdapter.notifyDataSetChanged();*/
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                if (!searchData.equals("")){
                    Intent intent = new Intent(this, EnterpriseListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("searchData", searchData);
                    intent.putExtras(bundle);
                    setResult(EnterpriseListActivity.REQUEST_SEARCHDATA_CODE, intent);
                }
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                updata();
                break;

            case 2:
                updata();
                break;
            default:
                break;
        }
    }

    private void updata() {
        mCurrentPage = 0;
        totalCount = 0;
        groupDatas.clear();
        checkRecordExpandAdapter.notifyDataSetChanged();
        mLoading.show();
        loadingCheckListInfos(strPunState, checkGround);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(ACTION_UPDATE_LIST_CHECK_NEW)) {
                updata();
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    private void registRecevier() {
        // 注册通知刷新界面的广播
        mReceiver = new MyBroadcastReceiver();
        IntentFilter mFilter = new IntentFilter(ACTION_UPDATE_LIST_CHECK_NEW);
        registerReceiver(mReceiver, mFilter);
    }

}
