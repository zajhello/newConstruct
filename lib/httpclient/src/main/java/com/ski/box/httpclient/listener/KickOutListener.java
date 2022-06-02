package com.ski.box.httpclient.listener;

/**
 * author : Dipper
 * date : 2020/4/10
 * desc :
 */
public interface KickOutListener {
    /**
     * 踢出登录
     */
    void onKickOut(String msg);
}
