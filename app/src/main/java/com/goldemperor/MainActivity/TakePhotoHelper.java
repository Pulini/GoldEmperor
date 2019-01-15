package com.goldemperor.MainActivity;

import android.net.Uri;
import android.os.Environment;

import com.goldemperor.Utils.LOG;

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
    final private int takephoto =2;
    final private int etSize=204800;
    private int etCropWidth=800;
    private int etHeightPx=800;
    private int etLimit=1;
    private File file;
    private String fileName;
    public void init(TakePhoto takePhoto, int type, boolean rbCropYes, int etLimit, int etCropWidth, int etHeightPx) {
        fileName =System.currentTimeMillis() + ".jpg";
        file=new File(Environment.getExternalStorageDirectory(), "/pictures/"+fileName);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        LOG.e("imageUri="+imageUri.getPath());
        this.etCropWidth=etCropWidth;
        this.etHeightPx=etHeightPx;
        this.etLimit=etLimit;

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        switch (type){
            case SelectPhoto:
                int limit= etLimit;
                if(limit>1){
                    if(rbCropYes){
                        takePhoto.onPickMultipleWithCrop(limit,getCropOptions());
                    }else {
                        takePhoto.onPickMultiple(limit);
                    }
                    return;
                }
                else {
                    if(rbCropYes){
                        takePhoto.onPickFromGalleryWithCrop(imageUri,getCropOptions());
                    }else {
                        takePhoto.onPickFromGallery();
                    }
                }
                break;
            case takephoto:
                if(rbCropYes){
                    takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());
                }else {
                    takePhoto.onPickFromCapture(imageUri);
                }
                break;
            default:
                break;
        }
    }

    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();

        builder.setWithOwnGallery(true);
        builder.setCorrectImage(false);
        takePhoto.setTakePhotoOptions(builder.create());

    }
    private void configCompress(TakePhoto takePhoto){
        int maxSize= etSize;
        int width= etCropWidth;
        int height= etHeightPx;
        boolean showProgressBar=true;
        boolean enableRawFile = false;
        CompressConfig config;

        /*
        config=new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width>=height? width:height)
                    .enableReserveRaw(enableRawFile)
                    .create();
                    */
        LubanOptions option=new LubanOptions.Builder()
                .setMaxHeight(etHeightPx)
                .setMaxWidth(etCropWidth)
                .setMaxSize(maxSize)
                .create();
        config=CompressConfig.ofLuban(option);

        takePhoto.onEnableCompress(config,showProgressBar);


    }
    private CropOptions getCropOptions(){
        int height= etHeightPx;
        int width= etCropWidth;
        boolean withWonCrop=true;

        CropOptions.Builder builder=new CropOptions.Builder();

        builder.setAspectX(width).setAspectY(height);

        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

}
