package com.example.a11708.graduationproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11708.graduationproject.Beans.TimeUsingBean;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.Utils.DeviceUtil;
import com.example.a11708.graduationproject.Utils.SharedPreferencesUtils;
import com.example.a11708.graduationproject.model.SqlDaoModel;

import java.util.List;

//import org.litepal.LitePal;
//import org.litepal.crud.LitePalSupport;

public class ItemContext extends AppCompatActivity {
private TextView mTitle;
private EditText mTextContext;
private TextView mNeedTime;
private TextView mInitButton;
private TextView mSubmitButton;
private ImageView mBack;
private String mTextContextText;
private String mNeedTimeText;
private String mTitleText;
private int yourChoice;
private NumberPicker mHourPicker;
private NumberPicker mMinutePicker;
private Button mTimePickerButton;
private int mWorkingTimeHour = 0;
private int mWorkingTimeMinute = 0;
private PopupWindow mPopupWindow;
private View mWorkingView;
private int misCompleted = 0;
private int mid;
private String mUsername = Constant.UserName;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Submit();
            super.handleMessage(msg);
        }
    };

    private void Submit() {
        if(checkData()) {
            TimeUsingBean work = new TimeUsingBean();
            work.setmContext(mTextContextText);
            work.setmTitle(mTitleText);
            work.setmUsingTime(mNeedTimeText);
            work.setCompleted(misCompleted);
            work.setmUsername(mUsername);
            work.setmData(Constant.TIME);
            SqlDaoModel sqlDaoModel = new SqlDaoModel(getApplicationContext());
            if(mid == 0){
                sqlDaoModel.add(mTitleText,mTextContextText,mNeedTimeText,Constant.TIME,mUsername,0);

            }else{
                sqlDaoModel.update(mid,mTitleText,mTextContextText,mNeedTimeText,Constant.TIME,mUsername,0);
            }
            int requesdCode = 0;
            Intent intent = new Intent();
            setResult(requesdCode,intent);
            Toast.makeText(ItemContext.this, "保存成功", Toast.LENGTH_SHORT).show();

            /*Intent intent = new Intent(Constant.config.action);
            intent.putExtra("data", "change");
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            sendBroadcast(intent);*/
            finish();
        }

    }

    private boolean checkData() {
        mTextContextText = mTextContext.getText().toString();
        mTitleText = mTitle.getText().toString();
        mNeedTimeText = mNeedTime.getText().toString();
        if("".equals(mTextContextText)){
            Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT).show();
            return false;
        }
        if("请输入任务主题".equals(mTitleText)){
            Toast.makeText(this,"请输入任务主题",Toast.LENGTH_SHORT).show();
            return false;
        }
        if("预计完成耗时：0小时0分钟".equals(mNeedTimeText)){
            Toast.makeText(this,"请输入时间",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_context);
        init();
        bindEvent();
        CheckIsComplete();
    }

    private void bindEvent() {
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTitleDialog();
            }
        });
        mNeedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopwindowSetting(view);
            }
        });
        mTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWorkingTimeHour = mHourPicker.getValue();
                mWorkingTimeMinute = mMinutePicker.getValue();
                mNeedTime.setText("预计完成耗时："+mWorkingTimeHour + "小时" + mWorkingTimeMinute + "分钟");
                mPopupWindow.dismiss();
            }
        });
        mInitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNeedTime.setText("预计完成耗时：0小时0分钟");
                mTextContext.setText("");
                mTitle.setText("请输入任务主题");
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //md5DeviceName = DeviceUtil.getAndroidId(getApplicationContext());
                handler.sendEmptyMessageDelayed(0,500);
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void PopwindowSetting(View view) {
        mHourPicker.setValue(mWorkingTimeHour);
        mMinutePicker.setValue(mWorkingTimeMinute);
        // 强制隐藏键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        mPopupWindow = new PopupWindow(mWorkingView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        // 触屏位置如果在选择框外面则销毁弹出框
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(mWorkingView,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //mPopupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // 设置背景透明度
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }

        });

    }

    private void showTitleDialog() {
        final String[] items = { "工作","学习","休息","其他" };
        yourChoice = -1;
        AlertDialog.Builder TitleChoiceDialog =
                new AlertDialog.Builder(ItemContext.this);
        TitleChoiceDialog.setTitle("任务类别");
        // 第二个参数是默认选项，此处设置为0
        TitleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        TitleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            mTitle.setText(items[yourChoice]);
                        }
                    }
                });
        TitleChoiceDialog.show();

    }
    private void init() {
        mid = getIntent().getIntExtra("mid",-1);
        InitNumberPicker();
        mTitle = (TextView)findViewById(R.id.activity_createvisit_tasktitle);
        mTextContext = (EditText)findViewById(R.id.activity_createvisit_et);
        mNeedTime = (TextView)findViewById(R.id.need_time);
        mInitButton = (TextView)findViewById(R.id.activity_createvisit_init);
        mSubmitButton = (TextView)findViewById(R.id.activity_createvisit_submit);
        mBack = (ImageView)findViewById(R.id.activity_createvisit_back);
        initdata(mid);

    }

    private void initdata(int id) {
        //md5DeviceName = DeviceUtil.getAndroidId(getApplicationContext());
        if(id != -1&&id != 0){
            //TimeUsingBean bean = LitePal.find(TimeUsingBean.class,id);
            SqlDaoModel sqlDaoModel = new SqlDaoModel(getApplicationContext());
            List<TimeUsingBean> bean = sqlDaoModel.find(mid);
            if(bean.get(0).getmTitle() == null){
                bean.get(0).setmTitle("请输入任务主题");

            }
            if(bean.get(0).getmUsingTime() == null){
                bean.get(0).setmUsingTime("预计完成耗时：0小时0分钟");
            }
            mTextContext.setText(bean.get(0).getmContext());
            mTitle.setText(bean.get(0).getmTitle());
            mNeedTime.setText(bean.get(0).getmUsingTime());
        }else if(id == 0){
            mTextContext.setText("");
            mTitle.setText("请输入任务主题");
            mNeedTime.setText("预计完成耗时：0小时0分钟");
        }
    }

    private void CheckIsComplete() {
        if(mNeedTime.getText().toString() == "预计完成耗时：0小时0分钟" ||
        mTextContext.getText().toString() == "" ||
        mTitle.getText().toString() == "请输入任务主题"){
            misCompleted = 0;
        }else{
            misCompleted = 0;
        }

    }

    private void InitNumberPicker() {
        mWorkingView = LayoutInflater.from(this).inflate(R.layout.view_popwindow, null);
        mTimePickerButton = (Button) mWorkingView.findViewById(R.id.submit_workingAge);
        mHourPicker = (NumberPicker) mWorkingView.findViewById(R.id.HournumberPicker);
        mHourPicker.setMaxValue(24);
        mHourPicker.setMinValue(0);
        mHourPicker.setFocusable(false);
        mHourPicker.setFocusableInTouchMode(false);
        mHourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mMinutePicker = (NumberPicker) mWorkingView.findViewById(R.id.MinutenumberPicker);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setFocusable(false);
        mMinutePicker.setFocusableInTouchMode(false);
        mMinutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }


}
