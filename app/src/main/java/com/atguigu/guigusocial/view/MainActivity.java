package com.atguigu.guigusocial.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atguigu.guigusocial.R;
import com.hyphenate.chat.EMClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EMClient.getInstance().getChatConfig();
    }
}
