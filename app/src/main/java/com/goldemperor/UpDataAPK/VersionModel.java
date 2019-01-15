package com.goldemperor.UpDataAPK;


/**
 * File Name : VersionModel
 * Created by : PanZX on  2018/12/8 14:19
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class VersionModel {
    int VersionCode;
    String VersionName;
    String Url;
    String Description;
    boolean Force;

    public int getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(int versionCode) {
        VersionCode = versionCode;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isForce() {
        return Force;
    }

    public void setForce(boolean force) {
        Force = force;
    }
}
