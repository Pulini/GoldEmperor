package com.goldemperor.PropertyRegistration;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * File Name : BluetoothUtils
 * Created by : PanZX on  2019/3/1 13:23
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：蓝牙打印机工具类
 */
public class BluetoothUtils {

    private static BluetoothSocket btSocket = null;
    private static OutputStream OutStream = null;
    private static InputStream InStream = null;
    private static byte[] readBuf = new byte[1024];
    private static boolean IsConnected = false;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = "00:0C:BF:18:72:2A";


    public interface OnBleListener {
        void Print(boolean isSend, String msg);
    }

    /**
     * 打开蓝牙
     *
     * @return
     */
    public static String EnableBlueTooth() {
        BluetoothAdapter BTadapter = BluetoothAdapter.getDefaultAdapter();
        if (BTadapter == null) {
            return "设备上没有发现有蓝牙设备";
        }
        if (!BTadapter.isEnabled()) {
            BTadapter.enable();
        }
        return "";
    }


    /**
     * 打印资产贴标
     *
     * @param tf       文本字体
     * @param FInterID 资产ID
     * @param Name     资产名称
     * @param Number   资产编号
     * @param Date     购买日期
     * @param Type     打印标签类型 0:(75*45) 1:(90*50)
     */
    public static void Print(Typeface tf, String FInterID, String Name, String Number, String Date, int Type, OnBleListener obl) {
        Log.e("Pan", Type == 0 ? "打印小标签" : "打印大标签");
        if (Type == 0) {
            new Thread(() -> PrintSmall(tf, FInterID, Name, Number, Date, obl)).start();
        } else if (Type == 1) {
            new Thread(() -> PrintBig(tf, FInterID, Name, Number, Date, obl)).start();
        }
    }

    /**
     * 起始坐标x20 y15 高540  宽900  内容高490  内容宽850
     *
     * @param tf       中文字体
     * @param FInterID ID
     * @param Name     设备名字
     * @param Number   设备编号
     * @param Date     购买日期
     * @param obl
     */
    public static void PrintSmall(Typeface tf, String FInterID, String Name, String Number, String Date, OnBleListener obl) {
        openport(address);//链接打印机
        String status1 = GetStatus();
        Log.e("Pan", "当前状态=" + status1);
        if (!"准备就绪".equals(status1)) {
            obl.Print(false, status1);
            return;
        }
        clearbuffer();
        setup(75, 45, 4, 12, 0, 2, 0);

        bar("20", "14", "850", "4");//顶部横线
        bar("20", "506", "850", "4");//底部横线

        bar("20", "14", "4", "496");//左侧竖线
        bar("870", "14", "4", "496");//右侧竖线

        bar("20", "140", "850", "4");//标题横线
        bar("496", "140", "4", "370");//二维码框线
        qrcode(510, 154, "H", "7", "A", "0", "M2", "S7", "http://jdwx.goldemperor.com/aspx/FACard/CardInfo.aspx?FInterID=" + FInterID);
        windowsfont(160, 50, tf, 60, "金帝集团股份有限公司");
        windowsfont(50, 200, tf, 40, "财产名称：" + Name);
        windowsfont(50, 300, tf, 40, "财产编号：" + Number);
        windowsfont(50, 400, tf, 40, "购置日期：" + Date);

        printlabel(1, 1);
        String status2 = GetStatus();
        Log.e("Pan", "当前状态=" + status2);
        if (status2.equals(status1) && status2.equals("准备就绪")) {
            obl.Print(true, "打印完成");
        } else {
            obl.Print(false, status2);
        }
        closeport(3000);
    }

