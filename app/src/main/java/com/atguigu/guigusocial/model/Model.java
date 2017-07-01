package com.atguigu.guigusocial.model;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/7/1.
 */

public class Model {

    private Context context;

    private Model(){
    }

    private static Model model = new Model();

    public static Model getInstance(){

        return model;
    }

    /**
     * 用于初始化
     * @param context
     */
    public void init(Context context){
        this.context = context;
    }

    /**
     * 创建线程池
     */
    private  ExecutorService service = Executors.newCachedThreadPool();

    public ExecutorService getGlobalThread(){

        return service;
    }


}
