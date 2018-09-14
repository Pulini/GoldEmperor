package com.goldemperor.GxReport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Widget.core.QRCodeView;
import com.goldemperor.Widget.zbar.ZBarView;
import com.tapadoo.alerter.Alerter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;



/**
 * Created by Nova on 2017/8/15.
 */

public class GxReportScan extends AppCompatActivity implements QRCodeView.Delegate {

    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private QRCodeView mQRCodeView;
    private ArrayList<Order> QRCodeList;
    private Context mContext;
    private Activity mActivity;

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
        mActivity=this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
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
    public void onScanQRCodeSuccess(String result) {
        Alerter.create(mActivity)
                .setTitle("提示")
                .setText("扫描成功"+result)
                .setBackgroundColorRes(R.color.colorAlert)
                .show();

        vibrate();
        boolean flag = true;
        for (int i = 0; i < QRCodeList.size(); i++) {
            if (QRCodeList.get(i).getFCardNo().equals(result)) {
                flag = false;
            }
        }
        if (flag && result.length() > 10) {

            String path = define.Net2+define.GetScProcessWorkCardInfoBysuitID;

            RequestParams params = new RequestParams(path);
            params.setConnectTimeout(60000);
            params.setReadTimeout(60000);
            params.addQueryStringParameter("BarCodeNumber", result);
            params.addQueryStringParameter("suitID", define.suitID);
            Log.e("jindi",params.toString());
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    //解析result
                    //重新设置数据
                    try {
                        Order order = new Order();
                        String decodeResult = URLDecoder.decode(result, "UTF-8");
                        decodeResult = decodeResult.replace("<?xml version=\"1.0\" standalone=\"yes\"?>", "");
                        Log.e("GoldEmperor", "FCardNo:" + decodeResult);
                        XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = pullFactory.newPullParser();
                        parser.setInput(new StringReader(decodeResult));
                        int eventType = parser.getEventType();
                        //只要不是文档结束事件，就一直循环
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            switch (eventType) {
                                //触发开始文档事件
                                case XmlPullParser.START_DOCUMENT:

                                    break;
                                //触发开始元素事件
                                case XmlPullParser.START_TAG:
                                    //获取解析器当前指向的元素的名称
                                    if ("string".equals(parser.getName())) {
                                        //获取解析器当前指向元素的文本节点的值
                                        if (decodeResult.contains("未找到")) {
                                            Toast.makeText(mContext, parser.nextText(), Toast.LENGTH_SHORT).show();
                                        }

                                    } else if ("FCardNo".equals(parser.getName())) {
                                        //获取解析器当前指向元素的文本节点的值
                                        //Log.e("GoldEmperor", "FCardNo:" + parser.nextText());
                                        order.setFCardNo(parser.nextText());

                                    } else if ("FEmpID".equals(parser.getName())) {
                                        //获取解析器当前指向元素的文本节点的值
                                        order.setFEmpID(parser.nextText());
                                        //Log.e("GoldEmperor", "FCardNo:" + parser.nextText());
                                    } else if ("FQty".equals(parser.getName())) {
                                        //获取解析器当前指向元素的文本节点的值
                                        order.setFQty(parser.nextText());
                                        //Log.e("GoldEmperor", "FCardNo:" + parser.nextText());
                                    }

                                    break;
                                //触发结束元素事件
                                case XmlPullParser.END_TAG:
                                    //

                                    break;
                                case XmlPullParser.END_DOCUMENT:
                                    //
                                    break;
                                default:
                                    break;
                            }
                            eventType = parser.next();
                        }
                        if (order.getFCardNo() != null) {
                            QRCodeList.add(order);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                //请求异常后的回调方法
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Alerter.create(mActivity)
                            .setTitle("提示")
                            .setText("网络错误")
                            .setBackgroundColorRes(R.color.colorAlert)
                            .show();
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
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

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
