package com.goldemperor.PgdActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * File Name : VideoPlayerActivity
 * Created by : PanZX on  2018/10/23 09:40
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.activity_video_player)
public class VideoPlayerActivity extends Activity {
    //    @ViewInject(R.id.JZ_Video)
//    private JzvdStdAutoCompleteAfterFullscreen JZ_Video;
    @ViewInject(R.id.SGSYVP_Video)
    private StandardGSYVideoPlayer SGSYVP_Video;

    //    String url = "http://192.168.99.79:8083/金帝集团股份有限公司/视频/手工鞋子制作过程_高清.mp4";
//    String url2 = "http://192.168.99.79:8083/Videos/MakeShoes.mp4";
    private OrientationUtils orientationUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        x.view().inject(this);
//        Jzvd.clearSavedProgress(this, url);
//        JZ_Video.setUp(url2, "视频教程", Jzvd.SCREEN_WINDOW_NORMAL);
//        JZ_Video.startVideo();

        String url = getIntent().getExtras().getString("path");
        LOG.e("URL="+url);
        SGSYVP_Video.setUp(url, true, "视频教程");

        //增加封面
//        ImageView imageView = new ImageView(this);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.xxx1);
//        videoPlayer.setThumbImageView(imageView);
        //增加title
        SGSYVP_Video.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        SGSYVP_Video.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, SGSYVP_Video);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        SGSYVP_Video.getFullscreenButton().setOnClickListener(v -> orientationUtils.resolveByClick());
        //是否可以滑动调整
        SGSYVP_Video.setIsTouchWiget(true);
        //设置返回按键功能
        SGSYVP_Video.getBackButton().setOnClickListener(v -> onBackPressed());
        SGSYVP_Video.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SGSYVP_Video.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SGSYVP_Video.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            SGSYVP_Video.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        SGSYVP_Video.setVideoAllCallBack(null);
        super.onBackPressed();
    }

//    @Override
//    public void onBackPressed() {
//        if (Jzvd.backPress()) {
//            return;
//        }
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Jzvd.releaseAllVideos();
//    }
}
