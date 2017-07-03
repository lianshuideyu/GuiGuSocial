package com.atguigu.guigusocial.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.common.BaseActivity;
import com.atguigu.guigusocial.view.activity.LoginActivity;
import com.atguigu.guigusocial.view.fragment.ChatFragment;
import com.atguigu.guigusocial.view.fragment.ContactsFragment;
import com.atguigu.guigusocial.view.fragment.SettingFragment;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {


    @InjectView(R.id.fragment_main)
    FrameLayout fragmentMain;
    @InjectView(R.id.tab_group)
    RadioGroup tabGroup;

    private List<Fragment> fragments;
    //切换的位置
    private int position;

    private Fragment tempFragment;//用于缓存的Fragment
    @Override
    public void initListener() {

        tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
//                Log.e("TAG","checkid==" + checkid);
                switch (checkid) {
                    case R.id.conv_list_btn :

                        position = 0;
                        break;
                    case R.id.contact_list_btn :
                        position = 1;
                        break;
                    case R.id.setting_btn :

                        position = 2;
                        break;
                }

                Fragment currentFragment = fragments.get(position);
                selectFragment(currentFragment);
            }
        });

        //默认选择 会话 按键
        tabGroup.check(R.id.conv_list_btn);
    }

    /**
     * 切换Fragment,方法一
     * @param currentFragment
     */
    private void selectFragment1(Fragment currentFragment) {

        if(currentFragment == null) {
            return;
        }

        //开启事务管理
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(currentFragment != tempFragment) {
            //第一次进来temp为空，肯定不等于

            //先隐藏之前的
            if(tempFragment != null) {
                ft.hide(tempFragment);
            }


            if(!currentFragment.isAdded()) {
                //判断是否添加过，没有就添加
                ft.add(R.id.fragment_main,currentFragment);

            }else {
                //添加过就显示缓存的
                ft.show(currentFragment);
            }

            tempFragment = currentFragment;
            ft.commit();//提交事务，不要忘记
        }

    }

    /**
     * 切换Fragment,方法二
     * 该方法每次切换会重新执行 生命周期
     * @param currentFragment
     */
    private void selectFragment(Fragment currentFragment) {
        if(currentFragment == null) {
            return;
        }

        //开启事务管理
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_main,currentFragment).commit();
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

        fragments = new ArrayList<>();
        fragments.add(new ChatFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new SettingFragment());
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 暂时 用户退出登录，测试用
     *
     * @param v
     */
    public void outUser(View v) {
//        EMClient.getInstance().logout(true);

        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("退出登录");
                    }
                });

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }
        });

    }
}
