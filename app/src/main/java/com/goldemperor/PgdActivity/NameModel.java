package com.goldemperor.PgdActivity;

/**
 * File Name : NameModel
 * Created by : PanZX on  2018/7/6 14:38
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class NameModel {

    String Name;
    String Code;
    String ID;
    String Departid;

    public NameModel(String name, String code, String ID, String departid) {
        Name = name;
        Code = code;
        this.ID = ID;
        Departid = departid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDepartid() {
        return Departid;
    }

    public void setDepartid(String departid) {
        Departid = departid;
    }
}
