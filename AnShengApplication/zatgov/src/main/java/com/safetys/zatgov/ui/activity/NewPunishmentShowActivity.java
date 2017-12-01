package com.safetys.zatgov.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.safetys.zatgov.R;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.Punishment;
import com.safetys.zatgov.config.PunishmentTypeEnum;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;
import com.safetys.zatgov.utils.StringUtil;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class NewPunishmentShowActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback {

    private TextView mTv_qymc;// 企业名称
    private TextView mTv_qydz;// 企业地址
    private TextView mTv_fddbr;// 法定代表人
    private TextView mTv_cfdw;// 处罚单位
    private TextView mTv_cfyy;// 处罚原因
    private TextView mTv_cflx;// 处罚类型
    private TextView mTv_cfsj;// 处罚时间
    private TextView mTv_cfnr;// 处罚内容
    private TextView mTv_bz;// 备注
    private PullToRefresh mListView;
    private PullToRefresh list_yh;
    private View history;
    private View checkfirst;
    private View ll_check;
    private boolean isCheck = false;
    private boolean isHis = false;
    private boolean isYh = false;
    private ImageView arrow;
    private ArrayList<Data> mdatas;
    private ImageView arrow2;
    private ImageView arrow3;
    private MyListAdapter mAdapter;
    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_lxr;
    private TextView tv_jctime;
    private TextView ed_checkplace;
    private TextView ed_phone;
    private TextView ed_people;
    private TextView ed_checkpeople;
    private TextView ed_law;//检查单位
    private TextView ed_now;//现场检查记录
    private TextView tv_zgtime;
    private LoadingDialogUtil mLoading;
    private View yh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punishment_show);
        initView();
        initData();
    }

    private void initData() {
        mLoading.show();
        Intent mIntent = getIntent();
        int id = mIntent.getIntExtra("id", -1);
        String checkId = mIntent.getStringExtra("checkId");
        HttpRequestHelper.getInstance().getPunishmentInfo(this,id, 0, this);
    }

    private void initView() {
        setHeadTitle("处罚详情");
        findViewById(R.id.btn_back).setOnClickListener(this);

        mTv_qymc = (TextView) findViewById(R.id.text_qymc);
        mTv_qydz = (TextView) findViewById(R.id.text_qydz);
        mTv_fddbr = (TextView) findViewById(R.id.text_fddbr);
        mTv_cfdw = (TextView) findViewById(R.id.text_cfdw);
        mTv_cfyy = (TextView) findViewById(R.id.text_cfyy);
        mTv_cflx = (TextView) findViewById(R.id.text_cflx);
        mTv_cfsj = (TextView) findViewById(R.id.text_cfsj);
        mTv_cfnr = (TextView) findViewById(R.id.text_cfnr);
        mTv_bz = (TextView) findViewById(R.id.text_bz);
        mLoading = new LoadingDialogUtil(this);

        mListView = (PullToRefresh) findViewById(R.id.list_fc);
        list_yh = (PullToRefresh) findViewById(R.id.list_yh);
        checkfirst = findViewById(R.id.checkfirst);// 初查记录
        // 初查记录里不能修改
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_lxr = (TextView) findViewById(R.id.tv_lxr);
        tv_jctime = (TextView) findViewById(R.id.tv_jctime);
        ed_checkplace = (TextView) findViewById(R.id.ed_checkplace);
        ed_phone = (TextView) findViewById(R.id.ed_phone);
        ed_people = (TextView) findViewById(R.id.ed_people);
        ed_checkpeople = (TextView) findViewById(R.id.ed_checkpeople);
        ed_law = (TextView) findViewById(R.id.ed_law);
        ed_now = (TextView) findViewById(R.id.ed_now);

        history = findViewById(R.id.history);// 历史记录
        yh = findViewById(R.id.yh);
        checkfirst.setOnClickListener(this);
        history.setOnClickListener(this);
        yh.setOnClickListener(this);
        ll_check = findViewById(R.id.ll_check);
        arrow = (ImageView) findViewById(R.id.arrow);
        arrow2 = (ImageView) findViewById(R.id.arrow2);
        arrow3 = (ImageView) findViewById(R.id.arrow3);

        Data data = new Data("中国石油", "2016-09-08", "5", "2");
        Data data2 = new Data("中国科技", "2016-09-18", "1", "10");
        Data data3 = new Data("中国石化", "2016-09-28", "3", "3");

        mdatas = new ArrayList<Data>();
        mdatas.add(data);
        mdatas.add(data2);
        mdatas.add(data3);
        mAdapter = new MyListAdapter(this, mdatas);
        mListView.setAdapter(mAdapter);
        list_yh.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.yh:
                if (!isYh) {
                    isYh = true;
                    list_yh.setVisibility(View.VISIBLE);
                    arrow3.setImageResource(R.mipmap.arrow_down);
                } else {
                    isYh = false;
                    list_yh.setVisibility(View.GONE);
                    arrow3.setImageResource(R.mipmap.arrow_up);
                }

                break;
            case R.id.checkfirst:
                if (!isCheck) {
                    isCheck = true;
                    ll_check.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.mipmap.arrow_down);
                } else {
                    isCheck = false;
                    ll_check.setVisibility(View.GONE);
                    arrow.setImageResource(R.mipmap.arrow_up);
                }

                break;
            case R.id.history:
                if (!isHis) {
                    isHis = true;
                    mListView.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.mipmap.arrow_down);
                } else {
                    isHis = false;
                    mListView.setVisibility(View.GONE);
                    arrow2.setImageResource(R.mipmap.arrow_up);
                }
                break;
            default:
                break;
        }
    }

    private class Data {
        String name;
        String data;
        String des;
        String fc;

        public Data(String name, String data, String des, String fc) {
            super();
            this.name = name;
            this.data = data;
            this.des = des;
            this.fc = fc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getFc() {
            return fc;
        }

        public void setFc(String fc) {
            this.fc = fc;
        }

    }

    private class MyListAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<Data> mDatas;

        public MyListAdapter(Context context, ArrayList<Data> mdatas) {
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
     
            return mdatas.get(position);
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
                                R.layout.list_view_string_three_and_arrow_and_date_item,
                                null);
                ViewHodler mHodler = new ViewHodler();
                mHodler.mTextView1 = (TextView) convertView
                        .findViewById(R.id.text1);
                mHodler.mTextView2 = (TextView) convertView
                        .findViewById(R.id.text2);
                mHodler.mTextView3 = (TextView) convertView
                        .findViewById(R.id.text3);
                mHodler.mTextView4 = (TextView) convertView
                        .findViewById(R.id.text4);
                convertView.setTag(mHodler);
            }
            ViewHodler mVH = (ViewHodler) convertView.getTag();
            mVH.mTextView1.setText(mDatas.get(position).getName());
            mVH.mTextView2.setText("未整改隐患数量" + mDatas.get(position).getDes()
                    + "个");
            mVH.mTextView3.setText("责令整改日期：" + mDatas.get(position).getData());
            mVH.mTextView4.setText("复查次数：" + mDatas.get(position).getFc());

            return convertView;
        }
    }

    private class ViewHodler {
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        TextView mTextView4;
    }

    @Override
    public void onError(String errorMsg) {
        mLoading.dismiss();
        DialogUtil.showMsgDialog(this, errorMsg, true, null);
    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();
        if (mJsonResult.getEntity() != null
                && !mJsonResult.getEntity().equals("{}")) {
            Punishment mPunishment = JSON.parseObject(mJsonResult.getEntity(),
                    Punishment.class);
            mTv_qymc.setText(StringUtil.nvl(mPunishment.getCompanyName()));
            mTv_qydz.setText(StringUtil.nvl(mPunishment.getAddress()));
            mTv_fddbr.setText(StringUtil.nvl(mPunishment.getFdDelegate()));
            mTv_cfdw.setText(StringUtil.nvl(mPunishment.getPunishmentUnit()));
            mTv_cfyy.setText(StringUtil.nvl(mPunishment.getPunishmentCause()));
            mTv_cflx.setText(PunishmentTypeEnum.getValue(mPunishment
                    .getPunishmentType()));
            mTv_cfsj.setText(StringUtil.nvl(mPunishment.getPunishmentTime()));
            mTv_cfnr.setText(StringUtil.nvl(mPunishment.getContent()));
            mTv_bz.setText(StringUtil.nvl(mPunishment.getRemark()));
        } else {
            DialogUtil.showMsgDialog(this, "未查询到数据。", false, null);
        }
    }
}

