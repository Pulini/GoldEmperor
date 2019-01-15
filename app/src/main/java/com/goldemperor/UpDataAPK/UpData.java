package com.goldemperor.UpDataAPK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.goldemperor.MainActivity.define;
import com.goldemperor.Update.VersionService;
import com.goldemperor.Utils.HttpUtils;
import com.goldemperor.Utils.LOG;
import com.google.gson.Gson;

import org.xutils.http.RequestParams;

import java.io.File;

/**
 * File Name : UpData
 * Created by : PanZX on  2018/12/8 14:06
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class UpData {
    private final static String name = define.SERVER + define.PORT_8020 + define.UpdateXML;
    private static boolean isRuning=false;
    public static void checkVersion(Activity act) {
        if(isRuning){
            return;
        }
        isRuning=true;
        RequestParams RP = new RequestParams(name);
        HttpUtils.get(RP, (Finish, result) -> {
            if (Finish.equals(HttpUtils.Success)) {
                LOG.e("result=" + result);
                VersionModel PWCI = new Gson().fromJson(result, VersionModel.class);
                LOG.e("OldVersion=" + VersionService.getVersionCode(act)+"   NewVersion="+PWCI.getVersionCode());
                if (VersionService.getVersionCode(act)<PWCI.getVersionCode()) {
                    new AlertDialog.Builder(act)
                            .setTitle("更新提示")
                            .setCancelable(false)
                            .setMessage(PWCI.getDescription())
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();
                                ShowDownload(act, PWCI.getUrl());
                            }).setNegativeButton("关闭", (dialog, which) -> {
                        if (PWCI.isForce()) {
                            System.exit(0);
                        } else {
                            isRuning=false;
                            dialog.dismiss();
                        }
                    }).show();
                }
            } else {

            }
        });
    }

    /**
     * 下载工艺指导书
     */
    private static void ShowDownload(Activity act, String path) {
        //创建进度条对话框
        ProgressDialog progressDialog = new ProgressDialog(act);
        //设置标题
        progressDialog.setTitle("下载指导书");
        //设置显示的格式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        final String name = "GoldEmperor.apk";
        String name = path.substring(path.lastIndexOf("/")+1, path.length());
        HttpUtils.Download(name, path, new HttpUtils.HttpDownload() {
            @Override
            public void Start() {
                progressDialog.setMessage("正在玩命下载" + name + "中...");
                progressDialog.show();
            }

            @Override
            public void Error(String msg) {
                progressDialog.dismiss();
                new AlertDialog.Builder(act)
                        .setTitle("下载失败")
                        .setCancelable(false)
                        .setPositiveButton("确定", null)
                        .show();
            }

            @Override
            public void Success(final File f) {
                progressDialog.dismiss();
                isRuning=false;
                installApk(act, f);
            }

            @Override
            public void Loading(int Progress) {
                progressDialog.setProgress(Progress);
            }
        });
    }

    //安装apk
    private static void installApk(Activity act, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(act, "com.goldemperor.fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            //执行的数据类型
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        act.startActivity(intent);
    }
}
