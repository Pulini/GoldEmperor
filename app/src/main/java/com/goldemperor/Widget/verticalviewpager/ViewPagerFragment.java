package com.goldemperor.Widget.verticalviewpager;

/**
 * File Name : ViewPagerFragment
 * Created by : PanZX on  2018/9/29 20:27
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public class ViewPagerFragment extends Fragment {
    private boolean hasCreateView;
    private boolean isFragmentVisible;
    protected View rootView;

    public ViewPagerFragment() {
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.e("Pan", "setUserVisibleHint() -> isVisibleToUser: " + isVisibleToUser);
        if (this.rootView != null) {
            this.hasCreateView = true;
            if (isVisibleToUser) {
                this.onFragmentVisibleChange(true);
                this.isFragmentVisible = true;
            } else if (this.isFragmentVisible) {
                this.onFragmentVisibleChange(false);
                this.isFragmentVisible = false;
            }
        }

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initVariable();
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!this.hasCreateView && this.getUserVisibleHint()) {
            this.onFragmentVisibleChange(true);
            this.isFragmentVisible = true;
        }

    }


    private void initVariable() {
        this.hasCreateView = false;
        this.isFragmentVisible = false;
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
//        Log.e("Pan", "onFragmentVisibleChange -> isVisible: " + isVisible);
    }

}
