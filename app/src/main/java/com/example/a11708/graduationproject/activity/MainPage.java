package com.example.a11708.graduationproject.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a11708.graduationproject.Beans.User;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.Utils.SharedPreferencesUtils;
import com.example.a11708.graduationproject.View.MainTabView;
import com.example.a11708.graduationproject.View.PactureMakerFragment;
import com.example.a11708.graduationproject.View.TimeMakerFragment;
import com.example.a11708.graduationproject.View.TitleView;
import com.example.a11708.graduationproject.View.UserCenterFragment;
import com.example.a11708.graduationproject.adapter.TimeMakerAdapter;
import com.example.a11708.graduationproject.model.UserModel;

import java.io.File;
import java.util.Calendar;

public class MainPage extends AppCompatActivity {
    private MainTabView mMainTabView;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private PactureMakerFragment mPactureMakerflag;
    private TimeMakerFragment mTimeMakerFrag;
    private UserCenterFragment mUserCenterFrag;
    private TitleView mtitleview;
    Fragment changeToFragment = null;
    private long mExitTime = 0;
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    //月
    int month = calendar.get(Calendar.MONTH);
    //日
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    public boolean isrefresh = false;
   /* public  volatile static MainPage instance = null;

    public static MainPage getInstance(){
        if(instance == null){
            synchronized (MainPage.class){
                if(null == instance){
                    instance = new MainPage();
                }
            }
        }
        return instance;
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        /*if(isrefresh == true) {
            isrefresh = false;
            mPactureMakerflag.onResume();

        }*/
        if(!SharedPreferencesUtils.getValue(Constant.config.IMAGE_URI,"").equals("")){
            String path = SharedPreferencesUtils.getValue(Constant.config.IMAGE_URI,"");
            File file = new File(path);
            Glide.with(this).load(file).into(mtitleview.mImageView);
        }
    }

    public void ChangeImage(Uri uri){
        Glide.with(this).load(uri).into(mtitleview.mImageView);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            try {
                FragmentManager manager = getSupportFragmentManager();
                manager.popBackStackImmediate(null, 1);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_page);
            int permission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
            }
            initView();
            bindEvent();
            initData();
            refreshView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void initData() {
        mCurrentFragment = null;
        mFragmentManager = getSupportFragmentManager();
    }

    private void bindEvent() {
        /*User user = UserModel.getInstance().getUserInfo();*/
        User user = new User();
        user.setName(Constant.UserName);
        mtitleview.mCalendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(MainPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        mtitleview.mCalendarImage.setText(year+"/"+month+"/"+day);
                        Constant.TIME = year+"/"+month+"/"+day;
                        if(changeToFragment != null) {
                            changeToFragment.onResume();
                        }
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                mDatePickerDialog.show();
            }
        });
        mtitleview.refreshView(user);
        mMainTabView.setOnTabClickListener(type -> changeFragment(type));
    }

    private void changeFragment(int type) {

        switch (type) {
            case MainTabView.TAB_TYPE_PACTURE_FRAGMENT:
                if (mPactureMakerflag == null) {
                    mPactureMakerflag = new PactureMakerFragment();
                }
                changeToFragment = mPactureMakerflag;
                Log.e("fragment1","actureMakerFragment");
                break;
            case MainTabView.TAB_TYPE_LIVETIME_FRAGMENT:
                if (mTimeMakerFrag == null) {
                    mTimeMakerFrag = new TimeMakerFragment();
                }
                changeToFragment = mTimeMakerFrag;
                Log.e("fragment2","TimeMakerFragment");
                break;
            case MainTabView.TAB_TYPE_USER_FRAGMENT:
                if (mUserCenterFrag == null) {
                    mUserCenterFrag = new UserCenterFragment();
                }
                changeToFragment = mUserCenterFrag;
                Log.e("fragment3","3");
                break;
        }
        if (changeToFragment != null) {
            mMainTabView.refreshTabViewState(type);
            if (changeToFragment != null && mCurrentFragment != changeToFragment) {
                try {
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    if (mCurrentFragment != null && mCurrentFragment.isAdded()) {
                        transaction.hide(mCurrentFragment);
                    }
                    if (changeToFragment.isAdded()) {
                        transaction.show(changeToFragment);
                    } else {
                        transaction.add(R.id.root, changeToFragment);
                    }
                    transaction.commit();
                    mCurrentFragment = changeToFragment;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void refreshView() {
        if(Constant.ClearUp == true){
            changeToFragment = null;
            Constant.ClearUp = false;
        }
        //getWindow().setFormat(PixelFormat.TRANSLUCENT);
        changeFragment(MainTabView.TAB_TYPE_LIVETIME_FRAGMENT);
    }

    private void initView() {
        mMainTabView = findViewById(R.id.v_main_tab);
        mtitleview = findViewById(R.id.Title_view);
    }

    public void onBackPressed(){
        try {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - mExitTime) > 2500) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
