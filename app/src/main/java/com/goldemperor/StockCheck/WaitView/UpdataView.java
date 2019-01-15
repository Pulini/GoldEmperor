package com.goldemperor.StockCheck.WaitView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.goldemperor.MainActivity.OSSHelper;
import com.goldemperor.MainActivity.People;
import com.goldemperor.R;
import com.goldemperor.MainActivity.TakePhotoHelper;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Widget.SuperDialog;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;

import com.google.gson.Gson;


import org.devio.takephoto.app.TakePhotoFragment;
import org.devio.takephoto.model.TResult;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nova on 2017/7/19.
 */

public class UpdataView extends TakePhotoFragment {

    private FragmentActivity act;


    private EditText edit_auditor;
    private EditText edit_info;

    private BootstrapButton submit;

    private ImageView image1Btn;

    private String info;
    //图片网络加载设置
    ImageOptions imageOptions;

    private TakePhotoHelper PhotoHelper;


    private List<String> mUpdataImageList;//图片
    private UpdataImageEditAdapter UpdataImageAdapter;
    private List<stock_check_image> lsi = new ArrayList<stock_check_image>();

    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_updata, null);
        super.onCreate(savedInstanceState);
        act = getActivity();
        bundle = act.getIntent().getExtras();

        //设置图片加载属性
        imageOptions = new ImageOptions.Builder()
                .setLoadingDrawableId(R.drawable.loading)
                .setFailureDrawableId(R.drawable.loading_failure)
                .setUseMemCache(true)
                .build();


        edit_info = (EditText) view.findViewById(R.id.edit_info);
        edit_info.setText((String) SPUtils.get(define.SharedInfo, ""));


        //设置照片选择
        PhotoHelper = new TakePhotoHelper();

        image1Btn = (ImageView) view.findViewById(R.id.image1Btn);

        image1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SuperDialog superDialog = new SuperDialog(getContext());
                ArrayList<SuperDialog.DialogMenuItem> menuItems = new ArrayList<>();
                menuItems.add(new SuperDialog.DialogMenuItem("拍照"));
                menuItems.add(new SuperDialog.DialogMenuItem("从相册选择"));
                superDialog.setTitle("上传照片")
                        .setListener(new SuperDialog.onDialogClickListener() {
                            @Override
                            public void click(boolean isButtonClick, int position) {
                                if (position == 1) {
                                    PhotoHelper.init(getTakePhoto(), define.SELECT_PHOTO, false, 10, 0, 0);
                                } else if (position == 0) {
                                    PhotoHelper.init(getTakePhoto(), define.TAKE_PHOTO, false, 1, 0, 0);
                                }
                            }
                        })
                        .setDialogMenuItemList(menuItems).show();
                superDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
//                        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                    }
                });

