
package com.goldemperor.ScanCode.ProcessReportInstock;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YOLANDA on 2016/7/22.
 */
public class PRI_Adapter extends RecyclerView.Adapter<PRI_Adapter.MyViewHolder> {

    List<CodeDataModel> data = new ArrayList<>();


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_TV_Code;
        TextView TV_Type;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_TV_Code = (TextView) itemView.findViewById(R.id.TV_Code);
            this.TV_Type = (TextView) itemView.findViewById(R.id.TV_Type);
        }
    }

    public PRI_Adapter(List<CodeDataModel> data) {
        super();
        this.data = data;
    }

    public void Updata(List<CodeDataModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pri, parent, false));
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(60)));
        holder.tv_TV_Code.setText(data.get(position).getCode());
        holder.TV_Type.setText("");
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }

    }

}
