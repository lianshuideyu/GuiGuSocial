package com.atguigu.guigusocial.utils;

import android.content.Context;
import android.widget.Toast;

import com.atguigu.guigusocial.common.MyApplication;

/**
 * Created by Administrator on 2017/7/3.
 */

public class UIUtils {


    public static Context getContext(){

        return MyApplication.getContext();
    }

    /**
     * 保证 runnable在主线程执行
     * @param runnable
     */
    public static void UIThread(Runnable runnable){

        if(MyApplication.getPid() == android.os.Process.myTid()) {
            //当前就是主线程
            runnable.run();
        }else {
            //当前为分线程 需要切换到主线程
            MyApplication.getHandler().post(runnable);

        }
    }

    /**
     * 保证toast在主线程
     * @param message
     */
    public static void showToast(final String message){

        UIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
