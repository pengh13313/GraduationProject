package com.example.a11708.graduationproject.Beans;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class TimeUsingBean{

    private int isCompleted;
    private String mTitle;
    private String mContext;
    private String mUsingTime;
    private int mID;
    private String mData;
    private String mUsername;


    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public int getCompleted() {
        return isCompleted;
    }

    public void setCompleted(int completed) {
        isCompleted = completed;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContext() {
        return mContext;
    }

    public void setmContext(String mContext) {
        this.mContext = mContext;
    }

    public String getmUsingTime() {
        return mUsingTime;
    }

    public void setmUsingTime(String mUsingTime) {
        this.mUsingTime = mUsingTime;
    }





}
