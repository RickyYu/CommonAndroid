package com.safetys.zatgov.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.safetys.zatgov.adapter.ZFReviewCompanyListAdapter;
import com.safetys.zatgov.bean.Punishment;
import com.safetys.zatgov.bean.ReviewInfoGov;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.PunishmentTypeEnum;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class NewZfPunListActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback, SearchBar.onSearchListener {
    public static final String ACTION_UPDATE_LIST = "cn.saftys.NewZfPunListActivity.update";
    protected static final int LOADING_MORE2 = 1;
    private View mBtn_back;
    private View mBtn_Trouble_dfc;// 未处罚
    private View mBtn_Trouble_yfc;// 已处罚
    private View currentButton;
    private PullToRefresh mList_Troubles_dfc;// 未处罚
    private PullToRefresh mList_Troubles_yfc;// 已处罚
    private SearchBar mSearchBar;
    private static final int PAGE_SIZE = 10;// 页大小
    private int mCurrentPage = 0;// 当前页
    private int totalCount = 0;// 总数
    private static final int PAGE_SIZE2 = 10;// 页大小
    private int mCurrentPage2 = 0;// 当前页
    private int totalCount2 = 0;// 总数
    private LoadingDialogUtil mLoading;
    private String code;
    private MyBroadcastReceiver mReceiver;
    private ArrayList<ReviewInfoGov> unPunDates;
    private ArrayList<Punishment> punDates;// 处罚数据
    private ZFReviewCompanyListAdapter unPunAdapter;
    private PunListAdapter punAdapter;
    private String id2;
    private TextView title_right;
    private Intent intent;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zf_punlish_list);
        initView();
        mLoading.show();
        loadingUnPunListDatas();
        // 注册通知刷新界面的广播
        mReceiver = new MyBroadcastReceiver();
        IntentFilter mFilter = new IntentFilter(ACTION_UPDATE_LIST);
        registerReceiver(mReceiver, mFilter);
    }

    private void initView() {
        setHeadTitle("行政处罚");
        search = (EditText) findViewById(R.id.et_search_text);
        search.setHint("请输入企业名称");
        mLoading = new LoadingDialogUtil(this);
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);
        mList_Troubles_dfc = (PullToRefresh) findViewById(R.id.list_troubles_a);
        mList_Troubles_yfc = (PullToRefresh) findViewById(R.id.list_troubles_b);
        mList_Troubles_dfc.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(NewZfPunListActivity.this,
                            "没有更多的数据了。");
                    mList_Troubles_dfc.finishLoading(false);
                } else {
                    // loadingCheckListInfos();
                    loadingUnPunListDatas();
                }
            }

        });

        mList_Troubles_yfc.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage2 > totalCount2) {
                    ToastUtils.showMessage(NewZfPunListActivity.this,
                            "没有更多的数据了。");
                    mList_Troubles_yfc.finishLoading(false);
                } else {
                    // loadingCheckListInfos2();
                    loadingPunListDatas();
                }
            }
        });

        mList_Troubles_dfc.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent mIntent = new Intent(NewZfPunListActivity.this,
                        NewPunishmentAddActivity.class);
                mIntent.putExtra("ID", unPunDates.get(position).getId());
                mIntent.putExtra("checkId", unPunDates.get(position)
                        .getJcjlId());
                mIntent.putExtra("companyId", unPunDates.get(position)
                        .getCompanyId());
                startActivity(mIntent);
            }

        });
        mList_Troubles_yfc.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent mIntent = new Intent(NewZfPunListActivity.this,
                        NewPunishmentShowActivity.class);
                mIntent.putExtra("id", punDates.get(position).getId());
                mIntent.putExtra("checkId", punDates.get(position)
                        .getPunishmentId());
                // mIntent.putExtra("companyId",
                // punDates.get(position).getCompanyId());
                startActivity(mIntent);
            }

        });

        mBtn_Trouble_dfc = findViewById(R.id.btn_trouble_review);
        mBtn_Trouble_yfc = findViewById(R.id.btn_trouble_reviewed);
        mBtn_Trouble_dfc.setOnClickListener(this);
        mBtn_Trouble_yfc.setOnClickListener(this);
        setButton(mBtn_Trouble_dfc);

        unPunDates = new ArrayList<ReviewInfoGov>();// 未处罚数据
        unPunAdapter = new ZFReviewCompanyListAdapter(this, unPunDates);
        mList_Troubles_dfc.setAdapter(unPunAdapter);
        punDates = new ArrayList<Punishment>();// 已处罚数据
        punAdapter = new PunListAdapter(this, punDates);
        mList_Troubles_yfc.setAdapter(punAdapter);
        mSearchBar = (SearchBar) findViewById(R.id.search_bar);
        mSearchBar.setOnSearchListener(this);
    }

    @Override
    public void onSearchButtonClick(String searchStr) {
        // 未处罚
        if (mList_Troubles_dfc.getVisibility() == View.VISIBLE) {
            mCurrentPage = 0;
            totalCount = 0;
            unPunDates.clear();
            unPunAdapter.notifyDataSetChanged();
            // loadingCheckListInfos();
            mLoading.show();
            loadingUnPunListDatas();
        } else {// 已处罚
            mCurrentPage2 = 0;
            totalCount2 = 0;
            punDates.clear();
            punAdapter.notifyDataSetChanged();
            mLoading.show();
            loadingPunListDatas();
        }
    }

    private void start(String id, String id2, int i) {
        if (i == 1) {
            intent = new Intent(NewZfPunListActivity.this,
                    NewPunishmentShowActivity.class);
        } else {
            intent = new Intent(NewZfPunListActivity.this,
                    NewPunishmentAddActivity.class);
        }
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("id2", id2);
        intent.putExtras(bundle);
        startActivity(intent);
    }

