package com.quickdevandroid.sample.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.quickdevandroid.framework.fragment.BaseFragment;
import com.quickdevandroid.sample.R;
import com.quickdevandroid.sample.SampleApplication;
import com.quickdevandroid.sample.mvvm.CaptchaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Application类提供API示例
 * Created by linxiao on 2017/2/17.
 */
public class ApplicationApiFragment extends BaseFragment {

    @BindView(R.id.tvAppName)
    TextView tvAppName;
    @BindView(R.id.tvAppVersion)
    TextView tvAppVersion;
    @BindView(R.id.tvAppSignature)
    TextView tvAppSignature;
    @BindView(R.id.ivAppIcon)
    ImageView ivAppIcon;
    @BindView(R.id.tvIsAppRunning)
    TextView tvIsAppRunning;
    @BindView(R.id.tvIsAppForeground)
    TextView tvIsAppForeground;

    @Override
    protected void onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(R.layout.fragment_application_api, container);
        ButterKnife.bind(this, getContentView());

        ivAppIcon.setImageDrawable(SampleApplication.getApplicationIcon());
        tvIsAppRunning.setText(getString(R.string.is_app_running) + ": " + SampleApplication.isAppForeground());
        tvIsAppForeground.setText(getString(R.string.is_app_foreground) + ": " + SampleApplication.isAppForeground());
    }

    @OnClick(R.id.btnGetAppName)
    public void onGetAppNameClick(View v) {
        tvAppName.setText(SampleApplication.getApplicationName());
    }

    @OnClick(R.id.btnGetAppVersion)
    public void onGetAppVersionClick(View v) {
        tvAppVersion.setText(SampleApplication.getApplicationVersionName());
    }

    @OnClick(R.id.btnGetAppSignature)
    public void onGetAppSignatureClick(View v) {
        tvAppSignature.setText(SampleApplication.getAppSignature());
    }

    @OnClick(R.id.btnExitApp)
    public void onExitAppClick(View v) {
        SampleApplication.exitApplication();
    }
    
    @OnClick(R.id.btnMVVMSample)
    public void onMVVMSampleClick(View v) {
        startActivity(new Intent(getActivity(), CaptchaActivity.class));
    }
}
