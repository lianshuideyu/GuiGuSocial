package com.atguigu.guigusocial.common;

/**
 * Created by Administrator on 2017/7/3.
 */

import android.content.Context;
import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * 全局的监听
 * 联系人 邀请消息...
 * 监听好友 状态事件
 */
public class GlobalListener {

    /**
     * 全局监听  在Model中初始化
     * @param context
     */
    public GlobalListener(Context context) {

        EMClient.getInstance().contactManager().setContactListener(emcontactListener);

    }

    private EMContactListener emcontactListener = new EMContactListener() {

        @Override
        public void onContactAgreed(String username) {
            //好友请求被同意 别人同意我的请求
            Log.e("GlobalListener","onContactAgreed==" + username);
        }

        @Override
        public void onContactRefused(String username) {
            //好友请求被拒绝 别人同意我的请求
            Log.e("GlobalListener","onContactRefused==" + username);

        }

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请 别人发给我的--发消息的人，和验证消息
            Log.e("GlobalListener","onContactInvited==" + username + "--" + reason);
        }

        @Override
        public void onContactDeleted(String username) {
            //被删除时回调此方法  被别人删除
            Log.e("GlobalListener","onContactDeleted==" + username );
        }


        @Override
        public void onContactAdded(String username) {
            //增加了联系人时回调此方法  添加了联系人
            Log.e("GlobalListener","onContactAdded==" + username );
        }
    };


}
