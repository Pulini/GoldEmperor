package com.goldemperor.PropertyRegistration.Phone;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.PgdActivity.Emp;
import com.goldemperor.PropertyRegistration.PropertyDetailsModel;
import com.goldemperor.PropertyRegistration.RegistrationUtils;
import com.goldemperor.PropertyRegistration.SubmitDataModel;
import com.goldemperor.PropertyRegistration.TakePhotoHelper;
import com.goldemperor.R;
import com.goldemperor.ScanCode.Share.ScanUtil;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;
import com.google.gson.Gson;
import com.panzx.pulini.ZProgressHUD;

import org.devio.takephoto.app.TakePhotoFragmentActivity;
import org.devio.takephoto.model.TResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * File Name : PropertyRegistrationDetailsForPadActivity
 * Created by : PanZX on  2019/1/18 10:35
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_property_registration_details_for_phone)
public class PropertyRegistrationDetailsForPhoneActivity extends TakePhotoFragmentActivity {

    @ViewInject(R.id.IV_Back)
    private ImageView IV_Back;

    @ViewInject(R.id.TV_Satus)
    private TextView TV_Satus;

    @ViewInject(R.id.FB_InitiateAudit)
    private FancyButton FB_InitiateAudit;

    @ViewInject(R.id.TV_Sno)
    private TextView TV_Sno;

    @ViewInject(R.id.TV_RegistrationDate)
    private TextView TV_RegistrationDate;

    @ViewInject(R.id.TV_Model)
    private TextView TV_Model;

    @ViewInject(R.id.TV_Unit)
    private TextView TV_Unit;

    @ViewInject(R.id.ET_Number)
    private EditText ET_Number;

    @ViewInject(R.id.TV_Name)
    private TextView TV_Name;

    @ViewInject(R.id.TV_TypeName)
    private TextView TV_TypeName;

    @ViewInject(R.id.TV_Qty)
    private TextView TV_Qty;

    @ViewInject(R.id.ET_Price)
    private EditText ET_Price;

    @ViewInject(R.id.ET_OrgVal)
    private EditText ET_OrgVal;

    @ViewInject(R.id.TV_Supplier)
    private TextView TV_Supplier;

    @ViewInject(R.id.TV_ManufactureDate)
    private TextView TV_ManufactureDate;

    @ViewInject(R.id.TV_BuyDate)
    private TextView TV_BuyDate;

    @ViewInject(R.id.TV_ReviceDate)
    private TextView TV_ReviceDate;

    @ViewInject(R.id.TV_DeptName)
    private TextView TV_DeptName;

    @ViewInject(R.id.TV_SAP_PurchaseNum)
    private TextView TV_SAP_PurchaseNum;

    @ViewInject(R.id.TV_SAP_Invoice)
    private TextView TV_SAP_Invoice;

    @ViewInject(R.id.ET_CustodianCode)
    private EditText ET_CustodianCode;

    @ViewInject(R.id.TV_CustodianName)
    private TextView TV_CustodianName;

    @ViewInject(R.id.ET_LiableCode)
    private EditText ET_LiableCode;

    @ViewInject(R.id.TV_LiableName)
    private TextView TV_LiableName;


    @ViewInject(R.id.ET_ParticipatorCode)
    private EditText ET_ParticipatorCode;

    @ViewInject(R.id.TV_ParticipatorName)
    private TextView TV_ParticipatorName;


    @ViewInject(R.id.ET_Address)
    private EditText ET_Address;

    @ViewInject(R.id.IV_Photo)
    private ImageView IV_Photo;

    @ViewInject(R.id.ET_Notes)
    private EditText ET_Notes;

    @ViewInject(R.id.LV_CardEntry)
    private ListView LV_CardEntry;

    @ViewInject(R.id.FB_Delete)
    private FancyButton FB_Delete;

    @ViewInject(R.id.LL_CardEntry)
    private LinearLayout LL_CardEntry;

