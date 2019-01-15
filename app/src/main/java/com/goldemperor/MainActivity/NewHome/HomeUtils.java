package com.goldemperor.MainActivity.NewHome;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.goldemperor.GylxActivity.GylxActivity;
import com.goldemperor.MainActivity.NetworkHelper;
import com.goldemperor.MainActivity.ProcessInformationActivity;
import com.goldemperor.MainActivity.define;
import com.goldemperor.ScanCode.FormingPosterior.FormingPosteriorActivity;
import com.goldemperor.ScanCode.ProcessReportInstock.ProcessReportInstockActivity;
import com.goldemperor.ScanCode.ProductionReport.ProductionReportActivity;
import com.goldemperor.ScanCode.ProductionWarehousing.ProductionWarehousingActivity;
import com.goldemperor.ScanCode.Supplier.SupplierActivity;
import com.goldemperor.ScanCode.WarehouseAllocation.WarehouseAllocationActivity;
import com.goldemperor.StaffWorkStatistics.StaffWorkStatisticsActivity;
import com.goldemperor.Update.CheckVersionTask;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * File Name : HomeUtils
 * Created by : PanZX on  2018/11/15 14:34
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class HomeUtils {
    public boolean IsChecking = false;
    private JurisdictionListener JL;

    public interface JurisdictionListener {
        void Checked(boolean have, ButtonData BD, String msg);

        void Checking();
    }

    public static final int PERMISSION = 12345;
    public static final String[] perms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    };

    public HomeUtils(JurisdictionListener jl) {
        JL = jl;

    }

    public static void UpdataAPK(Activity mActivity) {
        if (NetworkHelper.isNetworkAvailable(mActivity)) {
            new Thread() {
                @Override
                public void run() {
                    new CheckVersionTask(mActivity).run();
                }
            }.start();
        }
    }


    public void CheckJurisdiction(ButtonData BD) {
        if (IsChecking) {
            JL.Checking();
            return;
        }
        if (BD.getJurisdiction().equals("")) {
            JL.Checked(true, BD, "无需权限");
            return;
        }
        IsChecking = true;
        HashMap<String, String> map = new HashMap<>();
        map.put("OrganizeID", "1");
        map.put("empID", (String) SPUtils.get(define.SharedEmpId, ""));
        map.put("controlID", BD.getJurisdiction());
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(SPUtils.getServerPath() + define.ErpPublicServer,
                define.IsHaveControl2, map, result -> {
                    IsChecking = false;
                    if (result != null) {
                        LOG.E("IsHaveControl=" + result);
                        if ("true".equals(result)) {
                            JL.Checked(true, BD, "有权限");
                        } else {
                            JL.Checked(false, BD, "无权限");
                        }
                    } else {
                        JL.Checked(false, BD, "网络异常，检查失败");
                    }
                });

    }

}
