package com.goldemperor.PropertyRegistration;

/**
 * File Name : PropertyModel
 * Created by : PanZX on  2019/1/17 16:01
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class PropertyModel {
    //		"FInterID": 31914,
    //		"FNumber": "",
    //		"FName": "调音台",
    //		"FModel": "",
    //		"FbuyDate": "2019-01-18T00:00:00",
    //		"FWritedate": "2019-01-21T10:51:41.86",
    //		"FQty": 1.00,
    //		"FStatus": "已审核",
    //		"FLabelPrintQty": "未打印"
    //		"FSAPCgOrderNo": SAP采购凭证号


    int FInterID;
    int FLabelPrintQty;

    String CustodianName;
    String Faddress;
    String FNumber;
    String FName;
    String FbuyDate;
    String FWritedate;
    String FStatus;
    String FProcessStatus;
    String FSAPCgOrderNo;

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

    public String getFStatus() {
        return FStatus;
    }

    public void setFStatus(String FStatus) {
        this.FStatus = FStatus;
    }

    public int getFLabelPrintQty() {
        return FLabelPrintQty;
    }

    public void setFLabelPrintQty(int FLabelPrintQty) {
        this.FLabelPrintQty = FLabelPrintQty;
    }
}
