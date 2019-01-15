package com.goldemperor.Utils;

import android.app.Activity;
import android.os.Environment;

import com.goldemperor.MainActivity.define;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;


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
        RequestParams params = new RequestParams(SPUtils.getServerPath() + define.IsHaveUserControl);

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
//                LOG.e("onSuccess:" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                httpcallback.postcallback(Error,"");
//                LOG.e("onError:" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                httpcallback.postcallback(Cancelled,"");
//                LOG.e("onCancelled:" + cex.toString());
            }

            @Override
            public void onFinished() {
                httpcallback.postcallback(Finished,"");
//                LOG.e("onFinished");
            }
        });
    }

    public static void Download(String name, String path, final HttpDownload HD) {
        LOG.e("name=" + name);
        LOG.e("path=" + path);
        File file = new File(GetSDPath() + "/GoldEmperor");
        if (!file.exists()) {
            file.mkdirs();
        }
        String SavePath = file.getPath() + File.separatorChar + name;
        LOG.e("SavePath=" + SavePath);
        //设置请求参数
        RequestParams params = new RequestParams(path);
        params.setAutoResume(true);//设置是否在下载是自动断点续传
        params.setAutoRename(false);//设置是否根据头信息自动命名文件
        params.setSaveFilePath(SavePath);
        params.setExecutor(new PriorityExecutor(2, true));//自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
        params.setCancelFast(true);//是否可以被立即停止.
        //下面的回调都是在主线程中运行的,这里设置的带进度的回调
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onCancelled(CancelledException arg0) {
                LOG.e("取消" + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                LOG.e("onError: 失败" + arg0.toString());
                HD.Error(arg0.toString());
            }

            @Override
            public void onFinished() {
                LOG.e("完成,每次取消下载也会执行该方法" + Thread.currentThread().getName());
            }

            @Override
            public void onSuccess(File arg0) {
                LOG.e("下载成功的时候执行" + Thread.currentThread().getName());
                HD.Success(arg0);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading) {
                    LOG.e("下载中,会不断的进行回调:" + Thread.currentThread().getName());
                    HD.Loading((int) (current * 100 / total));
                }
            }

            @Override
            public void onStarted() {
                LOG.e("开始下载的时候执行" + Thread.currentThread().getName());
                HD.Start();
            }

            @Override
            public void onWaiting() {
                LOG.e("等待,在onStarted方法之前执行" + Thread.currentThread().getName());
            }

        });
    }

    public static String GetSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        LOG.e("SD卡路径："+sdDir.toString());
        return sdDir.toString();
    }
    public interface HttpDownload {
        void Start();
        void Error(String msg);
        void Success(File f);
        void Loading(int Progress);
    }
    public  interface httpcallback{
          void postcallback(String Finish, String paramString);
    }
}
