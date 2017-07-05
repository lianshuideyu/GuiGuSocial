package com.atguigu.guigusocial.view.activity;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.common.BaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends BaseActivity {


    private EaseChatFragment chatFragment;

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fl,chatFragment).commit();
    }

    @Override
    public void initView() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }
}
