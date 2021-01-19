package com.example.testpassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NativeActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testpassword.util.NotificationUtil;
import com.example.testpassword.util.TimeUtil;

import java.util.Calendar;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private EditText pwd;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pwd = (EditText)findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = getIntent();
                Long mills = in.getLongExtra("mills", 0);
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);

                if (pwd!=null && pwd.getText().toString().equals(pref.getString("password", ""))){
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Intent intent = new Intent(LoginActivity.this, LockService.class);
                    PendingIntent pendingIntent = PendingIntent.getService(LoginActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    if(Build.VERSION.SDK_INT < 19){
                        alarmManager.set(AlarmManager.RTC_WAKEUP, mills, pendingIntent);
                    }else{
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, mills, pendingIntent);
                    }
                    Toast.makeText(LoginActivity.this, "密码正确", Toast.LENGTH_SHORT).show();
                    // 当剩余时间不足总时间的5%时，设置剩余时长通知
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT > 23){
                        StatusBarNotification[] activeNotifications = notificationManager.getActiveNotifications();
                        for (int i=0; i<activeNotifications.length; i++){
                            notificationManager.cancel(activeNotifications[i].getId());
                        }
                    }
                    // 构造通知点击活动
                    Intent notifyIntent = new Intent(LoginActivity.this, NotificationActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(LoginActivity.this, 0, notifyIntent, 0);
                    Notification notification = new NotificationCompat.Builder(LoginActivity.this, NotificationUtil.createNotificationChannel(LoginActivity.this))
                                                    .setSmallIcon(R.drawable.notification_icon)
                                                    .setContentTitle("剩余时长")
                                                    .setContentText("您剩余可使用手机时间还有"+ TimeUtil.millsToMinutes(mills)+"分钟")
                                                    .setPriority(NotificationCompat.PRIORITY_MAX)
                                                    // Set the intent that will fire when the user taps the notification
                                                    .setContentIntent(pi)
                                                    .setAutoCancel(true)
                                                    .build();
                    notificationManager.notify(1, notification);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}