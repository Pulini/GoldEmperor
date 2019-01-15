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
package com.goldemperor.StockCheck.WaitView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class UpdataImageEditAdapter extends RecyclerView.Adapter<UpdataImageEditAdapter.DefaultViewHolder> {

    private List<String> imageList;

    private OnItemClickListener mOnItemClickListener;


    public UpdataImageEditAdapter(List<String> imageList) {
        this.imageList = imageList;


    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return imageList == null ? 0 : imageList.size();
    }

    @Override
    public UpdataImageEditAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_updata_item_image, parent, false));
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(UpdataImageEditAdapter.DefaultViewHolder holder, int position) {
        holder.setData(imageList.get(position));
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button editDelete;
        ImageView goodsImage;

        String fileName = null;
        OnItemClickListener mOnItemClickListener;

        private ImageOptions imageOptions;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            imageOptions = new ImageOptions.Builder()
                    .setLoadingDrawableId(R.drawable.loading)
                    .setFailureDrawableId(R.drawable.loading_failure)
                    .setUseMemCache(true)
                    .build();

            itemView.setOnClickListener(this);
            editDelete = (Button) itemView.findViewById(R.id.edit_delete);
            goodsImage = (ImageView) itemView.findViewById(R.id.goodsImage);

            editDelete.setTag("delete");
            editDelete.setOnClickListener(this);
        }

        public void setData(String imageName) {
            fileName = imageName;
            LOG.e("Path=" + define.endpoints + fileName);
            x.image().bind(goodsImage,
                    define.endpoints + fileName,
                    imageOptions);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null && v.getTag() != null) {
                mOnItemClickListener.onDeleteClick(getAdapterPosition());
            } else if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }

        }
    }

}
