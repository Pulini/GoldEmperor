package com.goldemperor.PzActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.DayWorkCardReport.model.SCCJLCCLXS_ReportModel;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.define;
import com.goldemperor.PgdActivity.RouteEntry;
import com.goldemperor.PgdActivity.Sc_WorkPlanMaterial;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.ScrollListenerHorizontalScrollView;
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.goldemperor.Widget.fancybuttons.FancyButton;

import static com.goldemperor.PzActivity.PgdActivity.selectWorkCardPlan;

/**
 * Created by Nova on 2017/10/28.
 */

public class YCListActivity extends AppCompatActivity implements ScrollListenerHorizontalScrollView.OnScrollListener {

    List<Sc_WorkCardAbnormity> Sc_WorkCardAbnormityList = new ArrayList<Sc_WorkCardAbnormity>();
    private SmartRefreshLayout refreshLayout;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private FancyButton btn_zj;
    private Context mContext;
    private Activity act;
    private TextView tv_tip;
    public ScrollListenerHorizontalScrollView ScrollView;
    public static YCListAdapter mMenuAdapter;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;

    private List<String[]> nameList = new ArrayList<String[]>();
    public static int YcCount = 0;

    public static List<AbnormityModel> abnormityModel = new ArrayList<AbnormityModel>();
    public static List<String[]> selectAbnormity = new ArrayList<String[]>();
    private String MyEmpID = "";
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_yclist);
        //隐藏标题栏
        getSupportActionBar().hide();
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        MyEmpID = dataPref.getString(define.SharedEmpId, "");
        LOG.e("EMPID=" + MyEmpID);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        ScrollView = (ScrollListenerHorizontalScrollView) findViewById(R.id.ScrollView);
        ScrollView.setOnScrollListener(this);
        act = this;
        mContext = this;
        btn_zj = (FancyButton) findViewById(R.id.btn_zj);
        btn_zj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, com.goldemperor.PzActivity.ZjActivity.class);
                startActivityForResult(i, 0);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                LOG.e("---------------------onRefresh");
                tv_tip.setVisibility(View.GONE);
                getData();
            }
        });
        refreshLayout.setEnableLoadMore(false);
        getData();
        getAbnormityData();
        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        // 设置菜单创建器。
        mMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);


        mMenuAdapter = new YCListAdapter(Sc_WorkCardAbnormityList, this);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
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
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。

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
            if (menuPosition == 0) {// 处理按钮被点击。
                LOG.e("MyEmpID=" + MyEmpID);
                LOG.e("FempID=" + Sc_WorkCardAbnormityList.get(adapterPosition).getFEmpID());
                if (MyEmpID.equals(String.valueOf(Sc_WorkCardAbnormityList.get(adapterPosition).getFEmpID()))) {
                    DeleteByFInterID(String.valueOf(Sc_WorkCardAbnormityList.get(adapterPosition).getFInterID()));
                } else {
                    Toast.makeText(getApplicationContext(), "只能删除自己生成的品质检测数据", Toast.LENGTH_LONG).show();
                }
            }
            menuBridge.closeMenu();// 关闭被点击的菜单。
        }


    };
    String recheckText = "返工合格";
    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(final int position) {
            String[] singleChoiceItems = {"返工合格", "报废"};
            int defaultSelectedIndex = 0;//单选框默认值：从0开始

            //创建对话框
            new AlertDialog.Builder(mContext)
                    .setTitle("复审")//设置对话框标题
                    .setIcon(android.R.drawable.ic_dialog_info)//设置对话框图标
                    .setSingleChoiceItems(singleChoiceItems, defaultSelectedIndex, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                recheckText = "返工合格";
                            } else {
                                recheckText = "报废";
                            }
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ReCheckAbnormity(String.valueOf(Sc_WorkCardAbnormityList.get(position).getFInterID()), recheckText);

                            dialog.dismiss();
                        }
                    })//设置对话框[肯定]按钮
                    .setNegativeButton("取消", null)//设置对话框[否定]按钮
                    .show();
        }
    };

    public void ReCheckAbnormity(String finterid, String textString) {
        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", finterid);
        map.put("textString", textString);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForMesServer,
                define.ReCheckAbnormity,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("DeleteByFInterID=" + result);
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                LOG.E("ReturnMsg=" + ReturnMsg);
                                if ("success".equals(ReturnType)) {
                                    getData();
//                                    act.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            getData();
//                                        }
//                                    });
                                } else {
                                    Toast.makeText(mContext, ReturnMsg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "数据解析异常", Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "数据解码异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "接口返回空", Toast.LENGTH_SHORT).show();
                        }
                        mMenuAdapter.notifyDataSetChanged();
                    }
                });

