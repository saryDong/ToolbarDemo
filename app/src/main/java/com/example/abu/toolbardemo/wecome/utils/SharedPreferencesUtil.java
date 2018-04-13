package com.example.abu.toolbardemo.wecome.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * created by 董长峰 on 2018/4/12.
 */
public class SharedPreferencesUtil {
    //存储是否第一次启动的信息文件名
    private static final String spFileName = "welcomePage";
    //存储是否第一次启动信息所对应的键
    public static final String FIRST_OPEN = "first_open";

    //获取是否是第一次启动
    public static Boolean getBoolean(Context context, String strKey,
                                     Boolean strDefault) {//strDefault	boolean: Value to return if this preference does not exist.
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }

    //修改是否第一次启动信息
    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }
}