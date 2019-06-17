package com.example.a11708.graduationproject.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a11708.graduationproject.R;

public class MainTabView extends LinearLayout {
    public static final int TAB_TYPE_LIVETIME_FRAGMENT = 1;//时间设置中心
    public static final int TAB_TYPE_PACTURE_FRAGMENT = 2;//形成图片中心
    public static final int TAB_TYPE_USER_FRAGMENT = 3;//个人中心
    private Context mContext;
    private TextView mTimeTextView;
    private TextView mPactureTextView;
    private TextView mUserTextView;
    private OnTabClickListener mOnTabClickListener;

    public MainTabView(Context context) {
        super(context);
        init(context);
    }

    public MainTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnTabClickListener(OnTabClickListener mOnTabClickListener) {
        this.mOnTabClickListener = mOnTabClickListener;
    }

    private void init(Context context) {
        mContext = context;
        initViews();
        bindEvent();
    }

    private void bindEvent() {
        findViewById(R.id.main_time).setOnClickListener(this::onClick);
        findViewById(R.id.main_pacture).setOnClickListener(this::onClick);
        findViewById(R.id.main_user).setOnClickListener(this::onClick);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.main_time:
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.onClick(TAB_TYPE_LIVETIME_FRAGMENT);
                }
                break;
            case R.id.main_pacture:
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.onClick(TAB_TYPE_PACTURE_FRAGMENT);
                }
                break;
            case R.id.main_user:
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.onClick(TAB_TYPE_USER_FRAGMENT);
                }
                break;

        }
    }

    public void refreshTabViewState(int tabType){
        switch (tabType) {
            case TAB_TYPE_LIVETIME_FRAGMENT:
                mTimeTextView.setSelected(true);
                mPactureTextView.setSelected(false);
                mUserTextView.setSelected(false);

                break;
            case TAB_TYPE_PACTURE_FRAGMENT:
                mTimeTextView.setSelected(false);
                mPactureTextView.setSelected(true);
                mUserTextView.setSelected(false);
                break;
            case TAB_TYPE_USER_FRAGMENT:
                mPactureTextView.setSelected(false);
                mPactureTextView.setSelected(false);
                mUserTextView.setSelected(true);
                break;
        }
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_main_tab, this);
        mTimeTextView = findViewById(R.id.tv_main_time);
        mPactureTextView = findViewById(R.id.tv_main_pacture);
        mUserTextView = findViewById(R.id.tv_main_user);
    }

    public interface OnTabClickListener{
        void onClick(int type);
    }


}
