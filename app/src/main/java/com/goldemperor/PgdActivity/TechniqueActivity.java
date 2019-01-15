package com.goldemperor.PgdActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.define;
import com.goldemperor.PzActivity.RouteEntryModel;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.goldemperor.PgdActivity.PgdActivity.selectWorkCardPlan;


/**
 * Created by Nova on 2017/7/25.
 */

public class TechniqueActivity extends AppCompatActivity {


    private Context mContext;
    private Activity act;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private TechniqueAdapter mMenuAdapter;
    private int finterid;
    List<String[]> processList = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gxpg_technique);
        //隐藏标题栏
        getSupportActionBar().hide();

        mContext = this;
        act = this;


        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(this));// 添加分割线。


        mMenuAdapter = new TechniqueAdapter(processList);

        mMenuRecyclerView.setAdapter(mMenuAdapter);

        Intent intent = getIntent();
        finterid = intent.getIntExtra("finterid", 0);
        getData(finterid);
    }

    public void getData(int finterid) {

        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", finterid + "");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForMesServer,
                define.GetRouteEntryBody,
                map,
                new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                result = URLDecoder.decode(result, "UTF-8");
                                LOG.E("获取工序=" + result);
                                //{"ReturnMsg":"[{\"FEntryId\":1,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"CX\",\"FProcessName\":\"成型\",\"FPrice\":0.0000000000,\"FNote\":\"\"},{\"FEntryId\":1,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C33\",\"FProcessName\":\"斩点垫底(4层)\",\"FPrice\":0.0255000000,\"FNote\":\"\"},{\"FEntryId\":2,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C34\",\"FProcessName\":\"垫底烫印\",\"FPrice\":0.0336000000,\"FNote\":\"\"},{\"FEntryId\":3,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C35\",\"FProcessName\":\"斩包头(20层)\",\"FPrice\":0.0054600000,\"FNote\":\"\"},{\"FEntryId\":4,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C36\",\"FProcessName\":\"斩主跟(10层)\",\"FPrice\":0.0113400000,\"FNote\":\"\"},{\"FEntryId\":5,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C37\",\"FProcessName\":\"机压包头\",\"FPrice\":0.0200000000,\"FNote\":\"\"},{\"FEntryId\":6,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C38\",\"FProcessName\":\"机压主跟（2人）\",\"FPrice\":0.0200000000,\"FNote\":\"\"},{\"FEntryId\":7,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C39\",\"FProcessName\":\"斩垫跟片\",\"FPrice\":0.0040000000,\"FNote\":\"\"}]","ReturnType":"success"}
                                JSONObject jsonObject = new JSONObject(result);
                                String ReturnType = jsonObject.getString("ReturnType");
                                String ReturnMsg = jsonObject.getString("ReturnMsg");
                                if ("success".equals(ReturnType)) {
                                    List<RouteEntryModel> gylx = new Gson().fromJson(ReturnMsg, new TypeToken<List<RouteEntryModel>>() {
                                    }.getType());
                                    for (int i = 0; i < gylx.size(); i++) {
                                        String[] process = {gylx.get(i).getFProcessNumber(), gylx.get(i).getFProcessName()};
                                        processList.add(process);
                                    }
                                    mMenuAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(mContext, ReturnMsg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(mContext, "数据解析异常", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "数据解码异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "接口访问异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}
