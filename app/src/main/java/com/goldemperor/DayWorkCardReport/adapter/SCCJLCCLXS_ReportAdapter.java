
package com.goldemperor.DayWorkCardReport.adapter;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemperor.DayWorkCardReport.activity.SCCJLCCLXS_ReportActivity;
import com.goldemperor.DayWorkCardReport.model.SCCJLCCLXS_ReportModel;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Widget.ScrollListenerHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : ProcessSendAdapter
 * Created by : PanZX on  2018/4/27 9:08
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class SCCJLCCLXS_ReportAdapter extends RecyclerView.Adapter<SCCJLCCLXS_ReportAdapter.DefaultViewHolder> {

    private List<SCCJLCCLXS_ReportModel> PSML;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void Click(int position);
    }

    public static SCCJLCCLXS_ReportActivity pgdActivity;

    public static List<ScrollListenerHorizontalScrollView> ScrollViewList = new ArrayList<ScrollListenerHorizontalScrollView>();

    public SCCJLCCLXS_ReportAdapter(List<SCCJLCCLXS_ReportModel> ls, SCCJLCCLXS_ReportActivity pgdActivity) {
        this.PSML = ls;
        this.pgdActivity = pgdActivity;
        if (PSML == null) {
            PSML = new ArrayList<>();
        }
        LOG.e("ProcessSendAdapter=" + PSML.size());

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void Updata(List<SCCJLCCLXS_ReportModel> psml) {
        PSML = psml;
        notifyDataSetChanged();
        LOG.e("Updata=" + PSML.size());
    }

    @Override
    public int getItemCount() {
        return PSML == null ? 0 : PSML.size();
    }


    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sccjlcclxs_report, parent, false));
    }

    @Override
    public void onBindViewHolder(SCCJLCCLXS_ReportAdapter.DefaultViewHolder holder, int position) {
        LOG.e("onBindViewHolder");
        setData(holder, PSML.get(position), position);
    }


    public void setData(final DefaultViewHolder holder, SCCJLCCLXS_ReportModel psm, int p) {
        if (p % 2 != 0) {
            holder.item.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.item.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        holder.TV_WorkshopLocation.setText(psm.get车间地点() == null ? "" : psm.get车间地点());
        holder.TV_Group.setText(psm.get组别() == null ? "" : psm.get组别());
        holder.TV_Cadres.setText(psm.get带线干部() == null ? "" : psm.get带线干部());
        holder.TV_TargetProductionToday.setText(psm.get今日目标产量() + "");
        holder.TV_ProductionToday.setText(psm.get今日产量() + "");
        holder.TV_CompletionRate.setText(psm.get完成率() == null ? "" : psm.get完成率());
        holder.TV_MonthlyCumulativeTargetProduction.setText(psm.get月累计目标产量() + "");
        holder.TV_MonthlyCumulativeOutput.setText(psm.get月累计产量() + "");
        holder.TV_MonthCompletionRate.setText(psm.get月完成率() == null ? "" : psm.get月完成率());
        holder.TV_ActualNumber.setText(psm.get实际人数() + "");

        holder.SV_ProcessSend_Item.setOnScrollListener(new ScrollListenerHorizontalScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX) {
                for (int i = 0; i < ScrollViewList.size(); i++) {
                    ScrollViewList.get(i).scrollTo(scrollX, holder.SV_ProcessSend_Item.getScrollY());
                }
                pgdActivity.SV_DayWork.scrollTo(scrollX, holder.SV_ProcessSend_Item.getScrollY());
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOG.e("onClick" + holder.getAdapterPosition());
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.Click(holder.getAdapterPosition());
                }
            }
        });

    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        public ScrollListenerHorizontalScrollView SV_ProcessSend_Item;

        LinearLayout item;
        TextView TV_WorkshopLocation;//车间地点
        TextView TV_Group;//组别
        TextView TV_Cadres;//带线干部
        TextView TV_TargetProductionToday;//今日目标产量
        TextView TV_ProductionToday;//今日产量
        TextView TV_CompletionRate;//完成率
        TextView TV_MonthlyCumulativeTargetProduction;//月累计目标产量
        TextView TV_MonthlyCumulativeOutput;//月累计产量
        TextView TV_MonthCompletionRate;//月完成率
        TextView TV_ActualNumber;//实际人数

        public DefaultViewHolder(View itemView) {
            super(itemView);
            SV_ProcessSend_Item = (ScrollListenerHorizontalScrollView) itemView.findViewById(R.id.SV_ProcessSend_Item);
            item = (LinearLayout) itemView.findViewById(R.id.item);

            TV_WorkshopLocation = (TextView) itemView.findViewById(R.id.TV_WorkshopLocation);
            TV_Group = (TextView) itemView.findViewById(R.id.TV_Group);
            TV_Cadres = (TextView) itemView.findViewById(R.id.TV_Cadres);
            TV_TargetProductionToday = (TextView) itemView.findViewById(R.id.TV_TargetProductionToday);
            TV_ProductionToday = (TextView) itemView.findViewById(R.id.TV_ProductionToday);
            TV_CompletionRate = (TextView) itemView.findViewById(R.id.TV_CompletionRate);
            TV_MonthlyCumulativeTargetProduction = (TextView) itemView.findViewById(R.id.TV_MonthlyCumulativeTargetProduction);
            TV_MonthlyCumulativeOutput = (TextView) itemView.findViewById(R.id.TV_MonthlyCumulativeOutput);
            TV_MonthCompletionRate = (TextView) itemView.findViewById(R.id.TV_MonthCompletionRate);
            TV_ActualNumber = (TextView) itemView.findViewById(R.id.TV_ActualNumber);


        }

    }


}
