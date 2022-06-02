package com.ski.box.httpclient.interceptor;

import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.utils.IOUtils;
import com.ski.box.httpclient.R;
import com.ski.box.httpclient.listener.KickOutListener;
import com.ski.box.httpclient.model.TioRespCode;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;

/**
 * date : 2020-01-10
 * 1、全局异常处理
 */
public class RespInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    // 踢出登录监听
    private KickOutListener kickOutListener;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return handleResp(response);
    }

    private Response handleResp(Response resp) {
        Response.Builder builder = resp.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();
        if (responseBody == null) return resp;
        try {
            if (HttpHeaders.hasBody(clone)) {
                if (isPlaintext(responseBody.contentType())) {
                    byte[] bytes = IOUtils.toByteArray(responseBody.byteStream());
                    MediaType contentType = responseBody.contentType();
                    String body = StringEscapeUtils.unescapeHtml4(new String(bytes, getCharset(contentType)));
                    handleRespBody(body);
                    responseBody = ResponseBody.create(responseBody.contentType(), bytes);
                    return resp.newBuilder().body(responseBody).build();
                } else {
                    //body: maybe [binary body], omitted!
                }
            }
        } catch (Exception ignored) {
        }

        return resp;
    }

    private void handleRespBody(String respBody) {
        try {
            JSONObject body = new JSONObject(respBody);
            int code = body.optInt("code");
            String msg = body.optString("msg");
            switch (code) {
                case TioRespCode.ForbidOper.NOTLOGIN:
                    msg = StringUtils.getString(R.string.lib_httpclient_login_expiration);
                    if (kickOutListener != null) {
                        kickOutListener.onKickOut(msg);
                    }
                    break;
                case TioRespCode.ForbidOper.KICKTED:
                    msg = StringUtils.getString(R.string.lib_httpclient_curr_account_logged_in_other_devices);
                    if (kickOutListener != null) {
                        kickOutListener.onKickOut(msg);
                    }
                    break;
            }
        } catch (JSONException ignored) {

        }
    }

    private static Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) charset = UTF8;
        return charset;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
                return true;
        }
        return false;
    }

    public void setKickOutListener(KickOutListener listener) {
        kickOutListener = listener;
    }
}
