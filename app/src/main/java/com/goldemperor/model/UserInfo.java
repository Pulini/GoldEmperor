package com.goldemperor.model;

/**
 * Created by xufanglou on 2016-08-13.
 */
public class UserInfo {
    public String ROWID;//行ID

    public void setROWID(String rowID) {
        this.ROWID = rowID;
    }

    public String getROWID() {
        return this.ROWID;
    }

    public String AccountSuitID;//账套ID

    public void setAccountSuitID(String accountSuitID) {
        this.AccountSuitID = accountSuitID;
    }

    public String getAccountSuitID() {
        return this.AccountSuitID;
    }

    public String OrganizationID;//账套ID

    public void setOrganizationID(String organizationID) {
        this.OrganizationID = organizationID;
    }

    public String getOrganizationID() {
        return this.OrganizationID;
    }

    public String BillTypeID="1";//单据类型ID

    public void setBillTypeID(String billTypeID) {
        this.BillTypeID = billTypeID;
    }
    public String getBillTypeID() {
         if(this.BillTypeID=="")
             return "1";
        return this.BillTypeID;
    }

    public String EmpID="0";//单据类型ID
    public void setEmpID(String empID) {
        this.EmpID = empID;
    }

    public String getEmpID() {
        return this.EmpID;
    }
    public String UserID;//

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public String getUserID() {
        return this.UserID;
    }

    public String UserName;//erp登录名

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getUserName() {
        return this.UserName;
    }

    public String PassWord;//erp登录密码

    public void setPassWord(String passWord) {
        this.PassWord = passWord;
    }

    public String getPassWord() {
        return this.PassWord;
    }

    public String Red;//是否冲红
    public void setRed(String red) {
        this.Red = red;
    }
    public String getRed() {
        return this.Red;
    }

    public String AutoLogin;//是否冲红
    public void setAutoLogin(String AutoLogin) {
        this.AutoLogin = AutoLogin;
    }
    public String getAutoLogin() {
        return this.AutoLogin;
    }

    public int AccountPosition;//账套选择序号
    public void setAccountPosition(int accountPosition) {
        this.AccountPosition = accountPosition;
    }
    public int getAccountPosition() {
        return this.AccountPosition;
    }

    public int OrgPosition;//组织选择序号
    public void setOrgPosition(int orgPosition) {
        this.OrgPosition = orgPosition;
    }
    public int getOrgPosition() {
        return this.OrgPosition;
    }

    public int BillTypePosition;//单据类型序号
    public void setBillTypePosition(int orgPosition) {
        this.BillTypePosition = orgPosition;
    }
    public int getBillTypePosition()
    {
        return this.BillTypePosition;
    }

    public String DefaultStockID;//默认仓库ID
    public void setDefaultStockID(String defaultStockID) {
        this.DefaultStockID = defaultStockID;
    }
    public String getDefaultStockID()
    {
        return this.DefaultStockID;
    }


    public String StockBillNO;//所生成的单据编号
    public void setStockBillNO(String stockBillNO) {
        this.StockBillNO = stockBillNO;
    }
    public String getStockBillNO()
    {
        return this.StockBillNO;
    }



}
