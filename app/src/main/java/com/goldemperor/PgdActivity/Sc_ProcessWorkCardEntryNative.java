package com.goldemperor.PgdActivity;

import java.util.Date;


public class Sc_ProcessWorkCardEntryNative implements Cloneable {
    private boolean isOpen;

    private String fmtono;

    private int fpartsid;

    private int fsrcicmointerid;

    private int fprdmoid;

    private int fmoid;//null

    private int foperplanningid;

    private int fproductid;

    private int fdeptmentid;

    private int fitemid;

    private int fprocessid;//null

    private String fprocessnumber;

    private String fprocessname;

    private String fsize;//null

    private int fempid;

    private String fmochinesid;//null

    private String fmochinecode;//null

    private long fmustqty;

    private long fqty;

    private Date fplanstartdate;

    private Date fplanfinishdate;

    private long ffinishqty;

    private Date flastfinishdate;

    private double fprice;

    private double famount;

    private int fprocesserid;//0

    private Date fprocessdate;//null

    private String fcardno;

    private String fnotes;

    private String fversion;//0.1

    private int fsourceinterid;

    private int fsourceentryid;

    private int fprocessoutputid;

    private String fbatchno;

    private int fgroupprintno;

    private int fmodelid;

    private int froutingid;

    private Long finterid;

    private int fentryid;

    private int fprocessflowid;

    private String fprocessingmethod;

    private int  fsourceentryfid;//源单分录自增长FID

    private int  frouteentryfid;//工艺路线分录自增长FID

    private int  foperplanningentryfid;//工序计划分录自增长FID

    private int  foperplanninginterid;//工序计划主键ID

    private int  foperplanningentryid;//工序计划分路ID


    private Long fid;

    private float fpreschedulingqty;

    private float fpartitioncoefficient;

    private float fsourceqty;

    private int frouteinterfid;

    public int getFrouteinterfid() {
        return frouteinterfid;
    }

    public void setFrouteinterfid(int frouteinterfid) {
        this.frouteinterfid = frouteinterfid;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }



    public void setFsourceqty(float fsourceqty) {
        this.fsourceqty = fsourceqty;
    }

    public float getFsourceqty() {
        return fsourceqty;
    }

    public void setFpartitioncoefficient(float fpartitioncoefficient) {
        this.fpartitioncoefficient = fpartitioncoefficient;
    }

    public float getFpartitioncoefficient() {
        return fpartitioncoefficient;
    }

    public void setFpreschedulingqty(float fpreschedulingqty) {
        this.fpreschedulingqty = fpreschedulingqty;
    }

    public float getFpreschedulingqty() {
        return fpreschedulingqty;
    }
    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public int getFsourceentryfid() {
        return fsourceentryfid;
    }

    public void setFsourceentryfid(int fsourceentryfid) {
        this.fsourceentryfid = fsourceentryfid;
    }

    public int getFrouteentryfid() {
        return frouteentryfid;
    }

    public void setFrouteentryfid(int frouteentryfid) {
        this.frouteentryfid = frouteentryfid;
    }

    public int getFoperplanningentryfid() {
        return foperplanningentryfid;
    }

    public void setFoperplanningentryfid(int foperplanningentryfid) {
        this.foperplanningentryfid = foperplanningentryfid;
    }


    public Sc_ProcessWorkCardEntryNative(){
    }


    public int getFoperplanninginterid() {
        return foperplanninginterid;
    }

    public void setFoperplanninginterid(int foperplanninginterid) {
        this.foperplanninginterid = foperplanninginterid;
    }

    public int getFoperplanningentryid() {
        return foperplanningentryid;
    }

    public void setFoperplanningentryid(int foperplanningentryid) {
        this.foperplanningentryid = foperplanningentryid;
    }

    public int getFprocessflowid() {
        return fprocessflowid;
    }

    public void setFprocessflowid(int fprocessflowid) {
        this.fprocessflowid = fprocessflowid;
    }

    public String getFprocessingmethod() {
        return fprocessingmethod;
    }

    public void setFprocessingmethod(String fprocessingmethod) {
        this.fprocessingmethod = fprocessingmethod;
    }

    public Long getFinterid() {
        return finterid;
    }

    public void setFinterid(Long finterid) {
        this.finterid = finterid;
    }

    public int getFentryid() {
        return fentryid;
    }

    public void setFentryid(int fentryid) {
        this.fentryid = fentryid;
    }

    public String getFmtono() {
        return fmtono;
    }

    public void setFmtono(String fmtono) {
        this.fmtono = fmtono == null ? null : fmtono.trim();
    }

    public int getFpartsid() {
        return fpartsid;
    }

    public void setFpartsid(int fpartsid) {
        this.fpartsid = fpartsid;
    }

    public int getFsrcicmointerid() {
        return fsrcicmointerid;
    }

    public void setFsrcicmointerid(int fsrcicmointerid) {
        this.fsrcicmointerid = fsrcicmointerid;
    }

    public int getFprdmoid() {
        return fprdmoid;
    }

    public void setFprdmoid(int fprdmoid) {
        this.fprdmoid = fprdmoid;
    }

