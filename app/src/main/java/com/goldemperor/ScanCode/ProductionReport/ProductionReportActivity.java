package com.goldemperor.ScanCode.ProductionReport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.ScanCode.Model.F_BarCodeModel;
import com.goldemperor.ScanCode.Share.ActionSheet;
import com.goldemperor.ScanCode.Share.ScanAdapter;
import com.goldemperor.ScanCode.Share.ScanListener;
import com.goldemperor.ScanCode.Share.ScanReceiver;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ScanCode.Share.ScanUtil;
import com.goldemperor.ScanCode.Show.ShowReportActivity;
import com.goldemperor.ScanCode.Model.D_BarCodeModel;
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
 * Created by : PanZX on  2018/11/1 14:58
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：生产汇报
 */
@ContentView(R.layout.activity_production_report)
public class ProductionReportActivity extends Activity implements ScanListener {

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
    List<F_BarCodeModel> FBCM= new ArrayList<>();//已入库条码
    private Activity mActivity;
    private Gson mGson;
    private ScanReceiver SR;
    private ScanUtil SU;
    private ScanAdapter SA;
    private ZProgressHUD mProgressHUD;

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
        SA.setMSG("汇报");
        setListener();
    }

    private void setListener() {
        SA.SetOnItemClickListener(position -> ET_Numbers.setText(CDM.get(position).getCode()));
        B_UpData.setOnClickListener(v -> SU.GetBarCodeStatus(ScanUtil.GetBarCodeType_ProductionReport));
        B_Add.setOnClickListener(v -> SU.Add(CDM,FBCM, ET_Numbers.getText().toString().trim()));
        B_Clear.setOnClickListener(v -> SU.Clear());
        B_Submit.setOnClickListener(v -> {
            if(SU.CheckList(CDM)){
                List<D_BarCodeModel> list = new ArrayList<>();
                for (CodeDataModel codeDataModel : CDM) {
                    list.add(new D_BarCodeModel(codeDataModel.getCode()));
                }
                SU.GetReport(list, ScanUtil.BillTypeID_ProductionReport, false);
            }else{
                SU.ShowDialog(ScanUtil.ReturnType_Information, "提示", "请删除已入库单号");
            }


        });
    }

    private void initdata() {
        mGson = new Gson();
        List<CodeDataModel> cdm=null;
        try {
            cdm = mGson.fromJson(SU.get(define.BarCode_PRA, ""), new TypeToken<List<CodeDataModel>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOG.e("数据解析异常");
        }
        if (cdm!= null&&cdm.size()>0) {
            for (CodeDataModel codeDataModel : cdm) {
                CDM.add(codeDataModel);
            }

            SA.Updata(CDM);
        }
        TV_Sum.setText("已扫描:" + CDM.size() + "条");
        SU.GetBarCodeStatus(ScanUtil.GetBarCodeType_ProductionReport);
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
        map.put("OrganizeID", SU.OrganizeID);
        map.put("TranType", ScanUtil.BillTypeID_LongCode + "");
        map.put("Red", "1");
        map.put("EmpID", SU.EmpID);
        map.put("UserID", SU.UserID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(SPUtils.getServerPath() + define.ErpForAppServer,
                define.SubmitWorkCardBarCode2CollectBill, map, result -> {
                    mProgressHUD.dismiss();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("提交生产汇报=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            // 提交生产汇报={"Result":"success","StockBillNO":"SCHBD1840040"}
                            if ("success".equals(ReturnType)) {
                                CDM.clear();
                                SA.Updata(CDM);
                                TV_Sum.setText("已扫描:" + CDM.size() + "条");
                                SU.seve(define.BarCode_PRA, "");
                                SU.GetBarCodeStatus(ScanUtil.GetBarCodeType_ProductionReport);
                                SU.ShowDialog(ScanUtil.ReturnType_Success, "提交成功", ReturnMsg);
                            }else{
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
                    SU.seve(define.BarCode_PRA, mGson.toJson(CDM));
                }
            }
        } else {
            SU.ShowDialog(SU.ReturnType_Success, "更新数据失败", (String) ReturnMsg);
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
            SU.seve(define.BarCode_PRA, mGson.toJson(CDM));
            TV_Sum.setText("已扫描:" + CDM.size() + "条");
        }, null, CDM.get(position).getCode()).show();
    }

    @Override
    public void AddItem(List<CodeDataModel> Cdm, String Code) {
        if (Cdm.size() > 0) {
            CDM.clear();
            CDM.addAll(Cdm);
            SA.Updata(CDM);
            SU.seve(define.BarCode_PRA, mGson.toJson(CDM));
            TV_Sum.setText("已扫描:" + CDM.size() + "条");
        } else {
            SU.ShowDialog(ScanUtil.ReturnType_Error, "失败", "(" + Code + ")已存在");
        }
    }

    @Override
    public void ClearItem() {
        CDM.clear();
        SA.Updata(CDM);
        SU.seve(define.BarCode_PRA, "");
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
                case ScanUtil.ShowReportRequestCode:
                    Submit();
                    break;
            }
        }

    }


}
