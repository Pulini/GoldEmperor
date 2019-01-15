package com.goldemperor.PgdActivity;

public class ProcessWorkCardPlanEntry implements Cloneable{
    private Long finterid;

    private int forganizename;//组织名称;

    private String processbillnumber;//工序派工单号;

    private String fbilldate;//单据日期;

    private int havecode;//有否配码;

    private Short fbilltype;//单据类型;
    
    private int fpushtypeid;//下推类型
    private String workshop;//生产车间;

    private String remark;//备注;

    private String batch2;//批次2;

    private String planbill;//计划跟踪号;

    private String productnumber;//产品编码;

    private int fscmoid ;//指令单ID,
    private String dispatching;//派工;

    private String fempnumber;//派工人代码;

    private String fbiller;//制单;

    private String fchecker;//审核;

    private String fcloser;//关闭;

    private int fsrcicmointerid;

    private int fitemid;

    private int fpartsid;

    private int fproductid;

    private int fmodelid;

    private int fdeptmentid;

    private int foperplanningid;

    private int fprdmoid;

    private int fempid;

    private int fsourceinterid;

    private int fsourceentryid;

    private int fentryid;//序号;

    private String fmtono;//计划跟踪号

    private String productionbill;//生产订单编号;

    private String processplanbill;//工序计划号;

    private String orderbill;//生产派工单号;

    private String parts;//部件;

    private String plantbody;//工厂型体;

    private String productname;//产品名称;

    private String productcode;//产品代码;

    private String materialcode;//物料编码;

    private String materialname;//物料名称;

    private String fcolor;//颜色;

    private String processcode;//工序编码;

    private String processname;//工序名称;

    private int pringgroupnumber;//分组打印序号;

    private String femp;//员工;

    private String fdeptmentname;//加工单位;

    private String fsize;//尺码;

    private double fmustqty;//应派工数量;

    private double ffinishqty;//计工数量;

    private double undispatchingnumber;//剩余派工数量;

    private double price;//工价;

    private String productionnumber;//生产单号;

    private double fmoney;//金额;

    private String fnotes;

    private String barcode;//条形码;

    private String processreportnumber;//工序汇报单号;

    private String fversion;//版本号;

    private String batch;//批次

    private int expr1;

    private int expr2;

    private int fprocessoutputid;

    private int froutingid;

    private int fprocessflowid;

    private String fprocessingmethod;

    private int  fsourceentryfid;//源单分录自增长FID

    private int  frouteentryfid;//工艺路线分录自增长FID

    private int  foperplanningentryfid;//工序计划分录自增长FID

    private int  foperplanninginterid;//工序计划主键ID

    private int  foperplanningentryid;//工序计划分路ID

    private float  reportedqty;//工序计划分路ID

    private int fid;

    private double fqty;

    private float fpartitioncoefficient;

    private float fsourceqty;


    private int frouteinterfid;

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

    public double getFqty() {
        return fqty;
    }

    public void setFqty(double fqty) {
        this.fqty = fqty;
    }
    private float fpreschedulingqty;

    public void setFpreschedulingqty(float fpreschedulingqty) {
        this.fpreschedulingqty = fpreschedulingqty;
    }

