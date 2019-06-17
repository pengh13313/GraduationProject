package com.example.a11708.graduationproject.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.Receiver.AlarmclockReceive;
import com.example.a11708.graduationproject.Receiver.CountDownReceiver;

import java.util.Calendar;

public class AlarmService extends Service {
    private String mTime;
    private String countHour;
    private String countMinute;
    //private boolean isStartCount = false;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTime = intent.getStringExtra("starttime");
        if(mTime !=null) {
            String a = mTime.replace("预计完成耗时：", "");
            String b = a.replace("分钟", "");
            String c = b.replace("小时", ",");
            String[] str = c.split(",");
            AlarmManager mAlarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
            long triggerAtTime = SystemClock.elapsedRealtime() + Integer.parseInt(str[0]) * 60 * 60 * 1000 + Integer.parseInt(str[1]) * 60 * 1000;
            Intent mIntent = new Intent(this, AlarmclockReceive.class);
            intent.setAction("111111");
            PendingIntent mPendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, 0);
            //        是否倒计时开启
            if (intent != null  && Constant.config.Worktime[0] !="0"&& Constant.config.Worktime[1]!="0") {
                AlarmManager mAlarmManager2 = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
                countHour = Constant.config.Worktime[0];
                countMinute = Constant.config.Worktime[1];
                long triggerAtTime2 = SystemClock.elapsedRealtime() + Integer.parseInt(countHour) * 60 * 60 * 1000 + Integer.parseInt(countMinute) * 60 * 1000;
                Log.e("countdown", "：" + countHour + " " + countMinute + " ");
                Intent mIntent2 = new Intent(this, CountDownReceiver.class);
                intent.setAction("2222");
                PendingIntent mPendingIntent2 = PendingIntent.getBroadcast(this, 2, mIntent2, 0);
                mAlarmManager2.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime2, mPendingIntent2);

            }
            //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
            //AlarmManager.RTC_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间，状态值为0；
            //mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, tri, mPendingIntent);
            mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, mPendingIntent);
        }



        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmclockReceive.class);
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(mPendingIntent);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
