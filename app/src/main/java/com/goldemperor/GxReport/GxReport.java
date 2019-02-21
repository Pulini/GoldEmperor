package com.goldemperor.GxReport;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;



import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.google.gson.Gson;


import com.panzx.pulini.Bootstrap.BootstrapButton;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Nova on 2017/8/15.
 */

public class GxReport extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private BootstrapButton scan;

    private BootstrapButton submit;

    private Context mContext;
    private Activity activity;
    private ArrayList<Order> QRCodeList;

    private MenuAdapter mMenuAdapter;

    private TextView scanCount;
    private TextView productCount;
    private SwipeMenuRecyclerView mMenuRecyclerView;


    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };
    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.wait)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };
    /**
     * 菜单点击监听。
     */
    private SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {

        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (menuPosition == 0) {// 处理按钮被点击。
                QRCodeList.remove(adapterPosition);
                mMenuAdapter.notifyItemRemoved(adapterPosition);
                setData();
            }
        }
    };
    private ZProgressHUD mProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gxreport);
        //隐藏标题栏
        getSupportActionBar().hide();



        mContext = this;
        activity = this;
        mProgressHUD = new ZProgressHUD(this);
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);

        QRCodeList = new ArrayList<>();
        scan = findViewById(R.id.scan);
        scan.setOnClickListener(v -> {
            Intent i = new Intent(mContext, GxReportScan.class);
            i.putParcelableArrayListExtra("QRCodeList", QRCodeList);
            activity.startActivityForResult(i, 1);
        });

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(v -> {
            if (QRCodeList.size() != 0) {
                submitData();
            } else {
                LemonHello.getInformationHello("提示", "请扫入条码")
                        .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide()))
                        .show(activity);
            }
        });

        mMenuRecyclerView = findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(activity));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(activity));// 添加分割线。

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        mMenuAdapter = new MenuAdapter(QRCodeList, this);

        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
        scanCount = (TextView) findViewById(R.id.tv_scanCount);
        scanCount.setText("条码数量:" + QRCodeList.size());
        productCount = (TextView) findViewById(R.id.tv_productCount);
        float productCountTemp = 0;
        for (int i = 0; i < QRCodeList.size(); i++) {
            productCountTemp += Float.valueOf(QRCodeList.get(i).getFQty());
        }
        productCount.setText("产品总数:" + productCountTemp + "件");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Order> QRCodeListTemp = data.getParcelableArrayListExtra("QRCodeList");
        QRCodeList.clear();
        QRCodeList.addAll(QRCodeListTemp);
        mMenuAdapter.notifyDataSetChanged();
        setData();
    }

    public void submitData() {
        try {
            submit.setEnabled(false);
            List<SubmitJson> sjl = new ArrayList<SubmitJson>();
            for (int i = 0; i < QRCodeList.size(); i++) {
                SubmitJson sj = new SubmitJson();
                sj.setD_BarCode(QRCodeList.get(i).getFCardNo());
                sj.setD_EmpID((String)SPUtils.get(define.SharedEmpId, "none"));
                sj.setD_Qty(QRCodeList.get(i).getFQty());
                sjl.add(sj);
            }
            mProgressHUD.setMessage("提交中...");
            mProgressHUD.show();

            HashMap<String, String> map = new HashMap<>();
            map.put("barcodeJson", URLEncoder.encode(new Gson().toJson(sjl), "UTF-8"));
            map.put("OrganizeID", (String)SPUtils.get(define.SharedFOrganizeid, "1"));
            map.put("BillTypeID", "1");
            map.put("EmpID", (String)SPUtils.get(define.SharedEmpId, "0"));
            map.put("UserID", (String)SPUtils.get(define.SharedUserId, "0"));
            WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
            WebServiceUtils.callWebService(
                    SPUtils.getServerPath() + define.ErpForAppServer,
                    define.SubmitProcessBarCode2CollectBill,
                    map, result -> {
                        submit.setEnabled(true);
                        mProgressHUD.dismiss();
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("工序汇报提交=" + result);
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if ("success".equals(ReturnType)) {
                                    LemonHello.getSuccessHello("提示", "提交成功,工序汇报单号:"+ReturnMsg)
                                            .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide()))
                                            .show(activity);
                                    QRCodeList.clear();
                                    activity.runOnUiThread(() -> mMenuAdapter.notifyDataSetChanged());
                                    setData();
                                } else {
                                    showdialog("提交失败:" + ReturnMsg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                showdialog("数据解析异常");

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                showdialog("数据解码异常");
                            }
                        } else {
                            showdialog("接口无返回");
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    private void showdialog(String msg){
        LemonHello.getErrorHello("错误", msg)
                .addAction(new LemonHelloAction("我知道啦", (helloView, helloInfo, helloAction) -> helloView.hide()))
                .show(activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    public void setData() {
        scanCount.setText("条码数量:" + QRCodeList.size());
        float productCountTemp = 0;
        for (int i = 0; i < QRCodeList.size(); i++) {
            productCountTemp += Float.valueOf(QRCodeList.get(i).getFQty());
        }
        productCount.setText("产品总数:" + productCountTemp + "件");
    }

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
