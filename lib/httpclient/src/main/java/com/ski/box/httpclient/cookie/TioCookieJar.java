package com.ski.box.httpclient.cookie;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.lzy.okgo.cookie.store.DBCookieStore;
import com.ski.box.httpclient.TioHttpClient;
import com.ski.box.httpclient.preferences.CookieUtils;

import java.util.List;
import java.util.Locale;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * date : 2020/6/3
 * desc :
 */
public class TioCookieJar extends MyCookieJarImpl {

    public TioCookieJar(Context context) {
        super(new DBCookieStore(context));
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        // 保存cookies
        super.saveFromResponse(url, cookies);
        Log.d(TioHttpClient.LOG_TAG, "| ------------------------------------------------------------------------------------");
        Log.d(TioHttpClient.LOG_TAG, String.format(Locale.getDefault(), "| url: %s", url));
        Log.d(TioHttpClient.LOG_TAG, String.format(Locale.getDefault(), "| saveFromResponse: cookies = %s", cookies));
        Log.d(TioHttpClient.LOG_TAG, "| ------------------------------------------------------------------------------------");

        // 获取新的tioCookies
        List<Cookie> tioCookies = CookieUtils.getCookies();
        if (tioCookies != null && !tioCookies.isEmpty()) {
            onSaveTioCookiesFromResponse(tioCookies);
        }
    }

    /**
     * 保存 Response 中的 TioCookies
     *
     * @param tioCookies TioCookies（不为空，个数不为0）
     */
    public void onSaveTioCookiesFromResponse(@NonNull List<Cookie> tioCookies) {

    }

}
