package com.goldemperor.ShowCapacity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ShowCapacity.model.AbnormityDetailModel;
import com.goldemperor.ShowCapacity.model.DayWorkCardReportModel;
import com.goldemperor.ShowCapacity.model.HourAllInfoModel;
import com.goldemperor.ShowCapacity.model.ProDayLeaveFourSizeReportSource;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Widget.DashboardView;
import com.goldemperor.Widget.verticalviewpager.ViewPagerFragment;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * File Name : CapacityDetailsFragment
 * Created by : PanZX on  2018/10/16 13:38
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.fragment_capacity_details)
public class CapacityDetailsFragment extends ViewPagerFragment {

    @ViewInject(R.id.DV_Capacity)
    private DashboardView DV_Capacity;

    @ViewInject(R.id.BC_Capacity)
    private BarChart BC_Capacity;

    @ViewInject(R.id.PC_Capacity)
    private PieChart PC_Capacity;

    @ViewInject(R.id.LL_Capacity)
    private LinearLayout LL_Capacity;

    @ViewInject(R.id.TV_Target)
    private TextView TV_Target;

    @ViewInject(R.id.TV_Complete)
    private TextView TV_Complete;

    @ViewInject(R.id.TV_QualificationRate)
    private TextView TV_QualificationRate;

    @ViewInject(R.id.TV_StandardTimeCapacity)
    private TextView TV_StandardTimeCapacity;


    @ViewInject(R.id.TV_ActualTimeCapacity)
    private TextView TV_ActualTimeCapacity;

    @ViewInject(R.id.TV_PerTimeCapacity)
    private TextView TV_PerTimeCapacity;

    @ViewInject(R.id.TV_ProductionType)
    private TextView TV_ProductionType;

    @ViewInject(R.id.TV_PeopleCount)
    private TextView TV_PeopleCount;


    @ViewInject(R.id.TV_MustPeopleCount)
    private TextView TV_MustPeopleCount;


    @ViewInject(R.id.IV_Picture)
    private ImageView IV_Picture;

    @ViewInject(R.id.TV_Name)
    private TextView TV_Name;

    @ViewInject(R.id.LL_Title)
    private LinearLayout LL_Title;


    private Activity mActivity;


