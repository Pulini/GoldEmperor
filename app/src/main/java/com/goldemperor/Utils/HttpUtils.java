package com.goldemperor.Utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;

import com.goldemperor.GylxActivity.GylxActivity;
import com.goldemperor.MainActivity.MyApplication;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;
import com.goldemperor.model.ProcessSendModel;


import org.xmlpull.v1.XmlPullParser;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * File Name : HttpUtils
 * Created by : PanZX on  2018/4/25 11:08
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：Http工具类
 */
public class HttpUtils{
    public static final String Success="onSuccess";
    public static final String Error="onError";
    public static final String Cancelled="onCancelled";
    public static final String Finished="onFinished";
    public static void post(RequestParams RP, final httpcallback httpcallback) {

        x.http().post(RP, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                httpcallback.postcallback(Success,result);
                LOG.e("onSuccess:" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                httpcallback.postcallback(Error,"");
                LOG.e("onError:" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                httpcallback.postcallback(Cancelled,"");
                LOG.e("onCancelled:" + cex.toString());
            }

            @Override
            public void onFinished() {
                httpcallback.postcallback(Finished,"");
                LOG.e("onFinished");
            }
        });
    }
public interface OnCheckPermissionsListener{
       void PermissionsResult(Boolean r);
}

    public static void CheckPermissions( final Activity act, final String controlID,final String userid,final  OnCheckPermissionsListener PR) {
        RequestParams params = new RequestParams(define.Net2 + define.IsHaveUserControl);

        params.addQueryStringParameter("OrganizeID", "1");
        params.addQueryStringParameter("UserID",userid);
        params.addQueryStringParameter("controlID", controlID);
        LOG.e("params="+params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                if (result.contains("false")) {
                    PR.PermissionsResult(false);

                } else if (result.contains("true")) {
                    PR.PermissionsResult(true);
                } else {

                    LemonHello.getErrorHello("提示", "服务器返回失败")
                            .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                }
                            }))
                            .show(act);
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                PR.PermissionsResult(false);

                LemonHello.getErrorHello("提示", "网络错误")
                        .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                            }
                        }))
                        .show(act);

            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }


    public static void get(RequestParams RP, final httpcallback httpcallback) {

        x.http().get(RP, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                httpcallback.postcallback(Success,result);
                LOG.e("onSuccess:" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                httpcallback.postcallback(Error,"");
                LOG.e("onError:" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                httpcallback.postcallback(Cancelled,"");
                LOG.e("onCancelled:" + cex.toString());
            }

            @Override
            public void onFinished() {
                httpcallback.postcallback(Finished,"");
                LOG.e("onFinished");
            }
        });
    }


    public  interface httpcallback{
          void postcallback(String Finish, String paramString);
    }
}
