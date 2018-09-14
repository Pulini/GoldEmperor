package com.goldemperor.PgdActivity;

import java.util.List;

/**
 * Created by Nova on 2017/9/26.
 */

public class ProcessPassQty {
    private String code;
    private String count;
    private List<CumulativeNumber> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setData(List<CumulativeNumber> data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getCount() {
        return this.count;
    }

    public List<CumulativeNumber> getData() {
        return this.data;
    }

    public ProcessPassQty(){

    }
}
