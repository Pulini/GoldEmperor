package com.goldemperor.GylxActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.PgdActivity.RouteEntry;
import com.goldemperor.R;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class GylxAdapter extends RecyclerView.Adapter<GylxAdapter.DefaultViewHolder> {

    private List<RouteEntry> ls;

    private OnItemClickListener mOnItemClickListener;



    public GylxAdapter(List<RouteEntry> ls) {
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
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gylx_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GylxAdapter.DefaultViewHolder holder, int position) {
        setData(holder,ls.get(position));
    }
    public void setData(DefaultViewHolder holder, RouteEntry o) {

        holder.tv_processcode.setText(o.getFprocessnumber());
        holder.tv_processname.setText(o.getFprocessname());
        holder.tv_price.setText(String.valueOf(o.getFprice()));
        holder.tv_part.setText(o.getPartname());
        holder.tv_note.setText(o.getFnote());
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {



        TextView tv_processcode;
        TextView tv_processname;
        TextView tv_price;
        TextView tv_part;

        TextView tv_note;

        public DefaultViewHolder(View itemView) {
            super(itemView);


            tv_processcode = (TextView) itemView.findViewById(R.id.tv_processcode);
            tv_processname = (TextView) itemView.findViewById(R.id.tv_processname);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);

            tv_part = (TextView) itemView.findViewById(R.id.tv_part);
            tv_note = (TextView) itemView.findViewById(R.id.tv_note);
        }




    }


}
