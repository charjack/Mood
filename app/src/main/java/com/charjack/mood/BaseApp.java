package com.charjack.mood;

/**
 * Created by Administrator on 2016/3/15.
 */
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.charjack.mood.utils.Utils;
import com.lidroid.xutils.DbUtils;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.prefs.Preferences;

/**
 * Created by Administrator on 15-8-17.
 */
public class BaseApp extends Application {
    public static DbUtils db;
    public static Context context;
    public static String list_preference_first;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        initImageLoadGlobalConfig();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        list_preference_first = mySharedPreferences.getString("list_preference", "null");
    }

    public static Context getContext() {
        return context;
    }

    private void initImageLoadGlobalConfig() {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this);
        DiskCache cache = new UnlimitedDiskCache(Utils.getImageCache());
        //设置线程并发数量
        builder.threadPoolSize(5);
        //配置文件保存的路径
        builder.diskCache(cache);
        //设置保存图片的最大张数
        builder.diskCacheFileCount(500);
        //配置磁盘缓存保存图片的最大内存
        builder.diskCacheSize(50 * 1024 * 1024);
        // 获取系统分配给应用程序的最大内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int maxSize = maxMemory / 8;
        //配置内存缓存的大小
        builder.memoryCacheSize(maxSize);
        //配置保存图片的命名器
        builder.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        ImageLoader.getInstance().init(builder.build());


    }


}
