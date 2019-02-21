package com.goldemperor.ScanCode.ProcessReportInstock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.goldemperor.ScanCode.Model.F_BarCodeModel;
import com.goldemperor.ScanCode.Share.ActionSheet;
import com.goldemperor.ScanCode.Share.ScanAdapter;
import com.goldemperor.ScanCode.Share.ScanListener;
import com.goldemperor.ScanCode.Share.ScanReceiver;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ScanCode.Share.ScanEMPCodeActivity;
import com.goldemperor.ScanCode.Show.ShowReportActivity;
import com.goldemperor.ScanCode.Share.ScanUtil;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.panzx.pulini.ZProgressHUD;
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
public class ProcessReportInstockActivity extends Activity implements ScanListener {

    @ViewInject(R.id.SMEL_List)
    private SwipeMenuRecyclerView SMEL_List;

    @ViewInject(R.id.ET_Numbers)
    private EditText ET_Numbers;

    @ViewInject(R.id.TV_Sum)
    private TextView TV_Sum;

    @ViewInject(R.id.B_UpData)
    private Button B_UpData;

    @ViewInject(R.id.B_Add)
    private Button B_Add;

    @ViewInject(R.id.B_Clear)
    private Button B_Clear;

    @ViewInject(R.id.B_Submit)
    private Button B_Submit;


    List<CodeDataModel> CDM = new ArrayList<>();//条码数据
    List<F_BarCodeModel> FBCM = new ArrayList<>();//条码数据
    List<ProcressFlowModel> PFM = new ArrayList<>();//制程类型
    List<String> NameList = new ArrayList<>();//制程名單
    private ScanAdapter SA;
    private Activity mActivity;
    private Gson mGson;
    private OptionsPickerView PKV;
    private String FProcessFlowID = "";
    private String FProcessNodeName = "";
    private ZProgressHUD mProgressHUD;
    private ScanReceiver SR;
    private String EmpCode1;
    private String EmpCode2;

