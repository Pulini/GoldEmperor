package com.goldemperor.PgdActivity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Nova on 2017/9/9.
 */

@Table(name = "gxpgplan")
public class GxpgPlan {
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;

    @Column(name = "style")
    private String style;

    @Column(name = "processname")
    private String processname;

    @Column(name = "usernumber")
    private String usernumber;

    @Column(name = "username")
    private String username;

    @Column(name = "empid")
    private int empid;

    @Column(name = "per")
    private float per;


    public GxpgPlan() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
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

    public int getEmpid() {
        return empid;
    }


    public void setEmpid(int empid) {
        this.empid = empid;
    }





    public float getPer() {
        return per;
    }

    public void setPer(float per) {
        this.per = per;
    }
}
