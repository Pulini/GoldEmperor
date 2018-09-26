package com.goldemperor.PgdActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.goldemperor.DayWorkCardReport.model.SCCJLCCLXS_ReportModel;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.PdfActivity;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;


import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.ScrollListenerHorizontalScrollView;
import com.goldemperor.Widget.SublimePickerFragment;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




/**
 * Created by Nova on 2017/7/25.
 */

public class PgdActivity extends AppCompatActivity implements ScrollListenerHorizontalScrollView.OnScrollListener {


    private Context mContext;
    private Activity act;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    private SmartRefreshLayout refreshLayout;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private PgdAdapter mMenuAdapter;
    private List<WorkCardPlan> pgdWorkCardPlan;
    public static List<WorkCardPlan> showWorkCardPlan;
    public static WorkCardPlan selectWorkCardPlan;
    private TextView tv_showDate;
    private TextView tv_tip;
    private EditText searchEdit;
    private FancyButton btn_search;
    private FancyButton btn_today;
    private FancyButton btn_week;
    private FancyButton btn_month;
    private FancyButton btn_lastMonth;

    private FancyButton btn_calendar;

    private FancyButton btn_weiwai;

    private FancyButton btn_noReport;

    private FancyButton btn_isClose;

    private boolean isWeiWai = false;

    private boolean isClose = true;

    private boolean readyPush = false;

    private String StartTime;
    private String EndTime;

    public ScrollListenerHorizontalScrollView ScrollView;

//    public static NameListResult nameListResult;
    public static List<NameList> nameListResult;

    private DbManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pgd);
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

        StartTime = Utils.getCurrentYear() + "-" + Utils.getCurrentMonth() + "-" + "01";
        EndTime = Utils.getCurrentTime();

        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_showDate = (TextView) findViewById(R.id.tv_showDate);

        //初始化下拉刷新
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                LOG.e("---------------------onRefresh");
                getFDeptmentData();
            }
        });
        pgdWorkCardPlan = new ArrayList<WorkCardPlan>();
        showWorkCardPlan = new ArrayList<WorkCardPlan>();

        getFDeptmentData();

        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(this));// 添加分割线。

        // 设置菜单创建器。
        mMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        mMenuAdapter = new PgdAdapter(showWorkCardPlan, this);

        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mMenuRecyclerView.setAdapter(mMenuAdapter);

        btn_weiwai = (FancyButton) findViewById(R.id.btn_weiwai);
        btn_weiwai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWeiWai) {
                    isWeiWai = false;
                    btn_weiwai.setText("委外(未选)");
                    btn_weiwai.setBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    isWeiWai = true;
                    btn_weiwai.setText("委外(已选)");
                    btn_weiwai.setBackgroundColor(getResources().getColor(R.color.red));
                }
                tv_tip.setVisibility(View.VISIBLE);
//                StartTime = Utils.getCurrentYear() + "-" + Utils.getCurrentMonth() + "-" + "01";
//                EndTime = Utils.getCurrentTime();
                getFDeptmentData();
            }
        });
        btn_isClose = (FancyButton) findViewById(R.id.btn_isClose);
        btn_isClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClose) {
                    isClose = false;
                    btn_isClose.setText("不显示已关闭单子(未选)");
                    btn_isClose.setBackgroundColor(getResources().getColor(R.color.blue));
                } else {
                    isClose = true;
                     btn_isClose.setText("不显示已关闭单子(已选)");
                    btn_isClose.setBackgroundColor(getResources().getColor(R.color.red));
                }
                tv_tip.setVisibility(View.VISIBLE);
                getFDeptmentData();
            }
        });


        btn_noReport = (FancyButton) findViewById(R.id.btn_noReport);

        btn_noReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StartTime = Utils.getCurrentYear() + "-" + Utils.getCurrentMonth() + "-" + "01";
