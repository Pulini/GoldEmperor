package com.goldemperor.PgdActivity;

import java.util.ArrayList;

/**
 * Created by Nova on 2017/9/18.
 */

public class ProcessOutPut {

    private String ReturnType;
    private ArrayList<Sc_ProcessOutPutEntry> ReturnMsg;

    public void setReturnMsg(ArrayList<Sc_ProcessOutPutEntry> ReturnMsg) {
        this.ReturnMsg = ReturnMsg;
    }

    public void setReturnType(String ReturnType) {
        this.ReturnType = ReturnType;
    }


    public ArrayList<Sc_ProcessOutPutEntry> getReturnMsg() {
        return this.ReturnMsg;
    }

    public String getReturnType() {
        return this.ReturnType;
    }

    public ProcessOutPut(){

    }

}