    private ScanUtil SU;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        SU = new ScanUtil(this, this);
        initview();
        initdata();
    }


    private void initview() {
        mProgressHUD = new ZProgressHUD(this);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        SA = new ScanAdapter(CDM);
        SMEL_List.setLayoutManager(new LinearLayoutManager(mActivity));// 布局管理器。
        SMEL_List.addItemDecoration(new ListViewDecoration(mActivity));// 添加分割线。
        SMEL_List.setSwipeMenuCreator(SU.swipeMenuCreator);
        SMEL_List.setSwipeMenuItemClickListener(SU.menuItemClickListener);
        SMEL_List.setAdapter(SA);
        setListener();
    }

    private void setListener() {
        SA.SetOnItemClickListener(position -> ET_Numbers.setText(CDM.get(position).getCode()));
        B_UpData.setOnClickListener(v -> SU.GetBarCodeStatus(ScanUtil.GetBarCodeType_ProcessReportInstock));
        B_Add.setOnClickListener(v -> SU.Add(CDM, FBCM,ET_Numbers.getText().toString().trim()));
        B_Clear.setOnClickListener(v -> SU.Clear());
        B_Submit.setOnClickListener(v -> {
            if (CDM.size() == 0) {
                SU.ShowDialog(ScanUtil.ReturnType_Information, "提示", "没有可提交的单号");
            } else {
                GetProcessFlowInfoByBarCode();
            }
        });
    }
    private void initdata() {
        mGson = new Gson();
        List<CodeDataModel>   cdm=null;
        try {
          cdm = mGson.fromJson(SU.get(define.BarCode_PRIA, ""), new TypeToken<List<CodeDataModel>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOG.e("数据解析异常");
        }
        if(cdm!=null&&cdm.size()>0){
            for (CodeDataModel codeDataModel : cdm) {
                CDM.add(codeDataModel);
            }
            SA.Updata(CDM);
        }
        TV_Sum.setText("已扫描:" + CDM.size() + "条");

        SU.GetBarCodeStatus(ScanUtil.GetBarCodeType_ProcessReportInstock);
    }

    /**
     * 设置并显示制程列表
     */
    private void setOPV() {
        NameList.clear();
        for (ProcressFlowModel procressFlowModel : PFM) {
            NameList.add(procressFlowModel.getFProcessNodeName());
        }
        //条件选择器
        PKV = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            FProcessFlowID = PFM.get(options1).getFProcessFlowID() + "";
            FProcessNodeName = PFM.get(options1).getFProcessNodeName();
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setMessage("选择提交类型:" + PFM.get(options1).getFProcessFlowName());
            builder.setTitle("所选制程");
            builder.setPositiveButton("确认", (dialog, which) -> {
                LOG.e("FProcessFlowID=" + FProcessFlowID);
                LOG.e("FProcessNodeName=" + FProcessNodeName);
                List<D_BarCodeModel> list = new ArrayList<>();
                for (CodeDataModel codeDataModel : CDM) {
                    list.add(new D_BarCodeModel(codeDataModel.getCode()));
                }
                SU.GetReport(list, ScanUtil.BillTypeID_ProcessReportInstock, false);

            }).setNegativeButton("取消", null).show();
        }).setTitleText("当前制程:" + PFM.get(0).getFProcessFlowName())
                .setTitleSize(18)
                .setTitleColor(Color.parseColor("#ff4444")).build();
        PKV.setPicker(NameList);

    }

    /**
     * 获取制程选项
     */
    private void GetProcessFlowInfoByBarCode() {
        mProgressHUD.setMessage("获取制程列表中...");
        mProgressHUD.show();
        List<D_BarCodeModel> list = new ArrayList<>();
        for (CodeDataModel codeDataModel : CDM) {
            list.add(new D_BarCodeModel(codeDataModel.getCode()));
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("barcodeJson", mGson.toJson(list));
        map.put("OrganizeID", SU.OrganizeID);
        map.put("BillTypeID", ScanUtil.BillTypeID_LongCode + "");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetProcessFlowInfoByBarCode,
                map, result -> {
                    mProgressHUD.dismiss();
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
                                PKV.show();
                                LOG.e("PFM=" + PFM.size());
                            } else {
                                SU.ShowDialog(ScanUtil.ReturnType_Error, "错误", ReturnMsg);
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

    private void Submit() {
        mProgressHUD.setMessage("提交中...");
        mProgressHUD.show();
        List<D_BarCodeModel> list = new ArrayList<>();
        for (CodeDataModel codeDataModel : CDM) {
            list.add(new D_BarCodeModel(codeDataModel.getCode()));
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("barcodeJson", mGson.toJson(list));
        map.put("FProcessFlowID", FProcessFlowID);
        map.put("FProcessNodeName", FProcessNodeName);
        map.put("OrganizeID", SU.OrganizeID);
        map.put("BillTypeID", ScanUtil.BillTypeID_LongCode + "");
        map.put("EmpCode", EmpCode1);
        map.put("ComfirmEmpCode", EmpCode1);
        map.put("UserID", SU.UserID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAppServer,
                define.SubmitScWorkCardBarCode2ProcessOutput,
                map, result -> {
                    mProgressHUD.dismiss();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("提交制程=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                CDM.clear();
                                TV_Sum.setText("已扫描:" + CDM.size() + "条");
                                SA.Updata(CDM);
                                SU.seve(define.BarCode_PRIA, "");
                                SU.GetBarCodeStatus(ScanUtil.GetBarCodeType_ProcessReportInstock);
                                SU.ShowDialog(ScanUtil.ReturnType_Success, "提交成功", ReturnMsg);
                            } else {
                                SU.ShowDialog(ScanUtil.ReturnType_Error, "提交失败", ReturnMsg);
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



    @Override
    public void CodeResult(String code) {
        LOG.e("返回码:" + code);
        SU.Add(CDM,FBCM, code);
    }

    @Override
    public void GetBarCodeStatusResult(boolean ReturnType, Object ReturnMsg) {
        if (ReturnType) {
            FBCM= (List<F_BarCodeModel>) ReturnMsg;
            if (FBCM.size() > 0) {
                Toast.makeText(mActivity,"刷新数据("+FBCM.size()+")条",Toast.LENGTH_LONG).show();
                if(CDM.size()>0){
                    for (CodeDataModel codeDataModel : CDM) {
                        codeDataModel.setType(false);
                    }
                    for (CodeDataModel cdm : CDM) {
                        for (F_BarCodeModel fbcm :FBCM ) {
                            if (cdm.getCode().equals(fbcm.getFBarCode())) {
                                cdm.setType(true);
                            }
                        }
                    }
                    SA.Updata(CDM);
                    SU.seve(define.BarCode_PRIA, mGson.toJson(CDM));
                }
            }
        } else {
            SU.ShowDialog(SU.ReturnType_Error, "更新数据失败", (String) ReturnMsg);
        }
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
            SU.seve(define.BarCode_PRIA, mGson.toJson(CDM));
            TV_Sum.setText("已扫描:" + CDM.size() + "条");
        }, null, CDM.get(position).getCode()).show();
    }

    @Override
    public void AddItem(List<CodeDataModel> Cdm, String Code) {
        if (Cdm.size() > 0) {
            CDM.clear();
            CDM.addAll(Cdm);
            SA.Updata(CDM);
            SU.seve(define.BarCode_PRIA, mGson.toJson(CDM));
            TV_Sum.setText("已扫描:" + CDM.size() + "条");
        } else {
            SU.ShowDialog(ScanUtil.ReturnType_Error, "失败", "(" + Code + ")已存在");
        }
    }

    @Override
    public void ClearItem() {
        CDM.clear();
        SU.seve(define.BarCode_PRIA, "");
        SA.Updata(CDM);
        TV_Sum.setText("已扫描:" + CDM.size() + "条");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOG.e("requestCode=" + requestCode + "resultCode=" + resultCode);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case ScanUtil.GetEmpCodeRequestCode:
                    EmpCode1 = data.getExtras().getString("EmpCode1");
                    EmpCode2 = data.getExtras().getString("EmpCode2");
                    LOG.e("EmpCode1=" + EmpCode1);
                    LOG.e("EmpCode2=" + EmpCode2);
                    Submit();
                    break;
                case ScanUtil.ShowReportRequestCode:
                    startActivityForResult(
                            new Intent(mActivity, ScanEMPCodeActivity.class)
                                    .putExtra("Name1", "操作人")
                                    .putExtra("Name2", ""),
                            ScanUtil.GetEmpCodeRequestCode
                    );
                    break;

            }
        }

    }

}
