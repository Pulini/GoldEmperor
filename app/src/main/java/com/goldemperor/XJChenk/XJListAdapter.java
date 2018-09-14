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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;

import java.text.ParseException;
import java.util.List;


/**
 * Created by YOLANDA on 2016/7/22.
 */
public class XJListAdapter extends RecyclerView.Adapter<XJListAdapter.DefaultViewHolder> {

    private List<priceResult> ls;

    private OnItemClickListener mOnItemClickListener;



    public XJListAdapter(List<priceResult> ls) {
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
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_xjdlist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(XJListAdapter.DefaultViewHolder holder, int position) {
        setData(holder,ls.get(position));
    }
    public void setData(final DefaultViewHolder holder, priceResult o) {
        holder.tv_foperatorname.setText("采购员:"+o.getFoperatorname());
        try {
            holder.tv_fdate.setText("日期:"+ Utils.dateConvert(o.getFdate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.  tv_fnumber.setText("询价单号:"+o.getFnumber());
        holder. tv_fitemname.setText("物料名称:"+o.getFitemname());
        holder. tv_suppliername.setText("供应商:"+o.getSuppliername());
        holder. tv_fneedauxqty.setText("数量:"+o.getFneedauxqty());
        holder. tv_fauxpricefor.setText("单价:"+o.getFauxpricefor());
        holder. tv_famountfor.setText("金额:"+o.getFsymbols()+o.getFamountfor());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder {

        OnItemClickListener mOnItemClickListener;
        RelativeLayout relativeLayout;

        private TextView tv_foperatorname;
        private TextView tv_fdate;
        private TextView tv_fnumber;

        private TextView tv_fitemname;
        private TextView tv_suppliername;
        private TextView tv_fneedauxqty;

        private TextView tv_fauxpricefor;
        private TextView tv_famountfor;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            relativeLayout= (RelativeLayout) itemView.findViewById(R.id.item);

            tv_foperatorname= (TextView) itemView.findViewById(R.id.tv_foperatorname);
            tv_fdate= (TextView) itemView.findViewById(R.id.tv_fdate);
            tv_fnumber= (TextView) itemView.findViewById(R.id.tv_fnumber);
            tv_fitemname= (TextView) itemView.findViewById(R.id.tv_fitemname);
            tv_suppliername= (TextView) itemView.findViewById(R.id.tv_suppliername);

            tv_fneedauxqty= (TextView) itemView.findViewById(R.id.tv_fneedauxqty);
            tv_fauxpricefor= (TextView) itemView.findViewById(R.id.tv_fauxpricefor);
            tv_famountfor= (TextView) itemView.findViewById(R.id.tv_famountfor);
        }


    }


}
