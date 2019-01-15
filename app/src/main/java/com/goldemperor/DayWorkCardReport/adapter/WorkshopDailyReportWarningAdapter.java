package com.goldemperor.DayWorkCardReport.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemperor.DayWorkCardReport.model.WorkshopDailyReportModel;
import com.goldemperor.DayWorkCardReport.model.WorkshopDailyReportWarningModel;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;
import com.goldemperor.ShowCapacity.FormDayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : WorkshopDailyReportAdapter
 * Created by : PanZX on  2018/12/11 13:22
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：日报表异常警报适配器
 */
public class WorkshopDailyReportWarningAdapter extends BaseAdapter {

    private List<List<WorkshopDailyReportWarningModel>> WDRML;
    private Activity mActivity;

    public WorkshopDailyReportWarningAdapter(Activity act, List<List<WorkshopDailyReportWarningModel>> wdrml) {
        mActivity = act;
        WDRML = wdrml;
        if (WDRML == null) {
            WDRML = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return WDRML.size();
    }

    @Override
    public Object getItem(int position) {
        return WDRML.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        List<WorkshopDailyReportWarningModel> data = WDRML.get(position);
        LinearLayout LL = new LinearLayout(mActivity);
        LL.setOrientation(LinearLayout.HORIZONTAL);
        TextView TV = new TextView(mActivity);
        TV.setText(data.get(0).getFWorkShopName());
        TV.setGravity(Gravity.CENTER);
        TV.setTextColor(Color.parseColor("#333333"));
        TV.setTextSize(20);
        TV.setLayoutParams(new LinearLayout.LayoutParams(Utils.dp2px(120), Utils.dp2px(40 * data.size())));
        TV.setBackgroundResource(R.drawable.shape_black);
        LL.addView(TV);
        LL.addView(setItemLL(data));
        return LL;
    }


    private View setItemLL(List<WorkshopDailyReportWarningModel> data) {
        LinearLayout LL = new LinearLayout(mActivity);
        LL.setOrientation(LinearLayout.VERTICAL);
        LL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        for (WorkshopDailyReportWarningModel datum : data) {
            LL.addView(setItem(datum));
        }
        return LL;
    }

    private View setItem(WorkshopDailyReportWarningModel data) {
        View V = LayoutInflater.from(mActivity).inflate(R.layout.item_workshop_daily_report_warning, null);
        ((TextView) V.findViewById(R.id.TV_FOrganizeName)).setText(data.getFOrganizeName());
        ((TextView) V.findViewById(R.id.TV_FParentDeptID)).setText(data.getFParentDeptID() + "");
        ((TextView) V.findViewById(R.id.TV_FDeptID)).setText(data.getFDeptID());
        ((TextView) V.findViewById(R.id.TV_FToDayMustQty)).setText(data.getFToDayMustQty() + "");
        ((TextView) V.findViewById(R.id.TV_FTimeMustQty)).setText(data.getFTimeMustQty() + "");
        ((TextView) V.findViewById(R.id.TV_FTimeQty)).setText(data.getFTimeQty() + "");
        ((TextView) V.findViewById(R.id.TV_FNoTimeQty)).setText(data.getFNoTimeQty() + "");
        ((TextView) V.findViewById(R.id.TV_FTimeFinishRate)).setText(data.getFTimeFinishRate());
        ((TextView) V.findViewById(R.id.TV_FNoDoingReason)).setText(data.getFNoDoingReason());
        V.findViewById(R.id.LL_BKG).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dp2px(40)));
        if (data.getFOrganizeName().contains("合计")) {
            V.findViewById(R.id.LL_BKG).setBackgroundColor(Color.parseColor("#88DDDDDD"));
        }
        return V;
    }


}
