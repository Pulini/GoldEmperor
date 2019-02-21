package com.goldemperor.PropertyRegistration;

/**
 * File Name : EmpModel
 * Created by : PanZX on  2019/1/26 08:28
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class EmpModel {

    //      "FEmpID": 70529, //保管人ID
    //		"FEmpCode": "000283", //保管人工号
    //		"FEmpName": "许方楼", //保管人名称
    //		"FLiableEmpID": 4677, //监管人ID
    //		"FLiableEmpCode": "000061", //监管人工号
    //		"FLiableEmpName": "林德勇", //监管人名称
    //		"FDeptmentID": 248842, //部门ID
    //		"DeptName": "信息中心" //部门名称

    int FEmpID;
    int FLiableEmpID;
    int FDeptmentID;
    String FEmpCode;
    String FEmpName;
    String FLiableEmpCode;
    String FLiableEmpName;
    String DeptName;

    public int getFEmpID() {
        return FEmpID;
    }

    public void setFEmpID(int FEmpID) {
        this.FEmpID = FEmpID;
    }

    public int getFLiableEmpID() {
        return FLiableEmpID;
    }

    public void setFLiableEmpID(int FLiableEmpID) {
        this.FLiableEmpID = FLiableEmpID;
    }

    public int getFDeptmentID() {
        return FDeptmentID;
    }

    public void setFDeptmentID(int FDeptmentID) {
        this.FDeptmentID = FDeptmentID;
    }

    public String getFEmpCode() {
        return FEmpCode;
    }

    public void setFEmpCode(String FEmpCode) {
        this.FEmpCode = FEmpCode;
    }

    public String getFEmpName() {
        return FEmpName;
    }

    public void setFEmpName(String FEmpName) {
        this.FEmpName = FEmpName;
    }

    public String getFLiableEmpCode() {
        return FLiableEmpCode;
    }

    public void setFLiableEmpCode(String FLiableEmpCode) {
        this.FLiableEmpCode = FLiableEmpCode;
    }

    public String getFLiableEmpName() {
        return FLiableEmpName;
    }

    public void setFLiableEmpName(String FLiableEmpName) {
        this.FLiableEmpName = FLiableEmpName;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }
}
