package com.goldemperor.PgdActivity;

import java.util.ArrayList;

/**
 * Created by Nova on 2017/9/19.
 */

public class WechatPostByFEmpIDJson {
    private String FEmpID;
    private String template_id;
    private String url;
    private ArrayList<WechatPostByFEmpIDData> data;

    public void setFEmpID(String FEmpID) {
        this.FEmpID = FEmpID;
    }

    public String getFEmpID() {
        return this.FEmpID;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTemplate_id() {
        return this.template_id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }


    public void setData(ArrayList<WechatPostByFEmpIDData> data) {
        this.data = data;
    }


    public ArrayList<WechatPostByFEmpIDData> getData() {
        return this.data;
    }

    public WechatPostByFEmpIDJson() {
        data=new ArrayList<WechatPostByFEmpIDData>();
    }
}
