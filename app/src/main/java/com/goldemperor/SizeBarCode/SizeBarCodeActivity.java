package com.goldemperor.SizeBarCode;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * File Name : SizeBarCodeActivity
 * Created by : PanZX on  2018/5/23 14:32
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class SizeBarCodeActivity extends Activity {
    private ListView LV_SizeBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_bar_code);
        LV_SizeBar=(ListView) findViewById(R.id.LV_SizeBar);
        getdata();
    }
    private void getdata(){
        HashMap<String, String> map = new HashMap<>();
        map.put("BillNO", "");
        map.put("suitID", "1");
        WebServiceUtils.callWebService(
                SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                define.GetScMoSizeBarCodeByMONo,
                map,
                result -> LOG.e("callBack="+result));
    }
}
