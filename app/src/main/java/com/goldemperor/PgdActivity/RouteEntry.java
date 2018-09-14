package com.goldemperor.PgdActivity;

import java.math.BigDecimal;

public class RouteEntry implements Cloneable {

    int finterid;//分录ID
    String routebillnumber;
    String routebillname;
    String productname;
    int fmaterialid;
    String unitname;
    String fname;
    String fmodel;
    String deptname;
    String feffectdate;
    String fexpiredate;
    String fnote;//备注
    int fcreateorgid;
    int fuseorgid;
    int fqty;
    boolean fhavesize;
    int fcanreportinadvance;
    Double ftotalprice;
    int fprocessflowid;
    int fdatatype;
    String fversion;
    int fentryid;
    int fpartsid;
    int fprocessflowid1;
    String partname;//部件
    int fitemid;
    String itemnumber;
    String itemname;
    String materialcode;//物料编码
    String materialname;//物料名称
    String fprocessnumber;//工序编码
    String fprocessname;//工序名称
    float fprice;//工价
    String fnote1;
    int fcoefficient;
    String processflowname;
    String fprocessingmethod;
    int fid;

    public int getFinterid() {
        return finterid;
    }

    public void setFinterid(int finterid) {
        this.finterid = finterid;
    }

    public String getRoutebillnumber() {
        return routebillnumber;
    }

    public void setRoutebillnumber(String routebillnumber) {
        this.routebillnumber = routebillnumber;
    }

    public String getRoutebillname() {
        return routebillname;
    }

    public void setRoutebillname(String routebillname) {
        this.routebillname = routebillname;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getFmaterialid() {
        return fmaterialid;
    }

    public void setFmaterialid(int fmaterialid) {
        this.fmaterialid = fmaterialid;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFmodel() {
        return fmodel;
    }

    public void setFmodel(String fmodel) {
        this.fmodel = fmodel;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getFeffectdate() {
        return feffectdate;
    }

    public void setFeffectdate(String feffectdate) {
        this.feffectdate = feffectdate;
    }

    public String getFexpiredate() {
        return fexpiredate;
    }

    public void setFexpiredate(String fexpiredate) {
        this.fexpiredate = fexpiredate;
    }

    public String getFnote() {
        return fnote;
    }

    public void setFnote(String fnote) {
        this.fnote = fnote;
    }

    public int getFcreateorgid() {
        return fcreateorgid;
    }

    public void setFcreateorgid(int fcreateorgid) {
        this.fcreateorgid = fcreateorgid;
    }

    public int getFuseorgid() {
        return fuseorgid;
    }

    public void setFuseorgid(int fuseorgid) {
        this.fuseorgid = fuseorgid;
    }

    public int getFqty() {
        return fqty;
    }

    public void setFqty(int fqty) {
        this.fqty = fqty;
    }

    public boolean getFhavesize() {
        return fhavesize;
    }

    public void setFhavesize(boolean fhavesize) {
        this.fhavesize = fhavesize;
    }

    public int getFcanreportinadvance() {
        return fcanreportinadvance;
    }

    public void setFcanreportinadvance(int fcanreportinadvance) {
        this.fcanreportinadvance = fcanreportinadvance;
    }

    public Double getFtotalprice() {
        return ftotalprice;
    }

    public void setFtotalprice(Double ftotalprice) {
        this.ftotalprice = ftotalprice;
    }

    public int getFprocessflowid() {
        return fprocessflowid;
    }

    public void setFprocessflowid(int fprocessflowid) {
        this.fprocessflowid = fprocessflowid;
    }

    public int getFdatatype() {
        return fdatatype;
    }

    public void setFdatatype(int fdatatype) {
        this.fdatatype = fdatatype;
    }

    public String getFversion() {
        return fversion;
    }

    public void setFversion(String fversion) {
        this.fversion = fversion;
    }

    public int getFentryid() {
        return fentryid;
    }

    public void setFentryid(int fentryid) {
        this.fentryid = fentryid;
    }

    public int getFpartsid() {
        return fpartsid;
    }

    public void setFpartsid(int fpartsid) {
        this.fpartsid = fpartsid;
    }

    public int getFprocessflowid1() {
        return fprocessflowid1;
    }

    public void setFprocessflowid1(int fprocessflowid1) {
        this.fprocessflowid1 = fprocessflowid1;
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }

    public int getFitemid() {
        return fitemid;
    }

    public void setFitemid(int fitemid) {
        this.fitemid = fitemid;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public void setItemnumber(String itemnumber) {
        this.itemnumber = itemnumber;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getMaterialcode() {
        return materialcode;
    }

    public void setMaterialcode(String materialcode) {
        this.materialcode = materialcode;
    }

    public String getMaterialname() {
        return materialname;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public String getFprocessnumber() {
        return fprocessnumber;
    }

    public void setFprocessnumber(String fprocessnumber) {
        this.fprocessnumber = fprocessnumber;
    }

    public String getFprocessname() {
        return fprocessname;
    }

    public void setFprocessname(String fprocessname) {
        this.fprocessname = fprocessname;
    }

    public float getFprice() {
        return fprice;
    }

    public void setFprice(float fprice) {
        this.fprice = fprice;
    }

    public String getFnote1() {
        return fnote1;
    }

    public void setFnote1(String fnote1) {
        this.fnote1 = fnote1;
    }

    public int getFcoefficient() {
        return fcoefficient;
    }

    public void setFcoefficient(int fcoefficient) {
        this.fcoefficient = fcoefficient;
    }

    public String getProcessflowname() {
        return processflowname;
    }

    public void setProcessflowname(String processflowname) {
        this.processflowname = processflowname;
    }

    public String getFprocessingmethod() {
        return fprocessingmethod;
    }

    public void setFprocessingmethod(String fprocessingmethod) {
        this.fprocessingmethod = fprocessingmethod;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public Object clone() {
        RouteEntry o = null;
        try {
            o = (RouteEntry) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}