package com.goldemperor.StaffWorkStatistics;

/**
 * File Name : StaffWorkStatistics_GX_Model
 * Created by : PanZX on  2018/7/27 16:26
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class StaffWorkStatistics_GX_Model {
    //	"Flevel": 1,
    //	"FEmpID": 17698,
    //	"FEmpNumber": "001292",
    //	"FItemNo": "001292|吴利-金帝集团股份有限公司|针车11组(日期:2018-7-01~2018-07-27)",
    //	"FProcessNumber": "",
    //	"FProcessName": "",
    //	"FQty": 0.00,
    //	"FPrice": 0.00000,
    //	"FAmount": 0.0000,
    //	"FSmallBillSubsidyPCT": 0.00,
    //	"FSmallBillSubsidy": 0.00,
    //	"FAmountSum": 0.0000


    int Flevel;
    int FEmpID;
    String FEmpNumber;
    String FItemNo;
    String FProcessNumber;
    String FProcessName;
    double FQty;
    double FPrice;
    double FAmount;
    double FSmallBillSubsidyPCT;
    double FSmallBillSubsidy;
    double FAmountSum;

    public int getFlevel() {
        return Flevel;
    }

    public void setFlevel(int flevel) {
        Flevel = flevel;
    }

    public int getFEmpID() {
        return FEmpID;
    }

    public void setFEmpID(int FEmpID) {
        this.FEmpID = FEmpID;
    }

    public String getFEmpNumber() {
        return FEmpNumber;
    }

    public void setFEmpNumber(String FEmpNumber) {
        this.FEmpNumber = FEmpNumber;
    }

    public String getFItemNo() {
        return FItemNo;
    }

    public void setFItemNo(String FItemNo) {
        this.FItemNo = FItemNo;
    }

    public String getFProcessNumber() {
        return FProcessNumber;
    }

    public void setFProcessNumber(String FProcessNumber) {
        this.FProcessNumber = FProcessNumber;
    }

    public String getFProcessName() {
        return FProcessName;
    }

    public void setFProcessName(String FProcessName) {
        this.FProcessName = FProcessName;
    }

    public double getFQty() {
        return FQty;
    }

    public void setFQty(double FQty) {
        this.FQty = FQty;
    }

    public double getFPrice() {
        return FPrice;
    }

    public void setFPrice(double FPrice) {
        this.FPrice = FPrice;
    }

    public double getFAmount() {
        return FAmount;
    }

    public void setFAmount(double FAmount) {
        this.FAmount = FAmount;
    }

    public double getFSmallBillSubsidyPCT() {
        return FSmallBillSubsidyPCT;
    }

    public void setFSmallBillSubsidyPCT(double FSmallBillSubsidyPCT) {
        this.FSmallBillSubsidyPCT = FSmallBillSubsidyPCT;
    }

    public double getFSmallBillSubsidy() {
        return FSmallBillSubsidy;
    }

    public void setFSmallBillSubsidy(double FSmallBillSubsidy) {
        this.FSmallBillSubsidy = FSmallBillSubsidy;
    }

    public double getFAmountSum() {
        return FAmountSum;
    }

    public void setFAmountSum(double FAmountSum) {
        this.FAmountSum = FAmountSum;
    }
}
