package com.example.testpassword;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class LockService extends Service {
    public LockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName who = new ComponentName(this, LockReceiver.class);
        // 判断是否已经开启管理员权限
        if (dpm.isAdminActive(who)) {
            // 锁屏
            dpm.lockNow();
        } else {
            // 如果为未开启 提示
            Toast.makeText(LockService.this, "请先开启管理员权限!", Toast.LENGTH_SHORT)
                    .show();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}