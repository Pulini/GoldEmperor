package com.goldemperor.CCActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.XJChenk.SPListAdapter;
import com.goldemperor.XJChenk.XJListActivity;
import com.goldemperor.XJChenk.priceEntryResult;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.goldemperor.Widget.fancybuttons.FancyButton;

/**
 * Created by Nova on 2017/10/28.
 */

public class CCPrintActivity extends AppCompatActivity {

    private Context mContext;
    private Activity act;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;


    private TextView tv_fsno;
    private TextView tv_fname;
    private TextView tv_fmodel;

    private TextView tv_suppliername;
    private TextView tv_fprice;
    private TextView tv_fempname;

    private TextView tv_fempnumber;
    private TextView tv_fdeptname;


    private TextView tv_faddress;
    private TextView tv_fnumber;


    private FancyButton btn_ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ccprint);

        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        act = this;
        mContext = this;


        tv_fsno = (TextView) findViewById(R.id.tv_fsno);
        tv_fname = (TextView) findViewById(R.id.tv_fname);
        tv_fmodel = (TextView) findViewById(R.id.tv_fmodel);
        tv_suppliername = (TextView) findViewById(R.id.tv_suppliername);

        tv_fprice = (TextView) findViewById(R.id.tv_fprice);
        tv_fempname = (TextView) findViewById(R.id.tv_fempname);
        tv_fempnumber = (TextView) findViewById(R.id.tv_fempnumber);

        tv_fdeptname = (TextView) findViewById(R.id.tv_fdeptname);
        tv_faddress = (TextView) findViewById(R.id.tv_faddress);
        tv_fnumber = (TextView) findViewById(R.id.tv_fnumber);

        tv_fsno.setText("申购单号:" + CCListActivity.selectFacardResult.getFsno());
        tv_fname.setText("财产名称:" + CCListActivity.selectFacardResult.getFname());
        tv_suppliername.setText("供应商:" + CCListActivity.selectFacardResult.getFvender());
        tv_fmodel.setText("规格型号:" + CCListActivity.selectFacardResult.getFmodel());
        tv_fprice.setText("财产单价:" + CCListActivity.selectFacardResult.getFprice());
        tv_fempname.setText("保管员工:" + CCListActivity.selectFacardResult.getFempname());

        tv_fempnumber.setText("员工工号:" + CCListActivity.selectFacardResult.getFempnumber());


        tv_fdeptname.setText("保管部门:" + CCListActivity.selectFacardResult.getFdeptname());
        tv_faddress.setText("保管地点:" + CCListActivity.selectFacardResult.getFaddress());
        tv_fnumber.setText("财产编号:" + CCListActivity.selectFacardResult.getFnumber());

        btn_ok = (FancyButton) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

}
