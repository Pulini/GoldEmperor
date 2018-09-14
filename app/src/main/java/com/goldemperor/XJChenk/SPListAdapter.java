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
package com.goldemperor.XJChenk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;

import java.text.ParseException;
import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class SPListAdapter extends RecyclerView.Adapter<SPListAdapter.DefaultViewHolder> {

    private List<priceEntryResult> ls;

    private OnItemClickListener mOnItemClickListener;


    public SPListAdapter(List<priceEntryResult> ls) {
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
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_xjdentrylist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SPListAdapter.DefaultViewHolder holder, int position) {
        setData(holder,ls.get(position));
    }
    public void setData(final DefaultViewHolder holder, priceEntryResult o) {
        holder.tv_fcontact.setText(o.getFname() + o.getFcontact() + o.getFmobiletelephone());
        try {
            holder.tv_FEndDate.setText("报价有效止:" + Utils.dateConvert(o.getFenddate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (o.getFproperty() == 1) {
            holder. tv_fproperty.setText("是否检测:是");
        } else {
            holder. tv_fproperty.setText("是否检测:否");
        }

        holder. tv_FReportPrice.setText("报价:" + o.getFprice());
        holder. tv_FPrice.setText("议价:" + o.getFprice());
        holder. tv_FAuxPriceFor.setText("含检测价:" + o.getFauxpricefor());
        holder. tv_FNote.setText("备注:" + o.getFnote());
        holder.fdefault = o.getFdefault();
        if ( holder.fdefault == -1) {
            holder. RadioButton1.setChecked(true);
        } else {
            holder.  RadioButton1.setChecked(false);
        }
        holder. RadioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( holder.fdefault == -1) {
                    holder.RadioButton1.setChecked(true);
                } else {
                    holder.RadioButton1.setChecked(false);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                }

            }
        });
    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder {



        private TextView tv_fcontact;
        private TextView tv_fproperty;
        private TextView tv_FReportPrice;

        private TextView tv_FPrice;
        private TextView tv_FAuxPriceFor;
        private TextView tv_FEndDate;

        private TextView tv_FNote;

        private RadioButton RadioButton1;
        private int fdefault = 0;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_fcontact = (TextView) itemView.findViewById(R.id.tv_fcontact);
            tv_fproperty = (TextView) itemView.findViewById(R.id.tv_fproperty);
            tv_FReportPrice = (TextView) itemView.findViewById(R.id.tv_FReportPrice);
            tv_FPrice = (TextView) itemView.findViewById(R.id.tv_FPrice);
            tv_FAuxPriceFor = (TextView) itemView.findViewById(R.id.tv_FAuxPriceFor);

            tv_FEndDate = (TextView) itemView.findViewById(R.id.tv_FEndDate);
            tv_FNote = (TextView) itemView.findViewById(R.id.tv_FNote);
            RadioButton1 = (RadioButton) itemView.findViewById(R.id.RadioButton1);
        }


    }


}
