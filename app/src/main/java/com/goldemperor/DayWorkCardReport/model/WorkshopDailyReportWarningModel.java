package com.goldemperor.DayWorkCardReport.model;

/**
 * File Name : WorkshopDailyReportModel
 * Created by : PanZX on  2018/12/11 11:25
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class WorkshopDailyReportWarningModel {

    //	"FWorkShopName": "裁断车间",
    //		"FOrganizeName": "金帝",
    //		"FParentDeptID": "金帝_裁断1课",
    //		"FDeptID": "裁断1组",
    //		"FToDayMustQty": 0,
    //		"FTimeMustQty": 0,
    //		"FTimeQty": 0,
    //		"FNoTimeQty": 0,
    //		"FTimeFinishRate": "0.00%",
    //		"FNoDoingReason": ""

    String FWorkShopName;
    String FOrganizeName;
    String FParentDeptID;
    String FDeptID;
    String FTimeFinishRate;
    String FNoDoingReason;

    int FToDayMustQty;
    int FTimeMustQty;
    int FTimeQty;
    int FNoTimeQty;

    public String getFWorkShopName() {
        return FWorkShopName;
    }

    public void setFWorkShopName(String FWorkShopName) {
        this.FWorkShopName = FWorkShopName;
    }

    public String getFOrganizeName() {
        return FOrganizeName;
    }

    public void setFOrganizeName(String FOrganizeName) {
        this.FOrganizeName = FOrganizeName;
    }

    public String getFParentDeptID() {
        return FParentDeptID;
    }

    public void setFParentDeptID(String FParentDeptID) {
        this.FParentDeptID = FParentDeptID;
    }

    public String getFDeptID() {
        return FDeptID;
    }

    public void setFDeptID(String FDeptID) {
        this.FDeptID = FDeptID;
    }

    public String getFTimeFinishRate() {
        return FTimeFinishRate;
    }

    public void setFTimeFinishRate(String FTimeFinishRate) {
        this.FTimeFinishRate = FTimeFinishRate;
    }

    public String getFNoDoingReason() {
        return FNoDoingReason;
    }

    public void setFNoDoingReason(String FNoDoingReason) {
        this.FNoDoingReason = FNoDoingReason;
    }

    public int getFToDayMustQty() {
        return FToDayMustQty;
    }

    public void setFToDayMustQty(int FToDayMustQty) {
        this.FToDayMustQty = FToDayMustQty;
    }

    public int getFTimeMustQty() {
        return FTimeMustQty;
    }

    public void setFTimeMustQty(int FTimeMustQty) {
        this.FTimeMustQty = FTimeMustQty;
    }

    public int getFTimeQty() {
        return FTimeQty;
    }

    public void setFTimeQty(int FTimeQty) {
        this.FTimeQty = FTimeQty;
    }

    public int getFNoTimeQty() {
        return FNoTimeQty;
    }

    public void setFNoTimeQty(int FNoTimeQty) {
        this.FNoTimeQty = FNoTimeQty;
    }
}
