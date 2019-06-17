package com.example.a11708.graduationproject.View;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.a11708.graduationproject.Beans.TimeUsingBean;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.Utils.DeviceUtil;
import com.example.a11708.graduationproject.Utils.SharedPreferencesUtils;
import com.example.a11708.graduationproject.model.SqlDaoModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PactureMakerFragment extends Fragment implements OnChartValueSelectedListener {

    private String mUsername;
    private View mView;
    private PieChart mPieChart;
    private List<TimeUsingBean> mList = new ArrayList<>();
    //中间体文字
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("我的计划");
        return s;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mList != null){
            initData();
            int[] myClassification = Classification();
            ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
            if(myClassification[0] != 0) {
                entries.add(new PieEntry(myClassification[0], "工作"));
            }
            if(myClassification[1] != 0) {
                entries.add(new PieEntry(myClassification[1], "学习"));
            }
            if(myClassification[2] != 0) {
                entries.add(new PieEntry(myClassification[2], "休息"));
            }
            if(myClassification[3] != 0) {
                entries.add(new PieEntry(myClassification[3], "其他"));
            }
            setData(entries);
            mPieChart.notifyDataSetChanged();
            mPieChart.invalidate();
        }

    }

    public PactureMakerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_pacture_maker, container, false);
        } else {
            ViewParent viewParent = mView.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeAllViews();
            }
        }
        initView();
        //getUserInfo();
        return mView;
    }

    private void initView() {
        mPieChart = (PieChart)mView.findViewById(R.id.mPieChart);
        //xy轴动画
        mPieChart.animateXY(1400, 1400);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);
        //模拟数据
        int[] myClassification = Classification();
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if(myClassification[0] != 0) {
            entries.add(new PieEntry(myClassification[0], "工作"));
        }
        if(myClassification[1] != 0) {
            entries.add(new PieEntry(myClassification[1], "学习"));
        }
        if(myClassification[2] != 0) {
            entries.add(new PieEntry(myClassification[2], "休息"));
        }
        if(myClassification[3] != 0) {
            entries.add(new PieEntry(myClassification[3], "其他"));
        }
        setData(entries);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);
        
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onResume();
    }

    private int[] Classification(){
        mList = initData();
        int[] myclass = new int[4];
        double work = 0;
        double study = 0;
        double rest = 0;
        double other = 0;
        for(int i = 0;i < mList.size();i++){
            if(mList.get(i).getmTitle().equals("工作")){
                work++;
            }else if(mList.get(i).getmTitle().equals("学习")){
                study++;
            }else if(mList.get(i).getmTitle().equals("休息")){
                rest++;
            }else{
                other++;
            }
        }
        double all = work + study + rest + other;
        myclass[0] = (int) ((work/all)*100);
        myclass[1] = (int) ((study/all)*100);
        myclass[2] = (int) ((rest/all)*100);
        myclass[3] = (int) ((other/all)*100);
        return myclass;
    }

    private List<TimeUsingBean> initData() {
        mUsername = SharedPreferencesUtils.getValue(Constant.SpKey.USER_NAME);
        if(mUsername.equals("")) {
            mUsername = DeviceUtil.getAndroidId(getContext());
        }
        List<TimeUsingBean> datalist = new ArrayList<>();
        SqlDaoModel model = new SqlDaoModel(getActivity());
        datalist = model.findAll(mUsername);
        return  datalist;
    }

    private void setData(ArrayList<PieEntry> entries) {

        PieDataSet dataSet = new PieDataSet(entries, "计划分类");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
