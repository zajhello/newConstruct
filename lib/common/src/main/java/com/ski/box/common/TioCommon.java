package com.ski.box.common;

import androidx.annotation.NonNull;

public class TioCommon {
    private static Bridge bridge;


    public static void init(@NonNull Bridge bridge) {
        TioCommon.bridge = bridge;
    }

    public static Bridge getBridge() {
        return bridge;
    }

    public interface Bridge {

    }
}
