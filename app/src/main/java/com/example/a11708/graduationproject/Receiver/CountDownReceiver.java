package com.example.a11708.graduationproject.Receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.a11708.graduationproject.Config.Constant;
import com.example.a11708.graduationproject.R;

public class CountDownReceiver extends BroadcastReceiver {
    private boolean isStartCount = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(Constant.config.REST_CHANNEL,
                    "Primary Channel2", NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableVibration(true);
            manager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(context, Constant.config.REST_CHANNEL);

        }else {
            builder = new NotificationCompat.Builder(context);
        }
        builder.setAutoCancel(true)
                .setChannelId(Constant.config.REST_CHANNEL)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("该休息了")
                .setContentTitle("该休息了")
                .setContentText("该休息了")
                .setSmallIcon(R.drawable.clock)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentInfo("Info");
        if(manager != null) {
            manager.notify(2, builder.build());
        }
        Log.e("xiuxi","ok");



    }

    public boolean isCountStart(){
        return isStartCount;
    }
}
