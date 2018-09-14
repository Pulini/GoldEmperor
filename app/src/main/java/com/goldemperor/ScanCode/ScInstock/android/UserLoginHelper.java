package com.goldemperor.ScanCode.ScInstock.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.goldemperor.ScanCode.ScInstock.model.UserLoginInfo;
import com.goldemperor.Utils.LOG;


/**
 * Created by xufanglou on 2016-08-13.
 */
public class UserLoginHelper {
    private static UserLoginHelper userLoginHelper = null;
    public Handler myHandler;
    public Context myContext;
    public UserLoginInfo myuserLoginInfo;
    public UserLoginHelper(Handler handler, Context context, UserLoginInfo userLoginInfo){
        myHandler = handler;
        myContext = context;
        myuserLoginInfo=userLoginInfo;
        IniLoginFormInfo();
        userLoginHelper=this;
    }
    //首先初始化登录窗体信息
    public void IniLoginFormInfo()
    {
        LOG.e("------------IniLoginFormInfo------------");
        StockBarCodeService Athread = new StockBarCodeService(myHandler, myContext,this);
        Athread.start();
    }

    /**
     * 保存用户登陆信息
     * @param context
     * @param username
     * @param password
     */
    public void saveLoginInfo(Context context, String username, String password) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString("username", username)
                .putString("username", password)
                .commit();
    }
    /**
     * 返回用户名，没有保存的话返回空字符串
     *
     * @param context
     * @return
     */
    public String getUsername(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString("username", "");
    }

    /**
     * 返回密码，没有保存的话返回空字符串
     *
     * @param context
     * @return
     */
    public String getPassword(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString("password", "");
    }
    /**
     * 判断是否有网络
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
