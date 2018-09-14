package com.goldemperor.PgdActivity;

import java.math.BigDecimal;
import java.util.Date;

public class Sc_ProcessOutPutEntry {
    private Integer finterid;

    private Integer forganizeid;//生产组织

    private Integer fbilltypeid;//单据类型;

    private Integer fbillstyle;//汇报类型;

    private Integer fpushtypeid;//下推类型;

    private String fbillnumber;//汇报单号;

    private String outputdate;//汇报日期;

    private String fdeptnumber;//生产车间;


    private Integer fcodecollectid;//采集单id;

    private Double ftotalqty;//合计数量;

    private Double ftotalamount;//合计金额;

    private String fbiller;//制单;

    private String fchecker;//审核;

    private String fbillerid;

    private String fcheckerid;

    private String outputname;//汇报;

    private Integer years;//年;

    private Integer months;//月;

    private Integer fpartid;//部件ID

    private Integer fproductid;//形体ID

    private Integer fprdmoid;//生产订单ID

    private Integer fmoid;//指令单ID

    private Integer fempid;//员工ID

    private Integer foperplanningid;//工序计划ID

    private Integer frow;//序号

    private String fmtono;//计划跟踪号

    private String productionorders;//生产订单编号

    private String processplannumber;//工序计划号

    private String processoutputnumber;//工序派工单号

    private String partname;//部件

    private String productnumber;//产品编码

    private String productname;//产品名称

    private Integer fitemid;

    private String materialcode;//物料编码

    private String fprocessnumber;//工序编码

    private String fprocessname;//工序名称

    private String femp;//员工

    private String fsize;//尺码

    private BigDecimal fqty;//汇报数量

    private BigDecimal fprice;//工价

    private BigDecimal famount;//金额

    private String fnote;//备注

    private BigDecimal fsourceqty;//派工数量

    private String fcardno;//条形码

    private Integer fsourceinterid;

    private Integer fsourceentryid;

    private Integer fworkcardinterid;//派工单ID

    private String productionorder;//生产单号

    private String fproductname;//型体

    private String routname;//工艺路线

    private Integer froutingid;//路线ID

    public int fnewworkcardinterid;

    public int fnewworkcardentryid;

    private Integer fprocessflowid;

    private String fprocessingmethod;

    private Integer foperplanninginterid;//工序计划主键ID

    private Integer foperplanningentryid;//工序计划分路ID

    private Integer  fsourceentryfid;//源单分录自增长FID

    private Integer  frouteentryfid;//工艺路线分录自增长FID

    private Integer  foperplanningentryfid;//工序计划分录自增长FID

    private Integer  fnewworkcardentryfid;//工序计划分录自增长FID


    public Integer getFsourceentryfid() {
        return fsourceentryfid;
    }

    public void setFsourceentryfid(Integer fsourceentryfid) {
        this.fsourceentryfid = fsourceentryfid;
    }

    public Integer getFrouteentryfid() {
        return frouteentryfid;
    }

    public void setFrouteentryfid(Integer frouteentryfid) {
        this.frouteentryfid = frouteentryfid;
    }

    public Integer getFoperplanningentryfid() {
        return foperplanningentryfid;
    }

    public void setFoperplanningentryfid(Integer foperplanningentryfid) {
        this.foperplanningentryfid = foperplanningentryfid;
    }

    public Integer getFoperplanninginterid() {
        return foperplanninginterid;
    }

    public void setFoperplanninginterid(Integer foperplanninginterid) {
        this.foperplanninginterid = foperplanninginterid;
    }

    public Integer getFoperplanningentryid() {
        return foperplanningentryid;
    }

    public void setFoperplanningentryid(Integer foperplanningentryid) {
        this.foperplanningentryid = foperplanningentryid;
    }

    public Integer getFprocessflowid() {
        return fprocessflowid;
    }

    public void setFprocessflowid(Integer fprocessflowid) {
        this.fprocessflowid = fprocessflowid;
    }

    public String getFprocessingmethod() {
        return fprocessingmethod;
    }

