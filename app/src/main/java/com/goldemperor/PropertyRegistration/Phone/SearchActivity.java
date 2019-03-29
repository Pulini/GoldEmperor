package com.goldemperor.PropertyRegistration.Phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.PropertyRegistration.BluetoothUtils;
import com.goldemperor.PropertyRegistration.OnItemClickListener;
import com.goldemperor.PropertyRegistration.PropertyModel;
import com.goldemperor.PropertyRegistration.RegistrationUtils;
import com.goldemperor.R;
import com.goldemperor.Utils.ClickProxy;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panzx.pulini.ClearWriteEditText.ClearWriteEditText;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * File Name : SearchActivity
 * Created by : PanZX on  2019/3/7 0007
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：查询
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends Activity {

    @ViewInject(R.id.IV_Back)
    private ImageView IV_Back;

    @ViewInject(R.id.LL_ShowCriteria)
    private LinearLayout LL_ShowCriteria;

    @ViewInject(R.id.IV_Arrow)
    private ImageView IV_Arrow;

    @ViewInject(R.id.SRL_Search)
    SmartRefreshLayout SRL_Search;

    @ViewInject(R.id.RV_Search)
    RecyclerView RV_Search;

    @ViewInject(R.id.TV_CriteriaMSG)
    private TextView TV_CriteriaMSG;

    @ViewInject(R.id.RL_QueryCriteria)
    private RelativeLayout RL_QueryCriteria;

    @ViewInject(R.id.IV_CriteriaBKG)
    private ImageView IV_CriteriaBKG;

    @ViewInject(R.id.LL_CriteriaBKG)
    private LinearLayout LL_CriteriaBKG;

    @ViewInject(R.id.CWET_PropertyNumber)
    private ClearWriteEditText CWET_PropertyNumber;

    @ViewInject(R.id.CWET_PropertyName)
    private ClearWriteEditText CWET_PropertyName;

    @ViewInject(R.id.CWET_EmpName)
    private ClearWriteEditText CWET_EmpName;

    @ViewInject(R.id.CWET_EmpCode)
    private ClearWriteEditText CWET_EmpCode;

    @ViewInject(R.id.TV_StartTime)
    private TextView TV_StartTime;

    @ViewInject(R.id.FB_StartTime)
    private FancyButton FB_StartTime;

    @ViewInject(R.id.TV_EndTime)
    private TextView TV_EndTime;

    @ViewInject(R.id.FB_EndTime)
    private FancyButton FB_EndTime;

    @ViewInject(R.id.FB_Clear)
    private FancyButton FB_Clear;

    @ViewInject(R.id.FB_Search)
    private FancyButton FB_Search;

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

    private TimePickerView tpvStart;
    private TimePickerView tpvEnd;

    private PropertyRegistrationListForPhoneAdapter PRLA;
    private List<PropertyModel> PML = new ArrayList<>();
    private String StartDate;
    private String EndDate;
    private SearchActivity mActivity;

    private Typeface typeface;
    private PropertyModel PM;
    private final int Print = 123;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Print:
                    RegistrationUtils.Print(PM.getFInterID() + "", (isSuccess, msg1) -> {
                        PrintActionHidden();
                        if (isSuccess) {
                            Search();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        mActivity=this;
        typeface = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
        initview();
        setListener();
    }

    private void initview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RV_Search.setLayoutManager(layoutManager);
        PRLA = new PropertyRegistrationListForPhoneAdapter(PML);
        RV_Search.setAdapter(PRLA);
        SRL_Search.setOnRefreshListener(refreshLayout -> Search());
        SRL_Search.setEnableLoadMore(false);
        StartDate = "选择起始时间";
        EndDate = "选择结束时间";
        TV_StartTime.setText(StartDate);
        TV_EndTime.setText(EndDate);
    }

    private void setListener() {

        tpvStart = new TimePickerBuilder(this, (date, v) -> {
            StartDate = Utils.getTime(date, "yyyy-MM-dd");
            TV_StartTime.setText(StartDate);
        }).setType(new boolean[]{true, true, true, false, false, false}).build();

        tpvEnd = new TimePickerBuilder(this, (date, v) -> {
            EndDate = Utils.getTime(date, "yyyy-MM-dd");
            TV_EndTime.setText(EndDate);
        }).setType(new boolean[]{true, true, true, false, false, false}).build();

        IV_Back.setOnClickListener(v -> onBackPressed());
        LL_ShowCriteria.setOnClickListener(v -> ShowCriteria());
        IV_CriteriaBKG.setOnClickListener(v -> ShowCriteria());
        FB_StartTime.setOnClickListener(v -> tpvStart.show());
        FB_EndTime.setOnClickListener(v -> tpvEnd.show());
        FB_Clear.setOnClickListener(v -> Clear());
        FB_Search.setOnClickListener(new ClickProxy(v -> Search()));

        IV_BKG.setOnClickListener(v->PrintActionHidden());
        FB_Cancel.setOnClickListener(v->PrintActionHidden());
        FB_Print.setOnClickListener(new ClickProxy(v->Prints()));

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
                                    Search();
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
        PrintActionShow();
        String st = BluetoothUtils.EnableBlueTooth();
        if (st.equals("")) {
            TV_MSG.setText("请选择打印标签尺寸。");
            TV_Name.setText("打印设备【"+PM.getFName()+"】");
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
                RB_Big.isChecked()?1:0,
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
    public void PrintActionShow() {
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
    public void PrintActionHidden() {
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



    private void ShowCriteria() {
        if (RL_QueryCriteria.getVisibility() == View.VISIBLE) {
            ActionHidden();
        } else {
            ActionShow();
        }
    }

    private void Clear() {
        StartDate = "选择起始时间";
        EndDate = "选择结束时间";
        TV_StartTime.setText(StartDate);
        TV_EndTime.setText(EndDate);
        CWET_PropertyNumber.setText("");
        CWET_PropertyName.setText("");
        CWET_EmpName.setText("");
        CWET_EmpCode.setText("");

    }

    /**
     * 弹出动画
     *
     * @return
     */
    public void ActionShow() {
        RL_QueryCriteria.setVisibility(View.VISIBLE);
        RotateAnimation Action1 = new RotateAnimation(
                0,
                180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        Action1.setInterpolator(new LinearInterpolator());
        Action1.setDuration(300);
        Action1.setFillAfter(true);//动画执行完后是否停留在执行完的状态;

        TranslateAnimation Action2 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        Action2.setDuration(300);

        AlphaAnimation Action3 = new AlphaAnimation(0, 1);//透明度从0~1
        Action3.setDuration(300);
        Action3.setFillAfter(true);

        IV_Arrow.startAnimation(Action1);
        LL_CriteriaBKG.startAnimation(Action2);
        IV_CriteriaBKG.startAnimation(Action3);

    }

    /**
     * 隐藏动画
     *
     * @return
     */
    public void ActionHidden() {
        new Handler().postDelayed(() -> RL_QueryCriteria.setVisibility(View.GONE), 300);

        RotateAnimation Action1 = new RotateAnimation(
                180,
                0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        Action1.setInterpolator(new LinearInterpolator());
        Action1.setDuration(300);
        Action1.setFillAfter(true);//动画执行完后是否停留在执行完的状态;

        TranslateAnimation Action2 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        Action2.setDuration(300);

        AlphaAnimation Action3 = new AlphaAnimation(1, 0);//透明度从0~1
        Action3.setDuration(300);
        Action3.setFillAfter(true);

        IV_Arrow.startAnimation(Action1);
        LL_CriteriaBKG.startAnimation(Action2);
        IV_CriteriaBKG.startAnimation(Action3);

    }

    private boolean Check() {
        boolean textNull = false;
        boolean timeNull = false;
        boolean timeError = false;
        String PropertyNumber = CWET_PropertyNumber.getText().toString();
        String PropertyName = CWET_PropertyName.getText().toString();
        String EmpName = CWET_EmpName.getText().toString();
        String EmpCode = CWET_EmpCode.getText().toString();
        if (PropertyNumber.equals("") && PropertyName.equals("") && EmpName.equals("") && EmpCode.equals("")) {
            textNull = true;
        }
        if (!StartDate.equals("选择起始时间") && !EndDate.equals("选择结束时间")) {
            if (!CompareDate(StartDate, EndDate)) {
                timeError = true;
            }
        } else {
            timeNull = true;
        }

        if (textNull && timeNull) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("错误").setContentText("搜索条件不足").setConfirmText("确定").show();
            return false;
        }else{
            if (!timeNull&&timeError) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("错误").setContentText("起始时间晚于结束时间").setConfirmText("确定").show();
                return false;
            }
        }

        return true;

    }

    private void Search() {
        if (!Check()) {
            return;
        }
        ActionHidden();
        PML.clear();
        PRLA.Updata(PML);
        TV_CriteriaMSG.setText("查询中...");
        TV_CriteriaMSG.setVisibility(View.VISIBLE);
        String PropertyNumber = CWET_PropertyNumber.getText().toString();
        String PropertyName = CWET_PropertyName.getText().toString();
        String EmpName = CWET_EmpName.getText().toString();
        String EmpCode = CWET_EmpCode.getText().toString();

        HashMap<String, String> map = new HashMap<>();
        map.put("PropertyNumber", PropertyNumber);
        map.put("PropertyName", PropertyName);
        map.put("EmpName", EmpName);
        map.put("EmpCode", EmpCode);
        map.put("StartDate", StartDate.equals("选择起始时间")?"":StartDate);
        map.put("EndDate",  EndDate.equals("选择结束时间")?"":EndDate);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidSever,
                define.SearchUnnumberedProperty,
                map, result -> {
                    SRL_Search.finishRefresh();
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
                                TV_CriteriaMSG.setVisibility(View.GONE);
                            } else {
                                TV_CriteriaMSG.setText(ReturnMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            TV_CriteriaMSG.setText("数据解析异常");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            TV_CriteriaMSG.setText("数据转码异常");
                        }
                    }
                });
    }

    public static boolean CompareDate(String StartDate, String EndDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date SD = df.parse(StartDate);
            Date ED = df.parse(EndDate);
            if (SD.getTime() > ED.getTime()) {
                return false;
            } else if (SD.getTime() < ED.getTime()) {
                return true;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(RL_QueryCriteria.getVisibility()==View.VISIBLE){
            ActionHidden();
        }else if(RL_Print.getVisibility()==View.VISIBLE){
            PrintActionHidden();
        }else{
            super.onBackPressed();
        }
    }
}