/*    private void loadingCheckListInfos() {
        code = "false";
        HttpRequestHelper.getInstance().getCheckList(this, totalCount, id2,
                code, mCurrentPage, mSearchBar.getSearchData(),
                Const.NET_GET_GOV_CHECK_LIST_CODE, this);

    }*/
    /**
     * 获取未处罚信息列表
     */
    private void loadingUnPunListDatas() {

        HttpRequestHelper.getInstance().getUnPunishmentList(this, mCurrentPage,
                totalCount, mSearchBar.getSearchData(),
                Const.NET_GET_GOV_UNPUN_LIST_CODE, this);

    }

    /**
     * 获取处罚信息列表
     */
    private void loadingPunListDatas() {
        HttpRequestHelper.getInstance().getPunishmentList(this, mCurrentPage2,
                totalCount2, mSearchBar.getSearchData(),
                Const.NET_GET_GOV_PUN_LIST_CODE, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_trouble_review:
                setButton(v);
//			mCurrentPage = 0;
//			totalCount = 0;
//			unPunDates.clear();
                search.setText("");
//			loadingUnPunListDatas();
                break;
            case R.id.btn_trouble_reviewed:
                setButton(v);
                mCurrentPage2= 0;
                totalCount2 = 0;
                punDates.clear();
                search.setText("");
                mLoading.show();
                loadingPunListDatas();
                break;
            case R.id.title_right:
                Intent intent = new Intent(NewZfPunListActivity.this,
                        NewZfCheckAddActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", id2);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    private void setButton(View v) {
        switch (v.getId()) {
            case R.id.btn_trouble_review:
                mList_Troubles_dfc.setVisibility(View.VISIBLE);
                mList_Troubles_yfc.setVisibility(View.GONE);
                break;
            case R.id.btn_trouble_reviewed:

                mList_Troubles_dfc.setVisibility(View.GONE);
                mList_Troubles_yfc.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
            ((TextView) ((ViewGroup) currentButton).getChildAt(0))
                    .setEnabled(true);
        }
        v.setEnabled(false);
        ((TextView) ((ViewGroup) v).getChildAt(0)).setEnabled(false);
        currentButton = v;
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
            case Const.NET_GET_GOV_UNPUN_LIST_CODE:
                if (mJsonResult.getJson() == null) {
                    if (unPunDates.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(this, "没有更多的数据。", false, null);
                    }
                } else {

                    mCurrentPage = mCurrentPage + 10;
                    totalCount = mJsonResult.getTotalCount();
                    List<ReviewInfoGov> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), ReviewInfoGov.class);
                    if (list == null || list.size() == 0) {
                    } else {
                        unPunDates.addAll(list);
                    }
                }

                unPunAdapter.notifyDataSetChanged();
                mList_Troubles_dfc.finishLoading(false);

                break;
            case Const.NET_GET_GOV_PUN_LIST_CODE:
                if (mJsonResult.getJson() == null) {
                    if (punDates.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(this, "没有更多的数据。", false, null);
                    }
                } else {

                    mCurrentPage2 = mCurrentPage2 + 10;
                    totalCount2 = mJsonResult.getTotalCount();
                    List<Punishment> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), Punishment.class);
                    if (list == null || list.size() == 0) {
                    } else {
                        punDates.addAll(list);
                    }
                }

                punAdapter.notifyDataSetChanged();
                mList_Troubles_yfc.finishLoading(false);
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
        mCurrentPage2 = 0;
        totalCount2 = 0;
        unPunDates.clear();
        punDates.clear();
        unPunAdapter.notifyDataSetChanged();
        punAdapter.notifyDataSetChanged();
        // loadingCheckListInfos();
        mLoading.show();
        loadingUnPunListDatas();
        loadingPunListDatas();

    }

    /**
     * @author sjw 刷新界面广播
     */
    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
     
            if (intent.getAction().equals(ACTION_UPDATE_LIST)) {
                updata();

            }
        }

    }

    private class PunListAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<Punishment> mDatas;

        public PunListAdapter(Context context, ArrayList<Punishment> mdatas) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mDatas = mdatas;
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
                convertView.setTag(mHodler);
            }
            ViewHodler mVH = (ViewHodler) convertView.getTag();
            mVH.mTextView2.setText("处罚类型:"
                    + PunishmentTypeEnum.getValue(mDatas.get(position)
                    .getPunishmentType()));
            mVH.mTextView1.setText(mDatas.get(position).getCompanyName());
            mVH.mTextView3.setText("处罚日期:"
                    + mDatas.get(position).getPunishmentTime());

            return convertView;
        }

    }

    private class ViewHodler {
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
    }
    @Override
    protected void onDestroy() {
 
        super.onDestroy();
        if(mReceiver!=null){
            unregisterReceiver(mReceiver);
        }
    }
}