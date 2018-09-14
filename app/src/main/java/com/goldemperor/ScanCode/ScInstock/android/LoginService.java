package com.goldemperor.ScanCode.ScInstock.android;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.goldemperor.ScanCode.ScInstock.model.MessageEnum;
import com.goldemperor.ScanCode.ScInstock.model.MessageObject;
import com.goldemperor.ScanCode.ScInstock.model.UserInfo;
import com.goldemperor.ScanCode.ScInstock.model.UserLoginInfo;
import com.goldemperor.Utils.LOG;

import java.util.HashMap;
import java.util.Map;

public class LoginService extends Thread {
    protected static final String TAG = "MainActivity";
    public Handler myHandler;
    public Context myContext;
    public UserInfo myuserInfo;
    public String _DefaultSuitID="1";//默认账套为金帝集团有限公司 即01.01账套
    private UserLoginInfo myuserLoginInfo = null;
    public LoginService(Handler handler, Context context, UserLoginInfo userLoginInfo) {
        myHandler = handler;
        myContext = context;
        myuserLoginInfo=userLoginInfo;
        myuserInfo = myuserLoginInfo.userInfo;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        try {
            String params =GetParams(myuserInfo);
            //设定账套下拉框 ,发送显示指令单命令 Looper.prepare();
            String XMLContent = PublicService.GetWebServiceParamsComnon(myContext, StockBarCodeService.asmxURL, "Login",params);
            Log.i(TAG, "XMLContent:" + XMLContent);//获得移动设备IMEI码
            LOG.e("LoginService----------------------");
            Message message = new Message();
            MessageObject myMessageObject = new MessageObject();
            myMessageObject.Content = URLCode.toURLDecoded(XMLContent);
            myMessageObject.context = this.myContext;
            message.what = MessageEnum.UserLogin;
            message.obj = myMessageObject;
            myHandler.sendMessage(message);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //获得参数
    public String GetParams(UserInfo userInfo) {
        Map<String, String> ParamsMap = new HashMap<String, String>();
        String params = "";
        ParamsMap.put("password", userInfo.PassWord);
        ParamsMap.put("username",userInfo.UserName);
        ParamsMap.put("organizeid",userInfo.OrganizationID);// map.FScannerIP 设备IP
        ParamsMap.put("accountsuitid",userInfo.AccountSuitID);
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
