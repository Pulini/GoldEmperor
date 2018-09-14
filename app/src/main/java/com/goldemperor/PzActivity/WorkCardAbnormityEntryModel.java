package com.goldemperor.PzActivity;

import java.math.BigDecimal;
import java.util.Date;

public class WorkCardAbnormityEntryModel {
    private Integer FInterID;

    public int FEntryID;

    public int FExceptionID;

    public int FExceptionLevel;

    public Integer getFInterID() {
        return FInterID;
    }

    public void setFInterID(Integer FInterID) {
        this.FInterID = FInterID;
    }

    public int getFEntryID() {
        return FEntryID;
    }

    public void setFEntryID(int FEntryID) {
        this.FEntryID = FEntryID;
    }

    public int getFExceptionID() {
        return FExceptionID;
    }

    public void setFExceptionID(int FExceptionID) {
        this.FExceptionID = FExceptionID;
    }

    public int getFExceptionLevel() {
        return FExceptionLevel;
    }

    public void setFExceptionLevel(int FExceptionLevel) {
        this.FExceptionLevel = FExceptionLevel;
    }

}