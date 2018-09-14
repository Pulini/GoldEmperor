package com.goldemperor.DayWorkCardReport.model;

import java.util.List;

/**
 * File Name : SCCJLCCLXS_ReportModel
 * Created by : PanZX on  2018/5/18 15:26
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class SCCJLCCLXS_ReportModel {

    //    "车间地点": "金帝a幢三层",
//		"组别": "成型7线后段",
//		"带线干部": "申云峰",
//		"今日目标产量": 0.0000000000,
//		"今日产量": 0.0000000000,
//		"完成率": "0%",
//		"月累计目标产量": 0.0000000000,
//		"月累计产量": 0.0000000000,
//		"月完成率": "0%",
//		"实际人数": 0

    String 车间地点;
    String 组别;
    String 带线干部;
    int 今日目标产量;
    int 今日产量;
    String 完成率;
    int 月累计目标产量;
    int 月累计产量;
    String 月完成率;
    int 实际人数;
    String fdeptid;

    public String get车间地点() {
        return 车间地点;
    }

    public void set车间地点(String 车间地点) {
        this.车间地点 = 车间地点;
    }

    public String get组别() {
        return 组别;
    }

    public void set组别(String 组别) {
        this.组别 = 组别;
    }

    public String get带线干部() {
        return 带线干部;
    }

    public void set带线干部(String 带线干部) {
        this.带线干部 = 带线干部;
    }

    public int get今日目标产量() {
        return 今日目标产量;
    }

    public void set今日目标产量(int 今日目标产量) {
        this.今日目标产量 = 今日目标产量;
    }

    public int get今日产量() {
        return 今日产量;
    }

    public void set今日产量(int 今日产量) {
        this.今日产量 = 今日产量;
    }

    public String get完成率() {
        return 完成率;
    }

    public void set完成率(String 完成率) {
        this.完成率 = 完成率;
    }

    public int get月累计目标产量() {
        return 月累计目标产量;
    }

    public void set月累计目标产量(int 月累计目标产量) {
        this.月累计目标产量 = 月累计目标产量;
    }

    public int get月累计产量() {
        return 月累计产量;
    }

    public void set月累计产量(int 月累计产量) {
        this.月累计产量 = 月累计产量;
    }

    public String get月完成率() {
        return 月完成率;
    }

    public void set月完成率(String 月完成率) {
        this.月完成率 = 月完成率;
    }

    public int get实际人数() {
        return 实际人数;
    }

    public void set实际人数(int 实际人数) {
        this.实际人数 = 实际人数;
    }

    public String getFdeptid() {
        return fdeptid;
    }

    public void setFdeptid(String fdeptid) {
        this.fdeptid = fdeptid;
    }
}