package com.goldemperor.ShowCapacity.model;

/**
 * File Name : DayWorkCardReportModel
 * Created by : PanZX on  2018/10/20 09:45
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class DayWorkCardReportModel {

    String FName; //姓名
    String Picture; //头像
    String ProductionType; //组别
    int NumberOfGoalsToday;//今日计划/目标
    int NumberOfCompletions;//今日达成
    int FMustPeopleCount;//实际出勤人数
    int FPeopleCount;//应出勤人数

    double StandardTimeCapacity;//标准时产能
    double ActualTimeCapacity;//实际时产能
    double PerTimeCapacity;//人均时产能
    double WorkRate;//实时产能/设置颜色
    double QualifiedRate;//合格率

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getProductionType() {
        return ProductionType;
    }

    public void setProductionType(String productionType) {
        ProductionType = productionType;
    }

    public int getNumberOfGoalsToday() {
        return NumberOfGoalsToday;
    }

    public void setNumberOfGoalsToday(int numberOfGoalsToday) {
        NumberOfGoalsToday = numberOfGoalsToday;
    }

    public int getNumberOfCompletions() {
        return NumberOfCompletions;
    }

    public void setNumberOfCompletions(int numberOfCompletions) {
        NumberOfCompletions = numberOfCompletions;
    }

    public int getFMustPeopleCount() {
        return FMustPeopleCount;
    }

    public void setFMustPeopleCount(int FMustPeopleCount) {
        this.FMustPeopleCount = FMustPeopleCount;
    }

    public int getFPeopleCount() {
        return FPeopleCount;
    }

    public void setFPeopleCount(int FPeopleCount) {
        this.FPeopleCount = FPeopleCount;
    }

    public double getStandardTimeCapacity() {
        return StandardTimeCapacity;
    }

    public void setStandardTimeCapacity(double standardTimeCapacity) {
        StandardTimeCapacity = standardTimeCapacity;
    }

    public double getActualTimeCapacity() {
        return ActualTimeCapacity;
    }

    public void setActualTimeCapacity(double actualTimeCapacity) {
        ActualTimeCapacity = actualTimeCapacity;
    }

    public double getPerTimeCapacity() {
        return PerTimeCapacity;
    }

    public void setPerTimeCapacity(double perTimeCapacity) {
        PerTimeCapacity = perTimeCapacity;
    }

    public double getWorkRate() {
        return WorkRate;
    }

    public void setWorkRate(double workRate) {
        WorkRate = workRate;
    }

    public double getQualifiedRate() {
        return QualifiedRate;
    }

    public void setQualifiedRate(double qualifiedRate) {
        QualifiedRate = qualifiedRate;
    }
}
