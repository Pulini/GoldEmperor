package com.goldemperor.PzActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.define;
import com.goldemperor.Public.SystemUtil;
import com.goldemperor.R;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;
import com.google.gson.Gson;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.goldemperor.Widget.fancybuttons.FancyButton;

import static com.goldemperor.PzActivity.PgdActivity.selectWorkCardPlan;
import static com.goldemperor.PzActivity.YCListActivity.selectAbnormity;

/**
 * Created by Nova on 2017/10/28.
 */

public class ZjActivity extends AppCompatActivity {

    private Context mContext;
    private Activity act;

    private TextView name;
    private TextView jobNumber;
    private TextView PaiNumber;
    private TextView PaiCount;
    private TextView Line;

    private TextView CountText;
    private TextView Count;

    private TextView qualifiedCountText;
    private TextView qualifiedCount;

    private TextView unqualifiedCountText;
    private TextView unqualifiedCount;
    private int unqualifiedCountint;
    private FancyButton btn_unqualified;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;



    private TextView tv_reason;
    private int base=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zj);
        //隐藏标题栏
        getSupportActionBar().hide();
        mContext = this;
        act = this;

        unqualifiedCountint = YCListActivity.YcCount;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        name = (TextView) findViewById(R.id.name);
        jobNumber = (TextView) findViewById(R.id.jobNumber);

        PaiNumber = (TextView) findViewById(R.id.PaiNumber);
        PaiCount = (TextView) findViewById(R.id.PaiCount);
        Line = (TextView) findViewById(R.id.Line);
        CountText = (TextView) findViewById(R.id.CountText);
        Count = (TextView) findViewById(R.id.Count);

        qualifiedCountText = (TextView) findViewById(R.id.qualifiedCountText);
        qualifiedCount = (TextView) findViewById(R.id.qualifiedCount);

        unqualifiedCountText= (TextView) findViewById(R.id.unqualifiedCountText);
        unqualifiedCount = (TextView) findViewById(R.id.unqualifiedCount);
        btn_unqualified = (FancyButton) findViewById(R.id.btn_unqualified);
        tv_reason = (TextView) findViewById(R.id.tv_reason);


        name.setText(dataPref.getString(define.SharedUser, ""));
        jobNumber.setText(dataPref.getString(define.SharedJobNumber, ""));
        PaiNumber.setText(selectWorkCardPlan.getOrderbill());
        PaiCount.setText(String.valueOf(PgdActivity.PaiCount));
        Line.setText(selectWorkCardPlan.getFgroup());

        if(selectWorkCardPlan.getFgroup().contains("针车")){
            CountText.setText("当日质检总数(只)");
            qualifiedCountText.setText("合格总数(只)");
            unqualifiedCountText.setText("不合格总数(只)");
            base=2;
        }
        qualifiedCount.setText(String.valueOf(selectWorkCardPlan.getFfinishqty()*base));
        unqualifiedCount.setText(String.valueOf(unqualifiedCountint));
        Count.setText(String.valueOf(unqualifiedCountint + selectWorkCardPlan.getFfinishqty()*base));

        btn_unqualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_reason.getText().length() < 2 || tv_reason.getText().equals("原因")) {
                    Toast.makeText(mContext, "请选择不合格原因", Toast.LENGTH_LONG).show();
                } else {
                    unqualifiedCountint++;
                    unqualifiedCount.setText(String.valueOf(unqualifiedCountint));
                    Count.setText(String.valueOf(unqualifiedCountint + selectWorkCardPlan.getFfinishqty()*base));
                    submitException();
                }
            }
        });

        tv_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, com.goldemperor.PzActivity.ReasonActivity.class);
                startActivityForResult(i, 0);
            }
        });

    }

    private void submitException() {
        WorkCardAbnormityModel model = new WorkCardAbnormityModel();
        model.setFEmpID(Integer.valueOf(dataPref.getString(define.SharedEmpId, "")));
        model.setFDeptmentID(Integer.valueOf(dataPref.getString(define.SharedFDeptmentid, "")));
        model.setFQty((long) 1);
        model.setFWorkCardInterID((int) selectWorkCardPlan.getFinterid());
        model.setFWorkCardEntryID(selectWorkCardPlan.getFentryid());

        List<WorkCardAbnormityEntryModel> entryModelList = new ArrayList<WorkCardAbnormityEntryModel>();
        int entryid = 0;
        for (int i = 0; i < selectAbnormity.size(); i++) {
            if (selectAbnormity.get(i)[3].equals("已选")) {
                WorkCardAbnormityEntryModel entryModel = new WorkCardAbnormityEntryModel();
                entryModel.setFEntryID(entryid);
                entryModel.setFExceptionID(Integer.valueOf(selectAbnormity.get(i)[1]));
                entryModel.setFExceptionLevel(Integer.valueOf(selectAbnormity.get(i)[2]));
                entryModelList.add(entryModel);
                entryid++;
            }
        }


        model.setEntry(entryModelList);

        Gson g = new Gson();
        RequestParams params = new RequestParams(define.Net2 + define.WorkCardAbnormityBysuitID);
        params.addQueryStringParameter("json", g.toJson(model, WorkCardAbnormityModel.class));
        params.addQueryStringParameter("suitID", define.suitID);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (result.contains("success")) {
                } else {
                    Toast.makeText(mContext, "提交失败,请检查网络连接", Toast.LENGTH_LONG).show();
                }
                Log.e("jindi", result);
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());

                LemonHello.getErrorHello("提示", "提交失败:" + ex.toString())
                        .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                            }
                        }))
                        .show(act);

            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }



    public void freshReason() {
        String text = "";
        for (int i = 0; i < selectAbnormity.size(); i++) {
            if (selectAbnormity.get(i)[3].equals("已选")) {
                if (selectAbnormity.get(i)[2].equals("0")) {
                    text += "," + selectAbnormity.get(i)[0] + "(轻微)";
                } else {
                    text += "," + selectAbnormity.get(i)[0] + "(严重)";
                }
            }
        }
        text = text.replaceFirst(",", "");
        tv_reason.setText(text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        freshReason();
    }
}
