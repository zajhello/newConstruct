package com.ski.box.common.page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.ski.box.base.mvp.BasePresenter;


/**
 * <pre>
 *     time   : 2020/10/28
 *     desc   :
 * </pre>
 */
public abstract class MvpActivity<P extends BasePresenter<?, ?>, T extends ViewDataBinding> extends BindingActivity<T> {

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mvp
        presenter = newPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    // ====================================================================================
    // mvp
    // ====================================================================================

    public abstract P newPresenter();

    @NonNull
    public P getPresenter() {
        return presenter;
    }
}
