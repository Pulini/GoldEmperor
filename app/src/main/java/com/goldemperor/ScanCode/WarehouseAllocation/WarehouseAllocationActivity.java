package com.goldemperor.ScanCode.WarehouseAllocation;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.ScanCode.Model.D_BarCodeModel;
import com.goldemperor.ScanCode.Share.ActionSheet;
import com.goldemperor.ScanCode.Share.ScanAdapter;
import com.goldemperor.ScanCode.Share.ScanListener;
import com.goldemperor.ScanCode.Share.ScanReceiver;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ScanCode.Share.ScanUtil;
import com.goldemperor.ScanCode.Show.ShowReportActivity;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Utils.ZProgressHUD;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
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
 * File Name : WarehouseAllocationActivity
 * Created by : PanZX on  2018/7/10 11:32
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：仓库调拨单
 */
@ContentView(R.layout.activity_warehouse_allocation)
public class WarehouseAllocationActivity extends Activity implements ScanListener {


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

    private Activity mActivity;
    public Gson mGson;
    private List<WarehouseListModel> WLM = new ArrayList<>();
    private List<List<String>> WarehouseList2 = new ArrayList<>();
    private OptionsPickerView OPVWarehouse;
    boolean isOut = true;
    private String FSCStockID = "";//出库
    private String FDCStockID = "";//入库
    private ScanAdapter SA;
    List<CodeDataModel> CDM = new ArrayList<>();//条码数据
    private ScanReceiver SR;
    private ScanUtil SU;
    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        SU = new ScanUtil(this, this);
        initview();
        initdata();
        getWarehouseList();
    }

    private void initdata() {
        mGson = new Gson();
        List<CodeDataModel> cdm=null;
        try {
            cdm = mGson.fromJson(SU.get(define.BarCode_WAA, ""), new TypeToken<List<CodeDataModel>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOG.e("数据解析异常");
        }
        if (cdm != null&&cdm.size()>0) {
            for (CodeDataModel codeDataModel : cdm) {
                CDM.add(codeDataModel);
            }
            SA.Updata(CDM);
        }
        TV_Sum.setText("已扫描:" + CDM.size() + "条");
    }

    private void initview() {
        B_Out.setEnabled(false);
        B_In.setEnabled(false);
        mProgressHUD = new ZProgressHUD(this);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        SA = new ScanAdapter(CDM);
        SA.setShowMSG(false);
        SMRV_data.setLayoutManager(new LinearLayoutManager(mActivity));// 布局管理器。
        SMRV_data.addItemDecoration(new ListViewDecoration(mActivity));// 添加分割线。
        SMRV_data.setSwipeMenuCreator(SU.swipeMenuCreator);
        SMRV_data.setSwipeMenuItemClickListener(SU.menuItemClickListener);
        SMRV_data.setAdapter(SA);
        setListener();
    }

    private void setListener() {
        SA.SetOnItemClickListener(position -> ET_Add.setText(CDM.get(position).getCode()));
        B_Add.setOnClickListener(v -> SU.Add(CDM,null, ET_Add.getText().toString().trim()));
        B_Out.setOnClickListener(v -> OutIn(true));
        B_In.setOnClickListener(v -> OutIn(false));
        B_Clear.setOnClickListener(v -> SU.Clear());
        B_smb.setOnClickListener(v -> smb());

    }


    /**
     * 设置选择器
     */
    private void setOPV() {
        //条件选择器
        OPVWarehouse = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
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

        }).build();
        OPVWarehouse.setPicker(WLM, WarehouseList2);
    }

    /**
     * 获取仓库列表
     */
    private void getWarehouseList() {
        mProgressHUD.setMessage("获取仓库列表...");
        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("OrganizeID", SU.OrganizeID);//ID错误 等1.7.4替换成map.put("SuitID", SU.SuitID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetStockList, map, result -> {
                    mProgressHUD.dismiss();
                    if (result != null) {
                        LOG.E("仓库列表=" + result);
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
                });

    }

    /**
     * 获取默认仓库
     */
    private void getDefaultStockIDForDB() {
        mProgressHUD.setMessage("获取默认仓库ID...");
        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("OrganizeID", SU.OrganizeID);
        map.put("UserID", SU.UserID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetDefaultStockIDForDB, map, result -> {
                    mProgressHUD.dismiss();
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
                });

    }

    private void Submit() {
        mProgressHUD.setMessage("提交中...");
        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        List<D_BarCodeModel> l = new ArrayList<>();
        for (CodeDataModel codeDataModel : CDM) {
            if (!codeDataModel.getCode().equals("")) {
                l.add(new D_BarCodeModel(codeDataModel.getCode()));
            }
        }
        map.put("barcodeJson", mGson.toJson(l));
        map.put("OrganizeID", SU.OrganizeID);
        map.put("BillTypeID", ScanUtil.BillTypeID_WarehouseAllocation + "");
        map.put("FSCStockID", FSCStockID);
        map.put("FDCStockID", FDCStockID);
        map.put("Red", "1");
        map.put("UserID", SU.UserID);
        map.put("SEmpCode", "");//仓库保管人工号  暂时传空20181101
        map.put("FEmpCode", "");//领料人工号 暂时传空20181101
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAppServer,
                define.SubmitBarCode2CkRequisitionSlipCollectBill,
                map,
                result -> {
                    mProgressHUD.dismiss();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("result=" + result);
                            JSONObject jb = new JSONObject(result);
                            String Result = jb.getString("Result");
                            String StockBillNO = jb.getString("StockBillNO");
                            if (Result.equals("success")) {
                                CDM.clear();
                                SA.Updata(CDM);
                                TV_Sum.setText("已扫描:" + CDM.size() + "条");
                                SU.seve(define.BarCode_WAA, "");
                                SU.ShowDialog(ScanUtil.ReturnType_Success, "提交成功", StockBillNO);
                            } else {
                                SU.ShowDialog(ScanUtil.ReturnType_Success, "提交失败", StockBillNO);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            SU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "数据解析异常");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            SU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "数据解码异常");
                        }
                    } else {
                        SU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "接口无返回");
                    }
                });

    }



    private void OutIn(boolean b) {
        isOut = b;
        OPVWarehouse.show();
    }

    private void smb() {
        if (CDM.size() == 0) {
            SU.ShowDialog(ScanUtil.ReturnType_Information, "提示", "请扫描调拨单条码");
            return;
        }
        if (FSCStockID.equals("") || FDCStockID.equals("")) {
            if (WLM.size() == 0) {
                getWarehouseList();
            } else {
                SU.ShowDialog(ScanUtil.ReturnType_Information, "提示", "请选择调出调入仓库");
            }
            return;
        }
        List<D_BarCodeModel> l = new ArrayList<>();
        for (CodeDataModel codeDataModel : CDM) {
            if (!codeDataModel.getCode().equals("")) {
                l.add(new D_BarCodeModel(codeDataModel.getCode()));
            }
        }
        SU.GetReport(l, ScanUtil.BillTypeID_WarehouseAllocation, false);
    }


    @Override
    public void CodeResult(String code) {
        SU.Add(CDM, null,code);

    }

    @Override
    public void GetBarCodeStatusResult(boolean ReturnType, Object ReturnMsg) {

    }

    @Override
    public void GetReport(boolean ReturnType, Object ReturnMsg) {
        if (ReturnType) {
            Bundle bundle = new Bundle();
            bundle.putString("Report", (String) ReturnMsg);
            Intent intent = new Intent(this, ShowReportActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, ScanUtil.ShowReportRequestCode);
        } else {
            SU.ShowDialog(SU.ReturnType_Error, "汇总失败", (String) ReturnMsg);
        }
    }

    @Override
    public void DeleteItem(int position) {
        ActionSheet.showSheet(mActivity, whichButton -> {
            CDM.remove(position);
            SA.RemovedItem(position);
            SU.seve(define.BarCode_WAA, mGson.toJson(CDM));
            TV_Sum.setText("已扫描:" + CDM.size() + "条");
        }, null, CDM.get(position).getCode()).show();
    }

    @Override
    public void AddItem(List<CodeDataModel> Cdm, String Code) {
        if (Cdm.size() > 0) {
            CDM.clear();
            CDM.addAll(Cdm);
            SA.Updata(CDM);
            SU.seve(define.BarCode_WAA, mGson.toJson(CDM));
            TV_Sum.setText("已扫描:" + CDM.size() + "条");
        } else {
            SU.ShowDialog(ScanUtil.ReturnType_Error, "失败", "(" + Code + ")已存在");
        }
    }

    @Override
    public void ClearItem() {
        CDM.clear();
        SA.Updata(CDM);
        SU.seve(define.BarCode_WAA, mGson.toJson(CDM));
        TV_Sum.setText("已扫描:" + CDM.size() + "条");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOG.e("requestCode=" + requestCode + "resultCode=" + resultCode);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case ScanUtil.ShowReportRequestCode:
                    Submit();
                    break;
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        ScanReceiver.unRegisterReceiver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScanReceiver.RegisterReceiver(this,this);
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

}
