package com.safetys.zatgov.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.DateParseUtils;
import com.safetys.widget.common.SPUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.CheckListAdapter;
import com.safetys.zatgov.bean.CheckList;
import com.safetys.zatgov.bean.CompanyDetailInfo;
import com.safetys.zatgov.bean.HyCheckInfo;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.bean.SafetyCheck;
import com.safetys.zatgov.bean.SafetyMatter;
import com.safetys.zatgov.bean.UserIds;
import com.safetys.zatgov.bean.WgyHiddenItemInfo;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.PrefKeys;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.entity.MessageEvent;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.CustomExpandableListView;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.ui.view.SearchBar;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class NewZfCheckAddActivityWgy extends BaseActivity implements
        View.OnClickListener, onNetCallback {
    private Calendar c = null;
    private final static int DATE_DIALOG = 1;
    private final static int DATE_DIALOG2 = 2;
    private View mBtn_back;
    private LoadingDialogUtil mLoading;
    protected static int TAKE_PICTURE_REQUEST_CODE = 1;
    public static int ADD_HIDDEN_WITH_TABLE_CODE = 0X1001;
    private HorizontalScrollView mPicScrollView;
    private LinearLayout mPicsLayout;
    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_zgtime;
    private TextView tv_lxr;
    private TextView tv_null;
    private CheckBox cb;
    private ImageView iv_time;
    private ImageView iv_zgtime;
    private boolean isHave = false;
    private String companyId;
    private int checkId;
    private TextView tv_checktime;
    private EditText tv_checkplace;
    private EditText tv_Phone;
    private EditText tv_CheckPeople;
    private EditText tv_unit;
    private EditText ed_now;
    private String time;
    private ImageView ivnow;
    private Button mBtnSubmit;
    // 是否检查表检查
    private View llTableCheck;
    private CheckBox mCbHvTable;
    private CheckBox mCbNoTable;
    // 是否存在隐患
    private View llHvView;
    private CheckBox mCbHvHidden;
    private CheckBox mCbNoHidden;
    // 是否进行处罚
	/*
	 * private View llIsPunView; private CheckBox mCbHvPun; private CheckBox
	 * mCbNoPun;
	 */
    private boolean isPunTag = false;

    private List<String> list_id;
    private List<String> list_title;
    private String[] areas;
    private LinkedHashMap<String, String> hashMap;
    private LinkedHashMap<String, String> hashMap2;
    private String tv_place;
    private String tv_phone;
    private String tv_people;
    private String tv_law;
    private String tv_now;
    private String tv_companyname;
    private String tv_companyadress;
    private String tv_companylxr;
    private String tv_content;
    private String zgtime;
    private String nowtime;
    private CheckList checkList;
    private File file2;
    private String noteId;
    private boolean isSelect = false;
    private String lxr;
    private boolean isReview;
    private List<UserIds> mDatas;// 一级检查项目源数据
    private List<UserIds> myUserIds;
    LayoutInflater inflater = null;
    private int a;
    /* private ImageOptions mImageOptions; */
    private View rl_checkcontent;
    private View view_time;
    private View view_line_bottom;
    private ArrayList<Long> listcb;
    // Expandable
    private CustomExpandableListView expandable_list_view;
    private MyExpandableAdapter myExpandableAdapter;
    private List<SafetyMatter> listExpandSafetyMatteys;// Group数据
    private List<List<WgyHiddenItemInfo>> listExpandHiddens;// Child数据
    private int outGroupPosition;
    private int outChildPosition;
    private boolean isRead = false;
    private boolean needPunish = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_add_wgy);
       initView();
    }

    private void initView() {
        setHeadTitle("监督检查");
        mLoading = new LoadingDialogUtil(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        companyId = bundle.getString("id");// 企业id
        checkId = bundle.getInt("checkId", -1);// 检查表ID
        isRead = bundle.getBoolean("isRead");

        SPUtils.getData(PrefKeys.PREF_USER_TYPE_KEY, false);
        boolean isGridPerson =  (boolean)SPUtils.getData(PrefKeys.PREF_USER_TYPE_KEY, false);
        // Expandble数据初始化
        expandable_list_view = (CustomExpandableListView) findViewById(R.id.expandable_list_view);
        rl_checkcontent = findViewById(R.id.rl_checkcontent);
        listExpandSafetyMatteys = new ArrayList<SafetyMatter>();
        listExpandHiddens = new ArrayList<List<WgyHiddenItemInfo>>();

        myExpandableAdapter = new MyExpandableAdapter(getApplicationContext(),
                listExpandSafetyMatteys, listExpandHiddens);
        expandable_list_view.setAdapter(myExpandableAdapter);

        // 是否有政府隐患未整改，决定新增是否弹出对话框。只在第一次新增提示
        isReview = bundle.getBoolean("num");
        checkList = (CheckList) bundle.getSerializable("checklist");
        if (checkList == null) {
            checkList = new CheckList();
        }
        listcb = new ArrayList<Long>();
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);
        mPicScrollView = (HorizontalScrollView) findViewById(R.id.gridScrollview);
        mPicsLayout = (LinearLayout) findViewById(R.id.gridlist);
        view_line_bottom = findViewById(R.id.view_line_bottom);
        view_time = findViewById(R.id.view_time);
        tv_null = (TextView) findViewById(R.id.tv_null);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_zgtime = (TextView) findViewById(R.id.tv_zgtime);
        iv_time = (ImageView) findViewById(R.id.iv_time);
        iv_time.setOnClickListener(this);
        iv_zgtime = (ImageView) findViewById(R.id.iv_zgtime);
        iv_zgtime.setOnClickListener(this);
        tv_checktime = (TextView) findViewById(R.id.tv_checktime);
        tv_checkplace = (EditText) findViewById(R.id.tv_checkplace);
        tv_Phone = (EditText) findViewById(R.id.tv_phone);
        tv_CheckPeople = (EditText) findViewById(R.id.tv_CheckPeople);
        tv_unit = (EditText) findViewById(R.id.tv_unit);
        ed_now = (EditText) findViewById(R.id.ed_now);
        tv_lxr = (TextView) findViewById(R.id.tv_lxr);
        // 是否检查表检查
        llTableCheck = findViewById(R.id.ll_table_check);
        llTableCheck.setOnClickListener(this);
        mCbHvTable = (CheckBox) findViewById(R.id.cb_table_yes);
        mCbNoTable = (CheckBox) findViewById(R.id.cb_table_no);
        mCbNoTable.setOnClickListener(this);
        mCbNoTable.setChecked(true);
        mCbHvTable.setOnClickListener(this);

        // 是否进行处罚
		/*
		 * llIsPunView = findViewById(R.id.ll_ispun);
		 * llIsPunView.setOnClickListener(this); mCbHvPun = (CheckBox)
		 * findViewById(R.id.cb_pun_yes); mCbNoPun = (CheckBox)
		 * findViewById(R.id.cb_pun_no); mCbNoPun.setOnClickListener(this);
		 * mCbNoPun.setChecked(true); mCbHvPun.setOnClickListener(this);
		 * if(isGridPerson){ llIsPunView.setVisibility(View.GONE); }
		 */

        // 存在隐患
        llHvView = findViewById(R.id.ll_ishave);
        llHvView.setOnClickListener(this);
        mCbHvHidden = (CheckBox) findViewById(R.id.cb_yes);
        mCbNoHidden = (CheckBox) findViewById(R.id.cb_no);
        mCbNoHidden.setOnClickListener(this);
        mCbNoHidden.setChecked(true);
        mCbHvHidden.setOnClickListener(this);
        cb = (CheckBox) findViewById(R.id.cb);

        mDatas = new ArrayList<UserIds>();

        tv_name.setTextColor(Color.GRAY);
        tv_address.setTextColor(Color.GRAY);
        tv_lxr.setTextColor(Color.GRAY);

        if (isRead) {
            mBtnSubmit.setVisibility(View.GONE);
            tv_zgtime.setTextColor(Color.GRAY);
            tv_checkplace.setEnabled(false);
            tv_Phone.setEnabled(false);
            tv_CheckPeople.setEnabled(false);
            tv_unit.setEnabled(false);

        } else {
            mBtnSubmit.setVisibility(View.VISIBLE);
        }
        time = DateParseUtils.stampToDate(System.currentTimeMillis());
        tv_checktime.setText(time);
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);

        initListner();
        loadingDatas();
    }

    private boolean isHaveHidden = false;// 是否存在隐患
    private boolean isHaveTable = false;// 是否存在检查表
    private boolean isHavePun = false;// 是否需要进行处罚

    /**
     * set Listner
     */
    private void initListner() {
        if (checkId == -1) {
            // 是否存在隐患
            mCbNoHidden
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            mCbHvHidden.setChecked(!isChecked);
                            // showPun(isHaveTable, isHaveHidden);
                            showZgTime(View.GONE);
                        }
                    });

            mCbHvHidden
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            mCbNoHidden.setChecked(!isChecked);
                            isHaveHidden = isChecked;
                            if (isChecked) {
                                mBtnSubmit.setText("下一步(隐患录入)");

                            } else {
                                mBtnSubmit.setText("提交");
                            }
                            // 是否显示是否处罚按钮
                            showZgTime(View.GONE);
                            // showPun(isHaveTable, isHaveHidden);

                        }
                    });
        }

        // 是否检查表检查
        mCbNoTable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mCbHvTable.setChecked(!isChecked);
                showZgTime(View.GONE);
                if (isChecked) {
                    checkId = -1;
                }
                rl_checkcontent.setVisibility(View.GONE);
                llHvView.setVisibility(View.VISIBLE);
                listExpandSafetyMatteys.clear();
                myExpandableAdapter.notifyDataSetChanged();
                view_line_bottom.setVisibility(View.GONE);
                // showPun(isHaveTable, isHaveHidden);
            }
        });

        mCbHvTable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mCbNoTable.setChecked(!isChecked);
                // 当检查表检查时候，存在隐患Cb重选为否
                mCbNoHidden.setChecked(true);
                isHaveTable = isChecked;
                showZgTime(View.VISIBLE);
                llHvView.setVisibility(View.GONE);
                if (isChecked) {
                    // 选择检查表
                    listExpandHiddens.clear();
                    showCheckTable();

                }

            }
        });


    }


    /**
     * 检查表Dialog
     */
    private PullToRefresh mPrOne;
    private ArrayList<SafetyCheck> mCheckDatasOne;
    private SearchBar mSbOne;
    private CheckListAdapter mCheckAdapterOne;
    private Dialog dialogOne;

    private void showCheckTable() {
        // 新建檢查表
        View inflate = LayoutInflater.from(NewZfCheckAddActivityWgy.this)
                .inflate(R.layout.wgy_checklist, null);
        mPrOne = (PullToRefresh) inflate.findViewById(R.id.list_checklist);
        mCheckDatasOne = new ArrayList<SafetyCheck>();
        mSbOne = (SearchBar) inflate.findViewById(R.id.checklist_search_bar);
        mSbOne.setHintSearch("请输入检查表名");
        mSbOne.setOnSearchListener(new SearchBar.onSearchListener() {
            @Override
            public void onSearchButtonClick(String searchStr) {
                reloadingAdd();
            }
        });

        mCheckAdapterOne = new CheckListAdapter(this, mCheckDatasOne);
        mPrOne.setAdapter(mCheckAdapterOne);
        mPrOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (dialogOne != null) {
                    dialogOne.dismiss();
                }
                checkId = mCheckDatasOne.get(position).getId();
                loadingCheckDatas(checkId);
                showZgTime(View.VISIBLE);
                rl_checkcontent.setVisibility(View.VISIBLE);
                llHvView.setVisibility(View.GONE);
                restData();

            }
        });

        mPrOne.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage_One > totalCount_One) {
                    ToastUtils.showMessage(NewZfCheckAddActivityWgy.this,
                            "没有更多的数据了。");
                    mPrOne.finishLoading(false);
                } else {
                    loadingAdd();
                }
            }

        });

        // 如果第一次按，是加载，之后按都为重新加载
        restData();
        loadingAdd();
        if (!DialogUtil.isFastDoubleClick()) {
            dialogOne = DialogUtil.getlistDialog(this, "检查表", inflate, null);
            dialogOne.getWindow().setType(
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialogOne.show();
        }
    }

    private int totalCount_One = 0;// 总数
    private int mCurrentPage_One = 0;// 当前页

    /**
     * 是否显示整改时间item
     *
     * @param visibility
     */
    private void showZgTime(int visibility) {
        view_time.setVisibility(visibility);
        view_line_bottom.setVisibility(visibility);
    }

    private void loadingAdd() {
        mLoading.show();
        HttpRequestHelper.getInstance().getWgyCheckList(this, totalCount_One,
                mCurrentPage_One, mSbOne.getSearchData(), Const.WGY_CHECKLIST,
                this);
    }

    private void restData() {
        totalCount_One = 0;
        mCurrentPage_One = 0;
        mCheckDatasOne.clear();
    }

    private void reloadingAdd() {
        restData();
        mCheckAdapterOne.notifyDataSetChanged();
        loadingAdd();
    }

    private void loadingDatas() {
        mLoading.show();
        HttpRequestHelper.getInstance().getCompanyItemInfo(this, companyId,
                Const.NET_GET_COMPANY_ITEM_INFO_CODE, this);
    }

    private void loadingCheckDatas(int checkId) {
        mLoading.show();
        HttpRequestHelper.getInstance().getSafetyCheckInfo(this, checkId,
                Const.NET_GET_SAFETY_CHECK_INFO_CODE, this);
    }

    private void isReivew() {
        if (isReview) {
            DialogUtil.showMsgDialog2(this,
                    "由于该企业存在未整改的政府检查隐患，是否现在对该企业进行隐患复查？", "取消",
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(
                                    NewZfCheckAddActivityWgy.this,
                                    ZfReviewCompanyListActivity.class);
                            intent.putExtra("name", tv_companyname);
                            startActivity(intent);
                        }
                    });
        }
    }

    private ArrayList<HyCheckItemInfo> listHy;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back:
                finish();
                break;

            case R.id.cc:
                Boolean isCheck = cb.isChecked();
                cb.setChecked(!isCheck);
                break;

            case R.id.cb_pun_no:
                isPunTag = false;
                break;

            case R.id.cb_pun_yes:
                isPunTag = true;
                break;

            case R.id.ll_ishave:
                Boolean isCheck2 = mCbHvHidden.isChecked();
                mCbHvHidden.setChecked(!isCheck2);
                mCbNoHidden.setChecked(isCheck2);
                break;

            case R.id.cb_table_no:
                slectTableCheck();

                break;

            case R.id.cb_table_yes:
                slectTableCheck();
                break;

            case R.id.ll_table_check:
                Boolean isCheck3 = mCbHvTable.isChecked();
                mCbHvTable.setChecked(!isCheck3);
                mCbNoTable.setChecked(isCheck3);
                slectTableCheck();
                break;

            case R.id.iv_time:
                showDialog(DATE_DIALOG);
            case R.id.iv_zgtime:
                showDialog(DATE_DIALOG2);

                break;

            case R.id.btn_submit:
                isSelect = false;
                if (!submit()) {
                    return;
                }
                if (checkId != -1 && view_time.getVisibility() == View.VISIBLE) {// 有检查项
                    if (TextUtils.isEmpty(zgtime)) {
                        ToastUtils.showMessage(this, "责令整改时间不能为空");
                        return;
                    }
                }
                if (mBtnSubmit.getText().equals("提交")) {// 提交

                    listHy = new ArrayList<HyCheckItemInfo>();
                    for (int i = 0; i < listExpandSafetyMatteys.size(); i++) {
                        SafetyMatter matter = listExpandSafetyMatteys.get(i);
                        boolean b = listExpandSafetyMatteys.get(i).isChecked();
                        if (!b) {// 如果不通过
                            // 判断child是否有数据
                            if (listExpandHiddens.get(i).size() == 0) {
                                ToastUtils.showMessage(this, "安监检查事	项第" + (i + 1)
                                        + "项检查项下无隐患！");
                                return;
                            }
                        }
                        String index = b ? "1" : "0";

                        listHy.add(new HyCheckItemInfo(matter.getId(), matter
                                .getContent(), index, matter.getId(), matter
                                .getRemark()));
                    }
                    mLoading.show();
                    HttpRequestHelper.getInstance().submitCheck(
                            NewZfCheckAddActivityWgy.this, needPunish, companyId,
                            nowtime, tv_place, tv_phone, listcb, tv_people, tv_law,
                            null, true, zgtime, tv_now, String.valueOf(checkId),
                            listHy, Const.NET_ADD_CHECK_ITEM,
                            NewZfCheckAddActivityWgy.this);
                } else {// 下一布，隐患录入
                    tv_now = null;
                    Intent intent4 = new Intent(this, NewZfYhLrActivity.class);
                    Bundle bundle4 = new Bundle();
                    intent4.putExtra("ishave", "no");
                    intent4.putExtra("isform", "yes");
                    checkList.setCheckId("");
                    intent4.putExtra("checklist", (Serializable) checkList);
                    intent4.putExtras(bundle4);
                    startActivity(intent4);
                    finish();
                }

                break;
            default:
                break;
        }
    }

    /**
     * 是否使用检查表检查
     */
    private void slectTableCheck() {
        if (mCbNoTable.isChecked()) {

        } else {

        }

    }

    // 提交
    private boolean submit() {
        tv_place = tv_checkplace.getText().toString();
        tv_phone = tv_Phone.getText().toString();
        tv_people = tv_CheckPeople.getText().toString();
        tv_law = tv_unit.getText().toString();
        tv_now = ed_now.getText().toString();
        zgtime = tv_zgtime.getText().toString();
        nowtime = tv_checktime.getText().toString();

        if (TextUtils.isEmpty(tv_place)) {
            ToastUtils.showMessage(this, "检查场所不能为空");
            return false;
        }
        if (TextUtils.isEmpty(nowtime)) {
            ToastUtils.showMessage(this, "检查时间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tv_phone)) {
            ToastUtils.showMessage(this, "联系方式不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tv_people)) {
            ToastUtils.showMessage(this, "检查人/记录人不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tv_law)) {
            ToastUtils.showMessage(this, "执法单位不能为空");
            return false;
        }
        if (TextUtils.isEmpty(tv_now)) {
            ToastUtils.showMessage(this, "现场检查记录不能为空");
            return false;
        }

        checkList.setCompanyname(tv_companyname);
        checkList.setCompanyId(companyId);
        checkList.setCompanyadress(tv_companyadress);
        checkList.setCompanylxr(tv_companylxr);
        checkList.setPlace(tv_place);
        checkList.setPhone(tv_phone);
        checkList.setPeople(tv_people);
        checkList.setLaw(tv_law);
        checkList.setZgtime(zgtime);
        checkList.setChecktime(nowtime);
        checkList.setCheck(true);
        checkList.setListCb(listcb);// 参与检查人
        checkList.setNowcontent(tv_now);// 现场检查记录
        return true;
    }

    // 弹出查看照片
    protected void showPicture(String picPath) {
        Intent intent = new Intent(this, ViewPhotoActivity.class);
        intent.putExtra("picPath", picPath);
        startActivity(intent);
    }


    @Override
    public void onError(String errorMsg) {
        mLoading.dismiss();
        DialogUtil.showMsgDialog(this, errorMsg, true, null);
    }

    /**
     * 创建日期及时间选择对话框
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DATE_DIALOG:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                c.set(year, month, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        "yyyy-MM-dd");
                                String formattedDate = sdf.format(c.getTime());
                                tv_checktime.setText(formattedDate);
                            }
                        }, c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );
                break;
            case DATE_DIALOG2:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                c.set(year, month, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        "yyyy-MM-dd");
                                String formattedDate = sdf.format(c.getTime());
                                tv_zgtime.setText(formattedDate);
                            }
                        }, c.get(Calendar.YEAR), // 传入年份
                        c.get(Calendar.MONTH), // 传入月份
                        c.get(Calendar.DAY_OF_MONTH) // 传入天数
                );
                break;
        }
        return dialog;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_HIDDEN_WITH_TABLE_CODE) {

            if (data != null) {
                Bundle bundle = new Bundle();
                bundle = data.getExtras();
                int position = bundle.getInt("position");
                WgyHiddenItemInfo hiddenItemInfo = (WgyHiddenItemInfo) bundle
                        .getSerializable("hiddeninfo");

                List<WgyHiddenItemInfo> listData = listExpandHiddens
                        .get(position);
                listData.add(hiddenItemInfo);
                listExpandHiddens.remove(position);
                listExpandHiddens.add(position, listData);
                expandable_list_view.expandGroup(position);
                myExpandableAdapter.notifyDataSetChanged();
            }

        }

    }

    private File tempFile;// 拍摄缓存照片文件

    // private List<File> pics = new ArrayList<File>(0);// 单条隐患所有照片

    public class MyExpandableAdapter extends BaseExpandableListAdapter {
        // private List<String> groupArray;
        private List<SafetyMatter> groupeExpandSafetyMatteys;// Group数据
        private List<List<WgyHiddenItemInfo>> childenlistExpandHiddens;// Child数据
        private Context mContext;
        private LayoutInflater mInflater;

        public MyExpandableAdapter(Context context,
                                   List<SafetyMatter> listExpandSafetyMatteys,
                                   List<List<WgyHiddenItemInfo>> listExpandHiddens) {
            mContext = context;
            this.groupeExpandSafetyMatteys = listExpandSafetyMatteys;
            this.childenlistExpandHiddens = listExpandHiddens;
            this.mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getGroupCount() {

            return groupeExpandSafetyMatteys.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {

            return childenlistExpandHiddens.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {

            return groupeExpandSafetyMatteys.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {

            return childenlistExpandHiddens.get(groupPosition).get(
                    childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {

            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {

            return childPosition;
        }

        @Override
        public boolean hasStableIds() {

            return true;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            View view = convertView;
            GroupHolder holder = null;
            if (view == null) {
                holder = new GroupHolder();
                view = mInflater
                        .inflate(
                                R.layout.list_view_string_and_checkbox_listview_item_wgy,
                                null);
                holder.textView1 = (TextView) view.findViewById(R.id.text1);
                holder.iv_addtrouble = (ImageView) view
                        .findViewById(R.id.iv_group_addtrouble);
                holder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                view.setTag(holder);
            } else {
                holder = (GroupHolder) view.getTag();
            }
            holder.iv_addtrouble.setTag(groupPosition);
            holder.checkBox.setTag(groupPosition);
            holder.textView1.setText(groupeExpandSafetyMatteys.get(
                    groupPosition).getContent());
            if (groupeExpandSafetyMatteys.get(groupPosition).isChecked()) {
                holder.checkBox.setChecked(true);
                holder.iv_addtrouble.setVisibility(View.GONE);

            } else {
                holder.checkBox.setChecked(false);
                holder.iv_addtrouble.setVisibility(View.VISIBLE);

            }

            boolean isViewTimeTag = false;//
            for (int i = 0; i < groupeExpandSafetyMatteys.size(); i++) {
                if (!groupeExpandSafetyMatteys.get(i).isChecked()) {
                    isViewTimeTag = true;
                }
            }
            if (isViewTimeTag) {
                showZgTime(View.VISIBLE);
                // llIsPunView.setVisibility(View.VISIBLE);
            } else {
                showZgTime(View.GONE);
                // llIsPunView.setVisibility(View.GONE);

            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int safetyMatterId = listExpandSafetyMatteys.get(
                            groupPosition).getId();
                    int position = Integer.parseInt(v.getTag().toString());
                    listExpandSafetyMatteys.get(position).setChecked(
                            !listExpandSafetyMatteys.get(position).isChecked());
                    listExpandHiddens.get(position).clear();
                    myExpandableAdapter.notifyDataSetChanged();

                }
            });

            holder.iv_addtrouble.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {


                    Intent intent = new Intent(NewZfCheckAddActivityWgy.this,
                            TroubleRegisterActivity.class);
                    intent.putExtra("source", "table");
                    intent.putExtra("position",
                            Integer.parseInt(v.getTag().toString()));// 隐患所在group位置
                    intent.putExtra("hiddeninfo",
                            (Serializable) new WgyHiddenItemInfo("",
                                    listExpandSafetyMatteys.get(groupPosition)
                                            .getId()));
                    startActivityForResult(intent, ADD_HIDDEN_WITH_TABLE_CODE);

                }
            });

            return view;
        }

        @Override
        public View getChildView(final int groupPosition,
                                 final int childPosition, boolean isLastChild, View convertView,
                                 ViewGroup parent) {
            View view = convertView;
            ChildHolder holder = null;
            if (view == null) {
                holder = new ChildHolder();
                view = mInflater.inflate(
                        R.layout.list_view_string_and_photo_delete_item, null);
                holder.mTextView1 = (TextView) view.findViewById(R.id.et_left);
                holder.btn_delete = view.findViewById(R.id.btn_delete);
                holder.mllShowPicture = (LinearLayout) view
                        .findViewById(R.id.ll_show_image);
                holder.mHsShowImage = (HorizontalScrollView) view
                        .findViewById(R.id.hs_show_image);
                holder.ivImage = (ImageView) view.findViewById(R.id.iv_image);
                view.setTag(holder);
            } else {
                holder = (ChildHolder) view.getTag();
            }

            WgyHiddenItemInfo itemInfo = listExpandHiddens.get(groupPosition)
                    .get(childPosition);
            holder.mTextView1.setText(itemInfo.getDes());

            if (itemInfo.getImageFiles() != null) {
                x.image().bind(holder.ivImage,
                        itemInfo.getImageFiles().get(0).getAbsolutePath(),
                        getImageOptions());
            }
            holder.ivImage.setTag(itemInfo.getImageFiles());
            holder.ivImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    List<File> files = (List<File>) v.getTag();
                    List<String> paths = new ArrayList<String>();
                    for (int i = 0; i < files.size(); i++) {
                        paths.add(files.get(i).getAbsolutePath());
                    }
                    showPictures(paths);
                }

            });



            holder.btn_delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listExpandHiddens.get(groupPosition).get(childPosition) != null) {
                        listExpandHiddens.get(groupPosition).remove(
                                childPosition);
                        myExpandableAdapter.notifyDataSetChanged();
                    }
                }
            });
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class GroupHolder {
            public TextView textView1;
            public CheckBox checkBox;
            public ImageView iv_addtrouble;
        }

        class ChildHolder {
            public TextView mTextView1;
            public View btn_delete;
            public LinearLayout mllShowPicture;
            public HorizontalScrollView mHsShowImage;
            public ImageView ivImage;
        }
    }

    /**
     * 增加ImageView
     *
     * @param mContext
     * @param llShowImage
     */
    private void addImageView(final Context mContext,
                              final LinearLayout llShowImage, File file) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        final RelativeLayout picture = (RelativeLayout) inflater.inflate(
                R.layout.photo, null);

        ImageView photo = (ImageView) picture.findViewById(R.id.photo);
        ImageButton delete = (ImageButton) picture.findViewById(R.id.delete);
        delete.setTag(file);
        picture.setTag(file.getAbsolutePath());
        // 缩略图使用压缩后的
        // photo.setImageBitmap(smallBit);
        x.image().bind(photo, file.getAbsolutePath(), getImageOptions());

        llShowImage.addView(picture);
        delete.setVisibility(View.GONE);
        // 删除
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogUtil.showDefaultAlertDialog(mContext, "提示", "确定删除吗？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                llShowImage.removeView(picture);
                                // pics.remove(v.getTag());
                                //
                                // tempFile = null;
                            }
                        });
            }
        });
        // 弹出查看
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil
                        .showDefaultProgressDialog(NewZfCheckAddActivityWgy.this);
                // 弹出传递照片地址
                showPicture(v.getTag().toString());
            }
        });

    }

    private List<List<File>> listFiles = new ArrayList<List<File>>();// 存放按Expand位置的图片
    private CompanyDetailInfo companyDetailInfo;
    private boolean refresh = false;
    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();
        switch (requestCode) {
            case Const.HAVE_PROBLEM:

                if (mJsonResult.getEntity() != null) {

                    DialogUtil.showMsgDialog2(this,
                            "由于该企业存在未整改的政府检查隐患，是否现在对该企业进行隐患复查？", "取消",
                            new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(
                                            NewZfCheckAddActivityWgy.this,
                                            ZfReviewCompanyListActivity.class);
                                    intent.putExtra("name", "name");
                                    startActivity(intent);
                                }
                            });
                }
                break;
            case Const.NET_ADD_CHECK_ITEM:
                if (mJsonResult.getEntity() != null) {
                    HyCheckItemInfo mCheckItemInfo = JSON.parseObject(
                            mJsonResult.getEntity(), HyCheckItemInfo.class);
                    EventBus.getDefault().post(new MessageEvent(ZfCheckRecordListActivity.ACTION_UPDATE));

                            com.safetys.zatgov.ui.view.Dialog mDialog = new com.safetys.zatgov.ui.view.Dialog(this,
                            "提示", "新增成功");
                    mDialog.setOnAcceptButtonClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            EventBus.getDefault().post(new MessageEvent(EnterpriseListActivity.ACTION_UPDATE_DATA));
                            finish();
                        }
                    });
                    mDialog.setCancelable(false);
                    mDialog.show();
                    if (listExpandSafetyMatteys.size() > 0) {

                        List<WgyHiddenItemInfo> inList = new ArrayList<WgyHiddenItemInfo>();
                        for (int i = 0; i < listExpandSafetyMatteys.size(); i++) {
                            for (int j = 0; j < listExpandHiddens.get(i).size(); j++) {
                                inList.add(listExpandHiddens.get(i).get(j));
                            }
                        }
                        for (int i = 0; i < inList.size(); i++) {
                            mLoading.show();
                            if (i == inList.size() - 1) {// 最后一次刷新界面
                                refresh = true;
                            }
                            HttpRequestHelper.getInstance().submitGenealTrouble(
                                    NewZfCheckAddActivityWgy.this, null,
                                    String.valueOf(inList.get(i).getCheckInfoId()),
                                    companyId, mCheckItemInfo.getNoteId(),
                                    companyDetailInfo.getFdDelegate(), tv_phone,
                                    inList.get(i).getYhlb(),
                                    inList.get(i).getDes(), zgtime,
                                    inList.get(i).getImageFiles(), nowtime,
                                    inList.get(i).getCheckInfoId() + "", null,
                                    false, Const.SUB_NOMARL_CHECK,
                                    NewZfCheckAddActivityWgy.this);
                        }
                    }
                }

                break;


            case Const.NET_GET_COMPANY_ITEM_INFO_CODE:
                if (checkId != -1) {
                    loadingCheckDatas(checkId);// 有检查项
                    showZgTime(View.VISIBLE);
                    rl_checkcontent.setVisibility(View.VISIBLE);
                    llHvView.setVisibility(View.GONE);
                } else {// 无检查项
                    showZgTime(View.GONE);
                    rl_checkcontent.setVisibility(View.GONE);
                    llHvView.setVisibility(View.VISIBLE);
                }
                if (mJsonResult.getEntity() != null) {
                    companyDetailInfo = JSON.parseObject(mJsonResult.getEntity(),
                            CompanyDetailInfo.class);
                    tv_companyname = companyDetailInfo.getCompanyName();
                    tv_companyadress = companyDetailInfo.getAddress();
                    tv_companylxr = companyDetailInfo.getFdDelegate();
                    tv_name.setText(tv_companyname);
                    tv_address.setText(tv_companyadress);
                    tv_lxr.setText(tv_companylxr);
                    lxr = companyDetailInfo.getFdDelegate();
                    tv_CheckPeople.setText(companyDetailInfo.getNoter());
                    tv_unit.setText(companyDetailInfo.getExecuteUnit());
                    LinkedList<String> list = new LinkedList<String>();

                    if (mJsonResult.getJson() == null
                            || mJsonResult.getJson().toString().equals("[]")) {

                    } else {
                        tv_null.setVisibility(View.GONE);
                        mPicScrollView.setVisibility(View.VISIBLE);
                        myUserIds = JSONArray.parseArray(
                                (String) mJsonResult.getJson(), UserIds.class);

                        if (myUserIds == null || myUserIds.size() == 0) {

                        } else {
                            mDatas.addAll(myUserIds);
                            // TODONEW
                            for (int i = 0; i < mDatas.size(); i++) {
                                if (inflater == null) {
                                    inflater = LayoutInflater.from(this);
                                }
                                final RelativeLayout gridLayout = (RelativeLayout) inflater
                                        .inflate(R.layout.wgy_grid_select, null);
                                CheckBox cb_checked = (CheckBox) gridLayout
                                        .findViewById(R.id.cb_checked);
                                TextView gridname = (TextView) gridLayout
                                        .findViewById(R.id.tv_gridname);
                                gridname.setText(mDatas.get(i).getGridName());
                                gridname.setTextSize(12);
                                mPicsLayout.addView(gridLayout);
                                a = i;
                                cb_checked
                                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(
                                                    CompoundButton buttonView,
                                                    boolean isChecked) {

                                                if (isChecked) {
                                                    if (!listcb.contains(mDatas
                                                            .get(a).getGridId())) {
                                                        listcb.add(mDatas.get(a)
                                                                .getGridId());
                                                    }
                                                } else {
                                                    if (listcb.contains(mDatas.get(
                                                            a).getGridId())) {
                                                        listcb.remove(mDatas.get(a)
                                                                .getGridId());
                                                    }
                                                }

                                            }
                                        });
                            }
                        }

                    }
                    isReivew();
                }
                break;
            case Const.NET_GET_SAFETY_CHECK_INFO_CODE:

                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {
                    if (mDatas.size() == 0) {
                        Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogUtil.showMsgDialog(this, "没有更多的数据。", false, null);
                    }
                } else {
                    listExpandSafetyMatteys.addAll(JSON.parseArray(
                            (String) mJsonResult.getJson(), SafetyMatter.class));
                    // 为隐患list 初始加入各list
                    for (int i = 0; i < listExpandSafetyMatteys.size(); i++) {
                        listExpandHiddens.add(new ArrayList<WgyHiddenItemInfo>());
                    }
                    myExpandableAdapter.notifyDataSetChanged();
                }

                break;
            case 1:
                if (mJsonResult.getJson() != null) {

                    List<HyCheckInfo> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), HyCheckInfo.class);
                    list_id = new ArrayList();
                    list_title = new ArrayList();
                    hashMap = new LinkedHashMap<String, String>();
                    hashMap2 = new LinkedHashMap<String, String>();

                    areas = new String[mJsonResult.getTotalCount() + 1];
                    int i = 0;
                    for (HyCheckInfo heCheckInfo : list) {
                        list_title.add(heCheckInfo.getTitle());
                        list_id.add(heCheckInfo.getId());

                        areas[i] = heCheckInfo.getTitle();
                        i++;
                        hashMap.put(heCheckInfo.getTitle(), heCheckInfo.getId());
                        hashMap2.put(heCheckInfo.getId(), heCheckInfo.getTitle());
                    }

                }

                break;

            case Const.WGY_CHECKLIST:

                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {
                    if (mCheckDatasOne.size() == 0) {
                        ToastUtils.showMessage(getApplicationContext(), "没有数据");
                    } else {
                        DialogUtil.showMsgDialog(this, "没有更多的数据。", false, null);
                    }
                } else {
                    mCurrentPage_One = mCurrentPage_One + Const.PAGE_SIZE;
                    totalCount_One = mJsonResult.getTotalCount();
                    mCheckDatasOne.addAll(JSON.parseArray(
                            (String) mJsonResult.getJson(), SafetyCheck.class));
                    mCheckAdapterOne.notifyDataSetChanged();
                }
                mPrOne.finishLoading(false);

                break;

            default:
                break;
        }

    }
}

