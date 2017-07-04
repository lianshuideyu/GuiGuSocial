package com.atguigu.guigusocial.common;

/**
 * Created by Administrator on 2017/7/3.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.atguigu.guigusocial.bean.InvitationInfo;
import com.atguigu.guigusocial.bean.UserInfo;
import com.atguigu.guigusocial.model.Model;
import com.atguigu.guigusocial.utils.SpUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * 全局的监听
 * 联系人 邀请消息...
 * 监听好友 状态事件
 */
public class GlobalListener {

    /**
     * 本地广播与全局广播的区别
     * 本地广播只能 当前应用的可以接收到
     * <p>
     * 全局广播 所有应用都可以接收到
     */
    private LocalBroadcastManager localBroadcastManager;

    /**
     * 全局监听  在Model中初始化
     *
     * @param context
     */
    public GlobalListener(Context context) {

        EMClient.getInstance().contactManager().setContactListener(emcontactListener);

        /**
         * 初始化本地广播
         */
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    private EMContactListener emcontactListener = new EMContactListener() {

        @Override
        public void onContactAgreed(String username) {
            //好友请求被同意 别人同意我的请求
            Log.e("GlobalListener", "onContactAgreed==" + username);

            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setReason("同意我的邀请");
            invitationInfo.setUserInfo(new UserInfo(username, username));

            invitationInfo.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);
            //保存到数据库
            Model.getInstance().getHelperManager().getInvitationDAO().addInvitation(invitationInfo);

            //保存小红点状态
            SpUtils.getInstance().saveSp(SpUtils.NEW_INVITE, true);

            //发送广播，消息改变
            localBroadcastManager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));

        }

        @Override
        public void onContactRefused(String username) {
            //好友请求被拒绝 别人没有同意我的请求
            Log.e("GlobalListener", "onContactRefused==" + username);

//            InvitationInfo invitationInfo = new InvitationInfo();
//            invitationInfo.setReason("请求被拒接");
//            invitationInfo.setUserInfo(new UserInfo(username, username));
//
//            invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);
//            //保存到数据库
//            Model.getInstance().getHelperManager().getInvitationDAO().addInvitation(invitationInfo);

            //保存小红点状态
            SpUtils.getInstance().saveSp(SpUtils.NEW_INVITE, true);

            //发送广播消息改变
            localBroadcastManager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));

        }

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请 别人发给我的--参数：发消息的人，和验证消息
            Log.e("GlobalListener", "onContactInvited==" + username + "--" + reason);

            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setUserInfo(new UserInfo(username, username));

            invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);
            //保存到数据库
            Model.getInstance().getHelperManager().getInvitationDAO().addInvitation(invitationInfo);

            //保存小红点状态
            SpUtils.getInstance().saveSp(SpUtils.NEW_INVITE, true);

            //发送广播,消息改变
            localBroadcastManager.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGE));

        }

        @Override
        public void onContactDeleted(String username) {
            //被删除时回调此方法  被别人删除
            Log.e("GlobalListener", "onContactDeleted==" + username);

            Model.getInstance().getHelperManager().getContactsDAO().deleteContact(username);

            Model.getInstance().getHelperManager().getInvitationDAO().removeInvitation(username);

            //发送广播,联系人改变
            localBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));
        }


        @Override
        public void onContactAdded(String username) {
            //增加了联系人时回调此方法  添加了联系人
            Log.e("GlobalListener", "onContactAdded==" + username);

            UserInfo userInfo = new UserInfo(username, username);

            Model.getInstance().getHelperManager().getContactsDAO().saveContact(userInfo,true);

            //发送广播,联系人改变
            localBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_CHANGE));
        }
    };


}
