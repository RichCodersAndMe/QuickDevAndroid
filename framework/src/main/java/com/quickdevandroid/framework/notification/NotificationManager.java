package com.quickdevandroid.framework.notification;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationManagerCompat;

import com.quickdevandroid.framework.QDFApplication;
import com.quickdevandroid.framework.R;

import java.util.Random;

/**
 * 通知消息包装器
 * Created by LinXiao on 2016-11-27.
 */
public class NotificationManager {
    private static final String TAG = NotificationManager.class.getSimpleName();

    public static String KEY_NOTIFICATION_EXTRA = "framework_notification_extra";
    public static final String KEY_TARGET_ACTIVITY_NAME = "key_dest_name";

    private static int defaultIconRes = R.drawable.ic_notify_default;

    private NotificationManager() {}

    public static void setDefaultIconRes(@DrawableRes int resId) {
        defaultIconRes = resId;
    }

    @DrawableRes
    public static int getDefaultIconRes() {
        return defaultIconRes;
    }

    public static SimpleNotificationBuilder buildNotification(Context context, String title, String contentText) {
        return new SimpleNotificationBuilder(context, title, contentText);
    }

    public static SimpleNotificationBuilder buildNotification(Context context, int icon, String title, String contentText) {
        return new SimpleNotificationBuilder(context, icon, title, contentText);
    }

    /**
     * 发送简单的通知消息，通知消息的重点在消息内容
     *
     * @param contentText  消息内容
     * @param targetActivityIntent 设定跳转目标的Intent
     */
    public static void sendSimpleNotification(String title, String contentText, Intent targetActivityIntent) {
        SimpleNotificationBuilder builder = new SimpleNotificationBuilder(QDFApplication.getAppContext(), title, contentText);
        builder.setTicker(contentText)
        .setTargetActivityIntent(targetActivityIntent)
        .configureNotificationAsDefault()
        .send(new Random().nextInt(65536));
    }


    /**
     * 将Notification的Intent转换成用广播传递的Intent
     * <p>
     * 主要用于自定义Notification时处理点击打开Activity的事件，使用此方法
     * 将会在应用启动时直接打开目标Activity，应用未启动时先启动应用再打开Activity
     * </p>
     * */
    public static Intent getBroadcastIntent(Context context, Intent targetActivityIntent) {
        Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putAll(targetActivityIntent.getExtras());
        bundle.putString(NotificationManager.KEY_TARGET_ACTIVITY_NAME, targetActivityIntent.getComponent().getClassName());
        broadcastIntent.putExtra(NotificationManager.KEY_NOTIFICATION_EXTRA, bundle);
        return broadcastIntent;
    }


    public static void sendNotification(Context context, int notifyId, Notification notification) {
        NotificationManagerCompat.from(context).notify(notifyId, notification);
    }

    public static void cancelNotification(Context context, int notifyId) {
        NotificationManagerCompat.from(context).cancel(notifyId);
    }

    /**
     * 检查用户是否屏蔽了通知显示。
     * */
    public static boolean checkNotificationEnabled() {
        return NotificationManagerCompat.from(QDFApplication.getAppContext()).areNotificationsEnabled();
    }

}
