package com.goldemperor.PzActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import com.goldemperor.Widget.fancybuttons.FancyButton;

import static com.goldemperor.PzActivity.YCListActivity.abnormityModel;
import static com.goldemperor.PzActivity.YCListActivity.selectAbnormity;


/**
 * Created by Nova on 2017/10/28.
 */

public class ReasonActivity extends AppCompatActivity {

    private SwipeMenuRecyclerView mMenuRecyclerView;
    public ReasonAdapter mMenuAdapter;


    private TextView tv_reason;
    private FancyButton btn_confirm;
    private FancyButton btn_cancel;
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reason);
        //隐藏标题栏
        getSupportActionBar().hide();
        act = this;
        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));// 布局管理器。


        mMenuAdapter = new ReasonAdapter(abnormityModel, this);
        LOG.e("abnormityModel="+abnormityModel.toString());
        mMenuRecyclerView.setAdapter(mMenuAdapter);
        tv_reason = (TextView) findViewById(R.id.tv_reason);
        btn_confirm = (FancyButton) findViewById(R.id.btn_confirm);
        btn_cancel = (FancyButton) findViewById(R.id.btn_cancel);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        clear();
    }

    public void clear() {
        for (int i = 0; i < selectAbnormity.size(); i++) {
            selectAbnormity.get(i)[2] = "无";
            selectAbnormity.get(i)[3] = "未选";
        }
        act.runOnUiThread(new Runnable() {
            public void run() {
                mMenuAdapter.notifyDataSetChanged();
            }

        });
        freshReason();
    }

    public void freshReason() {
        String text = "";
        for (int i = 0; i < selectAbnormity.size(); i++) {
            if (selectAbnormity.get(i)[3].equals("已选")) {
                if (selectAbnormity.get(i)[2].equals("0")) {
                    text += "," + selectAbnormity.get(i)[0] + "(轻微)";
                } else {
                    text += "," + selectAbnormity.get(i)[0] + "(严重)";
                }
            }
        }
        text = text.replaceFirst(",", "");
        tv_reason.setText(text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
