package com.goldemperor.LoginActivity;

/**
 * Created by Nova on 2017/7/27.
 */

public class Response {
    String StatusCode;
    String Info;

    public void setStatusCode(String StatusCode) {
        this.StatusCode = StatusCode;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }


    public String getInfo() {
        return this.Info;
    }

    public String getStatusCode() {
        return this.StatusCode;
    }

}
