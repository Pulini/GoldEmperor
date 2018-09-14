package com.goldemperor.PgdActivity;

import java.util.ArrayList;

/**
 * Created by Nova on 2017/8/26.
 */

public class GxpgResult {
    private ArrayList<ProcessWorkCardPlanEntry> data;

    public void setData(ArrayList<ProcessWorkCardPlanEntry> data) {
        this.data = data;
    }

    public ArrayList<ProcessWorkCardPlanEntry> getData() {
        return this.data;
    }

    public GxpgResult() {
    }
}
