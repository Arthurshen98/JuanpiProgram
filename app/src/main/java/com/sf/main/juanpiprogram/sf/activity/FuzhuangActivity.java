package com.sf.main.juanpiprogram.sf.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

public class FuzhuangActivity extends SwipeBackActivity {
    private SystemBarTintManager mTintManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuzhuang);


        setTranslucentStatus();
    }

    private void setTranslucentStatus() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);  mTintManager.setStatusBarTintResource(R.color.juanpi_main);
    }
    //返回关闭界面
    public void toFinish(View v) {
        finish();
    }
}
