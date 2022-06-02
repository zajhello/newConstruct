package com.ski.box.androidutils.utils;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * date : 2020-01-07
 * desc : View 工具
 */
public class ViewUtil {

    @Nullable
    public static String getText(TextView textView) {
        CharSequence charSequence = textView.getText();
        if (charSequence != null) {
            String text = charSequence.toString().trim();
            if (!TextUtils.isEmpty(text)) {
                return text;
            }
        }
        return null;
    }

}
