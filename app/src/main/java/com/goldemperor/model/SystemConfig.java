package com.goldemperor.model;

import com.goldemperor.MainActivity.define;
import com.goldemperor.Utils.SPUtils;

/**
 * Created by louge on 2017-07-12.
 */

public class SystemConfig {
    //webservice网址
//    public static String WebServiceURL="http://192.168.99.79:8012/ErpForAndroidStockServer.asmx";
//    public static String WebServiceURL="http://192.168.99.79:8012/ErpForAndroidStockServer.asmx";
    public static String WebServiceURL= SPUtils.getServerPath()+"ErpForAndroidStockServer.asmx";
//    public static String WebServiceURL="http://localhost:3087/ErpForAndroidStockServer.asmx";
  //  public static String WebServiceURL="http://10.0.2.2:3087/ErpForAndroidStockServer.asmx";
}
