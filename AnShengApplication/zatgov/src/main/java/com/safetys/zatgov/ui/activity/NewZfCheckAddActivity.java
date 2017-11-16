package com.safetys.zatgov.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.DateParseUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CheckList;
import com.safetys.zatgov.bean.CompanyDetailInfo;
import com.safetys.zatgov.bean.HyCheckInfo;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.bean.SafetyMatter;
import com.safetys.zatgov.bean.UserIds;
import com.safetys.zatgov.bean.WgyHiddenItemInfo;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.FileUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class NewZfCheckAddActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback {
    private Calendar c = null;
    private final static int DATE_DIALOG = 1;
    private final static int DATE_DIALOG2 = 2;
    private View mBtn_back;
    private LoadingDialogUtil mLoading;
    protected static int mTakePictureRequestCode = 1;
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
    private Button mBtn_submit;
    private View ll_ishave;
    private CheckBox cb_yes;
    private CheckBox cb_no;
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
    private String checkid;
    private String lxr;
    private boolean isReview;
    private List<UserIds> mDatas;//一级检查项目源数据
    private List<UserIds> myUserIds;
    LayoutInflater inflater = null;
    private int a;

    private ImageOptions mImageOptions;
    private View view_time;
    private List<SafetyMatter> listSafetyMatteys;//一级检查项目源数据
    private PullToRefresh lv_checkcontent;//一级检查项List
    private MyListAdapter mAdapter;//一级检查项数据适配器
    private ArrayList<Long> listcb;

    //二级隐患源数据
    private List<WgyHiddenItemInfo> listHiddens;
    private PullToRefresh lv_troubles;//一级检查项List
    private MyTroubleAdapter myTroubleAdapter;//一级检查项数据适配器
    private int mPosition;//二级列表隐患位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_add);
        mLoading = new LoadingDialogUtil(this);
        initView();
    }

    private void initView() {
        setHeadTitle("检查记录");
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        companyId = bundle.getString("id");// 企业id
        checkId = bundle.getInt("checkId",-1);
        // 是否有政府隐患未整改，决定新增是否弹出对话框。只在第一次新增提示
        isReview = bundle.getBoolean("num");
        checkList = (CheckList) bundle.getSerializable("checklist");
        listcb = new ArrayList<Long>();
        mBtn_submit = (Button) findViewById(R.id.btn_submit);
        mBtn_submit.setOnClickListener(this);
        mPicScrollView = (HorizontalScrollView) findViewById(R.id.gridScrollview);
        mPicsLayout = (LinearLayout) findViewById(R.id.gridlist);
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
        ll_ishave = findViewById(R.id.ll_ishave);
        ll_ishave.setOnClickListener(this);
        cb_yes = (CheckBox) findViewById(R.id.cb_yes);
        cb_no = (CheckBox) findViewById(R.id.cb_no);
        cb_no.setOnClickListener(this);
        cb_yes.setOnClickListener(this);
        cb_yes.setChecked(true);
        cb = (CheckBox) findViewById(R.id.cb);
        lv_checkcontent = (PullToRefresh) findViewById(R.id.list_checkcontent);
        lv_checkcontent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int safetyMatterId = listSafetyMatteys.get(position).getId();
                listSafetyMatteys.get(position).setChecked(!listSafetyMatteys.get(position).isChecked());
                mAdapter.notifyDataSetChanged();
            }
        });

        lv_checkcontent.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                ToastUtils.showMessage(NewZfCheckAddActivity.this,
                        "没有更多的检查事项了。");
                lv_checkcontent.finishLoading(false);
            }
        });
        mDatas = new ArrayList<UserIds>();
        listSafetyMatteys = new ArrayList<SafetyMatter>();
        mAdapter = new MyListAdapter(this, listSafetyMatteys);
        lv_checkcontent.setAdapter(mAdapter);


        tv_checktime.setText(DateParseUtils.stampToDate(System.currentTimeMillis()));
        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);

        // mLoading.show();
        // HttpRequestHelper.getInstance().getHyCheckInfo(this, 1, this);
        cb_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                cb_yes.setChecked(!isChecked);
            }
        });

        cb_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                cb_no.setChecked(!isChecked);
            }
        });
        loadingDatas();
    }

    private void loadingDatas() {
        mLoading.show();
        HttpRequestHelper.getInstance().getCompanyItemInfo(this, companyId, Const.NET_GET_COMPANY_ITEM_INFO_CODE, this);
    }

    private void loadingCheckDatas(){
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
                                    NewZfCheckAddActivity.this,
                                    ZfReviewCompanyListActivity.class);
                            intent.putExtra("name", tv_companyname);
                            startActivity(intent);
                        }
                    });
        }
    }


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

            case R.id.cb_no:
                getSelect();
                break;

            case R.id.cb_yes:
                getSelect();
                break;

            case R.id.ll_ishave:
                Boolean isCheck2 = cb_yes.isChecked();
                cb_yes.setChecked(!isCheck2);
                cb_no.setChecked(isCheck2);
                getSelect();
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
                zgtime = null;
                //cb.setChecked(false);
                mLoading.show();
		/*	HttpRequestHelper.getInstance().submitCheck(this, id2, nowtime,
					tv_place, tv_phone, listcb, tv_people, tv_law, pics,
					cb.isChecked(), zgtime, tv_now, null, null,
					Const.NET_ADD_CHECK_ITEM, this);*/
                break;
            default:
                break;
        }
    }

    private void getSelect() {
        if (cb_no.isChecked()) {
            view_time.setVisibility(View.GONE);
        } else {
            view_time.setVisibility(View.GONE);
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
        return true;
    }



    // 弹出查看照片
    protected void showPicture(String picPath) {
        Intent intent = new Intent(this, ViewPhotoActivity.class);
        intent.putExtra("picPath", picPath);
        startActivity(intent);
    }



    protected ImageOptions getImageOptions() {
        if (mImageOptions == null) {
            mImageOptions = new ImageOptions.Builder()
                    .setSize(DensityUtil.dip2px(300), DensityUtil.dip2px(397))
                    .setRadius(DensityUtil.dip2px(5))
                    // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setCrop(false) // 很多时候设置了合适的scaleType也不需要它.
                    // 加载中或错误图片的ScaleType
                    // .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP).build();
        }
        return mImageOptions;
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

    private class MyListAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private List<SafetyMatter> listSafetyMatters;
        private ViewHodler mHodler;

        public MyListAdapter(Context context, List<SafetyMatter> mDatas2) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.listSafetyMatters = mDatas2;
        }

        @Override
        public int getCount() {
            return listSafetyMatters.size();
        }

        @Override
        public Object getItem(int position) {
            return listSafetyMatters.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(
                        R.layout.list_view_string_and_checkbox_listview_item, null);
                mHodler = new ViewHodler();
                mHodler.mTextView1 = (TextView) convertView
                        .findViewById(R.id.text1);
                mHodler.iv_addtrouble = (ImageView) convertView
                        .findViewById(R.id.iv_addtrouble);
                mHodler.checkbox = (CheckBox) convertView
                        .findViewById(R.id.checkbox);
                mHodler.rl_hang =  convertView
                        .findViewById(R.id.rl_hang);
                mHodler.list_troubles =  (PullToRefresh) convertView
                        .findViewById(R.id.list_troubles);
                convertView.setTag(mHodler);

            }
            mHodler = (ViewHodler) convertView.getTag();
            mHodler.mTextView1.setText(listSafetyMatters.get(position).getContent());

            //init
            listHiddens =  new ArrayList<WgyHiddenItemInfo>();
            myTroubleAdapter = new MyTroubleAdapter(getApplicationContext(), listHiddens);
            mHodler.list_troubles.setAdapter(myTroubleAdapter);
            mHodler.list_troubles.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

                @Override
                public void onLoadingMore() {
                    // 加载更多
                    ToastUtils.showMessage(NewZfCheckAddActivity.this,
                            "没有更多的检查事项了。");
                    mHodler.list_troubles.finishLoading(false);
                }
            });

            if (listSafetyMatters.get(position).isChecked()) {
                mHodler.checkbox.setChecked(true);
                mHodler.rl_hang .setVisibility(View.GONE);
                mHodler.list_troubles .setVisibility(View.GONE);
            } else {
                mHodler.checkbox.setChecked(false);
                mHodler.rl_hang .setVisibility(View.VISIBLE);
                mHodler.list_troubles .setVisibility(View.VISIBLE);
                mHodler.iv_addtrouble.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //showDialog();
                        final EditText inputServer = new EditText(NewZfCheckAddActivity.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewZfCheckAddActivity.this);
                        builder.setTitle("请输入隐患描述！").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                                .setNegativeButton("取消", null);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                listHiddens.add(new WgyHiddenItemInfo(inputServer.getText().toString()));
                                myTroubleAdapter.notifyDataSetChanged();
                            }
                        });
                        builder.show();
                    }
                });
            }
            mHodler.checkbox.setTag(position);
            mHodler.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            mHodler.checkbox.setChecked(isChecked);
                        }
                    });
            return convertView;
        }
    }

    private  void showDialog(){
        View inflate = LayoutInflater.from(NewZfCheckAddActivity.this)
                .inflate(R.layout.wgy_hidden_input, null);
        EditText et_des = (EditText) inflate.findViewById(R.id.et_hidden_des);
        Dialog dialogOne;
        if (!DialogUtil.isFastDoubleClick()) {
            dialogOne = DialogUtil.getlistDialog(this, "添加隐患", inflate, null);
            dialogOne.getWindow().setType(
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialogOne.show();
        }
    }
    private class ViewHodler {
        TextView mTextView1;
        ImageView iv_addtrouble;
        CheckBox checkbox;
        View rl_hang;
        PullToRefresh list_troubles;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // 拍照，照片删除
        if (requestCode == mTakePictureRequestCode
                && resultCode == Activity.RESULT_OK && tempFile != null
                && tempFile.length() > 0) {
            try {
                tempFile= FileUtil.getCompressFile(tempFile);
                if (pics.size() < 10) {

                    pics.add(tempFile);
//				smallBit = ImageUtil.getThumbnails(tempFile.getAbsolutePath());
				/*if(inflater == null){
					inflater = LayoutInflater.from(NewZfCheckAddActivity.this);
				}
				final RelativeLayout picture = (RelativeLayout) inflater
						.inflate(R.layout.photo, null);
				ImageView photo = (ImageView) picture.findViewById(R.id.photo);
				ImageButton delete = (ImageButton) picture
						.findViewById(R.id.delete);
				delete.setTag(tempFile);
				picture.setTag(tempFile.getAbsolutePath());*/
                    listHiddens.get(mPosition).setImageFile(tempFile);
                    myTroubleAdapter.notifyDataSetChanged();
                    // 缩略图使用压缩后的
//				photo.setImageBitmap(smallBit);
                    //x.image().bind(photo, tempFile.getAbsolutePath(), getImageOptions());
                    //mPicsLayout.addView(picture);
                    //mPicScrollView.setVisibility(View.VISIBLE);
                    // 删除监听
		/*		delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(final View v) {
						DialogUtil.showDefaultAlertDialog(NewZfCheckAddActivity.this, "提示",
								"确定删除吗？",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										mPicsLayout.removeView(picture);
										pics.remove(v.getTag());
										//
										tempFile=null;
									}
								});
					}
				});*/
			/*	// 弹出查看
				picture.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						DialogUtil.showDefaultProgressDialog(NewZfCheckAddActivity.this);
						// 弹出传递照片地址
						showPicture(v.getTag().toString());
					}
				});*/}
            } catch (Exception e) {
                LogUtil.i("error:" + e.getMessage());
            }
        }
    }
    private File tempFile;// 拍摄缓存照片文件
    private List<File> pics = new ArrayList<File>(0);// 所有照片

    private class MyTroubleAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private List<WgyHiddenItemInfo> mDatas;
        private TroubleViewHodler mHodler;

        public MyTroubleAdapter(Context context, List<WgyHiddenItemInfo> mDatas2) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mDatas = mDatas2;
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
                convertView = mInflater.inflate(
                        R.layout.list_view_string_and_photo_delete_item, null);
                mHodler = new TroubleViewHodler();
                mHodler.mTextView1 = (TextView) convertView
                        .findViewById(R.id.et_left);
                mHodler.iv_now = (ImageView) convertView
                        .findViewById(R.id.iv_now);
                mHodler.btn_delete = convertView
                        .findViewById(R.id.btn_delete);
                convertView.setTag(mHodler);

            }
            mHodler = (TroubleViewHodler) convertView.getTag();
            mHodler.iv_now.setImageResource(R.drawable.wgy_pic);

            mHodler.mTextView1.setText(mDatas.get(position).getDes());
            mPosition = position;

            if (listHiddens.get(position).getImageFile() != null){
                //x.image().bind(mHodler.iv_now, listHiddens.get(position).getImageFile().getAbsolutePath(), getImageOptions());
                Bitmap bm = BitmapFactory.decodeFile(listHiddens.get(position).getImageFile().getAbsolutePath());
                //将图片显示到ImageView中
                mHodler.iv_now.setImageBitmap(bm);
            }

            mHodler.iv_now.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (pics.size() < 10) {
                        // 调用手机系统相机
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tempFile = new File(AppConfig.buildPath(AppConfig.HOME_CACHE), UUID
                                .randomUUID().toString().replace("-", "")
                                + ".jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, mTakePictureRequestCode);
                        }}else{
                        DialogUtil.showMsgDialog(NewZfCheckAddActivity.this, "最大上传图片数量为10张，无法再继续拍照，谢谢。", false, null);
                    }
                }
            });

            mHodler.btn_delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listHiddens.get(position) != null){
                        listHiddens.remove(position);
                        myTroubleAdapter.notifyDataSetChanged();
                    }
                }
            });
            return convertView;
        }
    }

    private class TroubleViewHodler {
        TextView mTextView1;
        ImageView iv_now;
        View btn_delete;
    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mLoading.dismiss();
        switch (requestCode) {
            case Const.HAVE_PROBLEM:
                // TODO
                if (mJsonResult.getEntity() != null) {

                    DialogUtil.showMsgDialog2(this,
                            "由于该企业存在未整改的政府检查隐患，是否现在对该企业进行隐患复查？", "取消",
                            new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(
                                            NewZfCheckAddActivity.this,
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
                    //// FIXME: 2017/11/15
              /*      this.sendBroadcast(new Intent(
                            ZfCheckRecordListActivity.ACTION_UPDATE_LIST_CHECK_NEW));*/
                    DialogUtil.showMsgDialog(this, "新增成功", true, null);
                }
                break;

            case Const.NET_GET_COMPANY_ITEM_INFO_CODE:
                if (checkId != -1) {
                    loadingCheckDatas();//加载检查项
                }
                if (mJsonResult.getEntity() != null) {
                    CompanyDetailInfo companyDetailInfo = JSON.parseObject(
                            mJsonResult.getEntity(), CompanyDetailInfo.class);
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

                    if (mJsonResult.getJson() == null||mJsonResult.getJson().toString().equals("[]")) {

                    } else {
                        tv_null.setVisibility(View.GONE);
                        lv_checkcontent.setVisibility(View.VISIBLE);
                        //TODO
                        mPicScrollView.setVisibility(View.VISIBLE);
                        myUserIds = JSONArray.parseArray(
                                (String) mJsonResult.getJson(), UserIds.class);

                        if (myUserIds == null || myUserIds.size() == 0) {

                        } else {
                            mDatas.addAll(myUserIds);
                            //TODONEW
                            for ( int i = 0; i < mDatas.size(); i++) {
                                if(inflater == null){
                                    inflater = LayoutInflater.from(this);
                                }
                                final RelativeLayout gridLayout = (RelativeLayout) inflater
                                        .inflate(R.layout.wgy_grid_select, null);
                                CheckBox cb_checked = (CheckBox) gridLayout.findViewById(R.id.cb_checked);
                                TextView gridname = (TextView) gridLayout
                                        .findViewById(R.id.tv_gridname);
                                gridname.setText(mDatas.get(i).getGridName());
                                mPicsLayout.addView(gridLayout);
                                a=i;
                                cb_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        if (isChecked) {
                                            if (!listcb.contains(mDatas.get(a).getGridId())) {
                                                listcb.add(mDatas.get(a).getGridId());
                                            }
                                        }else {
                                            if (listcb.contains(mDatas.get(a).getGridId())) {
                                                listcb.remove(mDatas.get(a).getGridId());
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
                    listSafetyMatteys.addAll(JSON.parseArray((String) mJsonResult.getJson(),
                            SafetyMatter.class));
                    mAdapter.notifyDataSetChanged();
                }
                lv_checkcontent.finishLoading(false);

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
            default:
                break;
        }

    }
}

