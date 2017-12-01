package com.safetys.zatgov.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.CheckListAdapter;
import com.safetys.zatgov.adapter.MyCheckContentAdapter;
import com.safetys.zatgov.adapter.WgyMakeCheckListAdapter;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.SafetyCheck;
import com.safetys.zatgov.bean.SafetyMatter;
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

import static com.safetys.zatgov.R.id.ed_checkname;
import static com.safetys.zatgov.R.id.ed_remark;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class WgyMakeCheckListActivity extends BaseActivity implements onNetCallback {

    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(ed_checkname)
    EditText edCheckname;
    @BindView(ed_remark)
    EditText edRemark;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.list_troubles)
    com.safetys.zatgov.ui.view.PullToRefresh listTroubles;
    private int mCurrentPage = 0;// 当前页
    private int mCurrentPage_One = 0;// 当前页
    private int mCurrentPage_Two = 0;// 当前页
    private int checkId = -99;
    private int totalCount = 0;// 总数
    private int totalCount_One = 0;// 总数
    private int totalCount_Two = 0;// 总数
    private boolean isAdd;
    private WgyMakeCheckListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wgy_make_check_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setHeadTitle("制作检查表");
        mLoading = new LoadingDialogUtil(this);
        mdatas = new ArrayList<SafetyMatter>();
        mAdapter = new WgyMakeCheckListAdapter(this, mdatas);
        listTroubles.setAdapter(mAdapter);
        listTroubles.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                listTroubles.finishLoading(false);
            }

        });
    }

    private void initData() {
        Intent intent = this.getIntent();
        checkId = intent.getIntExtra("checkId", -99);
        loadingSafetyChecks();
    }

    private void loadingSafetyChecks() {
        if (checkId != -99) {
            mLoading.show();
            HttpRequestHelper.getInstance().getSafetyCheckInfo(this, checkId,
                    Const.NET_GET_SAFETY_CHECK_INFO_CODE, this);
            setHeadTitle("修改检查表");
        } else {
            isAdd = true;
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_submit, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent2=new Intent(this,WgyCheckListActivity.class);
                intent2.putExtra("isChecklist", "0");
                startActivity(intent2);
                finish();
                break;
            case R.id.btn_submit:
                if (!submit()) {
                    return;
                }
                List<Integer> listId = new ArrayList<Integer>();
                mLoading.show();
                String ids = "";
                for (int i = 0; i < mdatas.size(); i++) {
                    listId.add(mdatas.get(i).getInfoId());
                    ids += mdatas.get(i).getInfoId();
                    ids += ",";
                }
                ids = ids.substring(0, ids.length() - 1);
                HttpRequestHelper.getInstance().addChecklistchange(this, checkId,
                        checkname, remark, ids, Const.SUBMIT_CHECK_LIST_TWO, this);
                break;
            case R.id.btn_add:
                mCurrentPage_One = 0;
                totalCount_One = 0;
                showChecklist();
                break;
        }
    }
    private String checkname;
    private String remark;
    private ArrayList<SafetyMatter> mdatas;
    private boolean submit() {
        checkname = edCheckname.getText().toString();
        remark = edRemark.getText().toString();
        if (TextUtils.isEmpty(checkname)) {
            ToastUtils.showMessage(this, "检查表名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(remark)) {
            ToastUtils.showMessage(this, "备注不能为空");
            return false;
        }

        if(mdatas.size()==0){
            ToastUtils.showMessage(this, "检查事项不能为空");
            return false;
        }
        return true;
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

            case Const.NET_GET_SAFETY_CHECK_INFO_CODE:
                if (mJsonResult.getEntity() != null
                        && !mJsonResult.getEntity().equals("{}")) {
                    SafetyCheck safetyCheck = JSON.parseObject(
                            mJsonResult.getEntity(), SafetyCheck.class);
                    edCheckname.setText(safetyCheck.getTitle());
                    edRemark.setText(safetyCheck.getRemark());
                }

                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {
                    if (mdatas.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(this, "没有更多的数据。", false, null);
                    }
                } else {
                    mdatas.addAll(JSON.parseArray((String) mJsonResult.getJson(),
                            SafetyMatter.class));
                    mAdapter.notifyDataSetChanged();
                }
                listTroubles.finishLoading(false);

                break;
            case Const.NET_CHECK_LIST_ONE:
                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {
                    if (mcheckDatas_One.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(this, "没有更多的数据。", false, null);
                    }
                } else {
                    mCurrentPage_One = mCurrentPage_One + Const.PAGE_SIZE;
                    totalCount_One = mJsonResult.getTotalCount();
                    mcheckDatas_One.addAll(JSON.parseArray(
                            (String) mJsonResult.getJson(), SafetyCheck.class));
                    mChecklistAdapter_One.notifyDataSetChanged();
                }
                list_checklist_One.finishLoading(false);

                break;
            case Const.NET_CHECK_LIST_TWO:
                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {
                    if (mcheckDatas_Two.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(this, "没有更多的数据。", false, null);
                    }
                } else {
                    mCurrentPage_Two = mCurrentPage_Two + Const.PAGE_SIZE;
                    totalCount_Two = mJsonResult.getTotalCount();
                    mcheckDatas_Two.addAll(JSON.parseArray(
                            (String) mJsonResult.getJson(), SafetyMatter.class));
                    mChecklistAdapter_Two.notifyDataSetChanged();
                }
                list_checklist_Two.finishLoading(false);

                break;
            case Const.SUBMIT_CHECK_LIST_TWO:
                Intent intent2=new Intent(this,WgyCheckListActivity.class);
                intent2.putExtra("isChecklist", "0");
                startActivity(intent2);
                this.finish();
                break;

            default:
                break;
        }

    }
    private PullToRefresh list_checklist_One;
    private PullToRefresh list_checklist_Two;
    private SearchBar mSearchBar_One;
    private ArrayList<SafetyCheck> mcheckDatas_One;
    private SearchBar mSearchBar_Two;
    private ArrayList<SafetyMatter> mcheckDatas_Two;
    private MyCheckContentAdapter mChecklistAdapter_Two;
    private CheckListAdapter mChecklistAdapter_One;
    private Dialog dialogOne;
    private int entityId;
    private void showChecklist() {
        // 新建檢查表
        View inflate = LayoutInflater.from(WgyMakeCheckListActivity.this)
                .inflate(R.layout.wgy_checklist, null);
        list_checklist_One = (PullToRefresh) inflate
                .findViewById(R.id.list_checklist);
        mcheckDatas_One = new ArrayList<SafetyCheck>();
        mSearchBar_One = (SearchBar) inflate
                .findViewById(R.id.checklist_search_bar);
        mSearchBar_One.setHintSearch("请输入检查表名");
        mSearchBar_One.setOnSearchListener(new SearchBar.onSearchListener() {
            @Override
            public void onSearchButtonClick(String searchStr) {
                reloadingAdd();
            }
        });
        mChecklistAdapter_One = new CheckListAdapter(this, mcheckDatas_One);

        list_checklist_One.setAdapter(mChecklistAdapter_One);
        list_checklist_One.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                entityId = mcheckDatas_One.get(position).getId();
                if (dialogOne != null) {
                    dialogOne.dismiss();
                }
                addCheckContent();
            }
        });
        list_checklist_One.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage_One > totalCount_One) {
                    ToastUtils.showMessage(WgyMakeCheckListActivity.this,
                            "没有更多的数据了。");
                    list_checklist_One.finishLoading(false);
                } else {
                    loadingAdd();
                }
            }

        });

        loadingAdd();
        if (!DialogUtil.isFastDoubleClick()) {
            dialogOne = DialogUtil.getlistDialog(this, "检查表", inflate, null);
            dialogOne.getWindow().setType(
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialogOne.show();
        }
    }

    private void loadingAdd() {
        mLoading.show();
        HttpRequestHelper.getInstance().getChecklistone(this,
                mSearchBar_One.getSearchData(), totalCount_One,
                mCurrentPage_One, Const.PAGE_SIZE, Const.NET_CHECK_LIST_ONE, this);
    }
    private void reloadingAdd() {
        totalCount_One = 0;
        mCurrentPage_One = 0;
        mcheckDatas_One.clear();
        mChecklistAdapter_One.notifyDataSetChanged();
        loadingAdd();
    }

    //检查项名
    private void addCheckContent() {
        // 新建檢查表
        View inflate = LayoutInflater.from(WgyMakeCheckListActivity.this)
                .inflate(R.layout.wgy_checklist, null);
        list_checklist_Two = (PullToRefresh) inflate
                .findViewById(R.id.list_checklist);
        mcheckDatas_Two = new ArrayList<SafetyMatter>();
        mSearchBar_Two = (SearchBar) inflate
                .findViewById(R.id.checklist_search_bar);
        mSearchBar_Two.setHintSearch("请输入检查项名");
        mSearchBar_Two.setOnSearchListener(new SearchBar.onSearchListener() {
            @Override
            public void onSearchButtonClick(String searchStr) {
                reloadingAddTwo();
            }
        });
        mChecklistAdapter_Two = new MyCheckContentAdapter(this, mcheckDatas_Two,mdatas);
        list_checklist_Two.setAdapter(mChecklistAdapter_Two);
        list_checklist_Two.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checked);
                checkBox.setChecked(!checkBox.isChecked());
                mChecklistAdapter_Two.notifyDataSetChanged();
            }
        });
        list_checklist_Two.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage_Two > totalCount_Two) {
                    ToastUtils.showMessage(WgyMakeCheckListActivity.this,
                            "没有更多的数据了。");
                    list_checklist_Two.finishLoading(false);
                } else {
                    loadingAddTwo();
                }
            }

        });

        loadingAddTwo();
        DialogUtil.showlistDialog_button(this, "检查表", inflate,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void reloadingAddTwo() {
        totalCount_One = 0;
        mCurrentPage_One = 0;
        mcheckDatas_Two.clear();
        mChecklistAdapter_Two.notifyDataSetChanged();
        loadingAddTwo();
    }

    private void loadingAddTwo() {
        mLoading.show();
        HttpRequestHelper.getInstance().getChecklisttwo(this, entityId,
                mSearchBar_Two.getSearchData(), totalCount_One,
                mCurrentPage_One, Const.PAGE_SIZE, Const.NET_CHECK_LIST_TWO, this);
    }

}
