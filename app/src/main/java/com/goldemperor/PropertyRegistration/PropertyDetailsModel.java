package com.goldemperor.PropertyRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : PropertyDetailsModel
 * Created by : PanZX on  2019/1/18 14:00
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class PropertyDetailsModel {
    int FInterID;
    int FStyleID;
    int FRegistrationerID;
    int FKeepEmpID;
    int FLiableEmpID;
    int FOrganizeID;
    int FDeptID;
    int FParticipator;


    double FQty;
    double FPrice;
    double FOrgVal;

    String FNumber;
    String FSno;
    String FName;
    String FTypeName;
    String FModel;
    String FUnit;
    String FbuyDate;
    String FProduceDate;
    String FRevicedate;
    String FWritedate;
    String CustodianCode;
    String CustodianName;
    String FLiableEmpCode;
    String FLiableEmpName;
    String FParticipatorCode;
    String FParticipatorName;
    String FOrganizeName;
    String FDeptName;
    String Faddress;
    String FNotes;
    String FStatus;
    String FVender;
    String FLabelPrintQty;
    String AssetPicture;
    String FProcessStatus;
    String FSAPCgOrderNo;
    String FSAPINVOICENO;

    public String getFSAPINVOICENO() {
        return FSAPINVOICENO;
    }

    public void setFSAPINVOICENO(String FSAPINVOICENO) {
        this.FSAPINVOICENO = FSAPINVOICENO;
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

    public String getAssetPicture() {
        return AssetPicture;
    }

    public void setAssetPicture(String assetPicture) {
        AssetPicture = assetPicture;
    }

    List<list> CardEntry = new ArrayList<>();

    public int getFInterID() {
        return FInterID;
    }

    public void setFInterID(int FInterID) {
        this.FInterID = FInterID;
    }

    public int getFStyleID() {
        return FStyleID;
    }

    public void setFStyleID(int FStyleID) {
        this.FStyleID = FStyleID;
    }

    public int getFRegistrationerID() {
        return FRegistrationerID;
    }

    public void setFRegistrationerID(int FRegistrationerID) {
        this.FRegistrationerID = FRegistrationerID;
    }

    public int getFKeepEmpID() {
        return FKeepEmpID;
    }

    public void setFKeepEmpID(int FKeepEmpID) {
        this.FKeepEmpID = FKeepEmpID;
    }

    public int getFLiableEmpID() {
        return FLiableEmpID;
    }

    public void setFLiableEmpID(int FLiableEmpID) {
        this.FLiableEmpID = FLiableEmpID;
    }

    public int getFOrganizeID() {
        return FOrganizeID;
    }

    public void setFOrganizeID(int FOrganizeID) {
        this.FOrganizeID = FOrganizeID;
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

    public double getFOrgVal() {
        return FOrgVal;
    }

    public void setFOrgVal(double FOrgVal) {
        this.FOrgVal = FOrgVal;
    }

    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }

    public String getFSno() {
        return FSno;
    }

    public void setFSno(String FSno) {
        this.FSno = FSno;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFTypeName() {
        return FTypeName;
    }

    public void setFTypeName(String FTypeName) {
        this.FTypeName = FTypeName;
    }

    public String getFModel() {
        return FModel;
    }

    public void setFModel(String FModel) {
        this.FModel = FModel;
    }

    public String getFUnit() {
        return FUnit;
    }

    public void setFUnit(String FUnit) {
        this.FUnit = FUnit;
    }

    public String getFbuyDate() {
        return FbuyDate;
    }

    public void setFbuyDate(String fbuyDate) {
        FbuyDate = fbuyDate;
    }

    public String getFProduceDate() {
        return FProduceDate;
    }

    public void setFProduceDate(String FProduceDate) {
        this.FProduceDate = FProduceDate;
    }

    public String getFRevicedate() {
        return FRevicedate;
    }

    public void setFRevicedate(String FRevicedate) {
        this.FRevicedate = FRevicedate;
    }

    public String getFWritedate() {
        return FWritedate;
    }

    public void setFWritedate(String FWritedate) {
        this.FWritedate = FWritedate;
    }

    public String getCustodianCode() {
        return CustodianCode;
    }

    public void setCustodianCode(String custodianCode) {
        CustodianCode = custodianCode;
    }

    public String getCustodianName() {
        return CustodianName;
    }

    public void setCustodianName(String custodianName) {
        CustodianName = custodianName;
    }

    public String getFLiableEmpCode() {
        return FLiableEmpCode;
    }

    public void setFLiableEmpCode(String FLiableEmpCode) {
        this.FLiableEmpCode = FLiableEmpCode;
    }

    public String getFLiableEmpName() {
        return FLiableEmpName;
    }

    public void setFLiableEmpName(String FLiableEmpName) {
        this.FLiableEmpName = FLiableEmpName;
    }

    public String getFParticipatorCode() {
        return FParticipatorCode;
    }

    public void setFParticipatorCode(String FParticipatorCode) {
        this.FParticipatorCode = FParticipatorCode;
    }

    public String getFParticipatorName() {
        return FParticipatorName;
    }

    public void setFParticipatorName(String FParticipatorName) {
        this.FParticipatorName = FParticipatorName;
    }

    public String getFOrganizeName() {
        return FOrganizeName;
    }

    public void setFOrganizeName(String FOrganizeName) {
        this.FOrganizeName = FOrganizeName;
    }

    public String getFDeptName() {
        return FDeptName;
    }

    public void setFDeptName(String FDeptName) {
        this.FDeptName = FDeptName;
    }

    public String getFaddress() {
        return Faddress;
    }

    public void setFaddress(String faddress) {
        Faddress = faddress;
    }

    public String getFNotes() {
        return FNotes;
    }

    public void setFNotes(String FNotes) {
        this.FNotes = FNotes;
    }

    public String getFStatus() {
        return FStatus;
    }

    public void setFStatus(String FStatus) {
        this.FStatus = FStatus;
    }

    public String getFVender() {
        return FVender;
    }

    public void setFVender(String FVender) {
        this.FVender = FVender;
    }

    public String getFLabelPrintQty() {
        return FLabelPrintQty;
    }

    public void setFLabelPrintQty(String FLabelPrintQty) {
        this.FLabelPrintQty = FLabelPrintQty;
    }

    public List<list> getCardEntry() {
        return CardEntry;
    }

    public void setCardEntry(List<list> cardEntry) {
        CardEntry = cardEntry;
    }

    public static class list {
        double FQty;
        double FPrice;
        double FAmount;
        String FName;
        String FPlace;
        String FNotes;

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

        public String getFName() {
            return FName;
        }

        public void setFName(String FName) {
            this.FName = FName;
        }

        public String getFPlace() {
            return FPlace;
        }

        public void setFPlace(String FPlace) {
            this.FPlace = FPlace;
        }

        public String getFNotes() {
            return FNotes;
        }

        public void setFNotes(String FNotes) {
            this.FNotes = FNotes;
        }
    }
}
