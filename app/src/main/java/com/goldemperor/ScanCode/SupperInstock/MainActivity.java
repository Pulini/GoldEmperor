package com.goldemperor.ScanCode.SupperInstock;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.ScanCode.CxStockIn.DialogLoadding.WeiboDialogUtils;
import com.goldemperor.ScanCode.CxStockIn.widget.DelSlideListView;
import com.goldemperor.ScanCode.CxStockIn.widget.ListViewonSingleTapUpListenner;
import com.goldemperor.ScanCode.CxStockIn.widget.OnDeleteListioner;
import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.ScanCode.ScInstock.ScanEMPCodeActivity;
import com.goldemperor.ScanCode.ShowReportActivity;
import com.goldemperor.ScanCode.SupperInstock.android.CommDB;
import com.goldemperor.ScanCode.SupperInstock.android.Config;
import com.goldemperor.ScanCode.SupperInstock.android.NetworkHelper;
import com.goldemperor.ScanCode.SupperInstock.android.NewBarCodeDB;
import com.goldemperor.ScanCode.SupperInstock.android.PublicService;
import com.goldemperor.ScanCode.SupperInstock.android.StockBarCodeService;
import com.goldemperor.ScanCode.SupperInstock.android.SubmitBarCodeService;
import com.goldemperor.ScanCode.SupperInstock.android.TelephonyManagerClass;
import com.goldemperor.ScanCode.SupperInstock.deleteslide.ActionSheet;
import com.goldemperor.ScanCode.SupperInstock.model.BarCode;
import com.goldemperor.ScanCode.SupperInstock.model.DeviceInfoMap;
import com.goldemperor.ScanCode.SupperInstock.model.MessageEnum;
import com.goldemperor.ScanCode.SupperInstock.model.MessageObject;
import com.goldemperor.ScanCode.SupperInstock.model.UpdataInfo;
import com.goldemperor.ScanCode.SupperInstock.model.UserLoginInfo;
import com.goldemperor.Utils.HttpUtils;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Utils.ZProgressHUD;
import com.goldemperor.model.BarCodeStatusModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;


