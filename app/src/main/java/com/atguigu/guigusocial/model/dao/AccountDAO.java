package com.atguigu.guigusocial.model.dao;

/**
 * Created by Administrator on 2017/7/1.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.guigusocial.bean.UserInfo;
import com.atguigu.guigusocial.model.db.AccountDB;
import com.atguigu.guigusocial.model.table.AccountTable;

/**
 * 这里对数据库进行
 * 增删改查
 */
public class AccountDAO {


    private AccountDB accountDB;

    public AccountDAO(Context context) {

        //初始化数据的实例
        this.accountDB = new AccountDB(context);
    }

    /**
     * 添加用户
     */
    public void addAccount(UserInfo userInfo){
        if(userInfo == null) {
            throw new NullPointerException("userInfo不能为空!!!");
        }

        //连接数据库
        SQLiteDatabase database = accountDB.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(AccountTable.COL_USERNAME,userInfo.getName());
        values.put(AccountTable.COL_PHOTO,userInfo.getPhoto());
        values.put(AccountTable.COL_HXID,userInfo.getHxId());
        values.put(AccountTable.COL_NICK,userInfo.getNick());

        database.replace(AccountTable.TABLE_NAME,null,values);

        /**
         * 这里先不关闭数据库
         * 后边还要用
         */
    }


    /**
     * 查询用户
     * 根据用户的环信 id 查找用户
     */
    public UserInfo getUserInfo(String hxid){
        if(TextUtils.isEmpty(hxid)) {
         //数据为空
            return null;
        }
        //连接数据库
        SQLiteDatabase database = accountDB.getWritableDatabase();

        String sql = "select * from "+AccountTable.TABLE_NAME
                +" where "+AccountTable.COL_HXID+"=?";
//该方法有问题，有待修改String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy
//        Cursor cursor = database.query(AccountTable.TABLE_NAME, null, sql, new String[]{hxid}, null, null, null);

        //方法二
        Cursor cursor = database.rawQuery(sql, new String[]{hxid});
        UserInfo userInfo = new UserInfo();

        if(cursor.moveToNext()) {//这里查一次就行了，所以就不用while，用if就ok了
            userInfo.setName(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USERNAME)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(AccountTable.COL_PHOTO)));
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(AccountTable.COL_HXID)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(AccountTable.COL_NICK)));

        }
        //关闭光标
        cursor.close();


        return userInfo;
    }


}
