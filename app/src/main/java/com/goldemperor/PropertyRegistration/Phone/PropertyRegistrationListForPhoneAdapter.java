package com.goldemperor.PropertyRegistration.Phone;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldemperor.PropertyRegistration.OnItemClickListener;
import com.goldemperor.PropertyRegistration.PropertyModel;
import com.goldemperor.R;
import com.goldemperor.Utils.ClickProxy;
import com.goldemperor.Widget.fancybuttons.FancyButton;

import java.util.List;

/**
 * File Name : PropertyRegistrationListForPadAdapter
 * Created by : PanZX on  2019/1/17 17:12
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class PropertyRegistrationListForPhoneAdapter extends RecyclerView.Adapter<PropertyRegistrationListForPhoneAdapter.MyViewHolder> {
    List<PropertyModel> data;
    private OnItemClickListener OICL;
    public void setOnItemClickListener(OnItemClickListener oicl) {
        OICL = oicl;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout RL_BKG;
        TextView TV_Number;
        TextView TV_isCheck;
        TextView TV_CustodianName;
        TextView TV_Name;
        TextView TV_BuyDate;
        TextView TV_RegistrationDate;
        TextView TV_Address;
        TextView TV_Satus;
        TextView TV_SAP_PurchaseNum;
        FancyButton FB_Close;
        FancyButton FB_Print;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.RL_BKG = itemView.findViewById(R.id.RL_BKG);
            this.TV_isCheck = itemView.findViewById(R.id.TV_isCheck);
            this.TV_Number = itemView.findViewById(R.id.TV_Number);
            this.TV_CustodianName = itemView.findViewById(R.id.TV_CustodianName);
            this.TV_Name = itemView.findViewById(R.id.TV_Name);
            this.TV_BuyDate = itemView.findViewById(R.id.TV_BuyDate);
            this.TV_RegistrationDate = itemView.findViewById(R.id.TV_RegistrationDate);
            this.TV_Address = itemView.findViewById(R.id.TV_Address);
            this.TV_Satus = itemView.findViewById(R.id.TV_Satus);
            this.TV_SAP_PurchaseNum = itemView.findViewById(R.id.TV_SAP_PurchaseNum);
            this.FB_Close = itemView.findViewById(R.id.FB_Close);
            this.FB_Print = itemView.findViewById(R.id.FB_Print);
        }
    }


    public PropertyRegistrationListForPhoneAdapter(List<PropertyModel> data) {
        super();
        this.data = data;

    }

    public void Updata(List<PropertyModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public PropertyRegistrationListForPhoneAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property_registration_list_for_phone, null);
        PropertyRegistrationListForPhoneAdapter.MyViewHolder myViewHolder = new PropertyRegistrationListForPhoneAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(PropertyRegistrationListForPhoneAdapter.MyViewHolder holder, int position) {
        PropertyModel am = data.get(position);
        holder.RL_BKG.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        if (position == 0 || position % 2 == 0) {
            holder.RL_BKG.setBackgroundColor(Color.parseColor("#BCE6F8"));
        } else {
            holder.RL_BKG.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        if (OICL != null) {
            holder.RL_BKG.setOnClickListener(new ClickProxy(v -> OICL.ItemClick(holder.getLayoutPosition())));
            holder.FB_Close.setOnClickListener(new ClickProxy(v->OICL.CloseClick(holder.getLayoutPosition())));
            holder.FB_Print.setOnClickListener(new ClickProxy(v->OICL.PrintClick(holder.getLayoutPosition())));
        }
        holder.FB_Close.setEnabled(false);
        holder.FB_Print.setEnabled(false);

        holder.TV_Number.setText(am.getFNumber());
        holder.TV_Name.setText(am.getFName());
        holder.TV_BuyDate.setText(am.getFbuyDate());
        holder.TV_RegistrationDate.setText(am.getFWritedate());
        holder.TV_SAP_PurchaseNum.setText(am.getFSAPCgOrderNo());

        holder.TV_CustodianName.setText(am.getCustodianName());
        holder.TV_Address.setText(am.getFaddress());

        if (am.getFLabelPrintQty() > 0) {
            holder.TV_Satus.setText("已打印" + am.getFLabelPrintQty() + "次");
            holder.FB_Close.setEnabled(true);
            holder.FB_Print.setEnabled(true);
        } else {
            if(am.getFProcessStatus().equals("已审核")){
                holder.FB_Print.setEnabled(true);
            }
            holder.TV_Satus.setText(am.getFProcessStatus());
        }
        if(am.getFVisitedNum()>0){
            holder.TV_isCheck.setText("已查看");
            holder.TV_isCheck.setTextColor(Color.parseColor("#220000ff"));
        }else{
            holder.TV_isCheck.setText("未查看");
            holder.TV_isCheck.setTextColor(Color.parseColor("#22ff0000"));
        }
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
