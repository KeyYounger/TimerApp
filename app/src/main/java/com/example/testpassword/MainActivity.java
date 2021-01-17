package com.example.testpassword;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView timeShow;
    DateFormat format = DateFormat.getDateTimeInstance();
    Calendar calendar = Calendar.getInstance(Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeShow = (TextView) findViewById(R.id.time);
        Button btnDate = (Button) findViewById(R.id.btn_date);
        Button btnTime = (Button) findViewById(R.id.btn_time);
        updateTimeShow();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateTimeShow();
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                updateTimeShow();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
                updateTimeShow();
            }
        });
    }

    public void openAdmin(View view) {
        // 创建一个Intent 添加设备管理员
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        // 激活MyAdmin广播接收着
        ComponentName who = new ComponentName(this, LockReceiver.class);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
        // 说明用户开启管理员权限的好处
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "开启可以一键锁屏，防止勿碰");
        startActivity(intent);
        Toast.makeText(MainActivity.this, "管理员权限已开启!", Toast.LENGTH_SHORT).show();
    }

    public void start(View view) {
        Intent in = new Intent(this, LoginActivity.class);
        in.putExtra("mills", calendar.getTimeInMillis());
        startActivity(in);
    }

    private void updateTimeShow() {
        timeShow.setText(format.format(calendar.getTime()));
    }

}