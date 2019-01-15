package com.goldemperor.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : ScProcessWorkCardInfoBysuitID
 * Created by : PanZX on  2018/10/29 14:52
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@XStreamAlias("NewDataSet")
public class ScProcessWorkCardInfoBysuitID {

    //<NewDataSet>
    //    <DbHelperTable>
    //        <FCardNo>GXPG1831003/1</FCardNo>
    //        <FEmpID>126517</FEmpID>
    //        <FEmpName>王滔</FEmpName>
    //        <FQty>1458.000</FQty>
    //    </DbHelperTable>
    //</NewDataSet>

    @XStreamImplicit()
    public List<table> DbHelperTable = new ArrayList<table>();

    public List<table> getDbHelperTable() {
        return DbHelperTable;
    }

    public void setDbHelperTable(List<table> dbHelperTable) {
        DbHelperTable = dbHelperTable;
    }

    public static class table {
        String FCardNo;
        String FEmpID;
        String FEmpName;
        String FQty;

        public String getFCardNo() {
            return FCardNo;
        }

        public void setFCardNo(String FCardNo) {
            this.FCardNo = FCardNo;
        }

        public String getFEmpID() {
            return FEmpID;
        }

        public void setFEmpID(String FEmpID) {
            this.FEmpID = FEmpID;
        }

        public String getFEmpName() {
            return FEmpName;
        }

        public void setFEmpName(String FEmpName) {
            this.FEmpName = FEmpName;
        }

        public String getFQty() {
            return FQty;
        }

        public void setFQty(String FQty) {
            this.FQty = FQty;
        }
    }
}
