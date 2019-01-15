package com.goldemperor.PgdActivity;

import java.util.Date;


public class Sc_ProcessWorkCardEntry implements Cloneable {
    private String fmtono;

    private int fpartsid;//部位ID

    private int fsrcicmointerid;//指令单ID

    private int fprdmoid;//生产订单ID

    private int fmoid;//null 指令单ID 已弃用  已改用fsrcicmointerid

    private int foperplanningid;//工序计划单ID

    private int fproductid;//产品ID

    private int fdeptmentid;//部门id

    private int fitemid;//物料ID

    private int fprocessid;//工序ID

    private String fprocessnumber;//工序编号

    private String fprocessname;//工序名称

    private String fsize;//尺码

    private int fempid;//员工ID

    private String fmochinesid;//null

    private String fmochinecode;//null

    private double fqty;//实际派工数，用于操作数据

    private Date fplanstartdate;//计划开始日期

    private Date fplanfinishdate;//计划结束日期

    private int ffinishqty;//工序汇报数，禁止操作，由下游工序汇报单反写

    private Date flastfinishdate;//最后一次提交日期

    private double fprice;//单价

    private double famount;//金额

    private int fprocesserid;//0

    private Date fprocessdate;//null

    private String fcardno;//卡号

    private String fnotes;//备注

    private String fversion;// 版本号 0.1

    private int fsourceinterid;//源单主键ID

    private int fsourceentryid;//源单分录ID

    private int fprocessoutputid;

    private String fbatchno;

    private int fgroupprintno;//分组打印序号

    private int fmodelid;//工厂型体ID

    private int froutingid;//工艺路线ID

    private double finterid;//单据主键ID

    private int fentryid;//单据分录ID

    private String jobNumber;

    private String name;

    public boolean haveSave;

    private int fprocessflowid;//制程ID 这里为针车 fprocessflowid=2

    private String fprocessingmethod;

    private boolean isOpen;//是否关闭 isOpen=true 开始， false 关闭

    private int  fsourceentryfid;//源单分录自增长FID

    private int  frouteentryfid;//工艺路线分录自增长FID

    private int  foperplanningentryfid;//工序计划分录自增长FID

    private int  foperplanninginterid;//工序计划主键ID

    private int  foperplanningentryid;//工序计划分录ID

    private float  reportedqty;//工序计划分录ID

    private int fid;//分录自增长ID  取自后台数据库自增长ID

    private int fpreschedulingqty;//预排数

    private float fpartitioncoefficient;//系数

    private float fsourceqty;//现在改为派工单的 派工数量

    private double fmustqty;//应派工数量;

    private int frouteinterfid;

    public boolean isHaveSave() {
        return haveSave;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getFrouteinterfid() {
        return frouteinterfid;
    }

    public void setFrouteinterfid(int frouteinterfid) {
        this.frouteinterfid = frouteinterfid;
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

    public void setFpreschedulingqty(int fpreschedulingqty) {
        this.fpreschedulingqty = fpreschedulingqty;
    }

    public int getFpreschedulingqty() {
        return fpreschedulingqty;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public void setReportedqty(float reportedqty) {
        this.reportedqty = reportedqty;
    }

    public float getReportedqty() {
        return reportedqty;
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


    public Sc_ProcessWorkCardEntry(){
        isOpen=true;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
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


    public boolean getHaveSave() {
        return haveSave;
    }

    public void setHaveSave(boolean haveSave) {
        this.haveSave = haveSave;
    }


    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFinterid() {
        return finterid;
    }

    public void setFinterid(double finterid) {
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

    public double getFmustqty() {
        return fmustqty;
    }

    public void setFmustqty(double fmustqty) {
        this.fmustqty = fmustqty;
    }

    public double getFqty() {
        return fqty;
    }

    public void setFqty(double fqty) {
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

    public int getFfinishqty() {
        return ffinishqty;
    }

    public void setFfinishqty(int ffinishqty) {
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
        Sc_ProcessWorkCardEntry o = null;
        try {
            o = (Sc_ProcessWorkCardEntry) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}