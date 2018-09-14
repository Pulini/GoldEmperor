package com.goldemperor.Utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : ApkTool
 * Created by : PanZX on  2018/6/26 14:58
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：获取设备中所有已安装的APP信息
 */
public class ApkTool {

    public static List<MyAppInfo> scanLocalInstallAppList(PackageManager packageManager) {
        List<MyAppInfo> myAppInfos = new ArrayList();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            LOG.e("packageInfos="+packageInfos.size());
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i); //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }
                MyAppInfo myAppInfo = new MyAppInfo();
                myAppInfo.setPackageName(packageInfo.packageName);
                myAppInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                myAppInfo.setImage(packageInfo.applicationInfo.loadIcon(packageManager));
                myAppInfos.add(myAppInfo);
            }
            LOG.e("myAppInfos="+myAppInfos.size());
        } catch (Exception e) {
            LOG.e("===============获取应用包信息失败");
        }
        return myAppInfos;
    }
}



