package com.sf.main.juanpiprogram.sf.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sf.main.juanpiprogram.R;

/**
 * Created by Administrator on 15-11-23.
 */
public class SwipeBackActivity extends FragmentActivity {
    protected SwipeBackLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉actionBar
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipe_back_layout_base, null);
        layout.attachToActivity(this);



    }
    /**
     *  沉浸式状态栏
     */


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }




    // Press the back button in mobile phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }
}
