package com.goldemperor.model;

/**
 * Created by louge on 2017-09-30.
 */

public class DatableName {
    //供应商扫码入库 对应的条形码表
    private String pgStockIn_TableName = "t_BarCode";
    //成型后段扫码入库 对应的条形码表
    private String cxStockIn_TableName = "t_CxBarCode";
    //生产汇报扫码 对应的条形码表
    private String scReport_TableName = "t_ScReportBarCode";

    public String getpgStockIn_TableName() {
        return this.pgStockIn_TableName;
    }

    public String getcxStockIn_TableName() {
        return this.cxStockIn_TableName;
    }

    public String getscReport_TableName() {
        return this.scReport_TableName;
    }
}
