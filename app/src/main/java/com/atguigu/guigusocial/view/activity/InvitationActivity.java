package com.atguigu.guigusocial.view.activity;

import android.widget.ListView;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.bean.InvitationInfo;
import com.atguigu.guigusocial.common.BaseActivity;
import com.atguigu.guigusocial.model.Model;
import com.atguigu.guigusocial.utils.UIUtils;
import com.atguigu.guigusocial.view.adapter.InvitatedAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.InjectView;

public class InvitationActivity extends BaseActivity {


    @InjectView(R.id.lv_invite)
    ListView lvInvite;

    private InvitatedAdapter adapter;


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        adapter = new InvitatedAdapter(this, myInvitate);

        refreshData();

        lvInvite.setAdapter(adapter);
    }

    /**
     * 接受和拒绝的点击事件 的回调监听
     */
    private InvitatedAdapter.OnInvitateListener myInvitate
            = new InvitatedAdapter.OnInvitateListener() {
        @Override
        public void inviteAccept(final InvitationInfo info) {
//            showToast("接受");
            //同意好友请求
            //在子线程中进行
            Model.getInstance().getGlobalThread().execute(new Runnable() {
                @Override
                public void run() {
                    if (info != null) {

                        try {//联网同意...

                            String name = info.getUserInfo().getName();
                            EMClient.getInstance().contactManager().acceptInvitation(name);
//                            UIUtils.showToast("同意请求");
                            //保存联系人，在全局监听的位置已经做了保存操作，这里不用再保存了
//                            Model.getInstance().getHelperManager()
//                                    .getContactsDAO()
//                                    .saveContact(new UserInfo(name, name), true);
                            //改变消息的状态
                            Model.getInstance().getHelperManager()
                                    .getInvitationDAO()
                                    .updateInvitationStatus
                                            (InvitationInfo.InvitationStatus.INVITE_ACCEPT, name);

                            //刷新适配器
                            UIUtils.UIThread(new Runnable() {
                                @Override
                                public void run() {
                                    UIUtils.showToast("添加成功");
                                    refreshData();

                                }
                            });

                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            UIUtils.showToast(e.getMessage());
                        }
                    }
                }
            });

        }

        @Override
        public void inviteReject(final InvitationInfo info) {
//            showToast("拒绝");
            //拒绝好友请求
            //在子线程中进行
            Model.getInstance().getGlobalThread().execute(new Runnable() {

                @Override
                public void run() {
                    if (info != null) {
                        try {
                            String name = info.getUserInfo().getName();
                            EMClient.getInstance().contactManager().declineInvitation(name);


                            //改变消息的状态
                            //拒绝后移除消息
                            Model.getInstance().getHelperManager()
                                    .getInvitationDAO()
                                    .removeInvitation(name);

                            //刷新适配器
                            UIUtils.UIThread(new Runnable() {
                                @Override
                                public void run() {
                                    UIUtils.showToast("拒绝成功");
                                    refreshData();

                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            UIUtils.showToast(e.getMessage());
                        }
                    }


                }
            });


        }
    };


    /*
    界面数据展示需要考虑三大数据源
    * 网络
    * 本地
    * 内存和页面
    *
    * */
    private void refreshData() {
        List<InvitationInfo> invitations =
                Model.getInstance().getHelperManager().getInvitationDAO().getInvitations();

        if (invitations != null) {

            adapter.refresh(invitations);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_invitation;
    }


}
