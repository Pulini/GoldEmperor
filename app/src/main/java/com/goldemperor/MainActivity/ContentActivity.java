package com.goldemperor.MainActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.Banner.DataProvider;
import com.goldemperor.Banner.SimpleImageBanner;
import com.goldemperor.Banner.SimpleTextBanner;
import com.goldemperor.Banner.ViewFindUtils;
import com.goldemperor.CCActivity.CCListActivity;
import com.goldemperor.ScanCode.CxStockIn.android.NetworkHelper;
import com.goldemperor.DayWorkCardReport.activity.SCCJLCCLXS_ReportActivity;
import com.goldemperor.GxReport.GxReport;
import com.goldemperor.GylxActivity.GylxActivity;
import com.goldemperor.LoginActivity.LoginActivity;
import com.goldemperor.PgdActivity.PgdActivity;
import com.goldemperor.ScanCode.New_SC_Report.NewScReportActivity;
import com.goldemperor.ScanCode.ProcessReportInstock.ProcessReportInstockActivity;
import com.goldemperor.ProcessSend.ProcessSendActvity;
import com.goldemperor.Public.SystemUtil;
import com.goldemperor.SetActivity.SetActivity;
import com.goldemperor.R;
import com.goldemperor.StaffWorkStatistics.StaffWorkStatisticsActivity;
import com.goldemperor.StockCheck.StockCheckActivity;
import com.goldemperor.Update.CheckVersionTask;
import com.goldemperor.Update.VersionService;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.ScanCode.WarehouseAllocation.WarehouseAllocationActivity;
import com.goldemperor.Widget.SuperDialog;
import com.goldemperor.Widget.banner.anim.select.ZoomInEnter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tapadoo.alerter.Alerter;
//import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.goldemperor.ScanCode.CxStockIn.CxStockInActivity.IsNeedCheckVersion;


/**
 * Created by Nova on 2017/7/25.
 */

public class ContentActivity extends AppCompatActivity {

    private FancyButton chenckBtn;

    private FancyButton orderBtn;
    private FancyButton processBtn;
    private FancyButton btn_pzgl;


    private FancyButton btn_cxstockin;

    private FancyButton btn_scstockin;

    private FancyButton btn_process_sc;

    private FancyButton btn_cxscanbarcode;

    private FancyButton btn_supperinstock;

    private FancyButton btn_cc;

    private FancyButton btn_gylx;

    private FancyButton setBtn;


    private FancyButton btn_xjdcheck;

    private FancyButton btn_gxjhpg;

    private FancyButton btn_day_work;

    private FancyButton btn_help;
    private FancyButton btn_warehouse_allocation;
    private FancyButton btn_WorkStatistics;
    private FancyButton btn_ProcessReportInstock;
    private FancyButton btn_ProcessInformation;

    private Button waiBtn;

    private Button neiBtn;

    private Button ceBtn;

    private TextView netStatus;
    private TextView version;

    private Context mContext;
    private Activity act;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    String SystemModel = SystemUtil.getSystemModel();
    private FancyButton btn_SizeBarCode;
    private SuperDialog superDialog;
    private ArrayList<SuperDialog.DialogMenuItem> menuItems;
    private SuperDialog helpDialog;
    private ArrayList<SuperDialog.DialogMenuItem> helpItems;
    Gson mGson;
    private List<HelpModel> HML = new ArrayList<>();
    private boolean isGetJurisdiction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_content);
        //隐藏标题栏
        getSupportActionBar().hide();
        mGson = new Gson();

        mContext = this;
        act = this;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();

        Map<String, String> map = new HashMap<>();
        String User = dataPref.getString(define.SharedUser, "");
