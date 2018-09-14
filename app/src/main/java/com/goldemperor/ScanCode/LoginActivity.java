package com.goldemperor.ScanCode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.thoughtworks.xstream.XStream;

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
 * File Name : LoginActivity
 * Created by : PanZX on  2018/7/11 13:54
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：扫码登录界面基类
 */
@ContentView(R.layout.wa_activity_login)
public class LoginActivity extends Activity {

    @ViewInject(R.id.SP_ZT)
    private Spinner SP_ZT;

    @ViewInject(R.id.SP_ZZ)
    private Spinner SP_ZZ;

    @ViewInject(R.id.ET_User)
    private EditText ET_User;

    @ViewInject(R.id.ET_Password)
    private EditText ET_Password;

    @ViewInject(R.id.CB_Remember)
    private CheckBox CB_Remember;

    @ViewInject(R.id.B_Login)
    private Button B_Login;

    @ViewInject(R.id.B_Cancle)
    private Button B_Cancle;

    @ViewInject(R.id.RL_Login)
    private RelativeLayout RL_Login;

    private Activity mActivity;
    private String password = "";
    private String username = "";
    private String organizeid = "";
    private String accountsuitid = "";
    private AccountSuitXmlModel ASM;
    private OrganizationXmlModel OM;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        initview();
        GetDataTableAccountSuit();
    }

    private void initview() {
        boolean isCheck = dataPref.getBoolean(define.RememberPassword, false);
        if (isCheck) {
            String UserName = dataPref.getString(define.Login_UserName, "");
            String UserPassword = dataPref.getString(define.Login_UserPassword, "");
            ET_User.setText(UserName);
            ET_Password.setText(UserPassword);
        }
        CB_Remember.setChecked(isCheck);
        CB_Remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataEditor.putBoolean(define.RememberPassword, isChecked);
                if (isChecked) {
                    dataEditor.putString(define.Login_UserName, ET_User.getText().toString());
                    dataEditor.putString(define.Login_UserPassword, ET_Password.getText().toString());
                } else {
                    dataEditor.putString(define.Login_UserName, "");
                    dataEditor.putString(define.Login_UserPassword, "");
                }
                dataEditor.commit();
            }
        });
        RL_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOG.e("RL_Login");
            }
        });
        SP_ZT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accountsuitid = ASM.DbHelperTable.get(position).getFAccountSuitID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        SP_ZZ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                organizeid = OM.DbHelperTable.get(position).getFAdminOrganizeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        B_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = ET_User.getText().toString().trim();
                password = ET_Password.getText().toString().trim();
                if (accountsuitid.equals("")) {
                    Toast.makeText(mActivity, "请选择账套", Toast.LENGTH_LONG).show();
                    return;
                }
                if (organizeid.equals("")) {
                    Toast.makeText(mActivity, "请选择组织", Toast.LENGTH_LONG).show();
                    return;
                }
                if (username.equals("")) {
                    Toast.makeText(mActivity, "请输入用户名", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.equals("")) {
                    Toast.makeText(mActivity, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                Login();
            }
        });
        B_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void GetDataTableAccountSuit() {
        RL_Login.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                "GetDataTableAccountSuit",
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        RL_Login.setVisibility(View.GONE);
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
                                    LOG.e(table.getCode());
                                }
                                //适配器
                                ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, list);
                                //设置下拉框样式
                                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                arr_adapter.notifyDataSetChanged();//通知spinner刷新数据
                                //加载适配器
                                SP_ZT.setAdapter(arr_adapter);
                                GetOrganization();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void GetOrganization() {
        RL_Login.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                "GetOrganization",
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        RL_Login.setVisibility(View.GONE);
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
                                    LOG.e(table.getCode());
                                }
                                //适配器
                                ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, list);
                                //设置下拉框样式
                                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                arr_adapter.notifyDataSetChanged();//通知spinner刷新数据
                                //加载适配器
                                SP_ZZ.setAdapter(arr_adapter);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Login() {
        RL_Login.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("password", password);
        map.put("username", username);
        map.put("organizeid", organizeid);
        map.put("accountsuitid", accountsuitid);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                "Login",
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        RL_Login.setVisibility(View.GONE);
                        if (result != null) {
                            String UserID = "";
                            String DefaultStockID = "";
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.e("result=" + result);
                                //{"Result":"success","UserID":"5047"}
                                JSONObject jb = new JSONObject(result);
                                String Result = jb.getString("Result");
                                if (Result.equals("success")) {
                                    UserID = jb.getString("UserID");
                                    DefaultStockID = jb.getString("DefaultStockID");
                                    setResult(RESULT_OK, new Intent().putExtra("UserID", UserID).putExtra("DefaultStockID", DefaultStockID));
                                    finish();
                                } else {
                                    Toast.makeText(mActivity, "登录失败:" + Result, Toast.LENGTH_LONG).show();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                setResult(RESULT_OK, new Intent().putExtra("UserID", UserID).putExtra("DefaultStockID", DefaultStockID));
                                finish();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
