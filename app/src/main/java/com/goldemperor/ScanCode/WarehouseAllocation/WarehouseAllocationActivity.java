package com.goldemperor.ScanCode.WarehouseAllocation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ScanCode.LoginActivity;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nlscan.android.scan.ScanManager;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * File Name : WarehouseAllocationActivity
 * Created by : PanZX on  2018/7/10 11:32
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：仓库调拨单
 */
@ContentView(R.layout.activity_warehouse_allocation)
public class WarehouseAllocationActivity extends Activity implements View.OnClickListener {


    @ViewInject(R.id.ET_Add)
    private EditText ET_Add;

    @ViewInject(R.id.B_Add)
    private Button B_Add;

    @ViewInject(R.id.TV_Out)
    private TextView TV_Out;

    @ViewInject(R.id.TV_In)
    private TextView TV_In;

    @ViewInject(R.id.SMRV_data)
    private SwipeMenuRecyclerView SMRV_data;

    @ViewInject(R.id.TV_Sum)
    private TextView TV_Sum;

    @ViewInject(R.id.B_Out)
    private Button B_Out;

    @ViewInject(R.id.B_In)
    private Button B_In;

    @ViewInject(R.id.B_Clear)
    private Button B_Clear;

    @ViewInject(R.id.B_smb)
    private Button B_smb;

    @ViewInject(R.id.RL_loading)
    private RelativeLayout RL_loading;

