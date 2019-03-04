package com.goldemperor.PropertyRegistration.Phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemperor.MainActivity.define;
import com.goldemperor.PropertyRegistration.BluetoothUtils;
import com.goldemperor.PropertyRegistration.OnItemClickListener;
import com.goldemperor.PropertyRegistration.PropertyModel;
import com.goldemperor.PropertyRegistration.RegistrationUtils;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panzx.pulini.SweetAlert.SweetAlertDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * File Name : PropertyRegistrationListForPadActivity
 * Created by : PanZX on  2019/1/17 15:46
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：财产列表
 */
@ContentView(R.layout.activity_property_registration_list_for_phone)
public class PropertyRegistrationListForPhoneActivity extends Activity {
    List<PropertyModel> PML = new ArrayList<>();

    @ViewInject(R.id.SRL_PropertyRegistrationList)
    SmartRefreshLayout SRL_PropertyRegistrationList;

    @ViewInject(R.id.RV_PropertyRegistrationList)
    RecyclerView RV_PropertyRegistrationList;

    @ViewInject(R.id.TV_NoData)
    TextView TV_NoData;
    @ViewInject(R.id.IV_Back)
    ImageView IV_Back;


    private Activity mActivity;
    private PropertyRegistrationListForPhoneAdapter PRLA;
    private Typeface typeface;
    private PropertyModel PM;
    private final int Print = 123;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Print:
                    RegistrationUtils.Print(PM.getFInterID() + "", (isSuccess, msg1) -> {
                        if (isSuccess) {
                            getDataList();
                            new SweetAlertDialog(mActivity, SweetAlertDialog.SUCCESS_TYPE).setTitleText("成功").setContentText(msg1).show();
                        } else {
                            new SweetAlertDialog(mActivity, SweetAlertDialog.ERROR_TYPE).setTitleText("失败").setContentText(msg1).show();
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
        mActivity = this;
        initview();
        SRL_PropertyRegistrationList.autoRefresh();
    }

    private void initview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RV_PropertyRegistrationList.setLayoutManager(layoutManager);
        PRLA = new PropertyRegistrationListForPhoneAdapter(PML);
        RV_PropertyRegistrationList.setAdapter(PRLA);
        SRL_PropertyRegistrationList.setOnRefreshListener(refreshLayout -> getDataList());
        SRL_PropertyRegistrationList.setEnableLoadMore(false);
        IV_Back.setOnClickListener(v -> finish());
        PRLA.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void ItemClick(int Position) {
                startActivity(new Intent(mActivity, PropertyRegistrationDetailsForPhoneActivity.class).putExtra("InterID", PML.get(Position).getFInterID()));
            }

            @Override
            public void CloseClick(int Position) {
                Close(PML.get(Position).getFInterID() + "");
            }

            @Override
            public void PrintClick(int Position) {
                PrintLabel(PML.get(Position));
            }
        });
    }


    private void getDataList() {
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAppServer,
                define.GetUnnumberedProperty,
                new HashMap<>(), result -> {
                    SRL_PropertyRegistrationList.finishRefresh();
                    TV_NoData.setVisibility(View.VISIBLE);
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("财产列表" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                PML = new Gson().fromJson(ReturnMsg, new TypeToken<List<PropertyModel>>() {
                                }.getType());
                                PRLA.Updata(PML);
                                TV_NoData.setVisibility(View.GONE);
                            } else {
                                TV_NoData.setText(ReturnMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            TV_NoData.setText("数据解析异常");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            TV_NoData.setText("数据转码异常");
                        }
                    }
                });
    }

    private void Close(String ID) {
        new SweetAlertDialog(mActivity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("警告")
                .setContentText("确定要入库吗?")
                .setCancelText("取消")
                .setConfirmText("确定")
                .setConfirmClickListener(sad -> {
                            sad.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                            sad.setTitleText("提交中..").setContentText("");
                            RegistrationUtils.RegistrationClose(ID, (isSuccess, msg) -> {
                                if (isSuccess) {
                                    getDataList();
                                    sad.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    sad.setTitleText("成功")
                                            .setContentText(msg)
                                            .showCancelButton(false)
                                            .setConfirmClickListener(null);
                                } else {
                                    sad.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    sad.setTitleText("失败")
                                            .setContentText(msg)
                                            .showCancelButton(false)
                                            .setConfirmClickListener(null);
                                }
                            });
                        }
                ).show();
    }

    private void PrintLabel(PropertyModel pm) {
        PM = pm;
        String st = BluetoothUtils.EnableBlueTooth();
        if (st.equals("")) {
            new SweetAlertDialog(mActivity, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("警告")
                    .setContentText("确定要打印贴标吗?")
                    .setCancelText("取消")
                    .setConfirmText("确定")
                    .setConfirmClickListener(sad ->
                            sad.setTitleText("选择标签大小")
                                    .setContentText("小标签：75 X 45\r\n大标签：90 X 50")
                                    .setConfirmText("小标签")
                                    .setConfirmClickListener(sad1 -> Prints(sad1, 0))
                                    .setCancelText("大标签")
                                    .setCancelClickListener(sad1 -> Prints(sad1, 1))
                    ).show();
        } else {
            new SweetAlertDialog(mActivity, SweetAlertDialog.ERROR_TYPE).setTitleText("打开蓝牙失败").setContentText(st).setConfirmText("确定").show();
        }
    }

    private void Prints(SweetAlertDialog sad, int type) {
        sad.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        sad.setTitleText("下发打印中...")
                .showCancelButton(false)
                .showConfirmButton(false)
                .setContentText("");
        BluetoothUtils.Print(
                typeface,
                PM.getFInterID() + "",
                PM.getFName(),
                PM.getFNumber(),
                PM.getFbuyDate(),
                type,
                (isSend, msg) -> {
                    sad.dismiss();
                    if (isSend) {
                        mHandler.sendEmptyMessage(Print);
                    } else {
                        new SweetAlertDialog(mActivity, SweetAlertDialog.ERROR_TYPE).setTitleText("失败").setContentText(msg).show();
                    }
                });

    }

}
