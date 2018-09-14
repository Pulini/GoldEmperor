package com.goldemperor.MainActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.goldemperor.R;

public class MainActivity extends AppCompatActivity {


    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //隐藏标题栏
        getSupportActionBar().hide();
        mContext=this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(mContext, ContentActivity.class);
                mContext.startActivity(i);
                finish();
            }
        }, 1500);
    }

}