    public void setFprocessingmethod(String fprocessingmethod) {
        this.fprocessingmethod = fprocessingmethod;
    }

    public int getFnewworkcardinterid() {
        return fnewworkcardinterid;
    }

    public void setFnewworkcardinterid(Integer fnewworkcardInterid) {
        this.fnewworkcardinterid = fnewworkcardInterid;
    }

    public int getFnewworkcardentryid() {
        return fnewworkcardentryid;
    }

    public void setFnewworkcardentryid(int fnewworkcardentryid) {
        this.fnewworkcardentryid = fnewworkcardentryid;
    }

    public int getFnewworkcardentryfid() {
        return fnewworkcardentryfid;
    }

    public void setFnewworkcardentryfid(int fnewworkcardentryfid) {
        this.fnewworkcardentryfid = fnewworkcardentryfid;
    }

    public Integer getFinterid() {
        return finterid;
    }

    public void setFinterid(Integer finterid) {
        this.finterid = finterid;
    }

    public Integer getForganizeid() {
        return forganizeid;
    }

    public void setForganizeid(Integer forganizeid) {
        this.forganizeid = forganizeid;
    }

    public Integer getFbilltypeid() {
        return fbilltypeid;
    }

    public void setFbilltypeid(Integer fbilltypeid) {
        this.fbilltypeid = fbilltypeid;
    }

    public Integer getFbillstyle() {
        return fbillstyle;
    }

    public void setFbillstyle(Integer fbillstyle) {
        this.fbillstyle = fbillstyle;
    }

    public Integer getFpushtypeid() {
        return fpushtypeid;
    }

    public void setFpushtypeid(Integer fpushtypeid) {
        this.fpushtypeid = fpushtypeid;
    }

    public String getFbillnumber() {
        return fbillnumber;
    }

    public void setFbillnumber(String fbillnumber) {
        this.fbillnumber = fbillnumber == null ? null : fbillnumber.trim();
    }

    public String getOutputdate() {
        return outputdate;
    }

    public void setOutputdate(String outputdate) {
        this.outputdate = outputdate;
    }

    public String getFdeptnumber() {
        return fdeptnumber;
    }

    public void setFdeptnumber(String fdeptnumber) {
        this.fdeptnumber = fdeptnumber == null ? null : fdeptnumber.trim();
    }

    public String getFproductname() {
        return fproductname;
    }

    public void setFproductname(String fproductname) {
        this.fproductname = fproductname == null ? null : fproductname.trim();
    }

    public Integer getFworkcardinterid() {
        return fworkcardinterid;
    }

    public void setFworkcardinterid(Integer fworkcardinterid) {
        this.fworkcardinterid = fworkcardinterid;
    }

    public Integer getFcodecollectid() {
        return fcodecollectid;
    }

    public void setFcodecollectid(Integer fcodecollectid) {
        this.fcodecollectid = fcodecollectid;
    }

    public Double getFtotalqty() {
        return ftotalqty;
    }

    public void setFtotalqty(Double ftotalqty) {
        this.ftotalqty = ftotalqty;
    }

    public Double getFtotalamount() {
        return ftotalamount;
    }

