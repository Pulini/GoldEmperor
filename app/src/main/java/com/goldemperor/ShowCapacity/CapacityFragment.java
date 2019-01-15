package com.goldemperor.ShowCapacity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldemperor.R;
import com.goldemperor.ShowCapacity.model.DayWorkCardReportModel;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Widget.verticalviewpager.ViewPagerFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * File Name : CapacityFragment
 * Created by : PanZX on  2018/9/30 15:22
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.fragment_capacity)
public class CapacityFragment extends ViewPagerFragment {

    @ViewInject(R.id.TV_Target)
    private TextView TV_Target;

    @ViewInject(R.id.TV_Complete)
    private TextView TV_Complete;

    @ViewInject(R.id.TV_CompletionRate)
    private TextView TV_CompletionRate;

    @ViewInject(R.id.TV_QualificationRate)
    private TextView TV_QualificationRate;

    DayWorkCardReportModel DWCRM;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = x.view().inject(this, inflater, container);
        }
        return rootView;
    }

    /**
     * 接收产能数据
     * @param data
     */
    public void setDayWorkCardReportData(DayWorkCardReportModel data) {
        DWCRM=data;
        LOG.e("NumberOfGoalsToday"+DWCRM.getNumberOfGoalsToday());
        TV_Target.setText(DWCRM.getNumberOfGoalsToday() + "");
        TV_Complete.setText(DWCRM.getNumberOfCompletions() + "");
        TV_CompletionRate.setText(ViewUtils.getCompletionRate(DWCRM.getNumberOfGoalsToday() , DWCRM.getNumberOfCompletions()));
        TV_QualificationRate.setText(DWCRM.getQualifiedRate() + "%");

        double WorkRate = DWCRM.getWorkRate();
        int color;
        if (WorkRate >= 100) {
            color = Color.parseColor("#3FB830");
        } else if (WorkRate >= 90) {
            color = Color.parseColor("#FFC107");
        } else {
            color = Color.parseColor("#FF0000");
        }
        TV_CompletionRate.setTextColor(color);
        LOG.e("目标数:" + DWCRM.getNumberOfGoalsToday() + "   完成数:" + DWCRM.getNumberOfCompletions() + "   完成率" + ViewUtils.getCompletionRate(DWCRM.getNumberOfGoalsToday(),  DWCRM.getNumberOfCompletions()) + "  产能=" + WorkRate);

    }

}
