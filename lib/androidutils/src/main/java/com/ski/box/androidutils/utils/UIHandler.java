package com.ski.box.androidutils.utils;

import android.os.Handler;
import android.os.Looper;

public class UIHandler  extends Handler {
    public UIHandler() {
        super(Looper.getMainLooper());
    }

    /**
     * 切换到UI线程
     *
     * @param runnable Runnable
     */
    public void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            UIHandler.this.post(runnable);
        } else {
            runnable.run();
        }
    }
}