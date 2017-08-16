package com.quickdevandroid.sample.sample;

import android.os.Bundle;
import android.view.View;


import com.quickdevandroid.framework.activity.BaseActivity;
import com.quickdevandroid.sample.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationTargetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_target);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvTargetDesc)
    public void onTextViewClick(View v) {
        finish();
    }
}
