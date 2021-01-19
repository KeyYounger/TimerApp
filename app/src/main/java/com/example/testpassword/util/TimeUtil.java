package com.example.testpassword.util;

public class TimeUtil {

    public static long millsToMinutes(long mills){
        long currentTimeMillis = System.currentTimeMillis();
        long restMills = (long) ((mills-currentTimeMillis)*0.05);
        int minutes = (int) (restMills/1000/60);
        return minutes;
    }

}
