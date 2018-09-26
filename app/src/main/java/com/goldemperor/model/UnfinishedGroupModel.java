package com.goldemperor.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : UnfinishedGroupModel
 * Created by : PanZX on  2018/9/21 17:15
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@XStreamAlias("NewDataSet")
public class UnfinishedGroupModel {
    //<NewDataSet>
    //  <DbHelperTable>
    //    <FItemID>376292</FItemID>
    //    <FParentID>376290</FParentID>
    //    <FNumber>01.01.01</FNumber>
    //    <FName>开发确认组</FName>
    //  </DbHelperTable>

    @XStreamImplicit()
    public List<table> DbHelperTable = new ArrayList<table>();

    public List<table> getDbHelperTable() {
        return DbHelperTable;
    }

    public void setDbHelperTable(List<table> dbHelperTable) {
        DbHelperTable = dbHelperTable;
    }

    public static class table {
        String FItemID;
        String FParentID;
        String FNumber;
        String FName;

        public String getFItemID() {
            return FItemID;
        }

        public void setFItemID(String FItemID) {
            this.FItemID = FItemID;
        }

        public String getFParentID() {
            return FParentID;
        }

        public void setFParentID(String FParentID) {
            this.FParentID = FParentID;
        }

        public String getFNumber() {
            return FNumber;
        }

        public void setFNumber(String FNumber) {
            this.FNumber = FNumber;
        }

        public String getFName() {
            return FName;
        }

        public void setFName(String FName) {
            this.FName = FName;
        }
    }
}
