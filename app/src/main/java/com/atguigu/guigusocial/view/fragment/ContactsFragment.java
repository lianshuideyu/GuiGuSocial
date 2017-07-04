package com.atguigu.guigusocial.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.common.Constant;
import com.atguigu.guigusocial.utils.SpUtils;
import com.atguigu.guigusocial.utils.UIUtils;
import com.atguigu.guigusocial.view.activity.AddContactActivity;
import com.hyphenate.easeui.ui.EaseContactListFragment;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ContactsFragment extends EaseContactListFragment {

    /**
     * 提示信息的小红点
     */
    private View iv_invite;
    /**
     * 广播
     */
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("invite","BroadcastReceiver");
            //接收到广播
            isShowRedView();

        }
    };

    /**
     * 显示红点
     */
    private void isShowRedView() {
        boolean b = SpUtils.getInstance().getBoolean(SpUtils.NEW_INVITE);

        iv_invite.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setUpView() {
        super.setUpView();

        /**
         * 头部布局
         */
        initHeadView();

        //先显示一下是否有红点
        isShowRedView();
        /**
         * 标题栏
         */
        initTitleBar();

        /**
         * 注册广播
         */
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter filter = new IntentFilter(Constant.NEW_INVITE_CHANGE);
        manager.registerReceiver(myReceiver,filter);
        Log.e("invite","BroadcastReceiver=" + "注册广播");
    }

    private void initTitleBar() {
        titleBar.setRightLayoutVisibility(View.VISIBLE);

        titleBar.setRightImageResource(R.drawable.ease_blue_add);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UIUtils.showToast("添加好友");
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initHeadView() {
        View view = View.inflate(getActivity(), R.layout.head_view,null);

        LinearLayout friends = (LinearLayout) view.findViewById(R.id.ll_new_friends);
        LinearLayout groups = (LinearLayout) view.findViewById(R.id.ll_groups);

        listView.addHeaderView(view);

        /**
         * 小红点
         */
        iv_invite = view.findViewById(R.id.iv_invite);
        /**
         * 加好友的点击事件
         */
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.showToast("friends");
            }
        });

        /**
         * 群组的点击事件
         */
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.showToast("groups");
            }
        });
    }


    /**
     * 解注册广播
     */
    public void unRegistBR(){

        getActivity().unregisterReceiver(myReceiver);
    }
}
