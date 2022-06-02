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
public abstract class MvpFragment<P extends BasePresenter<?, ?>, T extends ViewDataBinding> extends BindingFragment<T> {

    protected P presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // mvp
        presenter = newPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        if (presenter == null) {
            presenter = newPresenter();
        }
        return (P) presenter;
    }
}
