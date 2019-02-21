package com.goldemperor.LoginActivity;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.goldemperor.PgdActivity.NameListResult;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;
import com.google.gson.Gson;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;
import com.goldemperor.MainActivity.define;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import com.panzx.pulini.Bootstrap.BootstrapButton;
import com.tencent.bugly.crashreport.CrashReport;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nova on 2017/7/19.
 */

public class LoginActivity extends AppCompatActivity {


    public String appKey = "afeeb3ab6b0090293a70a5ba1d26a478";
    public String appSecret = "e3c0d24ddd06";
    public String nonce = "98269826";
    public static String checkSum;

    public String curTime = String.valueOf((new Date()).getTime() * 1000L);


    private EditText edit_phone;
    private EditText edit_password;
    //private TextView register;

    private BootstrapButton login;

    private Context con;
    private Activity act;

    private Button btn_Verification;
    private EditText VerificationEdit;

    Timer timer = new Timer();
    private int Countdown = 60;

    private String VerNum = "111111";
    private String vn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //隐藏标题栏
        getSupportActionBar().hide();
        con = this;
        act = this;

        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_password = (EditText) findViewById(R.id.edit_password);

        edit_phone.setText((String)SPUtils.get(define.SharedPhone, ""));

        btn_Verification = (Button) findViewById(R.id.btn_Verification);

