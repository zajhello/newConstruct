package com.ski.box.common.page;

import androidx.databinding.ViewDataBinding;

import com.ski.box.common.R;
import com.ski.box.base.mvp.BasePresenter;


/**
 * <pre>
 *     time   : 2020/10/29
 *     desc   : 深色主题：默认背景色、蓝色状态栏
 * </pre>
 */
public abstract class MvpDarkActivity<P extends BasePresenter<?, ?>, T extends ViewDataBinding> extends MvpActivity<P, T> {
    @Override
    protected Boolean statusBar_lightMode() {
        return false;
    }

    @Override
    public Integer statusBar_color() {
        return getResources().getColor(R.color.blue_4c94ff);
    }
}
