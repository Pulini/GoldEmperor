package com.goldemperor.PgdActivity;

import java.util.List;

/**
 * Created by Nova on 2017/8/26.
 */

public class ScResult {
    private String code;
    private String count;
    private List<SCfinterid> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setData(List<SCfinterid> data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getCount() {
        return this.count;
    }

    public List<SCfinterid> getData() {
        return this.data;
    }

    public ScResult() {
    }
}
