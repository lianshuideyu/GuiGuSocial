package com.atguigu.guigusocial.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atguigu.guigusocial.model.table.AccountTable;

/**
 * Created by Administrator on 2017/7/1.
 */

/*
*
create table student(id text primary key,name text)

insert into student(id,name) values('10','小福')

insert into student(name) values('小福')

insert into student(id,name) values('1','小仓')

insert into student(id,name) values('2','志玲')

update student set  id = '3' where name = '志玲'

select name from student where id = '1'

delete from student where id = '1'
*/

/**
 * 数据库
 * 操作数据库的创建和更新数据
 */
public class AccountDB extends SQLiteOpenHelper{


    public AccountDB(Context context) {
        //把除了context的参数  写为固定的就行
        super(context, "account.db", null, 1);//上下文，数据库名称，CursorFactory，版本
    }

    /**
     * 数据库的创建
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(AccountTable.CREATE_TABLE);//创建数据库
    }

    /**
     * 数据库的更新
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
