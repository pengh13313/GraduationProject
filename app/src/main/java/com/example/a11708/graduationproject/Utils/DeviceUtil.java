package com.example.a11708.graduationproject.Utils;

import android.content.Context;
import android.provider.Settings;

public class DeviceUtil {
    public static String getAndroidId(Context context) {
        String androidId = "";
        if (context == null) {
            return "";
        }
        try {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure
                    .ANDROID_ID);
            if (androidId == null) {
                androidId = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MD5Util.md5(androidId);
    }
}
