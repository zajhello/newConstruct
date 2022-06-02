package com.ski.box.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import com.ski.box.androidutils.utils.UIHandler;
import com.ski.box.db.prefernces.TioDBPreferences;
import com.ski.box.db.table.DaoMaster;
import com.ski.box.db.table.DaoSession;
import com.ski.box.db.upgrade.TioDBOpenHelper;
import com.ski.box.db.utils.LoggerUtils;
import com.ski.box.httpclient.TioHttpClient;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     time   : 2020/08/21
 *     desc   : 数据库
 * </pre>
 */
public class TioDBHelper {
    private static DaoSession mDaoSession;
    private static UIHandler mUIHandler;


    public static void init(@NonNull Application app) {
        init(app, "tio.db");
    }

    private static void init(@NonNull Application app, @NonNull String dbName) {
        release();

        initDatabase(app, dbName);
        mUIHandler = new UIHandler();

        TioDBPreferences.init(app);
        setDebug(BuildConfig.DEBUG);
    }

    private static void initDatabase(@NonNull Application app, @NonNull String dbName) {
        // DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(app, DB_NAME);
        TioDBOpenHelper helper = new TioDBOpenHelper(app, dbName);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    /**
     * 调试开关
     */
    public static void setDebug(boolean debug) {
        // 数据库操作日志
        // TAG = "greenDAO"
        QueryBuilder.LOG_SQL = debug;
        QueryBuilder.LOG_VALUES = debug;
        // 数据库升级日志
        // TAG = "MigrationHelper"
        MigrationHelper.DEBUG = debug;
        // 日志开关
        LoggerUtils.setDebug(debug);
    }

    /**
     * 数据库DAO
     */
    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 运行在线程池中
     * <p>
     * 线程池为：Executors.newCachedThreadPool()
     */
    public static void runInTx(@NonNull Runnable runnable) {
        mDaoSession.startAsyncSession().runInTx(runnable);
    }

    /**
     * UIHandler
     */
    public static UIHandler getUIHandler() {
        return mUIHandler;
    }


    public static void release() {

        if (mUIHandler != null) {
            mUIHandler.removeCallbacksAndMessages(null);
            mUIHandler = null;
        }
        // DAO清除
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }
}
