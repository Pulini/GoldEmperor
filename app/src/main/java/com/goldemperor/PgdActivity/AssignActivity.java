package com.goldemperor.PgdActivity;

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

import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.goldemperor.PgdActivity.PgdActivity.selectWorkCardPlan;


/**
 * Created by Nova on 2017/7/25.
 */

public class AssignActivity extends AppCompatActivity {


    private Context mContext;
    private Activity act;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private AssignAdapter mMenuAdapter;
    private FancyButton btn_clear;
    private DbManager dbManager;
    private List<GxpgPlan> gxpgPlanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gxpg_assign);
        //隐藏标题栏
        getSupportActionBar().hide();
        Intent intent = getIntent();
        final String planBody = intent.getStringExtra("planBody");

        mContext = this;
        act=this;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        dbManager = initDb();

        btn_clear = (FancyButton) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    WhereBuilder b = WhereBuilder.b();
                    b.and("style", "=", planBody);
                    dbManager.delete(GxpgPlan.class, b);
                    gxpgPlanList.clear();
                    mMenuAdapter.notifyDataSetChanged();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(this));// 添加分割线。

        try {
            gxpgPlanList = dbManager.selector(GxpgPlan.class).where("style", " = ", planBody).findAll();
            for (GxpgPlan gxpgPlan : gxpgPlanList) {
                Log.e("Pan","占比:"+gxpgPlan.getPer());
            }
            mMenuAdapter = new AssignAdapter(gxpgPlanList);
            mMenuRecyclerView.setAdapter(mMenuAdapter);
        } catch (DbException e) {
            e.printStackTrace();
        }


    }
    public DbManager initDb() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                // 数据库的名字
                .setDbName("jindi")
                // 保存到指定路径
                // .setDbDir(new
                // File(Environment.getExternalStorageDirectory().getAbsolutePath()))
                // 数据库的版本号
                .setDbVersion(1)
                //设置数据库打开的监听
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启数据库支持多线程操作，提升性能，对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                //设置表创建的监听
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Log.i("jindi", "onTableCreated：" + table.getName());
                    }
                })
                // 数据库版本更新监听
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager arg0, int arg1, int arg2) {
                        LogUtil.e("数据库版本更新了！");
                    }
                });
        return x.getDb(daoConfig);
    }


}
