package com.atguigu.guigusocial.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.common.BaseActivity;
import com.atguigu.guigusocial.model.Model;
import com.atguigu.guigusocial.utils.UIUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.InjectView;
import butterknife.OnClick;

public class AddContactActivity extends BaseActivity {


    @InjectView(R.id.invite_btn_search)
    Button inviteBtnSearch;
    @InjectView(R.id.invite_et_search)
    EditText inviteEtSearch;
    @InjectView(R.id.invite_tv_username)
    TextView inviteTvUsername;
    @InjectView(R.id.invite_btn_add)
    Button inviteBtnAdd;
    @InjectView(R.id.invite_ll_item)
    LinearLayout inviteLlItem;

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
        return R.layout.activity_add_contact;
    }


    /**
     * SDK 不提供好友查找的服务，如需要查找好友，需要调用开发者自己服务器的用户查询接口。
     * 为了保证查找到的好友可以添加，需要将开发者自己服务器的用户数据（用户的环信 ID），
     * 通过 SDK 的后台接口导入到环信服务器中
     *
     * @param view
     */
    private String username;

    @OnClick({R.id.invite_btn_search, R.id.invite_btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.invite_btn_search:
                //搜索联系人在本地服务器

                username = inviteEtSearch.getText().toString().trim();
                inviteSearch(username);


                break;
            case R.id.invite_btn_add:
                //添加好友
                //参数为要添加的好友的username和添加理由
                try {
                    String reason = "我是小名啊";
                    //访问环信服务器进行联系人添加
                    //第一个参数是环信ID 第二个参数是添加的原因
                    EMClient.getInstance().contactManager().addContact(username, reason);

                    UIUtils.showToast("请求发送成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    UIUtils.showToast(e.getMessage());
                }

                break;
        }
    }

    /**
     * 联系人的搜索
     * 在本地服务器执行
     */
    private void inviteSearch(final String username) {

        if (TextUtils.isEmpty(username)) {
            UIUtils.showToast("搜索内容为空");
            return;
        }

        //先在本公司的服务器上查询联系人信息
        //查询这些为耗时操作...
        Model.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                if (getuser()) {
                    //先设置默认为true存在，先测试后边再改getuser()
                    //连接环信 添加好友

                    //切换到主线程
                    UIUtils.UIThread(new Runnable() {
                        @Override
                        public void run() {
                            inviteLlItem.setVisibility(View.VISIBLE);
                            inviteTvUsername.setText(username);
                        }
                    });


                } else {
                    UIUtils.showToast("该好友不存在");

                }
            }
        });
    }


    /**
     * 通过查询本地服务器判断是否有该联系人
     *
     * @return
     */
    private boolean getuser() {
        return true;
    }
}
