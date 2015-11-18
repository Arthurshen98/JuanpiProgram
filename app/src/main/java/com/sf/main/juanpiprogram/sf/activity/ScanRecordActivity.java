package com.sf.main.juanpiprogram.sf.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.sf.main.juanpiprogram.R;

public class ScanRecordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉actionBar
        setContentView(R.layout.activity_scan_record);
    }

    //返回关闭界面
    public void toFinish(View v) {
        finish();
    }
}
