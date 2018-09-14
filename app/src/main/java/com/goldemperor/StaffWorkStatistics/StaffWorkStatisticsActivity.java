package com.goldemperor.StaffWorkStatistics;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.NiceSpinner.NiceSpinner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * File Name : StaffWorkStatisticsActivity
 * Created by : PanZX on  2018/7/27 13:37
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：员工计件明细
 */
@ContentView(R.layout.activity_staff_work_wtatistics)
public class StaffWorkStatisticsActivity extends Activity {

    @ViewInject(R.id.ET_Query)
    private EditText ET_Query;

    @ViewInject(R.id.FB_Query)
    private FancyButton FB_Query;

    @ViewInject(R.id.TV_StartTime)
    private TextView TV_StartTime;

    @ViewInject(R.id.FB_StartTime)
    private FancyButton FB_StartTime;

    @ViewInject(R.id.TV_EndTime)
    private TextView TV_EndTime;

    @ViewInject(R.id.FB_EndTime)
    private FancyButton FB_EndTime;

    @ViewInject(R.id.NS_SelectType)
    private NiceSpinner NS_SelectType;

    @ViewInject(R.id.SRL_Staff_Work_Satistics_List)
    private SmartRefreshLayout SRL_Staff_Work_Satistics_List;

    @ViewInject(R.id.HSL)
    private HorizontalScrollView HSL;


    @ViewInject(R.id.LL_GX)
    private LinearLayout LL_GX;

    @ViewInject(R.id.RV_Staff_Work_Satistics_CL_List)
    private RecyclerView RV_Staff_Work_Satistics_CL_List;

    @ViewInject(R.id.RV_Staff_Work_Satistics_GX_List)
    private RecyclerView RV_Staff_Work_Satistics_GX_List;

    @ViewInject(R.id.TV_FPrice1)
    private TextView TV_FPrice1;

    @ViewInject(R.id.TV_FAmount1)
    private TextView TV_FAmount1;

    @ViewInject(R.id.TV_FAmountSum1)
    private TextView TV_FAmountSum1;

    @ViewInject(R.id.TV_FPrice2)
    private TextView TV_FPrice2;

    @ViewInject(R.id.TV_FAmount2)
    private TextView TV_FAmount2;

