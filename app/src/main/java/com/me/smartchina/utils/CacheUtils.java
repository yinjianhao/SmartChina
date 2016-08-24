package com.me.smartchina.utils;

import android.content.Context;

/**
 * @auther yjh
 * @date 2016/8/17
 */
public class CacheUtils {

    /**
     * 设置缓存
     *
     * @param ctx
     * @param url  key
     * @param json value
     */
    public static void setCache(Context ctx, String url, String json) {
        PreferencesUtils.setString(ctx, url, json);
    }

    public static String getCache(Context ctx, String url, String defaultValue) {
        return PreferencesUtils.getString(ctx, url, defaultValue);
    }
}
