package com.safetys.zatgov.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.safetys.widget.common.SPUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.CheckListInfo;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.PrefKeys;
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

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description: 检查记录列表页面
 */
public class ZfCheckRecordListActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback, SearchBar.onSearchListener {
    public static final String ACTION_UPDATE = "cn.saftys.ZfCheckRecordListActivity.updateCheckList.new";
    private String companyId;
    private LoadingDialogUtil mLoading;
    private View mBtn_back;
    private TextView title_right;
    private PullToRefresh pr_record;
    private PullToRefresh pr_b;
    private int mCurrentPage = 0;// 当前页
    private int totalCount = 0;// 总数
    private static final int PAGE_SIZE = 10;// 页大小
    private ArrayList<CheckListInfo> recordDates;
    private Intent intent;
    private MyListAdapter recordListAdapter;
    private SearchBar mSearchBar;
    private View mBtn_Trouble_dfc;// 未处罚
    private View mBtn_Trouble_yfc;// 已处罚
    private TextView tv_company_name;
    private String strPunState; // 是否整改
    private String checkGround = "";// 检查场所
    private Spinner sp_choice;
    private String companyName;
    private static final String[] m = { "全部", "未处罚", "已处罚" };
    private ArrayAdapter<String> adapter;
    private int mDeteleItem = -1;
    private EditText etSearch;
    private boolean isReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zf_check_list);
        EventBus.getDefault().register(this);
        initView();
        setListner();
        mLoading.show();

    }

    private void initView() {
        setHeadTitle("检查记录");
        boolean type = (boolean)SPUtils.getData(PrefKeys.PREF_USER_TYPE_KEY, false);
        if (!type){
            setRightTitle("新增", this);
        }

        etSearch = (EditText) findViewById(R.id.et_search_text);
        etSearch.setHint("请输入检查场所");
        Intent intent = this.getIntent();
        companyName = intent.getStringExtra("companyName");
        isReview=intent.getBooleanExtra("num", false);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_company_name.setVisibility(View.VISIBLE);
        tv_company_name.setText(companyName);
        mBtn_Trouble_dfc = findViewById(R.id.btn_trouble_review);
        mBtn_Trouble_yfc = findViewById(R.id.btn_trouble_reviewed);
        mBtn_Trouble_dfc.setVisibility(View.GONE);
        mBtn_Trouble_yfc.setVisibility(View.GONE);
        sp_choice = (Spinner) findViewById(R.id.sp_choice);
        sp_choice.setVisibility(View.VISIBLE);
        adapter = new ArrayAdapter<String>(this,
                R.layout.item_spinner_view, m);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_choice.setAdapter(adapter);
        sp_choice.setOnItemSelectedListener(new SpinnerSelectedListener());
        sp_choice.setVisibility(View.VISIBLE);
        companyId = intent.getExtras().getString("id");
        mLoading = new LoadingDialogUtil(this);
        title_right = (TextView) findViewById(R.id.title_right);
        title_right.setOnClickListener(this);
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);
        pr_record = (PullToRefresh) findViewById(R.id.list_troubles_a);
        pr_b = (PullToRefresh) findViewById(R.id.list_troubles_b);
        pr_record.setVisibility(View.VISIBLE);
        pr_b.setVisibility(View.GONE);
        mSearchBar = (SearchBar) findViewById(R.id.search_bar);
        mSearchBar.setOnSearchListener(this);
        recordDates = new ArrayList<CheckListInfo>();
        recordListAdapter = new MyListAdapter(this, recordDates);
        pr_record.setAdapter(recordListAdapter);
    }

    private void setListner() {
        pr_record.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(ZfCheckRecordListActivity.this,
                            "没有更多的数据了。");
                    pr_record.finishLoading(false);
                } else {
                    loadingCheckListInfos(strPunState, checkGround);
                }
            }
        });
        pr_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int a=1;
                if (recordDates.get(position).getPunishState()) {
                    a=2;
                }
                start(recordDates.get(position).getId(),
                        recordDates.get(position).getHzCompanyId(), a);
            }

        });

    }

    /**
     * 删除item
     * @param position
     */
    private void delete(final int position) {
        DialogUtil.showMsgDialog2(this, "是否删除？", "取消", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLoading.show();
                mDeteleItem = position;
                deleteCheckRecord(recordDates.get(position).getId());
            }
        });
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
            recordDates.clear();
            mSearchBar.clearData();
            etSearch.setText("");
            recordListAdapter.notifyDataSetChanged();
            if (arg2 == 0) {
                strPunState = "all";
            } else if (arg2 == 1) {
                strPunState = "unPun";
            } else {
                strPunState ="pun";
            }
            loadingCheckListInfos(strPunState, mSearchBar.getSearchData());
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void start(String id, String companyid, int i) {
        intent = new Intent(ZfCheckRecordListActivity.this,
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
                companyId, strPunState, mSearchBar.getSearchData(), mCurrentPage,
                "", Const.NET_GET_GOV_CHECK_LIST_CODE, this);

    }

    /**
     * 删除检查记录
     *
     * @param recordId
     */
    private void deleteCheckRecord(String recordId) {
        HttpRequestHelper.getInstance().deleteCheckInfo(
                getApplicationContext(), recordId,
                Const.NET_DELETE_GOV_CHECK_LIST_CODE, this);
    }

    @Override
    public void onSearchButtonClick(String searchStr) {
        mCurrentPage = 0;
        totalCount = 0;
        recordDates.clear();
        recordListAdapter.notifyDataSetChanged();
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
                    if (recordDates.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mCurrentPage = mCurrentPage + PAGE_SIZE;
                    totalCount = mJsonResult.getTotalCount();
                    recordDates.addAll(JSON.parseArray(
                            (String) mJsonResult.getJson(), CheckListInfo.class));
                    recordListAdapter.notifyDataSetChanged();
                }
                pr_record.finishLoading(false);

                break;
            case Const.NET_DELETE_GOV_CHECK_LIST_CODE:
                recordDates.remove(mDeteleItem);
                recordListAdapter.notifyDataSetChanged();
                DialogUtil.showMsgDialog(this, "删除成功", false, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.title_right:
                Intent intent = new Intent(ZfCheckRecordListActivity.this,
                        NewZfCheckAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", companyId);
                bundle.putBoolean("num", isReview);
                bundle.putSerializable("checklist", null);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }

    }

    private class MyListAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<CheckListInfo> mDatas;

        public MyListAdapter(Context context, ArrayList<CheckListInfo> mDatas) {
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
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater
                        .inflate(
                                R.layout.list_view_string_and_arrow_and_date_item,
                                null);
                ViewHodler mHodler = new ViewHodler();
                mHodler.mTextView1 = (TextView) convertView
                        .findViewById(R.id.text1);
                mHodler.mTextView2 = (TextView) convertView
                        .findViewById(R.id.text2);
                mHodler.mTextView3 = (TextView) convertView
                        .findViewById(R.id.text3);
                mHodler.mTextView4 = (TextView) convertView
                        .findViewById(R.id.tv_review_times);
                convertView.setTag(mHodler);
            }
            ViewHodler mVH = (ViewHodler) convertView.getTag();
            mVH.mTextView1.setText("检查场所:"
                    + recordDates.get(position).getCheckGround());
            mVH.mTextView2.setText("未整改隐患数量("
                    + recordDates.get(position).getWzgNum() + ")个");
            mVH.mTextView3.setText("检查日期："
                    + recordDates.get(position).getCheckTimeBegin());
            mVH.mTextView4.setText("");
            //bug 21567 说去除
		/*	if (recordDates.get(position).getPunishState()) {  //已处罚 红色

				mVH.mTextView4.setTextColor(Color.rgb(220, 20, 60));
			} else {
				mVH.mTextView4.setText("未处罚");
				mVH.mTextView4.setTextColor(Color.rgb(0, 0, 0));//未处罚 黑色
			}*/
            return convertView;
        }

        private class ViewHodler {
            TextView mTextView1;
            TextView mTextView2;
            TextView mTextView3;
            TextView mTextView4;
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
        recordDates.clear();
        recordListAdapter.notifyDataSetChanged();
        mLoading.show();
        loadingCheckListInfos(strPunState, checkGround);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals(ACTION_UPDATE)){
            updata();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }


}
