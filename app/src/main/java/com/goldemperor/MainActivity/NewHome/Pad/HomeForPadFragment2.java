package com.goldemperor.MainActivity.NewHome.Pad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.CCActivity.CCListActivity;
import com.goldemperor.DayWorkCardReport.activity.SCCJLCCLXS_ReportActivity;
import com.goldemperor.GxReport.GxReport;
import com.goldemperor.GylxActivity.GylxActivity;
import com.goldemperor.MainActivity.HelpActivity;
import com.goldemperor.MainActivity.HelpModel;
import com.goldemperor.MainActivity.NewHome.ButtonData;
import com.goldemperor.MainActivity.NewHome.HomeAdapter;
import com.goldemperor.MainActivity.NewHome.HomeUtils;
import com.goldemperor.MainActivity.NewHome.Model.NewLoginModel;
import com.goldemperor.MainActivity.NewHome.NewLogin;
import com.goldemperor.MainActivity.NewHome.NewLoginListener;
import com.goldemperor.MainActivity.ProcessInformationActivity;
import com.goldemperor.MainActivity.UnfinishedReportActivity;
import com.goldemperor.MainActivity.define;
import com.goldemperor.PgdActivity.PgdActivity;
import com.goldemperor.ProcessSend.ProcessSendActvity;
import com.goldemperor.PropertyRegistration.Pad.PropertyRegistrationListForPadActivity;
import com.goldemperor.PropertyRegistration.Phone.PropertyRegistrationListForPhoneActivity;
import com.goldemperor.R;
import com.goldemperor.SetActivity.SetActivity;
import com.goldemperor.StaffWorkStatistics.StaffWorkStatisticsActivity;
import com.goldemperor.StockCheck.StockCheckActivity;
import com.goldemperor.Update.VersionService;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.SuperDialog;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.verticalviewpager.ViewPagerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panzx.pulini.ZProgressHUD;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * File Name : HomeForTVFragment1
 * Created by : PanZX on  2018/9/29 20:57
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：主页1
 */
@ContentView(R.layout.fragment_home2_for_pad)
public class HomeForPadFragment2 extends ViewPagerFragment implements HomeAdapter.HomeItemClickListener, NewLoginListener, HomeUtils.JurisdictionListener {

    @ViewInject(R.id.GV_Home)
    private GridView GV_Home;

    @ViewInject(R.id.TV_NetType)
    private TextView TV_NetType;


    @ViewInject(R.id.TV_Version)
    private TextView TV_Version;

    List<ButtonData> BD = new ArrayList<>();
    private Activity mActivity;
    List<HelpModel> HML = new ArrayList<>();
    private ZProgressHUD mProgressHUD;
    public static NewLogin NL;
    private HomeUtils HU;

    public static NewLogin getNL() {
        return NL;
    }

    private HomeAdapter HA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = x.view().inject(this, inflater, container);
            initview();
        }
        return rootView;
    }

    private void initview() {
        mActivity = getActivity();
        NL = new NewLogin(mActivity, this);
        HU = new HomeUtils(this);
        mProgressHUD = new ZProgressHUD(mActivity);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);

//            BD.add(new ButtonData("成型后段扫码入库", "1050101", R.drawable.btn_saoyisao, new Intent(mActivity, FormingPosteriorActivity.class)));
//            BD.add(new ButtonData("生产扫码入库", "1050201", R.drawable.btn_saoyisao, new Intent(mActivity, ProductionWarehousingActivity.class)));
//            BD.add(new ButtonData("供应商扫码入库", "1050301", R.drawable.btn_saoyisao, new Intent(mActivity, SupplierActivity.class)));
//            BD.add(new ButtonData("生产汇报", "1050501", R.drawable.btn_saoyisao, new Intent(mActivity,ProductionReportActivity.class)));
//            BD.add(new ButtonData("仓库调拨", "1050601", R.drawable.btn_saoyisao, new Intent(mActivity, WarehouseAllocationActivity.class)));
//
        BD.add(new ButtonData("来料稽查", "", R.drawable.btn_material, new Intent(mActivity, StockCheckActivity.class)));
        BD.add(new ButtonData("生产派工", "", R.drawable.btn_order, new Intent(mActivity, PgdActivity.class)));
        BD.add(new ButtonData("工序计划派工", "", R.drawable.btn_set, new Intent(mActivity, ProcessSendActvity.class)));
        BD.add(new ButtonData("工序汇报", "", R.drawable.btn_process, new Intent(mActivity, GxReport.class)));
        BD.add(new ButtonData("工艺路线变更", "401040304", R.drawable.btn_process, new Intent(mActivity, GylxActivity.class)));
//        BD.add(new ButtonData("工序汇报入库", "1050701", R.drawable.btn_process, new Intent(mActivity, ProcessReportInstockActivity.class)));

        BD.add(new ButtonData("品质管理", "", R.drawable.btn_produce, new Intent(mActivity, com.goldemperor.PzActivity.PgdActivity.class)));
        BD.add(new ButtonData("财产条码打印", "", R.drawable.btn_order, new Intent(mActivity, CCListActivity.class)));
        BD.add(new ButtonData("询价单审批", "", R.drawable.btn_process, new Intent(mActivity, com.goldemperor.XJChenk.XJListActivity.class)));
        BD.add(new ButtonData("员工计件明细", "303100101", R.drawable.btn_process, new Intent(mActivity, StaffWorkStatisticsActivity.class)));
        BD.add(new ButtonData("查看指令明细", "1050801", R.drawable.btn_process, new Intent(mActivity, ProcessInformationActivity.class)));
        BD.add(new ButtonData("日未完成达标", "", R.drawable.btn_process, new Intent(mActivity, UnfinishedReportActivity.class)));
