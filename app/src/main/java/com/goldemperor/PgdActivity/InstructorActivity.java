package com.goldemperor.PgdActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.define;

import com.goldemperor.R;
import com.goldemperor.StockCheck.ViewPageAdapter;
import com.goldemperor.Widget.PinchImageView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nova on 2017/7/25.
 */

public class InstructorActivity extends AppCompatActivity {


    private Context mContext;
    private Activity act;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private InstructorAdapter mMenuAdapter;

    private List<View> viewList;
    public ViewPager pager;
    ViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gxpg_instructor);
        //隐藏标题栏
        getSupportActionBar().hide();

        mContext = this;
        act=this;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();

        viewList = new ArrayList<View>();

        List<Integer> res=new ArrayList<Integer>();
        res.add(R.drawable.k10713_70_7);
        res.add(R.drawable.k10713_70_8);
        res.add(R.drawable.k10713_70_9);
        res.add(R.drawable.k10713_70_10);
        res.add(R.drawable.k10713_70_11);
        pager = (ViewPager) findViewById(R.id.pager);

        for(int i=0;i<res.size();i++) {
            View imageView = View.inflate(this, R.layout.activity_gxpg_instructor_list, null);
            PinchImageView im_instructor = (PinchImageView) imageView.findViewById(R.id.im_instructor);
            im_instructor.setImageResource(res.get(i));
            viewList.add(imageView);
        }
        adapter = new ViewPageAdapter(viewList);
        pager.setAdapter(adapter);

        /*
        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(this));// 添加分割线。


        mMenuAdapter = new InstructorAdapter(res);

        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
        */

    }

    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

}
