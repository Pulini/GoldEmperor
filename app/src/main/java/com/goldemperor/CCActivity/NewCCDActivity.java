package com.goldemperor.CCActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import com.goldemperor.Widget.fancybuttons.FancyButton;

/**
 * Created by Nova on 2017/10/28.
 */

public class NewCCDActivity extends AppCompatActivity {

    private Context mContext;
    private Activity act;
    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;


    private TextView tv_fsno;
    private TextView tv_fname;
    private TextView tv_fmodel;

    private TextView tv_suppliername;
    private TextView tv_fprice;
    private TextView tv_fempname;

    private TextView tv_fempnumber;
    private TextView tv_fdeptname;


    private TextView tv_faddress;
    private TextView tv_fnumber;

    private EditText edit_fempnumber;

    private FancyButton btn_ok;

    femp emp=new femp();
    private List<framework> frameworkList=new ArrayList<>();
    private List<String> companyList=new ArrayList<>();
    private Spinner spinner_company;
    private ArrayAdapter<String> companyAdapter;


    private List<departmentOne> departOneList=new ArrayList<>();
    private List<String> departOneNameList=new ArrayList<>();
    private Spinner spinner_departOne;
    private ArrayAdapter<String> departOneAdapter;

    private List<departmentTwo> departTwoList=new ArrayList<>();
    private List<String> departTwoNameList=new ArrayList<>();
    private Spinner spinner_departTwo;
    private ArrayAdapter<String> departTwoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_newccd);

        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();
        act = this;
        mContext = this;


        tv_fsno = (TextView) findViewById(R.id.tv_fsno);
        tv_fname = (TextView) findViewById(R.id.tv_fname);
        tv_fmodel = (TextView) findViewById(R.id.tv_fmodel);
        tv_suppliername = (TextView) findViewById(R.id.tv_suppliername);

        tv_fprice = (TextView) findViewById(R.id.tv_fprice);
        tv_fempname = (TextView) findViewById(R.id.tv_fempname);
        tv_fempnumber = (TextView) findViewById(R.id.tv_fempnumber);

        tv_fdeptname = (TextView) findViewById(R.id.tv_fdeptname);
        tv_faddress = (TextView) findViewById(R.id.tv_faddress);
        tv_fnumber = (TextView) findViewById(R.id.tv_fnumber);


        if(CCListActivity.selectFacardResult!=null) {
            tv_fsno.setText("申购单号:" + CCListActivity.selectFacardResult.getFsno());
            tv_fname.setText("财产名称:" + CCListActivity.selectFacardResult.getFname());
            tv_suppliername.setText("供应商:" + CCListActivity.selectFacardResult.getFvender());
            tv_fmodel.setText("规格型号:" + CCListActivity.selectFacardResult.getFmodel());
            tv_fprice.setText("财产单价:" + CCListActivity.selectFacardResult.getFprice());
            tv_fempname.setText("保管员工:" + CCListActivity.selectFacardResult.getFempname());

            tv_fempnumber.setText("员工工号:" + CCListActivity.selectFacardResult.getFempnumber());


            tv_fdeptname.setText("保管部门:" + CCListActivity.selectFacardResult.getFdeptname());
            tv_faddress.setText("保管地点:" + CCListActivity.selectFacardResult.getFaddress());
            tv_fnumber.setText("财产编号:" + CCListActivity.selectFacardResult.getFnumber());
        }
        btn_ok = (FancyButton) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        spinner_company = (Spinner)findViewById(R.id.spinner_company);
        companyAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, companyList);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_company.setAdapter(companyAdapter);
        spinner_company.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                getdepartOneData(frameworkList.get(arg2).getFnumber());
                Log.e("jindi",frameworkList.get(arg2).getFnumber());
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spinner_departOne = (Spinner)findViewById(R.id.spinner_departOne);
        departOneAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departOneNameList);
        departOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_departOne.setAdapter(departOneAdapter);
        spinner_departOne.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                getdepartTwoData(departOneList.get(arg2).getFjspaygroupingnumber());
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spinner_departTwo = (Spinner)findViewById(R.id.spinner_departTwo);
        departTwoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departTwoNameList);
        departTwoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_departTwo.setAdapter(departTwoAdapter);
        spinner_departTwo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        edit_fempnumber= (EditText)findViewById(R.id.edit_fempnumber);

        edit_fempnumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 */
                if (edit_fempnumber.getText().toString().trim().length() >= 6 && edit_fempnumber.isFocused()) {
                    getEmpData(edit_fempnumber.getText().toString().trim());
                }
            }
        });

        getCompanyData();


    }
    private void getCompanyData() {
        frameworkList.clear();
        companyList.clear();
        RequestParams params = new RequestParams(define.IP1718881 + define.GetCompany);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.e("jindi", result);
                if (result == null || result.isEmpty()) {
                    Toast.makeText(mContext,"未能获取到公司数据",Toast.LENGTH_LONG).show();
                } else {
                    List<framework> frameworkListTemp = GsonFactory.jsonToArrayList(result, framework.class);
                    for (int i = 0; i < frameworkListTemp.size(); i++) {
                        frameworkList.add(frameworkListTemp.get(i));
                        companyList.add(frameworkListTemp.get(i).getFname());
                        companyAdapter.notifyDataSetChanged();
                    }
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());

            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void getdepartOneData(String fnumber) {
        departOneList.clear();
        departOneNameList.clear();
        RequestParams params = new RequestParams(define.IP1718881 + define.GetDepartmentOneByFjspaygroupingnumber);
        params.addQueryStringParameter("fnumber", fnumber);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.e("jindi", result);
                if (result == null || result.isEmpty()) {
                    Toast.makeText(mContext,"未能获取到一级部门数据",Toast.LENGTH_LONG).show();
                } else {
                    List<departmentOne> departmentOneTemp = GsonFactory.jsonToArrayList(result, departmentOne.class);
                    for (int i = 0; i < departmentOneTemp.size(); i++) {
                        departOneList.add(departmentOneTemp.get(i));
                        departOneNameList.add(departmentOneTemp.get(i).getFjspaygroupingname());
                        departOneAdapter.notifyDataSetChanged();
                    }
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());

            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void getdepartTwoData(String fnumber) {
        departTwoList.clear();
        departTwoNameList.clear();
        RequestParams params = new RequestParams(define.IP1718881 + define.GetDepartmentTwoByFnumber);
        params.addQueryStringParameter("fnumber", fnumber);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.e("jindi", result);
                if (result == null || result.isEmpty()) {
                    Toast.makeText(mContext,"未能获取到二级部门数据",Toast.LENGTH_LONG).show();
                } else {
                    List<departmentTwo> departmentTwoTemp = GsonFactory.jsonToArrayList(result, departmentTwo.class);
                    for (int i = 0; i < departmentTwoTemp.size(); i++) {
                        departTwoList.add(departmentTwoTemp.get(i));
                        departTwoNameList.add(departmentTwoTemp.get(i).getFname());
                        departTwoAdapter.notifyDataSetChanged();
                    }
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());

            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }


    private void getEmpData(String fnumber) {
        tv_fempname.setText("保管员工 ");
        RequestParams params = new RequestParams(define.IP1718881 + define.GetFempByFnumber);
        params.addQueryStringParameter("fnumber", fnumber);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.e("jindi", result);
                Gson g=new Gson();
                if (result == null || result.isEmpty()) {
                    Toast.makeText(mContext,"未能获取到员工Emp数据",Toast.LENGTH_LONG).show();
                } else {
                    emp = g.fromJson(result, femp.class);
                    tv_fempname.setText("保管员工 "+emp.getFname());
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());

            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(final int position) {
        }
    };

}
