package com.goldemperor.ScanCode.SupperInstock.android;

/**
 * Created by xufanglou on 2016-08-16.
 * 系统配置类
 */
public class Config {
    private static int billTypeID = 1;//默认为入库单
    public void setbillTypeID(int billTypeID) {
        this.billTypeID = billTypeID;
    }
    public int getbillTypeID() {
        return this.billTypeID;
    }

    /// <summary>
    /// 所在帐套ID
    /// </summary>
    private static int AccountSuitID ;
    public void setAccountSuitID(int accountSuitID) {
        this.AccountSuitID = accountSuitID;
    }
    public int getAccountSuitID() {
        return this.AccountSuitID;
    }
    /// <summary>
    /// 所在帐套名称
    /// </summary>
    private static String AccountName;
    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }
    public String getAccountName() {
        return this.AccountName;
    }
    /// <summary>
    /// 所在管理组织ID
    /// </summary>
    private static int OrganizeID ;
    public void setOrganizeID(int organizeID) {
        this.OrganizeID = organizeID;
    }
    public int getOrganizeID() {
        return this.OrganizeID;
    }

    /// <summary>
    /// 是否在外网
    /// </summary>
    private static boolean IsInOutNetwork ;
    public void setIsInOutNetwork(boolean isInOutNetwork) {
        this.IsInOutNetwork = isInOutNetwork;
    }
    public boolean getIsInOutNetwork() {
        return this.IsInOutNetwork;
    }

//    private static String IP;
//    public boolean getIP() {
//        return this.IP;
//    }
}
