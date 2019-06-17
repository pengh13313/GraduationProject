package com.example.a11708.graduationproject.Utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class SqliteUtil extends SQLiteOpenHelper {
    public static final String TAG = "DBHelper";
    // 数据库名称
    private static final String DATABASE_NAME_STR = "Time";
    //数据库后缀名
    private static final String DATABASE_NAME_SUFFIX = ".db";
    //版本号
    private static final int DB_VERSION = 1;


    private volatile static SqliteUtil mInstance = null;

    private static final String Create_db = "create table if not exists Time ("
            +"mID Integer primary key autoincrement,"+
            "mTitle varchar(20)," +
            "mContext varchar(20)," +
            "mUsingTime varchar(20)," +
            "mUsername varchar(20),"+
            "mData varchar(20),"+
            "isCompleted varchar(20))";

    public SqliteUtil(Context context) {
        super(context, DATABASE_NAME_STR + DATABASE_NAME_SUFFIX, null, DB_VERSION);
    }

    public synchronized static SqliteUtil getmInstance(Context context){
        if(mInstance == null){
            synchronized (SqliteUtil.class){
                mInstance = new SqliteUtil(context);
            }
        }
        return mInstance;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

/*    public void open(Context context){
        mInstance = getmInstance(context);
        try {
            db = mInstance.getWritableDatabase();
        }catch (SQLiteException ex){
            ex.printStackTrace();
        }
        db.beginTransaction();
    }

    public void close() {
        db.setTransactionSuccessful();
        db.endTransaction();
        if (db != null) {
            db.close();
            db = null;

        }
    }*/






}
