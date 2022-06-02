package com.ski.box.db.dao;

import androidx.annotation.Nullable;

import com.ski.box.db.TioDBHelper;
import com.ski.box.db.converter.CurrUserTableConverter;
import com.ski.box.db.prefernces.TioDBPreferences;
import com.ski.box.db.table.CurrUserTable;
import com.ski.box.db.table.CurrUserTableDao;
import com.ski.box.httpclient.model.response.UserCurrResp;

import java.util.List;


/**
 * <pre>
 *     time   : 2020/09/03
 *     desc   :
 * </pre>
 */
public class CurrUserTableCrud {
    public static void insert(@Nullable CurrUserTable table) {
        if (table == null) return;
        TioDBHelper.getDaoSession().getCurrUserTableDao().insertOrReplace(table);
    }

    public static void insert(@Nullable List<CurrUserTable> tables) {
        if (tables == null || tables.size() == 0) return;
        TioDBHelper.getDaoSession().getCurrUserTableDao().insertOrReplaceInTx(tables);
    }

    public static void insert(@Nullable UserCurrResp userCurrResp) {
        if (userCurrResp == null) return;
        IpInfoTableCurd.insert(userCurrResp.ipInfo, userCurrResp.ipid);
        CurrUserTable table = CurrUserTableConverter.getInstance(userCurrResp);
        insert(table);
    }

    public static void insert_sync(@Nullable UserCurrResp currInfo) {
        TioDBHelper.runInTx(() -> insert(currInfo));
    }

    public static void update(@Nullable CurrUserTable item) {
        if (item == null) return;
        TioDBHelper.getDaoSession().getCurrUserTableDao().update(item);
    }

    public static void delete(long uid) {
        TioDBHelper.getDaoSession().getCurrUserTableDao().deleteByKey(uid);
    }

    @Nullable
    public static CurrUserTable query(long uid) {
        return TioDBHelper.getDaoSession().getCurrUserTableDao().queryBuilder()
                .where(CurrUserTableDao.Properties.Id.eq(uid))
                .unique();
    }

    /**
     * 更新-消息提醒开关
     *
     * @param msgremindflag 消息提醒开关：1：开启；2：不开启
     */
    public static void curr_update_msgremindflag(int msgremindflag) {
        CurrUserTable userTable = query(TioDBPreferences.getCurrUid());
        if (userTable != null) {
            userTable.setMsgremindflag(msgremindflag);
        }
        update(userTable);
    }

    /**
     * 是否打开了消息提醒
     *
     * @param defaultValue 默认值
     */
    public static boolean curr_isOpenMsgRemind(boolean defaultValue) {
        CurrUserTable userTable = query(TioDBPreferences.getCurrUid());
        if (userTable != null) {
            // 消息提醒开关：1：开启；2：不开启
            return userTable.getMsgremindflag() == 1;
        }
        return defaultValue;
    }

    /**
     * 获取昵称
     */
    @Nullable
    public static String curr_getNick() {
        CurrUserTable userTable = query(TioDBPreferences.getCurrUid());
        if (userTable != null) {
            return userTable.getNick();
        }
        return null;
    }

    /**
     * 删除当前用户信息
     */
    public static void curr_delete() {
        delete(TioDBPreferences.getCurrUid());
    }

    /**
     * 查询当前用户
     */
    @Nullable
    public static CurrUserTable curr_query() {
        return query(TioDBPreferences.getCurrUid());
    }

    /**
     * 获取绑定手机号
     */
    @Nullable
    public static String curr_getPhone() {
        CurrUserTable userTable = curr_query();
        if (userTable != null) {
            return userTable.getPhone();
        }
        return null;
    }

    /**
     * 获取登录名
     */
    @Nullable
    public static String curr_getLoginname() {
        CurrUserTable userTable = curr_query();
        if (userTable != null) {
            return userTable.getLoginname();
        }
        return null;
    }

    /**
     * 判断是否登录
     */
    public static boolean curr_isLogin() {
        CurrUserTable userTable = curr_query();
        return userTable != null;
    }

    /**
     * 获取当前头像
     *
     * @return
     */
    public static String currAvatar() {
        CurrUserTable userTable = curr_query();
        if (userTable != null) {
            return userTable.getAvatar();
        }
        return null;
    }

    /**
     * 获取当前用户设置的语言
     */
    public static int currLanguageType() {
        CurrUserTable currUserTable = CurrUserTableCrud.curr_query();
        if (currUserTable != null && currUserTable.getUserSetting() != null) {
            return currUserTable.getUserSetting().lang;
        }
        return -1;
    }

    /**
     * 获取当前用户设置的语言
     */
    public static byte currUpdLogin() {
        CurrUserTable currUserTable = CurrUserTableCrud.curr_query();
        if (currUserTable != null) {
            return currUserTable.getUpdLoginNameExt();
        }
        return -1;
    }
}
