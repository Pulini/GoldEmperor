package com.goldemperor.PgdActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nova on 2017/8/26.
 */

public class PgdResult {
    private ArrayList<WorkCardPlan> data;


    public void setData(ArrayList<WorkCardPlan> data) {
        this.data = data;
    }


    public ArrayList<WorkCardPlan> getData() {
        return this.data;
    }

    public PgdResult() {
    }
}