public class MainActivity extends FragmentActivity implements OnDeleteListioner, ListViewonSingleTapUpListenner, ActionSheet.OnActionSheetSelected, OnCancelListener {
    //滑动删除变量
    LinkedList<CodeDataModel> mlist = new LinkedList<CodeDataModel>();
    private CodeAdapter mMyAdapter;
    DelSlideListView mDelSlideListView;
    int delID = 0;
    //滑动删除变量
    public static String DeviceIMEI;//移动设备IMEI码
    public static Map<String, DeviceInfoMap> deviceInfoMap;//移动设备管理信息哈希表
    public static final String TAG = "MainActivity";
    private IntentFilter mFilter;
    private Button btnget, btnadd, btndelete, btnclear, btnsubmit;
    private EditText et_scanresult;
    public CheckBox checkBoxRed;//是否红冲
    public BarCodeHandler barCodeHandler;
    public TableLayout et_scan_table;
    public TextView txtcount;
    public SQLiteDatabase db;
    public String TABLE_NAME = "t_BarCode";
    private CommDB comDBHelper;
    public NewBarCodeDB BarCodeDBHelper;
    private RadioButton stockin, stockout, stocktemp;
    public static Config myConfig;//=new Config();
    public static boolean IsNeedCheckVersion = true;//是否检查自动更新
    //    public static MainActivity mainActivity_instance = null;//声明的变量
//    public static LoginActivity loginActivity_instance = null;//声明的变量
    private ScanManager mScanMgr;
    public UpdataInfo info;
    //    //条形码列表的适配器
//    private ContactsCursorAdapter m_contactsAdapter;
//
//    //加载器监听器
//    private ContactsLoaderListener m_loaderCallback = new ContactsLoaderListener();
//扫入工号条码窗口相关
    private Context mContext;
    private Activity act;
    private boolean isUserCode;
    private TextView userScanText;
    private TextView txtviewTitle;
    public static String userNumber="";
    String UserID = "0";
    private SubmitBarCodeService Athread;
    private Gson mGson;
    private List<String> BCSML = new ArrayList<>();
    private ZProgressHUD mProgressHUD;
    public static UserLoginInfo userLoginInfo = null;
    Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cxstockin_activity_main);
        mContext = this;
        mProgressHUD = new ZProgressHUD(mContext);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mGson = new Gson();
        act = this;
        isUserCode = false;
        //滑动删除实例化
        this.mDelSlideListView = (DelSlideListView) this.findViewById(R.id.listv);
        mMyAdapter = new CodeAdapter(this, mlist);
        mDelSlideListView.setAdapter(mMyAdapter);
        mDelSlideListView.setDeleteListioner(this);
        mDelSlideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //获得选中项的HashMap对象
                String s = ((CodeDataModel) arg0.getItemAtPosition(arg2)).getCode();
                et_scanresult.setText(s);

            }

        });
        mDelSlideListView.setSingleTapUpListenner(this);
        mMyAdapter.setOnDeleteListioner(this);

        //如下3行必须添加，否则HttpURLConnection.onnect 会出异常
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mScanMgr = ScanManager.getInstance();
        mScanMgr.setOutpuMode(ScanSettings.Global.VALUE_OUT_PUT_MODE_FILLING);//直接填充

        DeviceIMEI = new TelephonyManagerClass(this).getDeviceId();//移动设备唯一码，不要删除
        Log.i(TAG, "tm:" + DeviceIMEI);//获得移动设备IMEI码
        et_scanresult = (EditText) findViewById(R.id.et_workcard_no);
        // et_mono = (EditText) findViewById(R.id.et_mono);
        //et_scan_table = (TableLayout) findViewById(R.id.et_scan_table);
        //deviceInfoMap=WorkCardService.GetDeviceInfoMap();
        btnget = (Button) this.findViewById(R.id.btnget);
        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetBarCodeStatus();
            }
        });

        btnadd = (Button) this.findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new ClickEvent(this));

        btndelete = (Button) this.findViewById(R.id.btndelete);
        btndelete.setOnClickListener(new ClickEvent(this));

        btnclear = (Button) this.findViewById(R.id.btnclear);
        btnclear.setOnClickListener(new ClickEvent(this));
        btnsubmit = (Button) this.findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        txtcount = (TextView) this.findViewById(R.id.txtcount);
        txtviewTitle = (TextView) this.findViewById(R.id.txtviewtitle);
        txtviewTitle.setText("供应商扫码入库");

        stockin = (RadioButton) this.findViewById(R.id.stockin);
        stockout = (RadioButton) this.findViewById(R.id.stockout);
        stocktemp = (RadioButton) this.findViewById(R.id.stocktemp);
        stockin.setChecked(true);//默认设定选中入库单
        checkBoxRed = (CheckBox) this.findViewById(R.id.checkBoxRed);
        //先创建barCodeHandler 再创建数据库
        barCodeHandler = new BarCodeHandler(this, getMainLooper());
        checkBoxRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (userLoginInfo != null) {
                    if (isChecked) {
                        userLoginInfo.userInfo.setRed("-1");
                    } else {
                        userLoginInfo.userInfo.setRed("1");
                    }
                }
            }
        });
        //创建SQLLite 数据库
        IniDataBase();
        mFilter = new IntentFilter("ACTION_BAR_SCAN");
        //将广播的优先级调到最高1000
        mFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        if (BarCodeDBHelper == null)
            BarCodeDBHelper = new NewBarCodeDB(this, barCodeHandler, mMyAdapter, txtcount, BCSML);


        //加载器监听器

//        private ContactsLoaderListener m_loaderCallback = new ContactsLoaderListener();

