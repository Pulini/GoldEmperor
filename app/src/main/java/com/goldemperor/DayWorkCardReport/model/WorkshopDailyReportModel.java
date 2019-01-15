package com.goldemperor.DayWorkCardReport.model;

/**
 * File Name : WorkshopDailyReportModel
 * Created by : PanZX on  2018/12/11 11:25
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class WorkshopDailyReportModel {
    //"FWorkShopName": "贴合车间",
    //		"FOrganizeName": "合计",
    //		"FToDayMustQty": 0,
    //		"FToDayQty": 0,
    //		"FNoToDayQty": 0,
    //		"FToDayFinishRate": "0.00%",
    //		"FLJQty": 0,
    //		"FNoDoingReason": ""
    String FWorkShopName;
    String FOrganizeName;
    String FToDayFinishRate;
    String FNoDoingReason;
    int FToDayMustQty;
    int FToDayQty;
    int FNoToDayQty;
    int FLJQty;

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

    public String getFToDayFinishRate() {
        return FToDayFinishRate;
    }

    public void setFToDayFinishRate(String FToDayFinishRate) {
        this.FToDayFinishRate = FToDayFinishRate;
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

    public int getFToDayQty() {
        return FToDayQty;
    }

    public void setFToDayQty(int FToDayQty) {
        this.FToDayQty = FToDayQty;
    }

    public int getFNoToDayQty() {
        return FNoToDayQty;
    }

    public void setFNoToDayQty(int FNoToDayQty) {
        this.FNoToDayQty = FNoToDayQty;
    }

    public int getFLJQty() {
        return FLJQty;
    }

    public void setFLJQty(int FLJQty) {
        this.FLJQty = FLJQty;
    }
}
