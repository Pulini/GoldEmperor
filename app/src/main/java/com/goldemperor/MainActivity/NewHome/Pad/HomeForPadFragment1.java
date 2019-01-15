package com.goldemperor.MainActivity.NewHome.Pad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldemperor.Banner.BannerItem;
import com.goldemperor.Banner.DataProvider;
import com.goldemperor.Banner.SimpleImageBanner;
import com.goldemperor.Banner.SimpleTextBanner;
import com.goldemperor.MainActivity.NewHome.Model.HomePhotoModel;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.banner.anim.select.ZoomInEnter;
import com.goldemperor.Widget.verticalviewpager.ViewPagerFragment;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * File Name : HomeForTVFragment1
 * Created by : PanZX on  2018/9/29 20:57
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：主页1
 */
@ContentView(R.layout.fragment_home1_for_pad)
public class HomeForPadFragment1 extends ViewPagerFragment {
    @ViewInject(R.id.SIB_Home)
    private SimpleImageBanner SIB_Home;
    @ViewInject(R.id.STB_Home)
    private SimpleTextBanner STB_Home;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = x.view().inject(this, inflater, container);
        }
        return rootView;
    }



    private void getdata() {
        HashMap<String, String> map = new HashMap<>();
        WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
        WebServiceUtils.callWebService(SPUtils.getServerPath() + define.ErpForMesServer, define.GetPictureList, map, result -> {
            if (result != null) {
                try {
                    result = URLDecoder.decode(result, "UTF-8");
                    LOG.E("主页=" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    String ReturnType = jsonObject.getString("ReturnType");
                    String ReturnMsg = jsonObject.getString("ReturnMsg");
                    if ("success".equals(ReturnType)) {
                        List<HomePhotoModel> hpm = new Gson().fromJson(ReturnMsg, new TypeToken<List<HomePhotoModel>>() {
                        }.getType());

                        ArrayList<BannerItem> list = new ArrayList<>();
                        for (int i = 0; i < hpm.size(); i++) {
                            BannerItem item = new BannerItem();
                            item.imgUrl = hpm.get(i).getPicturePath();
                            item.title = hpm.get(i).getPictureName();
                            list.add(item);
                        }

                        SIB_Home.setSelectAnimClass(ZoomInEnter.class)
                                .setSource(list)
                                .setTitleShow(true)
                                .startScroll();
                        STB_Home.setSource(DataProvider.title)
                                .startScroll();
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getdata();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            //当前页面已显示
        } else {
            //当前页面未显示
        }
    }

}