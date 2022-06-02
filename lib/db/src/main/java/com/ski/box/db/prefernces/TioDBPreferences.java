package com.ski.box.db.prefernces;


import com.ski.box.httpclient.preferences.HttpPreferences;

/**
 * <pre>
 *     time   : 2020/09/03
 *     desc   : 数据库 配置文件
 * </pre>
 */
public class TioDBPreferences extends PreferencesUtils {

    // ====================================================================================
    // currUserId
    // ====================================================================================

    public static long getCurrUid() {
        return HttpPreferences.getCurrUid();
    }
}
