package com.goldemperor.ScanCode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.goldemperor.R;
import com.goldemperor.ScanCode.SupperInstock.ReportModel;
import com.goldemperor.ScanCode.SupperInstock.model.MessageEnum;
import com.goldemperor.Utils.LOG;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : ShowReportActivity
 * Created by : PanZX on  2018/6/5 21:51
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class  ShowReportActivity extends Activity {

    private ListView LV_Lsit;
    ReportModel RM;
    private Activity Act;
    MyAdapter MA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Act = this;
        ((TextView) findViewById(R.id.TV_Back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView) findViewById(R.id.TV_Submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Act)
                        .setMessage("请仔细核对指令号与尺码、数量，以免引起派工单分配错乱！")
                        .setTitle("报表检查完毕")
                        .setPositiveButton("检查完毕", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LOG.e("确认");
                                setResult(12);
                                finish();
                            }
                        })
                        .setNegativeButton("继续检查", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        LV_Lsit = (ListView) findViewById(R.id.LV_Lsit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            RM = (ReportModel) bundle.getSerializable("Report");
        }
        MA = new MyAdapter(RM.Report);
        LV_Lsit.setAdapter(MA);
        for (ReportModel.report reportModel : RM.Report) {
            LOG.E("List=" + reportModel.getFBillNo());
        }
    }

    class MyAdapter extends BaseAdapter {
        private List<ReportModel.report> list;

        public MyAdapter(List<ReportModel.report> l) {
            this.list = l;
            if (list == null) {
                list = new ArrayList<>();
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(Act);
            ReportModel.report data = list.get(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_report, null);
                holder = new ViewHolder();
                holder.LL_bkg = (LinearLayout) convertView.findViewById(R.id.LL_BKG);
                holder.TV_FBillNo = (TextView) convertView.findViewById(R.id.TV_FBillNo);
                holder.TV_FWorkCardNo = (TextView) convertView.findViewById(R.id.TV_FWorkCardNo);
                holder.TV_FICItemName = (TextView) convertView.findViewById(R.id.TV_FICItemName);
                holder.TV_FQty = (TextView) convertView.findViewById(R.id.TV_FQty);
                holder.TV_FSize = (TextView) convertView.findViewById(R.id.TV_FSize);
                holder.L1 = (View) convertView.findViewById(R.id.L1);
                holder.L2 = (View) convertView.findViewById(R.id.L2);
                holder.L3 = (View) convertView.findViewById(R.id.L3);
                holder.L4 = (View) convertView.findViewById(R.id.L4);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.TV_FBillNo.setText(data.getFBillNo());
            holder.TV_FQty.setText(data.getFQty() + "");
            if (data.getFBillNo().equals("合计")) {
                holder.LL_bkg.setBackgroundColor(Color.parseColor("#00ffff"));
                holder.L2.setBackgroundColor(Color.parseColor("#00ffff"));
            } else if (data.getFBillNo().contains("合计")) {
                holder.LL_bkg.setBackgroundColor(Color.parseColor("#FFFF00"));
                holder.L2.setBackgroundColor(Color.parseColor("#FFFF00"));
            } else {
                holder.LL_bkg.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.L2.setBackgroundColor(Color.parseColor("#000000"));
                holder.TV_FWorkCardNo.setText(data.getFWorkCardNo());
                holder.TV_FICItemName.setText(data.getFICItemName());
                holder.TV_FSize.setText(data.getFSize());
            }


            return convertView;
        }
    }

    public static class ViewHolder {
        LinearLayout LL_bkg;

        TextView TV_FBillNo;
        TextView TV_FWorkCardNo;
        TextView TV_FICItemName;
        TextView TV_FQty;
        TextView TV_FSize;
        View L1;
        View L2;
        View L3;
        View L4;
    }
}
