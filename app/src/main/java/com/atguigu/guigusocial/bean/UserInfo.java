package com.atguigu.guigusocial.bean;

/**
 * Created by Administrator on 2017/7/1.
 */

public class UserInfo {

    private String name;    // 用户名称
    private String hxId;    // 环信id
    private String nick;    // 昵称
    private String photo;   // 头像

    public UserInfo() {
    }

    public UserInfo(String name, String hxId) {
        this.name = name;
        this.hxId = hxId;
    }

    public UserInfo(String name) {
        this.name = name;
        this.hxId = name;
        this.nick = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHxId() {
        return hxId;
    }

    public void setHxId(String hxId) {
        this.hxId = hxId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", hxId='" + hxId + '\'' +
                ", nick='" + nick + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}

