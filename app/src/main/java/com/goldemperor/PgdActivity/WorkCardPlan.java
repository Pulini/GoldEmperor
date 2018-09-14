package com.goldemperor.PgdActivity;

import java.util.ArrayList;
import java.util.List;

public class WorkCardPlan {
    private Long finterid;

    private String forganizename;

    private String state;

    private String billtype;

    private String planbill;

    private String orderbill;

    private String orderdate;

    private String productionbill;

    private String fgroup;

    private int fdeptid;

    private String batch;

    private String remark;

    private String planstarttime;

    private String planendtime;

    private String plantbody;

    private String codeformat;

    private String materialcode;

    private String materialname;

    private String specifications;

    private String color;

    private String unit;

    private String guestsnumber;

    private String size;

    private double dispatchingnumber;

    private double alreadynumber;

    private double nonumber;

    private int alreadynumberCount;

    private int nonumberCount;

    private String lastcodetime;

    private String attributionprocess;

    private String fbiller;

    private String fchecker;

    private String fbilldate;

    private String fcheckdate;

    private String isprint;

    private String iscardprint;

    private String colser;

    private String closedate;

    private String truestarttime;

    private String trueendtime;

    private String iccard;

    private String cardtime;

    private String planbillnumber;

    private String factorydelivery;

    private String customerordernumber;

    private String productnumber;

    private Integer fsrcicmointerid;

    private Integer fproductid;

    private Integer fpmfmtid;

    private Integer fitemid;

    private Integer fentryid;

    private String fsize;

    private String planStatus;

    private String froutingid;
    private List<String[][]> sizeList;


    private Integer fsourceinterid;

    private Integer fsourceentryid;

    private double fconfirmqty;//生产订单总数

    private double  reportednumber;//已汇报数量

    private double  notreportnumber;//未汇报数量

    private double  cumulativenumber;//累计计工数

    private double  reportednotnumber;//已汇报未计工数

    private double  reportednotInnumber;//已汇报未入库数

    private Integer fprocessflowid;

    private String fprocessingmethod;

    private String fmapnumber;

    private boolean fcanreportbynostockin;

    private String routebillname;

    private String routebillnumber;

    public String getRoutebillnumber() {
        return routebillnumber.toUpperCase();
    }

    public void setRoutebillnumber(String routebillnumber) {
        this.routebillnumber = routebillnumber.toUpperCase();
    }

    public String getRoutebillname() {
        return routebillname.toUpperCase();
    }

    public void setRoutebillname(String routebillname) {
        this.routebillname = routebillname.toUpperCase();
    }

    public boolean getFcanreportbynostockin() {
        return fcanreportbynostockin;
    }

    public void setFcanreportbynostockin(boolean fcanreportbynostockin) {
        this.fcanreportbynostockin = fcanreportbynostockin;
    }

    public String getFmapnumber() {
        return fmapnumber;
    }

    public void setFmapnumber(String fmapnumber) {
        this.fmapnumber = fmapnumber;
    }

    public Integer getFdeptid() {
        return fdeptid;
    }

