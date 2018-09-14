package com.goldemperor.Update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

/**
 * Created by xufanglou on 2016-08-27.
 */
public class VersionService {
    public Handler myHandler;
    public Context myContext;
    public VersionService(Handler handler, Context context)
    {
        myHandler = handler;
        myContext = context;
    }

    /*
    * 获取当前程序的版本号
    */
    private static final String TAG = "Config";
    public static final String appPackName = "com.updateapp";

    public static int getVersionCode(Context context) {//throws PackageManager.NameNotFoundException
        int verCode = -1;
        try {
//            verCode = context.getPackageManager().getPackageInfo(appPackName, 0).versionCode;
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return verCode;
    }
    /*
     * 获取当前程序的版本号
     */
    public static String getVersionName(Context context){
        String verName = "";
        try {
            //获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            verName= packInfo.versionName;
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return verName;
    }

    public static String getAppName(Context context) {
        String appName = "金帝之家";
        return appName;
    }
}
