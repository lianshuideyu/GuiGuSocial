package com.atguigu.guigusocial.view.activity;

import android.content.Intent;
import android.os.CountDownTimer;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.common.BaseActivity;
import com.atguigu.guigusocial.view.MainActivity;
import com.hyphenate.chat.EMClient;

public class SplashActivity extends BaseActivity {

    private CountDownTimer countDownTimer;

    @Override
    public void initListener() {

        /**
         * 第一个参数是倒计时的总时长
         * 第二个参数为时间的间隔
         */
        countDownTimer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long l) {
//                Log.e("TAG","onTick" + l);
            }

            @Override
            public void onFinish() {
                //跳转到的Activity
                SelectActivity();
//                Log.e("TAG","onFinish");
            }
        }.start();

    }

    private void SelectActivity() {

        /**
         * 检测是否登录过为耗时操作，在子线程执行
         */
        new Thread(){
            public void run(){

                boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
//                Log.e("TAG","run--" + loggedInBefore);
                if(loggedInBefore) {//之前登录过直接到主页面
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {//没有登录过到登录页面
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }.start();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onPause() {
        super.onPause();

        countDownTimer.cancel();//取消时间延迟作用

        finish();
    }
}
