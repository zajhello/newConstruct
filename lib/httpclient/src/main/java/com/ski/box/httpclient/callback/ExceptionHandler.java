package com.ski.box.httpclient.callback;

import android.net.ParseException;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.JsonParseException;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.model.Response;
import com.ski.box.httpclient.R;
import com.ski.box.httpclient.model.BaseResp;


import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;

/**
 * <pre>
 *     time   : 2020/06/23
 *     desc   :
 * </pre>
 */
class ExceptionHandler {
    /**
     * 获取异常信息
     */
    public static <Data> String handleException(Response<BaseResp<Data>> response) {
        // 没有错误
        Throwable e = response.getException();
        if (e == null) return StringUtils.getString(R.string.lib_httpclient_no_error);

        String msg = StringUtils.getString(R.string.lib_httpclient_unknown_error);
        if (e instanceof HttpException) {
            int code = response.code();
            if (code != -1) {
                msg = code + StringUtils.getString(R.string.lib_httpclient_error);
            } else {
                msg = StringUtils.getString(R.string.lib_httpclient_network_error);
            }
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            msg = StringUtils.getString(R.string.lib_httpclient_no_network);
        } else if (e instanceof ConnectTimeoutException
                || e instanceof java.net.SocketTimeoutException) {
            msg = StringUtils.getString(R.string.lib_httpclient_connection_timed_out);
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            msg = StringUtils.getString(R.string.lib_httpclient_certificate_validation_exception);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            msg = StringUtils.getString(R.string.lib_httpclient_connection_analysis_exception);
        }
        return msg;
    }
}
