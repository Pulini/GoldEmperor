package com.goldemperor.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Widget.fancybuttons.FancyButton;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import id.zelory.compressor.Compressor;



/**
 * File Name : AutographActivity
 * Created by : PanZX on  2018/8/3 08:18
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：签名
 */
@ContentView(R.layout.activity_autograph)
public class AutographActivity extends Activity {

    @ViewInject(R.id.IV_Canvas)
    private ImageView IV_Canvas;

    @ViewInject(R.id.FB_Rewrite)
    private FancyButton FB_Rewrite;

    @ViewInject(R.id.FB_Save)
    private FancyButton FB_Save;


    private Paint paint;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private File compressedImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        FB_Rewrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeCanvas();
            }
        });
        FB_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitmap();

            }
        });
        IV_Canvas.setOnTouchListener(touch);
    }

    private void setPC() {
        if(baseBitmap!=null){
            return;
        }
        // 初始化一个画笔，笔触宽度为10，颜色为黑色
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        // 初始化一个画布，颜色为白色
        baseBitmap = Bitmap.createBitmap(IV_Canvas.getWidth(),
                IV_Canvas.getHeight(), Bitmap.Config.RGB_565);
        canvas = new Canvas(baseBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
    }

    private View.OnTouchListener touch = new View.OnTouchListener() {
        // 定义手指开始触摸的坐标
        float startX;
        float startY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                // 用户按下动作
                case MotionEvent.ACTION_DOWN:
                    setPC();
                    // 记录开始触摸的点的坐标
                    startX = event.getX();
                    startY = event.getY();
                    break;
                // 用户手指在屏幕上移动的动作
                case MotionEvent.ACTION_MOVE:
                    // 记录移动位置的点的坐标
                    float stopX = event.getX();
                    float stopY = event.getY();

                    //根据两点坐标，绘制连线
                    canvas.drawLine(startX, startY, stopX, stopY, paint);

                    // 更新开始点的位置
                    startX = event.getX();
                    startY = event.getY();

                    // 把图片展示到ImageView中
                    IV_Canvas.setImageBitmap(baseBitmap);
                    break;
                case MotionEvent.ACTION_UP:

                    break;
                default:
                    break;
            }
            return true;
        }
    };


    /**
     * 保存图片到SD卡上
     */
    protected void saveBitmap() {
        try {
            // 保存图片到SD卡上
            File file = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".webp");
            FileOutputStream stream = new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.WEBP, 100, stream);
            try {
                compressedImage = new Compressor(this)
                        .setMaxWidth(300)
                        .setMaxHeight(100)
                        .setQuality(7)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .compressToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            Toast.makeText(this, "保存图片成功", Toast.LENGTH_LONG).show();
            LOG.e("保存成功：" + file.getAbsolutePath());

        } catch (FileNotFoundException e) {
//            Toast.makeText(this, "保存图片失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    /**
     * 清除画板
     */
    protected void resumeCanvas() {
        // 手动清除画板的绘图，重新创建一个画板
        if (baseBitmap != null) {
            baseBitmap = Bitmap.createBitmap(IV_Canvas.getWidth(),
                    IV_Canvas.getHeight(), Bitmap.Config.RGB_565);
            canvas = new Canvas(baseBitmap);
            canvas.drawColor(Color.WHITE);
            IV_Canvas.setImageBitmap(baseBitmap);
//            Toast.makeText(this, "清除画板成功，可以重新开始绘图", Toast.LENGTH_LONG).show();
        }
    }
}



