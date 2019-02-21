package com.goldemperor.PropertyRegistration.Phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemperor.MainActivity.define;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
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
                new SweetAlertDialog(mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("警告")
                        .setContentText("确定要入库吗?")
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .setConfirmClickListener(sad -> {
                                    sad.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                                    sad.setTitleText("提交中..").setContentText("");
                                    RegistrationUtils.RegistrationClose(PML.get(Position).getFInterID() + "", (isSuccess, msg) -> {
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

            @Override
            public void PrintClick(int Position) {
                new SweetAlertDialog(mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("警告")
                        .setContentText("确定要打印贴标吗?")
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .setConfirmClickListener(sad -> {
                                    sad.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                                    sad.setTitleText("提交中..").setContentText("");
                                    RegistrationUtils.Print(PML.get(Position).getFInterID() + "", (isSuccess, msg) -> {
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
}
