package com.example.testpassword.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;

public class AlarmUtil {

    public static void setAlarm(AlarmManager alarmManager, PendingIntent pendingIntent, long mills)
    {
        if(Build.VERSION.SDK_INT < 19){
            alarmManager.set(AlarmManager.RTC_WAKEUP, mills, pendingIntent);
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mills, pendingIntent);
        }
    }

}
