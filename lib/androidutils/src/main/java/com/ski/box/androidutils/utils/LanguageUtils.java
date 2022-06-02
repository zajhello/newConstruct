package com.ski.box.androidutils.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

import java.util.Locale;


public class LanguageUtils {
    public final static Locale CHINA = Locale.CHINA;
    public final static Locale ENGLISH = Locale.ENGLISH;
    public final static Locale TAIWAN = Locale.TAIWAN;

    // ====================================================================================
    // 设置成系统默认的语言类型
    // ====================================================================================
    public static void setSystemLanguageTypeGlobal() {
        setSystemLanguageType(Utils.getApp());
    }

    public static void setSystemLanguageType(Context context) {
        changeLanguageType(context, Locale.getDefault());
//        if (CHINA.getLanguage().equals(Locale.getDefault().getLanguage()) || ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())){
//            changeLanguageType(context, Locale.getDefault());
//        }else{
//            setDefaultLanguageType(context);
//        }
    }


    // ====================================================================================
    // 设置成默认的语言类型
    // ====================================================================================
    public static void setDefaultLanguageTypeGlobal() {
        setDefaultLanguageType(Utils.getApp());
    }

    public static void setDefaultLanguageType(Context context){
        changeLanguageType(context, CHINA);
    }


    // ====================================================================================
    // 改变语言类型（全局）
    // ====================================================================================
    public static void changeLanguageTypeGlobal(Locale localelanguage) {
        changeLanguageType(Utils.getApp(), localelanguage);
    }

    public static void changeLanguageType(Context context, Locale localelanguage) {
        Log.i("=======", "mContext = " + context);
//        Resources resources = mContext.getResources();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        // 参考 https://developer.android.com/reference/android/content/res/Configuration.html

        //核心切换语言
        if (isAfter17()) {
            config.setLocale(localelanguage);
        } else {
            config.locale = localelanguage;
        }

        resources.updateConfiguration(config, dm);  //更新
    }


    // ====================================================================================
    // 获取语言类型
    // ====================================================================================
    public static Locale getLanguageTypeGlobal(){
        return getLanguageType(Utils.getApp());
    }

    public static Locale getLanguageType(Context context) {
        Log.i("=======", "mContext = " + context);
//        Resources resources = mContext.getResources();
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        if (isAfter24()) {
            return config.getLocales().get(0);
        } else {
            return config.locale;
        }
    }


    public static boolean isAfter24() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean isAfter17() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }
}
