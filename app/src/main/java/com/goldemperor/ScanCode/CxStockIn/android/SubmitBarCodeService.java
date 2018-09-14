package com.goldemperor.ScanCode.CxStockIn.android;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.goldemperor.ScanCode.CxStockIn.CxLoginActivity;
import com.goldemperor.model.MessageEnum;
import com.goldemperor.model.MessageObject;
import com.goldemperor.model.UserInfo;
import com.goldemperor.model.UserLoginInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//向webservice服务器提交条形码数据
public class SubmitBarCodeService extends Thread {
    protected static final String TAG = "ScReportActivity";
    public Handler myHandler;
    public Context myContext;
    public UserInfo myuserInfo;
    public String _DefaultSuitID = "1";//默认账套为金帝集团有限公司 即01.01账套
    private UserLoginInfo myuserLoginInfo = null;
    private CxLoginActivity myloginActivity_instance;
    private String myallDataJson;

    public SubmitBarCodeService(Handler handler, Context context, CxLoginActivity loginActivity_instance, String allDataJson) {
        myHandler = handler;
        myContext = context;
        myloginActivity_instance = loginActivity_instance;
        myallDataJson = allDataJson;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        try {
            if (myloginActivity_instance == null)
                return;
            String params = GetDefaultStockParams(myloginActivity_instance.userLoginInfo.userInfo);
            String DefaultStockID = PublicService.GetWebServiceParamsComnon(myContext, StockBarCodeService.asmxURL, "GetDefaultStockID", params);
            if (DefaultStockID.equals("0")) {
                Toast.makeText(myContext, "当前登录用户尚未配置默认仓库,请联系系统管理员", Toast.LENGTH_SHORT).show();
                return;
            }
            myloginActivity_instance.userLoginInfo.userInfo.setDefaultStockID(DefaultStockID);
            params = GetBarCodeParams(myloginActivity_instance.userLoginInfo.userInfo);
            //设定账套下拉框 ,发送显示指令单命令 Looper.prepare();
            String result = PublicService.GetWebServiceParamsComnon(myContext, StockBarCodeService.asmxURL, "SubmitCxBarCode2CollectBill", params);


         //  String result="{'Result':'success','StockBillNO':''}";
            result=URLCode.toURLDecoded(result);
            result = "{'result':" + result + "}";
            String Sendresult =result;
           result = PublicService.parseStockResultJson(myloginActivity_instance.userLoginInfo.userInfo, result);
            //if (result.equals("success")) {
                Looper.prepare();
                //new Dialog(myContext,myHandler).ClearBarCodeDataDialog("提示", "已成功生成单据编号："+myloginActivity_instance.userLoginInfo.userInfo.getStockBillNO());
                //Toast.makeText(myContext, "成功生成单据编号："+myloginActivity_instance.userLoginInfo.userInfo.getStockBillNO(), Toast.LENGTH_SHORT).show();
                //删除本地所有扫描的条形码数据
                Message message = new Message();
                MessageObject myMessageObject = new MessageObject();
                myMessageObject.Content = Sendresult;//myloginActivity_instance.userLoginInfo.userInfo.getStockBillNO();
                myMessageObject.context =myContext;
                message.what = MessageEnum.SubmitClearData;
                message.obj = myMessageObject;
                myHandler.sendMessage(message);
                Looper.loop();
           /* }
            else
            {
                Looper.prepare();
                new Dialog(myContext,myHandler).ClearBarCodeDataDialog("错误", myloginActivity_instance.userLoginInfo.userInfo.getStockBillNO());
                Looper.loop();
            }*/
        }catch (Exception ex) {
            //Toast.makeText(myContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
            Looper.prepare();
            new Dialog(myContext).ShowAlertDialog("异常",ex.getMessage());
            Looper.loop();
        }
    }
        //获得参数

    public String GetDefaultStockParams(UserInfo userInfo) {
        Map<String, String> ParamsMap = new HashMap<String, String>();
        String params = "";
        ParamsMap.put("OrganizeID", userInfo.getOrganizationID());
        ParamsMap.put("UserID", userInfo.getUserID());
        int i = 0;
        for (Map.Entry<String, String> entry : ParamsMap.entrySet()) {
            if (i == 0)
                params = entry.getKey() + "=" + entry.getValue();
            else {
                params += "&" + entry.getKey() + "=" + entry.getValue();
            }
            i++;
        }
        Log.i(TAG, "params :" + params);
        return params;
    }

    //获得参数
    public String GetBarCodeParams(UserInfo userInfo) {
        Map<String, String> ParamsMap = new HashMap<String, String>();
        String params = "";
        ParamsMap.put("UserID", userInfo.getUserID());
        ParamsMap.put("Red", userInfo.getRed());
        ParamsMap.put("DefaultStockID", userInfo.getDefaultStockID());
        ParamsMap.put("BillTypeID", userInfo.getBillTypeID());
        ParamsMap.put("OrganizeID", userInfo.getOrganizationID());
        ParamsMap.put("barcodeJson", URLCode.toURLEncoded(myallDataJson));

        int i = 0;
        for (Map.Entry<String, String> entry : ParamsMap.entrySet()) {
            if (i == 0)
                params = entry.getKey() + "=" + entry.getValue();
            else {
                params += "&" + entry.getKey() + "=" + entry.getValue();
            }
            i++;
        }
        Log.i(TAG, "params :" + params);
        return params;
    }

}
