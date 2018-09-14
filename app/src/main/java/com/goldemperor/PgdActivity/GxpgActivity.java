package com.goldemperor.PgdActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.LogToFile;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;

import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.ScrollListenerHorizontalScrollView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tapadoo.alerter.Alerter;

import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.goldemperor.PgdActivity.GxpgAdapter.gxpgActivity;
import static com.goldemperor.PgdActivity.PgdActivity.selectWorkCardPlan;


/**
 * Created by Nova on 2017/7/25.
 */

public class GxpgActivity extends AppCompatActivity implements ScrollListenerHorizontalScrollView.OnScrollListener {


    private Context mContext;
    private Activity act;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    public static GxpgAdapter mMenuAdapter;

    private TextView tv_group;
    private TextView tv_number;
    private TextView tv_planbill;
    private TextView tv_style;
    private TextView top_havedispatchingnumber;

    private TextView tv_tip;

    private TextView tv_todayTarget;

    TextView tv_reportednumber;

    TextView top_noReportednumber;

    private TextView top_record;

    private TextView top_norecord;

    private TextView tv_weiwai;

    private FancyButton btn_submit;

    private FancyButton btn_namelist;

    private FancyButton btn_instructor;

    //private FancyButton btn_dispatchingnumberPublish;

    private FancyButton btn_reportPublish;

    private FancyButton btn_reportCheck;

    private FancyButton btn_report;

    private FancyButton btn_again;

    private FancyButton btn_clearZero;

    private FancyButton btn_assign;

    private FancyButton btn_operation;

    private FancyButton btn_PlanMaterial;

    public FancyButton btn_useGxpgPlan;

    private FancyButton btn_save;

    private FancyButton btn_verify;
    //存储获取过来的工序
    List<ProcessWorkCardPlanEntry> processWorkCardPlanEntryList;

    //存储要传递的工序
    List<Sc_ProcessWorkCardEntry> sc_ProcessWorkCardEntryList;

    private List<NameModel> nameList;

    public ScrollListenerHorizontalScrollView ScrollView;

    private int finterid;

    private DbManager dbManager;


    private int recordCount = 0;//计工数


    private int reportCount = 0;//汇报数

    public static int norecord;//已汇报未计工数
    private int Havedispatchingnumber = 0;

    //记录计工数
    private List<Integer> reportNumberList = new ArrayList<Integer>();

    public static int mScrollX = 0;

    String UseStyle = null;
    //已计工数
    //public static HashMap<String, Float> readyRecordCount = new HashMap<>();
    //List<cj_processoutputentry> OutputList;
    private boolean hasCloseGx = false;//如果有关闭工序先提示保存


    List<RouteEntry> RouteResultList;

