package com.atguigu.guigusocial.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.guigusocial.bean.InvitationInfo;
import com.atguigu.guigusocial.bean.UserInfo;
import com.atguigu.guigusocial.model.db.HelperDB;
import com.atguigu.guigusocial.model.table.InvitationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class InvitationDAO {

    private final HelperDB dbHelper;

    public InvitationDAO(HelperDB dbHelper) {

        this.dbHelper = dbHelper;
    }

    // 添加邀请
    public void addInvitation(InvitationInfo invitationInfo){
        if (invitationInfo == null){
            return;
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InvitationTable.COL_REASON,invitationInfo.getReason());
        contentValues.put(InvitationTable.COL_HXID,
                invitationInfo.getUserInfo().getHxId());
        contentValues.put(InvitationTable.COL_USERNAME,
                invitationInfo.getUserInfo().getName());

        int ordinal = invitationInfo.getStatus().ordinal();
        contentValues.put(InvitationTable.COL_STATE, ordinal);

        database.replace(InvitationTable.TABLE_NAME,null,contentValues);
    }

    // 获取所有邀请信息
    public List<InvitationInfo> getInvitations(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sql = "select * from "+InvitationTable.TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);
        List<InvitationInfo> infoList = new ArrayList<>();
        while (cursor.moveToNext()){
            InvitationInfo info = new InvitationInfo();
            info.setReason(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_REASON)));
            info.setStatus(
                    int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InvitationTable.COL_STATE))));
            UserInfo userInfo = new UserInfo();
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USERNAME)));
            info.setUserInfo(userInfo);
            //将invitationInfo添加到集合中
            infoList.add(info);
        }
        cursor.close();
        return infoList;
    }

    // 将int类型状态转换为邀请的状态
    //private InvitationInfo.InvitationStatus int2InviteStatus(int intStatus)

    // 删除邀请
    public void removeInvitation(String hxId){
        if (TextUtils.isEmpty(hxId)){
            return;
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(InvitationTable.TABLE_NAME,
                InvitationTable.COL_HXID+"=?",new String[]{hxId});
    }

    // 更新邀请状态
    public void updateInvitationStatus(InvitationInfo.InvitationStatus invitationStatus,
                                       String hxId){
        if (TextUtils.isEmpty(hxId)){
            return;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InvitationTable.COL_STATE,invitationStatus.ordinal());
        database.update(InvitationTable.TABLE_NAME,contentValues,
                InvitationTable.COL_HXID+"=?",new String[]{hxId});
    }

    // 将int类型状态转换为邀请的状态
    //ordinal 为 序列，给个编号
    private InvitationInfo.InvitationStatus int2InviteStatus(int intStatus) {

        if (intStatus == InvitationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }
        return null;
    }
}
