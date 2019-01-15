package com.goldemperor.MainActivity.NewHome;

import android.content.Intent;

/**
 * File Name : ButtonData
 * Created by : PanZX on  2018/9/30 09:15
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ButtonData {
    public ButtonData(String name, String jurisdiction, int imageId, Intent intent) {
        Name = name;
        Jurisdiction = jurisdiction;
        this.imageId = imageId;
        this.intent = intent;
    }

    String Name;
    String Jurisdiction;
    int imageId;
    Intent intent;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getJurisdiction() {
        return Jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        Jurisdiction = jurisdiction;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
