package com.safetys.zatgov.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.safetys.zatgov.R;
import com.safetys.zatgov.adapter.InfomationListAdapter;
import com.safetys.zatgov.entity.InformationDataVo;
import com.safetys.zatgov.ui.activity.LawRegulationsActivity;
import com.safetys.zatgov.ui.activity.MsdsActivity;
import com.safetys.zatgov.ui.activity.SafetyNewsActivity;
import com.safetys.zatgov.ui.activity.TechnicalStandardActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class InfomationFragment extends Fragment {
    @BindView(R.id.btn_back)
    LinearLayout btnBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.listview)
    ListView listview;
    Unbinder unbinder;
    private InfomationListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_infomation, null);
        unbinder = ButterKnife.bind(this, mView);
        initView(mView);
        return mView;
    }

    private void initView(View mView) {
        title.setText("信息查询");
        btnBack.setVisibility(View.GONE);
        ArrayList<InformationDataVo> mdatas = new ArrayList<InformationDataVo>();
        mdatas.add(new InformationDataVo("法律法规",R.mipmap.icon_infomation_flfg));
        mdatas.add(new InformationDataVo("技术标准",R.mipmap.icon_infomation_jsbz));
        mdatas.add(new InformationDataVo("MSDS查询",R.mipmap.icon_infomation_msds));
        mdatas.add(new InformationDataVo("安监要闻",R.mipmap.icon_infomation_ajyw));
        mAdapter = new InfomationListAdapter(getActivity(), mdatas);
        listview.setAdapter(mAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        Intent mIntent0 = new Intent(getActivity(), LawRegulationsActivity.class);
                        startActivity(mIntent0);
                        break;
                    case 1:
                        Intent mIntent1 = new Intent(getActivity(), TechnicalStandardActivity.class);
                        startActivity(mIntent1);
                        break;
                    case 2:
                        Intent mIntent3 = new Intent(getActivity(), MsdsActivity.class);
                        startActivity(mIntent3);
                        break;
                    case 3:
                        Intent mIntent4 = new Intent(getActivity(), SafetyNewsActivity.class);
                        startActivity(mIntent4);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
