package com.goldemperor.StockCheck.ExceptionalView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.StockCheck.WaitView.ListViewDecoration;
import com.goldemperor.StockCheck.WaitView.LookImageAdapter;
import com.goldemperor.StockCheck.WaitView.stock_check_image;

import com.google.gson.Gson;
import com.goldemperor.StockCheck.sql.stock_check;
import com.goldemperor.R;
import com.goldemperor.MainActivity.TakePhotoHelper;
import com.goldemperor.MainActivity.define;


import org.devio.takephoto.app.TakePhotoFragment;
import org.devio.takephoto.model.TResult;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nova on 2017/7/22.
 */

public class DisposeView extends TakePhotoFragment {


    private TextView info;
    private TextView auditor;


    private TextView exceptional;

    private FragmentActivity act;

    //图片网络加载设置
    ImageOptions imageOptions;


    private TakePhotoHelper PhotoHelper;


    private Context mContext;
    private Bundle bundle;
    private List<String> mUpdataImageList;//图片
    private LookImageAdapter lookImageAdapter;

    private EditText edit_result;
    private BootstrapButton submitResult;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dispose, null);
        super.onCreate(savedInstanceState);
        act = getActivity();

        //设置图片加载属性
        imageOptions = new ImageOptions.Builder()
                .setLoadingDrawableId(R.drawable.loading)
                .setFailureDrawableId(R.drawable.loading_failure)
                .setUseMemCache(true)
                .build();


        info = (TextView) view.findViewById(R.id.info);
        auditor = (TextView) view.findViewById(R.id.auditor);


        exceptional = (TextView) view.findViewById(R.id.exceptional);

        bundle = act.getIntent().getExtras();
        //设置图片Grid
        mUpdataImageList = new ArrayList<>();
        RecyclerView imageRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        imageRecyclerView.setLayoutManager(new LinearLayoutManager(act));// 布局管理器。
        imageRecyclerView.addItemDecoration(new ListViewDecoration(act));// 添加分割线。

        lookImageAdapter = new LookImageAdapter(mUpdataImageList);
        lookImageAdapter.setOnItemClickListener(null);
        imageRecyclerView.setAdapter(lookImageAdapter);
        getImage();


        RequestParams params = new RequestParams(define.GetDataById);

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

                        info.setText("核查结果:" + sc.getInfo());
                        SpannableStringBuilder builder = new SpannableStringBuilder(info.getText().toString());
                        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                        builder.setSpan(redSpan, 5, info.getText().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        info.setText(builder);
                    }

                    if (sc.getAuditor() != null) {
                        info.setText("稽查人员:" + sc.getAuditor());
                    }

                    if (sc.getExceptional() != null) {
                        exceptional.setText("异常因素:" + sc.getExceptional());

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


        //设置照片选择
        PhotoHelper = new TakePhotoHelper();

        edit_result = (EditText) view.findViewById(R.id.edit_result);
        submitResult = (BootstrapButton) view.findViewById(R.id.submitResult);
        submitResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams(define.SubmitResult);
                params.addQueryStringParameter("id", bundle.getString("id"));
                params.addQueryStringParameter("caseclose", edit_result.getText().toString());
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
        });
        return view;
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

    @Override
    public void takeSuccess(TResult tResult) {
        super.takeSuccess(tResult);


    }

    public void putImage(OSS oss, final String fileName, String filePath, final ImageView im) {
        PutObjectRequest put = new PutObjectRequest(define.bucket, fileName, filePath);

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());

                    Toast.makeText(act, "照片上传失败，请检查网络设置", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void takeFail(TResult tResult, String s) {
        super.takeFail(tResult, s);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

}
