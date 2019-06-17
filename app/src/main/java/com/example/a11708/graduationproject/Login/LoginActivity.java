package com.example.a11708.graduationproject.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.NET.SQLNetService;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.Utils.MD5Util;
import com.example.a11708.graduationproject.Utils.NetStateUtils;
import com.example.a11708.graduationproject.Utils.SharedPreferencesUtils;
import com.example.a11708.graduationproject.activity.MainPage;
import com.example.a11708.graduationproject.model.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    private EditText mZhanghao;
    private EditText mMima;
    private TextView mDenglu;
    private TextView mZhuce;
    private UserModel userModel = UserModel.getInstance();
    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(LoginActivity.this, MainPage.class);
                Constant.UserName = mZhanghao.getText().toString();
                //SharedPreferencesUtils.setValue("Username",mZhanghao.getText().toString());
                startActivity(intent);
                finish();

            }else if(msg.what == 2){
                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(LoginActivity.this, MainPage.class);
                Constant.UserName = mZhanghao.getText().toString();
                startActivity(intent);
                finish();
            }else if(msg.what == 3){
                Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        bindEvent();
    }

    private void bindEvent() {
        mDenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zhanghao = mZhanghao.getText().toString();
                String mima = mMima.getText().toString();
                if(checkdata()){
                    loginIn(zhanghao,mima);
                }

            }
        });
        mZhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zhanghao = mZhanghao.getText().toString();
                String mima = mZhanghao.getText().toString();
                if(checkdata()){
                    if(SharedPreferencesUtils.getValue(Constant.SpKey.USER_NAME).equals(zhanghao)){
                        Toast.makeText(LoginActivity.this,"对不起，此账号已经注册",Toast.LENGTH_SHORT);

                    }
                    if(NetStateUtils.isNetworkAvailable(LoginActivity.this)){
                        register(zhanghao,mima);
                    }
                }

            }
        });
    }

    private void register(String zhanghao, String mima) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                String logSql = "select * from user where username ='" + zhanghao + "' " +
                        "and password='" + mima + "'";
                SQLNetService sql = SQLNetService.createInstance();
                sql.connectDB();
                ResultSet rs = sql.executeQuery(logSql);
                try {
                    if (rs.next()) {
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this,"对不起，此账号已经注册",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else{
                        String regSql = "insert into user(username,password) values('" + zhanghao + "','" +mima + "') ";
                        SQLNetService Insertsql = SQLNetService.createInstance();
                        sql.connectDB();
                        int ss = sql.executeUpdate(regSql);
                        if(ss!=0)
                        {
                            SharedPreferencesUtils.setValue(Constant.SpKey.USER_NAME,zhanghao);
                            SharedPreferencesUtils.setValue(Constant.SpKey.USER_PASSWORD,MD5Util.md5(mima));
                            Message msg = new Message();
                            msg.what = 2;
                            myHandler.sendMessage(msg);
                        }else{
                            Message msg = new Message();
                            msg.what = 3;
                            myHandler.sendMessage(msg);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void loginIn(String zhanghao, String mima) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String logSql = "select * from user where username ='" + zhanghao + "' " +
                        "and password='" + mima + "'";
                SQLNetService sql = SQLNetService.createInstance();
                sql.connectDB();
                ResultSet rs = sql.executeQuery(logSql);
                try {
                    if (rs.next()) {

                        SharedPreferencesUtils.setValue(Constant.SpKey.USER_NAME, zhanghao);
                        SharedPreferencesUtils.setValue(Constant.SpKey.USER_PASSWORD,MD5Util.md5(mima));
                        Message m = new Message();
                        m.what = 1;
                        myHandler.sendMessage(m);
                    }
                    else
                    {
                        Message m = new Message();
                        m.what = 0;
                        myHandler.sendMessage(m);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean checkdata() {
        if(mZhanghao.getText().toString().equals("") && mMima.getText().toString().equals("")){
            Toast.makeText(this,"请输入账号和密码",Toast.LENGTH_SHORT).show();
            return false;
        } else if(mZhanghao.getText().toString().equals("")){
            Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show();
            return false;
        }else if(mMima.getText().toString().equals("")){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void initView() {
        mZhanghao = (EditText)findViewById(R.id.et_login_account);
        mMima = (EditText)findViewById(R.id.et_login_password);
        mDenglu = (TextView)findViewById(R.id.btn_login);
        mZhuce = (TextView)findViewById(R.id.btn_register);
    }

}