//        RequestParams params = new RequestParams(define.Net1 + define.ReCheckAbnormity);
//        params.addQueryStringParameter("FInterID", finterid);
//        params.addQueryStringParameter("textString", textString);
//        Log.e("jindi", params.toString());
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    result = URLDecoder.decode(result, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                Log.e("jindi", result);
//                getData();
//            }
//
//            //请求异常后的回调方法
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.e("jindi", ex.toString());
//                Toast.makeText(mContext, "复审失败", Toast.LENGTH_LONG).show();
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

    public void DeleteByFInterID(String finterid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", finterid);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForMesServer,
                define.DeleteByFInterID,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("DeleteByFInterID=" + result);
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                LOG.E("ReturnMsg=" + ReturnMsg);
                                if ("success".equals(ReturnType)) {
                                    getData();
//                                    act.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            getData();
//                                        }
//                                    });
                                } else {
                                    Toast.makeText(mContext, ReturnMsg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "数据解析异常", Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "数据解码异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "接口返回空", Toast.LENGTH_SHORT).show();
                        }
                        mMenuAdapter.notifyDataSetChanged();
                    }
                });


//        RequestParams params = new RequestParams(define.Net1 + define.DeleteByFInterID);
//        params.addQueryStringParameter("FInterID", finterid);
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    result = URLDecoder.decode(result, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                Log.e("jindi", result);
//                act.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        getData();
//                    }
//                });
//
//            }
//
//            //请求异常后的回调方法
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.e("jindi", ex.toString());
//                Toast.makeText(mContext, "删除失败", Toast.LENGTH_LONG).show();
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

    @Override
    public void onScroll(int scrollX) {
        for (int i = 0; i < mMenuAdapter.ScrollViewList.size(); i++) {
            mMenuAdapter.ScrollViewList.get(i).scrollTo(scrollX, ScrollView.getScrollY());
        }
    }

    private void getData() {
        tv_tip.setText("数据载入中...");
        tv_tip.setVisibility(View.VISIBLE);
        Sc_WorkCardAbnormityList.clear();
        nameList.clear();

        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", String.valueOf(selectWorkCardPlan.getFinterid()));
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.GetWorkCardAbnormity,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {

                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("品质检查：" + result);

                                // {"ReturnMsg":"没有品质异常信息 ","ReturnType":"error"}
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if ("success".equals(ReturnType)) {
                                    List<Integer> is = new ArrayList<>();
                                    int EmpId = Integer.parseInt(dataPref.getString(define.SharedEmpId, ""));
                                    LOG.e("EmpId：" + EmpId);
                                    List<Sc_WorkCardAbnormity> list = new Gson().fromJson(ReturnMsg, new TypeToken<List<Sc_WorkCardAbnormity>>() {
                                    }.getType());
                                    LOG.e("list：" + list.size());
                                    if (list != null) {
                                        Sc_WorkCardAbnormityList.addAll(list);
                                        for (int i = 0; i < list.size(); i++) {
                                            LOG.e(i + "FEmpID：" + Sc_WorkCardAbnormityList.get(i).getFEmpID());
                                            if (EmpId == Sc_WorkCardAbnormityList.get(i).getFEmpID()) {
                                                Sc_WorkCardAbnormityList.get(i).setFname(dataPref.getString(define.SharedUser, ""));
                                                Sc_WorkCardAbnormityList.get(i).setFdeptmentName(dataPref.getString(define.SharedDeptmentName, ""));
                                                LOG.e("__1__：" + i);
                                                is.add(i);
                                                if (i == list.size() - 1) {
                                                    mMenuAdapter.notifyDataSetChanged();
                                                }
                                            } else {
                                                for (int j = 0; j < nameList.size(); j++) {
                                                    if (nameList.get(j)[0].equals(String.valueOf(Sc_WorkCardAbnormityList.get(i).getFEmpID()))) {
                                                        Sc_WorkCardAbnormityList.get(i).setFname(nameList.get(j)[1]);
                                                        Sc_WorkCardAbnormityList.get(i).setFdeptmentName(nameList.get(j)[2]);
                                                        LOG.e("__2__：" + i);
                                                        is.add(i);
                                                        if (i == list.size() - 1) {
                                                            mMenuAdapter.notifyDataSetChanged();
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                        LOG.e("is：" + is.size());
                                        for (Integer i : is) {
                                            LOG.e("-------" + i + "-------");
                                            GetUserID(i);
                                        }
                                        tv_tip.setVisibility(View.GONE);
                                    }
                                } else {
                                    tv_tip.setText(ReturnMsg);
                                }
                            } catch (JSONException e) {
                                tv_tip.setText("数据解析异常");
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                tv_tip.setText("数据解码异常");
                            }
                        } else {
                            tv_tip.setText("接口返回空");
                        }
                        refreshLayout.finishRefresh();
                    }
                });

    }

    public void GetUserID(final int position) {
        HashMap<String, String> map = new HashMap<>();
        map.put("FEmpID", String.valueOf(Sc_WorkCardAbnormityList.get(position).getFEmpID()));
        WebServiceUtils.WEBSERVER_NAMESPACE = define.jindishoes;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ERPForWeixinServer,
                define.GetUserID2,
                map, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            LOG.e(position + "GetUserID" + result);
                            if (result.contains("FUserID")) {
                                String Fname = result.substring(result.indexOf("<Fname>"), result.indexOf("</Fname>"));
                                Fname = Fname.replaceAll("<Fname>", "").replaceAll("</Fname>", "");
                                Sc_WorkCardAbnormityList.get(position).setFname(Fname);

                                String FDeptmentName = result.substring(result.indexOf("<FDeptmentName>"), result.indexOf("</FDeptmentName>"));
                                FDeptmentName = FDeptmentName.replaceAll("<FDeptmentName>", "").replaceAll("</FDeptmentName>", "");
                                Sc_WorkCardAbnormityList.get(position).setFdeptmentName(FDeptmentName);
                                String[] name = new String[3];
                                name[0] = String.valueOf(Sc_WorkCardAbnormityList.get(position).getFEmpID());
                                name[1] = Fname;
                                name[2] = FDeptmentName;
                                nameList.add(name);
                                mMenuAdapter.notifyDataSetChanged();
                            }
                        } else {

                        }
                    }
                });

    }

    private void getAbnormityData() {
        abnormityModel.clear();
        selectAbnormity.clear();

        HashMap<String, String> map = new HashMap<>();
        String MethodName = "";
        if (selectWorkCardPlan.getFgroup().contains("针车")) {
            MethodName = define.GetAbnormityByName;
            map.put("FName", "针车");
        } else {
            MethodName = define.GetAbnormityByID;
            map.put("paramString", "39,40,41,42,43");
        }
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForMesServer,
                MethodName,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("Abnormity=" + result);

                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                LOG.E("ReturnMsg=" + ReturnMsg);
                                if ("success".equals(ReturnType)) {
                                    List<AbnormityModel> gylx = new Gson().fromJson(ReturnMsg, new TypeToken<List<AbnormityModel>>() {
                                    }.getType());
                                    for (int i = 0; i < gylx.size(); i++) {
                                        abnormityModel.add(gylx.get(i));
                                        String[] temp = new String[4];
                                        temp[0] = gylx.get(i).getFName();
                                        temp[1] = String.valueOf(gylx.get(i).getFItemID());
                                        temp[2] = "无";
                                        temp[3] = "未选";
                                        selectAbnormity.add(temp);
                                    }
                                } else {
                                    Toast.makeText(mContext, ReturnMsg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(mContext, "数据解析异常", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "数据解码异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            tv_tip.setText("暂无数据");
                        }
                        mMenuAdapter.notifyDataSetChanged();
                    }
                });

//        RequestParams params;
//        if (selectWorkCardPlan.getFgroup().contains("针车")) {
//            params = new RequestParams(define.Net1 + define.GetAbnormityByName);
//            params.addQueryStringParameter("FName", "针车");
//        } else {
//            params = new RequestParams(define.Net1 + define.GetAbnormityByID);
//            params.addQueryStringParameter("paramString", "39,40,41,42,43");
//        }
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Gson g = new Gson();
//                ReasonResult reasonResult = g.fromJson(result, ReasonResult.class);
//
//                if (reasonResult.getData() != null) {
//                    for (int i = 0; i < reasonResult.getData().size(); i++) {
//                        abnormityModel.add(reasonResult.getData().get(i));
//                        String[] temp = new String[4];
//                        temp[0] = reasonResult.getData().get(i).getFname();
//                        temp[1] = String.valueOf(reasonResult.getData().get(i).getFitemID());
//                        temp[2] = "无";
//                        temp[3] = "未选";
//                        selectAbnormity.add(temp);
//                    }
//                }
//            }
//
//
//            //请求异常后的回调方法
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mMenuAdapter != null) {
            getData();
        }

    }
}
