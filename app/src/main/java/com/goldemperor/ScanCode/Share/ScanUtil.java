package com.goldemperor.ScanCode.Share;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;

import com.goldemperor.MainActivity.CodeDataModel;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.ScanCode.Model.D_BarCodeModel;
import com.goldemperor.ScanCode.Model.F_BarCodeModel;
import com.goldemperor.ScanCode.Show.ReportModel;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panzx.pulini.ZProgressHUD;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * File Name : ScanUtil
 * Created by : PanZX on  2018/11/1 09:53
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ScanUtil {

    public static final String ScanReceiverID = "nlscan.action.SCANNER_RESULT";

    public static final String ReturnType_Success = "Success";
    public static final String ReturnType_Error = "Error";
    public static final String ReturnType_Information = "Information";


    public static final String GetBarCodeType_ProcessReportInstock = "FProcessOutputID";//更新数据ID 工序汇报入库
    public static final String GetBarCodeType_ProductionReport = "FPRDMoRptID";//更新数据ID 生产汇报
    public static final String GetBarCodeType_InStockBill = "FInStockBillID";//更新数据ID 扫码入库
    public static final String GetBarCodeType_OutStockBill = "FOutStockBillID";//更新数据ID 领料
    public static final String GetBarCodeType_FCgInStockBillID = "FCgInStockBillID";//更新数据ID 供应商入库


    public static final int BillTypeID_Supplier = 1;// 供应商扫码入库
    public static final int BillTypeID_ProductionWarehousing = 2;// 生产扫码入库
    public static final int BillTypeID_FormingPosterior = 3;// 成型后段扫码入库
    public static final int BillTypeID_ProductionReport = 5;// 生产汇报
    public static final int BillTypeID_WarehouseAllocation = 6;// 仓库调拨单
    public static final int BillTypeID_ProcessReportInstock = 11;// 工序汇报入库

    public static final int BillTypeID_LongCode = 3030756;//获取制程/生产汇报提交

    public static final int TranTypeID_Production_Warehousing = 106;//生产扫码入库
    public static final int TranTypeID_Production_Picking = 107;//生产扫码领料

    public static final int TranTypeID_Supplier_Warehousing = 1;//扫码入库
    public static final int TranTypeID_Supplier_Picking = 24;//生产扫码领料


    public static final int GetEmpCodeRequestCode = 10000;//扫描工号返回码
    public static final int ShowReportRequestCode = 10001;//展示汇总表返回码




    public String EmpID;
    public String OrganizeID;
    public String DefaultStockID;
    public String UserID;
    public String SuitID;
    private Gson mGson;
    private ScanListener SL;
    private ZProgressHUD ZP;
    Activity mActivity;

    public ScanUtil(Activity act, ScanListener sl) {
        mActivity = act;
        SL = sl;
        ZP = new ZProgressHUD(mActivity);
        ZP.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        SuitID = (String)SPUtils.get(define.SharedSuitID, "1");
        UserID = (String)SPUtils.get(define.SharedUserId, "0");
        EmpID = (String)SPUtils.get(define.SharedEmpId, "0");
        DefaultStockID = (String)SPUtils.get(define.SharedDefaultStockID, "1");
        OrganizeID = (String)SPUtils.get(define.SharedFOrganizeid, "1");
        mGson = new Gson();
        Check();

    }

    public void seve(String name, String data) {
        SPUtils.put(name, data);
    }

    public String get(String name, String defValue) {
        return (String)SPUtils.get(name, defValue);
    }

    private void Check() {
        LOG.e("SuitID="+SuitID+"  UserID="+UserID+ "  DefaultStockID="+DefaultStockID+"  OrganizeID="+OrganizeID+"  EmpID="+EmpID);
        StringBuffer SB = new StringBuffer();
        if ("".equals(SuitID)) {
            SB.append("SuitID缺失\n");
        }
        if ("".equals(UserID)) {
            SB.append("UserID缺失\n");
        }
        if ("".equals(DefaultStockID)) {
            SB.append("DefaultStockID缺失\n");
        }
        if ("".equals(OrganizeID)) {
            SB.append("OrganizeID缺失\n");
        }
        if ("".equals(EmpID)) {
            SB.append("EmpID缺失");
        }
        if (SB.length() > 0) {
            SB.append("\n请重新登录！");
            ShowDialog(ScanUtil.ReturnType_Error, "资料缺失", SB.toString());
        }
    }

    public void GetBarCodeStatus(String SearchFieldName) {
        ZP.setMessage("更新数据中...");
        ZP.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("SearchFieldName", SearchFieldName);
        map.put("suitID", SuitID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetBarCodeStatusBysuitID2,
                map, result -> {
                    ZP.dismiss();
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("GetBarCodeStatusBysuitID=" + result);
                            JSONObject jb = new JSONObject(result);
                            String ReturnType = jb.getString("ReturnType");
                            String ReturnMsg = jb.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                List<F_BarCodeModel> BCSML = mGson.fromJson(ReturnMsg, new TypeToken<List<F_BarCodeModel>>() {
                                }.getType());
                                if (BCSML == null) {
                                    BCSML = new ArrayList<>();
                                }
                                LOG.e(BCSML==null?"BCSML=null":"BCSML="+BCSML.size());
                                SL.GetBarCodeStatusResult(true, BCSML);
                            } else {
                                SL.GetBarCodeStatusResult(false, ReturnMsg);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            SL.GetBarCodeStatusResult(false, "数据解码异常");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            SL.GetBarCodeStatusResult(false, "数据解析异常");
                        }
                    } else {
                        SL.GetBarCodeStatusResult(false, "接口无返回");
                    }
                });

    }

    public void GetReport(List<D_BarCodeModel> list, int BillTypeID, boolean IsRed) {
        ZP.setMessage("生成汇报数据...");
        ZP.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("barcodeJson", mGson.toJson(list));
        map.put("OrganizeID", OrganizeID);
        map.put("BillTypeID", BillTypeID + "");
        map.put("DefaultStockID", DefaultStockID);
        map.put("Red", IsRed ? "-1" : "1");
        map.put("UserID", UserID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.WEB_NEWGETSUBMITBARCODEREPORT, map, result -> {
                    ZP.dismiss();
                    LOG.e("提交汇报=" + result);
                    if (result != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                List<ReportModel> RML = mGson.fromJson(ReturnMsg, new TypeToken<List<ReportModel>>() {
                                }.getType());
                                LOG.e("汇总："+RML.size());
                                if (RML != null && RML.size()>0) {
                                    Collections.sort(RML, (o1, o2) -> o1.getRowIndex()-o2.getRowIndex());
                                    SL.GetReport(true, mGson.toJson(RML));
                                } else {
                                    SL.GetReport(false, ReturnMsg);
                                }
                            } else {
                                SL.GetReport(false, ReturnMsg);
                            }
                        } catch (JSONException e) {
                            SL.GetReport(false, "数据解析异常");
                            e.printStackTrace();
                        }
                    } else {
                        SL.GetReport(false, "接口访问异常");
                    }
                });
    }

    public void Add(List<CodeDataModel> CDM,List<F_BarCodeModel> FBCM, String code) {
        List<CodeDataModel> data=new ArrayList<>(CDM);
        boolean ishave = false;
        boolean isUsed = false;
        LOG.e("code="+code);
        if (code.equals("")) {
            return;
        }

        if(FBCM!=null){
            for (F_BarCodeModel f_barCodeModel : FBCM) {
                if (f_barCodeModel.getFBarCode().equals(code)) {
                    isUsed = true;
                }
            }
        }
        if(data.size()==0){
            data.add(new CodeDataModel(code,isUsed));
        }else{
            for (CodeDataModel codeDataModel : data) {
                if (codeDataModel.getCode().equals(code)) {
                    ishave = true;
                }
            }
            LOG.e("ishave="+ishave);
            if (!ishave) {
                data.add(new CodeDataModel(code,isUsed));
                Collections.sort(data, (o1, o2) -> o1.getCode().compareTo(o2.getCode()));
            } else {
                data.clear();
            }
        }
        SL.AddItem(data, code);

    }

    public void Clear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("确定要清空数据吗");
        builder.setTitle("清空");
        builder.setPositiveButton("确认", (dialog, which) -> {
            SL.ClearItem();
            dialog.dismiss();
        }).setNegativeButton("取消", null).show();
    }

    public SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu1, int i) {
            SwipeMenuItem addItem = new SwipeMenuItem(mActivity)
                    .setBackgroundColor(Color.parseColor("#882222"))
//                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setTextSize(15)
                    .setWidth(Utils.dp2px(60))
                    .setHeight(Utils.dp2px(40));
            swipeMenu1.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
        }
    };


    public SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {
                    SL.DeleteItem(adapterPosition);
                }
            }
        }
    };

    public void ShowDialog(String ReturnType, String Title, String MSG) {
        LOG.e("ShowDialog:\nReturnType= " + ReturnType + "  \nMSG=" + MSG);
        if (ReturnType_Success.equals(ReturnType)) {
            LemonHello.getSuccessHello(Title, MSG)
                    .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
        } else if (ReturnType_Error.equals(ReturnType)) {
            LemonHello.getErrorHello(Title, MSG)
                    .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
        } else if (ReturnType_Information.equals(ReturnType)) {
            LemonHello.getInformationHello(Title, MSG)
                    .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
        }
    }


}
