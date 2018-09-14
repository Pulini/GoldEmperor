package com.goldemperor.StaffWorkStatistics;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : StaffWorkStatistics_GX_ListAdapter
 * Created by : PanZX on  2018/7/27 17:02
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class StaffWorkStatistics_GX_ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  boolean isShowPrice= false;
    private boolean isShowAmount = false;

    public enum ITEM_TYPE {
        ITEM1,
        ITEM2
    }

    List<StaffWorkStatistics_GX_Model> data = new ArrayList<>();

    static class Item1ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout LL_BKG;
        TextView TV_FBillNO;

        public Item1ViewHolder(View itemView) {
            super(itemView);
            this.LL_BKG = (LinearLayout) itemView.findViewById(R.id.LL_BKG);
            this.TV_FBillNO = (TextView) itemView.findViewById(R.id.TV_FBillNO);
        }
    }

    static class Item2ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout LL_BKG;
        TextView TV_FItemNo;
        TextView TV_FProcessNumber;
        TextView TV_FProcessName;
        TextView TV_FQty;
        TextView TV_FPrice;
        TextView TV_FAmount;
        TextView TV_FSmallBillSubsidy;
        TextView TV_FAmountSum;
        TextView TV_FSmallBillSubsidyPCT;


        public Item2ViewHolder(View itemView) {
            super(itemView);
            this.LL_BKG = (LinearLayout) itemView.findViewById(R.id.LL_BKG);
            this.TV_FItemNo = (TextView) itemView.findViewById(R.id.TV_FItemNo);
            this.TV_FProcessNumber = (TextView) itemView.findViewById(R.id.TV_FProcessNumber);
            this.TV_FProcessName = (TextView) itemView.findViewById(R.id.TV_FProcessName);
            this.TV_FQty = (TextView) itemView.findViewById(R.id.TV_FQty);
            this.TV_FPrice = (TextView) itemView.findViewById(R.id.TV_FPrice);
            this.TV_FAmount = (TextView) itemView.findViewById(R.id.TV_FAmount);
            this.TV_FSmallBillSubsidy = (TextView) itemView.findViewById(R.id.TV_FSmallBillSubsidy);
            this.TV_FAmountSum = (TextView) itemView.findViewById(R.id.TV_FAmountSum);
            this.TV_FSmallBillSubsidyPCT = (TextView) itemView.findViewById(R.id.TV_FSmallBillSubsidyPCT);
        }
    }


    public StaffWorkStatistics_GX_ListAdapter(List<StaffWorkStatistics_GX_Model> data, boolean isshowprice, boolean isshowamount) {
        super();
        this.data = data;
        isShowPrice = isshowprice;
        isShowAmount = isshowamount;
        List<StaffWorkStatistics_GX_Model> list = new ArrayList<>();
        for (StaffWorkStatistics_GX_Model datum : data) {
            if (datum.getFlevel() == 2) {
                list.add(datum);
            }
        }
        data.removeAll(list);
        LOG.e("派工单数量：" + data.size());
    }

    public void Updata(List<StaffWorkStatistics_GX_Model> data) {
        this.data = data;
        List<StaffWorkStatistics_GX_Model> list = new ArrayList<>();
        for (StaffWorkStatistics_GX_Model datum : data) {
            if (datum.getFlevel() == 2) {
                list.add(datum);
            }
        }
        data.removeAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
        if (data.get(position).getFlevel() == 1) {
            return ITEM_TYPE.ITEM1.ordinal();
        } else {
            return ITEM_TYPE.ITEM2.ordinal();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            return new Item1ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_work_statistics_1, null));
        } else {
            return new Item2ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_work_statistics_gx, null));
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StaffWorkStatistics_GX_Model sws = data.get(position);
        if (holder instanceof Item1ViewHolder) {
            LOG.e("setItem1-------" + position);
            setItem1(holder, sws);
        } else if (holder instanceof Item2ViewHolder) {
            LOG.e("setItem2-------" + position);
            setItem2(holder, sws);
        }
    }


    private void setItem1(final RecyclerView.ViewHolder holder, StaffWorkStatistics_GX_Model sws) {
        ((Item1ViewHolder) holder).LL_BKG.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dp2px(50)));
        ((Item1ViewHolder) holder).TV_FBillNO.setText(sws.getFItemNo());
    }

    private void setItem2(final RecyclerView.ViewHolder holder, StaffWorkStatistics_GX_Model sws) {
        ((Item2ViewHolder) holder).LL_BKG.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, Utils.dp2px(45)));

        if (sws.getFlevel() == 3) {
            ((Item2ViewHolder) holder).LL_BKG.setBackgroundColor(Color.parseColor("#ffffff"));
            ((Item2ViewHolder) holder).TV_FItemNo.setText(sws.getFItemNo());
            ((Item2ViewHolder) holder).TV_FProcessNumber.setText(sws.getFProcessNumber());
            ((Item2ViewHolder) holder).TV_FProcessName.setText(sws.getFProcessName());
            ((Item2ViewHolder) holder).TV_FQty.setText(sws.getFQty() + "");
            ((Item2ViewHolder) holder).TV_FPrice.setText(sws.getFPrice() + "");
            ((Item2ViewHolder) holder).TV_FAmount.setText(sws.getFAmount() + "");
            ((Item2ViewHolder) holder).TV_FSmallBillSubsidy.setText(sws.getFSmallBillSubsidy() + "");
            ((Item2ViewHolder) holder).TV_FAmountSum.setText(sws.getFAmountSum() + "");
            ((Item2ViewHolder) holder).TV_FSmallBillSubsidyPCT.setText(sws.getFSmallBillSubsidyPCT() + "");

        } else if (sws.getFlevel() == 4) {
            ((Item2ViewHolder) holder).LL_BKG.setBackgroundColor(Color.parseColor("#BCE6F8"));
            ((Item2ViewHolder) holder).TV_FItemNo.setText(sws.getFItemNo());
            ((Item2ViewHolder) holder).TV_FProcessNumber.setText("");
            ((Item2ViewHolder) holder).TV_FProcessName.setText("");
            ((Item2ViewHolder) holder).TV_FQty.setText(sws.getFQty() + "");
            ((Item2ViewHolder) holder).TV_FPrice.setText(sws.getFPrice() + "");
            ((Item2ViewHolder) holder).TV_FAmount.setText(sws.getFAmount() + "");
            ((Item2ViewHolder) holder).TV_FSmallBillSubsidy.setText(sws.getFSmallBillSubsidy() + "");
            ((Item2ViewHolder) holder).TV_FAmountSum.setText(sws.getFAmountSum() + "");
            ((Item2ViewHolder) holder).TV_FSmallBillSubsidyPCT.setText(sws.getFSmallBillSubsidyPCT() + "");
        }

        if (isShowPrice) {
            ((Item2ViewHolder) holder).TV_FPrice.setVisibility(View.VISIBLE);
        } else {
            ((Item2ViewHolder) holder).TV_FPrice.setVisibility(View.GONE);
        }
        if (isShowAmount) {
            ((Item2ViewHolder) holder).TV_FAmount.setVisibility(View.VISIBLE);
        } else {
            ((Item2ViewHolder) holder).TV_FAmount.setVisibility(View.GONE);
        }
        if (isShowPrice&&isShowAmount) {
            ((Item2ViewHolder) holder).TV_FAmountSum.setVisibility(View.VISIBLE);
        } else {
            ((Item2ViewHolder) holder).TV_FAmountSum.setVisibility(View.GONE);
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