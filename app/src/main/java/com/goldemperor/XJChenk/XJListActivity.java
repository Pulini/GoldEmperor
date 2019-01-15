package com.goldemperor.XJChenk;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.PgdActivity.MaterialActivity;
import com.goldemperor.R;
import com.google.gson.Gson;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.goldemperor.PgdActivity.PgdActivity.selectWorkCardPlan;

/**
 * Created by Nova on 2017/10/28.
 */

public class XJListActivity extends AppCompatActivity {

    private SwipeMenuRecyclerView mMenuRecyclerView;
    private Context mContext;
    private Activity act;
    private TextView tv_tip;
    public XJListAdapter mMenuAdapter;


    private List<priceResult> priceResultList = new ArrayList<priceResult>();
    private ActionBar bar = null;
    private BasePopupWindow mPopupWindow;
    private View popupView;
    private TextView readyCheck;
    private TextView noCheck;

    private TextView today;
    private TextView week;
    private TextView month;
    private TextView openCalendar;
    private boolean isReadyCheck = false;
    private String StartDate;
    private String EndDate;

    public static priceResult selectPriceResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_xjlist);


        tv_tip = (TextView) findViewById(R.id.tv_tip);
        act = this;
        mContext = this;
        bar = getActionBar();
        initPopupWindow();

        StartDate = Utils.getCurrentTime();
        EndDate = Utils.getCurrentTime();

        getData();
        //getDataReadyCheck();

        tv_tip.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。


        mMenuAdapter = new XJListAdapter(priceResultList);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
    }


    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(final int position) {
            selectPriceResult=priceResultList.get(position);
            Intent i = new Intent(mContext, SPListActivity.class);
            startActivityForResult(i, 0);
        }
    };


    private void getData() {
        tv_tip.setText("数据载入中...");
        tv_tip.setVisibility(View.VISIBLE);
        priceResultList.clear();
        RequestParams params = new RequestParams(define.IP1718881 + define.GetXJD);
        params.addQueryStringParameter("CheckerID", "0");
        params.addQueryStringParameter("ManagerID", "0");
        params.addQueryStringParameter("StartDate", StartDate);
        params.addQueryStringParameter("EndDate", EndDate);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                if (result == null || result.isEmpty()) {
                    tv_tip.setText("暂无数据");
                } else {
                    List<priceResult> priceResultTemp = GsonFactory.jsonToArrayList(result, priceResult.class);
                    tv_tip.setText(priceResultTemp.size() + "条未审批数据");
                    for (int i = 0; i < priceResultTemp.size(); i++) {
                        priceResultList.add(priceResultTemp.get(i));
                    }

                }

                mMenuAdapter.notifyDataSetChanged();
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());
                tv_tip.setText("数据返回错误");

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

    private void getDataReadyCheck() {
        tv_tip.setText("数据载入中...");
        tv_tip.setVisibility(View.VISIBLE);
        priceResultList.clear();
        RequestParams params = new RequestParams(define.IP1718881 + define.GetXJDReadyCheck);
        params.addQueryStringParameter("CheckerID", "0");
        params.addQueryStringParameter("ManagerID", "0");
        params.addQueryStringParameter("StartDate", StartDate);
        params.addQueryStringParameter("EndDate", EndDate);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                if (result == null || result.isEmpty()) {
                    tv_tip.setText("暂无数据");
                } else {
                    List<priceResult> priceResultTemp = GsonFactory.jsonToArrayList(result, priceResult.class);
                    tv_tip.setText(priceResultTemp.size() + "条已审批数据");
                    for (int i = 0; i < priceResultTemp.size(); i++) {
                        priceResultList.add(priceResultTemp.get(i));
                    }
                }
                mMenuAdapter.notifyDataSetChanged();
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());
                tv_tip.setText("数据返回错误");

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

    public void GetUserID(final int position) {
    }

    public void initPopupWindow() {
        mPopupWindow = new BasePopupWindow(this);
        popupView = getLayoutInflater().inflate(R.layout.activity_xjdlist_popupwindow, null);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPopupWindow.setContentView(popupView);
        mPopupWindow.setmOnDismissListener(mOnDismissListener);
        readyCheck = (TextView) popupView.findViewById(R.id.readyCheck);
        noCheck = (TextView) popupView.findViewById(R.id.noCheck);

        today = (TextView) popupView.findViewById(R.id.today);
        week = (TextView) popupView.findViewById(R.id.week);
        month = (TextView) popupView.findViewById(R.id.month);
        openCalendar = (TextView) popupView.findViewById(R.id.openCalendar);

        noCheck.setBackgroundColor(Color.CYAN);
        readyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyCheck.setBackgroundColor(Color.CYAN);
                noCheck.setBackgroundColor(getResources().getColor(R.color.popup));
                isReadyCheck = true;
            }
        });

        noCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noCheck.setBackgroundColor(Color.CYAN);
                readyCheck.setBackgroundColor(getResources().getColor(R.color.popup));
                isReadyCheck = false;
            }
        });
        today.setBackgroundColor(Color.CYAN);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.setBackgroundColor(Color.CYAN);
                week.setBackgroundColor(getResources().getColor(R.color.popup));
                month.setBackgroundColor(getResources().getColor(R.color.popup));
                openCalendar.setBackgroundColor(getResources().getColor(R.color.popup));
                StartDate=Utils.getCurrentTime();
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week.setBackgroundColor(Color.CYAN);
                today.setBackgroundColor(getResources().getColor(R.color.popup));
                month.setBackgroundColor(getResources().getColor(R.color.popup));
                openCalendar.setBackgroundColor(getResources().getColor(R.color.popup));
                StartDate=Utils.getDateStr(Utils.getCurrentTime(), 7, false);
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month.setBackgroundColor(Color.CYAN);
                today.setBackgroundColor(getResources().getColor(R.color.popup));
                week.setBackgroundColor(getResources().getColor(R.color.popup));
                openCalendar.setBackgroundColor(getResources().getColor(R.color.popup));
                StartDate=Utils.getDateStr(Utils.getCurrentTime(), 30, false);
            }
        });

        openCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar.setBackgroundColor(Color.CYAN);
                today.setBackgroundColor(getResources().getColor(R.color.popup));
                week.setBackgroundColor(getResources().getColor(R.color.popup));
                month.setBackgroundColor(getResources().getColor(R.color.popup));
            }
        });

    }

    /**
     * 筛选窗口消失监听。
     */
    private PopupDismissmaListener mOnDismissListener = new PopupDismissmaListener() {

        @Override
        public void onDismiss() {
            //Toast.makeText(mContext, "筛选关闭", Toast.LENGTH_LONG).show();
            if (isReadyCheck) {
                getDataReadyCheck();
            } else {
                getData();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mMenuAdapter != null) {
            if (isReadyCheck) {
                getDataReadyCheck();
            } else {
                getData();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_action_1) {
            //Toast.makeText(mContext, "筛选", Toast.LENGTH_LONG).show();
            mPopupWindow.showAsDropDown((View) tv_tip, 0, -tv_tip.getMeasuredHeight());
        }
        return super.onOptionsItemSelected(item);
    }

}
