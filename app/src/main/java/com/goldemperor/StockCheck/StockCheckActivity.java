package com.goldemperor.StockCheck;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.goldemperor.R;
import com.google.gson.Gson;
import com.goldemperor.LoginActivity.Request;
import com.goldemperor.LoginActivity.RequestBody;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.StockCheck.DoneView.DoneView;
import com.goldemperor.StockCheck.ExceptionalView.ExceptionalView;
import com.goldemperor.LoginActivity.LoginActivity;
import com.goldemperor.StockCheck.RequestView.RequestView;
import com.goldemperor.StockCheck.WaitView.WaitView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tapadoo.alerter.Alerter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StockCheckActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public String appKey = "afeeb3ab6b0090293a70a5ba1d26a478";
    public String appSecret = "e3c0d24ddd06";
    public String nonce = "98269826";
    public static String checkSum;

    public String curTime = String.valueOf((new Date()).getTime() * 1000L );

    private List<View> viewList;
    public ViewPager pager;
    ViewPageAdapter adapter;
    private BottomBar bottomBar;

    private RequestView requestView;
    public WaitView waitView;
    public ExceptionalView exceptionalView;

    public DoneView doneView;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;

    private Context con;
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockcheck);
        //隐藏标题栏
        getSupportActionBar().hide();
        con = this;
        act = this;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();


        if (dataPref.getString(define.SharedPassword, define.NONE).equals(define.NONE)) {
            Intent i = new Intent(con, LoginActivity.class);
            con.startActivity(i);
            finish();
        } else {

            Gson g = new Gson();
            final Request request = new Request();
            RequestBody requestBody = new RequestBody();
            requestBody.setUserPhone(dataPref.getString(define.SharedPhone, define.NONE));
            requestBody.setPassword(dataPref.getString(define.SharedPassword, define.NONE));
            request.setData(requestBody);

            RequestParams params = new RequestParams(define.Login);
            params.addHeader("AppKey", appKey);
            params.addHeader("Nonce", nonce);
            params.addHeader("CurTime", curTime);
            checkSum = Utils.getCheckSum(appSecret, nonce, curTime);
            params.addHeader("CheckSum", checkSum);

            params.setAsJsonContent(true);
            params.setBodyContent(g.toJson(request));

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(final String result) {
                    //解析result
                    //重新设置数据
                    Log.e("jindi",result);
                    if (result.contains("成功")) {

                    } else {
                        Alerter.create(act)
                                .setTitle("提示")
                                .setText("请先登录")
                                .setBackgroundColorRes(R.color.colorAlert)
                                .show();
                    }
                }

                //请求异常后的回调方法
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

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
        //初始化viewpage页面
        viewList = new ArrayList<View>();
        pager = (ViewPager) findViewById(R.id.pager);

        View view_pager_request = View.inflate(this, R.layout.pager_request, null);
        View view_pager_wait = View.inflate(this, R.layout.pager_wait, null);
        View view_pager_done = View.inflate(this, R.layout.pager_done, null);
        View view_pager_exceptional = View.inflate(this, R.layout.pager_exceptional, null);

        viewList.add(view_pager_request);
        viewList.add(view_pager_wait);
        viewList.add(view_pager_done);
        viewList.add(view_pager_exceptional);

        adapter = new ViewPageAdapter(viewList);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);

        //初始化各界面
        waitView = new WaitView(this, view_pager_wait);
        doneView = new DoneView(this, view_pager_done);

        exceptionalView = new ExceptionalView(this, view_pager_exceptional);

        requestView = new RequestView(this, view_pager_request);


        //获取传值信息
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.getString("content") != null) {
                String alert = bundle.getString("content");
                if (alert.contains("稽查单号") || alert.contains("上传照片")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pager.setCurrentItem(1);
                        }
                    }, 500);
                } else if (alert.contains("已签收") || alert.contains("已结案")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pager.setCurrentItem(2);
                        }
                    }, 500);
                } else if (alert.contains("异常单号") || alert.contains("提交处理")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pager.setCurrentItem(3);
                        }
                    }, 500);
                }
            }

        }

        //设置底部栏
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.tab_require:
                        if (pager.getCurrentItem() != 0) {
                            pager.setCurrentItem(0);
                        }
                        break;
                    case R.id.tab_wait:
                        if (pager.getCurrentItem() != 1) {
                            pager.setCurrentItem(1);
                        }
                        break;
                    case R.id.tab_done:
                        if (pager.getCurrentItem() != 2) {
                            pager.setCurrentItem(2);
                        }
                        break;
                    case R.id.tab_exceptional:
                        if (pager.getCurrentItem() != 3) {
                            pager.setCurrentItem(3);
                        }
                        break;
                }

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (bottomBar.getCurrentTabId() != position) {

            bottomBar.selectTabAtPosition(position);

            switch (position) {
                case 1:
                    if (waitView != null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                waitView.getData();
                            }
                        }, 500);

                    }
                    break;
                case 2:
                    if (doneView != null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doneView.getData();
                            }
                        }, 500);
                    }
                    break;
                case 3:
                    if (exceptionalView != null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                exceptionalView.getData();
                            }
                        }, 500);
                    }
                    break;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //稽查结果上传后返回时更新数据
        if (requestCode == define.UPDATA) {
            if (waitView != null) {
                waitView.getData();
            }

            if (exceptionalView != null) {
                exceptionalView.getData();
            }

            if (doneView != null) {
                doneView.getData();
            }
        }
    }
}
