package com.ski.box.newconstruct;


import androidx.multidex.MultiDexApplication;

public class TioApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

//        long startTime = System.currentTimeMillis();
//        Log.e("xxxxxx", "AppStartTime:" + startTime);
        AppLauncher.getInstance().init(this);
//        long endTime = System.currentTimeMillis();
//        Log.e("xxxxxx", "AppEndTime:" + endTime);
//        Log.e("xxxxxx", "AppOnCreateTime:" + (endTime - startTime) + "\r\n");
    }

}
