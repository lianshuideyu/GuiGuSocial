package com.atguigu.guigusocial.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        ButterKnife.inject(this);

        initView();

        initData();

        initListener();
    }

    public abstract void initListener();

    public abstract void initData();

    public abstract void initView();

    /**
     * 添加布局id
     * @return
     */
    public abstract int getLayoutId() ;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.reset(this);
    }

    public void showToast(String message){
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
