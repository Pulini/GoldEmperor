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
package com.goldemperor.StockCheck.ExceptionalView;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldemperor.StockCheck.sql.stock_check;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.R;
import com.goldemperor.MainActivity.define;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.DefaultViewHolder> {

    private List<stock_check> ls;

    private OnItemClickListener mOnItemClickListener;

    public MenuAdapter(List<stock_check> ls) {
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
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuAdapter.DefaultViewHolder holder, int position) {
        setData(holder,ls.get(position));
    }
    public void setData(final DefaultViewHolder holder, stock_check sc) {
        holder.tv_number.setText("单号:"+sc.getNumber());
        holder.tv_date.setText("日期:"+sc.getApplydate());
        holder.tv_proposer.setText("申请人:"+sc.getProposer());
        String supplier = sc.getSupplier() == null ? "未填" : sc.getSupplier();
        holder.tv_supplier.setText("供应商:" + supplier);
        if(sc.getStatus().equals(define.EXCEPTIONAL)) {
            holder.tv_status.setText("状态:等待处理");
            SpannableStringBuilder builder = new SpannableStringBuilder(holder.tv_status.getText().toString());
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            builder.setSpan(redSpan, 3, holder.tv_status.getText().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_status.setText(builder);
        }else  if(sc.getStatus().equals(define.EXCEPTIONAL_RESULT)) {
            holder.tv_status.setText("状态:已上传处理结果");
        }
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
        TextView tv_number;
        TextView tv_date;
        TextView tv_proposer;
        TextView tv_supplier;
        TextView tv_status;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_proposer = (TextView) itemView.findViewById(R.id.tv_proposer);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_supplier = (TextView) itemView.findViewById(R.id.tv_supplier);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
        }


    }

}
