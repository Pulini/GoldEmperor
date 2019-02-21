package com.goldemperor.DayWorkCardReport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.goldemperor.DayWorkCardReport.adapter.SCCJLCCLXS_ReportAdapter;
import com.goldemperor.DayWorkCardReport.model.SCCJLCCLXS_ReportModel;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.ScrollListenerHorizontalScrollView;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.panzx.pulini.Bootstrap.BootstrapDropDown;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



/**
 * File Name : SCCJLCCLXS_ReportActivity
 * Created by : PanZX on  2018/5/18 14:51
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：生产车间楼层产量
 */
@ContentView(R.layout.activity_sccjlcclxs_report)
public class SCCJLCCLXS_ReportActivity extends Activity implements ScrollListenerHorizontalScrollView.OnScrollListener {

    @ViewInject(R.id.btn_select_time)
    public FancyButton btn_select_time;

    @ViewInject(R.id.TV_time)
    public TextView TV_time;

    @ViewInject(R.id.SV_DayWork)
    public ScrollListenerHorizontalScrollView SV_DayWork;

    @ViewInject(R.id.TRL_DayWork)
    public SmartRefreshLayout TRL_DayWork;

    @ViewInject(R.id.SMV_DayWork_Data)
    private SwipeMenuRecyclerView SMV_DayWork_Data;

    @ViewInject(R.id.BDD_Workshop)
    private BootstrapDropDown BDD_Workshop;


    private Gson mGson;
    private Activity mActivity;
    private List<SCCJLCCLXS_ReportModel> DWCM = new ArrayList<>();
    private SCCJLCCLXS_ReportAdapter DWCA;
    private TimePickerView pvTime;
    private String[] WorkshopName = {"裁断车间", "针车车间", "成型车间","制底车间", "印业车间", "其他"};
    private int  Workshop=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        mGson = new Gson();
        initview();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        LOG.e("choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }



    private void initview() {
        TV_time.setText(getTime(new Date()));
        BDD_Workshop.setDropdownData(WorkshopName);
        BDD_Workshop.setText(WorkshopName[Workshop-1]);
        BDD_Workshop.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View v, int id) {
                BDD_Workshop.setText(WorkshopName[id]);
                Workshop=id+1;
                TRL_DayWork.autoRefresh();
//                getdata();
            }
        });
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                TV_time.setText(getTime(date));
                TRL_DayWork.autoRefresh();
//                getdata();
            }
        }).setType(new boolean[]{true, true, true, true, true, true})
                .build();
        btn_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
        SV_DayWork.setOnScrollListener(this);
        //初始化下拉刷新
        TRL_DayWork.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getdata();
            }
        });
        TRL_DayWork.setEnableLoadMore(false);

        SMV_DayWork_Data.setLayoutManager(new LinearLayoutManager(mActivity));// 布局管理器。
        SMV_DayWork_Data.addItemDecoration(new ListViewDecoration(mActivity));// 添加分割线。

        // 设置菜单创建器。
//        SMV_ProcessSend_Data.setSwipeMenuCreator(swipeMenuCreator);


        DWCA = new SCCJLCCLXS_ReportAdapter(DWCM, this);
        DWCA.setOnItemClickListener(new SCCJLCCLXS_ReportAdapter.OnItemClickListener() {
            @Override
            public void Click(int position) {
                LOG.e("position="+position);
                Intent intent=new Intent(SCCJLCCLXS_ReportActivity.this,SCCJLCCLXS_ReportDetailedActivity.class);
                intent.putExtra("DepartmentID",DWCM.get(position).getFdeptid());
                startActivity(intent);
            }
        });
        SMV_DayWork_Data.setAdapter(DWCA);
        TRL_DayWork.autoRefresh();
    }

    private void getdata() {
        BDD_Workshop.setEnabled(false);
        btn_select_time.setEnabled(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("Exec_TodayDateTime", TV_time.getText().toString());
        map.put("Exec_Where", "where t.FWorkShopID = "+Workshop);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.jindishoes;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath()+define.ERPForSupplierServer,
                define.GetPrdShopDayReport,
                map,
                result -> {
                    BDD_Workshop.setEnabled(true);
                    btn_select_time.setEnabled(true);
                    TRL_DayWork.finishRefresh();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("result=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if("success".equals(ReturnType)){
                                DWCM = mGson.fromJson(ReturnMsg, new TypeToken<List<SCCJLCCLXS_ReportModel>>() {
                                }.getType());
                            }else{
                                DWCM.clear();
                                Toast.makeText(mActivity,ReturnMsg, Toast.LENGTH_SHORT).show();
                            }
                            LOG.E("PSML=" + DWCM.size());
                            DWCA.Updata(DWCM);
                        } catch (JSONException e) {
                            Toast.makeText(mActivity,"数据解析异常", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Toast.makeText(mActivity,"数据解码异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mActivity,"接口访问异常", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onScroll(int scrollX) {
        for (int i = 0; i < DWCA.ScrollViewList.size(); i++) {
            DWCA.ScrollViewList.get(i).scrollTo(scrollX, SV_DayWork.getScrollY());
        }
    }
}
