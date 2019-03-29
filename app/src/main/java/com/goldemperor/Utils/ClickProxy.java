package com.goldemperor.Utils;

import android.view.View;

/**
 * File Name : ClickProxy
 * Created by : PanZX on  2019/3/8 0008
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：点击事件代理，防止重复点击
 */
public class ClickProxy implements View.OnClickListener {

    private View.OnClickListener origin;
    private long lastclick = 0;
    private long timems = 1000; //ms
    private IAgain mIAgain;

    public ClickProxy(View.OnClickListener origin) {
        this.origin = origin;
    }

    public ClickProxy(View.OnClickListener origin, IAgain again) {
        this.origin = origin;
        this.mIAgain = again;
    }

    public ClickProxy(View.OnClickListener origin, long timems, IAgain again) {
        this.origin = origin;
        this.mIAgain = again;
        this.timems = timems;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastclick >= timems) {
            origin.onClick(v);
            lastclick = System.currentTimeMillis();
        } else {
            if (mIAgain != null) mIAgain.onAgain();
        }
    }

    public interface IAgain {
        void onAgain();//重复点击
    }
}