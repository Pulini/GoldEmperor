package com.goldemperor.PropertyRegistration.Pad;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldemperor.MainActivity.define;
import com.goldemperor.PropertyRegistration.PopupwindowGroup;
import com.goldemperor.PropertyRegistration.PropertyDetailsModel;
import com.goldemperor.PropertyRegistration.TakePhotoHelper;
import com.goldemperor.PzActivity.DeptModel;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.devio.takephoto.app.TakePhotoFragmentActivity;
import org.devio.takephoto.model.TResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * File Name : PropertyRegistrationDetailsForPadActivity
 * Created by : PanZX on  2019/1/18 10:35
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_property_registration_details_for_pad)
public class PropertyRegistrationDetailsForPadActivity extends TakePhotoFragmentActivity {

    @ViewInject(R.id.IV_Back)
    private ImageView IV_Back;

    @ViewInject(R.id.TV_Satus)
    private TextView TV_Satus;

    @ViewInject(R.id.FB_InitiateAudit)
    private FancyButton FB_InitiateAudit;

    @ViewInject(R.id.VP_Registration)
    private ViewPager VP_Registration;


    private PropertyDetailsModel PDML;
    private Activity mActivity;
    private List<DeptModel> DML;
    private PopupwindowGroup PG;

    private RegistrationFragment1 RF1;
    private RegistrationFragment2 RF2;
    List<Fragment> mFragment = new ArrayList<>();
    private TakePhotoHelper PhotoHelper = new TakePhotoHelper(getTakePhoto());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
        mActivity = this;
        initview();
        GetDetails();

    }

    private void initview() {
        IV_Back.setOnClickListener(v -> finish());
        FB_InitiateAudit.setEnabled(false);
        FB_InitiateAudit.setOnClickListener(v -> RF1.Submit(() -> {
            FB_InitiateAudit.setEnabled(false);
            TV_Satus.setText("审核中");
        }));
        RF1 = new RegistrationFragment1();
        RF2 = new RegistrationFragment2();
//        RF1.setOnSelectListener(() -> SelectGroup());
        RF1.setOnTakePhotoListener(() -> PhotoHelper.TakePhoto());
        mFragment.add(RF1);
        mFragment.add(RF2);
        VP_Registration.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        });

    }


    private void SelectGroup() {
        if (PG == null) {
            PG = new PopupwindowGroup(mActivity, DML);
        }
        PG.Show(dm -> {
            if (dm == null) {
                return;
            }
            RF1.setDeptName(dm.getFName());
            PDML.setFDeptID(dm.getFItemID());
            PDML.setFDeptName(dm.getFName());
        });
    }

    private void GetDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", getIntent().getExtras().getInt("InterID") + "");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAppServer,
                define.GetAssestByFInterID,
                map, result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("财产详情" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            if ("success".equals(ReturnType)) {
                                PDML = new Gson().fromJson(ReturnMsg, PropertyDetailsModel.class);
//                                GetGroup();
                                if (PDML.getFProcessStatus().equals("未审核")) {
                                    FB_InitiateAudit.setEnabled(true);
                                }
                                RF1.setData(PDML);
                                TV_Satus.setText(PDML.getFProcessStatus());
                                if (PDML.getCardEntry() != null) {
                                    RF2.setList(PDML.getCardEntry());
                                }
                            } else {
                                new AlertDialog.Builder(mActivity)
                                        .setTitle("获取数据失败")
                                        .setCancelable(false)
                                        .setMessage(ReturnMsg)
                                        .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*private void GetGroup() {
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForMesServer,
                define.GetDeptAll, new HashMap<>(), result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            LOG.E("ReturnMsg=" + ReturnMsg);
                            if ("success".equals(ReturnType)) {
                                DML = new Gson().fromJson(ReturnMsg, new TypeToken<List<DeptModel>>() {
                                }.getType());
                            } else {
                                new AlertDialog.Builder(mActivity)
                                        .setTitle("错误")
                                        .setCancelable(false)
                                        .setMessage(ReturnMsg)
                                        .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("错误")
                                .setCancelable(false)
                                .setMessage("获取组别数据失败")
                                .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });
    }
*/
    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        LOG.e("takeSuccess=" + result.getImages().size());
        LOG.e("OriginalPath=" + result.getImage().getOriginalPath());
        Luban.with(mActivity)
                .load(result.getImage().getOriginalPath())
                .ignoreBy(100)
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LOG.e("压缩开始前调用，可以在方法内启动 loading UI");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LOG.e("压缩成功后调用，返回压缩后的图片文件");
                        LOG.e("路径:" + file.getPath());
                        RF1.setPhoto(file);

                    }

                    @Override
                    public void onError(Throwable e) {
                        LOG.e("当压缩过程出现问题时调用");
                    }
                }).launch();
    }


}