    String RouteFile;
    String RouteFullName;
    private TextView tv_dispatchingnumber;
    private int delete_index = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gxpg);
        //隐藏标题栏
        getSupportActionBar().hide();
        mContext = this;
        act = this;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        //初始化数据库
        dbManager = initDb();

        ScrollView = (ScrollListenerHorizontalScrollView) findViewById(R.id.ScrollView);
        ScrollView.setOnScrollListener(this);


        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_dispatchingnumber = (TextView) findViewById(R.id.tv_dispatchingnumber);
        tv_dispatchingnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(mContext);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                new AlertDialog.Builder(gxpgActivity)
                        .setTitle("修改计工数")
                        .setView(editText)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText().toString().equals("")) {
                                    return;
                                }
                                int num = (int) Float.parseFloat(editText.getText().toString());
                                if (!selectWorkCardPlan.getFcanreportbynostockin()) {
                                    if (num > norecord) {
                                        Toast.makeText(mContext, "数量不能超过已汇报未计工数", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                                setWalkthrough(num);
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        tv_group = (TextView) findViewById(R.id.tv_group);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_planbill = (TextView) findViewById(R.id.tv_planbill);
        tv_style = (TextView) findViewById(R.id.tv_style);
        top_havedispatchingnumber = (TextView) findViewById(R.id.top_havedispatchingnumber);

        tv_reportednumber = (TextView) findViewById(R.id.tv_reportednumber);
        top_noReportednumber = (TextView) findViewById(R.id.top_noReportednumber);
        tv_todayTarget = (TextView) findViewById(R.id.tv_todayTarget);

        top_record = (TextView) findViewById(R.id.top_record);

        tv_weiwai = (TextView) findViewById(R.id.tv_weiwai);

        //获取生成派工单上的
        if (selectWorkCardPlan.getCumulativenumber() >= 0) {
            top_record.setText(String.valueOf("累计计工数:" + selectWorkCardPlan.getCumulativenumber()));
            recordCount = (int) selectWorkCardPlan.getCumulativenumber();
        }

        top_norecord = (TextView) findViewById(R.id.top_norecord);

        if (selectWorkCardPlan.getFcanreportbynostockin()) {
            tv_weiwai.setText("是否委外:是");
            top_record.setVisibility(View.GONE);
            top_norecord.setVisibility(View.GONE);
        } else {
            tv_weiwai.setVisibility(View.GONE);
        }
        if (PgdActivity.nameListResult == null || PgdActivity.nameListResult.size() == 0) {

        }
        //取出姓名和员工工号数据
        if (PgdActivity.nameListResult != null && PgdActivity.nameListResult != null) {

//            nameList = new String[PgdActivity.nameListResult.getData().size()][4];
            nameList = new ArrayList<>();
            for (int i = 0; i < PgdActivity.nameListResult.size(); i++) {
                nameList.add(new NameModel(
                        PgdActivity.nameListResult.get(i).getEmp_Name(),
                        PgdActivity.nameListResult.get(i).getEmp_Code(),
                        PgdActivity.nameListResult.get(i).getEmp_ID(),
                        PgdActivity.nameListResult.get(i).getEmp_Departid()
                ));
//                nameList[i][0] = PgdActivity.nameListResult.getData().get(i).getEmp_Name();
//                nameList[i][1] = PgdActivity.nameListResult.getData().get(i).getEmp_Code();
//                nameList[i][2] = PgdActivity.nameListResult.getData().get(i).getEmp_ID();
//                nameList[i][3] = PgdActivity.nameListResult.getData().get(i).getEmp_Departid();
            }
        } else {

//            nameList = new String[8][4];
            nameList = new ArrayList<>();

            for (int i = 0; i < 8; i++) {
                nameList.add(new NameModel("无法获取", "无法获取", "无法获取", "无法获取"));
//                nameList[i][0] = "无法获取";
//                nameList[i][1] = "无法获取";
//                nameList[i][2] = "无法获取";
//                nameList[i][3] = "无法获取";
            }
            Toast.makeText(this, "组别信息获取异常，请尝试重新登录，如无效请联系管理员。", Toast.LENGTH_LONG).show();
            return;
        }

        for (NameList list : PgdActivity.nameListResult) {
            LOG.e("Code=" + list.getEmp_Code()
                    + " Departid=" + list.getEmp_Departid()
                    + " Departname=" + list.getEmp_Departname()
                    + " Duty=" + list.getEmp_Duty()
                    + " ID=" + list.getEmp_ID()
                    + " Name=" + list.getEmp_Name());
        }
        for (int i = 0; i < 200; i++) {
            reportNumberList.add(0);
        }
        //初始化数据
        processWorkCardPlanEntryList = new ArrayList<ProcessWorkCardPlanEntry>();

        sc_ProcessWorkCardEntryList = new ArrayList<Sc_ProcessWorkCardEntry>();

        RouteResultList = new ArrayList<RouteEntry>();

        Intent intent = getIntent();
        finterid = intent.getIntExtra("finterid", 0);

        getData(finterid);

        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(this));// 添加分割线。

        // 设置菜单创建器。
        mMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        mMenuAdapter = new GxpgAdapter(processWorkCardPlanEntryList, nameList, this, dbManager);

        mMenuRecyclerView.setAdapter(mMenuAdapter);


        btn_submit = (FancyButton) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(act);
                normalDialog.setTitle("提示");
                normalDialog.setMessage("你确定要提交?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!checkJobNumber()) {
                                    submitData();
                                }
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                normalDialog.show();
            }
        });

        btn_verify = (FancyButton) findViewById(R.id.btn_verify);

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGylxData();
            }
        });

        btn_namelist = (FancyButton) findViewById(R.id.btn_namelist);
        btn_namelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NameListActivity.class);
                mContext.startActivity(intent);
            }
        });
        btn_instructor = (FancyButton) findViewById(R.id.btn_instructor);

        btn_instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_instructor.setEnabled(false);
                GetInstructor();
            }
        });


        btn_save = (FancyButton) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGxpgPlanReportNumber(true);
            }
        });

        btn_reportPublish = (FancyButton) findViewById(R.id.btn_reportPublish);

        btn_reportPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(act);
                normalDialog.setTitle("提示");
                normalDialog.setMessage("你确定要发布员工校对信息?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!checkJobNumber() && !checkReportNumber(false)) {
                                    for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
                                        if (sc_ProcessWorkCardEntryList.get(i).getFqty() > 0 && sc_ProcessWorkCardEntryList.get(i).getIsOpen()) {
                                            reportPublish(i);
                                        }
                                    }
                                    Toast.makeText(mContext, "信息已发布成功", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                normalDialog.show();

            }
        });

        btn_report = (FancyButton) findViewById(R.id.btn_report);

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasCloseGx) {
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(act);
                    normalDialog.setTitle("提示");
                    normalDialog.setMessage("你确定要对工序计工?");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!checkJobNumber()) {
                                        if (norecord > 0 || selectWorkCardPlan.getFcanreportbynostockin()) {
                                            if (!checkReportNumber(false)) {
                                                btn_report.setEnabled(false);
                                                Toast.makeText(mContext, "工序汇报中...", Toast.LENGTH_LONG).show();
                                                RecordProductionCount();
                                            }
                                        } else {
                                            Toast.makeText(mContext, "无法计工，未计工数为0", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                    normalDialog.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    normalDialog.show();
                } else {
                    Toast.makeText(mContext, "请先保存预排", Toast.LENGTH_LONG).show();
                }

            }
        });

        btn_again = (FancyButton) findViewById(R.id.btn_again);

        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "工序汇报校正中...", Toast.LENGTH_LONG).show();
                againSCProcessOutPutReWriteBysuitID();
            }
        });
        btn_clearZero = (FancyButton) findViewById(R.id.btn_clearZero);

        btn_clearZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(act);
                normalDialog.setTitle("提示");
                normalDialog.setMessage("确定要清零所有(已汇报未计工数)?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "正在清零", Toast.LENGTH_LONG).show();
                                for (Sc_ProcessWorkCardEntry sc_processWorkCardEntry : sc_ProcessWorkCardEntryList) {
                                    sc_processWorkCardEntry.setFqty(0);
                                }
                                mMenuAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                normalDialog.show();
            }

        });


        btn_PlanMaterial = (FancyButton) findViewById(R.id.btn_PlanMaterial);

        btn_PlanMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MaterialActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("finterid", selectWorkCardPlan.getFinterid());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        btn_assign = (FancyButton) findViewById(R.id.btn_assign);

        btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("planBody", processWorkCardPlanEntryList.get(0).getPlantbody());
                LOG.e("Plantbody：" + processWorkCardPlanEntryList.get(0).getPlantbody());
                Intent intent = new Intent(act,
                        AssignActivity.class);
                intent.putExtras(bundle);
                // 启动另一个Activity。
                startActivityForResult(intent, 0);
            }
        });

        btn_reportCheck = (FancyButton) findViewById(R.id.btn_reportCheck);
        btn_reportCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkJobNumber()) {
                    checkReportNumber(true);
                }
            }
        });


        btn_operation = (FancyButton) findViewById(R.id.btn_operation);

        btn_operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!checkJobNumber()) {
                    try {
                        List<GxpgPlanStatus> gxpgPlanStatusesList = dbManager.selector(GxpgPlanStatus.class).where("planbill", " = ", selectWorkCardPlan.getPlanbill()).and("orderbill", "=", selectWorkCardPlan.getOrderbill()).findAll();
                        if (norecord <= 0) {
                            Toast.makeText(mContext, "已汇报未计工数为零", Toast.LENGTH_LONG).show();
                        } else if (gxpgPlanStatusesList == null || gxpgPlanStatusesList.size() < 1) {
                            Toast.makeText(mContext, "请先保存预排", Toast.LENGTH_LONG).show();
                        } else {
                            for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
                                sc_ProcessWorkCardEntryList.get(i).setFqty(sc_ProcessWorkCardEntryList.get(i).getFpreschedulingqty());
                                mMenuAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        //按钮应用工序计划
        btn_useGxpgPlan = (FancyButton) findViewById(R.id.btn_useGxpgPlan);

        btn_useGxpgPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> bodyList = new ArrayList<String>();
                try {
                    List<GxpgPlan> gxpgPlanList = dbManager.findAll(GxpgPlan.class);
                    if (gxpgPlanList != null) {
                        for (int i = 0; i < gxpgPlanList.size(); i++) {
                            if (!bodyList.contains(gxpgPlanList.get(i).getStyle())) {
                                bodyList.add(gxpgPlanList.get(i).getStyle());
                            }
                        }
                        final String[] ChoiceItems = new String[bodyList.size()];
                        for (int i = 0; i < bodyList.size(); i++) {
                            ChoiceItems[i] = bodyList.get(i);
                        }
                        UseStyle = ChoiceItems[0];
                        new AlertDialog.Builder(mContext)
                                .setTitle("请选择要使用的型体")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setSingleChoiceItems(ChoiceItems, 0,
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                UseStyle = ChoiceItems[which];
                                            }
                                        }
                                )
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        dialog.dismiss();
                                        useGxpgPlan(UseStyle);
                                        Log.e("jindi", "UseStyle:" + UseStyle);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    } else {
                        Toast.makeText(mContext, "尚未记录工序计划", Toast.LENGTH_LONG).show();
                    }
                    Log.e("jindi", "bodyList:" + bodyList.size());
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });

        /*
        btn_saveOperation = (FancyButton) findViewById(R.id.btn_saveOperation);

        btn_saveOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGxpgPlanReportNumber(true);
            }
        });
        */
        //获取今日目标数
        GetDayWorkCardPlanQtyBysuitID();

        //获取已汇报数和累计计工数
        GetWorkCardQtyPassInfo();
        Log.e("jindi", selectWorkCardPlan.getFinterid() + "");
    }

    private void setWalkthrough(int max) {
        List<data> dataid = new ArrayList<>();
        //循环获取每条工单的序号
        for (Sc_ProcessWorkCardEntry sc_processWorkCardEntry : sc_ProcessWorkCardEntryList) {
            if (!dataid.contains(sc_processWorkCardEntry.getFprocessnumber())) {
                dataid.add(new data(sc_processWorkCardEntry.getFprocessnumber()));
            }
        }
        //设置每条序号下被复制的行数
        for (Sc_ProcessWorkCardEntry sc_processWorkCardEntry : sc_ProcessWorkCardEntryList) {
            for (data data : dataid) {
                if (data.getID().equals(sc_processWorkCardEntry.getFprocessnumber())) {
                    data.setNum(data.getNum() + 1);
                }
            }
        }
        //给每行赋值预排数
        for (data s : dataid) {
            int B = 0;//余数
            for (Sc_ProcessWorkCardEntry sc_processWorkCardEntry : sc_ProcessWorkCardEntryList) {
                if (s.getID().equals(sc_processWorkCardEntry.getFprocessnumber())) {
                    B = max % s.getNum();
                    //设置每行预排商数
                    sc_processWorkCardEntry.setFpreschedulingqty(max / s.getNum());
                }
            }
            if (B > 0) {
                for (Sc_ProcessWorkCardEntry sc_processWorkCardEntry : sc_ProcessWorkCardEntryList) {
                    if (s.getID().equals(sc_processWorkCardEntry.getFprocessnumber())) {
                        //将余数加到每个序号下第一条工单的预排数
//                        LOG.e("名称=" + s.getID() + "余数=" + B);
                        sc_processWorkCardEntry.setFpreschedulingqty(sc_processWorkCardEntry.getFpreschedulingqty() + B);
                        break;
                    }
                }
            }
        }
        for (Sc_ProcessWorkCardEntry sc_processWorkCardEntry : sc_ProcessWorkCardEntryList) {
            LOG.e("名称：" + sc_processWorkCardEntry.getFprocessname() + "   预排数" + sc_processWorkCardEntry.getFpreschedulingqty());
        }
        mMenuAdapter.notifyDataSetChanged();
    }

    private void setItemAddWalkthrough(int p) {

        int Max = 0;
        //获取本工工序所有工单预排数量总和
        for (Sc_ProcessWorkCardEntry sc_processWorkCardEntry : sc_ProcessWorkCardEntryList) {
            if (sc_processWorkCardEntry.getFprocessnumber().equals(sc_ProcessWorkCardEntryList.get(p).getFprocessnumber())) {
                Max += sc_processWorkCardEntry.getFpreschedulingqty();
            }
        }

        //添加一条数据
        ProcessWorkCardPlanEntry processWorkCardPlanEntry = (ProcessWorkCardPlanEntry) processWorkCardPlanEntryList.get(p).clone();
        processWorkCardPlanEntry.setFid(0);
        processWorkCardPlanEntryList.add(p + 1, processWorkCardPlanEntry);

        Sc_ProcessWorkCardEntry sc_processWorkCardEntry = (Sc_ProcessWorkCardEntry) sc_ProcessWorkCardEntryList.get(p).clone();
        sc_processWorkCardEntry.setFid(0);
        sc_processWorkCardEntry.setFqty(0);
        sc_processWorkCardEntry.setHaveSave(false);
        sc_processWorkCardEntry.setFpartitioncoefficient(0f);
        sc_processWorkCardEntry.setName("待选择");
        sc_processWorkCardEntry.setJobNumber("");
        sc_ProcessWorkCardEntryList.add(p + 1, sc_processWorkCardEntry);
        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
            sc_ProcessWorkCardEntryList.get(i).setFentryid(i + 1);
        }
        mMenuAdapter.notifyItemInserted(p + 1);

        //循环获取每条工单的序号
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
            if (sc_ProcessWorkCardEntryList.get(p).getFprocessnumber().equals(sc_ProcessWorkCardEntryList.get(i).getFprocessnumber())) {
                index.add(i);
            }
        }

        int A = Max / index.size();
        int B = Max % index.size();
        for (int i = 0; i < index.size(); i++) {
            if (i == 0) {
                sc_ProcessWorkCardEntryList.get(index.get(i)).setFpreschedulingqty(A + B);
            } else {
                sc_ProcessWorkCardEntryList.get(index.get(i)).setFpreschedulingqty(A);
            }
            LOG.e("名称：" + sc_ProcessWorkCardEntryList.get(index.get(i)).getFprocessname() + "   预排数" + sc_ProcessWorkCardEntryList.get(index.get(i)).getFpreschedulingqty());
            mMenuAdapter.notifyItemChanged(index.get(i));
        }
    }

    private void setItemRemovedWalkthrough(int p) {
        delete_index = p;
        int Max = 0;
        for (Sc_ProcessWorkCardEntry sc_processWorkCardEntry : sc_ProcessWorkCardEntryList) {
            if (sc_ProcessWorkCardEntryList.get(p).getFprocessnumber().equals(sc_processWorkCardEntry.getFprocessnumber())) {
                Max++;
            }
        }
        if (Max <= 1) {
            new AlertDialog.Builder(act)
                    .setTitle("提示")
                    .setMessage("本道工序仅有此一条数据,不允许删除")
                    .setNegativeButton("取消", null).show();
        } else {
            new AlertDialog.Builder(act)
                    .setTitle("提示")
                    .setMessage("你确定要删除这条数据")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String ID = sc_ProcessWorkCardEntryList.get(delete_index).getFprocessnumber();
                            LOG.e("删除:" + ID);
                            int DataMax = 0;
                            //累加所有该工序的预排熟数量
                            for (Sc_ProcessWorkCardEntry sc_processWorkCardEntry : sc_ProcessWorkCardEntryList) {
                                if (ID.equals(sc_processWorkCardEntry.getFprocessnumber())) {
                                    DataMax += sc_processWorkCardEntry.getFpreschedulingqty();
                                }
                            }
                            //删除本行
                            DeleteProcessWorkCardEntryByFID(sc_ProcessWorkCardEntryList.get(delete_index).getFid() + "");
                            //获取该工序所有行在列表中的下标位置
                            List<Integer> index = new ArrayList<>();
                            for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
                                if (ID.equals(sc_ProcessWorkCardEntryList.get(i).getFprocessnumber())) {
                                    index.add(i);
                                }
                            }
                            //计算商
                            int A = DataMax / index.size();
                            //计算余数
                            int B = DataMax % index.size();
                            //根据本工单所在所有行的下标进行重新预排
                            for (int i = 0; i < index.size(); i++) {
                                if (i == 0) {//将余数累加给第一行
                                    sc_ProcessWorkCardEntryList.get(index.get(i)).setFpreschedulingqty(A + B);
                                } else {
                                    sc_ProcessWorkCardEntryList.get(index.get(i)).setFpreschedulingqty(A);
                                }
                                mMenuAdapter.notifyItemChanged(index.get(i));
                            }
                        }
                    }).setNegativeButton("取消", null).show();
        }
    }

    class data {
        String ID;
        int num;

        public data(String ID) {
            this.ID = ID;
            this.num = 0;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    //先检查有没有工序未指定员工，再检查同一道工序有没有指定同一个员工
    public boolean checkJobNumber() {
        String message = "";
        boolean hasNameError = false;
        for (int j = 0; j < sc_ProcessWorkCardEntryList.size(); j++) {
            if (sc_ProcessWorkCardEntryList.get(j).getName().equals("待选择")) {
                message += "【" + sc_ProcessWorkCardEntryList.get(j).getFprocessnumber() + "】" + sc_ProcessWorkCardEntryList.get(j).getFprocessname() + ",未指定员工" + "\n";
            }
        }

        if (message.length() > 2) {
            new AlertDialog.Builder(act)
                    .setTitle("有工序未指定员工")
                    .setMessage(message)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null)
                    .show();
            hasNameError = true;
        } else {
            for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
                int count = 0;
                for (int j = 0; j < sc_ProcessWorkCardEntryList.size(); j++) {
                    if (processWorkCardPlanEntryList.get(i).getProcessname().equals(processWorkCardPlanEntryList.get(j).getProcessname()) && sc_ProcessWorkCardEntryList.get(i).getName().equals(sc_ProcessWorkCardEntryList.get(j).getName())) {
                        count++;
                    }
                }
                if (count > 1) {
                    message += "【" + sc_ProcessWorkCardEntryList.get(i).getFprocessnumber() + "】" + sc_ProcessWorkCardEntryList.get(i).getFprocessname() + ",同一道工序重复指定一个员工" + "\n";
                }
            }

            if (message.length() > 2) {
                new AlertDialog.Builder(act)
                        .setTitle("有工序重复指定同一个员工")
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .show();
                hasNameError = true;
            }
        }
        return hasNameError;
    }

    //计工检查
    public boolean checkReportNumber(boolean needShowNormalTip) {
        String message = "";
        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
            float count = 0;
            int OutputCount = 0;
            for (int j = 0; j < sc_ProcessWorkCardEntryList.size(); j++) {
                if (processWorkCardPlanEntryList.get(i).getProcessname().equals(processWorkCardPlanEntryList.get(j).getProcessname())) {
                    count += sc_ProcessWorkCardEntryList.get(j).getFqty();
                    OutputCount += (int) sc_ProcessWorkCardEntryList.get(j).getReportedqty();
                }
            }


            if (!selectWorkCardPlan.getFcanreportbynostockin()) {
                if (sc_ProcessWorkCardEntryList.get(i).getIsOpen()) {
                    if (count > norecord && !message.contains(sc_ProcessWorkCardEntryList.get(i).getFprocessname())) {
                        message += "【" + sc_ProcessWorkCardEntryList.get(i).getFprocessnumber() + "】" + sc_ProcessWorkCardEntryList.get(i).getFprocessname() + ",超过汇报数:" + (norecord - count) + "\n";
                    } else if (count < norecord && !message.contains(sc_ProcessWorkCardEntryList.get(i).getFprocessname())) {
                        message += "【" + sc_ProcessWorkCardEntryList.get(i).getFprocessnumber() + "】" + sc_ProcessWorkCardEntryList.get(i).getFprocessname() + ",少于汇报数:" + (norecord - count) + "\n";
                    }
                }
                /*
                else if (count + OutputCount > Havedispatchingnumber && !message.contains(sc_ProcessWorkCardEntryList.get(i).getFprocessname())) {
                 message += "【" + sc_ProcessWorkCardEntryList.get(i).getFprocessnumber() + "】" + sc_ProcessWorkCardEntryList.get(i).getFprocessname() + ",超过总数:" + (Havedispatchingnumber - count - OutputCount) + "\n";
                }
                */
            } else {
                if (sc_ProcessWorkCardEntryList.get(i).getIsOpen()) {
                    if (count + OutputCount > Havedispatchingnumber && !message.contains(sc_ProcessWorkCardEntryList.get(i).getFprocessname())) {
                        message += "【" + sc_ProcessWorkCardEntryList.get(i).getFprocessnumber() + "】" + sc_ProcessWorkCardEntryList.get(i).getFprocessname() + ",超过总数:" + (Havedispatchingnumber - count - OutputCount) + "\n";
                    }
                }
            }
        }

        if (message.length() > 2) {
            new AlertDialog.Builder(act)
                    .setTitle("计工数不符")
                    .setMessage(message)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null)
                    .show();
            return true;
        } else {
            if (needShowNormalTip) {
                new AlertDialog.Builder(act)
                        .setTitle("计工数正常")
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .show();
            }
            return false;
        }

    }

    //获取已汇报数和累计计工数
    public void GetWorkCardQtyPassInfo() {
        RequestParams params = new RequestParams(define.Net2 + define.GetWorkCardQtyPassInfo);
        params.addQueryStringParameter("WorkCardID", String.valueOf(selectWorkCardPlan.getFinterid()));

        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (!result.contains("error") || result.length() > 10) {
                    result = "{\"data\":" + result.substring(result.indexOf("[{"), result.indexOf("}]")) + "}]}";
                    Utils.e("jindi", "GetWorkCardQtyPassInfo" + result);
                    Gson g = new Gson();
                    GetWorkCardQtyPassInfoResult getWorkCardQtyPassInfoResult = g.fromJson(result, GetWorkCardQtyPassInfoResult.class);

                    reportCount = (int) getWorkCardQtyPassInfoResult.getData().get(0).getFqtypass();
                    recordCount = (int) getWorkCardQtyPassInfoResult.getData().get(0).getFqtyprocesspass();
                    tv_reportednumber.setText(String.valueOf("已汇报数:" + reportCount));
                    top_record.setText("累计计工数:" + String.valueOf(recordCount));
                    top_noReportednumber.setText("汇报欠数:" + (Havedispatchingnumber - reportCount));
                    norecord = reportCount - recordCount;//已汇报未计工数=已汇报数-累计计工数
                    top_norecord.setText("已汇报未计工数:" + String.valueOf(norecord));

                } else {
                    tv_tip.setText("获取已汇报数和累计计工数出错，请联系管理员");
                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                btn_instructor.setEnabled(true);
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


    //获取工序
    public void getData(int finterid) {
        tv_tip.setVisibility(View.VISIBLE);
        tv_tip.setText("数据载入中...");
        processWorkCardPlanEntryList.clear();
        sc_ProcessWorkCardEntryList.clear();

        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", String.valueOf(finterid));
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                "GetProcessWorkCardInfo",
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("获取工序=" + result);

                                List<ProcessWorkCardPlanEntry> list = new ArrayList<>();
                                Gson g = new Gson();

                                list = g.fromJson(result, new TypeToken<List<ProcessWorkCardPlanEntry>>() {
                                }.getType());
                                LOG.E("工序list=" + list.size());
                                if (list.size() > 0) {
                                    tv_group.setText("组别:" + selectWorkCardPlan.getFgroup());
                                    tv_number.setText("派工单号:" + list.get(0).getProcessbillnumber().toUpperCase());
                                    tv_planbill.setText("计划跟踪号:" + selectWorkCardPlan.getPlanbill());
                                    tv_style.setText("工厂型体:" + list.get(0).getPlantbody().toUpperCase());

                                    top_havedispatchingnumber.setText("派工总数:" + (int) list.get(0).getFsourceqty());

                                    Havedispatchingnumber = (int) list.get(0).getFsourceqty();
                                    for (int i = 0; i < list.size(); i++) {
                                        processWorkCardPlanEntryList.add(list.get(i));
                                        addSc_ProcessWorkCardEntry(list.get(i));
                                    }
                                    //自动应用计划工序
                                        List<GxpgPlanStatus> gxpgPlanStatusList = dbManager.selector(GxpgPlanStatus.class).where("planbill", " = ", selectWorkCardPlan.getPlanbill()).and("orderbill", "=", selectWorkCardPlan.getOrderbill()).findAll();
                                        LOG.e("工序list=" + processWorkCardPlanEntryList.size());
                                        if (gxpgPlanStatusList == null || gxpgPlanStatusList.size() <= 0) {
                                            useGxpgPlan(processWorkCardPlanEntryList.get(0).getPlantbody());

                                            Log.e("jindi", "应用计划工序");
                                        } else {
                                            Log.e("jindi", "工序已提交过");
                                        }

                                        //应用暂存数据
//                                        useGxpgPlanReportNumberSave();


                                    tv_tip.setVisibility(View.GONE);
                                    btn_report.setEnabled(true);
                                    GetWorkCardQtyPassInfo();
                                }
                            } catch (UnsupportedEncodingException e) {
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText("初始数据载入出错，请联系管理员");
                                e.printStackTrace();
                            } catch (DbException e) {
                                e.printStackTrace();
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText("数据库初始化失败，请联系管理员");
                            } catch (JsonSyntaxException e){
                                e.printStackTrace();
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText("数据解析异常，请联系管理员");
                            }
                        } else {
                            tv_tip.setText("暂无数据");
                        }
                        mMenuAdapter.notifyDataSetChanged();
                    }
                });

    }


    //计工后重新获取工序
    public void GetProcessWorkCardInfoByWorkCardID(long WorkCardID) {
        tv_tip.setVisibility(View.VISIBLE);
        tv_tip.setText("数据更新中...");
        processWorkCardPlanEntryList.clear();
        sc_ProcessWorkCardEntryList.clear();
        RequestParams params = new RequestParams(define.Net2 + define.GetProcessWorkCardInfoByWorkCardID);
        params.setReadTimeout(60000);
        params.setConnectTimeout(60000);
        params.addQueryStringParameter("WorkCardID", String.valueOf(WorkCardID));
        Log.e("jindi", "GetProcessWorkCardInfoByWorkCardID:" + params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (!result.contains("error") && result.length() > 10) {
                    result = "{\"data\":" + result.substring(result.indexOf("[{"), result.indexOf("}]")) + "}]}";
                    Utils.e("jindi", "GetProcessWorkCardInfoByWorkCardID" + result);

                    Gson g = new Gson();
                    GxpgResult gxpgs = g.fromJson(result, GxpgResult.class);
                    if (gxpgs != null && gxpgs.getData() != null) {
                        tv_group.setText("组别:" + selectWorkCardPlan.getFgroup());
                        tv_number.setText("派工单号:" + gxpgs.getData().get(0).getProcessbillnumber().toUpperCase());
                        tv_planbill.setText("计划跟踪号:" + selectWorkCardPlan.getPlanbill());
                        tv_style.setText("工厂型体:" + gxpgs.getData().get(0).getPlantbody().toUpperCase());
                        top_havedispatchingnumber.setText("派工总数:" + (int) gxpgs.getData().get(0).getFsourceqty());
                        Havedispatchingnumber = (int) gxpgs.getData().get(0).getFsourceqty();
                        for (int i = 0; i < gxpgs.getData().size(); i++) {
                            processWorkCardPlanEntryList.add(gxpgs.getData().get(i));
                            addSc_ProcessWorkCardEntry(gxpgs.getData().get(i));
                        }
                        tv_tip.setVisibility(View.GONE);
                        btn_report.setEnabled(true);
//                        Toast.makeText(mContext, "工序汇报成功", Toast.LENGTH_LONG).show();
                        GetWorkCardQtyPassInfo();
                    } else {
                        tv_tip.setText("暂无数据");
                    }
                    mMenuAdapter.notifyDataSetChanged();
                } else {
                    tv_tip.setText("数据载入出错，请联系管理员");
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());
                tv_tip.setVisibility(View.VISIBLE);
                tv_tip.setText("数据载入失败2:" + ex.toString());
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

    //删除工序操作
    public void DeleteProcessWorkCardEntryByFID(String fid) {
        if (fid.equals("0")) {
            processWorkCardPlanEntryList.remove(delete_index);
            sc_ProcessWorkCardEntryList.remove(delete_index);
            mMenuAdapter.notifyItemRemoved(delete_index);
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("FID", fid);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.DeleteProcessWorkCardEntryByFID,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("删除工序=" + result);
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if (ReturnType.equals("success")) {
                                    Toast.makeText(mContext, ReturnMsg, Toast.LENGTH_LONG).show();
                                    processWorkCardPlanEntryList.remove(delete_index);
                                    sc_ProcessWorkCardEntryList.remove(delete_index);
                                    mMenuAdapter.notifyItemRemoved(delete_index);
                                } else {
                                    Toast.makeText(mContext, "删除失败:" + ReturnMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (UnsupportedEncodingException e) {
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText("数据出错，请联系管理员");
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText("数据解析出错，请联系管理员");
                            }
                        } else {
                            tv_tip.setText("返回空");
                        }
                    }
                });
    }

    @Override
    public void onScroll(int scrollX) {
        mScrollX = scrollX;
        for (int i = 0; i < mMenuAdapter.ScrollViewList.size(); i++) {
            mMenuAdapter.ScrollViewList.get(i).scrollTo(scrollX, ScrollView.getScrollY());
        }
    }

    public void addSc_ProcessWorkCardEntry(ProcessWorkCardPlanEntry processWorkCardPlanEntry) {
        Sc_ProcessWorkCardEntry sc_processWorkCardEntry = new Sc_ProcessWorkCardEntry();
        sc_processWorkCardEntry.setFinterid(processWorkCardPlanEntry.getFinterid());
        sc_processWorkCardEntry.setFentryid(processWorkCardPlanEntry.getFentryid());
        sc_processWorkCardEntry.setFmtono(processWorkCardPlanEntry.getFmtono());
        sc_processWorkCardEntry.setFpartsid(processWorkCardPlanEntry.getFpartsid());
        sc_processWorkCardEntry.setFsrcicmointerid(processWorkCardPlanEntry.getFsrcicmointerid());
        sc_processWorkCardEntry.setFprdmoid(processWorkCardPlanEntry.getFprdmoid());
        sc_processWorkCardEntry.setFmoid(0);
        sc_processWorkCardEntry.setFoperplanningid(processWorkCardPlanEntry.getFoperplanningid());
        sc_processWorkCardEntry.setFproductid(processWorkCardPlanEntry.getFproductid());
        sc_processWorkCardEntry.setFdeptmentid(processWorkCardPlanEntry.getFdeptmentid());
        sc_processWorkCardEntry.setFitemid(processWorkCardPlanEntry.getFitemid());
        sc_processWorkCardEntry.setFprocessid(0);
        sc_processWorkCardEntry.setFprocessnumber(processWorkCardPlanEntry.getProcesscode());
        sc_processWorkCardEntry.setFprocessname(processWorkCardPlanEntry.getProcessname());
        sc_processWorkCardEntry.setFsize(null);
        sc_processWorkCardEntry.setFmochinecode(null);
        sc_processWorkCardEntry.setFmochinecode(null);
        sc_processWorkCardEntry.setFmustqty(processWorkCardPlanEntry.getFmustqty());
        sc_processWorkCardEntry.setFplanstartdate(null);
        sc_processWorkCardEntry.setFplanfinishdate(null);
        sc_processWorkCardEntry.setFfinishqty((int) processWorkCardPlanEntry.getFfinishqty());
        sc_processWorkCardEntry.setFlastfinishdate(null);
        sc_processWorkCardEntry.setFprice(processWorkCardPlanEntry.getPrice());
        sc_processWorkCardEntry.setFamount(processWorkCardPlanEntry.getFmoney());
        sc_processWorkCardEntry.setFprocesserid(0);
        sc_processWorkCardEntry.setFprocessdate(null);
        sc_processWorkCardEntry.setFcardno(processWorkCardPlanEntry.getBarcode());
        sc_processWorkCardEntry.setFnotes(null);
        sc_processWorkCardEntry.setFversion("0.1");
        sc_processWorkCardEntry.setFsourceinterid(processWorkCardPlanEntry.getFsourceinterid());
        sc_processWorkCardEntry.setFsourceentryid(processWorkCardPlanEntry.getFsourceentryid());
        sc_processWorkCardEntry.setFprocessoutputid(processWorkCardPlanEntry.getFprocessoutputid());
        sc_processWorkCardEntry.setFbatchno(processWorkCardPlanEntry.getBatch());
        sc_processWorkCardEntry.setFgroupprintno(0);
        sc_processWorkCardEntry.setFmodelid(processWorkCardPlanEntry.getFmodelid());
        sc_processWorkCardEntry.setFroutingid(processWorkCardPlanEntry.getFroutingid());
        sc_processWorkCardEntry.setFqty(processWorkCardPlanEntry.getFqty());
        sc_processWorkCardEntry.setFpreschedulingqty((int) processWorkCardPlanEntry.getFpreschedulingqty());
        sc_processWorkCardEntry.setHaveSave(true);
        sc_processWorkCardEntry.setReportedqty(processWorkCardPlanEntry.getReportedqty());

        sc_processWorkCardEntry.setFprocessflowid(processWorkCardPlanEntry.getFprocessflowid());

        sc_processWorkCardEntry.setFprocessingmethod(processWorkCardPlanEntry.getFprocessingmethod());

        sc_processWorkCardEntry.setFoperplanninginterid(processWorkCardPlanEntry.getFoperplanninginterid());
        //Log.e("jindi","Foperplanninginterid:"+processWorkCardPlanEntry.getFoperplanninginterid());
        sc_processWorkCardEntry.setFoperplanningentryid(processWorkCardPlanEntry.getFoperplanningentryid());

        sc_processWorkCardEntry.setFsourceentryfid(processWorkCardPlanEntry.getFsourceentryfid());

        sc_processWorkCardEntry.setFrouteentryfid(processWorkCardPlanEntry.getFrouteentryfid());

        sc_processWorkCardEntry.setFoperplanningentryfid(processWorkCardPlanEntry.getFoperplanningentryfid());

        sc_processWorkCardEntry.setFid(processWorkCardPlanEntry.getFid());

        sc_processWorkCardEntry.setFpartitioncoefficient(processWorkCardPlanEntry.getFpartitioncoefficient());

        sc_processWorkCardEntry.setFsourceqty(processWorkCardPlanEntry.getFsourceqty());


//        if ((int) processWorkCardPlanEntry.getFpreschedulingqty() == 0) {
//            Log.e("jindi", processWorkCardPlanEntry.getProcessname() + ":" + processWorkCardPlanEntry.getFmustqty() + "," + processWorkCardPlanEntry.getFpartitioncoefficient());
//            sc_processWorkCardEntry.setFpreschedulingqty((int) Math.floor((double) (processWorkCardPlanEntry.getFmustqty() * processWorkCardPlanEntry.getFpartitioncoefficient())));
//        }

        if (processWorkCardPlanEntry.getFempid() == 0) {
            sc_processWorkCardEntry.setName(nameList.get(0).getName());
            sc_processWorkCardEntry.setJobNumber(nameList.get(0).getCode());
            sc_processWorkCardEntry.setFempid(Integer.valueOf(nameList.get(0).getID()));
            sc_processWorkCardEntry.setFdeptmentid(Integer.valueOf(nameList.get(0).getDepartid()));
        } else {

            sc_processWorkCardEntry.setFempid(processWorkCardPlanEntry.getFempid());
            String emp = processWorkCardPlanEntry.getFemp();
            LOG.e("emp=" + emp);
            String name = emp.substring(emp.indexOf("(") + 1, emp.length() - 1);
            sc_processWorkCardEntry.setName(name);
            String jobNumber = emp.substring(0, emp.indexOf("("));
            sc_processWorkCardEntry.setJobNumber(jobNumber);
            sc_processWorkCardEntry.setFdeptmentid(processWorkCardPlanEntry.getFdeptmentid());

        }


        try {
            GxpgSwitch gxpgSwitch = dbManager.selector(GxpgSwitch.class).where("style", " = ", processWorkCardPlanEntry.getPlantbody()).and("processname", "=", processWorkCardPlanEntry.getProcessname()).findFirst();
            if (gxpgSwitch != null && !gxpgSwitch.getOpen()) {
                sc_processWorkCardEntry.setIsOpen(false);
            } else if (gxpgSwitch != null && gxpgSwitch.getOpen()) {
                sc_processWorkCardEntry.setIsOpen(true);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        sc_processWorkCardEntry.setFfinishqty(0);
        sc_ProcessWorkCardEntryList.add(sc_processWorkCardEntry);
    }

    //应用存储在本地的工序计划
    public void useGxpgPlan(String style) {
        LOG.e("useGxpgPlan=" + style);
        try {
            for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
                List<GxpgPlan> gxpgPlanList = dbManager.selector(GxpgPlan.class).where("style", " = ", style).and("processname", "=", sc_ProcessWorkCardEntryList.get(i).getFprocessname()).findAll();
                if (gxpgPlanList != null) {
                    for (GxpgPlan gxpgPlan : gxpgPlanList) {
                        LOG.e("Id=" + gxpgPlan.getId());
                        LOG.e("Empid=" + gxpgPlan.getEmpid());
                        LOG.e("Style=" + gxpgPlan.getStyle());
                        LOG.e("Username=" + gxpgPlan.getUsername());
                        LOG.e("Usernumber=" + gxpgPlan.getUsernumber());
                        LOG.e("Processname=" + gxpgPlan.getProcessname());
                        LOG.e("Per=" + gxpgPlan.getPer());
                        LOG.e("----------------------");
                    }
                    //先检查一遍此道工序是否有多道工序，如果是多道工序说明已应用过最近分配
                    int checkProcess = 0;
                    for (int c = 0; c < sc_ProcessWorkCardEntryList.size(); c++) {
                        if (sc_ProcessWorkCardEntryList.get(c).getFprocessname().equals(sc_ProcessWorkCardEntryList.get(i).getFprocessname())) {
                            checkProcess++;
                        }
                    }
                    for (int j = 0; j < gxpgPlanList.size(); j++) {
                        //如果一道工序多个派工,且不是第一个派工记录则添加数据
                        if (j > 0 && checkProcess <= 1) {
                            ProcessWorkCardPlanEntry processWorkCardPlanEntry = (ProcessWorkCardPlanEntry) processWorkCardPlanEntryList.get(i).clone();

                            processWorkCardPlanEntry.setFid(0);
                            processWorkCardPlanEntryList.add(i + j, processWorkCardPlanEntry);

                            Sc_ProcessWorkCardEntry sc_processWorkCardEntry = (Sc_ProcessWorkCardEntry) sc_ProcessWorkCardEntryList.get(i).clone();

                            sc_processWorkCardEntry.setFid(0);
                            sc_ProcessWorkCardEntryList.add(i + j, sc_processWorkCardEntry);

                        }
                        sc_ProcessWorkCardEntryList.get(i + j).setFempid(gxpgPlanList.get(j).getEmpid());
                        sc_ProcessWorkCardEntryList.get(i + j).setJobNumber(gxpgPlanList.get(j).getUsernumber());
                        sc_ProcessWorkCardEntryList.get(i + j).setName(gxpgPlanList.get(j).getUsername());

//                        double qty = processWorkCardPlanEntryList.get(i + j).getFmustqty() * gxpgPlanList.get(j).getPer();
//                        double qty = norecord * gxpgPlanList.get(j).getPer();
//                        LOG.e("norecord=" + norecord);
//                        DecimalFormat df = new DecimalFormat("#.0");
//                        try {
//                            sc_ProcessWorkCardEntryList.get(i + j).setFpreschedulingqty(Float.valueOf(df.format(qty)));
//                        } catch (Throwable ex) {
//                            ex.printStackTrace();
//                            Log.e("jindi", ex.toString());
//                            sc_ProcessWorkCardEntryList.get(i + j).setFpreschedulingqty(0);
//                        }
                        sc_ProcessWorkCardEntryList.get(i + j).setFpreschedulingqty(0);

                        Log.e("jindi", gxpgPlanList.get(j).getProcessname() + ",name:" + gxpgPlanList.get(j).getUsername() + ",qty:");
                    }
                    //赋值i，跳过重复派工
                    if (gxpgPlanList.size() > 1) {
                        i = i + gxpgPlanList.size() - 1;
                    }
                }
            }
            LOG.e("已汇报未计工数"+norecord);
            setWalkthrough(norecord);
        } catch (DbException e) {
            e.printStackTrace();
            Log.e("jindi", e.toString());
        }
    }


    //使用暂存的计工数
    public void useGxpgPlanReportNumberSave() {
        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
            try {
                GxpgPlanReportNumberSave gxpgPlanReportNumberSave = dbManager.selector(GxpgPlanReportNumberSave.class).where("planbill", " = ", selectWorkCardPlan.getPlanbill()).and("orderbill", "=", selectWorkCardPlan.getOrderbill()).and("usernumber", "=", sc_ProcessWorkCardEntryList.get(i).getJobNumber()).and("processname", "=", sc_ProcessWorkCardEntryList.get(i).getFprocessname()).findFirst();
                if (gxpgPlanReportNumberSave != null) {
                    sc_ProcessWorkCardEntryList.get(i).setFqty((int) gxpgPlanReportNumberSave.getRecordcount());
                }
            } catch (DbException e) {
                e.printStackTrace();
                Log.e("jindi", e.toString());
            }

        }
        mMenuAdapter.notifyDataSetChanged();
    }

    public void GetInstructor() {
        /*
        RequestParams params = new RequestParams(define.Net1 + define.GetRouteEntryPic);

        params.addQueryStringParameter("FInterID", String.valueOf(selectWorkCardPlan.getFroutingid()));
        params.addQueryStringParameter("FPlanBill", String.valueOf(selectWorkCardPlan.getPlanbill()));
        */

        RequestParams params = new RequestParams(define.Net2 + define.GetManufactureInstructions);

        params.addQueryStringParameter("FUserID", dataPref.getString(define.SharedUserId, define.NONE));
        params.addQueryStringParameter("FRouteID", String.valueOf(selectWorkCardPlan.getFroutingid()));

        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                result = result.replaceAll("\\\\", "");
                Log.e("jindi", result);

                if (result.contains("error")) {
                    String ReturnMsg = result.substring(result.indexOf("ReturnMsg"), result.indexOf(",")).replace("ReturnMsg\":", "").replace("\"", "");
                    Toast.makeText(mContext, ReturnMsg, Toast.LENGTH_LONG).show();
                } else {
                    String ReturnMsg = "{\"data\":" + result.substring(result.indexOf("[{"), result.indexOf("}]")) + "}]}";
                    Log.e("jindi", ReturnMsg);
                    Gson g = new Gson();
                    final T_Prd_Route_Result route = g.fromJson(ReturnMsg, T_Prd_Route_Result.class);
                    Log.e("jindi", route.getData().size() + "");
                    final String[] ChoiceItems = new String[route.getData().size()];
                    for (int i = 0; i < route.getData().size(); i++) {
                        ChoiceItems[i] = route.getData().get(i).getFTypeName() + " " + route.getData().get(i).getFFileName();
                    }
                    RouteFile = route.getData().get(0).getFFileName();
                    RouteFullName = route.getData().get(0).getFFullName();
                    new AlertDialog.Builder(mContext)
                            .setTitle("请选择工艺说明书")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setSingleChoiceItems(ChoiceItems, 0,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            RouteFile = route.getData().get(which).getFFileName();
                                            RouteFullName = route.getData().get(which).getFFullName();
                                        }
                                    }
                            )
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    dialog.dismiss();
                                    downloadInstructor(RouteFullName, RouteFile);
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                btn_instructor.setEnabled(true);
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

    //下载工艺说明书
    public void downloadInstructor(final String url, final String downloadFileName) {
        Toast.makeText(mContext, "正在下载工艺说明书...", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                download(url, downloadFileName);
            }
        }).start();
    }

    //获取工艺路线
    public void getGylxData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("FBillNo", selectWorkCardPlan.getRoutebillnumber());
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForMesServer,
                define.GetPrdRouteInfo2,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("获取工序=" + result);
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if (ReturnType.contains("error")) {
                                    tv_tip.setText("没有查询到数据");
                                } else {
                                    tv_tip.setVisibility(View.GONE);
                                    LOG.E("工序派工____拼接json=" + result);
                                    Gson g = new Gson();
                                    List<RouteEntry> gylx = g.fromJson(ReturnMsg, new TypeToken<List<RouteEntry>>() {
                                    }.getType());
                                    String verifyResult = "";
                                    String gylxCount = "";
                                    for (int j = 0; j < sc_ProcessWorkCardEntryList.size(); j++) {
                                        gylxCount += sc_ProcessWorkCardEntryList.get(j).getFprocessnumber() + " ";
                                    }

                                    if (gylx != null && gylx != null) {
                                        for (int i = 0; i < gylx.size(); i++) {
                                            RouteResultList.add(gylx.get(i));
                                            //检查工艺路线是否匹配
                                            if (!gylxCount.contains(gylx.get(i).getFprocessnumber())) {
                                                verifyResult += gylx.get(i).getFprocessnumber() + " 工序缺失 ";
                                            }
                                        }
                                    }

                                    if (verifyResult.length() > 10) {
                                        Toast.makeText(mContext, verifyResult, Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(mContext, "工序正常", Toast.LENGTH_LONG).show();
                                    }
                                }


                            } catch (UnsupportedEncodingException e) {
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText("初始数据载入出错，请联系管理员");
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tv_tip.setText("暂无数据");
                        }
                        mMenuAdapter.notifyDataSetChanged();
                    }
                });

