package com.safetys.zatgov.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.MyFragmentAdapter;
import com.safetys.zatgov.adapter.MyGridAdapter;
import com.safetys.zatgov.entity.GridBtnData;
import com.safetys.zatgov.ui.activity.EnterpriseListActivity;
import com.safetys.zatgov.ui.activity.MapActivity;
import com.safetys.zatgov.ui.activity.NewZfPunListActivity;
import com.safetys.zatgov.ui.activity.WgyCheckListActivity;
import com.safetys.zatgov.ui.activity.ZfReviewCompanyListActivity;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;

import static com.safetys.zatgov.R.id.title_right;


/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class LawEnforcementFragment extends Fragment implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener{
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(title_right)
    TextView titleRight;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabhost)
    FragmentTabHost tabhost;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.iv_makelist)
    ImageView ivMakelist;
    @BindView(R.id.iv_checknext)
    ImageView ivChecknext;
    Unbinder unbinder;
    private LoadingDialogUtil mloading;
    private int curPos = 0;
    private ComFragment comFragment;
    private GovFragment govFragment;
    private LayoutInflater layoutInflater;
    private List<Fragment> list = new ArrayList<Fragment>();
    private String textViewArray[] = { "", ""};
    private Class fragmentArray[] = { GovFragment.class, ComFragment.class};
    private int imageViewArray[] = { R.drawable.cir_orange_select,
            R.drawable.cir_orange_select, R.drawable.cir_orange_select };
    private MyGridAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_law_enforcement, null);
        unbinder = ButterKnife.bind(this, mView);
        initView(mView);
        mloading = new LoadingDialogUtil(getActivity());
        return mView;

    }

    private void initView(View mView) {
        title.setText("行政执法");
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("刷新");
        btnBack.setVisibility(View.GONE);
        pager.setOnPageChangeListener(this);
        layoutInflater = LayoutInflater.from(getActivity());
        tabhost.setup(getActivity(), getChildFragmentManager(), R.id.pager);
        tabhost.setOnTabChangedListener(this);
        int count = textViewArray.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(textViewArray[i])
                    .setIndicator(getTabItemView(i));
            tabhost.addTab(tabSpec, fragmentArray[i], null);
            tabhost.setTag(i);
        }
        tabhost.getTabWidget().getChildAt(0).findViewById(R.id.tab_imageview)
                .setPressed(true);
        comFragment = new ComFragment();
        govFragment = new GovFragment();
        list.add(govFragment);
        list.add(comFragment);
        pager.setAdapter(new MyFragmentAdapter(getFragmentManager(), list));

        ArrayList<GridBtnData> mDatas = new ArrayList<GridBtnData>();
        mDatas.add(new GridBtnData(R.drawable.law_btn_jcb_selector, "我的检查表", 0));
        mDatas.add(new GridBtnData(R.drawable.law_btn_jcjl_selector, "监督检查", 0));
        mDatas.add(new GridBtnData(R.drawable.law_btn_yhfc_selector, "政府复查", 0));
        mDatas.add(new GridBtnData(R.drawable.law_btn_xzcf_selector, "行政处罚", 0));
        mDatas.add(new GridBtnData(R.drawable.law_btn_lsjl_selector, "历史记录", 0));
        mDatas.add(new GridBtnData(R.drawable.law_btn_qyxx_selector, "企业管理", 0));
        mDatas.add(new GridBtnData(R.drawable.law_btn_qyyh_selector, "企业隐患", 0));
        mDatas.add(new GridBtnData(R.drawable.law_btn_dtdh_selector, "地图导航", 0));

        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new MyGridAdapter(getActivity(), mDatas);
        gridview.setAdapter(mAdapter);
    }

    private View getTabItemView(int i) {
        View view = layoutInflater.inflate(R.layout.tab_content, null);
        ImageView mImageView = (ImageView) view
                .findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setImageResource(imageViewArray[i]);
        mTextView.setText(textViewArray[i]);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.gridview)
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id){
        switch (position){
            case 0:
                // 检查表
                Intent intent2= new Intent(getActivity(),WgyCheckListActivity.class);
                intent2.putExtra("isChecklist", "0");
                startActivity(intent2);
                break;
            case 1:
                //监督检查
                Intent mIntent = new Intent(getActivity(),
                        EnterpriseListActivity.class);
                mIntent.putExtra("skipType", EnterpriseListActivity.SKIP_SUPERVISE_CHECKT);
                mIntent.putExtra("source", "checkTable");
                startActivity(mIntent);
                break;
            case 2:
                // 政府复查
                Intent mIntent1 = new Intent(getActivity(),
                        ZfReviewCompanyListActivity.class);
                startActivity(mIntent1);
                break;
            case 3:
                // 行政处罚
                Intent mIntent2 = new Intent(getActivity(),
                        NewZfPunListActivity.class);
                startActivity(mIntent2);

                break;
            case 4:
                //历史记录
                Intent mIntent0 = new Intent(getActivity(),
                        EnterpriseListActivity.class);
                mIntent0.putExtra(EnterpriseListActivity.SKIP_TYPR,
                        EnterpriseListActivity.SKIP_CHECK_RECORD_LIST_EXPANDABLE);
                startActivity(mIntent0);
                break;
            case 5:
                // 企业管理
                Intent mIntent5 = new Intent(getActivity(),
                        EnterpriseListActivity.class);
                mIntent5.putExtra(EnterpriseListActivity.SKIP_TYPR,
                        EnterpriseListActivity.SKIP_COMPANY_INFO);
                startActivity(mIntent5);
                break;
            case 6:
                // 企业隐患

                Intent mIntent7 = new Intent(getActivity(),
                        EnterpriseListActivity.class);
                mIntent7.putExtra(EnterpriseListActivity.SKIP_TYPR,
                        EnterpriseListActivity.SKIP_COMPANY_HIDDEN_INFO);
                startActivity(mIntent7);
                break;
            case 7:
                // 地图导航
                Intent mIntent6 = new Intent(getActivity(),
                        MapActivity.class);
                startActivity(mIntent6);
                break;
            default:
                break;
        }
    }

    @OnClick(title_right)
    public void onViewClicked(View view) {
                if (curPos == 0) {
                    govFragment.update();
                } else if (curPos == 1) {
                    comFragment.update();
                }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = tabhost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        tabhost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);
        tabhost.getTabWidget().getChildAt(position)
                .findViewById(R.id.tab_imageview).setPressed(true);
        curPos = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int position = tabhost.getCurrentTab();
        pager.setCurrentItem(position);
    }
}
