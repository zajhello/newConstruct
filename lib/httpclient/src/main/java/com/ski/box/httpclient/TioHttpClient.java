package com.ski.box.httpclient;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.db.CacheManager;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;
import com.lzy.okgo.utils.HttpUtils;
import com.ski.box.httpclient.callback.TioCallback;
import com.ski.box.httpclient.callback.TioConvert;
import com.ski.box.httpclient.callback.TioFileCallback;
import com.ski.box.httpclient.cookie.TioCookieJar;
import com.ski.box.httpclient.domain_manager.DomainBean;
import com.ski.box.httpclient.domain_manager.DomainManager;
import com.ski.box.httpclient.interceptor.HeaderInterceptor;
import com.ski.box.httpclient.interceptor.LoggingInterceptor;
import com.ski.box.httpclient.interceptor.ReqEInterceptor;
import com.ski.box.httpclient.interceptor.RespDInterceptor;
import com.ski.box.httpclient.interceptor.RespErrorInterceptor;
import com.ski.box.httpclient.interceptor.RespInterceptor;
import com.ski.box.httpclient.listener.OnCookieListener;
import com.ski.box.httpclient.model.BaseReq;
import com.ski.box.httpclient.model.BaseResp;
import com.ski.box.httpclient.model.TioMap;
import com.ski.box.httpclient.model.request.DomainListReq;
import com.ski.box.httpclient.model.request.UploadFileNewReq;
import com.ski.box.httpclient.preferences.HttpCache;


import java.io.File;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * date : 2020/4/28
 * desc :
 */
public class TioHttpClient {

    public static final String LOG_TAG = "TioHttpClient";

    public static final long DEFAULT_MILLISECONDS = 60000;      //OkGo?????????????????????
    public static long RETRY_COUNT = 3; //OkGo????????????
    // OkGo??????
    private final OkGo okGo;
    // ???????????????
    private final RespInterceptor respInterceptor;
    // ??????????????????
    private HeaderInterceptor headerInterceptor;
    // cookie?????????
    private OnCookieListener onCookieListener;
    // ???????????????
    private RespErrorInterceptor respErrorInterceptor;
    // Application
    private static Application application;
    // ???????????????
    private final LoggingInterceptor loggingInterceptor;

    private static String deviceId = "";



    private String DomainUrl = "";

    public static TioHttpClient getInstance() {
        return HttpClientHolder.holder;
    }

    public static String getDeviceId() {
        return deviceId;
    }

    public static void setDeviceId(String deviceId) {
        TioHttpClient.deviceId = deviceId;
    }

    public String getDomainUrl() {
        return DomainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        DomainUrl = domainUrl;
    }

    private static class HttpClientHolder {
        private static final TioHttpClient holder = new TioHttpClient();
    }

