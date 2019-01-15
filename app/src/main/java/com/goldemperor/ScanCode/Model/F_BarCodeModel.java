package com.goldemperor.ScanCode.Model;

/**
 * File Name : F_BarCodeModel
 * Created by : PanZX on  2018/11/1 10:42
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class F_BarCodeModel {
    public F_BarCodeModel(String FBarCode) {
        this.FBarCode = FBarCode;
    }

    String FBarCode;

    public String getFBarCode() {
        return FBarCode;
    }

    public void setFBarCode(String FBarCode) {
        this.FBarCode = FBarCode;
    }
}
