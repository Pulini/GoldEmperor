package com.goldemperor.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
//import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.goldemperor.PgdActivity.ProcessWorkCardPlanEntry;
import com.goldemperor.R;
import com.goldemperor.StaffWorkStatistics.StaffWorkStatisticsActivity;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.PdfUtil;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.NiceSpinner.NiceSpinner;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * File Name : ProcessInformationActivity
 * Created by : PanZX on  2018/8/27 14:05
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：制程查看界面
 */
@ContentView(R.layout.activity_process_information)
public class ProcessInformationActivity extends Activity implements OnPageChangeListener {


    @ViewInject(R.id.ET_Numbers)
    private EditText ET_Numbers;

    @ViewInject(R.id.NS_SelectType)
    private NiceSpinner NS_SelectType;

    @ViewInject(R.id.FB_Query)
    private FancyButton FB_Query;

    @ViewInject(R.id.PDFV_ShowPDF)
    private PDFView PDFV_ShowPDF;

    @ViewInject(R.id.LL_Reading)
    private LinearLayout LL_Reading;

    @ViewInject(R.id.PB_reading)
    private ProgressBar PB_reading;

    @ViewInject(R.id.TV_Msg)
    private TextView TV_Msg;

    private List<data> ProcessList = new ArrayList<>();
    private Activity mActivity;
    private int ProcessFlowID = -999;
    private int Page = 0;
    private String MoNo = "";

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        initview();

    }

    private void initview() {

        NS_SelectType.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NS_SelectType.setText(ProcessList.get(position).getProcessFlowName());
                ProcessFlowID = ProcessList.get(position).getProcessFlowID();
            }
        });
        FB_Query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(ET_Numbers);
                MoNo = ET_Numbers.getText().toString().trim();
                if (MoNo.equals("")) {
                    Toast.makeText(mActivity, "请输入指令单号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (ProcessFlowID == -999) {
                    Toast.makeText(mActivity, "请先选择制程", Toast.LENGTH_LONG).show();
                    return;
                }
                getProcessPDF();
            }
        });
        getProcessList();
    }

    public static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    private void getProcessList() {
        LL_Reading.setVisibility(View.VISIBLE);
        TV_Msg.setText("正在获取制程列表...");
        HashMap<String, String> map = new HashMap<>();
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.GetProcessFlowInfo,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        LL_Reading.setVisibility(View.GONE);
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("制程列表=" + result);
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if (ReturnType.equals("success")) {
                                    ProcessList = new Gson().fromJson(ReturnMsg, new TypeToken<List<data>>() {
                                    }.getType());
                                    List<String> list = new ArrayList<>();
                                    for (data d : ProcessList) {
                                        list.add(d.ProcessFlowName);
                                        LOG.e(d.ProcessFlowName);
                                    }
                                    NS_SelectType.attachDataSource(list);
                                    ProcessFlowID = ProcessList.get(NS_SelectType.getSelectedIndex()).getProcessFlowID();

                                } else {
                                    Toast.makeText(mActivity, ReturnMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mActivity, "解码失败", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mActivity, "解析失败", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(mActivity, "返回空", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void getProcessPDF() {
        LL_Reading.setVisibility(View.VISIBLE);
        TV_Msg.setText("正在读取PDF文件...");
        HashMap<String, String> map = new HashMap<>();
        map.put("MoNo", MoNo);
        map.put("ProcessFlowID", ProcessFlowID + "");
        map.put("type", "pdf");
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.GetPreviewFileByMoNo,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            TV_Msg.setText("下载完成,正在转码...");
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("result=" + result);
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if ("success".equals(ReturnType)) {
                                    PdfUtil.base64StringToPDF(ReturnMsg, new PdfUtil.Base64ToFileListener() {
                                        @Override
                                        public void OnFinish(File file) {
                                            TV_Msg.setText("转码完成,启动PDF");
                                            PDFV_ShowPDF.fromFile(file)
                                                    .defaultPage(Page)
                                                    .onPageChange(ProcessInformationActivity.this)
                                                    .enableAnnotationRendering(true)
                                                    .scrollHandle(new DefaultScrollHandle(mActivity))
                                                    .spacing(10)
//                                                    .pageFitPolicy(FitPolicy.BOTH)
                                                    .load();
                                        }

                                        @Override
                                        public void OnError(String msg) {
                                            TV_Msg.setText("转码完失败:" + msg);
                                        }
                                    });
                                } else {
                                    TV_Msg.setText("获取数据失败:" + ReturnMsg);
                                }
                            } catch (JSONException e) {
                                TV_Msg.setText("数据解析异常");
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                TV_Msg.setText("数据解码异常");
                                e.printStackTrace();
                            }
                        } else {
                            TV_Msg.setText("暂无数据");
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LL_Reading.setVisibility(View.GONE);
                            }
                        },1000);
                    }
                });
    }

    private static class data {
        //	"ProcessFlowID": 1,
        //	"ProcessFlowName": "成型"
        int ProcessFlowID;
        String ProcessFlowName;

        public int getProcessFlowID() {
            return ProcessFlowID;
        }

        public void setProcessFlowID(int processFlowID) {
            ProcessFlowID = processFlowID;
        }

        public String getProcessFlowName() {
            return ProcessFlowName;
        }

        public void setProcessFlowName(String processFlowName) {
            ProcessFlowName = processFlowName;
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        Page = page;
    }
}
