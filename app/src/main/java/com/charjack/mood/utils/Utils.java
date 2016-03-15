package com.charjack.mood.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

public class Utils {

    public static boolean isFirst(Context context) {
        SharedPreferences sp = context.getSharedPreferences("isFirst", Context.MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        int versionCode = sp.getInt("versionCode", 1);
        if (isFirst || getVersionCode(context) > versionCode) {
            sp.edit().putBoolean("isFirst", false).commit();
            sp.edit().putInt("versionCode", getVersionCode(context)).commit();
        }
        return isFirst;
    }

    private static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionCode;

    }

    /**
     * sdk/
     * �õ�·��
     * @return
     */
    public static String getAppPath() {
        String appPath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            appPath = Environment.getExternalStorageDirectory() + File.separator + "charjack";
        } else {
            appPath = Environment.getDataDirectory() + File.separator + "com.charjack.mood";
        }
        File file = new File(appPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return appPath;
    }

    /**
     * sdk/xytg/image
     *
     * @return
     */
    public static File getImageCache() {

        String imageCache = getAppPath() + File.separator + "charjackImage";
        File imageFile = new File(imageCache);
        if (!imageFile.exists()) {
            imageFile.mkdirs();
        }
        return imageFile;
    }


}
