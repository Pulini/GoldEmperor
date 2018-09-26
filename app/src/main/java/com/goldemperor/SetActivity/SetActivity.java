package com.goldemperor.SetActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.goldemperor.LoginActivity.LoginActivity;
import com.goldemperor.R;
import com.goldemperor.MainActivity.define;

/**
 * Created by Nova on 2017/7/17.
 */

public class SetActivity extends AppCompatActivity {

    private Activity mContext;
    private TextView Name;
    private TextView JobNumber;
    private TextView Sex;
    private TextView Position;


    private BootstrapButton exit;

    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        mContext = this;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();

        Name = (TextView) findViewById(R.id.nameContent);
        Name.setText(dataPref.getString(define.SharedUser, ""));

        JobNumber = (TextView) findViewById(R.id.jobNumberContent);
        JobNumber.setText(dataPref.getString(define.SharedJobNumber, ""));

        Sex = (TextView) findViewById(R.id.sexContent);
        Sex.setText(dataPref.getString(define.SharedSex, ""));

        Position = (TextView) findViewById(R.id.positionContent);
        Position.setText(dataPref.getString(define.SharedPosition, ""));

        exit = (BootstrapButton) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataEditor.putString(define.SharedPassword,define.NONE);
                dataEditor.commit();
                Intent i = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(i);
                mContext.finish();
            }
        });
    }
}
