package com.goldemperor.GxReport;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nova on 2017/8/16.
 */

public class Order implements Parcelable {
    private String FCardNo;
    private String FEmpID;
    private String FQty;

    public Order() {
    }

    public void setFCardNo(String FCardNo){
        this.FCardNo=FCardNo;
    }

    public void setFEmpID(String FEmpID){
        this.FEmpID=FEmpID;
    }

    public void setFQty(String FQty){
        this.FQty=FQty;
    }

    public String getFCardNo(){
        return this.FCardNo;
    }

    public String getFEmpID(){
        return this.FEmpID;
    }

    public String getFQty(){
        return this.FQty;
    }
    protected Order(Parcel in) {
        FCardNo = in.readString();
        FEmpID = in.readString();
        FQty = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(FCardNo);
        dest.writeString(FEmpID);
        dest.writeString(FQty);
    }
}
