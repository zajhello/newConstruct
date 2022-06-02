package com.ski.box.db.converter;

import androidx.annotation.NonNull;

import com.ski.box.db.table.IpInfoTable;
import com.ski.box.httpclient.model.response.UserCurrResp;


/**
 * <pre>
 *     author : Dipper
 *     e-mail : gmail.com
 *     time   : 2020/09/03
 *     desc   :
 * </pre>
 */
public class IpInfoTableConverter {
    @NonNull
    public static IpInfoTable getInstance(@NonNull UserCurrResp.IpInfoBean ipInfo, int id) {
        IpInfoTable table = new IpInfoTable();

        table.setId((long) id);
        table.setArea(ipInfo.area);
        table.setCity(ipInfo.city);
        table.setCountry(ipInfo.country);
        table.setOperator(ipInfo.operator);
        table.setProvince(ipInfo.province);

        return table;
    }
}