    private Activity mActivity;
    public Gson mGson;
    private List<WarehouseListModel> WLM = new ArrayList<>();
    private List<List<String>> WarehouseList2 = new ArrayList<>();
    private OptionsPickerView OPVWarehouse;
    boolean isOut = true;
    //        private String ip = "http://192.168.101.112:9001/";
    private String UserId = "";
    private String FSCStockID = "";//出库
    private String FDCStockID = "";//入库
    private WarehouseAdapter myAdapter;
    private List<String> list = new ArrayList<>();
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    boolean isfirst = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        mGson = new Gson();
        initview();
        if (isfirst) {
            isfirst = false;
            startActivityForResult(new Intent(WarehouseAllocationActivity.this, LoginActivity.class), 10);
        }
    }

    private void initview() {
        B_Out.setEnabled(false);
        B_In.setEnabled(false);

        B_Add.setOnClickListener(this);
        RL_loading.setOnClickListener(this);
        B_Out.setOnClickListener(this);
        B_In.setOnClickListener(this);
        B_Clear.setOnClickListener(this);
        B_smb.setOnClickListener(this);

        String l = dataPref.getString(define.SharedScanList, "");
        LOG.e("l=" + l);
        if (!l.equals("")) {
            list = mGson.fromJson(l, new TypeToken<List<String>>() {
            }.getType());
            TV_Sum.setText("已扫描:" + list.size() + "条");
        }
        myAdapter = new WarehouseAdapter(list);
        SMRV_data.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        SMRV_data.addItemDecoration(new ListViewDecoration(this));// 添加分割线。
        SMRV_data.setAdapter(myAdapter);
        // 设置菜单创建器。
        SMRV_data.setSwipeMenuCreator(swipeMenuCreator);
        SMRV_data.setSwipeMenuItemClickListener(menuItemClickListener);
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = Utils.dp2px(60);
            int height = Utils.dp2px(40);

            SwipeMenuItem deleteItem = new SwipeMenuItem(mActivity)
                    .setBackground(R.drawable.selector_red)
//                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                    .setTextSize(15)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    private SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                //Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                if (menuPosition == 0) {
                    list.remove(adapterPosition);
                    myAdapter.UpData(list);
                    dataEditor.putString(define.SharedScanList, mGson.toJson(list));
                    dataEditor.commit();
                    TV_Sum.setText("已扫描:" + list.size() + "条");
                }
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                //Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }

        }

    };

    /**
     * 设置选择器
     */
    private void setOPV() {
        //条件选择器
        OPVWarehouse = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                LOG.e("options1=" + options1 + "options2=" + options2 + "options3=" + options3);
                //返回的分别是三个级别的选中位置
                String tx = WLM.get(options1).getPickerViewText() + "——"
                        + WarehouseList2.get(options1).get(options2);
                if (isOut) {
                    TV_Out.setText(tx);
                    FSCStockID = WLM.get(options1).getEntryList().get(options2).getFItemID() + "";
                    LOG.e("FSCStockID=" + FSCStockID);
                } else {
                    TV_In.setText(tx);
                    FDCStockID = WLM.get(options1).getEntryList().get(options2).getFItemID() + "";
                    LOG.e("FDCStockID=" + FDCStockID);
                }

            }
        }).build();
        OPVWarehouse.setPicker(WLM, WarehouseList2);
    }

    /**
     * 获取仓库列表
     */
    private void getWarehouseList() {
        RL_loading.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("OrganizeID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.GetStockList,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        RL_loading.setVisibility(View.GONE);
                        if (result != null) {
                            LOG.E("result=" + result);
                            WLM = mGson.fromJson(result, new TypeToken<List<WarehouseListModel>>() {
                            }.getType());
                            if (WLM != null && WLM.size() > 0) {
                                B_Out.setEnabled(true);
                                B_In.setEnabled(true);
                                for (WarehouseListModel w : WLM) {
                                    List<String> list = new ArrayList<>();
                                    for (WarehouseListModel.Entry entry : w.EntryList) {
                                        list.add(entry.getFName());
                                    }
                                    WarehouseList2.add(list);
                                }
                                setOPV();
                            }
                            getDefaultStockIDForDB();
                        } else {
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /**
     * 获取默认仓库
     */
    private void getDefaultStockIDForDB() {
        RL_loading.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("OrganizeID", "1");
        map.put("UserID", UserId);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.GetDefaultStockIDForDB,
                map,
                new WebServiceUtils.WebServiceCallBack() {

                    @Override
                    public void callBack(String result) {
                        RL_loading.setVisibility(View.GONE);
                        if (result != null) {
                            LOG.E("result=" + result);
                            //{FDCStockID:4791,FSCStockID:59199}
                            try {
                                JSONObject jb = new JSONObject(result);
                                int FD = jb.getInt("FDCStockID");
                                int FS = jb.getInt("FSCStockID");
                                for (int i = 0; i < WLM.size(); i++) {
                                    for (int i1 = 0; i1 < WLM.get(i).getEntryList().size(); i1++) {
                                        if (WLM.get(i).getEntryList().get(i1).getFItemID() == FD) {
                                            TV_In.setText(WLM.get(i).getFName() + "——" + WLM.get(i).getEntryList().get(i1).getFName());
                                            FDCStockID = FD + "";
                                        }
                                        if (WLM.get(i).getEntryList().get(i1).getFItemID() == FS) {
                                            TV_Out.setText(WLM.get(i).getFName() + "——" + WLM.get(i).getEntryList().get(i1).getFName());
                                            FSCStockID = FS + "";
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getWarehouseData() {
        RL_loading.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        List<data> l = new ArrayList<>();
        for (String s : list) {
            l.add(new data(s));
        }
        map.put("barcodeJson", mGson.toJson(l));
        map.put("OrganizeID", "1");
        map.put("BillTypeID", "6");
        map.put("FSCStockID", FSCStockID);
        map.put("FDCStockID", FDCStockID);
        map.put("Red", "1");
        map.put("UserID", UserId);
        map.put("SEmpCode", "");
        map.put("FEmpCode", "");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.SubmitBarCodeBarCode2Ck_RequisitionSlipCollectBillByEmpCode,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        RL_loading.setVisibility(View.GONE);
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("result=" + result);
                                JSONObject jb = new JSONObject(result);
                                String Result = jb.getString("Result");
                                String StockBillNO = jb.getString("StockBillNO");
                                if (Result.equals("success")) {
                                    Toast.makeText(mActivity, "提交成功:" + StockBillNO, Toast.LENGTH_LONG).show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                                    builder.setMessage(StockBillNO);
                                    builder.setTitle("提交失败");
                                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
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

    private void goSummary() {
        Intent i = new Intent(WarehouseAllocationActivity.this, WarehouseSummary.class);
        List<data> l = new ArrayList<>();
        for (String s : list) {
            l.add(new data(s));
        }
        i.putExtra("List", mGson.toJson(l));
        i.putExtra("UserID", UserId);
        i.putExtra("FDCStockID", FDCStockID);
        startActivityForResult(i, 20);
    }

    private class data {
        String D_BarCode;

        public data(String s) {
            D_BarCode = s;
        }

        public String getD_BarCode() {
            return D_BarCode;
        }

        public void setD_BarCode(String d_BarCode) {
            D_BarCode = d_BarCode;
        }
    }

    private void registerReceiver() {
        IntentFilter intFilter = new IntentFilter(ScanManager.ACTION_SEND_SCAN_RESULT);
        registerReceiver(mReceiver, intFilter);
    }

    private void unRegisterReceiver() {
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }
    }

    /**
     * 监听扫码数据的广播，当设置广播输出时作用该方法获取扫码数据
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ScanManager.ACTION_SEND_SCAN_RESULT.equals(action)) {
                byte[] bvalue1 = intent.getByteArrayExtra(ScanManager.EXTRA_SCAN_RESULT_ONE_BYTES);
                byte[] bvalue2 = intent.getByteArrayExtra(ScanManager.EXTRA_SCAN_RESULT_TWO_BYTES);
                String svalue1 = null;
                String svalue2 = null;
                try {
                    if (bvalue1 != null)
                        svalue1 = new String(bvalue1, "GBK");
                    if (bvalue2 != null)
                        svalue2 = new String(bvalue1, "GBK");
                    svalue1 = svalue1 == null ? "" : svalue1;
                    svalue2 = svalue2 == null ? "" : svalue2;
//                    tv_broadcast_result.setText(svalue1+"\n"+svalue2);
                    final String scanResult = svalue1.replaceAll("(\r\n|\r|\n|\n\r)", "");//svalue1+"\n"+svalue2//替换回车
                    LOG.e("scanResult=" + scanResult);
                    if (!list.contains(scanResult)) {
                        list.add(scanResult);
                        ET_Add.setText(scanResult);
                        Collections.sort(list);
                        dataEditor.putString(define.SharedScanList, mGson.toJson(list));
                        dataEditor.commit();
                    }
                    myAdapter.UpData(list);
                    TV_Sum.setText("已扫描:" + list.size() + "条");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RL_loading:
                LOG.e("RL_loading");
                break;
            case R.id.B_Add:
                String code = ET_Add.getText().toString().trim();
                if (!list.contains(code)) {
                    list.add(code);
                    Collections.sort(list);
                    dataEditor.putString(define.SharedScanList, mGson.toJson(list));
                    dataEditor.commit();
                    myAdapter.UpData(list);
                    TV_Sum.setText("已扫描:" + list.size() + "条");
                }
                break;
            case R.id.B_Out:
                isOut = true;
                OPVWarehouse.show();
                break;
            case R.id.B_In:
                isOut = false;
                OPVWarehouse.show();
                break;
            case R.id.B_Clear:
                if (list.size() == 0) {
                    Toast.makeText(mActivity, "没有数据可以清空", Toast.LENGTH_LONG).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("确定要清空数据吗");
                builder.setTitle("清空");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.clear();
                        myAdapter.UpData(list);
                        dataEditor.putString(define.SharedScanList, "");
                        dataEditor.commit();
                        TV_Sum.setText("已扫描:" + list.size() + "条");
                        dialog.dismiss();
                    }
                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                break;
            case R.id.B_smb:
                if (list.size() == 0) {
                    Toast.makeText(mActivity, "请扫描调拨单条码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (FSCStockID.equals("") || FDCStockID.equals("")) {
                    if (WLM.size() == 0) {
                        LOG.e("FSCStockID=" + FSCStockID);
                        LOG.e("FDCStockID=" + FDCStockID);
                        startActivityForResult(new Intent(WarehouseAllocationActivity.this, LoginActivity.class), 10);
                    } else {
                        Toast.makeText(mActivity, "请选择调出调入仓库", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                goSummary();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOG.e("requestCode=" + requestCode + "resultCode=" + resultCode);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case 10:
                    UserId = data.getStringExtra("UserID");
                    LOG.e("UserId=" + UserId);
                    getWarehouseList();
                    break;
                case 20:
                    LOG.e("UserId=" + UserId);
                    getWarehouseData();
                    break;


            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();

    }
}
