package com.atguigu.guigusocial.model.table;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ContactsTable {

    /*
   * 注意 ：
   *       1 把sql语句一定要确定没有问题
   *       2 数据库创建有问题的时候，如果需要重新创建 一定要卸载原来的应用
   *
   * */
    public static final String TABLE_NAME = "contacts";
    public static final String COL_USERNAME = "username";
    public static final String COL_HXID = "hxid";
    public static final String COL_PHOTO = "photo";
    public static final String COL_NICK = "nick";
    public static final String COL_IS_CONTACTS = "contacts";


    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + COL_HXID + " text primary key,"
            + COL_USERNAME + " text,"
            + COL_PHOTO + " text,"
            + COL_NICK + " text,"
            + COL_IS_CONTACTS + " integer)"
            ;
}