        VerificationEdit = (EditText) findViewById(R.id.VerificationEdit);
        /*
        register = (TextView) findViewById(R.id.registerText);
        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(act, RegisterActivity.class);
                act.startActivity(i);
                act.finish();
            }
        });
*/
        //获得系统短信回执 此次调试可设置999999
        VerNum = "999999";//code;
        //点击发送验证码按钮
        btn_Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_phone.getText().length() == 11) {
                    final View view = v;
                    btn_Verification.setEnabled(false);

                    timer = new Timer();
                    task = new TimerTask() {

                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = Countdown--;

                            handler.sendMessage(message);
                        }
                    };
                    timer.schedule(task, 0, 1000);

                    CodeRequest codeRequest = new CodeRequest();
                    PhoneBody phoneBody = new PhoneBody();
                    phoneBody.setUserPhone(edit_phone.getText().toString().trim());
                    codeRequest.setData(phoneBody);

                    RequestParams params = new RequestParams(define.GetCode);
                    params.addHeader("AppKey", appKey);
                    params.addHeader("Nonce", nonce);

                    params.addHeader("CurTime", curTime);
                    checkSum = Utils.getCheckSum(appSecret, nonce, curTime);
                    params.addHeader("CheckSum", checkSum);
                    Gson g = new Gson();
                    params.setAsJsonContent(true);
                    params.setBodyContent(g.toJson(codeRequest));

                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            //解析result
                            result = result.replaceAll("\\\\", "");
                            String code = result.substring(result.indexOf("\"code\""), result.indexOf(",", result.indexOf("\"code\"")));
                            code = code.replaceAll("\"", "").replaceAll("code", "").replaceAll(":", "");
                            //获得系统短信回执 此次调试可设置999999
                            VerNum = code;//code;
                            LOG.e(VerNum);
                        }

                        //请求异常后的回调方法
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(con, "网络异常,短信发送失败，验证码为999999", Toast.LENGTH_LONG).show();
                            Log.e("jindi", "" + ex.toString());
                        }

                        //主动调用取消请求的回调方法
                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {
                        }
                    });
                } else {
                    Toast.makeText(con, "请输入正确的11位手机号码", Toast.LENGTH_LONG).show();
                }
            }
        });
        //点击登录按钮
        login = (BootstrapButton) findViewById(R.id.login);

        login.setOnClickListener(v -> {
            vn = VerificationEdit.getText().toString().trim();
            LOG.e("VerNum:"+VerNum);
            LOG.e("vn:"+vn);
            if (edit_phone.getText().toString().trim().isEmpty()) {

                LemonHello.getInformationHello("提示", "请输入手机号!")
                        .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                            }
                        })).show(act);

            } else if (edit_password.getText().toString().isEmpty()) {

                LemonHello.getInformationHello("提示", "请输入密码!")
                        .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                            }
                        })).show(act);
            } else if (!VerNum.equals(vn) && !VerNum.equals("999999")) {

                LemonHello.getErrorHello("提示", "验证码错误!")
                        .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                            }
                        })).show(act);
            } else {
                login.setText("登陆中...");
                Gson g = new Gson();
                final Request request = new Request();
                RequestBody requestBody = new RequestBody();
                requestBody.setUserPhone(edit_phone.getText().toString().trim());
                requestBody.setPassword(Utils.md5(edit_password.getText().toString().trim()).toUpperCase());
                request.setData(requestBody);

                RequestParams params = new RequestParams(define.Login);
                params.addHeader("AppKey", appKey);
                params.addHeader("Nonce", nonce);
                params.addHeader("CurTime", curTime);
                checkSum = Utils.getCheckSum(appSecret, nonce, curTime);
                params.addHeader("CheckSum", checkSum);

                params.setAsJsonContent(true);
                params.setBodyContent(g.toJson(request));

                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //解析result
                        //重新设置数据
                        result = result.replaceAll("\\\\", "");
                        //result=result.replaceAll("/","");
                        LOG.e("登录="+result);
                        final String resultTemp = result;
                        if (result.contains("成功")) {
                            login.setText("登录成功");

                            final AlertDialog.Builder normalDialog =
                                    new AlertDialog.Builder(act);
                            normalDialog.setTitle("提示");
                            normalDialog.setMessage("登录成功");
                            normalDialog.setPositiveButton("确定",
                                    (dialog, which) -> {

                                        //...To-do
                                        String name = resultTemp.substring(resultTemp.indexOf("name"), resultTemp.indexOf(",", resultTemp.indexOf("name")));
                                        name = name.replaceAll("\"", "").replaceAll("name", "").replaceAll(":", "");
                                        SPUtils.put(define.SharedUser, name);
                                        CrashReport.setUserId(name);

                                        String FNumber = resultTemp.substring(resultTemp.indexOf("FNumber"), resultTemp.indexOf(",", resultTemp.indexOf("FNumber")));
                                        FNumber = FNumber.replaceAll("\"", "").replaceAll("FNumber", "").replaceAll(":", "");
                                        SPUtils.put(define.SharedJobNumber, FNumber);

                                        String FEmpid = resultTemp.substring(resultTemp.indexOf("FEmpid"), resultTemp.indexOf(",", resultTemp.indexOf("FEmpid")));
                                        FEmpid = FEmpid.replaceAll("\"", "").replaceAll("FEmpid", "").replaceAll(":", "");
                                        SPUtils.put(define.SharedEmpId, FEmpid);

                                        //另外获取UserID
                                        GetUserID(FEmpid);

                                        String sex = resultTemp.substring(resultTemp.indexOf("sex"), resultTemp.indexOf(",", resultTemp.indexOf("sex")));
                                        sex = sex.replaceAll("\"", "").replaceAll("sex", "").replaceAll(":", "");
                                        SPUtils.put(define.SharedSex, sex);


                                        String position = resultTemp.substring(resultTemp.indexOf("position"), resultTemp.indexOf(",", resultTemp.indexOf("position")));
                                        position = position.replaceAll("\"", "").replaceAll("position", "").replaceAll(":", "");
                                        SPUtils.put(define.SharedPosition, position);

                                        String WXPic = resultTemp.substring(resultTemp.indexOf("userpic"), resultTemp.indexOf("}", resultTemp.indexOf("userpic")));
                                        WXPic = WXPic.replaceAll("\"", "").replaceAll("userpic", "").replaceAll(":", "");
                                        SPUtils.put(define.SharedWXPic, WXPic);


                                        String FDeptmentid = resultTemp.substring(resultTemp.indexOf("FDeptmentid"), resultTemp.indexOf(",", resultTemp.indexOf("FDeptmentid")));
                                        FDeptmentid = FDeptmentid.replaceAll("\"", "").replaceAll("FDeptmentid", "").replaceAll(":", "");
                                        SPUtils.put(define.SharedFDeptmentid, FDeptmentid);


                                        String FOrganizeid = resultTemp.substring(resultTemp.indexOf("FOrganizeid"), resultTemp.indexOf(",", resultTemp.indexOf("FOrganizeid")));
                                        FOrganizeid = FOrganizeid.replaceAll("\"", "").replaceAll("FOrganizeid", "").replaceAll(":", "");
                                        SPUtils.put(define.SharedFOrganizeid, FOrganizeid);


                                        SPUtils.put(define.SharedPhone, edit_phone.getText().toString().trim());

                                        SPUtils.put(define.SharedPassword, Utils.md5(edit_password.getText().toString().trim()).toUpperCase());

                                        act.finish();
                                    });
                            // 显示
                            normalDialog.show();
                        } else {

                            LemonHello.getErrorHello("提示", "密码错误!")
                                    .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                                        @Override
                                        public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                            helloView.hide();
                                        }
                                    })).show(act);
                            login.setText("登录");
                        }
                    }

                    //请求异常后的回调方法
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        login.setText("登陆错误,服务器请求失败");
                        Log.e("jindi", ex.getMessage());
                    }

                    //主动调用取消请求的回调方法
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });


            }
        });
    }

    public void GetUserID(String EmpID) {
        RequestParams params = new RequestParams(SPUtils.getServerPath() + define.GetUserID);
        params.addQueryStringParameter("FEmpID", EmpID);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (result.contains("FUserID")) {
                    String FUserID = result.substring(result.indexOf("<FUserID>"), result.indexOf("</FUserID>"));
                    FUserID = FUserID.replaceAll("<FUserID>", "").replaceAll("</FUserID>", "");
                    SPUtils.put(define.SharedUserId, FUserID);

                    String FDeptmentName = result.substring(result.indexOf("<FDeptmentName>"), result.indexOf("</FDeptmentName>"));
                    FDeptmentName = FDeptmentName.replaceAll("<FDeptmentName>", "").replaceAll("</FDeptmentName>", "");
                    SPUtils.put(define.SharedDeptmentName, FDeptmentName);


                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }

    //验证码倒计时
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            Message message = new Message();
            message.what = Countdown--;

            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what <= 0) {

                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }

                if (task != null) {
                    task.cancel();
                    task = null;
                }
                btn_Verification.setEnabled(true);
                btn_Verification.setText("发送验证码");
                Countdown = 60;
            } else {

                btn_Verification.setText("(" + Countdown + "s)发送验证码");

            }
            super.handleMessage(msg);
        }
    };
}
