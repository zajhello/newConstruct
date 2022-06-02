package com.ski.box.common.tools;

import com.blankj.utilcode.util.ThreadUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *     time   : 2020/08/05
 *     desc   : 计时器实现
 * </pre>
 */
public class WtTimer {
    private Timer mTimer;
    private TimerTask mTimerTask;

    /**
     * 开始执行
     *
     * @param listener        监听
     * @param isRunMainThread 是否运行在主线程，默认"否"
     * @param delay           延迟就多久开始执行
     * @param period          每次间隔多久
     */
    public void start(final OnTickListener listener, boolean isRunMainThread, long delay, long period) {
        stop();

        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                private int mCount = 0;

                @Override
                public void run() {
                    if (isRunMainThread && !ThreadUtils.isMainThread()) {
                        ThreadUtils.runOnUiThread(this::onTickNtf);
                    } else {
                        onTickNtf();
                    }
                }

                private void onTickNtf() {
                    if (listener != null) {
                        listener.onTick(mCount, WtTimer.this);
                    }
                    mCount++;
                }
            };
        }

        mTimer.schedule(mTimerTask, delay, period);
    }

    /**
     * 停止
     */
    public void stop() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public interface OnTickListener {
        /**
         * @param count 执行次数（0，1，2，3，...，n）
         */
        void onTick(int count, WtTimer timer);
    }
}
