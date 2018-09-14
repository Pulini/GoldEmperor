package com.goldemperor.PgdActivity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Nova on 2017/9/9.
 */

@Table(name = "gxpgplanreportNumber")
public class GxpgPlanReportNumberSave {
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;

    @Column(name = "planbill")
    private String planbill;

    @Column(name = "orderbill")
    private String orderbill;

    @Column(name = "processname")
    private String processname;

    @Column(name = "usernumber")
    private String usernumber;

    @Column(name = "username")
    private String username;

    @Column(name = "recordcount")
    private float recordcount;

    public GxpgPlanReportNumberSave() {
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

    public float getRecordcount() {
        return recordcount;
    }


    public void setRecordcount(float recordcount) {
        this.recordcount = recordcount;
    }

    public String getProcessname() {
        return processname;
    }
    public void setProcessname(String processname) {
        this.processname = processname;
    }


    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
