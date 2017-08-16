package com.quickdevandroid.sample.sample;

import android.app.IntentService;
import android.content.Intent;

import com.quickdevandroid.framework.dialog.AlertDialogManager;


/**
 * 用于测试全局ActivityDialog的使用
 * Created by linxiao on 2016-12-11.
 */
public class BackgroundService extends IntentService {


    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AlertDialogManager.showAlertDialog("this is top dialog from service");
    }
}
