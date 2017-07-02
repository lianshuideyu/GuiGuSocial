package com.atguigu.guigusocial.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.bean.UserInfo;
import com.atguigu.guigusocial.common.BaseActivity;
import com.atguigu.guigusocial.model.Model;
import com.atguigu.guigusocial.view.MainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

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


    private String username;
    private String userpsd;


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
                //注册
                username = loginEtUsername.getText().toString().trim();
                userpsd = loginEtPassword.getText().toString().trim();
                register();

                break;
            case R.id.login_btn_login:
                //登录
                username = loginEtUsername.getText().toString().trim();
                userpsd = loginEtPassword.getText().toString().trim();
                login();

                break;

        }
    }


    private void login() {

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpsd)) {
            showToast("用户名和密码不能为空");
        } else {

            EMClient.getInstance().login(username, userpsd, new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    Log.d("main", "登录聊天服务器成功！");

                    //登录成功后要保存数据
                    String currentUser = EMClient.getInstance().getCurrentUser();
                    /**
                     * 环信的 用户名和 环信 id --hxId是一样的
                     */
                    Model.getInstance().successLogin(new UserInfo(currentUser,currentUser));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("登录成功");

                        }
                    });
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    Log.d("main", "登录聊天服务器失败！");
                }
            });
        }
    }


    private void register() {

        new Thread() {
            public void run() {
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpsd)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("用户名和密码不能为空");
                        }
                    });

                } else {

                    //注册失败会抛出HyphenateException
                    try {
                        EMClient.getInstance().createAccount(username, userpsd);//同步方法
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("注册成功");
                            }
                        });
                    } catch (final HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast(e.getMessage());
                            }
                        });

                    }

                }
            }
        }.start();


    }
}
