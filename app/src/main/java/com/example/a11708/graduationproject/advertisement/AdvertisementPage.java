package com.example.a11708.graduationproject.advertisement;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.Login.LoginActivity;
import com.example.a11708.graduationproject.Utils.SharedPreferencesUtils;
import com.example.a11708.graduationproject.activity.MainPage;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.model.UserModel;

import okhttp3.OkHttpClient;

public class AdvertisementPage extends AppCompatActivity {
    private ImageView imageView;
    private Button button;
    private boolean flag_click = false;
    private boolean flag_ad = false;
    private Context context;
    private OkHttpClient client=new OkHttpClient();
    private Handler handler;
    private CountDownTimer timer;
    private UserModel userModel = UserModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement_page);
        InitView();
        bindEvent();

    }

    private void bindEvent() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_ad = true;
                login();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_click = true;
                login();
            }
        });
        CountDownTimer timer = new CountDownTimer(6*1000, 1000) {
            @Override
            public void onTick(long l) {
                button.setText(l/1000+"秒");
            }

            @Override
            public void onFinish() {
                finish();
                if(flag_click == false && flag_ad == false){
                    login();
                }
            }
        }.start();
    }

    private void login() {
        if(userModel.hasLogin()) {
            Intent intent = new Intent(AdvertisementPage.this, MainPage.class);
            Constant.UserName = SharedPreferencesUtils.getValue(Constant.SpKey.USER_NAME);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(AdvertisementPage.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void InitView() {
        imageView = (ImageView)findViewById(R.id.image_ad);
        button = (Button)findViewById(R.id.button_count_down);


    }
}
