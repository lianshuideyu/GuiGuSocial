package com.atguigu.guigusocial.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atguigu.guigusocial.model.table.ContactsTable;
import com.atguigu.guigusocial.model.table.InvitationTable;

/**
 * Created by Administrator on 2017/7/3.
 */

public class HelperDB extends SQLiteOpenHelper {


    public HelperDB(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /**
         * 创建 联系人和 邀请消息的数据库
         */
        sqLiteDatabase.execSQL(ContactsTable.CREATE_TABLE);
        sqLiteDatabase.execSQL(InvitationTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
