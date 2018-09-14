package com.goldemperor.LoginActivity;

/**
 * Created by Nova on 2017/7/27.
 */

public class RequestBody {
    private String UserPhone;
    private String Password;

    public void setUserPhone(String UserPhone) {
        this.UserPhone = UserPhone;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getUserPhone(){
        return this.UserPhone;
    }

    public String getPassword(){
        return  this.Password;
    }

    public RequestBody(){

    }
}
