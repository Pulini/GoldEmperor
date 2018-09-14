package com.goldemperor.model;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by xufanglou on 2016-07-20.
 */
public class UserLoginInfo {
    //登录窗体的控件信息
    public Spinner spinner_zzt;//当前账套
    public Spinner spinner_org;//组织名称
    public Spinner spinner_billtype;//单据类型
    public EditText login_username;//erp用户名
    public EditText login_password;//erp用户密码
    public CheckBox login_red;//是否红冲
    public CheckBox login_autoLogin;//记住密码

    public UserInfo userInfo;//用户的基本信息


    public UserLoginInfo(Spinner spinner_zzt,Spinner spinner_org,Spinner spinner_billtype,EditText login_username
    ,EditText login_password,CheckBox login_red,CheckBox login_autoLogin)
    {
        this.spinner_zzt=spinner_zzt;
        this.spinner_org=spinner_org;
        this.spinner_billtype=spinner_billtype;
        this.login_username=login_username;
        this.login_password=login_password;
        this.login_red=login_red;
        this.login_autoLogin=login_autoLogin;
        userInfo=new UserInfo();
    }
    //设置账套ID
    public void SetAccountSuitID(String AccountSuitID)
    {
        userInfo.AccountSuitID=AccountSuitID;
    }
}
