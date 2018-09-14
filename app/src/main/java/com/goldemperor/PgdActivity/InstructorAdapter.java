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

import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.R;
import com.goldemperor.Widget.PinchImageView;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.DefaultViewHolder> {

    private List<Integer> ls;




    public InstructorAdapter(List<Integer> ls) {
        this.ls = ls;

    }


    @Override
    public int getItemCount() {
        return ls == null ? 0 : ls.size();
    }


    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gxpg_instructor_list, parent, false));
    }

    @Override
    public void onBindViewHolder(InstructorAdapter.DefaultViewHolder holder, int position) {
        holder.im_instructor.setImageResource(ls.get(position));
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder  {

        OnItemClickListener mOnItemClickListener;
        private PinchImageView im_instructor;


        public DefaultViewHolder(View itemView) {
            super(itemView);
            im_instructor = (PinchImageView) itemView.findViewById(R.id.im_instructor);
        }
    }

}
