package com.ski.box.httpclient.ping;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ski.box.httpclient.domain_manager.DomainBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class PingManager {
    private static volatile PingManager INSTANCE;

    public static final String LOCAL_HOST = "127.0.0.1";

    private PingManager() {

    }

    public static PingManager getInstance() {
        if (INSTANCE == null) {
            synchronized (PingManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PingManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 异步ping
     *
     * @param callback
     */
    public void pingAsync(DomainBean domainBean, PingCallback callback) {
        if (domainBean == null || domainBean.list == null || domainBean.list.size() == 0) {
            return;
        }
        ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
            @Override
            public Void doInBackground() {
                for (int i = 0; i < domainBean.list.size(); i++) {
                    DomainBean.Bean item = domainBean.list.get(i);
                    String delay = getNetworkDelayPingIP(item.server.app.url);
                    item.server.app.delay = delay;
                    if (!TextUtils.isEmpty(delay) && isNumeric(delay)) {
                        int delayTime = Integer.parseInt(delay);
                        if (delayTime < DomainBean.DELAY_200 && delayTime > DomainBean.DELAY_INVALID) {
                            item.server.app.signal = DomainBean.SignalLevel.HIGH;
                        } else if (delayTime < DomainBean.DELAY_500 && delayTime > DomainBean.DELAY_200) {
                            item.server.app.signal = DomainBean.SignalLevel.MID;
                        } else {
                            item.server.app.signal = DomainBean.SignalLevel.LOW;
                        }
                    } else {
                        item.server.app.signal = DomainBean.SignalLevel.LOW;
                    }
                }
                if (callback != null) {
                    callback.onResult();
                }
                return null;
            }

            @Override
            public void onSuccess(Void result) {

            }
        });
    }

    private String getNetworkDelayPingIP(String ip) {
        if (ip.startsWith("http://")) {
            ip = ip.replaceFirst("http://", "");
        }

        if (ip.startsWith("https://")) {
            ip = ip.replaceFirst("https://", "");
        }

        String delay = "" + DomainBean.DELAY_LARGE;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 1 " + ip);
            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str;
            if ((buf.readLine()) == null) {
                LogUtils.e("Ping:" + ip + ",ping不通");
            }
            while ((str = buf.readLine()) != null) {
                //LogUtil.e("获取到数据" + str);
                if (str.contains("from")) {
                    int i = str.indexOf("from");
                    int j = str.indexOf(":");
                    String host = str.substring(i + 4, j).trim();
                    LogUtils.e("Ping host:" + host);
                    if (PingManager.LOCAL_HOST.equals(host)) {
                        return "" + DomainBean.DELAY_LARGE;
                    }
                }
                if (str.contains("avg")) {
                    //从第21个字符开始查找，“/”的位置，包括第21个字符
                    int i = str.indexOf("/", 20);
                    int j = str.indexOf(".", i);
                    //截取str中从beginIndex开始至endIndex结束时的字符串，   beginIndex =< str的值 < endIndex并将其赋值给str;
                    //LogUtil.e("Ping:" + ip + "延迟:" + str.substring(i + 1, j));
                    delay = str.substring(i + 1, j);
                    if (isNumeric(delay) && Integer.parseInt(delay) < DomainBean.DELAY_INVALID){
                        delay = "" + DomainBean.DELAY_LARGE;
                    }
                }
            }
            LogUtils.e("Ping:" + ip + " 平均延迟为：" + delay);
        } catch (IOException e) {
            e.printStackTrace();
            delay = "error";
        }

        return delay;
    }

    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public interface PingCallback {
        void onResult();
    }
}
