package com.goldemperor.CCActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.GxReport.GxReport;
import com.goldemperor.LoginActivity.LoginActivity;
import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.XJChenk.BasePopupWindow;
import com.goldemperor.XJChenk.PopupDismissmaListener;
import com.goldemperor.XJChenk.SPListActivity;
import com.goldemperor.XJChenk.XJListAdapter;
import com.goldemperor.XJChenk.priceResult;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nova on 2017/10/28.
 */

public class CCListActivity extends AppCompatActivity {

    private SwipeMenuRecyclerView mMenuRecyclerView;
    private Context mContext;
    private Activity act;
    private TextView tv_tip;
    public CCListAdapter mMenuAdapter;
    private SharedPreferences dataPref;

    private List<facardResult> facardResultList = new ArrayList<facardResult>();

    private String StartDate;
    private String EndDate;

    public static facardResult selectFacardResult;

    private EditText searchEdit;
    private Button button_search;

    private ActionBar bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cclist);

        dataPref = this.getSharedPreferences(define.SharedName, 0);

        tv_tip = (TextView) findViewById(R.id.tv_tip);
        act = this;
        mContext = this;

        bar = getActionBar();

        StartDate = Utils.getCurrentTime();
        EndDate = Utils.getCurrentTime();

        tv_tip.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。


        mMenuAdapter = new CCListAdapter(facardResultList);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mMenuRecyclerView.setAdapter(mMenuAdapter);

        button_search = (Button) findViewById(R.id.button_search);
        searchEdit = (EditText) findViewById(R.id.searchEdit);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchEdit.getText().toString().replaceAll(" ", "").length() > 2) {
                    facardResultList.clear();
                    if (mMenuAdapter != null) {
                        mMenuAdapter.notifyDataSetChanged();
                    }
                    getData(searchEdit.getText().toString().replaceAll(" ", ""));
                } else {
                    Toast.makeText(mContext, "请输入正确的工号", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(final int position) {
            if (facardResultList != null) {
                selectFacardResult = facardResultList.get(position);
            }
            Intent i = new Intent(mContext, CCPrintActivity.class);
            startActivityForResult(i, 0);
        }
    };


    private void getData(String fnumber) {
        tv_tip.setText("数据载入中...");
        tv_tip.setVisibility(View.VISIBLE);
        facardResultList.clear();
        RequestParams params = new RequestParams(define.IP1718881 + define.GetFACardByEmpNumber);
        params.addQueryStringParameter("fnumber", fnumber);
        Log.e("jindi", params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                Log.e("jindi", result);
                if (result == null || result.isEmpty()) {
                    tv_tip.setText("暂无财产数据或该工号尚未建立emp");
                } else {
                    List<facardResult> facardResultTemp = GsonFactory.jsonToArrayList(result, facardResult.class);
                    tv_tip.setText(facardResultTemp.size() + "条财产数据");
                    for (int i = 0; i < facardResultTemp.size(); i++) {
                        facardResultList.add(facardResultTemp.get(i));
                    }

                }

                mMenuAdapter.notifyDataSetChanged();
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());
                tv_tip.setText("数据返回错误");

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mMenuAdapter != null) {
            if (searchEdit.getText().toString().replaceAll(" ", "").length() > 2) {
                getData(searchEdit.getText().toString().replaceAll(" ", ""));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ccbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_action_1) {
            //Toast.makeText(mContext, "筛选", Toast.LENGTH_LONG).show();
            Intent i = new Intent(mContext, CCDListActivity.class);
            startActivityForResult(i, 0);
        }
        return super.onOptionsItemSelected(item);
    }

}
