package com.atguigu.guigusocial.model;

import android.content.Context;

import com.atguigu.guigusocial.model.dao.ContactsDAO;
import com.atguigu.guigusocial.model.dao.InvitationDAO;
import com.atguigu.guigusocial.model.db.HelperDB;

/**
 * Created by Administrator on 2017/7/3.
 */

/**
 * 为数据库的管理类
 */
public class HelperManager {

    private final HelperDB helper;
    private final ContactsDAO contactsDAO;
    private final InvitationDAO invitationDAO;

    /**
     * 初始化 联系人 及消息的 数据库
     *
     * @param context
     * @param name
     */
    public HelperManager(Context context, String name) {
        helper = new HelperDB(context, name + ".db");

        contactsDAO = new ContactsDAO(helper);
        invitationDAO = new InvitationDAO(helper);
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {

        if (helper != null) {

            helper.close();
        }
    }

    public HelperDB getHelper() {
        return helper;
    }

    public ContactsDAO getContactsDAO() {
        return contactsDAO;
    }

    public InvitationDAO getInvitationDAO() {
        return invitationDAO;
    }
}
