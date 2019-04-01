package com.goldemperor.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
//import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.goldemperor.R;
import com.goldemperor.Utils.HttpUtils;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.PdfUtil;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * File Name : PdfActivity
 * Created by : PanZX on  2018/8/14 08:58
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_pdf)
public class PdfActivity extends Activity implements OnPageChangeListener {

    @ViewInject(R.id.PDFV_ShowPDF)
    private PDFView PDFV_ShowPDF;

    @ViewInject(R.id.IV_Back)
    private ImageView IV_Back;

    @ViewInject(R.id.LL_Reading)
    private LinearLayout LL_Reading;

    @ViewInject(R.id.PB_reading)
    private ProgressBar PB_reading;

    @ViewInject(R.id.TV_Msg)
    private TextView TV_Msg;

    private Activity mActivity;
    private int Page = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mActivity = this;
        IV_Back.setOnClickListener(v -> finish());
        String ScWorkcardNo = getIntent().getExtras().getString("ScWorkcardNo");
        getdata(ScWorkcardNo);
    }

    private void getdata(String ScWorkcardNo) {
        LL_Reading.setVisibility(View.VISIBLE);
        PB_reading.setVisibility(View.VISIBLE);


        RequestParams RP = new RequestParams(define.SERVER+define.PORT_5142+define.GetScMoNoPreviewFile);
        RP.addQueryStringParameter("ScWorkcardNo", ScWorkcardNo);
        RP.addQueryStringParameter("suitID", "1");
        LOG.e("Map:" + RP.toString());
        HttpUtils.get(RP, (Finish, result) -> {
            LOG.E("result:" + result);
            if (Finish.equals(HttpUtils.Success)) {
                if (result != null) {
                    TV_Msg.setText("下载完成,正在转码...");
                    try {
                        result = URLDecoder.decode(result, "UTF-8");
                        LOG.E("callback:" + result);
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
                                            .onPageChange(PdfActivity.this)
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
                LL_Reading.setVisibility(View.GONE);
            } else {
                TV_Msg.setText("暂无数据");
            }
        });


    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        Page = page;
    }
}
