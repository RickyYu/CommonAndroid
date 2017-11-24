package com.safetys.zatgov.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.safetys.widget.common.SPUtils;
import com.safetys.widget.common.ToastUtils;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.CheckItemInfo;
import com.safetys.zatgov.bean.HyCheckItemInfo;
import com.safetys.zatgov.bean.ImageInfo;
import com.safetys.zatgov.bean.UserIds;
import com.safetys.zatgov.config.AppConfig;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.config.PrefKeys;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.entity.MessageEvent;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.ui.view.PullToRefresh;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:检查记录详情
 */
public class NewZfCheckItemActivity extends BaseActivity implements
        View.OnClickListener, onNetCallback {
    public static final String ACTION_UPDATE = "cn.saftys.NewZfCheckItemActivity.updateYH";
    private String id2;
    private TextView tv_name;
    private TextView tv_checktime;
    private TextView tv_address;
    private TextView tv_place;
    private TextView tv_lxr;
    private TextView tv_phone;
    private TextView tv_people;
    private TextView tv_join;
    private TextView tv_law;
    private TextView tv_now;
    private TextView tv_zgtime;
    private TextView tv_send;
    private ImageView iv0;
    private String companyid;
    private View mBtn_back;
    private LoadingDialogUtil mLoading;
    private PullToRefresh list_yh;
    private View title_right;
    private View btn_chufa;
    LayoutInflater inflater = null;
    private HorizontalScrollView mPicScrollView;
    private LinearLayout mPicsLayout;
    private MyListAdapter madapter;// 隐患描述列表数据适配
    private ArrayList<HyCheckItemInfo> mDatas;// 隐患描述
    private int mCurrentPage = 0;// 当前显示页数量
    private int totalCount = 0;// 总数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_detail_new);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        setHeadTitle("记录详情");
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_checktime = (TextView) findViewById(R.id.tv_checktime);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_place = (TextView) findViewById(R.id.tv_place);
        tv_lxr = (TextView) findViewById(R.id.tv_lxr);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_people = (TextView) findViewById(R.id.tv_people);
        tv_join = (TextView) findViewById(R.id.tv_join);
        tv_law = (TextView) findViewById(R.id.tv_law);
        tv_now = (TextView) findViewById(R.id.tv_now);
        tv_zgtime = (TextView) findViewById(R.id.tv_zgtime);
        tv_send = (TextView) findViewById(R.id.tv_send);
        iv0 = (ImageView) findViewById(R.id.iv0);

        time = findViewById(R.id.time);
        send = findViewById(R.id.send);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        ll_content = findViewById(R.id.ll_content);
        title_right = findViewById(R.id.btn_add);

        title_right.setOnClickListener(this);
        btn_chufa = findViewById(R.id.btn_chufa);
        btn_chufa.setOnClickListener(this);
        btn_check_type = findViewById(R.id.btn_check_type);
        btn_check_type.setOnClickListener(this);
        tv_type = (TextView) findViewById(R.id.tv_type);
        Intent intent = this.getIntent();
        have = intent.getExtras().getString("right");
        if (have.equals("2")) {
            btn_chufa.setVisibility(View.GONE);
            title_right.setVisibility(View.GONE);

        }
        tv_name.setTextColor(Color.GRAY);
        tv_checktime.setTextColor(Color.GRAY);
        tv_address.setTextColor(Color.GRAY);
        tv_place.setTextColor(Color.GRAY);
        tv_lxr.setTextColor(Color.GRAY);
        tv_phone.setTextColor(Color.GRAY);
        tv_people.setTextColor(Color.GRAY);
        tv_join.setTextColor(Color.GRAY);
        tv_law.setTextColor(Color.GRAY);
        tv_now.setTextColor(Color.GRAY);
        tv_zgtime.setTextColor(Color.GRAY);
        tv_send.setTextColor(Color.GRAY);
        tv_type.setTextColor(Color.GRAY);

        if ((boolean)SPUtils.getData(PrefKeys.PREF_USER_TYPE_KEY, false)) {
            title_right.setVisibility(View.GONE);
            btn_chufa.setVisibility(View.GONE);
        }
        id2 = intent.getExtras().getString("id");
        companyid = intent.getExtras().getString("id2");

        mLoading = new LoadingDialogUtil(this);

        mBtn_back = findViewById(R.id.btn_back);
        mBtn_back.setOnClickListener(this);
        list_yh = (PullToRefresh) findViewById(R.id.list_yh);
        list_yh.setOnRefreshListener(new PullToRefresh.OnRefreshListener() {

            @Override
            public void onLoadingMore() {
                // 加载更多
                if (mCurrentPage > totalCount) {
                    ToastUtils.showMessage(NewZfCheckItemActivity.this,
                            "没有更多的数据了。");
                    list_yh.finishLoading(false);
                } else {
                    loadingHiddenDesListDatas();
                }
            }
        });

        mDatas = new ArrayList<HyCheckItemInfo>();
        madapter = new MyListAdapter(this, mDatas);

        mPicScrollView = (HorizontalScrollView) findViewById(R.id.scrollview1);
        mPicsLayout = (LinearLayout) findViewById(R.id.photos1);

        mLoading.show();
        loadingCheckListInfos();
        loadingHiddenDesListDatas();

        list_yh.setAdapter(madapter);
        list_yh.setScrollingCacheEnabled(true);
        list_yh.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private Intent intent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (mDatas.get(position).getIsBig().equals("0")) {

                    intent = new Intent(NewZfCheckItemActivity.this,
                            NoTroubleModifyActivity.class);

                } else {
                    intent = new Intent(NewZfCheckItemActivity.this,
                            NoMajorChangeActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", mDatas.get(position).getId() + "");
                bundle.putString("noteid", id2);
                bundle.putString("companyid", companyid);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    private void loadingCheckListInfos() {
        HttpRequestHelper.getInstance().getCheckDetail(this, id2, this);

    }

    /**
     * 获取隐患描述列表
     */
    private void loadingHiddenDesListDatas() {

        HttpRequestHelper.getInstance().getCheckYhListInfo(this, id2,
                Const.NET_GET_GOV_HIDDEN_LIST_CODE, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check_type:
           /*     //// FIXME: 2017/11/16
                Intent intent = new Intent(this, NewZfCheckItemHyActivity.class);
                intent.putExtra("id", id2);
                intent.putExtra("name", type);
                startActivity(intent);*/

                break;
            case R.id.btn_add:
                if (isNull) {
                    mIntent = new Intent(NewZfCheckItemActivity.this,
                            TroubleRegisterActivity.class);
                    isNull = false;
                } else {
                    // 新增隐患
                    mIntent = new Intent(NewZfCheckItemActivity.this,
                            NewZfYhLrActivity.class);
                }

                Bundle bundle = new Bundle();
                bundle.putString("noteid", id2);
                bundle.putString("id", companyid);
                bundle.putString("right", have);

                mIntent.putExtras(bundle);
                startActivity(mIntent);
                break;
            case R.id.btn_chufa:
                // 新增chufa
                Intent mIntent2 = new Intent(NewZfCheckItemActivity.this,
                        NewPunishmentAddActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("checkId", id2);
                bundle2.putString("companyId", companyid);
                mIntent2.putExtras(bundle2);
                startActivity(mIntent2);
                finish();
                break;

            case R.id.btn_back:
                finish();
                break;

		/*
		 * case R.id.title_right: // Intent intent =new
		 * Intent(NewZfCheckItemActivity.this,TroubleRegisterActivity.class);
		 * Intent intent =new
		 * Intent(NewZfCheckItemActivity.this,NewZfCheckAddActivity.class);
		 * Bundle bundle=new Bundle(); bundle.putString("id", companyid); //
		 * intent.putExtras(bundle); startActivity(intent);
		 *
		 * break;
		 */
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
                        R.layout.list_view_string_three_enabled, null);
                mHodler = new ViewHodler();
                mHodler.mTextView1 = (TextView) convertView
                        .findViewById(R.id.text1);
                mHodler.mTextView2 = (TextView) convertView
                        .findViewById(R.id.text2);
                mHodler.mTextView3 = (TextView) convertView
                        .findViewById(R.id.text3);

            }
            mHodler.mTextView1.setText(mDatas.get(position).getContent());
            mHodler.mTextView2.setText("录入日期："
                    + mDatas.get(position).getCreateTime());
            if (mDatas.get(position).isRepaired()) {
                mHodler.mTextView3.setText("已整改");
            } else {
                mHodler.mTextView3.setText("未整改");
            }
            return convertView;
        }
    }

    private class ViewHodler {
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
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
            case Const.NET_GET_GOV_HIDDEN_LIST_CODE:// 获取隐患描述列表
                if (mJsonResult.getJson() == null
                        || mJsonResult.getJson().toString().equals("[]")) {
                    if (mDatas.size() == 0) {
                        // Toast.makeText(this, "隐患列表没有数据",
                        // Toast.LENGTH_SHORT).show();
                        time.setVisibility(View.GONE);
                        send.setVisibility(View.GONE);
                        t1.setVisibility(View.GONE);
                        t2.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(this, "隐患列表没有更多数据", Toast.LENGTH_SHORT)
                                .show();
                    }
                    isNull = true;
                } else {

                    mCurrentPage = mCurrentPage + 10;
                    totalCount = mJsonResult.getTotalCount();
                    List<HyCheckItemInfo> list = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), HyCheckItemInfo.class);
                    if (list == null || list.size() == 0) {
                        isNull = true;
                    } else {
                        mDatas.addAll(list);
                    }
                }

                madapter.notifyDataSetChanged();
                list_yh.finishLoading(false);
                break;

            case 0:
                if (mJsonResult.getEntity() == null
                        || mJsonResult.getEntity().toString().equals("[]")) {
                    Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                } else {
                    CheckItemInfo checkItemInfo = JSON.parseObject(
                            mJsonResult.getEntity(), CheckItemInfo.class);
                    tv_name.setText(checkItemInfo.getCompanyName());
                    String content = checkItemInfo.getContent();
                    type = checkItemInfo.getCheckTable();
                    tv_checktime.setText(checkItemInfo.getCheckTimeBegin());
                    tv_address.setText(checkItemInfo.getAddress());
                    tv_place.setText(checkItemInfo.getCheckGround());
                    tv_lxr.setText(checkItemInfo.getFdDelegate());
                    tv_phone.setText(checkItemInfo.getFdDelegateLink());
                    tv_people.setText(checkItemInfo.getNoter());
                    tv_law.setText(checkItemInfo.getExecuteUnit());
                    tv_now.setText(content);
                    tv_zgtime.setText(checkItemInfo.getCleanUpTimeLimit());
                    if (content == null || content.isEmpty()
                            || content.equals("[]")) {
                        ll_content.setVisibility(View.GONE);
                    }
                    if (type == null || type.isEmpty() || type.equals("[]")) {
                        btn_check_type.setVisibility(View.GONE);
                    } else {
                        btn_check_type.setVisibility(View.VISIBLE);
                        tv_type.setText(type);
                    }
                    if (checkItemInfo.getSendCleanUp()) {
                        tv_send.setText("是");
                    } else {
                        tv_send.setText("否");
                    }

                    LinkedList<String> list = new LinkedList<String>();
                    List<UserIds> userIds = JSONArray.parseArray(
                            (String) mJsonResult.getJson(), UserIds.class);
                    for (int i = 0; i < userIds.size(); i++) {
                        if (userIds.get(i).isChecked()) {
                            list.add(userIds.get(i).getGridName() + " ");
                        }

                    }
                    tv_join.setText(list.toString().substring(1,
                            list.toString().length() - 1));

                    mPicsLayout.removeAllViews();
                    List<ImageInfo> mImages = checkItemInfo.getImagesInfo();
                    if (mImages != null) {
                        for (int i = 0; i < mImages.size(); i++) {
                            if (inflater == null) {
                                inflater = LayoutInflater.from(this);
                            }
                            final RelativeLayout picture = (RelativeLayout) inflater
                                    .inflate(R.layout.photo, null);
                            ImageView photo = (ImageView) picture
                                    .findViewById(R.id.photo);
                            ImageButton delete = (ImageButton) picture
                                    .findViewById(R.id.delete);
                            delete.setVisibility(View.GONE);
                            // 此处兼容本地图片地址以及网络url地址
                            String photoPath = AppConfig.HOST_ADDRESS_YH
                                    + mImages.get(i).getPath();
                            picture.setTag(photoPath);

                            x.image().bind(photo, photoPath, getImageOptions());
                            picture.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                             
                                    showPicture(v.getTag().toString());
                                }
                            });
                            mPicsLayout.addView(picture);
                        }
                    }

                }
                break;
            default:
                break;
        }
    }

    /*	private ImageOptions mImageOptions;*/
    private boolean isNull;
    private Intent mIntent;
    private String have;

    private View time;

    private View t1;

    private View send;

    private View t2;

    private View ll_content;

    private View btn_check_type;

    private TextView tv_type;

    private String type;

/*	private ImageOptions getImageOptions() {
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
	}*/

    // 弹出查看照片
    protected void showPicture(String picPath) {
        Intent intent = new Intent(this, ViewPhotoActivity.class);
        intent.putExtra("picPath", picPath);
        startActivity(intent);
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
        madapter.notifyDataSetChanged();
        mLoading.show();
        loadingHiddenDesListDatas();

    }
}
