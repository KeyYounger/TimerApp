package com.example.testpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}