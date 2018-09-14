package com.goldemperor.ScanCode.ScInstock.android;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 获得设备的IP，及mac地址
 * Created by xufanglou on 2016-07-14.
 */
public class IPClass {
    private Activity myActivity;

    public IPClass(Activity myActivity) {
        this.myActivity = myActivity;
    }

    /*
     Wifi网络环境下获取ip的方法
    */
    public String getWifiIpAddress() {
        //获取 Wifi IP的方法
        WifiManager wifiManager = (WifiManager) myActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled() && wifiManager.getWifiState() == wifiManager.WIFI_STATE_ENABLED) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                int ipAddress = wifiInfo.getIpAddress();
                if (ipAddress == 0)
                    return "";
                return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "." + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
            }
        }
        return "0";
    }

    /*
     3G网络环境下获取ip的方法
    */
    public String get3GIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = en.nextElement();
                Enumeration<InetAddress> enIp = ni.getInetAddresses();
                while (enIp.hasMoreElements()) {
                    InetAddress inet = enIp.nextElement();
                    if (!inet.isLoopbackAddress()
                            && (inet instanceof Inet4Address)) {
                        return inet.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "0";
    }

    /*
     获得设备mac地址
     */
    public String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) myActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


}
