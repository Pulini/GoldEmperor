package com.goldemperor.LoginActivity;

/**
 * Created by Nova on 2017/7/27.
 */

public class ResponseBody {
    String FNumber;
    String name;
    String positon;
    String sex;

    public void setFNumber(String FNumber){
        this.FNumber=FNumber;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setPositon(String positon){
        this.positon=positon;
    }

    public void setSex(String sex){
        this.sex=sex;
    }

    public String getFNumber(){
        return this.FNumber;
    }

    public String getName(){
        return this.name;
    }

    public String getPositon(){
        return this.positon;
    }

    public String getSex(){
        return this.sex;
    }
}
