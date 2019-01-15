package com.goldemperor.MainActivity.NewHome.Model;

/**
 * File Name : HomePhotoModel
 * Created by : PanZX on  2018/10/24 19:52
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class HomePhotoModel {
    //	"PictureName": "整洁明亮的精益生产车间",
    //		"PicturePath": "http://192.168.99.79:8083/Picture/3.jpg"
    String PictureName;
    String PicturePath;

    public String getPictureName() {
        return PictureName;
    }

    public void setPictureName(String pictureName) {
        PictureName = pictureName;
    }

    public String getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(String picturePath) {
        PicturePath = picturePath;
    }
}