    @ViewInject(R.id.TV_FAmountSum2)
    private TextView TV_FAmountSum2;


    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    List<Type> TL = new ArrayList<>();
    List<StaffWorkStatistics_GX_Model> SWSM_GX_L = new ArrayList<>();
    List<StaffWorkStatistics_CL_Model> SWSM_CL_L = new ArrayList<>();
    private Gson mGson;
    private Activity mActivity;
    private TimePickerView tpvStart;
    private TimePickerView tpvEnd;
    private String startDate = "";
    private String endDate = "";
    private String FItemID = "";
    private StaffWorkStatistics_GX_ListAdapter SWSL_GX_A;
    private StaffWorkStatistics_CL_ListAdapter SWSL_CL_A;
    boolean isShowPrice = false;
    boolean isShowAmount = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        mGson = new Gson();
        initview();
        CheckJurisdiction("303100102");//查看单价权限
        CheckJurisdiction("303100103");//查看金额权限
        GetProcessOutputReport();
    }

    private void initview() {
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();

        FB_Query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SRL_Staff_Work_Satistics_List.autoRefresh();
            }
        });
        FB_StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpvStart.show();
            }
        });
        FB_EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpvEnd.show();
            }
        });

        SRL_Staff_Work_Satistics_List.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                LOG.e("---------------------onRefresh");
                GetProcessOutputReportDetail(ET_Query.getText().toString().trim());
            }
        });
        SRL_Staff_Work_Satistics_List.setEnableLoadMore(false);
        tpvStart = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                startDate = Utils.getTime(date, "yyyy-MM-dd");
                TV_StartTime.setText(startDate);
                if (!"".equals(startDate) && !"".equals(endDate)) {
                    SRL_Staff_Work_Satistics_List.autoRefresh();
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .build();
        tpvEnd = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                endDate = Utils.getTime(date, "yyyy-MM-dd");
                TV_EndTime.setText(endDate);
                if (!"".equals(startDate) && !"".equals(endDate)) {
                    SRL_Staff_Work_Satistics_List.autoRefresh();
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .build();
        NS_SelectType.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LOG.e("Name=" + TL.get(position).getFItemName());
                NS_SelectType.setText(TL.get(position).getFItemName());
                FItemID = TL.get(position).getFItemID() + "";
                SRL_Staff_Work_Satistics_List.autoRefresh();
            }
        });
        startDate = Utils.getCurrentYear() + "-" + Utils.getCurrentMonth() + "-" + "01";
        endDate = Utils.getCurrentTime();
        TV_StartTime.setText(startDate);
        TV_EndTime.setText(endDate);

        LinearLayoutManager layoutManagerCL = new LinearLayoutManager(mActivity);
        layoutManagerCL.setOrientation(LinearLayoutManager.VERTICAL);
        RV_Staff_Work_Satistics_CL_List.setLayoutManager(layoutManagerCL);

        LinearLayoutManager layoutManagerGX = new LinearLayoutManager(mActivity);
        layoutManagerCL.setOrientation(LinearLayoutManager.VERTICAL);
        RV_Staff_Work_Satistics_GX_List.setLayoutManager(layoutManagerGX);



    }

    private void CheckJurisdiction(final String controlID) {
        HashMap<String, String> map = new HashMap<>();
        map.put("OrganizeID", "1");
        map.put("empID", dataPref.getString(define.SharedEmpId, define.NONE));
        map.put("controlID", controlID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpPublicServer,
                define.IsHaveControl2,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.e("权限=" + result);
                                if (result.equals("true")) {
                                    if (controlID.equals("303100102")) {
                                        isShowPrice=true;
                                    } else if (controlID.equals("303100103")) {
                                        isShowAmount=true;
                                    }
                                }
                                if (isShowPrice) {
                                    TV_FPrice1.setVisibility(View.VISIBLE);
                                    TV_FPrice2.setVisibility(View.VISIBLE);
                                } else {
                                    TV_FPrice1.setVisibility(View.GONE);
                                    TV_FPrice2.setVisibility(View.GONE);
                                }

                                if (isShowAmount) {
                                    TV_FAmount1.setVisibility(View.VISIBLE);
                                    TV_FAmount2.setVisibility(View.VISIBLE);
                                } else {
                                    TV_FAmount1.setVisibility(View.GONE);
                                    TV_FAmount2.setVisibility(View.GONE);
                                }
                                if(isShowPrice&&isShowAmount){
                                    TV_FAmountSum1.setVisibility(View.VISIBLE);
                                    TV_FAmountSum2.setVisibility(View.VISIBLE);
                                }else{
                                    TV_FAmountSum1.setVisibility(View.GONE);
                                    TV_FAmountSum2.setVisibility(View.GONE);
                                }

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    /**
     * 获取员工计件查询FItemID
     */
    private void GetProcessOutputReport() {
        HashMap<String, String> map = new HashMap<>();
        map.put("SearchType", "app");
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.WEB_GETPROCESSOUTPUTREPORT,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("获取工艺指导书列表=" + result);
                                JSONObject jb = new JSONObject(result);
                                String ReturnType = jb.getString("ReturnType");
                                String ReturnMsg = jb.getString("ReturnMsg");
                                //{"ReturnMsg":"[{\"FItemID\":1001,\"FItemName\":\"产量原单明细\"},{\"FItemID\":1002,\"FItemName\":\"按工序汇总产量\"}]","ReturnType":"success"}
                                if (ReturnType.equals("success")) {
                                    TL = mGson.fromJson(ReturnMsg, new TypeToken<List<Type>>() {
                                    }.getType());
                                    List<String> list = new ArrayList<>();
                                    for (Type type : TL) {
                                        list.add(type.getFItemName());
                                        LOG.e(type.getFItemName());
                                    }
                                    NS_SelectType.attachDataSource(list);
                                    FItemID = TL.get(0).getFItemID() + "";
                                    SRL_Staff_Work_Satistics_List.autoRefresh();
                                } else {
                                    Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mActivity, "数据解密异常", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口返回空", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    /**
     * 获取员工计件明细数据
     *
     * @param query
     */
    private void GetProcessOutputReportDetail(String query) {
        FB_Query.setEnabled(false);
        FB_StartTime.setEnabled(false);
        FB_EndTime.setEnabled(false);
        NS_SelectType.setEnabled(false);
        LOG.e(query.equals("") ? "工号空：根据组别ID查询数据" : "有工号：根据工号查询单人数据");
        HashMap<String, String> map = new HashMap<>();
        map.put("FItemID", FItemID);
        map.put("BeginDate", startDate);
        map.put("EndDate", endDate);
        map.put("FDeptID", dataPref.getString(define.SharedFDeptmentid, "0"));
        map.put("FEmpNumber", query);
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.WEB_GETPROCESSOUTPUTREPORTDETAIL,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        SRL_Staff_Work_Satistics_List.finishRefresh();
                        FB_Query.setEnabled(true);
                        FB_StartTime.setEnabled(true);
                        FB_EndTime.setEnabled(true);
                        NS_SelectType.setEnabled(true);
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
//                                LOG.E("获取员工计件明细数据=" + result);
                                JSONObject jb = new JSONObject(result);
                                String ReturnType = jb.getString("ReturnType");
                                String ReturnMsg = jb.getString("ReturnMsg");
                                if (ReturnType.equals("success")) {
                                    LOG.E("获取员工计件明细数据=" + ReturnMsg);
                                    if (FItemID.equals("1002")) {
                                        SWSM_GX_L = mGson.fromJson(ReturnMsg, new TypeToken<List<StaffWorkStatistics_GX_Model>>() {
                                        }.getType());
                                        LOG.E("数据量=" + SWSM_GX_L.size());
                                        LL_GX.setVisibility(View.VISIBLE);
                                        HSL.setVisibility(View.GONE);
                                        SWSL_GX_A = new StaffWorkStatistics_GX_ListAdapter(SWSM_GX_L, isShowPrice,isShowAmount);
                                        RV_Staff_Work_Satistics_GX_List.setAdapter(SWSL_GX_A);
                                    } else if (FItemID.equals("1001")) {
                                        SWSM_CL_L = mGson.fromJson(ReturnMsg, new TypeToken<List<StaffWorkStatistics_CL_Model>>() {
                                        }.getType());
                                        LOG.E("数据量=" + SWSM_CL_L.size());
                                        HSL.setVisibility(View.VISIBLE);
                                        LL_GX.setVisibility(View.GONE);
                                        SWSL_CL_A = new StaffWorkStatistics_CL_ListAdapter(SWSM_CL_L, isShowPrice,isShowAmount);
                                        RV_Staff_Work_Satistics_CL_List.setAdapter(SWSL_CL_A);
                                    }
                                } else {
                                    Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mActivity, "数据解密异常", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口返回空", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public static class Type {
        int FItemID;
        String FItemName;

        public int getFItemID() {
            return FItemID;
        }

        public void setFItemID(int FItemID) {
            this.FItemID = FItemID;
        }

        public String getFItemName() {
            return FItemName;
        }

        public void setFItemName(String FItemName) {
            this.FItemName = FItemName;
        }
    }
}