//
//
//        RouteResultList.clear();
//        RequestParams params = new RequestParams(define.Net2 + define.GetPrdRouteInfo);
//        params.setReadTimeout(60000);
//        params.addQueryStringParameter("FBillNo", selectWorkCardPlan.getRoutebillnumber());
//        Log.e("jindi", params.toString());
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    result = URLDecoder.decode(result, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                result = result.substring(result.indexOf(">{") + 1, result.lastIndexOf("}<") + 1);
//                LOG.E("工序派工____去除XML包裹=" + result);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    String ReturnType = jsonObject.getString("ReturnType");
//                    String ReturnMsg = jsonObject.getString("ReturnMsg");
//                    LOG.e("工序派工____截取ReturnType=" + ReturnType);
//                    LOG.e("工序派工____截取ReturnMsg=" + ReturnMsg);
//                    if (ReturnType.contains("error")) {
//                        tv_tip.setText("没有查询到数据");
//                    } else {
//                        tv_tip.setVisibility(View.GONE);
//                        result = "{\"data\":" + ReturnMsg + "}";
//                        LOG.E("工序派工____拼接json=" + result);
//                        Gson g = new Gson();
//                        RouteResult gylx = g.fromJson(result, RouteResult.class);
//                        String verifyResult = "";
//                        String gylxCount = "";
//                        for (int j = 0; j < sc_ProcessWorkCardEntryList.size(); j++) {
//                            gylxCount += sc_ProcessWorkCardEntryList.get(j).getFprocessnumber() + " ";
//                        }
//
//                        if (gylx != null && gylx.getData() != null) {
//                            for (int i = 0; i < gylx.getData().size(); i++) {
//                                RouteResultList.add(gylx.getData().get(i));
//                                //检查工艺路线是否匹配
//                                if (!gylxCount.contains(gylx.getData().get(i).getFprocessnumber())) {
//                                    verifyResult += gylx.getData().get(i).getFprocessnumber() + " 工序缺失 ";
//                                }
//                            }
//                        }
//
//                        if (verifyResult.length() > 10) {
//                            Toast.makeText(mContext, verifyResult, Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(mContext, "工序正常", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            //请求异常后的回调方法
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.e("jindi", ex.toString());
//                tv_tip.setVisibility(View.VISIBLE);
//                tv_tip.setText("数据载入失败,请检查网络");
//            }
//
//            //主动调用取消请求的回调方法
//            @Override
//            public void onCancelled(CancelledException cex) {
//            }
//
//            @Override
//            public void onFinished() {
//            }
//        });

    }

    //下载工艺说明书具体操作
    private void download(String downloadUrl, final String downloadFileName) {
        try {
            //下载链接要编码成URL字符
            downloadUrl = Utils.cnToEncode(downloadUrl);
            Log.e("jindi", downloadUrl);
            URL url = new URL(downloadUrl);
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            InputStream is = conn.getInputStream();
            Log.e("jindi", "打开输入流");
            //获得长度
            int contentLength = conn.getContentLength();
            Log.e("jindi", "contentLength = " + contentLength);
            //创建文件夹 MyDownLoad，在存储卡下
            String dirName = Environment.getExternalStorageDirectory() + "/Instructor/";
            File file = new File(dirName);
            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            String fileName = dirName + downloadFileName;
            Log.e("jindi", fileName.toString());
            File file1 = new File(fileName);
            if (file1.exists()) {
                file1.delete();
            }
            //创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(fileName);
            //写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            //完成后关闭流
            Log.e("jindi", "download-finish");
            os.close();
            is.close();
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btn_instructor.setEnabled(true);
                }
            });


            Intent intent = new Intent(Intent.ACTION_VIEW);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(act, "com.goldemperor.fileprovider", file1);
                intent.setDataAndType(contentUri, "application/pdf");
            } else {
                Uri path = Uri.fromFile(file1);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext,
                                "没有安装查看word/pdf软件",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            Log.e("jindi", e.toString());
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "找不到文件", Toast.LENGTH_SHORT).show();
                    btn_instructor.setEnabled(true);
                }
            });
        }
    }

    public void submitData() {
        //保存最近工序计划
        saveGxpgPlan();
        saveGxpgPlanReportNumber(false);

        Gson g = new Gson();
        RequestParams params = new RequestParams(define.Net2 + define.SaveProcessWorkCardEntry);
        params.setReadTimeout(60000);
        params.setConnectTimeout(60000);


        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
            sc_ProcessWorkCardEntryList.get(i).setFentryid(i + 1);
            //关闭程序设成0
            if (!sc_ProcessWorkCardEntryList.get(i).getIsOpen()) {
                sc_ProcessWorkCardEntryList.get(i).setFqty(0);
                sc_ProcessWorkCardEntryList.get(i).setFpreschedulingqty(0);
            }
        }


        List<Sc_ProcessWorkCardEntryNative> sc_ProcessWorkCardEntryListNative = GsonFactory.jsonToArrayList(g.toJson(sc_ProcessWorkCardEntryList), Sc_ProcessWorkCardEntryNative.class);
        for (Sc_ProcessWorkCardEntryNative sc_processWorkCardEntryNative : sc_ProcessWorkCardEntryListNative) {
            if(!sc_processWorkCardEntryNative.isOpen()){
                sc_processWorkCardEntryNative.setFqty(0);
                sc_processWorkCardEntryNative.setFpreschedulingqty(0);
            }
        }


        Collections.reverse(sc_ProcessWorkCardEntryListNative);
