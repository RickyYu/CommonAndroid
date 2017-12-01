package com.safetys.zatgov.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.CheckList;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.entity.MessageEvent;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:检查记录——隐患列表
 */
public class NewZfYhLrActivity extends BaseActivity implements View.OnClickListener,
        onNetCallback, SearchBar.onSearchListener {
    public static final String ACTION_UPDATE = "cn.saftys.NewZfYhLrActivity.updat.list";
    private View mBtnBack;
    private PullToRefresh mListView;
    private MyListAdapter mAdapter;
    private ArrayList<HyCheckItemInfo> mDatas;
    private LoadingDialogUtil mLoading;
    private int mCurrentPage = 0;// 当前显示页数量
    private int totalCount = 0;// 总数
    private int mDeteleItem = -1;
    private EditText ed;
    private View llIshave;
    private View btnSubmit;

    private View btnAdd;
    private View btnLook;
    private String companyid;
    private String have;
    private String isform;
    private CheckList checkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_yh);
        EventBus.getDefault().register(this);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        setHeadTitle("检查记录-隐患列表");
        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        btnLook = findViewById(R.id.btn_look);
        btnLook.setOnClickListener(this);
        have = getIntent().getExtras().getString("ishave");
        isform = getIntent().getExtras().getString("isform");
        checkList = (CheckList) getIntent().getExtras().getSerializable(
                "checklist");
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        mListView = (PullToRefresh) findViewById(R.id.list_yh);
        mListView.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(NewZfYhLrActivity.this, "没有更多的数据了。");
                    mListView.finishLoading(false);
                } else {
                    loadingDatas();
                }
            }
        });

        mDatas = new ArrayList<HyCheckItemInfo>();
        mAdapter = new MyListAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);
        mLoading = new LoadingDialogUtil(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private Intent intent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (mDatas.get(position).getIsBig().equals("0")) {
                    intent = new Intent(NewZfYhLrActivity.this,
                            NoTroubleModifyActivity.class);

                } else {
                    intent = new Intent(NewZfYhLrActivity.this,
                            NoMajorChangeActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", mDatas.get(position).getId() + "");
                bundle.putSerializable("checklist", (Serializable) checkList);
                bundle.putString("noteid", checkList.getCheckId());
                bundle.putString("companyid", companyid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        companyid = checkList.getCompanyId();
        if (!checkList.getCheckId().isEmpty()) {
            mLoading.show();
            loadingDatas();
        }
    }

    private void delete(final int position) {
        DialogUtil.showMsgDialog2(NewZfYhLrActivity.this, "是否删除？", "取消",
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mLoading.show();
                        mDeteleItem = position;
                        HttpRequestHelper.getInstance().deleteGeneralInfo(
                                NewZfYhLrActivity.this,
                                mDatas.get(position).getId() + "", 147,
                                NewZfYhLrActivity.this);
                    }
                });
    }

    private void delete2(final int position) {
        DialogUtil.showMsgDialog2(NewZfYhLrActivity.this, "是否删除？", "取消",
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mLoading.show();
                        mDeteleItem = position;
                        HttpRequestHelper.getInstance().deleteMajorInfo(
                                NewZfYhLrActivity.this,
                                mDatas.get(position).getId() + "", 148,
                                NewZfYhLrActivity.this);
                    }
                });
    }

    private void loadingDatas() {

        HttpRequestHelper.getInstance().getCheckYhListInfo(this, checkList.getCheckId(), 111,
                this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                EventBus.getDefault().post(new MessageEvent(EnterpriseListActivity.ACTION_UPDATE_DATA));
                finish();
                break;

            case R.id.btn_add:
                Intent intent;
                intent = new Intent(this, TroubleRegisterActivity.class);
                intent.putExtra("checklist", (Serializable) checkList);
                intent.putExtra("ishave", have);
                intent.putExtra("isform", isform);
                intent.putExtra("noteId", checkList.getCheckId());
                startActivity(intent);
                finish();
                break;

            case R.id.btn_look:
         /*       //// FIXME: 2017/11/16
                intent = new Intent(this, NoTroubleListActivity.class);
                intent.putExtra("checklist", (Serializable) checkList);
                intent.putExtra("ishave", have);
                intent.putExtra("isform", isform);
                intent.putExtra("noteId", checkList.getCheckId());
                startActivity(intent);
                finish();*/
                break;

            case R.id.btn_submit:
                if (mDatas.size() == 0) {
                    Toast.makeText(this, "请先添加隐患", Toast.LENGTH_SHORT).show();
                } else {
                    EventBus.getDefault().post(new MessageEvent(EnterpriseListActivity.ACTION_UPDATE_DATA));
                    ToastUtils.showMessage(getApplicationContext(), "提交成功");
                    finish();
	/*			// TODO判断是否无隐患。
				DialogUtil.showMsgDialog(this, "提交成功", false,
						new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						});*/
                }
                break;
            default:
                break;
        }
    }

    private class MyListAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<HyCheckItemInfo> mDatas;
        private ViewHodler mHodler;

        public MyListAdapter(Context context, ArrayList<HyCheckItemInfo> mDatas) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mDatas = mDatas;
        }

        @Override
        public int getCount() {
     
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
     
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
     
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(
                        R.layout.list_view_top_and_bottom_string_item, null);
                mHodler = new ViewHodler();
                mHodler.mTextView1 = (TextView) convertView
                        .findViewById(R.id.text1);
                mHodler.mTextView2 = (TextView) convertView
                        .findViewById(R.id.text2);

            }

            mHodler.mTextView1.setText(mDatas.get(position).getContent());
            mHodler.mTextView2.setText("录入日期："
                    + mDatas.get(position).getCreateTime());

            return convertView;
        }
    }

    private class ViewHodler {
        TextView mTextView1;
        TextView mTextView2;
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
            case 111:
                if (mJsonResult.getJson() == null) {
                    if (mDatas.size() == 0) {
                        Toast.makeText(this, "没有数据,请先新增隐患", Toast.LENGTH_SHORT)
                                .show();

                    } else {
                        DialogUtil.showMsgDialog(NewZfYhLrActivity.this,
                                "没有更多的数据。", false, null);
                    }
                } else {

                    mCurrentPage = mCurrentPage + 10;
                    totalCount = mJsonResult.getTotalCount();
                    List<HyCheckItemInfo> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), HyCheckItemInfo.class);
                    if (list == null || list.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                        // Intent intent = new Intent(this,
                        // TroubleRegisterActivity.class);
                        // Bundle bundle2 = new Bundle();
                        // bundle2.putString("id", companyid);
                        // bundle2.putString("noteid", noteId);
                        // intent.putExtras(bundle2);
                        // startActivity(intent);
                    } else {
                        mDatas.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                }

                mListView.finishLoading(false);
                break;
            case 147:

                DialogUtil.showMsgDialog(this, "删除成功", false, null);
                reLoadListDatas();
                break;
            case 148:

                DialogUtil.showMsgDialog(this, "删除成功", false, null);
                reLoadListDatas();
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals(ACTION_UPDATE)){
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
        mDatas.clear();
        mAdapter.notifyDataSetChanged();
        mLoading.show();
        loadingDatas();

    }

    @Override
    public void onSearchButtonClick(String searchStr) {
 

    }
}
