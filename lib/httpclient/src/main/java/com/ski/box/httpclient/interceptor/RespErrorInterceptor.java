package com.ski.box.httpclient.interceptor;


import com.ski.box.httpclient.listener.RespErrorListener;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RespErrorInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private RespErrorListener respErrorListener;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            if (e instanceof IOException)
                throw e;
        }

        if (response == null || !response.isSuccessful()) {

            if (respErrorListener != null) {
                respErrorListener.onRespError();
            }

        }
        return response;
    }

    public void setRespErrorListener(RespErrorListener listener) {
        respErrorListener = listener;
    }
}
