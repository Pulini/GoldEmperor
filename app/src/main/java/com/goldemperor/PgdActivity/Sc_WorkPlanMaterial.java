package com.goldemperor.PgdActivity;


public class Sc_WorkPlanMaterial {
    //"FEntryID": 4,
    //	"FInterID": 52148903,
    //	"planbill": "J1851188",
    //	"Itemcode": "120100002",
    //	"ItemName": "(8610)热熔胶",
    //	"FModel": "",
    //	"FColor": "",
    //	"ItemUint": "千克",
    //	"Fsize": "",
    //	"FUseRate": 0,
    //	"molecular": 1.0000000000,
    //	"FDenominator": 0.0020000000,
    //	"FNeedQty": 6.0000000000,
    //	"FMustQty": 6.0000000000,
    //	"FPickedQty": 162.0000000000,
    //	"FNoPickedQty": 6.0000000000,
    //	"FRePickedQty": 0.0000000000

    int FEntryID;//分号
    long FInterID;//主表ID
    String planbill;//计划跟踪号
    String Itemcode;//子项物料编码
    String ItemName;//子项物料名称
    String FModel;//规格型号
    String FColor;//颜色
    String ItemUint;//子项单位
    String Fsize;//尺码
    int FUseRate;//[使用比例(%)]
    double molecular;//分子
    double FDenominator;//分母
    double FNeedQty;//需求数量
    double FMustQty;//应发数量
    double FPickedQty;//已领数量
    double FNoPickedQty;//未领数量
    double FRePickedQty;//补领数量


    public int getFEntryID() {
        return FEntryID;
    }

    public void setFEntryID(int FEntryID) {
        this.FEntryID = FEntryID;
    }

    public long getFInterID() {
        return FInterID;
    }

    public void setFInterID(long FInterID) {
        this.FInterID = FInterID;
    }

    public String getPlanbill() {
        return planbill;
    }

    public void setPlanbill(String planbill) {
        this.planbill = planbill;
    }

    public String getItemcode() {
        return Itemcode;
    }

    public void setItemcode(String itemcode) {
        Itemcode = itemcode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getFModel() {
        return FModel;
    }

    public void setFModel(String FModel) {
        this.FModel = FModel;
    }

    public String getFColor() {
        return FColor;
    }

    public void setFColor(String FColor) {
        this.FColor = FColor;
    }

    public String getItemUint() {
        return ItemUint;
    }

    public void setItemUint(String itemUint) {
        ItemUint = itemUint;
    }

    public String getFsize() {
        return Fsize;
    }

    public void setFsize(String fsize) {
        Fsize = fsize;
    }

    public int getFUseRate() {
        return FUseRate;
    }

    public void setFUseRate(int FUseRate) {
        this.FUseRate = FUseRate;
    }

    public double getMolecular() {
        return molecular;
    }

    public void setMolecular(double molecular) {
        this.molecular = molecular;
    }

    public double getFDenominator() {
        return FDenominator;
    }

    public void setFDenominator(double FDenominator) {
        this.FDenominator = FDenominator;
    }

    public double getFNeedQty() {
        return FNeedQty;
    }

    public void setFNeedQty(double FNeedQty) {
        this.FNeedQty = FNeedQty;
    }

    public double getFMustQty() {
        return FMustQty;
    }

    public void setFMustQty(double FMustQty) {
        this.FMustQty = FMustQty;
    }

    public double getFPickedQty() {
        return FPickedQty;
    }

    public void setFPickedQty(double FPickedQty) {
        this.FPickedQty = FPickedQty;
    }

    public double getFNoPickedQty() {
        return FNoPickedQty;
    }

    public void setFNoPickedQty(double FNoPickedQty) {
        this.FNoPickedQty = FNoPickedQty;
    }

    public double getFRePickedQty() {
        return FRePickedQty;
    }

    public void setFRePickedQty(double FRePickedQty) {
        this.FRePickedQty = FRePickedQty;
    }
}