package com.goldemperor.ShowCapacity.model;

import java.util.List;

/**
 * File Name : ProOrderOfCastReport
 * Created by : PanZX on  2018/10/24 08:59
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ProOrderOfCastReport {
    List<Month> MonthDT;
    List<Day> ToDayDT;

    public List<Month> getMonthDT() {
        return MonthDT;
    }

    public void setMonthDT(List<Month> monthDT) {
        MonthDT = monthDT;
    }

    public List<Day> getToDayDT() {
        return ToDayDT;
    }

    public void setToDayDT(List<Day> toDayDT) {
        ToDayDT = toDayDT;
    }

    public static class Month {
        /**
         * "FMonthOrderOfCast": 4,
         * "FMonthComprehensiveMark": 1.11,
         * "FMonthDepName": "针车31组",
         * "FMonthHGL": "100.00%",
         * "FMonthPlanQty": "5940",
         * "FMonthQty": "6767",
         * "FMonthDCL": "113.92%",
         * "FMonthRJCN": "2.10",
         * "FMonthHourAvgPay": "168.66",
         * "FComprehensiveMark": 1.30
         */
        Integer FMonthOrderOfCast;
        String FMonthDepName;
        String FMonthHGL;
        String FMonthQty;
        String FMonthRJCN;
        String FMonthHourAvgPay;
        double FMonthComprehensiveMark;
        String FMonthPlanQty;
        String FMonthDCL;
//        double FComprehensiveMark;

        public void setFMonthOrderOfCast(Integer FMonthOrderOfCast) {
            this.FMonthOrderOfCast = FMonthOrderOfCast;
        }

        public double getFMonthComprehensiveMark() {
            return FMonthComprehensiveMark;
        }

        public void setFMonthComprehensiveMark(double FMonthComprehensiveMark) {
            this.FMonthComprehensiveMark = FMonthComprehensiveMark;
        }

        public String getFMonthPlanQty() {
            return FMonthPlanQty;
        }

        public void setFMonthPlanQty(String FMonthPlanQty) {
            this.FMonthPlanQty = FMonthPlanQty;
        }

        public String getFMonthDCL() {
            return FMonthDCL;
        }

        public void setFMonthDCL(String FMonthDCL) {
            this.FMonthDCL = FMonthDCL;
        }
//
//        public double getFComprehensiveMark() {
//            return FComprehensiveMark;
//        }
//
//        public void setFComprehensiveMark(double FComprehensiveMark) {
//            this.FComprehensiveMark = FComprehensiveMark;
//        }

        public Integer getFMonthOrderOfCast() {
            return FMonthOrderOfCast;
        }

        public void setFMonthOrderOfCast(int FMonthOrderOfCast) {
            this.FMonthOrderOfCast = FMonthOrderOfCast;
        }

        public String getFMonthDepName() {
            return FMonthDepName;
        }

        public void setFMonthDepName(String FMonthDepName) {
            this.FMonthDepName = FMonthDepName;
        }

        public String getFMonthHGL() {
            return FMonthHGL;
        }

        public void setFMonthHGL(String FMonthHGL) {
            this.FMonthHGL = FMonthHGL;
        }

        public String getFMonthQty() {
            return FMonthQty;
        }

        public void setFMonthQty(String FMonthQty) {
            this.FMonthQty = FMonthQty;
        }

        public String getFMonthRJCN() {
            return FMonthRJCN;
        }

        public void setFMonthRJCN(String FMonthRJCN) {
            this.FMonthRJCN = FMonthRJCN;
        }

        public String getFMonthHourAvgPay() {
            return FMonthHourAvgPay;
        }

        public void setFMonthHourAvgPay(String FMonthHourAvgPay) {
            this.FMonthHourAvgPay = FMonthHourAvgPay;
        }
    }

    public static class Day {
        /**
         * "FOrderOfCast": 1,
         * "FDepName": "针车31组",
         * "FHGL": "100.00%",
         * "FPlanQty": "350",
         * "FQty": "490",
         * "FDCL": "140.00%",
         * "FRJCN": "2.59",
         * "FHourAvgPay": "0.00"
         */
        Integer FOrderOfCast;
        String FDepName;
        String FHGL;
        String FPlanQty;
        String FDCL;
        String FQty;
        String FRJCN;
        String FHourAvgPay;
        String FComprehensiveMark;

        public String getFComprehensiveMark() {
            return FComprehensiveMark;
        }

        public void setFComprehensiveMark(String FComprehensiveMark) {
            this.FComprehensiveMark = FComprehensiveMark;
        }

        public void setFOrderOfCast(Integer FOrderOfCast) {
            this.FOrderOfCast = FOrderOfCast;
        }

        public String getFPlanQty() {
            return FPlanQty;
        }

        public void setFPlanQty(String FPlanQty) {
            this.FPlanQty = FPlanQty;
        }

        public String getFDCL() {
            return FDCL;
        }

        public void setFDCL(String FDCL) {
            this.FDCL = FDCL;
        }

        public Integer getFOrderOfCast() {
            return FOrderOfCast;
        }

        public void setFOrderOfCast(int FOrderOfCast) {
            this.FOrderOfCast = FOrderOfCast;
        }

        public String getFDepName() {
            return FDepName;
        }

        public void setFDepName(String FDepName) {
            this.FDepName = FDepName;
        }

        public String getFHGL() {
            return FHGL;
        }

        public void setFHGL(String FHGL) {
            this.FHGL = FHGL;
        }

        public String getFQty() {
            return FQty;
        }

        public void setFQty(String FQty) {
            this.FQty = FQty;
        }

        public String getFRJCN() {
            return FRJCN;
        }

        public void setFRJCN(String FRJCN) {
            this.FRJCN = FRJCN;
        }

        public String getFHourAvgPay() {
            return FHourAvgPay;
        }

        public void setFHourAvgPay(String FHourAvgPay) {
            this.FHourAvgPay = FHourAvgPay;
        }
    }

}
