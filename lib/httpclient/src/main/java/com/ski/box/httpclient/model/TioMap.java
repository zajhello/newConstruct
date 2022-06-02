package com.ski.box.httpclient.model;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * author : Dipper
 * date : 2020-01-06
 * desc :
 */
public class TioMap<K, V> extends LinkedHashMap<K, V> {
    private TioMap() {
    }

    /**
     * @return 参数 map
     */
    public static TioMap<String, String> getParamMap() {
        return new TioMap<String, String>()
                // 公共参数，防互 T
                .append("p_is_android", "1");
    }

    /**
     * @return 文件 map
     */
    public static TioMap<String, File> getFileMap() {
        return new TioMap<String, File>();
    }

    @Override
    public V put(K key, V value) {
        if (key != null && value != null) {
            return super.put(key, value);
        }
        return null;
    }

    public TioMap<K, V> append(K key, V value) {
        put(key, value);
        return this;
    }
}
