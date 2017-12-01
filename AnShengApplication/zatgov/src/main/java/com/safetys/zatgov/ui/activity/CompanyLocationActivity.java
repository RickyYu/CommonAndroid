package com.safetys.zatgov.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.CompanyVo;
import com.safetys.zatgov.config.SecondTypeEnum;
import com.safetys.zatgov.config.ThirdTypeEnum;
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
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class CompanyLocationActivity  extends BaseActivity implements
        View.OnClickListener, onNetCallback {


    private View mBtn_back;
    private PullToRefresh mListView;
    private MyListAdapter mAdapter;
    private static final int PAGE_SIZE = 10;// 页大小
    private LoadingDialogUtil mLoading;
    private int mCurrentPage = 0;// 当前页
    private int totalCount=0;;// 总数
    String companyName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_list);
        initView();
    }

    private void initView() {
        setHeadTitle("企业信息列表");
        mLoading = new LoadingDialogUtil(this);
        loadingDatas(companyName);
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);
        mListView = (PullToRefresh) findViewById(R.id.listview);
        mListView.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(CompanyLocationActivity.this,
                            "没有更多的数据了。");
                    mListView.finishLoading(false);
                } else {
                    loadingDatas(companyName);
                }
            }
        });

        mdatas = new ArrayList<CompanyVo>();
        mAdapter = new MyListAdapter(this, mdatas);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private Intent intent;
            private String jd;
            private String wd;

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                jd = mdatas.get(position).getX();
                wd = mdatas.get(position).getY();
                String companyname = mdatas.get(position).getCompanyName();
                Intent intent =new Intent();
                intent.putExtra("x", jd);
                intent.putExtra("y", wd);
                intent.putExtra("companyName", companyname);

                setResult(4444, intent);


                finish();
            }
        });
        SearchBar searchBar=(SearchBar) findViewById(R.id.search_bar);
        searchBar.setOnSearchListener(new SearchBar.onSearchListener() {

            @Override
            public void onSearchButtonClick(String searchStr) {
                loadingDatas(searchStr);

            }
        });
    }



    private void loadingDatas(String companyName) {
        // mLoading.show();
        if (companyName!=null) {
            mCurrentPage = 0;
            totalCount = 0;
            mdatas.clear();
            mAdapter.notifyDataSetChanged();
        }

        mLoading.show();
        HttpRequestHelper.getInstance().getCompanyLocationListInfo(this, companyName,totalCount,
                mCurrentPage, 1, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;


            default:
                break;
        }
    }

    private class MyListAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<CompanyVo> mdatas;

        public MyListAdapter(Context context, ArrayList<CompanyVo> mdatas) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mdatas = mdatas;
        }

        @Override
        public int getCount() {
      
            return mdatas.size();
        }

        @Override
        public Object getItem(int position) {
      
            return mdatas.get(position);
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
                ViewHodler mHodler = new ViewHodler();
                mHodler.mTextView1 = (TextView) convertView
                        .findViewById(R.id.text1);
                mHodler.mTextView2 = (TextView) convertView
                        .findViewById(R.id.text2);
                convertView.setTag(mHodler);
            }
            ViewHodler mVH = (ViewHodler) convertView.getTag();
            CompanyVo companyListInfo = mdatas.get(position);
            mVH.mTextView1.setText(companyListInfo.getCompanyName());
            mVH.mTextView2.setText("负责人：" + companyListInfo.getFdDelegate()
                    + "  所属区域：" + "湖州市"
                    + SecondTypeEnum.getValue(companyListInfo.getSecondArea())
                    + ThirdTypeEnum.getValue(companyListInfo.getThirdArea()));
            return convertView;
        }

        private class ViewHodler {
            TextView mTextView1;
            TextView mTextView2;
        }

    }

    private ArrayList<CompanyVo> mdatas;

    @Override
    public void onError(String errorMsg) {
        mLoading.dismiss();
        DialogUtil.showMsgDialog(CompanyLocationActivity.this, errorMsg, true,
                null);
    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();
        switch (requestCode) {
            case 1:
                if (mJsonResult.getJson() == null||mJsonResult.getJson().toString().equals("[]")) {
                    if (mdatas.size() == 0) {
                        DialogUtil.showMsgDialog(CompanyLocationActivity.this,
                                "没有找到相关数据。", false, null);
                    } else {
                        DialogUtil.showMsgDialog(CompanyLocationActivity.this,
                                "没有更多的数据。", false, null);
                    }
                } else {

                    mCurrentPage = mCurrentPage + PAGE_SIZE;
                    totalCount = mJsonResult.getTotalCount();
                    List<CompanyVo> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), CompanyVo.class);
                    if (list == null || list.size() == 0) {

                    } else {

                        mdatas.addAll(list);
                    }
                }

                mAdapter.notifyDataSetChanged();
                mListView.finishLoading(false);
                break;
            default:
                break;
        }
    }

}
