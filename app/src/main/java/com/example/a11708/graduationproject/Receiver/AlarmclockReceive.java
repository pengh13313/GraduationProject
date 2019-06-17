package com.example.a11708.graduationproject.Receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.R;
import com.example.a11708.graduationproject.Service.AlarmService;

public class AlarmclockReceive extends BroadcastReceiver {
    private boolean isStartCount = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "收到定时广播", Toast.LENGTH_LONG).show();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(Constant.config.PRIMARY_CHANNEL,
                    "Primary Channel", NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableVibration(true);
            manager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(context, Constant.config.PRIMARY_CHANNEL);

        }else {
            builder = new NotificationCompat.Builder(context);
        }
            builder.setAutoCancel(true)
                    .setChannelId(Constant.config.PRIMARY_CHANNEL)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setTicker("到点了")
                    .setContentTitle("到点了")
                    .setContentText("到点了")
                    .setSmallIcon(R.drawable.clock)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentInfo("Info");
            if(manager != null) {
                 manager.notify(1, builder.build());
             }
             Constant.config.isCountDown = false;



    }

    public boolean isCountStart(){
        return isStartCount;
    }



}

