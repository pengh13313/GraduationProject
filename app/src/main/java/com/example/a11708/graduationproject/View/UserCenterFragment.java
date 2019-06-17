package com.example.a11708.graduationproject.View;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.Login.LoginActivity;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.Utils.ExcelUtils;
import com.example.a11708.graduationproject.Utils.SharedPreferencesUtils;
import com.example.a11708.graduationproject.Utils.SqliteUtil;
import com.example.a11708.graduationproject.View.roundImage.RoundedImageView;
import com.example.a11708.graduationproject.activity.MainPage;
import com.example.a11708.graduationproject.model.SqlDaoModel;

import java.io.File;
import java.net.URI;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCenterFragment extends Fragment {
    private View mView;
    private RelativeLayout mSettingLoginButton;
    private RelativeLayout mRestButton;
    private RelativeLayout mExcelButton;
    private RelativeLayout mDeleteButton;
    private RoundedImageView mRoundedImageView;
    private NumberPicker mHourPicker;
    private NumberPicker mMinutePicker;
    private static int mWorkingTimeHour = 0;
    private static int mWorkingTimeMinute = 0;
    private PopupWindow mPopupWindow;
    private View mWorkingView;
    private Button mTimePickerButton;
    private TextView mUsername;


    public UserCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_user_center, container, false);
        } else {
            ViewParent viewParent = mView.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeAllViews();
            }
        }
        initViews();
        bindEvent();
        //getUserInfo();
        return mView;
    }

    private void initViews() {
        InitNumberPicker();
        mSettingLoginButton = (RelativeLayout) mView.findViewById(R.id.settings_login_item);
        mRestButton = (RelativeLayout) mView.findViewById(R.id.settings_rest_item);
        mExcelButton = (RelativeLayout) mView.findViewById(R.id.settings_Excel_item);
        mDeleteButton = (RelativeLayout) mView.findViewById(R.id.settings_clear_item);
        mUsername = (TextView)mView.findViewById(R.id.nick_name);
        mRoundedImageView = (RoundedImageView) mView.findViewById(R.id.iv_head);
        /*if(!SharedPreferencesUtils.getValue(Constant.config.IMAGE_URI,"").equals("")){
            String path = SharedPreferencesUtils.getValue(Constant.config.IMAGE_URI,"");
            File file = new File(path);
            Glide.with(getActivity()).load(file).into(mRoundedImageView);
        }*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            if(requestCode == 1){
                try {
                    Cursor cursor = getActivity().managedQuery(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
//                    if (cursor != null) {
//                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        cursor.moveToFirst();
//                        String path = cursor.getString(columnIndex);
                        //SharedPreferencesUtils.setValue(Constant.config.IMAGE_URI, uri);
                    String path = uri.getPath();
                    SharedPreferencesUtils.setValue(Constant.config.IMAGE_URI, path);
                    Glide.with(getActivity()).load(uri).into(mRoundedImageView);
                    ((MainPage)getActivity()).ChangeImage(uri);
                        //mRoundedImageView.setImageURI(uri);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void bindEvent() {
        if(!Constant.UserName.equals("")) {
            mUsername.setText(Constant.UserName);
        }
        mSettingLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtils.remove(Constant.SpKey.USER_NAME);
                SharedPreferencesUtils.remove(Constant.SpKey.USER_PASSWORD);
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        mRestButton.setOnClickListener(new View.OnClickListener() {
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
                if(mWorkingTimeHour != 0 || mWorkingTimeMinute != 0) {
                    Constant.config.Worktime[0] = String.valueOf(mWorkingTimeHour);
                    Constant.config.Worktime[1] = String.valueOf(mWorkingTimeMinute);
                    Toast.makeText(getActivity(),"设置完毕", Toast.LENGTH_SHORT).show();
                    Log.e("resthour",Constant.config.Worktime[0]);
                    Log.e("restminute",Constant.config.Worktime[1]);
                    mPopupWindow.dismiss();
                }

            }
        });

        mExcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExcelUtils utils = new ExcelUtils(getActivity(),Constant.UserName);
                utils.ExportExcel();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        mRoundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);

            }
        });

    }

    private void showDialog() {
        final AlertDialog.Builder Dialog =
                new AlertDialog.Builder(getActivity());
        Dialog.setTitle("警告");
        Dialog.setMessage("确认清除用户数据？");
        Dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SqlDaoModel model = new SqlDaoModel(getActivity());
                model.deleteAll();
                Constant.ClearUp = true;
            }
        });
        Dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        Dialog.show();
    }


    private void PopwindowSetting(View view) {
        mHourPicker.setValue(mWorkingTimeHour);
        mMinutePicker.setValue(mWorkingTimeMinute);
        // 强制隐藏键盘
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }

        });
    }

    private void InitNumberPicker() {
        mWorkingView = LayoutInflater.from(getActivity()).inflate(R.layout.rest_popwindow, null);
        mTimePickerButton = (Button) mWorkingView.findViewById(R.id.submit_workingRest);
        mHourPicker = (NumberPicker) mWorkingView.findViewById(R.id.WorkHournumberPicker);
        mHourPicker.setMaxValue(24);
        mHourPicker.setMinValue(0);
        mHourPicker.setFocusable(false);
        mHourPicker.setFocusableInTouchMode(false);
        mHourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mMinutePicker = (NumberPicker) mWorkingView.findViewById(R.id.WorkMinutenumberPicker);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setFocusable(false);
        mMinutePicker.setFocusableInTouchMode(false);
        mMinutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }
}
