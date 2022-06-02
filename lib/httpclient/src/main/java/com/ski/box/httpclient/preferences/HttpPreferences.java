package com.ski.box.httpclient.preferences;


import com.ski.box.httpclient.model.BaseResp;

/**
 * <pre>
 *     time   : 2020/12/25
 *     desc   : 配置文件
 * </pre>
 */
public class HttpPreferences extends PreferencesUtils {

    private static final String KEY_BASE_URL = "base_url";
    private static final String KEY_RES_URL = "res_url";
    private static final String KEY_AUDIO_RES_URL = "audio_res_url";
    private static final String KEY_SESSION_COOKIE_NAME = "session_cookie_name";
    private static final String KEY_CURR_USER_ID = "curr_user_id";
    private static final String KEY_TIOT = "tiot";
    private static final String KEY_IM_HEARTBEAT_TIMEOUT = "im_heartbeat_timeout";

    private static final String WX_MSG_EDIT_MAX_TIME = "wx_msg_edit_max_time";
    private static final String WX_MSG_TWOWAY_DEL_MAX_TIME = "wx_msg_twoway_del_max_time";
    private static final String WX_MSG_BACK_MAX_TIME = "wx_msg_back_max_time";
    private static final String WX_IM_HEARTBEAT_NORESP_TIMEOUT = "im_heartbeat_noresp_timeout";

    private static final String KEY_DOMAIN_ID = "domain_id";

    private static final String KEY_LOGIN_SESSION = "login_session";
    private static final String KEY_AUTO_DOMAIN = "key_auto_domain";
    private static final String KEY_DOMAIN_LIST = "key_domain_list";

    // ====================================================================================
    // 数据处理
    // ====================================================================================

    public static <Data> void handleResponse(BaseResp<Data> resp) {
        if (resp == null)
            return;

        boolean ok = resp.isOk();
        if (ok) {
            Data data = resp.getData();

        }
    }

    // ====================================================================================
    // baseUrl
    // ====================================================================================

    public static void saveBaseUrl(String baseUrl) {
        saveString(KEY_BASE_URL, baseUrl);
    }

    public static String getBaseUrl() {
        return getString(KEY_BASE_URL, null);
    }

    // ====================================================================================
    // resUrl
    // ====================================================================================

    public static void saveResUrl(String resServer) {
        saveString(KEY_RES_URL, resServer);
    }

    public static String getResUrl() {
        return getString(KEY_RES_URL, null);
    }

    public static void saveAudioResUrl(String resServer) {
        saveString(KEY_AUDIO_RES_URL, resServer);
    }

    public static String getAudioResUrl() {
        return getString(KEY_AUDIO_RES_URL, null);
    }
    
    // ====================================================================================
    // sessionCookieName
    // ====================================================================================

    private static void saveSessionCookieName(String sessionCookieName) {
        saveString(KEY_SESSION_COOKIE_NAME, sessionCookieName);
    }

    public static String getSessionCookieName() {
        return getString(KEY_SESSION_COOKIE_NAME, "tio_session");
    }

    // ====================================================================================
    // currUserId
    // ====================================================================================

    public static void saveCurrUid(long uid) {
        saveLong(KEY_CURR_USER_ID, uid);
    }

    public static long getCurrUid() {
        return getLong(KEY_CURR_USER_ID, -1);
    }

    // ====================================================================================
    // tiot
    // ====================================================================================

    public static void saveTiot(String tiot) {
        saveString(KEY_TIOT, tiot);
    }

    public static String getTiot() {
        return getString(KEY_TIOT, null);
    }

    // ====================================================================================
    // im_heartbeat_timeout
    // ====================================================================================

    private static void saveImHeartbeatTimeout(long im_heartbeat_timeout) {
        saveLong(KEY_IM_HEARTBEAT_TIMEOUT, im_heartbeat_timeout);
    }

    public static long getImHeartbeatTimeout() {
        return getLong(KEY_IM_HEARTBEAT_TIMEOUT, -1);
    }

    // ====================================================================================
    // wx_msg_edit_max_time
    // ====================================================================================

    private static void saveWxMsgEditMaxTime(long wx_msg_edit_max_time) {
        saveLong(WX_MSG_EDIT_MAX_TIME, wx_msg_edit_max_time);
    }

    public static long getWxMsgEditMaxTime() {
        long time = getLong(WX_MSG_EDIT_MAX_TIME, -1);
        return time <= 0L ? 24 * 60 * 60 * 1000 : time;
    }

    // ====================================================================================
    // wx_msg_twoway_del_max_time
    // ====================================================================================

    private static void saveWxMsgTwowayDelMaxTime(long wx_msg_twoway_del_max_time) {
        saveLong(WX_MSG_TWOWAY_DEL_MAX_TIME, wx_msg_twoway_del_max_time);
    }

    public static long getWxMsgTwowayDelMaxTime() {
        return getLong(WX_MSG_TWOWAY_DEL_MAX_TIME, -1);
    }

    // ====================================================================================
    // wx_msg_back_max_time
    // ====================================================================================

    private static void saveWxMsgBackMaxTime(long wx_msg_back_max_time) {
        saveLong(WX_MSG_BACK_MAX_TIME, wx_msg_back_max_time);
    }

    public static long getWxMsgBackMaxTime() {
        long time = getLong(WX_MSG_BACK_MAX_TIME, -1);
        return time <= 0L ? 24 * 60 * 60 * 1000 : time;
    }

    // ====================================================================================
    // im_heartbeat_noresp_timeout
    // ====================================================================================

    private static void saveImHeartbeatNoresp(int im_heartbeat_noresp_timeout) {
        saveInt(WX_IM_HEARTBEAT_NORESP_TIMEOUT, im_heartbeat_noresp_timeout);
    }

    public static int getImHeartbeatNoresp() {
        return getInt(WX_IM_HEARTBEAT_NORESP_TIMEOUT, 5000);
    }

    // ====================================================================================
    // domain_id
    // ====================================================================================

    public static void saveDomainId(String domainId) {
        saveString(KEY_DOMAIN_ID, domainId);
    }

    public static String getDomainId() {
        return getString(KEY_DOMAIN_ID, "1");
    }

    // ====================================================================================
    // login_session
    // ====================================================================================

    public static void saveLoginSession(String loginSession) {
        saveString(KEY_LOGIN_SESSION, loginSession);
    }

    public static String getLoginSession() {
        return getString(KEY_LOGIN_SESSION, "");
    }

    // ====================================================================================
    // 是否开启了自动选服务器
    // ====================================================================================

    public static void saveAutoDomain(int isAuto) {
        saveInt(KEY_AUTO_DOMAIN, isAuto);
    }

    public static int getAutoDomain() {
        return getInt(KEY_AUTO_DOMAIN, 1);
    }


    // ====================================================================================
    // DomainList
    // ====================================================================================

    public static void saveDomainList(String domainList) {
        saveString(KEY_DOMAIN_LIST, domainList);
    }

    public static String getDomainList() {
        return getString(KEY_DOMAIN_LIST, "");
    }
}