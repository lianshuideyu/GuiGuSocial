package com.atguigu.guigusocial.view;

import android.widget.TextView;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.bean.UserInfo;
import com.atguigu.guigusocial.common.BaseActivity;
import com.atguigu.guigusocial.model.Model;
import com.atguigu.guigusocial.model.dao.AccountDAO;

public class MainActivity extends BaseActivity {

    private TextView tvtest;

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        tvtest = (TextView)findViewById(R.id.tvtest);

        AccountDAO accountDAO = Model.getInstance().getAccountDAO();
        if(accountDAO != null) {
            UserInfo user = accountDAO.getUserInfo("zjc");

            tvtest.setText("欢迎你： " + user.getName());
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
