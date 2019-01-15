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
public class FormMonthAdapter extends BaseAdapter {

    private List<ProOrderOfCastReport.Month> POOCR;
    private  Activity mActivity;

    public FormMonthAdapter(Activity act, List<ProOrderOfCastReport.Month> poocr) {
        mActivity=act;
        POOCR=poocr;
        if(POOCR==null){
            POOCR=new ArrayList<>();
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
        ProOrderOfCastReport.Month data = POOCR.get(position);
        Item holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_form_month, null);
            holder = new Item();
            holder.LL_BKG = convertView.findViewById(R.id.LL_BKG);
            holder.TV_MonthOrderOfCast = convertView.findViewById(R.id.TV_MonthOrderOfCast);
            holder.TV_MonthDepName = convertView.findViewById(R.id.TV_MonthDepName);
            holder.TV_MonthHGL = convertView.findViewById(R.id.TV_MonthHGL);
            holder.TV_MonthQty = convertView.findViewById(R.id.TV_MonthQty);
            holder.TV_MonthRJCN = convertView.findViewById(R.id.TV_MonthRJCN);
            holder.TV_MonthHourAvgPay = convertView.findViewById(R.id.TV_MonthHourAvgPay);
            holder.TV_MonthPlanQty = convertView.findViewById(R.id.TV_MonthPlanQty);
            holder.TV_MonthDCL = convertView.findViewById(R.id.TV_MonthDCL);
            holder.TV_MonthComprehensiveMark = convertView.findViewById(R.id.TV_MonthComprehensiveMark);
            convertView.setTag(holder);
        } else {
            holder = (Item) convertView.getTag();
        }
        holder.TV_MonthOrderOfCast.setText(data.getFMonthOrderOfCast()+"");
        holder.TV_MonthDepName.setText(data.getFMonthDepName());
        holder.TV_MonthHGL.setText(data.getFMonthHGL());
        holder.TV_MonthQty.setText(data.getFMonthQty());
        holder.TV_MonthRJCN.setText(data.getFMonthRJCN());
        holder.TV_MonthHourAvgPay.setText(data.getFMonthHourAvgPay());
        holder.TV_MonthPlanQty.setText(data.getFMonthPlanQty());
        holder.TV_MonthDCL.setText(data.getFMonthDCL());
        holder.TV_MonthComprehensiveMark.setText(data.getFMonthComprehensiveMark()+"");



        holder.LL_BKG.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,Utils.dp2px(40)));
        return convertView;
    }


    public static class Item {
        LinearLayout LL_BKG;
        TextView TV_MonthOrderOfCast;
        TextView TV_MonthDepName;
        TextView TV_MonthQty;
        TextView TV_MonthHGL;
        TextView TV_MonthRJCN;
        TextView TV_MonthHourAvgPay;
        TextView TV_MonthPlanQty;
        TextView TV_MonthDCL;
        TextView TV_MonthComprehensiveMark;
    }
}
