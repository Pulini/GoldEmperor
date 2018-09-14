package com.goldemperor.PgdActivity;

import java.util.ArrayList;

/**
 * Created by Nova on 2017/8/26.
 */

public class RouteResult {
    private ArrayList<RouteEntry> data;

    public void setData(ArrayList<RouteEntry> data) {
        this.data = data;
    }

    public ArrayList<RouteEntry> getData() {
        return this.data;
    }

    public RouteResult() {
    }
}
