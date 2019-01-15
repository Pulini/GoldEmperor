package com.goldemperor.MainActivity;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

import com.goldemperor.MainActivity.NewHome.Model.NewLoginModel;
import com.goldemperor.MainActivity.NewHome.Pad.HomeForPadActivity;
import com.goldemperor.MainActivity.NewHome.TV.HomeForTVActivity;
import com.goldemperor.MainActivity.NewHome.Phone.HomeForPhoneActivity;
import com.goldemperor.Public.SystemUtil;
import com.goldemperor.R;
import com.goldemperor.Update.CheckVersionTask;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import static cn.jpush.android.api.JPushInterface.stopPush;

public class MainActivity extends Activity {

    String SystemModel = SystemUtil.getSystemModel();
    private Context mContext;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        stopPush(getApplicationContext());
        mContext = this;
//        intent = new Intent(mContext, HomeForTVActivity.class);
        if (SystemModel.equals("CMR-W09") || SystemModel.equals("FDR-A01w")|| SystemModel.equals("BAH-W09")|| SystemModel.equals("BAH-AL00")) {
            intent = new Intent(mContext, HomeForPadActivity.class);
        } else if (SystemModel.equals("3280") || SystemModel.equals("rk3288")|| SystemModel.equals("ZC-83A")|| SystemModel.equals("S55CA-GC8351C-M")) {
            intent = new Intent(mContext, HomeForTVActivity.class);
        } else {
            intent = new Intent(mContext, HomeForPhoneActivity.class);
        }
        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 1500);
    }
    private void GetDeviceType() {
        HashMap<String, String> map = new HashMap<>();
//        map.put("accountSuitID", accountSuitID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(SPUtils.getServerPath() + define.ErpForAppServer,
                define.NewPhoneLogin,
                map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("登录=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                JSONArray ja = new JSONArray(ReturnMsg);
                                jsonObject = ja.getJSONObject(0);
//                                NewLoginModel NLM = mGson.fromJson(jsonObject.toString(), NewLoginModel.class);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();

                        }
                    } else {

                    }

                });
    }


}
