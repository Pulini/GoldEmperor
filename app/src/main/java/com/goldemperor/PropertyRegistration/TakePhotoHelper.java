package com.goldemperor.PropertyRegistration;

import android.net.Uri;
import android.os.Environment;


import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.cboy.me
 * GitHub:https://github.com/crazycodeboy
 * Eamil:crazycodeboy@gmail.com
 */
public class TakePhotoHelper {

    final private int SelectPhoto=1;
    final private int TakePhoto=2;
    final private int etSize=102400;

    private int CropWidth=800;
    private int CropHeight=800;
    private TakePhoto TP;

    /**
     * 初始化
     * @param takePhoto TakePhoto工具类对象
     */
    public TakePhotoHelper(TakePhoto takePhoto){
        TP=takePhoto;
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        //使用自带相册
        builder.setWithOwnGallery(true);
        //旋转图片
        builder.setCorrectImage(false);
        configCompress();
        TP.setTakePhotoOptions(builder.create());

    }

    /**
     * 拍照并压缩图片
     */
    public void TakePhoto(){
        File file = new File(Environment.getExternalStorageDirectory(), "/GoldEmperor/" + System.currentTimeMillis() + ".JPEG");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        TP.onPickFromCapture(imageUri);
    }

    /**
     * 选取照片并压缩
     * @param Limit
     */
    public void SelectPhoto(int Limit){
        if(Limit>1){
            TP.onPickMultiple(Limit);
        }else{
            TP.onPickFromGallery();
        }

    }

    /**
     * 图片压缩
     */
    private void configCompress(){
        CompressConfig config;
        LubanOptions option=new LubanOptions.Builder()
                .setMaxHeight(CropHeight)
                .setMaxWidth(CropWidth)
                .setMaxSize(etSize)
                .create();
        config= CompressConfig.ofLuban(option);
        TP.onEnableCompress(config,true);
    }

    /**
     * 图片裁剪
     * @return
     */
    private CropOptions getCropOptions(){
        CropOptions.Builder builder=new CropOptions.Builder();
        builder.setAspectX(CropWidth).setAspectY(CropHeight);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

}
