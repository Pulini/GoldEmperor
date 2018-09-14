package com.goldemperor.ScanCode.SupperInstock.model;

/**
 * 获得自动更新的版本信息
 * Created by xufanglou on 2016-08-27.
 */
public class UpdataInfo {
    private String version;
    private String url;
    private String description;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}