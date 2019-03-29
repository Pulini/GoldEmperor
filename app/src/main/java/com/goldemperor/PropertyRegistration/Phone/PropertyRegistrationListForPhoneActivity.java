package com.goldemperor.PropertyRegistration.Phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldemperor.MainActivity.define;
import com.goldemperor.PropertyRegistration.BluetoothUtils;
import com.goldemperor.PropertyRegistration.OnItemClickListener;
import com.goldemperor.PropertyRegistration.PropertyModel;
import com.goldemperor.PropertyRegistration.RegistrationUtils;
import com.goldemperor.R;
import com.goldemperor.Utils.ClickProxy;
import com.goldemperor.Utils.IClickListener;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panzx.pulini.FancyButtons.FancyButton;
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

    List<PropertyModel> PML = new ArrayList<>();//原始数据
    List<PropertyModel> PML2 = new ArrayList<>();//筛选数据

    @ViewInject(R.id.SRL_PropertyRegistrationList)
    SmartRefreshLayout SRL_PropertyRegistrationList;

    @ViewInject(R.id.RV_PropertyRegistrationList)
    RecyclerView RV_PropertyRegistrationList;

    @ViewInject(R.id.TV_NoData)
    TextView TV_NoData;

    @ViewInject(R.id.IV_Back)
    ImageView IV_Back;

    @ViewInject(R.id.IV_Search)
    ImageView IV_Search;

    @ViewInject(R.id.FB_Unaudited)
    FancyButton FB_Unaudited;

    @ViewInject(R.id.FB_Audited)
    FancyButton FB_Audited;

    @ViewInject(R.id.FB_Auditing)
    FancyButton FB_Auditing;

    @ViewInject(R.id.RL_Print)
    private RelativeLayout RL_Print;

    @ViewInject(R.id.IV_BKG)
    private ImageView IV_BKG;

    @ViewInject(R.id.LL_BKG)
    private LinearLayout LL_BKG;

    @ViewInject(R.id.TV_Name)
    private TextView TV_Name;

    @ViewInject(R.id.TV_MSG)
    private TextView TV_MSG;

    @ViewInject(R.id.RB_Big)
    private RadioButton RB_Big;

    @ViewInject(R.id.RB_Small)
    private RadioButton RB_Small;

    @ViewInject(R.id.FB_Cancel)
    private FancyButton FB_Cancel;

    @ViewInject(R.id.FB_Print)
    private FancyButton FB_Print;


    private Activity mActivity;
    private PropertyRegistrationListForPhoneAdapter PRLA;
    private Typeface typeface;
    private PropertyModel PM;
    private final int Print = 123;
    private String Audit = "未审核";//1未审核 2审核中 3已审核

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Print:
                    RegistrationUtils.Print(PM.getFInterID() + "", (isSuccess, msg1) -> {
                        ActionHidden();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        SRL_PropertyRegistrationList.autoRefresh();
    }

    private void initview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RV_PropertyRegistrationList.setLayoutManager(layoutManager);
        PRLA = new PropertyRegistrationListForPhoneAdapter(PML2);
        RV_PropertyRegistrationList.setAdapter(PRLA);
        SRL_PropertyRegistrationList.setOnRefreshListener(refreshLayout -> getDataList());
        SRL_PropertyRegistrationList.setEnableLoadMore(false);
        IV_Back.setOnClickListener(v -> onBackPressed());
        IV_Search.setOnClickListener(new ClickProxy(v -> startActivity(new Intent(mActivity, SearchActivity.class))));
        FB_Unaudited.setOnClickListener(v -> selectAudit("未审核"));
        FB_Audited.setOnClickListener(v -> selectAudit("已审核"));
        FB_Auditing.setOnClickListener(v -> selectAudit("审核中"));
        IV_BKG.setOnClickListener(v -> ActionHidden());
        FB_Cancel.setOnClickListener(v -> ActionHidden());
        FB_Print.setOnClickListener(new ClickProxy(v -> Prints()));

        PRLA.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void ItemClick(int Position) {
                startActivity(new Intent(mActivity, PropertyRegistrationDetailsForPhoneActivity.class).putExtra("InterID", PML2.get(Position).getFInterID()));
            }

            @Override
            public void CloseClick(int Position) {
                Close(PML2.get(Position).getFInterID() + "");
            }

            @Override
            public void PrintClick(int Position) {
                PrintLabel(PML2.get(Position));
            }
        });
    }

    private void selectAudit(String Type) {
        FB_Unaudited.setBackgroundColor(Color.parseColor("#00ACFE"));
        FB_Auditing.setBackgroundColor(Color.parseColor("#00ACFE"));
        FB_Audited.setBackgroundColor(Color.parseColor("#00ACFE"));
        if (Type.equals("未审核")) {
            FB_Unaudited.setBackgroundColor(Color.parseColor("#FF6969"));
        } else if (Type.equals("审核中")) {
            FB_Auditing.setBackgroundColor(Color.parseColor("#FF6969"));
        } else if (Type.equals("已审核")) {
            FB_Audited.setBackgroundColor(Color.parseColor("#FF6969"));
        }
        Audit = Type;
        PML2.clear();
        for (PropertyModel pm : PML) {
            if (pm.getFProcessStatus().equals(Audit)) {
                PML2.add(pm);
            }
        }
        if (PML2.size() == 0) {
            TV_NoData.setVisibility(View.VISIBLE);
            TV_NoData.setText("暂无[" + Audit + "]数据");
        } else {
            TV_NoData.setVisibility(View.GONE);
            TV_NoData.setText("无数据");
        }
        PRLA.Updata(PML2);
    }

    private void getDataList() {
        IV_Search.setEnabled(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("EmpID", (String) SPUtils.get(define.SharedEmpId, "0"));
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidSever,
                define.GetUnnumberedPropertyByEmpID,
                map, result -> {
                    IV_Search.setEnabled(true);
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
                                selectAudit(Audit);
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
        ActionShow();
        String st = BluetoothUtils.EnableBlueTooth();
        if (st.equals("")) {
            TV_MSG.setText("请选择打印标签尺寸。");
            TV_Name.setText("打印设备【" + PM.getFName() + "】");
        } else {
            TV_MSG.setText("打开蓝牙失败，请检查蓝牙。");
        }
    }

    private void Prints() {
        TV_MSG.setText("下发打印指令中,请稍后...");
        BluetoothUtils.Print(
                typeface,
                PM.getFInterID() + "",
                PM.getFName(),
                PM.getFNumber(),
                PM.getFbuyDate().substring(0, 10),
                RB_Big.isChecked() ? 1 : 0,
                (isSend, msg) -> {
                    if (isSend) {
                        mHandler.sendEmptyMessage(Print);
                    } else {
                        new SweetAlertDialog(mActivity, SweetAlertDialog.ERROR_TYPE).setTitleText("失败").setContentText(msg).show();
                    }
                });

    }

    /**
     * 弹出动画
     *
     * @return
     */
    public void ActionShow() {
        RL_Print.setVisibility(View.VISIBLE);

        TranslateAnimation Action1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        Action1.setDuration(300);

        AlphaAnimation Action2 = new AlphaAnimation(0, 1);//透明度从0~1
        Action2.setDuration(300);
        Action2.setFillAfter(true);

        LL_BKG.startAnimation(Action1);
        IV_BKG.startAnimation(Action2);

    }

    /**
     * 隐藏动画
     *
     * @return
     */
    public void ActionHidden() {
        new Handler().postDelayed(() -> RL_Print.setVisibility(View.GONE), 300);

        TranslateAnimation Action1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        Action1.setDuration(300);

        AlphaAnimation Action2 = new AlphaAnimation(1, 0);//透明度从0~1
        Action2.setDuration(300);
        Action2.setFillAfter(true);
        LL_BKG.startAnimation(Action1);
        IV_BKG.startAnimation(Action2);
    }

    @Override
    public void onBackPressed() {
        if (RL_Print.getVisibility() == View.VISIBLE) {
            ActionHidden();
        } else {
            super.onBackPressed();
        }
    }
}