//        BD.add(new ButtonData("财产登记", "", R.drawable.btn_process, new Intent(mActivity, PropertyRegistrationListForPhoneActivity.class)));

        BD.add(new ButtonData("常见问题解答", "", R.drawable.btn_order, null));
        BD.add(new ButtonData("报表", "", R.drawable.btn_order, null));
        BD.add(new ButtonData("设置", "", R.drawable.btn_set, new Intent(mActivity, SetActivity.class)));

        HA = new HomeAdapter(getActivity(), BD);
        HA.setOnItemClick(this);

        GV_Home.setAdapter(HA);
        setService();

        TV_Version.setText("当前版本:" + VersionService.getVersionName(mActivity));
    }

    private void setService() {
        if (SPUtils.getServerPath().equals(define.SERVER + define.PORT_8012)) {
            TV_NetType.setText("当前网络:正式库");
        } else if (SPUtils.getServerPath().equals(define.SERVER + define.PORT_8056)) {
            TV_NetType.setText("当前网络:测试库1");
        } else if (SPUtils.getServerPath().equals(define.SERVER_XL + define.PORT_8078)) {
            TV_NetType.setText("当前网络:测试库2");
        }
    }

    private void showdialog() {
        ArrayList<SuperDialog.DialogMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new SuperDialog.DialogMenuItem("生产车间楼层产量实时汇总表"));
//        menuItems.add(new SuperDialog.DialogMenuItem("本组今日产能"));
        menuItems.add(new SuperDialog.DialogMenuItem("其它报表正在制作中..."));
        new SuperDialog(mActivity)
                .setTitle("选择报表类型")
                .setTitleTextSize(30)
                .setContentTextSize(23)
                .setDialogMenuItemList(menuItems)
                .setContentTextGravity(Gravity.CENTER)
                .setListener((isButtonClick, position) -> {
                    switch (position) {
                        case 0:
                            mActivity.startActivity(new Intent(mActivity, SCCJLCCLXS_ReportActivity.class));
                            break;
                        case 1:
//                            mActivity.startActivity(new Intent(mActivity, CapacityActivity.class));
                            break;
                        case 2:
//                                mContext.startActivity(new Intent(mContext, AutographActivity.class));
                            break;
                    }
                }).show();

    }

    private void helpdialog() {
        ArrayList<SuperDialog.DialogMenuItem> helpItems = new ArrayList<>();
        for (HelpModel helpModel : HML) {
            helpItems.add(new SuperDialog.DialogMenuItem(helpModel.getFName()));
        }
        helpItems.add(new SuperDialog.DialogMenuItem("其它常见问题解答页面正在制作中..."));
        new SuperDialog(mActivity).setTitle("常见问题解答选项")
                .setTitleTextSize(30)
                .setContentTextSize(23)
                .setDialogMenuItemList(helpItems)
                .setContentTextGravity(Gravity.CENTER)
                .setListener((isButtonClick, position) -> {
                    if (position == HML.size()) {
                        return;
                    }
                    mActivity.startActivity(new Intent(mActivity, HelpActivity.class).putExtra("URL", HML.get(position).getFHelpUrl()));
                }).show();

    }

    private void getHelp() {
        mProgressHUD.setMessage("获取帮助列表...");
        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpPublicServer,
                define.GetHelpInfoList,
                map, result -> {
                    LOG.e("result=" + result);
                    mProgressHUD.dismiss();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.e("getHelp=" + result);
                            HML = new Gson().fromJson(result, new TypeToken<List<HelpModel>>() {
                            }.getType());
                            if (HML != null || HML.size() > 0) {
                                helpdialog();
                            } else {
                                LemonHello.getErrorHello("提示", "暂无帮助内容")
                                        .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            //当前页面已显示
        } else {
            //当前页面未显示
        }
    }


    @Override
    public void Login(NewLoginModel NLM) {
        setService();
    }

    @Override
    public void Back() {
        setService();
    }


    @Override
    public void click(int position) {
//        if (BD.get(position).getName().equals("工序汇报")) {//直接报工 没有ERP账号 不需要登录
//            mActivity.startActivity(BD.get(position).getIntent());
//        } else
        if ("".equals(SPUtils.get(define.LoginType, ""))) {
            NL.show();
        } else {
            if (BD.get(position).getIntent() != null) {
                HU.CheckJurisdiction(BD.get(position));
            } else {
                if (BD.get(position).getName().equals("常见问题解答")) {
                    getHelp();
                } else if (BD.get(position).getName().equals("报表")) {
                    showdialog();
                }
            }
        }
    }

    @Override
    public void Checked(boolean have, ButtonData BD, String msg) {
        if (have) {
            mActivity.startActivity(BD.getIntent());
        } else {
            LemonHello.getErrorHello("提示", msg)
                    .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
        }
    }

    @Override
    public void Checking() {
        LemonHello.getInformationHello("提示", "正在检查权限，请稍后")
                .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
    }
}