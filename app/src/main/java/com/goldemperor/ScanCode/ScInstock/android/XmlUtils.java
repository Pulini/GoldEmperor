package com.goldemperor.ScanCode.ScInstock.android;

/**
 * Created by xufanglou on 2016-07-20.
 */

import android.util.Log;
import android.util.Xml;

import com.goldemperor.ScanCode.ScInstock.MainActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析XML工具类
 */
public class XmlUtils {

    public static List<Object> parse(String xmlContent, Class<?> clazz,
                                     List<String> fields, List<String> elements, String itemElement) {
        Log.i(MainActivity.TAG, "xmlContent：" + xmlContent);
        List<Object> list = new ArrayList<Object>();
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlContent));
            list=parseCommon(xmlPullParser,clazz,fields,elements,itemElement);
        } catch (Exception e) {
            //Log.e("rss", "解析XML异常：" + e.getMessage());
            throw new RuntimeException("解析XML异常：" + e.getMessage());
        }
        return list;
    }
    public static List<Object> parseCommon(XmlPullParser xmlPullParser, Class<?> clazz,
                                           List<String> fields, List<String> elements, String itemElement) {
        Log.i(MainActivity.TAG, "开始解析XML.");
        List<Object> list = new ArrayList<Object>();
        try {
            int event = xmlPullParser.getEventType();
            Object obj = null;

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
//                    case XmlPullParser.START_DOCUMENT://文档开始事件,可以进行数据初始化处理
//                        //persons = new ArrayList<Person>();
//                        break;
                    case XmlPullParser.START_TAG:
                        //Log.i(MainActivity.TAG, "START_TAG");
                        if (itemElement.equals(xmlPullParser.getName())) {
                            obj = clazz.newInstance();
                            //Log.i(MainActivity.TAG, "创建对象");
                        }
                        if (obj != null && elements.contains(xmlPullParser.getName())) {
                            String FieldName = fields.get(elements.indexOf(xmlPullParser.getName()));
                            String FieldValue = xmlPullParser.nextText();
                            //Log.i(MainActivity.TAG, "Field：" +FieldName);
                            //Log.i(MainActivity.TAG, "Value：" + FieldValue);
                            if (obj != null) {
                                setFieldValue(obj, FieldName, FieldValue);
                            }
                            //setFieldValue(obj, fields.get(elements.indexOf(xmlPullParser.getName())),xmlPullParser.nextText());

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //Log.i(MainActivity.TAG, "END_TAG");
                        if (itemElement.equals(xmlPullParser.getName())) {
                            list.add(obj);
                            obj = null;
                        }
                        break;
                }
                event = xmlPullParser.next();
            }
            Log.i(MainActivity.TAG, "结束解析XML.");
        } catch (Exception e) {
            Log.i(MainActivity.TAG, "解析XML异常：" + e.getMessage());
            //Log.e("rss", "解析XML异常：" + e.getMessage());
            throw new RuntimeException("解析XML异常：" + e.getMessage());
        }
        return list;
    }

    /**
     * 解析XML转换成对象
     *
     * @param is          输入流
     * @param clazz       对象Class
     * @param fields      字段集合一一对应节点集合
     * @param elements    节点集合一一对应字段集合
     * @param itemElement 每一项的节点标签
     * @return
     */
    public static List<Object> parse(InputStream is, Class<?> clazz,
                                     List<String> fields, List<String> elements, String itemElement) {
        List<Object> list = new ArrayList<Object>();
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(is, "UTF-8");
            list=parseCommon(xmlPullParser,clazz,fields,elements,itemElement);
        } catch (Exception e) {
            //Log.e("rss", "解析XML异常：" + e.getMessage());
            throw new RuntimeException("解析XML异常：" + e.getMessage());
        }
        return list;
    }

    /**
     * 设置字段值
     *
     * @param propertyName 字段名
     * @param obj          实例对象
     * @param value        新的字段值
     * @return
     */

    public static void setFieldValue(Object obj, String propertyName,
                                     Object value) {
        try {
            //Log.i(MainActivity.TAG, "setFieldValue propertyName：" + propertyName);
            //Log.i(MainActivity.TAG, "setFieldValue FieldValue：" + value);
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

}

