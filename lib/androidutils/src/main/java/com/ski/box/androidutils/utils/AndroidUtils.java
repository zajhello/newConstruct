package com.ski.box.androidutils.utils;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;


/**
 * <pre>
 *     time   : 2020/11/05
 *     desc   :
 * </pre>
 */
public class AndroidUtils {

    private static ChangeBgBizListener changeBgBizListener;

    /**
     * 初始化
     */
    public static void init(Application app, ChangeBgBizListener changeBgBizListener) {
        // pinyin
        PinYin.init(app);
        // fresco
        Fresco.initialize(
                app,
                ImagePipelineConfig.newBuilder(app)
                        .build()
        );

        AndroidUtils.changeBgBizListener = changeBgBizListener;
    }

    public static ChangeBgBizListener getChangeBgBizListener() {
        return changeBgBizListener;
    }

    public interface ChangeBgBizListener {
        void onChange(boolean isNeed);
    }
}
