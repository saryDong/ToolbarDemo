package com.example.abu.toolbardemo.wecome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.example.abu.toolbardemo.MainActivity;
import com.example.abu.toolbardemo.R;
import com.example.abu.toolbardemo.databinding.ActivityWelcomeBinding;
import com.example.abu.toolbardemo.wecome.utils.SharedPreferencesUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * app启动的第一个活动，此活动有以下功能
 * 1.从SharedPreferences文件获取是否第一次打开的信息
 * 2.判断是否第一次打开是第一次打开则进入功能引导页
 * 3.若不是则加载当前界面并以观察者模式执行显示动画，动画结束时进入主界面并销毁当前活动，
 *    进入当前活动后屏蔽物理返回键避免退出
 */
public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding mBinding;
    private static final int ANIM_TIME=2000;

    private static final float SCALE_END=1.15F;

    private static final int[] Imgs={
            R.drawable.welcomimg1,R.drawable.welcomimg2,
            R.drawable.welcomimg3,R.drawable.welcomimg4,
            R.drawable.welcomimg5, R.drawable.welcomimg6,
            R.drawable.welcomimg7,R.drawable.welcomimg8,
            R.drawable.welcomimg9,R.drawable.welcomimg10,
            R.drawable.welcomimg11,R.drawable.welcomimg12};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //判断是否是第一次开启应用
        boolean isFirstLaunch= SharedPreferencesUtil.getBoolean(this,SharedPreferencesUtil.FIRST_OPEN,true);
        //如果第一次启动，则先进入功能引导页
        if (isFirstLaunch){
            startActivity(new Intent(this,WelcomeGuidActivity.class));
            finish();
            return;
        }
        //如果不是第一次启动
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_welcome);
        startMainActivity();

    }

    private void startMainActivity() {
        //SystemClock.elapsedRealtime():从开机到现在的毫秒数，包括睡眠
        Random random=new Random(SystemClock.elapsedRealtime());
        mBinding.ivEntry.setImageResource(Imgs[random.nextInt(Imgs.length)]);

        //观察者模式
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startAnim();
                    }
                });

    }

    private void startAnim() {
        ObjectAnimator animatorX=ObjectAnimator.ofFloat(mBinding.ivEntry,"scaleX",1f,SCALE_END);
        ObjectAnimator animatorY=ObjectAnimator.ofFloat(mBinding.ivEntry,"scaleY",1f,SCALE_END);

        AnimatorSet set=new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();
        //当动画结束时进入主界面并销毁当前活动
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this).toBundle());
                WelcomeActivity.this.finish();
            }
        });

    }

    /**
     * 屏蔽物理返回按钮
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
