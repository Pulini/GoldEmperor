package com.goldemperor.ScanCode.Share;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.goldemperor.Utils.LOG;

/**
 * File Name : ScanReceiver
 * Created by : PanZX on  2018/9/29 09:48
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：扫码广播接收器
 */
public class ScanReceiver extends BroadcastReceiver {

    private static ScanListener SL;
    private static ScanReceiver SR;


    public ScanReceiver(ScanListener scanlistener) {
        LOG.e("注册");
        SL = scanlistener;
    }

    public static void RegisterReceiver(Activity activity, ScanListener scanlistener) {
        LOG.e("注册广播");
        SR = new ScanReceiver(scanlistener);
        activity.registerReceiver(SR, new IntentFilter(ScanUtil.ScanReceiverID));
    }


    public static void unRegisterReceiver(Activity activity) {
        LOG.e("注销广播");
        activity.unregisterReceiver(SR);
        SR = null;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        LOG.e("接收广播");
        final String scanResult_1 = intent.getStringExtra("SCAN_BARCODE1");
        final String scanStatus = intent.getStringExtra("SCAN_STATE");
        if ("ok".equals(scanStatus)) {
            //成功
            SL.CodeResult(scanResult_1);
        } else {
            //失败如超时等
        }
    }
}
