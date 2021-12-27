package com.example.xiangxuestudy.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.example.xiangxuestudy.MyApplication;
import com.example.xiangxuestudy.R;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Date: 2021/11/9 10:16
 * author: leo
 * Description:
 */
@Keep
public class ToastPlus {
    private static Toast toast;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private ToastPlus() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    public static void showShort(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

//    public static void showShort(@StringRes int resId) {
//        show(resId, Toast.LENGTH_SHORT);
//    }

    public static void showLong(CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

//    public static void showLong(@StringRes int resId) {
//        show(resId, Toast.LENGTH_LONG);
//    }

//    private static void show(@StringRes int resId, int duration) {
//        CharSequence text = Utils.getApp().getResources().getText(resId);
//        show(text, duration);
//    }

    private static void show(CharSequence text, int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        customToastView(text, duration);
    }

    //强制主线程
    private static void customToastView(final CharSequence msg, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            customToastViewOnUIThread(msg, duration);
        } else {
            mHandler.post(() -> {
                customToastViewOnUIThread(msg, duration);
            });
        }
    }

    /**
     * 自定义toast的布局
     *
     * @param msg：toast提示语
     * @param duration：显示时间
     */
    private static void customToastViewOnUIThread(CharSequence msg, int duration) {
        Context context= MyApplication.getContext();
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, PxConvert.dp2px(120));
        toast.setDuration(duration);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.toast_layout, null);
        TextView tvMsg = view.findViewById(R.id.tv_msg);
        if (tvMsg == null) {
            return;
        }
        tvMsg.setText(msg);
        tvMsg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tvMsg.setBackground(getGradientDrawable(context, tvMsg.getHeight() >> 1));
                tvMsg.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
        toast.setView(view);

        hookToast();
        try {
            toast.show();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }


    /**
     * 解决 7.1版本toast显示bug 具体请看Android7.1 Toast源码
     */
    @SuppressLint("HandlerLeak")
    private static void hookToast() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            try {
                Field mTNField = Toast.class.getDeclaredField("mTN");
                mTNField.setAccessible(true);
                Object mTN = mTNField.get(toast);
                // 获取 mTN 中的 mHandler 字段对象
                Field mHandlerField = mTNField.getType().getDeclaredField("mHandler");
                mHandlerField.setAccessible(true);
                final Handler mHandler = (Handler) mHandlerField.get(mTN);
                mHandlerField.set(mTN, new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        // 捕获这个异常，避免程序崩溃
                        try {
                            Objects.requireNonNull(mHandler).handleMessage(msg);
                        } catch (WindowManager.BadTokenException ignored) {
                            Log.i("Toast", "android 7.1 系统toast异常。。。。");
                        }
                    }
                });
            } catch (IllegalAccessException | NoSuchFieldException ignored) {

            }
        }
    }

    /**
     * 动态设置背景色
     *
     * @param context
     * @param radius
     */
    private static GradientDrawable getGradientDrawable(Context context, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(0x99000000);
        drawable.setCornerRadius(radius);
        return drawable;
    }

}
