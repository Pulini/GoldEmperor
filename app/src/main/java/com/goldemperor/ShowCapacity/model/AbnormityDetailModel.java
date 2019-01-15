package com.goldemperor.ShowCapacity.model;

/**
 * File Name : AbnormityDetailModel
 * Created by : PanZX on  2018/10/20 08:53
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：异常数据分类列表
 */
public class AbnormityDetailModel {
    int FQty;
    int FExceptionID;
    String FName;

    public int getFQty() {
        return FQty;
    }

    public void setFQty(int FQty) {
        this.FQty = FQty;
    }

    public int getFExceptionID() {
        return FExceptionID;
    }

    public void setFExceptionID(int FExceptionID) {
        this.FExceptionID = FExceptionID;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }
}
