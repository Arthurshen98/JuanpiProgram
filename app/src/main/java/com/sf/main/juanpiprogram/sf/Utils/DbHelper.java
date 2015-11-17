package com.sf.main.juanpiprogram.sf.Utils;

import android.content.Context;

import com.lidroid.xutils.DbUtils;

/**
 * Created by jash
 * Date: 15-11-4
 * Time: 上午10:52
 */
public class DbHelper {
    private static DbUtils utils;
    public static void init(Context context){
        utils = DbUtils.create(context);
        utils.configAllowTransaction(true);
        utils.configDebug(true);
    }

    public static DbUtils getUtils() {
        return utils;
    }
}
