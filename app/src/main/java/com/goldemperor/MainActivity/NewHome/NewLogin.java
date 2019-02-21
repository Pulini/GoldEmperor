package com.goldemperor.MainActivity.NewHome;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goldemperor.MainActivity.NewHome.Model.AccountSuitXmlModel;
import com.goldemperor.MainActivity.NewHome.Model.GetCodeModel;
import com.goldemperor.MainActivity.NewHome.Model.NewLoginModel;
import com.goldemperor.MainActivity.NewHome.Model.OrganizationXmlModel;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.AMUtils;
import com.goldemperor.Utils.HttpUtils;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.ClearWriteEditText;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.google.gson.Gson;
import com.panzx.pulini.ZProgressHUD;
import com.tencent.bugly.crashreport.CrashReport;
import com.thoughtworks.xstream.XStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * File Name : NewLogin
 * Created by : PanZX on  2018/10/10 11:18
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class NewLogin {

    private ScrollView SV_Login;
    private TextView TV_Title;
    private Spinner SP_ZT;
    private Spinner SP_ZZ;
    private ClearWriteEditText CWET_IP;
    private ImageView IV_SetIP;
    private ClearWriteEditText CWET_Phone;
    private ClearWriteEditText CWET_Password;
    private ClearWriteEditText CWET_VerificationCode;
    private TextView TV_VerificationCode;
    private FancyButton FB_Login;
    private FancyButton FB_Bcak;


    private MaterialDialog MDLogin;
    private Activity mActivity;

    public static final String appKey = "afeeb3ab6b0090293a70a5ba1d26a478";
    public static final String appSecret = "e3c0d24ddd06";
    public static final String nonce = "98269826";
    private ZProgressHUD mProgressHUD;

    private Gson mGson;
    private CountDownTimer mCountDownTimer;
    private AccountSuitXmlModel ASM;
    private OrganizationXmlModel OM;
    private GetCodeModel GCM;
    private boolean isBright = true;
    private NewLoginListener NLL;
    private String SuitID = "";
    private String OrganizeID = "";
    private String UserID = "";
    private String LoginPhone = "";


    public NewLogin(Activity activity, NewLoginListener nll) {
        mActivity = activity;
        NLL = nll;
        MDLogin = new MaterialDialog.Builder(mActivity)
                .customView(R.layout.dialog_new_login, false)
                .canceledOnTouchOutside(false).build();
        initview();
    }

    public void show() {
        try {
            if (!isShowing()) {
                MDLogin.show();
                initdata();
            }
        } catch (Exception e) {
            LOG.e(e.toString());
        }
    }

    public void show(String Title) {
        try {
            TV_Title.setText(Title);
            if (!isShowing()) {
                MDLogin.show();
                initdata();
            }
        } catch (Exception e) {
            LOG.e(e.toString());
        }
    }

    public boolean isShowing() {
        return MDLogin.isShowing();
    }

    public void dismiss() {
        MDLogin.dismiss();
        CWET_Phone.setText("");
        CWET_Password.setText("");
        CWET_VerificationCode.setText("");
    }

    private void initview() {
        SV_Login = MDLogin.getCustomView().findViewById(R.id.SV_Login);
        TV_Title = MDLogin.getCustomView().findViewById(R.id.TV_Title);
        SP_ZT = MDLogin.getCustomView().findViewById(R.id.SP_ZT);
        SP_ZZ = MDLogin.getCustomView().findViewById(R.id.SP_ZZ);
        CWET_IP = MDLogin.getCustomView().findViewById(R.id.CWET_IP);
        IV_SetIP = MDLogin.getCustomView().findViewById(R.id.IV_SetIP);
        CWET_Phone = MDLogin.getCustomView().findViewById(R.id.CWET_Phone);
        CWET_Password = MDLogin.getCustomView().findViewById(R.id.CWET_Password);
        CWET_VerificationCode = MDLogin.getCustomView().findViewById(R.id.CWET_VerificationCode);
        TV_VerificationCode = MDLogin.getCustomView().findViewById(R.id.TV_VerificationCode);
        FB_Bcak = MDLogin.getCustomView().findViewById(R.id.FB_Bcak);
        FB_Login = MDLogin.getCustomView().findViewById(R.id.FB_Login);
        setListener();
    }

    private void initdata() {
        CWET_IP.setText(SPUtils.getServerPath());
        mGson = new Gson();
        mProgressHUD = new ZProgressHUD(mActivity);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);

        SuitID = (String) SPUtils.get(define.SharedSuitID, "1");
        OrganizeID = (String) SPUtils.get(define.SharedOrganizeID, "1");
        UserID = (String) SPUtils.get(define.SharedUserId, "");
        LoginPhone = (String) SPUtils.get(define.LoginPhone, "");

        if (!LoginPhone.equals("")) {
            CWET_Phone.setText(LoginPhone);
        }
        LOG.e("SuitID=" + SuitID + "   OrganizeID=" + OrganizeID + "   UserID=" + UserID);
        GetDataTableAccountSuit();
//        GetOrganization();
    }

    private void setListener() {
        IV_SetIP.setOnClickListener(v -> SetService());
        TV_VerificationCode.setOnClickListener(v -> startDown(60 * 1000, 1000));
        TV_VerificationCode.setClickable(false);
        FB_Login.setOnClickListener(v -> NewLogin(SuitID, OrganizeID, CWET_Phone.getText().toString().trim(), CWET_Password.getText().toString().trim(), CWET_VerificationCode.getText().toString()));
        FB_Bcak.setOnClickListener(v -> dismiss());
        SP_ZT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SuitID = ASM.DbHelperTable.get(position).getFAccountSuitID();
                GetOrganization();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SP_ZZ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OrganizeID = OM.DbHelperTable.get(position).getFAdminOrganizeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //监听手机号码  输入完整前不能点击获取验证码
        CWET_Phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11 && isBright) {
//                    AMUtils.onInactive(mActivity, CWET_Phone);
                    CWET_Password.requestFocus();
                    if (AMUtils.isMobile(CWET_Phone.getText().toString().trim())) {
                        TV_VerificationCode.setClickable(true);
                        TV_VerificationCode.setTextColor(Color.parseColor("#3989FC"));
                        return;
                    }
                }
                TV_VerificationCode.setClickable(false);
                TV_VerificationCode.setTextColor(Color.parseColor("#cccccc"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CWET_VerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    new Handler().post(() -> SV_Login.fullScroll(ScrollView.FOCUS_DOWN));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        MDLogin.setOnDismissListener(dialog -> NLL.Back());
    }


    private void SetService() {
        String IP = CWET_IP.getText().toString().trim();
        if(IP.equals("")){
            SPUtils.seveServerPath(define.SERVER + define.PORT_8012);
            CWET_IP.setText(SPUtils.getServerPath());
        }else{
            SPUtils.seveServerPath(IP);
        }
        LOG.e("服务器地址:" + SPUtils.getServerPath());
        Toast.makeText(mActivity, "服务器地址:" + SPUtils.getServerPath(), Toast.LENGTH_LONG).show();
        SP_ZZ.setAdapter(getAdapter(null));
        GetDataTableAccountSuit();
    }

    public void GetDataTableAccountSuit() {
        if (SPUtils.getServerPath().length() < 7) {
            return;
        }
        mProgressHUD.setMessage("读取账套列表...");
        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetDataTableAccountSuit,
                map, result -> {
                    mProgressHUD.dismiss();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("GetDataTableAccountSuit=" + result);
                            XStream xStream = new XStream();
                            xStream.processAnnotations(AccountSuitXmlModel.class);
                            ASM = (AccountSuitXmlModel) xStream.fromXML(result);
                            List<String> list = new ArrayList<>();
                            for (AccountSuitXmlModel.table table : ASM.getDbHelperTable()) {
                                list.add(table.getCode());
//                                LOG.e(table.getCode());
                            }
                            //加载适配器
                            SP_ZT.setAdapter(getAdapter(list));
                            for (int i = 0; i < ASM.getDbHelperTable().size(); i++) {
                                if (ASM.getDbHelperTable().get(i).getFAccountSuitID().equals(SuitID)) {
                                    SP_ZT.setSelection(i);
                                    break;
                                }
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ShowDialog("E", "错误", "接口异常，无法获取账套数据。");
                        SP_ZT.setAdapter(getAdapter(null));
                    }
                });
    }

    public void GetOrganization() {
        mProgressHUD.setMessage("读取组织列表...");
        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("suitID", SuitID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetOrganization,
                map, result -> {
                    mProgressHUD.dismiss();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("GetOrganization=" + result);
                            XStream xStream = new XStream();
                            xStream.processAnnotations(OrganizationXmlModel.class);
                            OM = (OrganizationXmlModel) xStream.fromXML(result);
                            List<String> list = new ArrayList<>();
                            for (OrganizationXmlModel.table table : OM.getDbHelperTable()) {
                                list.add(table.getCode());
                                LOG.e(table.getCode() + "-----" + table.getFAdminOrganizeID());
                            }
                            //加载适配器
                            SP_ZZ.setAdapter(getAdapter(list));
                            for (int i = 0; i < OM.getDbHelperTable().size(); i++) {
                                if (OM.getDbHelperTable().get(i).getFAdminOrganizeID().equals(OrganizeID)) {
                                    SP_ZZ.setSelection(i);
                                    break;
                                }
                            }

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ShowDialog("E", "错误", "接口异常，无法获取组织数据。");
                        SP_ZZ.setAdapter(getAdapter(null));
                    }
                });
    }


    public void startDown(long time, long mills) {
        mCountDownTimer = new CountDownTimer(time, mills) {
            @Override
            public void onTick(long millisUntilFinished) {
                TV_VerificationCode.setText(String.valueOf(millisUntilFinished / 1000) + "s");
                TV_VerificationCode.setClickable(false);
                TV_VerificationCode.setTextColor(Color.parseColor("#CCCCCC"));
                isBright = false;
            }

            @Override
            public void onFinish() {
                TV_VerificationCode.setText("获取验证码");
                TV_VerificationCode.setClickable(true);
                TV_VerificationCode.setTextColor(Color.parseColor("#3989FC"));
                isBright = true;
                if (mCountDownTimer != null) mCountDownTimer.cancel();
            }

        }.start();
        GetCode();
    }

    public void GetCode() {
        mProgressHUD.setMessage("获取验证码...");
        mProgressHUD.show();
        try {
            String curTime = String.valueOf((new Date()).getTime() * 1000L);
            String checkSum = Utils.getCheckSum(appSecret, nonce, curTime);

            JSONObject JB = new JSONObject();
            JB.put("UserPhone", CWET_Phone.getText().toString().trim());

            HashMap<String, String> map = new HashMap<>();
            map.put("data", JB.toString());

            RequestParams params = new RequestParams(define.HTTP_GETCODE);
            params.addHeader("AppKey", appKey);
            params.addHeader("Nonce", nonce);
            params.addHeader("CurTime", curTime);
            params.addHeader("CheckSum", checkSum);
            params.setAsJsonContent(true);
            params.setBodyContent(mGson.toJson(map));
            LOG.e("请求验证码:" + params.toJSONString());
            HttpUtils.post(params, (Finish, result) -> {
                mProgressHUD.dismiss();
                if (Finish.equals(HttpUtils.Success)) {
                    LOG.e("获取验证码=" + result);
                    String VerNum = "999999";
                    GCM = new GetCodeModel();
                    try {
                        JSONObject jb = new JSONObject(result);
                        int StatusCode = jb.getInt("StatusCode");
                        GCM.setStatusCode(StatusCode);
                        if (StatusCode == 200) {
                            String Info = jb.getString("Info");
                            String Data = jb.getString("Data");
                            LOG.e("Info：" + Info);
                            LOG.e("Data：" + Data);
                            GetCodeModel.info GCMi = mGson.fromJson(Info, GetCodeModel.info.class);
                            GetCodeModel.data GCMd = mGson.fromJson(Data, GetCodeModel.data.class);
                            GCM.setInfo(GCMi);
                            GCM.setData(GCMd);
                            if (GCM != null) {
                                if (GCM.getStatusCode() == 200) {
                                    VerNum = GCM.getData().getCode();
                                    ShowDialog("S", "成功", GCM.getInfo().getReturnMsg());
                                } else {
                                    ShowDialog("E", "失败", GCM.getInfo().getReturnMsg());
                                }
                            }
                        } else {
                            ShowDialog("E", "失败", jb.getString("Data"));
                        }
                        CWET_VerificationCode.setText(VerNum);
                    } catch (JSONException e) {
                        ShowDialog("E", "错误", "获取验证码解析异常");
                    }
                } else if (Finish.equals(HttpUtils.Error)) {
                    ShowDialog("E", "错误", "接口访问异常");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayAdapter<String> getAdapter(List<String> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        //适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, list);
        //设置下拉框样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arr_adapter.notifyDataSetChanged();//通知spinner刷新数据
        return arr_adapter;
    }

    private void NewLogin(String accountSuitID, String organizeID, String userName, String password, String code) {
        if ("".equals(accountSuitID)) {
            ShowDialog("W", "警告", "请选择账套信息");
            return;
        }
        if ("".equals(organizeID)) {
            ShowDialog("W", "警告", "请选择组织信息");
            return;
        }
        if ("".equals(userName)) {
            ShowDialog("W", "警告", "请输入手机号");
            return;
        }
        if ("".equals(userName)) {
            ShowDialog("W", "警告", "请输入密码");
            return;
        }
        if ("".equals(code)) {
            ShowDialog("W", "警告", "请输入验证码");
            return;
        }
        mProgressHUD.setMessage("正在登录...");
        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("accountSuitID", accountSuitID);
        map.put("organizeID", organizeID);
        map.put("phone", userName);
        map.put("password", password);
        map.put("code", code);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(SPUtils.getServerPath() + define.ErpForAppServer,
                define.NewPhoneLogin,
                map, result -> {
                    mProgressHUD.dismiss();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("登录=" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                JSONArray ja = new JSONArray(ReturnMsg);
                                jsonObject = ja.getJSONObject(0);
                                NewLoginModel NLM = mGson.fromJson(jsonObject.toString(), NewLoginModel.class);
                                LOG.e("Name" + NLM.getFName());
                                LOG.e("Number" + NLM.getFNumber());
                                CrashReport.setUserId(NLM.getFUserID() + "");
                                SPUtils.put(define.SharedSuitID, SuitID);
                                SPUtils.put(define.SharedOrganizeID, NLM.getFOrganizeID() + "");
                                SPUtils.put(define.SharedUser, NLM.getFName());
                                SPUtils.put(define.SharedJobNumber, NLM.getFNumber());
                                SPUtils.put(define.SharedEmpId, NLM.getFEmpID() + "");
                                SPUtils.put(define.SharedSex, NLM.getFSex());
                                SPUtils.put(define.SharedPosition, NLM.getFPosition());
                                SPUtils.put(define.SharedFDeptmentid, NLM.getFDeptmentID() + "");
                                SPUtils.put(define.SharedPhone, CWET_Phone.getText().toString().trim());
                                SPUtils.put(define.SharedPassword, Utils.md5(CWET_Password.getText().toString().trim()).toUpperCase());
                                SPUtils.put(define.SharedUserId, NLM.getFUserID() + "");
                                SPUtils.put(define.SharedDefaultStockID, NLM.getFDefaultStockID() + "");
                                SPUtils.put(define.SharedDiningRoomID, NLM.getFDiningRoomID() + "");
                                SPUtils.put(define.SharedDeptmentName, NLM.getFDeptmentName());
                                SPUtils.put(define.LoginPhone, userName);
                                SPUtils.put(define.LoginType, define.LoginType);

                                NLL.Login(NLM);
                                dismiss();
                            } else {
                                ShowDialog("E", "登录失败", ReturnMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ShowDialog("E", "错误", "数据解析异常");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            ShowDialog("E", "错误", "数据解码异常");
                        }
                    } else {
                        ShowDialog("E", "错误", "接口异常");
                    }
                });
    }

    private void ShowDialog(String Type, String Title, String MSG) {
        try {
            if ("S".equals(Type)) {
                LemonHello.getSuccessHello(Title, MSG)
                        .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
            } else if ("E".equals(Type)) {
                LemonHello.getErrorHello(Title, MSG)
                        .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
            } else if ("W".equals(Type)) {
                LemonHello.getWarningHello(Title, MSG)
                        .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
            } else if ("I".equals(Type)) {
                LemonHello.getInformationHello(Title, MSG)
                        .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
            }
        } catch (Exception exception) {
            LOG.e(exception.getMessage());
        }
    }

}
