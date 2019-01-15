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

public class MaterialActivity extends AppCompatActivity {


    private Context mContext;
    private Activity act;
    private SwipeMenuRecyclerView mMenuRecyclerView;
    private MaterialAdapter mMenuAdapter;
    private Long finterid;
    List<Sc_WorkPlanMaterial> sc_workPlanMaterialsList = new ArrayList<Sc_WorkPlanMaterial>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态啦
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gxpg_material);
        //隐藏标题栏
        getSupportActionBar().hide();

        mContext = this;
        act = this;

        Intent intent = getIntent();
        finterid = intent.getLongExtra("finterid", 0);
        Log.e("jindi", "finterid:" + finterid);
        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(mContext));// 添加分割线。


        mMenuAdapter = new MaterialAdapter(sc_workPlanMaterialsList);

        mMenuRecyclerView.setAdapter(mMenuAdapter);
        getData();
    }

    public void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("FInterID", finterid + "");
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForMesServer,
                define.GetWorkPlanMaterial,
//                "GetWorkPlanMaterial",
                map,
                result -> {
                    if (result != null) {
                        try {
                            result = URLDecoder.decode(result, "UTF-8");
                            LOG.E("获取生产订单用料=" + result);
                            //{"ReturnMsg":"[{\"FEntryId\":1,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"CX\",\"FProcessName\":\"成型\",\"FPrice\":0.0000000000,\"FNote\":\"\"},{\"FEntryId\":1,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C33\",\"FProcessName\":\"斩点垫底(4层)\",\"FPrice\":0.0255000000,\"FNote\":\"\"},{\"FEntryId\":2,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C34\",\"FProcessName\":\"垫底烫印\",\"FPrice\":0.0336000000,\"FNote\":\"\"},{\"FEntryId\":3,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C35\",\"FProcessName\":\"斩包头(20层)\",\"FPrice\":0.0054600000,\"FNote\":\"\"},{\"FEntryId\":4,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C36\",\"FProcessName\":\"斩主跟(10层)\",\"FPrice\":0.0113400000,\"FNote\":\"\"},{\"FEntryId\":5,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C37\",\"FProcessName\":\"机压包头\",\"FPrice\":0.0200000000,\"FNote\":\"\"},{\"FEntryId\":6,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C38\",\"FProcessName\":\"机压主跟（2人）\",\"FPrice\":0.0200000000,\"FNote\":\"\"},{\"FEntryId\":7,\"FInterId\":35986,\"FPartsID\":0,\"partName\":\"\",\"FItemID\":0,\"materialcode\":\"\",\"materialName\":\"\",\"FProcessNumber\":\"C39\",\"FProcessName\":\"斩垫跟片\",\"FPrice\":0.0040000000,\"FNote\":\"\"}]","ReturnType":"success"}
                            JSONObject jsonObject = new JSONObject(result);
                            String ReturnType = jsonObject.getString("ReturnType");
                            String ReturnMsg = jsonObject.getString("ReturnMsg");
                            LOG.E("ReturnMsg=" + ReturnMsg);
                            if ("success".equals(ReturnType)) {
                                List<Sc_WorkPlanMaterial> gylx = new Gson().fromJson(ReturnMsg, new TypeToken<List<Sc_WorkPlanMaterial>>() {
                                }.getType());
                                for (int i = 0; i < gylx.size(); i++) {
                                    sc_workPlanMaterialsList.add(gylx.get(i));
                                    mMenuAdapter.notifyDataSetChanged();
                                }
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
                });

    }




}
