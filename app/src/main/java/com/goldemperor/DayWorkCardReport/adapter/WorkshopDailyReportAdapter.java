package com.goldemperor.DayWorkCardReport.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : WorkshopDailyReportAdapter
 * Created by : PanZX on  2018/12/11 13:22
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：日报表适配器
 */
public class WorkshopDailyReportAdapter extends BaseAdapter {

    private List<List<WorkshopDailyReportModel>> WDRML;
    private Activity mActivity;

    public WorkshopDailyReportAdapter(Activity act, List<List<WorkshopDailyReportModel>> wdrml) {
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
        List<WorkshopDailyReportModel> data = WDRML.get(position);
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

    private View setItemLL(List<WorkshopDailyReportModel> data) {
        LinearLayout LL = new LinearLayout(mActivity);
        LL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LL.setOrientation(LinearLayout.VERTICAL);
        for (WorkshopDailyReportModel datum : data) {
            LL.addView(setItem(datum));
        }
        return LL;
    }

    private View setItem(WorkshopDailyReportModel data) {
        View V = LayoutInflater.from(mActivity).inflate(R.layout.item_workshop_daily_report, null);
        ((TextView) V.findViewById(R.id.TV_FOrganizeName)).setText(data.getFOrganizeName());
        ((TextView) V.findViewById(R.id.TV_FToDayMustQty)).setText(data.getFToDayMustQty() + "");
        ((TextView) V.findViewById(R.id.TV_FToDayQty)).setText(data.getFToDayQty() + "");
        ((TextView) V.findViewById(R.id.TV_FNoToDayQty)).setText(data.getFNoToDayQty() + "");
        ((TextView) V.findViewById(R.id.TV_FToDayFinishRate)).setText(data.getFToDayFinishRate());
        ((TextView) V.findViewById(R.id.TV_FLJQty)).setText(data.getFLJQty() + "");
        ((TextView) V.findViewById(R.id.TV_FNoDoingReason)).setText(data.getFNoDoingReason());
        V.findViewById(R.id.LL_BKG).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dp2px(40)));
        if (data.getFOrganizeName().contains("合计")) {
            V.findViewById(R.id.LL_BKG).setBackgroundColor(Color.parseColor("#88DDDDDD"));
        }
        return V;
    }

}
