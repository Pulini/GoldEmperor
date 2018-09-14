package com.goldemperor.PzActivity;

/**
 * File Name : RouteEntryModel
 * Created by : PanZX on  2018/8/30 11:18
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class RouteEntryModel {
    //"FEntryId": 1,
    //            "FInterId": 35986,
    //            "FPartsID": 0,
    //            "partName": "",
    //            "FItemID": 0,
    //            "materialcode": "",
    //            "materialName": "",
    //            "FProcessNumber": "CX",
    //            "FProcessName": "成型",
    //            "FPrice": 0.0000000000,
    //            "FNote": ""

    int FEntryId;
    int FInterId;
    int FPartsID;
    String partName;
    int FItemID;
    String materialcode;
    String materialName;
    String FProcessNumber;
    String FProcessName;
    Double FPrice;
    String FNote;

    public int getFEntryId() {
        return FEntryId;
    }

    public void setFEntryId(int FEntryId) {
        this.FEntryId = FEntryId;
    }

    public int getFInterId() {
        return FInterId;
    }

    public void setFInterId(int FInterId) {
        this.FInterId = FInterId;
    }

    public int getFPartsID() {
        return FPartsID;
    }

    public void setFPartsID(int FPartsID) {
        this.FPartsID = FPartsID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getFItemID() {
        return FItemID;
    }

    public void setFItemID(int FItemID) {
        this.FItemID = FItemID;
    }

    public String getMaterialcode() {
        return materialcode;
    }

    public void setMaterialcode(String materialcode) {
        this.materialcode = materialcode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public Double getFPrice() {
        return FPrice;
    }

    public void setFPrice(Double FPrice) {
        this.FPrice = FPrice;
    }

    public String getFNote() {
        return FNote;
    }

    public void setFNote(String FNote) {
        this.FNote = FNote;
    }
}
