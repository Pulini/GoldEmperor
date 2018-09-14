package com.goldemperor.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.goldemperor.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * File Name : HelpActivity
 * Created by : PanZX on  2018/7/5 08:19
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_help)
public class HelpActivity extends Activity {
    @ViewInject(R.id.IV_Back)
    private ImageView IV_Back;
    @ViewInject(R.id.WV_Help)
    private WebView WV_Help;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        WV_Help.loadUrl(getIntent().getExtras().getString("URL"));
        WebSettings webSettings=WV_Help.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        IV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
