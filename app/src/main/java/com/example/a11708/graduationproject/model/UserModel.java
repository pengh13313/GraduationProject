package com.example.a11708.graduationproject.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.a11708.graduationproject.Beans.User;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.Login.LoginActivity;
import com.example.a11708.graduationproject.NET.SQLNetService;
import com.example.a11708.graduationproject.Utils.LoginUtils;
import com.example.a11708.graduationproject.Utils.MD5Util;
import com.example.a11708.graduationproject.Utils.SharedPreferencesUtils;
import com.example.a11708.graduationproject.activity.MainPage;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserModel {

    private static volatile UserModel mInstance;

    private User mUser;

    private Context mcontext;



    public static UserModel getInstance() {
        if (mInstance == null) {
            synchronized (UserModel.class) {
                if (mInstance == null) {
                    mInstance = new UserModel();
                }
            }
        }
        return mInstance;
    }


    public boolean hasLogin() {
        if (SharedPreferencesUtils.getValue(Constant.SpKey.USER_NAME).equals("")||SharedPreferencesUtils.getValue(Constant.SpKey.USER_PASSWORD).equals("")) {
            return false;
        }
        return true;
    }

    public void logout(){
        mUser=null;
    }

    public void setUserInfo(User userInfo) {
        mUser = userInfo;
    }




}
