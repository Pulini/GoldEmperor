package com.goldemperor.ScanCode.ScInstock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.R;
import com.goldemperor.ScanCode.ScInstock.android.LoginService;
import com.goldemperor.ScanCode.ScInstock.android.NetworkHelper;
import com.goldemperor.ScanCode.ScInstock.android.PublicService;
import com.goldemperor.ScanCode.ScInstock.android.UserInfoDB;
import com.goldemperor.ScanCode.ScInstock.android.UserLoginHelper;
import com.goldemperor.ScanCode.ScInstock.android.XmlUtils;
import com.goldemperor.ScanCode.ScInstock.model.AccountSuit;
import com.goldemperor.ScanCode.ScInstock.model.MessageEnum;
import com.goldemperor.ScanCode.ScInstock.model.MessageObject;
import com.goldemperor.ScanCode.ScInstock.model.Organization;
import com.goldemperor.ScanCode.ScInstock.model.UserInfo;
import com.goldemperor.ScanCode.ScInstock.model.UserLoginInfo;
import com.goldemperor.Utils.LOG;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录信息窗体
 */
public class LoginActivity extends Activity {
    // UI references.

    public Spinner spinner_zzt;//当前账套
    public Spinner spinner_org;//组织名称
    public Spinner spinner_billtype;//单据类型
    public EditText login_username;//erp用户名
    public EditText login_password;//erp用户密码
    public CheckBox login_red;//是否红冲
    public CheckBox login_autoLogin;//记住密码

