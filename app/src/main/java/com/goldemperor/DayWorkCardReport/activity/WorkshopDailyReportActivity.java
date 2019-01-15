package com.goldemperor.DayWorkCardReport.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.ImageView;

import com.goldemperor.DayWorkCardReport.Fragment.DailyReportOneFragment;
import com.goldemperor.DayWorkCardReport.Fragment.DailyReportTwoFragment;
import com.goldemperor.R;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : WorkshopDailyReportActivity
 * Created by : PanZX on  2018/12/11 10:11
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：生产车间日报表
 */
@ContentView(R.layout.activity_workshop_daily_report)
public class WorkshopDailyReportActivity extends FragmentActivity {

    @ViewInject(R.id.VP_Report)
    private ViewPager VP_Report;

    @ViewInject(R.id.IV_Back)
    private ImageView IV_Back;


    private Activity mActivity;
    List<Fragment> mFragment = new ArrayList<>();
    FragmentPagerAdapter FPA;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        mActivity = this;
        initview();
    }

    private void initview() {
        mFragment.add(new DailyReportOneFragment());
        mFragment.add(new DailyReportTwoFragment());
        FPA = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        VP_Report.setAdapter(FPA);
        IV_Back.setOnClickListener(v -> finish());
    }
}
