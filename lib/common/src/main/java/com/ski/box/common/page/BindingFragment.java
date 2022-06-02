package com.ski.box.common.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * <pre>
 *     time   : 2020/12/24
 *     desc   :
 * </pre>
 */
public abstract class BindingFragment<T extends ViewDataBinding> extends TioFragment {
    // public final ObservableField<String> fromInfo = new ObservableField<>("小生的红包");

    protected T binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.unbind();
        }
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
