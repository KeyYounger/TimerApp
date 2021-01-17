package com.example.testpassword;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences= getSharedPreferences("count", 0); // 存在则打开它，否则创建新的Preferences
        int count = preferences.getInt("count", 0); // 取出数据

        /**
         *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
         */
        if (count == 0) {
            Intent intent1 = new Intent();
            intent1.setClass(SplashActivity.this, ResgistarActivity.class);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent();
            intent2.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent2);
        }
        finish();

        //实例化Editor对象
        SharedPreferences.Editor editor = preferences.edit();
        //存入数据
        editor.putInt("count", 1); // 存入数据
        //提交修改
        editor.commit();

    }
}