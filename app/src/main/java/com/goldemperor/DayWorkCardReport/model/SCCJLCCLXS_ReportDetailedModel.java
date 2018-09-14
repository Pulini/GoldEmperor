package com.goldemperor.DayWorkCardReport.model;

/**
 * File Name : SCCJLCCLXS_ReportDetailedModel
 * Created by : PanZX on  2018/6/20 08:14
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class SCCJLCCLXS_ReportDetailedModel {
    //		"fdepartmentid": 412910,
    //		"fsrcicmointerid": 108497,
    //		"线别": "针车32组",
    //		"工厂型体": "dxsa182186-30m",
    //		"鞋图": system.byte[],
    //		"鞋图路径": "金帝集团有限公司/型体/2017/12/dxsa182186-30m/dxsa182186-30m.jpg",
    //		"指令单号": "j1804665",
    //		"今任务数": 0.0000000000,
    //		"指令数量": 4014.0000000000,
    //		"今日完成": 0.0000000000,
    //		"累计扫描": 1705.0000000000,
    //		"指令欠数": 2309.0000000000
    int fdepartmentid;
    int fsrcicmointerid;
    String 线别;
    String 工厂型体;
    int 鞋图;
    String 鞋图路径;
    String 指令单号;
    int 今任务数;
    int 指令数量;
    int 今日完成;
    int 累计扫描;
    int 指令欠数;

    public int getFdepartmentid() {
        return fdepartmentid;
    }

    public void setFdepartmentid(int fdepartmentid) {
        this.fdepartmentid = fdepartmentid;
    }

    public int getFsrcicmointerid() {
        return fsrcicmointerid;
    }

    public void setFsrcicmointerid(int fsrcicmointerid) {
        this.fsrcicmointerid = fsrcicmointerid;
    }

    public String get线别() {
        return 线别;
    }

    public void set线别(String 线别) {
        this.线别 = 线别;
    }

    public String get工厂型体() {
        return 工厂型体;
    }

    public void set工厂型体(String 工厂型体) {
        this.工厂型体 = 工厂型体;
    }

    public String get鞋图路径() {
        return 鞋图路径;
    }

    public void set鞋图路径(String 鞋图路径) {
        this.鞋图路径 = 鞋图路径;
    }

    public String get指令单号() {
        return 指令单号;
    }

    public void set指令单号(String 指令单号) {
        this.指令单号 = 指令单号;
    }

    public int get今任务数() {
        return 今任务数;
    }

    public void set今任务数(int 今任务数) {
        this.今任务数 = 今任务数;
    }

    public int get指令数量() {
        return 指令数量;
    }

    public void set指令数量(int 指令数量) {
        this.指令数量 = 指令数量;
    }

    public int get今日完成() {
        return 今日完成;
    }

    public void set今日完成(int 今日完成) {
        this.今日完成 = 今日完成;
    }

    public int get累计扫描() {
        return 累计扫描;
    }

    public void set累计扫描(int 累计扫描) {
        this.累计扫描 = 累计扫描;
    }

    public int get指令欠数() {
        return 指令欠数;
    }

    public void set指令欠数(int 指令欠数) {
        this.指令欠数 = 指令欠数;
    }

    public int get鞋图() {
        return 鞋图;
    }

    public void set鞋图(int 鞋图) {
        this.鞋图 = 鞋图;
    }
}
