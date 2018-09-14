package com.goldemperor.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

//import com.andrognito.patternlockview.listener.PatternLockViewListener;
//import com.andrognito.patternlockview.utils.PatternLockUtils;
//import com.andrognito.patternlockview.utils.ResourceUtils;
import com.goldemperor.R;

import org.xutils.image.ImageOptions;

import java.util.List;

/**
 * Created by Nova on 2017/7/24.
 */

public class PatternLockActivity extends AppCompatActivity {
/**
//    private com.andrognito.patternlockview.PatternLockView mPatternLockView;

    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;

    private String Stauts="0";
    private String SetPatternLock="";
    private TextView profile_name;
    private Context mContext;

    //图片网络加载设置
    ImageOptions imageOptions;

    private ImageView profile_image;
    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.e("pattern", "Pattern drawing started");
        }

        @Override
        public void onProgress(List<com.andrognito.patternlockview.PatternLockView.Dot> progressPattern) {
            Log.e("pattern", "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<com.andrognito.patternlockview.PatternLockView.Dot> pattern) {
            Log.e("pattern", "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
            if(Stauts.equals("请设置密码")){
                SetPatternLock= PatternLockUtils.patternToString(mPatternLockView, pattern);
                Stauts="请重新绘制密码";
                profile_name.setText(Stauts);
                mPatternLockView.clearPattern();
            }
            else if (Stauts.equals("请重新绘制密码")||Stauts.equals("两次密码不一致,请重新绘制密码")){
                if(!PatternLockUtils.patternToString(mPatternLockView, pattern).equals(SetPatternLock)){
                    Stauts="两次密码不一致,请重新绘制密码";
                    profile_name.setText(Stauts);
                    mPatternLockView.clearPattern();
                }else{
                    dataEditor.putString(define.SharedPatternLock, PatternLockUtils.patternToString(mPatternLockView, pattern));
                    dataEditor.commit();
                    Stauts="解锁成功";
                    profile_name.setText(Stauts);
                    Intent i = new Intent(mContext, ContentActivity.class);
                    mContext.startActivity(i);
                    finish();
                }
            }else {
                if(!PatternLockUtils.patternToString(mPatternLockView, pattern).equals(dataPref.getString(define.SharedPatternLock, define.NONE))){
                    Stauts="密码错误,请重新绘制密码";
                    profile_name.setText(Stauts);
                    mPatternLockView.clearPattern();
                }else{
                    Stauts="解锁成功";
                    profile_name.setText(Stauts);
                    Intent i = new Intent(mContext, ContentActivity.class);
                    mContext.startActivity(i);
                    finish();
                }
            }
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pattern_lock);
        //隐藏标题栏
        getSupportActionBar().hide();

        //设置图片加载属性
        imageOptions = new ImageOptions.Builder()
                .setLoadingDrawableId(R.drawable.loading)
                .setFailureDrawableId(R.drawable.loading_failure)
                .setUseMemCache(true)
                .build();

        mContext=this;
        dataPref = this.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();

        profile_image=(ImageView)findViewById(R.id.profile_image);

        profile_name=(TextView)findViewById(R.id.profile_name);


        if(dataPref.getString(define.SharedPatternLock, define.NONE).equals(define.NONE)){
            Stauts="请设置密码";
            profile_name.setText(Stauts);
        }else{
            Stauts="请绘制密码";
            profile_name.setText(Stauts);
        }

        mPatternLockView = (com.andrognito.patternlockview.PatternLockView) findViewById(R.id.patter_lock_view);
        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(com.andrognito.patternlockview.PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(com.andrognito.patternlockview.PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.white));
        mPatternLockView.setInStealthMode(false);
        mPatternLockView.setTactileFeedbackEnabled(false);
        mPatternLockView.setInputEnabled(true);

        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

    }
    */
}

