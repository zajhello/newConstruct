package com.ski.box.common.page;

import androidx.databinding.ViewDataBinding;

/**
 * <pre>
 *     time   : 2020/10/29
 *     desc   : 浅色主题：默认背景色、白色状态栏
 * </pre>
 */
public abstract class BindingLightActivity<T extends ViewDataBinding> extends BindingActivity<T> {
    @Override
    protected Boolean statusBar_lightMode() {
        return true;
    }

//    @Override
//    protected Integer statusBar_color() {
//        return Color.WHITE;
//    }
}
