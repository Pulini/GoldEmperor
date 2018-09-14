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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.R;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class TechniqueAdapter extends RecyclerView.Adapter<TechniqueAdapter.DefaultViewHolder> {

    private List<String[]> ls;




    public TechniqueAdapter(List<String[]> ls) {
        this.ls = ls;

    }


    @Override
    public int getItemCount() {
        return ls == null ? 0 : ls.size();
    }





    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gxpg_technique_list, parent, false));
    }

    @Override
    public void onBindViewHolder(TechniqueAdapter.DefaultViewHolder holder, int position) {
        holder.tv_processnumber.setText(ls.get(position)[0]);
        holder.tv_processname.setText(ls.get(position)[1]);
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_processnumber;
        private TextView tv_processname;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_processnumber = (TextView) itemView.findViewById(R.id.tv_processnumber);
            tv_processname = (TextView) itemView.findViewById(R.id.tv_processname);
        }

        public void setData(String[] res) {

        }


    }

}
