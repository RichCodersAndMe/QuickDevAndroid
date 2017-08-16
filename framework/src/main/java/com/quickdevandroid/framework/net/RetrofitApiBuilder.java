package com.quickdevandroid.framework.net;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.quickdevandroid.framework.net.FrameworkNetConstants.ADD_COOKIE;

/**
 * Retrofit构建类
 * Created by linxiao on 2016-12-31.
 */
public class RetrofitApiBuilder {

    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder okHttpClientBuilder;
    private Map<String, String> universalHeaders;

    private CookieMode cookieMode;
    private boolean hasNoConvertFactory = true;

    private List<Interceptor> mInterceptors;

    public RetrofitApiBuilder() {
        mRetrofitBuilder = new Retrofit.Builder();
        okHttpClientBuilder = new OkHttpClient.Builder();
        universalHeaders = new ArrayMap<>();
        cookieMode = CookieMode.ADD_TO_ALL;
        mInterceptors = new ArrayList<>();
    }

    /**
     * 设置服务端地址
     * */
    public RetrofitApiBuilder setServerUrl(String serverUrl) {
        mRetrofitBuilder.baseUrl(serverUrl);
        return this;
    }

    /**
     * 配置请求通用Headers
     * @param name Header 名字
     * @param value Header 值
     * */
    public RetrofitApiBuilder addHeader(String name, String value) {
        universalHeaders.put(name, value);
        return this;
    }

    /**
     * 设置Cookie管理模式
     * */
    public RetrofitApiBuilder setCookieMode(CookieMode mode) {
        cookieMode = mode;
        return this;
    }

    /**
     * 添加Https支持
     * */
    public RetrofitApiBuilder setSSLSocketFactory(@NonNull SSLSocketFactory factory, X509TrustManager trustManager) {
        okHttpClientBuilder.sslSocketFactory(factory, trustManager);
        return this;
    }

    /**
     * 添加 CallAdapterFactory
     * */
    public RetrofitApiBuilder addCallAdapterFactory(@NonNull CallAdapter.Factory factory) {
        mRetrofitBuilder.addCallAdapterFactory(factory);
        return this;
    }

    /**
     * 添加 ConvertFactory;
     * */
    public RetrofitApiBuilder addConvertFactory(@NonNull Converter.Factory factory) {
        mRetrofitBuilder.addConverterFactory(factory);
        hasNoConvertFactory = false;
        return this;
    }

    /**
     * 给Retrofit的OkHttpClient添加其他拦截器
     * */
    public RetrofitApiBuilder addCustomInterceptor(@NonNull Interceptor interceptor) {
        mInterceptors.add(interceptor);
        return this;
    }


    public <T> T build(final Class<T> clazzClientApi) {
        //基础拦截器，第一个添加
        okHttpClientBuilder.addInterceptor(new RequestConfigInterceptor());
        okHttpClientBuilder.addInterceptor(new ResponseConfigInterceptor());
        //自定义拦截器放在后面添加
        for (Interceptor interceptor : mInterceptors) {
            okHttpClientBuilder.addInterceptor(interceptor);
        }
        mRetrofitBuilder.client(okHttpClientBuilder.build());
        if (hasNoConvertFactory) {
            mRetrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        }
        return mRetrofitBuilder.build().create(clazzClientApi);
    }

    private class RequestConfigInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            for (String key : universalHeaders.keySet()) {
                builder.addHeader(key, universalHeaders.get(key));
            }
            /* Cookie检查，在Retrofit的请求接口加上"@Header("Add-Cookie")"注解
            去动态选择是否为当前接口添加cookie */
            switch (cookieMode) {
            case NO_COOKIE:
                break;
            case ADD_BY_ANNOTATION:
                if (chain.request().headers().get(ADD_COOKIE) != null) {
                    builder.removeHeader(ADD_COOKIE);
                    if (!TextUtils.isEmpty(SessionManager.getSession())) {
                        builder.header("Set-Cookie", SessionManager.getSession());
                    }
                }
                break;
            case ADD_TO_ALL:
                if (!TextUtils.isEmpty(SessionManager.getSession())) {
                    builder.header("Set-Cookie", SessionManager.getSession());
                }
                break;
            default:
                break;
            }
            Request request = builder.build();
            return chain.proceed(request);
        }
    }

    private class ResponseConfigInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            //存入Session
            if (response.header("Set-Cookie") != null) {
                SessionManager.setSession(response.header("Set-Cookie"));
            }
            //刷新API调用时间
            SessionManager.setLastApiCallTime(System.currentTimeMillis());

            return response;
        }
    }

}
