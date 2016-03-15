package com.charjack.mood.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.*;
/**
 * Created by Administrator on 2016/3/15.
 */
public class ImageLoadHelper {

    private static ImageLoadHelper helper;
    private ImageLoadHelper() {
    }
    public static ImageLoadHelper getInstance() {
        if (helper == null) {
            helper = new ImageLoadHelper();
        }
        return helper;
    }


    public DisplayImageOptions getDisplayImageOptions(int resFailId, int resLoadingId) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(true);
        builder.cacheOnDisk(true);
        builder.showImageOnLoading(resLoadingId);
        builder.showImageOnFail(resFailId);
        return builder.build();
    }
}