    private UserLoginHelper userLoginHelper = null;
    public UserLoginInfo userLoginInfo = null;
    public MainActivity.BarCodeHandler barCodeHandler;
    public LoginHandler myLoginHandler;
    public static final String TAG = "MainActivity";
    private Map<String, AccountSuit> AccountSuitMap;//账套哈希表
    private Map<String, Organization> OrganizationMap;//组织哈希表
    private AccountSuit NowSelectAccountSuit;
    private Organization NowSelectOrganization;
    private String NowSelectBillType;
    public UserInfoDB UserInfoDBHelper;
    public Context MainActivityContext;//主窗体的Context
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cxstockin_activity_login);
        if (!NetworkHelper.isNetworkAvailable(this))
        {
            Toast.makeText(getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
            return;
        }
        spinner_zzt = (Spinner) findViewById(R.id.spinner_zzt);
        spinner_org = (Spinner) findViewById(R.id.spinner_org);
        spinner_billtype = (Spinner) findViewById(R.id.spinner_billtype);
        login_username = (EditText) findViewById(R.id.login_username);
        login_red = (CheckBox) findViewById(R.id.login_red);
        login_autoLogin = (CheckBox) findViewById(R.id.login_autoLogin);
        login_password = (EditText) findViewById(R.id.login_password);
        login_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        AccountSuitMap = new HashMap<String, AccountSuit>();
        OrganizationMap = new HashMap<String, Organization>();
        ((Button) findViewById(R.id.button_sign_in)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        ((Button) findViewById(R.id.button_sign_return)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //myLoginHandler
        myLoginHandler = new LoginHandler(this, getMainLooper());

        userLoginInfo = new UserLoginInfo(spinner_zzt, spinner_org, spinner_billtype, login_username, login_password, login_red, login_autoLogin);
        //userLoginHelper=new UserLoginHelper(MainActivity.mainActivity_instance.barCodeHandler,this,userLoginInfo);
        userLoginHelper = new UserLoginHelper(myLoginHandler, this, userLoginInfo);
//        userLoginHelper.IniLoginFormInfo();

        UserInfoDBHelper=new UserInfoDB(this,myLoginHandler,userLoginInfo);
        UserInfoDBHelper.open();
        UserInfoDBHelper.IniLoginInfo();

//        MainActivity.loginActivity_instance=LoginActivity.this;//以便在别的activity中可以直接调用
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        login_username.setError(null);
        login_password.setError(null);

        // Store values at the time of the login attempt.
        String username = login_username.getText().toString();
        String password = login_password.getText().toString();
        if (userLoginInfo.userInfo == null) {
            userLoginInfo.userInfo = new UserInfo();
        }
        userLoginInfo.userInfo.BillTypePosition=spinner_billtype.getSelectedItemPosition();//Integer.toString(spinner_billtype.getSelectedItemPosition());
        userLoginInfo.userInfo.OrgPosition=spinner_org.getSelectedItemPosition();//Integer.toString(spinner_org.getSelectedItemPosition());
        userLoginInfo.userInfo.AccountPosition=spinner_zzt.getSelectedItemPosition();//Integer.toString(spinner_zzt.getSelectedItemPosition())
        if(NowSelectAccountSuit==null||NowSelectAccountSuit.FAccountSuitID==null||NowSelectOrganization.FItemID==null||NowSelectBillType==null){
            LOG.e(NowSelectAccountSuit==null?"null":"!null");
            return;
        }
        userLoginInfo.userInfo.AccountSuitID = NowSelectAccountSuit.FAccountSuitID;
        userLoginInfo.userInfo.OrganizationID = NowSelectOrganization.FItemID;
        userLoginInfo.userInfo.BillTypeID = GetBillTypeID(NowSelectBillType);
        userLoginInfo.userInfo.UserName = username;
        userLoginInfo.userInfo.PassWord = password;
        if(login_red.isChecked())
            userLoginInfo.userInfo.Red="-1";
        else
            userLoginInfo.userInfo.Red="1";
        MainActivity.userLoginInfo=userLoginInfo;
        //UserInfoDBHelper.InsertUserInfo(userLoginInfo.userInfo);
        LoginService Athread = new LoginService(myLoginHandler, this,userLoginInfo);
        Athread.start();
    }

    public String GetBillTypeID(String BillTypeName) {
        String BillTypeID = "";
        if (BillTypeName == "入库单")
            BillTypeID = "106";
        else if (BillTypeName == "领料单")
            BillTypeID = "107";
        else if (BillTypeName == "暂收单")
            BillTypeID = "9";
        return BillTypeID;

    }

    private void attemptReturn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public class LoginHandler extends Handler {
        public Context myContext;

        private LoginHandler(Context context, Looper looper) {
            super(looper);
            myContext = context;
        }

        private List<String> _fields;
        public List<String> fields;

        public List<String> Getfields() {
            if (_fields == null)
                _fields = GetAccountSuitFields();
            return _fields;
        }

        private List<String> GetAccountSuitFields() {
            List<String> myfields = new ArrayList<String>();
            Field[] fields = AccountSuit.class.getFields();  //获得该类及其父类的所有字段
            for (Field field : fields)//利用增强for循环分解字段
            {
                String str = field.getName();//获得变量名
                myfields.add(str);
            }
            return myfields;
        }

        private List<String> GetOrganizationFields() {
            List<String> myfields = new ArrayList<String>();
            Field[] fields = Organization.class.getFields();  //获得该类及其父类的所有字段
            for (Field field : fields)//利用增强for循环分解字段
            {
                String str = field.getName();//获得变量名
                myfields.add(str);
            }
            return myfields;
        }

        /*
          初始化账套表
         */
        public void InizztTable(List<Object> list, MessageObject myMessageObject) {
            List<String> data_list;
            ArrayAdapter<String> arr_adapter;
            spinner_zzt = (Spinner) findViewById(R.id.spinner_zzt);
            //数据
            data_list = new ArrayList<String>();
            AccountSuitMap.clear();
            //扫描多次，则清除之前数据
            for (int i = 0; i < list.size(); i++) {
                AccountSuit rp = (AccountSuit) list.get(i);
                data_list.add(rp.Code);
                if (!AccountSuitMap.containsKey(rp.Code))
                    AccountSuitMap.put(rp.Code, rp);
            }
            //适配器
            arr_adapter = new ArrayAdapter<String>(myContext, android.R.layout.simple_spinner_item, data_list);
            //设置下拉框样式
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arr_adapter.notifyDataSetChanged();//通知spinner刷新数据
            //加载适配器
            spinner_zzt.setAdapter(arr_adapter);
            spinner_zzt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //获取选择的项的值
                    String sInfo = parent.getItemAtPosition(position).toString();
                    if (AccountSuitMap.containsKey(sInfo)) ;
                    {
                        NowSelectAccountSuit = AccountSuitMap.get(sInfo);
//                        Toast.makeText(myContext, NowSelectAccountSuit.FAccountSuitName, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    String sInfo = "什么也没选！";
                    Toast.makeText(myContext, sInfo, Toast.LENGTH_SHORT).show();

                }


            });

        }

        /*
          初始化组织
         */
        public void IniOrganizationTable(List<Object> list, MessageObject myMessageObject) {
            List<String> data_list;
            ArrayAdapter<String> arr_adapter;
            spinner_org = (Spinner) findViewById(R.id.spinner_org);
            //数据
            data_list = new ArrayList<String>();
            OrganizationMap.clear();
            int RandID = 1;//设定控件ID
            for (int i = 0; i < list.size(); i++) {
                Organization rp = (Organization) list.get(i);
                data_list.add(rp.Code);
                OrganizationMap.put(rp.Code, rp);
            }
            //适配器
            arr_adapter = new ArrayAdapter<String>(myContext, android.R.layout.simple_spinner_item, data_list);
            //设置下拉框样式
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arr_adapter.notifyDataSetChanged();//通知spinner刷新数据
            //加载适配器
            spinner_org.setAdapter(arr_adapter);
            spinner_org.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //获取选择的项的值
                    String sInfo = parent.getItemAtPosition(position).toString();
                    if (OrganizationMap.containsKey(sInfo)) ;
                    {
                        NowSelectOrganization = OrganizationMap.get(sInfo);
//                        Toast.makeText(myContext, NowSelectOrganization.FName, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }


            });
        }

        /*
            初始化单据类型
             */
        public void IniBillTypeTable() {
            List<String> data_list;
            ArrayAdapter<String> arr_adapter;
            spinner_billtype = (Spinner) findViewById(R.id.spinner_billtype);
            //数据
            data_list = new ArrayList<String>();
            int RandID = 1;//设定控件ID
            data_list.add("入库单");
//            data_list.add("领料单");
//            data_list.add("暂收单");
            //适配器
            arr_adapter = new ArrayAdapter<String>(myContext, android.R.layout.select_dialog_item, data_list);
            //设置下拉框样式
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arr_adapter.notifyDataSetChanged();//通知spinner刷新数据
            //加载适配器
            spinner_billtype.setAdapter(arr_adapter);
            spinner_billtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //获取选择的项的值
                    NowSelectBillType = parent.getItemAtPosition(position).toString();
//                    Toast.makeText(myContext, NowSelectBillType, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }


            });
        }
        // 普通Json数据解析
        public String parseLoginResultJson(UserLoginInfo userLoginInfo, String strResult) {
            try {
                JSONObject jsonObj = new JSONObject(strResult).getJSONObject("result");
                String Result = jsonObj.getString("Result");
                userLoginInfo.userInfo.UserID = jsonObj.getString("UserID");
                return Result;
            } catch (JSONException e) {
                System.out.println("Json parse error");
                e.printStackTrace();
                return "Json parse error";
            }
        }

        /*
        系统登录
        */
        public void DoLogin(Message msg)
        {
            MessageObject myMessageObject = (MessageObject) msg.obj;
            String result = myMessageObject.Content;
            result="{'result':"+result+"}" ;
            LOG.e("登录："+result);
            if(userLoginInfo.userInfo==null){
                return;
            }
            result= PublicService.parseLoginResultJson(userLoginInfo.userInfo,result);
            if(result.equals("success") ) {
                UserInfoDBHelper.ClearUserInfo();
                UserInfoDBHelper.InsertUserInfo(userLoginInfo.userInfo);
                setResult(11);
                finish();
            }
            else
            {
                Toast.makeText(myContext, result, Toast.LENGTH_SHORT).show();
                //login_username.setError(result);
            }
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageEnum.ShowZzt://显示账套信息
                    MessageObject myMessageSize = (MessageObject) msg.obj;
                    String xmlcontent = myMessageSize.Content;//URLCode.toURLDecoded(myMessageObject.Content);
                    Log.i(TAG, "账套信息解析后xmlcontent:" + xmlcontent);//获得账套信息
                    LOG.e("账套信息"+xmlcontent);
                    fields = GetAccountSuitFields();
                    List<String> elements = GetAccountSuitFields();
                    List<Object> list = XmlUtils.parse(xmlcontent, AccountSuit.class, fields, elements, "DbHelperTable");
                    InizztTable(list, myMessageSize);
                    break;
                case MessageEnum.ShowOrg://显示组织信息
                    MessageObject myMessageSize2 = (MessageObject) msg.obj;
                    String xmlcontent2 = myMessageSize2.Content;//URLCode.toURLDecoded(myMessageObject.Content);
                    Log.i(TAG, "组织信息解析后xmlcontent:" + xmlcontent2);//获得组织信息
                    LOG.e("组织信息"+xmlcontent2);
                    fields = GetOrganizationFields();
                    List<String> elements2 = GetOrganizationFields();
                    List<Object> list2 = XmlUtils.parse(xmlcontent2, Organization.class, fields, elements2, "DbHelperTable");
                    IniOrganizationTable(list2, myMessageSize2);
                    break;
                case MessageEnum.ShowBillType://显示单据信息
                    IniBillTypeTable();
                    break;
                case MessageEnum.UserLogin://系统登录
                    DoLogin(msg);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }
}