//        List<MyAppInfo> AppInfoList = ApkTool.scanLocalInstallAppList(getPackageManager());
//        map.put("UserName", User);
//        for (MyAppInfo myAppInfo : AppInfoList) {
//            LOG.e("AppName:" + myAppInfo.getAppName());
//            map.put(myAppInfo.getAppName(), myAppInfo.getPackageName());
//        }
//        MobclickAgent.onEvent(this, "AppList", new Gson().toJson(map));

        LogToFile.init(this);
        //LogToFile.e("test","写入成功");
        SimpleImageBanner sib = ViewFindUtils.find(getWindow().getDecorView(), R.id.sib);
        sib.setSelectAnimClass(ZoomInEnter.class)
                .setSource(DataProvider.getList())
                .startScroll();
        sib.setOnItemClickL(new SimpleImageBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(mContext, "positon:" + position, Toast.LENGTH_LONG).show();
            }
        });

        SimpleTextBanner stb = ViewFindUtils.find(getWindow().getDecorView(), R.id.stb);

        ArrayList<String> titles = new ArrayList<>();
        for (String title : DataProvider.text) {
            titles.add(title);
        }
        stb
                .setSource(titles)
                .startScroll();

        stb.setOnItemClickL(new SimpleImageBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(mContext, "positon:" + position, Toast.LENGTH_LONG).show();
            }
        });


        chenckBtn = (FancyButton) findViewById(R.id.btn_check);
        chenckBtn.setIconResource(R.drawable.btn_material);
        chenckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, StockCheckActivity.class);
                mContext.startActivity(i);
            }
        });

        processBtn = (FancyButton) findViewById(R.id.btn_process);
        processBtn.setIconResource(R.drawable.btn_process);

        btn_pzgl = (FancyButton) findViewById(R.id.btn_pzgl);
        btn_pzgl.setIconResource(R.drawable.btn_produce);

        orderBtn = (FancyButton) findViewById(R.id.btn_order);
        orderBtn.setIconResource(R.drawable.btn_order);

        btn_process_sc = (FancyButton) findViewById(R.id.btn_process_sc);
        btn_process_sc.setIconResource(R.drawable.btn_query);

        btn_cxstockin = (FancyButton) findViewById(R.id.btn_cxstockin);
        btn_cxstockin.setIconResource(R.drawable.btn_saoyisao);


        btn_scstockin = (FancyButton) findViewById(R.id.btn_scstockin);
        btn_scstockin.setIconResource(R.drawable.btn_set);

        btn_cxscanbarcode = (FancyButton) findViewById(R.id.btn_cxscanbarcode);
        btn_cxscanbarcode.setIconResource(R.drawable.btn_set);

        btn_supperinstock = (FancyButton) findViewById(R.id.btn_supperinstock);
        btn_supperinstock.setIconResource(R.drawable.btn_set);

        btn_xjdcheck = (FancyButton) findViewById(R.id.btn_xjdcheck);
        btn_xjdcheck.setIconResource(R.drawable.btn_set);

        btn_gxjhpg = (FancyButton) findViewById(R.id.btn_gxjhpg);
        btn_gxjhpg.setIconResource(R.drawable.btn_set);

        btn_day_work = (FancyButton) findViewById(R.id.btn_day_work);
        btn_day_work.setIconResource(R.drawable.btn_order);

        btn_cc = (FancyButton) findViewById(R.id.btn_cc);
        btn_cc.setIconResource(R.drawable.btn_set);

        btn_help = (FancyButton) findViewById(R.id.btn_help);
        btn_help.setIconResource(R.drawable.btn_order);

        setBtn = (FancyButton) findViewById(R.id.btn_set);

        setBtn.setIconResource(R.drawable.btn_set);

        btn_gylx = (FancyButton) findViewById(R.id.btn_gylx);
        btn_gylx.setIconResource(R.drawable.btn_set);

        btn_WorkStatistics = (FancyButton) findViewById(R.id.btn_WorkStatistics);
        btn_WorkStatistics.setIconResource(R.drawable.btn_order);


        btn_warehouse_allocation = (FancyButton) findViewById(R.id.btn_warehouse_allocation);
        btn_warehouse_allocation.setIconResource(R.drawable.btn_order);

        btn_ProcessReportInstock = (FancyButton) findViewById(R.id.btn_ProcessReportInstock);
        btn_ProcessReportInstock.setIconResource(R.drawable.btn_set);

        btn_ProcessInformation = (FancyButton) findViewById(R.id.btn_ProcessInformation);
        btn_ProcessInformation.setIconResource(R.drawable.btn_order);

