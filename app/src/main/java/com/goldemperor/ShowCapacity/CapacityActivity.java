package com.goldemperor.ShowCapacity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.NewHome.NewLogin;
import com.goldemperor.MainActivity.NewHome.NewLoginListener;
import com.goldemperor.MainActivity.NewHome.Model.NewLoginModel;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ShowCapacity.model.AbnormityDetailModel;
import com.goldemperor.ShowCapacity.model.DayWorkCardReportModel;
import com.goldemperor.ShowCapacity.model.HourAllInfoModel;
import com.goldemperor.ShowCapacity.model.MessageEvent;
import com.goldemperor.ShowCapacity.model.ProDayLeaveFourSizeReportSource;
import com.goldemperor.ShowCapacity.model.ProOrderOfCastReport;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static cn.jpush.android.api.JPushInterface.resumePush;
import static cn.jpush.android.api.JPushInterface.stopPush;

/**
 * File Name : CapacityActivity
 * Created by : PanZX on  2018/10/18 10:33
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_capacity)
public class CapacityActivity extends FragmentActivity implements NewLoginListener, ViewPager.OnPageChangeListener {

    @ViewInject(R.id.VP_Capacity)
    private ViewPager VP_Capacity;

    @ViewInject(R.id.RL_Back)
    private RelativeLayout RL_Back;

    @ViewInject(R.id.TV_Title)
    private TextView TV_Title;

    @ViewInject(R.id.TV_Time)
    private TextView TV_Time;
    private final int RefreshTime = 123;
    private final int RefreshDayWorkCardReport = 1234;
    private static final int TIME = 1000 * 15;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RefreshTime:
                    TV_Time.setText(Utils.getTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    mHandler.sendEmptyMessageDelayed(RefreshTime, 1000);
                    break;
                case RefreshDayWorkCardReport:
                    GetDayWorkCardReport();

                    break;
            }
        }
    };

    private Activity mActivity;
    private NewLogin NL;
    List<Fragment> mFragment = new ArrayList<>();
    ShowTemplateFragment STF;
    CapacityFragment CF;
    CapacityDetailsFragment CDF;
    RankingListDayFragment RLDF;
    RankingListMonthFragment RLMF;
    FragmentPagerAdapter fragmentPagerAdapter;
    private String Number;
    private Long UpDataTime;
    private int mCount = 4;
    private int itemPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        initview();

        mHandler.sendEmptyMessage(RefreshDayWorkCardReport);
        GetAbnormityDetail();
        GetHourAllInfo();
        GetProDayLeaveFourSizeReportSource();
        GetProOrderOfCastReport();

    }

    private void initview() {
        mActivity = this;
        NL = new NewLogin(this, this);
        Number = (String)SPUtils.get(define.SharedJobNumber, "");
        STF = new ShowTemplateFragment();
        CF = new CapacityFragment();
        CDF = new CapacityDetailsFragment();
        RLDF = new RankingListDayFragment();
        RLMF = new RankingListMonthFragment();
        mFragment.add(STF);
        mFragment.add(CF);
        mFragment.add(CDF);
        mFragment.add(RLDF);
        mFragment.add(RLMF);


        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };

        VP_Capacity.setAdapter(fragmentPagerAdapter);
        VP_Capacity.setOffscreenPageLimit(5);
        VP_Capacity.setOnPageChangeListener(this);
        RL_Back.setOnClickListener(v -> onBackPressed());
        String DeptmentName = (String)SPUtils.get(define.SharedDeptmentName, "");
        TV_Title.setText("【" + DeptmentName + "】生产信息");
        mHandler.postDelayed(runnableForViewPager, TIME);
    }

    /**
     * ViewPager的定时器
     */
    Runnable runnableForViewPager = new Runnable() {
        @Override
        public void run() {
            try {
                itemPosition++;
                mHandler.postDelayed(this, TIME);
                VP_Capacity.setCurrentItem(itemPosition % mCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void GetDayWorkCardReport() {
        String FNumber = (String)SPUtils.get(define.SharedJobNumber, "");
        LOG.e("FNumber=" + FNumber);
        if ("".equals(FNumber)) {
            NL.show("登录获取本组数据");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("FDate", Utils.getCurrentTime());
//        map.put("Exec_Where", "001156");
        map.put("FNumber", FNumber);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForMesServer,
                define.GetDayWorkCardReport2,
                map, result -> {
                    mHandler.sendEmptyMessageDelayed(RefreshDayWorkCardReport, 1000*60);
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("日产量报表=" + result);
                            //{"ReturnMsg":"[{\"NumberOfGoalsToday\":455,\"NumberOfCompletions\":390,\"WorkRate\":240,\"QualifiedRate\":100}]","ReturnType":"success"}
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                List<DayWorkCardReportModel> dwcrm = new Gson().fromJson(ReturnMsg, new TypeToken<List<DayWorkCardReportModel>>() {
                                }.getType());
                                if (dwcrm != null) {
                                    CF.setDayWorkCardReportData(dwcrm.get(0));
                                    CDF.setDayWorkCardReportData(dwcrm.get(0));
                                }
                            } else {
                                Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void GetAbnormityDetail() {
        String Deptmentid = (String)SPUtils.get(define.SharedFDeptmentid, "");
        LOG.e("Deptmentid=" + Deptmentid);
        if ("".equals(Deptmentid)) {
            NL.show("登录获取本组数据");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("FDate", Utils.getCurrentTime());
        map.put("FDepartmentID", Deptmentid);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForMesServer,
                define.GetAbnormityDetail,
                map, result -> {
                    if (result != null) {
//                        GetHourAllInfo();
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("扇形图数据=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                List<AbnormityDetailModel> adm = new Gson().fromJson(ReturnMsg, new TypeToken<List<AbnormityDetailModel>>() {
                                }.getType());
                                if (adm != null) {
                                    CDF.setAbnormityDetailData(adm);
                                }
                            } else {
                                Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_LONG).show();
                            }
//                            List<AbnormityDetailModel> adml = new ArrayList<>();
//                            AbnormityDetailModel ADM;
//                            for (int i = 0; i < 3; i++) {
//                                ADM = new AbnormityDetailModel();
//                                ADM.setFQty(i + 10);
//                                ADM.setFExceptionID(i + 1);
//                                ADM.setFName("异常类型" + i);
//                                adml.add(ADM);
//                            }
//                            CDF.setAbnormityDetailData(adml);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void GetHourAllInfo() {
        String Deptmentid = (String)SPUtils.get(define.SharedFDeptmentid, "");
        String FNumber = (String)SPUtils.get(define.SharedJobNumber, "");
        LOG.e("Deptmentid=" + Deptmentid);
        if ("".equals(Deptmentid) || "".equals(FNumber)) {
            NL.show("登录获取本组数据");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("FDate", Utils.getCurrentTime());
        map.put("FNumber", FNumber);
        map.put("FDepartmentID", Deptmentid);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForMesServer,
                define.GetHourAllInfo,
                map, result -> {
                    if (result != null) {
//                        GetProDayLeaveFourSizeReportSource();
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("条形图数据=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                HourAllInfoModel adm = new Gson().fromJson(ReturnMsg, new TypeToken<HourAllInfoModel>() {
                                }.getType());
                                if (adm != null) {
                                    CDF.setHourAllInfoData(adm);
                                }
                            } else {
                                Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void GetProDayLeaveFourSizeReportSource() {
        String Deptmentid = (String)SPUtils.get(define.SharedFDeptmentid, "");
        LOG.e("Deptmentid=" + Deptmentid);
        if ("".equals(Deptmentid)) {
            NL.show("登录获取本组数据");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("FDate", Utils.getCurrentTime());
        map.put("FDepartmentID", Deptmentid);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForMesServer,
                define.GetProDayLeaveFourSizeReportSource,
                map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("表格=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {

                                List<ProDayLeaveFourSizeReportSource> adm = new Gson().fromJson(ReturnMsg, new TypeToken<List<ProDayLeaveFourSizeReportSource>>() {
                                }.getType());
                                LOG.e("型体=" + adm.size());
                                for (ProDayLeaveFourSizeReportSource da : adm) {
                                    LOG.e("指令=" + da.getAllSizeList().size());
                                    for (ProDayLeaveFourSizeReportSource.data1 data1 : da.getAllSizeList()) {
                                        for (ProDayLeaveFourSizeReportSource.data2 data2 : data1.getSizeQtyList()) {
                                            if (data2.getSize().equals("指令号")) {
                                                LOG.e("data=" + data2.getQty());
                                            }
                                        }
                                    }
                                }
                                if (adm != null) {
                                    CDF.setCapacityListData(adm);
                                }
                            } else {
//                                CDF.setAbnormityDetailData(null);
                                Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_LONG).show();
                            }
//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void GetProOrderOfCastReport() {
        String Deptmentid = (String)SPUtils.get(define.SharedFDeptmentid, "");
        LOG.e("Deptmentid=" + Deptmentid);
        if ("".equals(Deptmentid)) {
            NL.show("登录获取本组数据");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("FDate", Utils.getCurrentTime());
        map.put("WhereStr", "");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForMesServer,
                define.GetProOrderOfCastReport,
                map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("龙虎表=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
//                                ReturnMsg = ReturnMsg.replaceAll("\\\\", "");
                                jsonObject = new JSONObject(ReturnMsg);
                                String MonthDT = jsonObject.getString("MonthDT");
                                LOG.e("MonthDT=" + MonthDT);
                                String ToDayDT = jsonObject.getString("ToDayDT");
                                LOG.e("ToDayDT=" + ToDayDT);
                                List<ProOrderOfCastReport.Month> MList = new Gson().fromJson(MonthDT, new TypeToken<List<ProOrderOfCastReport.Month>>() {
                                }.getType());
                                List<ProOrderOfCastReport.Day> DList = new Gson().fromJson(ToDayDT, new TypeToken<List<ProOrderOfCastReport.Day>>() {
                                }.getType());

                                if (DList != null) {
                                    Collections.sort(MList, (o1, o2) -> o1.getFMonthOrderOfCast().compareTo(o2.getFMonthOrderOfCast()));
                                    RLDF.setFormDayData(DList);
                                }
                                if (MList != null) {
                                    Collections.sort(DList, (o1, o2) -> o1.getFOrderOfCast().compareTo(o2.getFOrderOfCast()));
                                    RLMF.setFormMonthData(MList);
                                }

                            } else {
                                Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_LONG).show();
                            }
//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        finish();
//        NL.show("验证身份后方可返回主页");
    }

    @Override
    public void Login(NewLoginModel NLM) {

        if ("".equals(Number)) {
            finish();
            return;
        }
        if (Number.equals(NLM.getFNumber()) || NLM.getFNumber().equals("013600")) {
            finish();
        } else {
            LemonHello.getInformationHello("警告", "只有本人登录才能解锁！")
                    .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Refresh(MessageEvent messageEvent) {
        if (messageEvent.getType().equals(MyJPushReceiver.TypeRefresh)) {
            try {
                JSONObject jb = new JSONObject(messageEvent.getMessage());
                //{"Deptmentid":"123","UpDataTime":636759956432160850}
                String Deptmentid = jb.getString("Deptmentid");
                Long Time = jb.getLong("UpDataTime");
                if (UpDataTime == null) {
                    UpDataTime = Time - 1;
                }
                String id = (String)SPUtils.get(define.SharedFDeptmentid, "");
                if (id.equals(Deptmentid) && UpDataTime < Time) {
                    mHandler.removeMessages(RefreshDayWorkCardReport);
                    mHandler.sendEmptyMessage(RefreshDayWorkCardReport);
                    GetDayWorkCardReport();
                    GetAbnormityDetail();
                    GetHourAllInfo();
                    GetProDayLeaveFourSizeReportSource();
                    GetProOrderOfCastReport();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHandler.removeCallbacks(runnableForViewPager);
                break;
            case MotionEvent.ACTION_CANCEL:
                mHandler.postDelayed(runnableForViewPager, TIME);
                break;
            case MotionEvent.ACTION_UP:
                mHandler.postDelayed(runnableForViewPager, TIME);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getAction()) {
            case KeyEvent.ACTION_DOWN:
                mHandler.removeCallbacks(runnableForViewPager);
                break;
            case KeyEvent.ACTION_UP:
                mHandler.postDelayed(runnableForViewPager, TIME);
                break;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        resumePush(getApplicationContext());
        mHandler.sendEmptyMessageDelayed(RefreshTime, 1000);
        mHandler.removeCallbacks(runnableForViewPager);
        mHandler.postDelayed(runnableForViewPager, TIME);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnableForViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPush(getApplicationContext());
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacks(runnableForViewPager);
        mHandler.removeMessages(RefreshTime);
    }


    @Override
    public void Back() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
