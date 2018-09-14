package com.goldemperor.GxReport;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nova on 2017/8/16.
 */

public class SubmitJson {
    private String D_BarCode;
    private String D_EmpID;
    private String D_Qty;

    public SubmitJson() {
    }

    public void setD_BarCode(String D_BarCode) {
        this.D_BarCode = D_BarCode;
    }

    public void setD_EmpID(String D_EmpID) {
        this.D_EmpID = D_EmpID;
    }

    public void setD_Qty(String D_Qty) {
        this.D_Qty = D_Qty;
    }

    public String getD_BarCode() {
        return this.D_BarCode;
    }

    public String getD_EmpID() {
        return this.D_EmpID;
    }

    public String getD_Qty() {
        return this.D_Qty;
    }

}
