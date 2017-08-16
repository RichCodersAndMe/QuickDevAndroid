package com.quickdevandroid.sample.sample;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quickdevandroid.framework.dialog.AlertDialogManager;
import com.quickdevandroid.framework.fragment.BaseFragment;
import com.quickdevandroid.framework.permission.PermissionManager;
import com.quickdevandroid.framework.permission.PermissionProhibitedListener;
import com.quickdevandroid.framework.permission.RequestPermissionCallback;
import com.quickdevandroid.sample.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PermissionApiFragment extends BaseFragment {
    
    @Override
    protected void onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(R.layout.fragment_permission_api, container);
        ButterKnife.bind(this, getContentView());
    }

    @OnClick(R.id.btnRequestSample)
    public void onRequestPermissionClick(View v) {
        PermissionManager.performWithPermission(Manifest.permission.READ_CONTACTS)
        .perform(getActivity(),new RequestPermissionCallback() {
            @Override
            public void onGranted() {
                AlertDialogManager.showAlertDialog("权限已授予");
            }

            @Override
            public void onDenied() {
                AlertDialogManager.showAlertDialog("未授予权限");
            }
        });
    }

    @OnClick(R.id.btnRequestWithRationale)
    public void onRationaleClick(View v) {
        PermissionManager.performWithPermission(Manifest.permission.SEND_SMS)
        .showRationaleBeforeRequest("请授予发送短信权限权限以启用功能")
        .perform(getActivity(),new RequestPermissionCallback() {
            @Override
            public void onGranted() {
                AlertDialogManager.showAlertDialog("权限已授予");
            }

            @Override
            public void onDenied() {
                AlertDialogManager.showAlertDialog("未授予权限");
            }
        });
    }

    @OnClick(R.id.btnDoOnProhibited)
    public void OnProhibitedClick(View v) {
        PermissionManager.performWithPermission(Manifest.permission.READ_PHONE_STATE)
        .showRationaleBeforeRequest("请两次以上请求申请权限然后勾选\"不再提醒\"查看功能")
        .doOnProhibited(new PermissionProhibitedListener() {
            @Override
            public void onProhibited(String permission) {
                PermissionManager.showPermissionProhibitedDialog(getActivity(), permission);
            }
        })
        .perform(getActivity(),new RequestPermissionCallback() {
            @Override
            public void onGranted() {
                AlertDialogManager.showAlertDialog("权限已授予");
            }

            @Override
            public void onDenied() {
                AlertDialogManager.showAlertDialog("未授予权限");
            }
        });
    }

    @OnClick(R.id.btnRequestAlertWindow)
    public void onReqSysAlertClick(View v) {
        PermissionManager.requestSystemAlertWindowPermission(getActivity(), new RequestPermissionCallback() {
            @Override
            public void onGranted() {
                AlertDialogManager.showAlertDialog("权限已授予");
            }

            @Override
            public void onDenied() {
                AlertDialogManager.showAlertDialog("未授予权限");
            }
        });
    }

    @OnClick(R.id.btnRequestWriteSettings)
    public void onReqWriteSettingsClick(View v) {
        PermissionManager.requestWriteSystemSettingsPermission(getActivity(), new RequestPermissionCallback() {
            @Override
            public void onGranted() {
                AlertDialogManager.showAlertDialog("权限已授予");
            }

            @Override
            public void onDenied() {
                AlertDialogManager.showAlertDialog("未授予权限");
            }
        });
    }
}
