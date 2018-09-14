package com.goldemperor.ScanCode.ProcessReportInstock;

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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ScanCode.ScInstock.deleteslide.ActionSheet;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.ScanCode.LoginActivity;
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
import java.util.HashMap;
import java.util.List;

/**
 * File Name : ProcessReportInstockActivity
 * Created by : PanZX on  2018/8/16 14:58
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：工序汇报入库
 */
@ContentView(R.layout.activity_process_report_instock)
public class ProcessReportInstockActivity extends Activity {

    @ViewInject(R.id.SMEL_List)
    private SwipeMenuRecyclerView SMEL_List;

    @ViewInject(R.id.ET_Numbers)
    private EditText ET_Numbers;

    @ViewInject(R.id.TV_Sum)
    private TextView TV_Sum;

    @ViewInject(R.id.B_Add)
    private Button B_Add;

    @ViewInject(R.id.B_Clear)
    private Button B_Clear;

    @ViewInject(R.id.B_Submit)
    private Button B_Submit;


    List<CodeDataModel> CDM = new ArrayList<>();//条码数据
    List<ProcressFlowModel> PFM = new ArrayList<>();//制程类型
    List<String> NameList = new ArrayList<>();//制程名單
    private PRI_Adapter PRIA;
    private Activity mActivity;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    private Gson mGson;
    private OptionsPickerView PKV;
    private boolean isfirst = true;
    private String UserId = "";
    private String FProcessFlowID = "";
    private String FProcessNodeName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        initview();
        if (isfirst) {
            isfirst = false;
            startActivityForResult(new Intent(ProcessReportInstockActivity.this, LoginActivity.class), 10);
        }
    }

    private void initview() {
        mGson = new Gson();
        dataPref = getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        String data = dataPref.getString(define.PRA_BarCode, "");
        if (!"".equals(data)) {
            CDM = mGson.fromJson(data, new TypeToken<List<CodeDataModel>>() {
            }.getType());
            TV_Sum.setText("已扫描:" + CDM.size() + "条");
        }
        PRIA = new PRI_Adapter(CDM);
        SMEL_List.setLayoutManager(new LinearLayoutManager(mActivity));// 布局管理器。
        SMEL_List.addItemDecoration(new ListViewDecoration(mActivity));// 添加分割线。
        SMEL_List.setSwipeMenuCreator(swipeMenuCreator);
        SMEL_List.setSwipeMenuItemClickListener(onSwipeMenuItemClickListener);
        SMEL_List.setAdapter(PRIA);

        B_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ET_Numbers.getText().toString();
                if (s.equals("")) {
                    Toast.makeText(mActivity, "请输入单号", Toast.LENGTH_SHORT).show();
                } else {
                    CDM.add(new CodeDataModel(s));
                    PRIA.notifyItemInserted(CDM.size());
                    TV_Sum.setText("已扫描:" + CDM.size() + "条");
                }

            }
        });
        B_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("确定要清空数据吗");
                builder.setTitle("清空");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CDM.clear();
                        dataEditor.putString(define.PRA_BarCode, "");
                        dataEditor.commit();
                        dialog.dismiss();
                        PRIA.notifyDataSetChanged();
                        TV_Sum.setText("已扫描:" + CDM.size() + "条");
                    }
                }).setNegativeButton("取消", null).show();

            }
        });
        B_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CDM.size() == 0) {
                    Toast.makeText(mActivity, "没有可提交的单号", Toast.LENGTH_SHORT).show();
                } else {
                    if ("".equals(UserId)) {
                        startActivityForResult(new Intent(ProcessReportInstockActivity.this, LoginActivity.class), 15);
                    } else {
                        if (PFM.size() == 0) {
                            GetProcessFlowInfoByBarCode();
                        } else {
                            if (!PKV.isShowing()) {
                                PKV.show();
                            }
                        }
                    }
                }
            }
        });
    }


    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu1, int i) {
            int width = Utils.dp2px(80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem addItem = new SwipeMenuItem(mActivity)
                    .setBackgroundColor(Color.parseColor("#882222"))
                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeMenu1.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
        }
    };


    private SwipeMenuItemClickListener onSwipeMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {


            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(mActivity, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                Toast.makeText(mActivity, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                if (menuPosition == 0) {
                    //弹出删除确认窗体
                    ActionSheet.showSheet(mActivity, new ActionSheet.OnActionSheetSelected() {
                        @Override
                        public void onClick(int whichButton) {
                            PRIA.notifyItemRemoved(adapterPosition);
                            CDM.remove(adapterPosition);
                            dataEditor.putString(define.PRA_BarCode, mGson.toJson(CDM));
                            dataEditor.commit();
                            TV_Sum.setText("已扫描:" + CDM.size() + "条");
                        }
                    }, null, CDM.get(adapterPosition).getCode()).show();
                }
            }
            menuBridge.closeMenu();
        }


    };

    /**
     * 设置并显示制程列表
     */
    private void setOPV() {
        NameList.clear();
        for (ProcressFlowModel procressFlowModel : PFM) {
            NameList.add(procressFlowModel.getFProcessNodeName());
        }
        //条件选择器
        PKV = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                FProcessFlowID = PFM.get(options1).getFProcessFlowID() + "";
                FProcessNodeName = PFM.get(options1).getFProcessNodeName();
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("选择提交类型:" + PFM.get(options1).getFProcessFlowName());
                builder.setTitle("所选制程");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LOG.e("FProcessFlowID=" + FProcessFlowID);
                        LOG.e("FProcessNodeName=" + FProcessNodeName);
                        Submit();
                    }
                }).setNegativeButton("取消", null).show();
            }
        }).setTitleText("当前制程:" + PFM.get(0).getFProcessFlowName())
                .setTitleSize(18)
                .setTitleColor(Color.parseColor("#ff4444")).build();
        PKV.setPicker(NameList);

    }

    /**
     * 获取制程选项
     */
    private void GetProcessFlowInfoByBarCode() {
        List<Data> list = new ArrayList<>();
        for (CodeDataModel codeDataModel : CDM) {
            list.add(new Data(codeDataModel.getCode()));
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("barcodeJson", mGson.toJson(list));
        map.put("OrganizeID", dataPref.getString(define.SharedFOrganizeid, "1"));
        map.put("BillTypeID", "3030756");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.GetProcessFlowInfoByBarCode,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("result=" + result);
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if ("success".equals(ReturnType)) {
                                    PFM = mGson.fromJson(ReturnMsg, new TypeToken<List<ProcressFlowModel>>() {
                                    }.getType());
                                    setOPV();
                                    B_Submit.setText("选择制程");
                                    PKV.show();
                                    LOG.e("PFM=" + PFM.size());
                                } else {
                                    Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(mActivity, "数据解析异常", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mActivity, "数据解码异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Submit() {
        List<Data> list = new ArrayList<>();
        for (CodeDataModel codeDataModel : CDM) {
            list.add(new Data(codeDataModel.getCode()));
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("barcodeJson", mGson.toJson(list));
        map.put("OrganizeID", dataPref.getString(define.SharedFOrganizeid, "1"));
        map.put("BillTypeID", "3030756");
        map.put("FProcessFlowID", FProcessFlowID);
        map.put("FProcessNodeName", FProcessNodeName);
        map.put("EmpID", dataPref.getString(define.SharedEmpId, "none"));
        map.put("UserID", UserId);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.SubmitScWorkCardBarCode2ProcessOutput,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("提交制程=" + result);
                                //{"ReturnMsg":"提交成功","ReturnType":"success"}
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if ("success".equals(ReturnType)) {
                                    CDM.clear();
                                    PRIA.notifyDataSetChanged();
                                    TV_Sum.setText("已扫描:" + CDM.size() + "条");
                                }
                                Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Toast.makeText(mActivity, "数据解析异常", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mActivity, "数据解码异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

                    final String scanResult = svalue1.replaceAll("(\r\n|\r|\n|\n\r)", "");//svalue1+"\n"+svalue2//替换回车
                    List<String> list = new ArrayList<>();
                    for (CodeDataModel codeDataModel : CDM) {
                        list.add(codeDataModel.getCode());
                    }
                    if (!list.contains(scanResult)) {
                        CDM.add(new CodeDataModel(scanResult));
                        dataEditor.putString(define.PRA_BarCode, mGson.toJson(CDM));
                        dataEditor.commit();
                        PRIA.notifyItemInserted(CDM.size());
                        TV_Sum.setText("已扫描:" + CDM.size() + "条");
                    } else {
                        Toast.makeText(mActivity, "(" + scanResult + ")已存在", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static class Data {
        public Data(String d_BarCode) {
            D_BarCode = d_BarCode;
        }

        String D_BarCode;

        public String getD_BarCode() {
            return D_BarCode;
        }

        public void setD_BarCode(String d_BarCode) {
            D_BarCode = d_BarCode;
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
        mReceiver = null;

    }

    /*连续单击两次back键退出系统*/
    private long exitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 500) {
            Toast.makeText(getApplicationContext(), "连续按两次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
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
                    break;
                case 15:
                    UserId = data.getStringExtra("UserID");
                    LOG.e("UserId=" + UserId);
                    break;
                case 20:
                    LOG.e("UserId=" + UserId);
//                    getWarehouseData();
                    break;


            }
        }

    }


}
