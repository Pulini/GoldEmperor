package com.goldemperor.ShowCapacity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;


import com.goldemperor.ShowCapacity.model.MessageEvent;
import com.goldemperor.Utils.LOG;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * File Name : MyJPushReceiver
 * Created by : PanZX on  2018/10/12 13:46
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class MyJPushReceiver extends BroadcastReceiver {

    public static final String TypeRefresh="TypeRefresh";
    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();
        LOG.e("推送" + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LOG.e("JPush 用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LOG.e("接受到推送下来的自定义消息");
            EventBus.getDefault().post(new MessageEvent(TypeRefresh,bundle.getString(JPushInterface.EXTRA_MESSAGE)));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LOG.e("接受到推送下来的通知");
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LOG.e("用户点击打开了通知");
        } else {
            LOG.e("Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {

        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LOG.e(" title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        LOG.e("message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LOG.e("extras : " + extras);
        Toast.makeText(context, "title:" + title + "\n+message:" + message + "\nextras:" + extras, Toast.LENGTH_LONG).show();
//        LemonHello.getWarningHello("推送", "title:"+title+"\n+message:"+message+"\nextras:"+extras)
//                .addAction(new LemonHelloAction("确定", (helloView, helloInfo, helloAction) ->helloView.hide()))
//                .show(context);


    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        LOG.e("推送_MSG:" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        EventBus.getDefault().post(new MessageEvent("JP",bundle.getString(JPushInterface.EXTRA_MESSAGE)));
        LOG.e("推送_EXTRA:" + bundle.getString(JPushInterface.EXTRA_EXTRA));
        if (!TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
            try {
                JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                Iterator<String> it = json.keys();
                while (it.hasNext()) {
                    String myKey = it.next();
                    LOG.e("推送自定义消息:"+myKey + " - " + json.optString(myKey));
                }
            } catch (JSONException e) {
                LOG.e("Get message extra JSON error!");
            }
        }
        for (String key : bundle.keySet()) {

            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LOG.e("This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LOG.e("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }


    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("myKey");
        } catch (Exception e) {
            LOG.e("Unexpected: extras is not a valid json" + e);
            return;
        }

    }


}