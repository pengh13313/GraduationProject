package com.example.a11708.graduationproject.Beans;

import java.io.Serializable;

public class User implements Serializable {
    private String name;//用户昵称

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    private String img;//头像
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }




}
