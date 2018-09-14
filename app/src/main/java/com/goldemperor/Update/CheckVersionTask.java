package com.goldemperor.Update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;


import com.goldemperor.MainActivity.define;
import com.goldemperor.model.UpdataInfo;

import java.io.File;
import java.io.InputStream;


/**
 * Created by xufanglou on 2016-08-27.
 */
    /*
     * 从服务器获取xml解析并进行比对版本号
	 */
public class CheckVersionTask implements Runnable {
    protected static final String TAG = "ScReportActivity";
    public Activity act;
    public UpdataInfo myinfo;

    public CheckVersionTask(Activity act) {
        this.act = act;
    }

    public void run() {
        try {
            new Thread() {
                @Override
                public void run() {
                    //从资源文件获取服务器update.xml地址
                    String path = define.Net3+define.UpdateXML;
                    //包装成url的对象
                    InputStream stream = HttpUtilHelper.GetInputStreamFromURL(path);
//            StringBuilder sb= new StringBuilder("");
//
//
//            URL url = new URL(path);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5000);
//            InputStream is = conn.getInputStream();

                    try {
                        myinfo = UpdataInfoParser.getUpdataInfo(stream);//获得服务器上的版本号
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    int ServerVersionCode = VersionService.getVersionCode(act.getBaseContext());//获得服务器的版本号
                    if (myinfo != null) {
                        String Version = myinfo.getVersion();
                        //int NowversionCode = Integer.parseInt(Version);//本地的版本号
                        String ServerVersionName = VersionService.getVersionName(act.getBaseContext());
                        Log.e("jindi", ServerVersionName + "," + Version);
//            if (myinfo.getVersion().equals(versionCode)) {
                        if (!ServerVersionName.equals(Version)) {
                            Log.i(TAG, "服务器版本号大于本地版本号 ,提示用户升级 ");
                            showUpdataDialog(myinfo);

                        } else {
                            Log.i(TAG, "已经是最新版");
                        }
                    }
                }
            }.start();
        } catch (Exception e) {
            // 待处理
            Toast.makeText(act.getBaseContext(), "获取更新信息失败", Toast.LENGTH_LONG).show();
            Log.e("jindi", e.toString());
        }
    }


    /*
      *
      * 弹出对话框通知用户更新程序
      *
      * 弹出对话框的步骤：
      * 	1.创建alertDialog的builder.
      *	2.要给builder设置属性, 对话框的内容,样式,按钮
      *	3.通过builder 创建一个对话框
      *	4.对话框show()出来
      */
    protected void showUpdataDialog(final UpdataInfo myinfo) {


        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(act);
                normalDialog.setTitle("更新提示");
                normalDialog.setCancelable(false);
                normalDialog.setMessage(myinfo.getDescription());
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                                Log.i(TAG, "下载apk,更新");
                                downLoadApk(myinfo);
                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                                //...To-do
                            }
                        });
                // 显示
                normalDialog.show();

            }
        });

    }

    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk(final UpdataInfo myinfo) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final ProgressDialog pd;    //进度条对话框
                pd = new ProgressDialog(act);
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setMessage("正在下载更新");
                pd.setCancelable(false);
                pd.show();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String path = myinfo.getUrl();//getResources().getString(R.string.updateapkurl);
                            File file = DownLoadManager.getFileFromServer(path, pd, act);
                            installApk(file);
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss(); //结束掉进度条对话框
                                    System.exit(0);
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(act, "下载新版本失败", Toast.LENGTH_LONG).show();
                        }
                    }
                }.start();
            }
        });
    }

    //安装apk
    protected void installApk(File file) {
        Intent intent =new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(act, "com.goldemperor.fileprovider",file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            //执行的数据类型
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        act.startActivity(intent);
    }
}

