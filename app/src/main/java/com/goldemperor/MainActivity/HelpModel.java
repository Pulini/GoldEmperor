package com.goldemperor.MainActivity;

/**
 * File Name : HelpModel
 * Created by : PanZX on  2018/7/5 15:01
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class HelpModel {
    //[{"FName":"针车帮助文档","FHelpUrl":"http:\/\/192.168.99.79:3001\/document\/40603.html"}]
    String FName;
    String FHelpUrl;

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFHelpUrl() {
        return FHelpUrl;
    }

    public void setFHelpUrl(String FHelpUrl) {
        this.FHelpUrl = FHelpUrl;
    }
}
