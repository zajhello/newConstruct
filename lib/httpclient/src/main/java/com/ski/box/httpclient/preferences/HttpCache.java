package com.ski.box.httpclient.preferences;

import android.text.TextUtils;

/**
 * <pre>
 *     author : Dipper
 *     e-mail : gmail.com
 *     time   : 2020/12/25
 *     desc   :
 * </pre>
 */
public class HttpCache {

    // ====================================================================================
    // BASE_URL
    // ====================================================================================

    public static final String TIO_BASE_URL = "https://www.tiocloud.com";
//public static final String TIO_BASE_URL = "https://119.8.239.24:9326";
    private static String BASE_URL = null;

    public synchronized static String getBaseUrl() {
        if (BASE_URL == null) {
            BASE_URL = HttpPreferences.getBaseUrl();
        }
        if (BASE_URL == null) {
            BASE_URL = TIO_BASE_URL;
        }
        return BASE_URL;
    }

    public static void resetBaseUrl(String baseUrl) {
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        BASE_URL = baseUrl;
    }

    // ====================================================================================
    // RES_SERVER
    // ====================================================================================

    public static final String TIO_RES_URL = "https://res.tiocloud.com";
    private static String RES_URL = null;
    private static String AU_RES_URL = null;

    public synchronized static String getResUrl() {
        if (RES_URL == null) {
            RES_URL = HttpPreferences.getResUrl();
        }
        if (RES_URL == null) {
            RES_URL = TIO_RES_URL;
        }
        return RES_URL;
    }

    public synchronized static String getAudioResUrl() {
        if (AU_RES_URL == null) {
            AU_RES_URL = HttpPreferences.getAudioResUrl();
        }

        if (AU_RES_URL == null) {
            AU_RES_URL = HttpPreferences.getResUrl();
        }

        if (AU_RES_URL == null) {
            AU_RES_URL = TIO_RES_URL;
        }
        return AU_RES_URL;
    }

    public synchronized static String getResUrl(String relativeUrl) {
        if (TextUtils.isEmpty(relativeUrl)) {
            return "";
        } else if (relativeUrl.startsWith("http")) {
            return relativeUrl;
        } else {
            return getResUrl() + relativeUrl;
        }
    }

    public synchronized static String getAudioResUrl(String relativeUrl) {
        if (TextUtils.isEmpty(relativeUrl)) {
            return "";
        } else if (relativeUrl.startsWith("http")) {
            return relativeUrl;
        } else {
            return getAudioResUrl() + relativeUrl;
        }
    }
}
