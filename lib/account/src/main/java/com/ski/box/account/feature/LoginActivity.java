package com.ski.box.account.feature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.ski.box.account.R;
import com.ski.box.account.mvp.login.LoginContract;
import com.ski.box.account.mvp.login.LoginPresenter;
import com.ski.box.common.page.BindingActivity;

public class LoginActivity extends BindingActivity<com.ski.box.account.databinding.TioLoginActivityBinding> implements LoginContract.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private LoginPresenter presenter;

    public final ObservableField<String> txt_area_code = new ObservableField<>("86");

    @Override
    protected View statusBar_holder() {
        return binding.statusBar;
    }

    @Override
    protected Integer statusBar_color() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    protected Integer background_color() {
        return Color.WHITE;
    }

    @Override
    protected Boolean statusBar_lightMode() {
        return true;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.tio_login_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setData(LoginActivity.this);
        presenter = new LoginPresenter(this);
        resetUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void resetUI() {

        txt_area_code.set("44");
    }

}
