package com.atguigu.guigusocial.model.table;

/**
 * Created by Administrator on 2017/7/3.
 */

public class InvitationTable {

    public static final String TABLE_NAME = "invitation";

    public static final String COL_USERNAME = "username";
    public static final String COL_HXID = "hxid";
    public static final String COL_REASON = "reason";
    public static final String COL_STATE = "state";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "( "
            + COL_HXID + " text primary key,"
            + COL_USERNAME + " text,"
            + COL_REASON + " text,"
            + COL_STATE + " integer"
            ;

}
