package com.goldemperor.ShowCapacity.model;

import java.util.List;

/**
 * File Name : HourAllInfoModel
 * Created by : PanZX on  2018/10/20 14:52
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class HourAllInfoModel {
    //"Time": "7:00-9:00",
    //	"Qualified": 100,
    //	"UnQualified": 1

    int Line;
    List<data> Data;

    public int getLine() {
        return Line;
    }

    public void setLine(int line) {
        Line = line;
    }

    public List<data> getData() {
        return Data;
    }

    public void setData(List<data> data) {
        Data = data;
    }

    public class data {
        String Time;
        int Qualified;
        int UnQualified;

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public int getQualified() {
            return Qualified;
        }

        public void setQualified(int qualified) {
            Qualified = qualified;
        }

        public int getUnQualified() {
            return UnQualified;
        }

        public void setUnQualified(int unQualified) {
            UnQualified = unQualified;
        }
    }
}
