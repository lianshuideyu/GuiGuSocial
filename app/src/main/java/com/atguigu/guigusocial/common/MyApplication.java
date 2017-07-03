package com.atguigu.guigusocial.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.atguigu.guigusocial.model.Model;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by Administrator on 2017/7/1.
 */

public class MyApplication extends Application {

    private static Context context;

    private static Handler handler;

    private static int pid;



    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //是否自动添加群组
        options.setAutoAcceptGroupInvitation(false);
//初始化
        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        /**
         * 初始化Model
         *
         */
        Model.getInstance().init(this);

        context = this;

        handler = new Handler();
        //当前线程的  id
        pid = android.os.Process.myPid();
    }

    /**
     * 返回上下文
     * @return
     */
    public static Context getContext(){

        return context;
    }

    public static int getPid() {
        return pid;
    }

    public static Handler getHandler() {
        return handler;
    }
}
