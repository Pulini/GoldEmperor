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
package com.goldemperor.CCActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;
import com.goldemperor.XJChenk.priceResult;

import java.text.ParseException;
import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class CCListAdapter extends RecyclerView.Adapter<CCListAdapter.DefaultViewHolder> {

    private List<facardResult> ls;

    private OnItemClickListener mOnItemClickListener;



    public CCListAdapter(List<facardResult> ls) {
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
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cclist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CCListAdapter.DefaultViewHolder holder, int position) {
        setData(holder,ls.get(position));
    }

    public void setData(final DefaultViewHolder holder, facardResult o) {
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });

        holder.tv_fname.setText("物品:"+o.getFname());
        holder.tv_fdate.setText("制单日期:"+ o.getFoperatedate());

        holder.tv_userName.setText("保管人员:"+o.getFempname());
        holder.tv_userNumber.setText("工号:"+o.getFempnumber());


    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder {

        OnItemClickListener mOnItemClickListener;
        RelativeLayout relativeLayout;

        private TextView tv_fname;
        private TextView tv_fdate;
        private TextView tv_userName;

        private TextView tv_userNumber;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            relativeLayout= (RelativeLayout) itemView.findViewById(R.id.item);

            tv_fname= (TextView) itemView.findViewById(R.id.tv_fname);
            tv_fdate= (TextView) itemView.findViewById(R.id.tv_fdate);
            tv_userName= (TextView) itemView.findViewById(R.id.tv_userName);
            tv_userNumber= (TextView) itemView.findViewById(R.id.tv_userNumber);

        }

    }


}
