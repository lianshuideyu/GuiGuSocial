package com.atguigu.guigusocial.view.fragment;

import android.content.Intent;

import com.atguigu.guigusocial.view.activity.ChatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ChatFragment extends EaseConversationListFragment {

    @Override
    protected void initView() {
        super.initView();

        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void setUpView() {
        super.setUpView();

        //监听会话消息的改变
        // 监听会话的变化
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);

    }


    private EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //收到消息
            // 设置数据
            EaseUI.getInstance().getNotifier().onNewMesg(list);

            // 刷新列表
            refresh();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };
}

