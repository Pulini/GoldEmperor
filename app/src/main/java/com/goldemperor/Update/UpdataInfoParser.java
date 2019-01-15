package com.goldemperor.Update;

import android.util.Xml;

import com.goldemperor.MainActivity.define;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.model.UpdataInfo;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by xufanglou on 2016-08-27.
 */
public class UpdataInfoParser {
    /*
 * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
 */
    public static UpdataInfo getUpdataInfo(InputStream is) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");//设置解析的数据源
        int type = parser.getEventType();
        UpdataInfo info = new UpdataInfo();//实体
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("version".equals(parser.getName())) {
                        info.setVersion(parser.nextText());    //获取版本号
                    } else if ("outurl".equals(parser.getName())) {
                        if (SPUtils.getServerPath().contains("8056")) {
                            info.setUrl(parser.nextText());    //获取要升级的APK文件
                        }
                    } else if ("url".equals(parser.getName())) {
                        if (!SPUtils.getServerPath().contains("8056")) {
                            info.setUrl(parser.nextText()
//                                    .replace(".apk","2.apk")
                            );    //获取要升级的APK文件
                        }
                    } else if ("description".equals(parser.getName())) {
                        info.setDescription(parser.nextText());    //获取该文件的信息
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }
}
