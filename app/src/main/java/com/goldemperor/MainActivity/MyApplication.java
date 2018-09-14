package com.goldemperor.MainActivity;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.goldemperor.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
//import com.umeng.commonsdk.UMConfigure;


import org.xutils.x;

import java.util.List;


/**
 * Created by Administrator on 2017/7/3.
 */

public class MyApplication extends Application {

    // 使用自己APP的ID（官网注册的）
    private static final String APP_ID = "2882303761517600169";
    // 使用自己APP的Key（官网注册的）
    private static final String APP_KEY = "5701760073169";
    private static final String Umeng_KEY = "5b31ecf8f29d9862c6000014";

    public static final String TAG = "xiaomipush";

    public static Context context;
    private static MyApplication instance;
    public static Application getInstance() {
        return instance;
    }
    public static Context getContext() {
        return context;
    }
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.gray);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        UMConfigure.init(getApplicationContext(), Umeng_KEY, "UMENG_CHANNEL", UMConfigure.DEVICE_TYPE_PHONE, null);
        CrashReport.initCrashReport(getApplicationContext(), "5920ccce50", false);
        x.Ext.init(this);
        context=this;
        instance = this;


    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
