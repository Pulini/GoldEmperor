package com.goldemperor.ShowCapacity.model;

import java.util.List;

/**
 * File Name : ProDayLeaveFourSizeReportSource
 * Created by : PanZX on  2018/10/22 19:59
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ProDayLeaveFourSizeReportSource {

    List<data1> AllSizeList;
    String FItemID;
    String PicturePath;


    public String getFItemID() {
        return FItemID;
    }

    public void setFItemID(String FItemID) {
        this.FItemID = FItemID;
    }

    public String getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(String picturePath) {
        PicturePath = picturePath;
    }

    public List<data1> getAllSizeList() {
        return AllSizeList;
    }

    public void setAllSizeList(List<data1> allSizeList) {
        AllSizeList = allSizeList;
    }



    public static class data1 {
        List<data2> SizeQtyList;

        public List<data2> getSizeQtyList() {
            return SizeQtyList;
        }

        public void setSizeQtyList(List<data2> sizeQtyList) {
            SizeQtyList = sizeQtyList;
        }
    }

    public static class data2 {
        String Qty;
        String Size;

        public String getQty() {
            return Qty;
        }

        public void setQty(String qty) {
            Qty = qty;
        }

        public String getSize() {
            return Size;
        }

        public void setSize(String size) {
            Size = size;
        }
    }
}
