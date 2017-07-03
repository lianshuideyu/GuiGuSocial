package com.atguigu.guigusocial.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.utils.UIUtils;
import com.atguigu.guigusocial.view.activity.LoginActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/2.
 */

public class SettingFragment extends Fragment {

    @InjectView(R.id.setting_btn_exit)
    Button settingBtnExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_setttings, null);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.setting_btn_exit)
    public void onViewClicked() {

        outUser();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * 这里可以有两种方法获取当前用户的信息
         * 1.通过联网 从环信 获取
         * 2.通过本地当前用户 数据库获取
         */

        String username = EMClient.getInstance().getCurrentUser();
        settingBtnExit.setText("退出登录（" + username + ")");
    }

    /**
     * 暂时 用户退出登录，测试用
     */
    private void outUser() {
//        EMClient.getInstance().logout(true);

        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                UIUtils.showToast("退出登录");

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                getActivity().finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                UIUtils.showToast(message);
            }
        });

    }
}
