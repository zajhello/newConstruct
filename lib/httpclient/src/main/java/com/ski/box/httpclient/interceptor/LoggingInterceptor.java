package com.ski.box.httpclient.interceptor;

import com.lzy.okgo.utils.IOUtils;
import com.lzy.okgo.utils.OkLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

public class LoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final int LOG_MAX_LENGTH = 2 * 1024;
    private static final String SEPARATOR = System.getProperty("line.separator");
    private static boolean JSON_FORMAT = true;

    private volatile Level printLevel = Level.NONE;
    private java.util.logging.Level colorLevel;
    private Logger logger;

    public enum Level {
        NONE,       //不打印log
        BASIC,      //只打印 请求首行 和 响应首行
        HEADERS,    //打印请求和响应的所有 Header
        BODY        //所有数据全部打印
    }

    public LoggingInterceptor(String tag) {
        logger = Logger.getLogger(tag);
    }

    public void setPrintLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        printLevel = level;
    }

    public void setColorLevel(java.util.logging.Level level) {
        colorLevel = level;
    }

    public static void setJsonFormat(boolean jsonFormat) {
        JSON_FORMAT = jsonFormat;
    }

    public static boolean isJsonFormat() {
        return JSON_FORMAT;
    }

    private void log(String msg) {
        if (msg == null) return;
        int msgLen = msg.length();
        // 一次性显示
        if (msgLen <= LOG_MAX_LENGTH) {
            logger.log(colorLevel, msg);
            return;
        }
        // 分段显示
        for (int i = 0; i < msgLen; i += LOG_MAX_LENGTH) {
            String substring;
            if (i + LOG_MAX_LENGTH < msgLen) {
                substring = msg.substring(i, i + LOG_MAX_LENGTH);
            } else {
                substring = msg.substring(i);
            }
            if (i == 0) {
                logger.log(colorLevel, substring);
            } else {
                logger.log(colorLevel, "\t\t" + substring);
            }
        }
    }

    private void log_json(String json, int indent) {
        String indentStr = "";
        if (indent > 0 && indent <= 10) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < indent; i++) {
                builder.append("\t");
            }
            indentStr = builder.toString();
        }

        String _json = null;

        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                // 返回格式化的json字符串，其中的数字4是缩进字符数
                _json = jsonObject.toString(4);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                _json = jsonArray.toString(4);
            }
        } catch (JSONException ignored) {
        }

        // json 打印
        if (_json != null && SEPARATOR != null) {
            String[] lines = _json.split(SEPARATOR);

            for (int i = 0, length = lines.length; i < length; i++) {
                String line = lines[i];

                if (i == 0) {
                    // 第一行
                    logger.log(colorLevel, indentStr + "--------------------------------------------------");
                    logger.log(colorLevel, indentStr + "| " + line);
                } else if (i == length - 1) {
                    // 最后一行
                    logger.log(colorLevel, indentStr + "| " + line);
                    logger.log(colorLevel, indentStr + "--------------------------------------------------");
                } else {
                    // 中间行
                    logger.log(colorLevel, indentStr + "| " + line);
                }
            }
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (printLevel == Level.NONE) {
            return chain.proceed(request);
        }

        //请求日志拦截
        logForRequest(request, chain.connection());

        //执行请求，计算请求时间
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        //响应日志拦截
        return logForResponse(response, tookMs);
    }

    private void logForRequest(Request request, Connection connection) throws IOException {
        boolean logBody = (printLevel == Level.BODY);
        boolean logHeaders = (printLevel == Level.BODY || printLevel == Level.HEADERS);
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        try {
            String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
            log(requestStartMessage);

            if (logHeaders) {
                if (hasRequestBody) {
                    // Request body headers are only present when installed as a network interceptor. Force
                    // them to be included (when available) so there values are known.
                    if (requestBody.contentType() != null) {
                        log("\tContent-Type: " + requestBody.contentType());
                    }
                    if (requestBody.contentLength() != -1) {
                        log("\tContent-Length: " + requestBody.contentLength());
                    }
                }
                Headers headers = request.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    String name = headers.name(i);
                    // Skip headers from the request body as they are explicitly logged above.
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                        log("\t" + name + ": " + headers.value(i));
                    }
                }

                log(" ");
                if (logBody && hasRequestBody) {
                    if (isPlaintext(requestBody.contentType())) {
                        bodyToString(request);
                    } else {
                        log("\tbody: maybe [binary body], jhgy omitted!");
                    }
                }
            }
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        } finally {
            log("--> END " + request.method());
        }
    }

    private Response logForResponse(Response response, long tookMs) {
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();
        boolean logBody = (printLevel == Level.BODY);
        boolean logHeaders = (printLevel == Level.BODY || printLevel == Level.HEADERS);

        try {
            log("<-- " + clone.code() + ' ' + clone.message() + ' ' + clone.request().url() + " (" + tookMs + "ms）");
            if (logHeaders) {
                Headers headers = clone.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    log("\t" + headers.name(i) + ": " + headers.value(i));
                }
                log(" ");
                if (logBody && HttpHeaders.hasBody(clone)) {
                    if (responseBody == null) return response;

                    if (isPlaintext(responseBody.contentType())) {
                        byte[] bytes = IOUtils.toByteArray(responseBody.byteStream());
                        MediaType contentType = responseBody.contentType();
                        String body = new String(bytes, getCharset(contentType));
                        if (isJsonFormat()) {
                            log("\tbody: ");
                            log_json(body, 1);
                        } else {
                            log("\tbody: " + body);
                        }
                        responseBody = ResponseBody.create(responseBody.contentType(), bytes);
                        return response.newBuilder().body(responseBody).build();
                    } else {
                        log("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        } finally {
            log("<-- END HTTP");
        }
        return response;
    }

    private static Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) charset = UTF8;
        return charset;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
                return true;
        }
        return false;
    }

    private void bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            RequestBody body = copy.body();
            if (body == null) return;
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset = getCharset(body.contentType());
            log("\tbody:" + buffer.readString(charset));
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        }
    }
}
