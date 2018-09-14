package com.goldemperor.Utils;

import android.graphics.drawable.Drawable;

/**
 * File Name : MyAppInfo
 * Created by : PanZX on  2018/6/26 15:01
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class MyAppInfo {
    private Drawable image;
    private String packageName;
    private String AppName;

    public MyAppInfo(Drawable image, String appName) {
        this.image = image;
        this.packageName = appName;
    }

    public MyAppInfo() {
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String appName) {
        this.packageName = appName;
    }

    public String getAppName() {
        return AppName;
    }
    public void setAppName(String appName) {
        AppName= appName;
    }
}
