package com.quickdevandroid.framework.net;

/**
 * 框架下Retrofit配置模式
 * Created by linxiao on 2017-01-03.
 */
public enum CookieMode {
    /**
     * 所有请求均不添加cookie
     * */
    NO_COOKIE,

    /**
     * 根据注解添加Cookie
     * */
    ADD_BY_ANNOTATION,

    /**
     * 所有请求全部添加Cookie;
     * */
    ADD_TO_ALL;
}
