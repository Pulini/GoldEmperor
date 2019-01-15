/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.goldemperor.GxReport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ScanCode.Share.ScanUtil;
import com.goldemperor.Utils.SPUtils;

import java.util.ArrayList;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.DefaultViewHolder> {

    private ArrayList<Order> ls;

    private OnItemClickListener mOnItemClickListener;


    public MenuAdapter(ArrayList<Order> ls,Context con) {
        this.ls = ls;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return ls == null ? 0 : ls.size();
    }


    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gxreport_item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuAdapter.DefaultViewHolder holder, int position) {
        setData(holder,ls.get(position));
    }
    public void setData(final DefaultViewHolder holder, Order o) {
        holder.tv_number.setText("单号:"+o.getFCardNo());
        holder.tv_id.setText("工号:"+SPUtils.get(define.SharedJobNumber,"无"));
        holder.tv_name.setText("姓名:"+SPUtils.get(define.SharedUser,"无"));
        holder.tv_qua.setText("数量:"+o.getFQty());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder  {
        OnItemClickListener mOnItemClickListener;
        TextView tv_number;
        TextView tv_id;
        TextView tv_name;
        TextView tv_qua;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_qua= (TextView) itemView.findViewById(R.id.tv_qua);
        }


    }

}
