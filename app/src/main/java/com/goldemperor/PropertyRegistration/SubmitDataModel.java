package com.goldemperor.PropertyRegistration;

import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.Utils.SPUtils;

/**
 * File Name : SubmitDataModel
 * Created by : PanZX on  2019/1/21 19:10
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class SubmitDataModel {


    int FInterID;
    int FKeepEmpID;
    int FDeptID;
    int FParticipator;
    int FRegistrationerID;
    int FLiableEmpID;

    double FPrice;
    double FOrgVal;

    String FNumber;
    String Faddress;
    String AssetPicture;
    String FNotes;

    public int getFInterID() {
        return FInterID;
    }

    public void setFInterID(int FInterID) {
        this.FInterID = FInterID;
    }

    public int getFKeepEmpID() {
        return FKeepEmpID;
    }

    public void setFKeepEmpID(int FKeepEmpID) {
        this.FKeepEmpID = FKeepEmpID;
    }

    public int getFDeptID() {
        return FDeptID;
    }

    public void setFDeptID(int FDeptID) {
        this.FDeptID = FDeptID;
    }

    public int getFParticipator() {
        return FParticipator;
    }

    public void setFParticipator(int FParticipator) {
        this.FParticipator = FParticipator;
    }

    public int getFRegistrationerID() {
        return FRegistrationerID;
    }

    public void setFRegistrationerID(int FRegistrationerID) {
        this.FRegistrationerID = FRegistrationerID;
    }

    public int getFLiableEmpID() {
        return FLiableEmpID;
    }

    public void setFLiableEmpID(int FLiableEmpID) {
        this.FLiableEmpID = FLiableEmpID;
    }

    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }

    public String getFaddress() {
        return Faddress;
    }

    public void setFaddress(String faddress) {
        Faddress = faddress;
    }

    public String getAssetPicture() {
        return AssetPicture;
    }

    public void setAssetPicture(String assetPicture) {
        AssetPicture = assetPicture;
    }

    public String getFNotes() {
        return FNotes;
    }

    public void setFNotes(String FNotes) {
        this.FNotes = FNotes;
    }

    public double getFPrice() {
        return FPrice;
    }

    public void setFPrice(double FPrice) {
        this.FPrice = FPrice;
    }

    public double getFOrgVal() {
        return FOrgVal;
    }

    public void setFOrgVal(double FOrgVal) {
        this.FOrgVal = FOrgVal;
    }
}
