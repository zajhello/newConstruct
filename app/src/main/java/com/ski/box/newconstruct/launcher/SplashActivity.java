package com.ski.box.newconstruct.launcher;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ski.box.account.feature.LoginActivity;
import com.ski.box.common.page.TioActivity;
import com.ski.box.common.tools.TioLogger;
import com.ski.box.newconstruct.R;
import com.ski.box.newconstruct.launcher.mvp.launcher.LauncherContract;
import com.ski.box.newconstruct.launcher.mvp.launcher.LauncherPresenter;

public class SplashActivity extends TioActivity implements LauncherContract.View {
    @Nullable
    private LauncherPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 问题：用android的installer安装打开闪屏页，按Home键回到首页，然后点击launcher的图标会再打开一个闪屏页。
        // 原因：installer安装方式打开闪屏页，系统会创建了新的Task中用于存放闪屏页实例，从而导致重复启动闪屏页面。
        // 解决办法：避免从桌面启动程序后，会重新实例化入口类的activity。
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) > 0) {
            finish();
            return;
        }
        setContentView(R.layout.tio_activity_welcome);
        presenter = new LauncherPresenter(this);
        presenter.init();

        TioLogger.i("解析华为推送过来的数据, activity: MainActivity");
    }

    @Override
    public void openLoginPage() {
        LoginActivity.start(this);
    }

    @Override
    public void openMainPage() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

}
