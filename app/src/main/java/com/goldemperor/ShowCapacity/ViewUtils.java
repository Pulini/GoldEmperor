package com.goldemperor.ShowCapacity;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;
import com.goldemperor.ShowCapacity.model.HourAllInfoModel;
import com.goldemperor.ShowCapacity.model.ProDayLeaveFourSizeReportSource;
import com.goldemperor.Utils.LOG;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * File Name : ViewUtils
 * Created by : PanZX on  2018/10/20 15:44
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ViewUtils {


    /**
     * 初始化条形图
     */
    public static void InitBarChart(BarChart bc) {
        //显示边界
        bc.setDrawBorders(true);
        //图表的描述
        bc.getDescription().setText("");
        //关闭图标描述
        bc.getDescription().setEnabled(false);
        //设置数量超过20条就不显示值
        bc.setMaxVisibleValueCount(20);

        //关闭拖拽阴影
        bc.setDrawBarShadow(false);
        //关闭绘制背景
        bc.setDrawGridBackground(false);

        // X轴设置显示位置在底部
        bc.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置竖状的线是否显示
        bc.getXAxis().setDrawGridLines(false);

        // 添加动画效果
        bc.animateY(2500);
        //关闭底部文本
        bc.getLegend().setEnabled(false);
        //禁止触摸
        bc.setTouchEnabled(false);


    }

    public static void setInitBarLine(BarChart bc, int line) {
        LimitLine limitLine = new LimitLine(line); //得到限制线
        limitLine.setLabel("目标产能:" + line);
        limitLine.setLineWidth(3f); //宽度
        limitLine.setTextSize(15f);
        limitLine.setTextColor(Color.parseColor("#00AFE4"));  //颜色
        limitLine.setLineColor(Color.parseColor("#00AFE4"));
        bc.getAxisLeft().removeAllLimitLines();
        bc.getAxisLeft().addLimitLine(limitLine); //Y轴添加限制线
        LOG.e("绘制产能线");
//        bc.getAxisLeft().setAxisMinimum(line+100f);

    }

    public static void setYmax(BarChart bc, HourAllInfoModel line) {
        int max = 0;
        for (HourAllInfoModel.data data : line.getData()) {
            if (data.getQualified() > max) {
                max = data.getQualified();
            }
        }
        if (line.getLine() > max) {
            max = line.getLine();
        }
        bc.getAxisLeft().setAxisMaximum(max + 100f);
        bc.getAxisRight().setAxisMaximum(max + 100f);
        LOG.e("设置顶部高度");
    }

    /**
     * 初始化扇形图
     */
    public static void InitPieChar(PieChart pc) {


        pc.setUsePercentValues(false);
        pc.getDescription().setEnabled(false);
        pc.setExtraOffsets(5, 10, 5, 5);

        pc.setDragDecelerationFrictionCoef(0.95f);

//        pc.setCenterTextTypeface(mTfLight);
        pc.setCenterText("缺陷分类图");

        pc.setDrawHoleEnabled(true);
        pc.setHoleColor(Color.WHITE);

        pc.setTransparentCircleColor(Color.WHITE);
//        pc.setTransparentCircleAlpha(110);

        pc.setHoleRadius(40f);
        pc.setTransparentCircleRadius(45f);

        pc.setDrawCenterText(true);

        pc.setRotationAngle(0);
        // enable rotation of the chart by touch
        pc.setRotationEnabled(true);
        pc.setHighlightPerTapEnabled(true);

        // pc.setUnit(" €");
        // pc.setDrawUnitsInChart(true);

        // add a selection listener
//        pc.setOnChartValueSelectedListener(this);


        pc.animateY(1400);
        // pc.spin(2000, 0, 360);


        Legend l = pc.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pc.setEntryLabelColor(Color.BLACK);
//        pc.setEntryLabelTypeface(mTfRegular);
        pc.setEntryLabelTextSize(12f);

    }

    public static List<Integer> getColor() {
        List<Integer> colors = new ArrayList<Integer>();
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
//        LOG.e("颜色列表:" + colors.size());
        return colors;
    }

    public static String getCompletionRate(int Target, int Complete) {
        if (Target == 0 || Complete == 0) {
            return "0%";
        }

        double ratio = (double) Complete / (double) Target;
        LOG.e("百分比：" + ratio);
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(2);//设置保留几位小数
        return format.format(ratio);
    }


    public static TextView getListTitleView(Activity act, ProDayLeaveFourSizeReportSource.data2 data) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, Utils.dp2px(40));
        lp.weight = 1;
        TextView tv = new TextView(act);
        tv.setText(data.getSize());
        tv.setTextSize(20);
        tv.setTextColor(Color.parseColor("#333333"));
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundResource(R.drawable.shape_black);
        return tv;
    }

    public static View getListDataView(Activity act, ProDayLeaveFourSizeReportSource pdlf) {
        View view = LayoutInflater.from(act).inflate(R.layout.item_capacity, null);
        LinearLayout LL_BKG1 = view.findViewById(R.id.LL_BKG1);
        TextView TV_ItemID = view.findViewById(R.id.TV_ItemID);
        ImageView IV_Picture = view.findViewById(R.id.IV_Picture);
        LinearLayout LL_InterItem = view.findViewById(R.id.LL_InterItem);
        LinearLayout LL_Item = view.findViewById(R.id.LL_Item);

        TV_ItemID.setText(pdlf.getFItemID());
        Picasso.get().load(pdlf.getPicturePath()).placeholder(R.drawable.loading).error(R.drawable.loading_failure).into(IV_Picture);

        List<InterItemData> list = getInterList(pdlf.getAllSizeList());
        for (InterItemData data1 : list) {
            LL_InterItem.addView(getInterItemView(act, data1));
        }
        int i = 0;
        for (ProDayLeaveFourSizeReportSource.data1 data1 : pdlf.getAllSizeList()) {
            LL_Item.addView(getItemView(act, data1));
            i++;
        }
        LL_BKG1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dp2px(i * 40)));
        return view;
    }

    private static View getInterItemView(Activity act, InterItemData d) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(d.getNum() * 40));
        TextView tv = new TextView(act);
        tv.setText(d.getName());
        tv.setTextSize(20);
        tv.setTextColor(Color.parseColor("#333333"));
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundResource(R.drawable.shape_black);
        return tv;
    }

    private static View getItemView(Activity act, ProDayLeaveFourSizeReportSource.data1 data) {
//        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_items_capacity, null);
//        ((TextView)view.findViewById(R.id.TV_InterID)).setText(data.getFinterid());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, Utils.dp2px(40));
        lp.weight = 1;
        LinearLayout LL = new LinearLayout(act);
        LL.setOrientation(LinearLayout.HORIZONTAL);
        for (ProDayLeaveFourSizeReportSource.data2 data2 : data.getSizeQtyList()) {
            if (data2.getSize().equals("指令号")) {
                continue;
            }
            TextView tv = new TextView(act);
            tv.setText(data2.getQty());
            tv.setTextSize(20);
            tv.setTextColor(Color.parseColor("#333333"));
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundResource(R.drawable.shape_black);
            LL.addView(tv);
        }
        return LL;
    }

    private static List<InterItemData> getInterList(List<ProDayLeaveFourSizeReportSource.data1> list) {
        List<String> interIdList = new ArrayList<>();
        List<InterItemData> datalist = new ArrayList<>();
        for (ProDayLeaveFourSizeReportSource.data1 data1 : list) {
            for (ProDayLeaveFourSizeReportSource.data2 data2 : data1.getSizeQtyList()) {
                if (data2.getSize().equals("指令号")) {
                    if (!interIdList.contains(data2.getQty())) {
                        interIdList.add(data2.getQty());
                        datalist.add(new InterItemData(data2.getQty(), 0));
                    }
                }
            }
        }

        for (ProDayLeaveFourSizeReportSource.data1 data1 : list) {
            for (ProDayLeaveFourSizeReportSource.data2 data2 : data1.getSizeQtyList()) {
                for (InterItemData data : datalist) {
                    if (data.getName().equals(data2.getQty())) {
                        data.setNum(data.getNum() + 1);
                    }
                }
            }
        }
        return datalist;
    }

    public static class InterItemData {
        public InterItemData(String name, int num) {
            this.name = name;
            this.num = num;
        }

        String name;
        int num;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
