package com.goldemperor.ScanCode.WarehouseAllocation;

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
import android.widget.Toast;

import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * File Name : WarehouseSummary
 * Created by : PanZX on  2018/7/12 13:54
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：仓库调拨单 数据检查界面
 */
@ContentView(R.layout.activity_warehouse_summary)
public class WarehouseSummary extends Activity {

    @ViewInject(R.id.LV_Lsit)
    private ListView LV_Lsit;

    @ViewInject(R.id.TV_Back)
    private TextView TV_Back;

    @ViewInject(R.id.TV_Submit)
    private TextView TV_Submit;

    private Gson mGson;
    private String list;
    private String UserID;
//    private String ip = "http://192.168.101.112:9001/";
    private String FDCStockID;
    private Activity mActivity;
    private MyAdapter MA;
    private List<report> RL=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mGson = new Gson();
        mActivity=this;
        list = getIntent().getExtras().getString("List");
        UserID = getIntent().getExtras().getString("UserID");
        FDCStockID = getIntent().getExtras().getString("FDCStockID");
        LOG.e("list=" + list);
        MA = new MyAdapter(RL);
        LV_Lsit.setAdapter(MA);
        for (report reportModel :RL) {
            LOG.E("List=" + reportModel.getFBillNo());
        }
        TV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TV_Submit.setEnabled(false);
        TV_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity)
                        .setMessage("请仔细核对指令号与尺码、数量，以免引起派工单分配错乱！")
                        .setTitle("报表检查完毕")
                        .setPositiveButton("检查完毕", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LOG.e("确认");
                                setResult(RESULT_OK);
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
        getWarehouseData();
    }

    private void getWarehouseData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("barcodeJson", list);
        map.put("OrganizeID", "1");
        map.put("BillTypeID", "6");
        map.put("DefaultStockID",FDCStockID );
        map.put("Red", "1");
        map.put("UserID", UserID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.GetSubmitBarCodeReport,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {

                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("result=" + result);

                                JSONObject jb = new JSONObject(result);
                                String Result = jb.getString("ReturnType");
                                String ReturnMsg = jb.getString("ReturnMsg");
                                if (Result.equals("success")) {
                                    RL = mGson.fromJson(ReturnMsg, new TypeToken<List<report>>() {
                                    }.getType());
                                    if(RL.size()!=0){
                                        MA.Updata(RL);
                                    }
                                    TV_Submit.setEnabled(true);
                                } else {
                                    Toast.makeText(mActivity,ReturnMsg,Toast.LENGTH_LONG).show();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    class MyAdapter extends BaseAdapter {
        private List<report> list;

        public MyAdapter(List<report> l) {
            this.list = l;
            if (list == null) {
                list = new ArrayList<>();
            }
        }
        public void Updata(List<report> l){
            this.list = l;
            notifyDataSetChanged();
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
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            report data = list.get(position);
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
    public static class report implements Serializable {
        String FBillNo;
        String FWorkCardNo;
        String FICItemName;
        int FQty;
        String FSize;

        public String getFBillNo() {
            return FBillNo;
        }

        public void setFBillNo(String FBillNo) {
            this.FBillNo = FBillNo;
        }

        public String getFWorkCardNo() {
            return FWorkCardNo;
        }

        public void setFWorkCardNo(String FWorkCardNo) {
            this.FWorkCardNo = FWorkCardNo;
        }

        public String getFICItemName() {
            return FICItemName;
        }

        public void setFICItemName(String FICItemName) {
            this.FICItemName = FICItemName;
        }

        public int getFQty() {
            return FQty;
        }

        public void setFQty(int FQty) {
            this.FQty = FQty;
        }

        public String getFSize() {
            return FSize;
        }

        public void setFSize(String FSize) {
            this.FSize = FSize;
        }
    }


}

