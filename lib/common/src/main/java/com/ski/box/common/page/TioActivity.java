package com.ski.box.common.page;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.ski.box.androidutils.utils.LanguageUtils;
import com.ski.box.androidutils.utils.TioContextWrapper;

import java.util.Locale;

/**
 * desc : Activity 栈管理
 */
public abstract class TioActivity extends BaseActivity {

    @Override
    public TioActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置window背景色
        Integer backgroundColor = background_color();
        if (backgroundColor != null) {
            setBackgroundColor(backgroundColor);
        }
    }

    // ====================================================================================
    // background
    // ====================================================================================

    @ColorInt
    protected Integer background_color() {
        return Color.parseColor("#f3f3f3");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = LanguageUtils.getLanguageTypeGlobal();
        super.attachBaseContext(TioContextWrapper.wrap(newBase, languageType));
    }
}