    /**
     * 起始坐标x20 y15 高600  宽1080  内容高570  内容宽1040
     *
     * @param tf       中文字体
     * @param FInterID ID
     * @param Name     设备名字
     * @param Number   设备编号
     * @param Date     购买日期
     * @param obl
     */
    public static void PrintBig(Typeface tf, String FInterID, String Name, String Number, String Date, OnBleListener obl) {
        openport(address);//链接打印机
        String status1 = GetStatus();
        Log.e("Pan", "当前状态=" + status1);
        if (!"准备就绪".equals(status1)) {
            obl.Print(false, status1);
            return;
        }
        clearbuffer();
        setup(90, 50, 4, 12, 0, 2, 0);

        sendcommand("BOX 20,20,1040,570,4\n");//画线框

        bar("20", "150", "1020", "4");//标题横线
        bar("660", "150", "4", "420");//二维码框线
        qrcode(680, 190, "H", "7", "A", "0", "M2", "S7", "http://jdwx.goldemperor.com/aspx/FACard/CardInfo.aspx?FInterID=" + FInterID);
        windowsfont(200, 50, tf, 60, "金帝集团股份有限公司");
        windowsfont(50, 210, tf, 45, "财产名称：" + Name);
        windowsfont(50, 320, tf, 45, "财产编号：" + Number);
        windowsfont(50, 430, tf, 45, "购置日期：" + Date);

        printlabel(1, 1);
        String status2 = GetStatus();
        Log.e("Pan", "当前状态=" + status2);
        if (status2.equals(status1) && status2.equals("准备就绪")) {
            obl.Print(true, "打印完成");
        } else {
            obl.Print(false, status2);
        }
        closeport(3000);
    }

