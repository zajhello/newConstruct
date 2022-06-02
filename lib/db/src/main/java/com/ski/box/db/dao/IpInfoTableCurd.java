package com.ski.box.db.dao;

import androidx.annotation.Nullable;

import com.ski.box.db.TioDBHelper;
import com.ski.box.db.converter.IpInfoTableConverter;
import com.ski.box.db.table.IpInfoTable;
import com.ski.box.httpclient.model.response.UserCurrResp;


/**
 * <pre>
 *     time   : 2020/09/03
 *     desc   :
 * </pre>
 */
public class IpInfoTableCurd {
    public static void insert(@Nullable IpInfoTable table) {
        if (table == null) return;
        TioDBHelper.getDaoSession().getIpInfoTableDao().insertOrReplace(table);
    }

    public static void insert(@Nullable UserCurrResp.IpInfoBean ipInfo, int id) {
        if (ipInfo == null) return;
        IpInfoTable table = IpInfoTableConverter.getInstance(ipInfo, id);
        insert(table);
    }
}