    public void setFdeptid(Integer fdeptid) {
        this.fdeptid = fdeptid;
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

    public double getFfinishqty() {
        return reportednumber;
    }

    public void setReportednumber(double reportednumber) {
        this.reportednumber = reportednumber;
    }

    public double getNotreportnumber() {
        return notreportnumber;
    }

    public void setNotreportnumber(double notreportnumber) {
        this.notreportnumber = notreportnumber;
    }

    public double getCumulativenumber() {
        return cumulativenumber;
    }

    public void setCumulativenumber(double cumulativenumber) {
        this.cumulativenumber = cumulativenumber;
    }

    public double getReportednotnumber() {
        return reportednotnumber;
    }

    public void setReportednotnumber(double reportednotnumber) {
        this.reportednotnumber = reportednotnumber;
    }

    public double getReportednotInnumber() {
        return reportednotInnumber;
    }

    public void setReportednotInnumber(double reportednotInnumber) {
        this.reportednotInnumber = reportednotInnumber;
    }


    public List<String[][]> getSizeList() {
        return sizeList;
    }

    public WorkCardPlan(){
        sizeList=new ArrayList<String[][]>();
        alreadynumberCount=0;
        nonumberCount=0;
        planStatus="未排";
    }
    public void addSize(String[][] s) {
        this.sizeList.add(s);
    }

    public long getFinterid() {
        return finterid;
    }


    public void setFinterid(Long finterid) {
        this.finterid = finterid;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getFroutingid() {
        return froutingid;
    }

    public void setFroutingid(String froutingid) {
        this.froutingid = froutingid;
    }

    public String getForganizename() {
        return forganizename;
    }

    public void setForganizename(String forganizename) {
        this.forganizename = forganizename == null ? null : forganizename.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getBilltype() {
        return billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim().toUpperCase();
    }

    public String getPlanbill() {
        return planbill.toUpperCase();
    }

    public void setPlanbill(String planbill) {
        this.planbill = planbill == null ? null : planbill.trim().toUpperCase();
    }

    public String getOrderbill() {
        return orderbill.toUpperCase();
    }

    public void setOrderbill(String orderbill) {
        this.orderbill = orderbill == null ? null : orderbill.trim().toUpperCase();
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getProductionbill() {
        return productionbill;
    }

    public void setProductionbill(String productionbill) {
        this.productionbill = productionbill == null ? null : productionbill.trim();
    }

    public String getFgroup() {
        return fgroup;
    }

    public void setFgroup(String fgroup) {
        this.fgroup = fgroup == null ? null : fgroup.trim();
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPlanstarttime() {
        return planstarttime;
    }

    public void setPlanstarttime(String planstarttime) {
        this.planstarttime = planstarttime;
    }

    public String getPlanendtime() {
        return planendtime;
    }

    public void setPlanendtime(String planendtime) {
        this.planendtime = planendtime;
    }

    public String getPlantbody() {
        return plantbody.toUpperCase();
    }

    public void setPlantbody(String plantbody) {
        this.plantbody = plantbody == null ? null : plantbody.trim();
    }

    public String getCodeformat() {
        return codeformat.toUpperCase();
    }

    public void setCodeformat(String codeformat) {
        this.codeformat = codeformat == null ? null : codeformat.trim();
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

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications == null ? null : specifications.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getGuestsnumber() {
        return guestsnumber;
    }

    public void setGuestsnumber(String guestsnumber) {
        this.guestsnumber = guestsnumber == null ? null : guestsnumber.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public double getDispatchingnumber() {
        return dispatchingnumber;
    }

    public void setDispatchingnumber(double dispatchingnumber) {
        this.dispatchingnumber = dispatchingnumber;
    }

    public double getAlreadynumber() {
        return alreadynumber;
    }

    public void setAlreadynumber(double alreadynumber) {
        this.alreadynumber = alreadynumber;
    }

    public double getNonumber() {
        return nonumber;
    }

    public void setNonumber(double nonumber) {
        this.nonumber = nonumber;
    }

    public int getAlreadynumberCount() {
        return alreadynumberCount;
    }

    public void setAlreadynumberCount(int alreadynumberCount) {
        this.alreadynumberCount = alreadynumberCount;
    }

    public int getNonumberCount() {
        return nonumberCount;
    }

    public void setNonumberCount(int nonumberCount) {
        this.nonumberCount = nonumberCount;
    }


    public String getLastcodetime() {
        return lastcodetime;
    }

    public void setLastcodetime(String lastcodetime) {
        this.lastcodetime = lastcodetime;
    }

    public String getAttributionprocess() {
        return attributionprocess;
    }

    public void setAttributionprocess(String attributionprocess) {
        this.attributionprocess = attributionprocess == null ? null : attributionprocess.trim();
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

    public String getFbilldate() {
        return fbilldate;
    }

    public void setFbilldate(String fbilldate) {
        this.fbilldate = fbilldate;
    }

    public String getFcheckdate() {
        return fcheckdate;
    }

    public void setFcheckdate(String fcheckdate) {
        this.fcheckdate = fcheckdate;
    }

    public String getIsprint() {
        return isprint;
    }

    public void setIsprint(String isprint) {
        this.isprint = isprint == null ? null : isprint.trim();
    }

    public String getIscardprint() {
        return iscardprint;
    }

    public void setIscardprint(String iscardprint) {
        this.iscardprint = iscardprint == null ? null : iscardprint.trim();
    }

    public String getColser() {
        return colser;
    }

    public void setColser(String colser) {
        this.colser = colser == null ? null : colser.trim();
    }

    public String getClosedate() {
        return closedate;
    }

    public void setClosedate(String closedate) {
        this.closedate = closedate;
    }

    public String getTruestarttime() {
        return truestarttime;
    }

    public void setTruestarttime(String truestarttime) {
        this.truestarttime = truestarttime;
    }

    public String getTrueendtime() {
        return trueendtime;
    }

    public void setTrueendtime(String trueendtime) {
        this.trueendtime = trueendtime;
    }

    public String getIccard() {
        return iccard;
    }

    public void setIccard(String iccard) {
        this.iccard = iccard == null ? null : iccard.trim();
    }

    public String getCardtime() {
        return cardtime;
    }

    public void setCardtime(String cardtime) {
        this.cardtime = cardtime;
    }

    public String getPlanbillnumber() {
        return planbillnumber;
    }

    public void setPlanbillnumber(String planbillnumber) {
        this.planbillnumber = planbillnumber == null ? null : planbillnumber.trim();
    }

    public String getFactorydelivery() {
        return factorydelivery;
    }

    public void setFactorydelivery(String factorydelivery) {
        this.factorydelivery = factorydelivery;
    }

    public String getCustomerordernumber() {
        return customerordernumber;
    }

    public void setCustomerordernumber(String customerordernumber) {
        this.customerordernumber = customerordernumber == null ? null : customerordernumber.trim();
    }

    public String getProductnumber() {
        return productnumber;
    }

    public void setProductnumber(String productnumber) {
        this.productnumber = productnumber == null ? null : productnumber.trim();
    }

    public Integer getFsrcicmointerid() {
        return fsrcicmointerid;
    }

    public void setFsrcicmointerid(Integer fsrcicmointerid) {
        this.fsrcicmointerid = fsrcicmointerid;
    }

    public Integer getFproductid() {
        return fproductid;
    }

    public void setFproductid(Integer fproductid) {
        this.fproductid = fproductid;
    }

    public Integer getFpmfmtid() {
        return fpmfmtid;
    }

    public void setFpmfmtid(Integer fpmfmtid) {
        this.fpmfmtid = fpmfmtid;
    }

    public Integer getFitemid() {
        return fitemid;
    }

    public void setFitemid(Integer fitemid) {
        this.fitemid = fitemid;
    }

    public Integer getFentryid() {
        return fentryid;
    }

    public void setFentryid(Integer fentryid) {
        this.fentryid = fentryid;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize == null ? null : fsize.trim();
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

    public double getFconfirmqty() {
        return fconfirmqty;
    }

    public void setFconfirmqty(double fconfirmqty) {
        this.fconfirmqty = fconfirmqty;
    }
}