//        Utils.e("jindi", g.toJson(sc_ProcessWorkCardEntryListNative));
        params.addParameter("PushJsonCondition", g.toJson(sc_ProcessWorkCardEntryListNative));
        LOG.E("保存预排提交：" + g.toJson(sc_ProcessWorkCardEntryListNative));
        for (Sc_ProcessWorkCardEntryNative SC : sc_ProcessWorkCardEntryListNative) {
            LOG.e("isOpen:"+SC.isOpen()+"  EmpId:"+SC.getFempid()+"   工序名称:"+SC.getFprocessname()+"  预排数量:"+SC.getFpreschedulingqty()+"  已汇报未计工数:"+SC.getFqty());
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                //重新设置数据
                try {
                    LOG.E("保存预排：" + URLDecoder.decode(result, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (result.contains("OK") || result.contains("success")) {
                    Alerter.create(act)
                            .setTitle("提示")
                            .setText("提交成功")
                            .setBackgroundColorRes(R.color.colorAlert)
                            .show();
                    for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
                        reportNumberList.set(i,(int)sc_ProcessWorkCardEntryList.get(i).getFqty());
                    }
                    //提交成功后刷新
                    getData((int) sc_ProcessWorkCardEntryList.get(0).getFinterid());
                    selectWorkCardPlan.setPlanStatus("已排");
                    hasCloseGx = false;
                } else {
                    Alerter.create(act)
                            .setTitle("提示")
                            .setText("提交失败")
                            .setBackgroundColorRes(R.color.colorAlert)
                            .show();

                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", "saveData:" + ex.getMessage());
                tv_tip.setVisibility(View.VISIBLE);
                tv_tip.setText("提交失败,请联系管理员");
                Alerter.create(act)
                        .setTitle("提示")
                        .setText("提交失败,请联系管理员")
                        .setBackgroundColorRes(R.color.colorAlert)
                        .show();
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

    //工序计工
    public void RecordProductionCount() {

//        RequestParams params = new RequestParams(define.Net2 + define.RecordProductionCount);

        //重新设置entryid
        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
            sc_ProcessWorkCardEntryList.get(i).setFentryid(i + 1);
        }

        //设置qty计工数为finishqty界面上的操作数
        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
            //关闭程序设成0
            if (!sc_ProcessWorkCardEntryList.get(i).getIsOpen()) {
                sc_ProcessWorkCardEntryList.get(i).setFqty(0);
            }
        }

        Gson g = new Gson();

//        //过滤已关闭工序
//        List<Sc_ProcessWorkCardEntry> sc_ProcessWorkCardEntryListTemp = new ArrayList<Sc_ProcessWorkCardEntry>();
//        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
//            if (sc_ProcessWorkCardEntryList.get(i).getIsOpen()) {
//                sc_ProcessWorkCardEntryListTemp.add(sc_ProcessWorkCardEntryList.get(i));
//            }
//        }
        List<Sc_ProcessWorkCardEntryNative> sc_ProcessWorkCardEntryListNative = GsonFactory.jsonToArrayList(g.toJson(sc_ProcessWorkCardEntryList), Sc_ProcessWorkCardEntryNative.class);
        Collections.reverse(sc_ProcessWorkCardEntryListNative);
        Utils.e("jindi", "RecordProductionCount:" + g.toJson(sc_ProcessWorkCardEntryListNative));

        //记录日志
        LogToFile.init(this);
        LogToFile.WordCardId = selectWorkCardPlan.getPlanbill();
        LogToFile.e("jindi", g.toJson(GsonFactory.jsonToArrayList(g.toJson(sc_ProcessWorkCardEntryList), Log_ProcessOutPutEntry.class)));

        HashMap<String, String> map = new HashMap<>();
        map.put("PushJsonCondition", g.toJson(sc_ProcessWorkCardEntryListNative));
        map.put("UserID", dataPref.getString(define.SharedUserId, "0"));
        map.put("suitID", define.suitID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.RecordProductionCount,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        btn_report.setEnabled(true);
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("工序计工提交结果=" + result);
//                                {"ReturnMsg":"计工成功!","ReturnType":"success"}
                                JSONObject jb = new JSONObject(result);
                                String ReturnType = jb.getString("ReturnType");
                                String ReturnMsg = jb.getString("ReturnMsg");
                                GetProcessWorkCardInfoByWorkCardID(selectWorkCardPlan.getFinterid());
                                Alerter.create(act)
                                        .setTitle("提示")
                                        .setText(ReturnMsg)
                                        .setBackgroundColorRes(R.color.colorAlert)
                                        .show();
//                                if (ReturnType.equals("success")) {
//                                } else {
//                                }
                            } catch (UnsupportedEncodingException e) {
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText("数据出错，请联系管理员");
                                e.printStackTrace();
                            } catch (JSONException e) {
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText("解析异常，请联系管理员");
                                e.printStackTrace();
                            }
                        } else {
                            tv_tip.setText("暂无数据");
                        }
                    }
                });
    }


    //本地数据库保存工序计划
    public void saveGxpgPlan() {

        //存储派工单已排状态
        GxpgPlanStatus gxpgPlanStatus = new GxpgPlanStatus();
        gxpgPlanStatus.setOrderbill(selectWorkCardPlan.getOrderbill());
        gxpgPlanStatus.setPlanbill(selectWorkCardPlan.getPlanbill());
        gxpgPlanStatus.setStatus("已排");

        //存储工序计划
        ArrayList<GxpgPlan> gxpgPlanList = new ArrayList<>();
        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
            //先删除原先保留的工序计划
            try {
                WhereBuilder b = WhereBuilder.b();
                b.and("style", "=", processWorkCardPlanEntryList.get(i).getPlantbody());
                b.and("processname", "=", sc_ProcessWorkCardEntryList.get(i).getFprocessname());
                dbManager.delete(GxpgPlan.class, b);
            } catch (DbException e) {
                e.printStackTrace();
            }
            sc_ProcessWorkCardEntryList.get(i).setHaveSave(true);
            GxpgPlan gxpgPlan = new GxpgPlan();
            gxpgPlan.setStyle(processWorkCardPlanEntryList.get(i).getPlantbody());
            gxpgPlan.setProcessname(sc_ProcessWorkCardEntryList.get(i).getFprocessname());
            gxpgPlan.setUsername(sc_ProcessWorkCardEntryList.get(i).getName());
            gxpgPlan.setUsernumber(sc_ProcessWorkCardEntryList.get(i).getJobNumber());
            gxpgPlan.setEmpid(sc_ProcessWorkCardEntryList.get(i).getFempid());
            float f1 = sc_ProcessWorkCardEntryList.get(i).getFpreschedulingqty();
//            float f2 = (processWorkCardPlanEntryList.get(i).getFmustqty());
            float f2 = norecord;
            float f3 = f1 / f2;
            LOG.e("-----f1=" + f1 + " f2=" + f2 + " f3=" + f3);
            if (f2 == 0) {
                if (f1 > 0) {
                    gxpgPlan.setPer(1);
                } else {
                    gxpgPlan.setPer(0);
                }
            } else {
                gxpgPlan.setPer(f3);
            }
//            gxpgPlan.setPer(sc_ProcessWorkCardEntryList.get(i).getFpreschedulingqty() / (processWorkCardPlanEntryList.get(i).getFmustqty()));
            LOG.e("-----占比" + gxpgPlan.getPer());
            gxpgPlanList.add(gxpgPlan);
        }
        try {
            dbManager.save(gxpgPlanList);
            dbManager.save(gxpgPlanStatus);
        } catch (DbException e) {
            e.printStackTrace();
            Log.e("jindi", e.toString());
        }
    }


    //本地数据库暂存运算计工数
    public void saveGxpgPlanReportNumber(boolean showTip) {

        //存储工序计划
        ArrayList<GxpgPlanReportNumberSave> GxpgPlanReportNumberSaveList = new ArrayList<>();
        for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
            //先删除原先保留的工序计划
            try {
                WhereBuilder b = WhereBuilder.b();
                b.and("planbill", " = ", selectWorkCardPlan.getPlanbill());
                b.and("orderbill", "=", selectWorkCardPlan.getOrderbill());
                b.and("processname", "=", sc_ProcessWorkCardEntryList.get(i).getFprocessname());
                b.and("usernumber", "=", sc_ProcessWorkCardEntryList.get(i).getJobNumber());
                dbManager.delete(GxpgPlanReportNumberSave.class, b);
            } catch (DbException e) {
                e.printStackTrace();
            }
            GxpgPlanReportNumberSave gxpgPlanReportNumberSave = new GxpgPlanReportNumberSave();
            gxpgPlanReportNumberSave.setOrderbill(selectWorkCardPlan.getOrderbill());
            gxpgPlanReportNumberSave.setPlanbill(selectWorkCardPlan.getPlanbill());
            gxpgPlanReportNumberSave.setProcessname(sc_ProcessWorkCardEntryList.get(i).getFprocessname());
            gxpgPlanReportNumberSave.setUsername(sc_ProcessWorkCardEntryList.get(i).getName());
            gxpgPlanReportNumberSave.setUsernumber(sc_ProcessWorkCardEntryList.get(i).getJobNumber());
            gxpgPlanReportNumberSave.setRecordcount((int)sc_ProcessWorkCardEntryList.get(i).getFqty());
            Log.e("jindi", selectWorkCardPlan.getOrderbill() + "," + selectWorkCardPlan.getPlanbill() + "," + sc_ProcessWorkCardEntryList.get(i).getFprocessname() + "," + sc_ProcessWorkCardEntryList.get(i).getJobNumber());
            GxpgPlanReportNumberSaveList.add(gxpgPlanReportNumberSave);
        }
        try {
            dbManager.save(GxpgPlanReportNumberSaveList);
            if (showTip) {
                Toast.makeText(mContext, "计工暂存成功", Toast.LENGTH_LONG).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
            Log.e("jindi", e.toString());
        }

    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            LOG.e("-----------------------------------------------------");
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_add)
                        .setText("复制")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。

                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。


                SwipeMenuItem closeItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_close)
                        .setText("关闭") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem openItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_add)
                        .setText("开启") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(openItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    /**
     * 菜单点击监听。
     */
    private SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {


            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 处理按钮被点击。

                if (sc_ProcessWorkCardEntryList.get(adapterPosition).getIsOpen()) {
                    setItemAddWalkthrough(adapterPosition);
                } else {
                    Toast.makeText(mContext, "关闭工序无法复制", Toast.LENGTH_LONG).show();
                }
            } else if (menuPosition == 1) {

                setItemRemovedWalkthrough(adapterPosition);

            } else if (menuPosition == 2) {//关闭按钮
                sc_ProcessWorkCardEntryList.get(adapterPosition).setIsOpen(false);
                for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
                    if (sc_ProcessWorkCardEntryList.get(i).getFprocessname().equals(sc_ProcessWorkCardEntryList.get(adapterPosition).getFprocessname())) {
                        sc_ProcessWorkCardEntryList.get(i).setIsOpen(false);
                        sc_ProcessWorkCardEntryList.get(i).setFqty(0);
                        mMenuAdapter.notifyItemChanged(i);
                    }
                }
                //关闭工单行设置工号为000000 预排数量为0
                sc_ProcessWorkCardEntryList.get(adapterPosition).setJobNumber("000000");
                sc_ProcessWorkCardEntryList.get(adapterPosition).setFmustqty(0);

                mMenuAdapter.notifyItemChanged(adapterPosition);

                try {
                    WhereBuilder b = WhereBuilder.b();
                    b.and("style", "=", processWorkCardPlanEntryList.get(adapterPosition).getPlantbody());
                    b.and("processname", "=", sc_ProcessWorkCardEntryList.get(adapterPosition).getFprocessname());
                    dbManager.delete(GxpgSwitch.class, b);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                GxpgSwitch gxpgSwitch = new GxpgSwitch();
                gxpgSwitch.setStyle(processWorkCardPlanEntryList.get(adapterPosition).getPlantbody());
                gxpgSwitch.setProcessname(sc_ProcessWorkCardEntryList.get(adapterPosition).getFprocessname());
                gxpgSwitch.setOpen(false);
                hasCloseGx = true;

                try {
                    dbManager.save(gxpgSwitch);
                } catch (DbException e) {
                    e.printStackTrace();
                }

            } else if (menuPosition == 3)

            {
                sc_ProcessWorkCardEntryList.get(adapterPosition).setIsOpen(true);
                for (int i = 0; i < sc_ProcessWorkCardEntryList.size(); i++) {
                    if (sc_ProcessWorkCardEntryList.get(i).getFprocessname().equals(sc_ProcessWorkCardEntryList.get(adapterPosition).getFprocessname())) {
                        sc_ProcessWorkCardEntryList.get(i).setIsOpen(true);
                        mMenuAdapter.notifyItemChanged(i);
                    }
                }
                mMenuAdapter.notifyItemChanged(adapterPosition);

                try {
                    WhereBuilder b = WhereBuilder.b();
                    b.and("style", "=", processWorkCardPlanEntryList.get(adapterPosition).getPlantbody());
                    b.and("processname", "=", sc_ProcessWorkCardEntryList.get(adapterPosition).getFprocessname());
                    dbManager.delete(GxpgSwitch.class, b);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                GxpgSwitch gxpgSwitch = new GxpgSwitch();
                gxpgSwitch.setStyle(processWorkCardPlanEntryList.get(adapterPosition).getPlantbody());
                gxpgSwitch.setProcessname(sc_ProcessWorkCardEntryList.get(adapterPosition).getFprocessname());
                gxpgSwitch.setOpen(true);
                try {
                    dbManager.save(gxpgSwitch);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
            menuBridge.closeMenu();// 关闭被点击的菜单。
        }

    };

    public DbManager initDb() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                // 数据库的名字
                .setDbName("jindi")
                // 保存到指定路径
                // .setDbDir(new
                // File(Environment.getExternalStorageDirectory().getAbsolutePath()))
                // 数据库的版本号
                .setDbVersion(1)
                //设置数据库打开的监听
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启数据库支持多线程操作，提升性能，对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                //设置表创建的监听
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Log.i("jindi", "onTableCreated：" + table.getName());
                    }
                })
                // 数据库版本更新监听
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager arg0, int arg1, int arg2) {
                        LogUtil.e("数据库版本更新了！");
                    }
                });
        return x.getDb(daoConfig);
    }

    //获取今日目标数
    private void GetDayWorkCardPlanQtyBysuitID() {
        RequestParams params = new RequestParams(define.Net2 + define.GetDayWorkCardPlanQtyBysuitID);
        params.addParameter("FDeptID", dataPref.getString(define.SharedFDeptmentid, "0"));
        params.addParameter("FDate", Utils.getCurrentTime());
        params.addParameter("suitID", define.suitID);
        //String log=params.toString();
        //Utils.e("jindi",log);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                int TT = 0;
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                    if (result.contains("success")) {
                        LOG.e("今日目标数：" + result);
                        String todayTarget = result.substring(result.indexOf("ReturnMsg"), result.indexOf(",")).replace("ReturnMsg\":", "").replace("\"", "");
                        TT = (int)Double.parseDouble(todayTarget);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    TT = 0;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    TT = 0;
                }
                tv_todayTarget.setText("今日目标:" + TT);

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());
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


    private void againSCProcessOutPutReWriteBysuitID() {
        RequestParams params = new RequestParams(define.Net2 + define.ResetWorkCardPushData);
        params.addParameter("workcardID", selectWorkCardPlan.getFinterid());
        params.addParameter("suitID", define.suitID);
        params.setConnectTimeout(120000);
        params.setReadTimeout(120000);
        //Log.e("jindi", params.toString());

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e("jindi", result);
                if (result.contains("success")) {
                    Toast.makeText(mContext, "工序汇报校正成功", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(2000);
                        GetWorkCardQtyPassInfo();
                    } catch (InterruptedException e) {
                        return;
                    }

                } else {
                    Toast.makeText(mContext, "工序汇报校正失败,请重新校正。", Toast.LENGTH_LONG).show();
                }
                btn_report.setEnabled(true);
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());
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

    //发布派工指令
    private void dispatchingnumberPublish(int position) {
        RequestParams params = new RequestParams(define.WechatPostByFEmpID);
        params.addHeader("AppKey", define.appKey);
        params.addHeader("Nonce", define.nonce);
        String curTime = String.valueOf((new Date()).getTime());
        params.addHeader("CurTime", curTime);
        String checkSum = Utils.getCheckSum(define.appSecret, define.nonce, curTime);
        params.addHeader("CheckSum", checkSum);


        WechatPostByFEmpIDJson wechatPostByFEmpIDJson = new WechatPostByFEmpIDJson();
        wechatPostByFEmpIDJson.setFEmpID(String.valueOf(sc_ProcessWorkCardEntryList.get(position).getFempid()));
        wechatPostByFEmpIDJson.setTemplate_id(define.template_id);


        WechatPostByFEmpIDData wechatPostByFEmpIDData = new WechatPostByFEmpIDData();
        wechatPostByFEmpIDData.setFirst("您有一条新的派工单");
        wechatPostByFEmpIDData.setKeyword1("工厂型体:" + processWorkCardPlanEntryList.get(position).getPlantbody() + " " + processWorkCardPlanEntryList.get(position).getProcessname());
        wechatPostByFEmpIDData.setKeyword2("预排目标:" + sc_ProcessWorkCardEntryList.get(position).getFpreschedulingqty());

        //SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        //String dd = dff.format(new Date());

        try {
            wechatPostByFEmpIDData.setRemark("计划开工时间:" + Utils.dateConvert(selectWorkCardPlan.getPlanstarttime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        wechatPostByFEmpIDJson.getData().add(wechatPostByFEmpIDData);

        PulishData pulishData = new PulishData();
        pulishData.setWechatPostByFEmpIDData(wechatPostByFEmpIDJson);
        Gson g = new Gson();
        params.setAsJsonContent(true);
        params.setBodyContent(g.toJson(pulishData));
        //Log.e("jindi",g.toJson(pulishData));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("jindi", result);
                if (result.contains("200")) {

                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.getMessage());
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

    //发布计工结果
    private void reportPublish(int position) {
        RequestParams params = new RequestParams(define.WechatPostByFEmpID);
        params.addHeader("AppKey", define.appKey);
        params.addHeader("Nonce", define.nonce);
        String curTime = String.valueOf((new Date()).getTime());
        params.addHeader("CurTime", curTime);
        String checkSum = Utils.getCheckSum(define.appSecret, define.nonce, curTime);
        params.addHeader("CheckSum", checkSum);


        WechatPostByFEmpIDJson wechatPostByFEmpIDJson = new WechatPostByFEmpIDJson();
        wechatPostByFEmpIDJson.setFEmpID(String.valueOf(sc_ProcessWorkCardEntryList.get(position).getFempid()));
        wechatPostByFEmpIDJson.setTemplate_id(define.template_id);


        WechatPostByFEmpIDData wechatPostByFEmpIDData = new WechatPostByFEmpIDData();
        wechatPostByFEmpIDData.setFirst("工单汇报校对");
        wechatPostByFEmpIDData.setKeyword1("指令号:" + processWorkCardPlanEntryList.get(position).getPlanbill() + " 工厂型体:" + processWorkCardPlanEntryList.get(position).getPlantbody() + " " + processWorkCardPlanEntryList.get(position).getProcessname());
        wechatPostByFEmpIDData.setKeyword2("汇报数量:" + sc_ProcessWorkCardEntryList.get(position).getFqty());

        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String dd = dff.format(new Date());

        wechatPostByFEmpIDData.setRemark("汇报时间:" + dd);
        wechatPostByFEmpIDJson.getData().add(wechatPostByFEmpIDData);

        PulishData pulishData = new PulishData();
        pulishData.setWechatPostByFEmpIDData(wechatPostByFEmpIDJson);
        Gson g = new Gson();
        params.setAsJsonContent(true);
        params.setBodyContent(g.toJson(pulishData));
        //Log.e("jindi",g.toJson(pulishData));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("jindi", result);
                if (result.contains("200")) {

                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.getMessage());
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
}
