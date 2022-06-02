package com.ski.box.newconstruct;

import android.app.Activity;
import android.app.Application;


import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ski.box.androidutils.utils.AndroidUtils;
import com.ski.box.common.TioCommon;
import com.ski.box.common.tools.CrashLogUtils;
import com.ski.box.httpclient.TioHttpClient;


public class AppLauncher {

    private static final AppLauncher LAUNCHER = new AppLauncher();

    /**
     * app处理了前后台的逻辑，但在选文件或拍照时特殊处理，不走前后台逻辑
     */
    private boolean needBgBiz = true;

    private AppLauncher() {
    }

    public static AppLauncher getInstance() {
        return LAUNCHER;
    }

    public void setNeedBgBiz(boolean needBgBiz) {
        this.needBgBiz = needBgBiz;
    }

    public void init(Application app) {
        // 多进程APP，当非主进程时，无需初始化
        if (!ProcessUtils.isMainProcess()) {
            return;
        }

        // 会话模块
        initSessionModule();
        // 二维码模块

        // 踢出登录
        TioHttpClient.getInstance().getRespInterceptor().setKickOutListener(this::handleKickOut);
//        TioHttpClient.getInstance().getRespErrorInterceptor().setRespErrorListener(this::respError);

        // AndroidUtils
        initAndroidUtilsModule(app);

//        // 崩溃日志
        CrashLogUtils.getInstance().listener();
//        CrashLogUtils.getInstance().upload();
        // debug
        initDebug();

        initAppStatusChangeListener();

        //初始化语言
        initLanguage();
    }


    private void initAndroidUtilsModule(Application app) {
        AndroidUtils.init(app, this::setNeedBgBiz);
    }

    private void initDebug() {
        boolean openDebug = BuildConfig.DEBUG;
        TioHttpClient.getInstance().setDebug(openDebug);
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initSessionModule() {
        TioCommon.init(new TioCommon.Bridge() {

        });
    }

    private void handleKickOut(String msg) {

    }

    private void initAppStatusChangeListener() {
        AppUtils.registerAppStatusChangedListener(new Utils.OnAppStatusChangedListener() {
            @Override
            public void onForeground(Activity activity) {
                if (!needBgBiz) {
                    return;
                }
            }

            @Override
            public void onBackground(Activity activity) {
            }
        });
    }

    /**
     * 初始化语言
     */
    private void initLanguage() {
//        int lang = CurrUserTableCrud.currLanguageType();

    }
}
