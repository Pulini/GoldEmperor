package com.goldemperor.MainActivity.NewHome.Pad;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.goldemperor.MainActivity.MainActivity;
import com.goldemperor.MainActivity.NewHome.HomeUtils;
import com.goldemperor.R;
import com.goldemperor.UpDataAPK.UpData;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.verticalviewpager.VerticalViewPager;
import com.goldemperor.Widget.verticalviewpager.transforms.DefaultTransformer;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * File Name : HomeForPadActivity
 * Created by : PanZX on  2018/9/29 20:08
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_home_for_pad)
public class HomeForPadActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks {

    @ViewInject(R.id.VVP_Home)
    private VerticalViewPager VVP_Home;

    @ViewInject(R.id.TV_Home1)
    private TextView TV_Home1;

    @ViewInject(R.id.TV_Home2)
    private TextView TV_Home2;

    @ViewInject(R.id.V_Home1)
    private View V_Home1;

    @ViewInject(R.id.V_Home2)
    private View V_Home2;

    List<Fragment> mFragment = new ArrayList<>();
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        mActivity = this;
        initview();
    }

    private void initview() {
        mFragment.add(new HomeForPadFragment1());
        mFragment.add(new HomeForPadFragment2());
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        VVP_Home.setAdapter(fragmentPagerAdapter);
        VVP_Home.setPageTransformer(false, new DefaultTransformer());
        VVP_Home.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    TV_Home1.setTextColor(Color.parseColor("#ffffff"));
                    TV_Home2.setTextColor(Color.parseColor("#555555"));
                } else {
                    TV_Home1.setTextColor(Color.parseColor("#555555"));
                    TV_Home2.setTextColor(Color.parseColor("#ffffff"));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        V_Home1.setOnClickListener(v -> VVP_Home.setCurrentItem(0));
        V_Home2.setOnClickListener(v -> VVP_Home.setCurrentItem(1));
        getPermissions();

    }

    @Override
    protected void onResume() {
        super.onResume();
        UpData.checkVersion(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(HomeUtils.PERMISSION)
    public void getPermissions() {
        if (!EasyPermissions.hasPermissions(this, HomeUtils.perms)) {
            EasyPermissions.requestPermissions(this, "需要权限", HomeUtils.PERMISSION, HomeUtils.perms);
        }
    }

    @Override
    public void onBackPressed() {
        LemonHello.getWarningHello("退出", "确定要退出GoldEmperor吗?")
                .addAction(new LemonHelloAction("退出", (helloView, helloInfo, helloAction) -> {
                    helloView.hide();
                    System.exit(0);
                }), new LemonHelloAction("取消", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
