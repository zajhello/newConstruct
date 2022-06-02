package com.ski.box.httpclient.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author : Dipper
 *     e-mail : gmail.com
 *     time   : 2020/12/29
 *     desc   : 解析出url参数中的键值对
 * </pre>
 */
public class HttpUrlUtils {
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            int index = strSplit.indexOf("=");

            if (index > 0 && index < strSplit.length() - 1) {
                String key = strSplit.substring(0, index);
                String value = strSplit.substring(index + 1);
                mapRequest.put(key, value);
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     * @author lzf
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
//        strURL = strURL.trim().toLowerCase();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
//                for (int i = 1; i < arrSplit.length; i++) {
//                    strAllParam = arrSplit[i];
//                }
                strAllParam = arrSplit[arrSplit.length - 1];
            }
        }
        return strAllParam;
    }

}
