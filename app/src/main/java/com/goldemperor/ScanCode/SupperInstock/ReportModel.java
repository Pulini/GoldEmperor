package com.goldemperor.ScanCode.SupperInstock;

import java.io.Serializable;
import java.util.List;

/**
 * File Name : ReportModel
 * Created by : PanZX on  2018/6/5 22:10
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ReportModel implements Serializable {
  public   List<report> Report;

    public List<report> getReport() {
        return Report;
    }

    public void setReport(List<report> report) {
        Report = report;
    }

    public static class report implements Serializable {
        String FBillNo;
        String FWorkCardNo;
        String FICItemName;
        int FQty;
        String FSize;

        public String getFBillNo() {
            return FBillNo;
        }

        public void setFBillNo(String FBillNo) {
            this.FBillNo = FBillNo;
        }

        public String getFWorkCardNo() {
            return FWorkCardNo;
        }

        public void setFWorkCardNo(String FWorkCardNo) {
            this.FWorkCardNo = FWorkCardNo;
        }

        public String getFICItemName() {
            return FICItemName;
        }

        public void setFICItemName(String FICItemName) {
            this.FICItemName = FICItemName;
        }

        public int getFQty() {
            return FQty;
        }

        public void setFQty(int FQty) {
            this.FQty = FQty;
        }

        public String getFSize() {
            return FSize;
        }

        public void setFSize(String FSize) {
            this.FSize = FSize;
        }
    }

}
