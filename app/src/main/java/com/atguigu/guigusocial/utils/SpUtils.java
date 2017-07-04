package com.atguigu.guigusocial.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/7/4.
 */

public class SpUtils {

    public static final String NEW_INVITE = "newInvite";
    /**
     * 该sp用于记录 消息 中小红点的状态
     */

    private SharedPreferences sp;

    private SpUtils() {

    }

    private static SpUtils spUtils = new SpUtils();

    public static SpUtils getInstance() {
        return spUtils;
    }

    /**
     * 初始化sp
     */
    public void init(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);


    }

    /**
     * 保存数据
     */
    public void saveSp(String key, Object object) {
        SharedPreferences.Editor edit = sp.edit();

        if (object instanceof Boolean) {

            edit.putBoolean(key, (Boolean) object).commit();
        }

        if (object instanceof String) {
            edit.putString(key, (String) object).commit();
        }
    }

    public boolean getBoolean(String key){

        return sp.getBoolean(key,false);
    }

    public String getString(String key){

        return sp.getString(key,"");
    }
}
