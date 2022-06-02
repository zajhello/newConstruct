package com.ski.box.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.ski.box.httpclient.TioHttpClient;
import com.ski.box.httpclient.model.BaseReq;
import com.ski.box.httpclient.model.TioMap;


import java.lang.reflect.Type;

/**
 */
public class DomainListReq extends BaseReq<String> {

    @Override
    public String path() {
        return "";
    }

    @Override
    public String url() {
        return TioHttpClient.getInstance().getDomainUrl();
    }

    @Override
    public TioMap<String, String> params() {
        return TioMap.getParamMap()
                .append("tio_site_from_android", "1")
                .append("t", String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public Type bodyType() {
        return new TypeToken<String>() {
        }.getType();
    }
}
