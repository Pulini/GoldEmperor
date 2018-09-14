package com.goldemperor.PgdActivity;

import java.math.BigDecimal;

public class Log_ProcessOutPutEntry {

    private Integer fempid;//员工ID

    private String fprocessnumber;//工序编码

    private String fprocessname;//工序名称

    private String name;//员工

    private BigDecimal fqty;//汇报数量

    private boolean isOpen;

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getFempid() {
        return fempid;
    }

    public void setFempid(Integer fempid) {
        this.fempid = fempid;
    }


    public String getFprocessnumber() {
        return fprocessnumber;
    }

    public void setFprocessnumber(String fprocessnumber) {
        this.fprocessnumber = fprocessnumber == null ? null : fprocessnumber.trim();
    }

    public String getFprocessname() {
        return fprocessname;
    }

    public void setFprocessname(String fprocessname) {
        this.fprocessname = fprocessname == null ? null : fprocessname.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public BigDecimal getFqty() {
        return fqty;
    }

    public void setFqty(BigDecimal fqty) {
        this.fqty = fqty;
    }

}