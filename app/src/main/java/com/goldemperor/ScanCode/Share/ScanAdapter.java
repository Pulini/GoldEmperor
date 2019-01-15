package com.goldemperor.ScanCode.Share;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;


import java.util.ArrayList;
import java.util.List;

/**
 * File Name : ScanAdapter
 * Created by : PanZX on  2018/11/1 10:34
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.MyViewHolder> {
    OnItemClickListener OICL;
   public interface OnItemClickListener{
        void OnItemClick(int position );
    }
    List<CodeDataModel> CDML=new ArrayList<>();

    String MSG = "入库";
    boolean isShowMSG = true;

    public void SetOnItemClickListener(OnItemClickListener onitemclicklistener){
        OICL=onitemclicklistener;
    }
    public void setMSG(String msg) {
        this.MSG = msg;
    }

    public void setShowMSG(boolean showMSG) {
        isShowMSG = showMSG;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout LL_BKG;
        TextView TV_Code;
        TextView TV_Type;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.LL_BKG = itemView.findViewById(R.id.LL_BKG);
            this.TV_Code = itemView.findViewById(R.id.TV_Code);
            this.TV_Type = itemView.findViewById(R.id.TV_Type);
            TV_Type.setText("");
        }
    }

    public ScanAdapter(List<CodeDataModel> data) {
        if(data!=null){
            for (CodeDataModel datum : data) {
                CDML.add(datum);
            }
        }
    }

    public void Updata(List<CodeDataModel> data){
        if(data!=null){
            CDML.clear();
            for (CodeDataModel datum : data) {
                CDML.add(datum);
            }
            notifyDataSetChanged();
            LOG.e("Updata="+CDML.size());
        }

    }

    public void RemovedItem(int data){
        notifyItemRemoved(data);
        CDML.remove(data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_code, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(40)));
        holder.TV_Code.setText(CDML.get(position).getCode());
        holder.TV_Type.setVisibility(isShowMSG ? View.VISIBLE : View.GONE);
        if (CDML.get(position).getType()) {
            holder.TV_Type.setText("已" + MSG);
            holder.LL_BKG.setBackgroundColor(Color.parseColor("#ff0000"));
            holder.TV_Code.setTextColor(Color.parseColor("#ffffff"));
            holder.TV_Type.setTextColor(Color.parseColor("#ffDD20"));
        } else {
            holder.TV_Type.setText("未" + MSG);
            holder.LL_BKG.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.TV_Code.setTextColor(Color.parseColor("#333333"));
            holder.TV_Type.setTextColor(Color.parseColor("#0000aa"));
        }
        holder.itemView.setOnClickListener(v -> {
            if(OICL!=null){
                OICL.OnItemClick(holder.getLayoutPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return CDML.size();
    }


}
