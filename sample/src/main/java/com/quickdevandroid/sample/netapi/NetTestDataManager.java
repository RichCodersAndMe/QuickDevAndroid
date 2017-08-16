package com.quickdevandroid.sample.netapi;

import android.util.Log;

import com.quickdevandroid.framework.manager.BaseDataManager;
import com.quickdevandroid.framework.net.CookieMode;
import com.quickdevandroid.framework.net.HttpInfoCatchInterceptor;
import com.quickdevandroid.framework.net.HttpInfoCatchListener;
import com.quickdevandroid.framework.net.HttpInfoEntity;
import com.quickdevandroid.framework.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 分层架构简单示例
 * Created by linxiao on 2017/7/13.
 */
public class NetTestDataManager extends BaseDataManager {
    
    private ClientApi clientApi;
    
    public NetTestDataManager() {
        // 这些配置可以放在App工程的网络模块中，这里简要处理就不写了
        HttpInfoCatchInterceptor infoCatchInterceptor = new HttpInfoCatchInterceptor();
        infoCatchInterceptor.setCatchEnabled(true);
        infoCatchInterceptor.setHttpInfoCatchListener(new HttpInfoCatchListener() {
            @Override
            public void onInfoCaught(HttpInfoEntity entity) {
                Log.d(TAG, "onInfoCaught: " + entity.toString());;
                //do something......
            }
        });
        clientApi = RetrofitManager.createRetrofitBuilder("http://www.weather.com.cn/")
        .setCookieMode(CookieMode.ADD_BY_ANNOTATION)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addCustomInterceptor(infoCatchInterceptor)
        .build(ClientApi.class);
    }
    
    /**
     * 获取测试数据
     * */
    public Observable<String> getTestData() {
        return clientApi.getWeather("101010100")
        .flatMap(new Function<ResponseBody, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull ResponseBody responseBody) throws Exception {
                return Observable.just(responseBody.string());
            }
        });
    }
}
