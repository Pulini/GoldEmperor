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
package com.goldemperor.PgdActivity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class AssignAdapter extends RecyclerView.Adapter<AssignAdapter.DefaultViewHolder> {

    private List<GxpgPlan> ls;

    private OnItemClickListener mOnItemClickListener;


    public AssignAdapter(List<GxpgPlan> ls) {
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
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gxpg_assign_list, parent, false));
    }

    @Override
    public void onBindViewHolder(AssignAdapter.DefaultViewHolder holder, int position) {
        setData(holder,ls.get(position));
    }
    public void setData(DefaultViewHolder holder, GxpgPlan gxpgPlan) {
        holder.tv_planBody.setText(gxpgPlan.getStyle());
        holder.tv_itemname.setText(gxpgPlan.getProcessname());
        holder.tv_staff.setText(gxpgPlan.getUsername());
        DecimalFormat df = new DecimalFormat("#0.00");
        Log.e("Pan", "占比:" + gxpgPlan.getPer());
        try {
            float F = Float.parseFloat(df.format(gxpgPlan.getPer()));
            Log.e("Pan", "占比测试:" + F);
            holder.tv_per.setText(df.format(gxpgPlan.getPer()));
        } catch (NumberFormatException e) {
            holder.tv_per.setText("数据异常");
        }
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder  {

        private TextView tv_planBody;

        private TextView tv_itemname;
        private TextView tv_staff;

        private TextView tv_per;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_itemname = (TextView) itemView.findViewById(R.id.tv_itemname);

            tv_planBody = (TextView) itemView.findViewById(R.id.tv_planBody);
            tv_staff = (TextView) itemView.findViewById(R.id.tv_staff);

            tv_per = (TextView) itemView.findViewById(R.id.tv_per);
        }


    }

}
