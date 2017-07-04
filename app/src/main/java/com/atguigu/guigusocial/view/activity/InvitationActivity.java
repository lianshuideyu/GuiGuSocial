package com.atguigu.guigusocial.view.activity;

import android.widget.ListView;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.bean.InvitationInfo;
import com.atguigu.guigusocial.common.BaseActivity;
import com.atguigu.guigusocial.model.Model;
import com.atguigu.guigusocial.view.adapter.InvitatedAdapter;

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
        public void inviteAccept(InvitationInfo info) {
            showToast("接受");
        }

        @Override
        public void inviteReject(InvitationInfo info) {
            showToast("拒绝");
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

        if(invitations != null) {

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
