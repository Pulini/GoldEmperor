package com.goldemperor.PgdActivity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Nova on 2017/9/9.
 */

@Table(name = "gxpgplanstatus")
public class GxpgPlanStatus {
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;

    @Column(name = "planbill")
    private String planbill;

    @Column(name = "orderbill")
    private String orderbill;

    @Column(name = "status")
    private String status;


    public GxpgPlanStatus() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPlanbill() {
        return planbill;
    }
    public void setPlanbill(String planbill) {
        this.planbill = planbill;
    }

    public String getOrderbill() {
        return orderbill;
    }
    public void setOrderbill(String orderbill) {
        this.orderbill = orderbill;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}