    private TioHttpClient() {
        okGo = OkGo.getInstance();

        // ??????OkGo ?????????
        OkHttpClient.Builder builder = okGo.getOkHttpClient().newBuilder();
        builder.interceptors().clear();
        builder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.dns(new HttpDns());
        OkHttpClient okHttpClient = builder.build();
        okGo.setOkHttpClient(okHttpClient);

        // ??????OkGo userAgent
        HttpHeaders.setUserAgent("tiohttp/watayouxiang");

        // ???????????????
        loggingInterceptor = new LoggingInterceptor(LOG_TAG);
        loggingInterceptor.setPrintLevel(LoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        addInterceptor(loggingInterceptor);


        addInterceptor(new ReqEInterceptor());
        addInterceptor(new RespDInterceptor());

        // ???????????????
        addInterceptor(respInterceptor = new RespInterceptor());

//        addInterceptor(respErrorInterceptor = new RespErrorInterceptor());

        // debug
        setDebug(BuildConfig.DEBUG);

        // ?????????
        init(Utils.getApp());
    }

    // ====================================================================================
    // config
    // ====================================================================================

    /**
     * ???????????????Application????????????
     */
    private TioHttpClient init(Application app) {
        application = app;
        // ?????????OkGo
        okGo.init(app);
        // cookie?????????
        setCookieJar(new TioCookieJar(app) {
            @Override
            public void onSaveTioCookiesFromResponse(@NonNull List<Cookie> tioCookies) {
                super.onSaveTioCookiesFromResponse(tioCookies);
                if (onCookieListener != null) {
                    onCookieListener.onSaveTioCookiesFromResponse(tioCookies);
                }
            }
        });
        // ??????????????????
        addInterceptor(headerInterceptor = new HeaderInterceptor(app));
        return this;
    }

    /**
     * ??????cookie
     */
    public TioHttpClient setCookieJar(CookieJar cookieJar) {
        HttpUtils.checkNotNull(cookieJar, "cookieJar == null");
        okGo.setOkHttpClient(okGo.getOkHttpClient().newBuilder()
                .cookieJar(cookieJar)
                .build());
        return this;
    }

    /**
     * ???????????????
     */
    public TioHttpClient addInterceptor(Interceptor interceptor) {
        HttpUtils.checkNotNull(interceptor, "interceptor == null");
        okGo.setOkHttpClient(okGo.getOkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build());
        return this;
    }

    public TioHttpClient setTimeout(int timeout) {
        okGo.setOkHttpClient(okGo.getOkHttpClient().newBuilder()
                .connectTimeout(timeout <= 0 ? DEFAULT_MILLISECONDS : timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout <= 0 ? DEFAULT_MILLISECONDS : timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout <= 0 ? DEFAULT_MILLISECONDS : timeout, TimeUnit.MILLISECONDS)
                .build());
        return this;
    }

    public TioHttpClient setTimeout(long connectTimeout, long readTimeout, long writeTimeout) {
        okGo.setOkHttpClient(okGo.getOkHttpClient().newBuilder()
                .connectTimeout(connectTimeout <= 0 ? DEFAULT_MILLISECONDS : connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout <= 0 ? DEFAULT_MILLISECONDS : writeTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout <= 0 ? DEFAULT_MILLISECONDS : readTimeout, TimeUnit.MILLISECONDS)
                .build());
        return this;
    }

    /**
     * ???????????????
     */
    public TioHttpClient addNetworkInterceptor(Interceptor interceptor) {
        HttpUtils.checkNotNull(interceptor, "interceptor == null");
        okGo.setOkHttpClient(okGo.getOkHttpClient().newBuilder()
                .addNetworkInterceptor(interceptor)
                .build());
        return this;
    }

    /**
     * ?????????????????????
     */
    public RespInterceptor getRespInterceptor() {
        return respInterceptor;
    }

    public RespErrorInterceptor getRespErrorInterceptor() {
        return respErrorInterceptor;
    }

    /**
     * ?????????????????????
     */
    public HeaderInterceptor getHeaderInterceptor() {
        return headerInterceptor;
    }

    /**
     * ????????????
     */
    public static void clearCache() {
        CacheManager.getInstance().clear();
    }

    /**
     * ??????cookie?????????
     */
    public TioHttpClient setOnCookieListener(OnCookieListener listener) {
        onCookieListener = listener;
        return this;
    }

    /**
     * ??????debug????????????
     */
    public void setDebug(boolean isDebug) {
        if (loggingInterceptor != null) {
            LoggingInterceptor.Level level = isDebug ? LoggingInterceptor.Level.BODY : LoggingInterceptor.Level.NONE;
            loggingInterceptor.setPrintLevel(level);
        }
    }

    public static String getBaseUrl() {
        return HttpCache.getBaseUrl();
    }

    public static Application getApplication() {
        return application;
    }

    // ====================================================================================
    // ??????
    // ====================================================================================

    /**
     * ?????? get
     */
    public static <Req extends BaseReq<Data>, Data> Response<BaseResp<Data>> get(Req req) {
        try {
            return OkGo.<BaseResp<Data>>get(req.url())
                    .tag(req.getCancelTag())
                    .cacheMode(req.getCacheMode())
                    .params(req.params())
                    .converter(new TioConvert<Data>(req.bodyType()))
                    .adapt()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * ?????? get
     */
    public static <Req extends BaseReq<Data>, Data> void get(Req req, Callback<BaseResp<Data>> callback) {
        setBodyType(callback, req.bodyType());
        OkGo.<BaseResp<Data>>get(req.url())
                .tag(req.getCancelTag())
                .cacheMode(req.getCacheMode())
                .params(req.params())
                .execute(callback);
    }

    @Deprecated
    public static <Req extends BaseReq<Data>, Data> void get(Object CancelTag, Req req, Callback<BaseResp<Data>> callback) {
        req.setCancelTag(CancelTag);
        get(req, callback);
    }

    /**
     * ?????? post
     */
    public static <Req extends BaseReq<Data>, Data> Response<BaseResp<Data>> post(Req req) {
        try {
            return OkGo.<BaseResp<Data>>post(req.url())
                    .tag(req.getCancelTag())
                    .cacheMode(req.getCacheMode())
                    .params(req.params())
                    .converter(new TioConvert<Data>(req.bodyType()))
                    .adapt()
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * ?????? post
     */
    public static <Req extends BaseReq<Data>, Data> void post(Req req, Callback<BaseResp<Data>> callback) {
        setBodyType(callback, req.bodyType());
        OkGo.<BaseResp<Data>>post(req.url())
                .tag(req.getCancelTag())
                .cacheMode(req.getCacheMode())
                .params(req.params())
                .execute(callback);
    }

    @Deprecated
    public static <Req extends BaseReq<Data>, Data> void post(Object CancelTag, Req req, Callback<BaseResp<Data>> callback) {
        req.setCancelTag(CancelTag);
        post(req, callback);
    }

    /**
     * ?????? upload
     */
    public static <Req extends BaseReq<Data>, Data> Response<BaseResp<Data>> upload(Req req) {
        try {
            PostRequest<BaseResp<Data>> postReq = OkGo.<BaseResp<Data>>post(req.url())
                    .tag(req.getCancelTag())
                    .params(req.params())
                    .converter(new TioConvert<Data>(req.bodyType()));

            TioMap<String, File> fileMap = req.files();
            Set<Map.Entry<String, File>> entries = fileMap.entrySet();
            for (Map.Entry<String, File> entry : entries) {
                String key = entry.getKey();
                File value = entry.getValue();
                postReq.params(key, value);
            }

            return postReq.adapt().execute();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * ?????? upload
     */
    public static <Req extends BaseReq<Data>, Data> void upload(Req req, Callback<BaseResp<Data>> callback) {
        setBodyType(callback, req.bodyType());
        PostRequest<BaseResp<Data>> postReq = OkGo.<BaseResp<Data>>post(req.url())
                .tag(req.getCancelTag())
                .params(req.params());

        TioMap<String, File> fileMap = req.files();
        Set<Map.Entry<String, File>> entries = fileMap.entrySet();
        for (Map.Entry<String, File> entry : entries) {
            String key = entry.getKey();
            File value = entry.getValue();
            postReq.params(key, value);
        }

        postReq.execute(callback);
    }

    /**
     * ?????? upload  put??????
     */
    public static void uploadNew(UploadFileNewReq req, AbsCallback<String> callback) {
        String filePath = req.getFilePath();
        String fileType = req.getFiletype();

        if (!TextUtils.isEmpty(filePath)) {
            PutRequest<String> putRequest = OkGo.<String>put(req.url())
                    .tag(req.getCancelTag());
            if ("1".equals(fileType)) {
                putRequest.upFile(new File(filePath), MediaType.parse("image/*"));
            } else {
                putRequest.upRequestBody(RequestBody.create(MediaType.parse(""), new File(filePath)));
            }
            putRequest.execute(callback);
        }
    }


    public static void downloadNew(String url, TioFileCallback callback) {
        OkGo.<File>get(HttpCache.getResUrl(url))
                .execute(callback);
    }

    public static void getDomainList(DomainListReq req, StringCallback callback) {
        OkGo.<String>get(req.url())
                .tag(req.getCancelTag())
                .cacheMode(req.getCacheMode())
                .params(req.params())
                .execute(callback);
    }

    /**
     * ????????????
     */
    public static void cancel(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

    public static void cancelAll() {
        OkGo.getInstance().cancelAll();
    }

    private static <Data> void setBodyType(Callback<BaseResp<Data>> callback, Type bodyType) {
        if (callback instanceof TioCallback) {
            TioCallback tioCallback = (TioCallback) callback;
            tioCallback.setType(bodyType);
        }
    }

    private static class HttpDns implements Dns {
        @NonNull
        @Override
        public List<InetAddress> lookup(@NonNull String s) throws UnknownHostException {
            DomainBean.Bean domain = DomainManager.getInstance().getCurDomain();
            if (domain != null
                    && domain.server != null
                    && domain.server.api != null
                    && !TextUtils.isEmpty(domain.server.api.url)) {
                if (domain.server.api.url.contains(s)) {
                    if (domain.server.api.iplist != null
                            && !domain.server.api.iplist.isEmpty()) {
                        List<InetAddress> result = new ArrayList<>();
                        for (DomainBean.Bean.Server.ServerBean.Ip item : domain.server.api.iplist) {
                            result.addAll(Arrays.asList(InetAddress.getAllByName(item.ip)));
                        }
                        return result;
                    }
                } else {
                    DomainBean domainBean = DomainManager.getInstance().getData();
                    if (domainBean != null && domainBean.list != null && !domainBean.list.isEmpty()) {
                        for (DomainBean.Bean item : domainBean.list) {
                            if (item != null
                                    && item.server != null
                                    && item.server.api != null
                                    && !TextUtils.isEmpty(item.server.api.url)
                                    && item.server.api.url.contains(s)
                                    && item.server.api.iplist != null
                                    && !item.server.api.iplist.isEmpty()) {
                                List<InetAddress> result = new ArrayList<>();
                                for (DomainBean.Bean.Server.ServerBean.Ip ipBean : item.server.api.iplist) {
                                    result.addAll(Arrays.asList(InetAddress.getAllByName(ipBean.ip)));
                                }
                                return result;
                            }
                        }
                    }
                }
            }

            return Dns.SYSTEM.lookup(s);
        }
    }
}
