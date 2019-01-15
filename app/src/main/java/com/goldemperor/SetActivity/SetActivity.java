package com.goldemperor.SetActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.Public.SystemUtil;
import com.goldemperor.R;
import com.goldemperor.MainActivity.define;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;

/**
 * Created by Nova on 2017/7/17.
 */

public class SetActivity extends AppCompatActivity {

    private Activity mContext;
    private TextView web;
    private TextView Name;
    private TextView JobNumber;
    private TextView Sex;
    private TextView Position;


    private BootstrapButton exit;

    private String SYS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        SYS = SystemUtil.getSystemModel();
        mContext = this;

        web = findViewById(R.id.web);
        web.setText("服务器地址:"+SPUtils.getServerPath());

        Name = findViewById(R.id.nameContent);
        Name.setText((String)SPUtils.get(define.SharedUser, ""));

        JobNumber = findViewById(R.id.jobNumberContent);
        JobNumber.setText((String)SPUtils.get(define.SharedJobNumber, ""));

        Sex = findViewById(R.id.sexContent);
        Sex.setText((String)SPUtils.get(define.SharedSex, ""));

        Position = findViewById(R.id.positionContent);
        Position.setText((String)SPUtils.get(define.SharedPosition, ""));

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(v -> {
            SPUtils.put(define.SharedSuitID, "");
            SPUtils.put(define.SharedOrganizeID, "");
            SPUtils.put(define.SharedUser, "");
            SPUtils.put(define.SharedJobNumber, "");
            SPUtils.put(define.SharedEmpId, "");
            SPUtils.put(define.SharedSex, "");
            SPUtils.put(define.SharedPosition, "");
            SPUtils.put(define.SharedFDeptmentid, "");
            SPUtils.put(define.SharedPhone, "");
            SPUtils.put(define.SharedPassword, "");
            SPUtils.put(define.SharedUserId,"");
            SPUtils.put(define.SharedDefaultStockID, "");
            SPUtils.put(define.SharedDeptmentName, "");
//            SPUtils.put(define.LoginPhone, "");
            SPUtils.put(define.LoginType, "");

            LOG.e("型号:" + SYS);
//            if (
//                    !SYS.equals("3280")
//                    ) {
//                Intent i = new Intent(mContext, LoginActivity.class);
//                mContext.startActivity(i);
//            }
            mContext.finish();
        });
    }
}
