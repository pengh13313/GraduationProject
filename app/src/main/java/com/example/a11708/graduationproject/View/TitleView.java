package com.example.a11708.graduationproject.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.a11708.graduationproject.Beans.User;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.Utils.UIUtils;
import com.example.a11708.graduationproject.View.roundImage.RoundedImageView;
import com.example.a11708.graduationproject.activity.MainPage;

import java.util.Calendar;


public class TitleView extends LinearLayout {
    String mTitleStr;
    int mLeftDrawableId;

    private TextView mTitle;
    public RoundedImageView mImageView;
    public TextView mCalendarImage;
    private RequestManager glide;

    Calendar calendar = Calendar.getInstance();
    //获取系统的日期
//年
    int year = calendar.get(Calendar.YEAR);
    //月
    int month = calendar.get(Calendar.MONTH);
    //日
    int day = calendar.get(Calendar.DAY_OF_MONTH);



    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //TypedArray用来简化工作,将attrs转换简化
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        mImageView = new RoundedImageView(context);
        mImageView.setId(R.id.iv_head);
        int size = UIUtils.dip2px(context, 46);
        LayoutParams leftParams = new LayoutParams(size, size);
        //与左侧元素的矩形间隔
        leftParams.leftMargin = UIUtils.dip2px(context, 10);
        int padding = UIUtils.dip2px(context, 5);
        mImageView.setPadding(padding, padding, padding, padding);
        mImageView.setOval(true);
        addView(mImageView, leftParams);

        mTitle = new TextView(context);
        LayoutParams titleParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleParam.weight=1;
        titleParam.gravity=Gravity.CENTER_VERTICAL;
        mTitle.setGravity(Gravity.LEFT);
        mTitle.setTextSize(14);
        addView(mTitle, titleParam);
        //日历
        mCalendarImage = new TextView(context);
        mCalendarImage.setText(year+"/"+month+"/"+day);
        Constant.TIME = year+"/"+month+"/"+day;
        TextPaint tp = mCalendarImage.getPaint();
        tp.setFakeBoldText(true);
        mCalendarImage.setBackgroundResource(R.drawable.shape_button);
        try {
            Resources resources = context.getResources();
            Drawable left = resources.getDrawable(R.drawable.clock);
            left.setBounds(10, 0, (left.getIntrinsicWidth() + 20)/2, (left.getIntrinsicHeight())/2);
            mCalendarImage.setCompoundDrawablePadding(20);
            mCalendarImage.setCompoundDrawables(left, null, null, null);
            mCalendarImage.setGravity(Gravity.CENTER);
            mCalendarImage.setTextColor(Color.parseColor("#C0C0C0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LayoutParams CenterParam = new LayoutParams(LayoutParams.WRAP_CONTENT, UIUtils.dip2px(context, 30));
        //rightParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        //rightParam.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        CenterParam.gravity=Gravity.CENTER_VERTICAL;
        CenterParam.rightMargin = UIUtils.dip2px(context, 10);
        mCalendarImage.setMinWidth(UIUtils.dip2px(context, 97));
        mCalendarImage.setGravity(Gravity.CENTER);
        mCalendarImage.setSingleLine(true);
        mCalendarImage.setMaxWidth(UIUtils.dip2px(context, 180));
        addView(mCalendarImage, CenterParam);
        /*mCalendarImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        mCalendarImage.setText(year+"/"+month+"/"+day);
                        Constant.TIME = year+"/"+month+"/"+day;
                        isrefresh = true;

                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                mDatePickerDialog.show();
            }
        });*/

        mTitleStr = a.getString(R.styleable.TitleView_tv_center_text);
        if (!TextUtils.isEmpty(mTitleStr)) {
            mTitle.setText(mTitleStr);
        }
        mLeftDrawableId = a.getResourceId(R.styleable.TitleView_tv_left_drawble, -1);
        if (mLeftDrawableId != -1) {
            mImageView.setImageResource(mLeftDrawableId);
        } else {
            mImageView.setImageResource(R.drawable.header_default);
        }
        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (UserModel.getInstance().hasLogin()) {
                    //原来跳转个人信息编辑页面 v1.2 改为跳转到我的 tab页面
                    //EditUserActivity.startActivity(context);
                    Intent intent=new Intent(context, MainPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.IntentExtra.MAIN_JUMP_TYPE, MainTabView.TAB_TYPE_USER_FRAGMENT);
                    context.startActivity(intent);

                } else {
                    LoginUtils.userLogin(context);
                }*/
            }
        };
        mImageView.setOnClickListener(clickListener);
        mTitle.setOnClickListener(clickListener);
        setBackgroundColor(Color.parseColor("#C0C0C0"));


    }


    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
    }


    public void setTitle(String title) {
        if (mTitle == null || title == null) {
            return;
        }
        mTitle.setText(title);
    }

    public void setHeader(String url) {
        if (TextUtils.isEmpty(url) || mImageView == null) {
            return;
        }
        this.displayImageView(mImageView, url, R.drawable.header_default);
    }


    public void refreshView(User user) {
        if (user == null) {
            setTitle("立即登录");
            mImageView.setImageResource(R.drawable.header_default);
            return;
        }
        setTitle(user.getName());
        //setHeader(user.getImg());
    }

    public void displayImageView(ImageView imageView, String url, @DrawableRes int defaultResourceId) {
        if (TextUtils.isEmpty(url) || imageView == null|| !URLUtil.isNetworkUrl(url)) {
            return;
        }
        RequestOptions options = new RequestOptions();
        options.error(defaultResourceId);
        options.placeholder(defaultResourceId);
        glide.load(url).apply(options).into(imageView);
    }

}
