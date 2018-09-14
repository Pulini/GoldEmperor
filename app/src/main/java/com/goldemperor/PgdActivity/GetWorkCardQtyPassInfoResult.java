package com.goldemperor.PgdActivity;

import java.util.ArrayList;

/**
 * Created by Nova on 2017/8/26.
 */

public class GetWorkCardQtyPassInfoResult {
    private ArrayList<QtyPass> data;

    public void setData(ArrayList<QtyPass> data) {
        this.data = data;
    }


    public ArrayList<QtyPass> getData() {
        return this.data;
    }

    public GetWorkCardQtyPassInfoResult() {
    }
}
