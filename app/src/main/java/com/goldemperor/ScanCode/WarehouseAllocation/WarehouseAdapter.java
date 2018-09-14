package com.goldemperor.ScanCode.WarehouseAllocation;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldemperor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : WarehouseAdapter
 * Created by : PanZX on  2018/7/13 14:49
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：仓库调拨单适配器
 */
public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.WViewHolder> {
    private List<String> list=new ArrayList<>();
    WarehouseAdapter(List<String> l){
        list=l;
    }
    public void UpData(List<String> l){
        list=l;
        notifyDataSetChanged();
    }



    @Override
    public WViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new WViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wa, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(WViewHolder holder, int position) {
        if (position == 0 || position % 2 == 0) {
            holder.setData(list.get(position),true);
        }else{
            holder.setData(list.get(position),false);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class WViewHolder extends RecyclerView.ViewHolder {
        TextView TV;
        public WViewHolder(View itemView) {
            super(itemView);
            TV = (TextView) itemView.findViewById(R.id.TV);
        }

        public void setData(String t, boolean b) {
            if(b){
                TV.setBackgroundColor(Color.parseColor("#BCE6F8"));
            } else {
                TV.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            TV.setText(t);
        }
    }
}
