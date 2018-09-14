package com.goldemperor.XJChenk;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.StockCheck.StockCheckActivity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Nova on 2017/10/28.
 */

public class SPListActivity extends AppCompatActivity {

    private SwipeMenuRecyclerView mMenuRecyclerView;
    private Context mContext;
    private Activity act;
    public SPListAdapter mMenuAdapter;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;

    private List<priceEntryResult> priceEntryResultList = new ArrayList<priceEntryResult>();

    private String StartDate;
    private String EndDate;

    private TextView tv_foperatorname;
    private TextView tv_fdate;
    private TextView tv_fnumber;

    private TextView tv_fitemname;
    private TextView tv_suppliername;
    private TextView tv_fneedauxqty;

    private TextView tv_fauxpricefor;
    private TextView tv_famountfor;

    private FancyButton btn_ok;

    private FancyButton btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splist);

        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        act = this;
        mContext = this;

        StartDate = Utils.getCurrentTime();
        EndDate = Utils.getCurrentTime();

        getData();


        tv_foperatorname = (TextView) findViewById(R.id.tv_foperatorname);
        tv_fdate = (TextView) findViewById(R.id.tv_fdate);
        tv_fnumber = (TextView) findViewById(R.id.tv_fnumber);
        tv_fitemname = (TextView) findViewById(R.id.tv_fitemname);
        tv_suppliername = (TextView) findViewById(R.id.tv_suppliername);

        tv_fneedauxqty = (TextView) findViewById(R.id.tv_fneedauxqty);
        tv_fauxpricefor = (TextView) findViewById(R.id.tv_fauxpricefor);
        tv_famountfor = (TextView) findViewById(R.id.tv_famountfor);

        tv_fnumber.setText("询价单号:" + XJListActivity.selectPriceResult.getFnumber());
        tv_fitemname.setText("物料名称:" + XJListActivity.selectPriceResult.getFitemname());
        tv_suppliername.setText("供应商:" + XJListActivity.selectPriceResult.getSuppliername());
        tv_fneedauxqty.setText("数量:" + XJListActivity.selectPriceResult.getFneedauxqty());
        tv_fauxpricefor.setText("单价:" + XJListActivity.selectPriceResult.getFauxpricefor());
        tv_famountfor.setText("金额:" + XJListActivity.selectPriceResult.getFsymbols() + XJListActivity.selectPriceResult.getFamountfor());

        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。


        mMenuAdapter = new SPListAdapter(priceEntryResultList);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mMenuRecyclerView.setAdapter(mMenuAdapter);

        btn_ok = (FancyButton) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(define.isWaiNet) {
                    GetUserID(XJListActivity.selectPriceResult.getFinterid());
                }else{
                    Toast.makeText(mContext, "请先切换外网", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_cancel = (FancyButton) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }


    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(final int position) {
        }
    };


    private void getData() {
        priceEntryResultList.clear();
        RequestParams params = new RequestParams(define.IP1718881 + define.GetXJDPriceEntry);
        params.addQueryStringParameter("finterid", String.valueOf(XJListActivity.selectPriceResult.getFinterid()));
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.e("jindi", result);
                if (result == null || result.isEmpty()) {

                } else {
                    List<priceEntryResult> priceEntryResultTemp = GsonFactory.jsonToArrayList(result, priceEntryResult.class);

                    for (int i = 0; i < priceEntryResultTemp.size(); i++) {
                        priceEntryResultList.add(priceEntryResultTemp.get(i));
                    }

                }

                mMenuAdapter.notifyDataSetChanged();
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());


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

    public void GetUserID(final long finterid) {
        RequestParams params = new RequestParams(define.Net2 + define.GetUserID);
        params.addQueryStringParameter("FEmpID", dataPref.getString(define.SharedEmpId, "0"));
        params.setConnectTimeout(6000);
        params.setReadTimeout(6000);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e("jindi", result);
                if (result.contains("FUserID")) {
                    String FUserID = result.substring(result.indexOf("<FUserID>"), result.indexOf("</FUserID>"));
                    FUserID = FUserID.replaceAll("<FUserID>", "").replaceAll("</FUserID>", "");
                    dataEditor.putString(define.SharedUserId, FUserID);
                    dataEditor.commit();
                    UpdataCheckerID(finterid, FUserID);
                } else {
                    Toast.makeText(mContext, "获取员工ID失败,请先切换外网", Toast.LENGTH_LONG).show();
                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mContext, "获取员工ID失败,请先切换外网", Toast.LENGTH_LONG).show();
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

    public void UpdataCheckerID(final long finterid, final String FUserID) {
        RequestParams params = new RequestParams(define.IP1718881 + define.SetCheckerId);
        params.addQueryStringParameter("finterid", String.valueOf(finterid));
        params.addQueryStringParameter("checkerid", FUserID);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (result.equals("1")) {
                    Toast.makeText(mContext, "审批通过", Toast.LENGTH_LONG).show();
                    btn_ok.setEnabled(false);
                } else {
                    Toast.makeText(mContext, "审批失败", Toast.LENGTH_LONG).show();
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mContext, "审批失败", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mMenuAdapter != null) {
            getData();
        }
    }

}
