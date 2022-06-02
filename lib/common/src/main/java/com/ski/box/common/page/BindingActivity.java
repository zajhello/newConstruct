package com.ski.box.common.page;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * <pre>
 *     time   : 2020/10/28
 *     desc   :
 * </pre>
 */
public abstract class BindingActivity<T extends ViewDataBinding> extends TioActivity {
    // public final ObservableField<String> fromInfo = new ObservableField<>("小生的红包");

    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定布局
        binding = DataBindingUtil.setContentView(this, getContentViewId());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
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

    /**
     * 返回有值时，会取消状态栏的高度
     */
    @ColorInt
    protected Integer statusBar_color() {
        return Color.parseColor("#f3f3f3");
    }

    protected View statusBar_holder() {
        return null;
    }

    // ====================================================================================
    // contentView
    // ====================================================================================

    @LayoutRes
    protected abstract int getContentViewId();

    @NonNull
    public T getBinding() {
        return binding;
    }
}
