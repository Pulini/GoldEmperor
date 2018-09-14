package com.goldemperor.PgdActivity;

public class AttachFiles extends AttachFilesKey {
    private Integer forganizeid;

    private String fyear;

    private String fperiod;

    private Integer fclassid;

    private String fclassname;

    private Integer finterid;

    private String ffullname;

    private String ffullpath;

    private String ffilename;

    private Double ffilelength;

    private String ffileid;

    private String ffieldname;

    public Integer getForganizeid() {
        return forganizeid;
    }

    public void setForganizeid(Integer forganizeid) {
        this.forganizeid = forganizeid;
    }

    public String getFyear() {
        return fyear;
    }

    public void setFyear(String fyear) {
        this.fyear = fyear == null ? null : fyear.trim();
    }

    public String getFperiod() {
        return fperiod;
    }

    public void setFperiod(String fperiod) {
        this.fperiod = fperiod == null ? null : fperiod.trim();
    }

    public Integer getFclassid() {
        return fclassid;
    }

    public void setFclassid(Integer fclassid) {
        this.fclassid = fclassid;
    }

    public String getFclassname() {
        return fclassname;
    }

    public void setFclassname(String fclassname) {
        this.fclassname = fclassname == null ? null : fclassname.trim();
    }

    public Integer getFinterid() {
        return finterid;
    }

    public void setFinterid(Integer finterid) {
        this.finterid = finterid;
    }

    public String getFfullname() {
        return ffullname;
    }

    public void setFfullname(String ffullname) {
        this.ffullname = ffullname == null ? null : ffullname.trim();
    }

    public String getFfullpath() {
        return ffullpath;
    }

    public void setFfullpath(String ffullpath) {
        this.ffullpath = ffullpath == null ? null : ffullpath.trim();
    }

    public String getFfilename() {
        return ffilename;
    }

    public void setFfilename(String ffilename) {
        this.ffilename = ffilename == null ? null : ffilename.trim();
    }

    public Double getFfilelength() {
        return ffilelength;
    }

    public void setFfilelength(Double ffilelength) {
        this.ffilelength = ffilelength;
    }

    public String getFfileid() {
        return ffileid;
    }

    public void setFfileid(String ffileid) {
        this.ffileid = ffileid == null ? null : ffileid.trim();
    }

    public String getFfieldname() {
        return ffieldname;
    }

    public void setFfieldname(String ffieldname) {
        this.ffieldname = ffieldname == null ? null : ffieldname.trim();
    }
}