    /**
     * 链接打印机
     *
     * @param address MAC地址
     * @return
     */
    public static String openport(String address) {
        BluetoothDevice device;
        BluetoothAdapter mBluetoothAdapter;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            IsConnected = true;
            device = mBluetoothAdapter.getRemoteDevice(address);

            try {
                btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException var8) {
                return "-1";
            }

            mBluetoothAdapter.cancelDiscovery();

            try {
                btSocket.connect();
                OutStream = btSocket.getOutputStream();
                InStream = btSocket.getInputStream();
                return "1";
            } catch (IOException var7) {
                return "-1";
            }
        } else {
            IsConnected = false;
            return "-1";
        }
    }

    /**
     * 打印标签
     *
     * @param quantity
     * @param copy
     * @return
     */
    public static String printlabel(int quantity, int copy) {
        String message;
        message = "PRINT " + quantity + ", " + copy + "\r\n";
        byte[] msgBuffer = message.getBytes();
        if (OutStream != null && InStream != null) {
            try {
                OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var6) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    /**
     * 关闭端口
     *
     * @param timeout
     * @return
     */
    public static String closeport(int timeout) {
        try {
            Thread.sleep((long) timeout);
        } catch (InterruptedException var5) {
            var5.printStackTrace();
        }

        if (btSocket.isConnected()) {
            try {
                IsConnected = false;
                btSocket.close();
            } catch (IOException var4) {
                return "-1";
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }

            return "1";
        } else {
            return "-1";
        }
    }

    /**
     * 打印文本
     *
     * @param x_coordinates X坐标
     * @param y_coordinates Y坐标
     * @param typeface      文本字体
     * @param fontsize      文字大小
     * @param textToPrint   文字内容
     * @return
     */
    private static String windowsfont(int x_coordinates, int y_coordinates, Typeface typeface, int fontsize, String textToPrint) {
        Bitmap original_bitmap = null;
        Bitmap gray_bitmap;
        Bitmap binary_bitmap;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
//        Typeface typeface = Typeface.createFromAsset(mActivity.getAssets(), AssetsPath);
        paint.setTypeface(typeface);
        paint.setTextSize((float) fontsize);
        TextPaint textpaint = new TextPaint(paint);
        StaticLayout staticLayout = new StaticLayout(textToPrint, textpaint, 832, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
        int height = staticLayout.getHeight();
        int width = (int) Layout.getDesiredWidth(textToPrint, textpaint);
        if (height > 2378) {
            height = 2378;
        }

        try {
            original_bitmap = Bitmap.createBitmap(width + 8, height, Bitmap.Config.RGB_565);
            Canvas c = new Canvas(original_bitmap);
            c.drawColor(-1);
            c.translate(0.0F, 0.0F);
            staticLayout.draw(c);
        } catch (IllegalArgumentException var32) {
        } catch (OutOfMemoryError var33) {
        }

        gray_bitmap = bitmap2Gray(original_bitmap);
        binary_bitmap = gray2Binary(gray_bitmap);
        String x_axis = Integer.toString(x_coordinates);
        String y_axis = Integer.toString(y_coordinates);
        String picture_wdith = Integer.toString((binary_bitmap.getWidth() + 7) / 8);
        String picture_height = Integer.toString(binary_bitmap.getHeight());
        String mode = Integer.toString(0);
        String command = "BITMAP " + x_axis + "," + y_axis + "," + picture_wdith + "," + picture_height + "," + mode + ",";
        byte[] stream = new byte[(binary_bitmap.getWidth() + 7) / 8 * binary_bitmap.getHeight()];
        int Width_bytes = (binary_bitmap.getWidth() + 7) / 8;
        int Width = binary_bitmap.getWidth();
        int Height = binary_bitmap.getHeight();

        int y;
        for (y = 0; y < Height * Width_bytes; ++y) {
            stream[y] = -1;
        }

        for (y = 0; y < Height; ++y) {
            for (int x = 0; x < Width; ++x) {
                int pixelColor = binary_bitmap.getPixel(x, y);
                int colorR = Color.red(pixelColor);
                int colorG = Color.green(pixelColor);
                int colorB = Color.blue(pixelColor);
                int total = (colorR + colorG + colorB) / 3;
                if (total == 0) {
                    stream[y * ((Width + 7) / 8) + x / 8] ^= (byte) (128 >> x % 8);
                }
            }
        }

        sendcommand(command);
        sendcommand(stream);
        sendcommand("\r\n");
        return "1";
    }


    /**
     * 画线
     *
     * @param x      X坐标
     * @param y      Y坐标
     * @param width  宽
     * @param height 高
     * @return
     */
    public static String bar(String x, String y, String width, String height) {
        String message = "BAR " + x + "," + y + "," + width + "," + height + "\r\n";
        byte[] msgBuffer = message.getBytes();
        if (OutStream != null && InStream != null) {
            try {
                OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var8) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    /**
     * 清楚缓存
     *
     * @return
     */
    public static String clearbuffer() {
        String message = "CLS\r\n";
        byte[] msgBuffer = message.getBytes();
        if (OutStream != null && InStream != null) {
            try {
                OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var4) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    /**
     * 打印二维码
     *
     * @param x        X坐标
     * @param y        Y坐标
     * @param ecc      纠错等级 L:7% M:15% Q:25% H：30%
     * @param cell     线宽  1~10
     * @param mode     模式  A:自动 M:手动
     * @param rotation 旋转 0 90 180 270
     * @param model    模式 M1:(默认)原始版本 M2:扩大版本
     * @param mask     S0~S8, 默认为 S7
     * @param content  内容
     * @return
     */
    public static String qrcode(int x, int y, String ecc, String cell, String mode, String rotation, String model, String mask, String content) {
        String message = "QRCODE " + x + "," + y + "," + ecc + "," + cell + "," + mode + "," + rotation + "," + model + "," + mask + "," + "\"" + content + "\"" + "\r\n";
        byte[] msgBuffer = message.getBytes();
        if (OutStream != null && InStream != null) {
            try {
                OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var13) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    /**
     * 打印设置
     *
     * @param width           打印纸宽mm
     * @param height          打印纸高mm
     * @param speed           打印速度 1.0 1.5 2 3 4 6 8 10
     * @param density         打印浓度 0~15
     * @param sensor          感应器列表 0:垂直間距感測器(gap sensor) 1:黑標感測器(black mark )
     * @param sensor_distance 打印纸间距
     * @param sensor_offset   打印纸偏移
     * @return
     */
    public static String setup(int width, int height, int speed, int density, int sensor, int sensor_distance, int sensor_offset) {
        String message;
        String size = "SIZE " + width + " mm" + ", " + height + " mm";
        String speed_value = "SPEED " + speed;
        String density_value = "DENSITY " + density;
        String sensor_value = "";
        if (sensor == 0) {
            sensor_value = "GAP " + sensor_distance + " mm" + ", " + sensor_offset + " mm";
        } else if (sensor == 1) {
            sensor_value = "BLINE " + sensor_distance + " mm" + ", " + sensor_offset + " mm";
        }

        message = size + "\r\n" + speed_value + "\r\n" + density_value + "\r\n" + sensor_value + "\r\n";
        byte[] msgBuffer = message.getBytes();
        if (OutStream != null && InStream != null) {
            try {
                OutStream.write(msgBuffer);
                return "1";
            } catch (IOException var15) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    /**
     * 获取当前打印机状态
     *
     * @return
     */
    private static String GetStatus() {
        // 00 准备就绪
        // 01 打印头开启
        // 02 纸张卡纸
        // 03 打印头开启并且纸张卡纸
        // 04 纸张缺纸
        // 05 打印头开启并且纸张缺纸
        // 08 无碳带
        // 09 打印头开启并且无碳带
        // 0A 纸张卡纸并且无碳带
        // 0B 打印头开启、纸张卡纸并且无碳带
        // 0C 纸张缺纸并且无碳带
        // 0D 打印头开启、纸张缺纸并且无碳带
        // 10 暂停
        // 20 打印中
        // 80 其它
        String sta = printerstatus();
        String ret = "";
        switch (sta) {
            case "00":
                ret = "准备就绪";
                break;
            case "01":
                ret = "打印头开启";
                break;
            case "02":
                ret = "纸张卡纸";
                break;
            case "03":
                ret = "打印头开启并且纸张卡纸";
                break;
            case "04":
                ret = "纸张缺纸";
                break;
            case "05":
                ret = "打印头开启并且纸张缺纸";
                break;
            case "08":
                ret = "无碳带";
                break;
            case "09":
                ret = "打印头开启并且无碳带";
                break;
            case "0A":
                ret = "纸张卡纸并且无碳带";
                break;
            case "0B":
                ret = "打印头开启、纸张卡纸并且无碳带";
                break;
            case "0C":
                ret = "纸张缺纸并且无碳带";
                break;
            case "0D":
                ret = "打印头开启、纸张缺纸并且无碳带";
                break;
            case "10":
                ret = "暂停";
                break;
            case "20":
                ret = "打印中";
                break;
            case "80":
                ret = "其它";
                break;


        }

        return ret.equals("") ? "未知状态:" + sta : ret;
    }

    public static String sendcommand(String message) {
        byte[] msgBuffer = message.getBytes();
        if (OutStream == null)
            return "-1";
        try {
            OutStream.write(msgBuffer);
            return "1";
        } catch (IOException var4) {
            return "-1";
        }
    }

    public static String sendcommand(byte[] message) {
        if (OutStream != null && InStream != null) {
            try {
                OutStream.write(message);
                return "1";
            } catch (IOException var3) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public static Bitmap gray2Binary(Bitmap graymap) {
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        Bitmap binarymap = null;
        binarymap = graymap.copy(Bitmap.Config.ARGB_8888, true);

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                int col = binarymap.getPixel(i, j);
                int alpha = col & -16777216;
                int red = (col & 16711680) >> 16;
                int green = (col & '\uff00') >> 8;
                int blue = col & 255;
                int gray = (int) ((double) ((float) red) * 0.3D + (double) ((float) green) * 0.59D + (double) ((float) blue) * 0.11D);
                if (gray <= 127) {
                    gray = 0;
                } else {
                    gray = 255;
                }

                int newColor = alpha | gray << 16 | gray << 8 | gray;
                binarymap.setPixel(i, j, newColor);
            }
        }

        return binarymap;
    }

    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        Bitmap bmpGray;
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.0F);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0.0F, 0.0F, paint);
        return bmpGray;
    }


    public static String printerstatus() {
        byte[] message = new byte[]{27, 33, 63};
        String query = "";
        if (OutStream != null && InStream != null) {
            try {
                OutStream.write(message);
            } catch (IOException var5) {
                return "-1";
            }

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            try {
                while (InStream.available() > 0) {
                    int var3 = InStream.read(readBuf);
                }
            } catch (IOException var6) {
                return "-1";
            }

            if (readBuf[0] == 0) {
                query = "00";
            } else if (readBuf[0] == 1) {
                query = "01";
            } else if (readBuf[0] == 2) {
                query = "02";
            } else if (readBuf[0] == 3) {
                query = "03";
            } else if (readBuf[0] == 4) {
                query = "04";
            } else if (readBuf[0] == 5) {
                query = "05";
            } else if (readBuf[0] == 8) {
                query = "08";
            } else if (readBuf[0] == 9) {
                query = "09";
            } else if (readBuf[0] == 10) {
                query = "0A";
            } else if (readBuf[0] == 11) {
                query = "0B";
            } else if (readBuf[0] == 12) {
                query = "0C";
            } else if (readBuf[0] == 13) {
                query = "0D";
            } else if (readBuf[0] == 16) {
                query = "10";
            } else if (readBuf[0] == 32) {
                query = "20";
            } else if (readBuf[0] == 128) {
                query = "80";
            }

            return query;
        } else {
            return "-1";
        }
    }


}
