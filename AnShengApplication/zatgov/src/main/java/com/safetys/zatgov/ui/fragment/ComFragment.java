package com.safetys.zatgov.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.safetys.zatgov.R;
import com.safetys.zatgov.bean.BarChart2s;
import com.safetys.zatgov.bean.MathInfo;
import com.safetys.zatgov.bean.MonthCounts;
import com.safetys.zatgov.config.Const;
import com.safetys.zatgov.entity.JsonResult;
import com.safetys.zatgov.http.HttpRequestHelper;
import com.safetys.zatgov.http.onNetCallback;
import com.safetys.zatgov.utils.DialogUtil;
import com.safetys.zatgov.utils.LoadingDialogUtil;

import java.util.List;

/**
 * Author:Created by Ricky on 2017/11/14.
 * Description:
 */
public class ComFragment extends Fragment implements View.OnClickListener, onNetCallback {

    private BarChart2s mBarChart3s;
    //	private TextView t1;
//	private TextView t2;
    private TextView t3;
    private TextView t4;
    private LoadingDialogUtil mloading;
    private List<MonthCounts> monthCounts;
    private Float f1;
    private Float f2;
    private Float f3;
    private Float f4;
    private Float f5;
    private Float f6;
    private Float f12;
    private Float f22;
    private Float f32;
    private Float f42;
    private Float f52;
    private Float f62;
    private String m1;
    private String m2;
    private String m3;
    private String m4;
    private String m5;
    private String m6;
    private BarChart chart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_com, null);
        initView(mView);
        mloading = new LoadingDialogUtil(getActivity());
        update();
        return mView;
    }

    public void update() {
      //  mloading.show();
        HttpRequestHelper.getInstance().getComCount(getActivity(),
                Const.NET_GET_COM_CODE, this);
    }

    private void initView(View mRootView) {

//		t1 = (TextView) mRootView.findViewById(R.id.text_num_1);
//		t2 = (TextView) mRootView.findViewById(R.id.text_num_2);
        t3 = (TextView) mRootView.findViewById(R.id.text_num_3);
        t4 = (TextView) mRootView.findViewById(R.id.text_num_4);


        chart = (BarChart) mRootView.findViewById(R.id.chart);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:

                break;
            default:
                break;
        }
    }


    @Override
    public void onError(String errorMsg) {
        mloading.dismiss();
        DialogUtil.showMsgDialog(getActivity(), errorMsg, true, null);

    }

    @Override
    public void onSuccess(int requestCode, JsonResult mJsonResult) {
        mloading.dismiss();
        switch (requestCode) {
            case Const.NET_GET_COM_CODE:

                MathInfo mathInfo = JSON.parseObject(mJsonResult.getEntity(),
                        MathInfo.class);
                t3.setText( mathInfo.getDangerNum());
                t4.setText( mathInfo.getRectifyRateNum());
                monthCounts = mathInfo.getMonthCounts();
//自查数
                f1 = monthCounts.get(0).getByCom();
                f2 = monthCounts.get(1).getByCom();
                f3 = monthCounts.get(2).getByCom();
                f4 = monthCounts.get(3).getByCom();
                f5 = monthCounts.get(4).getByCom();
                f6 = monthCounts.get(5).getByCom();


                //整改数
                f12 = monthCounts.get(0).getRepairedNum();
                f22 = monthCounts.get(1).getRepairedNum();
                f32 = monthCounts.get(2).getRepairedNum();
                f42 = monthCounts.get(3).getRepairedNum();
                f52 = monthCounts.get(4).getRepairedNum();
                f62 = monthCounts.get(5).getRepairedNum();

                m1 = monthCounts.get(0).getDateMonth().substring(3);
                m2 = monthCounts.get(1).getDateMonth().substring(3);
                m3 = monthCounts.get(2).getDateMonth().substring(3);
                m4 = monthCounts.get(3).getDateMonth().substring(3);
                m5 = monthCounts.get(4).getDateMonth().substring(3);
                m6 = monthCounts.get(5).getDateMonth().substring(3);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mBarChart3s = new BarChart2s(chart);
                        BarData data = new BarData(mBarChart3s.getXAxisValues(m1,m2,m3,m4,m5,m6),
                                mBarChart3s.getDataSet(f1,f2,f3,f4,f5,f6,
                                        f12,f22,f32,f42,f52,f62));
                        // 设置数据
                        chart.setData(data);
                    }
                }, 1000); // 2秒


                break;

            default:
                break;
        }

    }
}
