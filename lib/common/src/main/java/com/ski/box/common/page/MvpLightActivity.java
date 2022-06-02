package com.ski.box.common.page;

import androidx.databinding.ViewDataBinding;

import com.blankj.utilcode.util.ColorUtils;
import com.ski.box.common.R;
import com.ski.box.base.mvp.BasePresenter;


/**
 * <pre>
 *     time   : 2020/10/29
 *     desc   : 浅色主题：默认背景色、白色状态栏
 * </pre>
 */
public abstract class MvpLightActivity<P extends BasePresenter<?, ?>, T extends ViewDataBinding> extends MvpActivity<P, T> {
    @Override
    protected Boolean statusBar_lightMode() {
        return true;
    }

    @Override
    protected Integer statusBar_color() {
        return ColorUtils.getColor(R.color.gray_f3f3f3);
    }
}
