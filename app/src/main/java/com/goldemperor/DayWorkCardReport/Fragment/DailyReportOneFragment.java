package com.goldemperor.DayWorkCardReport.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.goldemperor.DayWorkCardReport.adapter.WorkshopDailyReportAdapter;
import com.goldemperor.DayWorkCardReport.model.WorkshopDailyReportModel;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.verticalviewpager.ViewPagerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * File Name : DailyReportOneFragment
 * Created by : PanZX on  2018/12/14 08:51
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.fragment_daily_report_one)
public class DailyReportOneFragment extends ViewPagerFragment {

    @ViewInject(R.id.LV_Form_D1)
    private ListView LV_Form_D1;

    @ViewInject(R.id.PB_Loading1)
    private ProgressBar PB_Loading1;

    int time = 1000 * 60 * 10;
    private final int Refresh = 1001;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Refresh:
                    getdata();

                    break;
            }
        }
    };

    WorkshopDailyReportAdapter WDRA;
    List<List<WorkshopDailyReportModel>> WDRM = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = x.view().inject(this, inflater, container);
            initview();
        }
        return rootView;
    }

    private void initview() {
        WDRA = new WorkshopDailyReportAdapter(getActivity(), WDRM);
        LV_Form_D1.setAdapter(WDRA);
        mHandler.sendEmptyMessage(Refresh);
    }

    private void getdata() {
        PB_Loading1.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("date", Utils.getCurrentTime());
        map.put("FWorkShopID", "");//预留参数
        map.put("FOrganizeID", "");//预留参数
        map.put("suitID", (String) SPUtils.get(define.SharedSuitID, "1"));
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetEarlyWarningInfoReportOne, map, result -> {
                    PB_Loading1.setVisibility(View.GONE);
                    mHandler.sendEmptyMessageDelayed(Refresh, time);
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("生产达成异常汇总表=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                List<WorkshopDailyReportModel> wdrm = new Gson().fromJson(ReturnMsg, new TypeToken<List<WorkshopDailyReportModel>>() {
                                }.getType());
                                if (wdrm != null && wdrm.size() > 0) {
                                    WDRM.clear();
                                    List<String> srtlist = new ArrayList<>();
                                    for (WorkshopDailyReportModel W1 : wdrm) {
                                        if (!srtlist.contains(W1.getFWorkShopName())) {
                                            srtlist.add(W1.getFWorkShopName());
                                        }
                                    }
                                    for (String s : srtlist) {
                                        List<WorkshopDailyReportModel> data1 = new ArrayList<>();
                                        for (WorkshopDailyReportModel W2 : wdrm) {
                                            if (s.equals(W2.getFWorkShopName())) {
                                                data1.add(W2);
                                            }
                                        }
                                        WDRM.add(data1);
                                    }
                                    LOG.e("WDRM=" + WDRM.size());
                                    WDRA.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getContext(), ReturnMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
