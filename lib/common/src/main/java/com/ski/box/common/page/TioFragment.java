package com.ski.box.common.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * author : Dipper
 * date : 2020-02-06
 * desc : 取消网络请求
 */
public abstract class TioFragment extends BaseFragment {

    @Override
    public TioFragment getFragment() {
        return this;
    }

    public TioActivity getTioActivity() {
        FragmentActivity activity = super.getActivity();
        if (activity instanceof TioActivity) {
            return (TioActivity) activity;
        }
        return null;
    }

    public void finish() {
        FragmentActivity activity = super.getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 状态栏
        View statusBarHolder = statusBar_holder();
        if (statusBarHolder != null) {
            // 状态栏占位
            addMarginTopEqualStatusBarHeight(statusBarHolder);
            // 状态栏颜色
            Integer statusBarColor = statusBar_color();
            if (statusBarColor != null) {
                setStatusBarColor(statusBarColor);
            }
            // 状态栏模式
            Boolean statusBarLightMode = statusBar_lightMode();
            if (statusBarLightMode != null) {
                setStatusBarLightMode(statusBarLightMode);
            }
        }
    }

    // ====================================================================================
    // statusBar
    // ====================================================================================

    /**
     * true: LightMode(浅色) 状态栏字体为黑
     * false: DarkMode(深色) 状态栏字体为白
     */
    protected Boolean statusBar_lightMode() {
        return null;
    }

    @ColorInt
    protected Integer statusBar_color() {
        return null;
    }

    protected View statusBar_holder() {
        return null;
    }
}
