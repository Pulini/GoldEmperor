package com.goldemperor.ShowCapacity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;
import com.goldemperor.ShowCapacity.model.ProOrderOfCastReport;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : FormMonthAdapter
 * Created by : PanZX on  2018/10/24 09:07
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class FormDayAdapter extends BaseAdapter {

    private List<ProOrderOfCastReport.Day> POOCR;
    private Activity mActivity;

    public FormDayAdapter(Activity act, List<ProOrderOfCastReport.Day> poocr) {
        mActivity = act;
        POOCR = poocr;
        if (POOCR == null) {
            POOCR = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return POOCR.size();
    }

    @Override
    public Object getItem(int position) {
        return POOCR.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProOrderOfCastReport.Day data = POOCR.get(position);
        Item holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_form_day, null);
            holder = new Item();
            holder.LL_BKG = convertView.findViewById(R.id.LL_BKG);
            holder.TV_OrderOfCast = convertView.findViewById(R.id.TV_OrderOfCast);
            holder.TV_PlanQty = convertView.findViewById(R.id.TV_PlanQty);
            holder.TV_DCL = convertView.findViewById(R.id.TV_DCL);
            holder.TV_ComprehensiveMark = convertView.findViewById(R.id.TV_ComprehensiveMark);
            holder.TV_DepName = convertView.findViewById(R.id.TV_DepName);
            holder.TV_HGL = convertView.findViewById(R.id.TV_HGL);
            holder.TV_Qty = convertView.findViewById(R.id.TV_Qty);
            holder.TV_RJCN = convertView.findViewById(R.id.TV_RJCN);
            holder.TV_HourAvgPay = convertView.findViewById(R.id.TV_HourAvgPay);
            convertView.setTag(holder);
        } else {
            holder = (Item) convertView.getTag();
        }
        holder.TV_OrderOfCast.setText(data.getFOrderOfCast() + "");
        holder.TV_DepName.setText(data.getFDepName());
        holder.TV_HGL.setText(data.getFHGL());
        holder.TV_Qty.setText(data.getFQty());
        holder.TV_RJCN.setText(data.getFRJCN());
        holder.TV_HourAvgPay.setText(data.getFHourAvgPay());
        holder.TV_PlanQty.setText(data.getFPlanQty());
        holder.TV_DCL.setText(data.getFDCL());
        holder.TV_ComprehensiveMark.setText(data.getFComprehensiveMark());
        holder.LL_BKG.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dp2px(40)));
        return convertView;
    }

    public static class Item {
        LinearLayout LL_BKG;
        TextView TV_OrderOfCast;
        TextView TV_DepName;
        TextView TV_PlanQty;
        TextView TV_Qty;
        TextView TV_HGL;
        TextView TV_DCL;
        TextView TV_RJCN;
        TextView TV_HourAvgPay;
        TextView TV_ComprehensiveMark;
    }

}