//        btn_SizeBarCode = (FancyButton) findViewById(R.id.btn_SizeBarCode);
//        btn_SizeBarCode.setIconResource(R.drawable.btn_order);
//
//
//        btn_SizeBarCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
        btn_gylx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(mContext, GylxActivity.class);
//                mContext.startActivity(i);

                getControl("401040304");
            }
        });

        btn_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, CCListActivity.class);
                mContext.startActivity(i);

            }
        });

        processBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                } else {
                    Intent i = new Intent(mContext, GxReport.class);
                    mContext.startActivity(i);
                }
            }
        });


        btn_process_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SystemModel = SystemUtil.getSystemModel();
                Log.e("jindi", "手机型号：" + SystemModel);
                if (SystemModel.equals("MT65") || SystemModel.equals("NLS-MT66") || SystemModel.equals("NLS-MT90")) {
                    Intent i = new Intent(mContext, com.goldemperor.ScanCode.ScReport.ScReportActivity.class);
//                    Intent i = new Intent(mContext, NewScReportActivity.class);
                    mContext.startActivity(i);
                } else {

                    getControl("1050501");
                }
            }
        });


        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                } else {
                    Intent i = new Intent(mContext, PgdActivity.class);
                    mContext.startActivity(i);
                }
            }
        });

        btn_cxstockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("jindi", "手机型号：" + SystemModel);
                if (SystemModel.equals("MT65") || SystemModel.equals("NLS-MT66") || SystemModel.equals("NLS-MT90")) {
                    Intent i = new Intent(mContext, com.goldemperor.ScanCode.CxStockIn.CxStockInActivity.class);
                    mContext.startActivity(i);
                } else {

                    getControl("1050101");
                }
            }
        });

        btn_supperinstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SystemModel = SystemUtil.getSystemModel();
                Log.e("jindi", "手机型号：" + SystemModel);
                if (SystemModel.equals("MT65") || SystemModel.equals("NLS-MT66") || SystemModel.equals("NLS-MT90")) {
                    Intent i = new Intent(mContext, com.goldemperor.ScanCode.SupperInstock.MainActivity.class);
                    mContext.startActivity(i);
                } else {

                    getControl("1050301");
                }
            }
        });
        btn_scstockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SystemModel = SystemUtil.getSystemModel();
                Log.e("jindi", "手机型号：" + SystemModel);

                if (SystemModel.equals("MT65") || SystemModel.equals("NLS-MT66") || SystemModel.equals("NLS-MT90")) {
                    Intent i = new Intent(mContext, com.goldemperor.ScanCode.ScInstock.MainActivity.class);
                    mContext.startActivity(i);
                } else {

                    getControl("1050201");
                }
            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                } else {
                    Intent i = new Intent(mContext, SetActivity.class);
                    mContext.startActivity(i);
                }

            }
        });
        btn_pzgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
                    Intent i = new Intent(mContext, com.goldemperor.PzActivity.PgdActivity.class);
                    mContext.startActivity(i);
                } else {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                }

            }
        });

        btn_xjdcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
                    Intent i = new Intent(mContext, com.goldemperor.XJChenk.XJListActivity.class);
                    mContext.startActivity(i);
                } else {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                }

            }
        });

        btn_gxjhpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
//                    Toast.makeText(mContext,"功能模块正在完善...",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(mContext, ProcessSendActvity.class);
                    mContext.startActivity(i);
                } else {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                }

            }
        });
        btn_day_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
//                    Toast.makeText(mContext,"功能模块正在完善...",Toast.LENGTH_LONG).show();
                    if (superDialog == null) {
                        showdialog();
                    }
                    superDialog.show();
