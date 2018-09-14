package com.goldemperor.ScanCode;

/**
 * File Name : ScanCodeUpLoadModel
 * Created by : PanZX on  2018/8/24 14:07
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ScanCodeUpLoadModel {
    public ScanCodeUpLoadModel(String d_BarCode) {
        D_BarCode = d_BarCode;
    }

    String D_BarCode;

    public String getD_BarCode() {
        return D_BarCode;
    }

    public void setD_BarCode(String d_BarCode) {
        D_BarCode = d_BarCode;
    }
}
