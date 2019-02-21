package com.goldemperor.StockCheck.WaitView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;



import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;
import com.goldemperor.Widget.lovelydialog.LovelyTextInputDialog;
import com.google.gson.Gson;
import com.goldemperor.StockCheck.sql.stock_check;
import com.goldemperor.R;
import com.goldemperor.MainActivity.define;
import com.panzx.pulini.Bootstrap.BootstrapButton;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nova on 2017/7/19.
 */

public class LookActivity extends AppCompatActivity {


    private TextView info;
    private TextView auditor;

    private BootstrapButton sign;

    private BootstrapButton exceptional;
    //图片网络加载设置
    ImageOptions imageOptions;

    private Context mContext;
    private Activity act;
    private Bundle bundle;
    private List<String> mUpdataImageList;//图片
    private LookImageAdapter lookImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        mContext = this;
        act = this;
        //设置图片加载属性
        imageOptions = new ImageOptions.Builder()
                .setLoadingDrawableId(R.drawable.loading)
                .setFailureDrawableId(R.drawable.loading_failure)
                .setUseMemCache(true)
                .build();


        info = (TextView) findViewById(R.id.info);
        auditor = (TextView) findViewById(R.id.auditor);

        RequestParams params = new RequestParams(define.GetDataById);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("id") != null) {
                params.addQueryStringParameter("id", bundle.getString("id"));
            }
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                //解析result
                if (result != null) {
                    Gson gson = new Gson();
                    stock_check sc = gson.fromJson(result, stock_check.class);
                    if (sc.getInfo() != null) {
                        info.setText("稽查结果:" + sc.getInfo());
                        SpannableStringBuilder builder = new SpannableStringBuilder(info.getText().toString());
                        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                        builder.setSpan(redSpan, 5, info.getText().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        info.setText(builder);
                    }

                    if (sc.getAuditor() != null) {
                        auditor.setText("稽查人员:" + sc.getAuditor());
                    }
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

        //设置图片Grid
        mUpdataImageList = new ArrayList<>();
        RecyclerView imageRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        imageRecyclerView.setLayoutManager(new LinearLayoutManager(act));// 布局管理器。
        imageRecyclerView.addItemDecoration(new ListViewDecoration(act));// 添加分割线。

        lookImageAdapter = new LookImageAdapter(mUpdataImageList);
        lookImageAdapter.setOnItemClickListener(null);
        imageRecyclerView.setAdapter(lookImageAdapter);
        getImage();

        sign = (BootstrapButton) findViewById(R.id.sign);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getText().toString().contains("等待稽查")) {

                    LemonHello.getErrorHello("提示", "无法签收,本单号尚未上传稽查照片")
                            .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                }
                            }))
                            .show(act);
                } else {
                    RequestParams params = new RequestParams(define.Done);
                    params.addQueryStringParameter("id", bundle.getString("id"));
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(final String result) {
                            //解析result
                            //重新设置数据
                            final AlertDialog.Builder normalDialog =
                                    new AlertDialog.Builder(act);
                            normalDialog.setTitle("提示");
                            normalDialog.setMessage(result);
                            normalDialog.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            act.finish();
                                        }
                                    });
                            normalDialog.show();
                        }

                        //请求异常后的回调方法
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

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

            }

        });

        exceptional = (BootstrapButton) findViewById(R.id.refuse);
        exceptional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getText().toString().contains("等待稽查")) {
                    LemonHello.getErrorHello("提示", "无法报告异常,本单号尚未上传稽查照片")
                            .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                }
                            }))
                            .show(act);

                } else {

                    new LovelyTextInputDialog(mContext, R.style.EditTextTintTheme)
                            .setTopColorRes(R.color.darkDeepOrange)
                            .setTitle("请输入需要处理的异常因素")
                            .setMessage(" ")
                            .setIcon(R.drawable.ic_assignment_white_36dp)
                            .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                                @Override
                                public void onTextInputConfirmed(String text) {
                                    RequestParams params = new RequestParams(define.Exceptional);
                                    params.addQueryStringParameter("id", bundle.getString("id"));
                                    params.addQueryStringParameter("exceptional", text);
                                    x.http().get(params, new Callback.CommonCallback<String>() {
                                        @Override
                                        public void onSuccess(final String result) {
                                            //解析result
                                            //重新设置数据
                                            final AlertDialog.Builder normalDialog =
                                                    new AlertDialog.Builder(act);
                                            normalDialog.setTitle("提示");
                                            normalDialog.setMessage(result);
                                            normalDialog.setPositiveButton("确定",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    });
                                            normalDialog.show();
                                        }

                                        //请求异常后的回调方法
                                        @Override
                                        public void onError(Throwable ex, boolean isOnCallback) {

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
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();

                }
            }
        });

    }

    private void getImage() {
        RequestParams params = new RequestParams(define.GetImage);
        if (bundle != null) {
            if (bundle.getString("id") != null) {
                params.addQueryStringParameter("checkId", bundle.getString("id"));
            }
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                //解析result
                //重新设置数据
                ArrayList<stock_check_image> arraytemp = GsonFactory.jsonToArrayList(result, stock_check_image.class);
                for (int i = 0; i < arraytemp.size(); i++) {
                    mUpdataImageList.add(arraytemp.get(i).getImage());
                }
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lookImageAdapter.notifyDataSetChanged();
                    }
                });
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
}
