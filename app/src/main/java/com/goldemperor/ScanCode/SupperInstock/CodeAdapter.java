package com.goldemperor.ScanCode.SupperInstock;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemperor.ScanCode.CxStockIn.widget.OnDeleteListioner;
import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;

import java.util.LinkedList;

/**
 * File Name : CodeAdapter
 * Created by : PanZX on  2018/5/15 14:03
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class CodeAdapter extends BaseAdapter {
    private Context mContext;
    public LinkedList<CodeDataModel> mlist = null;
    private OnDeleteListioner mOnDeleteListioner;
    private boolean delete = false;
    private String MSG ="入库";

    public CodeAdapter(Context mContext, LinkedList<CodeDataModel> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;

    }
    public void setmsg(String msg){
        MSG=msg;
        notifyDataSetChanged();
    }
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setOnDeleteListioner(OnDeleteListioner mOnDeleteListioner) {
        this.mOnDeleteListioner = mOnDeleteListioner;
    }

    public int getCount() {

        return mlist.size();
    }

    public Object getItem(int pos) {
        return mlist.get(pos);
    }

    public long getItemId(int pos) {
        return pos;
    }

    public View getView(final int pos, View convertView, ViewGroup p) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.supperinstock_list_item, null);
            viewHolder = new ViewHolder();

            viewHolder.forward_layout = (LinearLayout) convertView.findViewById(R.id.forward_layout);
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_text);
            viewHolder.TV_Type = (TextView) convertView.findViewById(R.id.TV_Type);
            viewHolder.delete_action = (TextView) convertView.findViewById(R.id.delete_action);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final View.OnClickListener mOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mOnDeleteListioner != null)
                    mOnDeleteListioner.onDelete(pos);

            }
        };
        viewHolder.delete_action.setOnClickListener(mOnClickListener);
        viewHolder.name.setText(mlist.get(pos).getCode());
        if (mlist.get(pos).getType()) {
            viewHolder.name.setTextColor(Color.parseColor("#ffffff"));
            viewHolder.TV_Type.setText("已"+ MSG);
            viewHolder.TV_Type.setTextColor(Color.parseColor("#ffDD20"));
            viewHolder.forward_layout.setBackgroundColor(Color.parseColor("#ff0000"));

        } else {
            viewHolder.name.setTextColor(Color.parseColor("#333333"));
            viewHolder.TV_Type.setText("未"+ MSG);
            viewHolder.TV_Type.setTextColor(Color.parseColor("#0000aa"));
            viewHolder.forward_layout.setBackgroundColor(Color.parseColor("#ffffff"));

        }
        return convertView;
    }

    public static class ViewHolder {
        public LinearLayout forward_layout;
        public TextView name;
        public TextView TV_Type;
        public TextView delete_action;

    }


    public boolean checkdataYRK(){
        boolean b=true;
        LOG.e("数据总量"+mlist.size());
        for (CodeDataModel codeData : mlist) {
            LOG.e("数据Type="+codeData.getType());
            if(codeData.getType()){
                LOG.e("数据Code:"+codeData.getCode());
                b =false;
            }
        }
        return b;
    }
    public boolean checkdataWRK(){
        boolean b=true;
        LOG.e("数据总量"+mlist.size());
        for (CodeDataModel codeData : mlist) {
            LOG.e("数据Type="+codeData.getType());
            if(!codeData.getType()){
                LOG.e("数据Code:"+codeData.getCode());
                b =false;
            }
        }
        return b;
    }
}
