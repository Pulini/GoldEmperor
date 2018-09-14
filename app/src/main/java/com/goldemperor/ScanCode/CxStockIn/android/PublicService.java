package com.goldemperor.ScanCode.CxStockIn.android;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Xml;


import com.goldemperor.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PublicService {
    protected static final String TAG = "ScReportActivity";
    //根据设备的IMEI码获得指令单号
    public static String GetNowInsertMoNo(Context context, String asmxURL, String Methoname, String paramsKey, String paramsValue) {
        try {
            return GetWebServiceComnon(context, asmxURL, Methoname, paramsKey, paramsValue);
        } catch (Exception ex) {
            //Log.i(TAG, "异常:"+ex.getMessage());
            ex.printStackTrace();
        }
        return null;

    }

    //根据指令单号获得其对应的报表明细
    public static String GetMoReportByMoNo(Context context, String asmxURL, String Methoname, String paramsKey, String paramsValue) {
        try {
            return GetWebServiceComnon(context, asmxURL, Methoname, paramsKey, paramsValue);
        } catch (Exception ex) {
            //Log.i(TAG, "异常:"+ex.getMessage());
            ex.printStackTrace();
        }
        return null;

    }

    public static String GetWebServiceComnon(Context context, String asmxURL, String Methoname, String paramsKey, String paramsValue) {
        try {
            String params = "";
            if (!paramsKey.equals(""))
                params = paramsKey + "=" + paramsValue;//设置发送的参数
            return GetWebServiceParamsComnon(context, asmxURL, Methoname, params);
        } catch (Exception ex) {
            //Log.i(TAG, "异常:"+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public static String GetWebServiceParamsComnon(Context context, String asmxURL, String Methoname, String params) {
        HttpURLConnection conn = null;
        byte[] entity = params.getBytes();
        try {
            conn = (HttpURLConnection) new URL(asmxURL + "/" + Methoname).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(120000);
            conn.setReadTimeout(60000);
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
            conn.getOutputStream().write(entity);
            conn.connect();
            //System.out.println(conn.getResponseCode());
            int ResponseCode = conn.getResponseCode();
            if (ResponseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                Looper.prepare();
                new Dialog(context).ShowAlertDialog("提示", "WebService服务端有异常，请检查网络或联系系统管理员");
                Looper.loop();
            } else if (ResponseCode == HttpURLConnection.HTTP_OK) {
                //SystemClock.sleep(1000);//线程等待5秒
                String result = getByteArrayFromInputStream(conn.getInputStream());
                return result;
            }
        } catch (Exception ex) {
            Log.i(TAG, "异常:" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;

    }

    //Pull解析返回的XML数据
    public static String getByteArrayFromInputStream(InputStream ism)
            throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(ism, "utf-8");
        return getByteArrayFromXML(parser);
    }

    //Pull解析返回的XML数据
    public static String getByteArrayFromContent(String xmlContent)
            throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new StringReader(xmlContent));
        return getByteArrayFromXML(parser);
    }

    //Pull解析返回的XML数据
    public static String getByteArrayFromXML(XmlPullParser parser)
            throws Exception {
        String result = "";
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_TAG:
                    if ("string".equals(parser.getName())) {
                        result += parser.nextText();
                    }
                    break;
            }
            event = parser.next();
        }
        return result;
    }
    // 普通Json数据解析
    public static JSONObject GetJSONObject(String strResult) {
        try {
            JSONObject jsonObj = new JSONObject(strResult).getJSONObject("result");
            return jsonObj;
        } catch (JSONException e) {
            System.out.println("Json parse error");
            e.printStackTrace();
            return null;
        }
    }
    // 登录返回数据的Json数据解析
    public static String parseLoginResultJson(UserInfo userInfo, String strResult) {
        try {
            JSONObject jsonObj =GetJSONObject(strResult);// new JSONObject(strResult).getJSONObject("result");
            String Result = jsonObj.getString("Result");
            userInfo.UserID = jsonObj.getString("UserID");
            return Result;
        } catch (JSONException e) {
            System.out.println("Json parse error");
            e.printStackTrace();
            return "Json parse error";
        }
    }
    // 生成入库单，领料单或者暂收单后返回数据的Json数据解析
    public static String parseStockResultJson(UserInfo userInfo, String strResult) {
        try {
            JSONObject jsonObj =GetJSONObject(strResult);// new JSONObject(strResult).getJSONObject("result");
            String Result = jsonObj.getString("Result");
            userInfo.StockBillNO = jsonObj.getString("StockBillNO");
            return Result;
        } catch (JSONException e) {
            System.out.println("Json parse error");
            e.printStackTrace();
            return "error";
        }
    }

    // 直接解析Json数据
    public static String parseResultJson(String strResult,String StockBillNO) {
        try {
            JSONObject jsonObj =GetJSONObject(strResult);// new JSONObject(strResult).getJSONObject("result");
            String Result = jsonObj.getString("Result");
            StockBillNO= jsonObj.getString("StockBillNO");
            return Result;
        } catch (JSONException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
