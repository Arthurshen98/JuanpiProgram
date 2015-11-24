package com.sf.main.juanpiprogram.sf.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

public class MuyingActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muying);
    }

    //返回关闭界面
    public void toFinish(View v) {
        finish();
    }
}
