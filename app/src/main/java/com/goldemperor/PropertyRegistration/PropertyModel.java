package com.goldemperor.PropertyRegistration;

/**
 * File Name : PropertyModel
 * Created by : PanZX on  2019/1/17 16:01
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class PropertyModel {

    //            "FInterID": 31931,
    //            "FLabelPrintQty": 0,

    //            "CustodianName": "潘卓旭",
    //            "Faddress": "商品管理部",
    //	            "FNumber": "A@123456",
    //            "FName": "办公桌（经理）",
    //            "FbuyDate": "2018-10-30 00:00:00",
    //            "FWritedate": "2019-01-23 14:08:57",
    //            "FProcessStatus": "审核中",
    //            "FSAPCgOrderNo": null,
    //            "FVisitedNum": 0


    int FInterID;
    int FLabelPrintQty;


    int FVisitedNum;

    String CustodianName;
    String Faddress;
    String FNumber;
    String FName;
    String FbuyDate;
    String FWritedate;
    String FProcessStatus;
    String FSAPCgOrderNo;

    public int getFVisitedNum() {
        return FVisitedNum;
    }

    public void setFVisitedNum(int FVisitedNum) {
        this.FVisitedNum = FVisitedNum;
    }

    public String getCustodianName() {
        return CustodianName;
    }

    public void setCustodianName(String custodianName) {
        CustodianName = custodianName;
    }

    public String getFaddress() {
        return Faddress;
    }

    public void setFaddress(String faddress) {
        Faddress = faddress;
    }

    public String getFSAPCgOrderNo() {
        return FSAPCgOrderNo;
    }

    public void setFSAPCgOrderNo(String FSAPCgOrderNo) {
        this.FSAPCgOrderNo = FSAPCgOrderNo;
    }

    public String getFProcessStatus() {
        return FProcessStatus;
    }

    public void setFProcessStatus(String FProcessStatus) {
        this.FProcessStatus = FProcessStatus;
    }

    public int getFInterID() {
        return FInterID;
    }

    public void setFInterID(int FInterID) {
        this.FInterID = FInterID;
    }


    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }


    public String getFbuyDate() {
        return FbuyDate;
    }

    public void setFbuyDate(String fbuyDate) {
        FbuyDate = fbuyDate;
    }

    public String getFWritedate() {
        return FWritedate;
    }

    public void setFWritedate(String FWritedate) {
        this.FWritedate = FWritedate;
    }


    public int getFLabelPrintQty() {
        return FLabelPrintQty;
    }

    public void setFLabelPrintQty(int FLabelPrintQty) {
        this.FLabelPrintQty = FLabelPrintQty;
    }
}
