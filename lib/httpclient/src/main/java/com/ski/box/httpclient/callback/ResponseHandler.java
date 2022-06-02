package com.ski.box.httpclient.callback;

import android.util.Log;

import com.blankj.utilcode.util.ThrowableUtils;
import com.ski.box.httpclient.TioHttpClient;
import com.ski.box.httpclient.model.BaseResp;
import com.ski.box.httpclient.preferences.HttpPreferences;


/**
 * <pre>
 *     time   : 2021/01/18
 *     desc   :
 * </pre>
 */
class ResponseHandler {
    /**
     * 子线程中执行
     *
     * @param resp   响应
     * @param <Data> Data数据
     */
    public static <Data> void handleResponse(BaseResp<Data> resp) {
        try {
            handleResponseInternal(resp);
            HttpPreferences.handleResponse(resp);
        } catch (Exception e) {
            Log.e(TioHttpClient.LOG_TAG, ThrowableUtils.getFullStackTrace(e));
        }
    }

    private static <Data> void handleResponseInternal(BaseResp<Data> resp) {

    }
}
