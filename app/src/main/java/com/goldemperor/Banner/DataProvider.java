package com.goldemperor.Banner;

import android.support.v4.view.ViewPager;


import com.goldemperor.R;
import com.goldemperor.Widget.banner.transform.DepthTransformer;
import com.goldemperor.Widget.banner.transform.FadeSlideTransformer;
import com.goldemperor.Widget.banner.transform.FlowTransformer;
import com.goldemperor.Widget.banner.transform.RotateDownTransformer;
import com.goldemperor.Widget.banner.transform.RotateUpTransformer;
import com.goldemperor.Widget.banner.transform.ZoomOutSlideTransformer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataProvider {
    public static String[] titles = new String[]{
            "22条生产线开足马力忙生产",
            "第二届“金帝杯”中国象棋比赛成功举办",
            "新任温州海关关长林江来调研",
            "温大学生点赞企业",
            "马达加斯加纳尔松部长访问金帝",
    };
    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://www.jindishoes.com/UploadFile/201707/20170703032927248_7924.jpg",
            "http://www.jindishoes.com/UploadFile/201707/20170703034133101_7307.jpg",
            "http://www.jindishoes.com/UploadFile/201707/20170703033512773_6499.jpg",
            "http://www.jindishoes.com/UploadFile/201707/20170703033640211_6967.jpg",
            "http://www.jindishoes.com/UploadFile/201707/20170703030619734_0193.jpg",
    };
//    public static String[] titles = new String[]{
//            "22条生产线开足马力忙生产",
//            "年度表彰大会",
//            "金帝集团",
//            "研发中心",
//            "针车车间",
//    };
//   public static String[] urls = new String[]{//640*360 360/640=0.5625
//            "http://192.168.99.79:8083/Picture/jindi1.jpg",
//            "http://192.168.99.79:8083/Picture/jindi2.jpg",
//            "http://192.168.99.79:8083/Picture/jindi3.jpg",
//            "http://192.168.99.79:8083/Picture/jindi4.jpg",
//            "http://192.168.99.79:8083/Picture/jindi5.jpg",
//    };

    public static String[] text = new String[]{
            "重要通知:晚餐食堂搬迁至十足旁边",
    };

    public static List<String> title= Arrays.asList("重要通知:晚餐食堂搬迁至十足旁边");
    public static ArrayList<BannerItem> getList() {
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = urls[i];
            item.title = titles[i];

            list.add(item);
        }

        return list;
    }

    public static ArrayList<Integer> geUsertGuides() {
        ArrayList<Integer> list = new ArrayList<>();
//        list.add(R.mipmap.guide_img_1);
//        list.add(R.mipmap.guide_img_2);
//        list.add(R.mipmap.guide_img_3);
//        list.add(R.mipmap.guide_img_4);

        return list;
    }

    public static Class<? extends ViewPager.PageTransformer> transformers[] = new Class[]{
            DepthTransformer.class,
            FadeSlideTransformer.class,
            FlowTransformer.class,
            RotateDownTransformer.class,
            RotateUpTransformer.class,
            ZoomOutSlideTransformer.class,
    };
}
