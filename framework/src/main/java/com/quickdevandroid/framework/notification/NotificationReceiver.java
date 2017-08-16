package com.quickdevandroid.framework.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.quickdevandroid.framework.QDFApplication;


/**
 * 用于接收应用通知消息点击的事件并根据当前应用的状态做出处理
 * Created by LinXiao on 2016-12-05.
 */
public class NotificationReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle notificationExtra = intent.getBundleExtra(NotificationManager.KEY_NOTIFICATION_EXTRA);
        if (notificationExtra == null) {
            return;
        }
        if (QDFApplication.isAppForeground()) {
            Intent targetIntent = new Intent();
            String targetKey = notificationExtra.getString(NotificationManager.KEY_TARGET_ACTIVITY_NAME);
            if (TextUtils.isEmpty(targetKey)) {
                targetKey = NotificationResumeActivity.class.getName();
            }
            try {
                Class<?> targetActivityClass = Class.forName(targetKey);
                targetIntent.setClass(context, targetActivityClass);
                targetIntent.putExtras(notificationExtra);
            } catch (ClassNotFoundException e) {
                targetIntent.setClass(context, NotificationResumeActivity.class);
            }
            targetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(targetIntent);
        }
        else {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(
                    QDFApplication.getAppContext().getPackageName());
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            launchIntent.putExtra(NotificationManager.KEY_NOTIFICATION_EXTRA, notificationExtra);
            context.startActivity(launchIntent);
        }
    }

}
