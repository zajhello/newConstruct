package com.ski.box.common.tools;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.ski.box.httpclient.model.BaseResp;

import java.io.File;
import java.io.FilenameFilter;

public class CrashLogUtils {
    private static final String FILE_SEP = System.getProperty("file.separator");

    private static class InnerClass {
        private static final CrashLogUtils mUtils = new CrashLogUtils();
    }

    public static CrashLogUtils getInstance() {
        return InnerClass.mUtils;
    }

    private CrashLogUtils() {
    }

    /**
     * 监听崩溃
     */
    public void listener() {
        // 生成的崩溃日志如下：
        // "/storage/emulated/0/Android/data/com.tiocloud.chat/files/crash/2020_07_01-15_07_33.txt"
        CrashUtils.init();
    }

    /**
     * 上传崩溃
     */
    public void upload() {
        uploadLog();
    }

    private void uploadLog() {
        ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Void>() {
            @Override
            public Void doInBackground() throws Throwable {
                uploadLogInternal();
                return null;
            }

            @Override
            public void onSuccess(Void result) {

            }
        });
    }

    private void uploadLogInternal() {
        String crashDir = getCrashDir();
        File crashFile = new File(crashDir);
        File[] logs = crashFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
        if (logs != null) {
            for (File log : logs) {
                String absolutePath = log.getAbsolutePath();
                TioLogger.d("准备上传崩溃日志：" + absolutePath);
                boolean ok = reqUpload(absolutePath);
                TioLogger.d("崩溃日志上传成功：" + absolutePath);
                if (ok) {
                    File file = new File(absolutePath);
                    String newNameName = absolutePath.replace(".txt", ".log");
                    File newFile = new File(newNameName);
                    boolean success = file.renameTo(newFile);
                    if (success) {
                        TioLogger.d("崩溃修改成功：" + newFile.getAbsolutePath());
                    }
                }
            }
        }
    }

    private boolean reqUpload(String absolutePath) {

        return true;
    }

    private String getCrashDir() {
        String dirPath;
        if (SDCardUtils.isSDCardEnableByEnvironment()
                && Utils.getApp().getExternalFilesDir(null) != null)
            dirPath = Utils.getApp().getExternalFilesDir(null) + FILE_SEP + "crash" + FILE_SEP;
        else {
            dirPath = Utils.getApp().getFilesDir() + FILE_SEP + "crash" + FILE_SEP;
        }
        return dirPath;
    }
}
