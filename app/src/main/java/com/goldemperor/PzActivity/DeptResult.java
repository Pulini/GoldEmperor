package com.goldemperor.PzActivity;

import java.util.ArrayList;

/**
 * Created by Nova on 2017/8/26.
 */

public class DeptResult {
    private String code;
    private String count;
    private ArrayList<DeptModel> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setData(ArrayList<DeptModel> data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getCount() {
        return this.count;
    }

    public ArrayList<DeptModel> getData() {
        return this.data;
    }

    public DeptResult() {
    }
}
