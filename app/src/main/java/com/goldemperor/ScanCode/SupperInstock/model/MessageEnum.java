package com.goldemperor.ScanCode.SupperInstock.model;

/**
 * Created by xufanglou on 2016-07-21.
 */
public class MessageEnum {
    //显示指令单号
    public static final int ShowMoNo = 1;
    //显示指令报表
    public static final int ShowWorkCardInfo = 2;

    //显示指令尺码报表
    public static final int ShowWorkCardSizeList = 3;

    //显示指令尺码报表
    public static final int ClearBarCodeData =4;

    //清空本地数据，并提交到webservice
    public static final int ClearSubmitData =5;

    //显示指令尺码报表
    public static final int SubmitClearData=6;
    //显示指令尺码报表
    public static final int ShowZzt =7;//显示账套信息

    //显示指令尺码报表
    public static final int ShowOrg =8;//显示组织信息
    //显示指令尺码报表
    public static final int ShowBillType =9;//显示单据信息

    //用户登录
    public static final int  UserLogin=10;//显示单据信息

    //更新客户端
    public static final int UPDATA_CLIENT = 11;
    //更新客户端出错
    public static final int GET_UNDATAINFO_ERROR =12;
    //下载新版本失败
    public static final int DOWN_ERROR = 13;
    //系统登录
    public static final int LoginMain = 14;

}
