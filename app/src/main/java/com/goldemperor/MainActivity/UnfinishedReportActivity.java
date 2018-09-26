package com.goldemperor.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.goldemperor.PgdActivity.Emp;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Utils.ZProgressHUD;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.goldemperor.model.UnfinishedGroupModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * File Name : UnfinishedReportActivity
 * Created by : PanZX on  2018/9/21 10:59
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_unfinished_report)
public class UnfinishedReportActivity extends Activity {

    @ViewInject(R.id.LV_GroupList)
    private ListView LV_GroupList;

    @ViewInject(R.id.TV_UserGroup)
    private TextView TV_UserGroup;

    @ViewInject(R.id.ET_UserCode)
    private EditText ET_UserCode;

    @ViewInject(R.id.TV_UserName)
    private TextView TV_UserName;

    @ViewInject(R.id.TV_Duty)
    private TextView TV_Duty;

    @ViewInject(R.id.TV_Time)
    private TextView TV_Time;
    @ViewInject(R.id.FB_Submit)
    private FancyButton FB_Submit;

    @ViewInject(R.id.FB_SetTime)
    private FancyButton FB_SetTime;

    @ViewInject(R.id.ET_MSG)
    private TextView ET_MSG;

    Activity mActivity;
    private UnfinishedGroupModel UFGM;
    private MyAdapter MA;
    private Emp EMP;
    private String SubmitTime = "";
    private TimePickerView PickTime;
    private ZProgressHUD mProgressHUD;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        x.view().inject(this);
        initview();
        getdata();
    }

    private void initview() {
        mActivity = this;
        mProgressHUD = new ZProgressHUD(this);
        MA = new MyAdapter();
        LV_GroupList.setAdapter(MA);
        LV_GroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TV_UserGroup.setText(UFGM.DbHelperTable.get(position).getFName());
                if (EMP != null && !UFGM.DbHelperTable.get(position).getFItemID().equals(EMP.getEmp_Departid() + "")) {
                    Toast.makeText(mActivity, "员工(" + EMP.getEmp_Name() + ")不在(" + UFGM.DbHelperTable.get(position).getFName() + ")组别中。", Toast.LENGTH_SHORT).show();
                    ET_UserCode.setText("");
                    TV_UserName.setText("");
                    TV_Duty.setText("");
                    EMP = null;
                }
            }
        });
        ET_UserCode.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ET_UserCode.getText().toString().trim().length() >= 6 && ET_UserCode.isFocused()) {
                    getUser(ET_UserCode.getText().toString().trim());
                }
            }
        });
        FB_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EMP == null) {
                    Toast.makeText(mActivity, "请正确录入工号及组别信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ET_MSG.getText().toString().trim().length() == 0) {
                    Toast.makeText(mActivity, "请输入未完成原因！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Submit();
            }
        });
        FB_SetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickTime.show();
            }
        });
        PickTime = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SubmitTime = Utils.getTime(date, "yyyy-MM-dd");
                TV_Time.setText(SubmitTime);
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .build();
        SubmitTime = Utils.getTime(new Date(), "yyyy-MM-dd");
        TV_Time.setText(SubmitTime);
    }

    private void getdata() {
        mProgressHUD.setMessage("获取组别信息中...");
        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        WebServiceUtils.WEBSERVER_NAMESPACE = define.jindishoes;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ERPForWeixinServer,
                define.GetDeptment,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("获取组别列表=" + result);
                                XStream xStream = new XStream();
                                xStream.processAnnotations(UnfinishedGroupModel.class);
                                UFGM = (UnfinishedGroupModel) xStream.fromXML(result);
                                if (UFGM != null && UFGM.DbHelperTable.size() > 0) {
                                    MA.notifyDataSetChanged();
                                }
                                mProgressHUD.dismissWithSuccess();
                            } catch (UnsupportedEncodingException e) {
                                mProgressHUD.dismissWithFailure();
                                e.printStackTrace();
                                Toast.makeText(mActivity, "数据解码异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mProgressHUD.dismissWithFailure();
                            Toast.makeText(mActivity, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Submit() {
        mProgressHUD.setMessage("提交中...");
        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("FDate", SubmitTime);
        map.put("FDeptID", EMP.getEmp_Departid() + "");
        map.put("Value", ET_MSG.getText().toString());
        map.put("FNumber", EMP.getEmp_Code());
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.Submit2PrdDayReportNote,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {

                        if (result != null) {
                            mProgressHUD.dismissWithSuccess();
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                JSONObject jb = new JSONObject(result);
                                String ReturnType = jb.getString("ReturnType");
                                String ReturnMsg = jb.getString("ReturnMsg");
                                LOG.E("提交未完成原因=" + result);
                                if (ReturnType.equals("success")) {
                                    Toast.makeText(mActivity, "提交成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mActivity, "提交失败：" + ReturnMsg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (UnsupportedEncodingException e) {
                                mProgressHUD.dismissWithFailure("数据解码异常");
                                e.printStackTrace();
                            } catch (JSONException e) {
                                mProgressHUD.dismissWithFailure("数据解析异常");
                                e.printStackTrace();
                            }
                        } else {
                            mProgressHUD.dismissWithFailure("接口访问异常");
                        }
                    }
                });
    }


    private void getUser(String FNumber) {
        HashMap<String, String> map = new HashMap<>();
        map.put("FDeptmentID", "");
        map.put("FEmpNumber", FNumber);
        map.put("suitID", "1");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                define.Net2 + define.ErpForAndroidStockServer,
                define.GetEmpByFnumber,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.e("getUser:" + result);
                                Gson g = new Gson();
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if (ReturnType.equals("success")) {
                                    List<Emp> user = g.fromJson(ReturnMsg, new TypeToken<List<Emp>>() {
                                    }.getType());
                                    EMP = user.get(0);
                                    TV_UserName.setText(EMP.getEmp_Name());
                                    TV_UserGroup.setText("");
                                    for (UnfinishedGroupModel.table table : UFGM.DbHelperTable) {
                                        if (table.getFItemID().equals(EMP.getEmp_Departid() + "")) {
                                            TV_UserGroup.setText(table.getFName());
                                        }
                                    }
                                    TV_Duty.setText(EMP.getEmp_Duty());
                                } else {
                                    Toast.makeText(mActivity, "无此工号", Toast.LENGTH_LONG).show();
                                    TV_UserName.setText("");
                                    TV_UserGroup.setText("");
                                    TV_Duty.setText("");
                                }
                            } catch (Exception e) {
                                TV_UserName.setText("");
                                TV_UserGroup.setText("");
                                TV_Duty.setText("");
                                Toast.makeText(mActivity, "员工:" + ET_UserCode.getText().toString().trim() + "  数据异常，请联系管理员确定数据。", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            TV_UserName.setText("无此工号");
                            TV_UserName.setText("");
                            TV_UserGroup.setText("");
                            TV_Duty.setText("");
                            Toast.makeText(mActivity, "无此工号", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (UFGM != null) {
                return UFGM.DbHelperTable.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return UFGM.DbHelperTable.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View V = LayoutInflater.from(mActivity).inflate(R.layout.item_undinished, null);
            V.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(40)));
            ((TextView) V.findViewById(R.id.TV_Name)).setText(UFGM.DbHelperTable.get(position).getFName());
            return V;
        }
    }

}
