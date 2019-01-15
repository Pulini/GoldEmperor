package com.goldemperor.ProcessSend;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.HttpUtils;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Widget.ScrollListenerHorizontalScrollView;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.goldemperor.model.ProcessSendModel;
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
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;




/**
 * File Name : ProcessSendActvity
 * Created by : PanZX on  2018/4/25 11:08
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_process_send)
public class ProcessSendActvity extends Activity implements ScrollListenerHorizontalScrollView.OnScrollListener {

    @ViewInject(R.id.SV_ProcessSend)
    public ScrollListenerHorizontalScrollView SV_ProcessSend;

    @ViewInject(R.id.TRL_ProcessSend)
    private SmartRefreshLayout TRL_ProcessSend;

    @ViewInject(R.id.SMV_ProcessSend_Data)
    private SwipeMenuRecyclerView SMV_ProcessSend_Data;

    @ViewInject(R.id.ET_FMtono)
    private EditText ET_FMtono;

    @ViewInject(R.id.ET_FItemName)
    private EditText ET_FItemName;

    @ViewInject(R.id.ET_ProcessNum)
    private EditText ET_ProcessNum;

    @ViewInject(R.id.btn_search)
    private FancyButton btn_search;

    private ProcessSendAdapter PSA;
    private Gson mGson;

    List<ProcessSendModel> PSML=new ArrayList<>();
    private Activity mActivity;
    private String FMtono="";
    private String FItemName="";
    private String ProcessNum="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity=this;
        mGson = new Gson();
        initview();


    }

    private void initview() {
        SV_ProcessSend.setOnScrollListener(this);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TRL_ProcessSend.autoRefresh();
//                getdata();
            }
        });
        TRL_ProcessSend.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getdata();
            }
        });
        TRL_ProcessSend.setEnableLoadMore(false);
        SMV_ProcessSend_Data.setLayoutManager(new LinearLayoutManager(mActivity));// 布局管理器。
        SMV_ProcessSend_Data.addItemDecoration(new ListViewDecoration(mActivity));// 添加分割线。

        // 设置菜单创建器。
//        SMV_ProcessSend_Data.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
//        SMV_ProcessSend_Data.setSwipeMenuItemClickListener(menuItemClickListener);

        PSA = new ProcessSendAdapter(PSML, this);
        SMV_ProcessSend_Data.setAdapter(PSA);
        LOG.e("--------------------------");
//        getdata();
//        LOG.e("PSML.size():" + PSML.size());

    }


    private void getdata() {
        FMtono=ET_FMtono.getText().toString().trim();
        FItemName=ET_FItemName.getText().toString().trim();
        ProcessNum=ET_ProcessNum.getText().toString().trim();
        RequestParams RP = new RequestParams(SPUtils.getServerPath()+define.GetSfcOperPlanningInfo);
        RP.addQueryStringParameter("FMtono", FMtono);
        RP.addQueryStringParameter("FItemName", FItemName);
        LOG.e("Map:" + RP.toJSONString());
        HttpUtils.get(RP, (Finish, result) -> {
//                LOG.E("callback:" + result);
            if (Finish.equals(HttpUtils.Success)) {
                if (result != null) {
                    try {
                        result = URLDecoder.decode(result, "UTF-8");
//                            LOG.E("=====1======" + result + "=======1======");
                        result = result.substring(result.indexOf(">{") + 1, result.lastIndexOf("}<") + 1);
                        LOG.E("=====3======" + result + "=======3======");

                        JSONObject jsonObject = new JSONObject(result);
                        String data = jsonObject.getString("ReturnMsg");
                        PSML = mGson.fromJson(data, new TypeToken<List<ProcessSendModel>>() {
                        }.getType());
//                            for (ProcessSendModel processSendModel : PSML) {
//                                LOG.e("processSend:" + mGson.toJson(processSendModel));
//                            }
                        PSA.Updata(PSML);
                        LOG.e("PSML="+PSML.size());
                        TRL_ProcessSend.finishRefresh();
                     } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {

            }
        });
    }

    @Override
    public void onScroll(int scrollX) {
        for (int i = 0; i < PSA.ScrollViewList.size(); i++) {
            PSA.ScrollViewList.get(i).scrollTo(scrollX, SV_ProcessSend.getScrollY());
        }
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = mActivity.getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(mActivity)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除下游工序")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。

            }

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(mActivity)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setText("关闭")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

                SwipeMenuItem addItem2 = new SwipeMenuItem(mActivity)
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
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
                if (menuPosition == 0) {// 处理按钮被点击。

//                    final AlertDialog.Builder normalDialog =
//                            new AlertDialog.Builder(mActivity);
//                    normalDialog.setTitle("提示");
//                    normalDialog.setMessage("你确定要删除下游工序，删除后不可恢复");
//                    normalDialog.setPositiveButton("确定",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    DeleteScProcessWorkCard(showWorkCardPlan.get(adapterPosition));
//                                }
//                            });
//                    normalDialog.setNegativeButton("取消",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//
//                    normalDialog.show();

                } else if (menuPosition == 1) {

                }
                menuBridge.closeMenu();// 关闭被点击的菜单。
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {

                if (menuPosition == 0) {// 处理按钮被点击。
//                    SetWorkCardCloseStatus(showWorkCardPlan.get(adapterPosition).getOrderbill(),dataPref.getString(define.SharedUserId,""),"true");
                } else if (menuPosition == 1) {
//                   / SetWorkCardCloseStatus(showWorkCardPlan.get(adapterPosition).getOrderbill(),dataPref.getString(define.SharedUserId,""),"false");
                }
                menuBridge.closeMenu();// 关闭被点击的菜单。
            }
        }


    };

}