    public int getFmoid() {
        return fmoid;
    }

    public void setFmoid(int fmoid) {
        this.fmoid = fmoid;
    }

    public int getFoperplanningid() {
        return foperplanningid;
    }

    public void setFoperplanningid(int foperplanningid) {
        this.foperplanningid = foperplanningid;
    }

    public int getFproductid() {
        return fproductid;
    }

    public void setFproductid(int fproductid) {
        this.fproductid = fproductid;
    }

    public int getFdeptmentid() {
        return fdeptmentid;
    }

    public void setFdeptmentid(int fdeptmentid) {
        this.fdeptmentid = fdeptmentid;
    }

    public int getFitemid() {
        return fitemid;
    }

    public void setFitemid(int fitemid) {
        this.fitemid = fitemid;
    }

    public int getFprocessid() {
        return fprocessid;
    }

    public void setFprocessid(int fprocessid) {
        this.fprocessid = fprocessid;
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

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize == null ? null : fsize.trim();
    }

    public int getFempid() {
        return fempid;
    }

    public void setFempid(int fempid) {
        this.fempid = fempid;
    }

    public String getFmochinesid() {
        return fmochinesid;
    }

    public void setFmochinesid(String fmochinesid) {
        this.fmochinesid = fmochinesid == null ? null : fmochinesid.trim();
    }

    public String getFmochinecode() {
        return fmochinecode;
    }

    public void setFmochinecode(String fmochinecode) {
        this.fmochinecode = fmochinecode == null ? null : fmochinecode.trim();
    }

    public long getFmustqty() {
        return fmustqty;
    }

    public void setFmustqty(long fmustqty) {
        this.fmustqty = fmustqty;
    }

    public long getFqty() {
        return fqty;
    }

    public void setFqty(long fqty) {
        this.fqty = fqty;
    }

    public Date getFplanstartdate() {
        return fplanstartdate;
    }

    public void setFplanstartdate(Date fplanstartdate) {
        this.fplanstartdate = fplanstartdate;
    }

    public Date getFplanfinishdate() {
        return fplanfinishdate;
    }

    public void setFplanfinishdate(Date fplanfinishdate) {
        this.fplanfinishdate = fplanfinishdate;
    }

    public long getFfinishqty() {
        return ffinishqty;
    }

    public void setFfinishqty(long ffinishqty) {
        this.ffinishqty = ffinishqty;
    }

    public Date getFlastfinishdate() {
        return flastfinishdate;
    }

    public void setFlastfinishdate(Date flastfinishdate) {
        this.flastfinishdate = flastfinishdate;
    }

    public double getFprice() {
        return fprice;
    }

    public void setFprice(double fprice) {
        this.fprice = fprice;
    }

    public double getFamount() {
        return famount;
    }

    public void setFamount(double famount) {
        this.famount = famount;
    }

    public int getFprocesserid() {
        return fprocesserid;
    }

    public void setFprocesserid(int fprocesserid) {
        this.fprocesserid = fprocesserid;
    }

    public Date getFprocessdate() {
        return fprocessdate;
    }

    public void setFprocessdate(Date fprocessdate) {
        this.fprocessdate = fprocessdate;
    }

    public String getFcardno() {
        return fcardno;
    }

    public void setFcardno(String fcardno) {
        this.fcardno = fcardno == null ? null : fcardno.trim();
    }

    public String getFnotes() {
        return fnotes;
    }

    public void setFnotes(String fnotes) {
        this.fnotes = fnotes == null ? null : fnotes.trim();
    }

    public String getFversion() {
        return fversion;
    }

    public void setFversion(String fversion) {
        this.fversion = fversion == null ? null : fversion.trim();
    }

    public int getFsourceinterid() {
        return fsourceinterid;
    }

    public void setFsourceinterid(int fsourceinterid) {
        this.fsourceinterid = fsourceinterid;
    }

    public int getFsourceentryid() {
        return fsourceentryid;
    }

    public void setFsourceentryid(int fsourceentryid) {
        this.fsourceentryid = fsourceentryid;
    }

    public int getFprocessoutputid() {
        return fprocessoutputid;
    }

    public void setFprocessoutputid(int fprocessoutputid) {
        this.fprocessoutputid = fprocessoutputid;
    }

    public String getFbatchno() {
        return fbatchno;
    }

    public void setFbatchno(String fbatchno) {
        this.fbatchno = fbatchno == null ? null : fbatchno.trim();
    }

    public int getFgroupprintno() {
        return fgroupprintno;
    }

    public void setFgroupprintno(int fgroupprintno) {
        this.fgroupprintno = fgroupprintno;
    }

    public int getFmodelid() {
        return fmodelid;
    }

    public void setFmodelid(int fmodelid) {
        this.fmodelid = fmodelid;
    }

    public int getFroutingid() {
        return froutingid;
    }

    public void setFroutingid(int froutingid) {
        this.froutingid = froutingid;
    }

    public Object clone() {
        Sc_ProcessWorkCardEntryNative o = null;
        try {
            o = (Sc_ProcessWorkCardEntryNative) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}