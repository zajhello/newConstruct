package com.ski.box.httpclient.callback;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;
import com.ski.box.httpclient.model.BaseResp;
import com.ski.box.httpclient.utils.JsonUtils;


import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class TioConvert<Data> implements Converter<BaseResp<Data>> {

    private final Type type;

    /**
     * 避免泛型擦除，使用示例：
     * Type type = new TypeToken<TioResp<ImServerResp>>() {}.getType();
     */
    public TioConvert(Type type) {
        this.type = type;
    }

    @Override
    public BaseResp<Data> convertResponse(Response response) throws Throwable {
        if (response == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;

//        String result = body.string();
//        JsonReader jsonReader = new JsonReader(new StringReader(result));
        JsonReader jsonReader = new JsonReader(body.charStream());
        BaseResp<Data> tioResp = JsonUtils.fromJson(jsonReader, type);
        response.close();
        return tioResp;
    }

}