//                List<People> list = new ArrayList<>();
//                list.add(new People(define.TAKE_PHOTO, "拍照"));
//                list.add(new People(define.SELECT_PHOTO, "从相册选择"));
//                new SuperDialog.Builder(act)
//                        //.setAlpha(0.5f)
//                        //.setGravity(Gravity.CENTER)
//                        .setTitle("上传照片", ColorRes.negativeButton)
//                        .setCanceledOnTouchOutside(false)
//                        .setItems(list, new SuperDialog.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(int position) {
//                                if (position == 1) {
//                                    PhotoHelper.init(getTakePhoto(), define.SELECT_PHOTO, false, 10, 0, 0);
//                                } else if (position == 0) {
//                                    PhotoHelper.init(getTakePhoto(), define.TAKE_PHOTO, false, 1, 0, 0);
//                                }
//                            }
//                        })
//                        .setNegativeButton("取消", null)
//                        .setWindowAnimations(R.style.dialogWindowAnim)
//                        .build();
            }
        });

        edit_auditor = (EditText) view.findViewById(R.id.edit_auditor);
        edit_auditor.setText((String) SPUtils.get(define.SharedUser, ""));

        //设置图片Grid
        mUpdataImageList = new ArrayList<>();
        RecyclerView imageRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        imageRecyclerView.setLayoutManager(new LinearLayoutManager(act));// 布局管理器。
        imageRecyclerView.addItemDecoration(new ListViewDecoration(act));// 添加分割线。

        UpdataImageAdapter = new UpdataImageEditAdapter(mUpdataImageList);
        UpdataImageAdapter.setOnItemClickListener(onItemClickListener);
        imageRecyclerView.setAdapter(UpdataImageAdapter);

        submit = (BootstrapButton) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SPUtils.put(define.SharedInfo, edit_info.getText().toString());
                SPUtils.put(define.SharedAuditor, edit_auditor.getText().toString());

                if (mUpdataImageList.size() == 0) {

                    LemonHello.getWarningHello("提示", "请至少上传一张图片")
                            .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                }
                            }))
                            .show(act);
                } else if (edit_info.getText().toString().isEmpty()) {


                    LemonHello.getWarningHello("提示", "请输入情况说明")
                            .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                }
                            }))
                            .show(act);
                } else {
                    String checkId = "0";
                    RequestParams params = new RequestParams(define.SubmitCheck);
                    if (bundle != null) {
                        if (bundle.getString("id") != null) {
                            checkId = bundle.getString("id");
                            params.addQueryStringParameter("id", bundle.getString("id"));
                        }
                    }
                    params.addQueryStringParameter("image1", null);
                    params.addQueryStringParameter("image2", null);
                    params.addQueryStringParameter("info", edit_info.getText().toString());
                    params.addQueryStringParameter("auditor", edit_auditor.getText().toString());

                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(final String result) {
                            //解析result
                            //重新设置数据
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
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
                            });

                        }

                        //请求异常后的回调方法
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Log.e("jindi", ex.toString());

                            LemonHello.getErrorHello("提示", "网络错误，单号提交失败")
                                    .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                                        @Override
                                        public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                            helloView.hide();
                                        }
                                    }))
                                    .show(act);
                        }

                        //主动调用取消请求的回调方法
                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {
                        }
                    });

                    for (int i = 0; i < mUpdataImageList.size(); i++) {
                        stock_check_image sciTemp = new stock_check_image();
                        sciTemp.setCheckId(checkId);
                        sciTemp.setImage(mUpdataImageList.get(i));
                        lsi.add(sciTemp);
                    }
                    RequestParams params2 = new RequestParams(define.UpdataImage);
                    Gson g = new Gson();
                    params2.setAsJsonContent(true);
                    params2.setBodyContent(g.toJson(lsi));
                    x.http().post(params2, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(final String result) {


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
        getImage();
        return view;
    }

    private void getImage() {
        RequestParams params = new RequestParams(define.GetImage);
        if (bundle != null) {
            if (bundle.getString("id") != null) {
                params.addQueryStringParameter("checkId", bundle.getString("id"));
            }
        }
        Log.e("jindi", params.toString());
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
                        UpdataImageAdapter.notifyDataSetChanged();
                    }
                });
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());


                LemonHello.getErrorHello("提示", "网络错误，单号提交失败")
                        .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                            }
                        }))
                        .show(act);
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

    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            // Toast.makeText(act, "我目前是第" + position + "条。", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDeleteClick(int position) {
            mUpdataImageList.remove(position);
            UpdataImageAdapter.notifyItemRemoved(position);
        }
    };

    @Override
    public void takeSuccess(TResult tResult) {
        super.takeSuccess(tResult);
        LOG.e("takeSuccess="+tResult.getImages().size());

        for (int i = 0; i < tResult.getImages().size(); i++) {
            // 拷贝图片,最后通知图库更新
            //复制文件到huayifu目录
            final String fileName = System.currentTimeMillis() + ".jpg";
            final File file = new File(Environment.getExternalStorageDirectory(), "/pictures/" + fileName);
            LOG.e("takeSuccess:" + file.toString());

            act.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
            if (!file.getParentFile().exists()) {
                LOG.e("mkdirs");
                file.getParentFile().mkdirs();
            }
            Utils.copyfile(new File(tResult.getImages().get(i).getCompressPath()), file, true);
            putImage( fileName, file.toString());
        }
    }

    public void putImage( final String fileName, String filePath) {
        LOG.e("提交照片");
        PutObjectRequest put = new PutObjectRequest(define.bucket, fileName, filePath);
        // 异步上传时可以设置进度回调
        put.setProgressCallback((request, currentSize, totalSize) -> LOG.w("currentSize: " + currentSize + " totalSize: " + totalSize));

        OSSHelper.getOSSClient(act, define.OSS_KEY, define.OSS_SECRET)
                .asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LOG.e("UploadSuccess");

                LOG.e("ETag="+result.getETag());
                LOG.e("RequestId="+result.getRequestId());
                mUpdataImageList.add(fileName);

                for (String s : mUpdataImageList) {
                    LOG.e("fileName="+s);
                }
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UpdataImageAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                LOG.e("onFailure="+clientExcepion.getMessage());
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    LOG.e("ErrorCode="+serviceException.getErrorCode());
                    LOG.e("RequestId="+serviceException.getRequestId());
                    LOG.e("HostId="+ serviceException.getHostId());
                    LOG.e("RawMessage="+ serviceException.getRawMessage());

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
