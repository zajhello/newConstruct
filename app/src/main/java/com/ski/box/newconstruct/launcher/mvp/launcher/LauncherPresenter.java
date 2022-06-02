package com.ski.box.newconstruct.launcher.mvp.launcher;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.stream.JsonReader;
import com.ski.box.common.widget.TioToast;
import com.ski.box.httpclient.TioHttpClient;
import com.ski.box.httpclient.model.request.DomainListReq;
import com.ski.box.httpclient.utils.DeviceUtils;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * desc : 应用启动表现层
 */
public class LauncherPresenter extends LauncherContract.Presenter {

    private long startTime, endTime;

    public LauncherPresenter(LauncherContract.View view) {
        super(view);
    }

    @Override
    public void detachView() {
        super.detachView();

    }

    @Override
    public void init() {
        startTime = System.currentTimeMillis();
        getView().openLoginPage();
    }

    private void reqPermission() {
        // 请求权限
        PermissionUtils.permission(PermissionConstants.PHONE)
                .rationale((activity, shouldRequest) -> shouldRequest.again(true))
                .callback((isAllGranted, granted, deniedForever, denied) -> getDeviceId(getView().getActivity()))
                .request();
    }



    private void getDeviceId(Activity activity) {

        String deviceName = Build.PRODUCT;
        String imei = DeviceUtils.getImei(activity);
        String uiId = com.blankj.utilcode.util.DeviceUtils.getUniqueDeviceId();
        String deviceModel = Build.MODEL;

    }

    private void exitApp(String reason) {
        TioToast.showShort(reason);
        AppUtils.exitApp();
    }
}
