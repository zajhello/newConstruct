package com.ski.box.httpclient.interceptor;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 参数解密拦截器
 */
public class RespDInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        return chain.proceed(chain.request());
    }
}