//

                } else {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                }

            }
        });
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helpDialog != null) {
                    helpDialog.show();
                }
            }
        });
        btn_warehouse_allocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                } else {
                    getControl("1050601");
                }
            }
        });
        btn_WorkStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                } else {
                    getControl("303100101");
                }
            }
        });

        btn_ProcessReportInstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                } else {
                    getControl("1050701");
                }
            }
        });
        btn_ProcessInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                } else {
                    getControl("1050801");
                }
            }
        });

        netStatus = (TextView) findViewById(R.id.netStatus);
        netStatus.setText("当前网络:外网");
        define.isWaiNet = true;
        define.Net1 = define.IP1718341;
        define.Net2 = define.IP1718012;

        define.Net3 = define.IP1718020;
        define.Net4 = define.IP1718083;

        waiBtn = (Button) findViewById(R.id.btn_wai);
        waiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                define.isWaiNet = true;
                define.Net1 = define.IP1718341;
                define.Net2 = define.IP1718012;

                define.Net3 = define.IP1718020;
                define.Net4 = define.IP1718083;
                UpdataAPK();
                netStatus.setText("当前网络:外网");
            }
        });

        neiBtn = (Button) findViewById(R.id.btn_nei);
        neiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                define.isWaiNet = false;
                define.Net1 = define.IP798341;
                define.Net2 = define.IP798012;

                define.Net3 = define.IP798020;
                define.Net4 = define.IP798083;
                UpdataAPK();
                netStatus.setText("当前网络:内网");
            }
        });

        ceBtn = (Button) findViewById(R.id.btn_ce);
        ceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                define.isWaiNet = false;

                define.Net1 = define.IP799999;
                define.Net2 = define.IP798056;

                define.Net3 = define.IP798020;
                define.Net4 = define.IP798083;

                define.isCeNet = true;

                UpdataAPK();
                netStatus.setText("当前网络:测试库");
            }
        });

        if (SystemModel.equals("MT65") || SystemModel.equals("NLS-MT66")) {
            sib.setVisibility(View.GONE);
//            ceBtn.setVisibility(View.GONE);
        }

        UpdataAPK();
        version = (TextView) findViewById(R.id.version);
        version.setText("当前版本:" + VersionService.getVersionName(act.getBaseContext()));

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 201);

        if (Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED)) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 201);
            }

        }
        getHelp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 201:
                if (grantResults.length > 0) {
                    //确认权限
                    //处理自己的逻辑
                    //取消处理自己的逻辑
                } else {
                    //取消权限
                }
                break;
        }
    }

    private void showdialog() {
        superDialog = new SuperDialog(this);
        menuItems = new ArrayList<>();
        menuItems.add(new SuperDialog.DialogMenuItem("生产车间楼层产量实时汇总表"));
        menuItems.add(new SuperDialog.DialogMenuItem("其它报表正在制作中..."));
        superDialog.setTitle("选择报表类型")
                .setTitleTextSize(30)
                .setContentTextSize(23)
                .setContentTextGravity(Gravity.CENTER)
                .setListener(new SuperDialog.onDialogClickListener() {
                    @Override
                    public void click(boolean isButtonClick, int position) {
                        switch (position) {
                            case 0:
                                mContext.startActivity(new Intent(mContext, SCCJLCCLXS_ReportActivity.class));
                                break;
                            case 1:
                                mContext.startActivity(new Intent(mContext, AutographActivity.class));
                                break;
                        }
                    }
                })
                .setDialogMenuItemList(menuItems);
    }

    private void helpdialog() {

        helpDialog = new SuperDialog(this);
        helpItems = new ArrayList<>();
        for (HelpModel helpModel : HML) {
            helpItems.add(new SuperDialog.DialogMenuItem(helpModel.getFName()));
        }
        helpItems.add(new SuperDialog.DialogMenuItem("其它常见问题解答页面正在制作中..."));
        helpDialog.setTitle("常见问题解答选项")
                .setTitleTextSize(30)
                .setContentTextSize(23)
                .setContentTextGravity(Gravity.CENTER)
                .setListener(new SuperDialog.onDialogClickListener() {
                    @Override
                    public void click(boolean isButtonClick, int position) {
                        if (position == HML.size()) {
                            return;
                        }
                        Intent i = new Intent(ContentActivity.this, HelpActivity.class);
                        i.putExtra("URL", HML.get(position).getFHelpUrl());
                        startActivity(i);
                    }
                })
                .setDialogMenuItemList(helpItems);
    }

    private void UpdataAPK() {
        //如果有网络的情况下，apk更新
        if (IsNeedCheckVersion && NetworkHelper.isNetworkAvailable(this)) {
            new Thread() {
                @Override
                public void run() {
                    Log.e("jindi", "连接外网:" + define.isWaiNet);
                    CheckVersionTask myTask = new CheckVersionTask(act);
                    myTask.run();
                }
            }.start();
        }

    }

    private void getControl(final String controlID) {
        LOG.e(controlID + "isGetJurisdictio=" + isGetJurisdiction);
        if (!isGetJurisdiction) {
            isGetJurisdiction = true;
        } else {
            Toast.makeText(this, "正在检查权限", Toast.LENGTH_LONG).show();
            return;
        }
        RequestParams params = new RequestParams(define.Net2 + define.IsHaveControl);
        params.addQueryStringParameter("OrganizeID", "1");
        params.addQueryStringParameter("empID", dataPref.getString(define.SharedEmpId, define.NONE));
        params.addQueryStringParameter("controlID", controlID);
        LOG.e("检查权限=" + params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                LOG.e("权限=" + result);
                if (result.contains("false")) {
                    Alerter.create(act)
                            .setTitle("提示")
                            .setText("你没有权限,请联系管理员开通权限")
                            .setBackgroundColorRes(R.color.colorAlert)
                            .show();
                } else if (result.contains("true")) {
                    if (controlID.equals("1050101")) {
                        startActivity(new Intent(mContext, com.goldemperor.ScanCode.CxStockIn.CxStockInActivity.class));
                    } else if (controlID.equals("1050501")) {
                        startActivity(new Intent(mContext, com.goldemperor.ScanCode.ScReport.ScReportActivity.class));
//                        Intent i = new Intent(mContext, NewScReportActivity.class);
                    } else if (controlID.equals("401040304")) {
                        startActivity(new Intent(mContext, GylxActivity.class));
                    } else if (controlID.equals("1050601")) {
                        startActivity(new Intent(mContext, WarehouseAllocationActivity.class));
                    } else if (controlID.equals("303100101")) {
                        startActivity(new Intent(mContext, StaffWorkStatisticsActivity.class));
                    } else if (controlID.equals("1050701")) {
                        startActivity(new Intent(mContext, ProcessReportInstockActivity.class));
                    } else if (controlID.equals("1050801")) {
                        startActivity(new Intent(mContext, ProcessInformationActivity.class));
                    }
                } else {
                    Alerter.create(act)
                            .setTitle("提示")
                            .setText("服务器返回失败")
                            .setBackgroundColorRes(R.color.colorAlert)
                            .show();

                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.e("jindi", ex.toString());
                Alerter.create(act)
                        .setTitle("提示")
                        .setText("网络错误")
                        .setBackgroundColorRes(R.color.colorAlert)
                        .show();

            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                isGetJurisdiction = false;
            }
        });
    }

    private void getHelp() {
        HashMap<String, String> map = new HashMap<>();
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpPublicServer,
                define.GetHelpInfoList,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        LOG.e("result=" + result);
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.e("getHelp=" + result);
                                HML = mGson.fromJson(result, new TypeToken<List<HelpModel>>() {
                                }.getType());
                                helpdialog();

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }
}


