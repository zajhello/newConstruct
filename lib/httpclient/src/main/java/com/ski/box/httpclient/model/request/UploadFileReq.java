package com.ski.box.httpclient.model.request;

import com.google.gson.reflect.TypeToken;
import com.ski.box.httpclient.model.BaseReq;
import com.ski.box.httpclient.model.BaseResp;
import com.ski.box.httpclient.model.TioMap;

import java.io.File;
import java.lang.reflect.Type;

/**
 * author : Dipper
 * date : 2020/3/9
 * desc :
 */
public class UploadFileReq extends BaseReq<String> {
    private String chatlinkid;
    /**
     * 单文件上传
     */
    private String filePath;

    public UploadFileReq(String chatlinkid, String filePath) {
        this.chatlinkid = chatlinkid;
        this.filePath = filePath;
    }

    @Override
    public String path() {
        return "/mytio/chat/file.tio_x";
    }

    @Override
    public TioMap<String, String> params() {
        return super.params()
                .append("chatlinkid", chatlinkid)
                ;
    }

    @Override
    public TioMap<String, File> files() {
        return super.files()
                .append("uploadFile", new File(filePath))
                ;
    }

    @Override
    public Type bodyType() {
        return new TypeToken<BaseResp<String>>() {
        }.getType();
    }
}
