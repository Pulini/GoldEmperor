package com.goldemperor.Utils;


import android.os.Handler;
import android.os.Message;

import com.goldemperor.MainActivity.define;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;
import org.xutils.http.RequestParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 访问WebService的工具类 Created by Linus_Xie on 2016/6/25.
 */
public class WebServiceUtils {
    public static String WEBSERVER_NAMESPACE = "http://tempuri.org/";// 命名空间

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);// 含有3个线程的线程池

    /**
     * @param url                WebService服务器地址
     * @param methodName         WebService的调用方法名
     * @param properties         WebService的参数
     * @param webServiceCallBack 回调接口
     */
    public static void callWebService(String url, final String methodName, final HashMap<String, String> properties,
                                      final WebServiceCallBack webServiceCallBack) {

        LOG.e("Url=" + url);
        LOG.e("MethodName=" + methodName);
        LOG.E("map=" + properties.toString());
        // 创建MyHttpTransportSE对象，传递WebService服务器地址
        final MyHttpTransportSE ht = new MyHttpTransportSE(url);
        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(WEBSERVER_NAMESPACE, methodName);

        // SoapObject添加参数
        if (properties != null) {
            for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }


        // 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        // 设置是否调用的是.Net开发的WebService
        soapEnvelope.bodyOut = soapObject;
        soapEnvelope.dotNet = true;
        soapEnvelope.setOutputSoapObject(soapObject);

        // 用于子线程与主线程通信的Handler
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 将返回值回调到callBack的参数中
//                checkTokenErrorCode((String) msg.obj,activity,webServiceCallBack);
                webServiceCallBack.callBack((String) msg.obj);
            }
        };

        // 开启线程去访问WebService
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String resultSoapObject = null;
                try {
                    ht.call(WEBSERVER_NAMESPACE + methodName, soapEnvelope);
                    if (soapEnvelope.getResponse() != null) {
                        // 获取服务器响应返回的SoapObject
                        resultSoapObject = ((SoapPrimitive) soapEnvelope.getResponse()).toString();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 将获取的消息利用Handler发送到主线程
                    mHandler.sendMessage(mHandler.obtainMessage(0, resultSoapObject));
                }
            }
        });
    }


    public interface WebServiceCallBack {
        public void callBack(String result);
    }



}
