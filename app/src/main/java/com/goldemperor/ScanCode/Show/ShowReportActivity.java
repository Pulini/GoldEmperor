package com.goldemperor.ScanCode.Show;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * File Name : ShowReportActivity
 * Created by : PanZX on  2018/6/5 21:51
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：扫码汇总表
 */
@ContentView(R.layout.activity_show)
public class ShowReportActivity extends Activity {

    @ViewInject(R.id.LV_Lsit)
    private ListView LV_Lsit;

    private List<ReportModel> RDML;
    private Activity Act;
    MyAdapter MA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Act = this;
        findViewById(R.id.TV_Back).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.TV_Submit).setOnClickListener(v -> new AlertDialog.Builder(Act)
                .setMessage("请仔细核对指令号与尺码、数量，以免引起派工单分配错乱！")
                .setTitle("报表检查完毕")
                .setPositiveButton("检查完毕", (dialog, which) -> {
                    LOG.e("确认");
                    setResult(RESULT_OK);
                    finish();
                })
                .setNegativeButton("继续检查", (dialog, which) -> dialog.dismiss()).show());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            RDML = new Gson().fromJson((String) bundle.getSerializable("Report"), new TypeToken<List<ReportModel>>() {
            }.getType());
        }

        MA = new MyAdapter(RDML);
        LV_Lsit.setAdapter(MA);


    }


    private TextView GetContext(String text, String TC, String BC, int width) {
        if (TC == null && "".equals(TC)) {
            TC = "#000000";
        }
        TextView TV = new TextView(Act);
        TV.setLayoutParams(new LinearLayout.LayoutParams(Utils.dp2px(width), Utils.dp2px(40)));
        TV.setTextColor(Color.parseColor(TC));
        if(BC!=null&!"".equals(BC)){
            TV.setBackgroundColor(Color.parseColor(BC));
        }
        TV.setTextSize(15);
        TV.setGravity(Gravity.CENTER);
        TV.setText(text);
        return TV;
    }

    private View GetInterval() {
        View V = new TextView(Act);
        V.setLayoutParams(new LinearLayout.LayoutParams(Utils.dp2px(1), Utils.dp2px(30)));
        V.setBackgroundColor(Color.parseColor("#ffffff"));
        return V;
    }


    class MyAdapter extends BaseAdapter {
        private List<ReportModel> Data;


        public MyAdapter(List<ReportModel> data) {
            Data = data;
        }

        @Override
        public int getCount() {
            return Data.size();
        }

        @Override
        public Object getItem(int position) {
            return Data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ReportModel data = Data.get(position);
            LinearLayout LL = new LinearLayout(Act);
            LL.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, Utils.dp2px(40)));
            LL.setGravity(Gravity.CENTER);
            LL.setBackgroundColor(Color.parseColor(data.getBackgroundColor()));
            for (int i = 0; i < data.getDataList().size(); i++) {
                ReportModel.list D = data.getDataList().get(i);
                if (D.isFVisible()) {
                    LL.addView(GetContext(D.getFContent(), D.getFForeColor(),D.getFBackgroundColor(), D.getFWidth()));
                    if (i < data.getDataList().size() - 1) {
                        LL.addView(GetInterval());
                    }
                }
            }


            return LL;
        }
    }


}