//                EndTime = Utils.getCurrentTime();
                tv_tip.setVisibility(View.VISIBLE);
                getFDeptmentData();

            }
        });

        btn_today = (FancyButton) findViewById(R.id.btn_today);
        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tip.setVisibility(View.VISIBLE);
                StartTime = Utils.getCurrentTime();
                EndTime = Utils.getCurrentTime();
                getFDeptmentData();

            }
        });


        btn_week = (FancyButton) findViewById(R.id.btn_week);
        btn_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tip.setVisibility(View.VISIBLE);
                StartTime = Utils.getBeginDayOfWeek().toString();
                EndTime = Utils.getCurrentTime();
                getFDeptmentData();
            }
        });

        btn_month = (FancyButton) findViewById(R.id.btn_month);
        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tip.setVisibility(View.VISIBLE);
                StartTime = Utils.getCurrentYear() + "-" + Utils.getCurrentMonth() + "-" + "01";
                EndTime = Utils.getCurrentTime();
                getFDeptmentData();
            }
        });

        btn_lastMonth = (FancyButton) findViewById(R.id.btn_lastMonth);
        btn_lastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tip.setVisibility(View.VISIBLE);
                String month = String.valueOf(Integer.valueOf(Utils.getCurrentMonth()) - 1 <= 0 ? 12 : Integer.valueOf(Utils.getCurrentMonth()) - 1);
                String year = String.valueOf(Integer.valueOf(Utils.getCurrentMonth()) - 1 <= 0 ? Integer.valueOf(Utils.getCurrentYear()) - 1 : Utils.getCurrentYear());
                StartTime = year + "-" + month + "-" + "01";
                EndTime = Utils.getCurrentTime();
                getFDeptmentData();
            }
        });

        searchEdit = (EditText) findViewById(R.id.searchEdit);
        btn_search = (FancyButton) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEdit.getText().toString().trim();
                if (!searchText.isEmpty()) {
                    tv_tip.setVisibility(View.VISIBLE);
                    getSearchData(searchText);
                }
            }
        });
        //日历回调函数
        final SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
            @Override
            public void onCancelled() {

            }

            @Override
            public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                                int hourOfDay, int minute,
                                                SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                                String recurrenceRule) {

                if (selectedDate != null) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    if (selectedDate.getType() == SelectedDate.Type.SINGLE) {
                        StartTime = String.valueOf(format.format(selectedDate.getStartDate().getTime()));
                        EndTime = String.valueOf(format.format(selectedDate.getStartDate().getTime()));
                    } else if (selectedDate.getType() == SelectedDate.Type.RANGE) {
                        StartTime = String.valueOf(format.format(selectedDate.getStartDate().getTime()));
                        EndTime = String.valueOf(format.format(selectedDate.getEndDate().getTime()));
                    }
                    tv_tip.setVisibility(View.VISIBLE);
                    getData(StartTime, EndTime);
                }

            }
        };

        btn_calendar = (FancyButton) findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(mFragmentCallback);
                Pair<Boolean, SublimeOptions> optionsPair = getOptions();

                if (!optionsPair.first) { // If options are not valid
                    Toast.makeText(mContext, "No pickers activated",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Valid options
                Bundle bundle = new Bundle();
                bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
                pickerFrag.setArguments(bundle);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getFragmentManager(), "SUBLIME_PICKER");
            }
        });


    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除下游工序")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
                SwipeMenuItem addItem2 = new SwipeMenuItem(mContext)
                        .setBackgroundColor(Color.parseColor("#0099ff"))
                        .setImage(R.drawable.btn_order)
                        .setText("查看详情")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem2); // 添加一个按钮到右侧菜单。

            }

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setText("关闭")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

                SwipeMenuItem addItem2 = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_add)
                        .setText("开启")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(addItem2); // 添加一个按钮到左侧菜单。

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
            final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
                if (menuPosition == 0) {// 处理按钮被点击。

                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(act);
                    normalDialog.setTitle("提示");
                    normalDialog.setMessage("你确定要删除下游工序，删除后不可恢复");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DeleteScProcessWorkCard(showWorkCardPlan.get(adapterPosition));
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

                } else if (menuPosition == 1) {
                    Intent i= new Intent(mContext, PdfActivity.class);
                    i.putExtra("ScWorkcardNo",showWorkCardPlan.get(adapterPosition).getOrderbill());
                    mContext.startActivity(i);
                }
                menuBridge.closeMenu();// 关闭被点击的菜单。
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {

                if (menuPosition == 0) {// 处理按钮被点击。
                    SetWorkCardCloseStatus(showWorkCardPlan.get(adapterPosition).getOrderbill(), dataPref.getString(define.SharedUserId, ""), "true");
                } else if (menuPosition == 1) {
                    SetWorkCardCloseStatus(showWorkCardPlan.get(adapterPosition).getOrderbill(), dataPref.getString(define.SharedUserId, ""), "false");
                }
                menuBridge.closeMenu();// 关闭被点击的菜单。
            }


        }

    };


    public void DeleteScProcessWorkCard(final WorkCardPlan wp) {
        RequestParams params = new RequestParams(define.Net2 + define.DeleteScProcessWorkCard);
        params.addQueryStringParameter("FWorkCardID", String.valueOf(wp.getFinterid()));
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e("jindi", result);
                if (result.contains("删除成功")) {
                    try {
                        WhereBuilder b = WhereBuilder.b();
                        b.and("orderbill", "=", wp.getOrderbill());
                        b.and("planbill", " = ", wp.getPlanbill());
                        dbManager.delete(GxpgPlanStatus.class, b);
                        Toast.makeText(mContext, "删除下游工序成功", Toast.LENGTH_LONG).show();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                } else {
                    String ReturnMsg = result.substring(result.indexOf("ReturnMsg"), result.indexOf(",")).replace("ReturnMsg\":", "").replace("\"", "");
                    Toast.makeText(mContext, ReturnMsg, Toast.LENGTH_LONG).show();
                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());
                Toast.makeText(mContext, "删除下游工序失败", Toast.LENGTH_LONG).show();
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

    public void SetWorkCardCloseStatus(String FNumber, String FUserID, String CloseStatus) {
        RequestParams params = new RequestParams(define.Net2 + define.SetWorkCardCloseStatus);
        params.addQueryStringParameter("FNumber", FNumber);
        params.addQueryStringParameter("FUserID", FUserID);
        params.addQueryStringParameter("CloseStatus", CloseStatus);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e("jindi", result);
                String msg = result.substring(result.indexOf("\"ReturnMsg\":\""), result.indexOf("\",\"ReturnType\"")).replaceFirst("\"ReturnMsg\":\"", "");
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
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

    //初始化数据库
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

    //日历设置方法
    // Validates & returns SublimePicker options
    Pair<Boolean, SublimeOptions> getOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;


        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;


        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        options.setCanPickDateRange(true);

        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }

    //根据时间按钮显示，会显示全部包括已经汇报完成的单子
    public void getData(final String StartTime, final String EndTime) {
        tv_tip.setText("数据载入中...");
        tv_showDate.setText("显示日期:" + StartTime + "到" + EndTime);

        RequestParams params = new RequestParams(define.Net2 + define.GetWorkCardInfoNew);
        params.setReadTimeout(60000);
        params.setConnectTimeout(60000);
        params.addQueryStringParameter("FStartTime", StartTime);
        params.addQueryStringParameter("EndTime", EndTime);
        params.addQueryStringParameter("FDeptID", dataPref.getString(define.SharedFDeptmentid, "none"));
        if (isClose) {
            params.addQueryStringParameter("IsClose", "true");
        } else {
            params.addQueryStringParameter("IsClose", "false");
        }
        Log.e("jindi", "GetPlanbyTime:" + params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                LOG.E("GetWorkCardInfoNew:" + result);

                pgdWorkCardPlan.clear();
                showWorkCardPlan.clear();
                List<String> filter = new ArrayList<String>();
                if (result != null && result.contains("[{")) {
                    result = "{\"data\":" + result.substring(result.indexOf("[{"), result.indexOf("}]")) + "}]}";
                    Gson g = new Gson();
                    PgdResult pgds = g.fromJson(result, PgdResult.class);
                    if (pgds.getData() != null) {
                        for (int i = 0; i < pgds.getData().size(); i++) {
                            //Log.e("jindi","deptid:"+pgds.getData().get(i).getFdeptid()+" Deptmentid:"+dataPref.getString(define.SharedFDeptmentid,"none"));
                            if ((!filter.contains(pgds.getData().get(i).getPlanbill()) || !filter.contains(pgds.getData().get(i).getOrderbill()) && pgds.getData().get(i).getOrderbill().indexOf("J") != 0) && String.valueOf(pgds.getData().get(i).getFdeptid()).equals(dataPref.getString(define.SharedFDeptmentid, "none"))) {
                                filter.add(pgds.getData().get(i).getPlanbill());
                                filter.add(pgds.getData().get(i).getOrderbill());
                                //重新遍历,设置尺码,和已入未入库数
                                int alreadyNumberCount = 0;
                                int noNumberCount = 0;
                                for (int j = 0; j < pgds.getData().size(); j++) {
                                    if (pgds.getData().get(j).getPlanbill().equals(pgds.getData().get(i).getPlanbill()) && pgds.getData().get(j).getOrderbill().equals(pgds.getData().get(i).getOrderbill())) {
                                        String[][] s = new String[1][2];
                                        s[0][0] = pgds.getData().get(j).getFsize();
                                        s[0][1] = String.valueOf(pgds.getData().get(j).getDispatchingnumber());
                                        pgds.getData().get(i).addSize(s);
                                        alreadyNumberCount += (pgds.getData().get(j).getAlreadynumber() == 0 ? 0 : pgds.getData().get(j).getAlreadynumber());

                                        noNumberCount += (pgds.getData().get(j).getNonumber() == 0 ? 0 : pgds.getData().get(j).getNonumber());
                                        try {
                                            List<GxpgPlanStatus> gxpgPlanStatusesList = dbManager.selector(GxpgPlanStatus.class).where("planbill", " = ", pgds.getData().get(i).getPlanbill()).and("orderbill", "=", pgds.getData().get(i).getOrderbill()).findAll();
                                            if (gxpgPlanStatusesList != null && gxpgPlanStatusesList.size() >= 1) {
                                                pgds.getData().get(i).setPlanStatus("已排");
                                            } else {
                                                pgds.getData().get(i).setPlanStatus("未排");
                                            }
                                        } catch (DbException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                pgds.getData().get(i).setAlreadynumberCount(alreadyNumberCount);
                                pgds.getData().get(i).setNonumberCount(noNumberCount);
                                if (isWeiWai) {
                                    if (pgds.getData().get(i).getFcanreportbynostockin()) {
                                        pgdWorkCardPlan.add(pgds.getData().get(i));
                                    }
                                } else {
                                    pgdWorkCardPlan.add(pgds.getData().get(i));
                                }


                            }
                        }
                        if (pgdWorkCardPlan.size() == 0) {
                            tv_tip.setText("暂无数据");
                        } else {
                            tv_tip.setVisibility(View.GONE);
                        }
                    } else {
                        tv_tip.setText("暂无数据");
                    }
                } else {
                    tv_tip.setText("暂无数据");
                }
                showWorkCardPlan.addAll(pgdWorkCardPlan);
                mMenuAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LOG.e("onError=" + ex.toString());
                tv_tip.setVisibility(View.VISIBLE);
                tv_tip.setText("数据载入失败,请检查网络");
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

    //搜索指令号获取数据
    public void getSearchData(final String searchText) {
        tv_tip.setText("数据载入中...");
        tv_showDate.setText("显示日期:" + StartTime + "到" + EndTime);
        RequestParams params = new RequestParams(define.Net2 + define.GetWorkCardInfoByMoNo);
        params.setReadTimeout(60000);
        params.addQueryStringParameter("MoNo", searchText.toUpperCase());
        params.addQueryStringParameter("suitID", define.suitID);
        params.addQueryStringParameter("FDeptID", dataPref.getString(define.SharedFDeptmentid, "0"));
        params.addQueryStringParameter("FProcessFlowID", "2");
        Log.e("jindi", params.toString());


        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                pgdWorkCardPlan.clear();
                showWorkCardPlan.clear();
                List<String> filter = new ArrayList<String>();
                if (result != null && result.contains("[{")) {
                    result = "{\"data\":" + result.substring(result.indexOf("[{"), result.indexOf("}]")) + "}]}";
                    Utils.e("jindi", result);
                    Gson g = new Gson();
                    PgdResult pgds = g.fromJson(result, PgdResult.class);
                    if (pgds.getData() != null) {
                        for (int i = 0; i < pgds.getData().size(); i++) {
                            LOG.e("Fdeptid="+pgds.getData().get(i).getFdeptid()+"Deptmentid="+dataPref.getString(define.SharedFDeptmentid, "none"));
                            if (!filter.contains(pgds.getData().get(i).getPlanbill())
                                    || !filter.contains(pgds.getData().get(i).getOrderbill())
                                    && pgds.getData().get(i).getOrderbill().indexOf("J") != 0
                                    && pgds.getData().get(i).getFgroup().contains("针车")
//                                    && String.valueOf(pgds.getData().get(i).getFdeptid()).equals(dataPref.getString(define.SharedFDeptmentid, "none"))
                                    ) {
                                filter.add(pgds.getData().get(i).getPlanbill());
                                filter.add(pgds.getData().get(i).getOrderbill());
                                //重新遍历,设置尺码
                                for (int j = 0; j < pgds.getData().size(); j++) {
                                    if (pgds.getData().get(j).getPlanbill().equals(pgds.getData().get(i).getPlanbill()) && pgds.getData().get(j).getOrderbill().equals(pgds.getData().get(i).getOrderbill())) {
                                        String[][] s = new String[1][2];
                                        s[0][0] = pgds.getData().get(j).getFsize();
                                        s[0][1] = String.valueOf(pgds.getData().get(j).getDispatchingnumber());
                                        pgds.getData().get(i).addSize(s);
                                    }
                                }
                                pgdWorkCardPlan.add(pgds.getData().get(i));
                            }
                        }
                        tv_tip.setVisibility(View.GONE);
                    } else {
                        tv_tip.setText("暂无数据");
                    }
                } else {
                    tv_tip.setText("暂无数据");
                }
                showWorkCardPlan.addAll(pgdWorkCardPlan);
                mMenuAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());
                tv_tip.setVisibility(View.VISIBLE);
                tv_tip.setText("数据载入失败,请检查网络");
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

    /**
     * Item点击监听，点击下推工序派工单。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if (!readyPush) {
                readyPush = true;
                //先获取当前帐号UserID再下推
                GetUserID(position);
            }
        }
    };

    @Override
    public void onScroll(int scrollX) {
        for (int i = 0; i < mMenuAdapter.ScrollViewList.size(); i++) {
            mMenuAdapter.ScrollViewList.get(i).scrollTo(scrollX, ScrollView.getScrollY());
        }
    }

    //获取部门员工数据
    public void getFDeptmentData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("FDeptmentID", dataPref.getString(define.SharedFDeptmentid, ""));
        map.put("FEmpNumber", "");
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.GetEmpByFnumber,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                Gson g = new Gson();
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.e("getFDeptmentData:"+result);
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if("success".equals(ReturnType)){
                                    nameListResult = g.fromJson(ReturnMsg, new TypeToken<List<NameList>>() {
                                    }.getType());
                                    for (NameList nameList : nameListResult) {
                                        LOG.e("name="+nameList.getEmp_Name()+"code="+nameList.getEmp_Code());
                                    }
                                    getData(StartTime, EndTime);
                                }else{
                                    tv_tip.setVisibility(View.VISIBLE);
                                    tv_tip.setText("员工数据载入失败:"+ReturnMsg);
                                    refreshLayout.finishRefresh();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            tv_tip.setVisibility(View.VISIBLE);
                            tv_tip.setText("员工数据载入失败,请检查网络");
                            refreshLayout.finishRefresh();
                        }
                    }
                });

    }

    //获取当前帐号UserID
    public void GetUserID(final int position) {
        RequestParams params = new RequestParams(define.Net2 + define.GetUserID);
        params.addQueryStringParameter("FEmpID", dataPref.getString(define.SharedEmpId, "0"));

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                    LOG.e("GetUserID=" + result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (result.contains("FUserID")) {
                    String FUserID = result.substring(result.indexOf("<FUserID>"), result.indexOf("</FUserID>"));
                    FUserID = FUserID.replaceAll("<FUserID>", "").replaceAll("</FUserID>", "");
                    dataEditor.putString(define.SharedUserId, FUserID);
                    dataEditor.commit();

                    SCWorkCard2SCProcessWorkCard(position);
                    Toast.makeText(mContext, "正在下推中...", Toast.LENGTH_LONG).show();
                } else {
                    readyPush = false;
                    Toast.makeText(mContext, "没有建立UseID,请联系管理员建立UserID", Toast.LENGTH_LONG).show();
                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.getMessage());
                readyPush = false;
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

    public void SCWorkCard2SCProcessWorkCard(int position) {
        selectWorkCardPlan = showWorkCardPlan.get(position);
        Gson g = new Gson();
        List<PushJsonCondition> pushJsonConditionList = new ArrayList<PushJsonCondition>();
        for (int i = 0; i < showWorkCardPlan.get(position).getSizeList().size(); i++) {
            PushJsonCondition pushJsonCondition = new PushJsonCondition();
            pushJsonCondition.setFEntryID(String.valueOf(showWorkCardPlan.get(position).getFentryid()));
            pushJsonCondition.setFInterID(String.valueOf(showWorkCardPlan.get(position).getFinterid()));
            pushJsonCondition.setFSize(String.valueOf(showWorkCardPlan.get(position).getSizeList().get(i)[0][0]));
            pushJsonConditionList.add(pushJsonCondition);
        }
        RequestParams params = new RequestParams(define.Net2 + define.SCWorkCard2SCProcessWorkCardBysuitID);
        params.addParameter("PushJsonCondition", g.toJson(pushJsonConditionList));
        params.addParameter("OrganizeID", dataPref.getString(define.SharedFOrganizeid, "1"));
        params.addParameter("BillTypeID", "3");
        params.addParameter("UserID", dataPref.getString(define.SharedUserId, "0"));
        params.addParameter("DeptmentID", dataPref.getString(define.SharedFDeptmentid, "0"));
        params.addParameter("suitID", define.suitID);

       LOG.E("下推:"+ params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e("jindi", "SCWorkCard2SCProcessWorkCardBysuitID:" + result);
                if (result.contains("success")) {

                    int finterid = Integer.valueOf(result.substring(result.indexOf("ReturnMsg"), result.indexOf(",")).replace("ReturnMsg\":", "").replace("\"", ""));

                    Bundle bundle = new Bundle();
                    bundle.putInt("finterid", finterid);
                    Intent intent = new Intent(act,
                            GxpgActivity.class);
                    intent.putExtras(bundle);
                    // 启动另一个Activity。
                    startActivityForResult(intent, 0);

                } else {
                    readyPush = false;

                    LemonHello.getErrorHello("提示", "派工单下推失败")
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
                Log.e("jindi", ex.toString());
                tv_tip.setVisibility(View.VISIBLE);
                tv_tip.setText("数据载入失败:" + ex.toString());

            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                readyPush = false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mMenuAdapter != null) {
            readyPush = false;
            Log.e("jindi", "onActivityResult");
            pgdWorkCardPlan.clear();
            showWorkCardPlan.clear();
            mMenuAdapter.notifyDataSetChanged();
            tv_tip.setVisibility(View.VISIBLE);
            String searchText = searchEdit.getText().toString().trim();
            if (!searchText.isEmpty()) {
                tv_tip.setVisibility(View.VISIBLE);
                getSearchData(searchText);
            }else{
                getData(StartTime, EndTime);
            }
        }

    }
}
