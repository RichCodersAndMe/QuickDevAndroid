package com.quickdevandroid.sample.netapi;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static com.quickdevandroid.framework.net.FrameworkNetConstants.ADD_COOKIE_HEADER_STRING;

/**
 *
 * Created by LinXiao on 2016-12-04.
 */
public interface ClientApi {

    @Headers(ADD_COOKIE_HEADER_STRING)
    @GET("adat/sk/{cityId}.html")
    Observable<ResponseBody> getWeather(@Path("cityId") String cityId);
}