    private Activity mActivity;
    private PropertyDetailsModel PDML;
    private TakePhotoHelper PhotoHelper = new TakePhotoHelper(getTakePhoto());
    private ZProgressHUD mProgressHUD;
    private RegistrationUtils RU;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        mActivity = this;
        initview();
        GetDetails();

    }

    private void initview() {
        RU = new RegistrationUtils(this);
        IV_Back.setOnClickListener(v -> finish());
        FB_InitiateAudit.setEnabled(false);
        IV_Photo.setEnabled(false);
        FB_InitiateAudit.setOnClickListener(v -> Submit());
        mProgressHUD = new ZProgressHUD(mActivity);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        IV_Photo.setOnClickListener(v -> PhotoHelper.TakePhoto());
        FB_Delete.setOnClickListener(v -> LemonHello.getErrorHello("删除订单", "确定要删除本单数据吗？")
                .addAction(new LemonHelloAction("取消", (helloView, helloInfo, helloAction) -> helloView.hide()))
                .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> {
                    helloView.hide();
                    Delete();
                }))
                .show(mActivity));

        ET_ParticipatorCode.addTextChangedListener(new RegistrationUtils.MyTextListener(ET_ParticipatorCode, e -> {
            if (e == null) {
                RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "无此工号");
                TV_ParticipatorName.setText("");
                PDML.setFParticipator(-1);
            } else {
                TV_ParticipatorName.setText(e.getEmp_Name());
                PDML.setFParticipator(Integer.valueOf(e.getEmp_ID()));
            }
        }));

        ET_LiableCode.addTextChangedListener(new RegistrationUtils.MyTextListener(ET_LiableCode, e -> {
            if (e == null) {
                RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "无此工号");
                TV_LiableName.setText("");
                PDML.setFLiableEmpID(-1);
            } else {
                TV_LiableName.setText(e.getEmp_Name());
                PDML.setFLiableEmpID(Integer.valueOf(e.getEmp_ID()));
            }
        }));
        ET_CustodianCode.addTextChangedListener(new RegistrationUtils.CustodianTextListener(ET_CustodianCode, EM -> {
            if (EM == null) {
                RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "无此工号");
                TV_CustodianName.setText("");
                PDML.setFKeepEmpID(-1);
                PDML.setFDeptID(-1);
                TV_DeptName.setText("");
                TV_LiableName.setText("");
                ET_LiableCode.setText("");
                PDML.setFLiableEmpID(-1);
            } else {
                TV_CustodianName.setText(EM.getFEmpName());
                PDML.setFKeepEmpID(EM.getFEmpID());
                PDML.setFDeptID(EM.getFDeptmentID());
                TV_DeptName.setText(EM.getDeptName());
                TV_LiableName.setText(EM.getFLiableEmpName());
                ET_LiableCode.setText(EM.getFLiableEmpCode());
                PDML.setFLiableEmpID(EM.getFLiableEmpID());
            }
        }));


    }

    private void setdata() {
        TV_Satus.setText(PDML.getFProcessStatus());
        TV_Sno.setText(PDML.getFSno());
        TV_RegistrationDate.setText(PDML.getFWritedate());
        TV_Model.setText(PDML.getFModel());
        TV_Unit.setText(PDML.getFUnit());
        TV_TypeName.setText(PDML.getFTypeName());
        TV_Qty.setText(PDML.getFQty() + "");
        ET_Price.setText(PDML.getFPrice() + "");
        ET_OrgVal.setText(PDML.getFOrgVal() + "");
        TV_Supplier.setText(PDML.getFVender());
        TV_ManufactureDate.setText(PDML.getFProduceDate());
        TV_BuyDate.setText(PDML.getFbuyDate());
        TV_ReviceDate.setText(PDML.getFRevicedate());
        TV_DeptName.setText(PDML.getFDeptName());
        TV_CustodianName.setText(PDML.getCustodianName());
        TV_LiableName.setText(PDML.getFLiableEmpName());
        TV_ParticipatorName.setText(PDML.getFParticipatorName());
        TV_SAP_PurchaseNum.setText(PDML.getFSAPCgOrderNo());
        TV_SAP_Invoice.setText(PDML.getFSAPINVOICENO());

        ET_ParticipatorCode.setText(PDML.getFParticipatorCode());
        ET_LiableCode.setText(PDML.getFLiableEmpCode());
        ET_CustodianCode.setText(PDML.getCustodianCode());
        ET_Number.setText(PDML.getFNumber());
        TV_Name.setText(PDML.getFName());
        ET_Address.setText(PDML.getFaddress());
        ET_Notes.setText(PDML.getFNotes());
        if (PDML.getAssetPicture() != null) {
            IV_Photo.setImageBitmap(Utils.base64ToBitmap(PDML.getAssetPicture()));
        }
        if (PDML.getFProcessStatus().equals("未审核")) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }
    }

    private void setEnabled(boolean b) {
        FB_InitiateAudit.setEnabled(b);
        ET_ParticipatorCode.setEnabled(b);
        ET_LiableCode.setEnabled(b);
        ET_CustodianCode.setEnabled(b);
        ET_Number.setEnabled(b);
        ET_Address.setEnabled(b);
        ET_Notes.setEnabled(b);
        ET_Price.setEnabled(b);
        ET_OrgVal.setEnabled(b);
        IV_Photo.setEnabled(b);
    }

    private void Delete() {


    }

    public String CheckData() {
        String Participator = ET_ParticipatorCode.getText().toString();
        String Liable = ET_LiableCode.getText().toString();
        String Custodian = ET_CustodianCode.getText().toString();
        String Number = ET_Number.getText().toString();
        String Price = ET_Price.getText().toString();
        String OrgVal = ET_OrgVal.getText().toString();
        String Address = ET_Address.getText().toString();
        String Notes = ET_Notes.getText().toString();
        if (Participator.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_ParticipatorCode.getHint().toString());
            return null;
        }
        if (Liable.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_LiableCode.getHint().toString());
            return null;
        }
        if (Custodian.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_CustodianCode.getHint().toString());
            return null;
        }
        if (Number.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_Number.getHint().toString());
            return null;
        }
        if (Price.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_Price.getHint().toString());
            return null;
        }
        if (OrgVal.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_OrgVal.getHint().toString());
            return null;
        }
        if (Address.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_Address.getHint().toString());
            return null;
        }

        if (PDML.getAssetPicture().isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", "请拍摄现场照片");
            return null;
        }
        SubmitDataModel SDM = new SubmitDataModel();
        SDM.setFNumber(Number);
        SDM.setFPrice(Double.parseDouble(Price));
        SDM.setFOrgVal(Double.parseDouble(OrgVal));
        SDM.setFInterID(PDML.getFInterID());
        SDM.setFKeepEmpID(PDML.getFKeepEmpID());
        SDM.setFDeptID(PDML.getFDeptID());
        SDM.setFaddress(Address);
        SDM.setFLiableEmpID(PDML.getFLiableEmpID());
        SDM.setFParticipator(PDML.getFParticipator());
        SDM.setFRegistrationerID(Integer.parseInt((String) SPUtils.get(define.SharedEmpId, "0")));
        SDM.setFNotes(Notes);
        SDM.setAssetPicture(PDML.getAssetPicture());
        return new Gson().toJson(SDM);
    }

    private void GetDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", getIntent().getExtras().getInt("InterID") + "");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAppServer,
                define.GetAssestByFInterID,
                map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("财产详情" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                PDML = new Gson().fromJson(ReturnMsg, PropertyDetailsModel.class);
                                setdata();
                                if (PDML.getCardEntry() != null && PDML.getCardEntry().size() > 0) {
                                    LL_CardEntry.setVisibility(View.VISIBLE);
                                    LV_CardEntry.setAdapter(new RegistrationUtils.MyAdapterForPhone(PDML.getCardEntry()));
                                } else {
                                    LL_CardEntry.setVisibility(View.GONE);
                                }
                            } else {
                                RU.ShowDialog(ScanUtil.ReturnType_Error, "获取数据失败", ReturnMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void Submit() {
        String DataJson = CheckData();
        if (DataJson != null) {
            LemonHello.getInformationHello("提交", "确定要发起审核吗？")
                    .addAction(
                            new LemonHelloAction("取消", (helloView, helloInfo, helloAction) -> helloView.hide()),
                            new LemonHelloAction("确定", (helloView, helloInfo, helloAction) -> {
                                helloView.hide();
                                mProgressHUD.setMessage("提交中...");
                                mProgressHUD.show();
                                HashMap<String, String> map = new HashMap<>();
                                map.put("JSON", DataJson);
                                WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
                                WebServiceUtils.callWebService(
                                        SPUtils.getServerPath() + define.ErpForAppServer,
                                        define.UpdateAssest, map, result -> {
                                            mProgressHUD.dismiss();
                                            if (result != null) {
                                                try {
                                                    result = URLDecoder.decode(result, "UTF-8");
                                                    LOG.e("提交:" + result);
                                                    Gson g = new Gson();
                                                    JSONObject jsonObject = new JSONObject(result);
                                                    String ReturnType = jsonObject.getString("ReturnType");
                                                    String ReturnMsg = jsonObject.getString("ReturnMsg");
                                                    if (ReturnType.equals("success")) {
                                                        setEnabled(false);
                                                        TV_Satus.setText("审核中");
                                                        RU.ShowDialog(ScanUtil.ReturnType_Success, "提交成功", ReturnMsg);
                                                    } else {
                                                        RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "上传失败:" + ReturnMsg);
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                    RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "数据解码异常");

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "数据解析异常");
                                                }
                                            } else {
                                                RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "提交失败:接口返回空");
                                            }
                                        });
                            })
                    ).show(mActivity);

        }

    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        LOG.e("takeSuccess=" + result.getImages().size());
        LOG.e("OriginalPath=" + result.getImage().getOriginalPath());
        Luban.with(mActivity)
                .load(result.getImage().getOriginalPath())
                .ignoreBy(100)
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LOG.e("压缩开始前调用，可以在方法内启动 loading UI");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LOG.e("压缩成功后调用，返回压缩后的图片文件");
                        LOG.e("路径:" + file.getPath());
                        Bitmap bit = RU.Path2Bitmap(file.getPath());
                        PDML.setAssetPicture(Utils.bitmapToBase64(bit));
                        IV_Photo.setImageBitmap(bit);

                    }

                    @Override
                    public void onError(Throwable e) {
                        LOG.e("当压缩过程出现问题时调用");
                    }
                }).launch();
    }


}

