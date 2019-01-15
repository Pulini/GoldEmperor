package com.goldemperor.PgdActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.WindowManager;
import android.widget.TextView;

import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;


/**
 * Created by Nova on 2017/7/25.
 */

public class NameListActivity extends AppCompatActivity {


    private Context mContext;
    private Activity act;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private NameListAdapter mMenuAdapter;

    private TextView tv_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gxpg_name);
        //隐藏标题栏
        getSupportActionBar().hide();

        mContext = this;
        act=this;


        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new GridLayoutManager(this,4));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(this));// 添加分割线。

        mMenuAdapter = new NameListAdapter(PgdActivity.nameListResult);

        mMenuRecyclerView.setAdapter(mMenuAdapter);
        tv_group = (TextView) findViewById(R.id.tv_group);
        tv_group.setText("组别:"+PgdActivity.nameListResult.get(0).getEmp_Departname());
    }

}