    public float getFpreschedulingqty() {
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


    public void setFroutingid(int froutingid) {
        this.froutingid = froutingid;
    }

    public int getFroutingid() {
        return froutingid;
    }

    public Long getFinterid() {
        return finterid;
    }

    public void setFinterid(Long finterid) {
        this.finterid = finterid;
    }

    public int getForganizename() {
        return forganizename;
    }

    public void setForganizename(int forganizename) {
        this.forganizename = forganizename;
    }

    public String getProcessbillnumber() {
        return processbillnumber;
    }

    public void setProcessbillnumber(String processbillnumber) {
        this.processbillnumber = processbillnumber == null ? null : processbillnumber.trim();
    }

    public String getFbilldate() {
        return fbilldate;
    }

    public void setFbilldate(String fbilldate) {
        this.fbilldate = fbilldate;
    }

    public int getHavecode() {
        return havecode;
    }

    public void setHavecode(int havecode) {
        this.havecode = havecode;
    }

    public Short getFbilltype() {
        return fbilltype;
    }

    public void setFbilltype(Short fbilltype) {
        this.fbilltype = fbilltype;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop == null ? null : workshop.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBatch2() {
        return batch2;
    }

    public void setBatch2(String batch2) {
        this.batch2 = batch2 == null ? null : batch2.trim();
    }

    public String getPlanbill() {
        return planbill.toUpperCase();
    }

    public void setPlanbill(String planbill) {
        this.planbill = planbill == null ? null : planbill.trim().toUpperCase();
    }

    public String getProductnumber() {
        return productnumber;
    }

    public void setProductnumber(String productnumber) {
        this.productnumber = productnumber == null ? null : productnumber.trim();
    }

    public String getDispatching() {
        return dispatching;
    }

    public void setDispatching(String dispatching) {
        this.dispatching = dispatching == null ? null : dispatching.trim();
    }

    public String getFempnumber() {
        return fempnumber;
    }

    public void setFempnumber(String fempnumber) {
        this.fempnumber = fempnumber == null ? null : fempnumber.trim();
    }

    public String getFbiller() {
        return fbiller;
    }

    public void setFbiller(String fbiller) {
        this.fbiller = fbiller == null ? null : fbiller.trim();
    }

    public String getFchecker() {
        return fchecker;
    }

    public void setFchecker(String fchecker) {
        this.fchecker = fchecker == null ? null : fchecker.trim();
    }

    public String getFcloser() {
        return fcloser;
    }

    public void setFcloser(String fcloser) {
        this.fcloser = fcloser == null ? null : fcloser.trim();
    }

    public int getFsrcicmointerid() {
        return fsrcicmointerid;
    }

    public void setFsrcicmointerid(int fsrcicmointerid) {
        this.fsrcicmointerid = fsrcicmointerid;
    }

    public int getFitemid() {
        return fitemid;
    }

    public void setFitemid(int fitemid) {
        this.fitemid = fitemid;
    }

    public int getFpartsid() {
        return fpartsid;
    }

    public void setFpartsid(int fpartsid) {
        this.fpartsid = fpartsid;
    }

    public int getFproductid() {
        return fproductid;
    }

    public void setFproductid(int fproductid) {
        this.fproductid = fproductid;
    }

    public int getFmodelid() {
        return fmodelid;
    }

    public void setFmodelid(int fmodelid) {
        this.fmodelid = fmodelid;
    }

    public int getFdeptmentid() {
        return fdeptmentid;
    }

    public void setFdeptmentid(int fdeptmentid) {
        this.fdeptmentid = fdeptmentid;
    }

    public int getFoperplanningid() {
        return foperplanningid;
    }

    public void setFoperplanningid(int foperplanningid) {
        this.foperplanningid = foperplanningid;
    }

    public int getFprdmoid() {
        return fprdmoid;
    }

    public void setFprdmoid(int fprdmoid) {
        this.fprdmoid = fprdmoid;
    }

    public int getFempid() {
        return fempid;
    }

    public void setFempid(int fempid) {
        this.fempid = fempid;
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

    public String getProductionbill() {
        return productionbill.toUpperCase();
    }

    public void setProductionbill(String productionbill) {
        this.productionbill = productionbill == null ? null : productionbill.trim();
    }

    public String getProcessplanbill() {
        return processplanbill.toUpperCase();
    }

    public void setProcessplanbill(String processplanbill) {
        this.processplanbill = processplanbill == null ? null : processplanbill.trim();
    }

    public String getOrderbill() {
        return orderbill.toUpperCase();
    }

    public void setOrderbill(String orderbill) {
        this.orderbill = orderbill == null ? null : orderbill.trim();
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts == null ? null : parts.trim();
    }

    public String getPlantbody() {
        return plantbody.toUpperCase();
    }

    public void setPlantbody(String plantbody) {
        this.plantbody = plantbody == null ? null : plantbody.trim();
    }

    public String getProductname() {
        return productname.toUpperCase();
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public String getProductcode() {
        return productcode.toUpperCase();
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode == null ? null : productcode.trim();
    }

    public String getMaterialcode() {
        return materialcode.toUpperCase();
    }

    public void setMaterialcode(String materialcode) {
        this.materialcode = materialcode == null ? null : materialcode.trim();
    }

    public String getMaterialname() {
        return materialname.toUpperCase();
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname == null ? null : materialname.trim();
    }

    public String getFcolor() {
        return fcolor;
    }

    public void setFcolor(String fcolor) {
        this.fcolor = fcolor == null ? null : fcolor.trim();
    }

    public String getProcesscode() {
        return processcode;
    }

    public void setProcesscode(String processcode) {
        this.processcode = processcode == null ? null : processcode.trim();
    }

    public String getProcessname() {
        return processname.toUpperCase();
    }

    public void setProcessname(String processname) {
        this.processname = processname == null ? null : processname.trim();
    }

    public int getPringgroupnumber() {
        return pringgroupnumber;
    }

    public void setPringgroupnumber(int pringgroupnumber) {
        this.pringgroupnumber = pringgroupnumber;
    }

    public String getFemp() {
        return femp;
    }

    public void setFemp(String femp) {
        this.femp = femp == null ? null : femp.trim();
    }

    public String getFdeptmentname() {
        return fdeptmentname;
    }

    public void setFdeptmentname(String fdeptmentname) {
        this.fdeptmentname = fdeptmentname == null ? null : fdeptmentname.trim();
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize == null ? null : fsize.trim();
    }

    public double getFmustqty() {
        return fmustqty;
    }

    public void setFmustqty(double fmustqty) {
        this.fmustqty = fmustqty;
    }


    public double getFfinishqty() {
        return ffinishqty;
    }

    public void setFfinishqty(double ffinishqty) {
        this.ffinishqty = ffinishqty;
    }

    public double getUndispatchingnumber() {
        return undispatchingnumber;
    }

    public void setUndispatchingnumber(double undispatchingnumber) {
        this.undispatchingnumber = undispatchingnumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductionnumber() {
        return productionnumber;
    }

    public void setProductionnumber(String productionnumber) {
        this.productionnumber = productionnumber == null ? null : productionnumber.trim();
    }

    public double getFmoney() {
        return fmoney;
    }

    public void setFmoney(double fmoney) {
        this.fmoney = fmoney;
    }

    public String getFnotes() {
        return fnotes;
    }

    public void setFnotes(String fnotes) {
        this.fnotes = fnotes == null ? null : fnotes.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getProcessreportnumber() {
        return processreportnumber;
    }

    public void setProcessreportnumber(String processreportnumber) {
        this.processreportnumber = processreportnumber == null ? null : processreportnumber.trim();
    }

    public String getFversion() {
        return fversion;
    }

    public void setFversion(String fversion) {
        this.fversion = fversion == null ? null : fversion.trim();
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public int getExpr1() {
        return expr1;
    }

    public void setExpr1(int expr1) {
        this.expr1 = expr1;
    }

    public int getExpr2() {
        return expr2;
    }

    public void setExpr2(int expr2) {
        this.expr2 = expr2;
    }

    public int getFprocessoutputid() {
        return fprocessoutputid;
    }

    public void setFprocessoutputid(int fprocessoutputid) {
        this.fprocessoutputid = fprocessoutputid;
    }

	public int getFpushtypeid() {
		return fpushtypeid;
	}

	public void setFpushtypeid(int fpushtypeid) {
		this.fpushtypeid = fpushtypeid;
	}

	public int getFscmoid() {
		return fscmoid;
	}

	public void setFscmoid(int fscmoid) {
		this.fscmoid = fscmoid;
	}

    public Object clone() {
        ProcessWorkCardPlanEntry o = null;
        try {
            o = (ProcessWorkCardPlanEntry) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
	
}