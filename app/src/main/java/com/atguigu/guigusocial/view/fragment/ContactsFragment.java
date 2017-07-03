package com.atguigu.guigusocial.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.utils.UIUtils;
import com.hyphenate.easeui.ui.EaseContactListFragment;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ContactsFragment extends EaseContactListFragment {
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        TextView textView = new TextView(getContext());
//
//        textView.setText("联系人");
//        textView.setTextSize(25);
//        return textView;
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setUpView() {
        super.setUpView();

        View view = View.inflate(getActivity(), R.layout.head_view,null);

        LinearLayout friends = (LinearLayout) view.findViewById(R.id.ll_new_friends);
        LinearLayout groups = (LinearLayout) view.findViewById(R.id.ll_groups);

        listView.addHeaderView(view);

        /**
         * 加好友的点击事件
         */
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.showToast("加好友");
            }
        });

        /**
         * 群组的点击事件
         */
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.showToast("群组");
            }
        });
    }
}
