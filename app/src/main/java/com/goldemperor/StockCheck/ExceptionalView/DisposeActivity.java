package com.goldemperor.StockCheck.ExceptionalView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.goldemperor.R;

/**
 * Created by Nova on 2017/7/22.
 */

public class DisposeActivity extends AppCompatActivity {

    DisposeView fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment1, fragment = new DisposeView(), "DisposeView");
        transaction.commit();
    }
}
