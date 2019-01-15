package com.goldemperor.DayWorkCardReport.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;


import com.goldemperor.DayWorkCardReport.adapter.SCCJLCCLXS_ReportDetailedAdapter;
import com.goldemperor.DayWorkCardReport.model.SCCJLCCLXS_ReportDetailedModel;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.goldemperor.Widget.photoview.PhotoView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
 * File Name : SCCJLCCLXS_ReportDetailedActivity
 * Created by : PanZX on  2018/6/19 16:43
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_sccjlcclxs_report_detailed)
public class SCCJLCCLXS_ReportDetailedActivity extends Activity {

    @ViewInject(R.id.btn_select_time)
    public FancyButton btn_select_time;

    @ViewInject(R.id.TV_time)
    public TextView TV_time;

    @ViewInject(R.id.TRL_DayWork)
    public SmartRefreshLayout TRL_DayWork;

    @ViewInject(R.id.SMV_DayWork_Data)
    private SwipeMenuRecyclerView SMV_DayWork_Data;
    @ViewInject(R.id.PV_Show)
    private PhotoView PV_Show;


    private Gson mGson;
    private Activity mActivity;
    private List<SCCJLCCLXS_ReportDetailedModel> DWCDM = new ArrayList<>();
    private SCCJLCCLXS_ReportDetailedAdapter DWCA;
    private TimePickerView pvTime;
    private String DepartmentID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        mGson = new Gson();
         DepartmentID = getIntent().getStringExtra("DepartmentID");
        initview();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        LOG.e("choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    private void initview() {
        TV_time.setText(getTime(new Date()));
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
        TRL_DayWork.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getdata();
            }
        });
        TRL_DayWork.setEnableLoadMore(false);
        SMV_DayWork_Data.setLayoutManager(new LinearLayoutManager(mActivity));// 布局管理器。
        SMV_DayWork_Data.addItemDecoration(new ListViewDecoration(mActivity));// 添加分割线。

        DWCA = new SCCJLCCLXS_ReportDetailedAdapter(DWCDM);
        DWCA.setOnItemClickListener(new SCCJLCCLXS_ReportDetailedAdapter.OnItemClickListener() {
            @Override
            public void ItemClick(int position) {
                Intent intent=new Intent(SCCJLCCLXS_ReportDetailedActivity.this,SCCJLCCLXS_ReportDetailedSizeActivity.class);
                intent.putExtra("DepartmentID",DepartmentID);
                intent.putExtra("FsrcicmointerID",DWCDM.get(position).getFsrcicmointerid()+"");
                LOG.e("DepartmentID"+DepartmentID);
                LOG.e("FsrcicmointerID"+DWCDM.get(position).getFsrcicmointerid());
                startActivity(intent);
            }

            @Override
            public void PhotoClick(ImageView view) {
                Drawable d=  view.getDrawable();
                if(d!=null){
                    PV_Show.setImageDrawable(d);
                    PV_Show.setVisibility(View.VISIBLE);
                }
            }

        });
        PV_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PV_Show.getVisibility() == View.VISIBLE) {
                    PV_Show.setVisibility(View.GONE);
                }
            }
        });
        SMV_DayWork_Data.setAdapter(DWCA);
        TRL_DayWork.autoRefresh();
    }

    private void getdata() {
        btn_select_time.setEnabled(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("Exec_TodayDateTime", TV_time.getText().toString());
        map.put("Exec_Where", "where t.FDepartmentID = " + DepartmentID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.jindishoes;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath()+define.ERPForSupplierServer,
                define.GetDayOutPutLevelOneReport,
                map,
                result -> {
                    btn_select_time.setEnabled(true);
                    TRL_DayWork.finishRefresh();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("result=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                LOG.E("ReturnMsg=" + ReturnMsg);
                                DWCDM = mGson.fromJson(ReturnMsg, new TypeToken<List<SCCJLCCLXS_ReportDetailedModel>>() {
                                }.getType());
                            } else {
                                DWCDM.clear();
                                Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_SHORT).show();
                            }
                            LOG.E("PSML=" + DWCDM.size());
                            DWCA.Updata(DWCDM);
                        } catch (JSONException e) {
                            Toast.makeText(mActivity, "数据解析异常", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Toast.makeText(mActivity, "数据解码异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
