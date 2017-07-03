package com.atguigu.guigusocial.model;

import android.content.Context;

import com.atguigu.guigusocial.bean.UserInfo;
import com.atguigu.guigusocial.common.GlobalListener;
import com.atguigu.guigusocial.model.dao.AccountDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/7/1.
 */

public class Model {

    private Context context;

    /**
     * 登录者 数据操作的实例
     */
    private AccountDAO accountDAO;
    /**
     * 全局监听
     */
    private GlobalListener globalListener;

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
        //创建AccountDao的实例，用于对数据的操作
        accountDAO = new AccountDAO(context);

        globalListener = new GlobalListener(context);
    }

    /**
     * 创建线程池
     */
    private  ExecutorService service = Executors.newCachedThreadPool();

    public ExecutorService getGlobalThread(){
        return service;
    }

    /**
     * 登录成功保存数据
     * @param userInfo
     */
    public void successLogin(UserInfo userInfo){
        accountDAO.addAccount(userInfo);

    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }
}
