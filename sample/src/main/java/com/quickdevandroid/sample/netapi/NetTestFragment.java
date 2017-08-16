package com.quickdevandroid.sample.netapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quickdevandroid.framework.fragment.BaseFragment;
import com.quickdevandroid.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NetTestFragment extends BaseFragment {


    @BindView(R.id.tvResponse)
    TextView tvResponse;
    
    NetTestDataManager mDataManager;

    @Override
    protected void onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(R.layout.fragment_net_test, container);
        ButterKnife.bind(this, getContentView());
    }

    @OnClick(R.id.btnRequestNet)
    public void onRequestTestClick(View v) {
        requestApi();
    }

    public void requestApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        mDataManager.getTestData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                progressDialog.setMessage("正在请求");
                progressDialog.show();
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                progressDialog.dismiss();
            }
        })
        .subscribe(new Consumer<String>(){
    
            @Override
            public void accept(@NonNull String responseBody) throws Exception {
                String result = "Response:\n " + responseBody;
                tvResponse.setText(result);
            }
        });
    }

}
