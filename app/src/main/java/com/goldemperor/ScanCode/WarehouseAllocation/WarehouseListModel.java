package com.goldemperor.ScanCode.WarehouseAllocation;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * File Name : WarehouseListModel
 * Created by : PanZX on  2018/7/10 15:46
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：仓库调拨单 仓库列表数据模型
 */
public class WarehouseListModel implements IPickerViewData {
    //"EntryList": [{
    //		"FItemID": 1,
    //		"FName": "金帝面料仓"
    //	}, {
    //		"FItemID": 72406,
    //		"FName": "金帝针辅仓"
    //	}, {

    int FItemID;
    String FName;
    List<Entry> EntryList;

    public int getFItemID() {
        return FItemID;
    }

    public void setFItemID(int FItemID) {
        this.FItemID = FItemID;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public List<Entry> getEntryList() {
        return EntryList;
    }

    public void setEntryList(List<Entry> entryList) {
        EntryList = entryList;
    }

    public static class Entry {
        int FItemID;
        String FName;

        public int getFItemID() {
            return FItemID;
        }

        public void setFItemID(int FItemID) {
            this.FItemID = FItemID;
        }

        public String getFName() {
            return FName;
        }

        public void setFName(String FName) {
            this.FName = FName;
        }
    }
    @Override
    public String getPickerViewText() {
        return FName;
    }
}
