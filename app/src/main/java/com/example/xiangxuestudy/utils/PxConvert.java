package com.example.xiangxuestudy.utils;

import android.app.Application;
import android.util.DisplayMetrics;

public class PxConvert {

    private static float density = -1f;

    public static int dp2px(float value) {
        if(density == -1) {
            Application application = AutoSizeUtils.getApplicationByReflect();
            DisplayMetrics metrics = application.getResources().getDisplayMetrics();
            density = metrics.density;
        }
        return (int) (value * density + 0.5f);
    }
}
