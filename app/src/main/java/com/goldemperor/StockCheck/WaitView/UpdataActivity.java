package com.goldemperor.StockCheck.WaitView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.goldemperor.R;


/**
 * Created by Administrator on 2017/6/26.
 */

public class UpdataActivity extends FragmentActivity {
    UpdataView fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment1,fragment=new UpdataView(),"updata");
        transaction.commit();
    }
}
