package com.goldemperor.ScanCode.Share;

import com.goldemperor.Utils.LOG;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by xufanglou on 2016-07-20.
 */
public class URLCode {
    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            LOG.e( "toURLEncoded error:" + paramString);
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
            LOG.e( "toURLEncoded error:"+ localException);
        }

        return "";
    }

    public static String toURLDecoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            LOG.e( "toURLDecoded error:" + paramString);
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLDecoder.decode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
            LOG.e( "toURLDecoded error:" +  localException);
        }

        return "";
    }

}
