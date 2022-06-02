package com.ski.box.httpclient.domain_manager;

import com.google.gson.stream.JsonReader;
import com.ski.box.httpclient.preferences.HttpPreferences;
import com.ski.box.httpclient.utils.JsonUtils;


import java.io.StringReader;

public class DomainManager {

    private volatile static DomainManager INSTANCE;

    private DomainBean data;
    private int curIndex = 0;

    private DomainManager() {

    }

    public static DomainManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DomainManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DomainManager();
                }
            }
        }
        return INSTANCE;
    }

    public void setData(DomainBean data) {
        this.data = data;
    }

    public DomainBean getData() {
        if (data == null || data.list == null || data.list.isEmpty()) {
            data = JsonUtils.fromJson(new JsonReader(new StringReader(HttpPreferences.getDomainList())), DomainBean.class);
        }
        return data;
    }

    public int getCurIndex() {
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
    }

    public DomainBean.Bean getCurDomain() {
        return getDomain(curIndex);
    }

    public DomainBean.Bean getDomain(int index) {
        if (data == null || data.list == null || data.list.isEmpty()) {
            data = JsonUtils.fromJson(new JsonReader(new StringReader(HttpPreferences.getDomainList())), DomainBean.class);
        }

        if (data == null || data.list == null || data.list.isEmpty()) {
            return null;
        }

        if (index < 0 || index >= data.list.size()) {
            return data.list.get(0);
        }

        return data.list.get(index);
    }

    public interface IGetFastDomainCallback {
        void fastDomain(int index);
    }
}
