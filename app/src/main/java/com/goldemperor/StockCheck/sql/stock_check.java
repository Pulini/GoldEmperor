package com.goldemperor.StockCheck.sql;


public class stock_check {

    private long id;


    private String number;

    private String proposer;

    private String supplier;

    private String applydate;

    private String info;

    private String image1;

    private String image2;

    private String auditor;

    private String status;

    private String exceptional;

    private String caseclose;


    public void setNumber(String number) {
        this.number = number;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public void setApplydate(String applydate) {
        this.applydate = applydate;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setExceptional(String exceptional) {
        this.exceptional = exceptional;
    }

    public void setCaseclose(String caseclose) {
        this.caseclose = caseclose;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public long getId() {
        return this.id;
    }


    public String getNumber() {
        return this.number;
    }

    public String getProposer() {
        return this.proposer;
    }

    public String getApplydate() {
        return this.applydate;
    }

    public String getInfo() {
        return this.info;
    }

    public String getImage1() {
        return this.image1;
    }

    public String getImage2() {
        return this.image2;
    }

    public String getAuditor() {
        return this.auditor;
    }

    public String getStatus() {
        return this.status;
    }

    public String getExceptional() {
        return this.exceptional;
    }

    public String getCaseclose() {
        return this.caseclose;
    }


    public stock_check() {
    }

}
