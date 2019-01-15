package com.goldemperor.MainActivity;

/**
 * File Name : CodeDataModel
 * Created by : PanZX on  2018/8/16 16:11
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class CodeDataModel {
    public String Code;
    public boolean Type = false;

    public CodeDataModel(String Code) {
        this.Code = Code;
    }

    public CodeDataModel(String Code, boolean Type) {
        this.Code = Code;
        this.Type = Type;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public boolean getType() {
        return Type;
    }

    public void setType(boolean type) {
        Type = type;
    }
}
