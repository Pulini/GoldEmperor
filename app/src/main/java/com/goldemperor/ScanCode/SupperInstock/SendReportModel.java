package com.goldemperor.ScanCode.SupperInstock;

import java.util.List;

/**
 * File Name : SendReportModel
 * Created by : PanZX on  2018/6/7 11:02
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class SendReportModel {
    List<bjson> barcodeJson;
    String OrganizeID;
    String BillTypeID;
    String DefaultStockID;
    String Red;
    String UserID;

    public List<bjson> getBarcodeJson() {
        return barcodeJson;
    }

    public void setBarcodeJson(List<bjson> barcodeJson) {
        this.barcodeJson = barcodeJson;
    }

    public String getOrganizeID() {
        return OrganizeID;
    }

    public void setOrganizeID(String organizeID) {
        OrganizeID = organizeID;
    }

    public String getBillTypeID() {
        return BillTypeID;
    }

    public void setBillTypeID(String billTypeID) {
        BillTypeID = billTypeID;
    }

    public String getDefaultStockID() {
        return DefaultStockID;
    }

    public void setDefaultStockID(String defaultStockID) {
        DefaultStockID = defaultStockID;
    }

    public String getRed() {
        return Red;
    }

    public void setRed(String red) {
        Red = red;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public static class bjson {
        public bjson(String d_BarCode) {
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
}
