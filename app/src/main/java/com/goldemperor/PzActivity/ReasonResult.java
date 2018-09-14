package com.goldemperor.PzActivity;

import java.util.ArrayList;

/**
 * Created by Nova on 2017/8/26.
 */

public class ReasonResult {
    private String code;
    private String count;
    private ArrayList<AbnormityModel> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setData(ArrayList<AbnormityModel> data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getCount() {
        return this.count;
    }

    public ArrayList<AbnormityModel> getData() {
        return this.data;
    }

    public ReasonResult() {
    }
}
