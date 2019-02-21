package com.goldemperor.PropertyRegistration;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.goldemperor.MainActivity.define;
import com.goldemperor.PgdActivity.Emp;
import com.goldemperor.R;
import com.goldemperor.ScanCode.Share.ScanUtil;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panzx.pulini.ZProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * File Name : RegistrationUtils
 * Created by : PanZX on  2019/1/22 14:37
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class RegistrationUtils {
    private final ZProgressHUD mProgressHUD;
    private Activity mActivity;

    public RegistrationUtils(Activity act) {
        mActivity = act;
        mProgressHUD = new ZProgressHUD(mActivity);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    public void ShowLoading(String msg) {
        mProgressHUD.setMessage(msg);
        mProgressHUD.show();
    }

    public void DismissLoading() {
        mProgressHUD.dismiss();
    }

    public void ShowDialog(String ReturnType, String Title, String MSG) {
        LOG.e("ShowDialog:\nReturnType= " + ReturnType + "  \nMSG=" + MSG);
        if (ScanUtil.ReturnType_Success.equals(ReturnType)) {
            LemonHello.getSuccessHello(Title, MSG)
                    .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
        } else if (ScanUtil.ReturnType_Error.equals(ReturnType)) {
            LemonHello.getErrorHello(Title, MSG)
                    .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
        } else if (ScanUtil.ReturnType_Information.equals(ReturnType)) {
            LemonHello.getInformationHello(Title, MSG)
                    .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide())).show(mActivity);
        }
    }


    public interface CheckEmp {
        void UserData(Emp e);
    }

    public interface CheckCustodianEmp {
        void CustodianEmp(EmpModel e);
    }

    public static class MyTextListener implements TextWatcher {

        private EditText ET;
        private CheckEmp OTL;

        public MyTextListener(EditText et, CheckEmp otl) {
            OTL = otl;
            ET = et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() >= 6 && ET.hasFocus()) {
                getUser(s.toString(), OTL);
            }
        }
    }

    public static class CustodianTextListener implements TextWatcher {

        private CheckCustodianEmp OTL;
        private EditText ET;

        public CustodianTextListener(EditText et, CheckCustodianEmp otl) {
            OTL = otl;
            ET = et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() >= 6 && ET.hasFocus()) {
                GetLiableByEmpCode(s.toString(), OTL);
            }
        }
    }

    public static Bitmap Path2Bitmap(String Path) {
        return BitmapFactory.decodeFile(Path, getBitmapOption(2));
    }

    private  static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    public static class MyAdapterForPad extends BaseAdapter {
        List<PropertyDetailsModel.list> pdml;

        public MyAdapterForPad(List<PropertyDetailsModel.list> list) {
            pdml = new ArrayList<>(list);
        }

        @Override
        public int getCount() {
            if (pdml == null) {
                return 0;
            }
            return pdml.size();
        }

        @Override
        public Object getItem(int position) {
            return pdml.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PropertyDetailsModel.list pdm = pdml.get(position);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property_registraion_details_list_for_pad, null);
            ((TextView) view.findViewById(R.id.TV_Name)).setText(pdm.getFName());
            ((TextView) view.findViewById(R.id.TV_Qty)).setText(pdm.getFQty() + "");
            ((TextView) view.findViewById(R.id.TV_Price)).setText(pdm.getFPrice() + "");
            ((TextView) view.findViewById(R.id.TV_Amount)).setText(pdm.getFAmount() + "");
            ((TextView) view.findViewById(R.id.TV_Place)).setText(pdm.getFPlace());
            ((TextView) view.findViewById(R.id.TV_Notes)).setText(pdm.getFNotes());
            return view;
        }
    }

    public static class MyAdapterForPhone extends BaseAdapter {
        List<PropertyDetailsModel.list> pdml;

        public MyAdapterForPhone(List<PropertyDetailsModel.list> list) {
            pdml = new ArrayList<>(list);
        }

        @Override
        public int getCount() {
            if (pdml == null) {
                return 0;
            }
            return pdml.size();
        }

        @Override
        public Object getItem(int position) {
            return pdml.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PropertyDetailsModel.list pdm = pdml.get(position);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property_registraion_details_list_for_phone, null);
            if (position == 0 || position % 2 == 0) {
                (view.findViewById(R.id.LL_BKG)).setBackgroundColor(Color.parseColor("#bbbbbb"));
            } else {
                (view.findViewById(R.id.LL_BKG)).setBackgroundColor(Color.parseColor("#ffffff"));
            }
            ((TextView) view.findViewById(R.id.TV_Name)).setText(pdm.getFName());
            ((TextView) view.findViewById(R.id.TV_Qty)).setText(pdm.getFQty() + "");
            ((TextView) view.findViewById(R.id.TV_Price)).setText(pdm.getFPrice() + "");
            ((TextView) view.findViewById(R.id.TV_Amount)).setText(pdm.getFAmount() + "");
            ((TextView) view.findViewById(R.id.TV_Place)).setText(pdm.getFPlace());
            ((TextView) view.findViewById(R.id.TV_Notes)).setText(pdm.getFNotes());
            return view;
        }
    }


    private static void getUser(String FNumber, CheckEmp CEMP) {
        HashMap<String, String> map = new HashMap<>();
        map.put("FDeptmentID", "");
        map.put("FEmpNumber", FNumber);
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetEmpByFnumber, map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            Gson g = new Gson();
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if (ReturnType.equals("success")) {
                                List<Emp> user = g.fromJson(ReturnMsg, new TypeToken<List<Emp>>() {
                                }.getType());
                                CEMP.UserData(user.get(0));
                            } else {
                                CEMP.UserData(null);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        CEMP.UserData(null);
                    }
                });
    }

    private static void GetLiableByEmpCode(String EmpCode, CheckCustodianEmp cce) {
        HashMap<String, String> map = new HashMap<>();
        map.put("EmpCode", EmpCode);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpPublicServer,
                define.GetEmpAndLiableByEmpCode, map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.e("getUser:" + result);
                            Gson g = new Gson();
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if (ReturnType.equals("success")) {
                                EmpModel EM = g.fromJson(ReturnMsg, new TypeToken<EmpModel>() {
                                }.getType());
                                cce.CustodianEmp(EM);
                            } else {
                                cce.CustodianEmp(null);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        cce.CustodianEmp(null);
                    }
                });
    }


    public interface OnPrintListener {
        void Print(boolean isSuccess, String msg);
    }

    public interface OnCloseListener {
        void Close(boolean isSuccess, String msg);
    }

    public static void Print(String FInterID, OnPrintListener opl) {
        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", FInterID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAppServer,
                define.PrintAssestLabel, map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.e("打印贴标：" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if (ReturnType.equals("success")) {
                                opl.Print(true, ReturnMsg);
                            } else {
                                opl.Print(false, ReturnMsg);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        opl.Print(false, "接口无返回");
                    }
                });
    }

    public static void RegistrationClose(String FInterID, OnCloseListener opl) {
        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", FInterID);
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAppServer,
                define.AssestRegistrationClose, map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.e("入库：" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if (ReturnType.equals("success")) {
                                opl.Close(true, ReturnMsg);
                            } else {
                                opl.Close(false, ReturnMsg);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        opl.Close(false, "接口无返回");
                    }
                });
    }


}
