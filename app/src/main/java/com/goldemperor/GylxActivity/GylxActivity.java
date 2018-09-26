package com.goldemperor.GylxActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.PgdActivity.GxpgResult;
import com.goldemperor.PgdActivity.PgdAdapter;
import com.goldemperor.PgdActivity.PgdResult;
import com.goldemperor.PgdActivity.RouteEntry;
import com.goldemperor.PgdActivity.RouteResult;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;
import com.google.gson.Gson;

import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Nova on 2018/2/28.
 */

public class GylxActivity extends AppCompatActivity {

    private Context mContext;
    private Activity act;
    private EditText searchEdit;
    private FancyButton btn_search;
    private FancyButton btn_fill;
    private FancyButton btn_save;
    private TextView tv_tip;
    List<RouteEntry> RouteResultList;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private GylxAdapter mMenuAdapter;

    private SharedPreferences dataPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gylx);
        //隐藏标题栏
        getSupportActionBar().hide();
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        mContext = this;
        act = this;
        RouteResultList = new ArrayList<RouteEntry>();
        searchEdit = (EditText) findViewById(R.id.searchEdit);
        btn_search = (FancyButton) findViewById(R.id.btn_search);


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEdit.getText().toString().trim();
                if (!searchText.isEmpty()) {
                    getSearchData(searchText);
                }
            }
        });

        btn_fill = (FancyButton) findViewById(R.id.btn_fill);

        btn_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEdit.setText("GYLX1800016193");
                searchEdit.setSelection(searchEdit.getText().length());
                searchEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        });

        btn_save = (FancyButton) findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RouteResultList.size() > 0) {
                    SaveRoute();
                }
            }
        });

        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_tip.setVisibility(View.GONE);

        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(this));// 添加分割线。
        mMenuAdapter = new GylxAdapter(RouteResultList);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
        // 设置菜单创建器。
        mMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
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
                        .setBackground(R.drawable.selector_yellow)
                        .setImage(R.mipmap.ic_action_module_black)
                        .setText("修改")
                        .setTextColor(Color.RED)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。

                SwipeMenuItem addItem1 = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_module_black)
                        .setText("复制")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem1); // 添加一个按钮到右侧菜单。

                SwipeMenuItem addItem2 = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_purple)
                        .setImage(R.mipmap.ic_action_add)
                        .setText("新增")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem2); // 添加一个按钮到右侧菜单。

                SwipeMenuItem addItem3 = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem3); // 添加一个按钮到右侧菜单。

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
                if (menuPosition == 0) {// 处理按钮被点击。
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(act);
                    View view = getDialogView();
                    final EditText ed_processcode = (EditText) view.findViewById(R.id.tv_processcode);
                    ed_processcode.setText(RouteResultList.get(adapterPosition).getFprocessnumber());
                    ed_processcode.setSelection(ed_processcode.length());

                    final EditText ed_processname = (EditText) view.findViewById(R.id.tv_processname);
                    ed_processname.setText(RouteResultList.get(adapterPosition).getFprocessname());
                    ed_processname.setSelection(ed_processname.length());


                    final EditText ed_part = (EditText) view.findViewById(R.id.tv_part);
                    ed_part.setText(RouteResultList.get(adapterPosition).getPartname());
                    ed_part.setSelection(ed_part.length());

                    final EditText ed_note = (EditText) view.findViewById(R.id.tv_note);
                    ed_note.setText(RouteResultList.get(adapterPosition).getFnote());
                    ed_note.setSelection(ed_note.length());

                    final EditText ed_price = (EditText) view.findViewById(R.id.tv_price);
                    ed_price.setText(String.valueOf(RouteResultList.get(adapterPosition).getFprice()));
                    ed_price.setSelection(ed_price.length());

                    inputDialog.setTitle("修改工序").setView(view);
                    inputDialog.setNegativeButton("取消", null);
                    inputDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    float pricenumber = 0f;
                                    boolean notFloat = false;
                                    try {
                                        pricenumber = Float.parseFloat(ed_price.getText().toString());
                                    } catch (NumberFormatException e) {
                                        notFloat = true;
                                    }

                                    if (!notFloat) {
                                        if (ed_processcode.getText().toString().length() > 0 && ed_processname.getText().toString().length() > 0) {
                                            RouteResultList.get(adapterPosition).setFprocessnumber(ed_processcode.getText().toString());
                                            RouteResultList.get(adapterPosition).setFprocessname(ed_processname.getText().toString());
                                            RouteResultList.get(adapterPosition).setFprice(pricenumber);
                                            RouteResultList.get(adapterPosition).setPartname(ed_part.getText().toString());
                                            RouteResultList.get(adapterPosition).setFnote(ed_note.getText().toString());

                                            mMenuAdapter.notifyItemChanged(adapterPosition);
                                        } else {
                                            Toast.makeText(mContext, "请工序编号和名称不能为空", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(mContext, "请输入正确的单价", Toast.LENGTH_LONG).show();
                                    }

                                }
                            }).show();
                } else if (menuPosition == 1) {// 处理按钮被点击。
                    RouteEntry routeEntryTemp = (RouteEntry) RouteResultList.get(adapterPosition).clone();
                    routeEntryTemp.setFid(0);
                    routeEntryTemp.setFprocessnumber(RouteResultList.get(adapterPosition).getFprocessnumber() + "copy");
                    //routeEntryTemp.setF
                    RouteResultList.add(adapterPosition + 1, routeEntryTemp);

                    mMenuAdapter.notifyItemInserted(adapterPosition + 1);

                } else if (menuPosition == 2) {
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(act);
                    View view = getDialogView();
                    final EditText ed_processcode = (EditText) view.findViewById(R.id.tv_processcode);

                    final EditText ed_processname = (EditText) view.findViewById(R.id.tv_processname);

                    final EditText ed_price = (EditText) view.findViewById(R.id.tv_price);

                    final EditText ed_part = (EditText) view.findViewById(R.id.tv_part);

                    final EditText ed_note = (EditText) view.findViewById(R.id.tv_note);
                    ed_price.setText("0");

                    inputDialog.setTitle("新增工序").setView(view);
                    inputDialog.setNegativeButton("取消", null);
                    inputDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    float pricenumber = 0f;
                                    boolean notFloat = false;
                                    try {
                                        pricenumber = Float.parseFloat(ed_price.getText().toString());
                                    } catch (NumberFormatException e) {
                                        notFloat = true;
                                    }

                                    if (!notFloat) {
                                        if (ed_processcode.getText().toString().length() > 0 && ed_processname.getText().toString().length() > 0) {
                                            RouteEntry routeEntry = (RouteEntry) RouteResultList.get(adapterPosition).clone();

                                            routeEntry.setFprocessnumber(ed_processcode.getText().toString());
                                            routeEntry.setFprocessname(ed_processname.getText().toString());
                                            routeEntry.setFprice(pricenumber);
                                            routeEntry.setPartname(ed_part.getText().toString());
                                            routeEntry.setFnote(ed_note.getText().toString());
                                            routeEntry.setFid(0);

                                            RouteResultList.add(adapterPosition + 1, routeEntry);
                                            mMenuAdapter.notifyItemInserted(adapterPosition + 1);
                                        } else {
                                            Toast.makeText(mContext, "请工序编号和名称不能为空", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(mContext, "请输入正确的单价", Toast.LENGTH_LONG).show();
                                    }

                                }
                            }).show();
                } else if (menuPosition == 3) {

                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(act);
                    normalDialog.setTitle("提示");
                    normalDialog.setMessage("你确定要删除这条数据");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (RouteResultList.get(adapterPosition).getFid() == 0) {
                                        RouteResultList.remove(adapterPosition);
                                        mMenuAdapter.notifyItemRemoved(adapterPosition);
                                    } else {
                                        getControl(adapterPosition, "401040303");
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
            }
            menuBridge.closeMenu();;// 关闭被点击的菜单。

        }

    };

    private View getDialogView() {
        // TODO 动态添加布局(xml方式)
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                70, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater3 = LayoutInflater.from(act);
        View view = inflater3.inflate(R.layout.gylx_dialog, null);
        view.setLayoutParams(lp);
        return view;

    }

    //搜索指令号获取数据
    public void getSearchData(final String searchText) {
        tv_tip.setVisibility(View.VISIBLE);
        tv_tip.setText("数据载入中...");
        RouteResultList.clear();
        RequestParams params = new RequestParams(define.Net2 + define.GetPrdRouteInfo);
        params.setReadTimeout(60000);
        params.addQueryStringParameter("FBillNo", searchText.toUpperCase());
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                result = result.substring(result.indexOf(">{") + 1, result.lastIndexOf("}<") + 1);
                LOG.e("工艺路线____去除XML包裹=" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String ReturnType = jsonObject.getString("ReturnType");
                    String ReturnMsg= jsonObject.getString("ReturnMsg");
                    LOG.e("工艺路线____截取ReturnType=" + ReturnType);
                    LOG.e("工艺路线____截取ReturnMsg=" + ReturnMsg);
                    if (ReturnType.contains("error")) {
                        tv_tip.setText("没有查询到数据");
                    } else {
                        tv_tip.setVisibility(View.GONE);
                        result = "{\"data\":" + ReturnMsg+ "}";
                        LOG.e("工艺路线____拼接json=" + result);
                        Gson g = new Gson();
                        RouteResult gylx = g.fromJson(result, RouteResult.class);
                        if (gylx != null && gylx.getData() != null) {
                            for (int i = 0; i < gylx.getData().size(); i++) {
                                RouteResultList.add(gylx.getData().get(i));

                            }
                            mMenuAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    public void SaveRoute() {
        Gson g = new Gson();
        Utils.e("jindi", g.toJson(RouteResultList));

        RequestParams params = new RequestParams(define.Net2 + define.SavePrdRouteEntry);
        params.setReadTimeout(60000);
        params.addParameter("PushJsonCondition", g.toJson(RouteResultList));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e("jindi", result);
                if (result.contains("success")) {
                    String searchText = searchEdit.getText().toString().trim();
                    if (!searchText.isEmpty()) {
                        getSearchData(searchText);
                    }
                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "保存失败", Toast.LENGTH_LONG).show();
                }
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

    private void DeletePrdRouteEntryByFID(final int adapterPosition, long fid) {

        RequestParams params = new RequestParams(define.Net2 + define.DeletePrdRouteEntryByFID);
        params.setReadTimeout(60000);
        params.addParameter("FID", fid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e("jindi", result);
                if (result.contains("success")) {
                    RouteResultList.remove(adapterPosition);
                    mMenuAdapter.notifyItemRemoved(adapterPosition);
                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "删除失败", Toast.LENGTH_LONG).show();
                }


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

    private void getControl(final int adapterPosition, final String controlID) {
        RequestParams params = new RequestParams(define.Net2 + define.IsHaveControl);
        params.addQueryStringParameter("OrganizeID", "1");
        params.addQueryStringParameter("empID", dataPref.getString(define.SharedEmpId, define.NONE));
        params.addQueryStringParameter("controlID", controlID);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                if (result.contains("false")) {

                    LemonHello.getErrorHello("提示", "你没有权限,请联系管理员开通权限")
                            .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                }
                            })).show(act);
                } else if (result.contains("true")) {
                    if (controlID.equals("401040303")) {
                        DeletePrdRouteEntryByFID(adapterPosition, RouteResultList.get(adapterPosition).getFid());
                    }
                } else {
                    if (controlID.equals("401040303")) {
                        Toast.makeText(mContext, "你没有删除工艺路线权限", Toast.LENGTH_LONG).show();
                    }

                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());

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
}
