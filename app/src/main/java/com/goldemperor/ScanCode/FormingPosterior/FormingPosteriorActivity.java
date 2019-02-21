package com.goldemperor.ScanCode.FormingPosterior;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ScanCode.Model.D_BarCodeModel;
import com.goldemperor.ScanCode.Share.ActionSheet;
import com.goldemperor.ScanCode.Share.ScanAdapter;
import com.goldemperor.ScanCode.Share.ScanListener;
import com.goldemperor.ScanCode.Share.ScanReceiver;
import com.goldemperor.ScanCode.Share.ScanUtil;
import com.goldemperor.ScanCode.Show.ShowReportActivity;
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
 * File Name : FormingPosteriorActivity
 * Created by : PanZX on  2018/11/1 19:10
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：成型后段扫码
 */
@ContentView(R.layout.activity_forming_posterior)
public class FormingPosteriorActivity extends Activity implements ScanListener {

    @ViewInject(R.id.RG_Type)
    private RadioGroup RG_Type;

    @ViewInject(R.id.RB_Warehousing)
    private RadioButton RB_Warehousing;
    @ViewInject(R.id.RB_Picking)
    private RadioButton RB_Picking;

    @ViewInject(R.id.CB_Red)
    private CheckBox CB_Red;

    @ViewInject(R.id.SMEL_List)
    private SwipeMenuRecyclerView SMEL_List;

    @ViewInject(R.id.ET_Add)
    private EditText ET_Add;

    @ViewInject(R.id.TV_Sum)
    private TextView TV_Sum;

    @ViewInject(R.id.B_Add)
    private Button B_Add;

    @ViewInject(R.id.B_Clear)
    private Button B_Clear;

    @ViewInject(R.id.B_Submit)
    private Button B_Submit;

    private ZProgressHUD mProgressHUD;
    private ScanAdapter SA;
    private List<CodeDataModel> CDM = new ArrayList<>();
    private Activity mActivity;
    private ScanUtil SU;
    private Gson mGson;
    private ScanReceiver SR;
    private boolean isIn=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        SU = new ScanUtil(this, this);
        mActivity = this;
        initview();
        initdata();
    }

    private void initview() {
        mProgressHUD = new ZProgressHUD(this);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        SA = new ScanAdapter(CDM);
        SA.setShowMSG(false);
        SMEL_List.setLayoutManager(new LinearLayoutManager(mActivity));// 布局管理器。
        SMEL_List.addItemDecoration(new ListViewDecoration(mActivity));// 添加分割线。
        SMEL_List.setSwipeMenuCreator(SU.swipeMenuCreator);
        SMEL_List.setSwipeMenuItemClickListener(SU.menuItemClickListener);
        SMEL_List.setAdapter(SA);
        setListener();
    }

    private void setListener() {
        SA.SetOnItemClickListener(position -> ET_Add.setText(CDM.get(position).getCode()));
        B_Add.setOnClickListener(v -> SU.Add(CDM,null, ET_Add.getText().toString().trim()));
        B_Clear.setOnClickListener(v -> SU.Clear());
        B_Submit.setOnClickListener(v -> {
            if (CDM.size() == 0) {
                SU.ShowDialog(ScanUtil.ReturnType_Information, "提示", "没有可提交的单号");
            } else {
                List<D_BarCodeModel> list = new ArrayList<>();
                for (CodeDataModel codeDataModel : CDM) {
                    list.add(new D_BarCodeModel(codeDataModel.getCode()));
                }
                SU.GetReport(list, ScanUtil.BillTypeID_FormingPosterior, CB_Red.isChecked());
            }
        });
        RG_Type.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.RB_Warehousing:
                    isIn = true;
                    break;
                case R.id.RB_Picking:
                    isIn = false;
                    break;
            }
        });
    }

    private void initdata() {

        mGson = new Gson();
        List<CodeDataModel> data=null;
        try {
            data = mGson.fromJson(SU.get(define.BarCode_FPA, ""), new TypeToken<List<CodeDataModel>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOG.e("数据解析异常");
        }
        if(data!=null&&data.size()>0){
            for (CodeDataModel datum : data) {
                CDM.add(datum);
            }
            SA.Updata(CDM);
        }
        TV_Sum.setText("已扫描:" + CDM.size() + "条");
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
        map.put("TranTypeID", isIn?ScanUtil.TranTypeID_Supplier_Warehousing+"":ScanUtil.TranTypeID_Supplier_Picking+"");
        map.put("BillTypeID", ScanUtil.BillTypeID_FormingPosterior + "");
        map.put("DefaultStockID", SU.DefaultStockID);
        map.put("Red", CB_Red.isChecked() ? "-1" : "1");
        map.put("UserID", SU.UserID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAppServer,
                define.SubmitCxBarCode2CollectBill,
                map, result -> {
                    mProgressHUD.dismiss();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("成型后段扫码=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                CDM.clear();
                                SA.Updata(CDM);
                                TV_Sum.setText("已扫描:" + CDM.size() + "条");
                                SU.seve(define.BarCode_FPA, "");
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
    protected void onPause() {
        super.onPause();
        ScanReceiver.unRegisterReceiver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScanReceiver.RegisterReceiver(this,this);
    }

    @Override
    public void CodeResult(String code) {
        LOG.e("返回码:" + code);
        SU.Add(CDM,null, code);
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
            SU.seve(define.BarCode_FPA, mGson.toJson(CDM));
            TV_Sum.setText("已扫描:" + CDM.size() + "条");
        }, null, CDM.get(position).getCode()).show();
    }

    @Override
    public void AddItem(List<CodeDataModel> cdm, String Code) {
        LOG.e("cdm="+cdm.size());
        LOG.e("CDM="+CDM.size());

        if (cdm.size() > 0) {
            CDM.clear();
            for (CodeDataModel codeDataModel : cdm) {
                CDM.add(codeDataModel);
            }
            SA.Updata(CDM);
            SU.seve(define.BarCode_FPA, mGson.toJson(CDM));
            TV_Sum.setText("已扫描:" + CDM.size() + "条");
        } else {
            SU.ShowDialog(ScanUtil.ReturnType_Error, "失败", "(" + Code + ")已存在");
        }
    }

    @Override
    public void ClearItem() {
        CDM.clear();
        SU.seve(define.BarCode_FPA, "");
        SA.Updata(CDM);
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
