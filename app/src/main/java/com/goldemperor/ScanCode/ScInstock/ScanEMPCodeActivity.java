package com.goldemperor.ScanCode.ScInstock;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.nlscan.android.scan.ScanManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import com.goldemperor.Widget.fancybuttons.FancyButton;

/**
 * File Name : ScanEMPCodeActivity
 * Created by : PanZX on  2018/8/7 11:07
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_scan_emp_code)
public class ScanEMPCodeActivity extends Activity {

    @ViewInject(R.id.ET_Scan)
    private EditText ET_Scan;

    @ViewInject(R.id.FB_Back)
    private FancyButton FB_Back;

    @ViewInject(R.id.FB_Submit)
    private FancyButton FB_Submit;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        FB_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmpCode = ET_Scan.getText().toString().trim();
                if (!EmpCode.equals("")) {
                    CheckEmpCode(EmpCode);
                } else {
                    Toast.makeText(mActivity, "请输入工号或扫描工牌上的工号条码", Toast.LENGTH_LONG).show();
                }
            }
        });
        FB_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CheckEmpCode(final String EmpCode) {
        HashMap<String, String> map = new HashMap<>();
        map.put("FEmpNumber", EmpCode);
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpPublicServer,
                define.JudgeEmpNumber,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("提交工号合法=" + result);
                                //{"ReturnMsg":"合法工号","ReturnType":"success"}
                                //{"ReturnMsg":"工号长度有误,正常为6位","ReturnType":"error"}
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if ("success".equals(ReturnType)) {
                                    setResult(13, new Intent().putExtra("EMPCODE", EmpCode));
                                    finish();
                                }
                                Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Toast.makeText(mActivity, "数据解析异常", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mActivity, "数据解码异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

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
                    ET_Scan.setText(scanResult);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }
}
