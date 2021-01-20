package com.example.testpassword.util;

public class TimeUtil {

    public static String millsToMinutes(long mills){
        long currentTimeMillis = System.currentTimeMillis();
        long restMills = (long) ((mills-currentTimeMillis)*0.05);
        int minutes = (int) (restMills/1000/60);
        if (minutes<=0) return "不足1";
        else return String.valueOf(minutes);
    }

}