//        initLoader();
//        setRiverListViewAdapter();
        GetBarCodeStatus();
    }

    private void GetBarCodeStatus() {
        mProgressHUD.setMessage("获取服务器数据...");
        mProgressHUD.show();
        RequestParams params = new RequestParams(define.Net2 + define.GetBarCodeStatusBysuitID);
        params.addQueryStringParameter("SearchFieldName", "FInStockBillID");
        params.addQueryStringParameter("suitID", "1");
        LOG.e("params=" + params.toString());
        HttpUtils.get(params, new HttpUtils.httpcallback() {
            @Override
            public void postcallback(String Finish, String paramString) {
                if (Finish.equals(HttpUtils.Success)) {
                    try {
                        paramString = URLDecoder.decode(paramString, "UTF-8");
                        paramString = paramString.substring(paramString.indexOf(">{") + 1, paramString.lastIndexOf("}<") + 1);
                        LOG.E("---------" + paramString);
                        JSONObject jsonObject = new JSONObject(paramString);
                        String data = jsonObject.getString("ReturnMsg");

                        List<BarCodeStatusModel> bcsml = mGson.fromJson(data, new TypeToken<List<BarCodeStatusModel>>() {
                        }.getType());
                        for (BarCodeStatusModel barCodeStatusModel : bcsml) {
                            BCSML.add(barCodeStatusModel.getFBarCode());
                        }
                        BarCodeDBHelper.Updata(BCSML);
                        mMyAdapter.notifyDataSetChanged();
                        Toast.makeText(mContext, "已拉取" + BCSML.size() + "条数据", Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mProgressHUD.dismiss();
            }
        });

    }

    private void submit() {

        ArrayList<BarCode> lists2 = BarCodeDBHelper.GetAllData();
        if (lists2.size() == 0) {
            Toast.makeText(getApplicationContext(), "没有数据,无须提交", Toast.LENGTH_SHORT).show();
            btnsubmit.setEnabled(true);
            return;
        }
        LOG.e("2检查");
        if (!checkBoxRed.isChecked()) {
            if (!mMyAdapter.checkdataYRK()) {
                Toast.makeText(getApplicationContext(), "存在已入库数据，请检查列表并删除。", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (!mMyAdapter.checkdataWRK()) {
                Toast.makeText(getApplicationContext(), "存在未入库数据，请检查列表并删除。", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        SubmitBarCode();
        /*
        isUserCode = true;
        userNumber = null;
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(act);
        View dialogView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_scan, null);
        userScanText = (TextView) dialogView.findViewById(R.id.tv_scan_user);
        normalDialog.setTitle("提示");
        normalDialog.setView(dialogView);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isUserCode = false;
                        if (userNumber == null) {
                            Toast.makeText(mContext, "尚未扫入工号", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } else {
                            //员工工号已获取，在此处进行操作
                            dialog.dismiss();
                            ArrayList<BarCode> lists2 = BarCodeDBHelper.GetAllData();
                            if (lists2.size() == 0) {
                                Toast.makeText(getApplicationContext(), "没有数据,无须提交", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            SubmitBarCode();
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isUserCode = false;
                        btnsubmit.setEnabled(true);
                        dialog.dismiss();
                    }
                });

        normalDialog.show();
        */
    }

    //滑动删除
    private class data {
        String D_BarCode;

        public data(String s) {
            D_BarCode = s;
        }

        public String getD_BarCode() {
            return D_BarCode;
        }

        public void setD_BarCode(String d_BarCode) {
            D_BarCode = d_BarCode;
        }
    }

    private void getReport() {
        mProgressHUD.setMessage("获取服务器数据...");
        mProgressHUD.show();
        if (userLoginInfo.userInfo.getDefaultStockID() == null) {
            String params1 = SubmitBarCodeService.GetDefaultStockParams(userLoginInfo.userInfo);
            String DefaultStockID = PublicService.GetWebServiceParamsComnon(mContext, StockBarCodeService.asmxURL, "GetDefaultStockID", params1);
            userLoginInfo.userInfo.setDefaultStockID(DefaultStockID);
        }
        LOG.e("DefaultStockID=" + userLoginInfo.userInfo.getDefaultStockID());
        List<SendReportModel.bjson> list = new ArrayList<>();
        List<BarCode> L = BarCodeDBHelper.GetAllData();
        for (BarCode barCode : L) {
            list.add(new SendReportModel.bjson(barCode.getD_BarCode()));
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("barcodeJson", mGson.toJson(list));
        map.put("OrganizeID", "1");
        map.put("BillTypeID", "1");
        map.put("DefaultStockID", userLoginInfo.userInfo.getDefaultStockID());
        map.put("Red", checkBoxRed.isChecked() ? "-1" : "1");
        map.put("UserID", UserID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.WEB_GETSUBMITBARCODEREPORT,
                map, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        mProgressHUD.dismiss();
                        LOG.e("result=" + result);
                        if (result != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if ("success".equals(ReturnType)) {
                                    ReportModel RM = new ReportModel();
                                    RM.Report = mGson.fromJson(ReturnMsg, new TypeToken<List<ReportModel.report>>() {
                                    }.getType());
                                    if (RM.Report.size() > 0) {
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("Report", RM);
                                        Intent intent = new Intent(MainActivity.this, ShowReportActivity.class);
                                        intent.putExtras(bundle);
                                        startActivityForResult(intent, 102);
                                    }
                                } else {
                                    Toast.makeText(mContext, ReturnMsg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(mContext, "数据解析异常", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onSingleTapUp() {

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
    public boolean isCandelete(int position) {
        return true;
    }


    @Override
    public void onDelete(int ID) {
        delID = ID;
        //获得所要删除的条形码信息
        String SelDelbarcode = mlist.get(delID).getCode();
        //弹出删除确认窗体
        ActionSheet.showSheet(this, this, this, SelDelbarcode);
    }


    @Override
    public void onBack() {

    }

    @Override
    public void onCancel(DialogInterface arg0) {

    }

    @Override
    public void onClick(int whichButton) {
        switch (whichButton) {
            case 0://删除单条条形码
//                mlist.remove(delID);
                String barcode = mlist.get(delID).getCode();
                BarCodeDBHelper.deleteTicketbarcode(barcode);
                mDelSlideListView.SnapToScreen();
//                mMyAdapter.notifyDataSetChanged();

                break;
            case 1:

                break;
            default:
                break;
        }
    }

    //滑动删除
    private void registerReceiver() {
        IntentFilter intFilter = new IntentFilter(ScanManager.ACTION_SEND_SCAN_RESULT);
        registerReceiver(mReceiver, intFilter);
    }

    private void unRegisterReceiver() {
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }
    }

    /**
     * 监听扫码数据的广播，当设置广播输出时作用该方法获取扫码数据
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ScanManager.ACTION_SEND_SCAN_RESULT.equals(action)) {
                byte[] bvalue1 = intent.getByteArrayExtra(ScanManager.EXTRA_SCAN_RESULT_ONE_BYTES);
                byte[] bvalue2 = intent.getByteArrayExtra(ScanManager.EXTRA_SCAN_RESULT_TWO_BYTES);
                String svalue1 = null;
                String svalue2 = null;
                try {
                    if (bvalue1 != null)
                        svalue1 = new String(bvalue1, "GBK");
                    if (bvalue2 != null)
                        svalue2 = new String(bvalue1, "GBK");
                    svalue1 = svalue1 == null ? "" : svalue1;
                    svalue2 = svalue2 == null ? "" : svalue2;
//                    tv_broadcast_result.setText(svalue1+"\n"+svalue2);
                    final String scanResult = svalue1.replaceAll("(\r\n|\r|\n|\n\r)", "");//svalue1+"\n"+svalue2//替换回车
                    String Mono = "";
                    et_scanresult.setText(scanResult);
                    et_scanresult.invalidate();
                    if (!isUserCode) {
                        InsertBarCode(scanResult);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    et_scanresult.setText("data encode failed.");
                }

//                Random random = new Random();
//                tv_broadcast_result.setTextColor(Color.argb(255, random.nextInt(256),
//                        random.nextInt(256), random.nextInt(256)));
            }
        }
    };

    /**
     * 在内存创建数据库和数据表
     */
    void IniDataBase() {
        try {
            comDBHelper = new CommDB(this);
            comDBHelper.open();
            if (BarCodeDBHelper == null)
                BarCodeDBHelper = new NewBarCodeDB(this, barCodeHandler, mMyAdapter, txtcount, BCSML);
            BarCodeDBHelper.open();
            BarCodeDBHelper.RefreshTable();

        } catch (SQLException e) {
        }
    }

    public class ClickEvent implements View.OnClickListener {
        public Context myContext;

        public ClickEvent(Context context) {
            super();
            myContext = context;
        }

        @Override
        public void onClick(View v) {
            try {
                v.setEnabled(false);
                switch (v.getId()) {
                    case R.id.btnadd:
                        InsertBarCode();
                        break;
                    case R.id.btndelete:
                        DeleteBarCode();
                        break;
                    case R.id.btnclear:
                        ArrayList<BarCode> lists = BarCodeDBHelper.GetAllData();
                        if (lists.size() == 0)
                            return;
                        ClearBarCode();
                        break;
                    case R.id.btnsubmit:
                        ArrayList<BarCode> lists2 = BarCodeDBHelper.GetAllData();
                        if (lists2.size() == 0) {
                            Toast.makeText(getApplicationContext(), "没有数据,无须提交", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        LOG.e("1检查数据：" + mMyAdapter.checkdataYRK());
                        if (!checkBoxRed.isChecked()) {
                            if (!mMyAdapter.checkdataYRK()) {
                                Toast.makeText(getApplicationContext(), "存在已入库数据，请检查列表并删除。", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            if (!mMyAdapter.checkdataWRK()) {
                                Toast.makeText(getApplicationContext(), "存在未入库数据，请检查列表并删除。", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        SubmitBarCode();
                        break;
                    default:
                        break;
                }

            } catch (Exception ex) {

            } finally {
                v.setEnabled(true);
            }
            //Toast.makeText(myContext, "点击提交按钮", Toast.LENGTH_SHORT).show();
        }
    }

    //判断是否有非法字符
    public boolean CheckBarCodeValid(String Barcode) {
        String[] UnValidWord = new String[]{",", "，", "~", "@", "#", "$", "%", "^", "&", "*", "(", ")"};
        for (int i = 0; i < UnValidWord.length; i++) {   //输出你的数据}
            if (Barcode.contains(UnValidWord[i])) {
                Toast.makeText(this, "扫入非法字符", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    public void InsertBarCode() {
        String Barcode = et_scanresult.getText().toString().replace(" ", "").replace("\n", "");
        if (!CheckBarCodeValid(Barcode))
            return;
        InsertBarCode(Barcode);
    }

    //添加记录
    public void InsertBarCode(String Barcode) {
        BarCodeDBHelper.InsertBarcode(Barcode);
    }

    ///删除记录
    public void DeleteBarCode() {

    }

    ///清空记录
    public void ClearBarCode() {
        BarCodeDBHelper.deleteAllBarcodes();
    }

    public boolean CheckNetAvailable() {
        if (!NetworkHelper.isNetworkAvailable(this)) {
            Toast.makeText(getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    ///提交记录
    public void SubmitBarCode() {

        if (userLoginInfo != null) {
            UserID = userLoginInfo.userInfo.getUserID();
            if (UserID == null)
                UserID = "0";
        }
        if (!CheckNetAvailable()) {
            return;
        }
        //有登录过，不再需要重复登录，直接提交数据就可以了
        if (Integer.parseInt(UserID) == 0) {
            Intent intent;
            intent = new Intent(this, LoginActivity.class);
//            //用Bundle携带数据
//            Bundle bundle=new Bundle();
//            //传递name参数为tinyphp
//            bundle.putString("MainActivityContext", "tinyphp");
//            intent.putExtras(bundle);
            startActivityForResult(intent, 101);
//            this.finish();
        } else {
            getReport();

        }
    }

    public void ClearSubmitData(Context myContext, String MessageType) {
        if (BarCodeDBHelper == null)
            BarCodeDBHelper = new NewBarCodeDB(myContext, barCodeHandler, mMyAdapter, txtcount, BCSML);
        //BarCodeDBHelper=new BarCodeDB(getApplicationContext(),barCodeHandler,et_scan_table,txtcount);////BarCodeDBHelper=new BarCodeDB(this,barCodeHandler,et_scan_table,txtcount);
        BarCodeDBHelper.ClearSubmitData(myContext, MessageType);
    }

    //根据登录信息重新初始化控件信息
    public void IniMainActivtyControl() {
        if (userLoginInfo.userInfo == null)
            return;
        String BillTypeID = userLoginInfo.userInfo.getBillTypeID();
        if (BillTypeID == "1")
            stockin.setChecked(true);
        else if (BillTypeID == "24")
            stockout.setChecked(true);
        else if (BillTypeID == "9")
            stocktemp.setChecked(true);

        if (userLoginInfo.userInfo.Red == "-1")
            checkBoxRed.setChecked(true);
        else
            checkBoxRed.setChecked(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
//        for(int i=0;i<50;i++){
//            mlist.add("滑动删除->"+i);
//        }
        mMyAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        mReceiver = null;
        mFilter = null;
        comDBHelper.close();
        comDBHelper = null;
        BarCodeDBHelper.close();
        BarCodeDBHelper = null;
        super.onDestroy();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * 进入程序的主界面
     */
    private void LoginMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        IsNeedCheckVersion = false;
        //结束掉当前的activity
        this.finish();
    }

    public class MONOTimerTask extends TimerTask {
        public Handler myHandler;
        public Context myContext;

        public MONOTimerTask(Handler handler, Context context) {
            myHandler = handler;
            myContext = context;
        }

        @Override
        public void run() {
        }
    }

    public class BarCodeHandler extends Handler {
        public Context myContext;

        private BarCodeHandler(Context context, Looper looper) {
            super(looper);
            myContext = context;
        }

        private void redrawscan_table() {
            et_scan_table.invalidate();
            et_scan_table.refreshDrawableState();
        }

        android.app.Dialog dd;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageEnum.ClearBarCodeData://删除本地所有数据
                    BarCodeDBHelper.ClearBarcodes();
//                    redrawscan_table();
                    break;
                case MessageEnum.SubmitClearData://成功提交数据，生产单据后，删除本地所有数据
                    LOG.e("供应商扫码入库提交成功");
                    MessageObject myMessageObject2 = (MessageObject) msg.obj;
                    String StockBillNO = "";
                    String Result = PublicService.parseResultJson(myMessageObject2.Content, StockBillNO);
                    if (Result.equals("success")) {
                        for (CodeDataModel codeData : mMyAdapter.mlist) {
                            BCSML.add(codeData.getCode());
                        }
                        //new Dialog(myContext,this).ClearBarCodeDataDialog("提示", "已成功生成单据编号："+MainActivity.loginActivity_instance.userLoginInfo.userInfo.getStockBillNO());
                        BarCodeDBHelper.ClearBarcodes();//生产成功后再删除本地数据，否则不删除
                        //this.m.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "成功生成单据编号：" + userLoginInfo.userInfo.getStockBillNO(), Toast.LENGTH_SHORT).show();
                        BarCodeDBHelper.Updata(BCSML);
                        GetBarCodeStatus();
                    } else {
                        Toast.makeText(getApplicationContext(), "失败提示：" + userLoginInfo.userInfo.getStockBillNO(), Toast.LENGTH_SHORT).show();
                    }
                    btnsubmit.setEnabled(true);
                    WeiboDialogUtils.closeDialog(dd);
                    //DialogThridUtils.closeDialog(dd);
                    //redrawscan_table();
                    break;
                case MessageEnum.ClearSubmitData://先提交数据到webservice,再删除本地所有数据
                    LOG.e("----------------------MessageEnum.ClearSubmitData");

                    MessageObject myMessageObject = (MessageObject) msg.obj;
                    Context LoginContext = myMessageObject.context;
                    String MessageType = myMessageObject.MessageType;
                    String AllDataJson = BarCodeDBHelper.GetAllDataJson();//先获得json数据
                    if (MessageType == "LoginActivty") {
                        IniMainActivtyControl();
                        return;
                    } else {
                        dd = WeiboDialogUtils.createLoadingDialog(MainActivity.this, "提交中..");
                    }

                    LOG.e("UserID=" + UserID);
                    if (checkBoxRed.isChecked()) {
                        userLoginInfo.userInfo.setRed("-1");
                    } else {
                        userLoginInfo.userInfo.setRed("1");
                    }
                    Athread = new SubmitBarCodeService(this, myContext, userLoginInfo, AllDataJson);
                    HttpUtils.CheckPermissions(act, "1050301", UserID, new HttpUtils.OnCheckPermissionsListener() {
                        @Override
                        public void PermissionsResult(Boolean r) {
                            LOG.e("权限=" + r);
                            if (r) {
                                btnsubmit.setEnabled(false);
                                LOG.e("------------提交数据-------------------");
                                Athread.start();
                            } else {
                                dd.dismiss();
                                Toast.makeText(getApplicationContext(), "权限不足，无法提交。请联系管理员。", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    //BarCodeDBHelper.ClearBarcodes();

                    break;
                case MessageEnum.UPDATA_CLIENT:

                    break;
                case MessageEnum.GET_UNDATAINFO_ERROR:
                    //服务器超时
                    Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_LONG).show();
                    LoginMain();
                    break;
                case MessageEnum.DOWN_ERROR:
                    //下载apk失败
                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
                    LoginMain();
                    break;
                case MessageEnum.LoginMain:
                    LoginMain();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOG.e("requestCode=" + requestCode + "resultCode=" + resultCode);

        switch (requestCode) {
            case 101:
                if (resultCode == 11) {
                    ClearSubmitData(mContext, "LoginActivty");
                }
                break;
            case 102:
                if (resultCode == 12) {
                    startActivityForResult(new Intent(MainActivity.this, ScanEMPCodeActivity.class), 103);
                }
                break;
            case 103:
                if (resultCode == 13) {
                    userNumber = data.getExtras().getString("EMPCODE");
                    BarCodeDBHelper.ClearSubmitData(this, "MainActivity");
                }
                break;


        }
    }

}
