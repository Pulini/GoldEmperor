package com.goldemperor.PzActivity;

import java.util.Date;
import java.util.List;

public class WorkCardAbnormityModel {
    private int FInterID;

    private String FNumber;

    private String FDate;

    private int FEmpID;

    private int FDeptmentID;

    private int FExceptionID;

    private Long FQty;

    private String FProcessing;

    private String FOpinion;

    private String FAcceptedOpinion;

    private int FWorkCardInterID;

    private int FWorkCardEntryID;

    private List<WorkCardAbnormityEntryModel> Entry ;//异常分录信息

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
        this.FNumber = FNumber == null ? null : FNumber.trim();
    }

    public String getFDate() {
        return FDate;
    }

    public void setFDate(String FDate) {
        this.FDate = FDate;
    }

    public int getFEmpID() {
        return FEmpID;
    }

    public void setFEmpID(int FEmpID) {
        this.FEmpID = FEmpID;
    }

    public int getFDeptmentID() {
        return FDeptmentID;
    }

    public void setFDeptmentID(int FDeptmentID) {
        this.FDeptmentID = FDeptmentID;
    }

    public int getFExceptionID() {
        return FExceptionID;
    }

    public void setFExceptionID(int FExceptionID) {
        this.FExceptionID = FExceptionID;
    }

    public Long getFQty() {
        return FQty;
    }

    public void setFQty(Long FQty) {
        this.FQty = FQty;
    }

    public String getFProcessing() {
        return FProcessing;
    }

    public void setFProcessing(String FProcessing) {
        this.FProcessing = FProcessing == null ? null : FProcessing.trim();
    }

    public String getFOpinion() {
        return FOpinion;
    }

    public void setFOpinion(String FOpinion) {
        this.FOpinion = FOpinion == null ? null : FOpinion.trim();
    }

    public String getFAcceptedOpinion() {
        return FAcceptedOpinion;
    }

    public void setFAcceptedOpinion(String FAcceptedOpinion) {
        this.FAcceptedOpinion = FAcceptedOpinion == null ? null : FAcceptedOpinion.trim();
    }

    public int getFWorkCardInterID() {
        return FWorkCardInterID;
    }

    public void setFWorkCardInterID(int FWorkCardInterID) {
        this.FWorkCardInterID = FWorkCardInterID;
    }

    public int getFWorkCardEntryID() {
        return FWorkCardEntryID;
    }

    public void setFWorkCardEntryID(int FWorkCardEntryID) {
        this.FWorkCardEntryID = FWorkCardEntryID;
    }

    public List<WorkCardAbnormityEntryModel> getEntry() {
        return Entry;
    }

    public void setEntry(List<WorkCardAbnormityEntryModel> Entry) {
        this.Entry = Entry;
    }
}