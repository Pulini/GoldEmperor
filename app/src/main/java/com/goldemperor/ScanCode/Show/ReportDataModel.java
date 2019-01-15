package com.goldemperor.ScanCode.Show;

/**
 * File Name : ReportDataModel
 * Created by : PanZX on  2018/6/5 22:10
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ReportDataModel {
    //	"FBillNo": "指令合计",
    //		"FSize": ""
    //		"FSizeQianSumQty": -141.00,
    //		"FQty": 141,
    //		"FUnit": "",
    //		"FAllScanSumQty": 141.00,
    //		"FMoSumQty": 141.00,
    //		"FICItemName": "",
    //		"FWorkCardNo": "",
    String FBillNo;
    String FSize;
    String FSizeQianSumQty;//欠数
    String FQty;
    String FUnit;
    String FAllScanSumQty;//已汇报数
    String FMoSumQty;//指令数
    String FICItemName;
    String FWorkCardNo;

    public String getFBillNo() {
        return FBillNo;
    }

    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }

    public String getFSize() {
        return FSize;
    }

    public void setFSize(String FSize) {
        this.FSize = FSize;
    }

    public String getFSizeQianSumQty() {
        return FSizeQianSumQty;
    }

    public void setFSizeQianSumQty(String FSizeQianSumQty) {
        this.FSizeQianSumQty = FSizeQianSumQty;
    }

    public String getFQty() {
        return FQty;
    }

    public void setFQty(String FQty) {
        this.FQty = FQty;
    }

    public String getFUnit() {
        return FUnit;
    }

    public void setFUnit(String FUnit) {
        this.FUnit = FUnit;
    }

    public String getFAllScanSumQty() {
        return FAllScanSumQty;
    }

    public void setFAllScanSumQty(String FAllScanSumQty) {
        this.FAllScanSumQty = FAllScanSumQty;
    }

    public String getFMoSumQty() {
        return FMoSumQty;
    }

    public void setFMoSumQty(String FMoSumQty) {
        this.FMoSumQty = FMoSumQty;
    }

    public String getFICItemName() {
        return FICItemName;
    }

    public void setFICItemName(String FICItemName) {
        this.FICItemName = FICItemName;
    }

    public String getFWorkCardNo() {
        return FWorkCardNo;
    }

    public void setFWorkCardNo(String FWorkCardNo) {
        this.FWorkCardNo = FWorkCardNo;
    }
}
