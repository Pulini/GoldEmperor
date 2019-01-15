package com.goldemperor.GxReport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Utils.ZProgressHUD;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.model.ScProcessWorkCardInfoBysuitID;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;


/**
 * Created by Nova on 2017/8/15.
 */

public class GxReportScan extends AppCompatActivity implements QRCodeView.Delegate {

    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private QRCodeView mQRCodeView;
    private ArrayList<Order> QRCodeList;
    private List<String> list = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;
    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gxreport_scan);
        //隐藏标题栏
        getSupportActionBar().hide();
        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpot();
        mQRCodeView.changeToScanBarcodeStyle();
        Bundle bundle = this.getIntent().getExtras();
        QRCodeList = bundle.getParcelableArrayList("QRCodeList");
        mContext = this;
        mActivity = this;
        mProgressHUD = new ZProgressHUD(this);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String Code) {
        vibrate();
        boolean flag = true;
        for (int i = 0; i < QRCodeList.size(); i++) {
            if (QRCodeList.get(i).getFCardNo().equals(Code)) {
                flag = false;
            }
        }
        if (flag) {

            mProgressHUD.setMessage("检测条码中...");
            mProgressHUD.show();
            mQRCodeView.stopCamera();
            HashMap<String, String> map = new HashMap<>();
            map.put("BarCodeNumber", Code);
            map.put("suitID", "1");
            WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
            WebServiceUtils.callWebService(SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                    define.GetScProcessWorkCardInfoBysuitID, map, Result -> {
                        mProgressHUD.dismiss();
                        if (Result != null) {
                            try {
                                Result = URLDecoder.decode(Result, "UTF-8");
                                LOG.e("条码检查=" + Result);
                                JSONObject jsonObject = new JSONObject(Result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if(ReturnType.equals("success")){
                                    XStream xStream = new XStream();
                                    xStream.processAnnotations(ScProcessWorkCardInfoBysuitID.class);
                                    ScProcessWorkCardInfoBysuitID PWCI = (ScProcessWorkCardInfoBysuitID) xStream.fromXML(ReturnMsg);
                                    Order order;
                                    for (ScProcessWorkCardInfoBysuitID.table table : PWCI.getDbHelperTable()) {
                                        order = new Order();
                                        order.setFCardNo(table.getFCardNo());
                                        order.setFEmpID(table.getFEmpID());
                                        order.setFQty(table.getFQty());
                                        if (!list.contains(table.getFCardNo())) {
                                            list.add(table.getFCardNo());
                                            QRCodeList.add(order);
                                        }
                                    }
                                    LemonHello.getInformationHello("提示", "有效条码\n是否继续扫描？")
                                            .addAction(
                                                    new LemonHelloAction("继续扫码", (helloView, helloInfo, helloAction) -> {
                                                        mQRCodeView.startCamera();
                                                        mQRCodeView.startSpot();
                                                        mQRCodeView.showScanRect();
                                                        helloView.hide();
                                                    }),
                                                    new LemonHelloAction("去提交", (helloView, helloInfo, helloAction) -> {
                                                        helloView.hide();
                                                        onBackPressed();
                                                    }))
                                            .show(mActivity);
                                }else{
                                    showdialog(ReturnMsg);
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                mQRCodeView.startSpot();
                                showdialog("数据解码错误");
                            } catch (StreamException e) {
                                e.printStackTrace();
                                mQRCodeView.startSpot();
                                showdialog("数据解析异常");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mQRCodeView.startSpot();
                                showdialog("数据解析异常");
                            }
                        } else {
                            mQRCodeView.startSpot();
                            showdialog("检测数据失败");
                        }
                    });
        }


    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        String tipText = mQRCodeView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mQRCodeView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mQRCodeView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    private void showdialog(String msg){
        LemonHello.getErrorHello("错误", msg)
                .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide()))
                .show(mActivity);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        LemonHello.getErrorHello("错误", "打开相机出错")
                .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide()))
                .show(mActivity);
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent();
        mIntent.putParcelableArrayListExtra("QRCodeList", QRCodeList);
        // 设置结果，并进行传送
        this.setResult(1, mIntent);
        super.onBackPressed();

    }
}
