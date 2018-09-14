package com.goldemperor.ScanCode.ProcessReportInstock;

/**
 * File Name : ProcressFlowModel
 * Created by : PanZX on  2018/8/17 16:31
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ProcressFlowModel {
    //{"ReturnMsg":"[{\"FProcessFlowID\":14,\"FProcessFlowName\":\"自制中底\",\"FProcessNodeName\":\"中底领料\"}]","ReturnType":"success"}

    int FProcessFlowID;
    String FProcessFlowName;
    String FProcessNodeName;

    public int getFProcessFlowID() {
        return FProcessFlowID;
    }

    public void setFProcessFlowID(int FProcessFlowID) {
        this.FProcessFlowID = FProcessFlowID;
    }

    public String getFProcessFlowName() {
        return FProcessFlowName;
    }

    public void setFProcessFlowName(String FProcessFlowName) {
        this.FProcessFlowName = FProcessFlowName;
    }

    public String getFProcessNodeName() {
        return FProcessNodeName;
    }

    public void setFProcessNodeName(String FProcessNodeName) {
        this.FProcessNodeName = FProcessNodeName;
    }
}
