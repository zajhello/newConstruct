package com.ski.box.db;


public class TioDB {

    private static Bridge BRIDGE = null;

    public static void init(Bridge bridge) {
        BRIDGE = bridge;
    }

    public static Bridge getBridge() {
        return BRIDGE;
    }

    public interface Bridge {

    }
}
