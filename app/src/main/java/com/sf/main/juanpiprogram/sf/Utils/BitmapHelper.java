package com.sf.main.juanpiprogram.sf.utils;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;
import com.sf.main.juanpiprogram.R;


/**
 * Created by Administrator on 15-11-16.
 */
public class BitmapHelper {
    private static BitmapUtils utils;
    public static void init(Context context){
        utils=new BitmapUtils(context,null,0.125f);
        utils.configDefaultLoadFailedImage(R.mipmap.ic_launcher);
        utils.configDefaultLoadingImage(R.mipmap.ic_launcher);
    }

    public static BitmapUtils getUtils() {
        return utils;
    }
}
