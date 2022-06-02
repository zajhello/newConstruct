package com.ski.box.common.tools;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     time   : 2020/07/29
 *     desc   : 系统文件选择器
 * </pre>
 */
public class FilePicker {
    private static final int REQUEST_CODE = 2020;

    /**
     * 打开文件系统
     */
    public static void openSystemFile(Activity activity) {
        reqStoragePermission(() -> openSystemFileInternal(activity));
    }

    /**
     * 打开文件系统
     */
    public static void openSystemFile(Fragment fragment) {
        reqStoragePermission(() -> openSystemFileInternal(fragment));
    }

    private static void reqStoragePermission(Runnable runnable) {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale((activity2, shouldRequest) -> shouldRequest.again(true))
                .callback((isAllGranted, granted, deniedForever, denied) -> {
                    if (isAllGranted) {
                        runnable.run();
                    } else if (!deniedForever.isEmpty()) {
                        ToastUtils.showShort("存储权限被禁用，请打开权限！");
                        PermissionUtils.launchAppDetailsSettings();
                    }
                })
                .request();
    }

    private static void openSystemFileInternal(Activity activity) {
        try {
            Intent fileIntent = getNewFileIntent();
            activity.startActivityForResult(fileIntent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showShort("请安装文件管理器");
        }
    }

    private static void openSystemFileInternal(Fragment fragment) {
        try {
            Intent fileIntent = getNewFileIntent();
            fragment.startActivityForResult(fileIntent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showShort("请安装文件管理器");
        }
    }

    private static Intent getFileIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return Intent.createChooser(intent, "请选择文件");
    }

    private static Intent getNewFileIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return Intent.createChooser(intent, "请选择文件");
    }

    /**
     * 拿到 "文件系统页" 返回的数据
     *
     * @return 文件路径
     */
    public static List<String> onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        List<String> paths = new ArrayList<>();
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Get the Uri of the selected file
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    paths.add(ContentUriUtil.getPath(Utils.getApp(), uri));
                } else {
                    ClipData clipData = data.getClipData();
                    if (clipData == null) return null;
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri clipUri = item.getUri();
                        if (clipUri != null) {
                            paths.add(ContentUriUtil.getPath(Utils.getApp(), clipUri));
                        }
                    }
                }
            }
        }
        return paths;
    }

    private static class ContentUriUtil {
        /**
         * Get a file path from a Uri. This will get the the path for Storage Access
         * Framework Documents, as well as the _data field for the MediaStore and
         * other file-based ContentProviders.
         *
         * @param context The context.
         * @param uri     The Uri to query.
         * @author paulburke
         */
        public static String getPath(final Context context, final Uri uri) {

            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return getFilePathForN(context, uri);
            } else
                // DocumentProvider
                if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                    // ExternalStorageProvider
                    if (isExternalStorageDocument(uri)) {
                        final String docId = DocumentsContract.getDocumentId(uri);
                        final String[] split = docId.split(":");
                        final String type = split[0];

                        if ("primary".equalsIgnoreCase(type)) {
                            return Environment.getExternalStorageDirectory() + "/" + split[1];
                        }

                    }
                    // DownloadsProvider
                    else if (isDownloadsDocument(uri)) {

                        long _id = -1;
                        try {
                            final String id = DocumentsContract.getDocumentId(uri);
                            _id = Long.parseLong(id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Uri contentUri = null;
                        if (_id != -1) {
                            contentUri = ContentUris.withAppendedId(
                                    Uri.parse("content://downloads/public_downloads"), _id);
                        }

                        if (contentUri != null) {
                            return getDataColumn(context, contentUri, null, null);
                        }
                    }
                    // MediaProvider
                    else if (isMediaDocument(uri)) {
                        final String docId = DocumentsContract.getDocumentId(uri);
                        final String[] split = docId.split(":");
                        final String type = split[0];

                        Uri contentUri = null;
                        if ("image".equals(type)) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(type)) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(type)) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }

                        final String selection = "_id=?";
                        final String[] selectionArgs = new String[]{
                                split[1]
                        };

                        return getDataColumn(context, contentUri, selection, selectionArgs);
                    }
                }
                // MediaStore (and general)
                else if ("content".equalsIgnoreCase(uri.getScheme())) {

                    // Return the remote address
                    if (isGooglePhotosUri(uri))
                        return uri.getLastPathSegment();

                    return getDataColumn(context, uri, null, null);
                }
                // File
                else if ("file".equalsIgnoreCase(uri.getScheme())) {
                    return uri.getPath();
                }

            return null;
        }

        /**
         * android7.0以上处理方法
         */
        private static String getFilePathForN(Context context, Uri uri) {
            try {
                Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                String name = (returnCursor.getString(nameIndex));
                File file = new File(context.getFilesDir(), name);
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                FileOutputStream outputStream = new FileOutputStream(file);
                int read = 0;
                int maxBufferSize = 1024 * 1024;
                int bytesAvailable = inputStream.available();
                if (bytesAvailable != 0) {
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    final byte[] buffers = new byte[bufferSize];
                    while ((read = inputStream.read(buffers)) != -1) {
                        outputStream.write(buffers, 0, read);
                    }
                }
                returnCursor.close();
                inputStream.close();
                outputStream.close();
                if (bytesAvailable == 0) {
                    return "0";
                }
                return file.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * *
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {
                    column
            };

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } catch (Exception ignored) {

            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        private static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        private static boolean isGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.content".equals(uri.getAuthority());
        }
    }
}
