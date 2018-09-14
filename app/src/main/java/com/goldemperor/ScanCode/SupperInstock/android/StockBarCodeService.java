package com.goldemperor.ScanCode.SupperInstock.android;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.goldemperor.MainActivity.define;
import com.goldemperor.ScanCode.SupperInstock.MainActivity;
import com.goldemperor.ScanCode.SupperInstock.model.MessageEnum;
import com.goldemperor.ScanCode.SupperInstock.model.MessageObject;
import com.goldemperor.Utils.LOG;
import com.goldemperor.model.SystemConfig;


public class StockBarCodeService extends Thread {
    protected static final String TAG = "MainActivity";
    //正式库
    public static final String asmxURL = SystemConfig.WebServiceURL;//"http://192.168.88.254:8011/ErpForAndroidStockServer.asmx";// getString(R.string.webservice_url);//"http://localhost:3087/ErpForAndroidStockServer.asmx";//
    //开发库
    // public static final String asmxURL = "http://localhost:3087/ErpForAndroidStockServer.asmx";
    //public static final String asmxURL = "http://192.168.88.254:8056/ErpForAndroidStockServer.asmx";
    private static String _BarCode = "";//扫入条码
    private static String _MoNO = "";//指令单号
    public Handler myHandler;
    public Context myContext;
    public UserLoginHelper myUserLoginHelper;
    public String _DefaultSuitID="1";//默认账套为金帝集团有限公司 即01.01账套
    public StockBarCodeService(Handler handler, Context context, UserLoginHelper userLoginHelper) {
        myHandler = handler;
        myContext = context;
        myUserLoginHelper = userLoginHelper;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        try {
            //设定账套下拉框 ,发送显示指令单命令 Looper.prepare();
            String XMLContent = PublicService.GetWebServiceComnon(myContext, define.Net2+define.ErpForAndroidStockServer, "GetDataTableAccountSuit", "suitID", _DefaultSuitID);
            Log.i(TAG, "XMLContent:" + XMLContent);//获得移动设备IMEI码
            Message message = new Message();
            MessageObject myMessageObject = new MessageObject();
            myMessageObject.Content = URLCode.toURLDecoded(XMLContent);
            myMessageObject.context = this.myContext;
            message.what = MessageEnum.ShowZzt;
            message.obj = myMessageObject;
            LOG.e("4套账信息="+myMessageObject.toString());
            myHandler.sendMessage(message);


            //设定组织信息下拉框
            String XMLContent2 = PublicService.GetWebServiceComnon(myContext,asmxURL, "GetOrganization", "suitID", _DefaultSuitID);
            Message message2 = new Message();
            MessageObject myMessageObject2 = new MessageObject();
            myMessageObject2.Content = URLCode.toURLDecoded(XMLContent2);
            myMessageObject2.context = this.myContext;
            message2.what = MessageEnum.ShowOrg;
            message2.obj = myMessageObject2;
            LOG.e("4组织信息="+myMessageObject2.toString());
            myHandler.sendMessage(message2);
            //Looper.loop();
            //设定单据类型
            Message message3 = new Message();
            message3.what = MessageEnum.ShowBillType;
            message3.obj = "ShowBillType";
            myHandler.sendMessage(message3);
            //创建登录配置信息对象
            if(MainActivity.myConfig==null)
                MainActivity.myConfig=new Config();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
