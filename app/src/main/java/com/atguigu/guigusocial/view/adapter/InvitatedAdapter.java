package com.atguigu.guigusocial.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atguigu.guigusocial.R;
import com.atguigu.guigusocial.bean.InvitationInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/7/4.
 */

public class InvitatedAdapter extends BaseAdapter {

    private final Context context;

    public InvitatedAdapter(Context context,OnInvitateListener listener) {
        this.context = context;
        this.listener = listener;
    }

    private List<InvitationInfo> list = new ArrayList<>();

    /**
     * 刷新数据
     */
    public void refresh(List<InvitationInfo> list) {
        Log.e("invite","" + list.size());
        if (list != null && list.size() >= 0) {

            this.list.clear();//清除之前的数据
            this.list.addAll(list);//添加新传的数据

            notifyDataSetChanged();//刷新适配器
        }

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_invite, null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        /**
         * 设置数据
         */
        final InvitationInfo info = list.get(position);
        viewHolder.tvInviteName.setText(info.getUserInfo().getName());
//        viewHolder.tvInviteReason.setText(info.getReason());

        /**
         * 同意和拒绝的按键 默认不可显示
         */
        viewHolder.btInviteAccept.setVisibility(View.GONE);
        viewHolder.btInviteReject.setVisibility(View.GONE);

        switch (info.getStatus()) {
            case  INVITE_ACCEPT_BY_PEER:
                // 邀请被接受
                if(TextUtils.isEmpty(info.getReason())) {
                    viewHolder.tvInviteReason.setText("邀请被接受");
                }else {
                    viewHolder.tvInviteReason.setText(info.getReason());
                }

                break;
            case  INVITE_ACCEPT:
                //接受邀请
                if(TextUtils.isEmpty(info.getReason())) {
                    viewHolder.tvInviteReason.setText("接受邀请");
                }else {
                    viewHolder.tvInviteReason.setText(info.getReason());
                }

                break;
            case  NEW_INVITE:
                //新邀请
                if(TextUtils.isEmpty(info.getReason())) {
                    viewHolder.tvInviteReason.setText("新邀请");
                }else {
                    viewHolder.tvInviteReason.setText(info.getReason());
                }
                viewHolder.btInviteAccept.setVisibility(View.VISIBLE);
                viewHolder.btInviteReject.setVisibility(View.VISIBLE);
                //点击事件,点击事件的逻辑在InvitationActivity中处理,所以用接口
                viewHolder.btInviteAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listener != null) {
                            listener.inviteAccept(info);
                        }

                    }
                });

                viewHolder.btInviteReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listener != null) {
                            listener.inviteReject(info);
                        }
                    }
                });

                break;
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_invite_name)
        TextView tvInviteName;
        @InjectView(R.id.tv_invite_reason)
        TextView tvInviteReason;
        @InjectView(R.id.bt_invite_accept)
        Button btInviteAccept;
        @InjectView(R.id.bt_invite_reject)
        Button btInviteReject;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    /**
     * 接口
     */
    public interface OnInvitateListener {
        void inviteAccept(InvitationInfo info);
        void inviteReject(InvitationInfo info);

    }

    private OnInvitateListener listener;

    private void setInvitateListener(OnInvitateListener listener){
        this.listener = listener;

    }
}
