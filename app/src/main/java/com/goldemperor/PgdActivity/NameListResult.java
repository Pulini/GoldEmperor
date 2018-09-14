package com.goldemperor.PgdActivity;

import java.util.ArrayList;

/**
 * Created by Nova on 2017/8/26.
 */

public class NameListResult {
    private String code;
    private String count;
    private ArrayList<NameList> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setData(ArrayList<NameList> data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getCount() {
        return this.count;
    }

    public ArrayList<NameList> getData() {
        return this.data;
    }

    public NameListResult() {
    }
}
