package com.example.a11708.graduationproject.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.a11708.graduationproject.Beans.TimeUsingBean;
import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.Utils.SqliteUtil;

import java.util.ArrayList;
import java.util.List;

public class SqlDaoModel {
    private SqliteUtil sqLiteOpenHelper;
    private Context mcontext;

    public SqlDaoModel(Context context) {
        sqLiteOpenHelper = SqliteUtil.getmInstance(context);
        mcontext = context;

    }

    public long add(String mTitle, String mContext,String mUsingTime,String mData,String mUsername,int isCompleted) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
//      db.execSQL("insert  into time (name,number) values (?,?)", new String[]{name, number});
        ContentValues values=new ContentValues();
        values.put("mTitle",mTitle);
        values.put("mContext",mContext);
        values.put("mUsingTime",mUsingTime);
        values.put("mData",mData);
        values.put("mUsername",mUsername);
        values.put("isCompleted",isCompleted);
        //id返回的是数据的id，如果返回-1时表示未添加成功
        long id=db.insert("time",null,values);
        db.close();
        return id;
    }

    public Cursor Excelcursor(){
        SQLiteDatabase db=sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from time where mData = ? and mUsername = ?",new String[]{Constant.TIME,Constant.UserName});

        return cursor;
    }

    public List<TimeUsingBean> findAll(String Username){
        List<TimeUsingBean> list=new ArrayList();
        SQLiteDatabase db=sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from time where mData = ? and mUsername = ?",new String[]{Constant.TIME,Username});

        while(cursor.moveToNext()){
            int mID=cursor.getInt(cursor.getColumnIndex("mID"));
            String mTitle=cursor.getString(cursor.getColumnIndex("mTitle"));
            String mContext=cursor.getString(cursor.getColumnIndex("mContext"));
            String mData=cursor.getString(cursor.getColumnIndex("mData"));
            String mUsername=cursor.getString(cursor.getColumnIndex("mUsername"));
            int isCompleted=cursor.getInt(cursor.getColumnIndex("isCompleted"));
            String mUsingTime=cursor.getString(cursor.getColumnIndex("mUsingTime"));
            TimeUsingBean timeUsingBean=new TimeUsingBean();
            timeUsingBean.setmID(mID);
            timeUsingBean.setmTitle(mTitle);
            timeUsingBean.setmContext(mContext);
            timeUsingBean.setmData(mData);
            timeUsingBean.setmUsingTime(mUsingTime);
            timeUsingBean.setmUsername(mUsername);
            timeUsingBean.setCompleted(isCompleted);
            list.add(timeUsingBean);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<TimeUsingBean> find(int mid){
        List<TimeUsingBean> list=new ArrayList();
        SQLiteDatabase db=sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from time where mID = ?",new String[]{String.valueOf(mid)});
        while(cursor.moveToNext()) {
            int mID = cursor.getInt(cursor.getColumnIndex("mID"));
            String mTitle = cursor.getString(cursor.getColumnIndex("mTitle"));
            String mContext = cursor.getString(cursor.getColumnIndex("mContext"));
            String mData = cursor.getString(cursor.getColumnIndex("mData"));
            String mUsername = cursor.getString(cursor.getColumnIndex("mUsername"));
            int isCompleted = cursor.getInt(cursor.getColumnIndex("isCompleted"));
            String mUsingTime = cursor.getString(cursor.getColumnIndex("mUsingTime"));
            TimeUsingBean timeUsingBean = new TimeUsingBean();
            timeUsingBean.setmID(mID);
            timeUsingBean.setmTitle(mTitle);
            timeUsingBean.setmContext(mContext);
            timeUsingBean.setmData(mData);
            timeUsingBean.setmUsingTime(mUsingTime);
            timeUsingBean.setmUsername(mUsername);
            timeUsingBean.setCompleted(isCompleted);
            list.add(timeUsingBean);
        }
        cursor.close();
        db.close();
        return list;
    }


    public void update(int mID,String mTitle, String mContext,String mUsingTime,String mData,String mUsername,int isCompleted){
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
//        db.execSQL("update time set name=? where number=?", new String[]{name, number});
        ContentValues values=new ContentValues();
        values.put("mTitle",mTitle);
        values.put("mContext",mContext);
        values.put("mUsingTime",mUsingTime);
        values.put("mData",mData);
        values.put("mUsername",mUsername);
        values.put("isCompleted",isCompleted);
        db.update("time",values,"mID=?",new String[]{String.valueOf(mID)});
        db.close();
    }


    public void delete(int mID){
        //List<TimeUsingBean> list  = new ArrayList<>();
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        db.execSQL("delete from time where mID = ?",new String[]{String.valueOf(mID)});

    }

    public void deleteAll(){
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        db.execSQL("delete from time");
        List<TimeUsingBean> list = new ArrayList<>();
        list = findAll(Constant.UserName);
        if(list.size() == 0){
            Toast.makeText(mcontext,"删除表完毕",Toast.LENGTH_SHORT).show();
        }
    }


}
