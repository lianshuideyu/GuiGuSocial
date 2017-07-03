package com.atguigu.guigusocial.view;

import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.common.BaseActivity;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {


    @InjectView(R.id.fragment_main)
    FrameLayout fragmentMain;
    @InjectView(R.id.tab_group)
    RadioGroup tabGroup;

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

//        tvtest = (TextView)findViewById(R.id.tvtest);
//
//        AccountDAO accountDAO = Model.getInstance().getAccountDAO();
//        if(accountDAO != null) {
//            UserInfo user = accountDAO.getUserInfo("zjc");
//
//            tvtest.setText("欢迎你： " + user.getName());
//        }
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
