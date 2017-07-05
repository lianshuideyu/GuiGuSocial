package com.atguigu.guigusocial.view.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.bean.UserInfo;
import com.atguigu.guigusocial.common.Constant;
import com.atguigu.guigusocial.model.Model;
import com.atguigu.guigusocial.utils.SpUtils;
import com.atguigu.guigusocial.utils.UIUtils;
import com.atguigu.guigusocial.view.activity.AddContactActivity;
import com.atguigu.guigusocial.view.activity.ChatActivity;
import com.atguigu.guigusocial.view.activity.InvitationActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ContactsFragment extends EaseContactListFragment {

    /**
     * 提示信息的小红点
     */
    private View iv_invite;
    /**
     * 接受消息改变的广播
     */
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("invite", "myReceiver");
            //接收到广播
            isShowRedView();

        }
    };
    /**
     * 接受联系人改变的广播
     */
    private BroadcastReceiver contactReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("invite", "contactReceiver");
            //接收到广播后刷新显示
            refreshLocalData();
        }
    };

    //联系人信息
    private List<UserInfo> contacts;

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
        /**
         * 点击事件
         * 这里需要注意的是 此点击事件的方法要写在 initView()里边
         * 不要写在setUpView()里边，看环信源码会发现，写在setUpView（）里边 会空...从而不会执行点击事件方法
         */
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {

//                UIUtils.showToast("" + position);//position记得－1
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername());
                startActivity(intent);
            }
        });
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
        manager.registerReceiver(myReceiver, filter);
        Log.e("invite", "BroadcastReceiver=" + "注册广播");

        //注册联系人改变的广播
        manager.registerReceiver(contactReceiver, new IntentFilter(Constant.CONTACT_CHANGE));
        Log.e("invite", "BroadcastReceiver=" + "注册联系人改变的广播");
        //显示联系人
        showContacts();

        /**
         * 长按删除联系人
         */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

//                UIUtils.showToast("" + position);
                if (position == 0) {
                    return false;
                }
                //确认是否删除
                showDialog(position);

                return true;//消费事件
            }
        });

    }

    /**
     * 确认是否删除
     *
     * @param position
     */
    private void showDialog(final int position) {


        new AlertDialog.Builder(getActivity())
                .setTitle("确定删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //从网络删除
                        Model.getInstance().getGlobalThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                //从网络服务器删除
                                if (contacts != null) {

                                    try {//这里需要注意的是 因为listview的头文件 的position为0，所有listview的item的position从1开始
                                        UserInfo userInfo = contacts.get(position-1);
                                        EMClient.getInstance().contactManager().deleteContact(userInfo.getName());
                                        UIUtils.showToast("删除成功");

                                        //从本地删除全局监听已经做了操作
                                        Model.getInstance().getHelperManager().getContactsDAO().deleteContact(userInfo.getName());
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                        UIUtils.showToast(e.getMessage());
                                    }
                                }

                                //从内存
                                UIUtils.UIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshLocalData();
                                    }
                                });

                            }
                        });

                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

    /**
     * 显示联系人
     */
    private void showContacts() {

        //判断是否为程序第一次进入，第一次联网获取联系人
        //以后从本地获取联系人即可

        refreshFromNet();
    }

    private void refreshFromNet() {

        //从网络获取联系人信息
        Model.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    //从网络服务器获取所有联系人
                    List<String> contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    //保存到本地
                    if (contacts != null && contacts.size() > 0) {

                        List<UserInfo> userInfos = new ArrayList<UserInfo>();

                        for (String name : contacts) {

                            UserInfo userInfo = new UserInfo(name, name);
                            userInfos.add(userInfo);

                        }
                        Model.getInstance().getHelperManager().getContactsDAO().saveMoreContact(userInfos, true);

                        //从本地刷新 获取数据
                        UIUtils.UIThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLocalData();
                            }
                        });
                    }

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 从本地获取联系人信息
     */
    private void refreshLocalData() {

        contacts = Model.getInstance().getHelperManager().getContactsDAO().getContacts();
        if (contacts != null ) {

            Map<String, EaseUser> contactsMap = new HashMap<>();

            for (UserInfo info : contacts) {
                contactsMap.put(info.getName(), new EaseUser(info.getName()));

            }

            /**
             * 调用环信的方法
             */
            setContactsMap(contactsMap);
            //获取联系人列表
            getContactList();
            //刷新
            contactListLayout.refresh();

        }

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
        View view = View.inflate(getActivity(), R.layout.head_view, null);

        LinearLayout friends = (LinearLayout) view.findViewById(R.id.ll_new_friends);
        LinearLayout groups = (LinearLayout) view.findViewById(R.id.ll_groups);

        listView.addHeaderView(view);

        /**
         * 小红点
         */
        iv_invite = view.findViewById(R.id.iv_invite);
        /**
         * 加好友的点击事件
         * 注意一点：当在分线程 getActivity（）是需要先对 其反回值判空
         */
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UIUtils.showToast("friends");
                SpUtils.getInstance().saveSp(SpUtils.NEW_INVITE, false);//将小红点 状态改变
                Intent intent = new Intent(getActivity(), InvitationActivity.class);
                startActivity(intent);

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
    public void unRegistBR() {

        getActivity().unregisterReceiver(myReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();

        isShowRedView();//重新调用显示小红点的状态
    }
}
