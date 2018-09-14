package com.goldemperor.DayWorkCardReport.model;

/**
 * File Name : SCCJLCCLXS_ReportDetailedModel
 * Created by : PanZX on  2018/6/20 08:14
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class SCCJLCCLXS_ReportDetailedSizeModel {
    //	"线别": "针车1组",
    //	"指令单号": "j1804648",
    //	"工厂型体": "dx13301-10",
    //	"尺码": "36",
    //	"今任务数": 0.0000000000,
    //	"指令数量": 1271.0000000000,
    //	"今日完成": 0.0000000000,
    //	"累计扫描": 1181.0000000000,
    //	"指令欠数": 90.0000000000

    String 线别;
    String 指令单号;
    String 工厂型体;
    String 尺码;
    int 今任务数;
    int 指令数量;
    int 今日完成;
    int 累计扫描;
    int 指令欠数;

    public String get线别() {
        return 线别;
    }

    public void set线别(String 线别) {
        this.线别 = 线别;
    }

    public String get指令单号() {
        return 指令单号;
    }

    public void set指令单号(String 指令单号) {
        this.指令单号 = 指令单号;
    }

    public String get工厂型体() {
        return 工厂型体;
    }

    public void set工厂型体(String 工厂型体) {
        this.工厂型体 = 工厂型体;
    }

    public String get尺码() {
        return 尺码;
    }

    public void set尺码(String 尺码) {
        this.尺码 = 尺码;
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
}