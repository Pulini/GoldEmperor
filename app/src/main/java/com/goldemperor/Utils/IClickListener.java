package com.goldemperor.Utils;

import android.view.View;

/**
 * File Name : IClickListener
 * Created by : PanZX on  2019/3/8 0008
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public abstract class IClickListener implements View.OnClickListener {
    private long mLastClickTime = 0;
    public static final int TIME_INTERVAL = 1000;

    @Override
    public final void onClick(View v) {
        if (System.currentTimeMillis() - mLastClickTime >= TIME_INTERVAL) {
            onIClick(v);
            mLastClickTime = System.currentTimeMillis();
        } else {
            onAgain(v);
        }
    }

    protected abstract void onIClick(View v);

    protected void onAgain(View v) {

    }
}