    private DayWorkCardReportModel DWDRM;
    private List<AbnormityDetailModel> ADML;
    private HourAllInfoModel HAIML;
    private List<ProDayLeaveFourSizeReportSource> PDLFSRS;
//    private CapacityAdapter CA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = x.view().inject(this, inflater, container);
            mActivity = getActivity();


        }

        return rootView;
    }

    /**
     * 接收扇形图数据
     *
     * @param adm
     */
    public void setAbnormityDetailData(List<AbnormityDetailModel> adm) {
        LOG.e("setAbnormityDetailData");
        ADML = adm;
        ViewUtils.InitPieChar(PC_Capacity);

        setPieChartData(ADML);
    }

    /**
     * 接收产能数据
     *
     * @param dwcrm
     */
    public void setDayWorkCardReportData(DayWorkCardReportModel dwcrm) {
        LOG.e("setDayWorkCardReportData");
        DWDRM = dwcrm;
        setDashboardViewData(DWDRM);


    }

    /**
     * 接收条形图数据
     *
     * @param haiml
     */
    public void setHourAllInfoData(HourAllInfoModel haiml) {
        LOG.e("setDayWorkCardReportData");
        HAIML = haiml;
        ViewUtils.InitBarChart(BC_Capacity);
        setBarChartData(HAIML);
    }

    /**
     * 接收列表
     *
     * @param pdlfsrs
     */
    public void setCapacityListData(List<ProDayLeaveFourSizeReportSource> pdlfsrs) {
        LOG.e("setCapacityListData");
        PDLFSRS = pdlfsrs;
        LL_Title.removeAllViews();
        LL_Capacity.removeAllViews();
        for (ProDayLeaveFourSizeReportSource.data2 data : PDLFSRS.get(0).getAllSizeList().get(0).getSizeQtyList()) {
            if (data.getSize().equals("指令号")) {
                continue;
            }
            LL_Title.addView(ViewUtils.getListTitleView(mActivity, data));
        }
        for (ProDayLeaveFourSizeReportSource pdlfsr : PDLFSRS) {
            LL_Capacity.addView(ViewUtils.getListDataView(mActivity,pdlfsr));
        }
    }





    /**
     * 设置仪表盘数据
     *
     * @param dwdrm
     */
    private void setDashboardViewData(DayWorkCardReportModel dwdrm) {
        LOG.e("setDashboardViewData");
        if (dwdrm == null) {
            return;
        }
        double WorkRate = dwdrm.getWorkRate();
        TV_Name.setText("组长:" + dwdrm.getFName());
        Picasso.get()
                .load(dwdrm.getPicture())
                .placeholder(R.mipmap.downloading_photo)
                .error(R.drawable.loading_failure)
                .into(IV_Picture);
        TV_Target.setText("今日计划: " + dwdrm.getNumberOfGoalsToday() + " PAA");
        TV_Complete.setText("今日达成: " + dwdrm.getNumberOfCompletions() + " PAA");
        TV_QualificationRate.setText("合格率: " + dwdrm.getQualifiedRate() + "%");
        TV_StandardTimeCapacity.setText("标准时产能: " + dwdrm.getStandardTimeCapacity() + "PAA/H");
        TV_ActualTimeCapacity.setText("实际时产能: " + dwdrm.getActualTimeCapacity() + "PAA/H");
        TV_PerTimeCapacity.setText("人均时产能: " + dwdrm.getPerTimeCapacity() + "PAA/H");
        TV_ProductionType.setText("生产类型: " + dwdrm.getProductionType());
        TV_PeopleCount.setText("班组人数: " + dwdrm.getFMustPeopleCount());
        TV_MustPeopleCount.setText("实际出勤: " + dwdrm.getFPeopleCount());
//        if (WorkRate >= 100) {
//            DV_Capacity.setCreditValueWithAnim(100);
//        } else if (WorkRate < 0) {
//            DV_Capacity.setCreditValueWithAnim(0);
//        } else {.
//        }
        DV_Capacity.setCreditValueWithAnim((int) WorkRate);
    }

    /**
     * 设置条形图数据
     *
     * @param haiml
     */
    private void setBarChartData(HourAllInfoModel haiml) {
        LOG.e("setBarChartData");
        if (haiml == null) {
            return;
        }
        LOG.e("HourAllInfoModel="+haiml.getData().size());
        BC_Capacity.getXAxis().setValueFormatter((value, axis) -> haiml.getData().get((int) value).getTime());
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < haiml.getData().size(); i++) {
            yValues.add(new BarEntry(i, new float[]{haiml.getData().get(i).getUnQualified(), haiml.getData().get(i).getQualified()}));
        }
        // y 轴数据集
        BarDataSet barDataSet = new BarDataSet(yValues, "");
        // 设置 对应数据 颜色
        barDataSet.setValueTextSize(20);
        barDataSet.setColors(Color.parseColor("#D04040"), Color.parseColor("#5FE025"));
//        String[] labels = {"不合格", "合格"};
//        barDataSet.setStackLabels(labels);
        BarData mBarData = new BarData(barDataSet);
        LOG.e("BarData");
        BC_Capacity.setData(mBarData);
        ViewUtils.setInitBarLine(BC_Capacity, haiml.getLine());
        ViewUtils.setYmax(BC_Capacity, haiml);

    }


    /**
     * 设置扇形图数据
     *
     * @param ADML
     */
    private void setPieChartData(List<AbnormityDetailModel> ADML) {
        LOG.e("setPieChartData");
        if (ADML == null) {
            return;
        }
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (AbnormityDetailModel adm : ADML) {
            entries.add(new PieEntry(adm.getFQty(), adm.getFName()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ViewUtils.getColor());

        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(mTfLight);
        PC_Capacity.setData(data);
        // undo all highlights
        PC_Capacity.highlightValues(null);

        PC_Capacity.invalidate();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            onResume();
        } else if (!isVisibleToUser) {
            onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
//            TV_Name.setText("组长:"+dataPref.getString(define.SharedUser,""));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
