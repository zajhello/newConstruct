package com.ski.box.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.ski.box.httpclient.model.BaseReq;
import com.ski.box.httpclient.model.TioMap;


import java.io.File;
import java.lang.reflect.Type;

/**
 * date : 2020/3/9
 * desc :
 */
public class UploadFileNewReq extends BaseReq<String> {

    private String url;
    /**
     * 单文件上传
     */
    private String filePath;

    /**0-忽略，1-图片，2-文件，3-视频，4-其他*/
    private String filetype;

    public UploadFileNewReq(String filePath, String filetype, String url) {
        this.filePath = filePath;
        this.url = url;
        this.filetype = filetype;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public String path() {
        return null;
    }

    @Override
    public TioMap<String, String> params() {
        return null;
    }

    @Override
    public TioMap<String, File> files() {
        return null;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<String>() {
        }.getType();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFiletype() {
        return filetype;
    }
}
