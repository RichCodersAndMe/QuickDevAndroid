package com.quickdevandroid.framework.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.quickdevandroid.framework.QDFApplication;

/**
 * 应用内消息通知封装
 * Created by LinXiao on 2016-11-25.
 */
public class AlertDialogManager {

    private AlertDialogManager() {}

    public static AlertDialogBuilder createAlertDialogBuilder() {
        return createAlertDialogBuilder(QDFApplication.getAppContext());
    }

    public static AlertDialogBuilder createAlertDialogBuilder(Context context) {
        return new AlertDialogBuilder(context);
    }

    /**
     * show an alert dialog with simple message and positive button
     * */
    public static void showAlertDialog(String message) {
        createAlertDialogBuilder()
        .setMessage(message)
        .show();
    }

    /**
     * show alert dialog with simple message,
     * click event of positive button is configurable
     * */
    public static void showAlertDialog(String message, DialogInterface.OnClickListener positiveListener) {
        createAlertDialogBuilder()
        .setMessage(message)
        .setPositiveButton(positiveListener)
        .show();
    }

    /**
     * show alert dialog with simple message,
     * click event of positive button and negative button are configurable
     * */
    public static void showAlertDialog(String message, DialogInterface.OnClickListener positiveListener,
                                       DialogInterface.OnClickListener negativeListener) {
        createAlertDialogBuilder()
        .setMessage(message)
        .setPositiveButton(positiveListener)
        .setNegativeButton(negativeListener)
        .show();
    }

}