    public void setFtotalamount(Double ftotalamount) {
        this.ftotalamount = ftotalamount;
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

    public String getFbillerid() {
        return fbillerid;
    }

    public void setFbillerid(String fbillerid) {
        this.fbillerid = fbillerid;
    }

    public String getFcheckerid() {
        return fcheckerid;
    }

    public void setFcheckerid(String fcheckerid) {
        this.fcheckerid = fcheckerid;
    }

    public String getOutputname() {
        return outputname;
    }

    public void setOutputname(String outputname) {
        this.outputname = outputname == null ? null : outputname.trim();
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getFpartid() {
        return fpartid;
    }

    public void setFpartid(Integer fpartid) {
        this.fpartid = fpartid;
    }

    public Integer getFproductid() {
        return fproductid;
    }

    public void setFproductid(Integer fproductid) {
        this.fproductid = fproductid;
    }

    public Integer getFprdmoid() {
        return fprdmoid;
    }

    public void setFprdmoid(Integer fprdmoid) {
        this.fprdmoid = fprdmoid;
    }

    public Integer getFmoid() {
        return fmoid;
    }

    public void setFmoid(Integer fmoid) {
        this.fmoid = fmoid;
    }

    public Integer getFempid() {
        return fempid;
    }

    public void setFempid(Integer fempid) {
        this.fempid = fempid;
    }

    public Integer getFoperplanningid() {
        return foperplanningid;
    }

    public void setFoperplanningid(Integer foperplanningid) {
        this.foperplanningid = foperplanningid;
    }

    public Integer getFrow() {
        return frow;
    }

    public void setFrow(Integer frow) {
        this.frow = frow;
    }

    public String getFmtono() {
        return fmtono;
    }

    public void setFmtono(String fmtono) {
        this.fmtono = fmtono == null ? null : fmtono.trim();
    }

    public String getProductionorders() {
        return productionorders;
    }

    public void setProductionorders(String productionorders) {
        this.productionorders = productionorders == null ? null : productionorders.trim();
    }

    public String getProcessplannumber() {
        return processplannumber;
    }

    public void setProcessplannumber(String processplannumber) {
        this.processplannumber = processplannumber == null ? null : processplannumber.trim();
    }

    public String getProcessoutputnumber() {
        return processoutputnumber;
    }

    public void setProcessoutputnumber(String processoutputnumber) {
        this.processoutputnumber = processoutputnumber == null ? null : processoutputnumber.trim();
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname == null ? null : partname.trim();
    }

    public String getProductnumber() {
        return productnumber;
    }

    public void setProductnumber(String productnumber) {
        this.productnumber = productnumber == null ? null : productnumber.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public Integer getFitemid() {
        return fitemid;
    }

    public void setFitemid(Integer fitemid) {
        this.fitemid = fitemid;
    }

    public String getMaterialcode() {
        return materialcode;
    }

    public void setMaterialcode(String materialcode) {
        this.materialcode = materialcode == null ? null : materialcode.trim();
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

    public String getFemp() {
        return femp;
    }

    public void setFemp(String femp) {
        this.femp = femp == null ? null : femp.trim();
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize == null ? null : fsize.trim();
    }

    public BigDecimal getFqty() {
        return fqty;
    }

    public void setFqty(BigDecimal fqty) {
        this.fqty = fqty;
    }

    public BigDecimal getFprice() {
        return fprice;
    }

    public void setFprice(BigDecimal fprice) {
        this.fprice = fprice;
    }

    public BigDecimal getFamount() {
        return famount;
    }

    public void setFamount(BigDecimal famount) {
        this.famount = famount;
    }

    public String getFnote() {
        return fnote;
    }

    public void setFnote(String fnote) {
        this.fnote = fnote == null ? null : fnote.trim();
    }

    public BigDecimal getFsourceqty() {
        return fsourceqty;
    }

    public void setFsourceqty(BigDecimal fsourceqty) {
        this.fsourceqty = fsourceqty;
    }

    public String getFcardno() {
        return fcardno;
    }

    public void setFcardno(String fcardno) {
        this.fcardno = fcardno == null ? null : fcardno.trim();
    }

    public Integer getFsourceinterid() {
        return fsourceinterid;
    }

    public void setFsourceinterid(Integer fsourceinterid) {
        this.fsourceinterid = fsourceinterid;
    }

    public Integer getFsourceentryid() {
        return fsourceentryid;
    }

    public void setFsourceentryid(Integer fsourceentryid) {
        this.fsourceentryid = fsourceentryid;
    }


    public String getProductionorder() {
        return productionorder;
    }

    public void setProductionorder(String productionorder) {
        this.productionorder = productionorder == null ? null : productionorder.trim();
    }


    public String getRoutname() {
        return routname;
    }

    public void setRoutname(String routname) {
        this.routname = routname == null ? null : routname.trim();
    }

    public Integer getFroutingid() {
        return froutingid;
    }

    public void setFroutingid(Integer froutingid) {
        this.froutingid = froutingid;
    }
}