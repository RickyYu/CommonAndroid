package com.safetys.zatgov.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.MyGridAdapter;
import com.safetys.zatgov.base.BaseActivity;
import com.safetys.zatgov.bean.CompanyInfo;
import com.safetys.zatgov.bean.CompanyVo;
import com.safetys.zatgov.entity.GridBtnData;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.BasePopupWindow;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;
import com.safetys.zatgov.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class MainWanggyActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback {

    private View rll_check, rll_checkagain, rll_checklist, rll_compangy;
    private View lly_laws, lly_technology, lly_msds, lly_news, iv_makelist,
            iv_checknext, btn_checknext;
    private View currentButton;
    private LoadingDialogUtil mLoading;// 正在加载框
    private boolean isShow = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mLoading.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    private TextView num_checkCompany;
    private TextView num_checkagain;
    private TextView num_sb;
    private TextView num_zg;
    private TextView num1;
    private TextView num2;
    private TextView num3;
    private TextView companyname1;
    private TextView companyname2;
    private TextView companyname3;
    private ImageView iv_right;
    private TextView mTitle;
    private View ll_company;
    private View ll_company3;
    private View ll_company2;
    private View ll_company1;

    private View lly_uncheck_cpy;
    private View lly_unreview_cpy;
    private View lly_uncheck_hidden;
    private View lly_unreported;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainwgy);
        initView();
        mLoading = new LoadingDialogUtil(this);
        // 更新数据
        updateData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private GridView mGridview;
    private MyGridAdapter mAdapter;

    private void initView() {
        View btn_back = findViewById(R.id.btn_back);
        btn_back.setVisibility(View.GONE);
        mTitle = (TextView) findViewById(R.id.title);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        mTitle.setText("智安通");
        ArrayList<GridBtnData> mDatas = new ArrayList<GridBtnData>();
        mDatas.add(new GridBtnData(R.drawable.wgy_btn_jcb_selector,"我的检查表", 0));
        mDatas.add(new GridBtnData(R.drawable.check_select, "监督检查", 0));
        mDatas.add(new GridBtnData(R.drawable.checkagain_select, "政府复查", 0));
        mDatas.add(new GridBtnData(R.drawable.checklist_select, "历史记录", 0));
        mDatas.add(new GridBtnData(R.drawable.checkcompany_select, "企业管理", 0));
        mDatas.add(new GridBtnData(R.drawable.wgy_btn_hidden_selector, "企业隐患", 0));
        mDatas.add(new GridBtnData(R.drawable.wgy_btn_map_selector, "地图导航", 0));
        mGridview = (GridView) findViewById(R.id.gridview);
        mGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new MyGridAdapter(this, mDatas);
        mGridview.setAdapter(mAdapter);

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        // 检查表
                        Intent intent2 = new Intent(getApplicationContext(),
                                WgyCheckListActivity.class);
                        intent2.putExtra("isChecklist", "0");
                        startActivity(intent2);
                        break;
                    case 1:
                        // 监督检查
                        Intent mIntent = new Intent();
                        mIntent.setClass(getApplicationContext(),
                                EnterpriseListActivity.class);
                        mIntent.putExtra("skipType", EnterpriseListActivity.SKIP_SUPERVISE_CHECKT);
                        mIntent.putExtra("source", "checkTable");
                        startActivity(mIntent);
                        break;
                    case 2:
                        // 我要复查
                        Intent mIntent1 = new Intent(getApplicationContext(),
                                ZfReviewCompanyListActivity.class);
                        mIntent1.putExtra("name", "");
                        startActivity(mIntent1);
                        break;
                    case 3:
                        // 历史记录
                        Intent mIntent0 = new Intent(MainWanggyActivity.this,
                                EnterpriseListActivity.class);
                        mIntent0.putExtra(EnterpriseListActivity.SKIP_TYPR,
                                EnterpriseListActivity.SKIP_CHECK_RECORD_LIST_EXPANDABLE);
                        startActivity(mIntent0);
                        break;
                    case 4:
                        // 企业管理
                        Intent mIntent3 = new Intent(getApplicationContext(),
                                EnterpriseListActivity.class);
                        mIntent3.putExtra(EnterpriseListActivity.SKIP_TYPR,
                                EnterpriseListActivity.SKIP_COMPANY_INFO);
                        startActivity(mIntent3);
                        break;
                    case 5:
                        // 企业隐患
                        Intent mIntent7 = new Intent(getApplicationContext(),
                                EnterpriseListActivity.class);
                        mIntent7.putExtra(EnterpriseListActivity.SKIP_TYPR,
                                EnterpriseListActivity.SKIP_COMPANY_HIDDEN_INFO);
                        startActivity(mIntent7);
                        break;
                    case 6:
                        //地图导航
                        Intent mIntent6 = new Intent(getApplicationContext(),
                                MapActivity.class);
                        startActivity(mIntent6);
                        break;
                    default:
                        break;
                }

            }
        });

        iv_right.setBackgroundResource(R.drawable.wgy_setting_select);
        iv_right.setOnClickListener(this);
        iv_right.setVisibility(View.VISIBLE);
        // 我要检查
        rll_check = findViewById(R.id.rll_check);
        rll_check.setOnClickListener(this);
        btn_checknext = findViewById(R.id.in_check);
        // 检查表
        iv_makelist = findViewById(R.id.iv_makelist);
        iv_makelist.setOnClickListener(this);
        // 监督检查
        iv_checknext = findViewById(R.id.iv_checknext);
        iv_checknext.setOnClickListener(this);
        // 我要复查
        rll_checkagain = findViewById(R.id.rll_checkagain);
        rll_checkagain.setOnClickListener(this);
        // 检查记录
        rll_checklist = findViewById(R.id.rll_checklist);
        rll_checklist.setOnClickListener(this);
        // 企业管理
        rll_compangy = findViewById(R.id.rll_compangy);
        rll_compangy.setOnClickListener(this);
        // 法律法规
        lly_laws = findViewById(R.id.lly_laws);
        lly_laws.setOnClickListener(this);
        // 技术标准
        lly_technology = findViewById(R.id.lly_technology);
        lly_technology.setOnClickListener(this);
        // MSDS
        lly_msds = findViewById(R.id.lly_msds);
        lly_msds.setOnClickListener(this);
        // 安监要闻
        lly_news = findViewById(R.id.lly_news);
        lly_news.setOnClickListener(this);
        num_checkCompany = (TextView) findViewById(R.id.num_checkCompany);
        num_checkagain = (TextView) findViewById(R.id.num_checkagain);
        num_sb = (TextView) findViewById(R.id.num_sb);
        num_zg = (TextView) findViewById(R.id.num_zg);
        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        num3 = (TextView) findViewById(R.id.num3);
        companyname1 = (TextView) findViewById(R.id.companyname1);
        companyname2 = (TextView) findViewById(R.id.companyname2);
        companyname3 = (TextView) findViewById(R.id.companyname3);
        ll_company = findViewById(R.id.ll_company);
        ll_company1 = findViewById(R.id.ll_company1);
        ll_company2 = findViewById(R.id.ll_company2);
        ll_company3 = findViewById(R.id.ll_company3);

        lly_uncheck_cpy = findViewById(R.id.lly_uncheck_cpy);
        lly_unreview_cpy = findViewById(R.id.lly_unreview_cpy);
        lly_uncheck_hidden = findViewById(R.id.lly_uncheck_hidden);
        lly_unreported = findViewById(R.id.lly_unreported);

        lly_uncheck_cpy.setOnClickListener(this);
        lly_unreview_cpy.setOnClickListener(this);
        lly_uncheck_hidden.setOnClickListener(this);
        lly_unreported.setOnClickListener(this);
    }

    private void showPopWindow() {
        basePopupWindow = this.getInstance();
        basePopupWindow.showAsDropDown(rll_check);

    }

    private BasePopupWindow basePopupWindow = null;

    private BasePopupWindow getInstance() {
        if (basePopupWindow == null) {
            basePopupWindow = new BasePopupWindow(MainWanggyActivity.this);
            basePopupWindow.setOutsideTouchable(true);
            basePopupWindow.setFocusable(true);
            View view = LayoutInflater.from(MainWanggyActivity.this).inflate(
                    R.layout.wgy_check, null);
            iv_makelist = view.findViewById(R.id.iv_makelist);
            iv_checknext = view.findViewById(R.id.iv_checknext);
            iv_checknext.setOnClickListener(this);
            iv_makelist.setOnClickListener(this);
            basePopupWindow.setContentView(view);

        }
        return basePopupWindow;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right:
                Intent itent = new Intent(this, SettingActivity.class);
                itent.putExtra("isGrid", "yes");
                startActivity(itent);
                break;
            case R.id.rll_check:
                showPopWindow();
                break;
            case R.id.iv_makelist:
                // 检查表
                Intent intent2 = new Intent(this, WgyCheckListActivity.class);
                intent2.putExtra("isChecklist", "0");
                startActivity(intent2);
                break;
            case R.id.iv_checknext:
                // 监督检查
                Intent intent3 = new Intent(this, WgyCheckListActivity.class);
                intent3.putExtra("isChecklist", "1");
                startActivity(intent3);
                break;
            case R.id.rll_checkagain:
                // 复查
                Intent mIntent1 = new Intent(this,
                        ZfReviewCompanyListActivity.class);
                mIntent1.putExtra("name", "");
                startActivity(mIntent1);
                break;
            case R.id.rll_checklist:
                // 检查记录
                Intent mIntent0 = new Intent(MainWanggyActivity.this,
                        EnterpriseListActivity.class);
                mIntent0.putExtra(EnterpriseListActivity.SKIP_TYPR,
                        EnterpriseListActivity.SKIP_CHECK_RECORD_LIST);
                startActivity(mIntent0);
                break;
            case R.id.rll_compangy:
                // 企业管理
                Intent mIntent3 = new Intent(this, EnterpriseListActivity.class);
                mIntent3.putExtra(EnterpriseListActivity.SKIP_TYPR,
                        EnterpriseListActivity.SKIP_COMPANY_INFO);
                startActivity(mIntent3);
                break;
            case R.id.lly_laws:
                // 法律法规
                startActivity(new Intent(this, LawRegulationsActivity.class));
                break;
            case R.id.lly_technology:
                // 技术标准
                startActivity(new Intent(this, TechnicalStandardActivity.class));
                break;
            case R.id.lly_msds:
                // msds
                startActivity(new Intent(this, MsdsActivity.class));
                break;
            case R.id.lly_news:
                Intent mIntent4 = new Intent(this, SafetyNewsActivity.class);
                startActivity(mIntent4);
                break;
            case R.id.lly_uncheck_cpy:
			/*
			 * Intent mIntent6 = new Intent(this, EnterpriseListActivity.class);
			 * mIntent6.putExtra(EnterpriseListActivity.SKIP_TYPR,
			 * EnterpriseListActivity.SKIP_ENTERPRISE_INFO);
			 * mIntent6.putExtra("SeekType",
			 * SeekTypeEnum.UNCHECK.getTypeCode()); startActivity(mIntent6);
			 */
                break;
            case R.id.lly_unreview_cpy:
			/*
			 * Intent mIntent7 = new Intent(this, EnterpriseListActivity.class);
			 * mIntent7.putExtra(EnterpriseListActivity.SKIP_TYPR,
			 * EnterpriseListActivity.SKIP_ENTERPRISE_INFO);
			 * mIntent7.putExtra("SeekType",
			 * SeekTypeEnum.UNREVIEW.getTypeCode()); startActivity(mIntent7);
			 */
                break;
            case R.id.lly_uncheck_hidden:
			/*
			 * Intent mIntent8 = new Intent(this, EnterpriseListActivity.class);
			 * mIntent8.putExtra(EnterpriseListActivity.SKIP_TYPR,
			 * EnterpriseListActivity.SKIP_ENTERPRISE_INFO);
			 * mIntent8.putExtra("SeekType",
			 * SeekTypeEnum.UNMODIFY.getTypeCode()); startActivity(mIntent8);
			 */
                break;
            case R.id.lly_unreported:
			/*
			 * Intent mIntent9 = new Intent(this, EnterpriseListActivity.class);
			 * mIntent9.putExtra(EnterpriseListActivity.SKIP_TYPR,
			 * EnterpriseListActivity.SKIP_ENTERPRISE_INFO);
			 * mIntent9.putExtra("SeekType",
			 * SeekTypeEnum.UNREPORTED.getTypeCode());
			 *
			 * startActivity(mIntent9);
			 */
                break;
            default:
                break;
        }
    }

    private void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            (((ViewGroup) currentButton).getChildAt(0)).setEnabled(true);
            (((ViewGroup) currentButton).getChildAt(1)).setEnabled(true);
        }
        (((ViewGroup) v).getChildAt(0)).setEnabled(false);
        (((ViewGroup) v).getChildAt(1)).setEnabled(false);
        currentButton = v;
    }
    public static final int CODE_GET_DATA = 0;
    /**
     * 更新数据 如行业类型
     */
    private void updateData() {
        mLoading.show();
        HttpRequestHelper.getInstance().getWgy(this,CODE_GET_DATA, this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            // 具体的操作代码
            new AlertDialog.Builder(this)

                    .setTitle("确定退出程序?")

                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }

                    })

                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(getApplicationContext(),
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }).show();

            return true;
        }
        return super.dispatchKeyEvent(event);
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
            case CODE_GET_DATA:
                if (mJsonResult.getEntity() != null
                        && !mJsonResult.getEntity().equals("{}")) {
                    CompanyInfo mCompanyInfo = JSON.parseObject(
                            mJsonResult.getEntity(), CompanyInfo.class);
                    int num = Integer.parseInt(mCompanyInfo.getUnCheckNum());
                    num_checkCompany.setText((num > 20 ? 20 : num) + "");
                    num_checkagain.setText(StringUtil.nvl(mCompanyInfo
                            .getUnCallbackNum()));
                    num_sb.setText(StringUtil.nvl(mCompanyInfo
                            .getUnCleanDangerNum()));
                    num_zg.setText(StringUtil.nvl(mCompanyInfo.getUnReportNum()));
                    List<CompanyVo> list = mCompanyInfo.getCompanys();
                    Log.e("asd", "siza===" + list.size());
                    if (list.size() == 1) {
                        ll_company2.setVisibility(View.GONE);
                        ll_company3.setVisibility(View.GONE);
                        companyname1.setText(StringUtil.nvl(list.get(0)
                                .getCompanyName() + "（个）"));
                        num1.setText(StringUtil.nvl(list.get(0).getDangerNum()));
                    } else if (list.size() == 2) {
                        ll_company3.setVisibility(View.GONE);
                        companyname1.setText(StringUtil.nvl(list.get(0)
                                .getCompanyName() + "（个）"));
                        companyname2.setText(StringUtil.nvl(list.get(1)
                                .getCompanyName() + "（个）"));
                        num1.setText(StringUtil.nvl(list.get(0).getDangerNum()));
                        num2.setText(StringUtil.nvl(list.get(1).getDangerNum()));
                    } else if (list.size() == 3 || list.size() > 3) {
                        companyname1.setText(StringUtil.nvl(list.get(0)
                                .getCompanyName() + "（个）"));
                        companyname2.setText(StringUtil.nvl(list.get(1)
                                .getCompanyName() + "（个）"));
                        companyname3.setText(StringUtil.nvl(list.get(2)
                                .getCompanyName() + "（个）"));
                        num1.setText(StringUtil.nvl(list.get(0).getDangerNum()));
                        num2.setText(StringUtil.nvl(list.get(1).getDangerNum()));
                        num3.setText(StringUtil.nvl(list.get(2).getDangerNum()));
                    } else {
                        ll_company.setVisibility(View.GONE);
                    }

                } else {
                    DialogUtil.showMsgDialog(this, "未查询到数据。", false, null);
                }
                break;
            default:
                break;
        }
    }
}
