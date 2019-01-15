package com.goldemperor.Widget;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.WindowManager;

import com.goldemperor.Utils.LOG;

//import cn.jzvd.JZMediaManager;
//import cn.jzvd.JZUtils;
//import cn.jzvd.JzvdStd;

/**
 * File Name : JzvdStdAutoCompleteAfterFullscreen
 * Created by : PanZX on  2018/10/24 08:05
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
//public class JzvdStdAutoCompleteAfterFullscreen extends JzvdStd {
//    public JzvdStdAutoCompleteAfterFullscreen(Context context) {
//        super(context);
//    }
//
//    public JzvdStdAutoCompleteAfterFullscreen(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public void startVideo() {
//        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
//            LOG.E("startVideo [" + this.hashCode() + "] ");
//            initTextureView();
//            addTextureView();
//            AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
//            mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
//            JZUtils.scanForActivity(getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//            JZMediaManager.setDataSource(jzDataSource);
//            JZMediaManager.instance().positionInList = positionInList;
//            onStatePreparing();
//        } else {
//            super.startVideo();
//            super.onStatePreparing();
//
//        }
//    }
//
//    @Override
//    public void onAutoCompletion() {
//        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
//            onStateAutoComplete();
//        } else {
//            super.onAutoCompletion();
//        }
//    }
//}
