package com.safetys.zatgov.bean;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;

/**
 * Author:Created by Ricky on 2017/11/16.
 * Description:
 */
public class BarChartQy2s {
    private YAxis yAxis;
    private boolean isRes=false;

    public BarChartQy2s(BarChart chart) {
        // 数据描述
        chart.setDescription("");
        // 动画
        chart.animateY(1000);
        // 设置是否可以触摸
        chart.setTouchEnabled(false);
        // 是否可以拖拽
        chart.setDragEnabled(false);
        // 是否可以缩放
        chart.setScaleEnabled(false);
        // 集双指缩放
        chart.setPinchZoom(false);
        // 隐藏右边的坐标轴
        chart.getAxisRight().setEnabled(false);
        // 隐藏左边的左边轴
        chart.getAxisLeft().setEnabled(true);

        Legend mLegend = chart.getLegend(); // 设置比例图标示
        // 设置窗体样式
        mLegend.setForm(Legend.LegendForm.SQUARE);
        // 字体
        mLegend.setFormSize(4f);
        // 字体颜色
        mLegend.setTextColor(Color.parseColor("#7e7e7e"));
        // 网格背景颜色
        chart.setGridBackgroundColor(Color.parseColor("#AAC6D7"));

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setSpaceBetweenLabels(2);

        yAxis = chart.getAxisLeft();
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value
            ) {
                int n = (int) value;
                return n+"";
            }
        });
        yAxis.setAxisMaxValue(5f);
        yAxis.setLabelCount(4, false);
        chart.invalidate();
    }

    public ArrayList<BarDataSet> getDataSet(Float f1, Float f2, Float f3,
                                            Float f4, Float f5, Float f6, Float f12, Float f22, Float f32,
                                            Float f42, Float f52, Float f62) {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();

        setResMax(f1);
        setResMax(f2);
        setResMax(f3);
        setResMax(f4);
        setResMax(f5);
        setResMax(f6);
        setResMax(f12);
        setResMax(f22);
        setResMax(f32);
        setResMax(f42);
        setResMax(f52);
        setResMax(f62);

        // 隐患数量
        BarEntry v1e1 = new BarEntry(f1, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(f2, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(f3, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(f4, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(f5, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(f6, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<BarEntry>();
        BarEntry v2e1 = new BarEntry(f12, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(f22, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(f32, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(f42, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(f52, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(f62, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "隐患数量");
        barDataSet1.setColor(Color.parseColor("#00C0BF"));
        barDataSet1.setBarShadowColor(Color.parseColor("#01000000"));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "整改数量");
        // #00C0BF
        barDataSet2.setColor(Color.parseColor("#DEAD26"));
        barDataSet2.setBarShadowColor(Color.parseColor("#01000000"));
//		barDataSet1.setDrawValues(false);
//		barDataSet2.setDrawValues(false);

        barDataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value
            ) {
                int n = (int) value;
                return n+"";
            }
        });

        barDataSet2.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int n = (int) value;
                return n+"";
            }
        });
        dataSets = new ArrayList<BarDataSet>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        return dataSets;
    }

    public ArrayList<String> getXAxisValues(String m1, String m2, String m3,
                                            String m4, String m5, String m6) {
        ArrayList<String> xAxis = new ArrayList<String>();
        xAxis.add(m1 + "月");
        xAxis.add(m2 + "月");
        xAxis.add(m3 + "月");
        xAxis.add(m4 + "月");
        xAxis.add(m5 + "月");
        xAxis.add(m6 + "月");
        return xAxis;
    }

    public void setResMax(Float f) {
        if (isRes) {
        } else {

            if (f > 5f) {
                yAxis.resetAxisMaxValue();
                isRes = true;
            }
        }
    }
}
