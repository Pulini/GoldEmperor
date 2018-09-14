package com.goldemperor.model;

/**
 * File Name : ProcessSendModel
 * Created by : PanZX on  2018/4/25 15:34
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：工序计划派工数据模型
 */
public class ProcessSendModel {
//                "FInterID":10655,
//                "FSingleDispatchedProcessQty":0,
//                "Organization":"金帝集团有限公司 (测试库)",
//                "MONumber":"J17200351",
//                "Department":"",
//                "FPlanStartDate":"",
//                "FPlanFinishDate":"",
//                "FBillNO":"GXJH1710655",
//                "FMtono":"J1720035",
//                "ProductNumber":"80020600001",
//                "ProductName":"0710003+0403003",
//                "PartName":"",
//                "ItemFNumber":"",
//                "ItemName":"",
//                "FProcessNumber":"DLFH",
//                "FProcessNumber1":"底料复合",
//                "FSize":"36",
//                "FProcessQty":13.34,
//                "FReqPoOrderQty":0,
//                "FDispatchedProcessQty":0,
//                "FFinishQty":0,
//                "FReMainDispatchedProcessQty":0,
//                "FReMainReportQty":0,
//                "FBillerName":"滕和锋",
//                "FBillDate":"2018-03-16",
//                "FCheckerName":"滕和锋",
//                "FCheckDate":"2018/3/16 16:46:19",
//                "FID":227706,
//                "fentryid":2


    int FInterID;
    int FSingleDispatchedProcessQty;//已直推派工数量
    String Organization;//生产组织
    String MONumber;//生产订单编号
    String Department;//生产车间
    String FPlanStartDate;//计划开工日期
    String FPlanFinishDate;//计划完工日期
    String FBillNO;//工序计划单号
    String FMtono;//计划跟踪号
    String ProductNumber;//产品编码
    String ProductName;//产品名称
    String PartName;//部件
    String ItemFNumber;//物料编码
    String ItemName;//物料名称
    String FProcessNumber;//工序编码
    String FProcessNumber1;//工序名称
    String FSize;//尺码
    double FProcessQty;//工序数量
    int FReqPoOrderQty;//已委外工序数量
    int FDispatchedProcessQty;//已派工数量
    int FFinishQty;//已汇报数量
    int FReMainDispatchedProcessQty;//未派未委外数量
    int FReMainReportQty;//未汇报数量
    String FBillerName;//制单人
    String FBillDate;//制单日期
    String FCheckerName;//审核人
    String FCheckDate;//审核日期
    int FID;
    int fentryid;

    public int getFInterID() {
        return FInterID;
    }

    public void setFInterID(int FInterID) {
        this.FInterID = FInterID;
    }

    public int getFSingleDispatchedProcessQty() {
        return FSingleDispatchedProcessQty;
    }

    public void setFSingleDispatchedProcessQty(int FSingleDispatchedProcessQty) {
        this.FSingleDispatchedProcessQty = FSingleDispatchedProcessQty;
    }

    public String getOrganization() {
        return Organization;
    }

    public void setOrganization(String organization) {
        Organization = organization;
    }

    public String getMONumber() {
        return MONumber;
    }

    public void setMONumber(String MONumber) {
        this.MONumber = MONumber;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getFPlanStartDate() {
        return FPlanStartDate;
    }

    public void setFPlanStartDate(String FPlanStartDate) {
        this.FPlanStartDate = FPlanStartDate;
    }

    public String getFPlanFinishDate() {
        return FPlanFinishDate;
    }

    public void setFPlanFinishDate(String FPlanFinishDate) {
        this.FPlanFinishDate = FPlanFinishDate;
    }

    public String getFBillNO() {
        return FBillNO;
    }

    public void setFBillNO(String FBillNO) {
        this.FBillNO = FBillNO;
    }

    public String getFMtono() {
        return FMtono;
    }

    public void setFMtono(String FMtono) {
        this.FMtono = FMtono;
    }

    public String getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(String productNumber) {
        ProductNumber = productNumber;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPartName() {
        return PartName;
    }

    public void setPartName(String partName) {
        PartName = partName;
    }

    public String getItemFNumber() {
        return ItemFNumber;
    }

    public void setItemFNumber(String itemFNumber) {
        ItemFNumber = itemFNumber;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getFProcessNumber() {
        return FProcessNumber;
    }

    public void setFProcessNumber(String FProcessNumber) {
        this.FProcessNumber = FProcessNumber;
    }

    public String getFProcessNumber1() {
        return FProcessNumber1;
    }

    public void setFProcessNumber1(String FProcessNumber1) {
        this.FProcessNumber1 = FProcessNumber1;
    }

    public String getFSize() {
        return FSize;
    }

    public void setFSize(String FSize) {
        this.FSize = FSize;
    }

    public double getFProcessQty() {
        return FProcessQty;
    }

    public void setFProcessQty(double FProcessQty) {
        this.FProcessQty = FProcessQty;
    }

    public int getFReqPoOrderQty() {
        return FReqPoOrderQty;
    }

    public void setFReqPoOrderQty(int FReqPoOrderQty) {
        this.FReqPoOrderQty = FReqPoOrderQty;
    }

    public int getFDispatchedProcessQty() {
        return FDispatchedProcessQty;
    }

    public void setFDispatchedProcessQty(int FDispatchedProcessQty) {
        this.FDispatchedProcessQty = FDispatchedProcessQty;
    }

    public int getFFinishQty() {
        return FFinishQty;
    }

    public void setFFinishQty(int FFinishQty) {
        this.FFinishQty = FFinishQty;
    }

    public int getFReMainDispatchedProcessQty() {
        return FReMainDispatchedProcessQty;
    }

    public void setFReMainDispatchedProcessQty(int FReMainDispatchedProcessQty) {
        this.FReMainDispatchedProcessQty = FReMainDispatchedProcessQty;
    }

    public int getFReMainReportQty() {
        return FReMainReportQty;
    }

    public void setFReMainReportQty(int FReMainReportQty) {
        this.FReMainReportQty = FReMainReportQty;
    }

    public String getFBillerName() {
        return FBillerName;
    }

    public void setFBillerName(String FBillerName) {
        this.FBillerName = FBillerName;
    }

    public String getFBillDate() {
        return FBillDate;
    }

    public void setFBillDate(String FBillDate) {
        this.FBillDate = FBillDate;
    }

    public String getFCheckerName() {
        return FCheckerName;
    }

    public void setFCheckerName(String FCheckerName) {
        this.FCheckerName = FCheckerName;
    }

    public String getFCheckDate() {
        return FCheckDate;
    }

    public void setFCheckDate(String FCheckDate) {
        this.FCheckDate = FCheckDate;
    }

    public int getFID() {
        return FID;
    }

    public void setFID(int FID) {
        this.FID = FID;
    }

    public int getFentryid() {
        return fentryid;
    }

    public void setFentryid(int fentryid) {
        this.fentryid = fentryid;
    }
}
