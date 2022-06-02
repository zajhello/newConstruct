package com.ski.box.httpclient.utils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;

public class JsonUtils {
    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return getGson().fromJson(reader, typeOfT);
    }


}
