package com.example.a11708.graduationproject;

import android.app.Application;
import android.content.Context;

import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.Utils.DeviceUtil;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.Calendar;

public class AllApplication extends Application {
    private static AllApplication mContext;
    Calendar calendar = Calendar.getInstance();
    //获取系统的日期
    //年
    int year = calendar.get(Calendar.YEAR);
    //月
    int month = calendar.get(Calendar.MONTH);
    //日
    int day = calendar.get(Calendar.DAY_OF_MONTH);


    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        //LitePal.initialize(this);
        Constant.TIME = year+"/"+month+"/"+day;
    }

    public static AllApplication getInstance() {
        return mContext;
    }

}
