package com.goldemperor.MainActivity.NewHome.Model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : AccountSuitXmlModel
 * Created by : PanZX on  2018/7/11 15:25
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：账套数据模型XML格式
 */
@XStreamAlias("NewDataSet")
public class AccountSuitXmlModel {
    //<NewDataSet>
//    <DbHelperTable>
//        <FAccountSuitID>1</FAccountSuitID>
//        <Code>01|金帝集团股份有限公司</Code>
//        <FAccountSuitName>金帝集团股份有限公司</FAccountSuitName>
//    </DbHelperTable>


    @XStreamImplicit()
    public List<table> DbHelperTable = new ArrayList<table>();


    public List<table> getDbHelperTable() {
        return DbHelperTable;
    }

    public void setDbHelperTable(List<table> dbHelperTable) {
        DbHelperTable = dbHelperTable;
    }

    public static class table {
        String FAccountSuitID;
        String Code;
        String FAccountSuitName;

        public String getFAccountSuitID() {
            return FAccountSuitID;
        }

        public void setFAccountSuitID(String FAccountSuitID) {
            this.FAccountSuitID = FAccountSuitID;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getFAccountSuitName() {
            return FAccountSuitName;
        }

        public void setFAccountSuitName(String FAccountSuitName) {
            this.FAccountSuitName = FAccountSuitName;
        }
    }
}
