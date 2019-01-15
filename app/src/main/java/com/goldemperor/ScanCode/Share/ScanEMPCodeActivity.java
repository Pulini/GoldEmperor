package com.goldemperor.ScanCode.Share;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.fancybuttons.FancyButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

/**
 * File Name : ScanEMPCodeActivity
 * Created by : PanZX on  2018/8/7 11:07
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_scan_emp_code)
public class ScanEMPCodeActivity extends Activity implements ScanListener {

    @ViewInject(R.id.ET_Scan1)
    private EditText ET_Scan1;

    @ViewInject(R.id.ET_Scan2)
    private EditText ET_Scan2;

    @ViewInject(R.id.FB_Back)
    private FancyButton FB_Back;

    @ViewInject(R.id.FB_Submit)
    private FancyButton FB_Submit;

    private Activity mActivity;
    private ScanReceiver SR;
    String Code1 = "";
    String Code2 = "";
    boolean Scan1 = false;
    boolean Scan2 = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;

        String Name1 = getIntent().getExtras().getString("Name1");
        String Name2 = getIntent().getExtras().getString("Name2");
        if ("".equals(Name1)) {
            ET_Scan1.setVisibility(View.GONE);
        } else {
            ET_Scan1.setHint("扫描或输入" + Name1 + "工号");
        }
        if ("".equals(Name2)) {
            ET_Scan2.setVisibility(View.GONE);
        }else {
            ET_Scan2.setHint("扫描或输入" + Name2 + "工号");
        }

//        汇报人工号
        FB_Submit.setOnClickListener(v -> {
            if (!"".equals(Name1)) {
                Code1 = ET_Scan1.getText().toString().trim();
                if (Code1.equals("")) {
                    Toast.makeText(mActivity, "请输入或扫描" + Name1 + "工号", Toast.LENGTH_LONG).show();
                    return;
                } else if (!Scan1) {
                    Toast.makeText(mActivity, Name1 + "工号不合法", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            if (!"".equals(Name2)) {
                Code2 = ET_Scan2.getText().toString().trim();
                if (Code2.equals("")) {
                    Toast.makeText(mActivity, "请输入或扫描" + Name2 + "工号", Toast.LENGTH_LONG).show();
                    return;
                } else if (!Scan2) {
                    Toast.makeText(mActivity, Name2 + "工号不合法", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            setResult(RESULT_OK, new Intent().putExtra("EmpCode1", Code1).putExtra("EmpCode2", Code2));
            finish();
        });
        FB_Back.setOnClickListener(v -> finish());
        ET_Scan1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 6) {
                    CheckEmpCode(s.toString().trim(), 0);
                }
            }
        });

        ET_Scan2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 6) {
                    CheckEmpCode(s.toString().trim(), 1);
                }
            }
        });
    }


    private void CheckEmpCode(final String code, final int index) {

        HashMap<String, String> map = new HashMap<>();
        map.put("FEmpNumber", code);
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpPublicServer,
                define.JudgeEmpNumber,
                map,
                result -> {
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
                                if (index == 0) {
                                    Scan1 = true;
                                } else if (index == 1) {
                                    Scan2 = true;
                                }
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
                });

    }


    @Override
    protected void onPause() {
        super.onPause();
        ScanReceiver.unRegisterReceiver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScanReceiver.RegisterReceiver(this, this);
    }

    @Override
    public void CodeResult(String code) {
        if (ET_Scan1.hasFocus()) {
            ET_Scan1.setText(code);
            ET_Scan2.requestFocus();
        } else if (ET_Scan2.hasFocus()) {
            ET_Scan2.setText(code);
        }
    }

    @Override
    public void GetBarCodeStatusResult(boolean ReturnType, Object ReturnMsg) {

    }

    @Override
    public void GetReport(boolean ReturnType, Object ReturnMsg) {

    }

    @Override
    public void DeleteItem(int position) {

    }

    @Override
    public void AddItem(List<CodeDataModel> CDM, String Code) {

    }

    @Override
    public void ClearItem() {

    }

}
