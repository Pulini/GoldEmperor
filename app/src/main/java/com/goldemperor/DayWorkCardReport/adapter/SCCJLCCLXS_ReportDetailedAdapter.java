
package com.goldemperor.DayWorkCardReport.adapter;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemperor.DayWorkCardReport.model.SCCJLCCLXS_ReportDetailedModel;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

/**
 * File Name : ProcessSendAdapter
 * Created by : PanZX on  2018/4/27 9:08
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class SCCJLCCLXS_ReportDetailedAdapter extends RecyclerView.Adapter<SCCJLCCLXS_ReportDetailedAdapter.DefaultViewHolder> {

    private List<SCCJLCCLXS_ReportDetailedModel> PSML;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void ItemClick(int position);

        void PhotoClick(ImageView view);
    }


    public SCCJLCCLXS_ReportDetailedAdapter(List<SCCJLCCLXS_ReportDetailedModel> ls) {
        this.PSML = ls;
        if (PSML == null) {
            PSML = new ArrayList<>();
        }
        LOG.e("ProcessSendAdapter=" + PSML.size());

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void Updata(List<SCCJLCCLXS_ReportDetailedModel> psml) {
        PSML = psml;
        notifyDataSetChanged();
        LOG.e("Updata=" + PSML.size());
    }

    @Override
    public int getItemCount() {
        LOG.e("getItemCount=" + PSML.size());
        return PSML == null ? 0 : PSML.size();
    }


    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sccjlcclxs_report_detailed, parent, false));
    }

    @Override
    public void onBindViewHolder(SCCJLCCLXS_ReportDetailedAdapter.DefaultViewHolder holder, final int position) {
        LOG.e("onBindViewHolder");
        setData(holder,PSML.get(position),position);
    }
    public void setData(final DefaultViewHolder holder, SCCJLCCLXS_ReportDetailedModel psm, int p) {
        if(p%2!=0){
            holder.LL_BKG.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            holder.LL_BKG.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        LOG.e("path=" + define.Net4 + psm.get鞋图路径());

        Picasso.get()
                .load(define.Net4 + psm.get鞋图路径())
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading_failure)
                .into(holder.IV_icon);

        holder.TV_XB.setText(psm.get线别() == null ? "" : psm.get线别());
        holder.TV_GCXT.setText(psm.get工厂型体() == null ? "" : psm.get工厂型体());
        holder.TV_ZLDH.setText(psm.get指令单号() == null ? "" : psm.get指令单号());
        holder.TV_JRWS.setText(psm.get今任务数() + "");
        holder.TV_ZLSL.setText(psm.get指令数量() + "");
        holder.TV_JRWC.setText(psm.get今日完成() + "");
        holder.TV_LJSM.setText(psm.get累计扫描() + "");
        holder.TV_ZLQS.setText(psm.get指令欠数() + "");
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.ItemClick(holder.getAdapterPosition());
                }
            });
            holder.IV_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.PhotoClick(holder.IV_icon);
                }
            });

        }
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        LinearLayout LL_BKG;
        ImageView IV_icon;//图片
        TextView TV_XB;//线别
        TextView TV_GCXT;//工厂型体
        TextView TV_ZLDH;//指令单号
        TextView TV_JRWS;//今任务数
        TextView TV_ZLSL;//指令数量
        TextView TV_JRWC;//今日完成
        TextView TV_LJSM;//累计扫描
        TextView TV_ZLQS;//指令欠数

        public DefaultViewHolder(View itemView) {
            super(itemView);
            LL_BKG = (LinearLayout) itemView.findViewById(R.id.LL_BKG);
            IV_icon = (ImageView) itemView.findViewById(R.id.IV_icon);
            TV_XB = (TextView) itemView.findViewById(R.id.TV_XB);
            TV_GCXT = (TextView) itemView.findViewById(R.id.TV_GCXT);
            TV_ZLDH = (TextView) itemView.findViewById(R.id.TV_ZLDH);
            TV_JRWS = (TextView) itemView.findViewById(R.id.TV_JRWS);
            TV_ZLSL = (TextView) itemView.findViewById(R.id.TV_ZLSL);
            TV_JRWC = (TextView) itemView.findViewById(R.id.TV_JRWC);
            TV_LJSM = (TextView) itemView.findViewById(R.id.TV_LJSM);
            TV_ZLQS = (TextView) itemView.findViewById(R.id.TV_ZLQS);
        }

    }


}
