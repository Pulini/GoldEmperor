package com.goldemperor.PzActivity;

import com.goldemperor.PgdActivity.ProcessWorkCardPlanEntry;

import java.util.ArrayList;

/**
 * Created by Nova on 2017/8/26.
 */

public class YcResult {
    private String code;
    private String count;
    private ArrayList<Sc_WorkCardAbnormity> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setData(ArrayList<Sc_WorkCardAbnormity> data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getCount() {
        return this.count;
    }

    public ArrayList<Sc_WorkCardAbnormity> getData() {
        return this.data;
    }

    public YcResult() {
    }
}
