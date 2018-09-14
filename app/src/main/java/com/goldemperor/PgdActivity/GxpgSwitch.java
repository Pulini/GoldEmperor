package com.goldemperor.PgdActivity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Nova on 2017/9/9.
 */

@Table(name = "gxpgopen")
public class GxpgSwitch {
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;

    @Column(name = "style")
    private String style;

    @Column(name = "processname")
    private String processname;

    @Column(name = "open")
    private boolean open;


    public GxpgSwitch() {
        open=true;
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

    public boolean getOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }

}
