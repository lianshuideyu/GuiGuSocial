package com.atguigu.guigusocial.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.common.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.login_et_username)
    EditText loginEtUsername;
    @InjectView(R.id.login_et_password)
    EditText loginEtPassword;
    @InjectView(R.id.login_btn_register)
    Button loginBtnRegister;
    @InjectView(R.id.login_btn_login)
    Button loginBtnLogin;


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.login_btn_register, R.id.login_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn_register:
                break;
            case R.id.login_btn_login:
                break;
        }
    }
}
