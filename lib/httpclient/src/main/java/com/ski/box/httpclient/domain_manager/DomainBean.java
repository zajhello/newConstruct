package com.ski.box.httpclient.domain_manager;

import java.io.Serializable;
import java.util.List;

public class DomainBean implements Serializable {
    /**
     * 延时毫秒数1 ping到的是本地的ip的话，延时会少于1ms
     */
    public static final int DELAY_INVALID = 1;

    /**
     * 延时毫秒数1 ping到的是本地的ip的话，延时会少于1ms
     */
    public static final int DELAY_LARGE = 99999;

    /**
     * 延时毫秒数200
     */
    public static final int DELAY_200 = 200;

    /**
     * 延时毫秒数500
     */
    public static final int DELAY_500 = 500;

    public static class SignalLevel {
        public static final int HIGH = 10;

        public static final int MID = 5;

        public static final int LOW = 1;
    }

    public List<Bean> list;

    public static class Bean implements Serializable {
        public String id;
        public String name;
        public Server server;

        public static class Server implements Serializable {
            public ServerBean api;
            public ServerBean app;
            public ServerBean pc;
            public ServerBean PCapi;
            public ServerBean ws;

            public static class ServerBean implements Serializable {
                public String url;
                public int port;
                public int ssl;
                public List<Ip> iplist;

                /**
                 * 延时毫秒
                 */
                public String delay;

                /**
                 * 信号等级(延时越低数值越高)
                 */
                public int signal;

                public static class Ip implements Serializable {
                    public String ip;
                    public int port;
                }
            }
        }
    }
}
