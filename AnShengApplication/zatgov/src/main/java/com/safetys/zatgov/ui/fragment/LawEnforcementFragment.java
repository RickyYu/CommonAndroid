package com.safetys.zatgov.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class LawEnforcementFragment extends Fragment {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_right)
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
    //private AllFragment allFragment;
    private ComFragment comFragment;
    private GovFragment govFragment;

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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.title_right, R.id.iv_makelist, R.id.iv_checknext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right:
                if (curPos == 0) {
                    govFragment.update();
                } else if (curPos == 1) {
                    comFragment.update();
                }
                break;
            case R.id.iv_makelist:
                //检查表
        /*        Intent intent2= new Intent(getActivity(),WgyCheckListActivity.class);
                intent2.putExtra("isChecklist", "0");
                startActivity(intent2);*/
                break;
            case R.id.iv_checknext:
                //监督检查
            /*    Intent intent3=new Intent(getActivity(),WgyCheckListActivity.class);
                intent3.putExtra("isChecklist", "1");
                startActivity(intent3);*/
                break;
        }
    }
}
