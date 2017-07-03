package com.atguigu.guigusocial.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.guigusocial.bean.UserInfo;
import com.atguigu.guigusocial.model.db.HelperDB;
import com.atguigu.guigusocial.model.table.ContactsTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ContactsDAO {

    private final HelperDB helperDB;

    public ContactsDAO(HelperDB helperDB) {

        this.helperDB = helperDB;
    }

    /**
     * 查询所有联系人
     * COL_IS_CONTACTS 为1 时 即为联系人
     */
    public List<UserInfo> getContacts() {

        //关联数据库
        SQLiteDatabase database = helperDB.getWritableDatabase();

        String sql = "select * from " + ContactsTable.TABLE_NAME +
                " where " + ContactsTable.COL_IS_CONTACTS + "=1";
        Cursor cursor = database.rawQuery(sql, null);

        List<UserInfo> userInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            UserInfo userInfo = new UserInfo();

            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactsTable.COL_USERNAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactsTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactsTable.COL_PHOTO)));
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactsTable.COL_HXID)));

            userInfos.add(userInfo);
        }
        cursor.close();

        return userInfos;
    }


    /**
     * 通过环信 hxid查询单个 联系人
     */
    public UserInfo getAContact(String hxid) {

        if (TextUtils.isEmpty(hxid)) {
            return null;
        }

        //关联数据库
        SQLiteDatabase database = helperDB.getWritableDatabase();

        String sql = "select * from " + ContactsTable.TABLE_NAME +
                " where " + ContactsTable.COL_HXID + "=?";

        Cursor cursor = database.rawQuery(sql, new String[]{hxid});

        UserInfo userInfo = null;//便于后边判空

        if (cursor.moveToNext()) {
            userInfo = new UserInfo();
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactsTable.COL_USERNAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactsTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactsTable.COL_PHOTO)));
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactsTable.COL_HXID)));

        }
        cursor.close();
        return userInfo;
    }


    /**
     * 通过 多个hxid 查新联系人
     */
    public List<UserInfo> getMoreContact(List<String> hxids) {

        if (hxids == null || hxids.size() == 0) {

//            throw new NullPointerException("数据为空啊！！");
            return null;
        }

        List<UserInfo> userInfos = new ArrayList<>();

        for (int i = 0; i < hxids.size(); i++) {
            UserInfo info = getAContact(hxids.get(i));
            if (info != null) {
                userInfos.add(info);
            }

        }


        return userInfos;
    }


    /**
     * 保存单个联系人
     */
    public void saveContact(UserInfo userInfo, boolean isContact) {

        if (userInfo == null) {
            return;
        }

        //关联数据库
        SQLiteDatabase database = helperDB.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ContactsTable.COL_USERNAME, userInfo.getName());
        values.put(ContactsTable.COL_HXID, userInfo.getHxId());
        values.put(ContactsTable.COL_PHOTO, userInfo.getPhoto());
        values.put(ContactsTable.COL_NICK, userInfo.getNick());
        values.put(ContactsTable.COL_IS_CONTACTS, isContact ? 1 : 0);

        database.replace(ContactsTable.TABLE_NAME, null, values);
    }


    /**
     * 保存多个联系人
     */
    public void saveMoreContact(List<UserInfo> userInfos, boolean isContact) {
        if (userInfos == null || userInfos.size() == 0) {

//            throw new NullPointerException("数据为空啊！！");
            return;
        }

        for (int i = 0; i < userInfos.size(); i++) {

            saveContact(userInfos.get(i), isContact);

        }

    }


    /**
     * 删除联系人
     */
    public void deleteContact(String hxid ) {

        if(!TextUtils.isEmpty(hxid)) {
            //关联数据库
            SQLiteDatabase database = helperDB.getWritableDatabase();
            database.delete(ContactsTable.TABLE_NAME,ContactsTable.COL_HXID + "=?",new String[]{hxid});

        }

    }

}
