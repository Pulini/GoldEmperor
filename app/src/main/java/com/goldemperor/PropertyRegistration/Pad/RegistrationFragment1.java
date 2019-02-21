package com.goldemperor.PropertyRegistration.Pad;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.PgdActivity.Emp;
import com.goldemperor.PropertyRegistration.EmpModel;
import com.goldemperor.PropertyRegistration.PropertyDetailsModel;
import com.goldemperor.PropertyRegistration.RegistrationUtils;
import com.goldemperor.PropertyRegistration.SubmitDataModel;
import com.goldemperor.R;
import com.goldemperor.ScanCode.Share.ScanUtil;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.verticalviewpager.ViewPagerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;


/**
 * File Name : RegistrationFragment1
 * Created by : PanZX on  2019/1/21 10:18
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.fragment_registration_1)
public class RegistrationFragment1 extends ViewPagerFragment {


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

    @ViewInject(R.id.TV_SAP_PurchaseNum)
    private TextView TV_SAP_PurchaseNum;

    @ViewInject(R.id.TV_SAP_Invoice)
    private TextView TV_SAP_Invoice;


    @ViewInject(R.id.ET_Address)
    private EditText ET_Address;

    @ViewInject(R.id.IV_Photo)
    private ImageView IV_Photo;

    @ViewInject(R.id.ET_Notes)
    private EditText ET_Notes;

    private PropertyDetailsModel PDML;
    String TypeID = "";
    private RegistrationUtils RU;



    public interface OnTakePhotoListener {
        void TakePhoto();
    }

    private OnTakePhotoListener OTPL;

    public void setOnTakePhotoListener(OnTakePhotoListener otpl) {
        OTPL = otpl;
    }

    public interface OnSetMainDataListener {
        void SetMainData();
    }

    private OnSetMainDataListener OSDL;

    public void SetMainDataListener(OnSetMainDataListener otpl) {
        OSDL = otpl;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = x.view().inject(this, inflater, container);
        }

        initview();
        return rootView;
    }


    private void initview() {
        RU = new RegistrationUtils(getActivity());

//        TV_DeptName.setOnClickListener(v -> OSL.Select());
        IV_Photo.setEnabled(false);
        IV_Photo.setOnClickListener(v -> OTPL.TakePhoto());
    }

    public void setDeptName(String name) {
        TV_DeptName.setText(name);
    }

    public void setPhoto(File path) {
        Bitmap bit = RU.Path2Bitmap(path.getPath());
        PDML.setAssetPicture(Utils.bitmapToBase64(bit));
        IV_Photo.setImageBitmap(bit);
//        IV_Photo.setImageBitmap(getBitMBitmap(path.getPath()));
//        Picasso.get()
//                .load("file://"+path.getPath())
//                .error(R.drawable.loading_failure)
//                .into(IV_Photo);
    }


    public void setData(PropertyDetailsModel pdml) {
        PDML = pdml;
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
        ET_ParticipatorCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 6 && ET_ParticipatorCode.isFocused()) {
                    TypeID = "ParticipatorCode";
                    getUser(s.toString());
                }
            }
        });
        ET_LiableCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 6 && ET_LiableCode.isFocused()) {
                    TypeID = "LiableCode";
                    getUser(s.toString());
                }
            }
        });
        ET_CustodianCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 6 && ET_CustodianCode.isFocused()) {
                    TypeID = "CustodianCode";
                    GetLiableByEmpCode(s.toString());
                }
            }
        });


    }

    private void setEnabled(boolean b) {
        ET_ParticipatorCode.setEnabled(b);
        ET_LiableCode.setEnabled(b);
        ET_CustodianCode.setEnabled(b);
        ET_Number.setEnabled(b);
        ET_Price.setEnabled(b);
        ET_OrgVal.setEnabled(b);
        ET_Address.setEnabled(b);
        ET_Notes.setEnabled(b);
        IV_Photo.setEnabled(b);
    }

    private void getUser(String FNumber) {
        HashMap<String, String> map = new HashMap<>();
        map.put("FDeptmentID", "");
        map.put("FEmpNumber", FNumber);
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetEmpByFnumber, map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.e("getUser:" + result);
                            Gson g = new Gson();
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if (ReturnType.equals("success")) {
                                List<Emp> user = g.fromJson(ReturnMsg, new TypeToken<List<Emp>>() {
                                }.getType());
                                String empname = user.get(0).getEmp_Name();
                                int id = Integer.valueOf(user.get(0).getEmp_ID());
                                if (TypeID.equals("ParticipatorCode")) {
                                    TV_ParticipatorName.setText(empname);
                                    PDML.setFParticipator(id);
                                } else if (TypeID.equals("LiableCode")) {
                                    TV_LiableName.setText(empname);
                                    PDML.setFLiableEmpID(id);
                                }
                            } else {
                                if (TypeID.equals("ParticipatorCode")) {
                                    TV_ParticipatorName.setText("");
                                    PDML.setFParticipator(-1);
                                } else if (TypeID.equals("LiableCode")) {
                                    TV_LiableName.setText("");
                                    PDML.setFLiableEmpID(-1);
                                }
                                RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "无此工号");
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "数据解码异常");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "数据解析异常");
                        }
                    } else {
                        RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "无此工号");
                    }
                });
    }

    private void GetLiableByEmpCode(String EmpCode) {
        HashMap<String, String> map = new HashMap<>();
        map.put("EmpCode", EmpCode);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpPublicServer,
                define.GetEmpAndLiableByEmpCode, map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.e("getUser:" + result);
                            Gson g = new Gson();
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if (ReturnType.equals("success")) {
                                EmpModel EM = g.fromJson(ReturnMsg, new TypeToken<EmpModel>() {
                                }.getType());
                                TV_CustodianName.setText(EM.getFEmpName());
                                PDML.setFKeepEmpID(EM.getFEmpID());
                                PDML.setFDeptID(EM.getFDeptmentID());
                                TV_DeptName.setText(EM.getDeptName());
                                TV_LiableName.setText(EM.getFLiableEmpName());
                                ET_LiableCode.setText(EM.getFLiableEmpCode());
                                PDML.setFLiableEmpID(EM.getFLiableEmpID());
                            } else {
                                TV_CustodianName.setText("");
                                PDML.setFKeepEmpID(-1);
                                PDML.setFDeptID(-1);
                                TV_DeptName.setText("");
                                TV_LiableName.setText("");
                                ET_LiableCode.setText("");
                                PDML.setFLiableEmpID(-1);
                                RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "无此工号");
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "数据解码异常");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "数据解析异常");
                        }
                    } else {
                        RU.ShowDialog(ScanUtil.ReturnType_Error, "错误", "无此工号");
                    }
                });
    }

    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    public void Submit(OnSetMainDataListener SMD) {
        String DataJson = CheckData();
        if (DataJson != null) {
            LemonHello.getInformationHello("提交", "确定要发起审核吗？")
                    .addAction(
                            new LemonHelloAction("取消", (helloView, helloInfo, helloAction) -> helloView.hide()),
                            new LemonHelloAction("确定", (helloView, helloInfo, helloAction) -> {
                                helloView.hide();
                                RU.ShowLoading("提交中...");

                                HashMap<String, String> map = new HashMap<>();
                                map.put("JSON", DataJson);
                                WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
                                WebServiceUtils.callWebService(
                                        SPUtils.getServerPath() + define.ErpForAppServer,
                                        define.UpdateAssest, map, result -> {
                                            RU.DismissLoading();
                                            if (result != null) {
                                                try {
                                                    result = URLDecoder.decode(result, "UTF-8");
                                                    LOG.e("提交:" + result);
                                                    JSONObject jsonObject = new JSONObject(result);
                                                    String ReturnType = jsonObject.getString("ReturnType");
                                                    String ReturnMsg = jsonObject.getString("ReturnMsg");
                                                    LOG.e("ReturnType=" + ReturnType);
                                                    if (ReturnType.equals("success")) {
                                                        LOG.e("------------success");
                                                        SMD.SetMainData();
                                                        setEnabled(false);
                                                        RU.ShowDialog(ScanUtil.ReturnType_Success, "提交成功", ReturnMsg);
                                                    } else {
                                                        LOG.e("-------------error");
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
                    ).show(getActivity());

        }

    }

    public String CheckData() {
        String Participator = ET_ParticipatorCode.getText().toString();
        String Liable = ET_LiableCode.getText().toString();
        String Custodian = ET_CustodianCode.getText().toString();
        String Number = ET_Number.getText().toString();
        String Address = ET_Address.getText().toString();
        String Price = ET_Price.getText().toString();
        String OrgVal = ET_OrgVal.getText().toString();
        String Notes = ET_Notes.getText().toString();
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
        if (Participator.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_ParticipatorCode.getHint().toString());
            return null;
        }
        if (Custodian.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_CustodianCode.getHint().toString());
            return null;
        }
        if (Liable.isEmpty()) {
            RU.ShowDialog(ScanUtil.ReturnType_Error, "缺少数据", ET_LiableCode.getHint().toString());
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
        SDM.setFInterID(PDML.getFInterID());
        SDM.setFKeepEmpID(PDML.getFKeepEmpID());
        SDM.setFDeptID(PDML.getFDeptID());
        SDM.setFaddress(Address);
        SDM.setFPrice(Double.parseDouble(Price));
        SDM.setFOrgVal(Double.parseDouble(OrgVal));
        SDM.setFLiableEmpID(PDML.getFLiableEmpID());
        SDM.setFParticipator(PDML.getFParticipator());
        SDM.setFRegistrationerID(Integer.parseInt((String) SPUtils.get(define.SharedEmpId, "0")));
        SDM.setFNotes(Notes);
        SDM.setAssetPicture(PDML.getAssetPicture());
        return new Gson().toJson(SDM);
    }


}